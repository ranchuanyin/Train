# 仿12306项目

## 仅供学习

### 项目技术：

##### 后端：

SpringBoot3，Spring Cloud Alibaba(Nacos，Sentinel)，MybatisPlus(原教程为Mybatis)，Redis，MySQL 8，Quartz

##### 前端

Vue3，Ant Design Vue组件库

### 使用须知：

1.修改MySQL配置，创建数据库，修改MySQL用户（配置文件为一库一单独用户），也可以使用root用户登录即可，数据库连接池为HikariPool

2.修改Redis配置，用于SpringBoot Cache（基于Redis缓存）以及分布式锁（也可以使用*Redisson*）

3.配置好Nacos，在各项目的bootstrap.yml中配置分布式配置

4.可以选择开启Seata分布式事务（电脑配置），在bootstrap.yml文件中打开Seata相关配置，并且在com.train.service.impl.AfterConfirmOrderServiceImpl中添加注解

5.可以选择使用RocketMQ（项目是采用异步线程方式），springboot3使用RocketMQ需要在resources文件夹下添加META-INF\spring\org.springframework.boot.autoconfigure.AutoConfiguration.imports

```import
org.apache.rocketmq.spring.autoconfigure.RocketMQAutoConfiguration
```

6.Sentinel配置中的启动端口为18080

6.前端为Vue-Cli构建
