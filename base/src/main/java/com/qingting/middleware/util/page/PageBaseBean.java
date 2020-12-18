package com.qingting.middleware.util.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Gage
 * @describe 分页请求类
 * @date 2019-09-11 15:34
 */
@ApiModel(value = "分页请求类")
@Data
public class PageBaseBean {
    /**
     * 当前页码
     */
    @ApiModelProperty(value = "当前页码")
    @NotNull(message = "当前页码不能为空")
    @Min(value = 0,message = "页码必须大于0")
    private int pageNum;

    /**
     * 每页数量
     */
    @ApiModelProperty(value = "每页数量")
    @NotNull(message = "每页数量不能为空")
    @Min(value = 0,message = "每页数量必须大于0")
    private int pageSize;

}
