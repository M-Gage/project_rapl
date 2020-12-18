package com.qingting.middleware.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.qingting.middleware.service.ModelBuilder;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author Gage
 * @describe
 * @date 2019-09-23 11:33
 */
@Service
public class KJLModelBuilder extends ModelBuilder {

    /**
     * 好坏省的判断
     *
     * @param reportListMap    报文列表
     * @param filMap           因子报文对应的字段
     * @param factorFormulaMap 因子字段和对应的因子值
     * @param facFiledValue    收集处理结果
     * @return facFiledValue
     */
    @Override
    public Map<String, Object> reportProcessing(Map<String, Object> reportListMap, Map<String, Object> filMap, Map<String, Object> factorFormulaMap, Map<String, Object> facFiledValue) {
        JSONObject user = JSONObject.parseObject(reportListMap.get("user").toString());
        if (factorFormulaMap.get("good_province") != null) {
            idCardHandle(factorFormulaMap, user, facFiledValue, "good_province");
        }
        if (factorFormulaMap.get("bad_province") != null) {
            idCardHandle(factorFormulaMap, user, facFiledValue, "bad_province");
        }
        return facFiledValue;
    }

}
