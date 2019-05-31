package com.d2c.order.third.payment.alipay.refund;

import com.d2c.order.third.payment.alipay.core.pcwap.AlipayBase;
import com.d2c.order.third.payment.alipay.core.pcwap.AlipayConfig;
import com.d2c.order.third.payment.alipay.core.pcwap.AlipayCore;
import com.d2c.order.third.payment.alipay.core.pcwap.AlipaySubmit;
import org.apache.commons.httpclient.HttpException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类名：alipay_service 功能：支付宝外部服务接口控制 详细：该页面是请求参数核心处理文件，不需要修改 版本：3.0
 * 修改日期：2010-07-16 说明： 以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 * 该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */
public class AlipayRefundClient {

    public final static Map<String, String> ALIPAY_BANK_MAPPING = new HashMap<>();
    /**
     * 支付宝提供给商户的服务接入网关URL(新)
     */
    private static final String ALIPAY_GATEWAY_NEW = "https://mapi.alipay.com/gateway.do?";

    static {
        ALIPAY_BANK_MAPPING.put("BOC", "BOCB2C"); // 中国银行
        ALIPAY_BANK_MAPPING.put("ICBC", "ICBCB2C"); // 工商银行
        ALIPAY_BANK_MAPPING.put("CMB", "CMB"); // 招商银行
        ALIPAY_BANK_MAPPING.put("CCB", "CCB"); // 中国建设银行
        ALIPAY_BANK_MAPPING.put("ABC", "ABC"); // 中国农业银行
        ALIPAY_BANK_MAPPING.put("SPDB", "SPDB"); // 上海浦发
        ALIPAY_BANK_MAPPING.put("CIB", "CIB"); // 兴业银行
        ALIPAY_BANK_MAPPING.put("GDB", "GDB"); // 广东发展银行
        ALIPAY_BANK_MAPPING.put("SDB", "SDB"); // 深圳发展银行
        ALIPAY_BANK_MAPPING.put("CMBC", "CMBC"); // 民生银行
        ALIPAY_BANK_MAPPING.put("COMM", "COMM"); // 交通银行
        ALIPAY_BANK_MAPPING.put("CITIC", "CITIC"); // 中信银行
        ALIPAY_BANK_MAPPING.put("CEB", "CEBBANK"); // 光大银行
        ALIPAY_BANK_MAPPING.put("HZCB", "HZCBB2c"); // 杭州银行
        ALIPAY_BANK_MAPPING.put("NBBANK", "NBBANK"); // 宁波银行
        ALIPAY_BANK_MAPPING.put("SHBANK", "SHBANK"); // 上海银行
        ALIPAY_BANK_MAPPING.put("SPABANK", "SPABANK"); // 平安银行
        ALIPAY_BANK_MAPPING.put("BJRCB", "BJRCB"); // 北京农村商业银行
        ALIPAY_BANK_MAPPING.put("ALIPAY", ""); //
    }

    public static String createRefundUrl(String partner, String seller_email, String refund_date, String batch_no,
                                         String batch_num, String detail_data, String notify_url, String input_charset, String key, String sign_type)
            throws HttpException, IOException {
        Map<String, String> sParaTemp = new HashMap<>();
        sParaTemp.put("service", "refund_fastpay_by_platform_pwd");
        sParaTemp.put("partner", partner);
        sParaTemp.put("_input_charset", input_charset);
        sParaTemp.put("notify_url", notify_url);
        sParaTemp.put("seller_email", seller_email);
        sParaTemp.put("refund_date", refund_date);
        sParaTemp.put("batch_no", batch_no);
        sParaTemp.put("batch_num", batch_num);
        sParaTemp.put("detail_data", detail_data);
        Map<String, String> sParaNew = AlipayBase.ParaFilter(sParaTemp); // 除去数组中的空值和签名参数
        String mysign = AlipayBase.BuildMysign(sParaNew);// 生成签名结果
        String strUrl = "";
        // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        String arg = AlipayCore.createLinkString_urlencode(sParaNew, input_charset);
        // 得到请求URL
        strUrl = ALIPAY_GATEWAY_NEW + arg + "sign=" + mysign + "&sign_type=" + sign_type;
        return strUrl;
    }

    /**
     * @param partner     合作身份者ID
     * @param refund_date 退款请求
     * @param batch_no    退款批次 时间+流水号
     * @param batch_num   退款总笔数
     * @param detail_data 单笔数据集
     * @param notify_url  回调接口
     * @throws HttpException
     * @throws IOException
     */
    public static String doRefund(AlipayConfig config, String refund_date, String batch_no, String batch_num,
                                  String detail_data) throws HttpException, IOException {
        Map<String, String> sParaTemp = new HashMap<>();
        sParaTemp.put("service", "refund_fastpay_by_platform_nopwd");
        sParaTemp.put("partner", AlipayConfig.partnerId);
        sParaTemp.put("notify_url", config.getRefund_notify_url());
        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
        sParaTemp.put("refund_date", refund_date);
        sParaTemp.put("batch_no", batch_no);
        sParaTemp.put("batch_num", batch_num);
        sParaTemp.put("detail_data", detail_data);
        String sHtmlText = "";
        String result = "";
        try {
            sHtmlText = AlipaySubmit.buildRequest("", "", sParaTemp);
            result = getRefundInfo(sHtmlText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private static String getRefundInfo(String xml) {
        String result = "";
        Document doc;
        try {
            doc = DocumentHelper.parseText(xml);
            @SuppressWarnings("unchecked")
            List<Node> nodeList = doc.selectNodes("//alipay/*");
            for (Node node : nodeList) {
                if (node.getName().equals("is_success") && node.getText().equals("T")) {
                    return "SUCCESS";
                } else {
                    if (node.getName().equals("error")) {
                        return node.getText();
                    }
                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return result;
    }
}
