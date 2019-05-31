package com.d2c.flame.controller.member;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.ResponseResult;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.logger.model.ExchangeLog;
import com.d2c.logger.model.RefundLog;
import com.d2c.logger.model.ReshipLog;
import com.d2c.logger.service.ExchangeLogService;
import com.d2c.logger.service.RefundLogService;
import com.d2c.logger.service.ReshipLogService;
import com.d2c.member.enums.DeviceTypeEnum;
import com.d2c.member.model.MemberInfo;
import com.d2c.order.dto.ExchangeDto;
import com.d2c.order.dto.OrderItemDto;
import com.d2c.order.dto.ReshipDto;
import com.d2c.order.model.*;
import com.d2c.order.model.Exchange.ExchangeStatus;
import com.d2c.order.model.Order.OrderStatus;
import com.d2c.order.model.OrderItem.BusType;
import com.d2c.order.model.OrderItem.ItemStatus;
import com.d2c.order.model.Refund.RefundStatus;
import com.d2c.order.model.Reship.ReshipStatus;
import com.d2c.order.query.ExchangeSearcher;
import com.d2c.order.query.RefundSearcher;
import com.d2c.order.query.ReshipSearcher;
import com.d2c.order.service.*;
import com.d2c.order.service.tx.ExchangeTxService;
import com.d2c.product.model.Product.ProductSource;
import com.d2c.util.date.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 我的售后
 *
 * @author wwn
 * @version 3.0
 */
@RestController
@RequestMapping(value = "/v3/api/aftersale")
public class MyAfterSaleController extends BaseController {

    @Autowired
    private RefundService refundService;
    @Autowired
    private ReshipService reshipService;
    @Autowired
    private ExchangeService exchangeService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private SettingService settingService;
    @Autowired
    private RefundLogService refundLogService;
    @Autowired
    private ReshipLogService reshipLogService;
    @Autowired
    private ExchangeLogService exchangeLogService;
    @Reference
    private ExchangeTxService exchangeTxService;

