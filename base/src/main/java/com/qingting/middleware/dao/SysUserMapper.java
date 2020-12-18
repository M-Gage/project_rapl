package com.qingting.middleware.dao;

import com.qingting.middleware.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author Gage
 * @describe ${Description}
 * @date 2019-09-10 15:20
 */
@Mapper
public interface SysUserMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    SysUser userLogin(@Param("username") String username, @Param("password") String password);
}