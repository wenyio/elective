/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : 127.0.0.1:3306
 Source Schema         : elective

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 26/04/2021 21:16:41
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created` datetime(6) NULL DEFAULT NULL,
  `is_deleted` bit(1) NULL DEFAULT NULL,
  `updated` datetime(6) NULL DEFAULT NULL,
  `begin_time` datetime(6) NULL DEFAULT NULL,
  `credit` int(11) NOT NULL DEFAULT 0,
  `end_time` datetime(6) NULL DEFAULT NULL,
  `limit_num` int(11) NOT NULL DEFAULT 0,
  `name` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `teacher_id` int(11) NULL DEFAULT NULL,
  `selected` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKqwmcv0fcio215pf0ypi6ft96x`(`teacher_id`) USING BTREE,
  CONSTRAINT `FKqwmcv0fcio215pf0ypi6ft96x` FOREIGN KEY (`teacher_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created` datetime(6) NULL DEFAULT NULL,
  `is_deleted` bit(1) NULL DEFAULT NULL,
  `updated` datetime(6) NULL DEFAULT NULL,
  `credit` int(11) NOT NULL DEFAULT 0,
  `name` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `number` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `role` tinyint(2) NOT NULL DEFAULT 1,
  `status` tinyint(2) NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_course
-- ----------------------------
DROP TABLE IF EXISTS `user_course`;
CREATE TABLE `user_course`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created` datetime(6) NULL DEFAULT NULL,
  `is_deleted` bit(1) NULL DEFAULT NULL,
  `updated` datetime(6) NULL DEFAULT NULL,
  `grade` double NOT NULL DEFAULT 0,
  `status` tinyint(2) NOT NULL DEFAULT 0,
  `course_id` int(11) NULL DEFAULT NULL,
  `student_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKka18m18kpimodvy8yg2icu083`(`course_id`) USING BTREE,
  INDEX `FK93qr1ow9fb8cpq3p77wlq3i9b`(`student_id`) USING BTREE,
  CONSTRAINT `FK93qr1ow9fb8cpq3p77wlq3i9b` FOREIGN KEY (`student_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKka18m18kpimodvy8yg2icu083` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
