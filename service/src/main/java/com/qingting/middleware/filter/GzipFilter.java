package com.qingting.middleware.filter;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@Component
@WebFilter(urlPatterns = "/*", filterName = "GzipFilter")
@Order(1)
public class GzipFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(new GzipRequestWrapper((HttpServletRequest) request),
                response);
    }

}
