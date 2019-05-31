package com.d2c.backend.rest.order;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.ErrorResponse;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.logger.model.OrderLog;
import com.d2c.logger.service.OrderLogService;
import com.d2c.member.dto.AdminDto;
import com.d2c.member.model.Admin;
import com.d2c.member.model.MemberCertification;
import com.d2c.member.service.MemberCertificationService;
import com.d2c.order.dto.OrderDto;
import com.d2c.order.dto.OrderItemDto;
import com.d2c.order.enums.OrderTypeEnum;
import com.d2c.order.enums.PaymentTypeEnum;
import com.d2c.order.model.Order;
import com.d2c.order.model.Order.OrderStatus;
import com.d2c.order.model.OrderItem;
import com.d2c.order.model.OrderItem.ItemStatus;
import com.d2c.order.query.OrderSearcher;
import com.d2c.order.service.OrderItemService;
import com.d2c.order.service.OrderQueryService;
import com.d2c.order.service.OrderService;
import com.d2c.order.service.tx.OrderTxService;
import com.d2c.order.service.tx.PaymentTxService;
import com.d2c.order.third.caomei.CaomeiClient;
import com.d2c.order.third.kaola.KaolaClient;
import com.d2c.order.third.kaola.model.UserInfo;
import com.d2c.order.third.payment.alipay.core.app.AlipayConfig;
import com.d2c.product.model.Product.ProductSource;
import com.d2c.util.date.DateUtil;
import com.d2c.util.string.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping("/rest/order/order")
public class OrderCtrl extends BaseCtrl<OrderSearcher> {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private OrderLogService orderLogService;
    @Autowired
    private OrderQueryService orderQueryService;
    @Reference
    private OrderTxService orderTxService;
    @Reference
    private PaymentTxService paymentTxService;
    @Autowired
    private MemberCertificationService memberCertificationService;

