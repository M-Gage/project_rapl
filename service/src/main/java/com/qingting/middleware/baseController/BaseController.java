package com.qingting.middleware.baseController;

import com.qingting.middleware.bean.LoginInfo;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

public class BaseController {

    @Resource
    protected HttpServletRequest request;

    // 获取appId
    public String getAppId() {
        Object appId = request.getAttribute("appId");
        if (appId == null) {
            return null;
        }
        return (String) appId;
    }

    // 获取app的私钥
    public String getPrivateKey() {
        Object priKey = request.getAttribute("privateKey");
        if (priKey == null) {
            return null;
        }
        return (String) priKey;
    }

    // 获取信息
    public LoginInfo getLoginInfo() {

        Object mer = request.getAttribute("app");
        if (mer == null) {
            return null;
        }
        return (LoginInfo) mer;
    }
}
