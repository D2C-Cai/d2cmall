package com.d2c.order.third.payment.alipay.client;

import com.d2c.order.third.payment.alipay.core.pcwap.*;
import com.d2c.util.date.DateUtil;
import com.d2c.util.string.StringUtil;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 类名：alipay_service 功能：支付宝外部服务接口控制 详细：该页面是请求参数核心处理文件，不需要修改 版本：3.0
 * 修改日期：2010-07-16 说明： 以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 * 该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */
public class AlipayPcClient {

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

    /**
     * 功能：构造请求URL（GET方式请求）
     *
     * @param partner            合作身份者ID
     * @param seller_email       签约支付宝账号或卖家支付宝帐户
     * @param return_url         付完款后跳转的页面 要用 以http开头格式的完整路径，不允许加?id=123这类自定义参数
     * @param notify_url         交易过程中服务器通知的页面 要用 以http开格式的完整路径，不允许加?id=123这类自定义参数
     * @param show_url           网站商品的展示地址，不允许加?id=123这类自定义参数
     * @param out_trade_no       请与贵网站订单系统中的唯一订单号匹配
     * @param subject            订单名称，显示在支付宝收银台里的“商品名称”里，显示在支付宝的交易管理的“商品名称”的列表里。
     * @param body               订单描述、订单详细、订单备注，显示在支付宝收银台里的“商品描述”里
     * @param total_fee          订单总金额，显示在支付宝收银台里的“应付总额”里
     * @param paymethod          默认支付方式，四个值可选：bankPay(网银); cartoon(卡通); directPay(余额);
     *                           CASH(网点支付)
     * @param defaultbank        默认网银代号，代号列表见club.alipay.com/read.php?tid=8681379
     * @param encrypt_key        防钓鱼时间戳
     * @param exter_invoke_ip    买家本地电脑的IP地址
     * @param extra_common_param 自定义参数，可存放任何内容（除等特殊字符外），不会显示在页面上
     * @param buyer_email        默认买家支付宝账号
     * @param royalty_type       提成类型，该值为固定值：10，不需要修改
     * @param royalty_parameters 提成信息集，与需要结合商户网站自身情况动态获取每笔交易的各分润收款账号、各分润金额、各分润说明。最多只能设置10条
     * @param it_b_pay           超时时间，不填默认是15天。八个值可选：1h(1小时),2h(2小时),3h(3小时),1d(1天),3d(3天),7d(7
     *                           天),15d(15天),1c(当天)
     * @param input_charset      字符编码格式 目前支持 GBK 或 utf-8
     * @param key                安全校验码
     * @param sign_type          加密方式 不需修改
     * @return 请求URL
     * @throws Exception
     */
    public static String createUrl(AlipayConfig config, String out_trade_no, String subject, String body,
                                   String total_fee, String paymethod, String defaultbank, String out_trade_order_type, String timeout,
                                   String qr_pay_mode) {
        Map<String, String> sPara = new HashMap<>();
        sPara.put("service", "create_direct_pay_by_user");
        sPara.put("payment_type", "1");
        sPara.put("partner", AlipayConfig.partnerId);
        sPara.put("seller_email", AlipayConfig.sellerEmail);
        sPara.put("return_url", config.getReturn_url());
        sPara.put("notify_url", config.getNotify_url());
        sPara.put("_input_charset", AlipayConfig.input_charset);
        sPara.put("show_url", config.getShow_url());
        sPara.put("out_trade_no", out_trade_no + "_" + paymethod);
        sPara.put("subject", subject);
        sPara.put("body", body);
        sPara.put("total_fee", total_fee);
        sPara.put("paymethod", paymethod);
        sPara.put("defaultbank", defaultbank);
        sPara.put("anti_phishing_key", "");
        sPara.put("exter_invoke_ip", "");
        sPara.put("extra_common_param", out_trade_order_type);
        sPara.put("buyer_email", "");
        sPara.put("royalty_type", "");
        sPara.put("royalty_parameters", "");
        // 超时时间，超时交易自动被关闭,参数不接受小数点，取值范围：1m～15d
        sPara.put("it_b_pay", timeout);
        sPara.put("default_login", "N");
        sPara.put("goods_type", "1");
        if (StringUtil.isNotBlank(qr_pay_mode)) {
            sPara.put("qr_pay_mode", "4");
            sPara.put("qrcode_width", "200");
        }
        // String strUrl = "https://www.alipay.com/cooperate/gateway.do?";
        Map<String, String> sParaNew = AlipayBase.ParaFilter(sPara); // 除去数组中的空值和签名参数
        String mysign = AlipayBase.BuildMysign(sParaNew);// 生成签名结果
        // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        String arg = AlipayCore.createLinkString_urlencode(sParaNew, AlipayConfig.input_charset);
        // 得到请求URL
        String strUrl = ALIPAY_GATEWAY_NEW + arg + "sign=" + mysign + "&sign_type=" + AlipayConfig.sign_type;
        return strUrl;
    }