    @Override
    protected List<Map<String, Object>> getRow(OrderSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected int count(OrderSearcher searcher) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    protected String getFileName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected String[] getExportTitles() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doHelp(OrderSearcher searcher, PageModel page) {
        return orderList(null, page, searcher);
    }

    @Override
    protected Response doList(OrderSearcher searcher, PageModel page) {
        return orderList(null, page, searcher);
    }

    @Override
    protected Response findById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doBatchDelete(Long[] id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doDelete(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doInsert(JSONObject data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected String getExportFileType() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * 按订单状态和搜索条件获取订单
     *
     * @param orderStatus
     * @param page
     * @param query
     * @return
     */
    @RequestMapping(value = "/list/{orderStatus}", method = RequestMethod.POST)
    public Response orderList(@PathVariable String orderStatus, PageModel page, OrderSearcher query) {
        BeanUt.trimString(query);
        query.analyseOrderStatus();
        query.setSplit(false);
        if (StringUtils.isNotBlank(orderStatus)) {
            String[] statuss = orderStatus.split(",");
            OrderStatus[] orderStatuss = new OrderStatus[statuss.length];
            for (int i = 0; i < statuss.length; i++) {
                orderStatuss[i] = OrderStatus.valueOf(orderStatus);
            }
            query.setOrderStatus(orderStatuss);
        }
        // 店主零售
        AdminDto admin = this.getLoginedAdmin();
        if (admin.getStoreId() != null) {
            query.setMemberId(admin.getMemberId());
        }
        PageResult<OrderDto> pager = orderQueryService.findBySearcher(query, page);
        SuccessResponse result = new SuccessResponse(pager);
        result.put("orderTotalAmount", orderQueryService.countOrdersAmount(query));
        return result;
    }

    /**
     * 按订单状态和搜索条件获取订单
     *
     * @return
     */
    @RequestMapping(value = "/group/count", method = RequestMethod.POST)
    public Response orderCount() {
        SuccessResponse result = new SuccessResponse();
        // 统计各种状态订单的数量
        Map<String, Object> map = orderQueryService.getCountsMap();
        result.setDatas(map);
        return result;
    }

    /**
     * 更改预计发货时间
     *
     * @param id
     * @param estimateDate
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/updateEstimateDate", method = RequestMethod.POST)
    public Response updateEstimateDate(Long id, Date estimateDate) throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        Date now = new Date();
        if (estimateDate != null
                && DateUtil.getStartOfDay(estimateDate).getTime() <= DateUtil.getStartOfDay(now).getTime()) {
            result.setStatus(-1);
            result.setMessage("预计发货时间不能设置今天或更早");
            return result;
        }
        OrderItem orderItem = orderItemService.findById(id);
        if (orderItem == null || !ItemStatus.NORMAL.name().equals(orderItem.getStatus())) {
            result.setStatus(-1);
            result.setMessage("明细状态不匹配，不允许更改预计发货时间");
            return result;
        }
        Order order = orderService.findById(orderItem.getOrderId());
        if (order == null || order.getOrderStatus() < 2 || order.getOrderStatus() > 3) {
            result.setStatus(-1);
            result.setMessage("订单状态不匹配，不允许更改预计发货时间");
            return result;
        }
        try {
            orderItemService.updateEstimateDate(id, estimateDate, admin.getUsername());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        result.setMessage("更新成功");
        return result;
    }

    /**
     * 关闭订单
     *
     * @param id
     * @param closeReason
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/close/{id}", method = RequestMethod.POST)
    public Response close(@PathVariable Long id, String closeReason) throws NotLoginException {
        SuccessResponse result = closeOrder(id, closeReason);
        result.put("closeReason", closeReason);
        return result;
    }

    /**
     * 批量关闭订单
     *
     * @param ids
     * @param closeReason
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/closeMulti", method = RequestMethod.POST)
    public Response closeMulti(Long[] ids, String closeReason) throws NotLoginException {
        for (Long id : ids) {
            closeOrder(id, closeReason);
        }
        return new SuccessResponse();
    }

    private SuccessResponse closeOrder(Long id, String closeReason) throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        try {
            if (admin != null) {
                int success = orderTxService.doCloseOrder(id, closeReason, admin.getUsername(), OrderStatus.MallClose);
                if (success > 0) {
                    result.setMessage("订单关闭成功");
                } else {
                    result.setStatus(-1);
                    result.setMessage("只有未付款或货到付款未确认的订单可以关闭订单，关闭不成功");
                }
            }
        } catch (Exception e) {
            result.setStatus(-1);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    /**
     * 确认发货
     *
     * @param deliveryCorpName
     * @param deliverySn
     * @param id
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/delivery", method = RequestMethod.POST)
    public Response doDelivery(String deliveryCorpName, String deliverySn, Long id) throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        Order order = orderService.findById(id);
        int success = 0;
        try {
            if (order != null && order.getOrderStatus().equals(OrderStatus.WaitingForDelivery.getCode())) {
                success = orderService.doDeliverOrder(order.getId(), deliveryCorpName, deliverySn, admin.getUsername(),
                        getLoginIp());
            }
            if (success == 1) {
                result.setMessage("发货成功！");
                result.put("id", id);
                result.put("orderStatus", OrderStatus.Delivered.getCode());
            } else {
                result.setMessage("发货不成功！");
                result.setStatus(-1);
            }
        } catch (Exception e) {
            result.setStatus(-1);
            result.setMessage(e.getMessage());
            logger.error(e.getMessage());
        }
        return result;
    }

    /**
     * 修改收货地址
     *
     * @param id
     * @param contact
     * @param province
     * @param city
     * @param district
     * @param address
     * @param reciver
     * @return
     * @throws BusinessException
     * @throws NotLoginException
     */
    @RequestMapping(value = "/updateShippingAddress", method = RequestMethod.POST)
    public Response updateAddress(Long id, String contact, String province, String city, String district,
                                  String address, String reciver) throws BusinessException, NotLoginException {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        if (contact.contains("*")) {
            contact = null;
        }
        if (reciver.contains("*")) {
            reciver = null;
        }
        orderService.updateAddress(id, contact, province, city, district, address, reciver, admin.getUsername());
        result.setMessage("修改收货地址成功");
        return result;
    }

    /**
     * 卖家备注
     *
     * @param id
     * @param adminMemo
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/updateAdminMemo", method = RequestMethod.POST)
    public Response updateAdminMemo(Long id, String adminMemo) throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        orderService.updateAdminMemo(id, adminMemo, admin.getUsername());
        result.setMessage("卖家备注修改成功");
        return result;
    }

    /**
     * 订单需要物流标记
     *
     * @param id
     * @param flag
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/flag/{id}/{flag}", method = RequestMethod.POST)
    public Response updateFlag(@PathVariable("id") Long id, @PathVariable("flag") int flag) throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        this.getLoginedAdmin();
        orderService.updateFlag(id, flag);
        result.setMessage("订单标记修改成功");
        return result;
    }

    /**
     * 订单标记开票信息
     *
     * @param id
     * @param invoiced
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/invoice/{id}/{invoiced}", method = RequestMethod.POST)
    public Response updateInvoiced(@PathVariable("id") Long id, @PathVariable("invoiced") int invoiced)
            throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        int success = orderService.updateInvoiced(id, invoiced, admin.getUsername());
        if (success > 0) {
            result.setMessage("开票成功");
        } else {
            result.setMessage("开票不成功");
        }
        return result;
    }

    /**
     * 预计发货不足三日的订单
     *
     * @param query
     * @param page
     * @return
     */
    @RequestMapping(value = "/warning/lacking", method = RequestMethod.POST)
    public Response warningLacking(OrderSearcher query, PageModel page) {
        query.setWarningTime(3L * 24 * 60 * 60);
        return orderList(null, page, query);
    }

    /**
     * 订单有货未发的订单
     *
     * @param query
     * @param page
     * @return
     */
    @RequestMapping(value = "/warning/inStock", method = RequestMethod.POST)
    public Response warningInStock(OrderSearcher query, PageModel page) {
        query.setMoreStock(0);
        return orderList(null, page, query);
    }

    /**
     * 订单操作日志列表
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/log/{id}", method = RequestMethod.GET)
    public Response logList(@PathVariable Long id) {
        List<OrderLog> logList = orderLogService.findByOrderId(id);
        return new SuccessResponse(logList);
    }

    /**
     * 支付回调
     *
     * @param orderSn
     * @param payType
     * @param totalFee
     * @param tradeNo
     * @param buyerEmail
     * @return
     */
    @RequestMapping(value = "/pay/order", method = RequestMethod.POST)
    public Response payOrder(String orderSn, Integer payType, BigDecimal totalFee, String tradeNo, String buyerEmail) {
        paymentTxService.doPaySuccess(OrderTypeEnum.ORDER.name(), orderSn, totalFee, tradeNo, buyerEmail,
                PaymentTypeEnum.getByCode(payType), null, null, AlipayConfig.partner2, null);
        return new SuccessResponse();
    }

    /**
     * 考拉订单重推
     *
     * @param orderSn
     * @return
     */
    @RequestMapping(value = "/push/kaola", method = RequestMethod.POST)
    public Response pushKaola(String orderSn) {
        Order old = orderService.findByOrderSn(orderSn);
        OrderDto orderDto = orderService.findOrderDtoById(old.getId());
        orderDto.setOrderSn(orderDto.getOrderSn() + "!");
        MemberCertification memberCertification = memberCertificationService.findDefaultOne(orderDto.getMemberId());
        UserInfo userInfo = new UserInfo(orderDto.getLoginCode(), memberCertification.getRealName(),
                orderDto.getLoginCode(), orderDto.getAddress(), orderDto.getProvince(), orderDto.getCity(),
                orderDto.getDistrict(), memberCertification.getIdentityCard());
        String domain = "https://img.d2c.cn";
        if (StringUtil.isNotBlank(memberCertification.getFrontPic())) {
            userInfo.setIdentityPicFront(domain + memberCertification.getFrontPic());
        }
        if (StringUtil.isNotBlank(memberCertification.getBehindPic())) {
            userInfo.setIdentityPicBack(domain + memberCertification.getBehindPic());
        }
        Map<Long, List<com.d2c.order.third.kaola.model.OrderItem>> pushOrders = new HashMap<>();
        for (OrderItemDto dto : orderDto.getOrderItems()) {
            if (dto.getProductSource() != null && dto.getProductSource().equals(ProductSource.KAOLA.name())
                    && dto.getStatus().equals(ItemStatus.NORMAL.name())) {
                com.d2c.order.third.kaola.model.OrderItem item = new com.d2c.order.third.kaola.model.OrderItem(
                        dto.getProductSn(), dto.getProductSkuSn(), dto.getProductQuantity(), dto.getProductPrice(),
                        dto.getWarehouseId().toString());
                if (pushOrders.get(dto.getWarehouseId()) == null) {
                    pushOrders.put(dto.getWarehouseId(), new ArrayList<com.d2c.order.third.kaola.model.OrderItem>());
                }
                pushOrders.get(dto.getWarehouseId()).add(item);
            }
        }
        for (Map.Entry<Long, List<com.d2c.order.third.kaola.model.OrderItem>> entry : pushOrders.entrySet()) {
            try {
                if (pushOrders.entrySet().size() > 0) {
                    KaolaClient.getInstance().bookOrder(orderDto.getOrderSn() + "-" + entry.getKey(), userInfo,
                            entry.getValue());
                    KaolaClient.getInstance().payOrder(orderDto.getOrderSn() + "-" + entry.getKey());
                }
            } catch (Exception e) {
                throw new BusinessException(e.getMessage());
            }
        }
        return new SuccessResponse();
    }

    /**
     * 草莓订单重推
     *
     * @param orderSn
     * @return
     */
    @RequestMapping(value = "/push/caomei", method = RequestMethod.POST)
    public Response pushCaomei(String orderSn) {
        Order order = orderService.findByOrderSn(orderSn);
        OrderDto orderDto = orderService.findOrderDtoById(order.getId());
        List<OrderItem> items = new ArrayList<>();
        for (OrderItem oi : orderDto.getOrderItems()) {
            if (oi.getProductSource() != null && oi.getProductSource().equals(ProductSource.CAOMEI.name())) {
                items.add(oi);
            }
        }
        if (items.size() > 0) {
            MemberCertification memberCertification = memberCertificationService.findByName(order.getReciver(),
                    order.getMemberId());
            if (memberCertification != null) {
                String idno = memberCertification.getIdentityCard();
                try {
                    CaomeiClient.getInstance().createOrder(order, items, idno);
                } catch (Exception e) {
                    return new ErrorResponse(e.getMessage());
                }
            }
        }
        return new SuccessResponse();
    }

}
