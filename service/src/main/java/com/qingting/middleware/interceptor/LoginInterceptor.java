package com.qingting.middleware.interceptor;

import com.qingting.middleware.annotation.Login;
import com.qingting.middleware.bean.LoginInfo;
import com.qingting.middleware.enums.Code;
import com.qingting.middleware.exception.MyException;
import com.qingting.middleware.util.JwtUtil;
import com.qingting.middleware.util.RedisUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Slf4j
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Resource
    private JwtUtil jwtUtil;
    @Resource
    private RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            log.info("resource handler=====" + request.getRequestURI());
            return false;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        //获取方法上的登录注解
        Login login = method.getAnnotation(Login.class);
        //是否为空
        if (login != null) {
            //获取token
            String token = request.getHeader("Authorization");

            if (StringUtils.isEmpty(token)) {
                throw new MyException(Code.TOKEN_NULL);
            }
            Claims tokenParams;
            try {
                //从token中拿出保存的信息
                 tokenParams = jwtUtil.getTokenPayload(token);
            } catch (Exception e) {//不是系统颁发的token
                throw new MyException(Code.TOKEN_FAIL);
            }
            String appId = tokenParams.get("appId", String.class);
            String appKey = tokenParams.get("appKey", String.class);
            //获取appKey
            if (StringUtils.isEmpty(appKey)) {
                return false;
            }
            //校验token是否过期及其合法性，是否当前保存的token
            Object tokenRedis = redisUtil.get(appKey);
            if (tokenRedis == null) {
                throw new MyException(Code.TOKEN_EXPIRED);
            }
            if (!token.equals(tokenRedis)) {
                throw new MyException(Code.TOKEN_ERROR);
            }
            //获取appId
            if (StringUtils.isEmpty(appId)) {
                return false;
            }
            // 获取属性
            LoginInfo app = (LoginInfo) redisUtil.get(appId);
            if (StringUtils.isEmpty(app)) {
                throw new MyException(Code.TOKEN_ERROR);
            }
            // 放入request中
            request.setAttribute("appId", app.getAppId());
            request.setAttribute("privateKey", app.getPrivateKey());
            request.setAttribute("app", app);
            //重置过期时间
            redisUtil.set(appKey,tokenRedis,12*3600L);
        }
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }
}
