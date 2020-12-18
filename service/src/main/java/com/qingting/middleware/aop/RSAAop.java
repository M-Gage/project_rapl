package com.qingting.middleware.aop;

import com.alibaba.fastjson.JSONObject;
import com.qingting.middleware.bean.request.ModelParamBean;
import com.qingting.middleware.util.JsonResult;
import com.qingting.middleware.util.RSAEncryptionUtil;
import com.qingting.middleware.annotation.RSA;
import com.qingting.middleware.baseController.BaseController;
import com.qingting.middleware.enums.Code;
import com.qingting.middleware.exception.MyException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;

@Slf4j
@Aspect
@Component
@Order(1)
public class  RSAAop extends BaseController {

    @Autowired
    private Environment env;

    /**
     * 加密解密切面（环绕）
     *
     * @param joinPoint 环绕切入点对象
     * @param rsa       方法上的注解对象
     * @return 方法抛出的结果
     * @throws Throwable
     */
    @Around(value = "@annotation(rsa)", argNames = "joinPoint,rsa")
    public Object before(ProceedingJoinPoint joinPoint, RSA rsa) throws Throwable {
        Signature signature = joinPoint.getSignature();
        log.info("RSA加解密切面method:[{}]", signature.getName());
        //获取目标方法的参数信息
        Object[] obj = joinPoint.getArgs();
        Object[] objects = obj;
        //如果是前置或者环绕那么要进行解密
        if (rsa.isBefore() || rsa.isAround()) {
            //创建新的参数组
            objects = new Object[obj.length];
            //处理参数（post）
            for (int i = 0; i < obj.length; i++) {
                if (obj[i] instanceof String) {
                    Map map = (Map) JSONObject.parse((String) obj[i]);
                    //实际需要的解密内容
                    String data = (String) map.get("data");
                    //私钥解密
                    String decrypt = RSAEncryptionUtil.decryptByPrivateKey(data, getPrivateKey(), true);
                    log.debug("解密后参数：data -> [{}]", decrypt);
                    //存入参数组
                    objects[i] = decrypt;
                }
                //模型解密
                if (obj[i] instanceof ModelParamBean) {
                    ModelParamBean param = (ModelParamBean) obj[i];
                    //获取yml的配置
                    String property = env.getProperty("model.encrypt.param");
                    if (property == null) {
                        throw new MyException(Code.YML_ERROR);
                    }
                    String[] fields = property.split(",");
                    Class<? extends ModelParamBean> clazz = param.getClass();
                    for (String field : fields) {
                        Method get = clazz.getMethod("get"+field);
                        Method set = clazz.getMethod("set"+field,String.class);
                        String g = (String) get.invoke(param);
                        //私钥解密
                        String decrypt = RSAEncryptionUtil.decryptByPrivateKey(g, getPrivateKey(), true);
                        set.invoke(param, decrypt);
                    }

                    //存入参数组
                    objects[i] = param;
                }
            }
        }

        //用处理后的参数组执行方法
        Object proceed = joinPoint.proceed(objects);
        //如果是后置置或者环绕那么要进行加密
        if (rsa.isAround() || rsa.isAfter()) {
            if (proceed instanceof JsonResult) {
                if (((JsonResult) proceed).getData()==null) {
                    return proceed;
                }
                String data = JSONObject.toJSONString(((JsonResult) proceed).getData());

                try {
                    //私钥加密
                    data = RSAEncryptionUtil.encryptByPrivateKey(data, getPrivateKey(), true);
                } catch (Exception e) {
                    throw new MyException(Code.ENCRYPT_ERROR);
                }
                ((JsonResult) proceed).setData(data);
                return proceed;
            } else {
                try {
                    //私钥加密
                    proceed = RSAEncryptionUtil.encryptByPrivateKey((String) proceed, getPrivateKey(), true);
                } catch (Exception e) {
                    throw new MyException(Code.ENCRYPT_ERROR);
                }
                return proceed;
            }
        }

        return proceed;
    }
}
