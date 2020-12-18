package com.qingting.middleware.common.filter;

import com.qingting.middleware.common.filter.BodyRequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;

@Slf4j
@Order(2)
@Component
@WebFilter(urlPatterns = "/*", filterName = "UriFilter")
public class UriFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String servletPath = request.getServletPath();
        BodyRequestWrapper bodyRequestWrapper = null;

        //排除显示swagger路径
        if (!servletPath.contains("swagger")
                && !servletPath.contains("/csrf")
                && !servletPath.contains(".php")
                && !servletPath.contains("/proxy")
                && !servletPath.equals("/")
                && !servletPath.equals("/favicon.ico")
                && !servletPath.contains("/v2/api-docs")) {

            log.debug("请求地址===>>path[{}]", servletPath);

            //获取包装后的request
            bodyRequestWrapper = new BodyRequestWrapper(request).copyRequest();
            Enumeration enu = request.getParameterNames();
            //get请求
            if (request.getMethod().equals("GET")) {
                log.debug("requestParameter：==>");
                while (enu.hasMoreElements()) {
                    String paraName = (String) enu.nextElement();
                    //打印日志
                    log.debug(" {}：{}", paraName, request.getParameter(paraName));
                }

            } else {//post请求
                try {
                    ServletInputStream is = bodyRequestWrapper.getInputStream();
                    //读取复制的输入流
                    String sb = StreamUtils.copyToString(is, StandardCharsets.UTF_8);
                    //数据过大处理
                    if (request.getContentLength() > 10240) {
                        log.debug("字符串过大(大于10K)，不输出日志");
                    }else {
                        //打印入参日志
                        log.debug("requestBody:==>{}", sb);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if (bodyRequestWrapper == null) {
            filterChain.doFilter(request,
                    servletResponse);
        } else {
            //说的就是这个地方
            filterChain.doFilter(bodyRequestWrapper,
                    servletResponse);
        }
    }
}
