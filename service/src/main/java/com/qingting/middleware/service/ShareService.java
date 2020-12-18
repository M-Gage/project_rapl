package com.qingting.middleware.service;

import com.alibaba.fastjson.JSONObject;
import com.qingting.middleware.entity.UserApplyRecord;

import java.util.List;
import java.util.Map;

public interface ShareService {

    /**
     * 风险评估共享版(致诚阿福) 平台访问机构
     * @param pd
     * @return
     * @throws Exception
     */
    JSONObject fxpgShare(Map<String, Object> pd, String sign) throws Exception;

    /**
     * 风险评估共享版(致诚阿福) 平台访问机构 用户申请情况查询
     * @param pd
     * @return
     * @throws Exception
     */
    Map<String, Object> fxpgShareByUser(Map<String, Object> pd) throws Exception;

    /**
     * 风险评估共享版(致诚阿福) 平台访问机构 订单情况查询
     * @param pd
     * @return
     * @throws Exception
     */
    List<Map<String, Object>> fxpgShareByIndent(Map<String, Object> pd) throws Exception;

    /**
     * 风险评估共享版(致诚阿福) 平台访问机构
     * @param list
     * @return
     * @throws Exception
     */
    List<String> fxpgSharePushByA(List<UserApplyRecord> list) throws Exception;

}
