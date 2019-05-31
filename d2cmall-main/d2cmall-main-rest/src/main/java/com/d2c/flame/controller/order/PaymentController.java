package com.d2c.flame.controller.order;

import com.alibaba.dubbo.config.annotation.Reference;
import com.d2c.common.api.response.ResponseResult;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.service.AccountService;
import com.d2c.order.enums.OrderTypeEnum;
import com.d2c.order.enums.PaymentTypeEnum;
import com.d2c.order.model.Setting;
import com.d2c.order.model.base.IPaymentInterface;
import com.d2c.order.service.*;
import com.d2c.order.service.tx.PaymentTxService;
import com.d2c.order.third.payment.alipay.client.AlipayPcClient;
import com.d2c.order.third.payment.alipay.client.AlipayWapClient;
import com.d2c.order.third.payment.alipay.core.pcwap.AlipayConfig;
import com.d2c.order.third.payment.wxpay.client.WxPayUtil;
import com.d2c.order.third.payment.wxpay.core.WeixinPayHelper;
import com.d2c.order.third.payment.wxpay.core.WxWapConfig;
import com.d2c.order.third.payment.wxpay.core.WxXcxConfig;
import com.d2c.util.http.HttpUtil;
import com.d2c.util.string.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;

/**
 * 消费相关包括付款
 *
 * @author Lain
 * @version 3.0
 */
