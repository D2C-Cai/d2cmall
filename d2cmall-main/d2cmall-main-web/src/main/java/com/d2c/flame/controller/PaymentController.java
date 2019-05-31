package com.d2c.flame.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.member.dto.MemberDto;
import com.d2c.member.model.MemberInfo;
import com.d2c.order.enums.OrderTypeEnum;
import com.d2c.order.enums.PaymentTypeEnum;
import com.d2c.order.model.Order;
import com.d2c.order.model.Setting;
import com.d2c.order.model.base.IPaymentInterface;
import com.d2c.order.service.AuctionMarginService;
import com.d2c.order.service.CouponOrderService;
import com.d2c.order.service.OrderService;
import com.d2c.order.service.SettingService;
import com.d2c.order.service.tx.PaymentTxService;
import com.d2c.order.third.payment.alipay.client.AlipayPcClient;
import com.d2c.order.third.payment.alipay.client.AlipayWapClient;
import com.d2c.order.third.payment.alipay.core.pcwap.AlipayConfig;
import com.d2c.order.third.payment.wxpay.client.WxPayUtil;
import com.d2c.order.third.payment.wxpay.core.WeixinPayHelper;
import com.d2c.order.third.payment.wxpay.core.WxWapConfig;
import com.d2c.util.http.HttpUtil;
import com.d2c.util.string.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;

