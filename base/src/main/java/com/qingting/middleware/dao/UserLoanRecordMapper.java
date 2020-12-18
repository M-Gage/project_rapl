package com.qingting.middleware.dao;

import com.qingting.middleware.bean.TogetherDebtHistoryBean;
import com.qingting.middleware.bean.UserLoanRecordBean;
import com.qingting.middleware.entity.UserLoanRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Gage
 * @describe ${Description}
 * @date 2019-09-20 11:39
 */
@Mapper
public interface UserLoanRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserLoanRecord record);

    int insertSelective(UserLoanRecord record);

    UserLoanRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserLoanRecord record);

    int updateByPrimaryKey(UserLoanRecord record);

    List<UserLoanRecord> selectAllByUserInfo(@Param("userIdcard") String userIdcard, @Param("userMobile") String userMobile, @Param("userName") String userName, @Param("pIds") String[] pIds);

    List<String> selectDebtByUserInfo(@Param("userIdcard") String userIdcard, @Param("userMobile") String userMobile, @Param("userName") String userName);

    int updateStatusByIndId(UserLoanRecord ul);

    TogetherDebtHistoryBean selectHistoryByUserInfo(@Param("userIdcard") String userIdcard, @Param("userMobile") String userMobile, @Param("userName") String userName, @Param("allApp") String allApp);

    UserLoanRecord selectByMobile(String userMobile);

    int updateBatchOrderStatus(@Param("appId") String appId, @Param("indId") List<String> indId, @Param("indIfPay") String indIfPay, @Param("indFangTime") Date indFangTime);

    List<UserLoanRecordBean> fxpgShareByIndent(Map<String, Object> pd);

    List<UserLoanRecord> getPastDayDebtRecord(@Param("userIdcard") String userIdcard, @Param("userMobile") String userMobile, @Param("userName") String userName, @Param("app") String app);
}