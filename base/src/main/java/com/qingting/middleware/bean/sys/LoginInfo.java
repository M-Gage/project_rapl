package com.qingting.middleware.bean.sys;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LoginInfo {

    /**
     * 主键
     */
    @ApiModelProperty(value="主键")
    private String userId;

    /**
     * 手机号
     */
    @ApiModelProperty(value="手机号")
    private String phone;

    /**
     * 用户名，即登录名
     */
    @ApiModelProperty(value="用户名，即登录名")
    private String username;

    /**
     * 姓名
     */
    @ApiModelProperty(value="姓名")
    private String name;

    /**
     * 最后登录时间
     */
    @ApiModelProperty(value="最后登录时间")
    private String lastLogin;

    /**
     * 状态
     */
    @ApiModelProperty(value="状态")
    private String status;

}
