==============================================================================================================================================================
/*订单*/
SELECT email, sex, `name` ,mobile, nickname FROM memberinfo WHERE id 
IN(SELECT memberInfoId FROM member WHERE id IN
(SELECT DISTINCT(o.member_id) FROM orders o,orderitem oi 
WHERE oi.designerId=10042 AND o.id=oi.orderId)
)
/*收藏*/
SELECT email, sex, `name` ,mobile, nickname FROM memberinfo WHERE id 
IN(SELECT memberInfoId FROM member WHERE id IN
(SELECT DISTINCT(mi.memberid) FROM myinterest mi,product p
WHERE mi.source='PRODUCT' AND p.id=mi.sourceid AND p.designer_id=10042)
)
/*点击*/
SELECT mi.email, mi.sex, mi.name ,mi.mobile, mi.nickname FROM memberinfo mi,(SELECT m.memberInfoId as id FROM member m,(SELECT DISTINCT(a.memberId) as id FROM product p,(SELECT memberId,objectId,objectName FROM 
useroperatelog WHERE logType='VIEWPRODUCT' AND memberId IS NOT NULL)a 
WHERE p.id=a.objectId AND p.designer_id=10042)t 
WHERE t.id=m.id)n
WHERE n.id=mi.id;
==============================================================================================================================================================
/*销售库存对照表*/
SELECT t.*,p.sf_stock,p.st_stock FROM `product_sku_stock_summary` p,
(SELECT oi.`create_date`,`product_sn` ,`product_sku_sn`,`product_sku_id`,`product_quantity`,oi.designer_name FROM `orderitem` oi,`designer` d WHERE oi.designer_id=d.id AND d.operation=68 AND oi.create_date >'2016-09-01 00:00:00' AND oi.status NOT IN('MALLCLOSE','CLOSE'))t
WHERE t.product_sku_id=p.sku_id ORDER BY t.create_date ASC;
==============================================================================================================================================================
/*设计师商品订单统计*/
SELECT t1.*,t2.count FROM 
(SELECT YEAR(orderitem.create_date) as year ,MONTH(orderitem.create_date) as month,designer_id,designer_name,sum(product_quantity) as salesNum,sum(product_price*product_quantity-orderitem.promotion_amount) as salesAmt 
from orderitem,b_brand,orders where orderitem.designer_id=b_brand.id 
and orderitem.order_id=orders.id
and YEAR(orderitem.create_date)=2017 and MONTH(orderitem.create_date)>=1
group by year,month,designer_name ORDER BY year,month,salesAmt DESC)t1
LEFT JOIN 
(SELECT YEAR(create_date) as year ,MONTH(create_date) as month,designer_id,count(*) as count FROM `product` 
WHERE YEAR(create_date)=2017 and MONTH(create_date)>=1
group by year,month,designer_id)t2
ON t1.year=t2.year and t1.month=t2.month and t1.designer_id=t2.designer_id
==============================================================================================================================================================
/*月份毛订单成交量统计*/
SELECT MONTH(createDate) as month_, COUNT(*) as orderNum,SUM(totalAmount+promotionAmount) from orders 
WHERE orders.split=0
GROUP BY month_
/*月份净订单成交量统计*/
SELECT MONTH(orders.createDate) as month_, COUNT(DISTINCT orders.id), SUM(productPrice*productQuantity-promotionPrice) from orders,orderitem 
WHERE orderitem.orderId=orders.id
and orders.split=0
and orders.orderStatus>=2
and orders.deleted=0
and (orderitem.`status`="DELIVERED" OR orderitem.`status`="SUCCESS" OR orderitem.`status`="NORMAL")
GROUP BY month_
==============================================================================================================================================================
/*月份实际购买人数统计*/
SELECT MONTH(createDate) as month_,count(*) FROM orders o 
WHERE id=(select max(id) from orders where orders.member_id = o.member_id) 
AND o.split=0
AND o.orderStatus>=2
AND o.deleted=0
GROUP BY month_
==============================================================================================================================================================
/*上月总购买的用户数量，金额 （已付款订单）*/
SELECT COUNT(DISTINCT(`member_id`)),SUM(`paid_amount`)  FROM orders  
WHERE create_date>='2017-5-1 00:00:00' AND create_date<'2017-6-1 00:00:00' AND `order_status` NOT IN (-1,-2,-3,0,1);
/*上月之前有购买过的用户在上月再次购买的用户的数量， 金额  （已付款订单）*/
SELECT COUNT(DISTINCT(`member_id`)),SUM(`paid_amount`)  FROM orders  
WHERE create_date>='2017-5-1 00:00:00' AND create_date<'2017-6-1 00:00:00' AND `order_status` NOT IN (-1,-2,-3,0,1)
AND  member_id  IN(SELECT DISTINCT member_id FROM orders WHERE create_date<'2017-5-1' AND `order_status` NOT IN (-1,-2,-3,0,1));
/*上月之前注册一直未购买过订单的用户在上月购买的用户的数量，金额 （已付款订单）*/
SELECT COUNT(DISTINCT(`member_id`)),SUM(`paid_amount`)  FROM orders  
WHERE create_date>='2017-5-1 00:00:00' AND create_date<'2017-6-1 00:00:00' AND `order_status` NOT IN (-1,-2,-3,0,1)
AND  member_id NOT  IN(SELECT DISTINCT member_id FROM orders WHERE create_date<'2017-5-1' AND `order_status` NOT IN (-1,-2,-3,0,1))  
AND  member_id  not IN(SELECT `id` FROM `memberinfo` WHERE create_date>='2017-5-1' and create_date<'2017-6-1 00:00:00');
/*上月注册在上月购买的用户的数量，金额（已付款订单）*/
SELECT COUNT(DISTINCT(`member_id`)),SUM(`paid_amount`)  FROM orders  
WHERE create_date>='2017-5-1 00:00:00' AND create_date<'2017-6-1 00:00:00' AND `order_status` NOT IN (-1,-2,-3,0,1)
AND  member_id NOT  IN(SELECT DISTINCT member_id FROM orders WHERE create_date<'2017-5-1' AND `order_status` NOT IN (-1,-2,-3,0,1))  
AND  member_id   IN(SELECT `id` FROM `memberinfo` WHERE create_date>='2017-5-1' and create_date<'2017-6-1 00:00:00');
==============================================================================================================================================================
/*某品牌收藏加购下单用户手机*/
SELECT DISTINCT `member_id` as 会员号,`memberinfo`.`login_code`  from `member_collection` left join `memberinfo` 
on `memberinfo`.`id`=`member_collection`.`member_id` where `member_collection`.`designers`='YEESIN' and  `login_code`  REGEXP "^[1][2356789][0-9]{9}$"
UNION 
SELECT DISTINCT `member_id` as 会员号,`memberinfo`.`login_code`  from `member_attention` left join `memberinfo` 
on `memberinfo`.`id`=`member_attention`.`member_id` where  `member_attention`.`designer_id`=10177 and  `login_code`  REGEXP "^[1][2356789][0-9]{9}$" 
UNION
SELECT DISTINCT `buyer_member_id` as 会员号,`memberinfo`.`login_code`from `orderitem` left join `memberinfo` on 
`memberinfo`.`id`=`orderitem`.`buyer_member_id` where  `orderitem`.`designer_id` =10177   and  `login_code`  REGEXP "^[1][2356789][0-9]{9}$"
UNION
SELECT DISTINCT `buy_member_id` as 会员号,`memberinfo`.`login_code`  from `cartitem` left join `memberinfo` on 
`memberinfo`.`id`=`cartitem`.`buy_member_id` where `cartitem`.`designer_id`  =10177   and  `login_code`  REGEXP "^[1][2356789][0-9]{9}$"
==============================================================================================================================================================
/*衰退表*/
CREATE PROCEDURE `corhort`() 
BEGIN

    DECLARE i INT;
    DECLARE j INT;
    SET i = 2014;
	SET j = 1;

	WHILE i <= 2017
	DO
	WHILE j <= 12
	DO

	INSERT INTO corhort_result(

        SELECT
            t1.*, t2.num
        FROM
            (
                SELECT
                    *
                FROM
                    corhort_dates
                WHERE
                    (YEAR * 100 + MONTH) >= (I * 100 + J)
            ) t1
        LEFT JOIN (
            SELECT
                YEAR (create_date) AS years,
                MONTH (create_date) AS months,
                /*此处统计订单数，订单金额，会员数量*/
                count(id) AS num
            FROM
                orders
            WHERE
                member_id IN (
                    SELECT
                        member_id
                    FROM
                        orders
                    WHERE
                        YEAR (create_date) = i
                    AND MONTH (create_date) = j
                    AND order_status >= 2
                )
            AND member_id NOT IN (
                SELECT
                    member_id
                FROM
                    orders
                WHERE
                    (YEAR (`create_date`) * 100 + MONTH (`create_date`)) < (I * 100 + J)
                AND order_status >= 2
            )
            AND order_status >= 2
            GROUP BY
                YEAR (create_date),
                MONTH (create_date)
        ) t2
        ON t1.`year` = t2.years
        AND t1.`month` = t2.months
        ORDER BY
            t1.`year`,
            t1.`month`

);

