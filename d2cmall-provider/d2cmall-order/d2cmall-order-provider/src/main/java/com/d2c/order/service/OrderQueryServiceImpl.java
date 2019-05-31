package com.d2c.order.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.service.CrmGroupService;
import com.d2c.member.service.MemberInfoService;
import com.d2c.order.dao.OrderMapper;
import com.d2c.order.dto.OrderDto;
import com.d2c.order.dto.OrderItemDto;
import com.d2c.order.model.Order;
import com.d2c.order.model.Order.OrderStatus;
import com.d2c.order.model.OrderItem;
import com.d2c.order.model.Setting;
import com.d2c.order.query.OrderSaleSearcher;
import com.d2c.order.query.OrderSearcher;
import com.d2c.product.model.ProductSkuStockSummary;
import com.d2c.product.service.ProductSkuStockSummaryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service("orderQueryService")
public class OrderQueryServiceImpl implements OrderQueryService {

    private static String[] ALL_STATUS = {"MallClose", "Close", "Initial", "WaitingForPay", "WaitingForConfirmation",
            "WaitingForDelivery", "Delivered", "Success"};
    private static String[] ALL_ORDER_STATUS = {"orderInvalidCount", "orderMallCloseCount", "orderWaitintForPayCount",
            "orderWaitingForConfirmationCount", "orderWaitingForDeliveryCount", "orderDeliveredCount",
            "orderSuccessCount"};
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private ProductSkuStockSummaryService productSkuStockSummaryService;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private ReshipService reshipService;
    @Autowired
    private RefundService refundService;
    @Autowired
    private ExchangeService exchangeService;
    @Autowired
    private SettingService settingService;
    @Autowired
    private MemberInfoService memberInfoService;
    @Autowired
    private CrmGroupService crmGroupService;

