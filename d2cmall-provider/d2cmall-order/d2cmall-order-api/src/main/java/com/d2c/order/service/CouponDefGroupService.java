package com.d2c.order.service;

import com.d2c.order.model.CouponDefGroup;
import com.d2c.order.model.CouponGroup;

public interface CouponDefGroupService {

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    CouponDefGroup findById(Long id);

    /**
     * 插入定义组数据
     *
     * @param couponDefGroup 封装后的定义组数据
     * @return
     */
    CouponDefGroup insert(CouponDefGroup couponDefGroup);

    /**
     * 更新定义组数据
     *
     * @param couponDefGroup 封装后的定义组数据
     * @return
     */
    int update(CouponDefGroup couponDefGroup);

    /**
     * 更新优惠券定义组中固定的优惠券定义ids
     *
     * @param groupId 优惠券组id
     * @param newIds  组合成优惠券定义id的字符串，","连接成
     * @return
     */
    int updateFixDefIds(Long groupId, String newIds);

    /**
     * 更新优惠券定义组中随机的优惠券定义ids
     *
     * @param groupId 定义组id
     * @param newIds  组合成优惠券定义id的字符串，","连接成
     * @return
     */
    int updateRandomDefIds(Long groupId, String newIds);

    /**
     * 上架优惠券定义组
     *
     * @param id 定义组id
     * @return
     */
    int doUp(Long id);

    /**
     * 下架优惠券定义组
     *
     * @param id 定义组id
     * @return
     */
    int doDown(Long id);

    /**
     * 对定义组数据归档，但是不是物理删除
     *
     * @param id
     * @return
     */
    int delete(Long id);

    /**
     * 通过定义组的id，会员的id，发放的人员和渠道来源，给会员添加一组优惠券
     *
     * @param couponDefGroupId 定义组id
     * @param memberInfoId     会员的id
     * @param giver            发放人员
     * @param source           渠道来源
     * @return
     */
    CouponGroup doClaimedCoupon(Long couponDefGroupId, Long memberInfoId, String mobile, String giver, String source);

}
