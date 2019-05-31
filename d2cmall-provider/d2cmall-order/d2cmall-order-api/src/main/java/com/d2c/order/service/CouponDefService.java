package com.d2c.order.service;

import com.d2c.order.model.Coupon;
import com.d2c.order.model.CouponDef;

public interface CouponDefService {

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    CouponDef findById(Long id);

    /**
     * 插入优惠券定义的数据
     *
     * @param couponDef
     * @return
     */
    CouponDef insert(CouponDef couponDef);

    /**
     * 更新优惠券定义数据
     *
     * @param couponDef
     * @return
     */
    int update(CouponDef couponDef);

    /**
     * 逻辑删除优惠券定义数据，只是关闭了优惠券定义的状态
     *
     * @param defineId
     */
    void delete(Long defineId);

    /**
     * 单张领取优惠券<br>
     * <p>
     * 1.注册赠送100 2.点击链接赠送 3.暗码获取 4.系统后台给用户赠送
     *
     * @param defineId
     * @param member
     * @param username
     * @return
     */
    Coupon doClaimedCoupon(Long defineId, Long memberInfoId, String mobile, String username, String channel,
                           boolean msg);

    /**
     * 优惠券组领优惠券<br>
     * <p>
     * 5.批量领取
     *
     * @param defineId
     * @param memberInfoId
     * @param loginCode
     * @param username
     * @param channel
     * @param msg
     * @return
     */
    Coupon doClaimedCoupon4Group(Long defineId, Long memberInfoId, String loginCode, String username, String channel,
                                 boolean msg);

    /**
     * 付费购买领优惠券<br>
     *
     * @param defineId
     * @param memberInfoId
     * @param loginCode
     * @param channel
     * @param msg
     * @return
     */
    Coupon doClaimedCoupon4Buy(Long defineId, Long memberInfoId, String loginCode, String channel, boolean msg);

    /**
     * 微信卡券领优惠券<br>
     *
     * @param defineId
     * @param memberInfoId
     * @param loginCode
     * @param wxCode
     * @param channel
     * @return
     */
    int doClaimedWxCoupon(Long defineId, Long memberInfoId, String loginCode, String wxCode, String channel);

    /**
     * 创建一批次的密码优惠券<br>
     *
     * @param defineId 定义的id
     * @param quantity 需要创建的数量
     * @return
     */
    int doCreatePasswdCoupon(Long defineId, int quantity);

    /**
     * 更新优惠券的上下架状态
     *
     * @param mark
     * @param promotionId
     * @return
     */
    int doMark(Integer mark, Long defineId);

    /**
     * 通过优惠券定义的id，将优惠券的定义的管理状态修改为发送状态
     *
     * @param couponDefId
     * @return
     */
    int doSendById(Long couponDefId);

    /**
     * 关闭掉 一些过期且可以使用的的优惠券，将enable状态设置成不可用状态
     *
     * @return
     */
    int doCloseExpiredCouponDef();

    /**
     * 将对应的优惠券定义数据中微信卡包绑成指定的wxCardId
     *
     * @param wxCardId    微信卡的id
     * @param couponDefId 定义的id
     * @return
     */
    int doBindWxCard(String wxCardId, Long couponDefId);

}
