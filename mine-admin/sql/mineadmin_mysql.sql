-- MySQL dump 10.13  Distrib 8.0.34
--
-- Host: 127.0.0.1    Database: mineadmin
--
-- Server version	8.0.34
-- ------------------------------------------------------


--
-- Table structure for table `attachment`
--

DROP TABLE IF EXISTS `attachment`;
CREATE TABLE `attachment` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `storage_mode` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'local' COMMENT '存储模式:local=本地,oss=阿里云,qiniu=七牛云,cos=腾讯云',
  `origin_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '原文件名',
  `object_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '新文件名',
  `hash` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '文件hash',
  `mime_type` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '资源类型',
  `storage_path` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '存储目录',
  `suffix` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '文件后缀',
  `size_byte` bigint DEFAULT NULL COMMENT '字节数',
  `size_info` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '文件大小',
  `url` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'url地址',
  `created_by` bigint NOT NULL DEFAULT '0' COMMENT '创建者',
  `updated_by` bigint DEFAULT '0' COMMENT '更新者',
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `remark` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `attachment_hash_unique` (`hash`),
  KEY `attachment_storage_path_index` (`storage_path`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='上传文件信息表';


--
-- Table structure for table `menu`
--

DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `parent_id` bigint unsigned NOT NULL COMMENT '父ID',
  `name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '菜单名称',
  `meta` json DEFAULT NULL COMMENT '附加属性',
  `path` varchar(60) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '路径',
  `component` varchar(150) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '组件路径',
  `redirect` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '重定向地址',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '状态:1=正常,2=停用',
  `sort` smallint NOT NULL DEFAULT '0' COMMENT '排序',
  `created_by` bigint NOT NULL DEFAULT '0' COMMENT '创建者',
  `updated_by` bigint NOT NULL DEFAULT '0' COMMENT '更新者',
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `remark` varchar(60) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `menu_name_unique` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='菜单信息表';

INSERT INTO `menu` (`id`, `parent_id`, `name`, `meta`, `path`, `component`, `redirect`, `status`, `sort`, `created_by`, `updated_by`, `created_at`, `updated_at`, `remark`)
VALUES
    (1, 0, 'permission', '{"i18n": "baseMenu.permission.index", "icon": "ri:git-repository-private-line", "type": "M", "affix": false, "cache": true, "title": "权限管理", "hidden": false, "copyright": true, "componentPath": "modules/", "componentSuffix": ".vue", "breadcrumbEnable": true}', '/permission', '', '', 1, 0, 0, 0, now(), now(), ''),
    (2, 1, 'permission:user', '{"i18n": "baseMenu.permission.user", "icon": "material-symbols:manage-accounts-outline", "type": "M", "affix": false, "cache": true, "title": "用户管理", "hidden": false, "copyright": true, "componentPath": "modules/", "componentSuffix": ".vue", "breadcrumbEnable": true}', '/permission/user', 'base/views/permission/user/index', '', 1, 0, 0, 0, now(), now(), ''),
    (3, 2, 'permission:user:index', '{"i18n": "baseMenu.permission.userList", "type": "B", "title": "用户列表"}', '', '', '', 1, 0, 0, 0, now(), now(), ''),
    (4, 2, 'permission:user:save', '{"i18n": "baseMenu.permission.userSave", "type": "B", "title": "用户保存"}', '', '', '', 1, 0, 0, 0, now(), now(), ''),
    (5, 2, 'permission:user:update', '{"i18n": "baseMenu.permission.userUpdate", "type": "B", "title": "用户更新"}', '', '', '', 1, 0, 0, 0, now(), now(), ''),
    (6, 2, 'permission:user:delete', '{"i18n": "baseMenu.permission.userDelete", "type": "B", "title": "用户删除"}', '', '', '', 1, 0, 0, 0, now(), now(), ''),
    (7, 2, 'permission:user:password', '{"i18n": "baseMenu.permission.userPassword", "type": "B", "title": "用户初始化密码"}', '', '', '', 1, 0, 0, 0, now(), now(), ''),
    (8, 2, 'permission:user:getRole', '{"i18n": "baseMenu.permission.getUserRole", "type": "B", "title": "获取用户角色"}', '', '', '', 1, 0, 0, 0, now(), now(), ''),
    (9, 2, 'permission:user:setRole', '{"i18n": "baseMenu.permission.setUserRole", "type": "B", "title": "用户角色赋予"}', '', '', '', 1, 0, 0, 0, now(), now(), ''),
    (10, 1, 'permission:menu', '{"i18n": "baseMenu.permission.menu", "icon": "ph:list-bold", "type": "M", "affix": false, "cache": true, "title": "菜单管理", "hidden": false, "copyright": true, "componentPath": "modules/", "componentSuffix": ".vue", "breadcrumbEnable": true}', '/permission/menu', 'base/views/permission/menu/index', '', 1, 0, 0, 0, now(), now(), ''),
    (11, 10, 'permission:menu:index', '{"i18n": "baseMenu.permission.menuList", "type": "B", "title": "菜单列表"}', '', '', '', 1, 0, 0, 0, now(), now(), ''),
    (12, 10, 'permission:menu:create', '{"i18n": "baseMenu.permission.menuSave", "type": "B", "title": "菜单保存"}', '', '', '', 1, 0, 0, 0, now(), now(), ''),
    (13, 10, 'permission:menu:save', '{"i18n": "baseMenu.permission.menuUpdate", "type": "B", "title": "菜单更新"}', '', '', '', 1, 0, 0, 0, now(), now(), ''),
    (14, 10, 'permission:menu:delete', '{"i18n": "baseMenu.permission.menuDelete", "type": "B", "title": "菜单删除"}', '', '', '', 1, 0, 0, 0, now(), now(), ''),
    (15, 1, 'permission:role', '{"i18n": "baseMenu.permission.role", "icon": "material-symbols:supervisor-account-outline-rounded", "type": "M", "affix": false, "cache": true, "title": "角色管理", "hidden": false, "copyright": true, "componentPath": "modules/", "componentSuffix": ".vue", "breadcrumbEnable": true}', '/permission/role', 'base/views/permission/role/index', '', 1, 0, 0, 0, now(), now(), ''),
    (16, 15, 'permission:role:index', '{"i18n": "baseMenu.permission.roleList", "type": "B", "title": "角色列表"}', '', '', '', 1, 0, 0, 0, now(), now(), ''),
    (17, 15, 'permission:role:save', '{"i18n": "baseMenu.permission.roleSave", "type": "B", "title": "角色保存"}', '', '', '', 1, 0, 0, 0, now(), now(), ''),
    (18, 15, 'permission:role:update', '{"i18n": "baseMenu.permission.roleUpdate", "type": "B", "title": "角色更新"}', '', '', '', 1, 0, 0, 0, now(), now(), ''),
    (19, 15, 'permission:role:delete', '{"i18n": "baseMenu.permission.roleDelete", "type": "B", "title": "角色删除"}', '', '', '', 1, 0, 0, 0, now(), now(), ''),
    (20, 15, 'permission:role:getMenu', '{"i18n": "baseMenu.permission.getRolePermission", "type": "B", "title": "获取角色权限"}', '', '', '', 1, 0, 0, 0, now(), now(), ''),
    (21, 15, 'permission:role:setMenu', '{"i18n": "baseMenu.permission.setRolePermission", "type": "B", "title": "赋予角色权限"}', '', '', '', 1, 0, 0, 0, now(), now(), ''),
    (22, 0, 'log', '{"i18n": "baseMenu.log.index", "icon": "ph:instagram-logo", "type": "M", "affix": false, "cache": true, "title": "日志管理", "hidden": false, "copyright": true, "componentPath": "modules/", "componentSuffix": ".vue", "breadcrumbEnable": true}', '/log', '', '', 1, 0, 0, 0, now(), now(), ''),
    (23, 22, 'log:userLogin', '{"i18n": "baseMenu.log.userLoginLog", "icon": "ph:user-list", "type": "M", "affix": false, "cache": true, "title": "用户登录日志管理", "hidden": false, "copyright": true, "componentPath": "modules/", "componentSuffix": ".vue", "breadcrumbEnable": true}', '/log/userLoginLog', 'base/views/log/userLogin', '', 1, 0, 0, 0, now(), now(), ''),
    (24, 23, 'log:userLogin:list', '{"i18n": "baseMenu.log.userLoginLogList", "type": "B", "title": "用户登录日志列表"}', '/log/userLoginLog', '', '', 1, 0, 0, 0, now(), now(), ''),
    (25, 23, 'log:userLogin:delete', '{"i18n": "baseMenu.log.userLoginLogDelete", "type": "B", "title": "删除用户登录日志"}', '', '', '', 1, 0, 0, 0, now(), now(), ''),
    (26, 22, 'log:userOperation', '{"i18n": "baseMenu.log.operationLog", "icon": "ph:list-magnifying-glass", "type": "M", "affix": false, "cache": true, "title": "操作日志管理", "hidden": false, "copyright": true, "componentPath": "modules/", "componentSuffix": ".vue", "breadcrumbEnable": true}', '/log/operationLog', 'base/views/log/userOperation', '', 1, 0, 0, 0, now(), now(), ''),
    (27, 26, 'log:userOperation:list', '{"i18n": "baseMenu.log.userOperationLog", "type": "B", "title": "用户操作日志列表"}', '', '', '', 1, 0, 0, 0, now(), now(), ''),
    (28, 26, 'log:userOperation:delete', '{"i18n": "baseMenu.log.userOperationLogDelete", "type": "B", "title": "删除用户操作日志"}', '', '', '', 1, 0, 0, 0, now(), now(), ''),
    (29, 0, 'dataCenter', '{"i18n": "baseMenu.dataCenter.index", "icon": "ri:database-line", "type": "M", "affix": false, "cache": true, "title": "数据中心", "hidden": false, "copyright": true, "componentPath": "modules/", "componentSuffix": ".vue", "breadcrumbEnable": true}', '/dataCenter', '', '', 1, 0, 0, 0, now(), now(), ''),
    (30, 29, 'dataCenter:attachment', '{"i18n": "baseMenu.dataCenter.attachment", "icon": "ri:attachment-line", "type": "M", "affix": false, "cache": true, "title": "附件管理", "hidden": false, "copyright": true, "componentPath": "modules/", "componentSuffix": ".vue", "breadcrumbEnable": true}', '/dataCenter/attachment', 'base/views/dataCenter/attachment/index', '', 1, 0, 0, 0, now(), now(), ''),
    (31, 30, 'dataCenter:attachment:list', '{"i18n": "baseMenu.dataCenter.attachmentList", "type": "B", "title": "附件列表"}', '', '', '', 1, 0, 0, 0, now(), now(), ''),
    (32, 30, 'dataCenter:attachment:upload', '{"i18n": "baseMenu.dataCenter.attachmentUpload", "type": "B", "title": "上传附件"}', '', '', '', 1, 0, 0, 0, now(), now(), ''),
    (33, 30, 'dataCenter:attachment:delete', '{"i18n": "baseMenu.dataCenter.attachmentDelete", "type": "B", "title": "删除附件"}', '', '', '', 1, 0, 0, 0, now(), now(), '');


--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '角色名称',
  `code` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '角色代码',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '状态:1=正常,2=停用',
  `sort` smallint NOT NULL DEFAULT '0' COMMENT '排序',
  `created_by` bigint NOT NULL DEFAULT '0' COMMENT '创建者',
  `updated_by` bigint NOT NULL DEFAULT '0' COMMENT '更新者',
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `remark` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `role_code_unique` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色信息表';
INSERT INTO `role` VALUES (1,'超级管理员','SuperAdmin',1,0,0,0,now(),now(),'');


--
-- Table structure for table `role_belongs_menu`
--

DROP TABLE IF EXISTS `role_belongs_menu`;
CREATE TABLE `role_belongs_menu` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `role_id` bigint NOT NULL COMMENT '角色id',
  `menu_id` bigint NOT NULL COMMENT '菜单id',
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=132 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '用户ID,主键',
  `username` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名',
  `password` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
  `user_type` varchar(3) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '100' COMMENT '用户类型:100=系统用户',
  `nickname` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '用户昵称',
  `phone` varchar(11) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '手机',
  `email` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '用户邮箱',
  `avatar` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '用户头像',
  `signed` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '个人签名',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '状态:1=正常,2=停用',
  `login_ip` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '127.0.0.1' COMMENT '最后登陆IP',
  `login_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后登陆时间',
  `backend_setting` json DEFAULT NULL COMMENT '后台设置数据',
  `created_by` bigint NOT NULL DEFAULT '0' COMMENT '创建者',
  `updated_by` bigint NOT NULL DEFAULT '0' COMMENT '更新者',
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `remark` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_username_unique` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户信息表';
INSERT INTO `user` VALUES (1,'admin','$2a$10$upytMZff/Q6qysroPnG8mOEPXfw6b8zZHLmCnNUALTFmvpeKoohwW','100','创始人','16858888988','admin@adminmine.com','','广阔天地，大有所为。',1,'127.0.0.1',CURRENT_TIMESTAMP,NULL,0,0,now(),now(),'');


--
-- Table structure for table `user_belongs_role`
--

DROP TABLE IF EXISTS `user_belongs_role`;
CREATE TABLE `user_belongs_role` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL COMMENT '用户id',
  `role_id` bigint NOT NULL COMMENT '角色id',
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
INSERT INTO `user_belongs_role` VALUES (1,1,1,now(),now());


--
-- Table structure for table `user_login_log`
--

DROP TABLE IF EXISTS `user_login_log`;
CREATE TABLE `user_login_log` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名',
  `ip` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '登录IP地址',
  `os` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '操作系统',
  `browser` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '浏览器',
  `status` smallint NOT NULL DEFAULT '1' COMMENT '登录状态 (1成功 2失败)',
  `message` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '提示消息',
  `login_time` datetime NOT NULL COMMENT '登录时间',
  `remark` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `user_login_log_username_index` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='登录日志表';


--
-- Table structure for table `user_operation_log`
--

DROP TABLE IF EXISTS `user_operation_log`;
CREATE TABLE `user_operation_log` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名',
  `method` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '请求方式',
  `router` varchar(500) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '请求路由',
  `service_name` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '业务名称',
  `ip` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '请求IP地址',
  `created_at` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `updated_at` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `user_operation_log_username_index` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='操作日志表';

