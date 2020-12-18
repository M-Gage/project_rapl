package com.qingting.middleware.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qingting.middleware.bean.ModelUserBean;
import com.qingting.middleware.bean.ReportBaseBean;
import com.qingting.middleware.entity.App;
import com.qingting.middleware.entity.Model;
import com.qingting.middleware.enums.Code;
import com.qingting.middleware.enums.Constant;
import com.qingting.middleware.exception.MyException;
import com.qingting.middleware.util.thread.ChildThreadException;
import com.qingting.middleware.util.thread.MultiParallelThreadHandler;
import com.qingting.middleware.util.thread.MultiThreadHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.Callable;

/**
 * 模型业务处理工具类
 */
@Slf4j
public class ModelHandlerUtil {

    private static ScriptEngine jse = new ScriptEngineManager().getEngineByName("JavaScript");
    private final static String EQUAL = "=";
    private final static String GREATER_EQUAL = ">=";
    private final static String LESS_EQUAL = "<=";
    private final static String LESS_THAN = "<";
    private final static String GREATER_THAN = ">";
    private final static String IS_NOT_NULL = "isnotnull";
    private final static String SINGLE_QUOTATION_MARK = "'";
    private final static String AT = "@";
    private final static String SEMICOLON = ";";
    private final static String AND = "and";
    private static final String OR = "or";
    private static final String THEN = "then";


