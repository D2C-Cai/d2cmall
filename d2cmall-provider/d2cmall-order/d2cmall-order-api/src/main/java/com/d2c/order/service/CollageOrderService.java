package com.d2c.order.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.order.dto.CollageOrderDto;
import com.d2c.order.model.CollageOrder;
import com.d2c.order.query.CollageOrderSearcher;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface CollageOrderService {

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    CollageOrder findById(Long id);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    CollageOrderDto findDtoById(Long id);

    /**
     * 根据sn查询
     *
     * @param sn
     * @return
     */
    CollageOrder findBySn(String sn);

    /**
     * 查询任意一条已支付
     *
     * @param groupId
     * @return
     */
    CollageOrder findOneByGroupId(Long groupId);

    /**
     * 根据groupId查询团员
     *
     * @param groupId
     * @return
     */
    List<CollageOrder> findByGroupId(Long groupId);

    /**
     * 通过条件查询，得到分页的换货数据
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<CollageOrderDto> findBySearch(CollageOrderSearcher searcher, PageModel page);

    /**
     * 符合查询条件的数量
     *
     * @param searcher
     * @return
     */
    int countBySearch(CollageOrderSearcher searcher);

    /**
     * 分页查找
     *
     * @param page
     * @param searcher
     * @return
     */
    PageResult<CollageOrderDto> findMyCollage(PageModel page, CollageOrderSearcher searcher);

    /**
     * 统计超时支付的订单
     *
     * @param deadline
     * @param page
     * @return
     */
    int countExpirePayOrder(Date deadline);

    /**
     * 创建订单
     *
     * @param collageOrder
     * @return
     */
    CollageOrder insert(CollageOrder collageOrder);

    /**
     * 查找超时支付的订单
     *
     * @param deadline
     * @param page
     * @return
     */
    PageResult<CollageOrder> findExpirePayOrder(Date deadline, PageModel page);

    /**
     * 超时关闭
     *
     * @param id
     * @param closeMemo
     * @return
     */
    int doClose(Long id, String closeMemo);

    /**
     * 待退款
     *
     * @param id
     * @return
     */
    int doRefund(Long id);

    /**
     * 支付成功
     *
     * @param orderSn
     * @param paymentId
     * @param paySn
     * @param paymentType
     * @param payAmount
     * @param cashAmount
     * @param giftAmount
     * @return
     */
    int doPaySuccess(String orderSn, Long paymentId, String paySn, Integer paymentType, BigDecimal payAmount,
                     BigDecimal cashAmount, BigDecimal giftAmount);

    /**
     * 超时关闭退款
     *
     * @param id
     * @param payAmount
     * @param paymentType
     * @param paySn
     * @param paymentId
     * @return
     */
    int doCloseRefund(Long id, Long paymentId, String paySn, Integer paymentType, BigDecimal payAmount);

    /**
     * 拼团成功
     *
     * @param id
     * @return
     */
    int doSuccess(Long id);

    /**
     * 查找某用户对于某活动是否存在正在进行中的团
     *
     * @param id
     * @param memberId
     * @return
     */
    CollageOrder findExistProcess(Long id, Long memberId);

    /**
     * 退款成功
     *
     * @param id
     * @param operator
     * @param memo
     * @return
     */
    int doRefundSuccess(Long id, String operator, String refundMemo, Integer refundPaymentType, String refundPaySn);

    /**
     * 查找该用户是否已参加该团，除去超时关闭的
     *
     * @param memberId
     * @param groupId
     * @return
     */
    CollageOrder findByMemberIdAndGroupId(Long memberId, Long groupId);

    /**
     * 根据sn和memberId查找
     *
     * @param orderSn
     * @param memberId
     * @return
     */
    CollageOrderDto findBySnAndMemberId(String orderSn, Long memberId);

    /**
     * 根据memberId和id查找
     *
     * @param id
     * @param memberId
     * @return
     */
    CollageOrderDto findByIdAndMemberId(Long id, Long memberId);

    /**
     * 查询未支付的
     *
     * @param collageId
     * @param memberInfo
     * @return
     */
    CollageOrder findUnpayByPromotion(Long promotionId, Long memberId);

    /**
     * 修改角色类型
     *
     * @param id
     * @param code
     * @return
     */
    int updateType(Long id, int type);

}
