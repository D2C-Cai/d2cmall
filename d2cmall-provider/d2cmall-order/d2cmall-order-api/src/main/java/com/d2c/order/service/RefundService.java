package com.d2c.order.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.order.dto.RefundDto;
import com.d2c.order.model.Refund;
import com.d2c.order.query.RefundSearcher;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
public interface RefundService {

    /**
     * 根据id获取退款单详情
     *
     * @param id
     * @return
     */
    Refund findById(Long id);

    /**
     * 根据退款单id及用户id获取退货单信息
     *
     * @param refundId
     * @param memberInfoId
     * @return
     */
    Refund findByIdAndMemberInfoId(Long refundId, Long memberInfoId);

    /**
     * 根据退款编号获取退货单信息
     *
     * @param refundSn
     * @return
     */
    Refund findByRefundSn(String refundSn);

    /**
     * 根据RefundSearcher过滤器内的过滤条件获取相关退款单信息
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<Refund> findMine(RefundSearcher searcher, PageModel page);

    /**
     * 根据RefundSearcher过滤器内的过滤条件获取相关退款单信息， 采用分页形式，封装成PageResult对象返回
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<RefundDto> findBySearch(RefundSearcher searcher, PageModel page);

    /**
     * 根据RefundSearcher查询数量
     *
     * @param searcher
     * @return
     */
    int countBySearch(RefundSearcher searcher);

    /**
     * 统计个人的退款单状态数量
     *
     * @param memberInfoId
     * @return
     */
    Map<String, Object> countByStatusAndMemberId(Long memberInfoId);

    /**
     * 按状态分组查询退款数量
     *
     * @return
     */
    Map<String, Object> countRefundsMaps();

    /**
     * 生成退款单，同一笔明细，不能有两笔及以上退款明细
     *
     * @param refund
     * @return
     */
    Refund insert(Refund refund);

    /**
     * 批量 生成退款单
     *
     * @param refunds
     * @return
     */
    int insertBatch(List<Refund> refunds);

    /**
     * 根据退款单id设置对应的退货单id
     *
     * @param refundId
     * @param reshipId
     * @return
     */
    int updateReshipId(Long refundId, Long reshipId);

    /**
     * 修改退款账户
     *
     * @param refundId        退款单id
     * @param backAccountSn   新账户号
     * @param backAccountName 新账户号名称
     * @param creator         修改人
     * @return
     */
    int updateBackAccount(Long refundId, String backAccountSn, String backAccountName, String creator);

    /**
     * 根据退货单id修改退款金额
     *
     * @param totalAmount 退款金额
     * @param refundId    退货单id
     * @return
     */
    int updateAmount(BigDecimal totalAmount, Long refundId);

    /**
     * 钱包支付更新本金赠金
     *
     * @param id
     * @param cashAmount
     * @param gitfAmount
     * @return
     */
    int updateWalletAmount(Long id, BigDecimal cashAmount, BigDecimal gitfAmount);

    /**
     * 支付回调错误，更新错误码
     *
     * @param refundSn
     * @param resultCode
     * @return
     */
    int updateErrorCode(String refundSn, String resultCode);

    /**
     * 客服同意申请单
     *
     * @param refundId    退款单id
     * @param totalAmount 退款金额
     * @param username    用户名称
     * @param info        客户备注信息
     * @return
     */
    int doCustomerAgree(Long refundId, BigDecimal totalAmount, String username, String info);

    /**
     * 平台关闭退款申请单
     *
     * @param refundId  退款单id
     * @param username  关闭人员
     * @param closeInfo 关闭理由
     * @return
     */
    int doCustomerRefuse(Long refundId, String username, String closeInfo);

    /**
     * 客服锁定退款单
     *
     * @param refundId 退款单id
     * @param username 锁定人员
     * @return
     */
    int doLock(Long refundId, String username);

    /**
     * 客服取消退款单锁定
     *
     * @param refundId 退款单id
     * @param username 取消锁定人员
     * @return
     */
    int doCancelLock(Long refundId, String username);

    /**
     * 财务退回
     *
     * @param refundId 退款单id
     * @param username 操作人
     * @param string   备注
     * @return
     */
    int doBack(Long refundId, String username, String string);

    /**
     * 用户取消退款申请
     *
     * @param refundId  退款单id
     * @param modifyMan 修改人
     * @return
     */
    int doCancel(Long refundId, String modifyMan);

    /**
     * 更新赔偿金额
     *
     * @param id
     * @param compansation
     * @return
     */
    int doCompensation(Long id, BigDecimal compensation);

    /**
     * 退款退货成功
     *
     * @param refundId
     * @param backAccountType
     * @param payDate
     * @param payMoney
     * @param paySn
     * @param payer
     * @param ip
     * @param allRefund
     * @param userId
     * @return
     */
    int doSuccess(Long refundId, Integer backAccountType, Date payDate, BigDecimal payMoney, String paySn, String payer,
                  int allRefund);

    /**
     * 第三方支付平台支付回调
     *
     * @param refundSn
     * @param notifyTime
     * @param payMoney
     * @param paySn
     * @return
     */
    int doThirdSuccess(Long refundId, String paySn, Date notifyTime);

}
