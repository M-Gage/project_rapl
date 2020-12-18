package com.qingting.productb.controller.model;

import com.qingting.middleware.annotation.BlackList;
import com.qingting.middleware.annotation.Login;
import com.qingting.middleware.annotation.RSA;
import com.qingting.middleware.baseController.BaseController;
import com.qingting.middleware.bean.request.ModelParamBean;
import com.qingting.middleware.bean.ModelResultBean;
import com.qingting.middleware.service.*;
import com.qingting.middleware.service.impl.ModelDirector;
import com.qingting.middleware.util.JsonResult;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"自有模型风控接口"})
@Slf4j
@RestController
public class ModelController extends BaseController {

    @Autowired
    private ModelService modelService;

    @Autowired
    private AppService appService;



    @ApiOperation(value = "风控接口")
    @Login
    @ResponseBody
    @RSA(isAround = true)
    @BlackList
    @PostMapping(value = {"/api/model/aladdin","/api/model/fangRiTu"})
    public JsonResult<ModelResultBean> modelRisk(@RequestBody @Validated @ApiParam(name = "模型参数", value = "部分加密，可压缩", required = true) ModelParamBean param) {
        ModelResultBean result = new ModelDirector(modelService,appService)
                .create(param,getAppId())
                .build();
        return JsonResult.success(result);
    }
}
