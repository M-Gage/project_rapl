package com.qingting.middleware.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qingting.middleware.bean.ModelResultBean;
import com.qingting.middleware.entity.Factor;
import com.qingting.middleware.entity.Model;
import com.qingting.middleware.util.CommonUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @author Gage
 * @describe 模型建造类
 * @date 2019-09-17 15:28
 */
public abstract class ModelBuilder {

    public void idCardHandle(Map<String, Object> factorFormulaByFiled, JSONObject user,Map<String, Object> facFiledValue, String filedStr) {
        String province = (String) factorFormulaByFiled.get(filedStr);
        //逗号和括号中间是截取位数（LEFT(a.bus_idcard,2)）
        String subNum = province.substring(province.indexOf(',') + 1, province.indexOf(')'));
        String subProvince = user.get("idCard").toString().substring(0, Integer.parseInt(subNum));
        facFiledValue.put(filedStr, subProvince);
    }


    public abstract Map<String, Object> reportProcessing(Map<String, Object> reportListMap, Map<String, Object> filMap,  Map<String, Object> factorFormulaMap, Map<String, Object> facFiledValue);
}
