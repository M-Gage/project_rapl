package com.qingting.middleware.service;

import com.qingting.middleware.entity.SysUserOperatingRecord;
    /**
*@author Gage
*@describe ${Description}
*@date 2019-09-10 15:20 
*/
public interface SysUserOperatingRecordService{


    int deleteByPrimaryKey(Integer id);

    int insert(SysUserOperatingRecord record);

    int insertSelective(SysUserOperatingRecord record);

    SysUserOperatingRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUserOperatingRecord record);

    int updateByPrimaryKey(SysUserOperatingRecord record);

}
