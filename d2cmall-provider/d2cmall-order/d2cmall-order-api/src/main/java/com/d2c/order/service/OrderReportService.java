package com.d2c.order.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.order.dto.OrderExportDto;
import com.d2c.order.query.DesignerOrderSumSearcher;
import com.d2c.order.query.OrderAnalysisSearcher;
import com.d2c.order.query.OrderSearcher;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface OrderReportService {

    /**
     * 用户下单分析
     *
     * @param type
     * @return
     */
    PageResult<Map<String, Object>> findOrderAnalysis(OrderAnalysisSearcher searcher, PageModel page);

    int countOrderAnalysis(OrderAnalysisSearcher searcher);

    /**
     * 第三方支付用户下单
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<Map<String, Object>> findThirdPayList(OrderAnalysisSearcher searcher, PageModel page);

    /**
     * 即时设计师销售排行榜
     *
     * @param page
     * @param searcher
     * @return
     */
    PageResult<Map<String, Object>> findRankingByDesigner(PageModel page, DesignerOrderSumSearcher searcher);

    int countRankingByDesigner(DesignerOrderSumSearcher searcher);

    int countBySearch(OrderSearcher searcher);

    List<OrderExportDto> findBySearch(OrderSearcher searcher, PageModel page);

    PageResult<OrderExportDto> findPageBySearch(OrderSearcher searcher, PageModel page);

    List<Map<String, Object>> findWalletDeliveryAmount(Date calculateDate);

    List<Map<String, Object>> findWalletReadyAmount(Date calculateDate);

    List<Map<String, Object>> findWalletRefundAmount(Date calculateDate, Boolean reshipeds);

    /**
     * 货到付款下单金额统计
     *
     * @param beginTime
     * @return
     */
    BigDecimal findCodReadyAmount(Date beginTime);

    /**
     * 货到付款发货金额统计
     *
     * @param beginTime
     * @return
     */
    BigDecimal findCodDeliveryAmount(Date beginTime);

    /**
     * 货到付款退款金额
     *
     * @param beginTime
     * @return
     */
    BigDecimal findCodRefundAmount(Date beginTime);

    /**
     * 货到付款结算金额统计
     *
     * @param beginTime
     * @return
     */
    BigDecimal findCodBalanceAmount(Date beginTime);

    /**
     * 拒收金额
     *
     * @param beginTime
     * @return
     */
    BigDecimal findRefuseAmount(Date beginTime);

    /**
     * 在线付款发货金额
     *
     * @param lastTime
     * @return
     */
    List<Map<String, Object>> findOnlineDeliveryAmount(Date lastTime);

    /**
     * 在线付款退款金额
     *
     * @param lastTime
     * @param reship   区分仅退款还是退款退货 true 退款退货 ;false 仅退款 ;null 全部
     * @return
     */
    List<Map<String, Object>> findOnlineRefundAmount(Date lastTime, Boolean reship);

    /**
     * 在线付款收款金额
     *
     * @param lastTime
     * @return
     */
    List<Map<String, Object>> findOnlineReadyAmount(Date lastTime);

    /**
     * 用户下单分析
     */
    int countBuyerCount(Date beginDate, Date endDate);

    BigDecimal findSalesAmount(Date beginDate, Date endDate);

    int countRebuyBuyerCount(Date beginDate, Date endDate);

    BigDecimal findRebuySalesAmount(Date beginDate, Date endDate);

    int countOldCustomerBuyerCount(Date beginDate, Date endDate);

    BigDecimal findOldCustomerSalesAmount(Date beginDate, Date endDate);

    int countNewCustomerBuyerCount(Date beginDate, Date endDate);

    BigDecimal findNewCustomerSalesAmount(Date beginDate, Date endDate);

    /**
     * 按查询条件统计订单实付金额
     *
     * @param query
     * @return
     */
    List<HashMap<String, Object>> groupByOrderSn(OrderSearcher query);

    /**
     * 分销订单分销
     *
     * @param begainDate
     * @param endDate
     * @return
     */
    BigDecimal findPartnerAmount(Date begainDate, Date endDate, int type);

    BigDecimal findOrdinaryAmount(Date begainDate, Date endDate);

    int findPartnerCount(Date begainDate, Date endDate, int type);

}