INSERT INTO corhort_result(years,months,num)VALUES(0000,0000,0000);

SET j = j + 1;
END WHILE;
SET i = i + 1;
SET j = 1;
END WHILE;
END 
==============================================================================================================================================================
// 2月份预收款 = 2月底钱包余额 - 2月份交通银行充值 + 2月份已收款未发货

// 2月底钱包余额
SELECT a.account,b.* FROM m_account a,
(SELECT self_account_id, SUM(amount*direction) FROM o_account_item WHERE `status`=1 AND create_date<'2018-04-01' 
AND id NOT IN(SELECT bill_id FROM o_recharge WHERE `status`=1 AND create_date>='2018-03-01' AND create_date<'2018-04-01' AND pay_channel='COMMPAY' AND pay_type='RECHARGE')
GROUP BY self_account_id)b
WHERE a.id=b.self_account_id;

// 2月份交通银行充值
SELECT create_date,creator,recharge_amount FROM o_recharge WHERE `status`=1 AND create_date>='2018-03-01' AND create_date<'2018-04-01' AND pay_channel='COMMPAY' AND pay_type='RECHARGE';

// 2月份已收款未发货
SELECT order_sn, product_sn, product_price*product_quantity-order_promotion_amount-promotion_amount-coupon_amount-red_amount FROM orderitem 
WHERE payment_time<'2018-04-01' AND payment_time>='2018-03-01' AND (delivery_time>='2018-04-01' OR `status`='NORMAL');
