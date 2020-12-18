package com.qingting.productb.controller.model;

import com.alibaba.fastjson.JSONObject;
import com.qingting.middleware.annotation.Login;
import com.qingting.middleware.annotation.RSA;
import com.qingting.middleware.baseController.BaseController;
import com.qingting.middleware.bean.*;
import com.qingting.middleware.bean.request.*;
import com.qingting.middleware.entity.UserApplyRecord;
import com.qingting.middleware.entity.UserLoanRecord;
import com.qingting.middleware.enums.Code;
import com.qingting.middleware.exception.MyException;
import com.qingting.middleware.service.ShareService;
import com.qingting.middleware.service.TogetherDebtService;
import com.qingting.middleware.service.UserRequestModelRecordService;
import com.qingting.middleware.util.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Api(tags = {"共债接口"})
@Slf4j
@RestController
@RequestMapping("/api")
@ResponseBody
public class TogetherDebtController extends BaseController {
    @Resource
    private TogetherDebtService togetherDebtService;
    @Resource
    private UserRequestModelRecordService userRequestModelRecordService;
    @Resource
    private ShareService shareService;

    @ApiOperation(value = "核验用户")
    @GetMapping(value = "/verify/user")
    public JsonResult verifyUser(@Validated({Normal.class}) @ApiParam(name = "用户三要素", required = true) UserBean param) {
        UserLoanRecord ulr = togetherDebtService.verifyUser(param);
        VerifyUserBean vu = new VerifyUserBean();
        vu.setPhone(param.getUserMobile());
        if (ulr == null) {
            vu.setMsg("无数据");
            vu.setResult(2);
            return JsonResult.success(vu);
        } else {
            if (param.getUserIdcard().equals(ulr.getUserIdcard()) && param.getUserName().equals(ulr.getUserName())) {
                vu.setMsg("核验一致");
                vu.setResult(0);
                return JsonResult.success(vu);
            }
            vu.setMsg("核验不一致");
            vu.setResult(1);
            return JsonResult.success(vu);

        }
    }


    @ApiOperation(value = "放款信息传输接口")
    @RSA(isAfter = true)
    @Login
    @RequestMapping(value = "/save/loan/info", method = RequestMethod.POST)
    public JsonResult saveLoanInfo(@Validated @RequestBody @ApiParam(name = "用户放款对象", required = true) LoanBean param) {
        if (togetherDebtService.saveLoanInfo(param)) {
            return JsonResult.success();
        } else {
            return JsonResult.normal(Code.ENTER_ERROR);

        }
    }

    @ApiOperation(value = "结清信息传输接口")
    @Login
    @RSA(isAfter = true)
    @RequestMapping(value = "/save/repayment/info", method = RequestMethod.POST)
    public JsonResult saveRepaymentRecordInfo(@RequestBody @Validated @ApiParam(name = "用户还款对象", required = true) RepaymentBean param) {
        if (togetherDebtService.saveRepaymentRecordInfo(param)) {
            return JsonResult.success();
        } else {
            return JsonResult.error(Code.ENTER_ERROR);

        }
    }

    @ApiOperation(value = "修改订单状态(批量)")
    @Login
    @RSA(isAfter = true)
    @PostMapping(value = "/update/pay/status")
    public JsonResult updatePayStatus(@RequestBody @Validated @ApiParam(name = "订单号和产品编号", required = true) UpdateOrderStatusBean param) {
        if (togetherDebtService.updatePayStatus(param)) {
            return JsonResult.success();
        } else {
            return JsonResult.error(Code.ENTER_ERROR);

        }
    }

    @ApiOperation(value = "获取用户共债情况")
    @Login
    @RSA(isAfter = true)
    @GetMapping(value = "/get/together/debt/info")
    public JsonResult<TogetherDebtResultBean> getUserTogetherDebtInfo(@Validated @ApiParam(name = "用户三要素和产品id", required = true) TogetherDebtBean param) {
        TogetherDebtResultBean result = togetherDebtService.getUserTogetherDebtInfo(param);
        if (result != null) {
            return JsonResult.success(result);
        } else {
            throw new MyException(Code.RETURN_NULL, "获取不到产品信息");
        }
    }


