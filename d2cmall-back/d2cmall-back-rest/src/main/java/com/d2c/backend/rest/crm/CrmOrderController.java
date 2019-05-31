package com.d2c.backend.rest.crm;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.SuperCtrl;
import com.d2c.backend.rest.crm.oauth.D2cApiOauth;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.order.dto.RefundDto;
import com.d2c.order.model.Logistics;
import com.d2c.order.model.Order;
import com.d2c.order.query.RefundSearcher;
import com.d2c.order.service.LogisticsService;
import com.d2c.order.service.OrderService;
import com.d2c.order.service.RefundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/api/crm/order")
public class CrmOrderController extends SuperCtrl {

    @Autowired
    private OrderService orderService;
    @Autowired
    private LogisticsService logisticsService;
    @Autowired
    private RefundService refundService;

    /**
     * 发送给crm 订单状态信息
     *
     * @throws BusinessException
     */
    @RequestMapping(value = "/query/status", method = RequestMethod.POST)
    public String sendOrderStatus(HttpServletRequest request, HttpServletResponse response) {
        D2cApiOauth d2cApiOauth = D2cApiOauth.getInstance();
        JSONArray jsonArray = d2cApiOauth.getJsonArray(request, "order");
        if (jsonArray == null) {
            return null;
        }
        JSONObject obj = jsonArray.getJSONObject(0);
        String orderSn = obj.getString("sn");
        Order order = orderService.findByOrderSn(orderSn);
        JSONObject result = new JSONObject();
        if (order != null) {
            result.put("orderStatus", order.getOrderStatus());
            result.put("status", 1);
            result.put("message", "查询成功！");
        } else {
            result.put("status", 0);
            result.put("message", "不存在该订单编号！");
        }
        d2cApiOauth.sendJson(response, result);
        return null;
    }

    /**
     * 发送给crm 订单物流信息
     *
     * @throws BusinessException
     */
    @RequestMapping(value = "/query/delivery", method = RequestMethod.POST)
    public String sendOrderDeliveryInfo(HttpServletRequest request, HttpServletResponse response) {
        D2cApiOauth d2cApiOauth = D2cApiOauth.getInstance();
        JSONArray jsonArray = d2cApiOauth.getJsonArray(request, "order");
        if (jsonArray == null) {
            return null;
        }
        JSONObject obj = jsonArray.getJSONObject(0);
        String deliverySn = obj.getString("deliverySn");
        String deliveryName = obj.getString("deliveryName");
        Logistics logistics = logisticsService.findBySnAndCom(deliverySn, deliveryName, null);
        JSONObject result = new JSONObject();
        if (logistics != null) {
            result.put("deliverySn", logistics.getDeliverySn());
            result.put("deliveryName", logistics.getDeliveryCode());
            result.put("deliveryInfo", logistics.getDeliveryInfo());
            result.put("deliveryStatus", logistics.getStatus());
            result.put("status", 1);
            result.put("message", "查询成功！");
        } else {
            result.put("status", 0);
            result.put("message", "不存在该物流编号！");
        }
        d2cApiOauth.sendJson(response, result);
        return null;
    }

    /**
     * 发送给crm 订单状态信息
     *
     * @throws BusinessException
     */
    @RequestMapping(value = "/query/refund", method = RequestMethod.POST)
    public String sendOrderRefundInfo(HttpServletRequest request, HttpServletResponse response) {
        D2cApiOauth d2cApiOauth = D2cApiOauth.getInstance();
        JSONArray jsonArray = d2cApiOauth.getJsonArray(request, "order");
        if (jsonArray == null) {
            return null;
        }
        JSONObject obj = jsonArray.getJSONObject(0);
        String orderSn = obj.getString("sn");
        RefundSearcher refundSearcher = new RefundSearcher();
        refundSearcher.setOrderSn(orderSn);
        PageModel page = new PageModel();
        PageResult<RefundDto> refundList = refundService.findBySearch(refundSearcher, page);
        JSONObject result = new JSONObject();
        JSONArray refundArray = new JSONArray();
        if (refundList.getList() != null) {
            for (RefundDto refund : refundList.getList()) {
                JSONObject refundJsonObj = new JSONObject();
                refundJsonObj.put("sn", refund.getRefundSn());
                refundJsonObj.put("loginCode", refund.getLoginCode());
                refundJsonObj.put("tradeAmount", refund.getTradeAmount());
                refundJsonObj.put("status", refund.getRefundStatus());
                refundJsonObj.put("orderSn", refund.getOrderSn());
                refundJsonObj.put("reason", refund.getRefundReasonName());
                refundJsonObj.put("quantity", refund.getQuantity());
                refundJsonObj.put("applyAmount", refund.getApplyAmount());
                refundJsonObj.put("totalAmount", refund.getTotalAmount());
                refundJsonObj.put("payAmount", refund.getPayMoney());
                refundJsonObj.put("payDate", refund.getPayDate());
                refundJsonObj.put("modifyDate", refund.getModifyDate());
                refundArray.add(refundJsonObj);
            }
            result.put("orderSn", orderSn);
            result.put("datas", refundArray);
            result.put("status", 1);
            result.put("message", "查询成功！");
        } else {
            result.put("status", 0);
            result.put("message", "不存在该订单编号对应的退款单！");
        }
        d2cApiOauth.sendJson(response, result);
        return null;
    }

}
