package com.qingting.sysadmin.controller;

import com.alibaba.fastjson.JSONObject;
import com.qingting.middleware.annotation.BlackList;
import com.qingting.middleware.util.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

@Slf4j
@Api(tags = {"测试接口"})
@RestController
@RequestMapping("/api")
public class TestController {
    private static final CloseableHttpClient closeableHttpClient = HttpClients.createDefault();

    @Resource
    private RedisUtil redisUtil;

    @ApiOperation("测试接口")
    @RequestMapping(value = "/test", method = RequestMethod.GET)
//    @Login
    public String save() throws IOException {
        JSONObject jsonObj = new JSONObject();
        String url = "http://bee.topting-fintech.com//app/blackList/v1/blackList4";
        jsonObj.put("idCardNo", "420106198710111249");
        jsonObj.put("mobile","13476062874");
        jsonObj.put("name", "叶璇");
        jsonObj.put("idCard",jsonObj.getString("idCardNo"));
        jsonObj.put("appkey","94k053e0e8474813b6r2f31fs15fc22f7");
        jsonObj.put("rtype","json");
        StringEntity stringEntity = new StringEntity(jsonObj.toString(),"UTF-8");
        stringEntity.setContentEncoding("UTF-8");
        //stringEntity.setContentType("application/json");
        //stringEntity.setContentType("multipart/form-data");
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Connection","close");
        httpPost.setHeader("Content-Type","application/json");
        httpPost.setEntity(stringEntity);
        HttpResponse resp = closeableHttpClient.execute(httpPost);
        HttpEntity he = resp.getEntity();
        return EntityUtils.toString(he, "UTF-8");




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

}
