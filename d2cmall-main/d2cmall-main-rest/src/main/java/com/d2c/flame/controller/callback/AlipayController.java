package com.d2c.flame.controller.callback;

import com.alibaba.dubbo.config.annotation.Reference;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.order.enums.PaymentTypeEnum;
import com.d2c.order.service.tx.PaymentTxService;
import com.d2c.order.third.payment.alipay.core.app.AlipayConfig;
import com.d2c.order.third.payment.alipay.core.app.AlipayNotify;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 阿里支付的返回与通知服务
 */
@RestController
@RequestMapping("")
public class AlipayController extends BaseController {

    @Reference
    private PaymentTxService paymentTxService;

    /**
     * 处理支付宝返回的支付信息（POST方式）
     *
     * @param response
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/alipayNotify", method = RequestMethod.POST)
    public void alipayNotify(HttpServletResponse response) throws Exception {
        // 获取支付宝POST过来反馈信息
        HttpServletRequest request = getRequest();
        Map<String, String> params = new HashMap<>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }
        // 预留"order"或"card"
        String trade_order_type = new String(request.getParameter("body").getBytes("ISO-8859-1"), "UTF-8");
        // 商户订单号
        String orderNum = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
        // 支付金额
        String totalFee = new String(request.getParameter("total_fee").getBytes("ISO-8859-1"), "UTF-8");
        // 支付宝交易号
        String tradeNo = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
        // 买家支付宝邮箱账号
        String buyerEmail = new String(request.getParameter("buyer_email").getBytes("ISO-8859-1"), "UTF-8");
        // 交易状态
        String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
        if (AlipayNotify.verify(params) || AlipayNotify.verify2(params)) {// 验证成功
            if (trade_status.equals("TRADE_FINISHED")) {
            } else if (trade_status.equals("TRADE_SUCCESS")) {
                int value = paymentTxService.doPaySuccess(trade_order_type, orderNum, new BigDecimal(totalFee), tradeNo,
                        buyerEmail, PaymentTypeEnum.ALIPAY, null, null, AlipayConfig.partner2, null);
                if (1 == value) {
                    response.getWriter().println("success");
                }
            }
        } else {// 验证不成功
            response.getWriter().println("fail");
        }
    }

}
