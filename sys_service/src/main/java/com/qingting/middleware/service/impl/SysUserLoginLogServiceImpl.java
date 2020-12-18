package com.qingting.middleware.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.qingting.middleware.dao.SysUserLoginLogMapper;
import com.qingting.middleware.entity.SysUserLoginLog;
import com.qingting.middleware.service.SysUserLoginLogService;
/**
*@author Gage
*@describe ${Description}
*@date 2019-09-10 15:20 
*/
@Service
public class SysUserLoginLogServiceImpl implements SysUserLoginLogService{

    @Resource
    private SysUserLoginLogMapper sysUserLoginLogMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return sysUserLoginLogMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(SysUserLoginLog record) {
        return sysUserLoginLogMapper.insert(record);
    }

    @Override
    public int insertSelective(SysUserLoginLog record) {
        return sysUserLoginLogMapper.insertSelective(record);
    }

    @Override
    public SysUserLoginLog selectByPrimaryKey(Integer id) {
        return sysUserLoginLogMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(SysUserLoginLog record) {
        return sysUserLoginLogMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(SysUserLoginLog record) {
        return sysUserLoginLogMapper.updateByPrimaryKey(record);
    }

}
