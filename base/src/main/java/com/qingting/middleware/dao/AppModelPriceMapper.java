package com.qingting.middleware.dao;

import com.qingting.middleware.entity.AppModelPrice;
import org.apache.ibatis.annotations.Mapper;import org.apache.ibatis.annotations.Param;

/**
 * @author Gage
 * @describe ${Description}
 * @date 2019-10-09 12:00
 */
@Mapper
public interface AppModelPriceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AppModelPrice record);

    int insertSelective(AppModelPrice record);

    AppModelPrice selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AppModelPrice record);

    int updateByPrimaryKey(AppModelPrice record);

    AppModelPrice selectByAppIdAndModelId(@Param("appId") Integer appId, @Param("modelId") Integer modelId);

    int updateUsedTimes(@Param("appId") String appId, @Param("modelId") Integer modelId);

    int updateAppModelPrice(AppModelPrice amp);
}