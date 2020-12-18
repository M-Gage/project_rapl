# 风控决策引擎交行平台——B系统
用于其他系统用户进行风控评分

### 1. 开发说明
* * *

##### · base 
底层依赖，几乎所有的工具类，实体类，bean，dao，配置都放置在这一层
##### · service
业务逻辑层，一些aop,过滤器，拦截器，业务代码
##### · product_B
本系统的入口，mapper，controller

### 2. 服务
* * *
| 项目  | 端口 | 描述
|---|---|---
|ProductB |8086  |风控接口


### 3. 接口命名规则
* * *
不要暴露模型的内容

 *模型* ：/api/model/[二十八星宿名](https://baike.baidu.com/item/%E6%98%9F%E5%AE%BF/7135?fr=aladdin)
```
示例  ：@RequestMapping(value = "/api/model/jueMuJiao", method = RequestMethod.POST)
```
>东方青龙七宿：~~角木蛟JMJ~~ ~~亢金龙KJL~~ ~~氐土貉DTM~~ 房日兔FRT 心月狐XYH 尾火虎WHH 箕水豹JSB

>北方玄武七宿：斗木獬DMX 牛金牛NJN 女土蝠NTF 虚日鼠XRS 危月燕WYY 室火猪SHZ 壁水獝BSY

>西方白虎七宿：奎木狼KML 娄金狗LJG 胃土雉WTZ 昴日鸡MRJ 毕月乌BYW 觜火猴ZHH 参水猿CSY

>南方朱雀七宿：井木犴JMG 鬼金羊GJY 柳土獐LTZ 星日马XRM 张月鹿ZYL 翼火蛇YHS 轸水蚓ZSY

### 4. 模型命名规则
* * *
* 模型名称：
>公司_产品_类型（自有：ZY/第三方：DSF/整合：ZH）_版本号：QT_MX_ZY_V1;
* 产品编号：
>产品名和创建日期：mx190812
>
### 5. 生成文档
依次执行mvn指令：mvn asciidoctor:process-asciidoc  和 mvn generate-resources

