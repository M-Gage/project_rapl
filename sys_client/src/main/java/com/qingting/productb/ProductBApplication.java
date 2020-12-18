package com.qingting.productb;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@Slf4j
@EnableCaching
@SpringBootApplication
@ComponentScan("com.qingting")
@MapperScan("com.qingting.middleware.dao")
public class ProductBApplication {

    public static void main(String[] args) {
        log.info("》》》》》开始加载项目........《《《《《");
        SpringApplication.run(ProductBApplication.class, args);
        log.info("》》》》》项目加载完成！！！！《《《《《");
    }

}
