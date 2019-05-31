package com.d2c.flame.controller.callback;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.common.base.utils.security.MD5Ut;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.logger.model.Signature;
import com.d2c.logger.service.SignatureService;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.third.oauth.WeixinGzOauthClient;
import com.d2c.order.enums.CouponSourceEnum;
import com.d2c.order.enums.PaymentTypeEnum;
import com.d2c.order.model.CollageOrder;
import com.d2c.order.model.CouponDef;
import com.d2c.order.service.CollageOrderService;
import com.d2c.order.service.CouponDefQueryService;
import com.d2c.order.service.CouponDefService;
import com.d2c.order.service.tx.PaymentTxService;
import com.d2c.order.third.payment.wxpay.client.WxPayUtil;
import com.d2c.order.third.payment.wxpay.client.card.WxCard;
import com.d2c.order.third.payment.wxpay.client.card.WxCardCreate;
import com.d2c.order.third.payment.wxpay.client.card.WxCardSign;
import com.d2c.order.third.payment.wxpay.core.WxAppConfig;
import com.d2c.order.third.payment.wxpay.core.WxWapConfig;
import com.d2c.order.third.payment.wxpay.sign.SHA1;
import com.d2c.util.string.StringUtil;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;

/**
 * 微信支付请求，通知回调
 */
@Controller
@RequestMapping("")
public class WeixinPayController extends BaseController {

    @Reference
    private PaymentTxService paymentTxService;
    @Autowired
    private CollageOrderService collageOrderService;
    @Autowired
    private CouponDefService couponDefService;
    @Autowired
    private CouponDefQueryService couponDefQueryService;
    @Autowired
    private WeixinGzOauthClient weixinGzOauthClient;
    @Autowired
    private SignatureService signatureService;

    private static String setXML(String return_code, String return_msg) {
        return "<xml><return_code><![CDATA[" + return_code + "]]></return_code><return_msg><![CDATA[" + return_msg
                + "]]></return_msg></xml>";
    }

    /**
     * 微信支付返回信息（POST方式）
     */
    @RequestMapping(value = "/weixinNotify", method = {RequestMethod.GET, RequestMethod.POST})
    public void weixinNotify(HttpServletResponse response) throws Exception {
        HttpServletRequest request = getRequest();
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
        logger.info("[PAYMENT] 微信支付通知返回：" + responseXml);
        HashMap<String, String> responseMap = StringUtil.xmlToMap(responseXml);
        if ("SUCCESS".equalsIgnoreCase(responseMap.get("result_code"))) {
            try {
                String responseSignOld = responseMap.get("sign");
                responseMap.remove("sign");
                String responseSignNew = WxPayUtil.createSign(responseMap, WxWapConfig.PAY_KEY);
                if (responseSignNew.equals(responseSignOld)) { // 微信xml报文，签名验证
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
                    if ("JSAPI".equals(trade_type)) {
                        result = paymentTxService.doPaySuccess(order_type, orderSn, tatolFee, tradeNo, payer,
                                PaymentTypeEnum.WXPAY, null, null, mchId, appId);
                    } else if ("NATIVE".equals(trade_type)) {
                        result = paymentTxService.doPaySuccess(order_type, orderSn, tatolFee, tradeNo, payer,
                                PaymentTypeEnum.WX_SCANPAY, null, null, mchId, appId);
                    }
                    if (1 == result) {
                        logger.info("[PAYMENT] 微信支付通知操作成功!");
                        response.getWriter().write(setXML("SUCCESS", "OK"));
                        response.getWriter().flush();
                    } else {
                        response.getWriter().write(setXML("FAIL", "FAILED"));
                        response.getWriter().flush();
                    }
                }
            } finally {
            }
        }
    }

