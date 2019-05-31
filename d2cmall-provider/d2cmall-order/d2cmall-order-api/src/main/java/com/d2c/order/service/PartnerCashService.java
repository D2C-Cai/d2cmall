package com.d2c.order.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.order.model.PartnerCash;
import com.d2c.order.query.PartnerCashSearcher;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface PartnerCashService {

    /**
     * 新增数据
     *
     * @param partnerCash
     * @return
     */
    PartnerCash insert(PartnerCash partnerCash);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    PartnerCash findById(Long id);

    /**
     * 通过提现单号查询
     *
     * @param sn
     * @return
     */
    PartnerCash findBySn(String sn);

    /**
     * 查询提现中的单据
     *
     * @param loginCode
     * @return
     */
    PartnerCash findActiveByMobile(String loginCode);

    /**
     * 获取提现中的金额
     *
     * @param partnerId
     * @return
     */
    BigDecimal findApplyCashByPartnerId(Long partnerId);

    /**
     * 查询已经提现的金额
     *
     * @param partnerId
     * @param startDate
     * @param endDate
     * @param taxType
     * @return
     */
    BigDecimal findWithCashByDate(Long partnerId, Date startDate, Date endDate, Integer taxType);

    /**
     * 根据searcher查询
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<PartnerCash> findBySearcher(PartnerCashSearcher searcher, PageModel page);

    /**
     * 查询数量
     *
     * @param searcher
     * @return
     */
    Integer countBySearcher(PartnerCashSearcher searcher);

    /**
     * 根据状态统计
     *
     * @return
     */
    List<Map<String, Object>> findCountGroupByStatus();

    /**
     * 查询本月最后一条
     *
     * @return
     */
    PartnerCash findLastSuccessOne(Long partnerId, Date startDate, Date endDate, Integer taxType);

    /**
     * 运营同意
     *
     * @param id
     * @param confirmMan
     * @param confirmOperateMan
     * @return
     */
    int doAgree(Long id, String confirmMan, String confirmOperateMan);

    /**
     * 拒绝付款
     *
     * @param id
     * @param refuseReason
     * @param refuseMan
     * @param confirmOperateMan
     * @return
     */
    int doRefuse(Long id, String refuseReason, String refuseMan, String confirmOperateMan);

    /**
     * 同意付款
     *
     * @param id
     * @param paySn
     * @param applyAmount
     * @param payMan
     * @param payDate
     * @return
     */
    int doPay(Long id, String paySn, BigDecimal applyAmount, String payMan, Date payDate);

}
