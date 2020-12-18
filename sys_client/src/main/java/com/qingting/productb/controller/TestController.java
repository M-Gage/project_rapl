package com.qingting.productb.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qingting.middleware.annotation.BlackList;
import com.qingting.middleware.baseController.BaseController;
import com.qingting.middleware.util.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.Map;

@Slf4j
@Api(tags = {"测试接口"})
@RestController
@RequestMapping("/api")
public class TestController extends BaseController {
    private static final CloseableHttpClient closeableHttpClient = HttpClients.createDefault();

    @Resource
    private RedisUtil redisUtil;

    /*
    * B系统后台测试B系统本身是否可以调通
    * */
    @ApiOperation("测试在线接口")
    @GetMapping(value = "/test/line")
    public String line() {
        return "success";
    }

    @RequestMapping(value = "/encryption/test", method = RequestMethod.GET)
//    @RSA(isAround = true)
    public String encryption(String param, String param2)
    {
        redisUtil.set(param,param2);
        return param ;
    }

    @RequestMapping(value = "/model/test", method = RequestMethod.POST)
    @BlackList
    public String model(@RequestBody String param) {
        return param ;
    }



    protected JSONObject getJsonParam(HttpServletRequest request) {
        String params = request.getParameter("params");
        if(StringUtils.isNotBlank(params)) {
            return JSON.parseObject(params);
        }
        // 参数Map
        Map properties = request.getParameterMap();
        // 返回值Map
        JSONObject returnObject = new JSONObject();
        Iterator entries = properties.entrySet().iterator();
        Map.Entry entry;
        String name;
        String value = "";
        while (entries.hasNext()) {
            entry = (Map.Entry) entries.next();
            name = (String) entry.getKey();
            Object valueObj = entry.getValue();
            if(null == valueObj){
                value = "";
            }else if(valueObj instanceof String[]){
                String[] values = (String[])valueObj;
                for(int i=0;i<values.length;i++){
                    value = values[i] + ",";
                }
                value = value.substring(0, value.length()-1);
            }else{
                value = valueObj.toString();
            }
            returnObject.put(name, value);
        }
        return returnObject;
    }
}
