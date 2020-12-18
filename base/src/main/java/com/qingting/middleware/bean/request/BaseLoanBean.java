package com.qingting.middleware.bean.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value = "借贷基础对象")
public class BaseLoanBean {
    @ApiModelProperty(value = "订单id",required = true)
    @NotBlank(message = "订单id不能为空")
    private String indId;

}
