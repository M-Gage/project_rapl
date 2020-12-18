package com.qingting.middleware.service;

import com.qingting.middleware.bean.*;
import com.qingting.middleware.bean.request.*;
import com.qingting.middleware.entity.UserLoanRecord;

import java.util.List;

public interface TogetherDebtService {
    boolean saveLoanInfo(LoanBean param);

    boolean saveRepaymentRecordInfo(RepaymentBean param);

    boolean updatePayStatus(UpdateOrderStatusBean param);

    TogetherDebtResultBean getUserTogetherDebtInfo(TogetherDebtBean param);

    boolean deleteUserLoanRecord(TogetherDebtDeleteBean param);

    boolean deleteUserRepaymentRecord(TogetherDebtDeleteBean param);

    TogetherDebtHistoryBean getUserTogetherDebtHistory(UserBean param);

    UserLoanRecord verifyUser(UserBean param);

    List<UserLoanRecord> getPastDayDebtRecord(PastDayDebtBean param);
}
