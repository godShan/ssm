
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for back_operator
-- ----------------------------
DROP TABLE IF EXISTS `back_operator`;
CREATE TABLE `back_operator` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `USER_NAME` varchar(500) NOT NULL COMMENT '用户名',
  `PASSWORD` varchar(100) NOT NULL COMMENT '登录密码',
  `REAL_NAME` varchar(500) DEFAULT NULL COMMENT '真实姓名',
  `OPERATOR_STATUS` int(11) NOT NULL COMMENT '用户状态1:已开通 0:已冻结     \r\n            ',
  `PHONE` varchar(20) DEFAULT NULL COMMENT '电话',
  `MOBILE` varchar(20) DEFAULT NULL COMMENT '手机',
  `EMAIL` varchar(500) DEFAULT NULL COMMENT '邮箱',
  `STAFF_NO` varchar(300) DEFAULT NULL COMMENT '工号',
  `IS_SUPER_USER` int(11) NOT NULL COMMENT '是否超级用户，0：非超级用户 1：超级用户\r\n            ',
  `LOGIN_TIMES` int(11) NOT NULL COMMENT '登录次数',
  `LAST_LOGIN_DATE` datetime DEFAULT NULL COMMENT '最后一次登录时间',
  `LAST_IP` varchar(50) DEFAULT NULL COMMENT '最后登录的IP',
  `sex` int(11) DEFAULT NULL,
  `RECENTLY_PASSWORD` varchar(1000) DEFAULT NULL COMMENT '最近三次密码',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  `CREATE_BY` bigint(20) NOT NULL COMMENT '创建者',
  `UPDATE_BY` bigint(20) DEFAULT NULL COMMENT '更新者',
  `IS_DELETE` int(11) NOT NULL COMMENT '是否删除',
  `PROVINCE_ID` bigint(20) DEFAULT NULL COMMENT '归属省份id',
  `PROVINCE_NAME` varchar(200) DEFAULT NULL COMMENT '归属省份名称',
  `CITY_ID` bigint(20) DEFAULT NULL COMMENT '归属城市ID',
  `CITY_NAME` varchar(200) DEFAULT NULL COMMENT '归属城市名称',
  `COUNTY_ID` bigint(20) DEFAULT NULL COMMENT '归属地区ID',
  `COUNTY_NAME` varchar(200) DEFAULT NULL COMMENT '归属地区名称',
  `DEPARTMENT` varchar(500) DEFAULT NULL COMMENT '所属部门',
  `COMPANY` varchar(500) DEFAULT NULL COMMENT '所属公司',
  `WEIXIN_USER_ID` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `index_operator_name` (`USER_NAME`(255)),
  KEY `index_operator_real_name` (`REAL_NAME`(255))
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='后台用户表';

-- ----------------------------
-- Records of back_operator
-- ----------------------------
INSERT INTO `back_operator` VALUES ('1', 'admin', 'e10adc3949ba59abbe56e057f20f883e', '超级管理员', '1', '13111112222', '13111112222', '', null, '1', '37', '2016-10-11 10:02:55', '192.168.8.29', null, null, '2016-08-18 14:48:48', '2016-10-11 10:02:55', '1', '1', '0', null, null, null, null, null, null, null, null, '1');

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(50) NOT NULL COMMENT '菜单名称',
  `menu_url` varchar(500) NOT NULL COMMENT '菜单URL',
  `menu_code` varchar(500) NOT NULL COMMENT '菜单编号',
  `menu_type` int(11) NOT NULL COMMENT '菜单类型，1：一级模块，2：URL，3：功能',
  `is_visible` int(11) NOT NULL COMMENT '0：不可见，1：可见',
  `is_leaf` int(11) DEFAULT NULL COMMENT '是否是子菜单',
  `menu_parent_id` bigint(20) DEFAULT NULL COMMENT '父菜单ID',
  `create_by` bigint(20) NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `updtae_by` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `is_delete` int(11) NOT NULL COMMENT '是否删除',
  `source` int(11) DEFAULT NULL COMMENT '1：运营商  3：渠道商',
  PRIMARY KEY (`ID`),
  KEY `idx_menu_menu_code` (`menu_code`(255))
) ENGINE=InnoDB AUTO_INCREMENT=84 DEFAULT CHARSET=utf8 COMMENT='菜单表';

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES ('45', '菜单管理', '/menu/menuList.action', 'unicom_backend_menu_menuManager_menuList', '1', '1', '1', '66', '1', '2014-08-18 17:12:25', '1', '2014-08-18 17:12:25', '0', '1');
INSERT INTO `menu` VALUES ('46', '用户管理', '/backend/toUserControllerIndex.action', 'unicom_backend_system_backOperatorManager_toPage', '1', '1', '1', '66', '1', '2014-08-18 17:13:01', '1', '2014-08-18 17:13:01', '0', '1');
INSERT INTO `menu` VALUES ('59', '角色管理', '/user/toRolePage.action', 'unicom_backend_system_rolePageManager', '1', '1', '1', '66', '1', '2014-08-27 14:41:29', '1', '2014-08-27 14:41:29', '0', '1');
INSERT INTO `menu` VALUES ('66', '系统管理', '/', 'system', '1', '1', '0', '0', '1', '2014-08-29 16:39:03', '1', '2014-08-29 16:39:03', '0', '1');

-- ----------------------------
-- Table structure for operator_role
-- ----------------------------
DROP TABLE IF EXISTS `operator_role`;
CREATE TABLE `operator_role` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `back_operator_id` bigint(20) NOT NULL COMMENT '后台用户ID',
  `create_by` bigint(20) NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`ID`),
  KEY `idx_operator_role_operator_id` (`back_operator_id`),
  KEY `idx_operator_role_role_id` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色关系表';

-- ----------------------------
-- Records of operator_role
-- ----------------------------

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(50) NOT NULL COMMENT '角色名称',
  `create_by` bigint(20) NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `is_delete` int(11) NOT NULL COMMENT '是否删除',
  `role_status` int(11) DEFAULT NULL,
  `remark` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `idx_role_role_name` (`role_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of role
-- ----------------------------

-- ----------------------------
-- Table structure for role_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单ID',
  `create_by` bigint(20) NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`ID`),
  KEY `idx_role_menu_role_id` (`role_id`),
  KEY `idx_role_menu_menu_id` (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色菜单表';

-- ----------------------------
-- Records of role_menu
-- ----------------------------
