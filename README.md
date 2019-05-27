# GoldenEye - 金睛反爬系统
GoldenEye（金睛）是一套基于spring MVC编写的RESTFUL架构的http服务，前台页面或者客户端可以通过http接口请求接口服务与系统交互。系统需要配合iprobe、Aerospike(v3版本)、ksql(v4版本)、pipelinedb等组件实现整套反爬服务。


# Deployment
服务基于Spring MVC开发，打包需要配合jetty服务运行。
运行环境：
jetty 9.3.13.v20161014
jdk 1.8

配合一下组件运行：
pipelinedb 实时统计数据并存储数据
Aerospike/ksql 爬虫逻辑判断
iprobe 流量采集



# Features
* **系统管理**
  * 角色管理 对不同用户的不同权限做配置
  * 用户管理 管理员对不同账号分配不用角色，可对用户做增删改查
  * 系统配置 配置邮件服务器
  * 应用管理、数据资产管理 配置应用的域名、ip等信息，以及应用下面的数据接口url
  * 策略模板管理 配置爬虫策略的模板信息，方便用户快速配置

* **爬虫防御配置**
  * 用于配置爬虫策略，包括黑白名单、序列规则、反爬策略等
  * 将策略推送给Aerospike(v3版本)/ksql(v4版本)

* **CC防御配置**
  * 用于配置防御CC攻击的策略

* **爬虫情报板、爬虫概览、爬虫告警、爬虫分析**
  * 实时数据，根据时间、地域、运营商、来源、爬虫类别等维度来展示爬虫的统计信息

* **报告**
  * 离线数据
  * 用户配置关心的应用数据，系统则期推送数据到用户邮箱
