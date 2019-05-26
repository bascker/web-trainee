# README
## 简介
《架构探险-从零开始写 JavaWeb 框架》的学习之旅，以分支来区分每一个代码阶段（章节）。
```
|- master               # 主干代码
|- br_helloword         # hello world
|- br_customers         # 客户管理
|- |- 1.0.0
|- |- 1.0.1             # 项目结构调整
|- br_smartframework
|- |- 1.0.0             # 基础版框架：提供简单的IOC容器、请求分发功能
|- |- 1.0.1             # 加入 AOP 框架
```
项目结构以 master 为准，结构如下：
```
web-trainee
|- customer-manage      # 客户管理系统
|- smart-framework      # 核心，自研 web 框架
```

## 二、SQL
客户管理初始化 SQL
```
CREATE TABLE `customer` (
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(255) NOT NULL,
	`contact` VARCHAR(255) DEFAULT NULL,
	`telephone` VARCHAR(255) DEFAULT NULL,
	`email` VARCHAR(255) DEFAULT NULL,
	`remark` text,
	PRIMARY KEY(`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

INSERT INTO `customer` VALUES(1, 'bascker', NULL, '13023457890', 'bascker@huawei.com', NULL);
INSERT INTO `customer` VALUES(2, 'paul', NULL, '13154320987', 'paul@huawei.com', NULL);
```

## 三、案例
customer-manager 使用 smart-framework 来实现自己的业务逻辑。

1、配置 mvn 工程  
![customer-config](asset/customer-config.png)

2、编译程序   
```
# 根目录下执行如下命令
$ mvn clean install
```

3、启动 web 应用：运行第一步创建的 customer 应用, 待输出如下数据即启动成功。    
![customer-start](asset/customer-star.png)

4、浏览器输入`localhost/customers`进入系统  
![customer-home](asset/customer-home.png)  