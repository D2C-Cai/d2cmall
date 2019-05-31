package com.d2c.order.service;

import com.alibaba.fastjson.JSONObject;
import com.codingapi.tx.annotation.TxTransaction;
import com.d2c.cache.redis.RedisHandler;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.common.core.annotation.AsyncMethod;
import com.d2c.logger.model.BurgeonErrorLog.DocType;
import com.d2c.logger.model.OrderLog;
import com.d2c.logger.model.OrderLog.OrderLogType;
import com.d2c.logger.service.MsgUniteService;
import com.d2c.logger.service.OrderLogService;
import com.d2c.logger.support.MsgBean;
import com.d2c.logger.support.PushBean;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.service.MemberInfoService;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.order.dao.OrderItemMapper;
import com.d2c.order.dto.OrderDto;
import com.d2c.order.dto.OrderItemDto;
import com.d2c.order.model.CustomerCompensation.CompensationType;
import com.d2c.order.model.Logistics;
import com.d2c.order.model.Order;
import com.d2c.order.model.Order.OrderStatus;
import com.d2c.order.model.Order.OrderType;
import com.d2c.order.model.OrderItem;
import com.d2c.order.model.OrderItem.BusType;
import com.d2c.order.model.OrderItem.ItemStatus;
import com.d2c.order.model.RequisitionItem;
import com.d2c.order.query.OrderSearcher;
import com.d2c.order.query.ProductOrderSumSearcher;
import com.d2c.product.model.Brand;
import com.d2c.product.model.Product;
import com.d2c.product.model.Product.ProductSellType;
import com.d2c.product.model.Product.ProductSource;
import com.d2c.product.model.ProductSku;
import com.d2c.product.model.ProductSkuStock;
import com.d2c.product.service.BrandService;
import com.d2c.product.service.ProductService;
import com.d2c.product.service.ProductSkuService;
import com.d2c.product.service.ProductSkuStockService;
import com.d2c.util.date.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

