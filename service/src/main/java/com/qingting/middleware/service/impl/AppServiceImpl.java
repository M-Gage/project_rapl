package com.qingting.middleware.service.impl;

import com.qingting.middleware.dao.AppMapper;
import com.qingting.middleware.entity.App;
import com.qingting.middleware.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Gage
 * @describe
 * @date 2019-12-10 14:23
 */
@Service
public class AppServiceImpl implements AppService {

    @Autowired
    AppMapper appMapper;

    @Override
    public App getAppInfo(String appId) {
        return appMapper.selectByPrimaryKey(Integer.parseInt(appId));
    }
}
