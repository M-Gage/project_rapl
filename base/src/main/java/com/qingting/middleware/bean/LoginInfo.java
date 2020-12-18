package com.qingting.middleware.bean;

import lombok.Data;

@Data
public class LoginInfo {

    /**
     * appId
     */
    private String appId;

    /**
     * appName
     */
    private String appName;

    /**
     * app唯一标识
     */
    private String appKey;

    /**
     * app公钥
     */
    private String publicKey;

    /**
     * app私钥
     */
    private String privateKey;

    /**
     * A系统报文获取开关 0 开启 -1 关闭
     */
    private Integer reportSwitch;

    /**
     * A系统报文获取开关 0 开启 -1 关闭
     */
    private Integer status;

}
