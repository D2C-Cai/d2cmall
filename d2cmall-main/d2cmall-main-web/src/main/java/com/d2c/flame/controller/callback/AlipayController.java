package com.d2c.flame.controller.callback;

import com.alibaba.dubbo.config.annotation.Reference;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.member.enums.DeviceTypeEnum;
import com.d2c.order.enums.OrderTypeEnum;
import com.d2c.order.enums.PaymentTypeEnum;
import com.d2c.order.service.CollageOrderService;
import com.d2c.order.service.tx.PaymentTxService;
import com.d2c.order.third.payment.alipay.core.pcwap.*;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Map;

/**
 * 阿里支付的返回与通知服务
 */
@Controller
@RequestMapping("")
public class AlipayController extends BaseController {

    @Reference
    private PaymentTxService paymentTxService;
    @Autowired
    private CollageOrderService collageOrderService;

    /**
     * WAP支付宝返回信息（GET方式）
     */
    @RequestMapping(value = "/wapAlipayReturn", method = RequestMethod.GET)
    public String alipayWapReturn(Model model) {
        boolean result = false;
        HttpServletRequest request = getRequest();
        String returnUrl = "redirect:/member/order";
        try {
            boolean valid = AlipayWapNotify.verifyReturn(request.getParameterMap());
            if (valid) {
                String status = request.getParameter("result");
                if (status.equalsIgnoreCase("success")) {
                    String outTradeSn = request.getParameter("out_trade_no");
                    String paySn = request.getParameter("trade_no");
                    String buyer_email = request.getParameter("buyer_email");
                    String[] orderNumArray = outTradeSn.split("[|]");
                    String orderType = orderNumArray[0];
                    String totalFee = orderNumArray[1];
                    String orderSn = orderNumArray[2];
                    int value = paymentTxService.doPaySuccess(orderType, orderSn, new BigDecimal(totalFee), paySn,
                            buyer_email, PaymentTypeEnum.ALIPAY, null, null, AlipayConfig.partnerId, null);
                    if (value > 0) {
                        result = true;
                    }
                    if (orderType != null && OrderTypeEnum.ORDER.name().equalsIgnoreCase(orderType)) {
                        // 商品订单
                        returnUrl = "redirect:/member/order";
                    } else if (orderType != null && OrderTypeEnum.MARGIN.name().equalsIgnoreCase(orderType)) {
                        // 拍卖相关
                        returnUrl = "redirect:/auction/member/mymargin";
                    } else if (orderType != null && OrderTypeEnum.COUPON.name().equalsIgnoreCase(orderType)) {
                        // 优惠券相关
                        returnUrl = "redirect:/coupon/myCoupon?status=UNCLAIMED&status=CLAIMED";
                    }
                }
            }
            if (result) {
                model.addAttribute("result", result);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
        }
        return returnUrl;
    }

    /**
     * WAP支付宝返回信息（POST方式）
     */
    @RequestMapping(value = "/wapAlipayNotify", method = RequestMethod.POST)
    public @ResponseBody
    String alipayWapNotify(Model model, HttpServletResponse response) {
        boolean result = false;
        logger.info("[PAYMENT] 支付宝WAP返回通知!");
        response.setContentType("text/html; charset=utf-8");
        try {
            result = this.processWapNotify(DeviceTypeEnum.MOBILE);
            if (result) {
                logger.info("[PAYMENT] 支付宝WAP返回通知-SUCCESS!");
                return "success";
            } else {
                logger.info("[PAYMENT] 支付宝WAP返回通知-FAILED!");
                return "fail";
            }
        } finally {
        }
    }

    /**
     * WAP成功操作
     */
    private boolean processWapNotify(DeviceTypeEnum device) {
        boolean valid = false;
        HttpServletRequest request = getRequest();
        try {
            valid = AlipayWapNotify.verifyNotify(request.getParameterMap());
            if (valid) {
                Object notifyData = request.getParameterMap().get("notify_data");
                String data = "";
                if (notifyData != null) {
                    if (notifyData instanceof String) {
                        data = notifyData.toString();
                    } else if (notifyData instanceof String[]) {
                        data = ((String[]) notifyData)[0];
                    }
                }
                Document doc_notify_data = DocumentHelper.parseText(data);
                // 商户订单号
                String orderNum = doc_notify_data.selectSingleNode("//notify/out_trade_no").getText();
                // 支付宝交易号
                String trade_no = doc_notify_data.selectSingleNode("//notify/trade_no").getText();
                // 交易状态
                String trade_status = doc_notify_data.selectSingleNode("//notify/trade_status").getText();
                String buyer_email = doc_notify_data.selectSingleNode("//notify/buyer_email").getText();
                // 交易成功且结束
                if ("TRADE_FINISHED".equalsIgnoreCase(trade_status)) {
                } else if ("TRADE_SUCCESS".equalsIgnoreCase(trade_status)) {
                    String[] orderNumArray = orderNum.split("[|]");
                    String orderType = orderNumArray[0];
                    String totalFee = orderNumArray[1];
                    String orderSn = orderNumArray[2];
                    logger.info("[PAYMENT] 正在处理!" + orderSn);
                    int value = paymentTxService.doPaySuccess(orderType, orderSn, new BigDecimal(totalFee), trade_no,
                            buyer_email, PaymentTypeEnum.ALIPAY, null, null, AlipayConfig.partnerId, null);
                    if (value > 0) {
                        valid = true;
                    }
                }
            }
            return valid;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return valid;
    }

    /**
     * PC支付宝返回信息（GET方式）
     */
    @RequestMapping(value = "/alipayReturn", method = RequestMethod.GET)
    public String alipayPcReturn(Model model) {
        boolean result = false;
        HttpServletRequest request = getRequest();
        String returnUrl = "redirect:/member/order";
        try {
            String mySign = AlipayPcNotify.GetMysign(request.getParameterMap());
            if (mySign.equals(request.getParameter("sign"))) {
                String status = request.getParameter("trade_status");
                if (status.equalsIgnoreCase("TRADE_FINISHED")) {
                } else if ("TRADE_SUCCESS".equalsIgnoreCase(status)) {
                    String trade_order_type = request.getParameter("extra_common_param");
                    String orderNum = request.getParameter("out_trade_no");
                    String totalFee = request.getParameter("total_fee");
                    String tradeNo = request.getParameter("trade_no");
                    @SuppressWarnings("unchecked")
                    Map<String, String> sParaNew = AlipayBase.ParaFilter(request.getParameterMap());
                    String buyer_email = sParaNew.get("buyer_email");
                    int endIndex = orderNum.indexOf("_bankPay");
                    if (endIndex <= 0) {
                        endIndex = orderNum.indexOf("_directPay");
                    }
                    if (endIndex > 0) {
                        orderNum = orderNum.substring(0, endIndex);
                    }
                    int value = paymentTxService.doPaySuccess(trade_order_type, orderNum, new BigDecimal(totalFee),
                            tradeNo, buyer_email, PaymentTypeEnum.ALIPAY, null, null, AlipayConfig.partnerId, null);
                    if (value > 0) {
                        result = true;
                    }
                    if (trade_order_type != null && OrderTypeEnum.ORDER.name().equalsIgnoreCase(trade_order_type)) {
                        // 商品订单
                        returnUrl = "redirect:/member/order";
                    } else if (trade_order_type != null
                            && OrderTypeEnum.MARGIN.name().equalsIgnoreCase(trade_order_type)) {
                        // 拍卖相关
                        returnUrl = "redirect:/auction/member/mymargin";
                    } else if (trade_order_type != null
                            && OrderTypeEnum.COUPON.name().equalsIgnoreCase(trade_order_type)) {
                        // 优惠券相关
                        returnUrl = "redirect:/coupon/myCoupon?status=UNCLAIMED&status=CLAIMED";
                    }
                }
            }
            if (result) {
                model.addAttribute("result", result);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
        }
        return returnUrl;
    }

    /**
     * PC支付宝返回信息（POST方式）
     */
    @RequestMapping(value = "/alipayNotify", method = RequestMethod.POST)
    public @ResponseBody
    String alipayPcNotify(Model model, HttpServletResponse response) {
        logger.info("[PAYMENT] 支付宝PC返回通知!");
        response.setContentType("text/html; charset=utf-8");
        try {
            boolean result = processPcNotify(DeviceTypeEnum.PC);
            if (result) {
                logger.info("[PAYMENT] 支付宝PC返回通知-SUCCESS!");
                return "success";
            } else {
                logger.info("[PAYMENT] 支付宝PC返回通知-FAILED!");
                return "fail";
            }
        } finally {
        }
    }

    /**
     * PC成功操作
     */
    private boolean processPcNotify(DeviceTypeEnum device) {
        boolean result = false;
        HttpServletRequest request = getRequest();
        try {
            String mySign = AlipayPcNotify.GetMysign(request.getParameterMap());
            if (mySign.equals(request.getParameter("sign"))) {
                String status = request.getParameter("trade_status");
                if ("TRADE_FINISHED".equalsIgnoreCase(status)) {
                } else if ("TRADE_SUCCESS".equalsIgnoreCase(status)) {
                    String trade_order_type = request.getParameter("extra_common_param");
                    String orderNum = request.getParameter("out_trade_no");
                    String totalFee = request.getParameter("total_fee");
                    String tradeNo = request.getParameter("trade_no");
                    @SuppressWarnings("unchecked")
                    Map<String, String> sParaNew = AlipayBase.ParaFilter(request.getParameterMap());
                    String buyer_email = sParaNew.get("buyer_email");
                    int endIndex = orderNum.indexOf("_bankPay");
                    if (endIndex <= 0) {
                        endIndex = orderNum.indexOf("_directPay");
                    }
                    if (endIndex > 0) {
                        orderNum = orderNum.substring(0, endIndex);
                    }
                    logger.info("[PAYMENT] 正在处理!" + tradeNo);
                    int value = paymentTxService.doPaySuccess(trade_order_type, orderNum, new BigDecimal(totalFee),
                            tradeNo, buyer_email, PaymentTypeEnum.ALIPAY, null, null, AlipayConfig.partnerId, null);
                    if (value > 0) {
                        result = true;
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return result;
    }

    /**
     * 支付宝退款回调
     *
     * @param model
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/alipayRefundNotify", method = RequestMethod.POST)
    public String refundNotify(Model model, HttpServletResponse response) throws Exception {
        // 支付宝交易号
        HttpServletRequest request = getRequest();
        logger.info("[PAYMENT] 支付宝退款返回通知!");
        try {
            // 退款批次号
            String refundBatchNo = new String(request.getParameter("batch_no").getBytes("ISO-8859-1"), "UTF-8");
            // 退款成功总数
            int num = StringUtils.isEmpty(request.getParameter("success_num")) ? 0
                    : Integer.parseInt(new String(request.getParameter("success_num").getBytes("ISO-8859-1"), "UTF-8"));
            // 处理结果详情 (原付款交易号^退款总金额^退款处理码^是否充退^充退结果)
            String resultDetails = new String(request.getParameter("result_details").getBytes("ISO-8859-1"), "UTF-8");
            // 退款编号
            String refundSn = (refundBatchNo.length() >= 8 ? refundBatchNo.substring(8) : refundBatchNo);
            // 原支付交易流水号
            String paySn = "";
            // 退款金额
            BigDecimal payMoney = new BigDecimal(0);
            // 处理结果码
            String resultCode = "";
            boolean valid = AlipayWapNotify.verifyReturn(request.getParameterMap());
            if (valid) {
                if (num > 0 && StringUtils.isNotEmpty(resultDetails)) {
                    String[] resultDetail = resultDetails.split("\\^");
                    if (resultDetail.length >= 3) {
                        paySn = resultDetail[0].toString();
                        payMoney = (StringUtils.isEmpty(resultDetail[1]) ? payMoney
                                : new BigDecimal(resultDetail[1].toString()));
                        resultCode = resultDetail[2].toString();
                        if ("SUCCESS".equalsIgnoreCase(resultCode)) {
                            int success = collageOrderService.doRefundSuccess(Long.valueOf(refundSn), "支付宝回调",
                                    resultCode, PaymentTypeEnum.ALIPAY.getCode(), paySn);
                            if (success == 1) {
                                response.getWriter().println("success");
                            }
                        } else {
                            // 如果失败则记录错误码
                            logger.error(refundSn + "支付宝退款失败，错误码：" + AlipayUtil.convertError(resultCode));
                        }
                    }
                }
            } else {
                if (response != null)
                    response.getWriter().println("fail");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            if (response != null)
                response.getWriter().println("fail");
        }
        return null;
    }

}
