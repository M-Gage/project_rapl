package com.qingting.middleware.bean;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class UserInfoBean {
    /**
     * 发送的APPKey
     */
    private String appKey;

    /**
     * 用户id
     */
    private String zhimiUsId;

    /**
     * 用户名称
     */
    private String zhimiUsName;

    /**
     * 手机号
     */
    private String zhimiUsMobile;

    /**
     * 身份证
     */
    private String zhimiUsIdcard;

    /**
     * 用户渠道号
     */
    private String zhimiUsQds;

    /**
     * 创建时间
     */
    private String zhimiCdate;

    /**
     * 修改时间
     */
    private String zhimiUdate;

}
