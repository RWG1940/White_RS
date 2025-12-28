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

 Date: 24/12/2025 18:20:41
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for accessories_purchase_contract
-- ----------------------------
DROP TABLE IF EXISTS `accessories_purchase_contract`;
CREATE TABLE `accessories_purchase_contract`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `image_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '图片地址（可存文件URL或MinIO路径）',
  `sku` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '货号',
  `color` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '颜色',
  `brand` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '品牌',
  `name_en` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '英文品名',
  `material_main` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '大面材料',
  `material_lining` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '里衬材质',
  `wash_label_color` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '洗标颜色',
  `wash_label_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '洗标种类',
  `factory` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '工厂',
  `address` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '地址',
  `follower` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '跟单人',
  `quantity` int NULL DEFAULT NULL COMMENT '数量',
  `wash_unit_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '洗标单价',
  `wash_total_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '洗标总价',
  `tag_unit_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '吊牌单价',
  `tag_total_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '吊牌总价',
  `wash_priority` int NULL DEFAULT NULL COMMENT '洗标优先级',
  `wash_status` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '洗标状态（如：未确认/已确认/已出货）',
  `wash_confirm_time` datetime NULL DEFAULT NULL COMMENT '洗标确认时间',
  `wash_ship_quantity` int NULL DEFAULT NULL COMMENT '洗标出货数量',
  `wash_ship_time` datetime NULL DEFAULT NULL COMMENT '洗标实际出货时间',
  `wash_express_no` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '洗标快递单号',
  `tag_priority` int NULL DEFAULT NULL COMMENT '吊牌优先级',
  `tag_status` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '吊牌状态（如：未确认/已确认/已出货）',
  `tag_confirm_time` datetime NULL DEFAULT NULL COMMENT '吊牌确认时间',
  `tag_ship_time` datetime NULL DEFAULT NULL COMMENT '吊牌出货时间',
  `tag_ship_quantity` int NULL DEFAULT NULL COMMENT '吊牌实际出货数量',
  `tag_express_no` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '吊牌快递单号',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `status` tinyint NULL DEFAULT 1 COMMENT '状态：1 启用，0 禁用',
  `priority` int NULL DEFAULT NULL COMMENT '整体优先级字段',
  `import_id` bigint NULL DEFAULT NULL COMMENT '导入批次ID（用于区分不同季度表）',
  `extra_json` json NULL COMMENT '额外字段（用于存储多余扩展字段）',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `quarter` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '季度',
  `sc_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '货号和颜色组成的唯一标识符',
  PRIMARY KEY (`id`, `sc_id`) USING BTREE,
  INDEX `idx_import`(`import_id` ASC) USING BTREE,
  INDEX `idx_sku`(`sku` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 113 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '洗标吊牌信息表（支持批量导入，多季度管理）' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of accessories_purchase_contract
-- ----------------------------
INSERT INTO `accessories_purchase_contract` VALUES (91, '/api/files/download/fcdf9811-0459-41cf-98f5-907dbb4ce3e1-imported_image_eed28798-c5d4-4111-9022-54f888afcb1d.jpg', 'CWBEO-HTR-U1-007-SS26', 'khaki', '123', 'Cosmetic bag', '100% POLYESTER', '100% POLYESTER', '黑色', '配饰不可水洗竖版', 'white', '倍佳', 'white', 2855, 2452.00, 7000460.00, NULL, NULL, 2, '3', NULL, NULL, '2025-12-02 20:25:29', '123122', 1, '1', NULL, '2025-12-18 19:49:37', NULL, '', '12', 3, 2, 6666, NULL, '2025-12-16 16:21:04', '2025-12-17 18:48:51', '26SS', 'CWBEO-HTR-U1-007-SS26_khaki');
INSERT INTO `accessories_purchase_contract` VALUES (92, '/api/files/download/1a7d9a8c-70de-4638-b8f8-ffca0775e690-imported_image_b5e25fff-5121-4e08-b010-3306a310047a.jpg', 'CWBEO-HTR-U1-002-SS26', 'black', '', 'Cosmetic bag', '100% POLYESTER', '100% POLYESTER', '黑色', '配饰不可水洗竖版', 'gendan', '倍佳', '杨瑾', 2855, 11.00, 31405.00, 666.00, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2025-12-09 18:24:41', NULL, NULL, NULL, 1, NULL, 6666, NULL, '2025-12-16 16:21:04', '2025-12-17 18:48:51', '26SS', 'CWBEO-HTR-U1-002-SS26_black');
INSERT INTO `accessories_purchase_contract` VALUES (94, '/api/files/download/18d6229f-b3a2-4b00-82af-75f2e04f6735-imported_image_9979212e-e8bb-45a1-990d-30a9d7c6197e.jpg', 'CEO-BHPC-W1-012-SS26', 'brown', '', 'Cosmetic bag', '100% POLYURETHANE', '100% POLYESTER', '黑色', '配饰不可水洗竖版', 'white', '倍佳', 'white', 3925, 33.00, 129525.00, NULL, 111.00, NULL, NULL, NULL, 11, NULL, NULL, NULL, NULL, NULL, '2025-12-20 19:44:12', NULL, NULL, NULL, 1, NULL, 6666, NULL, '2025-12-16 16:21:04', '2025-12-17 18:48:51', '26SS', 'CEO-BHPC-W1-012-SS26_brown');
INSERT INTO `accessories_purchase_contract` VALUES (96, '/api/files/download/8562a343-0225-47c1-8cfc-200e958255bf-imported_image_353de31f-56f2-4a6f-83f2-4b1e44e84dc6.jpg', 'WB-HTR-U1-004-SS26', 'blue', '', 'Cosmetic bag', '100% POLYURETHANE', '100% POLYESTER', '黑色', '配饰不可水洗竖版', 'gongchang', '倍佳', 'gendan', 600, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, 6666, NULL, '2025-12-17 18:48:51', '2025-12-17 18:48:51', '26SS', 'WB-HTR-U1-004-SS26_blue');
INSERT INTO `accessories_purchase_contract` VALUES (98, '/api/files/download/c077f745-22cc-428a-b6ad-4dfab1c597d4-imported_image_ba105cc6-4771-455d-96a5-69258f773f37.jpg', 'EO-ROSE-LA224-19454', 'black', 'GINO ROSSI', 'Ladies Bag', '100% FIBER', '100% FIBER', '黑色', '包包不可水洗竖版', 'gongchang', '', 'white', 43, 99999.00, 4299957.00, 0.00, 0.00, 0, '', '2025-12-17 00:00:00', 0, NULL, '', 0, '', NULL, NULL, 0, '', NULL, 1, 2, 7789, NULL, '2025-12-18 18:07:47', '2025-12-18 18:11:50', '26SS', 'EO-ROSE-LA224-19454_black');
INSERT INTO `accessories_purchase_contract` VALUES (99, '/api/files/download/52351dfb-eb5a-4db5-a0a3-9df7a1d511e7-imported_image_73d09d03-dc55-4672-b8f6-e67ca6e51728.jpg', 'EO-ROSE-LA224-19454', 'off-white', 'GINO ROSSI', 'Ladies Bag', '100% FIBER', '100% FIBER', '白色', '包包不可水洗竖版', '', '', 'white', 435, 0.00, 0.00, 0.00, 0.00, 0, '1', '2025-12-17 00:00:00', 0, NULL, '', 0, '', NULL, NULL, 0, '', NULL, 1, NULL, 7789, NULL, '2025-12-18 18:07:47', '2025-12-18 18:11:50', '26SS', 'EO-ROSE-LA224-19454_off-white');
INSERT INTO `accessories_purchase_contract` VALUES (100, '/api/files/download/42139ad1-2cf8-4b98-9852-8fd3fa271ca9-imported_image_db271825-ac06-4800-8bce-66322d7b052e.jpg', 'EO-ROSE-LA224-19454', 'golden', 'GINO ROSSI', 'Ladies Bag', '100% FIBER', '100% FIBER', '白色', '包包不可水洗竖版', '', '', 'white', 290, 0.00, 0.00, 0.00, 0.00, 0, '', '2025-12-17 00:00:00', 0, NULL, '', 0, '', NULL, NULL, 0, '', NULL, 1, NULL, 7789, NULL, '2025-12-18 18:07:47', '2025-12-18 18:11:50', '26SS', 'EO-ROSE-LA224-19454_golden');
INSERT INTO `accessories_purchase_contract` VALUES (101, '/api/files/download/5bf05623-ee76-4252-9d48-f1a9afa1023f-imported_image_92887029-a2e6-427c-a0c3-e0a45a7d0733.jpg', 'EO-ROSE-LA224-19454', 'silver', 'GINO ROSSI', 'Ladies Bag', '100% FIBER', '100% FIBER', '白色', '包包不可水洗竖版', '', '', '', 290, 0.00, 0.00, 0.00, 0.00, 0, '', '2025-12-17 00:00:00', 0, NULL, '', 0, '', NULL, NULL, 0, '', NULL, 1, NULL, 7789, NULL, '2025-12-18 18:07:47', '2025-12-18 18:11:50', '26SS', 'EO-ROSE-LA224-19454_silver');
INSERT INTO `accessories_purchase_contract` VALUES (102, '/api/files/download/0bbc1b00-f9b4-4e33-a639-7d0a974a0cc0-imported_image_6aa76147-697d-45e5-8398-66fe7e9706ed.jpg', 'EO-ROSE-LA224-19650', 'black', 'GINO ROSSI', 'Ladies Bag', '100% FIBER', '100% POLYESTER', '黑色', '包包不可水洗竖版', '', '', '', 435, 0.00, 0.00, 0.00, 0.00, 0, '', '2025-12-17 00:00:00', 0, NULL, '', 0, '', NULL, NULL, 0, '', NULL, 1, NULL, 7789, NULL, '2025-12-18 18:07:47', '2025-12-18 18:11:50', '26SS', 'EO-ROSE-LA224-19650_black');
INSERT INTO `accessories_purchase_contract` VALUES (103, '/api/files/download/6d12010e-ba8b-4dc0-8a1d-045a7cae64b2-imported_image_a2bdf246-f584-4a13-acad-f69bfa5cba6b.jpg', 'EO-ROSE-LA224-19650', 'off-white', 'GINO ROSSI', 'Ladies Bag', '100% FIBER', '100% POLYESTER', '白色', '包包不可水洗竖版', '', '', '', 435, 0.00, 0.00, 0.00, 0.00, 0, '', '2025-12-17 00:00:00', 0, NULL, '', 0, '', NULL, NULL, 0, '', NULL, 1, NULL, 7789, NULL, '2025-12-18 18:07:47', '2025-12-18 18:11:50', '26SS', 'EO-ROSE-LA224-19650_off-white');
INSERT INTO `accessories_purchase_contract` VALUES (104, '/api/files/download/4243c1ba-0b04-4df0-8dee-dd89d365b7a4-imported_image_4bf5de53-a9c1-4a8b-aa14-e5c022b15c8e.jpg', 'EO-ROSE-LA224-19650', 'golden', 'GINO ROSSI', 'Ladies Bag', '100% FIBER', '100% POLYESTER', '白色', '包包不可水洗竖版', '', '', 'gendan', 290, 0.00, 0.00, 0.00, 0.00, 0, '', '2025-12-17 00:00:00', 0, NULL, '', 0, '', NULL, NULL, 0, '', NULL, 1, NULL, 7789, NULL, '2025-12-18 18:07:47', '2025-12-18 18:07:47', '26SS', 'EO-ROSE-LA224-19650_golden');
INSERT INTO `accessories_purchase_contract` VALUES (105, '/api/files/download/9a34beae-f7d4-4290-af8b-1e861b10c028-imported_image_9587f809-7f48-489b-b070-402f6f42989a.jpg', 'EO-ROSE-LA224-19650', 'silver', 'GINO ROSSI', 'Ladies Bag', '100% FIBER', '100% POLYESTER', '白色', '包包不可水洗竖版', '', '', '', 290, 0.00, 0.00, 0.00, 0.00, 0, '', '2025-12-17 00:00:00', 0, NULL, '', 0, '', NULL, NULL, 0, '', NULL, 1, NULL, 7789, NULL, '2025-12-18 18:07:47', '2025-12-18 18:11:50', '26SS', 'EO-ROSE-LA224-19650_silver');
INSERT INTO `accessories_purchase_contract` VALUES (106, '/api/files/download/db4a0fa7-d994-4d60-80d9-972dd47fdaee-imported_image_570fe74c-6f44-4672-b6a5-6477328c9ca3.jpg', 'EO-ROSE-LA224-194455', 'black', 'GINO ROSSI', 'Ladies Bag', '100% FIBER', '100% POLYESTER', '黑色', '包包不可水洗竖版', '', '', 'gendan', 435, 0.00, 0.00, 0.00, 0.00, 0, '', '2025-12-17 00:00:00', 0, NULL, '', 0, '', NULL, NULL, 0, '', NULL, 1, NULL, 7789, NULL, '2025-12-18 18:07:47', '2025-12-18 18:11:50', '26SS', 'EO-ROSE-LA224-194455_black');
INSERT INTO `accessories_purchase_contract` VALUES (107, '/api/files/download/8824fb58-5f8a-43f5-beaa-783c50768cd6-imported_image_3f185871-becb-4f53-9ffd-02176fafc641.jpg', 'EO-ROSE-LA224-194455', 'off-white', 'GINO ROSSI', 'Ladies Bag', '100% FIBER', '100% POLYESTER', '白色', '包包不可水洗竖版', '', '', '', 435, 0.00, 0.00, 0.00, 0.00, 0, '', '2025-12-17 00:00:00', 0, NULL, '', 0, '', NULL, NULL, 0, '', NULL, 1, NULL, 7789, NULL, '2025-12-18 18:07:47', '2025-12-18 18:11:50', '26SS', 'EO-ROSE-LA224-194455_off-white');
INSERT INTO `accessories_purchase_contract` VALUES (108, '/api/files/download/1b10b8cc-075d-4826-ae57-311cf0ea446a-imported_image_fc457455-f68d-4968-95a9-3e0a49050651.jpg', 'EO-ROSE-LA224-194455', 'golden', 'GINO ROSSI', 'Ladies Bag', '100% FIBER', '100% POLYESTER', '白色', '包包不可水洗竖版', '', '', 'white', 290, 0.00, 0.00, 0.00, 0.00, 0, '', '2025-12-17 00:00:00', 0, NULL, '', 0, '', NULL, NULL, 0, '', NULL, 1, NULL, 7789, NULL, '2025-12-18 18:07:47', '2025-12-18 18:11:50', '26SS', 'EO-ROSE-LA224-194455_golden');
INSERT INTO `accessories_purchase_contract` VALUES (109, '/api/files/download/cbbeb970-fbba-46b2-aabf-3607065739d6-imported_image_6b8ab57e-388f-4bf9-8b14-9781715b7978.jpg', 'EO-ROSE-LA224-194455', 'silver', 'GINO ROSSI', 'Ladies Bag', '100% FIBER', '100% POLYESTER', '白色', '包包不可水洗竖版', '', '', '', 290, 0.00, 0.00, 0.00, 0.00, 0, '', '2025-12-17 00:00:00', 0, NULL, '', 0, '', NULL, NULL, 0, '', NULL, 1, NULL, 7789, NULL, '2025-12-18 18:07:47', '2025-12-18 18:11:50', '26SS', 'EO-ROSE-LA224-194455_silver');
INSERT INTO `accessories_purchase_contract` VALUES (112, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'gongchang', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, '2025-12-24 10:07:57', '2025-12-24 10:07:57', NULL, 'c54e60a7-6a3a-4baa-bf4f-79db28d037aa');

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
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '部门表（组织架构）' ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '字典数据表' ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '字典类型表' ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '员工表（用户扩展信息）' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of employees
-- ----------------------------

-- ----------------------------
-- Table structure for file_resource
-- ----------------------------
DROP TABLE IF EXISTS `file_resource`;
CREATE TABLE `file_resource`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `file_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始文件名',
  `file_key` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '对象存储中的文件key(包含路径)',
  `file_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '文件访问URL（MinIO预签名/反代URL）',
  `bucket` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '所属bucket名称',
  `file_size` bigint UNSIGNED NOT NULL COMMENT '文件大小（字节）',
  `file_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '文件MIME类型，如 image/png',
  `file_ext` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '文件扩展名，如 png/jpg/pdf',
  `biz_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '业务分类，如 avatar、order, product',
  `biz_id` bigint NULL DEFAULT NULL COMMENT '业务ID，如用户ID/订单ID',
  `is_deleted` tinyint NULL DEFAULT 0 COMMENT '是否删除（逻辑删）1=已删除',
  `creator_id` bigint NULL DEFAULT NULL COMMENT '上传用户ID',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '上传时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 121 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '对象存储文件元数据表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of file_resource
-- ----------------------------
INSERT INTO `file_resource` VALUES (2, 'wp2218362-macos-wallpapers.jpg', 'd09e319f-4059-43ac-ab9a-fe88afb1eda8-wp2218351-macos-wallpapers.jpg', '/api/files/download/d09e319f-4059-43ac-ab9a-fe88afb1eda8-wp2218351-macos-wallpapers.jpg', 'white-rs', 6201245, 'image/jpeg', 'jpg', '2', 2, 0, 2, '2025-12-09 10:20:09', '2025-12-11 14:44:29');
INSERT INTO `file_resource` VALUES (3, 'wp2218351-macos-wallpapers (1).jpg', '3c13c7f4-4ede-4ed6-985d-1cae1e566e41-wp2218351-macos-wallpapers (1).jpg', '/api/files/download/3c13c7f4-4ede-4ed6-985d-1cae1e566e41-wp2218351-macos-wallpapers (1).jpg', 'white-rs', 3502891, 'image/jpeg', 'jpg', '1', 1, 0, 2, '2025-12-09 16:56:58', '2025-12-09 16:56:58');
INSERT INTO `file_resource` VALUES (4, '20250828163003_755642 (1).jpg', 'afe07b22-84d3-4a42-89db-9b5e9e95f9ad-20250828163003_755642 (1).jpg', '/api/files/download/afe07b22-84d3-4a42-89db-9b5e9e95f9ad-20250828163003_755642 (1).jpg', 'white-rs', 207025, 'image/jpeg', 'jpg', '6', 6, 0, 1, '2025-12-09 17:02:58', '2025-12-09 17:02:58');
INSERT INTO `file_resource` VALUES (5, 'SCD2506121 CI&PL&BL 邮件(1).msg', 'd479ac1b-4433-433b-b54a-a550ac0f33d1-SCD2506121 CI&PL&BL 邮件(1).msg', '/api/files/download/d479ac1b-4433-433b-b54a-a550ac0f33d1-SCD2506121 CI&PL&BL 邮件(1).msg', 'white-rs', 13797376, 'application/octet-stream', 'msg', '6', 5, 0, 1, '2025-12-09 18:11:33', '2025-12-09 18:11:33');
INSERT INTO `file_resource` VALUES (6, '20250828163003_755642 (1).jpg', '5e762955-f662-4b8a-8526-f414aacbd719-20250828163003_755642 (1).jpg', '/api/files/download/5e762955-f662-4b8a-8526-f414aacbd719-20250828163003_755642 (1).jpg', 'white-rs', 207025, 'image/jpeg', 'jpg', '2', 1, 0, 2, '2025-12-10 17:55:02', '2025-12-10 17:55:02');
INSERT INTO `file_resource` VALUES (7, '20250828163003_755642 (1).jpg', 'f22fda98-a13d-42cc-a31d-b88d30be290e-20250828163003_755642 (1).jpg', '/api/files/download/f22fda98-a13d-42cc-a31d-b88d30be290e-20250828163003_755642 (1).jpg', 'white-rs', 207025, 'image/jpeg', 'jpg', '2', 1, 0, 2, '2025-12-10 18:02:25', '2025-12-10 18:02:25');
INSERT INTO `file_resource` VALUES (8, '20250828163003_755642 (1).jpg', '2a2ed3c1-b999-4bbd-9991-a4d882bdee65-20250828163003_755642 (1).jpg', '/api/files/download/2a2ed3c1-b999-4bbd-9991-a4d882bdee65-20250828163003_755642 (1).jpg', 'white-rs', 207025, 'image/jpeg', 'jpg', '2', 1, 0, 2, '2025-12-10 18:02:27', '2025-12-10 18:02:27');
INSERT INTO `file_resource` VALUES (9, '20250828163003_755642 (1).jpg', 'ec95152a-8740-47c4-8f08-64b6c422f48e-20250828163003_755642 (1).jpg', '/api/files/download/ec95152a-8740-47c4-8f08-64b6c422f48e-20250828163003_755642 (1).jpg', 'white-rs', 207025, 'image/jpeg', 'jpg', '2', 1, 0, 2, '2025-12-10 18:02:28', '2025-12-10 18:02:28');
INSERT INTO `file_resource` VALUES (10, '20250828163003_755642 (1).jpg', 'b7b82e08-796d-4f12-9832-cd91a295e8b3-20250828163003_755642 (1).jpg', '/api/files/download/b7b82e08-796d-4f12-9832-cd91a295e8b3-20250828163003_755642 (1).jpg', 'white-rs', 207025, 'image/jpeg', 'jpg', '2', 1, 0, 2, '2025-12-11 10:42:05', '2025-12-11 10:42:05');
INSERT INTO `file_resource` VALUES (11, 'wp2218351-macos-wallpapers (1).jpg', '389ee9df-28f6-4dec-8a9f-15a202da824e-wp2218351-macos-wallpapers (1).jpg', '/api/files/download/389ee9df-28f6-4dec-8a9f-15a202da824e-wp2218351-macos-wallpapers (1).jpg', 'white-rs', 3502891, 'image/jpeg', 'jpg', '5', 5, 0, 1, '2025-12-11 10:49:26', '2025-12-11 10:49:26');
INSERT INTO `file_resource` VALUES (12, '5e6f8fac22394d74b8c41625e6c737b04143.png', '63cfd3c8-a525-4090-b254-b3fcab569715-wp2218351-macos-wallpapers (1).jpg', '/api/files/download/63cfd3c8-a525-4090-b254-b3fcab569715-wp2218351-macos-wallpapers (1).jpg', 'white-rs', 2363828, 'image/png', 'png', '1', 1, 0, 1, '2025-12-11 11:02:10', '2025-12-11 15:03:28');
INSERT INTO `file_resource` VALUES (13, 'wp7510702-macos-high-sierra-wallpapers.jpg', '9f8d954f-e96c-4cf1-aec5-46b070a2f7d5-wp2218351-macos-wallpapers (1).jpg', '/api/files/download/9f8d954f-e96c-4cf1-aec5-46b070a2f7d5-wp2218351-macos-wallpapers (1).jpg', 'white-rs', 314509, 'image/jpeg', 'jpg', '1', 1, 0, 1, '2025-12-11 11:08:22', '2025-12-11 15:04:54');
INSERT INTO `file_resource` VALUES (14, '20241018095507_3392107.jpg', '9fc9004b-899e-49db-9517-af5c17144d4b-20241018095507_3392107.jpg', '/api/files/download/9fc9004b-899e-49db-9517-af5c17144d4b-20241018095507_3392107.jpg', 'white-rs', 276335, 'image/jpeg', 'jpg', '1', 1, 0, 1, '2025-12-11 14:30:53', '2025-12-11 14:30:53');
INSERT INTO `file_resource` VALUES (15, '20250828163003_755642 (1).jpg', 'fdb8b7d0-1e5f-45fc-9d57-7507fff79ca4-20250828163003_755642 (1).jpg', '/api/files/download/fdb8b7d0-1e5f-45fc-9d57-7507fff79ca4-20250828163003_755642 (1).jpg', 'white-rs', 207025, 'image/jpeg', 'jpg', '1', 1, 0, 1, '2025-12-11 14:48:53', '2025-12-11 14:48:53');
INSERT INTO `file_resource` VALUES (16, 'wp2218351-macos-wallpapers (1).jpg', '708bbcc8-c5cb-4f46-869d-f69955eb3e81-wp2218351-macos-wallpapers (1).jpg', '/api/files/download/708bbcc8-c5cb-4f46-869d-f69955eb3e81-wp2218351-macos-wallpapers (1).jpg', 'white-rs', 3502891, 'image/jpeg', 'jpg', '1', 1, 0, 1, '2025-12-11 15:03:04', '2025-12-11 15:03:04');
INSERT INTO `file_resource` VALUES (19, 'Snipaste_2025-12-02_17-18-17.png', 'b99654fd-f7f1-42aa-9962-b73911b1af51-Snipaste_2025-12-02_17-18-17.png', '/api/files/download/b99654fd-f7f1-42aa-9962-b73911b1af51-Snipaste_2025-12-02_17-18-17.png', 'white-rs', 23944, 'image/png', 'png', '1', 1, 0, 1, '2025-12-12 09:33:09', '2025-12-12 09:33:09');
INSERT INTO `file_resource` VALUES (20, 'Snipaste_2025-12-01_09-29-12.png', 'cad41569-efc0-4a46-9adb-46c7c1af2d7a-Snipaste_2025-12-01_09-29-12.png', '/api/files/download/cad41569-efc0-4a46-9adb-46c7c1af2d7a-Snipaste_2025-12-01_09-29-12.png', 'white-rs', 2631, 'image/png', 'png', '1', 1, 0, 1, '2025-12-12 18:30:20', '2025-12-12 18:30:20');
INSERT INTO `file_resource` VALUES (21, 'imported_image_38758417-b323-4994-9ba8-f3d9d44a3cbe.jpg', '596d1451-599d-47a3-a03a-f5f79deff81d-imported_image_38758417-b323-4994-9ba8-f3d9d44a3cbe.jpg', '/api/files/download/596d1451-599d-47a3-a03a-f5f79deff81d-imported_image_38758417-b323-4994-9ba8-f3d9d44a3cbe.jpg', 'white-rs', 946, 'image/png', 'jpg', 'contract_image', 0, 0, 0, '2025-12-15 09:24:23', '2025-12-15 09:24:23');
INSERT INTO `file_resource` VALUES (22, 'imported_image_4e243531-f7e3-4910-9679-13e2b07c706d.jpg', '2a58d63b-4a00-4e66-aec2-b0a4d5d1cb72-imported_image_4e243531-f7e3-4910-9679-13e2b07c706d.jpg', '/api/files/download/2a58d63b-4a00-4e66-aec2-b0a4d5d1cb72-imported_image_4e243531-f7e3-4910-9679-13e2b07c706d.jpg', 'white-rs', 946, 'image/png', 'jpg', 'contract_image', 0, 0, 0, '2025-12-15 09:25:19', '2025-12-15 09:25:19');
INSERT INTO `file_resource` VALUES (23, 'imported_image_f2764691-c69d-498c-8dfc-cc3ab035af6d.jpg', 'e39dcf64-2574-4fbe-8010-9f6d58dc99fa-imported_image_f2764691-c69d-498c-8dfc-cc3ab035af6d.jpg', '/api/files/download/e39dcf64-2574-4fbe-8010-9f6d58dc99fa-imported_image_f2764691-c69d-498c-8dfc-cc3ab035af6d.jpg', 'white-rs', 946, 'image/png', 'jpg', 'contract_image', 0, 0, 0, '2025-12-15 09:38:41', '2025-12-15 09:38:41');
INSERT INTO `file_resource` VALUES (24, 'imported_image_5ee79efc-5fe3-4a47-bcaf-e0435452e2c0.jpg', '9b5241c0-5be8-404d-b252-b864610adc25-imported_image_5ee79efc-5fe3-4a47-bcaf-e0435452e2c0.jpg', '/api/files/download/9b5241c0-5be8-404d-b252-b864610adc25-imported_image_5ee79efc-5fe3-4a47-bcaf-e0435452e2c0.jpg', 'white-rs', 946, 'image/png', 'jpg', 'contract_image', 0, 0, 0, '2025-12-15 09:57:48', '2025-12-15 09:57:48');
INSERT INTO `file_resource` VALUES (25, 'imported_image_f1a096a3-49ad-4109-9a85-d7640a8390fd.jpg', '957f177b-39c6-45ca-b28e-eb16e98089cd-imported_image_f1a096a3-49ad-4109-9a85-d7640a8390fd.jpg', '/api/files/download/957f177b-39c6-45ca-b28e-eb16e98089cd-imported_image_f1a096a3-49ad-4109-9a85-d7640a8390fd.jpg', 'white-rs', 946, 'image/png', 'jpg', 'contract_image', 0, 0, 0, '2025-12-15 09:57:48', '2025-12-15 09:57:48');
INSERT INTO `file_resource` VALUES (26, 'imported_image_d4f1463d-1fbf-47be-b734-57fc12022d07.jpg', '760d0d63-c329-4894-9665-0af9dac4125f-imported_image_d4f1463d-1fbf-47be-b734-57fc12022d07.jpg', '/api/files/download/760d0d63-c329-4894-9665-0af9dac4125f-imported_image_d4f1463d-1fbf-47be-b734-57fc12022d07.jpg', 'white-rs', 9589, 'image/png', 'jpg', 'contract_image', 0, 0, 0, '2025-12-15 09:57:48', '2025-12-15 09:57:48');
INSERT INTO `file_resource` VALUES (27, 'imported_image_d2d62fc8-2f31-45a2-8867-32391c8660b9.jpg', 'c215ae76-514c-47fd-af2f-795777836914-imported_image_d2d62fc8-2f31-45a2-8867-32391c8660b9.jpg', '/api/files/download/c215ae76-514c-47fd-af2f-795777836914-imported_image_d2d62fc8-2f31-45a2-8867-32391c8660b9.jpg', 'white-rs', 9811, 'image/png', 'jpg', 'contract_image', 0, 0, 0, '2025-12-15 09:57:48', '2025-12-15 09:57:48');
INSERT INTO `file_resource` VALUES (28, 'imported_image_24296331-1e49-46fa-bda5-a17392bc4724.jpg', '86316081-0662-4456-a87d-17cc1081ed8a-imported_image_24296331-1e49-46fa-bda5-a17392bc4724.jpg', '/api/files/download/86316081-0662-4456-a87d-17cc1081ed8a-imported_image_24296331-1e49-46fa-bda5-a17392bc4724.jpg', 'white-rs', 946, 'image/png', 'jpg', 'contract_image', 0, 0, 0, '2025-12-15 11:03:56', '2025-12-15 11:03:56');
INSERT INTO `file_resource` VALUES (29, 'imported_image_5e849db3-9cd8-4d51-92e1-e3f9254fcf02.jpg', '17ea7963-8e27-47dc-9244-f1cd634f0c00-imported_image_5e849db3-9cd8-4d51-92e1-e3f9254fcf02.jpg', '/api/files/download/17ea7963-8e27-47dc-9244-f1cd634f0c00-imported_image_5e849db3-9cd8-4d51-92e1-e3f9254fcf02.jpg', 'white-rs', 946, 'image/png', 'jpg', 'contract_image', 0, 0, 0, '2025-12-15 11:03:56', '2025-12-15 11:03:56');
INSERT INTO `file_resource` VALUES (30, 'imported_image_8821a2d4-8980-4a9e-a0cb-b7e44336255c.jpg', '0d807479-283c-4be2-9f0a-345c34328df0-imported_image_8821a2d4-8980-4a9e-a0cb-b7e44336255c.jpg', '/api/files/download/0d807479-283c-4be2-9f0a-345c34328df0-imported_image_8821a2d4-8980-4a9e-a0cb-b7e44336255c.jpg', 'white-rs', 9589, 'image/png', 'jpg', 'contract_image', 0, 0, 0, '2025-12-15 11:03:56', '2025-12-15 11:03:56');
INSERT INTO `file_resource` VALUES (31, 'imported_image_b7f2f05d-f296-4a98-966e-f7f0066d5f16.jpg', '68cde74e-0179-4f72-b767-ed0bd8caae5c-imported_image_b7f2f05d-f296-4a98-966e-f7f0066d5f16.jpg', '/api/files/download/68cde74e-0179-4f72-b767-ed0bd8caae5c-imported_image_b7f2f05d-f296-4a98-966e-f7f0066d5f16.jpg', 'white-rs', 9811, 'image/png', 'jpg', 'contract_image', 0, 0, 0, '2025-12-15 11:03:56', '2025-12-15 11:03:56');
INSERT INTO `file_resource` VALUES (32, 'imported_image_9aa200ee-de8f-421a-b99a-b6108809d056.jpg', '35b1a43a-ce22-4482-8625-95e9d67f58e6-imported_image_9aa200ee-de8f-421a-b99a-b6108809d056.jpg', '/api/files/download/35b1a43a-ce22-4482-8625-95e9d67f58e6-imported_image_9aa200ee-de8f-421a-b99a-b6108809d056.jpg', 'white-rs', 946, 'image/png', 'jpg', 'contract_image', 0, 0, 0, '2025-12-15 11:31:41', '2025-12-15 11:31:41');
INSERT INTO `file_resource` VALUES (33, 'imported_image_2f0e55f1-78f1-47cb-bfae-79ed90e44390.jpg', 'a6c1170b-658d-4b3d-9f6b-093cfb9ae180-imported_image_2f0e55f1-78f1-47cb-bfae-79ed90e44390.jpg', '/api/files/download/a6c1170b-658d-4b3d-9f6b-093cfb9ae180-imported_image_2f0e55f1-78f1-47cb-bfae-79ed90e44390.jpg', 'white-rs', 946, 'image/png', 'jpg', 'contract_image', 0, 0, 0, '2025-12-15 11:31:41', '2025-12-15 11:31:41');
INSERT INTO `file_resource` VALUES (34, 'imported_image_e934f59e-9f27-4ad7-9bad-278a3a162171.jpg', '42b81378-4a9f-460e-b1b4-23f130d68ee6-imported_image_e934f59e-9f27-4ad7-9bad-278a3a162171.jpg', '/api/files/download/42b81378-4a9f-460e-b1b4-23f130d68ee6-imported_image_e934f59e-9f27-4ad7-9bad-278a3a162171.jpg', 'white-rs', 9589, 'image/png', 'jpg', 'contract_image', 0, 0, 0, '2025-12-15 11:31:41', '2025-12-15 11:31:41');
INSERT INTO `file_resource` VALUES (35, 'imported_image_65159588-5a46-409f-aaec-7649adbcd644.jpg', 'c45b8004-34a9-41e6-9926-29e0a955e8c2-imported_image_65159588-5a46-409f-aaec-7649adbcd644.jpg', '/api/files/download/c45b8004-34a9-41e6-9926-29e0a955e8c2-imported_image_65159588-5a46-409f-aaec-7649adbcd644.jpg', 'white-rs', 9811, 'image/png', 'jpg', 'contract_image', 0, 0, 0, '2025-12-15 11:31:41', '2025-12-15 11:31:41');
INSERT INTO `file_resource` VALUES (36, 'imported_image_133ced83-65cf-4cca-8095-2d991a85f8a9.jpg', '778a6162-8e7b-4a22-b862-cd042f6381fa-imported_image_133ced83-65cf-4cca-8095-2d991a85f8a9.jpg', '/api/files/download/778a6162-8e7b-4a22-b862-cd042f6381fa-imported_image_133ced83-65cf-4cca-8095-2d991a85f8a9.jpg', 'white-rs', 946, 'image/png', 'jpg', 'contract_image', 0, 0, 0, '2025-12-15 15:55:46', '2025-12-15 15:55:46');
INSERT INTO `file_resource` VALUES (37, 'imported_image_5bfad136-267a-46f6-89c1-01b5b6502881.jpg', '25574ba7-6b62-4f77-bb4d-f4855dacf779-imported_image_5bfad136-267a-46f6-89c1-01b5b6502881.jpg', '/api/files/download/25574ba7-6b62-4f77-bb4d-f4855dacf779-imported_image_5bfad136-267a-46f6-89c1-01b5b6502881.jpg', 'white-rs', 946, 'image/png', 'jpg', 'contract_image', 0, 0, 0, '2025-12-15 15:55:46', '2025-12-15 15:55:46');
INSERT INTO `file_resource` VALUES (38, 'imported_image_fe774963-d37f-4c87-a3c5-5b784a86f888.jpg', '7749a416-bd39-4cbf-8d9d-90a131caccee-imported_image_fe774963-d37f-4c87-a3c5-5b784a86f888.jpg', '/api/files/download/7749a416-bd39-4cbf-8d9d-90a131caccee-imported_image_fe774963-d37f-4c87-a3c5-5b784a86f888.jpg', 'white-rs', 9589, 'image/png', 'jpg', 'contract_image', 0, 0, 0, '2025-12-15 15:55:46', '2025-12-15 15:55:46');
INSERT INTO `file_resource` VALUES (39, 'imported_image_81b8e845-2790-4da6-92df-ca5617f49768.jpg', '4937c046-563e-456e-9090-e758a6a5e6a2-imported_image_81b8e845-2790-4da6-92df-ca5617f49768.jpg', '/api/files/download/4937c046-563e-456e-9090-e758a6a5e6a2-imported_image_81b8e845-2790-4da6-92df-ca5617f49768.jpg', 'white-rs', 9811, 'image/png', 'jpg', 'contract_image', 0, 0, 0, '2025-12-15 15:55:46', '2025-12-15 15:55:46');
INSERT INTO `file_resource` VALUES (40, 'imported_image_61b30f40-ae3d-461c-8e2a-2560adaf2454.jpg', '9a80ede6-2bdb-446c-a984-dfa58cfc2f09-imported_image_61b30f40-ae3d-461c-8e2a-2560adaf2454.jpg', '/api/files/download/9a80ede6-2bdb-446c-a984-dfa58cfc2f09-imported_image_61b30f40-ae3d-461c-8e2a-2560adaf2454.jpg', 'white-rs', 946, 'image/png', 'jpg', 'contract_image', 0, 0, 0, '2025-12-15 16:35:47', '2025-12-15 16:35:47');
INSERT INTO `file_resource` VALUES (41, 'imported_image_0e5568db-b6ef-4f7e-96d8-d5b68fe1d967.jpg', '520481b3-2312-412f-a01c-cf94c88aebcc-imported_image_0e5568db-b6ef-4f7e-96d8-d5b68fe1d967.jpg', '/api/files/download/520481b3-2312-412f-a01c-cf94c88aebcc-imported_image_0e5568db-b6ef-4f7e-96d8-d5b68fe1d967.jpg', 'white-rs', 946, 'image/png', 'jpg', 'contract_image', 0, 0, 0, '2025-12-15 16:35:47', '2025-12-15 16:35:47');
INSERT INTO `file_resource` VALUES (42, 'imported_image_a2fd1024-bac9-4444-b1b2-172b560be2bf.jpg', '2c0c0fd6-e29f-491e-bf46-c9a1bfa65412-imported_image_a2fd1024-bac9-4444-b1b2-172b560be2bf.jpg', '/api/files/download/2c0c0fd6-e29f-491e-bf46-c9a1bfa65412-imported_image_a2fd1024-bac9-4444-b1b2-172b560be2bf.jpg', 'white-rs', 9589, 'image/png', 'jpg', 'contract_image', 0, 0, 0, '2025-12-15 16:35:47', '2025-12-15 16:35:47');
INSERT INTO `file_resource` VALUES (43, 'imported_image_12715c1d-d644-4d40-bcdb-48b29cdf4404.jpg', '39f00e16-22cd-4ff5-99ee-3fb8f5d0b067-imported_image_12715c1d-d644-4d40-bcdb-48b29cdf4404.jpg', '/api/files/download/39f00e16-22cd-4ff5-99ee-3fb8f5d0b067-imported_image_12715c1d-d644-4d40-bcdb-48b29cdf4404.jpg', 'white-rs', 9811, 'image/png', 'jpg', 'contract_image', 0, 0, 0, '2025-12-15 16:35:47', '2025-12-15 16:35:47');
INSERT INTO `file_resource` VALUES (44, 'imported_image_c9a3ca38-0f3a-488c-a0bc-6817febfb281.jpg', '84de5253-b4b4-4bc5-807a-958a7ad889cb-imported_image_c9a3ca38-0f3a-488c-a0bc-6817febfb281.jpg', '/api/files/download/84de5253-b4b4-4bc5-807a-958a7ad889cb-imported_image_c9a3ca38-0f3a-488c-a0bc-6817febfb281.jpg', 'white-rs', 946, 'image/png', 'jpg', 'contract_image', 0, 0, 0, '2025-12-15 16:36:18', '2025-12-15 16:36:18');
INSERT INTO `file_resource` VALUES (45, 'imported_image_836434bc-3644-4244-b2ad-8aec67f6a182.jpg', '9b8b8d0e-4489-49ba-81ff-6845c5d65119-imported_image_836434bc-3644-4244-b2ad-8aec67f6a182.jpg', '/api/files/download/9b8b8d0e-4489-49ba-81ff-6845c5d65119-imported_image_836434bc-3644-4244-b2ad-8aec67f6a182.jpg', 'white-rs', 946, 'image/png', 'jpg', 'contract_image', 0, 0, 0, '2025-12-15 16:36:18', '2025-12-15 16:36:18');
INSERT INTO `file_resource` VALUES (46, 'imported_image_e68a50f3-3740-4e62-aaed-0f3ca4979906.jpg', 'e76510f2-e3f3-4693-b7e3-3bd928d88982-imported_image_e68a50f3-3740-4e62-aaed-0f3ca4979906.jpg', '/api/files/download/e76510f2-e3f3-4693-b7e3-3bd928d88982-imported_image_e68a50f3-3740-4e62-aaed-0f3ca4979906.jpg', 'white-rs', 9589, 'image/png', 'jpg', 'contract_image', 0, 0, 0, '2025-12-15 16:36:18', '2025-12-15 16:36:18');
INSERT INTO `file_resource` VALUES (47, 'imported_image_a8a10cf1-8f8b-467f-8fec-05e2e25db9b8.jpg', '059bd751-9ce3-4a9c-97e2-c9cf75a9d40d-imported_image_a8a10cf1-8f8b-467f-8fec-05e2e25db9b8.jpg', '/api/files/download/059bd751-9ce3-4a9c-97e2-c9cf75a9d40d-imported_image_a8a10cf1-8f8b-467f-8fec-05e2e25db9b8.jpg', 'white-rs', 9811, 'image/png', 'jpg', 'contract_image', 0, 0, 0, '2025-12-15 16:36:18', '2025-12-15 16:36:18');
INSERT INTO `file_resource` VALUES (49, 'imported_image_74ac198f-27d2-4340-8945-0738cde4ebec.jpg', 'a90d1bb0-32e7-46ee-bf65-420b6ad54861-imported_image_74ac198f-27d2-4340-8945-0738cde4ebec.jpg', '/api/files/download/a90d1bb0-32e7-46ee-bf65-420b6ad54861-imported_image_74ac198f-27d2-4340-8945-0738cde4ebec.jpg', 'white-rs', 946, 'image/png', 'jpg', 'contract_image', 0, 0, 0, '2025-12-15 18:37:55', '2025-12-15 18:37:55');
INSERT INTO `file_resource` VALUES (50, 'imported_image_28e562da-522c-4002-90ab-062584fcb1f9.jpg', '07df1039-befd-4d16-a59b-02b56ea3cdd9-imported_image_28e562da-522c-4002-90ab-062584fcb1f9.jpg', '/api/files/download/07df1039-befd-4d16-a59b-02b56ea3cdd9-imported_image_28e562da-522c-4002-90ab-062584fcb1f9.jpg', 'white-rs', 946, 'image/png', 'jpg', 'contract_image', 0, 0, 0, '2025-12-15 18:37:55', '2025-12-15 18:37:55');
INSERT INTO `file_resource` VALUES (51, 'imported_image_e21ede30-2cb4-4854-9ac4-898cb62cc028.jpg', '178310fa-623f-412c-be63-743c5fdb35cb-imported_image_e21ede30-2cb4-4854-9ac4-898cb62cc028.jpg', '/api/files/download/178310fa-623f-412c-be63-743c5fdb35cb-imported_image_e21ede30-2cb4-4854-9ac4-898cb62cc028.jpg', 'white-rs', 9589, 'image/png', 'jpg', 'contract_image', 0, 0, 0, '2025-12-15 18:37:55', '2025-12-15 18:37:55');
INSERT INTO `file_resource` VALUES (52, 'imported_image_686962a0-ae9a-47ae-8873-3f0142979e25.jpg', '36e88f09-19a3-4ebd-acaa-7afe28a7f3d9-imported_image_686962a0-ae9a-47ae-8873-3f0142979e25.jpg', '/api/files/download/36e88f09-19a3-4ebd-acaa-7afe28a7f3d9-imported_image_686962a0-ae9a-47ae-8873-3f0142979e25.jpg', 'white-rs', 9811, 'image/png', 'jpg', 'contract_image', 0, 0, 0, '2025-12-15 18:37:55', '2025-12-15 18:37:55');
INSERT INTO `file_resource` VALUES (53, 'imported_image_5dbd4a16-d88d-45d2-8b31-11fa592ba543.jpg', '52520322-1df0-4f2b-9dce-3284c174dbee-imported_image_5dbd4a16-d88d-45d2-8b31-11fa592ba543.jpg', '/api/files/download/52520322-1df0-4f2b-9dce-3284c174dbee-imported_image_5dbd4a16-d88d-45d2-8b31-11fa592ba543.jpg', 'white-rs', 946, 'image/png', 'jpg', 'contract_image', 0, 0, 0, '2025-12-16 09:59:47', '2025-12-16 09:59:47');
INSERT INTO `file_resource` VALUES (54, 'imported_image_ff93b6df-024b-47a3-b929-f15034d56e44.jpg', 'cefdc736-4e52-4250-a6ee-3ae4d103663f-imported_image_ff93b6df-024b-47a3-b929-f15034d56e44.jpg', '/api/files/download/cefdc736-4e52-4250-a6ee-3ae4d103663f-imported_image_ff93b6df-024b-47a3-b929-f15034d56e44.jpg', 'white-rs', 946, 'image/png', 'jpg', 'contract_image', 0, 0, 0, '2025-12-16 09:59:47', '2025-12-16 09:59:47');
INSERT INTO `file_resource` VALUES (55, 'imported_image_95bf0c6a-316f-418a-b19f-67bf218b2d71.jpg', '8118343a-ab79-43ae-ac5e-d5efbe53ae50-imported_image_95bf0c6a-316f-418a-b19f-67bf218b2d71.jpg', '/api/files/download/8118343a-ab79-43ae-ac5e-d5efbe53ae50-imported_image_95bf0c6a-316f-418a-b19f-67bf218b2d71.jpg', 'white-rs', 9589, 'image/png', 'jpg', 'contract_image', 0, 0, 0, '2025-12-16 09:59:47', '2025-12-16 09:59:47');
INSERT INTO `file_resource` VALUES (56, 'imported_image_25f003f4-a9f2-4185-9d49-ce8dd87adfb7.jpg', 'cd2786be-f180-479e-aa79-3147909c4e7f-imported_image_25f003f4-a9f2-4185-9d49-ce8dd87adfb7.jpg', '/api/files/download/cd2786be-f180-479e-aa79-3147909c4e7f-imported_image_25f003f4-a9f2-4185-9d49-ce8dd87adfb7.jpg', 'white-rs', 9811, 'image/png', 'jpg', 'contract_image', 0, 0, 0, '2025-12-16 09:59:47', '2025-12-16 09:59:47');
INSERT INTO `file_resource` VALUES (57, 'imported_image_c30027d5-c789-4882-bcfe-f04510ed9aaa.jpg', 'b4862dd6-55a1-428e-8c35-3e6365d3c6c0-imported_image_c30027d5-c789-4882-bcfe-f04510ed9aaa.jpg', '/api/files/download/b4862dd6-55a1-428e-8c35-3e6365d3c6c0-imported_image_c30027d5-c789-4882-bcfe-f04510ed9aaa.jpg', 'white-rs', 946, 'image/png', 'jpg', 'contract_image', 0, 0, 0, '2025-12-16 11:40:36', '2025-12-16 11:40:36');
INSERT INTO `file_resource` VALUES (58, 'imported_image_02a34081-fb55-43e8-aa2a-b9f0612a8f32.jpg', '21b1145c-394a-4e6b-8c8f-6d6b7c12ef62-imported_image_02a34081-fb55-43e8-aa2a-b9f0612a8f32.jpg', '/api/files/download/21b1145c-394a-4e6b-8c8f-6d6b7c12ef62-imported_image_02a34081-fb55-43e8-aa2a-b9f0612a8f32.jpg', 'white-rs', 946, 'image/png', 'jpg', 'contract_image', 0, 0, 0, '2025-12-16 11:40:36', '2025-12-16 11:40:36');
INSERT INTO `file_resource` VALUES (59, 'imported_image_829d8c68-038c-4530-8e65-082a6b2904af.jpg', 'e16ad738-3555-4ac5-a03a-79a4add8f165-imported_image_829d8c68-038c-4530-8e65-082a6b2904af.jpg', '/api/files/download/e16ad738-3555-4ac5-a03a-79a4add8f165-imported_image_829d8c68-038c-4530-8e65-082a6b2904af.jpg', 'white-rs', 9589, 'image/png', 'jpg', 'contract_image', 0, 0, 0, '2025-12-16 11:40:36', '2025-12-16 11:40:36');
INSERT INTO `file_resource` VALUES (60, 'imported_image_86c7b128-d239-4b76-9cee-cefc9453b588.jpg', '4a52ec32-1e38-4e26-a95d-1bf79b99a6cb-imported_image_86c7b128-d239-4b76-9cee-cefc9453b588.jpg', '/api/files/download/4a52ec32-1e38-4e26-a95d-1bf79b99a6cb-imported_image_86c7b128-d239-4b76-9cee-cefc9453b588.jpg', 'white-rs', 9811, 'image/png', 'jpg', 'contract_image', 0, 0, 0, '2025-12-16 11:40:36', '2025-12-16 11:40:36');
INSERT INTO `file_resource` VALUES (61, 'imported_image_958a8703-1300-4cb5-89ef-dd3c9449331f.jpg', '7568c60b-4aab-459e-a941-134af9266796-imported_image_958a8703-1300-4cb5-89ef-dd3c9449331f.jpg', '/api/files/download/7568c60b-4aab-459e-a941-134af9266796-imported_image_958a8703-1300-4cb5-89ef-dd3c9449331f.jpg', 'white-rs', 946, 'image/png', 'jpg', 'contract_image', 0, 0, 0, '2025-12-16 11:45:05', '2025-12-16 11:45:05');
INSERT INTO `file_resource` VALUES (62, 'imported_image_70c9e5a1-fd2a-4055-80b0-166f9fd785f7.jpg', '6ce67479-1169-4f81-9dbf-2eac89e23375-imported_image_70c9e5a1-fd2a-4055-80b0-166f9fd785f7.jpg', '/api/files/download/6ce67479-1169-4f81-9dbf-2eac89e23375-imported_image_70c9e5a1-fd2a-4055-80b0-166f9fd785f7.jpg', 'white-rs', 946, 'image/png', 'jpg', 'contract_image', 0, 0, 0, '2025-12-16 11:45:05', '2025-12-16 11:45:05');
INSERT INTO `file_resource` VALUES (63, 'imported_image_eb2b25e4-4374-43b6-be1f-69472ca04ad4.jpg', '5ca9d1a5-49d1-4224-ab24-2c0506c79af5-imported_image_eb2b25e4-4374-43b6-be1f-69472ca04ad4.jpg', '/api/files/download/5ca9d1a5-49d1-4224-ab24-2c0506c79af5-imported_image_eb2b25e4-4374-43b6-be1f-69472ca04ad4.jpg', 'white-rs', 9589, 'image/png', 'jpg', 'contract_image', 0, 0, 0, '2025-12-16 11:45:05', '2025-12-16 11:45:05');
INSERT INTO `file_resource` VALUES (65, 'imported_image_61c888f1-9073-4b42-9e38-e455e169f9ff.jpg', '167d92a4-1c64-4dcc-86b1-3b249b712619-imported_image_61c888f1-9073-4b42-9e38-e455e169f9ff.jpg', '/api/files/download/167d92a4-1c64-4dcc-86b1-3b249b712619-imported_image_61c888f1-9073-4b42-9e38-e455e169f9ff.jpg', 'white-rs', 946, 'image/png', 'jpg', 'contract_image', 0, 0, 0, '2025-12-16 16:15:26', '2025-12-16 16:15:26');
INSERT INTO `file_resource` VALUES (66, 'imported_image_e319ab64-5c7e-46b0-9c71-1d466608155f.jpg', '45695d40-d6a1-4f08-b77f-01fc1dbf177c-imported_image_e319ab64-5c7e-46b0-9c71-1d466608155f.jpg', '/api/files/download/45695d40-d6a1-4f08-b77f-01fc1dbf177c-imported_image_e319ab64-5c7e-46b0-9c71-1d466608155f.jpg', 'white-rs', 946, 'image/png', 'jpg', 'contract_image', 0, 0, 0, '2025-12-16 16:15:26', '2025-12-16 16:15:26');
INSERT INTO `file_resource` VALUES (67, 'imported_image_90e413f6-b1e2-4899-b6ac-469ff25bc448.jpg', 'fee947e4-f603-4ce5-a1fd-d3d05f3e53a1-imported_image_90e413f6-b1e2-4899-b6ac-469ff25bc448.jpg', '/api/files/download/fee947e4-f603-4ce5-a1fd-d3d05f3e53a1-imported_image_90e413f6-b1e2-4899-b6ac-469ff25bc448.jpg', 'white-rs', 9589, 'image/png', 'jpg', 'contract_image', 0, 0, 0, '2025-12-16 16:15:26', '2025-12-16 16:15:26');
INSERT INTO `file_resource` VALUES (68, 'imported_image_fc2ee4c9-2702-4caf-bb12-77345947079b.jpg', '239c51ff-9cb6-46d5-b747-d41ab9a08be2-imported_image_fc2ee4c9-2702-4caf-bb12-77345947079b.jpg', '/api/files/download/239c51ff-9cb6-46d5-b747-d41ab9a08be2-imported_image_fc2ee4c9-2702-4caf-bb12-77345947079b.jpg', 'white-rs', 9811, 'image/png', 'jpg', 'contract_image', 0, 0, 0, '2025-12-16 16:15:26', '2025-12-16 16:15:26');
INSERT INTO `file_resource` VALUES (69, 'imported_image_7cf12996-3207-479f-8d4b-4ec7043af6cc.jpg', '4f6d609c-dabe-46fe-8a39-14d1b02dca20-imported_image_7cf12996-3207-479f-8d4b-4ec7043af6cc.jpg', '/api/files/download/4f6d609c-dabe-46fe-8a39-14d1b02dca20-imported_image_7cf12996-3207-479f-8d4b-4ec7043af6cc.jpg', 'white-rs', 946, 'image/png', 'jpg', 'contract_image', 0, 0, 0, '2025-12-16 16:18:52', '2025-12-16 16:18:52');
INSERT INTO `file_resource` VALUES (70, 'imported_image_d6fb8467-3bf4-4445-b6b9-453ce46bda0a.jpg', 'd239b224-4f24-4c6d-bb01-c672e91beaa1-imported_image_d6fb8467-3bf4-4445-b6b9-453ce46bda0a.jpg', '/api/files/download/d239b224-4f24-4c6d-bb01-c672e91beaa1-imported_image_d6fb8467-3bf4-4445-b6b9-453ce46bda0a.jpg', 'white-rs', 946, 'image/png', 'jpg', 'contract_image', 0, 0, 0, '2025-12-16 16:18:52', '2025-12-16 16:18:52');
INSERT INTO `file_resource` VALUES (71, 'imported_image_7493ef99-c634-4f7e-a001-198f22cec0ef.jpg', '15ff9211-3282-40af-94e7-b3ed06e3425a-imported_image_7493ef99-c634-4f7e-a001-198f22cec0ef.jpg', '/api/files/download/15ff9211-3282-40af-94e7-b3ed06e3425a-imported_image_7493ef99-c634-4f7e-a001-198f22cec0ef.jpg', 'white-rs', 9589, 'image/png', 'jpg', 'contract_image', 0, 0, 0, '2025-12-16 16:18:52', '2025-12-16 16:18:52');
INSERT INTO `file_resource` VALUES (72, 'imported_image_a1edbfb1-4f86-4042-bb13-3a5cb36e691c.jpg', 'd4d08762-b908-4bb7-8429-ad76fc620ee6-imported_image_a1edbfb1-4f86-4042-bb13-3a5cb36e691c.jpg', '/api/files/download/d4d08762-b908-4bb7-8429-ad76fc620ee6-imported_image_a1edbfb1-4f86-4042-bb13-3a5cb36e691c.jpg', 'white-rs', 9811, 'image/png', 'jpg', 'contract_image', 0, 0, 0, '2025-12-16 16:18:52', '2025-12-16 16:18:52');
INSERT INTO `file_resource` VALUES (73, 'imported_image_335ed307-65a2-4e4e-a197-481437de520a.jpg', 'ecfbf233-0f3e-42a0-ae76-be947f8fdc83-imported_image_335ed307-65a2-4e4e-a197-481437de520a.jpg', '/api/files/download/ecfbf233-0f3e-42a0-ae76-be947f8fdc83-imported_image_335ed307-65a2-4e4e-a197-481437de520a.jpg', 'white-rs', 946, 'image/png', 'jpg', 'contract_image', 0, 0, 0, '2025-12-16 16:19:53', '2025-12-16 16:19:53');
INSERT INTO `file_resource` VALUES (74, 'imported_image_54396627-fd9b-4e1b-ba9b-4c0cdabc22e7.jpg', 'bd19bb57-c1bb-4100-9278-250b3f9ac332-imported_image_54396627-fd9b-4e1b-ba9b-4c0cdabc22e7.jpg', '/api/files/download/bd19bb57-c1bb-4100-9278-250b3f9ac332-imported_image_54396627-fd9b-4e1b-ba9b-4c0cdabc22e7.jpg', 'white-rs', 946, 'image/png', 'jpg', 'contract_image', 0, 0, 0, '2025-12-16 16:19:53', '2025-12-16 16:19:53');
INSERT INTO `file_resource` VALUES (75, 'imported_image_0a9211ee-fc61-4180-8fc8-bbd272f134b6.jpg', 'ddef3ac6-4f2a-47c7-bd50-3e9b721b8b7f-imported_image_0a9211ee-fc61-4180-8fc8-bbd272f134b6.jpg', '/api/files/download/ddef3ac6-4f2a-47c7-bd50-3e9b721b8b7f-imported_image_0a9211ee-fc61-4180-8fc8-bbd272f134b6.jpg', 'white-rs', 9589, 'image/png', 'jpg', 'contract_image', 0, 0, 0, '2025-12-16 16:19:53', '2025-12-16 16:19:53');
INSERT INTO `file_resource` VALUES (76, 'imported_image_50cda7dc-5025-4922-9b0d-ce2c79e62f42.jpg', '16b67243-e612-47de-94bf-ed2d3b3ac6b8-imported_image_50cda7dc-5025-4922-9b0d-ce2c79e62f42.jpg', '/api/files/download/16b67243-e612-47de-94bf-ed2d3b3ac6b8-imported_image_50cda7dc-5025-4922-9b0d-ce2c79e62f42.jpg', 'white-rs', 9811, 'image/png', 'jpg', 'contract_image', 0, 0, 0, '2025-12-16 16:19:53', '2025-12-16 16:19:53');
INSERT INTO `file_resource` VALUES (77, 'Snipaste_2025-12-04_09-35-24.png', 'b1056826-d4a8-4207-ba2b-4290f7836de8-imported_image_57f471d3-b22b-423d-8e4d-4f59559dd050.jpg', '/api/files/download/b1056826-d4a8-4207-ba2b-4290f7836de8-imported_image_57f471d3-b22b-423d-8e4d-4f59559dd050.jpg', 'white-rs', 97194, 'image/png', 'png', 'contract_image', 0, 0, 0, '2025-12-16 16:21:04', '2025-12-16 17:06:04');
INSERT INTO `file_resource` VALUES (78, 'imported_image_3f94dcad-0266-4f21-8caf-19081bc9a254.jpg', 'b4cb1de7-7ec1-4c40-a45e-4ee9bcd99226-imported_image_3f94dcad-0266-4f21-8caf-19081bc9a254.jpg', '/api/files/download/b4cb1de7-7ec1-4c40-a45e-4ee9bcd99226-imported_image_3f94dcad-0266-4f21-8caf-19081bc9a254.jpg', 'white-rs', 946, 'image/png', 'jpg', 'contract_image', 0, 0, 0, '2025-12-16 16:21:04', '2025-12-16 16:21:04');
INSERT INTO `file_resource` VALUES (80, 'imported_image_4170c0ef-13ac-4f2e-acca-43d56b52d795.jpg', '3ee69476-61c7-4f27-9462-ce5d80d4c33b-imported_image_4170c0ef-13ac-4f2e-acca-43d56b52d795.jpg', '/api/files/download/3ee69476-61c7-4f27-9462-ce5d80d4c33b-imported_image_4170c0ef-13ac-4f2e-acca-43d56b52d795.jpg', 'white-rs', 9811, 'image/png', 'jpg', 'contract_image', 0, 0, 0, '2025-12-16 16:21:04', '2025-12-16 16:21:04');
INSERT INTO `file_resource` VALUES (81, 'Snipaste_2025-12-04_09-39-21.png', 'fcdf9811-0459-41cf-98f5-907dbb4ce3e1-imported_image_eed28798-c5d4-4111-9022-54f888afcb1d.jpg', '/api/files/download/fcdf9811-0459-41cf-98f5-907dbb4ce3e1-imported_image_eed28798-c5d4-4111-9022-54f888afcb1d.jpg', 'white-rs', 62224, 'image/png', 'png', 'contract_image', 0, 0, 0, '2025-12-17 18:48:50', '2025-12-23 11:24:46');
INSERT INTO `file_resource` VALUES (82, 'imported_image_b5e25fff-5121-4e08-b010-3306a310047a.jpg', '1a7d9a8c-70de-4638-b8f8-ffca0775e690-imported_image_b5e25fff-5121-4e08-b010-3306a310047a.jpg', '/api/files/download/1a7d9a8c-70de-4638-b8f8-ffca0775e690-imported_image_b5e25fff-5121-4e08-b010-3306a310047a.jpg', 'white-rs', 946, 'image/png', 'jpg', 'contract_image', 0, 0, 0, '2025-12-17 18:48:50', '2025-12-17 18:48:50');
INSERT INTO `file_resource` VALUES (83, 'imported_image_353de31f-56f2-4a6f-83f2-4b1e44e84dc6.jpg', '8562a343-0225-47c1-8cfc-200e958255bf-imported_image_353de31f-56f2-4a6f-83f2-4b1e44e84dc6.jpg', '/api/files/download/8562a343-0225-47c1-8cfc-200e958255bf-imported_image_353de31f-56f2-4a6f-83f2-4b1e44e84dc6.jpg', 'white-rs', 9589, 'image/png', 'jpg', 'contract_image', 0, 0, 0, '2025-12-17 18:48:50', '2025-12-17 18:48:50');
INSERT INTO `file_resource` VALUES (84, 'imported_image_9979212e-e8bb-45a1-990d-30a9d7c6197e.jpg', '18d6229f-b3a2-4b00-82af-75f2e04f6735-imported_image_9979212e-e8bb-45a1-990d-30a9d7c6197e.jpg', '/api/files/download/18d6229f-b3a2-4b00-82af-75f2e04f6735-imported_image_9979212e-e8bb-45a1-990d-30a9d7c6197e.jpg', 'white-rs', 9811, 'image/png', 'jpg', 'contract_image', 0, 0, 0, '2025-12-17 18:48:50', '2025-12-17 18:48:50');
INSERT INTO `file_resource` VALUES (85, 'imported_image_19892d58-82b4-4160-87c0-a5e5e0770a81.jpg', 'b0fb4a42-07e9-474d-9ccc-29c7a9c047ac-imported_image_19892d58-82b4-4160-87c0-a5e5e0770a81.jpg', '/api/files/download/b0fb4a42-07e9-474d-9ccc-29c7a9c047ac-imported_image_19892d58-82b4-4160-87c0-a5e5e0770a81.jpg', 'white-rs', 67730, 'image/jpeg', 'jpg', 'contract_image', 0, 0, 0, '2025-12-18 18:04:39', '2025-12-18 18:04:39');
INSERT INTO `file_resource` VALUES (86, 'imported_image_11574bed-d6d5-4679-a693-58e56197e5a9.jpg', '5b62b5a9-6c19-48d6-ad0d-9172b3518e3e-imported_image_11574bed-d6d5-4679-a693-58e56197e5a9.jpg', '/api/files/download/5b62b5a9-6c19-48d6-ad0d-9172b3518e3e-imported_image_11574bed-d6d5-4679-a693-58e56197e5a9.jpg', 'white-rs', 171788, 'image/png', 'jpg', 'contract_image', 0, 0, 0, '2025-12-18 18:04:39', '2025-12-18 18:04:39');
INSERT INTO `file_resource` VALUES (87, 'imported_image_4ea15273-8701-487a-b3bf-fef850ee82c6.jpg', 'a2797f1b-1221-44a9-96b0-ccfa541b8b37-imported_image_4ea15273-8701-487a-b3bf-fef850ee82c6.jpg', '/api/files/download/a2797f1b-1221-44a9-96b0-ccfa541b8b37-imported_image_4ea15273-8701-487a-b3bf-fef850ee82c6.jpg', 'white-rs', 103902, 'image/jpeg', 'jpg', 'contract_image', 0, 0, 0, '2025-12-18 18:04:39', '2025-12-18 18:04:39');
INSERT INTO `file_resource` VALUES (88, 'imported_image_d957f176-9a2a-46b8-bc37-5c2cdfd13204.jpg', '3a60f67f-5fab-44d6-9510-a0f2e65be7a1-imported_image_d957f176-9a2a-46b8-bc37-5c2cdfd13204.jpg', '/api/files/download/3a60f67f-5fab-44d6-9510-a0f2e65be7a1-imported_image_d957f176-9a2a-46b8-bc37-5c2cdfd13204.jpg', 'white-rs', 61036, 'image/jpeg', 'jpg', 'contract_image', 0, 0, 0, '2025-12-18 18:04:39', '2025-12-18 18:04:39');
INSERT INTO `file_resource` VALUES (89, 'imported_image_66ff8ab0-995e-4508-b6e3-7ad9cd400a3c.jpg', '93063889-222d-4ce6-b3cf-5856c7a2de8e-imported_image_66ff8ab0-995e-4508-b6e3-7ad9cd400a3c.jpg', '/api/files/download/93063889-222d-4ce6-b3cf-5856c7a2de8e-imported_image_66ff8ab0-995e-4508-b6e3-7ad9cd400a3c.jpg', 'white-rs', 57271, 'image/jpeg', 'jpg', 'contract_image', 0, 0, 0, '2025-12-18 18:04:39', '2025-12-18 18:04:39');
INSERT INTO `file_resource` VALUES (90, 'imported_image_e861e7ac-d181-401a-9187-4e108255d163.jpg', 'f6c9e356-925e-4c14-a3d2-bb969b42c0f0-imported_image_e861e7ac-d181-401a-9187-4e108255d163.jpg', '/api/files/download/f6c9e356-925e-4c14-a3d2-bb969b42c0f0-imported_image_e861e7ac-d181-401a-9187-4e108255d163.jpg', 'white-rs', 105131, 'image/png', 'jpg', 'contract_image', 0, 0, 0, '2025-12-18 18:04:39', '2025-12-18 18:04:39');
INSERT INTO `file_resource` VALUES (91, 'imported_image_054a9d94-864d-462d-bc90-7e8cc20682f0.jpg', 'e48392c6-e2b3-454e-8888-affa1427fd9b-imported_image_054a9d94-864d-462d-bc90-7e8cc20682f0.jpg', '/api/files/download/e48392c6-e2b3-454e-8888-affa1427fd9b-imported_image_054a9d94-864d-462d-bc90-7e8cc20682f0.jpg', 'white-rs', 75666, 'image/jpeg', 'jpg', 'contract_image', 0, 0, 0, '2025-12-18 18:04:39', '2025-12-18 18:04:39');
INSERT INTO `file_resource` VALUES (92, 'imported_image_a4e28dc6-6305-42f7-bfaa-9fd070bd3a6e.jpg', '569c26ce-9ea7-429f-bf1c-89005ed6e2ca-imported_image_a4e28dc6-6305-42f7-bfaa-9fd070bd3a6e.jpg', '/api/files/download/569c26ce-9ea7-429f-bf1c-89005ed6e2ca-imported_image_a4e28dc6-6305-42f7-bfaa-9fd070bd3a6e.jpg', 'white-rs', 45130, 'image/jpeg', 'jpg', 'contract_image', 0, 0, 0, '2025-12-18 18:04:39', '2025-12-18 18:04:39');
INSERT INTO `file_resource` VALUES (93, 'imported_image_7a7bebea-e3b4-47bb-85ad-0418435f6b1d.jpg', '062d0c1f-ad8f-4e63-9055-018cac5b046b-imported_image_7a7bebea-e3b4-47bb-85ad-0418435f6b1d.jpg', '/api/files/download/062d0c1f-ad8f-4e63-9055-018cac5b046b-imported_image_7a7bebea-e3b4-47bb-85ad-0418435f6b1d.jpg', 'white-rs', 68339, 'image/jpeg', 'jpg', 'contract_image', 0, 0, 0, '2025-12-18 18:04:39', '2025-12-18 18:04:39');
INSERT INTO `file_resource` VALUES (94, 'imported_image_3a42fa46-a2ce-4518-a327-4a31e75f2451.jpg', 'edbd89a0-4c4b-4fe8-b249-a8dc14e73394-imported_image_3a42fa46-a2ce-4518-a327-4a31e75f2451.jpg', '/api/files/download/edbd89a0-4c4b-4fe8-b249-a8dc14e73394-imported_image_3a42fa46-a2ce-4518-a327-4a31e75f2451.jpg', 'white-rs', 139812, 'image/png', 'jpg', 'contract_image', 0, 0, 0, '2025-12-18 18:04:39', '2025-12-18 18:04:39');
INSERT INTO `file_resource` VALUES (95, 'imported_image_94393e53-49f7-4061-a997-55c09115b62f.jpg', 'd90e34db-1e0d-4681-bbd7-9ca587c47220-imported_image_94393e53-49f7-4061-a997-55c09115b62f.jpg', '/api/files/download/d90e34db-1e0d-4681-bbd7-9ca587c47220-imported_image_94393e53-49f7-4061-a997-55c09115b62f.jpg', 'white-rs', 94418, 'image/jpeg', 'jpg', 'contract_image', 0, 0, 0, '2025-12-18 18:04:39', '2025-12-18 18:04:39');
INSERT INTO `file_resource` VALUES (96, 'imported_image_ec903117-b238-425d-9d11-8764fb5b71f5.jpg', '3a7c62cf-7247-4672-af84-2ec818373cd8-imported_image_ec903117-b238-425d-9d11-8764fb5b71f5.jpg', '/api/files/download/3a7c62cf-7247-4672-af84-2ec818373cd8-imported_image_ec903117-b238-425d-9d11-8764fb5b71f5.jpg', 'white-rs', 55597, 'image/jpeg', 'jpg', 'contract_image', 0, 0, 0, '2025-12-18 18:04:39', '2025-12-18 18:04:39');
INSERT INTO `file_resource` VALUES (97, 'imported_image_9fa775bb-4950-4477-a38c-673a5fe9e673.jpg', 'be055c67-6e30-4e3b-a5e1-54dc8632b3d8-imported_image_9fa775bb-4950-4477-a38c-673a5fe9e673.jpg', '/api/files/download/be055c67-6e30-4e3b-a5e1-54dc8632b3d8-imported_image_9fa775bb-4950-4477-a38c-673a5fe9e673.jpg', 'white-rs', 67730, 'image/jpeg', 'jpg', 'contract_image', 0, 0, 0, '2025-12-18 18:07:47', '2025-12-18 18:07:47');
INSERT INTO `file_resource` VALUES (98, 'imported_image_ede5bcee-9fdd-4b12-8657-729a71087450.jpg', 'faf36a6e-b0aa-4bb0-932e-411a326660bd-imported_image_ede5bcee-9fdd-4b12-8657-729a71087450.jpg', '/api/files/download/faf36a6e-b0aa-4bb0-932e-411a326660bd-imported_image_ede5bcee-9fdd-4b12-8657-729a71087450.jpg', 'white-rs', 171788, 'image/png', 'jpg', 'contract_image', 0, 0, 0, '2025-12-18 18:07:47', '2025-12-18 18:07:47');
INSERT INTO `file_resource` VALUES (99, 'imported_image_53b86b1f-47c2-44fe-a10a-cd718748cb87.jpg', 'e8abcc64-3691-4c0b-b77b-3f686f86bc6c-imported_image_53b86b1f-47c2-44fe-a10a-cd718748cb87.jpg', '/api/files/download/e8abcc64-3691-4c0b-b77b-3f686f86bc6c-imported_image_53b86b1f-47c2-44fe-a10a-cd718748cb87.jpg', 'white-rs', 103902, 'image/jpeg', 'jpg', 'contract_image', 0, 0, 0, '2025-12-18 18:07:47', '2025-12-18 18:07:47');
INSERT INTO `file_resource` VALUES (100, 'imported_image_e8c92aef-cfd0-48b3-ae84-4e262a3e12a2.jpg', '19bb0ebf-9419-4aad-91bc-2fb132afcde9-imported_image_e8c92aef-cfd0-48b3-ae84-4e262a3e12a2.jpg', '/api/files/download/19bb0ebf-9419-4aad-91bc-2fb132afcde9-imported_image_e8c92aef-cfd0-48b3-ae84-4e262a3e12a2.jpg', 'white-rs', 61036, 'image/jpeg', 'jpg', 'contract_image', 0, 0, 0, '2025-12-18 18:07:47', '2025-12-18 18:07:47');
INSERT INTO `file_resource` VALUES (101, 'imported_image_fb5b9407-d190-4d84-8459-73b413b971d5.jpg', 'ab435d61-bcf7-4ba4-a1db-2fd62744d4cf-imported_image_fb5b9407-d190-4d84-8459-73b413b971d5.jpg', '/api/files/download/ab435d61-bcf7-4ba4-a1db-2fd62744d4cf-imported_image_fb5b9407-d190-4d84-8459-73b413b971d5.jpg', 'white-rs', 57271, 'image/jpeg', 'jpg', 'contract_image', 0, 0, 0, '2025-12-18 18:07:47', '2025-12-18 18:07:47');
INSERT INTO `file_resource` VALUES (102, 'imported_image_79530d61-bf0b-4f2f-806f-140c32556151.jpg', 'a7ffe72f-dd91-489d-9421-39fe6c31493a-imported_image_79530d61-bf0b-4f2f-806f-140c32556151.jpg', '/api/files/download/a7ffe72f-dd91-489d-9421-39fe6c31493a-imported_image_79530d61-bf0b-4f2f-806f-140c32556151.jpg', 'white-rs', 105131, 'image/png', 'jpg', 'contract_image', 0, 0, 0, '2025-12-18 18:07:47', '2025-12-18 18:07:47');
INSERT INTO `file_resource` VALUES (103, 'imported_image_4bf5de53-a9c1-4a8b-aa14-e5c022b15c8e.jpg', '4243c1ba-0b04-4df0-8dee-dd89d365b7a4-imported_image_4bf5de53-a9c1-4a8b-aa14-e5c022b15c8e.jpg', '/api/files/download/4243c1ba-0b04-4df0-8dee-dd89d365b7a4-imported_image_4bf5de53-a9c1-4a8b-aa14-e5c022b15c8e.jpg', 'white-rs', 75666, 'image/jpeg', 'jpg', 'contract_image', 0, 0, 0, '2025-12-18 18:07:47', '2025-12-18 18:07:47');
INSERT INTO `file_resource` VALUES (104, 'imported_image_1af2c666-1e46-436c-8116-560ad14a8e50.jpg', 'c166b6a5-30d8-42e6-8dba-6a1a85cd58b2-imported_image_1af2c666-1e46-436c-8116-560ad14a8e50.jpg', '/api/files/download/c166b6a5-30d8-42e6-8dba-6a1a85cd58b2-imported_image_1af2c666-1e46-436c-8116-560ad14a8e50.jpg', 'white-rs', 45130, 'image/jpeg', 'jpg', 'contract_image', 0, 0, 0, '2025-12-18 18:07:47', '2025-12-18 18:07:47');
INSERT INTO `file_resource` VALUES (105, 'imported_image_65b84b24-80fb-4531-b62f-310bcb37f188.jpg', 'ecb40a91-4556-4630-9bb6-2e350033f5d9-imported_image_65b84b24-80fb-4531-b62f-310bcb37f188.jpg', '/api/files/download/ecb40a91-4556-4630-9bb6-2e350033f5d9-imported_image_65b84b24-80fb-4531-b62f-310bcb37f188.jpg', 'white-rs', 68339, 'image/jpeg', 'jpg', 'contract_image', 0, 0, 0, '2025-12-18 18:07:47', '2025-12-18 18:07:47');
INSERT INTO `file_resource` VALUES (106, 'imported_image_418ebf50-c034-40cd-8732-0cfc6c5be7d0.jpg', '562cefa6-f282-408e-8a1d-d510b4609e05-imported_image_418ebf50-c034-40cd-8732-0cfc6c5be7d0.jpg', '/api/files/download/562cefa6-f282-408e-8a1d-d510b4609e05-imported_image_418ebf50-c034-40cd-8732-0cfc6c5be7d0.jpg', 'white-rs', 139812, 'image/png', 'jpg', 'contract_image', 0, 0, 0, '2025-12-18 18:07:47', '2025-12-18 18:07:47');
INSERT INTO `file_resource` VALUES (107, 'imported_image_f75ff867-5d6b-4b83-a09a-cf6a03a5ac30.jpg', '6892082e-9fdb-4521-a43c-3420d8c42fab-imported_image_f75ff867-5d6b-4b83-a09a-cf6a03a5ac30.jpg', '/api/files/download/6892082e-9fdb-4521-a43c-3420d8c42fab-imported_image_f75ff867-5d6b-4b83-a09a-cf6a03a5ac30.jpg', 'white-rs', 94418, 'image/jpeg', 'jpg', 'contract_image', 0, 0, 0, '2025-12-18 18:07:47', '2025-12-18 18:07:47');
INSERT INTO `file_resource` VALUES (108, 'imported_image_473f53a7-5ec3-4bd3-b42b-d2d3176f5539.jpg', '39f3f75b-8a88-4220-aef8-9d3c6650161f-imported_image_473f53a7-5ec3-4bd3-b42b-d2d3176f5539.jpg', '/api/files/download/39f3f75b-8a88-4220-aef8-9d3c6650161f-imported_image_473f53a7-5ec3-4bd3-b42b-d2d3176f5539.jpg', 'white-rs', 55597, 'image/jpeg', 'jpg', 'contract_image', 0, 0, 0, '2025-12-18 18:07:47', '2025-12-18 18:07:47');
INSERT INTO `file_resource` VALUES (109, 'imported_image_ba105cc6-4771-455d-96a5-69258f773f37.jpg', 'c077f745-22cc-428a-b6ad-4dfab1c597d4-imported_image_ba105cc6-4771-455d-96a5-69258f773f37.jpg', '/api/files/download/c077f745-22cc-428a-b6ad-4dfab1c597d4-imported_image_ba105cc6-4771-455d-96a5-69258f773f37.jpg', 'white-rs', 67730, 'image/jpeg', 'jpg', 'contract_image', 0, 0, 0, '2025-12-18 18:11:50', '2025-12-18 18:11:50');
INSERT INTO `file_resource` VALUES (110, 'imported_image_73d09d03-dc55-4672-b8f6-e67ca6e51728.jpg', '52351dfb-eb5a-4db5-a0a3-9df7a1d511e7-imported_image_73d09d03-dc55-4672-b8f6-e67ca6e51728.jpg', '/api/files/download/52351dfb-eb5a-4db5-a0a3-9df7a1d511e7-imported_image_73d09d03-dc55-4672-b8f6-e67ca6e51728.jpg', 'white-rs', 171788, 'image/png', 'jpg', 'contract_image', 0, 0, 0, '2025-12-18 18:11:50', '2025-12-18 18:11:50');
INSERT INTO `file_resource` VALUES (111, 'imported_image_db271825-ac06-4800-8bce-66322d7b052e.jpg', '42139ad1-2cf8-4b98-9852-8fd3fa271ca9-imported_image_db271825-ac06-4800-8bce-66322d7b052e.jpg', '/api/files/download/42139ad1-2cf8-4b98-9852-8fd3fa271ca9-imported_image_db271825-ac06-4800-8bce-66322d7b052e.jpg', 'white-rs', 103902, 'image/jpeg', 'jpg', 'contract_image', 0, 0, 0, '2025-12-18 18:11:50', '2025-12-18 18:11:50');
INSERT INTO `file_resource` VALUES (112, 'imported_image_92887029-a2e6-427c-a0c3-e0a45a7d0733.jpg', '5bf05623-ee76-4252-9d48-f1a9afa1023f-imported_image_92887029-a2e6-427c-a0c3-e0a45a7d0733.jpg', '/api/files/download/5bf05623-ee76-4252-9d48-f1a9afa1023f-imported_image_92887029-a2e6-427c-a0c3-e0a45a7d0733.jpg', 'white-rs', 61036, 'image/jpeg', 'jpg', 'contract_image', 0, 0, 0, '2025-12-18 18:11:50', '2025-12-18 18:11:50');
INSERT INTO `file_resource` VALUES (113, 'imported_image_6aa76147-697d-45e5-8398-66fe7e9706ed.jpg', '0bbc1b00-f9b4-4e33-a639-7d0a974a0cc0-imported_image_6aa76147-697d-45e5-8398-66fe7e9706ed.jpg', '/api/files/download/0bbc1b00-f9b4-4e33-a639-7d0a974a0cc0-imported_image_6aa76147-697d-45e5-8398-66fe7e9706ed.jpg', 'white-rs', 57271, 'image/jpeg', 'jpg', 'contract_image', 0, 0, 0, '2025-12-18 18:11:50', '2025-12-18 18:11:50');
INSERT INTO `file_resource` VALUES (114, 'imported_image_a2bdf246-f584-4a13-acad-f69bfa5cba6b.jpg', '6d12010e-ba8b-4dc0-8a1d-045a7cae64b2-imported_image_a2bdf246-f584-4a13-acad-f69bfa5cba6b.jpg', '/api/files/download/6d12010e-ba8b-4dc0-8a1d-045a7cae64b2-imported_image_a2bdf246-f584-4a13-acad-f69bfa5cba6b.jpg', 'white-rs', 105131, 'image/png', 'jpg', 'contract_image', 0, 0, 0, '2025-12-18 18:11:50', '2025-12-18 18:11:50');
INSERT INTO `file_resource` VALUES (115, 'imported_image_94664384-6a5b-4c82-89c5-cb110d5f787c.jpg', '5acc13f4-b5b8-474d-abcb-0196e7875398-imported_image_94664384-6a5b-4c82-89c5-cb110d5f787c.jpg', '/api/files/download/5acc13f4-b5b8-474d-abcb-0196e7875398-imported_image_94664384-6a5b-4c82-89c5-cb110d5f787c.jpg', 'white-rs', 75666, 'image/jpeg', 'jpg', 'contract_image', 0, 0, 0, '2025-12-18 18:11:50', '2025-12-18 18:11:50');
INSERT INTO `file_resource` VALUES (116, 'imported_image_9587f809-7f48-489b-b070-402f6f42989a.jpg', '9a34beae-f7d4-4290-af8b-1e861b10c028-imported_image_9587f809-7f48-489b-b070-402f6f42989a.jpg', '/api/files/download/9a34beae-f7d4-4290-af8b-1e861b10c028-imported_image_9587f809-7f48-489b-b070-402f6f42989a.jpg', 'white-rs', 45130, 'image/jpeg', 'jpg', 'contract_image', 0, 0, 0, '2025-12-18 18:11:50', '2025-12-18 18:11:50');
INSERT INTO `file_resource` VALUES (117, 'imported_image_570fe74c-6f44-4672-b6a5-6477328c9ca3.jpg', 'db4a0fa7-d994-4d60-80d9-972dd47fdaee-imported_image_570fe74c-6f44-4672-b6a5-6477328c9ca3.jpg', '/api/files/download/db4a0fa7-d994-4d60-80d9-972dd47fdaee-imported_image_570fe74c-6f44-4672-b6a5-6477328c9ca3.jpg', 'white-rs', 68339, 'image/jpeg', 'jpg', 'contract_image', 0, 0, 0, '2025-12-18 18:11:50', '2025-12-18 18:11:50');
INSERT INTO `file_resource` VALUES (118, 'imported_image_3f185871-becb-4f53-9ffd-02176fafc641.jpg', '8824fb58-5f8a-43f5-beaa-783c50768cd6-imported_image_3f185871-becb-4f53-9ffd-02176fafc641.jpg', '/api/files/download/8824fb58-5f8a-43f5-beaa-783c50768cd6-imported_image_3f185871-becb-4f53-9ffd-02176fafc641.jpg', 'white-rs', 139812, 'image/png', 'jpg', 'contract_image', 0, 0, 0, '2025-12-18 18:11:50', '2025-12-18 18:11:50');
INSERT INTO `file_resource` VALUES (119, 'imported_image_fc457455-f68d-4968-95a9-3e0a49050651.jpg', '1b10b8cc-075d-4826-ae57-311cf0ea446a-imported_image_fc457455-f68d-4968-95a9-3e0a49050651.jpg', '/api/files/download/1b10b8cc-075d-4826-ae57-311cf0ea446a-imported_image_fc457455-f68d-4968-95a9-3e0a49050651.jpg', 'white-rs', 94418, 'image/jpeg', 'jpg', 'contract_image', 0, 0, 0, '2025-12-18 18:11:50', '2025-12-18 18:11:50');
INSERT INTO `file_resource` VALUES (120, 'imported_image_6b8ab57e-388f-4bf9-8b14-9781715b7978.jpg', 'cbbeb970-fbba-46b2-aabf-3607065739d6-imported_image_6b8ab57e-388f-4bf9-8b14-9781715b7978.jpg', '/api/files/download/cbbeb970-fbba-46b2-aabf-3607065739d6-imported_image_6b8ab57e-388f-4bf9-8b14-9781715b7978.jpg', 'white-rs', 55597, 'image/jpeg', 'jpg', 'contract_image', 0, 0, 0, '2025-12-18 18:11:50', '2025-12-18 18:11:50');

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
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '登录日志表' ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '菜单表（前端路由）' ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统通知表' ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '操作日志表' ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '权限表（权限点）' ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色-权限关联表' ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of roles
-- ----------------------------
INSERT INTO `roles` VALUES (1, '管理员', '3294', '拥有最高权限的用户', 1, '2025-11-23 21:58:42');
INSERT INTO `roles` VALUES (2, '普通用户', '1502', '拥有普通权限的用户', 1, '2025-11-23 21:59:31');
INSERT INTO `roles` VALUES (3, '优鼎', '5293', '优鼎业务员', 1, '2025-12-03 14:19:23');
INSERT INTO `roles` VALUES (4, '辅料工厂', '6666', '辅料工厂用户', 1, '2025-12-03 14:19:55');
INSERT INTO `roles` VALUES (5, '工厂', '7777', '工厂用户', 1, '2025-12-03 14:20:19');
INSERT INTO `roles` VALUES (7, '跟单', '5555', '优鼎跟单用户', 1, '2025-12-10 09:54:27');

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
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户会话表（管理登录Token）' ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of settings
-- ----------------------------

-- ----------------------------
-- Table structure for table_import
-- ----------------------------
DROP TABLE IF EXISTS `table_import`;
CREATE TABLE `table_import`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `file_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `period` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `import_user` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `created_at` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7790 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of table_import
-- ----------------------------
INSERT INTO `table_import` VALUES (1, NULL, NULL, NULL, '2025-12-16 16:21:04');
INSERT INTO `table_import` VALUES (666, NULL, NULL, NULL, '2025-12-16 16:18:52');
INSERT INTO `table_import` VALUES (6666, NULL, NULL, NULL, '2025-12-17 18:48:51');
INSERT INTO `table_import` VALUES (7789, NULL, NULL, NULL, '2025-12-18 18:07:47');

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
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户-角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (5, 22, 2, NULL, '2025-12-03 14:03:37');
INSERT INTO `user_role` VALUES (6, 3, 3, NULL, '2025-12-03 14:25:18');
INSERT INTO `user_role` VALUES (10, 6, 5, NULL, '2025-12-05 13:48:01');
INSERT INTO `user_role` VALUES (11, 2, 1, NULL, '2025-12-05 16:00:38');
INSERT INTO `user_role` VALUES (13, 4, 7, NULL, '2025-12-10 10:21:29');
INSERT INTO `user_role` VALUES (14, 1, 1, NULL, '2025-12-18 18:33:27');
INSERT INTO `user_role` VALUES (16, 5, 4, NULL, '2025-12-22 14:00:30');

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
  `avatar_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_username`(`username` ASC) USING BTREE,
  UNIQUE INDEX `uk_email`(`email` ASC) USING BTREE,
  UNIQUE INDEX `uk_phone`(`phone` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '后台用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (1, 'white', '1940449715@qq.com', '13177675636', '$2a$10$a805H2iXJqJDOtssurFyO.pNVwi4R/fB3llZdStgbeUdBQLUSndYy', 1, '2025-12-24 16:15:53', '192.168.24.188', NULL, 0, '2025-12-02 10:29:16', '2025-12-24 16:15:53', NULL, '/api/files/download/d09e319f-4059-43ac-ab9a-fe88afb1eda8-wp2218351-macos-wallpapers.jpg');
INSERT INTO `users` VALUES (2, 'one', NULL, NULL, '$2a$10$1096l.Yu9S3PTOmcphDH2.XeOUqOtzsMY8byluLS8ORUaYfvT0iUC', 1, '2025-12-05 16:00:44', '192.168.24.188', NULL, 0, '2025-12-02 09:05:59', '2025-12-05 16:00:44', NULL, NULL);
INSERT INTO `users` VALUES (3, 'youding', NULL, NULL, '$2a$10$ZuCJwxxSD1X02hgy0bMHieM/BLEz6oVwQ3B5A.FQiRGQWCd5YQre2', 1, '2025-12-22 14:09:46', '192.168.25.156', NULL, 0, '2025-12-02 09:06:02', '2025-12-22 14:09:46', NULL, NULL);
INSERT INTO `users` VALUES (4, 'gendan', NULL, NULL, '$2a$10$X6bu.h/OHx3e50fe2D7i3e/nZInXEzOMLYeV/o2/yOpPYEoUIz9tq', 1, '2025-12-18 14:16:50', '192.168.24.77', NULL, 0, '2025-12-02 09:06:03', '2025-12-18 14:16:50', NULL, NULL);
INSERT INTO `users` VALUES (5, '鸿瑞', NULL, NULL, '$2a$10$XpIW6J0KPr233CbIFIASte21Vd2QdvsloiItneefKKDPMsKhf6YGO', 1, '2025-12-05 15:40:35', '192.168.31.248', NULL, 0, '2025-12-02 09:06:07', '2025-12-05 15:40:35', NULL, NULL);
INSERT INTO `users` VALUES (6, 'gongchang', NULL, NULL, '$2a$10$sumYsvz.cxWNofoIsVxVpuCtCSPTTjI2vhWQCTIGOap1/frKPeYda', 1, '2025-12-18 14:17:43', '192.168.24.77', NULL, 0, '2025-12-02 09:06:08', '2025-12-18 14:17:43', NULL, NULL);
INSERT INTO `users` VALUES (7, 'e11253', NULL, NULL, '$2a$10$NjXH/FIRn54/sigrCcMn/ePX97Bb.PjB0Q6GSpMD2k/V4AZV1dTj6', 1, NULL, NULL, NULL, 0, '2025-12-02 09:06:08', '2025-12-02 09:06:08', NULL, NULL);
INSERT INTO `users` VALUES (8, '13b98a', NULL, NULL, '$2a$10$wg6zrE2eDBCCIdD9.9pVD.tKl7HQy0NnwOnMYewtLTKsLbpp4RdKq', 1, NULL, NULL, NULL, 0, '2025-12-02 09:06:08', '2025-12-02 09:06:08', NULL, NULL);
INSERT INTO `users` VALUES (9, '1490e1', NULL, NULL, '$2a$10$740rLusfCOexBTnPThV6HuAZfer/5bVH47YXDNDwPbRfWl2LnoSUK', 1, NULL, NULL, NULL, 0, '2025-12-02 09:06:08', '2025-12-02 09:06:08', NULL, NULL);
INSERT INTO `users` VALUES (10, 'fcdbab', NULL, NULL, '$2a$10$A0lNHXynZp4Mwx0FTQMOQe3hNFt3JrcqpFoHcd.rXt.YC1anKTfty', 1, NULL, NULL, NULL, 0, '2025-12-02 09:06:08', '2025-12-02 09:06:08', NULL, NULL);
INSERT INTO `users` VALUES (11, '022a9b', NULL, NULL, '$2a$10$FodQy6Ke0UFr2RVUH1EV..lYkE6m7YuxYJszzM9cCULZwUML3MKPi', 1, NULL, NULL, NULL, 0, '2025-12-02 09:06:09', '2025-12-02 09:06:09', NULL, NULL);
INSERT INTO `users` VALUES (12, 'e8e8c8', NULL, NULL, '$2a$10$zNYnMDsqiK6oeuzOY8hTducTpqPTuGXZRe6BfU7BRoTwF5x4fp51y', 1, NULL, NULL, NULL, 0, '2025-12-02 09:06:09', '2025-12-02 09:06:09', NULL, NULL);
INSERT INTO `users` VALUES (13, 'ab8001', NULL, NULL, '$2a$10$D7DcG7PI7i8091z.fIWn8eME5Cr5VUg0NqT12FoG4zYaAGlXYsok2', 1, NULL, NULL, NULL, 0, '2025-12-02 09:06:09', '2025-12-02 09:06:09', NULL, NULL);
INSERT INTO `users` VALUES (15, 'oqe253', NULL, NULL, '$2a$10$N700BR47bYx3vtEv.xeGy.IsDYdDYX6nHj6gzWOVewjZyRHs9pHn2', 1, NULL, NULL, NULL, 0, '2025-12-02 13:52:15', '2025-12-02 13:52:15', NULL, NULL);
INSERT INTO `users` VALUES (17, '1fzrqa', NULL, NULL, '$2a$10$xCNBKI9li1ooGWqrVXzOqOPXIuCnPkA.c4UBd.aaH73nUt5OkQjlG', 1, NULL, NULL, NULL, 0, '2025-12-02 13:52:43', '2025-12-02 13:52:43', NULL, NULL);
INSERT INTO `users` VALUES (18, 'ssqhuw', NULL, NULL, '$2a$10$raZJysFQNOdIULNUWZ4X5e96LlOMlxOcmccUXt9m4IM1ECWeK4VmG', 1, NULL, NULL, NULL, 0, '2025-12-02 15:18:03', '2025-12-02 15:18:03', NULL, NULL);
INSERT INTO `users` VALUES (19, '054bkc', NULL, NULL, '$2a$10$LQQiuIai0wKeiOC70vvg2O0TU/G/YoicW7f5g2I55jOxTVQbuDNiC', 1, NULL, NULL, NULL, 0, '2025-12-03 09:03:25', '2025-12-03 09:03:25', NULL, NULL);
INSERT INTO `users` VALUES (20, 'vlm3n5', NULL, NULL, '$2a$10$s8/2OBAOvZ4TkHlY8m5o7.R3nyJseVM.uR42mk79hdp/jnGPLlCs2', 1, NULL, NULL, NULL, 0, '2025-12-03 11:46:18', '2025-12-03 11:46:18', NULL, NULL);
INSERT INTO `users` VALUES (21, '3jh8t8', NULL, NULL, '$2a$10$HYiV/KXQDKngsUNgZ6jkmuokvVdcvkcAtcvn70w7gjnM6xm9kHjza', 1, NULL, NULL, NULL, 0, '2025-12-03 11:46:23', '2025-12-03 11:46:23', NULL, NULL);
INSERT INTO `users` VALUES (22, 'k76wwa', NULL, NULL, '$2a$10$1JKEn9LEbDRl5QNu0qQwzuRQvCt5wCP0bK952qajbIa/ngjuPaWFW', 1, NULL, NULL, NULL, 0, '2025-12-03 14:03:37', '2025-12-03 14:03:37', NULL, NULL);

-- ----------------------------
-- Table structure for webhook
-- ----------------------------
DROP TABLE IF EXISTS `webhook`;
CREATE TABLE `webhook`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'Webhook 名称',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'Webhook 地址',
  `status` int NULL DEFAULT NULL COMMENT '启用/禁用',
  `type` int NULL DEFAULT NULL COMMENT '类型',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `createdAt` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updatedAt` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `sendCount` int NULL DEFAULT NULL COMMENT '推送次数',
  `lastSendTime` datetime NULL DEFAULT NULL COMMENT '最近一次推送结果',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of webhook
-- ----------------------------
INSERT INTO `webhook` VALUES (1, '测试', 'https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=69a9cf59-fd0e-40bf-a435-2c55a90c1376', 1, 1, NULL, NULL, NULL, 11, '2025-12-24 10:10:34');

-- ----------------------------
-- Table structure for webhook_table_import
-- ----------------------------
DROP TABLE IF EXISTS `webhook_table_import`;
CREATE TABLE `webhook_table_import`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `webhook_id` int NULL DEFAULT NULL,
  `import_id` int NULL DEFAULT NULL COMMENT '批次id',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of webhook_table_import
-- ----------------------------
INSERT INTO `webhook_table_import` VALUES (2, 1, 6666, NULL);
INSERT INTO `webhook_table_import` VALUES (3, 1, 7789, NULL);

-- ----------------------------
-- Triggers structure for table accessories_purchase_contract
-- ----------------------------
DROP TRIGGER IF EXISTS `apc_before_insert`;
delimiter ;;
CREATE TRIGGER `apc_before_insert` BEFORE INSERT ON `accessories_purchase_contract` FOR EACH ROW BEGIN
    SET NEW.wash_total_price = NEW.quantity * NEW.wash_unit_price;
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table accessories_purchase_contract
-- ----------------------------
DROP TRIGGER IF EXISTS `apc_before_update`;
delimiter ;;
CREATE TRIGGER `apc_before_update` BEFORE UPDATE ON `accessories_purchase_contract` FOR EACH ROW BEGIN
    SET NEW.wash_total_price = NEW.quantity * NEW.wash_unit_price;
END
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
