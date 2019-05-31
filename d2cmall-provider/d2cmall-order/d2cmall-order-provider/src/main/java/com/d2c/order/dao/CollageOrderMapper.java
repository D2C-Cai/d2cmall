package com.d2c.order.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.order.model.CollageOrder;
import com.d2c.order.query.CollageOrderSearcher;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface CollageOrderMapper extends SuperMapper<CollageOrder> {

    /**
     * 根据拼单编号查询
     *
     * @param sn
     * @return
     */
    CollageOrder findBySn(@Param("sn") String sn);

    /**
     * 查找任意一单成功支付的参团单
     *
     * @param groupId
     * @return
     */
    CollageOrder findOneByGroupId(@Param("groupId") Long groupId);

    /**
     * 查找参团单,除去超时关闭的
     *
     * @param groupId
     * @return
     */
    List<CollageOrder> findByGroupId(@Param("groupId") Long groupId);

    /**
     * 查找该用户是否已参加该团，除去超时关闭的
     *
     * @param memberId
     * @param groupId
     * @return
     */
    CollageOrder findByMemberIdAndGroupId(@Param("memberId") Long memberId, @Param("groupId") Long groupId);

    /**
     * 根据会员id和orderSn查找
     *
     * @param sn
     * @param memberId
     * @return
     */
    CollageOrder findBySnAndMemberId(@Param("sn") String sn, @Param("memberId") Long memberId);

    /**
     * 根据会员id和id查找
     *
     * @param id
     * @param memberId
     * @return
     */
    CollageOrder findByIdAndMemberId(@Param("id") Long id, @Param("memberId") Long memberId);

    /**
     * 根据会员id和活动id查找未支付的
     *
     * @param id
     * @param memberId
     * @return
     */
    CollageOrder findUnpayByPromotion(@Param("promotionId") Long promotionId, @Param("memberId") Long memberId);

    /**
     * 查找该商品该用户是否存在正在进行中的（待付款和正在进行）
     *
     * @param promotionId
     * @param memberId
     * @return
     */
    CollageOrder findExistProcess(@Param("promotionId") Long promotionId, @Param("memberId") Long memberId);

    /**
     * 分页查找
     *
     * @param searcher
     * @param pager
     * @return
     */
    List<CollageOrder> findBySearch(@Param("searcher") CollageOrderSearcher searcher, @Param("pager") PageModel pager);

    /**
     * 分页统计
     *
     * @param searcher
     * @return
     */
    int countBySearch(@Param("searcher") CollageOrderSearcher searcher);

    /**
     * 支付成功
     *
     * @param paymentId
     * @param paySn
     * @param paymentType
     * @param orderSn
     * @param payAmount
     * @param cashAmount
     * @param giftAmount
     * @return
     */
    int doPaySuccess(@Param("paymentId") Long paymentId, @Param("paySn") String paySn,
                     @Param("paymentType") Integer paymentType, @Param("orderSn") String orderSn,
                     @Param("paymentAmount") BigDecimal payAmount, @Param("cashAmount") BigDecimal cashAmount,
                     @Param("giftAmount") BigDecimal giftAmount);

    /**
     * 超时关闭
     *
     * @param id
     * @param closeMemo
     * @return
     */
    int doClose(@Param("id") Long id, @Param("closeMemo") String closeMemo);

    /**
     * 待退款
     *
     * @param id
     * @return
     */
    int doRefund(@Param("id") Long id);

    /**
     * 超时关闭变成待退款
     *
     * @param id
     * @param paymentAmount
     * @param paymentType
     * @param paySn
     * @param paymentId
     * @return
     */
    int doCloseRefund(@Param("id") Long id, @Param("paymentId") Long paymentId, @Param("paySn") String paySn,
                      @Param("paymentType") Integer paymentType, @Param("paymentAmount") BigDecimal paymentAmount);

    /**
     * /** 拼团成功
     *
     * @param id
     * @return
     */
    int doSuccess(@Param("id") Long id);

    /**
     * 退款成功
     *
     * @param id
     * @param operator
     * @return
     */
    int doRefundSuccess(@Param("id") Long id, @Param("operator") String operator,
                        @Param("refundMemo") String refundMemo, @Param("refundPaymentType") Integer refundPaymentType,
                        @Param("refundPaySn") String refundPaySn);

    /**
     * 修改角色类型
     *
     * @param id
     * @param code
     * @return
     */
    int updateType(@Param("id") Long id, @Param("type") int type);

}
