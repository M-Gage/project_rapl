package com.qingting.middleware.service.impl;

import com.google.common.base.Joiner;
import com.qingting.middleware.baseController.BaseController;
import com.qingting.middleware.bean.*;
import com.qingting.middleware.bean.request.*;
import com.qingting.middleware.dao.AppConfigureMapper;
import com.qingting.middleware.dao.UserLoanRecordMapper;
import com.qingting.middleware.dao.UserRepaymentRecordMapper;
import com.qingting.middleware.entity.AppConfigure;
import com.qingting.middleware.entity.UserLoanRecord;
import com.qingting.middleware.entity.UserRepaymentRecord;
import com.qingting.middleware.service.TogetherDebtService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
public class TogetherDebtServiceImpl extends BaseController implements TogetherDebtService {

    @Resource
    private UserLoanRecordMapper userLoanRecordMapper;
    @Resource
    private UserRepaymentRecordMapper userRepaymentRecordMapper;
    @Resource
    private AppConfigureMapper appConfigureMapper;

    @Override
    public boolean saveLoanInfo(LoanBean param) {
        UserLoanRecord ul = new UserLoanRecord();
        ul.setAppId(getAppId());
        ul.setUserName(param.getUserName());
        ul.setUserIdcard(param.getUserIdcard());
        ul.setUserMobile(param.getUserMobile());
        ul.setIndId(param.getIndId());
        ul.setIndMoneyJie(param.getIndMoneyJie());
        ul.setCreateTime(new Date());
        if (userLoanRecordMapper.insertSelective(ul) > 0) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean saveRepaymentRecordInfo(RepaymentBean param) {


        UserLoanRecord ul = new UserLoanRecord();
        ul.setIndId(param.getIndId());
        if (param.getIndIfPay().equals("1")) {
            ul.setIndIfPay(1);
        }
        if (param.getIndIfPay().equals("2")) {
            ul.setIndIfPay(2);
        }
        UserRepaymentRecord ur = new UserRepaymentRecord();
        ur.setIndId(param.getIndId());
        ur.setIndRepayTime(param.getIndRepayTime());
        ur.setIndRepayMoney(param.getIndRepayMoney());
        ur.setCreateTime(new Date());

        if (userRepaymentRecordMapper.insertSelective(ur) > 0) {
            ul.setLastRepaymentTime(param.getIndRepayTime());
            if (userLoanRecordMapper.updateByPrimaryKeySelective(ul) == 0) {
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean updatePayStatus(UpdateOrderStatusBean param) {
        return userLoanRecordMapper.updateBatchOrderStatus(getAppId(), param.getIndId(), param.getIndIfPay(), param.getIndFangTime()) > 0;
    }

    @Override
    public TogetherDebtResultBean getUserTogetherDebtInfo(TogetherDebtBean param) {
        String[] pIds = param.getPlatforms().split(",");
        TogetherDebtResultBean tdr = new TogetherDebtResultBean();
        //获取所有平台id
        List<UserLoanRecord> alls = userLoanRecordMapper.selectAllByUserInfo(param.getUserIdcard(), param.getUserMobile(), param.getUserName(), pIds);
        tdr.setUserName(param.getUserName());
        tdr.setUserIdcard(param.getUserIdcard());
        tdr.setUserMobile(param.getUserMobile());
        tdr.setMonitorCount(0);
        tdr.setTotalCount(0);
        tdr.setAllCount(0);
        if (alls == null) return tdr;

        List<String> allAppId = new ArrayList<>();
        List<String> debtAppId = new ArrayList<>();
        List<String> repaymentAppId = new ArrayList<>();
        List<String> overdueAppId = new ArrayList<>();
        Map<String, Date> platformUpdateTime = new HashMap<>();
        tdr.setAllCount(alls.size());
        for (UserLoanRecord ulr : alls) {
            String appId = ulr.getAppId();
            Integer indIfPay = ulr.getIndIfPay();
            allAppId.add(appId);
            platformUpdateTime.put(appId,ulr.getUpdateTime());
            if (0 == indIfPay || 2 == indIfPay || 3 == indIfPay)
                debtAppId.add(appId);
            if (1 == indIfPay)
                repaymentAppId.add(appId);
            if (3 == indIfPay)
                overdueAppId.add(appId);

        }
        tdr.setPlatformUpdateTime(platformUpdateTime);
        tdr.setAllAppId(Joiner.on(",").join(allAppId));
        tdr.setOverdueCount(overdueAppId.size());
        tdr.setOverdueAppId(Joiner.on(",").join(overdueAppId));
        tdr.setRepaymentAppId(Joiner.on(",").join(repaymentAppId));
        tdr.setRepaymentCount(repaymentAppId.size());

        AppConfigure appConfigure = appConfigureMapper.selectByProductId(getAppId());
        //共债平台计数
        int count = 0;
        //是否有配置共债平台
        if (appConfigure == null) {
            tdr.setMonitorCount(0);
        } else {

            //获取共债平台列表
            String[] split = appConfigure.getAppAll().split(",");
            for (String pu : debtAppId) {
                for (String s : split) {
                    //判断id是否相等
                    if (pu.equals(s)) {
                        count += 1;
                    }
                }
            }
        }
        tdr.setMonitorCount(count);
        tdr.setTotalCount(debtAppId.size());
        tdr.setDebtAppId(Joiner.on(",").join(debtAppId));
        return tdr;
    }

    @Override
    public boolean deleteUserLoanRecord(TogetherDebtDeleteBean param) {
        UserLoanRecord ul = new UserLoanRecord();
        ul.setIndId(param.getIndId());
        ul.setStatus(Integer.parseInt(param.getStatus()));
        return userLoanRecordMapper.updateStatusByIndId(ul) > 0;

    }

    @Override
    public boolean deleteUserRepaymentRecord(TogetherDebtDeleteBean param) {
        UserRepaymentRecord ur = new UserRepaymentRecord();
        ur.setIndId(param.getIndId());
        ur.setStatus(Integer.parseInt(param.getStatus()));
        ur.setIndRepayTime(param.getIndRepayTime());
        return userRepaymentRecordMapper.updateStatusByIndIdAndRepayTime(ur) > 0;
    }

    @Override
    public TogetherDebtHistoryBean getUserTogetherDebtHistory(UserBean param) {
        //查询共债配置表
        AppConfigure appConfigure = appConfigureMapper.selectByProductId(getAppId());
        String all = "9999";
        if (appConfigure != null) all = appConfigure.getAppAll();
        return userLoanRecordMapper.selectHistoryByUserInfo(param.getUserIdcard(), param.getUserMobile(), param.getUserName(), all);
    }

    @Override
    public UserLoanRecord verifyUser(UserBean param) {
        return userLoanRecordMapper.selectByMobile(param.getUserMobile());
    }

    @Override
    public List<UserLoanRecord> getPastDayDebtRecord(PastDayDebtBean param) {
        //查询共债配置表
        AppConfigure appConfigure = appConfigureMapper.selectByProductId(getAppId());
        if (appConfigure == null) return null;
        return userLoanRecordMapper.getPastDayDebtRecord(param.getUserIdcard(), param.getUserMobile(), param.getUserName(), appConfigure.getAppAll());
    }

}
