package com.d2c.flame.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.logger.model.ExchangeLog;
import com.d2c.logger.model.RefundLog;
import com.d2c.logger.model.ReshipLog;
import com.d2c.logger.service.ExchangeLogService;
import com.d2c.logger.service.RefundLogService;
import com.d2c.logger.service.ReshipLogService;
import com.d2c.member.enums.DeviceTypeEnum;
import com.d2c.member.model.Distributor;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.service.DistributorService;
import com.d2c.order.dto.ExchangeDto;
import com.d2c.order.dto.OrderItemDto;
import com.d2c.order.dto.ReshipDto;
import com.d2c.order.model.*;
import com.d2c.order.model.Exchange.ExchangeStatus;
import com.d2c.order.model.Order.OrderType;
import com.d2c.order.model.OrderItem.ItemStatus;
import com.d2c.order.model.Refund.RefundStatus;
import com.d2c.order.model.Reship.ReshipStatus;
import com.d2c.order.query.ExchangeSearcher;
import com.d2c.order.query.RefundSearcher;
import com.d2c.order.query.ReshipSearcher;
import com.d2c.order.service.*;
import com.d2c.order.service.tx.ExchangeTxService;
import com.d2c.product.model.Product.ProductSource;
import com.d2c.product.model.ProductSku;
import com.d2c.product.service.ProductSkuService;
import com.d2c.util.date.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.*;

@Controller
@RequestMapping("/member")
public class MyAfterSaleController extends BaseController {

    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private RefundService refundService;
    @Autowired
    private ReshipService reshipService;
    @Autowired
    private ExchangeService exchangeService;
    @Autowired
    private DistributorService distributorService;
    @Autowired
    private ProductSkuService productSkuService;
    @Autowired
    private RefundLogService refundLogService;
    @Autowired
    private ReshipLogService reshipLogService;
    @Autowired
    private ExchangeLogService exchangeLogService;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private SettingService settingService;
    @Reference
    private ExchangeTxService exchangeTxService;

    /**
     * 是否已售后
     *
     * @param orderItem
     * @return
     */
    private String getRedirectAfterUrl(OrderItemDto orderItem) {
        /**
         * 已经存在退货申请，直接返回退货
         */
        if (orderItem.getReship() != null && orderItem.getReship().getReshipStatus() >= 0) {
            return "redirect:/member/reship/" + orderItem.getReshipId();
        }
        /**
         * 已经存在退款申请，直接返回退款
         */
        if (orderItem.getRefund() != null && orderItem.getRefund().getRefundStatus() >= 0) {
            return "redirect:/member/refund/" + orderItem.getRefundId();
        }
        /**
         * 已经存在换货申请，直接返回换货
         */
        if (orderItem.getExchange() != null && orderItem.getExchange().getExchangeStatus() >= 0) {
            return "redirect:/member/exchange/" + orderItem.getExchangeId();
        }
        return null;
    }

    /**
     * 我的售后单
     *
     * @param model
     * @param orderItemId
     * @return
     */
    @RequestMapping(value = "/after/{orderItemId}", method = RequestMethod.GET)
    public String after(ModelMap model, @PathVariable Long orderItemId) {
        this.getLoginMemberInfo();
        OrderItem item = orderItemService.findById(orderItemId);
        OrderItemDto dto = new OrderItemDto();
        BeanUtils.copyProperties(item, dto);
        if (item != null) {
            Order order = orderService.findById(item.getOrderId());
            model.put("order", order);
            model.put("orderItem", dto);
            return "after/orderitem_after";
        } else {
            throw new BusinessException("订单明细不存在");
        }
    }

