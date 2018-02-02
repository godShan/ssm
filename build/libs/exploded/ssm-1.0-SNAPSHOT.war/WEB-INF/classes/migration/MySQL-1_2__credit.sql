/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2017/2/3 17:41:24                            */
/*==============================================================*/


drop table if exists ASSET_INFO;

drop table if exists COLLATERAL_INFO;

drop table if exists CREDIT_APPLICATION;

drop table if exists CUSTOMER_BASE_INFO;

drop table if exists CUSTOMER_ENTER_CONSTRAINTS;

drop table if exists CUSTOMER_EXTEND_ATTRIBUTE_DICTIONARY;

drop table if exists CUSTOMER_EXTEND_ATTRIBUTE_ITEM;

drop table if exists CUSTOMER_EXTEND_INFO;

drop table if exists LOAN_APPLICATION;

drop table if exists LOAN_COLLATERAL;

drop table if exists LOAN_RECORD;

/*==============================================================*/
/* Table: ASSET_INFO                                            */
/*==============================================================*/
create table ASSET_INFO
(
   ID BIGINT(20) not null AUTO_INCREMENT COMMENT 'ID',
   CUSTOMER_CODE varchar(20) DEFAULT NULL COMMENT '客户编号',
   CUSTOMER_NAME varchar(256) DEFAULT NULL COMMENT '客户名称',
   CREDIT_AMOUNT DECIMAL(10,2) DEFAULT NULL COMMENT '授信额度',
   CONSUMED_AMOUNT DECIMAL(10,2) DEFAULT NULL COMMENT '已使用额度',
   FROZEN_AMOJNT DECIMAL(10,2) DEFAULT NULL COMMENT '冻结额度',
   CREDIT_APPLICATION_ID BIGINT(20) DEFAULT NULL COMMENT '授信申请ID',
   CREDIT_START_DATE datetime DEFAULT NULL COMMENT '授信开始日期',
   CREDIT_END_DATE datetime DEFAULT NULL COMMENT '授信结束日期',
   CREDIT_APPLICATION_TIME datetime DEFAULT NULL COMMENT '授信申请时间',
   CREDIT_APPROVE_TIME datetime DEFAULT NULL COMMENT '授信通过时间',
   primary key (ID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户资产信息';

/*==============================================================*/
/* Table: COLLATERAL_INFO                                       */
/*==============================================================*/
create table COLLATERAL_INFO
(
   ID BIGINT(20) not null AUTO_INCREMENT COMMENT 'ID',
   CUSTOMER_CODE varchar(20) DEFAULT NULL COMMENT '客户编号',
   CUSTOMER_NAME varchar(256) DEFAULT NULL COMMENT '客户名称',
   COLLATERAL_NAME varchar(100) DEFAULT NULL COMMENT '押品名称',
   COLLATERAL_TYPE TINYINT DEFAULT NULL COMMENT '押品类型',
   COLLATERAL_PERIOD TINYINT DEFAULT NULL COMMENT '押品周期',
   MARKET_VALUE DECIMAL(10,2) DEFAULT NULL COMMENT '市场价值',
   CONSUMED_AMOUNT DECIMAL(10,2) DEFAULT NULL COMMENT '已用额度',
   FROZEN_AMOUNT DECIMAL(10,2) DEFAULT NULL COMMENT '冻结金额',
   REGISTER_TIME datetime DEFAULT NULL COMMENT '冻结金额',
   REGISTER_ID BIGINT(20) DEFAULT NULL COMMENT '登记人',
   CURRENT_EVALUATE_TIME datetime DEFAULT NULL COMMENT '当前评估时间',
   NEXT_EVLALUATE_TIME  datetime  DEFAULT NULL COMMENT '下次评估时间',
   primary key (ID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户押品信息';

/*==============================================================*/
/* Table: CREDIT_APPLICATION                                    */
/*==============================================================*/
create table CREDIT_APPLICATION
(
   ID BIGINT(20) not null AUTO_INCREMENT COMMENT 'ID',
   CUSTOMER_CODE varchar(20) DEFAULT NULL COMMENT '客户编号',
   CUSTOMER_NAME varchar(256) DEFAULT NULL COMMENT '客户名称',
   CREDIT_AUDIT_STATUS TINYINT DEFAULT NULL COMMENT '授信审核状态',
   APPLICATION_CREDIT_AMOUNT DECIMAL(10,2) DEFAULT NULL COMMENT '申请授信额度',
   APPROVE_CREDIT_AMOUNT2 DECIMAL(10,2) DEFAULT NULL COMMENT '审批授信额度',
   APPLICATION_ID BIGINT(20) DEFAULT NULL COMMENT '申请人',
   CREDIT_APPLICATION_TIME datetime DEFAULT NULL COMMENT '授信申请时间',
   CREDIT_APPROVE_TIME datetime DEFAULT NULL COMMENT '授信通过时间',
   primary key (ID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户授信申请表';

/*==============================================================*/
/* Table: CUSTOMER_BASE_INFO                                    */
/*==============================================================*/
create table CUSTOMER_BASE_INFO
(
   ID BIGINT(20) not null AUTO_INCREMENT COMMENT 'ID',
   CUSTOMER_CODE varchar(20) DEFAULT NULL COMMENT '客户编号',
   CUSTOMER_NAME varchar(256) DEFAULT NULL COMMENT '客户名称',
   CUSTOMER_IDENTITY_TYPE TINYINT DEFAULT NULL COMMENT '授信审核状态',
   primary key (ID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户基本信息';

/*==============================================================*/
/* Table: CUSTOMER_ENTER_CONSTRAINTS                            */
/*==============================================================*/
create table CUSTOMER_ENTER_CONSTRAINTS
(
   ID BIGINT(20) not null AUTO_INCREMENT COMMENT 'ID',
   ATTRIBUTE_ID BIGINT(20) DEFAULT NULL COMMENT '属性ID',
   VALUE_RANGE varchar(100) DEFAULT NULL COMMENT '值范围',
   primary key (ID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户准入约束信息';

/*==============================================================*/
/* Table: CUSTOMER_EXTEND_ATTRIBUTE_DICTIONARY                  */
/*==============================================================*/
create table CUSTOMER_EXTEND_ATTRIBUTE_DICTIONARY
(
   ID BIGINT(20) not null AUTO_INCREMENT COMMENT 'ID',
   ATRRIBUTE_NAME varchar(100) DEFAULT NULL COMMENT '属性名称',
   VALUE_TYPE TINYINT DEFAULT NULL COMMENT '值类型',
   INPUT_LIMIT_CONDITION varchar(100) DEFAULT NULL COMMENT '约束条件',
   primary key (ID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户扩展属性字典表';

/*==============================================================*/
/* Table: CUSTOMER_EXTEND_ATTRIBUTE_ITEM                        */
/*==============================================================*/
create table CUSTOMER_EXTEND_ATTRIBUTE_ITEM
(
   ID BIGINT(20) not null AUTO_INCREMENT COMMENT 'ID',
   ATTRIBUTE_ID BIGINT(20) DEFAULT NULL COMMENT '属性ID',
   ITEM_VALUE varchar(100) DEFAULT NULL COMMENT '值范围',
   `ORDER` TINYINT DEFAULT NULL COMMENT '顺序',
   primary key (ID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户扩展属性键值表';

/*==============================================================*/
/* Table: CUSTOMER_EXTEND_INFO                                  */
/*==============================================================*/
create table CUSTOMER_EXTEND_INFO
(
   ID BIGINT(20) not null AUTO_INCREMENT COMMENT 'ID',
   CUSTOMER_ID BIGINT(20) DEFAULT NULL COMMENT '客户ID',
   ATTRIBUTE_ID BIGINT(20) DEFAULT NULL COMMENT '属性ID',
   VALUE_TYPE TINYINT DEFAULT NULL COMMENT '值类型',
   ITEM_VALUE varchar(1000) DEFAULT NULL COMMENT '属性值',
   primary key (ID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户扩展信息表';

/*==============================================================*/
/* Table: LOAN_APPLICATION                                      */
/*==============================================================*/
create table LOAN_APPLICATION
(
   ID BIGINT(20) not null AUTO_INCREMENT COMMENT 'ID',
   CUSTOMER_CODE varchar(20) DEFAULT NULL COMMENT '客户编号',
   CUSTOMER_NAME varchar(256) DEFAULT NULL COMMENT '客户名称',
   LOAN_TYPE TINYINT DEFAULT NULL COMMENT '贷款类型',
   LOAN_PERIOD DECIMAL(10,2) DEFAULT NULL COMMENT '贷款周期',
   LOAN_APPLICATION_AMOUNT DECIMAL(10,2) DEFAULT NULL COMMENT '贷款申请金额',
   LOAN_APPROVE_AMOUNT DECIMAL(10,2) DEFAULT NULL COMMENT '贷款审批金额',
   LOAN_AUDIT_STATUS TINYINT DEFAULT NULL COMMENT '贷款审核状态',
   APPLICATION_ID BIGINT(20) DEFAULT NULL COMMENT '申请人',
   LOAN_APPLICATION_TIME datetime DEFAULT NULL COMMENT '贷款申请时间',
   LOAN_APPROVE_TIME datetime DEFAULT NULL COMMENT '贷款通过时间',
   primary key (ID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户扩展信息表';

/*==============================================================*/
/* Table: LOAN_COLLATERAL                                       */
/*==============================================================*/
create table LOAN_COLLATERAL
(
   ID BIGINT(20) not null AUTO_INCREMENT COMMENT 'ID',
   CUSTOMER_CODE varchar(20) DEFAULT NULL COMMENT '客户编号',
   CUSTOMER_NAME varchar(256) DEFAULT NULL COMMENT '客户名称',
   COLLATERAL_ID BIGINT(20) DEFAULT NULL COMMENT '抵押物品ID',
   LOAN_ID BIGINT(20) DEFAULT NULL COMMENT '贷款ID',
   COLLATERAL_AMOUNT DECIMAL(10,2) DEFAULT NULL COMMENT '抵押金额',
   primary key (ID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户扩展信息表';

/*==============================================================*/
/* Table: LOAN_RECORD                                           */
/*==============================================================*/
create table LOAN_RECORD
(
   ID BIGINT(20) not null AUTO_INCREMENT COMMENT 'ID',
   CUSTOMER_CODE varchar(20) DEFAULT NULL COMMENT '客户编号',
   CUSTOMER_NAME varchar(256) DEFAULT NULL COMMENT '客户名称',
   LOAN_TYPE TINYINT DEFAULT NULL COMMENT '贷款类型',
   LOAN_PERIOD DECIMAL(10,2) DEFAULT NULL COMMENT '贷款周期',
   LOAN_AMOUNT DECIMAL(10,2) DEFAULT NULL COMMENT '贷款额度',
   LOAN_STAUTS BIGINT(20) DEFAULT NULL COMMENT '贷款状态',
   LOAN_APPLICATION_TIME datetime DEFAULT NULL COMMENT '贷款申请时间',
   LOAN_APPROVE_TIME datetime DEFAULT NULL COMMENT '贷款通过时间',
   primary key (ID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户贷款记录';

/*==============================================================*/
/* Table: DEPARTMENT                                           */
/*==============================================================*/
create table DEPARTMENT
(
   `ID` BIGINT(20) not null AUTO_INCREMENT COMMENT 'ID',
   `DEPARTMENT_NAME` varchar(50) DEFAULT NULL COMMENT '部门名称',
   `DEPARTMENT_PID` BIGINT(20) DEFAULT NULL COMMENT '父部门id',
   `IS_DELETE` int(11) NOT NULL COMMENT '是否删除 0，表示删除 1.表示没有',
   `CREATE_BY` bigint(20) NOT NULL COMMENT '创建者',
   `CREATE_DATE` datetime NOT NULL COMMENT '创建时间',
   `UPDATE_BY` bigint(20) DEFAULT NULL COMMENT '更新者',
   `UPDATE_DATE` datetime DEFAULT NULL COMMENT '更新时间',
   `REMARK` varchar(500) DEFAULT NULL COMMENT '部门描述',
   primary key (ID),
   UNIQUE KEY `INDEX_DEPA_DEPA_NAME` (`DEPARTMENT_NAME`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='部门表';

/*==============================================================*/
/* Table: OPERATOR_DEPARTMENT                                           */
/*==============================================================*/
CREATE TABLE `OPERATOR_DEPARTMENT` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `DEPARTMENT_ID` bigint(20) NOT NULL COMMENT '部门ID',
  `BACK_OPERATOR_ID` bigint(20) NOT NULL COMMENT '后台用户ID',
  `POSITION` bigint(20) NOT NULL COMMENT '职务,1:部门负责人，2：普通员工',
  `CREATE_BY` bigint(20) NOT NULL COMMENT '创建者',
  `CREATE_DATE` datetime NOT NULL COMMENT '创建时间',
  `UPDATE_BY` bigint(20) DEFAULT NULL COMMENT '更新者',
  `UPDATE_DATE` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`ID`),
  KEY `INDEX_OPERATOR_DEPA_OPERATOR_ID` (`BACK_OPERATOR_ID`),
  KEY `INDEX_OPERATOR_DEPA_DEPARTMENT_ID` (`DEPARTMENT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户部门关系表';