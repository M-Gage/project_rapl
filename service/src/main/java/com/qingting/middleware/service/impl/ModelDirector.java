package com.qingting.middleware.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qingting.middleware.bean.ModelResultBean;
import com.qingting.middleware.bean.request.ModelParamBean;
import com.qingting.middleware.entity.App;
import com.qingting.middleware.entity.Model;
import com.qingting.middleware.enums.Code;
import com.qingting.middleware.exception.MyException;
import com.qingting.middleware.service.AppService;
import com.qingting.middleware.service.IRiskControl;
import com.qingting.middleware.service.ModelService;
import com.qingting.middleware.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Gage
 * @describe 模型建造指挥者
 * @date 2019-12-09 14:46
 */
@Slf4j
public class ModelDirector {

    private String modelNo;
    private String modelName;
    private JSONObject reports;
    private String appId;


    private ModelService modelService;
    private AppService appService;

    public ModelDirector(ModelService modelService, AppService appService) {
        this.modelService = modelService;
        this.appService = appService;
    }

    public ModelDirector create(ModelParamBean param, String appId) {
        this.appId = appId;
        if (param == null) {
            log.info("===>app:[{}]请求失败，入参为空", appId);
            throw new MyException(Code.PARAM_NULL);
        }
        this.modelNo = param.getModelNo();
        this.modelName = param.getModelName();
        log.info("===>app:[{}]启用[ {} ]模型，版本号为：{}", appId, modelName, modelNo);
        this.reports = CommonUtil.jsonToMap(JSON.toJSONString(param));
        return this;
    }

    public ModelResultBean build() {
        App a = getAppInfo(appId);
        Model m = checkModel(a.getModelId(),modelNo, modelName);

        Integer factorId = null;
        String factorListStr = null;
        //用户无特殊设置
        if (a.getFactorSwitch() == null) {
            if (m.getFactorListSwitch() == 0) {
                factorListStr = m.getFactorList();
            } else {
                factorId = m.getFactorId();
            }
        } else if (a.getFactorSwitch() == 0) {
            factorId = a.getFactorId();
        } else {
            factorListStr = a.getFactorList();
        }

        /*
         * 策略-简单工厂模式
         */
        String factor;
        IRiskControl riskControl;
        //不为空则使用单一风控模式
        if (factorId != null) {
            log.info("===>单一风控模式:factor:[{}]", factorId);
            factor = factorId.toString();
            riskControl = new SingleModelRisk(reports, m,a);
        } else {//复合风控模式
            log.info("===>复合风控模式:factor:[{}]", factorListStr);
            factor = factorListStr;
            riskControl = new ComplexModelRisk(reports, m,a);
        }


        riskControl.getFactor(factor);
        riskControl.getReport();
        riskControl.preProcessing();
        riskControl.parseReport();
        riskControl.reportProcessing();
        riskControl.policyDecision();
        riskControl.getScore();
        return riskControl.saveRecord();
    }

    private Model checkModel(Integer modelId, String modelNo, String modelName) {
        Model model = modelService.selectByPrimaryKey(modelId);
        if (model == null) {
            throw new MyException(Code.RETURN_NULL,"获取模型："+modelName);
        }
        if (!modelNo.equals(model.getModelNo())|| !modelName.equals(model.getModelName())){
            throw new MyException(Code.MODEL_MATCH_ERROR);
        }
        return model;
    }

    private App getAppInfo(String appId) {
        App appInfo = appService.getAppInfo(appId);
        if (appInfo == null) {
            throw new MyException(Code.RETURN_NULL,"获取产品信息："+appId);
        }
        return appInfo;
    }


}
