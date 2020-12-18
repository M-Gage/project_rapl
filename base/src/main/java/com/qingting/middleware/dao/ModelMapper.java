package com.qingting.middleware.dao;

import com.qingting.middleware.entity.Model;
import org.apache.ibatis.annotations.Mapper;import org.apache.ibatis.annotations.Param;

/**
 * @author Gage
 * @describe ${Description}
 * @date 2019-12-09 16:56
 */
@Mapper
public interface ModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Model record);

    int insertSelective(Model record);

    Model selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Model record);

    int updateByPrimaryKey(Model record);

    Model selectByModelNo(@Param("modelNo") String modelNo, @Param("modelName") String modelName);
}