package com.d2c.flame.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.member.model.MemberInfo;
import com.d2c.order.dto.OrderDto;
import com.d2c.order.model.AuctionMargin;
import com.d2c.order.model.Order;
import com.d2c.order.model.Order.OrderStatus;
import com.d2c.order.model.OrderItem;
import com.d2c.order.model.OrderItem.ItemStatus;
import com.d2c.order.model.Setting;
import com.d2c.order.query.OrderSearcher;
import com.d2c.order.service.*;
import com.d2c.order.service.tx.OrderTxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@Controller
@RequestMapping("/member")
public class MyOrderController extends BaseController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private OrderQueryService orderQueryService;
    @Autowired
    private AuctionMarginService auctionMarginService;
    @Autowired
    private SettingService settingService;
    @Reference
    private OrderTxService orderTxService;

    /**
     * 我的订单列表
     *
     * @param page
     * @param model
     * @param searcher
     * @return
     */
    @RequestMapping(value = "/order", method = RequestMethod.GET)
    public String orderList(PageModel page, ModelMap model, OrderSearcher searcher) {
        MemberInfo memberInfo = this.getLoginMemberInfo();
        searcher.setEndDate(searcher.getEndDate());
        searcher.setMemberId(memberInfo.getId());
        searcher.setDeleted(0);
        searcher.setSplit(false);
        searcher.setOrderType(Order.OrderType.values());
        PageResult<OrderDto> pager = orderQueryService.findMyOrder(memberInfo.getId(), searcher.getOrderSn(),
                searcher.getProductName(), searcher.getStartDate(), searcher.getEndDate(), searcher.getOrderStatus(),
                searcher.getItemStatus(), searcher.getDesignerName(), searcher.getCommented(), page);
        Map<String, Object> statusMap = orderQueryService.countGropByStatus(memberInfo.getId());
        model.put("WaitingForPay", statusMap.get("WaitingForPay"));
        model.put("WaitingForDelivery", Integer.parseInt(statusMap.get("WaitingForConfirmation").toString())
                + Integer.parseInt(statusMap.get("WaitingForDelivery").toString()));
        model.put("Delivered", statusMap.get("Delivered"));
        model.put("Success", statusMap.get("Success"));
        model.put("pager", pager);
        model.put("searcher", searcher);
        return "member/my_order";
    }

    /**
     * 订单详情页
     *
     * @param model
     * @param orderSn
     * @return
     */
    @RequestMapping(value = "/order/{orderSn}", method = RequestMethod.GET)
    public String orderDetail(ModelMap model, @PathVariable String orderSn) {
        MemberInfo memberInfo = this.getLoginMemberInfo();
        OrderDto order = orderService.doFindByOrderSnAndMemberInfoId(orderSn, memberInfo.getId());
        if (order == null) {
            throw new BusinessException("亲，您的订单信息不见了，请刷新后再试");
        }
        model.put("order", order);
        return "order/order_detail";
    }

    /**
     * 买家确认收货
     *
     * @param model
     * @param orderItemId
     * @return
     */
    @RequestMapping(value = "/orderItem/confirm/{orderItemId}", method = RequestMethod.POST)
    public String confirmOrderItem(ModelMap model, @PathVariable Long orderItemId) {
        SuccessResponse result = new SuccessResponse();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        OrderItem orderItem = orderItemService.findByIdAndMemberInfoId(orderItemId, memberInfo.getId());
        if (orderItem == null) {
            throw new BusinessException("订单明细不存在，确认收货不成功！");
        }
        if (!ItemStatus.DELIVERED.toString().equals(orderItem.getStatus())) {
            throw new BusinessException("订单还未发货，确认收货不成功！");
        }
        Setting setting = settingService.findByCode(Setting.ORDERAFTERCLOSE);
        int success = orderItemService.doSign(orderItemId, memberInfo.getLoginCode(), "确认收货",
                Integer.parseInt(Setting.defaultValue(setting, new Integer(7)).toString()));
        if (success > 0) {
            result.setMsg("确认收货成功！");
        } else {
            throw new BusinessException("确认收货不成功！");
        }
        model.put("result", result);
        return "";
    }

    /**
     * 取消订单
     *
     * @param model
     * @param orderSn
     * @param closeReason
     * @return
     */
    @RequestMapping(value = "/order/cancel/{orderSn}", method = RequestMethod.POST)
    public String cancelOrder(ModelMap model, @PathVariable String orderSn, String closeReason) {
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        MemberInfo memberInfo = this.getLoginMemberInfo();
        OrderDto order = orderService.doFindByOrderSnAndMemberInfoId(orderSn, memberInfo.getId());
        if (order == null) {
            throw new BusinessException("订单不存在，取消不成功！");
        }
        int success = 0;
        order.setLastModifyMan(memberInfo.getId() + ":" + memberInfo.getLoginCode());
        order.setCloseReason(closeReason);
        if (order.getOrderStatus() == 0 || order.getOrderStatus() == 1) {
            success = orderTxService.doCloseOrder(order.getId(), closeReason, memberInfo.getLoginCode(),
                    OrderStatus.UserClose);
            result.setMsg("订单取消成功！");
        } else if ((order.getPaymentType() == 3 || order.getPaymentType() == 18) && order.getOrderStatus() == 2) {
            success = orderTxService.doCloseOrder(order.getId(), closeReason, memberInfo.getLoginCode(),
                    OrderStatus.UserClose);
            result.setMsg("订单取消成功！");
        }
        if (success <= 0) {
            result.setMsg("订单取消不成功，请申请退款操作！");
        }
        return "";
    }

    /**
     * 删除订单
     *
     * @param model
     * @param orderSn
     * @return
     */
    @RequestMapping(value = "/order/delete/{orderSn}", method = RequestMethod.POST)
    public String deleteOrder(ModelMap model, @PathVariable String orderSn) {
        SuccessResponse result = new SuccessResponse();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        OrderDto order = orderService.doFindByOrderSnAndMemberInfoId(orderSn, memberInfo.getId());
        if (order == null) {
            throw new BusinessException("订单不存在， 删除不成功！");
        }
        order.setLastModifyMan(memberInfo.getId() + ":" + memberInfo.getLoginCode());
        int success = orderService.delete(order.getId(), memberInfo.getLoginCode());
        model.put("result", result);
        if (success > 0) {
            result.setMsg("删除成功！");
        } else {
            throw new BusinessException("删除不成功！");
        }
        return "";
    }

    /**
     * 订单状态查询
     *
     * @param model
     * @param orderSn
     * @return
     */
    @RequestMapping(value = "/orderstatus/{orderSn}", method = RequestMethod.GET)
    public String orderStatus(ModelMap model, @PathVariable String orderSn) {
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        if (orderSn != null && orderSn.indexOf("AM") != -1) {
            AuctionMargin auctionMargin = auctionMarginService.findByMarginSn(orderSn.split("-")[0]);
            if (auctionMargin == null) {
                throw new BusinessException("订单不存在！");
            }
            if (auctionMargin.getStatus() == 1 || auctionMargin.getStatus() == 6) {
                result.setStatus(3);
                result.setMsg("订单已支付！");
                return "";
            }
            if (auctionMargin.getStatus() < 0) {
                throw new BusinessException("订单状态异常！");
            }
            if (auctionMargin.getStatus() == 0 || auctionMargin.getStatus() == 2) {
                throw new BusinessException("订单未支付！");
            }
        } else {
            Order order = orderService.findByOrderSn(orderSn);
            if (order == null) {
                throw new BusinessException("订单不存在！");
            }
            if (order.getOrderStatus() < 0) {
                throw new BusinessException("订单已关闭！");
            }
            if (order.getOrderStatus() < 2) {
                throw new BusinessException("订单未支付！");
            }
        }
        result.setMsg("订单已支付！");
        return "";
    }

}
