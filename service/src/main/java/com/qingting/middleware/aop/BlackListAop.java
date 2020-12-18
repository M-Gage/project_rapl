package com.qingting.middleware.aop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qingting.middleware.annotation.BlackList;
import com.qingting.middleware.baseController.BaseController;
import com.qingting.middleware.bean.ModelResultBean;
import com.qingting.middleware.bean.ModelUserBean;
import com.qingting.middleware.bean.request.ModelParamBean;
import com.qingting.middleware.common.bean.ReportProperties;
import com.qingting.middleware.entity.AppModelPrice;
import com.qingting.middleware.entity.Model;
import com.qingting.middleware.entity.UserApplyRecord;
import com.qingting.middleware.entity.UserRequestModelRecord;
import com.qingting.middleware.enums.Code;
import com.qingting.middleware.enums.Constant;
import com.qingting.middleware.exception.MyException;
import com.qingting.middleware.service.AppModelPriceService;
import com.qingting.middleware.service.ModelService;
import com.qingting.middleware.service.UserRequestModelRecordService;
import com.qingting.middleware.util.CommonUtil;
import com.qingting.middleware.util.DateUtil;
import com.qingting.middleware.util.HttpUtil;
import com.qingting.middleware.util.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

@Slf4j
@Aspect
@Component
@Order(2)
public class BlackListAop extends BaseController {

    @Resource
    private UserRequestModelRecordService userRequestModelRecordService;
    @Resource
    private ReportProperties reportProperties;
    @Resource
    private ModelService modelService;
    @Resource
    private AppModelPriceService appModelPriceService;


    /**
     * 黑名单切面（环绕）
     *
     * @param joinPoint 环绕切入点对象
     * @param blackList 方法上的注解对象
     * @return 方法抛出的结果
     * @throws Throwable
     */
    @Around(value = "@annotation(blackList)", argNames = "joinPoint,blackList")
    public JsonResult before(ProceedingJoinPoint joinPoint, BlackList blackList) throws Throwable {

        Signature signature = joinPoint.getSignature();
        log.info("黑名单切面method:[{}]", signature.getName());

        //获取目标方法的参数信息
        Object[] obj = joinPoint.getArgs();
        //是模型参数
        if (obj[0] instanceof ModelParamBean) {
            String appId = getAppId();
            ModelParamBean body = (ModelParamBean) obj[0];
            body.setTaskId(appId + DateUtil.getNowTimestamp() + CommonUtil.getFixLengthString(6));
            Model model = modelService.getModel(body.getModelNo(), body.getModelName());
            if (model == null) {
                throw new MyException(Code.MODEL_NULL_ERROR);
            }
            AppModelPrice amp = appModelPriceService.getAppModelPrice(appId, model.getId());
            if (amp == null) {
                throw new MyException(Code.SQL_RESULT_NULL, "AppModelPrice");
            }
            if (amp.getUsedTimes() >= amp.getTotalTimes()) {
                throw new MyException(Code.NO_AVAILABLE_TIMES);
            }

            ModelUserBean user;
            try {
                user = JSON.parseObject(body.getUser(), ModelUserBean.class);
            } catch (Exception e) {
                e.printStackTrace();
                throw new MyException(Code.JSON_PARSE_ERROR, "用户信息不正确");
            }

            String mobile = user.getMobile();
            String idCard = user.getIdCard();
            String name = user.getName();
            JSONObject json = new JSONObject();
            //从A黑名单库中查询数据
            String url = reportProperties.getProductA() + reportProperties.getBlackListQuery();
            json.put("mobile", mobile);
            json.put("idcard", idCard);
            json.put("rtype", reportProperties.getRtype());
            json.put("appkey", reportProperties.getAppkey());
            json.put("platform_code", reportProperties.getPlatformCode());
            log.debug("==>A黑名单查询请求数据:" + json);
            String responseStr = HttpUtil.doHttpRequest(url, json.toJSONString(), Constant.FILED_UTF_8.getConstant());
            log.debug("<==A黑名单查询响应数据：" + responseStr);
            //用户申请记录
            UserApplyRecord uar = new UserApplyRecord();
            uar.setAppId(appId);
            uar.setName(name);
            uar.setIdCard(idCard);
            uar.setMobile(mobile);
            uar.setApplyTime(new Date());
            uar.setCreateTime(new Date());
            uar.setTaskId(body.getTaskId());

            boolean flag = false;
            ModelResultBean mr = null;
            //没有查询到数据则通过
            if (!StringUtils.isBlank(responseStr)) {
                JSONObject result = CommonUtil.jsonToMap(responseStr);
                //在黑名单中
                if (!StringUtils.isBlank(responseStr) && result.getString("task_code").equals("200") && result.getString("is_in").equalsIgnoreCase("true")) {
                    //记录流水
                    log.debug("=====> 命中黑名单！！！");

                    // 插入流水
                    UserRequestModelRecord modelRecord = new UserRequestModelRecord();
                    modelRecord.setAppId(appId);
                    modelRecord.setUsername(user.getName());
                    modelRecord.setIdCard(user.getIdCard());
                    modelRecord.setMobile(user.getMobile());
                    modelRecord.setScore("0");
                    modelRecord.setModelId(model.getId());
                    modelRecord.setTaskId( body.getTaskId());
                    modelRecord.setChannelId(body.getChannelId());
                    modelRecord.setCreateTime(new Date());
                    modelRecord = userRequestModelRecordService.saveRequestRecordInfo(modelRecord);

                    mr = userRequestModelRecordService.modelResultHandle(BigDecimal.ZERO, null,modelRecord);
                    uar.setStatus("2");
                    flag = true;
                }
            }
            if (userRequestModelRecordService.saveRequestUserApply(uar) > 0) {
                log.debug("=====> 用户申请记录录入成功！！！");
            } else {
                log.debug("=====> 用户申请记录录入失败！！！");
            }
            if (flag) return JsonResult.success(mr);
        }
        //执行方法
        return (JsonResult) joinPoint.proceed(obj);
    }
}
