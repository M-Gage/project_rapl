package com.qingting.middleware.service;

import com.alibaba.fastjson.JSONObject;
import com.qingting.middleware.bean.ModelResultBean;
import com.qingting.middleware.entity.App;
import com.qingting.middleware.entity.Model;

/**
 * @author Gage
 * @describe 风控流程
 * @date 2019-12-09 14:23
 */
public interface IRiskControl {

    /**
     * 获取因子
     * @param factor 因子id
     */
    void getFactor(String factor);

    /**
     * 获取报文
     */
    void getReport();

    /**
     * 报文预处理（这一步仅做字段名的处理）
     */
    void preProcessing();

    /**
     * 解析报文
     */
    void parseReport();

    /**
     * 报文处理(特定处理，保证决策的因子值可用)
     */
    void reportProcessing();

    /**
     * 报文决策
     */
    void policyDecision();

    /**
     * 获取分数
     */
    void getScore();

    /**
     * 保存流水
     * @return 模型返回对象
     */
    ModelResultBean saveRecord();
}
