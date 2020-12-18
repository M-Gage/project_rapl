package com.qingting.middleware.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class
ModelUserBean {


    @ApiModelProperty(value = "身份证",required = true)
//    @NotBlank(message = "身份证不能为空")
    private String idCard;

    @ApiModelProperty(value = "手机号",required = true)
//    @NotBlank(message = "手机号不能为空")
    private String mobile;

    @ApiModelProperty(value = "用户名",required = true)
//    @NotBlank(message = "用户名不能为空")
    private String name;

    @ApiModelProperty(value = "学信状态",required = true)
//    @NotBlank(message = "学信状态不能为空")
    private String CHSIStatus;

    @ApiModelProperty(value = "学信状态(因子）",required = true)
//    @NotBlank(message = "学信状态不能为空")
    private String bus_006;

    public String getBus_006() {
        return bus_006;
    }

    public String getCHSIStatus() {
        return CHSIStatus;
    }

    public void setCHSIStatus(String CHSIStatus) {
        this.CHSIStatus = CHSIStatus;
        this.bus_006 = CHSIStatus;
    }
}
