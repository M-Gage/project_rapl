package com.qingting.middleware.service.impl;

import com.qingting.middleware.bean.LoginInfo;
import com.qingting.middleware.dao.AppMapper;
import com.qingting.middleware.entity.App;
import com.qingting.middleware.entity.AppModelPrice;
import com.qingting.middleware.enums.Code;
import com.qingting.middleware.exception.MyException;
import com.qingting.middleware.service.LoginService;
import com.qingting.middleware.util.RSAEncryptionUtil;
import com.qingting.middleware.util.RedisUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private AppMapper appMapper;
    @Resource
    private RedisUtil redisUtil;

    @Override
    public String appLogin(String appKey, String key) {
        //根据app唯一标识获取信息
        App app = appMapper.selectByAppKey(appKey);
        if (app == null) {
            return null;
        }
        //获得私钥
        String privateKey = app.getPrivateKey();
        String password;
        try {
            //解密密码
            password = RSAEncryptionUtil.decryptByPrivateKey(key, privateKey, false);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(Code.DECRYPT_ERROR);
        }
        //判断密码
        if (password.equals(app.getPassword())) {
            LoginInfo loginInfo = new LoginInfo();
            loginInfo.setAppId(app.getAppId());
            loginInfo.setAppKey(appKey);
            loginInfo.setAppName(app.getAppName());
            loginInfo.setPrivateKey(app.getPrivateKey());
            loginInfo.setPublicKey(app.getPublicKey());
            loginInfo.setReportSwitch(app.getReportSwitch());
            loginInfo.setStatus(app.getStatus());
            //将APP信息保存到redis
            if (redisUtil.set(app.getAppId(), loginInfo)) {
                //返回APPid用于token保存
                return app.getAppId();
            }
        }
        return null;
    }
}
