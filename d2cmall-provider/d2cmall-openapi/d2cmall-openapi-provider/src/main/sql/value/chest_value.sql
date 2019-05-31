set foreign_key_checks=0;

-- ----------------------------
-- records of chest_similar_scheme
-- ----------------------------
alter table `chest_similar_scheme` auto_increment=10;
delete from `chest_similar_scheme`;
--insert into `chest_similar_scheme` values ('1', now(), now(), 'admin', 'admin', '20', '女装相似度', null, '');

-- ----------------------------
-- records of chest_dimen_tpl
-- ----------------------------
alter table `chest_dimen_tpl` auto_increment=10;
delete from `chest_dimen_tpl`;
--insert into `chest_dimen_tpl` values ('1', now(), now(), 'PRODUCT_CATEGORY', '薄厚程度', '薄', '厚', 0, -10, 10 , '品类-薄厚程度');
--insert into `chest_dimen_tpl` values ('2', now(), now(), 'PRODUCT_CATEGORY', '内穿外套', '内穿', '外套', 0, -10, 10, '品类-内穿外套');
--insert into `chest_dimen_tpl` values ('3', now(), now(), 'PRODUCT_CATEGORY', '休闲正式', '休闲', '正式', 0, -10, 10, '品类-休闲正式程度');

-- insert into `chest_dimen_tpl` values ('4', now(), now(), '4', '简单复杂', '简单', '复杂', '风格-简单复杂程度');
-- insert into `chest_dimen_tpl` values ('5', now(), now(), '4', '可爱炫酷', '可爱', '炫酷', '风格-可爱炫酷程度');
-- insert into `chest_dimen_tpl` values ('6', now(), now(), '4', '低调前卫', '低调', '前卫', '风格-低调前卫程度');


