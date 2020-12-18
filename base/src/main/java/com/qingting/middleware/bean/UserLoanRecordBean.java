package com.qingting.middleware.bean;

import com.qingting.middleware.entity.UserLoanRecord;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "com.qingting.middleware.bean.UserLoanRecordBean")
@Data
public class UserLoanRecordBean extends UserLoanRecord {

    /**
     * 订单期数
     */
    @ApiModelProperty(value = "订单期数")
    private Integer periods;

}
