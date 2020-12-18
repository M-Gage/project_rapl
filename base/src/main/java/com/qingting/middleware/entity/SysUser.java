package com.qingting.middleware.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
*@author Gage
*@describe ${Description}
*@date 2019-09-10 15:20 
*/
@ApiModel(value="com.qingting.middleware.entity.SysUser")
@Data
public class SysUser {
    /**
    * 主键
    */
    @ApiModelProperty(value="主键")
    private String id;

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
    * 密码
    */
    @ApiModelProperty(value="密码")
    private String password;

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

    /**
    * 备注
    */
    @ApiModelProperty(value="备注")
    private String remark;
}