    /**
     * 售后数量
     *
     * @return
     */
    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public ResponseResult count() {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        Map<String, Object> refundCounts = refundService.countByStatusAndMemberId(memberInfo.getId());
        result.put("refundCount", refundCounts);
        Map<String, Object> reshipCounts = reshipService.countByStatusAndMemberId(memberInfo.getId());
        result.put("reshipCount", reshipCounts);
        Map<String, Object> exchangeCounts = exchangeService.countByStatusAndMemberId(memberInfo.getId());
        result.put("exchangeCount", exchangeCounts);
        int totalCount = 0;
        try {
            totalCount = Integer.parseInt(refundCounts.get("processCount").toString())
                    + Integer.parseInt(refundCounts.get("reshipCount").toString())
                    + Integer.parseInt(exchangeCounts.get("processCount").toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        result.put("totalCount", totalCount);
        return result;
    }

    /**
     * 考拉订单同仓库的明细列表
     *
     * @param orderItemId
     * @return
     */
    @RequestMapping(value = "/kaola/list", method = RequestMethod.GET)
    public ResponseResult kaolaList(Long orderItemId) {
        ResponseResult result = new ResponseResult();
        OrderItem orderItem = orderItemService.findById(orderItemId);
        List<OrderItemDto> list = orderItemService.findDtoByOrderId(orderItem.getOrderId());
        JSONArray array = new JSONArray();
        for (OrderItemDto item : list) {
            if (orderItem.getWarehouseId() != null && item.getWarehouseId() != null) {
                if (orderItem.getWarehouseId() > 0 && orderItem.getWarehouseId().equals(item.getWarehouseId())) {
                    array.add(item.toJson());
                }
            }
        }
        result.put("items", array);
        return result;
    }

    /**
     * 退款单详情
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/refund/{id}", method = RequestMethod.GET)
    public ResponseResult refund(@PathVariable Long id) {
        ResponseResult result = new ResponseResult();
        Refund refund = refundService.findById(id);
        result.put("refund", refund.toJson());
        List<RefundLog> refundLogs = refundLogService.findByRefundId(refund.getId());
        JSONArray logs = new JSONArray();
        refundLogs.forEach(log -> logs.add(log.toJson()));
        result.put("refundLogs", logs);
        return result;
    }

    /**
     * 退货单详情
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/reship/{id}", method = RequestMethod.GET)
    public ResponseResult reship(@PathVariable Long id) {
        ResponseResult result = new ResponseResult();
        Reship reship = reshipService.findById(id);
        result.put("reship", reship.toJson());
        List<ReshipLog> reshipLogs = reshipLogService.findByReshipId(reship.getId());
        JSONArray logs = new JSONArray();
        reshipLogs.forEach(log -> logs.add(log.toJson()));
        result.put("reshipLogs", logs);
        if (reship.getRefundId() != null) {
            Refund refund = refundService.findById(reship.getRefundId());
            result.put("refund", refund.toJson());
            List<RefundLog> refundLogs = refundLogService.findByRefundId(refund.getId());
            JSONArray logs2 = new JSONArray();
            refundLogs.forEach(log -> logs2.add(log.toJson()));
            result.put("refundLogs", logs2);
        } else {
            JSONObject obj = new JSONObject();
            obj.put("applyAmount", reship.getTradeAmount());
            obj.put("totalAmount", reship.getTradeAmount());
            result.put("refund", obj);
        }
        return result;
    }

    /**
     * 换货单详情
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/exchange/{id}", method = RequestMethod.GET)
    public ResponseResult exchange(@PathVariable Long id) {
        ResponseResult result = new ResponseResult();
        Exchange exchange = exchangeService.findById(id);
        result.put("exchange", exchange.toJson());
        List<ExchangeLog> exchangeLogs = exchangeLogService.findByExchangeId(exchange.getId());
        JSONArray logs = new JSONArray();
        exchangeLogs.forEach(log -> logs.add(log.toJson()));
        result.put("exchangeLogs", logs);
        return result;
    }

    /**
     * 我的退款申请
     *
     * @param searcher
     * @param page
     * @return
     */
    @RequestMapping(value = "/refund/list", method = RequestMethod.GET)
    public ResponseResult refundList(RefundSearcher searcher, PageModel page) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        searcher.setMemberId(memberInfo.getId());
        PageResult<Refund> pager = refundService.findMine(searcher, page);
        JSONArray array = new JSONArray();
        pager.getList().forEach(item -> array.add(item.toJson()));
        result.putPage("refunds", pager, array);
        return result;
    }

    /**
     * 我的退款退货申请
     *
     * @param searcher
     * @param page
     * @return
     */
    @RequestMapping(value = "/reship/list", method = RequestMethod.GET)
    public ResponseResult reshipList(ReshipSearcher searcher, PageModel page) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        searcher.setMemberId(memberInfo.getId());
        PageResult<ReshipDto> pager = reshipService.findMine(searcher, page);
        JSONArray array = new JSONArray();
        pager.getList().forEach(item -> array.add(item.toJson()));
        result.putPage("reships", pager, array);
        return result;
    }

