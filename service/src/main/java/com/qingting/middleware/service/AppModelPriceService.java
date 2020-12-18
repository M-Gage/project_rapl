package com.qingting.middleware.service;

import com.qingting.middleware.entity.AppModelPrice;

/**
 * @author Gage
 * @describe app模型价格业务接口
 * @date 2019-10-08 18:15
 */
public interface AppModelPriceService {
    AppModelPrice getAppModelPrice(String appId, Integer modelId);
}
