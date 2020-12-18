package com.qingting.middleware.configurer;

import com.qingting.middleware.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class WabAppConfigurer implements WebMvcConfigurer {

    @Resource
    private LoginInterceptor loginInterceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new UriInterceptor()).addPathPatterns("/**")
//                //过滤swagger
//                .excludePathPatterns("/swagger-resources/**")
//                .excludePathPatterns("/webjars/**")
//                .excludePathPatterns("/error")
//                .excludePathPatterns("/csrf")
//                .excludePathPatterns("/null/**")
//                .excludePathPatterns("/")
//                .excludePathPatterns("/swagger-ui.html");
        registry.addInterceptor(loginInterceptor).addPathPatterns("/api/**");
    }
    /**
     * Swagger2和图片资源配置
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //添加swagger资源
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/*").addResourceLocations("classpath:/META-INF/resources/webjars/");
        //添加图片资源（loadPath）
//        registry.addResourceHandler("/images/**").addResourceLocations(loadPath);
    }
}
