package com.qingting.middleware.util;

import com.qingting.middleware.common.bean.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * jwt 工具类
 */
@Component
public class JwtUtil {

    @Resource
    private JwtProperties jwtProperties;

    /**
     * 创建token
     *
     * @return token字符串
     */
    public String createToken(Map<String, Object> claims, String subject) {
        Date createDate = new Date();
        Date expirationDate = new Date(createDate.getTime() + jwtProperties.getExpiration());

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(createDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, jwtProperties.getSecret())
                .compact();
    }

    /**
     * 获取payload
     *
     * @param token token
     * @return token保存的对象
     */
    public Claims getTokenPayload(String token) {
        return Jwts.parser()
                .setSigningKey(jwtProperties.getSecret())
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 获取参数
     *
     * @param token 令牌
     * @param s     令牌中存储的对象字符串
     * @param clazz 对象类型
     * @param <T>   泛型
     * @return 对象
     */
    public <T> T getValue(String token, String s, Class<T> clazz) {
        return getTokenPayload(token).get(s, clazz);
    }
}
