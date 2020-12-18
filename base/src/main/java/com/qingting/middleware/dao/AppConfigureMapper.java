package com.qingting.middleware.dao;

import com.qingting.middleware.entity.AppConfigure;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Gage
 * @describe ${Description}
 * @date 2019-09-11 17:08
 */
@Mapper
public interface AppConfigureMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AppConfigure record);

    int insertSelective(AppConfigure record);

    AppConfigure selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AppConfigure record);

    int updateByPrimaryKey(AppConfigure record);

    AppConfigure selectByProductId(String prdId);
}