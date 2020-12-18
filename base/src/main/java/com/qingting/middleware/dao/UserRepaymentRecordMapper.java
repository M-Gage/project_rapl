package com.qingting.middleware.dao;

import com.qingting.middleware.entity.UserRepaymentRecord;
import org.apache.ibatis.annotations.Mapper;

/**
*@author Gage
*@describe ${Description}
*@date 2019-09-04 15:33 
*/
@Mapper
public interface UserRepaymentRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserRepaymentRecord record);

    int insertSelective(UserRepaymentRecord record);

    UserRepaymentRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserRepaymentRecord record);

    int updateByPrimaryKey(UserRepaymentRecord record);

    int updateStatusByIndIdAndRepayTime(UserRepaymentRecord ur);
}