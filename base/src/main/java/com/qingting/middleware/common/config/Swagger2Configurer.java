package com.qingting.middleware.common.config;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiOperation;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * swagger 配置
 */
//@Profile({"dev","test"})
@ConditionalOnProperty(prefix = "swagger", value = {"enable"}, havingValue = "true")//配置是否启用swagger（yml）
@Configuration
@EnableSwagger2
public class Swagger2Configurer {

    private Predicate<RequestHandler> swaggerSelector = RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class)::apply;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())//接口文档信息
                .useDefaultResponseMessages(false)//是否默认启动
                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.qingting"))//扫描的范围加载
                .apis(swaggerSelector::test)//凡是注解的都加载
                .paths(PathSelectors.any())//路径
                .build()
                .securitySchemes(securitySchemes())//安全计划
                .securityContexts(securityContexts());//配置上下文


    }

    /* 为全局设置安全认证方式 */
    //swagger 安全计划
    private List<ApiKey> securitySchemes() {
        //用于描述我们的API如何保护
        return Lists.newArrayList(new ApiKey("Authorization", "Authorization", "header"));
    }

    //swagger api上下文
    private List<SecurityContext> securityContexts() {
        List<SecurityContext> scs = new ArrayList<>();
        scs.add(SecurityContext.builder()
                //定义认证区域
                .securityReferences(Lists.newArrayList(new SecurityReference("Authorization",
                        new AuthorizationScope[]{new AuthorizationScope("global", "accessEverything")})))
                .forPaths(PathSelectors.any())
                .build());
        return scs;
    }


    //api整体信息
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("青亭B系统 决策引擎交换平台 RESTFul APIs")
                .description("本系统由mgz研发")
                .contact(new Contact("MiaoGuozhu", "", "summitpointmgz@gmail.com"))
                .version("0.0.1")
                .build();
    }


}