    /**
     * 决策分析（因子值经过因子公式处理获取因子）
     *
     * @param factor  因子字段对应的因子值
     * @param formula 因子字段对应的因子公式
     * @return 因子字段对应的因子
     */
    public static List<Map<String, BigDecimal>> policyDecision(Map<String, Object> factor, Map<String, Object> formula) {
        List<Map<String, BigDecimal>> rl = new ArrayList<>();
        //按因子值遍历
        Iterator iterator = factor.entrySet().iterator();
        //保存结果
        Map<String, BigDecimal> formulaResult = new HashMap<>();
        //保存因子值
        Map<String, BigDecimal> factorResult = new HashMap<>();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();

            //因子值
            Object value = entry.getValue();
            //若是集合对象，那么取最后一个
           /* if (value instanceof List) {
                value = ((List) value).get(((List) value).size() - 1);
            }*/
            String key = (String) entry.getKey();
            String formulaStr = formula.get(key).toString()
                    .replaceAll("\r\n", Constant.FILED_NO_STR.getConstant())
                    .replaceAll(Constant.FILED_BLANK.getConstant(), Constant.FILED_NO_STR.getConstant())
                    .replaceAll("\\(", Constant.FILED_NO_STR.getConstant())
                    .replaceAll("\\)", Constant.FILED_NO_STR.getConstant());
            //@后部分是因子系数，前部分是全部的因子公式
            String[] sp = formulaStr.split(AT);
            //因子系数
            String factorCoefficient = sp[1];
            //因子公式
            String[] factorFormula = sp[0].split(SEMICOLON);
            //因子
            String factorScore = Constant.FILED_NO_STR.getConstant();
            //处理一个因子字段的因子公式
            for (String ff : factorFormula) {
                //定义切割符then
                String[] thens = ff.split(THEN);

                if (value == null) {
                    //不能被then切割的都是ifnull
                    if (thens.length == 1) {//else
                        //因子值为空那么直接获取ifnull的值
                        factorScore = ff.substring(ff.indexOf(Constant.FILED_COMMA.getConstant()) + 1);
                        break;
                    }
                } else {
                    //因子值不为空且进入ifnull中，因子值等于其本身
                    if (thens.length == 1) {//else
                        String val = value.toString();
                        //当得到的值为空字符串的时候则取默认值即fnull的值
                        if (StringUtils.isBlank(val)) {
                            factorScore = ff.substring(ff.indexOf(Constant.FILED_COMMA.getConstant()) + 1);
                        } else {
                            factorScore = val;
                        }
                        break;
                    }
                    /*
                     * 以下都是需要公式判断
                     * */
                    //前部分为判断公式
                    String condition = thens[0];
                    //后部分为符合公式的值
                    String val = thens[1];
                    //包含and，那么就是多个条件
                    if (condition.contains(AND)) {
                        String[] ops = condition.split(AND);
                        //计数
                        int count = 0;
                        for (String op : ops) {
                            //公式判断
                            if (getResult(value, op)) count += 1;
                        }
                        //全部符合那么通过
                        if (count == ops.length) {
                            factorScore = val;
                            break;
                        }
                        continue;
                    }
                    //处理方式与and相同
                    if (condition.contains(OR)) {
                        String[] ops = condition.split(OR);
                        int count = 0;
                        for (String op : ops) {
                            if (getResult(value, op)) count += 1;
                        }
                        //只要有一个符合都行
                        if (count >= 1) {
                            factorScore = val;
                            break;
                        }
                        continue;
                    }
                    //单个公式处理（大多数情况下是这样）
                    if (getResult(value, condition)) {
                        factorScore = val;
                        break;
                    }
                }
            }
            //运算符
            String operator = factorCoefficient.substring(0, 1);
            //因子系数
            String num = factorCoefficient.substring(1);
            BigDecimal bigNum = new BigDecimal(num);

            //与因子系数计算后的因子值（即因子）
            BigDecimal factorAfter;
            factorResult.put(key, new BigDecimal(factorScore));

            if (operator.equals("*")) {
                factorAfter = bigNum.multiply(new BigDecimal(factorScore));
            } else if (operator.equals("+")) {
                factorAfter = bigNum.add(new BigDecimal(factorScore));
            } else if (operator.equals("-")) {
                factorAfter = bigNum.subtract(new BigDecimal(factorScore));
            } else {
                factorAfter = bigNum.divide(new BigDecimal(factorScore));
            }
            //放入集合用于总计
            formulaResult.put(key, factorAfter);
        }
        rl.add(formulaResult);
        rl.add(factorResult);
        return rl;
    }

    /**
     * 计算分值
     *
     * @param sum              计算总和公式
     * @param stringIntegerMap 因子字段对应的因子
     * @return 分值
     */
    public static BigDecimal getSum(String sum, Map<String, BigDecimal> stringIntegerMap) {
        //去除多余的字符
        sum = sum.replaceAll("\r\n", Constant.FILED_NO_STR.getConstant()).replaceAll(Constant.FILED_BLANK.getConstant(), Constant.FILED_NO_STR.getConstant());
        Iterator<Map.Entry<String, BigDecimal>> iterator = stringIntegerMap.entrySet().iterator();
        //遍历因子
        while (iterator.hasNext()) {
            Map.Entry<String, BigDecimal> next = iterator.next();
            //将因子字段换成因子
            sum = sum.replaceAll(next.getKey(), "(" + String.valueOf(next.getValue()) + ")");
        }
        try {
            //计算公式
            String eval = jse.eval(sum).toString();
            log.debug("计算总分为：{}", eval);
            return new BigDecimal(eval);
        } catch (ScriptException e) {
            log.debug("计算出错");
            e.printStackTrace();
            return new BigDecimal(0);
        }
    }

    /**
     * 获取公式匹配结果
     *
     * @param value     因子值
     * @param operation 运算公式
     * @return true 匹配 false 不匹配
     */
    private static boolean getResult(Object value, String operation) {
        //以下为因子判断公式处理
        if (operation.contains(GREATER_EQUAL)) {
            return operatorHandler(value, operation, GREATER_EQUAL);
        } else if (operation.contains(LESS_EQUAL)) {
            return operatorHandler(value, operation, LESS_EQUAL);
        } else if (operation.contains(LESS_THAN)) {
            return operatorHandler(value, operation, LESS_THAN);
        } else if (operation.contains(GREATER_THAN)) {
            return operatorHandler(value, operation, GREATER_THAN);
        } else if (operation.contains(EQUAL)) {
            //等于判断时有数值或字符串判断
            String sub = operation.substring(operation.indexOf(EQUAL) + 1);
            try {
                double x = Double.parseDouble(value.toString());
                double y = Double.parseDouble(sub);
                return x == y;
            } catch (Exception e) {
                //表示是字符串的处理，字符串由单引号内的内容判断
                String s = sub.replaceAll(SINGLE_QUOTATION_MARK, Constant.FILED_NO_STR.getConstant());
                return s.equals(value);
            }
        } else if (operation.contains(IS_NOT_NULL)) {
            //前面已经判空，那么直接返回
            return true;
        } else {
            return false;
        }
    }

    /**
     * 计算公式处理
     *
     * @param value     对比的入参值
     * @param operation 计算公式
     * @param opStr     运算符
     * @return true 匹配 false 不匹配
     */
    private static boolean operatorHandler(Object value, String operation, String opStr) {
        //当传入的值为空字符串时直接返回错误
        if (value instanceof String && StringUtils.isEmpty((CharSequence) value)) return false;

        //因为包含不等符号，那么一定是数值比较
        double x = Double.parseDouble(value.toString());
        String sub = operation.substring(operation.indexOf(opStr) + opStr.length());
        double y = Double.parseDouble(sub);

        //先判断是否有等号且值相等
        if (opStr.length() > 1 && x == y) return true;

        //判断值是否符合大小比
        if (opStr.contains(LESS_THAN)) {
            return x < y;
        } else {
            return x > y;
        }
    }


    /**
     * 从JsonStr中获取字符串列表中的字段存入map中
     *
     * @param jsonStr   待操作的字符串
     * @param keys      字段列表
     * @param resultMap 结果集合
     * @return 结果集合
     */
    private static Map<String, Object> getMapOnJsonStrByKeys(String jsonStr, List<String> keys, Map<String, Object> resultMap) {
        //对关键字进行逐个匹配
        for (String key : keys) {
            //解析每一层的json对象
            Map rp = (Map) JSON.parse(jsonStr);
            //空报文默认为空
            if (rp == null || rp.size() == 0) {
                resultMap.put(key, null);
                continue;
            }
            Iterator entries = rp.entrySet().iterator();
            //遍历
            while (entries.hasNext()) {
                Map.Entry entry = (Map.Entry) entries.next();
                //键
                Object entryKey = entry.getKey();
                //值
                Object entryValue = entry.getValue();
                if (key.equals(entryKey)) {
                    //匹配就录入
                    resultMap.put(key, entryValue);
                   /* //先获取key的信息
                    Object value = resultMap.get(key);
                    //key第一次录入
                    if (value == null) {
                        resultMap.put(key, entryValue);
                    } else {
                        //第二次以上录入
                        if (value instanceof List) {
                            ((List) value).add(entryValue);
                            resultMap.put(key, value);
                        } else {
                            //第二次录入
                            List<Object> l = new ArrayList<>();
                            l.add(value);
                            l.add(entryValue);
                            resultMap.put(key, l);
                        }
                    }*/
                }
                //不匹配那么查询值内是否包含
                if (entryValue != null && entryValue.toString().contains(key)) {
                    //只对当前关键字查询
                    List<String> oneKey = new ArrayList<>();
                    oneKey.add(key);
                    //是否对象格式
                    if (entryValue instanceof Map) {
                        //递归
                        resultMap = ModelHandlerUtil.getMapOnJsonStrByKeys(entryValue.toString(), oneKey, resultMap);
                    } else if (entryValue instanceof JSONArray) {//是否JSONArray格式
                        //逐个递归
                        /*for (Object o : (JSONArray) entryValue) {
                            resultMap = ReportParseUtil.getMapOnJsonStrByKeys(o.toString(), oneKey, resultMap);
                        }*/
                        //取最后一个
                        JSONArray arr = (JSONArray) entryValue;
                        JSONObject jsonObject = arr.getJSONObject(arr.size() - 1);
                        resultMap = ModelHandlerUtil.getMapOnJsonStrByKeys(jsonObject.toString(), oneKey, resultMap);
                    }
                }
            }
        }
        return resultMap;
    }

    /**
     * 获取原报文集合
     *
     * @param reportList 因子报文名集合
     * @param reports    传参集合
     * @param model      模型对象
     * @param app        产品对象
     * @param methods    方法集合
     * @return 原报文集合
     */
    public static Map<String, Object> getReportList(List<String> reportList, JSONObject reports, Model model, App app, Method[] methods) {
        //多线程执行
        MultiThreadHandler handler = new MultiParallelThreadHandler();

        //报文流水录入信息
        ModelUserBean user = JSON.parseObject(reports.get("user").toString(), ModelUserBean.class);
        ReportBaseBean rb = new ReportBaseBean();
        rb.setName(user.getName());
        rb.setMobile(user.getMobile());
        rb.setIdCard(user.getIdCard());
        rb.setChannelId(reports.getString("channelId"));
        rb.setTaskId(reports.getString("taskId"));
        rb.setAppId(app.getAppId());

        //报文补全开关是否开启
        boolean reportSwitch = model.getReportSwitch() == 0 && app.getReportSwitch() == 0;

        //开始获取报文
        for (String rep : reportList) {
            //接收报文结果
            Map<String, Object> resultMap = new HashMap<>();

            //获取当前对应的关键报文（外部）
            Object json = reports.get(rep);

            Callable callable;
            //外部没有传报文，那么进行报文补全
            if (StringUtils.isEmpty((CharSequence) json)) {
                log.info("缺失关键报文：[{}],尝试补全", rep);
                //判断报文补全开关是否开启
                if (reportSwitch) {
                    callable = new Callable<Map<String, Object>>() {
                        @Override
                        public Map<String, Object> call() throws Exception {
                            //录入报文名称
                            rb.setBlacklistName(rep);
                            String json = null;
                            //遍历所有获取报文的方法
                            for (Method method : methods) {
                                String name = method.getName();
                                //方法名是由报文名拼装而成，创建新的的时候按照这一标准，以数据库录入为主
                                String str = "getReport4" + rep;
                                //匹配报文名称是否和方法名称一致
                                if (str.equalsIgnoreCase(name)) {
                                    //从第三方获取报文
                                    json = (String) method.invoke(SpringUtil.getBean("reportCompleteService"), rb);
                                    break;
                                }
                            }
                            resultMap.put(rep, json);
                            return resultMap;
                        }
                    };
                    handler.addTask(callable);
                } else {
                    log.info("报文补全被关闭！！！");
                    throw new MyException(Code.MODEL_REPORT_MISS, rep);
                }
            } else {//如果存在那么用传入的报文
                callable = new Callable<Map<String, Object>>() {
                    //这样做完全只是为了收集结果方便（要优化你去做）
                    @Override
                    public Map<String, Object> call() throws Exception {
                        resultMap.put(rep, json);
                        return resultMap;
                    }
                };
                handler.addTask(callable);
            }
        }
        Map<String, Object> reportListMap = new HashMap<>();
        try {
            log.info("开始补全报文===============》");
            List<Object> call = handler.call();
            for (Object o : call) {
                reportListMap.putAll((Map<String, Object>) o);
            }
        } catch (ChildThreadException e) {
            e.printStackTrace();
        }
        return reportListMap;
    }

    /**
     * 解析报文（即获取因子字段在报文中的值）
     *
     * @param factorFiledMap 报文对应的因子字段集合
     * @param reportListMap  报文集合
     * @param filedList      因子字段集合
     * @return 因子字段及其对应的值集合
     */
    public static Map<String, Object> parseReport(Map<String, Object> factorFiledMap, Map<String, Object> reportListMap, List<String> filedList) {
        Iterator<Map.Entry<String, Object>> filIter = factorFiledMap.entrySet().iterator();
        Map<String, Object> parseResult = new HashMap<>();
        //多线程执行
        MultiThreadHandler handler = new MultiParallelThreadHandler();
        while (filIter.hasNext()) {
            Map.Entry entry = (Map.Entry) filIter.next();
            //键（报文名）
            String entryKey = (String) entry.getKey();
            //值(报文所需的字段集合)
            List<String> entryValue = new ArrayList<>();
            entryValue.addAll((Collection<? extends String>) entry.getValue());
            //获取原报文
            String json = (String) reportListMap.get(entryKey);
            Callable callable = new Callable<Map<String, Object>>() {
                @Override
                public Map<String, Object> call() throws Exception {
                    return getMapOnJsonStrByKeys(json, entryValue, parseResult);
                }
            };
            handler.addTask(callable);
        }
        try {
            log.info("开始解析报文============>");
            List<Object> call = handler.call();
            for (Object o : call) {
                parseResult.putAll((Map<String, Object>) o);
            }
            log.info("解析结果：parseResult={}", JSONObject.toJSONString(parseResult));
        } catch (ChildThreadException e) {
            e.printStackTrace();
            log.info("解析报文出错============>");
        }
        //对于缺失的因子初始化为空
        for (String s : filedList) {
            //因子缺失补null
            parseResult.putIfAbsent(s, null);
        }

        log.info("结束解析报文============>");
        return parseResult;
    }
}