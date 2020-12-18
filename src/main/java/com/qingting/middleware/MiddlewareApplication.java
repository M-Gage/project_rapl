package com.qingting.middleware;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@Slf4j
@EnableCaching
@SpringBootApplication
public class MiddlewareApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        log.debug("》》》》》开始加载项目........《《《《《");
        SpringApplication.run(MiddlewareApplication.class, args);
        log.debug("》》》》》项目加载完成！！！！《《《《《");

    }


}


