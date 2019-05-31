set foreign_key_checks=0;


-- ----------------------------
-- chest_similar_scheme 商品相似度方案表 
-- ----------------------------
drop table if exists `chest_similar_scheme`;
create table `chest_similar_scheme` (
  `id` int(11) not null auto_increment comment '@desc 主键 auto_increment',
  `gmt_create` datetime default null comment '@desc 创建时间',
  `gmt_modified` datetime default null comment '@desc 修改时间',
  `creator` varchar(64) default null comment '@desc 创建者',
  `modifier` varchar(64) default null comment '@desc 修改者',
  `category_id` int(11) not null comment '@desc 品类ID',
  `scheme_name` varchar(64) default null comment '@desc 方案名称',
  `max_size` int(11) default null comment '@desc 方案处理数量',
  `has_exec` int(11) default 0 comment '@desc 是否已执行',
  `remark` varchar(256) default null comment '@desc 备注',
  primary key (`id`),
  key `idx_scheme_name` (`scheme_name`)
) engine=innodb default charset=utf8mb4 comment='商品相似度方案表';


-- ----------------------------
-- chest_similar_rule 商品相似度规则表 
-- ----------------------------
drop table if exists `chest_similar_rule`;
create table `chest_similar_rule` (
  `id` int(11) not null auto_increment comment '@desc 主键 auto_increment',
  `gmt_create` datetime default null comment '@desc 创建时间',
  `gmt_modified` datetime default null comment '@desc 修改时间',
  `creator` varchar(64) default null comment '@desc 创建者',
  `modifier` varchar(64) default null comment '@desc 修改者',
  `scheme_id` int(11) not null comment '@desc 方案ID',
  `rule_code` varchar(64) default null comment '@desc 规则CODE',
  `rule_name` varchar(64) default null comment '@desc 规则名称',
  `rule_type` varchar(64) not null comment '@desc 相似度规则类型',
  `is_dimen` tinyint(1) default 0 comment '@desc 是否多维离散',
  `field_expr` varchar(128) default null comment '@desc 字段表达式',
  `field_type` varchar(128) default null comment '@desc 字段类型',
  `deviation` double(14, 4) default 0 comment '@desc 偏差',
  `weight` double(14, 4) default 1 comment '@desc 权重',
  `remark` varchar(256) default null comment '@desc 备注',
  primary key (`id`),
  key `idx_rule_code` (`rule_code`),
  key `idx_rule_type` (`rule_type`)
) engine=innodb default charset=utf8mb4 comment='商品相似度规则表';


-- ----------------------------
-- chest_dimen_key 相似度离散型数据距离表 
-- ----------------------------
drop table if exists `chest_dimen_key`;
create table `chest_dimen_key` (
  `id` int(11) not null auto_increment comment '@desc 主键 auto_increment',
  `gmt_create` datetime default null comment '@desc 创建时间',
  `gmt_modified` datetime default null comment '@desc 修改时间',
  `rule_id` int(11) not null comment '@desc 规则ID',
  `field_name` varchar(64) default null comment '@desc 字段名称',
  `field_value` varchar(64) default null comment '@desc 字段对应值',
  `dist` double(14, 4) default null comment '@desc 距离值',
  primary key (`id`),
  key `uk_rule_id` (`rule_id`)
) engine=innodb default charset=utf8mb4 comment='相似度离散型数据距离表';


-- ----------------------------
-- chest_dimen_tpl 离散型多维度数据模板
-- ----------------------------
drop table if exists `chest_dimen_tpl`;
create table `chest_dimen_tpl` (
  `id` int(11) not null auto_increment comment '@desc 主键 auto_increment',
  `gmt_create` datetime default null comment '@desc 创建时间',
  `gmt_modified` datetime default null comment '@desc 修改时间',
  `rule_code` varchar(64) default null comment '@desc 规则CODE',
  `dimen_name` varchar(64) default null comment '@desc 规格维度名称',
  `low_name` varchar(64) default null comment '@desc 低数值名称',
  `high_name` varchar(64) default null comment '@desc 高数值名称',
  `def_value` double(10, 4) default 0 comment '@desc 默认值',
  `low_value` double(10, 4) default -10 comment '@desc 最低值',
  `high_value` double(10, 4) default 10 comment '@desc 最高值',
  `remark` varchar(256) default null comment '@desc 备注',
  primary key (`id`),
  key `uk_rule_code` (`rule_code`),
  key `idx_dimen_name` (`dimen_name`)
) engine=innodb default charset=utf8mb4 comment='离散型多维度数据模板';


-- ----------------------------
-- chest_dimen_value 离散型多维数据表 
-- ----------------------------
drop table if exists `chest_dimen_value`;
create table `chest_dimen_value` (
  `id` int(11) not null auto_increment comment '@desc 主键 auto_increment',
  `gmt_create` datetime default null comment '@desc 创建时间',
  `gmt_modified` datetime default null comment '@desc 修改时间',
  `key_id` int(11) not null comment '@desc 离散型数值表ID',
  `tpl_id` int(11) not null comment '@desc 模板ID',
  `dist` double(14, 4) default null comment '@desc 距离值',
  primary key (`id`),
  key `uk_key_id` (`key_id`),
  key `uk_tpl_id` (`tpl_id`)
) engine=innodb default charset=utf8mb4 comment='离散型多维数据表 ';


-- -------------- 商品推荐 recom ----------------


-- ----------------------------
-- chest_recom_rule 商品推荐规则表 
-- ----------------------------
drop table if exists `chest_recom_rule`;
create table `chest_recom_rule` (
  `id` int(11) not null auto_increment comment '@desc 主键 auto_increment',
  `gmt_create` datetime default null comment '@desc 创建时间',
  `gmt_modified` datetime default null comment '@desc 修改时间',
  `rule_code` varchar(64) not null comment '@desc 规则CODE',
  `rule_name` varchar(64) not null comment '@desc 规则名称',
  `rule_type` varchar(64) not null comment '@desc 规则类型',
  `field_expr` varchar(128) not null comment '@desc 字段表达式',
  `max` double(13, 3) default 0 comment '@desc 最大值',
  `weight` double(13, 3) default 1 comment '@desc 权重',
  `remark` varchar(256) default null comment '@desc 备注',
  primary key (`id`),
  key `idx_rule_code` (`rule_code`),
  key `idx_rule_name` (`rule_name`)
) engine=innodb default charset=utf8mb4 comment='商品推荐规则表';
