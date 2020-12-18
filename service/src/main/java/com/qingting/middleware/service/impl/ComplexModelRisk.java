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
 * @describe 复合模式风控方案
 * @date 2019-12-09 14:38
 */
@Slf4j
public class ComplexModelRisk implements IRiskControl {

    private JSONObject reports;
    private Model model;
    private App app;
    private String taskId;
    private String channelId;
    private String appId;
    private ModelUserBean user;

    //报文名集合
    private List<String> reportList = new ArrayList<>();
    private List<String> filedList = new ArrayList<>();
    //报文字段集合
    private Map<String, Object> filMap = new HashMap<>();
    //原报文json集合
    private Map<String, Object> reportListMap;
    //报文解析结果
    private Map<String, Object> parseResult = new HashMap<>();

    //原因子集合
    private List<Factor> factors;
    //原因子的因子字段集合
    private Map<String, List<String>> factorFiledMap = new HashMap<>();
    //原因子的因子公式集合
    private Map<String, Map<String, Object>> factorFormulaMap = new HashMap<>();
    //决策前的因子值集合
    private Map<String, Map<String, Object>> factor4Decision = new HashMap<>();
    //因子系数集合
    private Map<String, List<Map<String, BigDecimal>>> factorCoefficientMap = new HashMap<>();
    //分数集合
    private Map<String, BigDecimal> scoreMap = new HashMap<>();

    public ComplexModelRisk(JSONObject reports, Model model, App app) {
        this.reports = reports;
        this.model = model;
        this.app = app;
        this.taskId = reports.getString("taskId");
        this.channelId = reports.getString("channelId");
        this.appId = app.getAppId();
        this.user = JSON.parseObject(reports.get("user").toString(), ModelUserBean.class);
    }

    @Override
    public void getFactor(String factor) {
        FactorService factorService = (FactorService) SpringUtil.getBean("factorService");
        //获取因子列表
        factors = factorService.getFactorList(factor);
        if (factors == null) {
            throw new MyException(Code.SQL_RESULT_NULL, "因子公式查询不到");
        }
        //获取不重复的报文
        Set<String> repSet = new HashSet<>();
        //获取不重复的因子字段
        Set<String> filSet = new HashSet<>();

        for (Factor fac : factors) {
            //记录该报文因子字段对应的因子公式
            Map<String, Object> thisFactorFormulaMap = new HashMap<>();
            //记录该报文因子字段
            List<String> factorFiledList = new ArrayList<>();
            //因子报文名集合
            String[] rep = fac.getReport().split(Constant.FILED_FACTOR_SEPARATOR.getConstant());

            //因子字段集合
            String[] fil = fac.getFactorFiled().split(Constant.FILED_FACTOR_SEPARATOR.getConstant());
            //因子公式集合
            String[] frm = fac.getFactorFormula().split(Constant.FILED_FACTOR_SEPARATOR.getConstant());

            repSet.addAll(Arrays.asList(rep));

            //按定义的顺序匹配
            for (int i = 0; i < rep.length; i++) {
                //获取报文的因子字段
                Object o = filMap.get(rep[i]);

                //报文的因子字段集合
                String[] filSplit = fil[i].split(Constant.FILED_SPLIT.getConstant());
                //报文的因子公式集合
                String[] frmSplit = frm[i].split(Constant.FILED_SPLIT.getConstant());

                factorFiledList.addAll(Arrays.asList(filSplit));

                for (int j = 0; j < filSplit.length; j++) {
                    filSet.add(filSplit[j]);
                    thisFactorFormulaMap.put(filSplit[j], frmSplit[j]);
                }

                //收集报文的不重复字段
                Set<String> set = new HashSet<>();
                if (o == null) {
                    set.addAll(Arrays.asList(filSplit));
                } else {
                    if (o instanceof HashSet) {
                        set.addAll((HashSet) o);
                        set.addAll(Arrays.asList(filSplit));
                    }
                }
                filMap.put(rep[i], set);

            }
            factorFiledMap.put(fac.getId().toString(), factorFiledList);
            factorFormulaMap.put(fac.getId().toString(), thisFactorFormulaMap);
        }
        filedList.addAll(filSet);
        reportList.addAll(repSet);
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
            if (userFil instanceof HashSet) {
                Set<String> userFiledList = (HashSet) userFil;
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

        //使各个因子的因子字段都可以找到解析出来的值
        for (Factor factor : factors) {
            String factorId = factor.getId().toString();
            //因子字段对应的值
            Map<String, Object> facFiledValue = new HashMap<>();
            Iterator<Map.Entry<String, List<String>>> iterator = factorFiledMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, List<String>> next = iterator.next();
                //从解析的全部因子字段值中获取对应因子所需
                if (next.getKey().equals(factorId)) {
                    List<String> value = next.getValue();
                    for (String s : value) {
                        facFiledValue.put(s, parseResult.get(s));
                    }
                }
            }

            String ps = factor.getProcessingStrategy();
            //实例化处理策略
            ModelBuilder modelBuilder = (ModelBuilder) SpringUtil.getBean(ps + "ModelBuilder");
            //得到可以用于决策的因子字段及其对应的值
            facFiledValue = modelBuilder.reportProcessing(reportListMap, filMap, factorFormulaMap.get(factorId), facFiledValue);
            factor4Decision.put(factorId, facFiledValue);
        }
    }

