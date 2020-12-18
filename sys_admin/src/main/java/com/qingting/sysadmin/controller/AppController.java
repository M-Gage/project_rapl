package com.qingting.sysadmin.controller;

import com.qingting.middleware.bean.sys.AppCreateBean;
import com.qingting.middleware.bean.sys.AppRechargeBean;
import com.qingting.middleware.bean.sys.AppUpdateBean;
import com.qingting.middleware.bean.request.SearchBean;
import com.qingting.middleware.entity.App;
import com.qingting.middleware.enums.Code;
import com.qingting.middleware.service.AppService;
import com.qingting.middleware.util.JsonResult;
import com.qingting.middleware.util.page.PageResultBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Gage
 * @describe App管理控制器
 * @date 2019-09-11 11:35
 */
@Api(tags = {"app控制器"})
@Slf4j
@RestController
@ResponseBody
public class AppController {

    @Resource
    private AppService appService;

    @ApiOperation(value = "创建app账号")
    @PostMapping(value = "/api/app/add")
    public JsonResult createApp(@RequestBody @Validated @ApiParam(name = "app对象", value = "传入json格式", required = true) AppCreateBean param) throws Exception {
        App app = appService.addApp(param);
        return JsonResult.success(app);
    }

    @ApiOperation(value = "查询app列表")
    @GetMapping(value = "/api/app/get")
    public JsonResult getApp(@Validated @ApiParam(name = "分页搜索基础类 ", value = "传入json格式", required = true) SearchBean param) throws Exception {
        PageResultBean<App> result = appService.getAppList(param);
        return JsonResult.success(result);
    }

    @ApiOperation(value = "app修改")
    @PostMapping(value = "/api/app/upd")
    public JsonResult updateApp(@Validated @RequestBody @ApiParam(name = "分页搜索基础类 ", value = "传入json格式", required = true) AppUpdateBean param) throws Exception {
        if(appService.updateApp(param)){
            return JsonResult.success();
        }else {
            return JsonResult.error(Code.UPDATE_ERROR);
        }
    }

    @ApiOperation(value = "app充值")
    @PostMapping(value = "/api/app/recharge")
    public JsonResult appRecharge(@Validated @RequestBody @ApiParam(name = "app充值接口 ", value = "传入json格式", required = true) AppRechargeBean param) throws Exception {
        if(appService.updateAppAmount(param)){
            return JsonResult.success();
        }else {
            return JsonResult.error(Code.UPDATE_ERROR);
        }
    }


}