    @Override
    public PageResult<OrderDto> findMyOrder(Long memberInfoId, String orderSn, String productName, Date beginOrderDate,
                                            Date endOrderDate, OrderStatus[] orderStatus, String[] itemStatus, String designer, Integer commented,
                                            PageModel page) {
        PageResult<OrderDto> pager = new PageResult<>(page);
        Integer totalCount = orderMapper.countBy(memberInfoId, orderSn, productName, beginOrderDate, endOrderDate,
                orderStatus, itemStatus, designer, commented);
        List<Order> list = new ArrayList<>();
        if (totalCount > 0) {
            list = orderMapper.findBy(memberInfoId, orderSn, productName, beginOrderDate, endOrderDate, orderStatus,
                    itemStatus, designer, commented, page);
        }
        Setting orderSettting = settingService.findByCode(Setting.ORDERCLOSECODE);
        Setting crossSettting = settingService.findByCode(Setting.CROSSCLOSECODE);
        Setting collageSettting = settingService.findByCode(Setting.COLLAGECLOSECODE);
        Setting seckillSettting = settingService.findByCode(Setting.SECKILLCLOSECODE);
        List<OrderDto> orderDtos = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Order order = list.get(i);
            OrderDto dto = new OrderDto();
            BeanUtils.copyProperties(order, dto);
            // 跨境维度的订单关闭时间
            if (dto.getCrossBorder() == 1) {
                dto.setCloseTime(Integer.valueOf(Setting.defaultValue(crossSettting, "1200").toString()));
            } else {
                dto.setCloseTime(Integer.valueOf(Setting.defaultValue(orderSettting, "86400").toString()));
            }
            // 活动维度的订单关闭时间
            if (dto.getType().equals(Order.OrderType.seckill.toString())
                    || dto.getType().equals(Order.OrderType.bargain.toString())) {
                dto.setCloseTime(Integer.valueOf(Setting.defaultValue(seckillSettting, "3600").toString()));
            } else if (dto.getType().equals(Order.OrderType.collage.toString())) {
                dto.setCloseTime(Integer.valueOf(Setting.defaultValue(collageSettting, "600").toString()));
            }
            List<OrderItem> orderItems = orderItemService.findByOrderId(order.getId());
            List<OrderItemDto> itemDtos = new ArrayList<>();
            for (OrderItem item : orderItems) {
                OrderItemDto temp = new OrderItemDto();
                BeanUtils.copyProperties(item, temp);
                itemDtos.add(temp);
                if (item.getReshipId() != null) {
                    temp.setReship(reshipService.findById(item.getReshipId()));
                }
                if (item.getRefundId() != null) {
                    temp.setRefund(refundService.findById(item.getRefundId()));
                }
                if (item.getExchangeId() != null) {
                    temp.setExchange(exchangeService.findById(item.getExchangeId()));
                }
            }
            dto.setOrderItems(itemDtos);
            orderDtos.add(dto);
        }
        pager.setTotalCount(totalCount);
        pager.setList(orderDtos);
        return pager;
    }

    @Override
    public int countMyOrder(Long memberInfoId, String orderSn, String productName, Date beginOrderDate,
                            Date endOrderDate, OrderStatus[] orderStatus, String[] itemStatus, String designer, Integer commented) {
        return orderMapper.countBy(memberInfoId, orderSn, productName, beginOrderDate, endOrderDate, orderStatus,
                itemStatus, designer, commented);
    }

    @Override
    public PageResult<Order> findPageSimpleBySearch(OrderSearcher searcher, PageModel page) {
        PageResult<Order> pager = new PageResult<>(page);
        int totalCount = orderMapper.countSimpleBySearch(searcher);
        List<Order> list = new ArrayList<>();
        if (totalCount > 0) {
            list = orderMapper.findSimpleBySearch(searcher, page);
        }
        pager.setTotalCount(totalCount);
        pager.setList(list);
        return pager;
    }

    @Override
    public List<Order> findSimpleBySearch(OrderSearcher searcher, PageModel page) {
        return orderMapper.findSimpleBySearch(searcher, page);
    }

    @Override
    public PageResult<OrderDto> findBySearcher(OrderSearcher searcher, PageModel page) {
        PageResult<OrderDto> pager = new PageResult<>(page);
        Integer totalCount = orderMapper.countBySearcher(searcher);
        List<Order> list = new ArrayList<>();
        List<OrderDto> dtos = new ArrayList<>();
        if (totalCount > 0) {
            list = orderMapper.findBySearcher(searcher, page);
            for (Order order : list) {
                OrderDto dto = new OrderDto();
                BeanUtils.copyProperties(order, dto);
                List<OrderItem> orderItems = orderItemService.findByOrderId(order.getId());
                List<OrderItemDto> itemDtos = new ArrayList<>();
                for (OrderItem item : orderItems) {
                    OrderItemDto itemDto = new OrderItemDto();
                    BeanUtils.copyProperties(item, itemDto);
                    ProductSkuStockSummary productSkuStockSummary = productSkuStockSummaryService
                            .findBySkuId(itemDto.getProductSkuId());
                    itemDto.setProductSkuStockSummary(productSkuStockSummary);
                    itemDtos.add(itemDto);
                    if (item.getReshipId() != null) {
                        itemDto.setReship(reshipService.findById(item.getReshipId()));
                    }
                    if (item.getRefundId() != null) {
                        itemDto.setRefund(refundService.findById(item.getRefundId()));
                    }
                    if (item.getExchangeId() != null) {
                        itemDto.setExchange(exchangeService.findById(item.getExchangeId()));
                    }
                }
                dto.setOrderItems(itemDtos);
                if (dto.getCrmGroupId() != null) {
                    dto.setCrmGroup(crmGroupService.findById(dto.getCrmGroupId()));
                }
                dtos.add(dto);
            }
        }
        pager.setTotalCount(totalCount);
        pager.setList(dtos);
        return pager;
    }

    @Override
    public Map<String, Object> countGropByStatus(Long memberInfoId) {
        Map<String, Object> map = new HashMap<>();
        List<Map<String, Object>> counts = orderMapper.countGropByStatus(memberInfoId);
        for (Map<String, Object> count : counts) {
            String status = count.get("orderStatus").toString();
            switch (status) {
                case "-3":
                    map.put("MallClose", count.get("counts"));
                    break;
                case "-1":
                    map.put("Close", count.get("counts"));
                    break;
                case "0":
                    map.put("Initial", count.get("counts"));
                    break;
                case "1":
                    map.put("WaitingForPay", count.get("counts"));
                    break;
                case "2":
                    map.put("WaitingForConfirmation", count.get("counts"));
                    break;
                case "3":
                    map.put("WaitingForDelivery", count.get("counts"));
                    break;
                case "4":
                    map.put("Delivered", count.get("counts"));
                    break;
                case "8":
                    map.put("Success", count.get("counts"));
                    break;
            }
        }
        // 将没有状态的数量，默认设置成0
        for (String status : ALL_STATUS) {
            if (map.get(status) == null) {
                map.put(status, 0);
            }
        }
        return map;
    }

    @Override
    public Map<String, Object> getCountsMap() {
        Map<String, Object> map = new HashMap<>();
        // 各状态订单数量
        List<Map<String, Object>> counts = orderMapper.countGroupByStatus();
        int orderAllCount = 0;
        for (Map<String, Object> count : counts) {
            String status = count.get("order_status").toString();
            switch (status) {
                case "-3":
                    map.put("orderMallCloseCount", count.get("counts"));
                    break;
                case "-1":
                    map.put("orderInvalidCount", count.get("counts"));
                    break;
                case "1":
                    map.put("orderWaitintForPayCount", count.get("counts"));
                    break;
                case "2":
                    map.put("orderWaitingForConfirmationCount", count.get("counts"));
                    break;
                case "3":
                    map.put("orderWaitingForDeliveryCount", count.get("counts"));
                    break;
                case "4":
                    map.put("orderDeliveredCount", count.get("counts"));
                    break;
                case "8":
                    map.put("orderSuccessCount", count.get("counts"));
            }
            orderAllCount += ((Number) count.get("counts")).intValue();
        }
        // 将没有状态的数量，默认设置成0
        for (String status : ALL_ORDER_STATUS) {
            if (map.get(status) == null) {
                map.put(status, 0);
            }
        }
        // 总订单数
        map.put("orderAllCount", orderAllCount);
        return map;
    }

    @Override
    public Map<String, Integer> getWarningMap() {
        Map<String, Integer> map = new HashMap<>();
        OrderSearcher query = new OrderSearcher();
        query.setOrderStatus(new OrderStatus[]{OrderStatus.WaitingForDelivery});
        query.setSplit(false);
        query.setWarningTime(3L * 24 * 60 * 60);
        Integer lackingCount = orderMapper.countSimpleBySearch(query);
        query.setWarningTime(null);
        query.setMoreStock(0);
        Integer inStockCount = orderMapper.countSimpleBySearch(query);
        map.put("lackingCount", lackingCount);
        map.put("inStockCount", inStockCount);
        return map;
    }

    /**
     * 状态统计
     */
    @Override
    public int getOrderStatusCount(OrderStatus[] orderStatus, String[] itemStatus, Long memberId, Integer commented) {
        return orderMapper.countBy(memberId, null, null, null, null, orderStatus, itemStatus, null, commented);
    }

    @Override
    public int countSimpleBySearch(OrderSearcher query) {
        return orderMapper.countSimpleBySearch(query);
    }

    @Override
    public List<Map<String, Object>> findCountAndAmountGroupByStatus(OrderSearcher searcher) {
        return orderMapper.findCountAndAmountGroupByStatus(searcher);
    }

    @Override
    public List<Map<String, Object>> findCountAndAmountGroupByPayType(OrderSearcher searcher) {
        return orderMapper.findCountAndAmountGroupByPayType(searcher);
    }

    @Override
    public List<Map<String, Object>> findCountAndAmountGroupByDevice(OrderSearcher searcher) {
        return orderMapper.findCountAndAmountGroupByDevice(searcher);
    }

    @Override
    public BigDecimal countOrdersAmount(OrderSearcher query) {
        return orderMapper.countOrdersAmount(query);
    }

    @Override
    public PageResult<Order> findWaitingSuccessOrder(OrderStatus[] orderStatus, PageModel page) {
        PageResult<Order> pager = new PageResult<>(page);
        Integer totalCount = orderMapper.countWaitingSuccessOrder(orderStatus);
        List<Order> list = new ArrayList<>();
        if (totalCount > 0) {
            list = orderMapper.findWaitingSuccessOrder(orderStatus, page);
        }
        pager.setTotalCount(totalCount);
        pager.setList(list);
        return pager;
    }

    @Override
    public int countWaitingSuccessOrder(OrderStatus[] orderStatus) {
        return orderMapper.countWaitingSuccessOrder(orderStatus);
    }

    @Override
    public PageResult<Long> findExpireOrder(int closeTime, Order.OrderType[] types, Integer cross, PageModel page) {
        PageResult<Long> pager = new PageResult<>(page);
        int totalCount = orderMapper.countExpireOrder(closeTime, types, cross);
        if (totalCount > 0) {
            List<Long> list = orderMapper.findExpireOrder(closeTime, types, cross, page);
            pager.setTotalCount(totalCount);
            pager.setList(list);
        }
        return pager;
    }

    @Override
    public int countExpireOrder(int closeTime, Order.OrderType[] types, Integer cross) {
        return orderMapper.countExpireOrder(closeTime, types, cross);
    }

    @Override
    public OrderDto findPushMinutesOrder(String minutes) {
        Order order = orderMapper.findPushMinutesOrder(Long.parseLong(minutes));
        if (order != null) {
            MemberInfo memberInfo = memberInfoService.findById(order.getMemberId());
            OrderDto orderDto = new OrderDto();
            BeanUtils.copyProperties(order, orderDto);
            orderDto.setMemberInfo(memberInfo);
            return orderDto;
        }
        return null;
    }

    @Override
    public PageResult<Map<String, Object>> findSaleBySearcher(OrderSaleSearcher searcher, PageModel page) {
        PageResult<Map<String, Object>> pager = new PageResult<>(page);
        int totalCount = orderMapper.countSaleBySearcher(searcher);
        if (totalCount > 0) {
            List<Map<String, Object>> list = orderMapper.findSaleBySearcher(searcher, page);
            pager.setList(list);
        }
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    public int countSaleBySearcher(OrderSaleSearcher searcher) {
        return orderMapper.countSaleBySearcher(searcher);
    }

    @Override
    public Map<String, Object> findOrderStat(Long memberInfoId) {
        return orderMapper.findOrderStat(memberInfoId);
    }

}
