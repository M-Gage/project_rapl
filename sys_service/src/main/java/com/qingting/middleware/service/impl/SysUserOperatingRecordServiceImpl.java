package com.qingting.middleware.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.qingting.middleware.entity.SysUserOperatingRecord;
import com.qingting.middleware.dao.SysUserOperatingRecordMapper;
import com.qingting.middleware.service.SysUserOperatingRecordService;
/**
*@author Gage
*@describe ${Description}
*@date 2019-09-10 15:20 
*/
@Service
public class SysUserOperatingRecordServiceImpl implements SysUserOperatingRecordService{

    @Resource
    private SysUserOperatingRecordMapper sysUserOperatingRecordMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return sysUserOperatingRecordMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(SysUserOperatingRecord record) {
        return sysUserOperatingRecordMapper.insert(record);
    }

    @Override
    public int insertSelective(SysUserOperatingRecord record) {
        return sysUserOperatingRecordMapper.insertSelective(record);
    }

    @Override
    public SysUserOperatingRecord selectByPrimaryKey(Integer id) {
        return sysUserOperatingRecordMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(SysUserOperatingRecord record) {
        return sysUserOperatingRecordMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(SysUserOperatingRecord record) {
        return sysUserOperatingRecordMapper.updateByPrimaryKey(record);
    }

}
