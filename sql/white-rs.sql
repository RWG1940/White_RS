/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50744
Source Host           : localhost:3306
Source Database       : white-rs

Target Server Type    : MYSQL
Target Server Version : 50744
File Encoding         : 65001

Date: 2025-12-01 19:43:09
*/

SET
FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for departments
-- ----------------------------
DROP TABLE IF EXISTS `departments`;
CREATE TABLE `departments`
(
    `id`             bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `parent_id`      bigint(20) unsigned DEFAULT NULL COMMENT '父部门ID，根部门为NULL',
    `name`           varchar(100) NOT NULL COMMENT '部门名称',
    `leader_user_id` bigint(20) unsigned DEFAULT NULL COMMENT '部门负责人用户ID',
    `order_num`      int(11) DEFAULT '0' COMMENT '排序号，数值越小越靠前',
    `status`         tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态：1启用 0禁用',
    `created_at`     datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`     datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY              `idx_parent_id` (`parent_id`),
    KEY              `idx_leader_user_id` (`leader_user_id`),
    CONSTRAINT `fk_dept_leader` FOREIGN KEY (`leader_user_id`) REFERENCES `users` (`id`) ON DELETE SET NULL,
    CONSTRAINT `fk_dept_parent` FOREIGN KEY (`parent_id`) REFERENCES `departments` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='部门表（组织架构）';

-- ----------------------------
-- Records of departments
-- ----------------------------

-- ----------------------------
-- Table structure for dict_data
-- ----------------------------
DROP TABLE IF EXISTS `dict_data`;
CREATE TABLE `dict_data`
(
    `id`         bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `type_code`  varchar(100) NOT NULL COMMENT '所属字典类型编码',
    `k`          varchar(100) NOT NULL COMMENT '键',
    `v`          varchar(255) NOT NULL COMMENT '值',
    `order_num`  int(11) DEFAULT '0' COMMENT '排序号',
    `status`     tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态：1启用 0禁用',
    `created_at` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY          `idx_type` (`type_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字典数据表';

-- ----------------------------
-- Records of dict_data
-- ----------------------------

-- ----------------------------
-- Table structure for dict_type
-- ----------------------------
DROP TABLE IF EXISTS `dict_type`;
CREATE TABLE `dict_type`
(
    `id`          bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `code`        varchar(100) NOT NULL COMMENT '字典类型编码（唯一）',
    `name`        varchar(100) NOT NULL COMMENT '字典类型名称',
    `description` varchar(255)          DEFAULT NULL COMMENT '描述',
    `created_at`  datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字典类型表';

-- ----------------------------
-- Records of dict_type
-- ----------------------------

-- ----------------------------
-- Table structure for employees
-- ----------------------------
DROP TABLE IF EXISTS `employees`;
CREATE TABLE `employees`
(
    `id`            bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `user_id`       bigint(20) unsigned NOT NULL COMMENT '关联的用户ID（唯一）',
    `department_id` bigint(20) unsigned DEFAULT NULL COMMENT '所属部门ID',
    `job_title`     varchar(100)      DEFAULT NULL COMMENT '职位名称，如：后端工程师/管理员',
    `employee_no`   varchar(50)       DEFAULT NULL COMMENT '工号（可唯一）',
    `hire_date`     date              DEFAULT NULL COMMENT '入职日期',
    `status`        tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态：1在职 0离职',
    `extra`         json              DEFAULT NULL COMMENT '员工扩展信息（JSON）',
    `created_at`    datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`    datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_id` (`user_id`),
    UNIQUE KEY `uk_employee_no` (`employee_no`),
    KEY             `idx_department_id` (`department_id`),
    CONSTRAINT `fk_employee_dept` FOREIGN KEY (`department_id`) REFERENCES `departments` (`id`) ON DELETE SET NULL,
    CONSTRAINT `fk_employee_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='员工表（用户扩展信息）';

-- ----------------------------
-- Records of employees
-- ----------------------------

-- ----------------------------
-- Table structure for files
-- ----------------------------
DROP TABLE IF EXISTS `files`;
CREATE TABLE `files`
(
    `id`            bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `original_name` varchar(255) NOT NULL COMMENT '原始文件名',
    `storage_key`   varchar(255) NOT NULL COMMENT '存储路径或对象存储KEY',
    `size`          bigint(20) NOT NULL COMMENT '文件大小（字节）',
    `mime_type`     varchar(100)          DEFAULT NULL COMMENT '文件类型',
    `hash`          varchar(64)           DEFAULT NULL COMMENT '文件哈希（用于去重）',
    `storage`       varchar(32)           DEFAULT 'local' COMMENT '存储类型：local/s3/minio',
    `uploader_id`   bigint(20) unsigned DEFAULT NULL COMMENT '上传者ID',
    `created_at`    datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '上传时间',
    PRIMARY KEY (`id`),
    KEY             `idx_hash` (`hash`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文件上传表';

-- ----------------------------
-- Records of files
-- ----------------------------

-- ----------------------------
-- Table structure for login_logs
-- ----------------------------
DROP TABLE IF EXISTS `login_logs`;
CREATE TABLE `login_logs`
(
    `id`         bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `user_id`    bigint(20) unsigned DEFAULT NULL COMMENT '用户ID',
    `username`   varchar(64)       DEFAULT NULL COMMENT '用户名（快照）',
    `status`     tinyint(4) NOT NULL COMMENT '结果：1成功 0失败',
    `ip`         varchar(45)       DEFAULT NULL COMMENT '登录IP',
    `message`    varchar(255)      DEFAULT NULL COMMENT '登录提示信息',
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '登录时间',
    PRIMARY KEY (`id`),
    KEY          `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='登录日志表';

-- ----------------------------
-- Records of login_logs
-- ----------------------------

-- ----------------------------
-- Table structure for menus
-- ----------------------------
DROP TABLE IF EXISTS `menus`;
CREATE TABLE `menus`
(
    `id`              bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `parent_id`       bigint(20) unsigned DEFAULT NULL COMMENT '父菜单ID',
    `name`            varchar(100) NOT NULL COMMENT '菜单名称',
    `path`            varchar(255) DEFAULT NULL COMMENT '路由路径',
    `component`       varchar(255) DEFAULT NULL COMMENT '前端组件路径',
    `icon`            varchar(100) DEFAULT NULL COMMENT '图标',
    `order_num`       int(11) DEFAULT '0' COMMENT '排序号',
    `hidden`          tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否隐藏：0显示 1隐藏',
    `permission_code` varchar(200) DEFAULT NULL COMMENT '关联权限编码',
    `meta`            json         DEFAULT NULL COMMENT '菜单元数据（JSON）',
    PRIMARY KEY (`id`),
    KEY               `idx_parent` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜单表（前端路由）';

-- ----------------------------
-- Records of menus
-- ----------------------------

-- ----------------------------
-- Table structure for notifications
-- ----------------------------
DROP TABLE IF EXISTS `notifications`;
CREATE TABLE `notifications`
(
    `id`               bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `title`            varchar(255) NOT NULL COMMENT '通知标题',
    `content`          text         NOT NULL COMMENT '通知内容（可为富文本）',
    `type`             varchar(50)           DEFAULT 'system' COMMENT '通知类型：system/message/warning',
    `receiver_user_id` bigint(20) unsigned DEFAULT NULL COMMENT '接收者用户ID，null表示全体',
    `read_at`          datetime              DEFAULT NULL COMMENT '阅读时间',
    `created_at`       datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY                `idx_receiver` (`receiver_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统通知表';

-- ----------------------------
-- Records of notifications
-- ----------------------------

-- ----------------------------
-- Table structure for operation_logs
-- ----------------------------
DROP TABLE IF EXISTS `operation_logs`;
CREATE TABLE `operation_logs`
(
    `id`           bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `user_id`      bigint(20) unsigned DEFAULT NULL COMMENT '操作用户ID',
    `username`     varchar(64)           DEFAULT NULL COMMENT '操作用户名（快照）',
    `action`       varchar(100) NOT NULL COMMENT '操作动作，如 UPDATE/DELETE',
    `target_table` varchar(100)          DEFAULT NULL COMMENT '操作目标表名',
    `target_id`    varchar(64)           DEFAULT NULL COMMENT '目标记录ID',
    `before_state` json                  DEFAULT NULL COMMENT '变更前数据（JSON）',
    `after_state`  json                  DEFAULT NULL COMMENT '变更后数据（JSON）',
    `ip`           varchar(45)           DEFAULT NULL COMMENT '操作IP',
    `user_agent`   varchar(255)          DEFAULT NULL COMMENT '客户端UA',
    `created_at`   datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
    PRIMARY KEY (`id`),
    KEY            `idx_created_at` (`created_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='操作日志表';

-- ----------------------------
-- Records of operation_logs
-- ----------------------------

-- ----------------------------
-- Table structure for permissions
-- ----------------------------
DROP TABLE IF EXISTS `permissions`;
CREATE TABLE `permissions`
(
    `id`         bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `name`       varchar(100) NOT NULL COMMENT '权限名称',
    `code`       varchar(200) NOT NULL COMMENT '权限编码（唯一，如 user:create）',
    `type`       varchar(32)           DEFAULT NULL COMMENT '权限类型：menu/api/action',
    `path`       varchar(255)          DEFAULT NULL COMMENT 'API 路径 或 前端路由路径',
    `method`     varchar(10)           DEFAULT NULL COMMENT 'HTTP方法：GET POST PUT DELETE',
    `meta`       json                  DEFAULT NULL COMMENT '权限扩展信息（JSON）',
    `created_at` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='权限表（权限点）';

-- ----------------------------
-- Records of permissions
-- ----------------------------

-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles`
(
    `id`          bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `name`        varchar(100) NOT NULL COMMENT '角色名称',
    `code`        varchar(100) NOT NULL COMMENT '角色编码（唯一）',
    `description` varchar(255)          DEFAULT NULL COMMENT '角色描述',
    `status`      tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态：1启用 0禁用',
    `created_at`  datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- ----------------------------
-- Records of roles
-- ----------------------------
INSERT INTO `roles`
VALUES ('1', '管理员', '3294', '拥有最高权限的用户', '1', '2025-11-23 21:58:42');
INSERT INTO `roles`
VALUES ('2', '普通用户', '1502', '拥有普通权限的用户', '1', '2025-11-23 21:59:31');

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission`
(
    `id`            bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `role_id`       bigint(20) unsigned NOT NULL COMMENT '角色ID',
    `permission_id` bigint(20) unsigned NOT NULL COMMENT '权限ID',
    PRIMARY KEY (`id`),
    KEY             `idx_role_id` (`role_id`),
    KEY             `idx_permission_id` (`permission_id`),
    CONSTRAINT `fk_role_permission_perm` FOREIGN KEY (`permission_id`) REFERENCES `permissions` (`id`),
    CONSTRAINT `fk_role_permission_role` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色-权限关联表';

-- ----------------------------
-- Records of role_permission
-- ----------------------------

-- ----------------------------
-- Table structure for sessions
-- ----------------------------
DROP TABLE IF EXISTS `sessions`;
CREATE TABLE `sessions`
(
    `id`         bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `user_id`    bigint(20) unsigned NOT NULL COMMENT '用户ID',
    `token`      varchar(500) NOT NULL COMMENT '会话Token（如JWT），或Token哈希',
    `ip`         varchar(45)           DEFAULT NULL COMMENT '登录IP地址',
    `device`     varchar(255)          DEFAULT NULL COMMENT '设备信息/浏览器UA',
    `login_at`   datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '登录时间',
    `expires_at` datetime     NOT NULL COMMENT '过期时间',
    `revoked`    tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否已注销：0未注销 1已注销',
    `revoked_at` datetime              DEFAULT NULL COMMENT '注销时间（手动退出或被踢）',
    PRIMARY KEY (`id`),
    KEY          `idx_user_id` (`user_id`),
    KEY          `idx_token` (`token`(100)),
    CONSTRAINT `fk_sessions_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户会话表（管理登录Token）';

-- ----------------------------
-- Records of sessions
-- ----------------------------

-- ----------------------------
-- Table structure for settings
-- ----------------------------
DROP TABLE IF EXISTS `settings`;
CREATE TABLE `settings`
(
    `id`           bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `config_key`   varchar(200) NOT NULL COMMENT '配置键（唯一）',
    `config_value` text         NOT NULL COMMENT '配置值（JSON或字符串）',
    `group_name`   varchar(100)          DEFAULT NULL COMMENT '配置组，如 system/email/oss',
    `description`  varchar(255)          DEFAULT NULL COMMENT '配置说明',
    `updated_by`   bigint(20) unsigned DEFAULT NULL COMMENT '最后修改人ID',
    `updated_at`   datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_key` (`config_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统配置表';

-- ----------------------------
-- Records of settings
-- ----------------------------

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`
(
    `id`            bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `username`      varchar(64)  NOT NULL COMMENT '用户名（唯一）',
    `email`         varchar(255)          DEFAULT NULL COMMENT '邮箱（唯一，可为空）',
    `phone`         varchar(32)           DEFAULT NULL COMMENT '手机号（唯一，可为空）',
    `password_hash` varchar(255) NOT NULL COMMENT '密码哈希（bcrypt/argon2）',
    `status`        tinyint(4) NOT NULL DEFAULT '1' COMMENT '账号状态：0禁用 1正常 2锁定',
    `last_login_at` datetime              DEFAULT NULL COMMENT '上次登录时间',
    `last_login_ip` varchar(45)           DEFAULT NULL COMMENT '上次登录IP',
    `profile`       json                  DEFAULT NULL COMMENT '扩展字段（JSON）',
    `version`       int(11) NOT NULL DEFAULT '0' COMMENT '版本号（乐观锁）',
    `created_at`    datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`    datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted_at`    datetime              DEFAULT NULL COMMENT '软删除时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_username` (`username`),
    UNIQUE KEY `uk_email` (`email`),
    UNIQUE KEY `uk_phone` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='后台用户表';

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users`
VALUES ('1', 'White', '1940449715@qq.com', '10086', '$2a$10$pNxa8/hVQ2vbz2MP4.VrvettRI0OYSFj81QCHb86IXEn6phoeyo8a', '1',
        '2025-11-30 21:32:48', '0:0:0:0:0:0:0:1', null, '0', '2025-11-23 22:24:23', '2025-11-30 21:32:47', null);

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`
(
    `id`          bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `user_id`     bigint(20) unsigned NOT NULL COMMENT '用户ID',
    `role_id`     bigint(20) unsigned NOT NULL COMMENT '角色ID',
    `assigned_by` bigint(20) unsigned DEFAULT NULL COMMENT '分配者用户ID',
    `assigned_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '分配时间',
    PRIMARY KEY (`id`),
    KEY           `idx_user_id` (`user_id`),
    KEY           `idx_role_id` (`role_id`),
    CONSTRAINT `fk_user_role_role` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`),
    CONSTRAINT `fk_user_role_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户-角色关联表';

-- ----------------------------
-- Records of user_role
-- ----------------------------
SET
FOREIGN_KEY_CHECKS=1;
