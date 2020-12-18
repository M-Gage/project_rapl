package com.qingting.middleware.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@ApiModel(value = "风控模型返回对象")
@Data
public class ModelResultBean {

    @ApiModelProperty(value = "用户身份证号", required = true)
    private String idCard;

    @ApiModelProperty(value = "手机号", required = true)
    private String mobile;

    @ApiModelProperty(value = "风控分", required = true)
    private BigDecimal score;

    @ApiModelProperty(value = "多个风控分", required = true)
    private Map<String,BigDecimal> scores;

    @ApiModelProperty(value = "用户历史操作轨迹", required = true)
    private HistoryApplyBean historyApplyBean;

    @ApiModelProperty(value = "任务id", required = true)
    private String taskId;
}
