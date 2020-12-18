package com.qingting.middleware.service.impl;

import com.qingting.middleware.dao.AppModelPriceMapper;
import com.qingting.middleware.entity.AppModelPrice;
import com.qingting.middleware.service.AppModelPriceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Gage
 * @describe
 * @date 2019-10-08 18:17
 */
@Service
public class AppModelPriceServiceImpl implements AppModelPriceService {

    @Resource
    private AppModelPriceMapper appModelPriceMapper;


    @Override
    public AppModelPrice getAppModelPrice(String appId, Integer modelId) {

        return appModelPriceMapper.selectByAppIdAndModelId(Integer.parseInt(appId),modelId);
    }
}