@RestController
@RequestMapping(value = "/v3/api")
public class PaymentController extends BaseController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private CouponOrderService couponOrderService;
    @Autowired
    private CollageOrderService collageOrderService;
    @Autowired
    private AuctionMarginService auctionMarginService;
    @Autowired
    private AccountService accountService;
    @Reference
    private PaymentTxService paymentTxService;
    @Autowired
    private SettingService settingService;
    @Autowired
    private AlipayConfig alipayConfig;
    @Autowired
    private WxWapConfig wxWapConfig;
    @Autowired
    private WxXcxConfig wxXcxConfig;

    /**
     * 跳转支付网关
     *
     * @param model
     * @param orderSn
     * @param orderType
     * @param paymentType
     * @param loginCode
     * @param password
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/payment", method = RequestMethod.POST)
    public ResponseResult payment(Model model, @RequestParam(required = true) String orderSn,
                                  @RequestParam(required = true) String orderType, @RequestParam(required = true) String paymentType,
                                  String loginCode, String password) throws Exception {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        if (PaymentTypeEnum.COD.toString().equals(paymentType)) {
            throw new BusinessException("暂不支持货到付款。");
        }
        if (StringUtils.isBlank(orderType)) {
            orderType = OrderTypeEnum.ORDER.name();
        }
        OrderTypeEnum typeEnum = OrderTypeEnum.valueOf(orderType.toUpperCase());
        IPaymentInterface order;
        switch (typeEnum) {
            case ORDER:
                order = orderService.doFindByOrderSnAndMemberInfoId(orderSn, memberInfo.getId());
                break;
            case COLLAGE:
                order = collageOrderService.findBySnAndMemberId(orderSn, memberInfo.getId());
                break;
            case COUPON:
                order = couponOrderService.findBySnAndMemberId(orderSn, memberInfo.getId());
                break;
            case MARGIN:
                order = auctionMarginService.findByMarginSnAndMemberId(orderSn.split("-")[0], memberInfo.getId());
                break;
            default:
                order = orderService.doFindByOrderSnAndMemberInfoId(orderSn, memberInfo.getId());
                break;
        }
        if (order == null) {
            throw new BusinessException("订单不存在，支付不成功！");
        }
        if (order.isWaitPay()) {
            if (order.getBillTotalFee().equals(new BigDecimal("0.00"))) {
                paymentType = PaymentTypeEnum.FREEPAY.name();
            }
            String data = this.doPayment(model, order, paymentType, loginCode, password, null, null);
            result.put("url", data);
            return result;
        } else {
            throw new BusinessException("订单已支付或关闭，支付不成功！");
        }
    }

    /**
     * 微信公众号支付
     *
     * @param model
     * @param id
     * @param orderType
     * @param paymentType
     * @param appId
     * @param openId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/payment/weixin/gz", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseResult paymentWxGz(Model model, Long id, @RequestParam(required = true) String orderType,
                                      String paymentType, String appId, String openId) throws Exception {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        if (StringUtils.isBlank(orderType)) {
            orderType = OrderTypeEnum.ORDER.name();
        }
        OrderTypeEnum typeEnum = OrderTypeEnum.valueOf(orderType.toUpperCase());
        IPaymentInterface order;
        switch (typeEnum) {
            case ORDER:
                order = orderService.doFindByIdAndMemberInfoId(id, memberInfo.getId());
                break;
            case COLLAGE:
                order = collageOrderService.findByIdAndMemberId(id, memberInfo.getId());
                break;
            case COUPON:
                order = couponOrderService.findByIdAndMemberId(id, memberInfo.getId());
                break;
            case MARGIN:
                order = auctionMarginService.findByIdAndMemberId(id, memberInfo.getId());
                break;
            default:
                order = orderService.doFindByIdAndMemberInfoId(id, memberInfo.getId());
                break;
        }
        if (order == null) {
            throw new BusinessException("订单不存在，支付不成功！");
        }
        if (order.isWaitPay() && PaymentTypeEnum.WXPAY.name().equals(paymentType)) {
            if (order.getBillTotalFee().equals(new BigDecimal("0.00"))) {
                paymentType = PaymentTypeEnum.FREEPAY.name();
            }
            this.doPayment(model, order, paymentType, null, null, appId, openId);
            result.put("data", model.asMap());
            return result;
        } else {
            throw new BusinessException("订单已支付或关闭，支付不成功！");
        }
    }

    /**
     * 微信扫码支付
     *
     * @param model
     * @param id
     * @param orderType
     * @param paymentType
     * @param appId
     * @param openId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/payment/weixin/kf", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseResult paymentWxKf(Model model, Long id, @RequestParam(required = true) String orderType,
                                      String paymentType, String appId, String openId) throws Exception {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        if (StringUtils.isBlank(orderType)) {
            orderType = OrderTypeEnum.ORDER.name();
        }
        OrderTypeEnum typeEnum = OrderTypeEnum.valueOf(orderType.toUpperCase());
        IPaymentInterface order;
        switch (typeEnum) {
            case ORDER:
                order = orderService.doFindByIdAndMemberInfoId(id, memberInfo.getId());
                break;
            case COLLAGE:
                order = collageOrderService.findByIdAndMemberId(id, memberInfo.getId());
                break;
            case COUPON:
                order = couponOrderService.findByIdAndMemberId(id, memberInfo.getId());
                break;
            case MARGIN:
                order = auctionMarginService.findByIdAndMemberId(id, memberInfo.getId());
                break;
            default:
                order = orderService.doFindByIdAndMemberInfoId(id, memberInfo.getId());
                break;
        }
        if (order == null) {
            throw new BusinessException("订单不存在，支付不成功！");
        }
        if (order.isWaitPay() && PaymentTypeEnum.WX_SCANPAY.name().equals(paymentType)) {
            if (order.getBillTotalFee().equals(new BigDecimal("0.00"))) {
                paymentType = PaymentTypeEnum.FREEPAY.name();
            }
            this.doPayment(model, order, paymentType, null, null, appId, openId);
            result.put("data", model.asMap());
            return result;
        } else {
            throw new BusinessException("订单已支付或关闭，支付不成功！");
        }
    }

    public String doPayment(Model model, IPaymentInterface order, String paymentType, String memo, String password,
                            String appId, String openId) throws Exception {
        PaymentTypeEnum payType = PaymentTypeEnum.valueOf(paymentType);
        switch (payType) {
            case WXPAY:
                return wxPayment(model, order, openId, appId, wxXcxConfig.getMCH_ID(), wxXcxConfig.getNOTIFY_URL(),
                        WxXcxConfig.PAY_KEY, "JSAPI");
            case WX_SCANPAY:
                return wxPayment(model, order, null, wxWapConfig.getAPPID(), wxWapConfig.getMCH_ID(),
                        wxWapConfig.getNOTIFY_URL(), WxWapConfig.PAY_KEY, "NATIVE");
            case WALLET:
                return walletPayment(model, order, password);
            case FREEPAY:
                return freePayment(order);
            case ALIPAY:
                return aliPayment(order, memo);
            default:
                logger.error("未知支付方式！" + paymentType);
                return null;
        }
    }

    /**
     * 支付宝支付
     */
    protected String aliPayment(IPaymentInterface order, String bank) {
        String url = "";
        String timeout = "30m";
        try {
            String value = "86400";
            if (order.getCross()) {
                Setting setting = settingService.findByCode(Setting.CROSSCLOSECODE);
                value = Setting.defaultValue(setting, "1200").toString();
            } else {
                Setting setting = settingService.findByCode(Setting.ORDERCLOSECODE);
                Setting.defaultValue(setting, "86400").toString();
            }
            timeout = Math.floorDiv(Integer.parseInt(value), 60) - 1 + "m";
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (isNormalDevice()) {
            url = generateAlipayUrl(order, bank, timeout);
        } else {
            url = generateAlipayWapUrl(order, bank, timeout);
        }
        return url;
    }

    private String generateAlipayUrl(IPaymentInterface order, String bank, String timeout) {
        String subject = order.getBillSubject(); // 订单名称，显示在支付宝收银台里的“商品名称”里，显示在支付宝的交易管理的“商品名称”的列表里。
        String body = order.getBillBody(); // 订单描述、订单详细、订单备注，显示在支付宝收银台里的“商品描述”里
        BigDecimal total_fee = order.getBillTotalFee(); // 订单总金额，显示在支付宝收银台里的“应付总额”里
        DecimalFormat df = new DecimalFormat("0.00");
        String orderNum = order.getBillSourceSn();
        String paymethod = "bankPay";
        if ("ALIPAY".equals(bank))
            paymethod = "directPay";
        String defaultbank = AlipayPcClient.ALIPAY_BANK_MAPPING.get(bank);
        return AlipayPcClient.createUrl(alipayConfig, orderNum, subject, body, df.format(total_fee), paymethod,
                defaultbank, order.getBillSourceType(), timeout, "4");
    }

    private String generateAlipayWapUrl(IPaymentInterface order, String bank, String timeout) {
        String subject = order.getBillSubject(); // 订单名称，显示在支付宝收银台里的“商品名称”里，显示在支付宝的交易管理的“商品名称”的列表里。
        BigDecimal total_fee = order.getBillTotalFee(); // 订单总金额，显示在支付宝收银台里的“应付总额”里
        DecimalFormat df = new DecimalFormat("0.00");
        String orderSn = order.getBillSourceSn();
        return AlipayWapClient.createUrl(alipayConfig, order.getBillSourceType(), orderSn, subject,
                df.format(total_fee), "http://www.d2cmall.com", orderSn, timeout);
    }

    /**
     * 微信支付
     */
    protected String wxPayment(Model model, IPaymentInterface order, String openId, String appId, String mchId,
                               String notifyUrl, String pay_key, String wxType) throws Exception {
        try {
            WeixinPayHelper wxPayHelper = new WeixinPayHelper();
            DecimalFormat df = new DecimalFormat("0");
            wxPayHelper.setAppid(appId);
            wxPayHelper.setMch_id(mchId);
            String productName = order.getBillSubject();
            wxPayHelper.setBody(productName.length() > 32 ? productName.substring(0, 32) : productName);
            wxPayHelper.setAttach(order.getBillSourceType());
            wxPayHelper.setNonce_str(WxPayUtil.createNoncestr());
            wxPayHelper.setNotify_url(notifyUrl);
            if ("JSAPI".equals(wxType)) {
                wxPayHelper.setOpenid(openId);
            } else if ("NATIVE".equals(wxType)) {
                wxPayHelper.setProduct_id(order.getBillSourceSn());
            }
            wxPayHelper.setOut_trade_no(order.getBillSourceSn());
            String ip = getLoginIp();
            if (StringUtils.isEmpty(ip)) {
                wxPayHelper.setSpbill_create_ip("127.0.0.1");
            } else {
                wxPayHelper.setSpbill_create_ip(ip.split(",")[0].trim());
            }
            wxPayHelper.setTotal_fee(df.format(order.getBillTotalFee().multiply(new BigDecimal(100)))); // 支付金额
            wxPayHelper.setTrade_type(wxType);
            String jsXml = wxPayHelper.paramXml(pay_key);
            String responseXml = HttpUtil.sendPostXmlHttps("https://api.mch.weixin.qq.com/pay/unifiedorder", jsXml);
            HashMap<String, String> responseMap = StringUtil.xmlToMap(responseXml);
            if (responseMap.containsKey("return_msg") && "OK".equals(responseMap.get("return_msg"))
                    && "SUCCESS".equals(responseMap.get("return_code"))
                    && "SUCCESS".equals(responseMap.get("result_code"))) {
                String responseSignOld = responseMap.get("sign");
                responseMap.remove("sign");
                String responseSignNew = WxPayUtil.createSign(responseMap, pay_key);
                if (responseSignNew.equals(responseSignOld)) {// 微信返回xml报文，签名验证
                    if ("JSAPI".equals(wxType)) {// js支付
                        HashMap<String, String> result = new HashMap<>();
                        result.put("appId", responseMap.get("appid"));
                        result.put("nonceStr", WxPayUtil.createNoncestr());
                        result.put("package", "prepay_id=" + responseMap.get("prepay_id"));
                        result.put("signType", "MD5");
                        result.put("timeStamp", WxPayUtil.createTimestamp());
                        String paySign = WxPayUtil.createSign(result, pay_key);
                        result.put("paySign", paySign);
                        result.put("successUrl", wxWapConfig.getRETURN_URL());// 支付成功，跳转到我的订单
                        model.addAllAttributes(result);
                        return "pay/wx_payment";
                    } else if ("NATIVE".equals(wxType)) {// 扫码付
                        model.addAttribute("code_url", responseMap.get("code_url"));
                        model.addAttribute("orderSn", order.getBillSourceSn());
                        model.addAttribute("totalFee", order.getBillTotalFee());
                        return "pay/wx_scan_payment";
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return "";
    }

    /**
     * 钱包支付
     */
    protected String walletPayment(Model model, IPaymentInterface order, String password) {
        logger.info("[PAYMENT] 钱包付款:" + order.getBillSourceSn());
        SuccessResponse resp = new SuccessResponse();
        String error = accountService.checkPassword(order.getMemberId(), password);
        if (!("1".equalsIgnoreCase(error))) {
            if ("输入密码错误已经5次，请点击忘记密码或2小时后重试".equalsIgnoreCase(error)) {
                model.addAttribute("errorTimes", 5);
            }
            throw new BusinessException(error);
        }
        int result = paymentTxService.doWalletPayment(order, password, getDeviceType());
        if (result > 0) {
            resp.setStatus(1);
            resp.setMsg("钱包支付成功！");
            model.addAttribute("result", resp);
        }
        return "/member/order";
    }

    /**
     * 0元支付
     */
    protected String freePayment(IPaymentInterface order) {
        logger.info("[PAYMENT] 0元支付:" + order.getBillSourceSn());
        paymentTxService.doPaySuccess(order.getBillSourceType(), order.getBillSourceSn(), order.getBillTotalFee(),
                order.getBillSourceSn(), String.valueOf(order.getMemberId()), PaymentTypeEnum.FREEPAY, null, null, null,
                null);
        return "/member/order";
    }

}
