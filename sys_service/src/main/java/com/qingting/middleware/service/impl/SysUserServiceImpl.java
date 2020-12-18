package com.qingting.middleware.service.impl;

import com.qingting.middleware.bean.sys.LoginInfo;
import com.qingting.middleware.util.RedisUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import com.qingting.middleware.dao.SysUserMapper;
import com.qingting.middleware.entity.SysUser;
import com.qingting.middleware.service.SysUserService;

/**
 * @author Gage
 * @describe ${Description}
 * @date 2019-09-10 15:20
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private RedisUtil redisUtil;

    @Override
    public int deleteByPrimaryKey(String id) {
        return sysUserMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(SysUser record) {
        return sysUserMapper.insert(record);
    }

    @Override
    public int insertSelective(SysUser record) {
        return sysUserMapper.insertSelective(record);
    }

    @Override
    public SysUser selectByPrimaryKey(String id) {
        return sysUserMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(SysUser record) {
        return sysUserMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(SysUser record) {
        return sysUserMapper.updateByPrimaryKey(record);
    }

    @Override
    public String userLogin(String username, String password) {
        //根据app唯一标识获取信息
        SysUser user = sysUserMapper.userLogin(username, password);
        if (user == null) {
            return "";
        }
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setUserId(user.getId());
        loginInfo.setPhone(user.getPhone());
        loginInfo.setUsername(user.getUsername());
        loginInfo.setName(user.getName());
        loginInfo.setLastLogin(user.getLastLogin());
        loginInfo.setStatus(user.getStatus());
        //将user信息保存到redis
        if (redisUtil.set(user.getUsername(), loginInfo)) {
            //返回userId用于token保存
            return user.getId();
        }
        return "";
    }

}
