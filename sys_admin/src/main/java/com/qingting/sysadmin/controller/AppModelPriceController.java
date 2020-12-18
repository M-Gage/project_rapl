package com.qingting.sysadmin.controller;

import com.qingting.middleware.bean.request.SearchBean;
import com.qingting.middleware.bean.sys.*;
import com.qingting.middleware.entity.App;
import com.qingting.middleware.entity.AppModelPrice;
import com.qingting.middleware.enums.Code;
import com.qingting.middleware.service.AppModelPriceService;
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
import java.util.List;

/**
 * @author Gage
 * @describe App模型价格控制器
 * @date 2019-10-09 11:35
 */
@Api(tags = {"App模型价格控制器"})
@Slf4j
@RestController
@ResponseBody
public class AppModelPriceController {

    @Resource
    private AppModelPriceService appModelPriceService;

    @ApiOperation(value = "创建一条app的模型")
    @PostMapping(value = "/api/app/model/add")
    public JsonResult createAppModel(@RequestBody @Validated @ApiParam(name = "App模型创建对象", value = "传入json格式", required = true) AppModelCreateBean param) {
        if (appModelPriceService.addAppModelPrice(param)) {

            return JsonResult.success();
        }
        return JsonResult.error(Code.CREATE_ERROR);
    }


    @ApiOperation(value = "修改一条app的模型")
    @PostMapping(value = "/api/app/model/update")
    public JsonResult updateAppModel(@RequestBody @Validated @ApiParam(name = "App模型修改对象", value = "传入json格式", required = true) AppModelUpdateBean param) {
        if (appModelPriceService.updateAppModelPrice(param)) {
            return JsonResult.success();
        }
        return JsonResult.error(Code.UPDATE_ERROR);
    }

}

