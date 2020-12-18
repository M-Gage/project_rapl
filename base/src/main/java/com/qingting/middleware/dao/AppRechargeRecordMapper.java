package com.qingting.middleware.dao;

import com.qingting.middleware.entity.AppRechargeRecord;
import org.apache.ibatis.annotations.Mapper;

/**
*@author Gage
*@describe ${Description}
*@date 2019-10-08 17:31 
*/
@Mapper
public interface AppRechargeRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AppRechargeRecord record);

    int insertSelective(AppRechargeRecord record);

    AppRechargeRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AppRechargeRecord record);

    int updateByPrimaryKey(AppRechargeRecord record);
}