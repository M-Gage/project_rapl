package com.qingting.middleware.bean.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Gage
 * @describe 系统管理员登录对象
 * @date 2019-09-10 15:40
 */
@ApiModel(value = "登录对象")
@Data
public class SysLoginBean {

    @ApiModelProperty(value = "用户名(唯一）", required = true)
    @NotBlank(message = "用户名不能为空")
    private String username;

    @ApiModelProperty(value = "密码（用rsa加密）", required = true)
    @NotBlank(message = "密码不能为空")
    private String password;

}