    @ApiOperation(value = "获取用户放还记录")
    @Login
    @RSA(isAfter = true)
    @GetMapping(value = "/get/together/debt/history")
    public JsonResult<TogetherDebtHistoryBean> getUserTogetherDebtHistory(@Validated @ApiParam(name = "用户三要素", required = true) UserBean param) {
        TogetherDebtHistoryBean result = togetherDebtService.getUserTogetherDebtHistory(param);
        if (result != null) {
            return JsonResult.success(result);
        } else {
            throw new MyException(Code.RETURN_NULL, "获取不到产品信息");
        }
    }

    @ApiOperation(value = "过去一天用户借款申请记录")
    @Login
    @RSA(isAfter = true)
    @GetMapping(value = "/get/past/day/debt/record")
    public JsonResult< List<UserLoanRecord>> getPastDayDebtRecord(@Validated @ApiParam(name = "用户三要素", required = true) PastDayDebtBean param) {
        List<UserLoanRecord> result = togetherDebtService.getPastDayDebtRecord(param);
        if (result != null && result.size() > 0) {
            return JsonResult.success(result);
        } else {
            throw new MyException(Code.RETURN_NULL, "获取不到借款申请信息");
        }
    }

    @ApiOperation(value = "删除借款记录")
    @Login
    @RSA(isAfter = true)
    @PostMapping(value = "/del/together/debt/loan")
    public JsonResult deleteUserTogetherDebtLoanInfo(@RequestBody @Validated @ApiParam(name = "订单id，状态", required = true) TogetherDebtDeleteBean param) {
        if (togetherDebtService.deleteUserLoanRecord(param)) {
            return JsonResult.success("删除成功");
        } else {
            return JsonResult.customMessage(Code.DELETE_ERROR, "：输出参数为[" + param.toString() + "]", "删除失败");
        }
    }

    @ApiOperation(value = "删除还款记录")
    @Login
    @RSA(isAfter = true)
    @PostMapping(value = "/del/together/debt/repayment")
    public JsonResult deleteUserTogetherDebtRepaymentInfo(@RequestBody @Validated @ApiParam(name = "订单id，还款时间，状态", required = true) TogetherDebtDeleteBean param) {

        if (param.getIndRepayTime() == null) {
            throw new MyException(Code.PARAM_ERROR, "还款时间不能为空");
        }

        if (togetherDebtService.deleteUserRepaymentRecord(param)) {
            return JsonResult.success("删除成功");
        } else {
            return JsonResult.customMessage(Code.DELETE_ERROR, "：输出参数为[" + param.toString() + "]", "删除失败");
        }
    }

    @ApiOperation(value = "修改用户申请状态")
    @Login
    @PostMapping(value = "/upd/user/apply/status")
    public JsonResult updateUserApplyStatus(@RequestBody @Validated @ApiParam(name = "订单id，还款时间，状态", required = true) UserApplyRecordBean param) {

        if (param.getUserMobile().size() == 0) {
            throw new MyException(Code.PARAM_ERROR, "手机号不能为空");
        }
        if (userRequestModelRecordService.updateUserApplyStatus(param, getAppId()) > 0) {
            return JsonResult.success("修改成功");
        } else {
            return JsonResult.customMessage(Code.UPDATE_ERROR, "：输出参数为[" + param.toString() + "]", "修改失败");
        }
    }

    @ApiOperation(value = "A系统afu风险评估共享数据推送接口")
    @Login
    @RequestMapping(value = "/save/user/apply/info", method = RequestMethod.POST)
    public JsonResult saveAfuPushData(@Validated @RequestBody @ApiParam(name = "用户申请记录", required = true) List<UserApplyRecord> list) {
        if(list == null || list.isEmpty()){
            return JsonResult.customMessage(Code.PARAM_ERROR, "参数为空", list);
        }
        List<String> idCardList;
        try {
            idCardList = shareService.fxpgSharePushByA(list);
        }catch (Exception e){
            e.printStackTrace();
            return JsonResult.failed();
        }
        return JsonResult.success(idCardList);
    }



}
