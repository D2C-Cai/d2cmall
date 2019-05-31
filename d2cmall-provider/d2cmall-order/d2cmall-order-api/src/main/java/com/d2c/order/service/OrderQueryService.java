package com.d2c.order.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.order.dto.OrderDto;
import com.d2c.order.model.Order;
import com.d2c.order.model.Order.OrderStatus;
import com.d2c.order.query.OrderSaleSearcher;
import com.d2c.order.query.OrderSearcher;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderQueryService {

    /**
     * 统计，列表页显示各状态订单数量
     *
     * @return
     */
    Map<String, Object> getCountsMap();

    /**
     * 查询订单状态的数量
     *
     * @param orderStatus
     * @param itemStatus
     * @param memberId
     * @param commented
     * @return
     */
    int getOrderStatusCount(OrderStatus[] orderStatus, String[] itemStatus, Long memberId, Integer commented);

    /**
     * 发货预警统计
     *
     * @return
     */
    Map<String, Integer> getWarningMap();

    /**
     * 统计订单数量
     *
     * @param memberInfoId
     * @return
     */
    Map<String, Object> countGropByStatus(Long memberInfoId);

    /**
     * 导出查询
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<Order> findPageSimpleBySearch(OrderSearcher searcher, PageModel page);

    /**
     * 列表查询
     *
     * @param queryOrder
     * @param page
     * @return
     */
    PageResult<OrderDto> findBySearcher(OrderSearcher queryOrder, PageModel page);

    /**
     * 导出查询
     *
     * @param searcher
     * @param page
     * @return
     */
    List<Order> findSimpleBySearch(OrderSearcher searcher, PageModel page);

    /**
     * 导出统计
     *
     * @param query
     * @return
     */
    int countSimpleBySearch(OrderSearcher query);

    /**
     * 统计金额和数量
     *
     * @param query
     * @return
     */
    List<Map<String, Object>> findCountAndAmountGroupByStatus(OrderSearcher searcher);

    /**
     * 统计金额和数量
     *
     * @param query
     * @return
     */
    List<Map<String, Object>> findCountAndAmountGroupByPayType(OrderSearcher searcher);

    /**
     * 统计金额和数量
     *
     * @param query
     * @return
     */
    List<Map<String, Object>> findCountAndAmountGroupByDevice(OrderSearcher searcher);

    /**
     * 统计订单数量
     *
     * @param query
     * @return
     */
    BigDecimal countOrdersAmount(OrderSearcher query);

    /**
     * 查询未完成的订单
     *
     * @param orderStatus
     * @param page
     * @return
     */
    PageResult<Order> findWaitingSuccessOrder(OrderStatus[] orderStatus, PageModel page);

    /**
     * 查询未完成的订单数量
     *
     * @param orderStatus
     * @return
     */
    int countWaitingSuccessOrder(OrderStatus[] orderStatus);

    /**
     * 查询订单dto数据
     *
     * @param memberInfoId
     * @param orderSn
     * @param productName
     * @param beginOrderDate
     * @param endOrderDate
     * @param orderStatus
     * @param itemStatus
     * @param designer
     * @param commented
     * @param page
     * @return
     */
    PageResult<OrderDto> findMyOrder(Long memberInfoId, String orderSn, String productName, Date beginOrderDate,
                                     Date endOrderDate, OrderStatus[] orderStatus, String[] itemStatus, String designer, Integer commented,
                                     PageModel page);

    /**
     * 查询数量
     *
     * @param memberInfoId
     * @param orderSn
     * @param productName
     * @param beginOrderDate
     * @param endOrderDate
     * @param orderStatus
     * @param itemStatus
     * @param designer
     * @param commented
     * @return
     */
    int countMyOrder(Long memberInfoId, String orderSn, String productName, Date beginOrderDate, Date endOrderDate,
                     OrderStatus[] orderStatus, String[] itemStatus, String designer, Integer commented);

    /**
     * 查询最近两分钟支付成功的订单
     *
     * @param string
     * @return
     */
    OrderDto findPushMinutesOrder(String minutes);

    /**
     * 查询超时订单
     *
     * @param closeTime
     * @param type
     * @param cross
     * @param page
     * @return
     */
    PageResult<Long> findExpireOrder(int closeTime, Order.OrderType[] type, Integer cross, PageModel page);

    /**
     * 查询超时订单数量
     *
     * @param closeTime
     * @param type
     * @param cross
     * @return
     */
    int countExpireOrder(int closeTime, Order.OrderType[] type, Integer cross);

    /**
     * 销售分页查询
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<Map<String, Object>> findSaleBySearcher(OrderSaleSearcher searcher, PageModel page);

    /**
     * 分页统计
     *
     * @param searcher
     * @return
     */
    int countSaleBySearcher(OrderSaleSearcher searcher);

    /**
     * 查找用户相关交易统计
     */
    Map<String, Object> findOrderStat(Long memberInfoId);

}