    /**
     * 我的换货申请
     *
     * @param searcher
     * @param page
     * @return
     */
    @RequestMapping(value = "/exchange/list", method = RequestMethod.GET)
    public ResponseResult exchangeList(ExchangeSearcher searcher, PageModel page) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        searcher.setMemberId(memberInfo.getId());
        PageResult<ExchangeDto> pager = exchangeService.findMine(searcher, page);
        JSONArray array = new JSONArray();
        pager.getList().forEach(item -> array.add(item.toJson()));
        result.putPage("exchanges", pager, array);
        return result;
    }

    /**
     * 是否已售后
     *
     * @param orderItem
     * @return
     */
    private boolean isAftered(OrderItemDto orderItem) {
        /**
         * 已经存在退货申请，直接返回退货
         */
        if (orderItem.getReship() != null && orderItem.getReship().getReshipStatus() >= 0) {
            return true;
        }
        /**
         * 已经存在退款申请，直接返回退款
         */
        if (orderItem.getRefund() != null && orderItem.getRefund().getRefundStatus() >= 0) {
            return true;
        }
        /**
         * 已经存在换货申请，直接返回换货
         */
        if (orderItem.getExchange() != null && orderItem.getExchange().getExchangeStatus() >= 0) {
            return true;
        }
        return false;
    }

    /**
     * 申请退款
     *
     * @param refund
     * @param appTerminal
     * @param appVersion
     * @return
     */
    @RequestMapping(value = "/apply/refund", method = RequestMethod.POST)
    public ResponseResult applyRefund(Refund refund, String appTerminal, String appVersion) {
        ResponseResult result = new ResponseResult();
        result.setMsg("退款单提交成功");
        this.getLoginMemberInfo();
        if (refund.getOrderItemId() == null) {
            throw new BusinessException("订单明细不存在，请联系D2C客服！");
        }
        OrderItemDto orderItem = orderItemService.findOrderItemDtoById(refund.getOrderItemId());
        if (orderItem == null) {
            throw new BusinessException("订单明细不存在，请联系D2C客服！");
        }
        if (orderItem.getProductSource() != null && orderItem.getProductSource().equals(ProductSource.KAOLA.name())) {
            throw new BusinessException("申请售后请联系客服！");
        }
        if (orderItem.getAfter() == 0) {
            throw new BusinessException("该订单不支持申请售后！");
        }
        if (isAftered(orderItem)) {
            throw new BusinessException("已经存在售后申请，不可重复提交！");
        }
        Order order = orderService.findById(orderItem.getOrderId());
        if (order.getType().equals(Order.OrderType.distribution.toString()) && orderItem.getDplusId() == null) {
            throw new BusinessException("非店主的经销商订单不允许申请售后！");
        }
        if (order.getType().equals(Order.OrderType.collage.toString()) && orderItem.getCollageStatus() != 8) {
            throw new BusinessException("拼团中的订单不允许申请售后！");
        }
        try {
            if (order.getOrderStatus() >= OrderStatus.WaitingForDelivery.getCode()) {
                refund.createRefund(order, orderItem);
                refund.setDevice(DeviceTypeEnum.divisionDevice(appTerminal));
                refund.setAppVersion(appVersion);
                if (refund.getAllRefund() == null) {
                    refund.setAllRefund(1);
                }
                Payment pay = paymentService.findById(order.getPaymentId());
                if (pay != null) {
                    refund.setBackAccountSn(pay.getPayer() != null ? pay.getPayer() : pay.getAlipaySn());
                }
                refundService.insert(refund);
            } else {
                throw new BusinessException("订单状态不匹配，提交不成功！");
            }
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        return result;
    }

    /**
     * 申请退款退货
     *
     * @param reship
     * @param applyAmount
     * @param appTerminal
     * @param appVersion
     * @return
     */
    @RequestMapping(value = "/apply/reship", method = RequestMethod.POST)
    public ResponseResult applyReship(Reship reship, BigDecimal applyAmount, String appTerminal, String appVersion) {
        ResponseResult result = new ResponseResult();
        result.setMsg("退货退款单提交成功");
        MemberInfo memberInfo = this.getLoginMemberInfo();
        if (reship.getOrderItemId() == null) {
            throw new BusinessException("订单明细不存在，请联系D2C客服！");
        }
        OrderItemDto orderItem = orderItemService.findOrderItemDtoById(reship.getOrderItemId());
        if (orderItem == null) {
            throw new BusinessException("订单明细不存在，请联系D2C客服！");
        }
        if (orderItem.getProductSource() != null && orderItem.getProductSource().equals(ProductSource.KAOLA.name())) {
            throw new BusinessException("申请售后请联系客服！");
        }
        if (orderItem.getAfter() == 0 && !BusType.DPLUS.name().equals(orderItem.getBusType())) {
            throw new BusinessException("该订单不支持申请售后！");
        }
        if (isAftered(orderItem)) {
            throw new BusinessException("已经存在售后申请，不可重复提交！");
        }
        if (reship.getActualQuantity() == null || reship.getActualQuantity() < 1) {
            throw new BusinessException("退货数量不能小于1！");
        }
        if (orderItem.getDeliveryQuantity() < reship.getActualQuantity()) {
            throw new BusinessException("实退数量不能大于发货数量！");
        }
        Order order = orderService.findById(orderItem.getOrderId());
        if (order.getType().equals(Order.OrderType.distribution.toString()) && orderItem.getDplusId() == null) {
            throw new BusinessException("非店主的经销商订单不允许申请售后！");
        }
        if ((ItemStatus.DELIVERED.name().equals(orderItem.getStatus())
                || ItemStatus.SIGNED.name().equals(orderItem.getStatus()))) {
            // 时间判定在退款单那边没有
            Date deadline = orderItem.getSignDate();
            if (deadline != null) {
                deadline = DateUtil.add(deadline, Calendar.DAY_OF_YEAR, 10);
            } else {
                deadline = orderItem.getDeliveryTime();
                deadline = DateUtil.add(deadline, Calendar.DAY_OF_YEAR, 15);
            }
            if (deadline.after(new Date())) {
                reship.createReship(order, orderItem);
                reship.setDevice(DeviceTypeEnum.divisionDevice(appTerminal));
                reship.setAppVersion(appVersion);
                setBackInfo(reship, orderItem.getDeliveryType(), orderItem.getDesignerId(),
                        ProductSource.KAOLA.name().equals(orderItem.getProductSource()));
                reship = reshipService.insert(reship);
                if (orderItem.getBusType() != null && orderItem.getBusType().equals(BusType.DPLUS.name())) {
                    Refund refund = new Refund();
                    refund.createRefund(reship, order.getPaymentType());
                    refund.setOrderPaySn(order.getPaymentSn());
                    refund.setOrderPaymentId(order.getPaymentId());
                    refund.setOrderCreateDate(order.getCreateDate());
                    refund.setOrderMemo(order.getMemo());
                    refund.setAuditor(memberInfo.getLoginCode());
                    refund.setAuditDate(new Date());
                    refund.setMemberName(order.getMemberMobile());
                    refund.setRefundStatus(RefundStatus.WAITFORPAYMENT.getCode());
                    refund.setCreator(reship.getCreator());
                    refund.setDevice(reship.getDevice());
                    refund.setAppVersion(reship.getAppVersion());
                    refund.setApplyAmount(reship.getTradeAmount());
                    refund.setTotalAmount(reship.getTradeAmount());
                    refund.setProxy(reship.getProxy());
                    refund.setBackAccountType(order.getPaymentType());
                    refund.setBackAccountName(order.getPaymentSn());
                    Payment pay = paymentService.findById(order.getPaymentId());
                    if (pay != null) {
                        refund.setBackAccountSn(pay.getPayer() != null ? pay.getPayer() : pay.getAlipaySn());
                    }
                    reshipService.doStoreAgree(reship.getId(), memberInfo.getLoginCode(), "店主零售，无需退货，自动收货", refund);
                }
            } else {
                throw new BusinessException("已经超过售后7天了！");
            }
        } else {
            throw new BusinessException("订单状态不匹配，提交不成功！");
        }
        return result;
    }

    /**
     * 申请换货
     *
     * @param exchange
     * @param appTerminal
     * @param appVersion
     * @return
     */
    @RequestMapping(value = "/apply/exchange", method = RequestMethod.POST)
    public ResponseResult applyExchange(Exchange exchange, String appTerminal, String appVersion) {
        ResponseResult result = new ResponseResult();
        result.setMsg("换货单提交成功");
        MemberInfo memberInfo = this.getLoginMemberInfo();
        OrderItemDto orderItem = orderItemService.findOrderItemDtoById(exchange.getOrderItemId());
        if (orderItem == null) {
            throw new BusinessException("订单明细不存在，请联系D2C客服！");
        }
        if (orderItem.getProductSource() != null && orderItem.getProductSource().equals(ProductSource.KAOLA.name())) {
            throw new BusinessException("申请售后请联系客服！");
        }
        if (orderItem.getAfter() == 0) {
            throw new BusinessException("该订单不支持申请售后！");
        }
        if (isAftered(orderItem)) {
            throw new BusinessException("已经存在售后申请，不可重复提交！");
        }
        Order order = orderService.findById(orderItem.getOrderId());
        if (order.getType().equals(Order.OrderType.distribution.toString()) && orderItem.getDplusId() == null) {
            throw new BusinessException("非店主的经销商订单不允许申请售后！");
        }
        if (ItemStatus.DELIVERED.name().equals(orderItem.getStatus())
                || ItemStatus.SIGNED.name().equals(orderItem.getStatus())) {
            Date deadline = orderItem.getSignDate();
            if (deadline != null) {
                deadline = DateUtil.add(deadline, Calendar.DAY_OF_YEAR, 10);
            } else {
                deadline = orderItem.getDeliveryTime();
                deadline = DateUtil.add(deadline, Calendar.DAY_OF_YEAR, 15);
            }
            if (deadline.after(new Date())) {
                exchange.createExchange(order, orderItem);
                exchange.setDevice(DeviceTypeEnum.divisionDevice(appTerminal));
                exchange.setAppVersion(appVersion);
                setBackInfo(exchange, orderItem.getDeliveryType(), orderItem.getDesignerId(),
                        ProductSource.KAOLA.name().equals(orderItem.getProductSource()));
                exchangeService.insert(exchange, memberInfo.getLoginCode());
            } else {
                throw new BusinessException("已经超过售后7天了！");
            }
        } else {
            throw new BusinessException("订单状态不匹配，提交不成功！");
        }
        return result;
    }

    /**
     * 设置退货地址
     *
     * @param t
     * @param deliveryType
     * @param designerId
     * @param isKAOLA
     * @return
     */
    private <T> T setBackInfo(T t, Integer deliveryType, Long designerId, boolean isKAOLA) {
        String backAddress = "";
        String backMobile = "";
        String backConsignee = "";
        // if (deliveryType == 1) { 注释掉目前统一发给LBP仓
        // Brand brand = brandService.findById(designerId);
        // backAddress = brand.getAddress();
        // backMobile = brand.getMobile();
        // backConsignee = brand.getConsignee();
        // }
        if (StringUtils.isBlank(backAddress) || StringUtils.isBlank(backMobile) || StringUtils.isBlank(backConsignee)) {
            Setting setting = null;
            if (isKAOLA) {
                setting = settingService.findByCode(Setting.KAOLABACKINFOCODE);
            } else {
                setting = settingService.findByCode(Setting.BACKINFOCODE);
            }
            JSONObject json = JSONObject.parseObject(Setting.defaultValue(setting, "{}").toString());
            backAddress = json.getString("backAddress");
            backConsignee = json.getString("backConsignee");
            backMobile = json.getString("backMobile");
        }
        if (t instanceof Reship) {
            ((Reship) t).setBackAddress(backAddress);
            ((Reship) t).setBackMobile(backMobile);
            ((Reship) t).setBackConsignee(backConsignee);
        } else if (t instanceof Exchange) {
            ((Exchange) t).setBackAddress(backAddress);
            ((Exchange) t).setBackMobile(backMobile);
            ((Exchange) t).setBackConsignee(backConsignee);
        }
        return t;
    }

    /**
     * 取消退款
     *
     * @param refundId
     * @return
     */
    @RequestMapping(value = "/cancel/refund", method = RequestMethod.POST)
    public ResponseResult cancelRefund(Long refundId) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        Refund refund = refundService.findByIdAndMemberInfoId(refundId, memberInfo.getId());
        if (refund == null) {
            throw new BusinessException("退款单不存在！");
        }
        if (refund.getRefundStatus().equals(RefundStatus.APPLY.getCode())
                || refund.getRefundStatus().equals(RefundStatus.CREATE.getCode())) {
            int success = 0;
            if (refund.getReshipId() != null) {
                success = reshipService.doCancel(refund.getReshipId(), memberInfo.getLoginCode());
            } else {
                success = refundService.doCancel(refund.getId(), memberInfo.getLoginCode());
            }
            if (success > 0) {
                result.setMsg("取消退款成功");
                Refund newRefund = refundService.findByIdAndMemberInfoId(refundId, memberInfo.getId());
                result.put("refund", newRefund.toJson());
            } else {
                throw new BusinessException("取消退款不成功，请联系D2C客服！");
            }
        } else {
            throw new BusinessException("取消退款不成功，正在退款处理中，请联系客服取消！");
        }
        return result;
    }

    /**
     * 取消退款退货
     *
     * @param reshipId
     * @return
     */
    @RequestMapping(value = "/cancel/reship", method = RequestMethod.POST)
    public ResponseResult cancelReship(Long reshipId) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        Reship reship = reshipService.findByIdAndMemberInfoId(reshipId, memberInfo.getId());
        if (reship == null) {
            throw new BusinessException("退货单不存在！");
        }
        if (!(reship.getReshipStatus().equals(ReshipStatus.SUCCESS.getCode()))) {
            reshipService.doCancel(reship.getId(), memberInfo.getLoginCode());
            result.setMsg("取消退款退货成功");
            Reship newReship = reshipService.findByIdAndMemberInfoId(reshipId, memberInfo.getId());
            result.put("reship", newReship.toJson());
        } else {
            throw new BusinessException("取消退款退货不成功，正在退款处理中，请联系客服取消！");
        }
        return result;
    }

    /**
     * 取消换货
     *
     * @param exchangeId
     * @return
     */
    @RequestMapping(value = "/cancel/exchange", method = RequestMethod.POST)
    public ResponseResult cancelExchange(Long exchangeId) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        Exchange exchange = exchangeService.findByIdAndMemberInfoId(exchangeId, memberInfo.getId());
        if (exchange == null) {
            throw new BusinessException("换货单不存在！");
        }
        if (exchange.getExchangeStatus() >= Exchange.ExchangeStatus.APPLY.getCode()
                && exchange.getExchangeStatus() <= Exchange.ExchangeStatus.WAITFORRECEIVE.getCode()) {
            int success = exchangeService.doCancel(exchange.getId(), memberInfo.getLoginCode());
            if (success > 0) {
                result.setMsg("取消换货成功");
                Exchange newExchange = exchangeService.findByIdAndMemberInfoId(exchangeId, memberInfo.getId());
                result.put("exchange", newExchange.toJson());
            } else {
                throw new BusinessException("取消换货不成功，请联系D2C客服！");
            }
        } else {
            throw new BusinessException("取消换货不成功，状态不匹配！");
        }
        return result;
    }

    /**
     * 退货提交物流信息
     *
     * @param reshipId
     * @param deliveryCorpName
     * @param deliverySn
     * @param memo
     * @return
     */
    @RequestMapping(value = "/reship/logistic", method = RequestMethod.POST)
    public ResponseResult doReshipLogistics(Long reshipId, String deliveryCorpName, String deliverySn, String memo) {
        ResponseResult result = new ResponseResult();
        MemberInfo member = this.getLoginMemberInfo();
        result.setMsg("物流信息填写成功");
        if (StringUtils.isNotBlank(deliverySn)) {
            Reship reship = this.reshipService.findByIdAndMemberInfoId(reshipId, member.getId());
            if (reship != null) {
                reshipService.doLogistic(reship.getId(), deliverySn, deliveryCorpName, memo, member.getLoginCode());
            } else {
                throw new BusinessException("退货订单不存在！");
            }
        } else {
            throw new BusinessException("物流编号不能为空！");
        }
        return result;
    }

    /**
     * 换货提交物流信息
     *
     * @param exchangeId
     * @param deliveryCorpName
     * @param deliverySn
     * @param memo
     * @return
     */
    @RequestMapping(value = "/exchange/logistic", method = RequestMethod.POST)
    public ResponseResult doExchangeLogistics(Long exchangeId, String deliveryCorpName, String deliverySn,
                                              String memo) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        result.setMsg("物流信息填写成功");
        if (StringUtils.isNotBlank(deliverySn)) {
            Exchange exchange = exchangeService.findByIdAndMemberInfoId(exchangeId, memberInfo.getId());
            if (exchange != null) {
                exchange.setDeliveryCorp(deliveryCorpName);
                exchange.setDeliverySn(deliverySn);
                exchange.setMemo(memo);
                exchangeService.doLogistic(exchange.getId(), deliveryCorpName, deliverySn, memberInfo.getLoginCode());
            } else {
                throw new BusinessException("换货订单不存在！");
            }
        } else {
            throw new BusinessException("物流编号不能为空！");
        }
        return result;
    }

    /**
     * 换货确认收货
     *
     * @param exchangeId
     * @return
     */
    @RequestMapping(value = "/exchange/receive/{exchangeId}", method = RequestMethod.POST)
    public ResponseResult receivceExchange(@PathVariable Long exchangeId) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        Exchange exchange = exchangeService.findByIdAndMemberInfoId(exchangeId, memberInfo.getId());
        if (exchange == null) {
            throw new BusinessException("换货单不存在！");
        }
        if (exchange.getExchangeStatus().equals(ExchangeStatus.DELIVERED.getCode())) {
            exchangeTxService.doReceive(exchange.getId(), memberInfo.getLoginCode(), "用户已确认收货");
            result.setMsg("确认收货成功");
            Exchange newExchange = exchangeService.findByIdAndMemberInfoId(exchangeId, memberInfo.getId());
            result.put("exchange", newExchange.toJson());
        } else {
            throw new BusinessException("确认收货不成功，状态不匹配！");
        }
        return result;
    }

}
