package com.qingting.middleware.bean.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.Date;
import java.util.List;

/**
 * @author Gage
 * @describe 修改订单状态
 * @date 2019-11-14 17:33
 */
@Data
@ApiModel(value = "修改订单状态")
public class UpdateOrderStatusBean {
    @ApiModelProperty(value = "订单id",required = true)
    @NotNull(message = "订单id不能为空")
    private List<String> indId;

    @ApiModelProperty(value = "是否结清：0：未结清,1：已结清，2：续期中，3：逾期，4：待放款 默认 4",required = true)
    @NotBlank(message = "订单砖头不能为空")
    private String indIfPay;

    @ApiModelProperty(value = "放款时间",required = true)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Past(message="放款日期必须是一个过去的日期")
    private Date indFangTime;

}
