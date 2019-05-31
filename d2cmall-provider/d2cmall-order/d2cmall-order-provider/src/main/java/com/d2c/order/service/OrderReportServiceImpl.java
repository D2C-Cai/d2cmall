package com.d2c.order.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.order.dao.OrderReportMapper;
import com.d2c.order.dto.OrderExportDto;
import com.d2c.order.query.DesignerOrderSumSearcher;
import com.d2c.order.query.OrderAnalysisSearcher;
import com.d2c.order.query.OrderSearcher;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service("orderReportService")
public class OrderReportServiceImpl implements OrderReportService {

    @Autowired
    private OrderReportMapper orderReportMapper;

    @Override
    public PageResult<Map<String, Object>> findOrderAnalysis(OrderAnalysisSearcher searcher, PageModel page) {
        PageResult<Map<String, Object>> pager = new PageResult<>(page);
        int totalCount = orderReportMapper.countOrderAnalysis(searcher);
        if (totalCount > 0) {
            // 查出本月所有的购买量
            List<Map<String, Object>> allBuy = orderReportMapper.doOrderAnalysis(searcher, page);
            // 本月首次购买的用户
            searcher.setFirstBuy(true);
            List<Map<String, Object>> firstBuy = orderReportMapper.doOrderAnalysis(searcher, page);
            Integer zero = new Integer(0);
            for (Map<String, Object> all : allBuy) {
                if (firstBuy.size() > 0) {
                    for (Map<String, Object> first : firstBuy) {
                        if (all.get("year").equals(first.get("year")) && all.get("month").equals(first.get("month"))
                                && all.get("device").equals(first.get("device"))) {
                            all.put("firstMemberCount", first.get("memberCount"));
                            all.put("firstOrderCount", first.get("orderCount"));
                            all.put("firstQuantity", first.get("quantity"));
                            all.put("firstAmount", first.get("amount"));
                            break;
                        }
                        if (firstBuy.get(firstBuy.size() - 1).equals(first)) {
                            all.put("firstMemberCount", zero);
                            all.put("firstOrderCount", zero);
                            all.put("firstQuantity", zero);
                            all.put("firstAmount", zero);
                        }
                    }
                } else {
                    all.put("firstMemberCount", zero);
                    all.put("firstOrderCount", zero);
                    all.put("firstQuantity", zero);
                    all.put("firstAmount", zero);
                }
                all.put("device", convertDevice(all.get("device").toString()));
            }
            pager.setList(allBuy);
        }
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    public PageResult<Map<String, Object>> findThirdPayList(OrderAnalysisSearcher searcher, PageModel page) {
        PageResult<Map<String, Object>> pager = new PageResult<>(page);
        int totalCount = orderReportMapper.countOrderAnalysis(searcher);
        if (totalCount > 0) {
            // 订单的
            List<Map<String, Object>> orders = orderReportMapper.doOrderAnalysis(searcher, page);
            Integer zero = new Integer(0);
            for (Map<String, Object> order : orders) {
                int year = Integer.parseInt(order.get("year").toString());
                int month = Integer.parseInt(order.get("month").toString());
                String device = order.get("device").toString();
                order.put("device", convertDevice(order.get("device").toString()));
                Map<String, Object> reships = orderReportMapper.findThirdPayRefundByDevice(year, month, device,
                        "reship");
                // 仅退款
                Map<String, Object> refunds = orderReportMapper.findThirdPayRefundByDevice(year, month, device,
                        "refund");
                // 换货
                Map<String, Object> exchanges = orderReportMapper.finThirdPayExchangeByDevice(year, month, device);
                if (reships != null && reships.size() > 0) {
                    order.put("reshipCount", reships.get("count"));
                    order.put("reshipAmount", reships.get("amount"));
                } else {
                    order.put("reshipCount", zero);
                    order.put("reshipAmount", zero);
                }
                if (refunds != null && refunds.size() > 0) {
                    order.put("refundCount", refunds.get("count"));
                    order.put("refundAmount", refunds.get("amount"));
                } else {
                    order.put("refundCount", zero);
                    order.put("refundAmount", zero);
                }
                if (exchanges != null && exchanges.size() > 0) {
                    order.put("exchangeCount", exchanges.get("count"));
                    order.put("exchangeAmount", exchanges.get("amount"));
                } else {
                    order.put("exchangeCount", zero);
                    order.put("exchangeAmount", zero);
                }
            }
            pager.setList(orders);
        }
        pager.setTotalCount(totalCount);
        return pager;
    }

    private String convertDevice(String device) {
        if (StringUtils.isNotBlank(device)) {
            switch (device) {
                case "PC":
                    return "电脑PC";
                case "WAP":
                    return "手机WAP";
                case "APPIOS":
                    return "苹果APP";
                case "APPANDROID":
                    return "安卓APP";
                case "MOBILE":
                    return "APP";
            }
        }
        return "未知";
    }

    @Override
    public PageResult<Map<String, Object>> findRankingByDesigner(PageModel page, DesignerOrderSumSearcher searcher) {
        List<Map<String, Object>> list = new ArrayList<>();
        PageResult<Map<String, Object>> pager = new PageResult<>(page);
        int totalCount = orderReportMapper.countRankingByDesigner(searcher);
        if (totalCount > 0) {
            list = orderReportMapper.findRankingByDesigner(page, searcher);
        }
        pager.setList(list);
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    public int countRankingByDesigner(DesignerOrderSumSearcher searcher) {
        return orderReportMapper.countRankingByDesigner(searcher);
    }

    @Override
    public int countBySearch(OrderSearcher searcher) {
        return orderReportMapper.countOrderExportBySearch(searcher);
    }

    @Override
    public List<OrderExportDto> findBySearch(OrderSearcher searcher, PageModel page) {
        return orderReportMapper.findOrderExportBySearch(searcher, page);
    }

    @Override
    public PageResult<OrderExportDto> findPageBySearch(OrderSearcher searcher, PageModel page) {
        PageResult<OrderExportDto> pager = new PageResult<>(page);
        int totalCount = orderReportMapper.countOrderExportBySearch(searcher);
        List<OrderExportDto> list = new ArrayList<>();
        if (totalCount > 0) {
            list = orderReportMapper.findOrderExportBySearch(searcher, page);
        }
        pager.setTotalCount(totalCount);
        pager.setList(list);
        return pager;
    }

    @Override
    public int countOrderAnalysis(OrderAnalysisSearcher searcher) {
        return orderReportMapper.countOrderAnalysis(searcher);
    }

    @Override
    public List<Map<String, Object>> findWalletDeliveryAmount(Date calculateDate) {
        return orderReportMapper.findWalletDeliveryAmount(calculateDate);
    }

    @Override
    public List<Map<String, Object>> findWalletReadyAmount(Date calculateDate) {
        return orderReportMapper.findWalletReadyAmount(calculateDate);
    }

    @Override
    public List<Map<String, Object>> findWalletRefundAmount(Date calculateDate, Boolean reshiped) {
        return orderReportMapper.findWalletRefundAmount(calculateDate, reshiped);
    }

    @Override
    public BigDecimal findCodReadyAmount(Date beginTime) {
        return orderReportMapper.findCodReadyAmount(beginTime);
    }

    @Override
    public BigDecimal findCodDeliveryAmount(Date beginTime) {
        return orderReportMapper.findCodDeliveryAmount(beginTime);
    }

    @Override
    public BigDecimal findCodRefundAmount(Date beginTime) {
        return orderReportMapper.findCodRefundAmount(beginTime);
    }

    @Override
    public BigDecimal findCodBalanceAmount(Date beginTime) {
        return orderReportMapper.findCodBalanceAmount(beginTime);
    }

    @Override
    public BigDecimal findRefuseAmount(Date beginTime) {
        return orderReportMapper.findRefuseAmount(beginTime);
    }

    @Override
    public List<Map<String, Object>> findOnlineDeliveryAmount(Date lastTime) {
        return orderReportMapper.findOnlineDeliveryAmount(lastTime);
    }

    @Override
    public List<Map<String, Object>> findOnlineRefundAmount(Date lastTime, Boolean reship) {
        return orderReportMapper.findOnlineRefundAmount(lastTime, reship);
    }

    @Override
    public List<Map<String, Object>> findOnlineReadyAmount(Date lastTime) {
        return orderReportMapper.findOnlineReadyAmount(lastTime);
    }

    @Override
    public int countBuyerCount(Date beginDate, Date endDate) {
        return orderReportMapper.countBuyerCount(beginDate, endDate);
    }

    @Override
    public BigDecimal findSalesAmount(Date beginDate, Date endDate) {
        return orderReportMapper.findSalesAmount(beginDate, endDate);
    }

    @Override
    public int countRebuyBuyerCount(Date beginDate, Date endDate) {
        return orderReportMapper.countRebuyBuyerCount(beginDate, endDate);
    }

    @Override
    public BigDecimal findRebuySalesAmount(Date beginDate, Date endDate) {
        return orderReportMapper.findRebuySalesAmount(beginDate, endDate);
    }

    @Override
    public int countOldCustomerBuyerCount(Date beginDate, Date endDate) {
        return orderReportMapper.countOldCustomerBuyerCount(beginDate, endDate);
    }

    @Override
    public BigDecimal findOldCustomerSalesAmount(Date beginDate, Date endDate) {
        return orderReportMapper.findOldCustomerSalesAmount(beginDate, endDate);
    }

    @Override
    public int countNewCustomerBuyerCount(Date beginDate, Date endDate) {
        return orderReportMapper.countNewCustomerBuyerCount(beginDate, endDate);
    }

    @Override
    public BigDecimal findNewCustomerSalesAmount(Date beginDate, Date endDate) {
        return orderReportMapper.findNewCustomerSalesAmount(beginDate, endDate);
    }

    @Override
    public List<HashMap<String, Object>> groupByOrderSn(OrderSearcher searcher) {
        return orderReportMapper.groupByOrderSn(searcher);
    }

    @Override
    public BigDecimal findPartnerAmount(Date begainDate, Date endDate, int type) {
        return orderReportMapper.findPartnerAmount(begainDate, endDate, type);
    }

    @Override
    public BigDecimal findOrdinaryAmount(Date begainDate, Date endDate) {
        return orderReportMapper.findOrdinaryAmount(begainDate, endDate);
    }

    @Override
    public int findPartnerCount(Date begainDate, Date endDate, int type) {
        return orderReportMapper.findPartnerCount(begainDate, endDate, type);
    }

}