    /**
     * 我的退款单
     *
     * @param model
     * @param page
     * @param searcher
     * @return
     */
    @RequestMapping(value = "/refund/list", method = RequestMethod.GET)
    public String listRefunds(ModelMap model, PageModel page, RefundSearcher searcher) {
        MemberInfo memberInfo = this.getLoginMemberInfo();
        searcher.setMemberId(memberInfo.getId());
        if (searcher.getEndCreateDate() != null) {
            searcher.setEndCreateDate(DateUtil.add(searcher.getEndCreateDate(), Calendar.DAY_OF_YEAR, 1));
        }
        PageResult<Refund> pager = refundService.findMine(searcher, page);
        Map<String, Object> map = refundService.countByStatusAndMemberId(memberInfo.getId());
        model.put("totalCount", map.get("totalCount"));
        model.put("processCount", map.get("processCount"));
        model.put("pager", pager);
        model.put("searcher", searcher);
        return "after/refund_list";
    }

    /**
     * 申请退款
     *
     * @param orderItemId
     * @param model
     * @return
     */
    @RequestMapping(value = "/refund/create/{orderItemId}", method = RequestMethod.GET)
    public String editRefund(@PathVariable Long orderItemId, ModelMap model) {
        MemberInfo memberInfo = this.getLoginMemberInfo();
        OrderItemDto orderItem = orderItemService.findOrderItemDtoById(orderItemId);
        // 如果当前订单不包含提交的SKU号
        if (orderItem == null) {
            throw new BusinessException("订单不存在，申请退款不成功");
        } else {
            String redirectUrl = this.getRedirectAfterUrl(orderItem);
            if (StringUtils.isNotBlank(redirectUrl)) {
                return redirectUrl;
            }
        }
        Order order = orderService.findById(orderItem.getOrderId());
        if (order.getOrderStatus() < 2) {
            throw new BusinessException("没有付款，申请退款不成功");
        }
        if (order.getType().equals(OrderType.distribution.name())) {
            // 分销订单，判断经销商是否启用:未启用则可以退，启用则不可退
            Distributor distributor = distributorService.findEnableByMemberInfoId(memberInfo.getId());
            if (distributor != null && distributor.getReship() == 0) {
                throw new BusinessException("分销订单，不允许退款，退款不成功");
            }
        }
        Refund refund = new Refund();
        refund.createRefund(order, orderItem);
        model.put("refund", refund);
        model.put("orderItem", orderItem);
        model.put("status", 1);
        return "after/refund";
    }

