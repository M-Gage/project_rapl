package com.qingting.sysadmin;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@Slf4j
@EnableCaching
@SpringBootApplication
@ComponentScan("com.qingting")
@MapperScan("com.qingting.middleware.dao")
public class SysAdminApplication {

    public static void main(String[] args) {
        log.info("》》》》》开始加载项目........《《《《《");
        SpringApplication.run(SysAdminApplication.class, args);
        log.info("》》》》》项目加载完成！！！！《《《《《");
    }

}
