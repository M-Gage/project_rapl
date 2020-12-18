package com.qingting.middleware.bean.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value = "借贷对象")
public class LoanBean extends BaseLoanBean {

    @ApiModelProperty(value = "用户名称",required = true)
    @NotBlank(message = "用户名称不能为空")
    private String userName;

    @ApiModelProperty(value = "用户身份证号",required = true)
    @NotBlank(message = "用户身份证号不能为空")
    private String userIdcard;

    @ApiModelProperty(value = "用户手机号",required = true)
    @NotBlank(message = "用户手机号不能为空")
    private String userMobile;

    @ApiModelProperty(value = "借款金额",required = true)
    @NotBlank(message = "借款金额不能为空")
    @Min(value = 0,message = "不能为负数")
    private String indMoneyJie;

}
