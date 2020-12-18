package com.qingting.middleware.common.bean;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "report")
public class ReportProperties {
    //appkey
    private String appkey;
    //rtype
    private String rtype;
    //平台通行码
    private String platformCode;
    //A系统请求路径
    private String productA;
    //A黑名单数据查询
    private String blackListQuery;
    //浅橙
    private String qianCheng;
    //白骑士黑名单
    private String baiQiShi;
    //孚临接口
    private String fuLin;
    //致诚阿福
    private String zhiChengAFu;
    //信用探针
    private String creditTanzhen;
    //新源多头
    private String xinYuan;
    //孚临接口生产秘钥（用来解密数据的）
    private String fuLinKey;
    //百融—借贷意向
    private String baiRong;
    //致诚阿福_风险评估
    private String aFufxpg;
}
