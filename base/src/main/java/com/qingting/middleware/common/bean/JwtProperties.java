package com.qingting.middleware.common.bean;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    //头部标识字段
    private String header ;
    //密码
    private String secret ;
    //过期时间
    private Long expiration;
}