@Controller
@RequestMapping("")
public class PaymentController extends BaseController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private AuctionMarginService auctionMarginService;
    @Autowired
    private CouponOrderService couponOrderService;
    @Reference
    private PaymentTxService paymentTxService;
    @Autowired
    private SettingService settingService;
    @Autowired
    private AlipayConfig alipayConfig;
    @Autowired
    private WxWapConfig wxWapConfig;

    /**
     * 统一支付接口
     *
     * @param model
     * @param billType
     * @param billSn
     * @return
     */
    @RequestMapping(value = "/payment/prepare/{billType}/{billSn}", method = RequestMethod.GET)
    public String preparePayment(ModelMap model, @PathVariable String billType, @PathVariable String billSn) {
        this.getLoginMemberInfo();
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        OrderTypeEnum typeEnum = OrderTypeEnum.valueOf(billType.toUpperCase());
        switch (typeEnum) {
            case ORDER:
                return "redirect:/order/payment/" + billSn;
            case MARGIN:
                return "redirect:/auction/margin/payment/" + billSn;
            default:
                return "error/503";
        }
    }

    /**
     * 跳转支付网关
     *
     * @param model
     * @param id
     * @param orderType
     * @param paymentType
     * @param payment
     * @param password
     * @return
     */
    @RequestMapping(value = "/payment", method = RequestMethod.POST)
    public String payment(Model model, Long id, String orderType, String paymentType, String payment, String password) {
        MemberInfo memberInfo = this.getLoginMemberInfo();
        SuccessResponse result = new SuccessResponse();
        model.addAttribute("result", result);
        if (StringUtils.isBlank(orderType)) {
            orderType = OrderTypeEnum.ORDER.name();
        }
        OrderTypeEnum typeEnum = OrderTypeEnum.valueOf(orderType.toUpperCase());
        IPaymentInterface order;
        try {
            switch (typeEnum) {
                case ORDER:
                    order = orderService.doFindByIdAndMemberInfoId(id, memberInfo.getId());
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
            if (order.isWaitPay()) {
                if (order.getBillTotalFee().equals(new BigDecimal("0.00"))) {
                    paymentType = PaymentTypeEnum.FREEPAY.name();
                }
                return "redirect:" + getPayUrl(model, order, paymentType, payment, null);
            }
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        throw new BusinessException("订单已支付或关闭，支付不成功！");
    }

    /**
     * 代付支付网关
     *
     * @param model
     * @param id
     * @param orderType
     * @param paymentType
     * @param payment
     * @return
     */
    @RequestMapping(value = "/substitute/pay", method = RequestMethod.POST)
    public String substitute(Model model, Long id, String orderType, String paymentType, String payment) {
        SuccessResponse result = new SuccessResponse();
        model.addAttribute("result", result);
        if (StringUtils.isBlank(orderType)) {
            orderType = OrderTypeEnum.ORDER.name();
        }
        Order order = orderService.findById(id);
        if (order == null) {
            throw new BusinessException("订单不存在，支付不成功！");
        }
        IPaymentInterface iPayment = orderService.doFindByIdAndMemberInfoId(id, order.getMemberId());
        if (iPayment.isWaitPay()) {
            if (iPayment.getBillTotalFee().equals(new BigDecimal("0.00"))) {
                paymentType = PaymentTypeEnum.FREEPAY.name();
            }
            PaymentTypeEnum payType = PaymentTypeEnum.valueOf(paymentType);
            switch (payType) {
                case ALIPAY:
                    return "redirect:" + getPayUrl(model, iPayment, paymentType, payment, null);
                case WXPAY:
                    MemberDto member = this.getLoginDto();
                    return getPayUrl(model, iPayment, paymentType, null, member.getGzOpenId());
                default:
                    throw new BusinessException("未知支付方式，支付不成功！");
            }
        }
        throw new BusinessException("订单已支付或关闭，支付不成功！");
    }

    /**
     * 微信公众号支付
     *
     * @param model
     * @param id
     * @param orderType
     * @param paymentType
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/payment/weixin/gz", method = {RequestMethod.GET, RequestMethod.POST})
    public String paymentWxGz(Model model, Long id, String orderType, String paymentType) throws Exception {
        MemberDto member = this.getLoginDto();
        SuccessResponse result = new SuccessResponse();
        model.addAttribute("result", result);
        if (StringUtils.isBlank(orderType)) {
            orderType = OrderTypeEnum.ORDER.name();
        }
        OrderTypeEnum typeEnum = OrderTypeEnum.valueOf(orderType.toUpperCase());
        IPaymentInterface order;
        switch (typeEnum) {
            case ORDER:
                order = orderService.doFindByIdAndMemberInfoId(id, member.getId());
                break;
            case COUPON:
                order = couponOrderService.findByIdAndMemberId(id, member.getId());
                break;
            case MARGIN:
                order = auctionMarginService.findByIdAndMemberId(id, member.getId());
                break;
            default:
                order = orderService.doFindByIdAndMemberInfoId(id, member.getId());
                break;
        }
        if (order == null) {
            throw new BusinessException("订单不存在，支付不成功！");
        }
        if (order.isWaitPay() && PaymentTypeEnum.WXPAY.name().equals(paymentType)) {
            if (isMobileDevice()) {
                if (order.getBillTotalFee().equals(new BigDecimal("0.00"))) {
                    paymentType = PaymentTypeEnum.FREEPAY.name();
                }
                return getPayUrl(model, order, paymentType, null, member.getGzOpenId());
            } else {
                throw new BusinessException("未知支付方式，支付不成功！");
            }
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
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/payment/weixin/kf", method = {RequestMethod.GET, RequestMethod.POST})
    public String paymentWxKf(Model model, Long id, String orderType, String paymentType) throws Exception {
        MemberInfo memberInfo = this.getLoginMemberInfo();
        SuccessResponse result = new SuccessResponse();
        model.addAttribute("result", result);
        if (StringUtils.isBlank(orderType)) {
            orderType = OrderTypeEnum.ORDER.name();
        }
        OrderTypeEnum typeEnum = OrderTypeEnum.valueOf(orderType.toUpperCase());
        IPaymentInterface order;
        switch (typeEnum) {
            case ORDER:
                order = orderService.doFindByIdAndMemberInfoId(id, memberInfo.getId());
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
            return getPayUrl(model, order, paymentType, null, null);
        } else {
            throw new BusinessException("订单已支付或关闭，支付不成功！");
        }
    }

    protected String getPayUrl(Model model, IPaymentInterface order, String paymentType, String memo, String openId) {
        PaymentTypeEnum payType = PaymentTypeEnum.valueOf(paymentType);
        switch (payType) {
            case ALIPAY:
                return aliPayment(order, memo);
            case WXPAY:
                return wxPayment(model, order, openId, "JSAPI");
            case WX_SCANPAY:
                return wxPayment(model, order, null, "NATIVE");
            case FREEPAY:
                return freePayment(order);
            default:
                logger.error("未知支付方式！");
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
                defaultbank, order.getBillSourceType(), timeout, null);
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
    protected String wxPayment(Model model, IPaymentInterface order, String openId, String wxType) {
        String pay_key = WxWapConfig.PAY_KEY;
        WeixinPayHelper wxPayHelper = new WeixinPayHelper();
        DecimalFormat df = new DecimalFormat("0");
        wxPayHelper.setAppid(wxWapConfig.getAPPID());
        wxPayHelper.setMch_id(wxWapConfig.getMCH_ID());
        String productName = order.getBillSubject();
        wxPayHelper.setBody(productName.length() > 32 ? productName.substring(0, 32) : productName);
        wxPayHelper.setAttach(order.getBillSourceType());
        wxPayHelper.setNonce_str(WxPayUtil.createNoncestr());
        wxPayHelper.setNotify_url(wxWapConfig.getNOTIFY_URL());
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
        HashMap<String, String> responseMap = null;
        try {
            responseMap = StringUtil.xmlToMap(responseXml);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
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
            } else {
                throw new BusinessException("微信签名错误");
            }
        }
        return "";
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
