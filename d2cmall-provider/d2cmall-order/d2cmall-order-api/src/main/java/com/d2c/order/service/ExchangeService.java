package com.d2c.order.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.order.dto.ExchangeDto;
import com.d2c.order.model.Exchange;
import com.d2c.order.model.Refund;
import com.d2c.order.query.ExchangeSearcher;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ExchangeService {

    /**
     * 通过id，查换货数据
     *
     * @param exchangeId
     * @return
     */
    Exchange findById(Long exchangeId);

    /**
     * 通过换货的id和会员的id，得到换货的实体数据
     *
     * @param exchangeId
     * @param memberId
     * @return
     */
    Exchange findByIdAndMemberInfoId(Long exchangeId, Long memberId);

    /**
     * 通过sn，查换货数据
     *
     * @param exchangeSn
     * @return
     */
    Exchange findBySn(String exchangeSn);

    /**
     * 通过物流查询
     *
     * @param applyNu   用户申请换货所填物流
     * @param exchangNu 仓库换货给客户所填物流
     * @param applyCom  用户申请换货所填物流公司编号
     * @return exchangCom
     */
    List<Exchange> findByDeliverySn(String nu);

    /**
     * 通过条件查询，得到分页的换货数据
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<ExchangeDto> findMine(ExchangeSearcher searcher, PageModel page);

    /**
     * 通过条件查询，得到分页的换货数据
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<ExchangeDto> findBySearch(ExchangeSearcher searcher, PageModel page);

    /**
     * 符合查询条件的数量
     *
     * @param searcher
     * @return
     */
    int countBySearch(ExchangeSearcher searcher);

    /**
     * 距离客服发货15天的，系统默认收到货
     *
     * @param date
     * @param i
     * @param page
     * @return
     */
    PageResult<Exchange> findDeliveredByDate(Date date, int i, PageModel page);

    /**
     * 距离客服发货15天的数量
     *
     * @param date
     * @param i
     * @return
     */
    int countDeliveredByDate(Date date, int i);

    /**
     * 根据换货单的状态统计个人
     *
     * @return
     */
    Map<String, Object> countByStatusAndMemberId(Long id);

    /**
     * 添加一条换货的数据
     *
     * @param exchange
     * @param username
     * @return
     */
    Exchange insert(Exchange exchange, String username);

    /**
     * 在客服同意之后，代客填写物流
     *
     * @param exchangeId
     * @param deliverCorp
     * @param deliverSn
     * @param modifyMan
     * @return
     */
    int doLogistic(Long exchangeId, String deliverCorp, String deliverSn, String modifyMan);

    /**
     * 用户收货
     *
     * @param exchangeId
     * @return
     */
    int doReceive(Long exchangeId);

    /**
     * 用户取消
     *
     * @param exchangeId
     * @param loginCode
     * @return
     */
    int doCancel(Long exchangeId, String modifyMan);

    /**
     * 客服同意
     *
     * @param exchangeId
     * @param modifyMan
     * @param info
     * @param exchangePrice
     * @return
     */
    int doCustomerAgree(Long exchangeId, String modifyMan, String info, BigDecimal exchangePrice, String ip,
                        String backAddress, String backConsignee, String backMobile);

    /**
     * 客服拒绝
     *
     * @param exchangeId
     * @param modifyMan
     * @param info
     * @return
     */
    int doCustomerRefuse(Long exchangeId, String modifyMan, String info);

    /**
     * 代客关闭换货
     *
     * @param exchangeId
     * @param modifyMan
     * @param info
     * @return
     */
    int doCustomerClose(Long exchangeId, String modifyMan, String info);

    /**
     * 客服发货
     *
     * @param exchangeId
     * @param modifyMan
     * @param exchangeDeliveryCorp
     * @param exchangeDeliverySn
     * @return
     */
    int doCustomerDeliver(Long exchangeId, String modifyMan, String exchangeDeliveryCorp, String exchangeDeliverySn);

    /**
     * 仓库收货
     *
     * @param exchangeId
     * @param modifyMan
     * @param info
     * @return
     */
    int doStoreAgree(Long exchangeId, String modifyMan, String info);

    /**
     * 仓库拒绝换货
     *
     * @param exchangeId
     * @param modifyMan
     * @param info
     * @return
     */
    int doStoreRefuse(Long exchangeId, String modifyMan, String info);

    /**
     * 代客换货退款
     *
     * @param exchangeId
     * @param modifyMan
     * @param info
     * @param refund
     * @return
     */
    int doRefundClose(Long exchangeId, String modifyMan, String info, Refund refund);

    /**
     * 根据退款单更新换货单ID
     *
     * @param exchangeId
     * @param refundId
     * @return
     */
    int updateRefundId(Long exchangeId, Long refundId);

    /**
     * 客服选择更新要换货的SKU
     *
     * @param skuId
     * @param exchangeId
     * @param modifyMan
     * @return
     */
    int updateToChangeSku(Long skuId, Long exchangeId, String modifyMan);

    /**
     * 更新退货地址
     *
     * @param id
     * @param backAddress
     * @param backMobile
     * @param backConsignee
     * @return
     */
    int updateBackAddress(Long id, String backAddress, String backMobile, String backConsignee);

}
