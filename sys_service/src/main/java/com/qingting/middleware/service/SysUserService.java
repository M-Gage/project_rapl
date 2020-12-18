package com.qingting.middleware.service;

import com.qingting.middleware.entity.SysUser;

/**
 * @author Gage
 * @describe ${Description}
 * @date 2019-09-10 15:20
 */
public interface SysUserService {


    int deleteByPrimaryKey(String id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    String userLogin(String username, String password);
}
