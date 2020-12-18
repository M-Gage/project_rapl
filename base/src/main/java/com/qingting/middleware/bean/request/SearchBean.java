package com.qingting.middleware.bean.request;

import com.qingting.middleware.util.page.PageBaseBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Gage
 * @describe 搜索基础类
 * @date 2019-09-11 15:09
 */
@ApiModel(value = "搜索基础类")
@Data
public class SearchBean extends PageBaseBean {

    @ApiModelProperty(value = "条件")
    private String condition;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "开始时间")
    private String startTime;

    @ApiModelProperty(value = "结束时间")
    private String endTime;
}
