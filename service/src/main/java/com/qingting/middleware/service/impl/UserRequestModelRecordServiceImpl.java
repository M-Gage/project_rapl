package com.qingting.middleware.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.qingting.middleware.bean.HistoryApplyBean;
import com.qingting.middleware.bean.ModelResultBean;
import com.qingting.middleware.bean.request.UserApplyRecordBean;
import com.qingting.middleware.dao.AppModelPriceMapper;
import com.qingting.middleware.dao.UserApplyRecordMapper;
import com.qingting.middleware.dao.UserRequestModelRecordMapper;
import com.qingting.middleware.entity.UserApplyRecord;
import com.qingting.middleware.entity.UserRequestModelRecord;
import com.qingting.middleware.service.UserRequestModelRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

@Slf4j
@Service("userRequestModelRecordService")
public class UserRequestModelRecordServiceImpl implements UserRequestModelRecordService {

    @Resource
    private UserRequestModelRecordMapper userRequestModelRecordMapper;
    @Resource
    private AppModelPriceMapper appModelPriceMapper;
    @Resource
    private UserApplyRecordMapper userApplyRecordMapper;


    @Override
    public UserRequestModelRecord saveRequestRecordInfo(UserRequestModelRecord urmr) {
        if (userRequestModelRecordMapper.insertSelective(urmr) > 0) {
            return urmr;
        } else {
            log.debug("===>身份证:[{}]，电话号:[{}]的用户流水保存失败", urmr.getIdCard(), urmr.getMobile());
            return null;
        }
    }

    @Override
    public boolean updateByIdCardAndMobile(UserRequestModelRecord urmr) {
        if (userRequestModelRecordMapper.updateByPrimaryKeySelective(urmr) > 0) {
            return true;
        } else {
            log.debug("===>身份证:[{}]，电话号:[{}]的用户更新返回报文失败", urmr.getIdCard(), urmr.getMobile());
            return false;
        }
    }

    @Override
    public HistoryApplyBean getHistoryApply(String idCard, String mobile) {
        HistoryApplyBean mha = userRequestModelRecordMapper.selectUserMobileHistoryApply(mobile);
        HistoryApplyBean icha = userRequestModelRecordMapper.selectUserIdCardHistoryApply(idCard);

        mha.setIdCard_1h_cnt(icha.getIdCard_1h_cnt());
        mha.setIdCard_3h_cnt(icha.getIdCard_3h_cnt());
        mha.setIdCard_12h_cnt(icha.getIdCard_12h_cnt());
        mha.setIdCard_1d_cnt(icha.getIdCard_1d_cnt());
        mha.setIdCard_3d_cnt(icha.getIdCard_3d_cnt());
        mha.setIdCard_7d_cnt(icha.getIdCard_7d_cnt());
        mha.setIdCard_14d_cnt(icha.getIdCard_14d_cnt());
        mha.setIdCard_30d_cnt(icha.getIdCard_30d_cnt());
        mha.setIdCard_60d_cnt(icha.getIdCard_60d_cnt());

        return mha;
    }

    @Override
    @Transactional
    public ModelResultBean modelResultHandle(BigDecimal score, ModelResultBean mr, UserRequestModelRecord modelRecord) {

        // 返回分数，历史计数结果，二要素（身份证号，电话号码），用户历史操作轨迹
//        HistoryApplyBean ha = userRequestModelRecordService.getHistoryApply(idCard, mobile);
        //拼装返回结果
        if (mr == null) mr = new ModelResultBean();
        mr.setIdCard(modelRecord.getIdCard());
        mr.setMobile(modelRecord.getMobile());
        mr.setTaskId(modelRecord.getTaskId());
        if (score != null) {
            mr.setScore(score);
        }
        //用户操作历史轨迹（所以后面还要更新一次流水）
//        mr.setHistoryApplyBean(ha);
        //更新返回报文记录
        UserRequestModelRecord urmr = new UserRequestModelRecord();
        urmr.setIdCard(modelRecord.getIdCard());
        urmr.setMobile(modelRecord.getMobile());
        urmr.setTaskId(modelRecord.getTaskId());
        urmr.setRequestResult(JSONObject.toJSONString(mr));
        updateByIdCardAndMobile(urmr);
        appModelPriceMapper.updateUsedTimes(modelRecord.getAppId(), modelRecord.getModelId());
        return mr;
    }

    @Override
    public int saveRequestUserApply(UserApplyRecord uar) {
        return userApplyRecordMapper.insertSelective(uar);
    }

    @Override
    public int updateUserApplyStatus(UserApplyRecordBean param, String appId) {
        return userApplyRecordMapper.updateBatchApplyRecord(param.getUserMobile(), param.getStatus(), appId);
    }

    @Override
    public int updateAFuStatus(String appId, String idCard) {
        UserApplyRecord uar =new UserApplyRecord();
        uar.setStatus("1");
        uar.setAppId(appId);
        uar.setIdCard(idCard);
        uar.setUpdateTime(new Date());
        return userApplyRecordMapper.updateStatusByAppIdAndIdCard(uar);
    }


}
