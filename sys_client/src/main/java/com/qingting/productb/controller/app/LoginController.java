package com.qingting.productb.controller.app;

import com.qingting.middleware.util.JsonResult;
import com.qingting.middleware.util.JwtUtil;
import com.qingting.middleware.util.RedisUtil;
import com.qingting.middleware.bean.request.LoginBean;
import com.qingting.middleware.enums.Code;
import com.qingting.middleware.service.LoginService;
import io.swagger.annotations.*;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
@Api(tags = {"B系统登录接口"})
@RestController

public class  LoginController {


    @Resource
    private JwtUtil jwtUtil;
    @Resource
    private LoginService loginService;
    @Resource
    private RedisUtil redisUtil;

    @ApiOperation(value = "登录获取token")
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public JsonResult login(@RequestBody @Validated @ApiParam(name = "用户登录对象", value = "传入json格式", required = true) LoginBean param) {
        String appKey = param.getAppKey();
        String appId = loginService.appLogin(appKey,param.getKey());
            if (StringUtils.isEmpty(appId)) {
                return JsonResult.normal(Code.ACCOUNT_ERROR);
            }
            //将appId放入token中
            Map<String, Object> tokenParams = new HashMap<>();
            tokenParams.put("appId", appId);
            tokenParams.put("appKey", appKey);
            //生成token
            String token = jwtUtil.createToken(tokenParams, appKey);
            //token保存到redis中(12小时)
            redisUtil.set(appKey,token,12*3600L);
            return JsonResult.success(token);
    }

}
