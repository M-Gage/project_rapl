package com.qingting.productb.controller.share;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.qingting.middleware.baseController.BaseController;
import com.qingting.middleware.service.ShareService;
import com.qingting.middleware.util.zcaf.EchoCallOrgClient;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;

@RestController
public class ShareController extends BaseController {

    @Resource
    private ShareService shareService;

    /**
     * 风险评估共享版(致诚阿福) 平台访问机构
     * @Description
     * @param params
     */
    @ResponseBody
    @RequestMapping(value = "/share/fxpgShare")
    public JSONObject fxpgShare(@RequestParam("params") String params){
        String sign = "ca57366d27d6872f";//rc4秘钥
        JSONObject resultJson = new JSONObject();//
        Boolean success=true; //成功:true; 失败:false
        try {
            if(StringUtils.isEmpty(params)){
                resultJson.put("success",false);
                resultJson.put("code","40004");//缺少 params 参数
                return resultJson;
            }
            String strParam = "";
            try {
                strParam= EchoCallOrgClient.decrypt(params,sign);//解密报文
            }catch (JSONException e){
                e.printStackTrace();
                resultJson.put("success",false);
                resultJson.put("code","40006");//params 格式不对
                return resultJson;
            }
            JSONObject paramObject = JSON.parseObject(strParam);
            if(StringUtils.isEmpty(paramObject.getString("id_no"))){
                resultJson.put("success",false);
                resultJson.put("code","43016");//被查身份证不合法
                return resultJson;
            }
            if(StringUtils.isEmpty(paramObject.getString("name"))){
                resultJson.put("success",false);
                resultJson.put("code","43017");     //被查姓名不合法
                return resultJson;
            }
            //解密后参数
            Map<String, Object> pd = new HashMap<>(paramObject);

			/*Map loanRecord=new HashMap();
			loanRecord.put("approvalStatus","");//审批结果码
			loanRecord.put("idNo","");//被查询借款人身份证号
			loanRecord.put("loanAmount","");//借款金额，通过的，取合同金额;未通过或审核中的， 取申请金额
			loanRecord.put("loanDate","");//借款时间，通过的，取合同时间;未通过或审核中的， 取申请时间。 作为数据提供方，平台可识别的时间格式有 2 种: YYYYMM或者YYYYMMDD
			loanRecord.put("loanStatus","");//还款状态码，指一笔借款合同当前的状态;若历史出 现过逾期，当前还款正常，则还款状态取“正常”
			loanRecord.put("loanType","");//借款类型码，指一笔借款所属的类型
			loanRecord.put("name","");//被查询借款人姓名
			loanRecord.put("overdueAmount","");//逾期金额，指一笔借款中，达到还款期限，尚未偿还 的总金额
			loanRecord.put("overdueM3","");//历史逾期 M3+次数(不含 M3，包括 M6 及以上)
			loanRecord.put("overdueM6","");//历史逾期 M6+次数(不含 M6)
			loanRecord.put("overdueStatus","");//逾期情况，指一笔借款当前逾期的程度
			loanRecord.put("overdueTotal","");//历史逾期总次数
			loanRecord.put("periods","");//期数, , 通过的，取合同期数;未通过或审核中的，取 申请期数，范围 1~120
			Map riskResult=new HashMap();//风险项记录
			riskResult.put("riskDetail","");//风险明细，合作机构提供的借款人的风险类别
			riskResult.put("riskItemType","");//命中项码，如证件号码(当前命中项仅包括证件号码)
			riskResult.put("riskItemValue","");//命中内容，身份证号的具体值
			riskResult.put("riskTime","");//风险最近时间，指风险记录最近一次发现的时间 平台可识别的时间格式有 3 种: YYYY YYYYMM YYYYMMDD
			List<Map> loanRecords=new ArrayList<>();
			loanRecords.add(loanRecord);
			List<Map> riskResults=new ArrayList<>();
			riskResults.add(riskResult);*/


            //System.out.println("响应结果"+resultJson.toJSONString());

            return shareService.fxpgShare(pd, sign);
        }catch (JSONException e){
            e.printStackTrace();
            resultJson.put("success",false);
            resultJson.put("code","40006");//params 格式不对
            return resultJson;
        }catch (Exception e){
            e.printStackTrace();
        }
        return resultJson;
    }

}
