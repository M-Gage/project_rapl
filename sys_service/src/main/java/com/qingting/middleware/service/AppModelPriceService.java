package com.qingting.middleware.service;

import com.qingting.middleware.bean.sys.AppModelCreateBean;
import com.qingting.middleware.bean.sys.AppModelUpdateBean;

/**
 * @author Gage
 * @describe app模型价格业务接口
 * @date 2019-10-09 11:32
 */
public interface AppModelPriceService {

    boolean addAppModelPrice(AppModelCreateBean param);

    boolean updateAppModelPrice(AppModelUpdateBean param);
}
