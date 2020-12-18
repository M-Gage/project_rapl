package com.qingting.middleware.service;

import com.qingting.middleware.entity.Model;
public interface ModelService{


    int deleteByPrimaryKey(Integer id);

    int insert(Model record);

    int insertSelective(Model record);

    Model selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Model record);

    int updateByPrimaryKey(Model record);

    Model getModel(String modelNo, String modelName);
}
