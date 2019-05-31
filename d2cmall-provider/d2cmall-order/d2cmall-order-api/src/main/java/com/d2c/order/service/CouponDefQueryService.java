package com.d2c.order.service;

import com.d2c.common.api.dto.HelpDTO;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.order.model.CouponDef;
import com.d2c.order.query.CouponDefSearcher;

import java.util.Date;
import java.util.List;

public interface CouponDefQueryService {

    /**
     * 通过定义的id查找优惠券定义数据
     *
     * @param defineId 定义id
     * @return
     */
    CouponDef findById(Long defineId);

    /**
     * 通过随机码查找出对应的优惠券定义的数据
     *
     * @param code 随机码
     * @return
     */
    CouponDef findByCode(String code);

    /**
     * 通过微信卡包id找出对应的优惠券定义数据
     *
     * @param cardId 微信卡包id
     * @return
     */
    CouponDef findByWxCardId(String cardId);

    /**
     * 通过查询条件和分页条件，得到简单的优惠券定义集合数据
     *
     * @param couponDefSearcher
     * @param page
     * @return
     */
    PageResult<HelpDTO> findHelpDto(CouponDefSearcher couponDefSearcher, PageModel page);

    /**
     * 通过优惠券定义的名字，查出具体的优惠券定义数据
     *
     * @param name 优惠券定义名字
     * @return
     */
    CouponDef findByName(String name);

    /**
     * 通过暗码查找出对应的优惠券定义数据
     *
     * @param cipher 暗码
     * @return
     */
    CouponDef findByCipher(String cipher);

    /**
     * 通过商品id，查询优惠券的定义
     *
     * @param productId
     * @return
     */
    List<CouponDef> findByProductId(Long productId);

    /**
     * 通过查询条件和分页条件，得到分页的优惠券定义dto数据
     *
     * @param page              分页条件
     * @param couponDefSearcher 查询条件爱您
     * @return
     */
    PageResult<CouponDef> findBySearcher(PageModel page, CouponDefSearcher couponDefSearcher);

    PageResult<Long> findByUpdateClaimed(Date modifyDate, PageModel page);

    /**
     * 是否超过个人限制领取量
     *
     * @param couponDef
     * @param memberInfoId
     * @return
     */
    boolean checkPersonalLimit(Long couponDefId, int claimLimit, Long memberInfoId, String loginCode);

    /**
     * 是否超过总发行量
     *
     * @param couponDef
     * @param memberId
     * @return
     */
    boolean checkQuantity(CouponDef couponDef, int quantity);

    /**
     * 上架状态，在领用期内，发行量>1000的折扣券和抵用券 默认不超过500条
     *
     * @return
     */
    List<CouponDef> findAvailableCoupons();

    void clearCache(Long couponDefId);

    /**
     * 查找关联该品牌的优惠券
     *
     * @param brandId
     * @return
     */
    List<CouponDef> findAvailableByBrandId(Long brandId, Integer free);

}