    /**
     * 微信退款回调
     *
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/weixinRefundNotify", method = {RequestMethod.GET, RequestMethod.POST})
    public void weixinRefundNotify(HttpServletResponse response) throws Exception {
        HttpServletRequest request = getRequest();
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
        logger.info("[PAYMENT] 微信退款通知返回：" + responseXml);
        HashMap<String, String> responseMap = StringUtil.xmlToMap(responseXml);
        if ("SUCCESS".equalsIgnoreCase(responseMap.get("return_code"))) {
            try {
                String req_info = responseMap.get("req_info");
                byte[] b = Base64Utils.decodeFromString(req_info);
                String pay_key = "";
                if (responseMap.get("mch_id").endsWith(WxAppConfig.MCH_TAIL)) {
                    pay_key = WxAppConfig.PAY_KEY;
                } else if (responseMap.get("mch_id").endsWith(WxWapConfig.MCH_TAIL)) {
                    pay_key = WxWapConfig.PAY_KEY;
                }
                SecretKeySpec key = new SecretKeySpec(MD5Ut.md5(pay_key).toLowerCase().getBytes(), "AES");
                Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
                cipher.init(Cipher.DECRYPT_MODE, key);
                String data = new String(cipher.doFinal(b), "utf-8");
                HashMap<String, String> dataMap = StringUtil.xmlToMap(data);
                String refundSn = dataMap.get("out_refund_no");
                String refund_fee = dataMap.get("refund_fee");
                BigDecimal payMoney = new BigDecimal(refund_fee);
                payMoney = payMoney.divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                String paySn = dataMap.get("refund_id");
                CollageOrder collageOrder = collageOrderService.findBySn(refundSn);
                int result = collageOrderService.doRefundSuccess(collageOrder.getId(), "微信支付回调",
                        responseMap.get("return_code"), collageOrder.getPaymentType(), paySn);
                if (1 == result) {
                    logger.info("[PAYMENT] 微信退款通知操作成功!");
                    response.getWriter().write(setXML("SUCCESS", "OK"));
                    response.getWriter().flush();
                } else {
                    response.getWriter().write(setXML("FAIL", "FAILED"));
                    response.getWriter().flush();
                }
            } finally {
            }
        }
    }

    /**
     * 微信卡券回调
     *
     * @return
     * @throws JSONException
     */
    @RequestMapping(value = "/wxcard", method = {RequestMethod.GET, RequestMethod.POST})
    public String wxCard() throws JSONException {
        HttpServletRequest request = getRequest();
        String signature = request.getParameter("signature");
        String code = request.getParameter("encrypt_code");
        String cardId = request.getParameter("card_id");
        // String openId = request.getParameter("openid");
        // 验证签名
        if (signature == null || code == null || cardId == null) {
            logger.info("签名异常！");
            return "redirect:/";
        } else {
            // 解密CODE
            Signature s = signatureService.findByAppid(weixinGzOauthClient.getWEIXIN_GZ_APPKEY());
            String url = WxCard.decryptUrl + s.getToken();
            org.json.JSONObject m_data = new org.json.JSONObject();
            m_data.put("encrypt_code", code);
            try {
                String responseText = WxCardCreate.doPostJson(url, m_data.toString());
                JSONObject responseJo = JSONObject.parseObject(responseText);
                code = responseJo.getString("code");
            } catch (Exception e) {
                logger.info("卡券CODE解密不成功！");
                return "redirect:/";
            }
            if (code.isEmpty()) {
                logger.info("卡券CODE异常！");
                return "redirect:/";
            }
            WxCardSign sign = new WxCardSign();
            sign.AddData(weixinGzOauthClient.getWEIXIN_GZ_APPSECRET());
            sign.AddData(code);
            sign.AddData(cardId);
            String newsignature = sign.GetSignature();
            if (!signature.equals(newsignature)) {
                logger.info("签名错误！");
                return "redirect:/";
            }
        }
        // 授权用户获取
        MemberInfo memberInfo;
        try {
            memberInfo = this.getLoginMemberInfo();
        } catch (NotLoginException e) {
            logger.info("授权不成功！");
            return "redirect:/";
        }
        String redirectUrl = null;
        // 生成对应记录的优惠券
        CouponDef couponDef = couponDefQueryService.findByWxCardId(cardId);
        // 同一卡券只生成一张优惠券
        try {
            couponDefService.doClaimedWxCoupon(couponDef.getId(), memberInfo.getId(), memberInfo.getLoginCode(), code,
                    CouponSourceEnum.WXPROMOTION.name());
            redirectUrl = couponDef.getRedirectUrl();
        } catch (Exception e) {
            logger.info(e.getMessage());
            return "redirect:/";
        }
        if (redirectUrl == null) {
            redirectUrl = "/";
        }
        return "redirect:" + redirectUrl;
    }

    /**
     * 微信参数设置入口
     *
     * @param response
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echostr
     * @throws IOException
     */
    @RequestMapping(value = "/wxAccess", method = RequestMethod.GET)
    public void wxAccess(HttpServletResponse response, String signature, String timestamp, String nonce, String echostr)
            throws IOException {
        String token = "a66751fb542346e24fc001a4d0df41b8";// "d2cmall"的md5
        String[] arrTmp = {token, timestamp, nonce};
        Arrays.sort(arrTmp);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < arrTmp.length; i++) {
            sb.append(arrTmp[i]);
        }
        String mySign = SHA1.sha1(sb.toString());
        if (mySign.equals(signature)) {
            response.getWriter().print(echostr);
        } else {
            response.getWriter().print("error");
        }
    }

    /**
     * 微信JS-API签名接口
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/jsapi/sign", method = RequestMethod.POST)
    public JSONObject wxJsApiSign() {
        Signature s = signatureService.findByAppid(weixinGzOauthClient.getWEIXIN_GZ_APPKEY());
        JSONObject obj = new JSONObject();
        obj.put("access_token", s.getToken());
        obj.put("ticket", s.getTicket());
        return obj;
    }

}