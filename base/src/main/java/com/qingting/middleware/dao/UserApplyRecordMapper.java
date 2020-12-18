package com.qingting.middleware.dao;

import com.qingting.middleware.entity.UserApplyRecord;
import org.apache.ibatis.annotations.Mapper;import org.apache.ibatis.annotations.Param;import java.util.List;import java.util.Map;

/**
 * @author Gage
 * @describe ${Description}
 * @date 2019-12-10 14:03
 */
@Mapper
public interface UserApplyRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserApplyRecord record);

    int insertSelective(UserApplyRecord record);

    int insertFxpgSharePushByA(List<UserApplyRecord> list);

    UserApplyRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserApplyRecord record);

    int updateByPrimaryKey(UserApplyRecord record);

    int updateBatchApplyRecord(@Param("userMobile") List<String> userMobile, @Param("status") String status, @Param("appId") String appId);

    UserApplyRecord fxpgShareByUser(Map<String, Object> pd);

    int updateStatusByAppIdAndIdCard(UserApplyRecord uar);
}