package com.qingting.middleware.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qingting.middleware.bean.ModelResultBean;
import com.qingting.middleware.bean.ModelUserBean;
import com.qingting.middleware.entity.App;
import com.qingting.middleware.entity.Factor;
import com.qingting.middleware.entity.Model;
import com.qingting.middleware.entity.UserRequestModelRecord;
import com.qingting.middleware.enums.Code;
import com.qingting.middleware.enums.Constant;
import com.qingting.middleware.exception.MyException;
import com.qingting.middleware.service.*;
import com.qingting.middleware.util.ModelHandlerUtil;
import com.qingting.middleware.util.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author Gage
 * @describe 单一模式风控方案
 * @date 2019-12-09 14:35
 */
@Slf4j
public class SingleModelRisk implements IRiskControl {

    private JSONObject reports;
    private Model model;
    private App app;
    private String taskId;
    private String channelId;
    private String appId;
    private ModelUserBean user;

    //报文名集合
    private List<String> reportList = new ArrayList<>();
    //字段名集合
    private List<String> filedList = new ArrayList<>();
    //报文解析结果
    private Map<String, Object> parseResult = new HashMap<>();
    //原报文json集合
    private Map<String, Object> reportListMap;
    //报文字段集合
    private Map<String, Object> filMap = new HashMap<>();

    //原因子
    private Factor factor;
    //原因子的因子公式集合
    private Map<String, Object> factorFormulaMap = new HashMap<>();

    //决策前的因子值集合
    private Map<String, Object> factor4Decision;
    private BigDecimal score;
    //因子系数集合
    private List<Map<String, BigDecimal>> factorCoefficientMap;

    public SingleModelRisk(JSONObject reports, Model model, App app) {
        this.reports = reports;
        this.model = model;
        this.app = app;
        this.taskId = reports.getString("taskId");
        this.channelId = reports.getString("channelId");
        this.appId = app.getAppId();
        this.user = JSON.parseObject(reports.get("user").toString(), ModelUserBean.class);
    }

    @Override
    public void getFactor(String factorStr) {
        FactorService factorService = (FactorService) SpringUtil.getBean("factorService");
        factor = factorService.getFactor(factorStr);
        if (factor == null) {
            throw new MyException(Code.SQL_RESULT_NULL, "因子公式查询不到");
        }
        //因子报文名集合
        String[] rep = factor.getReport().split(Constant.FILED_FACTOR_SEPARATOR.getConstant());

        //因子字段集合
        String[] fil = factor.getFactorFiled().split(Constant.FILED_FACTOR_SEPARATOR.getConstant());
        //因子公式集合
        String[] frm = factor.getFactorFormula().split(Constant.FILED_FACTOR_SEPARATOR.getConstant());

        reportList.addAll(Arrays.asList(rep));

        //按定义的顺序匹配
        for (int i = 0; i < rep.length; i++) {
            //报文的因子字段集合
            String[] filSplit = fil[i].split(Constant.FILED_SPLIT.getConstant());
            //报文的因子公式集合
            String[] frmSplit = frm[i].split(Constant.FILED_SPLIT.getConstant());

            List<String> fac = new ArrayList<>(Arrays.asList(filSplit));
            filedList.addAll(fac);
            //录入因子字段对应的因子公式
            for (int j = 0; j < filSplit.length; j++) {
                factorFormulaMap.put(filSplit[j], frmSplit[j]);
            }
            filMap.put(rep[i], fac);
        }
    }

    @Override
    public void getReport() {
        ReportCompleteService reportCompleteService = (ReportCompleteService) SpringUtil.getBean("reportCompleteService");
        Class<ReportCompleteService> clazz = (Class<ReportCompleteService>) reportCompleteService.getClass();
        Method[] methods = clazz.getMethods();
        //获取全部报文集合
        reportListMap = ModelHandlerUtil.getReportList(reportList, reports, model, app, methods);

        String operatorReportJson = reports.getString("operatorReport");
        if (StringUtils.isNotEmpty(operatorReportJson)) {

            reportCompleteService.insertBlacklistRecord(operatorReportJson, taskId, channelId, "operatorReport", appId);

        }
    }

    @Override
    public void preProcessing() {
        if (filMap.containsKey("user")) {
            Object userFil = filMap.get("user");
            if (userFil instanceof List) {
                List<String> userFiledList = (List) userFil;
                if (userFiledList.contains("bus_006")) {
                    String user = (String) reportListMap.get("user");
                    String replace = user.replace("CHSIStatus", "bus_006");
                    reportListMap.put("user", replace);
                }
            }
        }
    }

    @Override
    public void parseReport() {
        parseResult = ModelHandlerUtil.parseReport(filMap, reportListMap, filedList);
    }

    @Override
    public void reportProcessing() {
        ModelBuilder modelBuilder = (ModelBuilder) SpringUtil.getBean(factor.getProcessingStrategy() + "ModelBuilder");
        factor4Decision = modelBuilder.reportProcessing(reportListMap, filMap, factorFormulaMap, parseResult);
    }

    @Override
    public void policyDecision() {
        factorCoefficientMap = ModelHandlerUtil.policyDecision(factor4Decision, factorFormulaMap);
    }

    @Override
    public void getScore() {
        score = ModelHandlerUtil.getSum(factor.getSum(), factorCoefficientMap.get(0));
    }

    @Override
    public ModelResultBean saveRecord() {
        UserRequestModelRecordService userRequestModelRecordService = (UserRequestModelRecordService) SpringUtil.getBean("userRequestModelRecordService");
        if (reportListMap.containsKey("aFufxpg")) {
            userRequestModelRecordService.updateAFuStatus(appId, user.getIdCard());
        }
        // 插入流水
        UserRequestModelRecord modelRecord = new UserRequestModelRecord();
        modelRecord.setAppId(appId);
        modelRecord.setUsername(user.getName());
        modelRecord.setIdCard(user.getIdCard());
        modelRecord.setMobile(user.getMobile());
        modelRecord.setScore(score.toString());
        modelRecord.setModelId(model.getId());
        modelRecord.setUsedFactor(JSONObject.toJSONString(factorCoefficientMap.get(1)));
        modelRecord.setTaskId(taskId);
        modelRecord.setChannelId(channelId);
        modelRecord.setCreateTime(new Date());
        modelRecord = userRequestModelRecordService.saveRequestRecordInfo(modelRecord);
        return userRequestModelRecordService.modelResultHandle(score, null, modelRecord);
    }
}