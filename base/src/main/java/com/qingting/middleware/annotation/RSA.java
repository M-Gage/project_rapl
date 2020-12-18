package com.qingting.middleware.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)

public @interface RSA {

    //在方法前进行解密
    boolean isBefore() default false;

    //方法前解密，方法后对返回参数加密
    boolean isAround() default false;

    //方法后对返回参数加密
    boolean isAfter() default false;

}
