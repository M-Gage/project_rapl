package com.qingting.middleware.util.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Gage
 * @describe 分页结果类
 * @date 2019-09-11 15:37
 */
@ApiModel(value = "分页结果返回类")
@Data
public class PageResultBean<T> {
    /**
     * 当前页码
     */
    @ApiModelProperty(value = "当前页码")
    private int pageNum;
    /**
     * 每页数量
     */
    @ApiModelProperty(value = "每页数量")
    private int pageSize;
    /**
     * 记录总数
     */
    @ApiModelProperty(value = "记录总数")
    private long totalSize;
    /**
     * 页码总数
     */
    @ApiModelProperty(value = "页码总数")
    private int totalPages;

    /**
     * 是否有前一页
     */
    @ApiModelProperty(value = "是否有前一页")
    private boolean hasPreviousPage;
    /**
     * 是否有后一页
     */
    @ApiModelProperty(value = "是否有后一页")
    private boolean hasNextPage;

    /**
     * 最后一页页码
     */
    @ApiModelProperty(value = "最后一页页码")
    private int lastPage;

    /**
     * 数据模型
     */
    @ApiModelProperty(value = "数据模型")
    private List<T> list;

}
