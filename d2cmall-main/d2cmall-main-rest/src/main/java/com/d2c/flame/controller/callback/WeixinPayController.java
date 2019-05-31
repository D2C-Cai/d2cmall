package com.d2c.flame.controller.callback;

import com.alibaba.dubbo.config.annotation.Reference;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.order.enums.PaymentTypeEnum;
import com.d2c.order.service.tx.PaymentTxService;
import com.d2c.order.third.payment.wxpay.client.WxPayUtil;
import com.d2c.order.third.payment.wxpay.core.WxAppConfig;
import com.d2c.util.string.StringUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashMap;

/**
 * 微信通知回调
 */
@RestController
@RequestMapping("")
public class WeixinPayController extends BaseController {

    @Reference
    private PaymentTxService paymentTxService;

    private static String setXML(String return_code, String return_msg) {
        return "<xml><return_code><![CDATA[" + return_code + "]]></return_code><return_msg><![CDATA[" + return_msg
                + "]]></return_msg></xml>";
    }

    /**
     * 微信支付成功回调
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/weixinNotify", method = {RequestMethod.GET, RequestMethod.POST})
    public void weixinNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        InputStream inStream = request.getInputStream();
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        outSteam.close();
        inStream.close();
        String responseXml = new String(outSteam.toByteArray(), "utf-8");
        HashMap<String, String> responseMap = StringUtil.xmlToMap(responseXml);
        if ("SUCCESS".equalsIgnoreCase(responseMap.get("result_code"))) {
            String responseSign = responseMap.get("sign");
            responseMap.remove("sign");
            String responseSignNew = WxPayUtil.createSign(responseMap, WxAppConfig.PAY_KEY);
            if (responseSignNew.equals(responseSign)) { // 微信xml报文，签名验证
                String appId = responseMap.get("appid");
                String mchId = responseMap.get("mch_id");
                String orderSn = responseMap.get("out_trade_no");
                String totalFee = responseMap.get("total_fee");
                String tradeNo = responseMap.get("transaction_id");
                String trade_type = responseMap.get("trade_type");
                String payer = responseMap.get("openid");
                String order_type = responseMap.get("attach");
                int result = 0;
                BigDecimal tatolFee = new BigDecimal(totalFee);
                tatolFee = tatolFee.divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                if ("APP".equals(trade_type)) {
                    result = paymentTxService.doPaySuccess(order_type, orderSn, tatolFee, tradeNo, payer,
                            PaymentTypeEnum.WXAPPPAY, null, null, mchId, appId);
                } else if ("JSAPI".equals(trade_type)) {
                    result = paymentTxService.doPaySuccess(order_type, orderSn, tatolFee, tradeNo, payer,
                            PaymentTypeEnum.WXPAY, null, null, mchId, appId);
                }
                if (1 == result) {
                    response.getWriter().write(setXML("SUCCESS", "OK"));
                    response.getWriter().flush();
                } else {
                    response.getWriter().write(setXML("FAIL", "FAILED"));
                    response.getWriter().flush();
                }
            }
        }
    }

}