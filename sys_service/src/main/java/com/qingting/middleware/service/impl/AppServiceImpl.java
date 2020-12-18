package com.qingting.middleware.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qingting.middleware.bean.sys.AppCreateBean;
import com.qingting.middleware.bean.request.SearchBean;
import com.qingting.middleware.bean.sys.AppRechargeBean;
import com.qingting.middleware.bean.sys.AppUpdateBean;
import com.qingting.middleware.dao.AppMapper;
import com.qingting.middleware.dao.AppRechargeRecordMapper;
import com.qingting.middleware.entity.App;
import com.qingting.middleware.entity.AppRechargeRecord;
import com.qingting.middleware.enums.Code;
import com.qingting.middleware.exception.MyException;
import com.qingting.middleware.service.AppService;
import com.qingting.middleware.util.RSAEncryptionUtil;
import com.qingting.middleware.util.page.PageResultBean;
import com.qingting.middleware.util.page.PageUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class AppServiceImpl implements AppService {

    @Resource
    private AppMapper appMapper;
    @Resource
    private AppRechargeRecordMapper appRechargeRecordMapper;

    @Override
    public App addApp(AppCreateBean param) throws Exception {
        App app = appMapper.selectByAppKey(param.getAppKey());
        if (app != null) {
            throw new MyException(Code.DUPLICATE_ERROR, param.getAppName());
        }
        App a = new App();
        a.setAppName(param.getAppName());
        a.setAppKey(param.getAppKey());
        a.setPassword(param.getPassword());
        //初始化密钥
        Map<String, Object> keyMap = RSAEncryptionUtil.initKey();

        //公钥
        String privateKeyStr = RSAEncryptionUtil.getPrivateKey(keyMap);

        //私钥
        String publicKeyStr = RSAEncryptionUtil.getPublicKey(keyMap);
        a.setPrivateKey(privateKeyStr);
        a.setPublicKey(publicKeyStr);
        a.setCreateTime(new Date());
        if (appMapper.insertSelective(a) > 0) {
            return a;
        } else {
            throw new MyException(Code.CREATE_ERROR);
        }
    }

    @Override
    public PageResultBean<App> getAppList(SearchBean param) {

        PageHelper.startPage(param.getPageNum(), param.getPageSize());
        List<App> apps = appMapper.selectAllApp(param.getCondition(), param.getStatus(), param.getStartTime(), param.getEndTime());
        if (apps == null || apps.size() == 0) {
            return null;
        }
        PageInfo<App> pageInfo = new PageInfo(apps);
        return PageUtils.getPageResult(pageInfo);
    }

    @Override
    public boolean updateApp(AppUpdateBean param) throws Exception {
        App a = new App();
        a.setAppName(param.getAppName());
        a.setAppKey(param.getAppKey());
        a.setPassword(param.getPassword());
        if (param.getUpdateKey() != null && param.getUpdateKey()) {
            //初始化密钥
            Map<String, Object> keyMap = RSAEncryptionUtil.initKey();

            //公钥
            String privateKeyStr = RSAEncryptionUtil.getPrivateKey(keyMap);

            //私钥
            String publicKeyStr = RSAEncryptionUtil.getPublicKey(keyMap);
            a.setPrivateKey(privateKeyStr);
            a.setPublicKey(publicKeyStr);
        }
        a.setReportSwitch(param.getReportSwitch());
        a.setStatus(param.getStatus());
        return appMapper.updateByPrimaryKeySelective(a) > 0;
    }

    @Override
    public boolean updateAppAmount(AppRechargeBean param) {
        AppRechargeRecord arr = new AppRechargeRecord();
        arr.setAppId(param.getAppId());
        arr.setPrice(param.getRechargeAmount());
        arr.setCreateTime(new Date());
        if (appRechargeRecordMapper.insertSelective(arr) < 1)
            throw new MyException(Code.CREATE_ERROR, "appRechargeRecord");
        return appMapper.updateAppAmount(Integer.parseInt(param.getAppId()), param.getRechargeAmount()) > 0;
    }

}
