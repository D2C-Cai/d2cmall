package com.d2c.order.service;

import com.d2c.order.model.Coupon;
import com.d2c.order.model.CouponDef;

import java.math.BigDecimal;

public interface CouponService {

    /**
     * 插入优惠券
     *
     * @param coupon
     * @return
     */
    Coupon insert(Coupon coupon);

    /**
     * 根据优惠券定义，为生成优惠券做准备 1.通过优惠券定义设置优惠券的基本属性 2.更具优惠券的定义类型，设置优惠券的管理状态
     * 3.设置优惠券的有效时间 4.设置优惠券的优惠金额
     *
     * @param coupon    优惠券数据
     * @param couponDef 优惠券定义数据
     * @param sendor    发放人员
     * @param channel   来源
     * @return
     */
    Coupon doInsertCoupon(Coupon coupon, CouponDef couponDef, String sendor, String channel);

    /**
     * 通过密码，兑换一张优惠券 1.首先查找出符合条件的优惠券，包括没有领取，是密码券，已经发送状态，密码是password
     * 2.设置优惠券属于会员，设置领取状态，有效时间 3.更新
     *
     * @param password
     * @param memberInfoId
     * @return
     */
    Coupon doConvertCoupon(String password, Long memberInfoId);

    /**
     * 新注册会员，取回手机号领取的优惠券
     *
     * @param loginCode
     * @param memberInfoId
     * @return
     */
    int doGetBackCoupon(String loginCode, Long memberInfoId);

    /**
     * 将没有发送且没有领取的优惠券更新成发送状态，并且更新相应的发放人员信息
     *
     * @param couponId 优惠券的id
     * @param sendor   发放人员
     * @param sendmark 备注
     * @return
     */
    int doSendCoupon(Long couponId, String sendor, String sendmark);

    /**
     * 发放优惠券
     *
     * @param id
     * @param username
     * @return
     */
    int doSendCouponByDefineId(Long defId, String username, String sendMark);

    /**
     * 会员激活优惠券
     *
     * @param couponId     优惠券id
     * @param memberInfoId 会员id
     * @return
     */
    int doActivateCoupon(Long couponId, Long memberInfoId);

    /**
     * 通过订单id和订单号，对优惠券进行锁定设置
     *
     * @param orderId 订单id
     * @param orderSn 订单编号
     * @param code    优惠券code
     * @return
     */
    BigDecimal doLockCoupon(Long orderId, String orderSn, BigDecimal orderTotalAmount, String code);

    /**
     * 在线支付解锁优惠券，通过订单id，查找出已经锁定的优惠券后将状态设置成已经领取等状态
     *
     * @param orderId 订单的id
     * @return
     */
    int doUnlockCoupon(Long orderId);

    /**
     * 货到付款解锁优惠券，通过订单id，将已经使用的优惠券设置成已领取的状态
     *
     * @param orderId 订单的id
     * @return
     */
    int doReleaseCoupon(Long orderId);

    /**
     * 门店使用优惠券
     *
     * @param code         编码
     * @param memberInfoId 会员id
     * @param storeId      门店id
     * @return
     */
    Coupon doUsedCoupon(String code, Long memberInfoId, Long storeId);

    /**
     * 使用优惠券成功，设置优惠券已使用状态，更新使用时间
     *
     * @param couponId
     * @return
     */
    int doSuccessCoupon(Long couponId);

    /**
     * 取消优惠券，将没有领取的优惠券设置成已经作废状态
     *
     * @param couponId
     * @return
     */
    int doInvalidCoupon(Long couponId);

    /**
     * 优惠券转让，如果优惠券没有领取或者已经领取时，可以转让给会员
     *
     * @param memberInfoId 会员id
     * @param couponId     优惠券id
     * @return
     */
    int doTransferCoupon(Long memberInfoId, Long couponId);

    /**
     * 关闭已经过期的，且没有领取或者已经领取状态的优惠券
     *
     * @return
     */
    int doCloseExpiredCoupon();

    /**
     * 关闭已经过期的，移动到历史库中(过期一个月)
     *
     * @return
     */
    int doCopy2CouponHistory();

    /**
     * 关闭已经过期的，且没有领取或者已经领取状态的优惠券(过期一个月)
     *
     * @return
     */
    int doDeleteExpiredCoupon();

    /**
     * 优惠券转增
     *
     * @param couponId
     * @param toMemberId
     * @param loginCode
     * @return
     */
    int doTransfer(Long couponId, Long toMemberId, String toLoginCode);

}
