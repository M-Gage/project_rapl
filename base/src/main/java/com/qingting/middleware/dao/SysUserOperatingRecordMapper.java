package com.qingting.middleware.dao;

import com.qingting.middleware.entity.SysUserOperatingRecord;
import org.apache.ibatis.annotations.Mapper;

/**
*@author Gage
*@describe ${Description}
*@date 2019-09-10 15:20 
*/
@Mapper
public interface SysUserOperatingRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysUserOperatingRecord record);

    int insertSelective(SysUserOperatingRecord record);

    SysUserOperatingRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUserOperatingRecord record);

    int updateByPrimaryKey(SysUserOperatingRecord record);
}