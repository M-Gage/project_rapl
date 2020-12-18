package com.qingting.middleware.service;

import com.qingting.middleware.bean.HistoryApplyBean;
import com.qingting.middleware.bean.ModelResultBean;
import com.qingting.middleware.bean.request.UserApplyRecordBean;
import com.qingting.middleware.entity.Model;
import com.qingting.middleware.entity.UserApplyRecord;
import com.qingting.middleware.entity.UserRequestModelRecord;

import java.math.BigDecimal;
import java.util.Map;


public interface UserRequestModelRecordService{


    UserRequestModelRecord saveRequestRecordInfo(UserRequestModelRecord userRequestModelRecord);

    boolean updateByIdCardAndMobile(UserRequestModelRecord userRequestModelRecord);

    HistoryApplyBean getHistoryApply(String idCard, String mobile);

    ModelResultBean modelResultHandle(BigDecimal score, ModelResultBean mr, UserRequestModelRecord modelRecord);

    int saveRequestUserApply(UserApplyRecord uar);

    int updateUserApplyStatus(UserApplyRecordBean param, String appId);

    int updateAFuStatus(String appId, String idCard);
}
