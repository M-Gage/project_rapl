package com.qingting.middleware.bean.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@ApiModel(value = "登录对象")
@Data
public class LoginBean {

    @ApiModelProperty(value = "appKey(app唯一标识）",required = true)
    @NotBlank(message = "appKey不能为空")
    private String appKey;

    @ApiModelProperty(value = "密码（用rsa加密）",required = true)
    @NotBlank(message = "密码不能为空")
    private String  key;
}