    /**
     * 财务明细数据 单笔交易查询接口
     *
     * @param accountNo 支付交易流水号
     * @return
     */
    public static TradeQuery singleTradeQuery(String tradeNo) {
        Map<String, String> sParaTemp = new HashMap<>();
        sParaTemp.put("partner", AlipayConfig.partnerId);
        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
        sParaTemp.put("service", "single_trade_query");
        sParaTemp.put("trade_no", tradeNo);
        try {
            String xml = AlipaySubmit.buildRequest("", "", sParaTemp);
            Map<String, String> maps = getTradeInfo(xml);
            if (maps.size() > 0) {
                TradeQuery record = new TradeQuery(maps);
                if (record.getAlipay_order_no().equalsIgnoreCase(tradeNo))
                    return record;
            }
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 财务明细数据 单笔交易查询接口
     *
     * @param tradeNo
     * @return
     */
    public static List<TradeQuery> singeAccountRecord(String accountNo, Date accountDate) {
        Map<String, String> sParaTemp = new HashMap<>();
        sParaTemp.put("service", "account.page.query");
        sParaTemp.put("partner", AlipayConfig.partnerId);
        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
        sParaTemp.put("trade_no", accountNo);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sParaTemp.put("gmt_start_time", sdf.format(accountDate));
        sParaTemp.put("gmt_end_time", sdf.format(DateUtil.getEndOfDay(accountDate)));
        sParaTemp.put("trans_code", "6001,4003,3011");
        try {
            String sHtmlText = AlipaySubmit.buildRequest("", "", sParaTemp);
            sHtmlText = sHtmlText.replaceAll("&lt;", "<");
            sHtmlText = sHtmlText.replaceAll("&gt;", ">");
            List<TradeQuery> recordList = new ArrayList<>();
            recordList = getCsvData(sHtmlText);
            return recordList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static List<TradeQuery> getCsvData(String xml) {
        List<TradeQuery> result = new ArrayList<>();
        Document doc;
        try {
            doc = DocumentHelper.parseText(xml);
            @SuppressWarnings("unchecked")
            List<Node> nodeList = doc.selectNodes("//alipay/*");
            for (Node node : nodeList) {
                // 截取部分不需要解析的信息
                if (node.getName().equals("is_success") && node.getText().equals("T")) {
                    // 判断是否有成功标示
                    @SuppressWarnings("unchecked")
                    List<Node> nodeList1 = doc.selectNodes("//response/account_page_query_result/account_log_list/*");
                    for (Node node1 : nodeList1) {
                        {
                            TradeQuery newRecode = new TradeQuery(node1);
                            result.add(newRecode);
                        }
                    }
                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static Map<String, String> getTradeInfo(String xml) {
        Map<String, String> result = new HashMap<>();
        Document doc;
        try {
            doc = DocumentHelper.parseText(xml);
            @SuppressWarnings("unchecked")
            List<Node> nodeList = doc.selectNodes("//alipay/*");
            for (Node node : nodeList) {
                // 截取部分不需要解析的信息
                if (node.getName().equals("is_success") && node.getText().equals("T")) {
                    // 判断是否有成功标示
                    @SuppressWarnings("unchecked")
                    List<Node> nodeList1 = doc.selectNodes("//response/trade/*");
                    for (Node node1 : nodeList1) {
                        result.put(node1.getName(), node1.getText());
                    }
                    break;
                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return result;
    }

}