@Service(value = "orderItemService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class OrderItemServiceImpl extends ListServiceImpl<OrderItem> implements OrderItemService {

    private static String[] ORDER_ITEM_STATUS = {"AFTERCLOSE", "CLOSE", "DELIVERED", "INIT", "MALLCLOSE", "NORMAL",
            "SIGNED", "SUCCESS"};
    @Autowired
    private RedisHandler<String, Map<String, Object>> redisHandler;
    @Autowired
    private RedisHandler<String, List<Date>> redisDateHandler;
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderQueryService orderQueryService;
    @Autowired
    private OrderLogService orderLogService;
    @Autowired
    private ProductSkuStockService productSkuStockService;
    @Autowired
    private ProductSkuService productSkuService;
    @Autowired
    private ProductService productService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private MemberInfoService memberInfoService;
    @Autowired
    private ReshipService reshipService;
    @Autowired
    private RefundService refundService;
    @Autowired
    private ExchangeService exchangeService;
    @Autowired
    private MsgUniteService msgUniteService;
    @Autowired
    private LogisticsService logisticsService;
    @Autowired
    private RequisitionItemService requisitionItemService;
    @Autowired
    private CustomerCompensationService customerCompensationService;
    @Autowired
    private PartnerBillService partnerBillService;
    @Autowired
    private HolidayService holidayService;

    @Override
    public OrderItem findById(Long id) {
        return this.findOneById(id);
    }

    @Override
    @TxTransaction
    public List<OrderItem> findByOrderId(Long orderId) {
        return orderItemMapper.findByOrderId(orderId);
    }

    @Override
    public List<OrderItem> findSimpleByOrderId(Long orderId) {
        return orderItemMapper.findByOrderId(orderId);
    }

    @Override
    @TxTransaction
    public List<OrderItem> findByOrderSn(String orderSn) {
        return orderItemMapper.findByOrderSn(orderSn);
    }

    @Override
    public OrderItem findByOrderSnAndSku(String orderSn, String barCode) {
        return orderItemMapper.findByOrderSnAndSku(orderSn, barCode);
    }

    @Override
    public OrderItem findByIdAndMemberInfoId(Long id, Long memberInfoId) {
        return orderItemMapper.findByIdAndMemberInfoId(id, memberInfoId);
    }

    @Override
    public List<OrderItemDto> findDtoByOrderId(Long orderId) {
        List<OrderItem> items = this.findByOrderId(orderId);
        List<OrderItemDto> itemDtos = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            OrderItemDto dto = new OrderItemDto();
            BeanUtils.copyProperties(items.get(i), dto);
            ProductSku productSKU = productSkuService.findById(dto.getProductSkuId());
            dto.setProductSku(productSKU);
            if (dto.getReshipId() != null) {
                dto.setReship(reshipService.findById(dto.getReshipId()));
            }
            if (dto.getRefundId() != null) {
                dto.setRefund(refundService.findById(dto.getRefundId()));
            }
            if (dto.getExchangeId() != null) {
                dto.setExchange(exchangeService.findById(dto.getExchangeId()));
            }
            itemDtos.add(dto);
        }
        return itemDtos;
    }

    @Override
    public OrderItemDto findOrderItemDtoById(Long orderItemId) {
        OrderItem item = findById(orderItemId);
        if (item == null) {
            return null;
        }
        OrderItemDto dto = new OrderItemDto();
        BeanUtils.copyProperties(item, dto);
        if (dto.getReshipId() != null) {
            dto.setReship(reshipService.findById(item.getReshipId()));
        }
        if (item.getRefundId() != null) {
            dto.setRefund(refundService.findById(item.getRefundId()));
        }
        if (item.getExchangeId() != null) {
            dto.setExchange(exchangeService.findById(item.getExchangeId()));
        }
        return dto;
    }

    /**
     * 列表查询
     *
     * @param queryOrder
     * @param pager
     * @return
     */
    @Override
    public List<OrderItemDto> findBySearcher(OrderSearcher queryOrder, PageModel page) {
        List<OrderItem> list = orderItemMapper.findBySearcher(queryOrder, page);
        List<OrderItemDto> dtos = new ArrayList<>();
        for (OrderItem item : list) {
            OrderItemDto dto = new OrderItemDto();
            BeanUtils.copyProperties(item, dto);
            Order order = orderService.findById(item.getOrderId());
            OrderDto orderDto = new OrderDto();
            BeanUtils.copyProperties(order, orderDto);
            dto.setOrder(orderDto);
            dtos.add(dto);
        }
        return dtos;
    }

    /**
     * 列表查询
     *
     * @param queryOrder
     * @param pager
     * @return
     */
    @Override
    public PageResult<OrderItemDto> findPageBySearcher(OrderSearcher queryOrder, PageModel page) {
        PageResult<OrderItemDto> pager = new PageResult<>(page);
        int count = this.countBySearcher(queryOrder);
        pager.setTotalCount(count);
        if (count > 0) {
            List<OrderItem> items = orderItemMapper.findBySearcher(queryOrder, page);
            List<OrderItemDto> dtos = new ArrayList<>();
            for (OrderItem item : items) {
                OrderItemDto dto = new OrderItemDto();
                BeanUtils.copyProperties(item, dto);
                dto.setProductSku(productSkuService.findById(dto.getProductSkuId()));
                dto.setOrder(orderService.findById(dto.getOrderId()));
                Brand brand = brandService.findById(item.getDesignerId());
                if (brand != null) {
                    dto.setOperation(brand.getOperation());
                }
                dtos.add(dto);
            }
            pager.setList(dtos);
        }
        return pager;
    }

    @Override
    public PageResult<OrderItemDto> findSimpleBySearcher(OrderSearcher queryOrder, PageModel page) {
        PageResult<OrderItemDto> pager = new PageResult<>(page);
        int count = this.countBySearcher(queryOrder);
        pager.setTotalCount(count);
        if (count > 0) {
            List<OrderItem> items = orderItemMapper.findBySearcher(queryOrder, page);
            List<OrderItemDto> dtos = new ArrayList<>();
            for (OrderItem item : items) {
                OrderItemDto dto = new OrderItemDto();
                BeanUtils.copyProperties(item, dto);
                if (dto.getReshipId() != null) {
                    dto.setReship(reshipService.findById(item.getReshipId()));
                }
                if (item.getRefundId() != null) {
                    dto.setRefund(refundService.findById(item.getRefundId()));
                }
                if (item.getExchangeId() != null) {
                    dto.setExchange(exchangeService.findById(item.getExchangeId()));
                }
                dtos.add(dto);
            }
            pager.setList(dtos);
        }
        return pager;
    }

    @Override
    public PageResult<OrderItem> findDeliveredByDate(Date date, int interval, PageModel page) {
        PageResult<OrderItem> pager = new PageResult<>(page);
        int count = orderItemMapper.countDeliveredByDate(date, interval);
        pager.setTotalCount(count);
        if (count > 0) {
            List<OrderItem> result = orderItemMapper.findDeliveredByDate(date, interval, page);
            pager.setList(result);
        }
        return pager;
    }

    @Override
    public int countDeliveredByDate(Date date, int interval) {
        return orderItemMapper.countDeliveredByDate(date, interval);
    }

    @Override
    public PageResult<OrderItem> findSignedByDate(Date date, PageModel page) {
        PageResult<OrderItem> pager = new PageResult<>(page);
        int count = orderItemMapper.countSignedByDate(date);
        pager.setTotalCount(count);
        if (count > 0) {
            List<OrderItem> result = orderItemMapper.findSignedByDate(date, page);
            pager.setList(result);
        }
        return pager;
    }

    @Override
    public int countSignedByDate(Date date) {
        return orderItemMapper.countSignedByDate(date);
    }

    @Override
    public PageResult<Map<String, Object>> findRankingByProduct(PageModel page, ProductOrderSumSearcher searcher) {
        PageResult<Map<String, Object>> pager = new PageResult<>(page);
        int totalCount = orderItemMapper.countRankingByProduct(searcher);
        if (totalCount > 0) {
            List<Map<String, Object>> list = orderItemMapper.findRankingByProduct(page, searcher);
            pager.setList(list);
        }
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    public List<HashMap<String, Object>> findProductSales() {
        return orderItemMapper.findProductSales();
    }

    @Override
    public PageResult<Map<String, Object>> findBrandSales(PageModel page, int days) {
        PageResult<Map<String, Object>> pager = new PageResult<>(page);
        int totalCount = orderItemMapper.countBrandSales(days);
        if (totalCount > 0) {
            List<Map<String, Object>> list = orderItemMapper.findBrandSales(days, page);
            pager.setList(list);
        }
        return pager;
    }

    @Override
    public PageResult<OrderItemDto> findPartnerOrder(OrderSearcher searcher, PageModel page) {
        PageResult<OrderItemDto> pager = new PageResult<>(page);
        int count = orderItemMapper.countPartnerOrder(searcher);
        pager.setTotalCount(count);
        if (count > 0) {
            List<OrderItem> items = orderItemMapper.findPartnerOrder(searcher, page);
            List<OrderItemDto> dtos = new ArrayList<>();
            for (OrderItem item : items) {
                OrderItemDto dto = new OrderItemDto();
                BeanUtils.copyProperties(item, dto);
                if (dto.getReshipId() != null) {
                    dto.setReship(reshipService.findById(item.getReshipId()));
                }
                if (item.getRefundId() != null) {
                    dto.setRefund(refundService.findById(item.getRefundId()));
                }
                if (item.getExchangeId() != null) {
                    dto.setExchange(exchangeService.findById(item.getExchangeId()));
                }
                dtos.add(dto);
            }
            pager.setList(dtos);
        }
        return pager;
    }

    @Override
    public List<OrderItemDto> findByDeliverySn(String deliverySn) {
        List<OrderItem> items = orderItemMapper.findByDeliverySn(deliverySn);
        List<OrderItemDto> itemDtos = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            OrderItemDto dto = new OrderItemDto();
            BeanUtils.copyProperties(items.get(i), dto);
            if (dto.getReshipId() != null) {
                dto.setReship(reshipService.findById(dto.getReshipId()));
            }
            if (dto.getRefundId() != null) {
                dto.setRefund(refundService.findById(dto.getRefundId()));
            }
            if (dto.getExchangeId() != null) {
                dto.setExchange(exchangeService.findById(dto.getExchangeId()));
            }
            itemDtos.add(dto);
        }
        return itemDtos;
    }

    @Override
    public PageResult<Map<String, Object>> findProductOrderAnalysis(Date dayTime, String type, PageModel page) {
        PageResult<Map<String, Object>> pager = new PageResult<>(page);
        Integer totalCount = orderItemMapper.countProductOrderAnalysis(dayTime,
                new Date(dayTime.getTime() + 24 * 60 * 60 * 1000), type);
        List<Map<String, Object>> list = new ArrayList<>();
        if (totalCount > 0) {
            list = orderItemMapper.findProductOrderAnalysis(dayTime, new Date(dayTime.getTime() + 24 * 60 * 60 * 1000),
                    type, page);
        }
        pager.setTotalCount(totalCount);
        pager.setList(list);
        return pager;
    }

    @Override
    public PageResult<OrderItem> findDtoForStatement(Date beginDate, PageModel page) {
        PageResult<OrderItem> pager = new PageResult<>(page);
        // 在线支付
        int count = orderItemMapper.countForStatement(beginDate);
        List<OrderItem> orderItems = new ArrayList<>();
        List<OrderItem> codOrderItems = new ArrayList<>();
        if (count > 0) {
            orderItems = orderItemMapper.findForStatement(beginDate, page);
        }
        int codCount = orderItemMapper.countCodStatement(beginDate);
        if (codCount > 0) {
            // 货到付款
            codOrderItems = orderItemMapper.findCodStatement(beginDate, page);
        }
        if (orderItems != null) {
            if (codOrderItems != null) {
                orderItems.addAll(codOrderItems);
            }
        } else {
            orderItems = codOrderItems;
        }
        pager.setTotalCount(count + codCount);
        pager.setList(orderItems);
        return pager;
    }

    @Override
    public PageResult<OrderItem> findExpiredDelivery(Date beginDate, Date endDate, PageModel page) {
        PageResult<OrderItem> pager = new PageResult<>(page);
        int totalCount = orderItemMapper.countExpiredDelivery(beginDate, endDate);
        if (totalCount > 0) {
            List<OrderItem> list = orderItemMapper.findExpiredDelivery(beginDate, endDate, page);
            pager.setList(list);
        }
        pager.setTotalCount(totalCount);
        return pager;
    }

    /**
     * 统计数量
     *
     * @param queryOrder
     * @return
     */
    @Override
    public Integer countBySearcher(OrderSearcher queryOrder) {
        return orderItemMapper.countBySearcher(queryOrder);
    }

    @Override
    public int countByProductSkuId(Long productSkuId) {
        return orderItemMapper.countByProductSkuId(productSkuId);
    }

    @Override
    public Map<String, Object> countGroupByStatus(Long memberInfoId) {
        Map<String, Object> cacheMap = redisHandler.get("orderitem_count_" + memberInfoId);
        if (cacheMap == null) {
            Map<String, Object> map = new HashMap<>();
            List<Map<String, Object>> counts = orderItemMapper.countGroupByStatus(memberInfoId);
            int init = orderQueryService.countMyOrder(memberInfoId, null, null, null, null,
                    new OrderStatus[]{Order.OrderStatus.WaitingForPay}, null, null, null);
            for (Map<String, Object> count : counts) {
                String status = count.get("status").toString();
                switch (status) {
                    case "MALLCLOSE":
                        map.put("MALLCLOSE", count.get("counts"));
                        break;
                    case "AFTERCLOSE":
                        map.put("AFTERCLOSE", count.get("counts"));
                        break;
                    case "CLOSE":
                        map.put("CLOSE", count.get("counts"));
                        break;
                    case "INIT":
                        map.put("INIT", count.get("counts"));
                        break;
                    case "NORMAL":
                        map.put("NORMAL", count.get("counts"));
                        break;
                    case "DELIVERED":
                        map.put("DELIVERED", count.get("counts"));
                        break;
                    case "SIGNED":
                        map.put("SIGNED", count.get("counts"));
                        break;
                    case "SUCCESS":
                        map.put("SUCCESS", count.get("counts"));
                        break;
                }
            }
            if ((map.get("SUCCESS") != null && (Long) map.get("SUCCESS") > 0)
                    || (map.get("SIGNED") != null && (Long) map.get("SIGNED") > 0)) {
                int waitComment = orderItemMapper.countWaitComment(memberInfoId);
                map.put("WAITCOMMENT", waitComment);
            }
            map.put("INIT", init);
            redisHandler.setInSec("orderitem_count_" + memberInfoId, map, 120);
            return map;
        } else {
            return cacheMap;
        }
    }

    @Override
    public int countRankingByProduct(ProductOrderSumSearcher searcher) {
        return orderItemMapper.countRankingByProduct(searcher);
    }

    @Override
    public Map<String, Object> getCountsMap(Boolean isStore, Long storeId) {
        Map<String, Object> map = new HashMap<>();
        // 各状态订单数量
        List<Map<String, Object>> counts = null;
        if (isStore) {
            counts = orderItemMapper.countGroupByStatusAndStore(true, storeId);
        } else {
            counts = orderItemMapper.countGroupByStatusAndStore(false, null);
        }
        int orderAllCount = 0;
        Map<String, Object> result = new HashMap<>();
        for (Map<String, Object> count : counts) {
            String status = count.get("status").toString();
            result.put(status, count.get("counts"));
        }
        for (String status : ORDER_ITEM_STATUS) {
            Object obj = result.get(status);
            if (obj == null) {
                obj = 0;
                map.put(status, 0);
            } else {
                map.put(status, obj);
            }
            orderAllCount += ((Number) obj).intValue();
        }
        // 总订单数
        map.put("orderAllCount", orderAllCount);
        return map;
    }

    @Override
    public PageResult<OrderItem> findPopOrderitems(Date deadline, PageModel page) {
        PageResult<OrderItem> pager = new PageResult<>(page);
        int totalCount = orderItemMapper.countPopOrderitems(deadline);
        if (totalCount > 0) {
            List<OrderItem> list = orderItemMapper.findPopOrderitems(deadline, page);
            pager.setList(list);
            pager.setTotalCount(totalCount);
        }
        return pager;
    }

    @Override
    public int countPopOrderitems(Date deadline) {
        return orderItemMapper.countPopOrderitems(deadline);
    }

    @Override
    public PageResult<OrderItem> findNormalOrderitems(Date deadline, PageModel page) {
        PageResult<OrderItem> pager = new PageResult<>(page);
        int totalCount = orderItemMapper.countNormalOrderitems(deadline);
        if (totalCount > 0) {
            List<OrderItem> list = orderItemMapper.findNormalOrderitems(deadline, page);
            pager.setList(list);
            pager.setTotalCount(totalCount);
        }
        return pager;
    }

    @Override
    public int countNormalOrderitems(Date deadline) {
        return orderItemMapper.countNormalOrderitems(deadline);
    }

    @Override
    public int createOrderLog(Long orderId, Long orderItemId, OrderLogType logType, String operator, String info) {
        OrderLog orderLog = new OrderLog();
        orderLog.setOrderId(orderId);
        orderLog.setCreator(operator);
        orderLog.setCreateDate(new Date());
        orderLog.setOrderItemId(orderItemId);
        orderLog.setInfo(info);
        orderLog.setOrderLogType(logType);
        try {
            orderLogService.insert(orderLog);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return 1;
    }

    @Override
    @TxTransaction
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public OrderItemDto insert(OrderItem orderItem) {
        orderItem = this.save(orderItem);
        OrderItemDto dto = new OrderItemDto();
        BeanUtils.copyProperties(orderItem, dto);
        return dto;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateReship(Long reshipId, Long orderItemId) {
        return orderItemMapper.updateReship(reshipId, orderItemId);
    }

    @Override
    @TxTransaction
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateRefund(Long refundId, Long orderItemId) {
        return orderItemMapper.updateRefund(refundId, orderItemId);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int updateExchange(Long exchangeId, Long orderItemId) {
        return orderItemMapper.updateExchange(exchangeId, orderItemId);
    }

    @Override
    @TxTransaction
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateRequisition(Long orderItemId, Integer requisition) {
        return orderItemMapper.updateRequisition(orderItemId, requisition);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateEstimateDate(Long id, Date estimateDate, String lastMan) {
        OrderItem item = this.findById(id);
        int result = orderItemMapper.updateEstimateDate(id, estimateDate);
        String info = "修改预计发货时间，原始：" + ((item.getEstimateDate() == null ? "" : DateUtil.day2str(item.getEstimateDate()))
                + "，当前：" + DateUtil.day2str(estimateDate));
        this.createOrderLog(item.getOrderId(), id, OrderLogType.ItemEstimateDateLog, lastMan, info);
        return result;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateExpectDate(Long id, Date expectDate) {
        return orderItemMapper.updateExpectDate(id, expectDate);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateCouponAmount(Long id, BigDecimal couponAmount) {
        return orderItemMapper.updateCouponAmount(id, couponAmount);
    }

    /**
     * 客服明细备注修改
     *
     * @param itemId
     * @param adminMemo
     * @param lastMan
     */
    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateAdminMemo(Long itemId, String adminMemo, String lastMan) {
        OrderItem orderItem = this.findById(itemId);
        int result = orderItemMapper.updateAdminMemo(itemId, adminMemo);
        createOrderLog(orderItem.getOrderId(), itemId, OrderLogType.Update, lastMan,
                "客服明细备注修改 原始：" + (orderItem.getItemMemo() == null ? "" : orderItem.getItemMemo() + "， 新：" + adminMemo));
        return result;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateBarcodeById(String barcode, Long id, String lastMan) {
        OrderItem orderItem = this.findById(id);
        int result = orderItemMapper.updateBarcodeById(barcode, id);
        if (result > 0) {
            requisitionItemService.updateBarCode(id, barcode, lastMan);
            this.createOrderLog(orderItem.getOrderId(), orderItem.getId(), OrderLogType.Update, lastMan,
                    "修改发货SKU编号，原始：" + orderItem.getDeliveryBarCode() + "，当前：" + barcode);
        }
        return result;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateBalance(Long id, BigDecimal balanceMoney, String balanceMan, String balanceReason) {
        return orderItemMapper.updateBalance(id, balanceMoney, balanceMan, balanceReason);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateDeliveryInfo(Long orderItemId, String deliveryCorpName, String deliverySn, String deliveryBarCode,
                                  String creator) {
        OrderItem oldItem = this.findById(orderItemId);
        int success = orderItemMapper.updateDeliveryInfo(orderItemId, deliverySn, deliveryCorpName, deliveryBarCode);
        this.createOrderLog(oldItem.getOrderId(), orderItemId, OrderLogType.Update, creator, "修改物流信息，原始："
                + getDeliveryInfo(oldItem) + "，当前：" + getDeliveryInfo(deliveryCorpName, deliverySn, deliveryBarCode));
        // 发货后 就将物流号推送到快递100
        logisticsService.createLogistics(deliveryCorpName, deliverySn, Logistics.BusinessType.ORDER.name(), creator);
        return success;
    }

    private String getDeliveryInfo(OrderItem item) {
        StringBuilder builder = new StringBuilder();
        builder.append("物流公司：" + (item.getDeliveryCorpName() == null ? "" : item.getDeliveryCorpName()));
        builder.append("，物流编号：" + (item.getDeliverySn() == null ? "" : item.getDeliverySn()));
        builder.append("，发货条码：" + (item.getDeliveryBarCode() == null ? "" : item.getDeliveryBarCode()));
        return builder.toString();
    }

    private String getDeliveryInfo(String deliveryCorpName, String deliverySn, String deliveryBarCode) {
        StringBuilder builder = new StringBuilder();
        builder.append("物流公司：" + (deliveryCorpName == null ? "" : deliveryCorpName));
        builder.append("，物流编号：" + (deliverySn == null ? "" : deliverySn));
        builder.append("，发货条码：" + (deliveryBarCode == null ? "" : deliveryBarCode));
        return builder.toString();
    }

    @Override
    @TxTransaction
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int updateCollageStatus(List<String> orderSn, Integer status) {
        return orderItemMapper.updateCollageStatus(orderSn, status);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int updateAccountAmount(Long id, BigDecimal cashAmount, BigDecimal giftAmount) {
        return orderItemMapper.updateAccountAmount(id, cashAmount, giftAmount);
    }

    @Override
    @TxTransaction
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doReOpen(OrderItem orderItem) {
        int success = orderItemMapper.cancelClose(orderItem.getId());
        if (success > 0) {
            this.createOrderLog(orderItem.getOrderId(), orderItem.getId(), OrderLogType.Update, "支付回调",
                    "重新打开已经关闭的订单，仅仅用于支付回调");
        }
        return success;
    }

    @Override
    @TxTransaction
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doPaySuccess(Order order, List<OrderItem> orderItems) {
        for (int i = 0; i < orderItems.size(); i++) {
            OrderItem orderItem = orderItems.get(i);
            // 防止订单已关闭后，支付宝继续支付
            ItemStatus status = ItemStatus.getItemStatusByName(orderItem.getStatus());
            int reopen = 0;
            if (status.getCode() == -1 || status.getCode() == -3) {
                reopen = this.doReOpen(orderItem);
            }
            int pop = 0;
            if (order.getType().equals(OrderType.bargain.name()) || order.getType().equals(OrderType.collage.name())
                    || (orderItem.getFlashPromotionId() != null && orderItem.getFlashPromotionId() > 0)) {
                // 取消冻结库存 和 扣减活动库存
                pop = productSkuService.doCancelFreezeAndDeduceFlashStock(orderItem.getProductId(),
                        orderItem.getProductSkuId(), orderItem.getProductQuantity(), reopen);
            } else {
                // 取消冻结库存 和 扣减真实库存
                pop = productSkuService.doCancelFreezeAndDeduceStock(orderItem.getProductId(),
                        orderItem.getProductSkuId(), orderItem.getProductQuantity(), reopen);
            }
            // 预计发货时间
            Date estimateDate = this.getEstimateDate(pop, orderItem.getProductId());
            // 订单明细支付成功
            orderItemMapper.doPaySuccess(orderItem.getId(), order.getPaymentType(), pop, orderItem.getCashAmount(),
                    orderItem.getGiftAmount(), estimateDate);
        }
        // @Async-考拉订单处理
        orderService.pushKaolaOrder(order.getOrderSn(), orderItems);
        // @Async-草莓订单创建
        orderService.pushCaomeiOrder(order, orderItems);
        return 1;
    }

    /**
     * 计算预计发货时间
     *
     * @param pop       占单方式
     * @param productId 商品ID
     * @return
     */
    private Date getEstimateDate(int pop, Long productId) {
        Date estimateDate = null;
        if (pop == 0) {
            // 明天之前
            estimateDate = DateUtil.getIntervalDay(new Date(), 1);
        } else {
            // 按商品类型处理
            Product product = productService.findById(productId);
            int interval = 1;
            boolean holiday = true;
            if (ProductSource.KAOLA.name().equals(product.getSource())) {
                // 考拉商品，不需要顺延节假日
                interval = 3;
                holiday = false;
            }
            if (product.getProductSellType().equals(ProductSellType.SPOT.name())) {
                // 现货
                estimateDate = DateUtil.getIntervalDay(new Date(), interval);
                estimateDate = (holiday ? this.getRecentUnholiday(estimateDate) : DateUtil.getEndOfDay(estimateDate));
            } else {
                if (product.getEstimateDay() != null) {
                    // 天数优先
                    estimateDate = DateUtil.getIntervalDay(new Date(), product.getEstimateDay());
                    estimateDate = (holiday ? this.getRecentUnholiday(estimateDate)
                            : DateUtil.getEndOfDay(estimateDate));
                } else if (product.getEstimateDate() != null) {
                    // 日期
                    estimateDate = product.getEstimateDate();
                    if (DateUtil.getStartOfDay(estimateDate).getTime() <= DateUtil.getStartOfDay(new Date())
                            .getTime()) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(new Date());
                        calendar.add(Calendar.DATE, 1);
                        estimateDate = calendar.getTime();
                    }
                    estimateDate = (holiday ? this.getRecentUnholiday(estimateDate)
                            : DateUtil.getEndOfDay(estimateDate));
                }
            }
        }
        if (estimateDate == null) {
            estimateDate = DateUtil.getIntervalDay(new Date(), 1);
        }
        estimateDate = DateUtil.getEndOfDay(estimateDate);
        return estimateDate;
    }

    /**
     * 过滤节假日
     *
     * @param dateparam
     * @return
     */
    private Date getRecentUnholiday(Date dateparam) {
        dateparam = DateUtil.getStartOfDay(dateparam);
        String dayStr = DateUtil.day2str(dateparam);
        if (redisDateHandler.get("freedays_" + dayStr) == null) {
            redisDateHandler.set("freedays_" + dayStr, holidayService.findList(dateparam));
        }
        List<Date> list = redisDateHandler.get("freedays_" + dayStr);
        // 放假日期是从小到大排列的
        for (int i = 0; i < list.size(); i++) {
            try {
                if (dateparam.compareTo(list.get(i)) < 0) {
                    return dateparam;
                } else if (dateparam.compareTo(list.get(i)) == 0) {
                    for (int j = 1; j < list.size() - i; j++) {
                        if (DateUtil.add(dateparam, Calendar.DAY_OF_YEAR, j).compareTo(list.get(i + j)) < 0) {
                            return DateUtil.add(dateparam, Calendar.DAY_OF_YEAR, j);
                        }
                    }
                    return DateUtil.add(dateparam, Calendar.DAY_OF_YEAR, list.size() - i);
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return dateparam;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doBindStore(Long id, String itemMemo, String deliveryBarCode, Long storeId, String storeCode,
                           String store_memo, String lastMan) {
        OrderItem orderItem = this.findById(id);
        Order order = orderService.findById(orderItem.getOrderId());
        if (order.getOrderStatus() != 2 && order.getOrderStatus() != 3) {
            throw new BusinessException("订单状态不允许指定门店发货，状态为：" + order.getOrderStatusName());
        }
        int result = productSkuStockService.updateOccupyStock(storeCode, deliveryBarCode,
                orderItem.getProductQuantity());
        if (result < 0) {
            throw new BusinessException("该指定门店已没有库存！");
        }
        int success = 0;
        RequisitionItem requisitionItem = requisitionItemService.doStoreDelivery(orderItem, storeId, lastMan);
        if (requisitionItem != null) {
            success = orderItemMapper.doBindStore(id, itemMemo, deliveryBarCode, storeId, store_memo);
            this.createOrderLog(orderItem.getOrderId(), orderItem.getId(), OrderLogType.Update, lastMan,
                    "指定门店发货：" + storeId + "," + (store_memo == null ? "" : store_memo));
        }
        return success;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doDesignerDelivery(Long id, int status, String operator) {
        OrderItem orderItem = this.findById(id);
        // 生成调拨单的控制
        if (orderItem.getProductSaleType() != null && orderItem.getProductSaleType().equals("BUYOUT") && status > 0) {
            throw new BusinessException("买断商品不能生成调拨单");
        }
        int success = 0;
        if (status == 0) {
            success = requisitionItemService.doCloseByOrderItem(id, operator, "取消设计师采购");
            if (success > 0) {
                this.createOrderLog(orderItem.getOrderId(), orderItem.getId(), OrderLogType.Update, operator,
                        "取消设计师采购");
            }
        } else {
            RequisitionItem requisitionItem = requisitionItemService.doDesignerDelivery(orderItem, operator);
            if (requisitionItem != null) {
                success = 1;
                this.createOrderLog(orderItem.getOrderId(), orderItem.getId(), OrderLogType.Update, operator,
                        "设置为设计师采购");
            }
        }
        return success;
    }

    /**
     * 订单明细发货
     *
     * @param orderItemId
     * @param deliveryType
     * @param deliveryBarCode
     * @param deliveryCorpName
     * @param deliverySn
     * @param operator
     * @return
     */
    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doDeliveryItem(Long orderItemId, Integer deliveryType, String deliveryBarCode, String deliveryCorpName,
                              String deliverySn, String operator, boolean msg, Integer deliveryQuantity) {
        if (StringUtils.isEmpty(deliveryCorpName)) {
            throw new BusinessException("物流公司不能为空");
        }
        if (StringUtils.isEmpty(deliverySn)) {
            throw new BusinessException("物流单号不能为空");
        }
        OrderItem orderItem = this.findOneById(orderItemId);
        if (deliveryQuantity == null || deliveryQuantity < 0 || deliveryQuantity > orderItem.getProductQuantity()) {
            throw new BusinessException("发货数量不对");
        }
        orderItem.setDeliveryCorpName(deliveryCorpName);
        orderItem.setDeliverySn(deliverySn);
        orderItem.setStatus(OrderItem.ItemStatus.DELIVERED.name());
        orderItem.setDeliveryQuantity(deliveryQuantity);
        int success = orderItemMapper.doDeliveryItem(orderItem.getId(), deliveryType, deliveryBarCode, deliveryCorpName,
                deliverySn, deliveryQuantity);
        if (success > 0) {
            redisHandler.delete("orderitem_count_" + orderItem.getBuyerMemberId());
            orderService.doDeliveryByItem(orderItem.getOrderId(), deliveryCorpName, deliverySn, operator);
            requisitionItemService.doDeliverByOrderItem(orderItemId);
            customerCompensationService.doOrderItemCompensation(orderItem, operator,
                    CompensationType.OVERDELIVERY.getCode());
            // 发货后 就将物流号推送到快递100
            logisticsService.createLogistics(orderItem.getDeliveryCorpName(), orderItem.getDeliverySn(),
                    Logistics.BusinessType.ORDER.name(), operator);
            if (msg) {
                String alert = "D2C提醒您，您购买的商品：" + orderItem.getProductName() + " 已发货，物流公司：" + deliveryCorpName
                        + " 物流单号：" + deliverySn;
                this.doSendDeliveryMsg(orderItem, alert);
            }
            this.createOrderLog(orderItem.getOrderId(), orderItem.getId(), OrderLogType.Delivered, operator, "明细发货");
        }
        return success;
    }

    private void doSendDeliveryMsg(OrderItem lastItem, String msg) {
        if (lastItem != null) {
            MemberInfo memberInfo = memberInfoService.findById(lastItem.getBuyerMemberId());
            if (memberInfo != null) {
                String subject = "发货通知";
                String content = msg;
                PushBean pushBean = new PushBean(lastItem.getBuyerMemberId(), content, 11);
                pushBean.setAppUrl("/details/order/" + lastItem.getOrderSn());
                MsgBean msgBean = new MsgBean(lastItem.getBuyerMemberId(), 11, subject, content);
                msgBean.setAppUrl("/details/order/" + lastItem.getOrderSn());
                msgBean.setPic(lastItem.getProductImg());
                msgUniteService.sendPush(pushBean, msgBean);
            }
        }
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doSign(Long itemId, String operator, String logMemo, Integer interval) {
        OrderItem item = this.findById(itemId);
        item.setStatus(ItemStatus.SIGNED.name());
        Date now = new Date();
        int success = orderItemMapper.doSign(itemId, now, DateUtil.getIntervalDay(now, interval));
        if (success > 0) {
            redisHandler.delete("orderitem_count_" + item.getBuyerMemberId());
            createOrderLog(item.getOrderId(), item.getId(), OrderLogType.Success, operator, logMemo);
        }
        return success;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doCodSign(Long itemId, Date confirmDate, String operator, String logMemo, Integer interval) {
        OrderItem item = this.findById(itemId);
        item.setStatus(ItemStatus.SIGNED.name());
        int success = orderItemMapper.doSign(itemId, confirmDate, DateUtil.getIntervalDay(confirmDate, interval));
        if (success > 0) {
            redisHandler.delete("orderitem_count_" + item.getBuyerMemberId());
            createOrderLog(item.getOrderId(), item.getId(), OrderLogType.Success, operator, logMemo);
            List<OrderItem> items = orderItemMapper.findByOrderId(item.getOrderId());
            int colseNum = 0;
            int successNum = 0;
            for (OrderItem oi : items) {
                String statusStr = oi.getStatus();
                ItemStatus status = ItemStatus.getItemStatusByName(statusStr);
                if (status.getCode() < 0) {
                    colseNum++;
                }
                if (status.getCode() >= 7) {
                    successNum++;
                }
            }
            // 除了关闭的OrderItem，其他OrderItem都已发货，order改为已发货
            if ((colseNum + successNum) == items.size() && successNum > 0) {
                orderService.updateOrderStatus(item.getOrderId(), OrderStatus.Delivered, OrderStatus.Success, operator);
            }
        }
        return success;
    }

    @Override
    @AsyncMethod(delay = 3000)
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void doDplusSign(List<OrderItem> orderItems) {
        int success = 0;
        Long orderId = 0L;
        for (OrderItem item : orderItems) {
            if (item.getDplusId() != null && item.getDplusCode() != null) {
                orderId = item.getOrderId();
                ProductSkuStock stockObj = productSkuStockService.findOne(item.getDplusCode(), item.getProductSkuSn());
                if (stockObj != null
                        && (stockObj.getStock() - stockObj.getOccupyStock() >= item.getProductQuantity())) {
                    int result = productSkuStockService.updateOccupyStock(item.getDplusCode(),
                            item.getDeliveryBarCode(), item.getProductQuantity());
                    if (result < 0) {
                        throw new BusinessException("该指定门店已没有库存！");
                    }
                    item.setStatus(ItemStatus.SIGNED.name());
                    Date now = new Date();
                    success = orderItemMapper.doSign(item.getId(), now, DateUtil.getIntervalDay(now, 7));
                    if (success > 0) {
                        orderItemMapper.updateBusType(item.getId(), BusType.DPLUS.name());
                        redisHandler.delete("orderitem_count_" + item.getBuyerMemberId());
                        createOrderLog(item.getOrderId(), item.getId(), OrderLogType.Success, "sys", "店主零售，无需发货，自动签收");
                        requisitionItemService.doBurgeonForOrderItem(item.getId(), DocType.Retail);
                    }
                }
            }
        }
        if (success > 0) {
            orderService.updateOrderStatus(orderId, OrderStatus.WaitingForDelivery, OrderStatus.Delivered, "sys");
        }
    }

    @Override
    @TxTransaction
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doSuccess(Long id, BigDecimal diffAmount) {
        return orderItemMapper.doSuccess(id, diffAmount);
    }

    @Override
    @TxTransaction
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doCloseByOrder(Long id, String itemCloseMan, String itemCloseReason, String status) {
        return orderItemMapper.doClose(id, itemCloseMan, itemCloseReason, status);
    }

    @Override
    @TxTransaction
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doCloseItem(Long orderItemId, String closer, String closeInfo) {
        OrderItem orderItem = orderItemMapper.selectByPrimaryKey(orderItemId);
        ItemStatus itemStatus = ItemStatus.getItemStatusByName(orderItem.getStatus());
        if (itemStatus.getCode() <= 0) {
            throw new BusinessException("订单已关闭，不能重复关闭，关闭不成功");
        }
        Order order = orderService.findById(orderItem.getOrderId());
        int success = 0;
        if (order.getOrderStatus().equals(OrderStatus.WaitingForPay.getCode())) {
            // 未付款
            orderItem.setStatus(ItemStatus.MALLCLOSE.toString());
            success = orderItemMapper.doClose(orderItem.getId(), closer, closeInfo, ItemStatus.MALLCLOSE.toString());
        } else {
            // 售后关闭
            orderItem.setStatus(ItemStatus.AFTERCLOSE.toString());
            success = orderItemMapper.doClose(orderItem.getId(), closer, closeInfo, ItemStatus.AFTERCLOSE.toString());
        }
        if (success > 0) {
            redisHandler.delete("orderitem_count_" + orderItem.getBuyerMemberId());
            // 分销订单返利
            partnerBillService.doClose(orderItemId);
            // 赔偿金额大于0的
            if (orderItem.getCompensationAmount().compareTo(new BigDecimal(0)) > 0) {
                customerCompensationService.updateStatusByOrderItem(orderItem.getId());
            }
            orderService.doSuccessByItem(order.getId(), OrderStatus.getStatus(order.getOrderStatus()), closer);
            return success;
        } else {
            throw new BusinessException("关闭不成功！");
        }
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doCancelDelivery(Long orderItemId, String cancelReason, String operator) {
        OrderItem orderItem = findById(orderItemId);
        int success = 0;
        if (ItemStatus.DELIVERED.name().equals(orderItem.getStatus())) {
            // 先做取消状态
            success = orderItemMapper.cancelDelivery(orderItemId);
            if (success > 0) {
                // 取消成功，将状态改为准备发货状态
                redisHandler.delete("orderitem_count_" + orderItem.getBuyerMemberId());
                this.createOrderLog(orderItem.getOrderId(), orderItem.getId(), OrderLogType.Update, operator, "取消明细发货");
                Order order = this.orderService.findById(orderItem.getOrderId());
                if (order.getOrderStatus().equals(OrderStatus.Delivered.getCode())) {
                    orderService.updateOrderStatus(order.getId(), OrderStatus.Delivered, OrderStatus.WaitingForDelivery,
                            operator);
                }
            }
        } else {
            throw new BusinessException("状态不匹配，目前的状态为：" + ItemStatus.getItemStatusByName(orderItem.getStatus()));
        }
        return success;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doCancelStore(Long orderItemId, String info, String username) {
        int success = requisitionItemService.doCloseByOrderItem(orderItemId, username, info);
        if (success > 0) {
            orderItemMapper.cancelStore(orderItemId);
            OrderItem orderItem = this.findById(orderItemId);
            this.createOrderLog(orderItem.getOrderId(), orderItem.getId(), OrderLogType.Update, username,
                    info + "取消" + orderItem.getStoreId() + "发货");
        }
        return success;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doLock(Long id, String info, String lastMan) {
        OrderItem orderItem = this.findById(id);
        int success = orderItemMapper.doLock(id, orderItem.getItemMemo() + info);
        if (success > 0) {
            this.createOrderLog(orderItem.getOrderId(), orderItem.getId(), OrderLogType.Update, lastMan,
                    "客服已经锁定订单明细，商品" + orderItem.getProductName());
        }
        return success;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doCancelLock(Long id, String info, String lastMan) {
        OrderItem orderItem = this.findById(id);
        int success = orderItemMapper.cancelLock(id, orderItem.getItemMemo() + info);
        if (success > 0) {
            this.createOrderLog(orderItem.getOrderId(), orderItem.getId(), OrderLogType.Update, lastMan,
                    "客服已经取消锁定订单，商品" + orderItem.getProductName());
        }
        return success;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doBalance(Long id, Date signDate, String balanceMan, BigDecimal balanceMoney, String balanceReason,
                         String billNumber) {
        OrderItem oi = orderItemMapper.selectByPrimaryKey(id);
        if (oi.getPaymentType() != 3) {
            throw new BusinessException("非货到付款订单，结算不成功！");
        }
        orderItemMapper.doBalance(id, balanceMan, balanceMoney, balanceReason, billNumber);
        long diff = System.currentTimeMillis() - signDate.getTime();
        ItemStatus status = ItemStatus.getItemStatusByName(oi.getStatus());
        if (status.getCode() == 2) {
            this.doCodSign(id, new Date(), balanceMan, "确认收货", 7);
            if (diff > 7 * 1000 * 60 * 60 * 24) {
                orderItemMapper.doAutoSuccess(7 * 24 * 60 * 60);
            }
        } else if (status.getCode() == 7) {
            if (diff > 7 * 1000 * 60 * 60 * 24) {
                orderItemMapper.doAutoSuccess(7 * 24 * 60 * 60);
            }
        }
        return 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doComment(Long orderItemId, Long commentId) {
        int success = orderItemMapper.updateComment(orderItemId, commentId);
        return success;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doCreateReship(Long reshipId, Long orderItemId) {
        return orderItemMapper.doCreateReship(reshipId, orderItemId);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doCreateRefund(Long refundId, Long orderItemId) {
        return orderItemMapper.doCreateRefund(refundId, orderItemId);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doCreateExchange(Long exchangeId, Long orderItemId) {
        return orderItemMapper.doCreateExchange(exchangeId, orderItemId);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doCompensation(Long id, BigDecimal compensation) {
        return orderItemMapper.doCompensation(id, compensation);
    }

    @Override
    public int updateWalletAmount(Long id, BigDecimal cashAmount, BigDecimal giftAmount) {
        return orderItemMapper.updateWalletAmount(id, cashAmount, giftAmount);
    }

    @Override
    public PageResult<Map<String, Object>> findRankingSummaryByProduct(PageModel page,
                                                                       ProductOrderSumSearcher searcher) {
        PageResult<Map<String, Object>> pager = new PageResult<>(page);
        int totalCount = orderItemMapper.countRankingSummaryByProduct(searcher);
        if (totalCount > 0) {
            List<Map<String, Object>> list = orderItemMapper.findRankingSummaryByProduct(page, searcher);
            pager.setList(list);
            pager.setTotalCount(totalCount);
        }
        return pager;
    }

    @Override
    public List<Long> findProductIdByBuyer(Long memberId) {
        return orderItemMapper.findProductIdByBuyer(memberId);
    }

    @Override
    public PageResult<Map<String, Object>> findDailyFinishAmount(PageModel page) {
        PageResult<Map<String, Object>> pager = new PageResult<>(page);
        int totalCount = orderItemMapper.countDailyFinishAmount();
        if (totalCount > 0) {
            List<Map<String, Object>> list = orderItemMapper.findDailyFinishAmount(page);
            pager.setList(list);
        }
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    public List<Map<String, Object>> findRecentlySales(PageModel page) {
        return orderItemMapper.findRecentlySales(page);
    }

    @Override
    public List<Map<String, Object>> findRecentlyMonthSales(PageModel page, Integer month) {
        return orderItemMapper.findRecentlyMonthSales(page, month);
    }

    @Override
    public List<Map<String, Object>> findPartnerSales(PageModel pageModel, int days) {
        return orderItemMapper.findPartnerSales(pageModel, days);
    }

    @Override
    public List<OrderItem> findByMemberInfoId(Long memberInfoId, PageModel pager) {
        return orderItemMapper.findByMemberInfoId(memberInfoId, pager);
    }

    @Override
    public JSONObject findInfoByMemberId(Long memberId, Date beginDate, Date endDate) {
        JSONObject obj = new JSONObject();
        String brandsName = orderItemMapper.findBrandsByMemberId(memberId, 6, beginDate, endDate);
        OrderItem orderItem = orderItemMapper.findMaxCostByMemberId(memberId, beginDate, endDate);
        BigDecimal sumCost = orderItemMapper.findSumCostByMemberId(memberId, beginDate, endDate);
        Integer number = orderItemMapper.findRankByMoney(sumCost == null ? new BigDecimal(0) : sumCost, beginDate,
                endDate);
        obj.put("brandsName", brandsName == null ? "" : brandsName);
        obj.put("MaxCostOrderItem", orderItem);
        obj.put("sumCost", sumCost == null ? 0 : sumCost);
        Map<String, Object> map = new HashMap<>();
        Integer totalMember = orderItemMapper.countMemberId();
        map.put("number", totalMember);
        obj.put("rank", new DecimalFormat("0.00").format((double) (totalMember - number) * 100 / totalMember));
        return obj;
    }

    @Override
    public List<Map<String, Object>> findActualAmountForAward(Date beginPaymentDate, Date endPaymentDate,
                                                              Date beginFinishDate, Date endFinishDate, List<Long> awardIds) {
        return orderItemMapper.findActualAmountForAward(beginPaymentDate, endPaymentDate, beginFinishDate,
                endFinishDate, awardIds);
    }

    @Override
    public PageResult<OrderItem> findCompensation(PageModel page, Date deadline) {
        PageResult<OrderItem> pager = new PageResult<>(page);
        int totalCount = orderItemMapper.countCompensation(deadline);
        if (totalCount > 0) {
            List<OrderItem> list = orderItemMapper.findCompensation(page, deadline);
            pager.setList(list);
        }
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    public PageResult<OrderItem> findWaitUpdateDeliveryCaomeiOrder(PageModel page) {
        PageResult<OrderItem> pager = new PageResult<OrderItem>(page);
        Integer totalCount = orderItemMapper.countWaitUpdateDeliveryCaomeiOrder();
        List<OrderItem> list = new ArrayList<>();
        if (totalCount > 0) {
            list = orderItemMapper.findWaitUpdateDeliveryCaomeiOrder(page);
        }
        pager.setTotalCount(totalCount);
        pager.setList(list);
        return pager;
    }

    @Override
    public Integer countWaitUpdateDeliveryCaomeiOrder() {
        return orderItemMapper.countWaitUpdateDeliveryCaomeiOrder();
    }

    @Override
    public List<Map<String, Object>> findDplusTotalAmount(Date startDate, Date endDate) {
        return orderItemMapper.findDplusTotalAmount(startDate, endDate);
    }

    @Override
    public List<Map<String, Object>> findDplusAmountByShop(Long storeId, Date startDate, Date endDate) {
        return orderItemMapper.findDplusAmountByShop(storeId, startDate, endDate);
    }

    @Override
    public PageResult<OrderItem> findDplusForStatement(Date beginDate, PageModel page) {
        PageResult<OrderItem> pager = new PageResult<>(page);
        int count = orderItemMapper.countDplusForStatement(beginDate);
        List<OrderItem> list = new ArrayList<>();
        if (count > 0) {
            list = orderItemMapper.findDplusForStatement(beginDate, page);
        }
        pager.setTotalCount(count);
        pager.setList(list);
        return pager;
    }

}
