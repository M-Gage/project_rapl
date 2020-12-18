package com.qingting.middleware.service.impl;

import com.qingting.middleware.bean.sys.AppModelCreateBean;
import com.qingting.middleware.bean.sys.AppModelUpdateBean;
import com.qingting.middleware.dao.AppMapper;
import com.qingting.middleware.dao.AppModelPriceMapper;
import com.qingting.middleware.entity.App;
import com.qingting.middleware.entity.AppModelPrice;
import com.qingting.middleware.enums.Code;
import com.qingting.middleware.exception.MyException;
import com.qingting.middleware.service.AppModelPriceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Gage
 * @describe
 * @date 2019-10-09 13:51
 */
@Service
public class AppModelPriceServiceImpl implements AppModelPriceService {

    @Resource
    private AppModelPriceMapper appModelPriceMapper;

    @Resource
    private AppMapper appMapper;

    @Override
    @Transactional
    public boolean addAppModelPrice(AppModelCreateBean param) {
        AppModelPrice appModelPrice = appModelPriceMapper.selectByAppIdAndModelId(Integer.parseInt(param.getAppId()), param.getModelId());
        if (appModelPrice!=null) {
            throw new MyException(Code.DATA_EXISTING,"appModelPrice");
        }
        String appId = param.getAppId();
        AppModelPrice amp = new AppModelPrice();
        amp.setAppId(appId);
        amp.setModelId(param.getModelId());
        amp.setMarketPrice(param.getMarketPrice());
        amp.setCostPrice(param.getCostPrice());
        amp.setTotalTimes(param.getAvailableTimes());
        amp.setCreateTime(new Date());
        if (param.getAvailableTimes() != null) {
            deduction(param.getMarketPrice(),param.getAvailableTimes(), appId);
        }
        return appModelPriceMapper.insertSelective(amp) > 0;
    }



    @Override
    @Transactional
    public boolean updateAppModelPrice(AppModelUpdateBean param) {
        AppModelPrice amp = new AppModelPrice();
        String appId = param.getAppId();
        amp.setAppId(appId);
        amp.setModelId(param.getModelId());
        amp.setTotalTimes(param.getAvailableTimes());
        if (param.getAvailableTimes() != null) {
            AppModelPrice appModelPrice = appModelPriceMapper.selectByAppIdAndModelId(Integer.parseInt(appId), param.getModelId());
            if (appModelPrice==null) throw new MyException(Code.RETURN_NULL,"appModelPrice");
            deduction(appModelPrice.getMarketPrice(),param.getAvailableTimes(), appId);
        }
        amp.setStatus(param.getStatus());
        return appModelPriceMapper.updateAppModelPrice(amp) > 0;
    }


    private void deduction(BigDecimal marketPrice,Integer availableTimes, String appId) {
        App app = appMapper.selectByPrimaryKey(Integer.parseInt(appId));
        if (app == null){
            throw new MyException(Code.RETURN_NULL,"app");
        }
        BigDecimal useAmount = marketPrice.multiply(new BigDecimal(availableTimes));
        if (app.getAvailableAmount().compareTo(useAmount) <0) {
            throw new MyException(Code.TOO_MANY_TIMES);
        }
        appMapper.updateAppAvailableAmount(Integer.parseInt(appId),useAmount.negate());
    }
}
