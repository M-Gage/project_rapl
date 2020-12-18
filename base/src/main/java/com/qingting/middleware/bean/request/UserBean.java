package com.qingting.middleware.bean.request;

import com.qingting.middleware.bean.Normal;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value = "用户基础对象")
public class UserBean {


    @ApiModelProperty(value = "用户名称")
    @NotBlank(groups = {Normal.class},message = "用户名称不能为空")
    private String userName;

    @ApiModelProperty(value = "用户身份证号")
    @NotBlank(groups = {Normal.class},message = "身份证号不能为空")
    private String userIdcard;

    @ApiModelProperty(value = "用户手机号")
    @NotBlank(message = "用户手机号不能为空")
    private String userMobile;

}
