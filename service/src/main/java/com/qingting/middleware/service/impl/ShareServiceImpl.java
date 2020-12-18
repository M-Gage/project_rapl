package com.qingting.middleware.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.qingting.middleware.bean.UserLoanRecordBean;
import com.qingting.middleware.dao.UserApplyRecordMapper;
import com.qingting.middleware.dao.UserLoanRecordMapper;
import com.qingting.middleware.entity.UserApplyRecord;
import com.qingting.middleware.service.ShareService;
import com.qingting.middleware.util.DateUtil;
import com.qingting.middleware.util.zcaf.EchoCallOrgClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class ShareServiceImpl implements ShareService {

    @Resource
    private UserApplyRecordMapper userApplyRecordMapper;
    @Resource
    private UserLoanRecordMapper userLoanRecordMapper;

    /**
     * 风险评估共享版(致诚阿福) 平台访问机构
     * @param pd
     * @return
     * @throws Exception
     */
    public JSONObject fxpgShare(Map<String, Object> pd, String sign) throws Exception{
        JSONObject resultJson = new JSONObject();//
        //成功:true; 失败:false
        Boolean success=true;
        String strData = "";
        try {
            List<Map<String, Object>> loanRecords = new ArrayList<>();

            Map<String, Object> loanByUser = fxpgShareByUser(pd);
            if(loanByUser != null && loanByUser.size() > 0){
                //如果审核通过则根据订单查询
                if("ACCEPT".equals(loanByUser.get("approvalStatus"))){
                    pd.put("app_id",loanByUser.get("app_id"));
                    loanRecords = fxpgShareByIndent(pd);
                    //如果审核通过但没有订单,则是审核通过暂未下款,属于审核中
                    if(loanRecords == null || loanRecords.size() < 1){
                        loanByUser.put("approvalStatus","IN_PROGRESS");
                        loanByUser.remove("app_id");
                        loanRecords.add(loanByUser);
                    }
                    //如果订单状态为4,则是待放款,属于审核中
                    if(loanRecords != null && loanRecords.size() > 0 && "4".equals(loanRecords.get(0).get("loanStatus")+"")){
                        loanByUser.put("approvalStatus","IN_PROGRESS");
                        loanByUser.remove("app_id");
                        loanByUser.put("loanAmount", loanRecords.get(0).get("loanAmount"));
                        loanRecords.remove(0);
                        loanRecords.add(0, loanByUser);
                    }
                }else{
                    loanByUser.remove("app_id");
                    loanRecords.add(loanByUser);
                }
            }

            List<Map> riskResults=new ArrayList<>();
            JSONObject dataJson=new JSONObject(); //响应结果
            dataJson.put("loanRecords",loanRecords);
            dataJson.put("riskResults",riskResults);
            strData= EchoCallOrgClient.encrypt(dataJson.toJSONString(),sign);
            //System.out.println("data数据明文"+dataJson.toJSONString());
            //System.out.println("data数据加密"+strData);
        }catch (Exception e){
            success=false;
            resultJson.put("code","53000");
            e.printStackTrace();
        }

        resultJson.put("success",success);
        resultJson.put("data",strData);
        return resultJson;
    }

    /**
     * 风险评估共享版(致诚阿福) 平台访问机构 查询申请通过信息
     * @param pd
     * @return
     * @throws Exception
     */
    public Map<String, Object> fxpgShareByUser(Map<String, Object> pd) throws Exception {
        UserApplyRecord user = userApplyRecordMapper.fxpgShareByUser(pd);
        if(user == null){
            return null;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("app_id",user.getAppId());
        String status = String.valueOf(user.getStatus());
        if("0".equals(status)){
            map.put("approvalStatus","IN_PROGRESS");//审批结果码
        }else if("1".equals(status)){
            map.put("approvalStatus","ACCEPT");
        }else if("2".equals(status)){
            map.put("approvalStatus","REJECT");
        }else {
            map.put("approvalStatus","REJECT");
        }
        //如果申请时间一个月后还未审核,改状态为客户放弃
        if(new Date().after(DateUtil.addMonth(user.getCreateTime(),1))){
            map.put("approvalStatus","CUSTOMER_REJECT");
        }
        map.put("idNo",user.getIdCard());//被查询借款人身份证号
        map.put("loanAmount","1500.00");//借款金额
        map.put("loanDate", DateUtil.format(user.getCreateTime(), DateUtil.FORMAT_SIMPLE_NUMBER));//借款时间
        //map.put("loanStatus","");//还款状态码
        map.put("loanType","CREDIT");//借款类型码
        map.put("name",user.getName());//被查询借款人姓名
        //map.put("overdueAmount","");//逾期金额
        //map.put("overdueM3","");//历史逾期 M3+次数
        //map.put("overdueM6","");//历史逾期 M6+次数
        //map.put("overdueStatus","");//逾期情况
        //map.put("overdueTotal",null);//历史逾期总次数
        map.put("periods",1);//期数
        return map;
    }

    /**
     * 风险评估共享版(致诚阿福) 平台访问机构 查询订单状态
     * @param pd
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> fxpgShareByIndent(Map<String, Object> pd) throws Exception {
        List<UserLoanRecordBean> userLoanRecordBeans = userLoanRecordMapper.fxpgShareByIndent(pd);
        List<Map<String, Object>> list = new ArrayList<>();
        for (UserLoanRecordBean userLoanRecordBean :userLoanRecordBeans) {
            Map<String, Object> map = new HashMap<>();
            map.put("approvalStatus","ACCEPT");//审批结果码
            map.put("idNo",userLoanRecordBean.getUserIdcard());//被查询借款人身份证号
            map.put("loanAmount",userLoanRecordBean.getIndMoneyJie());//借款金额
            map.put("loanDate", DateUtil.format(userLoanRecordBean.getCreateTime(), DateUtil.FORMAT_SIMPLE_NUMBER));//借款时间

            Integer indIfPay = userLoanRecordBean.getIndIfPay();
            if(0 == indIfPay || 2 == indIfPay){//还款状态码
                map.put("loanStatus","NORMAL");
            }else if(1 == indIfPay){
                map.put("loanStatus","COMPLETED");
            }else if(3 == indIfPay){
                map.put("loanStatus","OVERDUE");
                map.put("overdueTotal",1);//历史逾期总次数
            }else{
                map.put("loanStatus",indIfPay);//其他状态,后面转化
            }

            map.put("loanType","CREDIT");//借款类型码
            map.put("name",userLoanRecordBean.getUserName());//被查询借款人姓名

            if(3 != indIfPay){//逾期情况
                //map.put("overdueStatus","");
            }else {//逾期情况
                Date time = userLoanRecordBean.getUpdateTime();
                //逾期天数
                Integer overdueDays = DateUtil.differentDay(new Date(), time) + 1;
                if(overdueDays <= 30 ){
                    map.put("overdueStatus","M1");
                }else if(overdueDays > 30 && overdueDays < 61){
                    map.put("overdueStatus","M2");
                }else if(overdueDays > 60 && overdueDays < 91){
                    map.put("overdueStatus","M3");
                }else if(overdueDays > 90){
                    map.put("overdueStatus","M3+");
                }else{
                    map.put("overdueStatus","");
                }
                String overdueAmount = "";
                if(overdueDays < 11){
                    overdueAmount = String.format("%.2f", Double.parseDouble(userLoanRecordBean.getIndMoneyJie()) * (1.00+ (0.01 * overdueDays)));
                }else{
                    overdueAmount = String.format("%.2f", Double.parseDouble(userLoanRecordBean.getIndMoneyJie()) * 1.1);
                }
                userLoanRecordBean.getIndMoneyJie();
                map.put("overdueAmount",overdueAmount);//逾期金额
            }

            //map.put("overdueM3","");//历史逾期 M3+次数
            //map.put("overdueM6","");//历史逾期 M6+次数

            map.put("periods",userLoanRecordBean.getPeriods());//期数

            list.add(map);
        }
        return list;
    }

    @Override
    public List<String> fxpgSharePushByA(List<UserApplyRecord> list) throws Exception {
        JSONObject resultJson = new JSONObject();//
        List<String> idCardList = new ArrayList<>();//需要返回哪些数据已成功插入
        int i = 0;//成功条数
        try {
            for (UserApplyRecord uar : list) {
                uar.setAppId("9999");//A推送数据默认9999
                uar.setMobile("");//手机号
                uar.setTaskId("");//任务id
                uar.setApplyTime(uar.getCreateTime());//申请时间
                uar.setZcafFlag(true);//afu调用标志
                uar.setStatus("1");//审核状态
                if(Integer.valueOf(uar.getIdCard().substring(6, 10)) > 2001){//截取身份证年份,小于18岁拒绝
                    uar.setStatus("2");//审核状态
                }
                //uar.setCreateTime(new Date());//创建时间
                uar.setUpdateTime(uar.getCreateTime());//创建时间
                idCardList.add(uar.getIdCard());
            }
            i = userApplyRecordMapper.insertFxpgSharePushByA(list);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(i > 0){
            return idCardList;
        }
        return null;
    }

}
