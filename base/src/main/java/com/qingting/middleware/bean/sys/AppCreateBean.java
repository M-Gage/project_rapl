package com.qingting.middleware.bean.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@ApiModel(value = "App创建对象")
@Data
public class AppCreateBean {
    /**
     * 产品名称
     */
    @ApiModelProperty(value = "产品名称",required = true)
    @NotBlank(message = "appName不能为空")
    private String appName;

    /**
     * app唯一标识
     */
    @ApiModelProperty(value = "app唯一标识",required = true)
    @NotBlank(message = "app唯一标识不能为空")
    private String appKey;

    /**
     * 密码
     */
    @ApiModelProperty(value = "app登录密码）",required = true)
    @NotBlank(message = "password不能为空")
    private String password;


}
