package com.qingting.middleware.util.page;

import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author Gage
 * @describe 分页查询工具类
 * @date 2019-09-11 15:56
 */
public class PageUtils {

        /**
         * 将分页信息封装到统一的接口
         * @param pi 分页信息
         * @return PageResultBean
         */
        public static <T> PageResultBean<T> getPageResult(PageInfo<T> pi) {
            PageResultBean<T> pr = new PageResultBean<T>();
            pr.setPageNum(pi.getPageNum());
            pr.setPageSize(pi.getPageSize());
            pr.setTotalSize(pi.getTotal());
            pr.setTotalPages(pi.getPages());
            pr.setHasNextPage(pi.isHasNextPage());
            pr.setHasPreviousPage(pi.isHasPreviousPage());
            pr.setLastPage(pi.getNavigateLastPage());
            pr.setList(pi.getList());
            return pr;
        }
    }


