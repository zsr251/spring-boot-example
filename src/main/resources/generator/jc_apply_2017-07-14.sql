
/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table activity
# ------------------------------------------------------------

DROP TABLE IF EXISTS `activity`;

CREATE TABLE `activity` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `activity_name` varchar(100) DEFAULT NULL COMMENT '活动名',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `apply_begin_time` datetime DEFAULT NULL COMMENT '报名开始时间',
  `apply_end_time` datetime DEFAULT NULL COMMENT '报名结束时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `apply_num` int(11) DEFAULT '0' COMMENT '报名人数',
  `status` varchar(2) DEFAULT NULL COMMENT '0 正常 1 关闭',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='活动';



# Dump of table activity_apply
# ------------------------------------------------------------

DROP TABLE IF EXISTS `activity_apply`;

CREATE TABLE `activity_apply` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `activity_id` int(11) DEFAULT NULL COMMENT '活动id',
  `employee_id` int(11) DEFAULT NULL COMMENT '员工id',
  `activity_name` varchar(200) DEFAULT NULL COMMENT '活动名',
  `real_name` varchar(50) DEFAULT NULL COMMENT '真是姓名',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `status` varchar(2) DEFAULT NULL COMMENT '0 报名 1 取消报名',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='活动报名表';



# Dump of table employee
# ------------------------------------------------------------

DROP TABLE IF EXISTS `employee`;

CREATE TABLE `employee` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `department` varchar(50) DEFAULT '未知' COMMENT '部门',
  `real_name` varchar(50) DEFAULT NULL COMMENT '姓名',
  `english_name` varchar(50) DEFAULT NULL COMMENT '英文名',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='员工';




/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
