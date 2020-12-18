package com.qingting.middleware.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.qingting.middleware.bean.ReportBaseBean;
import com.qingting.middleware.common.bean.ReportProperties;
import com.qingting.middleware.dao.AppRequestBacklistRecordMapper;
import com.qingting.middleware.entity.AppRequestBacklistRecord;
import com.qingting.middleware.service.ReportCompleteService;
import com.qingting.middleware.util.DESCrypto;
import com.qingting.middleware.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author Gage
 * @describe 报文补全
 * @date 2019-12-10 11:14
 */
@Slf4j
@Service("reportCompleteService")
public class ReportCompleteServiceImpl implements ReportCompleteService {

    @Resource
    private AppRequestBacklistRecordMapper appRequestBacklistRecordMapper;
    @Resource
    private ReportProperties reportProperties;


    @Override
    public String getReport4Fulin(ReportBaseBean rb) {
        String url = reportProperties.getProductA() + reportProperties.getFuLin();
        log.debug("孚临url地址为：" + url);
        JSONObject param = new JSONObject();
        param.put("name", rb.getName());
        param.put("idCard", rb.getIdCard());
        param.put("mobile", rb.getMobile());
        param.put("appkey", reportProperties.getAppkey());
        param.put("rtype", reportProperties.getRtype());
        String result= HttpUtil.doHttpRequest(url, param.toJSONString(), HttpUtil.TYPE_JSON);

        if (StringUtils.isEmpty(result)) {
            insertBlacklistRecord(result,rb.getTaskId(),rb.getChannelId(),rb.getBlacklistName(),rb.getAppId());
            return "";
        }
        JSONObject fulin = (JSONObject) JSONObject.parse(result);
        if ("200".equals(fulin.getString("task_code"))) {
            String encryptkey = reportProperties.getFuLinKey();
            String data = (String) fulin.get("data");
            if (data != null && !"".equals(data)) {
                String flReport = DESCrypto.decrypt(data, encryptkey);
                JSONObject dataMap = JSONObject.parseObject(flReport.replace("score", "fl_score"));
                fulin.put("data", dataMap);
                insertBlacklistRecord(fulin.toJSONString(),rb.getTaskId(),rb.getChannelId(),rb.getBlacklistName(),rb.getAppId());
                return fulin.toJSONString();
            }
        }
        insertBlacklistRecord(result,rb.getTaskId(),rb.getChannelId(),rb.getBlacklistName(),rb.getAppId());
        return result;
    }

    @Override
    public String getReport4ZhiChengAFu(ReportBaseBean rb) {
        String url = reportProperties.getProductA() + reportProperties.getZhiChengAFu();
        log.debug("致诚阿福url地址为：" + url);
        JSONObject param = new JSONObject();
        param.put("name", rb.getName());
        param.put("idCard", rb.getIdCard());
        param.put("mobile", rb.getMobile());
        return getResultAndSaveRecord(rb, url, param);
    }


    @Override
    public String getReport4CreditTanzhen(ReportBaseBean rb) {
        String url = reportProperties.getProductA() + reportProperties.getCreditTanzhen();
        log.debug("信用探针url地址为：" + url);

        JSONObject param = new JSONObject();
        param.put("name", rb.getName());
        param.put("idcard", rb.getIdCard());
        param.put("mobile", rb.getMobile());
        return getResultAndSaveRecord(rb, url, param);
    }



    @Override
    public String getReport4BaiRong(ReportBaseBean rb) {
        String url = reportProperties.getProductA() + reportProperties.getBaiRong();
        log.debug("百融-借贷意向url地址为：" + url);
        JSONObject param = new JSONObject();
        param.put("name", rb.getName());
        param.put("idCard", rb.getIdCard());
        param.put("mobile", rb.getMobile());

        return getResultAndSaveRecord(rb, url, param);
    }

    @Override
    public String getReport4AFufxpg(ReportBaseBean rb) {
        String url = reportProperties.getProductA() + reportProperties.getAFufxpg();
        log.debug("致诚阿福_风险评估url地址为：" + url);
        JSONObject param = new JSONObject();
        param.put("name", rb.getName());
        param.put("id_no", rb.getIdCard());

        return getResultAndSaveRecord(rb, url, param);
    }

    private String getResultAndSaveRecord(ReportBaseBean rb, String url, JSONObject param) {
        param.put("appkey", reportProperties.getAppkey());
        param.put("rtype", reportProperties.getRtype());
        String res = HttpUtil.doHttpRequest(url, param.toJSONString(), HttpUtil.TYPE_JSON);
        insertBlacklistRecord(res,rb.getTaskId(),rb.getChannelId(),rb.getBlacklistName(),rb.getAppId());
        return res;
    }

    public void insertBlacklistRecord(Object json, String taskId, String channelId, String blacklistName, String appId) {
        AppRequestBacklistRecord arbr = new AppRequestBacklistRecord();
        arbr.setAppId(appId);
        arbr.setBlackListName(blacklistName);
        arbr.setRequestResult(json.toString());
        arbr.setTaskId(taskId);
        arbr.setChannelId(channelId);
        arbr.setCreateTime(new Date());
        appRequestBacklistRecordMapper.insertSelective(arbr);
    }

    @Override
    public String getReport4BaiQiShi(ReportBaseBean rb) {
        String url = reportProperties.getProductA() + reportProperties.getBaiQiShi();
        log.debug("白骑士url地址为：" + url);

        JSONObject param = new JSONObject();
        param.put("name", rb.getName());
        param.put("certNo", rb.getIdCard());
        param.put("mobile", rb.getMobile());
        param.put("partnerId", "hdwl");
        param.put("verifyKey", "c30f9f64251d4e2abcc637b50d963106");
        param.put("appId", "hdwl");
        param.put("eventType", "blacklis");
        return getResultAndSaveRecord(rb, url, param);
    }

    @Override
    public String getReport4XinYuan(ReportBaseBean rb) {
        String url = reportProperties.getProductA() + reportProperties.getXinYuan();
        log.debug("新源多头url地址为：" + url);
        JSONObject param = new JSONObject();
        param.put("name", rb.getName());
        param.put("idCard", rb.getIdCard());
        param.put("mobile", rb.getMobile());
        return getResultAndSaveRecord(rb, url, param);
    }

    @Override
    public String getReport4QianCheng(ReportBaseBean rb) {
        String url = reportProperties.getProductA() + reportProperties.getQianCheng();
        log.debug("浅橙url地址为：" + url);
        JSONObject param = new JSONObject();
        param.put("name", rb.getName());
        param.put("idCard", rb.getIdCard());
        param.put("mobile", rb.getMobile());
        return getResultAndSaveRecord(rb, url, param);
    }
}
