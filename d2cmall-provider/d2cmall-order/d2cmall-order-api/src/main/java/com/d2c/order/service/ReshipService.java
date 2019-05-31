package com.d2c.order.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.order.dto.ReshipDto;
import com.d2c.order.model.Refund;
import com.d2c.order.model.Reship;
import com.d2c.order.query.ReshipSearcher;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ReshipService {

    /**
     * 通过id，得到退货实体数据
     *
     * @param id
     * @return
     */
    Reship findById(Long id);

    /**
     * 通过退货单号
     *
     * @param reshipSn
     * @return
     */
    Reship findBySn(String reshipSn);

    /**
     * 通过退货id和会员得到退货数据
     *
     * @param reshipId
     * @param id
     * @return
     */
    Reship findByIdAndMemberInfoId(Long reshipId, Long id);

    /**
     * 根据物流单号查询
     *
     * @param nu
     * @return
     */
    List<Reship> findByDeliverySn(String nu);

    /**
     * 通过查询条件得到退货分页数据
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<ReshipDto> findMine(ReshipSearcher searcher, PageModel page);

    /**
     * 通过查询条件得到退货分页数据
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<ReshipDto> findBySearch(ReshipSearcher searcher, PageModel page);

    /**
     * 查询总数
     *
     * @param searcher
     * @return
     */
    int countBySearcher(ReshipSearcher searcher);

    /**
     * 统计设计师的退款情况
     *
     * @param designerId
     * @return
     */
    Map<String, Object> findAmountByDesigner(Long designerId);

    /**
     * 退款退货生成对账单明细
     *
     * @param beginDate
     * @param page
     * @return
     */
    PageResult<Reship> findReshipForStatement(Date beginDate, PageModel page);

    /**
     * 用户未发货关闭
     *
     * @return
     */
    List<Reship> findNotDeliveryClose();

    /**
     * 发货提醒
     *
     * @param day
     * @return
     */
    List<Reship> findNotDeliveryRemind(Integer day);

    /**
     * 统计个人的退货单状态数量
     *
     * @param memberInfoId
     * @return
     */
    Map<String, Object> countByStatusAndMemberId(Long memberInfoId);

    /**
     * 统计退货数据
     *
     * @return
     */
    Map<String, Object> countReshipsMaps();

    /**
     * 添加一条退货数据和退款数据
     *
     * @param reship
     * @return
     */
    Reship insert(Reship reship);

    /**
     * 用户发货
     *
     * @param reshipId
     * @param deliverySn
     * @param deliveryCorpName
     * @param memo
     * @param creator
     * @return
     */
    int doLogistic(Long reshipId, String deliverySn, String deliveryCorpName, String memo, String creator);

    /**
     * 用户取消退货申请
     *
     * @param reshipId
     * @param modifyMan
     * @return
     */
    int doCancel(Long reshipId, String modifyMan);

    /**
     * 同意退货
     *
     * @param reshipId
     * @param totalAmount
     * @param modifyMan
     * @param info
     * @param ip
     * @param backAddress
     * @param backConsignee
     * @param backMobile
     * @return
     */
    int doCustomerAgree(Long reshipId, BigDecimal totalAmount, String modifyMan, String info, String ip,
                        String backAddress, String backConsignee, String backMobile);

    /**
     * 客服批量拒绝退货
     *
     * @param reship
     * @param modifyMan
     * @param info
     * @return
     */
    int doCustomerRefuse(Long reshipId, String modifyMan, String info);

    /**
     * 客服关闭退货单
     *
     * @param reship
     * @param modifyMan
     * @param info
     * @return
     */
    int doCustomerClose(Long reshipId, String modifyMan, String info);

    /**
     * 门店同意退货
     *
     * @param reship
     * @param username
     * @param info
     * @param refund
     * @return
     */
    int doStoreAgree(Long reshipId, String modifyMan, String info, Refund refund);

    /**
     * 仓库拒绝退货
     *
     * @param reship
     * @param modifyMan
     * @param info
     * @return
     */
    int doStoreRefuse(Long reshipId, String modifyMan, String info);

    /**
     * 系统关闭退货
     *
     * @param reshipId
     * @param closer
     * @param info
     * @return
     */
    int doSysCloseReship(Long reshipId, String closer, String info);

    /**
     * 更新退货地址
     *
     * @param id
     * @param backAddress
     * @param backConsignee
     * @param backMobile
     * @return
     */
    int updateBackAddress(Long id, String backAddress, String backConsignee, String backMobile);

}
