package com.qingting.middleware.service;

import com.qingting.middleware.bean.sys.AppRechargeBean;
import com.qingting.middleware.bean.sys.AppCreateBean;
import com.qingting.middleware.bean.request.SearchBean;
import com.qingting.middleware.bean.sys.AppUpdateBean;
import com.qingting.middleware.entity.App;
import com.qingting.middleware.util.page.PageResultBean;

public interface AppService {

    App addApp(AppCreateBean param) throws Exception;

    PageResultBean<App> getAppList(SearchBean param);

    boolean updateApp(AppUpdateBean param) throws Exception;

    boolean updateAppAmount(AppRechargeBean param);
}
