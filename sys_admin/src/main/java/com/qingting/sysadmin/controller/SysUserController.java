package com.qingting.sysadmin.controller;

import com.qingting.middleware.bean.sys.SysLoginBean;
import com.qingting.middleware.enums.Code;
import com.qingting.middleware.service.SysUserService;
import com.qingting.middleware.util.JsonResult;
import com.qingting.middleware.util.JwtUtil;
import com.qingting.middleware.util.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Gage
 * @describe 系统管理员控制器
 * @date 2019-09-03 15:18
 */
@Api(tags = {"系统管理员接口"})
@Slf4j
@RestController
@RequestMapping("/api")
@ResponseBody
public class SysUserController {

    @Resource
    private JwtUtil jwtUtil;
    @Resource
    private SysUserService sysUserService;
    @Resource
    private RedisUtil redisUtil;

    @ApiOperation(value = "登录获取token")
    @RequestMapping(value = "sys/login", method = RequestMethod.POST)
    public JsonResult login(@RequestBody @Validated @ApiParam(name = "系统用户登录对象", value = "传入json格式", required = true) SysLoginBean param) {
        String username = param.getUsername();
        String userId = sysUserService.userLogin(username, param.getPassword());
        if (StringUtils.isEmpty(userId)) {
            return JsonResult.normal(Code.ACCOUNT_ERROR);
        }
        //将appId放入token中
        Map<String, Object> tokenParams = new HashMap<>();
        tokenParams.put("userId", userId);
        tokenParams.put("username", username);
        //生成token
        String token = jwtUtil.createToken(tokenParams, userId);
        //token保存到redis中(12小时)
        redisUtil.set(userId, token, 2 * 3600L);
        return JsonResult.success(token);
    }
}
