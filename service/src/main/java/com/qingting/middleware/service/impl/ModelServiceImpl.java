package com.qingting.middleware.service.impl;

import com.qingting.middleware.enums.Code;
import com.qingting.middleware.exception.MyException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.qingting.middleware.dao.ModelMapper;
import com.qingting.middleware.entity.Model;
import com.qingting.middleware.service.ModelService;
@Service
public class ModelServiceImpl implements ModelService{

    @Resource
    private ModelMapper modelMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return modelMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Model record) {
        return modelMapper.insert(record);
    }

    @Override
    public int insertSelective(Model record) {
        return modelMapper.insertSelective(record);
    }

    @Override
    public Model selectByPrimaryKey(Integer id) {
        return modelMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Model record) {
        return modelMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Model record) {
        return modelMapper.updateByPrimaryKey(record);
    }

    @Override
    public Model getModel(String modelNo, String modelName) {
        Model model = modelMapper.selectByModelNo(modelNo, modelName);
        if (model == null) {
            throw new MyException(Code.MODEL_NULL_ERROR);
        }
        return model;
    }

}
