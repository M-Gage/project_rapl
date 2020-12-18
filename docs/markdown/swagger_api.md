# 青亭B系统 决策引擎交换平台 RESTFul APIs


<a name="overview"></a>
## 概览
本系统由mgz研发


### 版本信息
*版本* : 0.0.1


### 联系方式
*名字* : MiaoGuozhu  
*邮箱* : summitpointmgz@gmail.com


### URI scheme
*域名* : localhost:8086  
*基础路径* : /


### 标签

* B系统登录接口 : Login Controller
* 共债接口 : Together Debt Controller
* 指谜风控接口 : You Dun Controller
* 测试接口 : Test Controller
* 自有模型风控接口 : Model Controller




<a name="paths"></a>
## 资源

<a name="aa00622447a1e1fc9f614a2399c74c49"></a>
### B系统登录接口
Login Controller


<a name="loginusingpost"></a>
#### 登录获取token
```
POST /login
```


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Body**|**用户登录对象**  <br>*必填*|传入json格式|[登录对象](#df6eb66472516c228df1d62f76890578)|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|[数据返回类](#413a64c225e2c4f80e5646f2d8ac6cf9)|


##### 消耗

* `application/json`


##### 生成

* `*/*`


##### 安全

|类型|名称|作用域|
|---|---|---|
|**apiKey**|**[Authorization](#authorization)**|global|


##### HTTP请求示例

###### 请求 path
```
/login
```


###### 请求 body
```
json :
{
  "appKey" : "string",
  "key" : "string"
}
```


##### HTTP响应示例

###### 响应 200
```
json :
{
  "code" : 0,
  "data" : "object",
  "message" : "string"
}
```


<a name="c1246911493ea16e19e38fd2e7fe0a5a"></a>
### 共债接口
Together Debt Controller


<a name="deleteusertogetherdebtloaninfousingpost"></a>
#### 删除借款记录
```
POST /api/del/together/debt/loan
```


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Body**|**订单id，状态**  <br>*必填*|传入加密json格式|[TogetherDebtDeleteBean](#togetherdebtdeletebean)|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|[数据返回类](#413a64c225e2c4f80e5646f2d8ac6cf9)|


##### 消耗

* `application/json`


##### 生成

* `*/*`


##### 安全

|类型|名称|作用域|
|---|---|---|
|**apiKey**|**[Authorization](#authorization)**|global|


##### HTTP请求示例

###### 请求 path
```
/api/del/together/debt/loan
```


###### 请求 body
```
json :
{
  "indId" : "string",
  "indRepayTime" : "string",
  "status" : "string"
}
```


##### HTTP响应示例

###### 响应 200
```
json :
{
  "code" : 0,
  "data" : "object",
  "message" : "string"
}
```


<a name="deleteusertogetherdebtrepaymentinfousingpost"></a>
#### 删除还款记录
```
POST /api/del/together/debt/repayment
```


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Body**|**订单id，还款时间，状态**  <br>*必填*|传入加密json格式|[TogetherDebtDeleteBean](#togetherdebtdeletebean)|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|[数据返回类](#413a64c225e2c4f80e5646f2d8ac6cf9)|


##### 消耗

* `application/json`


##### 生成

* `*/*`


##### 安全

|类型|名称|作用域|
|---|---|---|
|**apiKey**|**[Authorization](#authorization)**|global|


##### HTTP请求示例

###### 请求 path
```
/api/del/together/debt/repayment
```


###### 请求 body
```
json :
{
  "indId" : "string",
  "indRepayTime" : "string",
  "status" : "string"
}
```


##### HTTP响应示例

###### 响应 200
```
json :
{
  "code" : 0,
  "data" : "object",
  "message" : "string"
}
```


<a name="getusertogetherdebthistoryusingget"></a>
#### 获取用户放还记录
```
GET /api/get/together/debt/history
```


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Query**|**userIdcard**  <br>*可选*|用户身份证号|string|
|**Query**|**userMobile**  <br>*可选*|用户手机号|string|
|**Query**|**userName**  <br>*可选*|用户名称|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|[数据返回类«用户放还款历史记录»](#6ced76898f01a3f2a14d3230aa93c9be)|


##### 生成

* `*/*`


##### 安全

|类型|名称|作用域|
|---|---|---|
|**apiKey**|**[Authorization](#authorization)**|global|


##### HTTP请求示例

###### 请求 path
```
/api/get/together/debt/history
```


###### 请求 query
```
json :
{
  "userIdcard" : "string",
  "userMobile" : "string",
  "userName" : "string"
}
```


##### HTTP响应示例

###### 响应 200
```
json :
{
  "code" : 0,
  "data" : {
    "appCount" : 0,
    "lastIndFangTime" : "string",
    "lastRepaymentTime" : "string",
    "orderCount" : 0,
    "overdueCount" : 0,
    "repaymentCount" : 0,
    "userIdcard" : "string",
    "userMobile" : "string",
    "userName" : "string"
  },
  "message" : "string"
}
```


<a name="getusertogetherdebtinfousingget"></a>
#### 获取用户共债情况
```
GET /api/get/together/debt/info
```


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Query**|**userIdcard**  <br>*可选*|用户身份证号|string|
|**Query**|**userMobile**  <br>*可选*|用户手机号|string|
|**Query**|**userName**  <br>*可选*|用户名称|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|[数据返回类«TogetherDebtResultBean»](#12d078b65ca2a05900760970d1415ccb)|


##### 生成

* `*/*`


##### 安全

|类型|名称|作用域|
|---|---|---|
|**apiKey**|**[Authorization](#authorization)**|global|


##### HTTP请求示例

###### 请求 path
```
/api/get/together/debt/info
```


###### 请求 query
```
json :
{
  "userIdcard" : "string",
  "userMobile" : "string",
  "userName" : "string"
}
```


##### HTTP响应示例

###### 响应 200
```
json :
{
  "code" : 0,
  "data" : {
    "monitorCount" : 0,
    "totalCount" : 0
  },
  "message" : "string"
}
```


<a name="saveloaninfousingpost"></a>
#### 放款信息传输接口
```
POST /api/save/loan/info
```


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Body**|**用户放款对象**  <br>*必填*|传入加密json格式|[LoanBean](#loanbean)|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|[数据返回类](#413a64c225e2c4f80e5646f2d8ac6cf9)|


##### 消耗

* `application/json`


##### 生成

* `*/*`


##### 安全

|类型|名称|作用域|
|---|---|---|
|**apiKey**|**[Authorization](#authorization)**|global|


##### HTTP请求示例

###### 请求 path
```
/api/save/loan/info
```


###### 请求 body
```
json :
{
  "indFangTime" : "string",
  "indId" : "string",
  "indMoneyJie" : "string",
  "userIdcard" : "string",
  "userMobile" : "string",
  "userName" : "string"
}
```


##### HTTP响应示例

###### 响应 200
```
json :
{
  "code" : 0,
  "data" : "object",
  "message" : "string"
}
```


<a name="saverepaymentrecordinfousingpost"></a>
#### 结清信息传输接口
```
POST /api/save/repayment/info
```


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Body**|**用户还款对象**  <br>*必填*|传入加密json格式|[RepaymentBean](#repaymentbean)|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|[数据返回类](#413a64c225e2c4f80e5646f2d8ac6cf9)|


##### 消耗

* `application/json`


##### 生成

* `*/*`


##### 安全

|类型|名称|作用域|
|---|---|---|
|**apiKey**|**[Authorization](#authorization)**|global|


##### HTTP请求示例

###### 请求 path
```
/api/save/repayment/info
```


###### 请求 body
```
json :
{
  "indId" : "string",
  "indIfPay" : "string",
  "indRepayMoney" : "string",
  "indRepayTime" : "string"
}
```


##### HTTP响应示例

###### 响应 200
```
json :
{
  "code" : 0,
  "data" : "object",
  "message" : "string"
}
```


<a name="updatepaystatususingpost"></a>
#### 修改结清状态为未结清状态
```
POST /api/update/pay/status
```


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Body**|**订单号和产品编号**  <br>*必填*|传入加密json格式|[BaseLoanBean](#baseloanbean)|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|[数据返回类](#413a64c225e2c4f80e5646f2d8ac6cf9)|


##### 消耗

* `application/json`


##### 生成

* `*/*`


##### 安全

|类型|名称|作用域|
|---|---|---|
|**apiKey**|**[Authorization](#authorization)**|global|


##### HTTP请求示例

###### 请求 path
```
/api/update/pay/status
```


###### 请求 body
```
json :
{
  "indId" : "string"
}
```


##### HTTP响应示例

###### 响应 200
```
json :
{
  "code" : 0,
  "data" : "object",
  "message" : "string"
}
```


<a name="b9766fe9710e032a1162779a71e1cf5e"></a>
### 指谜风控接口
You Dun Controller


<a name="saveusingpost"></a>
#### 有盾风控对接
```
POST /api/youdun/risk
```


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Body**|**param**  <br>*必填*|有盾所需参数（压缩）|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|[数据返回类](#413a64c225e2c4f80e5646f2d8ac6cf9)|


##### 消耗

* `application/json`


##### 生成

* `*/*`


##### 安全

|类型|名称|作用域|
|---|---|---|
|**apiKey**|**[Authorization](#authorization)**|global|


##### HTTP请求示例

###### 请求 path
```
/api/youdun/risk
```


###### 请求 body
```
json :
{ }
```


##### HTTP响应示例

###### 响应 200
```
json :
{
  "code" : 0,
  "data" : "object",
  "message" : "string"
}
```


<a name="requestzhimiriskusingpost"></a>
#### 指谜风控对接
```
POST /risk/gzip/
```


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Body**|**param**  <br>*必填*|纸迷所需参数（压缩）|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|[数据返回类](#413a64c225e2c4f80e5646f2d8ac6cf9)|


##### 消耗

* `application/json`


##### 生成

* `*/*`


##### 安全

|类型|名称|作用域|
|---|---|---|
|**apiKey**|**[Authorization](#authorization)**|global|


##### HTTP请求示例

###### 请求 path
```
/risk/gzip/
```


###### 请求 body
```
json :
{ }
```


##### HTTP响应示例

###### 响应 200
```
json :
{
  "code" : 0,
  "data" : "object",
  "message" : "string"
}
```


<a name="cba1afa8756152014d0034b3b9dc688b"></a>
### 测试接口
Test Controller


<a name="saveusingget"></a>
#### 测试接口
```
GET /api/test
```


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|string|


##### 生成

* `*/*`


##### 安全

|类型|名称|作用域|
|---|---|---|
|**apiKey**|**[Authorization](#authorization)**|global|


##### HTTP请求示例

###### 请求 path
```
/api/test
```


##### HTTP响应示例

###### 响应 200
```
json :
"string"
```


<a name="6efcf1d566fde770e2219498a7c52220"></a>
### 自有模型风控接口
Model Controller


<a name="modelv1plususingpost"></a>
#### V1风控对接
```
POST /api/model/jueMuJiao/plus
```


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Body**|**模型参数**  <br>*必填*|部分加密，可压缩|[ModelParamBean](#modelparambean)|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|[数据返回类«风控模型返回对象»](#56edc2aa9785d52e6684fc29bfc3b8df)|


##### 消耗

* `application/json`


##### 生成

* `*/*`


##### 安全

|类型|名称|作用域|
|---|---|---|
|**apiKey**|**[Authorization](#authorization)**|global|


##### HTTP请求示例

###### 请求 path
```
/api/model/jueMuJiao/plus
```


###### 请求 body
```
json :
{
  "baiQiShi" : "string",
  "channelId" : "string",
  "creditTanzhen" : "string",
  "fuLin" : "string",
  "moXie" : "string",
  "modelName" : "string",
  "modelNo" : "string",
  "qianCheng" : "string",
  "user" : "string",
  "youDun" : "string",
  "zhiChengAFu" : "string"
}
```


##### HTTP响应示例

###### 响应 200
```
json :
{
  "code" : 0,
  "data" : {
    "historyApplyBean" : {
      "idCard_12h_cnt" : 0,
      "idCard_14d_cnt" : 0,
      "idCard_1d_cnt" : 0,
      "idCard_1h_cnt" : 0,
      "idCard_30d_cnt" : 0,
      "idCard_3d_cnt" : 0,
      "idCard_3h_cnt" : 0,
      "idCard_60d_cnt" : 0,
      "idCard_7d_cnt" : 0,
      "mobile_12h_cnt" : 0,
      "mobile_14d_cnt" : 0,
      "mobile_1d_cnt" : 0,
      "mobile_1h_cnt" : 0,
      "mobile_30d_cnt" : 0,
      "mobile_3d_cnt" : 0,
      "mobile_3h_cnt" : 0,
      "mobile_60d_cnt" : 0,
      "mobile_7d_cnt" : 0
    },
    "idCard" : "string",
    "mobile" : "string",
    "score" : 0.0,
    "taskId" : "string"
  },
  "message" : "string"
}
```




<a name="definitions"></a>
## 定义

<a name="baseloanbean"></a>
### BaseLoanBean

|名称|说明|类型|
|---|---|---|
|**indId**  <br>*必填*|订单id  <br>**样例** : `"string"`|string|


<a name="loanbean"></a>
### LoanBean

|名称|说明|类型|
|---|---|---|
|**indFangTime**  <br>*必填*|放款时间  <br>**样例** : `"string"`|string (date-time)|
|**indId**  <br>*必填*|订单id  <br>**样例** : `"string"`|string|
|**indMoneyJie**  <br>*必填*|借款金额  <br>**样例** : `"string"`|string|
|**userIdcard**  <br>*必填*|用户身份证号  <br>**样例** : `"string"`|string|
|**userMobile**  <br>*必填*|用户手机号  <br>**样例** : `"string"`|string|
|**userName**  <br>*必填*|用户名称  <br>**样例** : `"string"`|string|


<a name="modelparambean"></a>
### ModelParamBean

|名称|说明|类型|
|---|---|---|
|**baiQiShi**  <br>*可选*|**样例** : `"string"`|string|
|**channelId**  <br>*可选*|渠道号  <br>**样例** : `"string"`|string|
|**creditTanzhen**  <br>*可选*|信用探针报文  <br>**样例** : `"string"`|string|
|**fuLin**  <br>*可选*|孚临报文  <br>**样例** : `"string"`|string|
|**moXie**  <br>*可选*|魔蝎报文  <br>**样例** : `"string"`|string|
|**modelName**  <br>*必填*|模型名称（加密）  <br>**样例** : `"string"`|string|
|**modelNo**  <br>*必填*|模型编号（加密）  <br>**样例** : `"string"`|string|
|**qianCheng**  <br>*可选*|**样例** : `"string"`|string|
|**user**  <br>*必填*|用户信息  <br>**样例** : `"string"`|string|
|**youDun**  <br>*可选*|有盾报文  <br>**样例** : `"string"`|string|
|**zhiChengAFu**  <br>*可选*|致诚阿福报文  <br>**样例** : `"string"`|string|


<a name="repaymentbean"></a>
### RepaymentBean

|名称|说明|类型|
|---|---|---|
|**indId**  <br>*必填*|订单id  <br>**样例** : `"string"`|string|
|**indIfPay**  <br>*必填*|是否结清：0 未结清 ；1 已结清 ；默认 0  <br>**样例** : `"string"`|string|
|**indRepayMoney**  <br>*必填*|结清金额  <br>**样例** : `"string"`|string|
|**indRepayTime**  <br>*必填*|结清时间  <br>**样例** : `"string"`|string (date-time)|


<a name="togetherdebtdeletebean"></a>
### TogetherDebtDeleteBean

|名称|说明|类型|
|---|---|---|
|**indId**  <br>*必填*|订单id  <br>**样例** : `"string"`|string|
|**indRepayTime**  <br>*可选*|还款时间  <br>**样例** : `"string"`|string (date-time)|
|**status**  <br>*必填*|状态：0 正常 ；-1 删除 ；默认 0  <br>**样例** : `"string"`|string|


<a name="togetherdebtresultbean"></a>
### TogetherDebtResultBean

|名称|说明|类型|
|---|---|---|
|**monitorCount**  <br>*可选*|**样例** : `0`|integer (int32)|
|**totalCount**  <br>*可选*|**样例** : `0`|integer (int32)|


<a name="413a64c225e2c4f80e5646f2d8ac6cf9"></a>
### 数据返回类

|名称|说明|类型|
|---|---|---|
|**code**  <br>*可选*|响应编码号  <br>**样例** : `0`|integer (int32)|
|**data**  <br>*可选*|返回数据  <br>**样例** : `"object"`|object|
|**message**  <br>*可选*|具体信息  <br>**样例** : `"string"`|string|


<a name="12d078b65ca2a05900760970d1415ccb"></a>
### 数据返回类«TogetherDebtResultBean»

|名称|说明|类型|
|---|---|---|
|**code**  <br>*可选*|响应编码号  <br>**样例** : `0`|integer (int32)|
|**data**  <br>*可选*|返回数据  <br>**样例** : `"[togetherdebtresultbean](#togetherdebtresultbean)"`|[TogetherDebtResultBean](#togetherdebtresultbean)|
|**message**  <br>*可选*|具体信息  <br>**样例** : `"string"`|string|


<a name="6ced76898f01a3f2a14d3230aa93c9be"></a>
### 数据返回类«用户放还款历史记录»

|名称|说明|类型|
|---|---|---|
|**code**  <br>*可选*|响应编码号  <br>**样例** : `0`|integer (int32)|
|**data**  <br>*可选*|返回数据  <br>**样例** : `"[854ef6aa493fec92a9fcfe91841af851](#854ef6aa493fec92a9fcfe91841af851)"`|[用户放还款历史记录](#854ef6aa493fec92a9fcfe91841af851)|
|**message**  <br>*可选*|具体信息  <br>**样例** : `"string"`|string|


<a name="56edc2aa9785d52e6684fc29bfc3b8df"></a>
### 数据返回类«风控模型返回对象»

|名称|说明|类型|
|---|---|---|
|**code**  <br>*可选*|响应编码号  <br>**样例** : `0`|integer (int32)|
|**data**  <br>*可选*|返回数据  <br>**样例** : `"[9bc016c1b51f63a45eb4a10055198733](#9bc016c1b51f63a45eb4a10055198733)"`|[风控模型返回对象](#9bc016c1b51f63a45eb4a10055198733)|
|**message**  <br>*可选*|具体信息  <br>**样例** : `"string"`|string|


<a name="c053c768da6e662ff11ff6585a9c9ed7"></a>
### 有盾入参数据

|名称|说明|类型|
|---|---|---|
|**bus_idcard**  <br>*可选*|身份证  <br>**样例** : `"string"`|string|
|**bus_mobile**  <br>*可选*|号码  <br>**样例** : `"string"`|string|
|**bus_name**  <br>*可选*|姓名  <br>**样例** : `"string"`|string|
|**channel_id**  <br>*可选*|渠道号  <br>**样例** : `"string"`|string|
|**mx_yysbg**  <br>*可选*|魔蝎运营商报告  <br>**样例** : `"string"`|string|
|**self_existent_type**  <br>*可选*|类型  <br>**样例** : `"string"`|string|


<a name="a4b9713b76fd417a8770f802053e9092"></a>
### 用户历史操作轨迹

|名称|说明|类型|
|---|---|---|
|**idCard_12h_cnt**  <br>*可选*|相同身份证过去12小时内调用次数  <br>**样例** : `0`|integer (int32)|
|**idCard_14d_cnt**  <br>*可选*|相同身份证过去14天内调用次数  <br>**样例** : `0`|integer (int32)|
|**idCard_1d_cnt**  <br>*可选*|相同身份证过去1天内调用次数  <br>**样例** : `0`|integer (int32)|
|**idCard_1h_cnt**  <br>*可选*|相同身份证过去1小时内调用次数  <br>**样例** : `0`|integer (int32)|
|**idCard_30d_cnt**  <br>*可选*|相同身份证过去30天内调用次数  <br>**样例** : `0`|integer (int32)|
|**idCard_3d_cnt**  <br>*可选*|相同身份证过去3天内调用次数  <br>**样例** : `0`|integer (int32)|
|**idCard_3h_cnt**  <br>*可选*|相同身份证过去3小时内调用次数  <br>**样例** : `0`|integer (int32)|
|**idCard_60d_cnt**  <br>*可选*|相同身份证过去60天内调用次数  <br>**样例** : `0`|integer (int32)|
|**idCard_7d_cnt**  <br>*可选*|相同身份证过去7天内调用次数  <br>**样例** : `0`|integer (int32)|
|**mobile_12h_cnt**  <br>*可选*|相同手机号过去12小时内调用次数  <br>**样例** : `0`|integer (int32)|
|**mobile_14d_cnt**  <br>*可选*|相同手机号过去14天内调用次数  <br>**样例** : `0`|integer (int32)|
|**mobile_1d_cnt**  <br>*可选*|相同手机号过去1天内调用次数  <br>**样例** : `0`|integer (int32)|
|**mobile_1h_cnt**  <br>*可选*|相同手机号过去1小时内调用次数  <br>**样例** : `0`|integer (int32)|
|**mobile_30d_cnt**  <br>*可选*|相同手机号过去30天内调用次数  <br>**样例** : `0`|integer (int32)|
|**mobile_3d_cnt**  <br>*可选*|相同手机号过去3天内调用次数  <br>**样例** : `0`|integer (int32)|
|**mobile_3h_cnt**  <br>*可选*|相同手机号过去3小时内调用次数  <br>**样例** : `0`|integer (int32)|
|**mobile_60d_cnt**  <br>*可选*|相同手机号过去60天内调用次数  <br>**样例** : `0`|integer (int32)|
|**mobile_7d_cnt**  <br>*可选*|相同手机号过去7天内调用次数  <br>**样例** : `0`|integer (int32)|


<a name="854ef6aa493fec92a9fcfe91841af851"></a>
### 用户放还款历史记录

|名称|说明|类型|
|---|---|---|
|**appCount**  <br>*可选*|平台数  <br>**样例** : `0`|integer (int32)|
|**lastIndFangTime**  <br>*可选*|最后一次借款时间  <br>**样例** : `"string"`|string|
|**lastRepaymentTime**  <br>*可选*|最后一次还款时间  <br>**样例** : `"string"`|string|
|**orderCount**  <br>*可选*|总订单数  <br>**样例** : `0`|integer (int32)|
|**overdueCount**  <br>*可选*|逾期订单笔数  <br>**样例** : `0`|integer (int32)|
|**repaymentCount**  <br>*可选*|还款订单笔数  <br>**样例** : `0`|integer (int32)|
|**userIdcard**  <br>*可选*|用户身份证号  <br>**样例** : `"string"`|string|
|**userMobile**  <br>*可选*|用户手机号  <br>**样例** : `"string"`|string|
|**userName**  <br>*可选*|用户名称  <br>**样例** : `"string"`|string|


<a name="df6eb66472516c228df1d62f76890578"></a>
### 登录对象

|名称|说明|类型|
|---|---|---|
|**appKey**  <br>*必填*|appKey(app唯一标识）  <br>**样例** : `"string"`|string|
|**key**  <br>*必填*|密码（用rsa加密）  <br>**样例** : `"string"`|string|


<a name="9bc016c1b51f63a45eb4a10055198733"></a>
### 风控模型返回对象

|名称|说明|类型|
|---|---|---|
|**historyApplyBean**  <br>*必填*|用户历史操作轨迹  <br>**样例** : `"[a4b9713b76fd417a8770f802053e9092](#a4b9713b76fd417a8770f802053e9092)"`|[用户历史操作轨迹](#a4b9713b76fd417a8770f802053e9092)|
|**idCard**  <br>*必填*|用户身份证号  <br>**样例** : `"string"`|string|
|**mobile**  <br>*必填*|手机号  <br>**样例** : `"string"`|string|
|**score**  <br>*必填*|风控分  <br>**样例** : `0.0`|number|
|**taskId**  <br>*必填*|任务id  <br>**样例** : `"string"`|string|




<a name="securityscheme"></a>
## 安全

<a name="authorization"></a>
### Authorization
*类型* : apiKey  
*名称* : Authorization  
*在* : HEADER



