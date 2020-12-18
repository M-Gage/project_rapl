package com.qingting.middleware.service;

import com.qingting.middleware.entity.Factor;

import java.util.List;

/**
 * @author Gage
 * @describe ${Description}
 * @date 2019-10-31 16:01
 */
public interface FactorService {


    int deleteByPrimaryKey(Integer id);

    int insert(Factor record);

    int insertSelective(Factor record);

    Factor selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Factor record);

    int updateByPrimaryKey(Factor record);

    /**
     * 获取单个因子对象
     *
     * @param factor 因子id
     * @return 因子对象
     */
    Factor getFactor(String factor);

    /**
     * 获取因子集合
     *
     * @param factor 多个因子id（用逗号分隔）
     * @return 因子集合
     */
    List<Factor> getFactorList(String factor);
}
