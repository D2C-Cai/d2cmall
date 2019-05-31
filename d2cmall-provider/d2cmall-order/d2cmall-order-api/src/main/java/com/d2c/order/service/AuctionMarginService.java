package com.d2c.order.service;

import com.d2c.common.api.dto.HelpDTO;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.order.dto.AuctionMarginDto;
import com.d2c.order.model.AuctionMargin;
import com.d2c.order.query.AuctionMarginSearcher;

import java.math.BigDecimal;
import java.util.List;

public interface AuctionMarginService {

    /**
     * 添加一条拍卖保证金的实体类
     *
     * @param margin
     * @return
     */
    AuctionMargin insert(AuctionMargin margin);

    /**
     * 逻辑删除对应的拍卖保证金，将数据状态改成已删除状态
     *
     * @param id
     * @return
     */
    int delete(Long id);

    /**
     * 通过searcher查询
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<AuctionMargin> findBySearcher(AuctionMarginSearcher searcher, PageModel page);

    /**
     * 通过searcher查询数量
     *
     * @param searcher
     * @return
     */
    int countBySearch(AuctionMarginSearcher searcher);

    /**
     * 通过searcher查询
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<AuctionMarginDto> findDtoBySearcher(AuctionMarginSearcher searcher, PageModel page);

    /**
     * 通过id得到保证金的实体类
     *
     * @param id
     * @return
     */
    AuctionMargin findById(Long id);

    /**
     * 根据保证金的单号查询
     *
     * @param marginSn 保证金的单号
     * @return
     */
    AuctionMargin findByMarginSn(String marginSn);

    /**
     * 根据保证金id查询
     *
     * @param id 保证金id
     * @return
     */
    AuctionMarginDto findByIdAndMemberId(Long id, Long memberId);

    /**
     * 根据保证金的单号查询
     *
     * @param marginSn 保证金的单号
     * @return
     */
    AuctionMarginDto findByMarginSnAndMemberId(String marginSn, Long memberId);

    /**
     * 通过拍卖id和会员的id，得到一条保证金实体数据
     *
     * @param auctionProductId 拍卖id
     * @param memberId         会员id
     * @return
     */
    AuctionMarginDto findByMemberIdAndAuctionId(Long memberId, Long auctionId);

    /**
     * 通过查询条件和分页条件，得到简单的保证金数据集合
     *
     * @param searcher
     * @param page
     * @return
     */
    List<HelpDTO> findHelpDtos(AuctionMarginSearcher searcher, PageModel page);

    /**
     * 对指定的保证金更新支付金额
     *
     * @param offer    支付金额
     * @param marginId 保证金id
     * @return
     */
    int updateOffer(BigDecimal offer, Long marginId);

    /**
     * 更新收货地址
     *
     * @param addressId
     * @param marginId
     * @return
     */
    int updateAddress(Long addressId, Long marginId);

    /**
     * 将指定的保证金设置的竞拍成功状态
     *
     * @param marginId 保证金id
     * @param offer    支付金额
     * @return
     */
    int doSuccess(Long marginId, BigDecimal offer);

    /**
     * 拍卖结束后修改未中拍的参与者的保证金状态
     *
     * @param auctionId 拍卖活动id
     * @return
     */
    int doOut(Long auctionId);

    /**
     * 对指定的保证金数据进行退款操作
     *
     * @param marginId   保证金id
     * @param refundType 支付方式
     * @param refundSn   支付凭据
     * @param refunder   退款人
     * @return
     */
    int doBackMargin(Long marginId, String refundType, String refundSn, String refunder);

    /**
     * 支付成功回调
     *
     * @param paymentId   支付id
     * @param paySn       支付凭据
     * @param paymentType 支付
     * @param orderSn     订单凭据
     * @param payedAmount 支付金额
     * @return
     */
    int doPaySuccess(Long paymentId, String paySn, Integer paymentType, String orderSn, BigDecimal payedAmount);

    /**
     * 将指定保证金状态改为违约状态
     *
     * @param id
     * @return
     */
    int doBreachAuction(Long id);

    /**
     * 获取某一个拍卖或的最大状态值
     *
     * @param id
     * @return
     */
    int getMaxStatus(Long auctionId);

    /**
     * @param id
     * @param deliveryCorpName 物流公司
     * @param deliverySn       物流编码
     * @return
     */
    int doDelivery(Long id, String deliveryCorpName, String deliverySn);

    /**
     * 将指定保证金状态改为用户确认收货
     *
     * @param id
     * @return
     */
    int doReceived(Long id);

}
