package com.qingting.middleware.bean.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Gage
 * @describe 共债信息查询实体
 * @date 2020-02-28 14:06
 */
@Data
@ApiModel(value = "共债信息查询实体")
public class TogetherDebtBean extends UserBean{

    @ApiModelProperty(value = "需要过滤的平台（逗号分隔）")
    private String platforms;
}
