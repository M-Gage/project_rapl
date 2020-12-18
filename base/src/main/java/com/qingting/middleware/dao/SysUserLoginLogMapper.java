package com.qingting.middleware.dao;

import com.qingting.middleware.entity.SysUserLoginLog;
import org.apache.ibatis.annotations.Mapper;

/**
*@author Gage
*@describe ${Description}
*@date 2019-09-10 15:20 
*/
@Mapper
public interface SysUserLoginLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysUserLoginLog record);

    int insertSelective(SysUserLoginLog record);

    SysUserLoginLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUserLoginLog record);

    int updateByPrimaryKey(SysUserLoginLog record);
}