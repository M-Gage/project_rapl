package com.qingting.middleware.service;

import com.qingting.middleware.entity.SysUserLoginLog;
    /**
*@author Gage
*@describe ${Description}
*@date 2019-09-10 15:20 
*/
public interface SysUserLoginLogService{


    int deleteByPrimaryKey(Integer id);

    int insert(SysUserLoginLog record);

    int insertSelective(SysUserLoginLog record);

    SysUserLoginLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUserLoginLog record);

    int updateByPrimaryKey(SysUserLoginLog record);

}
