package com.qingting.middleware.service;

import com.qingting.middleware.entity.App;

/**
 * @author Gage
 * @describe 产品接口
 * @date 2019-12-10 14:22
 */
public interface AppService {
    App getAppInfo(String appId);
}