    /**
     * 退款单处理
     *
     * @param model
     * @param refund
     * @param path
     * @return
     */
    @RequestMapping(value = "/refund", method = RequestMethod.POST)
    public String doRefund(ModelMap model, Refund refund, String[] path) {
        SuccessResponse result = new SuccessResponse();
        result.setMsg("退款单提交成功");
        model.put("result", result);
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
        // 已经存在售后申请
        String redirectUrl = this.getRedirectAfterUrl(orderItem);
        if (StringUtils.isNotBlank(redirectUrl)) {
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
            // 对于在已付款状态或者已发货但是只退款的订单，生成退款单
            if (order.getOrderStatus() >= 2) {
                refund.createRefund(order, orderItem);
                refund.setDevice(isMobileDevice() ? DeviceTypeEnum.MOBILE.toString() : DeviceTypeEnum.PC.toString());
                refund.setAppVersion(VERSION);
                refund.setEvidences(StringUtils.join(path, ","));
                refund.setAllRefund(1);
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
        return "";
    }

    /**
     * 退款处理
     *
     * @param refundId
     * @param model
     * @return
     */
    @RequestMapping(value = "/refund/{refundId}", method = RequestMethod.GET)
    public String awaitRefund(@PathVariable Long refundId, ModelMap model) {
        MemberInfo memberInfo = this.getLoginMemberInfo();
        Refund refund = refundService.findByIdAndMemberInfoId(refundId, memberInfo.getId());
        if (refund.getReshipId() != null) {
            List<ReshipLog> reshipLogs = reshipLogService.findByReshipId(refund.getReshipId());
            model.put("reshipLogs", reshipLogs);
        }
        List<RefundLog> refundLogs = refundLogService.findByRefundId(refund.getId());
        if (refund.getReshipId() != null) {
            Reship reship = this.reshipService.findById(refund.getReshipId());
            model.put("reship", reship);
        }
        model.put("refund", refund);
        model.put("refundLogs", refundLogs);
        return "after/refund_success";
    }

    /**
     * 用户取消退款
     *
     * @param model
     * @param refundId
     * @return
     */
    @RequestMapping(value = "/refund/cancel/{refundId}", method = RequestMethod.POST)
    public String cancelRefund(ModelMap model, @PathVariable Long refundId) {
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        MemberInfo memberInfo = this.getLoginMemberInfo();
        Refund refund = refundService.findByIdAndMemberInfoId(refundId, memberInfo.getId());
        if (refund == null) {
            throw new BusinessException("退款单不存在");
        }
        if (refund.getRefundStatus().equals(RefundStatus.APPLY.getCode())) {
            try {
                if (refund.getReshipId() != null) {
                    reshipService.doCancel(refund.getReshipId(), memberInfo.getLoginCode());
                } else {
                    refundService.doCancel(refund.getId(), memberInfo.getLoginCode());
                }
                result.setMsg("取消退款成功");
            } catch (Exception e) {
                throw new BusinessException(e.getMessage());
            }
        } else {
            throw new BusinessException("取消退款不成功，已经退款处理中，请联系客服取消");
        }
        return "";
    }

    /**
     * 我的退货单
     *
     * @param model
     * @param page
     * @param searcher
     * @return
     */
    @RequestMapping(value = "/reship/list", method = RequestMethod.GET)
    public String listReships(ModelMap model, PageModel page, ReshipSearcher searcher) {
        MemberInfo memberInfo = this.getLoginMemberInfo();
        searcher.setMemberId(memberInfo.getId());
        if (searcher.getEndCreateDate() != null) {
            searcher.setEndCreateDate(DateUtil.add(searcher.getEndCreateDate(), Calendar.DAY_OF_YEAR, 1));
        }
        PageResult<ReshipDto> pager = reshipService.findMine(searcher, page);
        Map<String, Object> map = reshipService.countByStatusAndMemberId(memberInfo.getId());
        model.put("totalCount", map.get("totalCount"));
        model.put("processCount", map.get("processCount"));
        model.put("pager", pager);
        model.put("searcher", searcher);
        return "after/reship_list";
    }

    /**
     * 申请退货 1、已经发货或者已经签收
     *
     * @param orderItemId
     * @param model
     * @return
     */
    @RequestMapping(value = "/reship/create/{orderItemId}", method = RequestMethod.GET)
    public String editReship(@PathVariable Long orderItemId, ModelMap model) {
        MemberInfo memberInfo = this.getLoginMemberInfo();
        OrderItemDto orderItem = orderItemService.findOrderItemDtoById(orderItemId);
        // 如果当前订单不包含提交的SKU号
        if (orderItem == null) {
            return "error/503";
        } else {
            String redirectUrl = this.getRedirectAfterUrl(orderItem);
            if (StringUtils.isNotBlank(redirectUrl)) {
                return redirectUrl;
            }
        }
        Order order = this.orderService.findById(orderItem.getOrderId());
        if (order.getType().equals(OrderType.distribution.name())) {
            // 分销订单，判断经销商是否启用:未启用则可以退，启用则不可退
            Distributor distributor = distributorService.findEnableByMemberInfoId(memberInfo.getId());
            if (distributor != null && distributor.getReship() == 0) {
                return "error/503";
            }
        }
        Reship reship = new Reship();
        reship.createReship(order, orderItem);
        model.put("orderItem", orderItem);
        model.put("reship", reship);
        model.put("status", getReshipStatus(orderItem));
        return "after/reship";
    }

    /**
     * -1 退货日期已经过去了 ; -2 已经完成的订单，不允许退款
     *
     * @param item
     * @return
     */
    private int getReshipStatus(OrderItem item) {
        int status = -1;
        if ((item.getStatus().equals(ItemStatus.DELIVERED.name())
                || item.getStatus().equals(ItemStatus.SIGNED.name()))) {
            status = 1;
        }
        return status;
    }

    /**
     * 退货退款单处理
     *
     * @param model
     * @param reship
     * @param orderSn
     * @param path
     * @return
     */
    @RequestMapping(value = "/reship", method = RequestMethod.POST)
    public String doReship(ModelMap model, Reship reship, String orderSn, String[] path) {
        SuccessResponse result = new SuccessResponse();
        result.setMsg("退货退款单提交成功");
        model.put("result", result);
        this.getLoginMemberInfo();
        OrderItemDto orderItem = orderItemService.findOrderItemDtoById(reship.getOrderItemId());
        if (orderItem == null) {
            throw new BusinessException("订单明细不存在，请联系D2C客服！");
        }
        if (orderItem.getProductSource() != null && orderItem.getProductSource().equals(ProductSource.KAOLA.name())) {
            throw new BusinessException("申请售后请联系客服！");
        }
        if (orderItem.getAfter() == 0) {
            throw new BusinessException("该订单不支持申请售后！");
        }
        // 已经存在售后申请
        String redirectUrl = this.getRedirectAfterUrl(orderItem);
        if (StringUtils.isNotBlank(redirectUrl)) {
            throw new BusinessException("已经存在售后申请，不可重复提交！");
        }
        // 普通订单
        Order order = orderService.findById(orderItem.getOrderId());
        if (order.getType().equals(Order.OrderType.distribution.toString()) && orderItem.getDplusId() == null) {
            throw new BusinessException("非店主的经销商订单不允许申请售后！");
        }
        try {
            // 对于在已发货状态且发货至今未超过一周的订单，生成退货单
            if (ItemStatus.DELIVERED.name().equals(orderItem.getStatus())
                    || ItemStatus.SIGNED.name().equals(orderItem.getStatus())) {
                Date date = orderItem.getSignDate();
                if (date != null) {
                    date = DateUtil.add(date, Calendar.DAY_OF_YEAR, -10);
                } else {
                    date = orderItem.getDeliveryTime();
                    date = DateUtil.add(date, Calendar.DAY_OF_YEAR, -15);
                }
                if (new Date().after(date)) {
                    ProductSku sku = productSkuService.findById(orderItem.getProductSkuId());
                    if (sku != null) {
                        if (orderItem.getRefund() != null && orderItem.getRefund().getRefundStatus() > 0) {
                            return "redirect:/member/refund/" + orderItem.getRefund().getRefundSn();
                        }
                        if (orderItem.getReship() != null && orderItem.getReship().getReshipStatus() > 0) {
                            return "redirect:/member/reship/" + orderItem.getReship().getReshipSn();
                        }
                        reship.createReship(order, orderItem);
                        reship.setDevice(isMobileDevice() ? DeviceTypeEnum.MOBILE.name() : DeviceTypeEnum.PC.name());
                        reship.setAppVersion(VERSION);
                        reship.setEvidences(StringUtils.join(path, ","));
                        setBackInfo(reship, orderItem.getDeliveryType(), orderItem.getDesignerId(),
                                ProductSource.KAOLA.name().equals(orderItem.getProductSource()));
                        reshipService.insert(reship);
                    }
                }
            } else {
                throw new BusinessException("订单状态不匹配，提交不成功！");
            }
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        return "";
    }

    /**
     * 退货处理
     *
     * @param reshipId
     * @param model
     * @return
     */
    @RequestMapping(value = "/reship/{reshipId}", method = RequestMethod.GET)
    public String awaitReship(@PathVariable Long reshipId, ModelMap model) {
        MemberInfo memberInfo = this.getLoginMemberInfo();
        Reship reship = reshipService.findByIdAndMemberInfoId(reshipId, memberInfo.getId());
        List<ReshipLog> reshipLogs = reshipLogService.findByReshipId(reship.getId());
        List<RefundLog> refundLogs = refundLogService.findByRefundId(reship.getRefundId());
        List<Map<String, Object>> list = new ArrayList<>();
        for (ReshipLog log : reshipLogs) {
            Map<String, Object> map = new HashMap<>();
            map.put("createDate", log.getCreateDate());
            map.put("info", "退货：" + log.getInfo());
            map.put("creator", log.getCreator());
            list.add(map);
        }
        for (RefundLog log : refundLogs) {
            Map<String, Object> map = new HashMap<>();
            map.put("createDate", log.getCreateDate());
            map.put("info", "退款：" + log.getInfo());
            map.put("creator", log.getCreator());
            list.add(map);
        }
        Collections.sort(list, new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                Date date1 = (Date) o1.get("createDate");
                Date date2 = (Date) o2.get("createDate");
                return date2.compareTo(date1);
            }
        });
        model.put("reship", reship);
        model.put("reshipLogs", list);
        return "after/reship_success";
    }

    /**
     * 退货物流页面
     *
     * @param reshipId
     * @param model
     * @return
     */
    @RequestMapping(value = "/reship/logistic/{reshipId}", method = RequestMethod.GET)
    public String logisticReship(@PathVariable Long reshipId, ModelMap model) {
        MemberInfo memberInfo = this.getLoginMemberInfo();
        Reship reship = reshipService.findByIdAndMemberInfoId(reshipId, memberInfo.getId());
        List<ReshipLog> reshipLogs = reshipLogService.findByReshipId(reship.getId());
        if (StringUtils.isBlank(reship.getBackAddress()) || StringUtils.isBlank(reship.getBackMobile())
                || StringUtils.isBlank(reship.getBackConsignee())) {
            OrderItem orderItem = orderItemService.findById(reship.getOrderItemId());
            Setting setting = null;
            if (orderItem.getProductSource() != null
                    && orderItem.getProductSource().equals(ProductSource.KAOLA.name())) {
                setting = settingService.findByCode(Setting.KAOLABACKINFOCODE);
            } else {
                setting = settingService.findByCode(Setting.BACKINFOCODE);
            }
            JSONObject json = JSONObject.parseObject(Setting.defaultValue(setting, "{}").toString());
            reship.setBackAddress(json.getString("backAddress"));
            reship.setBackConsignee(json.getString("backConsignee"));
            reship.setBackMobile(json.getString("backMobile"));
        }
        model.put("reship", reship);
        model.put("reshipLogs", reshipLogs);
        return "after/reship_logistics";
    }

    /**
     * 退货物流信息
     *
     * @param shipId
     * @param deliveryCorpName
     * @param deliverySn
     * @param memo
     * @param model
     * @return
     */
    @RequestMapping(value = "/reship/logistic", method = RequestMethod.POST)
    public String logisticReship(Long shipId, String deliveryCorpName, String deliverySn, String memo, ModelMap model) {
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        MemberInfo memberInfo = this.getLoginMemberInfo();
        result.setMsg("物流信息填写成功");
        if (StringUtils.isNotBlank(deliverySn)) {
            Reship reship = this.reshipService.findByIdAndMemberInfoId(shipId, memberInfo.getId());
            if (reship != null) {
                reship.setDeliveryCorpName(deliveryCorpName);
                reship.setDeliverySn(deliverySn);
                reship.setMemo(memo);
                try {
                    reshipService.doLogistic(reship.getId(), deliverySn, deliveryCorpName, memo,
                            memberInfo.getLoginCode());
                } catch (Exception e) {
                    throw new BusinessException(e.getMessage());
                }
            }
        } else {
            throw new BusinessException("物流编号不能为空");
        }
        return "";
    }

    /**
     * 用户取消退款退货
     *
     * @param model
     * @param reshipId
     * @return
     */
    @RequestMapping(value = "/reship/cancel/{reshipId}", method = RequestMethod.POST)
    public String cancelReship(ModelMap model, @PathVariable Long reshipId) {
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        MemberInfo memberInfo = this.getLoginMemberInfo();
        Reship reship = reshipService.findByIdAndMemberInfoId(reshipId, memberInfo.getId());
        if (reship == null) {
            throw new BusinessException("退货单不存在");
        }
        if (!(reship.getReshipStatus().equals(ReshipStatus.SUCCESS.getCode()))) {
            try {
                reshipService.doCancel(reship.getId(), memberInfo.getLoginCode());
                result.setMsg("取消退款退货成功");
            } catch (Exception e) {
                throw new BusinessException(e.getMessage());
            }
        } else {
            throw new BusinessException("取消退款退货不成功，款项已退");
        }
        return "";
    }

    /**
     * 我的换货单
     *
     * @param model
     * @param page
     * @param searcher
     * @return
     */
    @RequestMapping(value = "/exchange/list", method = RequestMethod.GET)
    public String listExchanges(ModelMap model, PageModel page, ExchangeSearcher searcher) {
        MemberInfo memberInfo = this.getLoginMemberInfo();
        searcher.setMemberId(memberInfo.getId());
        if (searcher.getEndCreateDate() != null) {
            searcher.setEndCreateDate(DateUtil.add(searcher.getEndCreateDate(), Calendar.DAY_OF_YEAR, 1));
        }
        PageResult<ExchangeDto> pager = exchangeService.findMine(searcher, page);
        Map<String, Object> map = exchangeService.countByStatusAndMemberId(memberInfo.getId());
        model.put("totalCount", map.get("totalCount"));
        model.put("processCount", map.get("processCount"));
        model.put("pager", pager);
        model.put("searcher", searcher);
        return "after/exchange_list";
    }

    /**
     * 申请换货 1、已经发货或者已经签收
     *
     * @param orderItemId
     * @param model
     * @return
     */
    @RequestMapping(value = "/exchange/create/{orderItemId}", method = RequestMethod.GET)
    public String editExchange(@PathVariable Long orderItemId, ModelMap model) {
        MemberInfo memberInfo = this.getLoginMemberInfo();
        OrderItemDto orderItem = orderItemService.findOrderItemDtoById(orderItemId);
        // 如果当前订单明细不存在
        if (orderItem == null) {
            return "error/503";
        } else {
            String redirectUrl = this.getRedirectAfterUrl(orderItem);
            if (StringUtils.isNotBlank(redirectUrl)) {
                return redirectUrl;
            }
        }
        Order order = orderService.findById(orderItem.getOrderId());
        if (order.getType().equals(OrderType.distribution.name())) {
            // 分销订单，判断经销商是否启用:未启用则可以退，启用则不可退
            Distributor distributor = distributorService.findEnableByMemberInfoId(memberInfo.getId());
            if (distributor != null && distributor.getReship() == 0) {
                return "error/503";
            }
        }
        Exchange exchange = new Exchange();
        exchange.createExchange(order, orderItem);
        model.put("orderItem", orderItem);
        model.put("exchange", exchange);
        model.put("status", this.getExchangeStatus(orderItem));
        return "after/exchange";
    }

    /**
     * -1 换货日期已经过去了 ; -2 已经完成的订单，不允许退款
     *
     * @param item
     * @return
     */
    private int getExchangeStatus(OrderItem item) {
        int status = -1;
        if ((item.getStatus().equals(ItemStatus.DELIVERED.name())
                || item.getStatus().equals(ItemStatus.SIGNED.name()))) {
            status = 1;
        }
        return status;
    }

    /**
     * 换货处理
     *
     * @param model
     * @param exchange
     * @param path
     * @return
     */
    @RequestMapping(value = "/exchange", method = RequestMethod.POST)
    public String doExchange(ModelMap model, Exchange exchange, String[] path) {
        SuccessResponse result = new SuccessResponse();
        result.setMsg("换货单提交成功");
        model.put("result", result);
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
        // 已经存在售后申请
        String redirectUrl = this.getRedirectAfterUrl(orderItem);
        if (StringUtils.isNotBlank(redirectUrl)) {
            throw new BusinessException("已经存在售后申请，不可重复提交！");
        }
        Order order = orderService.findById(orderItem.getOrderId());
        if (order.getType().equals(Order.OrderType.distribution.toString()) && orderItem.getDplusId() == null) {
            throw new BusinessException("非店主的经销商订单不允许申请售后！");
        }
        try {
            // 对于在已发货状态且发货至今未超过一周的订单，生成换货单
            if (ItemStatus.DELIVERED.name().equals(orderItem.getStatus())
                    || ItemStatus.SIGNED.name().equals(orderItem.getStatus())) {
                Date date = orderItem.getSignDate();
                if (date != null) {
                    date = DateUtil.add(date, Calendar.DAY_OF_YEAR, -10);
                } else {
                    date = orderItem.getDeliveryTime();
                    date = DateUtil.add(date, Calendar.DAY_OF_YEAR, -15);
                }
                if (new Date().after(date)) {
                    ProductSku sku = productSkuService.findById(orderItem.getProductSkuId());
                    if (sku != null) {
                        exchange.createExchange(order, orderItem);
                        exchange.setDevice(
                                isMobileDevice() ? DeviceTypeEnum.MOBILE.toString() : DeviceTypeEnum.PC.toString());
                        exchange.setAppVersion(VERSION);
                        setBackInfo(exchange, orderItem.getDeliveryType(), orderItem.getDesignerId(),
                                ProductSource.KAOLA.name().equals(orderItem.getProductSource()));
                        exchangeService.insert(exchange, memberInfo.getLoginCode());
                    }
                }
            } else {
                throw new BusinessException("订单状态不匹配，提交不成功！");
            }
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        return "";
    }

    /**
     * 换货处理
     *
     * @param exchangeId
     * @param model
     * @return
     */
    @RequestMapping(value = "/exchange/{exchangeId}", method = RequestMethod.GET)
    public String awaitExchange(@PathVariable Long exchangeId, ModelMap model) {
        MemberInfo memberInfo = this.getLoginMemberInfo();
        Exchange exchange = exchangeService.findByIdAndMemberInfoId(exchangeId, memberInfo.getId());
        List<ExchangeLog> exchangeLogs = exchangeLogService.findByExchangeId(exchangeId);
        model.put("exchange", exchange);
        model.put("exchangeLogs", exchangeLogs);
        return "after/exchange_success";
    }

    /**
     * 换货物流页面
     *
     * @param exchangeId
     * @param model
     * @return
     */
    @RequestMapping(value = "/exchange/logistic/{exchangeId}", method = RequestMethod.GET)
    public String logisticExchange(@PathVariable Long exchangeId, ModelMap model) {
        MemberInfo memberInfo = this.getLoginMemberInfo();
        Exchange exchange = exchangeService.findByIdAndMemberInfoId(exchangeId, memberInfo.getId());
        List<ExchangeLog> exchangeLogs = exchangeLogService.findByExchangeId(exchangeId);
        if (StringUtils.isBlank(exchange.getBackAddress()) || StringUtils.isBlank(exchange.getBackMobile())
                || StringUtils.isBlank(exchange.getBackConsignee())) {
            OrderItem orderItem = orderItemService.findById(exchange.getOrderItemId());
            Setting setting = null;
            if (orderItem.getProductSource() != null
                    && orderItem.getProductSource().equals(ProductSource.KAOLA.name())) {
                setting = settingService.findByCode(Setting.KAOLABACKINFOCODE);
            } else {
                setting = settingService.findByCode(Setting.BACKINFOCODE);
            }
            JSONObject json = JSONObject.parseObject(Setting.defaultValue(setting, "{}").toString());
            exchange.setBackAddress(json.getString("backAddress"));
            exchange.setBackConsignee(json.getString("backConsignee"));
            exchange.setBackMobile(json.getString("backMobile"));
        }
        model.put("exchange", exchange);
        model.put("exchangeLogs", exchangeLogs);
        return "after/exchange_logistics";
    }

    /**
     * 换货物流信息
     *
     * @param exchangeId
     * @param deliveryCorpName
     * @param deliverySn
     * @param memo
     * @param model
     * @return
     */
    @RequestMapping(value = "/exchange/logistic", method = RequestMethod.POST)
    public String logisticExchange(Long exchangeId, String deliveryCorpName, String deliverySn, String memo,
                                   ModelMap model) {
        SuccessResponse result = new SuccessResponse();
        model.clear();
        model.put("result", result);
        MemberInfo memberInfo = this.getLoginMemberInfo();
        result.setMsg("物流信息填写成功");
        if (StringUtils.isNotBlank(deliverySn)) {
            Exchange exchange = exchangeService.findByIdAndMemberInfoId(exchangeId, memberInfo.getId());
            if (exchange != null) {
                exchange.setDeliveryCorp(deliveryCorpName);
                exchange.setDeliverySn(deliverySn);
                exchange.setMemo(memo);
                try {
                    exchangeService.doLogistic(exchange.getId(), deliveryCorpName, deliverySn,
                            memberInfo.getLoginCode());
                } catch (Exception e) {
                    throw new BusinessException(e.getMessage());
                }
            }
        } else {
            throw new BusinessException("物流编号不能为空");
        }
        return "";
    }

    /**
     * 用户取消换货
     *
     * @param model
     * @param exchangeId
     * @return
     */
    @RequestMapping(value = "/exchange/cancel/{exchangeId}", method = RequestMethod.POST)
    public String cancelExchange(ModelMap model, @PathVariable Long exchangeId) {
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        MemberInfo memberInfo = this.getLoginMemberInfo();
        Exchange exchange = exchangeService.findByIdAndMemberInfoId(exchangeId, memberInfo.getId());
        if (exchange == null) {
            throw new BusinessException("换货单不存在");
        }
        if (exchange.getExchangeStatus() >= 0 && exchange.getExchangeStatus() <= 2) {
            try {
                exchangeService.doCancel(exchange.getId(), memberInfo.getLoginCode());
                result.setMsg("取消换货成功");
            } catch (Exception e) {
                throw new BusinessException(e.getMessage());
            }
        } else {
            throw new BusinessException("取消换货不成功，状态不匹配");
        }
        return "";
    }

    /**
     * 用户确认收货
     *
     * @param model
     * @param exchangeId
     * @return
     */
    @RequestMapping(value = "/exchange/receive/{exchangeId}", method = RequestMethod.POST)
    public String receiveExchange(ModelMap model, @PathVariable Long exchangeId) {
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        MemberInfo memberInfo = this.getLoginMemberInfo();
        Exchange exchange = exchangeService.findByIdAndMemberInfoId(exchangeId, memberInfo.getId());
        if (exchange == null) {
            throw new BusinessException("换货单不存在");
        }
        if (exchange.getExchangeStatus().equals(ExchangeStatus.DELIVERED.getCode())) {
            try {
                String str = "用户已确认收货";
                exchangeTxService.doReceive(exchange.getId(), memberInfo.getLoginCode(), str);
                result.setMsg("确认收货成功");
            } catch (Exception e) {
                throw new BusinessException(e.getMessage());
            }
        } else {
            throw new BusinessException("确认收货不成功，状态不匹配");
        }
        return "";
    }

    /**
     * 设置退货地址
     *
     * @param t
     * @param deliveryType
     * @param designerId
     * @return
     */
    private <T> T setBackInfo(T t, Integer deliveryType, Long designerId, boolean isKAOLA) {
        String backAddress = "";
        String backMobile = "";
        String backConsignee = "";
        // if (deliveryType == 1) {
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

}
