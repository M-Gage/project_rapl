package com.qingting.middleware.bean.request;

import com.qingting.middleware.bean.request.BaseLoanBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.util.Date;

@Data
@ApiModel(value = "还款对象")
public class RepaymentBean extends BaseLoanBean {

    @ApiModelProperty(value = "结清时间",required = true)
    @NotNull(message = "结清时间不能为空")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Past(message="还款日期必须是一个过去的日期")
    private Date indRepayTime;


    @ApiModelProperty(value = "结清金额",required = true)
    @NotBlank(message = "结清金额不能为空")
    @Min(value = 0,message = "结清金额不能为负数")
    private String indRepayMoney;

    @ApiModelProperty(value = "是否结清：0 未结清 ；1 已结清；2 续期；3 逾期；默认 0",required = true)
    @NotBlank(message = "是否结清不能为空")
    @Pattern(regexp = "[0-3]",message = "0 未结清 ；1 已结清,请填入0-3的整数")
    private String indIfPay;

}
