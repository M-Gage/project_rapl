package com.qingting.middleware.bean.request;

import com.qingting.middleware.bean.Normal;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Gage
 * @describe 过去一天借款申请请求类
 * @date 2019-11-27 10:29
 */
@Data
@ApiModel(value = "过去一天借贷申请请求对象")
public class PastDayDebtBean {
    @ApiModelProperty(value = "用户名称")
    private String userName;

    @ApiModelProperty(value = "用户身份证号")
    @NotBlank(message = "身份证号不能为空")
    private String userIdcard;

    @ApiModelProperty(value = "用户手机号")
    private String userMobile;
}

