package com.qingting.middleware.dao;

import com.qingting.middleware.entity.AppRequestBacklistRecord;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AppRequestBacklistRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AppRequestBacklistRecord record);

    int insertSelective(AppRequestBacklistRecord record);

    AppRequestBacklistRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AppRequestBacklistRecord record);

    int updateByPrimaryKey(AppRequestBacklistRecord record);
}