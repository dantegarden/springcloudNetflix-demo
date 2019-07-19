# springcloudNetflix-demo

### 先决条件

首先本机先要安装以下环境。

- [git](https://git-scm.com/)
- [java8](http://www.oracle.com/technetwork/java/javase/downloads/index.html) 
- [maven](http://maven.apache.org/)

例外本机用到了以下中间件，推荐使用docker部署。
- rabbitmq
- mysql
- redis

### 技术栈
- 核心框架：springcloud:Greenwich.RELEASE全家桶，指定版本springboot:2.1.4.RELEASE
- 持久层框架：springDataJpa
- 数据库连接池：Alibaba Druid
- 日志管理：Logback
- 数据存储：mysql
- 公用缓存：redis
- 消息中间件：rabbitmq
- 链路监控：sleuth + zipkin

### 已实现的功能
- 基于Eureka的服务管理
- 基于Spring-Cloud-Config的配置管理
- 基于Spring-Cloud-Bus和Rabbitmq的动态刷新配置
- 基于Feign和RestTemplate的服务调用
- 基于Zuul的网关服务
- 基于Zipkin的链路监控服务
- 基于RabbitMQ或SpringCloudStream的消息驱动
- 支持网关的动态配置和流量控制
- 支持Redis的数据服务缓存
- 支持Hystrix的服务熔断以及HystrixDashbord的监控

### 项目目录结构说明

```
├─springcloudNetflix-demo-----------------------父项目，公共依赖
│  │
│  ├─springcloud-eureka------------------------微服务注册中心
│  │
│  ├─springcloud-eureka-client-----------------Eureka客户端示例
│  │
│  ├─springcloud-config-server-----------------微服务配置中心
│  │
│  ├─springcloud-gateway-----------------------微服务网关中心
│  │
│  ├─springcloud-biz-common--------------------全局公共依赖
│  │  │
│  │  ├─common-core-----------------------------核心基础依赖
│  │  │
│  │  ├─common-web------------------------------web基础依赖
│  │  │
│  │  └─common-api------------------------------api基础依赖
│  │
│  ├─springcloud-biz-product---------------------商品服务
│  │  │
│  │  ├─product-common--------------------------商品服务基础依赖
│  │  │
│  │  ├─product-client--------------------------商品服务客户端
│  │  │
│  │  └─product-server--------------------------商品服务服务端
│  │
│  ├─springcloud-biz-order-----------------------订单服务
│  │  │
│  │  ├─order-common----------------------------订单服务基础依赖
│  │  │
│  │  ├─order-client----------------------------订单服务客户端
│  │  │
│  │  └─order-server----------------------------订单服务服务端
│  │
│  ├─springcloud-biz-user------------------------用户服务
│  │  │
│  │  ├─user-common-----------------------------用户服务基础依赖
│  │  │
│  │  ├─user-client-----------------------------用户服务客户端
│  │  │
│  │  └─user-server-----------------------------用户服务服务端
```

### 部署配置
默认的部署分布如下：

|  服务          |   服务名         |  端口     | 备注                                            |
|---------------|-----------------|-----------|-------------------------------------------------|
|  数据库        |   mysql        |  3306     |  目前各应用共用一个，应用可建不同的database     |
|  公共缓存        |   redis         |  6379     |  目前共用，原则上各应用单独实例    |
|  消息中间件     |   rabbitmq      |  5672     |  共用                          |
|  日志收集中间件  |   zipkin       |  9411     |  共用                          |
|  注册中心  |   eureka       |  8761     |  目前单点，可以配置高可用        
|  配置中心  |   config-server       |  8790     |  目前单点，可以配置高可用        
|  网关中心  |   api-gateway       |  9500     |  目前单点，可以配置高可用
|  商品服务  |   biz-product       |  9101     |  作为提供者的示例
|  订单服务  |   biz-order         |  9001     |  作为消费者的示例
|  用户服务  |   biz-user          |  9201     |  与网关配合实现用户鉴权        

其中redis,rabbitmq,zipkin都用docker部署，注意配置里应填宿主机地址。

### 开发环境

1. 克隆代码库： `https://github.com/dantegarden/springcloudNetflix-demo.git`
2. 在根目录下安装依赖：`mvn clean install -U`
3. 创建数据库，导入db.sql
4. 修改配置文件
5. 按照以下顺序，启动项目
```
eureka
config-server
other services......
```

[线上配置文件仓库](https://github.com/dantegarden/springcloudNetflix-demo-repo) 需要配合github的webhook使用，因此要求配置中心有一个公网地址，推荐本地测试使用内网穿透（Natapp）
