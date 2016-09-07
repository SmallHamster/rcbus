/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.6.11 : Database - leoman_rcbus_demo
*********************************************************************
*/


/*!40101 SET NAMES utf8mb4 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`leoman_rcbus` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `leoman_rcbus`;

/*Table structure for table `t_admin` */

DROP TABLE IF EXISTS `t_admin`;

CREATE TABLE `t_admin` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '登录名',
  `password` varchar(50) NOT NULL,
  `mobile` varchar(20) DEFAULT NULL,
  `create_date` bigint(20) DEFAULT NULL,
  `modify_date` bigint(20) DEFAULT NULL,
  `last_login_date` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COMMENT='系统管理员表';

/*Data for the table `t_admin` */

insert  into `t_admin`(`id`,`username`,`password`,`mobile`,`create_date`,`modify_date`,`last_login_date`) values (1,'admin','E10ADC3949BA59ABBE56E057F20F883E',NULL,NULL,NULL,NULL);

/*Table structure for table `t_admin_role` */

DROP TABLE IF EXISTS `t_admin_role`;

CREATE TABLE `t_admin_role` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `admin_id` bigint(32) DEFAULT NULL COMMENT '管理员ID',
  `role_id` bigint(32) DEFAULT NULL COMMENT '角色ID',
  `create_date` bigint(20) DEFAULT NULL,
  `modify_date` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COMMENT='用户角色关联关系表';

/*Data for the table `t_admin_role` */

/*Table structure for table `t_feedback` */

DROP TABLE IF EXISTS `t_feedback`;

CREATE TABLE `t_feedback` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(32) NOT NULL COMMENT '用户ID',
  `content` varchar(200) DEFAULT '' COMMENT '反馈内容',
  `create_date` bigint(20) DEFAULT NULL,
  `modify_date` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='意见反馈管理';

/*Data for the table `t_feedback` */

/*Table structure for table `t_log` */

DROP TABLE IF EXISTS `t_log`;

CREATE TABLE `t_log` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `message` varchar(100) DEFAULT '' COMMENT '日志消息',
  `user_type` int(2) DEFAULT '0' COMMENT '用户类别 0:后台管理员 1:APP用户',
  `user_id` bigint(20) DEFAULT NULL COMMENT '操作用户ID',
  `url` varchar(500) DEFAULT '' COMMENT '访问路径',
  `params` varchar(500) DEFAULT '' COMMENT '访问参数',
  `log_type` int(2) DEFAULT '0' COMMENT '日志类型 0:信息 1:错误',
  `create_date` bigint(20) DEFAULT NULL,
  `modify_date` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=243 DEFAULT CHARSET=utf8 COMMENT='日志表';

/*Table structure for table `t_module` */

DROP TABLE IF EXISTS `t_module`;

CREATE TABLE `t_module` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '模块名称',
  `url` varchar(200) DEFAULT NULL COMMENT '模块url',
  `module_icon` varchar(50) DEFAULT NULL COMMENT '模块图标',
  `pid` bigint(32) DEFAULT NULL COMMENT '父模块ID',
  `code` int(11) DEFAULT NULL COMMENT '模块编号',
  `description` varchar(2000) DEFAULT NULL COMMENT '描述',
  `admin_id` bigint(20) DEFAULT NULL COMMENT '操作员',
  `create_date` bigint(20) DEFAULT NULL,
  `modify_date` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COMMENT='模块表';

/*Data for the table `t_module` */

insert  into `t_module`(`id`,`name`,`url`,`module_icon`,`pid`,`code`,`description`,`admin_id`,`create_date`,`modify_date`) values (14,'权限管理','','fa fa-sitemap',NULL,1000,'权限管理',NULL,1472540847952,1472540847952);
insert  into `t_module`(`id`,`name`,`url`,`module_icon`,`pid`,`code`,`description`,`admin_id`,`create_date`,`modify_date`) values (15,'管理员列表','/admin/admin/index','',14,1001,'管理员列表',NULL,1472540979413,1472540979413);
insert  into `t_module`(`id`,`name`,`url`,`module_icon`,`pid`,`code`,`description`,`admin_id`,`create_date`,`modify_date`) values (16,'角色管理','/admin/role/index','',14,1002,'角色管理',NULL,1472541193352,1472541193352);
insert  into `t_module`(`id`,`name`,`url`,`module_icon`,`pid`,`code`,`description`,`admin_id`,`create_date`,`modify_date`) values (17,'模块管理','/admin/module/index','',14,1003,'模块管理',NULL,1472631469775,1472631469775);
insert  into `t_module`(`id`,`name`,`url`,`module_icon`,`pid`,`code`,`description`,`admin_id`,`create_date`,`modify_date`) values (18,'用户管理','','fa fa-users',NULL,2000,'用户管理',NULL,1472642706939,1472642706939);
insert  into `t_module`(`id`,`name`,`url`,`module_icon`,`pid`,`code`,`description`,`admin_id`,`create_date`,`modify_date`) values (19,'用户列表','/admin/user/index','',18,2001,'用户列表',NULL,1472642721569,1472642721569);
insert  into `t_module`(`id`,`name`,`url`,`module_icon`,`pid`,`code`,`description`,`admin_id`,`create_date`,`modify_date`) values (20,'系统管理','','fa fa-cog',NULL,3000,'系统管理',NULL,1472697026454,1472697026454);
insert  into `t_module`(`id`,`name`,`url`,`module_icon`,`pid`,`code`,`description`,`admin_id`,`create_date`,`modify_date`) values (21,'界面测试','/admin/demo/index','',20,3001,'界面测试',NULL,1472697051055,1472697051055);

