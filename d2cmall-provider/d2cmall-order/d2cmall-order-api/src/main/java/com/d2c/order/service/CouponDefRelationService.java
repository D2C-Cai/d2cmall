package com.d2c.order.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.order.model.CouponDef;
import com.d2c.order.model.CouponDefRelation;
import com.d2c.product.model.Product;
import com.d2c.product.query.ProductSearcher;

import java.util.List;

public interface CouponDefRelationService {

    /**
     * 根据id，查找出优惠券定义关系数据
     *
     * @param id
     * @return
     */
    CouponDefRelation findById(Long id);

    /**
     * 通过关系类型，目标的id和优惠券定义id，查找出优惠券定义关系数据
     *
     * @param targetId 目标的id，可能是设计师的id，或者商品的id
     * @param defineId 优惠券定义的id
     * @param type     关系类型
     * @return
     */
    CouponDefRelation findByCidAndTid(Long targetId, Long defineId, String type);

    /**
     * 查询符合条件的关系
     *
     * @param targetIds
     * @param couponDefId
     * @param type
     * @return
     */
    List<CouponDefRelation> findByCidAndTids(List<Long> targetIds, Long couponDefId, String type);

    /**
     * 根据优惠券定义查询商品
     *
     * @param ProductSearcher
     * @param defineId
     * @param page
     * @return
     */
    PageResult<Product> findProductsBySearcher(ProductSearcher searcher, Long defineId, PageModel page);

    /**
     * 异步根据设计师ID获取品牌优惠券
     *
     * @param designerId
     * @return
     */
    List<CouponDef> findCouponsByDesigner(Long designerId);

    /**
     * 异步根据商品ID获取商品优惠券
     *
     * @param productId
     * @return
     */
    List<CouponDef> findCouponsByProduct(Long productId);

    /**
     * 插入一个优惠券定义的关系数据，包括优惠券与设计师或者商品的关系
     *
     * @param cdr 优惠券定义关系
     * @return
     */
    CouponDefRelation insert(CouponDefRelation cdr);

    /**
     * 更新优惠券定义关系数据
     *
     * @param record
     * @return
     */
    int update(CouponDefRelation record);

    /**
     * 删除优惠券定义关系数据
     *
     * @param id
     * @return
     */
    int delete(Long id);

    /**
     * 通过关系类型，删除掉对应的关系数据
     *
     * @param targetId 目标的id，可能是设计师的id，或者商品的id
     * @param defineId 优惠券定义的id
     * @param type     关系类型
     * @return
     */
    int deleteByCidAndTid(Long targetId, Long defineId, String type, String operator);

    /**
     * 通过关系类型，删除掉对应的关系数据
     *
     * @param targetId 目标的id，可能是设计师的id，或者商品的id
     * @param type     关系类型
     * @return
     */
    int deleteByTargetId(Long targetId, String type);

    /**
     * 优惠券定义变动时，清除店铺相关优惠券列表的缓存
     *
     * @param couponId
     */
    void clearProductCouponListCache(Long couponId);

}
