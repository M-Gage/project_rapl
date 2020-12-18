package com.qingting.middleware.service.impl;

import com.qingting.middleware.dao.FactorMapper;
import com.qingting.middleware.entity.Factor;
import com.qingting.middleware.entity.Model;
import com.qingting.middleware.enums.Code;
import com.qingting.middleware.exception.MyException;
import com.qingting.middleware.service.FactorService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Gage
 * @describe ${Description}
 * @date 2019-10-31 16:01
 */
@Service("factorService")
public class FactorServiceImpl implements FactorService {

    @Resource
    private FactorMapper factorMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return factorMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Factor record) {
        return factorMapper.insert(record);
    }

    @Override
    public int insertSelective(Factor record) {
        return factorMapper.insertSelective(record);
    }

    @Override
    public Factor selectByPrimaryKey(Integer id) {
        return factorMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Factor record) {
        return factorMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Factor record) {
        return factorMapper.updateByPrimaryKey(record);
    }

    @Override
    @Cacheable(value = "factor", key = "#factor")
    public Factor getFactor(String factor) {
        return factorMapper.selectByPrimaryKey(Integer.parseInt(factor));
    }

    @Override
    @Cacheable(value = "factorList", key = "#factor")
    public List<Factor> getFactorList(String factor) {
        return factorMapper.selectListByFactorStr(factor);
    }
}
