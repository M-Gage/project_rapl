package com.qingting.middleware.service;

import com.qingting.middleware.bean.ReportBaseBean;

/**
 * @author Gage
 * @describe 获取报文接口
 * 严格按照数据库app_datasource_unitprice表里所命名的字段命名方法，在配置因子的时候也要遵守这一名称
 * @date 2019-12-10 11:23
 */
public interface ReportCompleteService {
    /**
     * 孚临报文（黑名单4）
     *
     * @param rb 报文请求实体
     * @return 报文的json字符串
     */
    String getReport4Fulin(ReportBaseBean rb);

    /**
     * 致诚阿福_黑名单报文（黑名单）
     *
     * @param rb 报文请求实体
     * @return 报文的json字符串
     */
    String getReport4ZhiChengAFu(ReportBaseBean rb);

    /**
     * 白骑士报文
     *
     * @param rb 报文请求实体
     * @return 报文的json字符串
     */
    String getReport4BaiQiShi(ReportBaseBean rb);

    /**
     * 浅橙报文
     *
     * @param rb 报文请求实体
     * @return 报文的json字符串
     */
    String getReport4QianCheng(ReportBaseBean rb);

    /**
     * 信用探针报文
     *
     * @param rb 报文请求实体
     * @return 报文的json字符串
     */
    String getReport4CreditTanzhen(ReportBaseBean rb);

    /**
     * 新源多头报文
     *
     * @param rb 报文请求实体
     * @return 报文的json字符串
     */
    String getReport4XinYuan(ReportBaseBean rb);

    /**
     * 百融_借贷意向报文
     *
     * @param rb 报文请求实体
     * @return 报文的json字符串
     */
    String getReport4BaiRong(ReportBaseBean rb);

    /**
     * 致诚阿福_风险评估报文
     *
     * @param rb 报文请求实体
     * @return 报文的json字符串
     */
    String getReport4AFufxpg(ReportBaseBean rb);

    /**
     * 添加报文请求记录
     * @param json 报文json
     * @param taskId 任务id
     * @param channelId 渠道id
     * @param blacklistName 报名称
     * @param appId appid
     */
    void insertBlacklistRecord(Object json, String taskId, String channelId, String blacklistName, String appId);
}
