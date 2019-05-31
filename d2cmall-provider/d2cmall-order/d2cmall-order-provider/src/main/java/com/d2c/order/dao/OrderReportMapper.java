package com.d2c.order.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.order.dto.OrderExportDto;
import com.d2c.order.query.DesignerOrderSumSearcher;
import com.d2c.order.query.OrderAnalysisSearcher;
import com.d2c.order.query.OrderSearcher;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface OrderReportMapper extends SuperMapper<OrderExportDto> {

    List<Map<String, Object>> doOrderAnalysis(@Param("searcher") OrderAnalysisSearcher searcher,
                                              @Param("pager") PageModel page);

    int countOrderAnalysis(@Param("searcher") OrderAnalysisSearcher searcher);

    /**
     * 即时设计师排行榜
     *
     * @param searcher
     * @return
     */
    int countRankingByDesigner(@Param("searcher") DesignerOrderSumSearcher searcher);

    List<Map<String, Object>> findRankingByDesigner(@Param("pager") PageModel page,
                                                    @Param("searcher") DesignerOrderSumSearcher searcher);

    int countOrderExportBySearch(@Param("searcher") OrderSearcher searcher);

    List<OrderExportDto> findOrderExportBySearch(@Param("searcher") OrderSearcher searcher,
                                                 @Param("pager") PageModel pager);

    /**
     * 在线支付退款按设备分
     *
     * @param type
     * @return
     */
    Map<String, Object> findThirdPayRefundByDevice(@Param("year") int year, @Param("month") int month,
                                                   @Param("device") String device, @Param("type") String type);

    /**
     * 在线支付换货按设备分
     *
     * @param type
     * @return
     */
    Map<String, Object> finThirdPayExchangeByDevice(@Param("year") int year, @Param("month") int month,
                                                    @Param("device") String device);

    List<Map<String, Object>> findWalletDeliveryAmount(@Param("calculateDate") Date calculateDate);

    List<Map<String, Object>> findWalletReadyAmount(@Param("calculateDate") Date calculateDate);

    List<Map<String, Object>> findWalletRefundAmount(@Param("calculateDate") Date calculateDate,
                                                     @Param("reshiped") Boolean reshiped);

    /**
     * 货到付款收款金额 按天算
     */
    BigDecimal findCodReadyAmount(@Param("calculateDate") Date calculateDate);

    /**
     * 货到付款发货金额分页
     */
    BigDecimal findCodDeliveryAmount(@Param("calculateDate") Date calculateDate);

    /**
     * 货到付款结算金额分页
     */
    BigDecimal findCodBalanceAmount(@Param("calculateDate") Date calculateDate);

    /**
     * 货到付款退款金额分页查询
     */
    BigDecimal findCodRefundAmount(@Param("calculateDate") Date calculateDate);

    /**
     * 货到付款拒收金额
     *
     * @param beginTime
     * @return
     */
    BigDecimal findRefuseAmount(@Param("calculateDate") Date calculateDate);

    /**
     * 在线付款发货金额分页
     */
    List<Map<String, Object>> findOnlineDeliveryAmount(@Param("calculateDate") Date calculateDate);

    /**
     * 在线付款收款金额分页
     */
    List<Map<String, Object>> findOnlineReadyAmount(@Param("calculateDate") Date calculateDate);

    /**
     * 在线支付退款金额
     *
     * @param reship
     */
    List<Map<String, Object>> findOnlineRefundAmount(@Param("calculateDate") Date calculateDate,
                                                     @Param("reship") Boolean reship);

    /**
     * 用户下单分析
     */
    int countBuyerCount(@Param("beginDate") Date beginDate, @Param("endDate") Date endDate);

    BigDecimal findSalesAmount(@Param("beginDate") Date beginDate, @Param("endDate") Date endDate);

    int countRebuyBuyerCount(@Param("beginDate") Date beginDate, @Param("endDate") Date endDate);

    BigDecimal findRebuySalesAmount(@Param("beginDate") Date beginDate, @Param("endDate") Date endDate);

    int countOldCustomerBuyerCount(@Param("beginDate") Date beginDate, @Param("endDate") Date endDate);

    BigDecimal findOldCustomerSalesAmount(@Param("beginDate") Date beginDate, @Param("endDate") Date endDate);

    int countNewCustomerBuyerCount(@Param("beginDate") Date beginDate, @Param("endDate") Date endDate);

    BigDecimal findNewCustomerSalesAmount(@Param("beginDate") Date beginDate, @Param("endDate") Date endDate);

    List<HashMap<String, Object>> groupByOrderSn(@Param("searcher") OrderSearcher searcher);

    BigDecimal findPartnerAmount(@Param("begainDate") Date begainDate, @Param("endDate") Date endDate,
                                 @Param("type") int type);

    BigDecimal findOrdinaryAmount(@Param("begainDate") Date begainDate, @Param("endDate") Date endDate);

    int findPartnerCount(@Param("begainDate") Date begainDate, @Param("endDate") Date endDate, @Param("type") int type);

}
