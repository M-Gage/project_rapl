package com.qingting.middleware.service.impl;

import com.qingting.middleware.service.ModelBuilder;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author Gage
 * @describe
 * @date 2019-09-17 16:17
 */
@Service
public class JMJModelBuilder extends ModelBuilder {

    /**
     * 不做任何处理直接返回
     *
     * @param reportListMap    报文列表
     * @param filMap           因子报文对应的字段
     * @param factorFormulaMap 因子字段和对应的因子值
     * @param facFiledValue    收集处理结果
     * @return facFiledValue
     */
    @Override
    public Map<String, Object> reportProcessing(Map<String, Object> reportListMap, Map<String, Object> filMap, Map<String, Object> factorFormulaMap, Map<String, Object> facFiledValue) {
        return facFiledValue;
    }

}