/*Table structure for table `t_report` */

DROP TABLE IF EXISTS `t_report`;

CREATE TABLE `t_report` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(32) NOT NULL COMMENT '用户ID',
  `report_type` varchar(50) NOT NULL COMMENT '举报类型 多选',
  `post_type` tinyint(4) NOT NULL COMMENT '帖子类型 0:普通贴 1:直播贴 2:投票贴 ',
  `post_id` bigint(32) NOT NULL COMMENT '帖子ID',
  `create_date` bigint(20) DEFAULT NULL,
  `modify_date` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='举报管理';

/*Data for the table `t_report` */

/*Table structure for table `t_role` */

DROP TABLE IF EXISTS `t_role`;

CREATE TABLE `t_role` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '角色',
  `admin_id` bigint(20) DEFAULT NULL COMMENT '操作员',
  `create_date` bigint(20) DEFAULT NULL,
  `modify_date` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='角色表';

/*Data for the table `t_role` */

/*Table structure for table `t_role_module` */

DROP TABLE IF EXISTS `t_role_module`;

CREATE TABLE `t_role_module` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(32) DEFAULT NULL COMMENT '角色ID',
  `module_id` bigint(32) DEFAULT NULL COMMENT '模块ID',
  `create_date` bigint(20) DEFAULT NULL,
  `modify_date` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COMMENT='角色模块关联关系表';

/*Data for the table `t_role_module` */

/*Table structure for table `t_system_config` */

DROP TABLE IF EXISTS `t_system_config`;

CREATE TABLE `t_system_config` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `type` varchar(20) NOT NULL COMMENT '分类',
  `content` varchar(20) NOT NULL COMMENT '内容',
  `create_date` bigint(20) DEFAULT NULL,
  `modify_date` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='主创动态管理';

/*Data for the table `t_system_config` */

/*Table structure for table `t_user_info` */

DROP TABLE IF EXISTS `t_user_info`;

CREATE TABLE `t_user_info` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(32) NOT NULL COMMENT '关联user_login表ID',
  `mobile` varchar(32) NOT NULL COMMENT '账号',
  `password` varchar(50) NOT NULL COMMENT '密码',
  `nickname` varchar(50) DEFAULT '' COMMENT '昵称',
  `gender` tinyint(4) DEFAULT '0' COMMENT '性别 男:male 女:female',
  `avater` varchar(200) DEFAULT '' COMMENT '头像',
  `status` tinyint(4) DEFAULT '0' COMMENT '状态 0:正常 1:冻结',
  `type` tinyint(4) DEFAULT '0' COMMENT '会员类型 0:普通会员 1:主创',
  `level` int(11) DEFAULT '1' COMMENT '会员等级',
  `funs` bigint(20) DEFAULT '0' COMMENT '粉丝数',
  `focus` bigint(20) DEFAULT '0' COMMENT '关注数',
  `posts` int(11) DEFAULT '0' COMMENT '发帖数',
  `praises` bigint(20) DEFAULT '0' COMMENT '主创点赞数',
  `coin` bigint(20) DEFAULT '0' COMMENT '馒头币',
  `integral` bigint(20) DEFAULT '0' COMMENT '积分',
  `is_creator` tinyint(4) DEFAULT '0' COMMENT '是否主播 0:不是 1:是',
  `creator_id` bigint(20) DEFAULT '0' COMMENT '主创ID',
  `create_date` bigint(20) DEFAULT NULL,
  `modify_date` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户详细信息表';

/*Data for the table `t_user_info` */

/*Table structure for table `t_user_login` */

DROP TABLE IF EXISTS `t_user_login`;

CREATE TABLE `t_user_login` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '登录名',
  `password` varchar(50) NOT NULL,
  `create_date` bigint(20) DEFAULT NULL,
  `modify_date` bigint(20) DEFAULT NULL,
  `last_login_date` bigint(20) DEFAULT NULL,
  `ip_address` varchar(20) DEFAULT NULL COMMENT '登录IP地址',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户登录信息表';

/*Data for the table `t_user_login` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
