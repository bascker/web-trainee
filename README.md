# README
《架构探险-从零开始写 JavaWeb 框架》的学习之旅, 以分支来区分每一个代码阶段（章节）。
```
|- master               # 主干代码
|- br_helloword         # hello world
|- br_customers         # 客户管理
|- |- 1.0.0
```

## SQL
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