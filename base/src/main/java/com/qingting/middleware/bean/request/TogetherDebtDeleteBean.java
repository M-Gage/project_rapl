package com.qingting.middleware.bean.request;

import com.qingting.middleware.bean.request.BaseLoanBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * @author Gage
 * @describe 共债删除实体
 * @date 2019-09-04 16:06
 */
@Data
@ApiModel(value = "共债删除实体")
public class TogetherDebtDeleteBean extends BaseLoanBean {


    @ApiModelProperty(value = "还款时间")
    private Date indRepayTime;

    @ApiModelProperty(value = "状态：0 正常 ；-1 删除 ；默认 0",required = true)
    @NotBlank(message = "状态不能为空")
    private String status;
}
