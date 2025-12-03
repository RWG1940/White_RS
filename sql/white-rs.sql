/*
 Navicat Premium Data Transfer

 Source Server         : 运维助手
 Source Server Type    : MySQL
 Source Server Version : 80042 (8.0.42)
 Source Host           : 192.168.24.6:3306
 Source Schema         : white-rs

 Target Server Type    : MySQL
 Target Server Version : 80042 (8.0.42)
 File Encoding         : 65001

 Date: 03/12/2025 17:38:22
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for departments
-- ----------------------------
DROP TABLE IF EXISTS `departments`;
CREATE TABLE `departments`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `parent_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '父部门ID，根部门为NULL',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '部门名称',
  `leader_user_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '部门负责人用户ID',
  `order_num` int NULL DEFAULT 0 COMMENT '排序号，数值越小越靠前',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态：1启用 0禁用',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_parent_id`(`parent_id` ASC) USING BTREE,
  INDEX `idx_leader_user_id`(`leader_user_id` ASC) USING BTREE,
  CONSTRAINT `fk_dept_leader` FOREIGN KEY (`leader_user_id`) REFERENCES `users` (`id`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `fk_dept_parent` FOREIGN KEY (`parent_id`) REFERENCES `departments` (`id`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '部门表（组织架构）' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of departments
-- ----------------------------

-- ----------------------------
-- Table structure for dict_data
-- ----------------------------
DROP TABLE IF EXISTS `dict_data`;
CREATE TABLE `dict_data`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `type_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '所属字典类型编码',
  `k` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '键',
  `v` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '值',
  `order_num` int NULL DEFAULT 0 COMMENT '排序号',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态：1启用 0禁用',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_type`(`type_code` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '字典数据表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dict_data
-- ----------------------------

-- ----------------------------
-- Table structure for dict_type
-- ----------------------------
DROP TABLE IF EXISTS `dict_type`;
CREATE TABLE `dict_type`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '字典类型编码（唯一）',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '字典类型名称',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '描述',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_code`(`code` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '字典类型表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dict_type
-- ----------------------------

-- ----------------------------
-- Table structure for employees
-- ----------------------------
DROP TABLE IF EXISTS `employees`;
CREATE TABLE `employees`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint UNSIGNED NOT NULL COMMENT '关联的用户ID（唯一）',
  `department_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '所属部门ID',
  `job_title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '职位名称，如：后端工程师/管理员',
  `employee_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '工号（可唯一）',
  `hire_date` date NULL DEFAULT NULL COMMENT '入职日期',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态：1在职 0离职',
  `extra` json NULL COMMENT '员工扩展信息（JSON）',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_user_id`(`user_id` ASC) USING BTREE,
  UNIQUE INDEX `uk_employee_no`(`employee_no` ASC) USING BTREE,
  INDEX `idx_department_id`(`department_id` ASC) USING BTREE,
  CONSTRAINT `fk_employee_dept` FOREIGN KEY (`department_id`) REFERENCES `departments` (`id`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `fk_employee_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '员工表（用户扩展信息）' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of employees
-- ----------------------------

-- ----------------------------
-- Table structure for files
-- ----------------------------
DROP TABLE IF EXISTS `files`;
CREATE TABLE `files`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `original_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '原始文件名',
  `storage_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '存储路径或对象存储KEY',
  `size` bigint NOT NULL COMMENT '文件大小（字节）',
  `mime_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文件类型',
  `hash` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文件哈希（用于去重）',
  `storage` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'local' COMMENT '存储类型：local/s3/minio',
  `uploader_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '上传者ID',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '上传时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_hash`(`hash` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '文件上传表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of files
-- ----------------------------

-- ----------------------------
-- Table structure for login_logs
-- ----------------------------
DROP TABLE IF EXISTS `login_logs`;
CREATE TABLE `login_logs`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '用户ID',
  `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户名（快照）',
  `status` tinyint NOT NULL COMMENT '结果：1成功 0失败',
  `ip` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '登录IP',
  `message` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '登录提示信息',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '登录时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '登录日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of login_logs
-- ----------------------------

-- ----------------------------
-- Table structure for menus
-- ----------------------------
DROP TABLE IF EXISTS `menus`;
CREATE TABLE `menus`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `parent_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '父菜单ID',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '菜单名称',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '路由路径',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '前端组件路径',
  `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '图标',
  `order_num` int NULL DEFAULT 0 COMMENT '排序号',
  `hidden` tinyint NOT NULL DEFAULT 0 COMMENT '是否隐藏：0显示 1隐藏',
  `permission_code` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '关联权限编码',
  `meta` json NULL COMMENT '菜单元数据（JSON）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_parent`(`parent_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '菜单表（前端路由）' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menus
-- ----------------------------

-- ----------------------------
-- Table structure for notifications
-- ----------------------------
DROP TABLE IF EXISTS `notifications`;
CREATE TABLE `notifications`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '通知标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '通知内容（可为富文本）',
  `type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'system' COMMENT '通知类型：system/message/warning',
  `receiver_user_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '接收者用户ID，null表示全体',
  `read_at` datetime NULL DEFAULT NULL COMMENT '阅读时间',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_receiver`(`receiver_user_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统通知表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of notifications
-- ----------------------------

-- ----------------------------
-- Table structure for operation_logs
-- ----------------------------
DROP TABLE IF EXISTS `operation_logs`;
CREATE TABLE `operation_logs`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '操作用户ID',
  `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '操作用户名（快照）',
  `action` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '操作动作，如 UPDATE/DELETE',
  `target_table` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '操作目标表名',
  `target_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '目标记录ID',
  `before_state` json NULL COMMENT '变更前数据（JSON）',
  `after_state` json NULL COMMENT '变更后数据（JSON）',
  `ip` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '操作IP',
  `user_agent` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '客户端UA',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_created_at`(`created_at` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '操作日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of operation_logs
-- ----------------------------

-- ----------------------------
-- Table structure for permissions
-- ----------------------------
DROP TABLE IF EXISTS `permissions`;
CREATE TABLE `permissions`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '权限名称',
  `code` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '权限编码（唯一，如 user:create）',
  `type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限类型：menu/api/action',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'API 路径 或 前端路由路径',
  `method` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'HTTP方法：GET POST PUT DELETE',
  `meta` json NULL COMMENT '权限扩展信息（JSON）',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_code`(`code` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '权限表（权限点）' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of permissions
-- ----------------------------

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `role_id` bigint UNSIGNED NOT NULL COMMENT '角色ID',
  `permission_id` bigint UNSIGNED NOT NULL COMMENT '权限ID',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_role_id`(`role_id` ASC) USING BTREE,
  INDEX `idx_permission_id`(`permission_id` ASC) USING BTREE,
  CONSTRAINT `fk_role_permission_perm` FOREIGN KEY (`permission_id`) REFERENCES `permissions` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_role_permission_role` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色-权限关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_permission
-- ----------------------------

-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色名称',
  `code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色编码（唯一）',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '角色描述',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态：1启用 0禁用',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_code`(`code` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of roles
-- ----------------------------
INSERT INTO `roles` VALUES (1, '管理员', '3294', '拥有最高权限的用户', 1, '2025-11-23 21:58:42');
INSERT INTO `roles` VALUES (2, '普通用户', '1502', '拥有普通权限的用户', 1, '2025-11-23 21:59:31');
INSERT INTO `roles` VALUES (3, '优鼎', '5293', '优鼎业务员', 1, '2025-12-03 14:19:23');
INSERT INTO `roles` VALUES (4, '辅料工厂', '6666', '辅料工厂用户', 1, '2025-12-03 14:19:55');
INSERT INTO `roles` VALUES (5, '工厂', '7777', '工厂用户', 1, '2025-12-03 14:20:19');

-- ----------------------------
-- Table structure for sessions
-- ----------------------------
DROP TABLE IF EXISTS `sessions`;
CREATE TABLE `sessions`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint UNSIGNED NOT NULL COMMENT '用户ID',
  `token` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '会话Token（如JWT），或Token哈希',
  `ip` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '登录IP地址',
  `device` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '设备信息/浏览器UA',
  `login_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '登录时间',
  `expires_at` datetime NOT NULL COMMENT '过期时间',
  `revoked` tinyint NOT NULL DEFAULT 0 COMMENT '是否已注销：0未注销 1已注销',
  `revoked_at` datetime NULL DEFAULT NULL COMMENT '注销时间（手动退出或被踢）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE,
  INDEX `idx_token`(`token`(100) ASC) USING BTREE,
  CONSTRAINT `fk_sessions_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户会话表（管理登录Token）' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sessions
-- ----------------------------

-- ----------------------------
-- Table structure for settings
-- ----------------------------
DROP TABLE IF EXISTS `settings`;
CREATE TABLE `settings`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `config_key` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '配置键（唯一）',
  `config_value` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '配置值（JSON或字符串）',
  `group_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '配置组，如 system/email/oss',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '配置说明',
  `updated_by` bigint UNSIGNED NULL DEFAULT NULL COMMENT '最后修改人ID',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_key`(`config_key` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of settings
-- ----------------------------

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint UNSIGNED NOT NULL COMMENT '用户ID',
  `role_id` bigint UNSIGNED NOT NULL COMMENT '角色ID',
  `assigned_by` bigint UNSIGNED NULL DEFAULT NULL COMMENT '分配者用户ID',
  `assigned_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '分配时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE,
  INDEX `idx_role_id`(`role_id` ASC) USING BTREE,
  CONSTRAINT `fk_user_role_role` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_user_role_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户-角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (1, 1, 1, 1, '2025-12-02 10:37:39');
INSERT INTO `user_role` VALUES (2, 2, 2, 1, '2025-12-02 11:12:03');
INSERT INTO `user_role` VALUES (4, 4, 2, NULL, '2025-12-03 14:03:12');
INSERT INTO `user_role` VALUES (5, 22, 2, NULL, '2025-12-03 14:03:37');
INSERT INTO `user_role` VALUES (6, 3, 3, NULL, '2025-12-03 14:25:18');
INSERT INTO `user_role` VALUES (7, 5, 4, NULL, '2025-12-03 14:26:00');
INSERT INTO `user_role` VALUES (8, 6, 5, NULL, '2025-12-03 14:26:04');

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名（唯一）',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱（唯一，可为空）',
  `phone` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '手机号（唯一，可为空）',
  `password_hash` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码哈希（bcrypt/argon2）',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '账号状态：0禁用 1正常 2锁定',
  `last_login_at` datetime NULL DEFAULT NULL COMMENT '上次登录时间',
  `last_login_ip` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '上次登录IP',
  `profile` json NULL COMMENT '扩展字段（JSON）',
  `version` int NOT NULL DEFAULT 0 COMMENT '版本号（乐观锁）',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted_at` datetime NULL DEFAULT NULL COMMENT '软删除时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_username`(`username` ASC) USING BTREE,
  UNIQUE INDEX `uk_email`(`email` ASC) USING BTREE,
  UNIQUE INDEX `uk_phone`(`phone` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '后台用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (1, 'white', '1940449715@qq.com', '13177675636', '$2a$10$QGDi2XA//w/Ehlh1.HTENuzWgatZPhM.OAUNdshzwCgZ5/LY2rNXy', 1, '2025-12-03 15:55:50', '192.168.24.188', NULL, 0, '2025-12-02 10:29:16', '2025-12-03 15:55:50', NULL);
INSERT INTO `users` VALUES (2, 'one', NULL, NULL, '$2a$10$1096l.Yu9S3PTOmcphDH2.XeOUqOtzsMY8byluLS8ORUaYfvT0iUC', 1, '2025-12-02 15:07:12', '192.168.24.77', NULL, 0, '2025-12-02 09:05:59', '2025-12-02 15:07:12', NULL);
INSERT INTO `users` VALUES (3, 'youding', NULL, NULL, '$2a$10$ZuCJwxxSD1X02hgy0bMHieM/BLEz6oVwQ3B5A.FQiRGQWCd5YQre2', 1, '2025-12-02 16:06:31', '192.168.24.188', NULL, 0, '2025-12-02 09:06:02', '2025-12-02 16:06:31', NULL);
INSERT INTO `users` VALUES (4, 'a28609', NULL, NULL, '$2a$10$X6bu.h/OHx3e50fe2D7i3e/nZInXEzOMLYeV/o2/yOpPYEoUIz9tq', 0, '2025-12-02 13:41:44', '192.168.24.77', NULL, 0, '2025-12-02 09:06:03', '2025-12-02 16:05:24', NULL);
INSERT INTO `users` VALUES (5, '901e32', NULL, NULL, '$2a$10$XpIW6J0KPr233CbIFIASte21Vd2QdvsloiItneefKKDPMsKhf6YGO', 1, NULL, NULL, NULL, 0, '2025-12-02 09:06:07', '2025-12-02 09:06:07', NULL);
INSERT INTO `users` VALUES (6, '631cf7', NULL, NULL, '$2a$10$sumYsvz.cxWNofoIsVxVpuCtCSPTTjI2vhWQCTIGOap1/frKPeYda', 1, NULL, NULL, NULL, 0, '2025-12-02 09:06:08', '2025-12-02 09:06:08', NULL);
INSERT INTO `users` VALUES (7, 'e11253', NULL, NULL, '$2a$10$NjXH/FIRn54/sigrCcMn/ePX97Bb.PjB0Q6GSpMD2k/V4AZV1dTj6', 1, NULL, NULL, NULL, 0, '2025-12-02 09:06:08', '2025-12-02 09:06:08', NULL);
INSERT INTO `users` VALUES (8, '13b98a', NULL, NULL, '$2a$10$wg6zrE2eDBCCIdD9.9pVD.tKl7HQy0NnwOnMYewtLTKsLbpp4RdKq', 1, NULL, NULL, NULL, 0, '2025-12-02 09:06:08', '2025-12-02 09:06:08', NULL);
INSERT INTO `users` VALUES (9, '1490e1', NULL, NULL, '$2a$10$740rLusfCOexBTnPThV6HuAZfer/5bVH47YXDNDwPbRfWl2LnoSUK', 1, NULL, NULL, NULL, 0, '2025-12-02 09:06:08', '2025-12-02 09:06:08', NULL);
INSERT INTO `users` VALUES (10, 'fcdbab', NULL, NULL, '$2a$10$A0lNHXynZp4Mwx0FTQMOQe3hNFt3JrcqpFoHcd.rXt.YC1anKTfty', 1, NULL, NULL, NULL, 0, '2025-12-02 09:06:08', '2025-12-02 09:06:08', NULL);
INSERT INTO `users` VALUES (11, '022a9b', NULL, NULL, '$2a$10$FodQy6Ke0UFr2RVUH1EV..lYkE6m7YuxYJszzM9cCULZwUML3MKPi', 1, NULL, NULL, NULL, 0, '2025-12-02 09:06:09', '2025-12-02 09:06:09', NULL);
INSERT INTO `users` VALUES (12, 'e8e8c8', NULL, NULL, '$2a$10$zNYnMDsqiK6oeuzOY8hTducTpqPTuGXZRe6BfU7BRoTwF5x4fp51y', 1, NULL, NULL, NULL, 0, '2025-12-02 09:06:09', '2025-12-02 09:06:09', NULL);
INSERT INTO `users` VALUES (13, 'ab8001', NULL, NULL, '$2a$10$D7DcG7PI7i8091z.fIWn8eME5Cr5VUg0NqT12FoG4zYaAGlXYsok2', 1, NULL, NULL, NULL, 0, '2025-12-02 09:06:09', '2025-12-02 09:06:09', NULL);
INSERT INTO `users` VALUES (15, 'oqe253', NULL, NULL, '$2a$10$N700BR47bYx3vtEv.xeGy.IsDYdDYX6nHj6gzWOVewjZyRHs9pHn2', 1, NULL, NULL, NULL, 0, '2025-12-02 13:52:15', '2025-12-02 13:52:15', NULL);
INSERT INTO `users` VALUES (17, '1fzrqa', NULL, NULL, '$2a$10$xCNBKI9li1ooGWqrVXzOqOPXIuCnPkA.c4UBd.aaH73nUt5OkQjlG', 1, NULL, NULL, NULL, 0, '2025-12-02 13:52:43', '2025-12-02 13:52:43', NULL);
INSERT INTO `users` VALUES (18, 'ssqhuw', NULL, NULL, '$2a$10$raZJysFQNOdIULNUWZ4X5e96LlOMlxOcmccUXt9m4IM1ECWeK4VmG', 1, NULL, NULL, NULL, 0, '2025-12-02 15:18:03', '2025-12-02 15:18:03', NULL);
INSERT INTO `users` VALUES (19, '054bkc', NULL, NULL, '$2a$10$LQQiuIai0wKeiOC70vvg2O0TU/G/YoicW7f5g2I55jOxTVQbuDNiC', 1, NULL, NULL, NULL, 0, '2025-12-03 09:03:25', '2025-12-03 09:03:25', NULL);
INSERT INTO `users` VALUES (20, 'vlm3n5', NULL, NULL, '$2a$10$s8/2OBAOvZ4TkHlY8m5o7.R3nyJseVM.uR42mk79hdp/jnGPLlCs2', 1, NULL, NULL, NULL, 0, '2025-12-03 11:46:18', '2025-12-03 11:46:18', NULL);
INSERT INTO `users` VALUES (21, '3jh8t8', NULL, NULL, '$2a$10$HYiV/KXQDKngsUNgZ6jkmuokvVdcvkcAtcvn70w7gjnM6xm9kHjza', 1, NULL, NULL, NULL, 0, '2025-12-03 11:46:23', '2025-12-03 11:46:23', NULL);
INSERT INTO `users` VALUES (22, 'k76wwa', NULL, NULL, '$2a$10$1JKEn9LEbDRl5QNu0qQwzuRQvCt5wCP0bK952qajbIa/ngjuPaWFW', 1, NULL, NULL, NULL, 0, '2025-12-03 14:03:37', '2025-12-03 14:03:37', NULL);

SET FOREIGN_KEY_CHECKS = 1;
