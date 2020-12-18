package com.qingting.middleware.dao;

import com.qingting.middleware.bean.HistoryApplyBean;import com.qingting.middleware.entity.UserRequestModelRecord;
import org.apache.ibatis.annotations.Mapper;import org.apache.ibatis.annotations.Param;

/**
 * @author Gage
 * @describe ${Description}
 * @date 2019-12-09 16:07
 */
@Mapper
public interface UserRequestModelRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserRequestModelRecord record);

    int insertSelective(UserRequestModelRecord record);

    UserRequestModelRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserRequestModelRecord record);

    int updateByPrimaryKey(UserRequestModelRecord record);

    HistoryApplyBean selectUserIdCardHistoryApply(@Param("idCard") String idCard);

    HistoryApplyBean selectUserMobileHistoryApply(String mobile);
}