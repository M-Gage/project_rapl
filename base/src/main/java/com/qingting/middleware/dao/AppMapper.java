package com.qingting.middleware.dao;

import com.qingting.middleware.entity.App;
import org.apache.ibatis.annotations.Mapper;import org.apache.ibatis.annotations.Param;import java.math.BigDecimal;import java.util.List;

/**
 * @author Gage
 * @describe ${Description}
 * @date 2019-12-11 16:32
 */
@Mapper
public interface AppMapper {
    int deleteByPrimaryKey(Integer appId);

    int insert(App record);

    int insertSelective(App record);

    App selectByPrimaryKey(Integer appId);

    int updateByPrimaryKeySelective(App record);

    int updateByPrimaryKey(App record);

    App selectByAppKey(@Param("appKey") String appKey);

    List<App> selectAllApp(@Param("condition") String condition, @Param("status") String status, @Param("startTime") String startTime, @Param("endTime") String endTime);

    int updateAppAmount(@Param("appId") Integer appId, @Param("rechargeAmount") BigDecimal rechargeAmount);

    int updateAppAvailableAmount(@Param("appId") Integer appId, @Param("useAmount") BigDecimal useAmount);
}