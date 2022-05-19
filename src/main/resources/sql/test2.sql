/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50553
Source Host           : localhost:3306
Source Database       : test2

Target Server Type    : MYSQL
Target Server Version : 50553
File Encoding         : 65001

Date: 2022-05-19 17:47:12
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL,
  `roleName` varchar(50) DEFAULT NULL,
  `roleDesc` text,
  `deleted` int(1) DEFAULT '0',
  `version` int(11) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1354080427122688', '院长', '负责全面工作', '0', '1');
INSERT INTO `sys_role` VALUES ('1354080459579392', '研究员', '课程研发工作', '0', '1');
INSERT INTO `sys_role` VALUES ('1354080466444288', '讲师', '授课工作', '0', '1');
INSERT INTO `sys_role` VALUES ('1354080472670208', '助教', '协助解决学生的问题', '0', '1');
INSERT INTO `sys_role` VALUES ('1354080478863360', '助管', '帮助老师处理事务', '0', '1');
INSERT INTO `sys_role` VALUES ('1354080926785536', 'test', 'tests', '0', '1');
INSERT INTO `sys_role` VALUES ('1354095773917184', '教授', '科研主力', '0', '1');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL,
  `username` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `password` varchar(80) DEFAULT NULL,
  `phoneNum` varchar(20) DEFAULT NULL,
  `sex` tinyint(1) DEFAULT NULL,
  `status` tinyint(1) DEFAULT NULL,
  `deleted` int(1) DEFAULT '0',
  `version` int(11) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1354095694069760', 'admin', 'admin@qq.com', '$2a$10$bWXhbCNNZBN7J/TolVVG8ebc8B268ByCuEY7XkiAspWY8pMvcvISe', '18750607859', '0', '1', '0', '1');
INSERT INTO `sys_user` VALUES ('1354095719440384', 'test', 'test@qq.com', '$2a$10$DFSyy0rZSCdEF8ssWwn3mebjEUpn6RExW9oxjukFY9aLN2YpGwx2G', '18750607859', '1', '1', '0', '1');
INSERT INTO `sys_user` VALUES ('1354095744458752', 'huweiv', 'huweiv@qq.com', '$2a$10$sBCkRRFT6mkwgUlE4Vl9x.FK1town3N80xs0RwJ..J/bmdaVC1u/S', '18750607859', '0', '1', '0', '1');
INSERT INTO `sys_user` VALUES ('1354095760547840', 'zhangsan', 'zhangsan@qq.com', '$2a$10$/0eCvthrY2JGCJv0N0XDJ.Pkvv01CODkkyIFNmAqCywF97RLhTmzm', '18750607859', '0', '1', '0', '1');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `userId` bigint(20) NOT NULL,
  `roleId` bigint(20) NOT NULL,
  PRIMARY KEY (`userId`,`roleId`),
  KEY `roleId` (`roleId`),
  CONSTRAINT `sys_user_role_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `sys_user` (`id`),
  CONSTRAINT `sys_user_role_ibfk_2` FOREIGN KEY (`roleId`) REFERENCES `sys_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1354095694069760', '1354080427122688');
INSERT INTO `sys_user_role` VALUES ('1354095694069760', '1354080459579392');
INSERT INTO `sys_user_role` VALUES ('1354095744458752', '1354080459579392');
INSERT INTO `sys_user_role` VALUES ('1354095760547840', '1354080466444288');
INSERT INTO `sys_user_role` VALUES ('1354095744458752', '1354080472670208');
INSERT INTO `sys_user_role` VALUES ('1354095744458752', '1354080478863360');
INSERT INTO `sys_user_role` VALUES ('1354095719440384', '1354080926785536');