    @Override
    public void policyDecision() {
        for (Factor factor : factors) {
            String key = factor.getId().toString();
            //获取因子字段及因子公式
            Map<String, Object> factorFormulaValue = factorFormulaMap.get(key);
            //获取因子字段及因子值
            Map<String, Object> factorFiledValue = factor4Decision.get(key);
            //决策
            List<Map<String, BigDecimal>> policyDecision = ModelHandlerUtil.policyDecision(factorFiledValue, factorFormulaValue);
            factorCoefficientMap.put(key, policyDecision);
        }
    }

    @Override
    public void getScore() {
        for (Factor factor : factors) {
            String key = factor.getId().toString();
            List<Map<String, BigDecimal>> list = factorCoefficientMap.get(key);
            //计算总分
            scoreMap.put(key, ModelHandlerUtil.getSum(factor.getSum(), list.get(0)));
        }
    }

    @Override
    public ModelResultBean saveRecord() {
        UserRequestModelRecordService userRequestModelRecordService = (UserRequestModelRecordService) SpringUtil.getBean("userRequestModelRecordService");
        if (reportListMap.containsKey("aFufxpg")) {
            userRequestModelRecordService.updateAFuStatus(appId,user.getIdCard());
        }
        String factorShow = app.getFactorShow();
        //是否默认展示分数
        boolean flag = "0".equals(factorShow);
        ModelResultBean resultBean = new ModelResultBean();
        Map<String, BigDecimal> scores = new HashMap<>();

        // 插入流水
        UserRequestModelRecord modelRecord = new UserRequestModelRecord();
        modelRecord.setAppId(appId);
        modelRecord.setUsername(user.getName());
        modelRecord.setIdCard(user.getIdCard());
        modelRecord.setMobile(user.getMobile());
        modelRecord.setModelId(model.getId());
        modelRecord.setChannelId(channelId);
        modelRecord.setTaskId(taskId);
        for (Factor factor : factors) {
            String key = factor.getId().toString();

            BigDecimal score = scoreMap.get(key);
            modelRecord.setScore(score.toString());
            modelRecord.setUsedFactor(JSONObject.toJSONString(factorCoefficientMap.get(key).get(1)));
            modelRecord.setCreateTime(new Date());
            modelRecord.setFactorId(factor.getId());
            modelRecord = userRequestModelRecordService.saveRequestRecordInfo(modelRecord);

            //添加扩展分数
            BigDecimal returnScore = score.add(new BigDecimal(factor.getExtendScore()));
            if (!flag) {
                //多个返回分数的因子
                List<String> strings = Arrays.asList(factorShow.split(Constant.FILED_COMMA.getConstant()));
                //时候包含当前
                boolean contains = strings.contains(key);
                //记录分数
                if (contains) scores.put(factor.getName(), returnScore);
            } else {
                //没有规定的话所有都记录
                scores.put(factor.getName(), returnScore);
            }
        }
        //返回多个分数时
        if (scores.size() > 1) {
            resultBean.setScores(scores);
            //返回数据不出现score字段
            resultBean.setScore(null);
        } else if (scores.size() == 1) {
            BigDecimal resScore = (BigDecimal) scores.values().toArray()[0];
            resultBean.setScore(resScore);
        } else {
            throw new MyException(Code.RETURN_SCORE_ERROR);
        }
        //记录流水
        resultBean = userRequestModelRecordService.modelResultHandle(null, resultBean, modelRecord);
        return resultBean;
    }
}
