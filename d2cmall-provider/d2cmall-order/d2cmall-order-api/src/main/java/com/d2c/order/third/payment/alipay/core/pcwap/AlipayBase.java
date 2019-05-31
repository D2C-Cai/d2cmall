package com.d2c.order.third.payment.alipay.core.pcwap;

import com.d2c.order.third.payment.alipay.sgin.MD5;
import org.apache.commons.httpclient.NameValuePair;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * import org.dom4j.Document;
 * import org.dom4j.DocumentException;
 * import org.dom4j.Node;
 * import org.dom4j.io.SAXReader;
 * <p>
 * import com.alipay.config.AlipayConfig;
 **/

/**
 * 功能：支付宝接口公用函数 详细：该页面是请求、通知返回两个文件所调用的公用函数核心处理文件，不需要修改 版本：3.0 修改日期：2010-07-16
 * '说明： '以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 * '该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class AlipayBase {

    /**
     * 功能：生成签名结果
     *
     * @param sArray
     *            要加密的数组
     * @param key
     *            安全校验码
     * @return 签名结果字符串
     */
    public static String BuildMysign(Map sArray) {
        String key = AlipayConfig.key;
        return BuildMysign(sArray, key);
    }

    /**
     * 功能：生成签名结果
     *
     * @param sArray
     *            要加密的数组
     * @param key
     *            安全校验码
     * @return 签名结果字符串
     */
    public static String BuildMysign(Map sArray, String key) {
        String prestr = AlipayCore.createLinkString(sArray); // 排序，把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        prestr = prestr + key; // 把拼接后的字符串再与安全校验码直接连接起来
        String mysign = MD5.md5(prestr);
        return mysign;
    }

    /**
     * 功能：除去数组中的空值和签名参数
     *
     * @param sArray
     *            加密参数组
     * @return 去掉空值与签名参数后的新加密参数组
     */
    public static Map ParaFilter(Map sArray) {
        List keys = new ArrayList(sArray.keySet());
        Map<String, String> sArrayNew = new HashMap<String, String>();
        for (int i = 0; i < keys.size(); i++) {
            String key = (String) keys.get(i);
            String value = "";
            if (sArray.get(key) instanceof String)
                value = (String) sArray.get(key);
            else if (sArray.get(key) instanceof String[])
                value = ((String[]) sArray.get(key))[0];
            if (value.equals("") || value == null
                    || key.equalsIgnoreCase("sign")
                    || key.equalsIgnoreCase("sign_type")) {
                continue;
            }
            sArrayNew.put(key, value);
        }
        return sArrayNew;
    }

    /**
     * 功能：写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     *
     * @param sWord
     *            要写入日志里的文本内容
     */
    public static void LogResult(String sWord) {
        // 该文件存在于和应用服务器 启动文件同一目录下，文件名是alipay log加服务器时间
        try {
            FileWriter writer = new FileWriter("D:\\alipay_log"
                    + System.currentTimeMillis() + ".txt");
            writer.write(sWord);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 功能：用于防钓鱼，调用接口query_timestamp来获取时间戳的处理函数 注意：远程解析XML出错，与服务器是否支持SSL等配置有关
     *
     * @param partner
     *            合作身份者ID
     * @return 时间戳字符串
     * @throws IOException
     * @throws DocumentException
     * @throws MalformedURLException
     */
    /**
     * public static String query_timestamp(String partner) throws
     * MalformedURLException, DocumentException, IOException { String strUrl =
     * "https://mapi.alipay.com/gateway.do?service=query_timestamp&partner="
     * +partner; StringBuffer buf1 = new StringBuffer(); SAXReader reader = new
     * SAXReader(); Document doc = reader.read(new URL(strUrl).openStream());
     *
     * List<Node> nodeList = doc.selectNodes("//alipay/*");
     *
     * for (Node node : nodeList) { // 截取部分不需要解析的信息 if
     * (node.getName().equals("is_success") && node.getText().equals("T")) { //
     * 判断是否有成功标示 List<Node> nodeList1 =
     * doc.selectNodes("//response/timestamp/*"); for (Node node1 : nodeList1) {
     * buf1.append(node1.getText()); } } }
     *
     * return buf1.toString(); }
     **/
    /**
     * MAP类型数组转换成NameValuePair类型
     *
     * @param properties
     *            MAP类型数组
     * @return NameValuePair类型数组
     */
    public static NameValuePair[] generatNameValuePair(
            Map<String, String> properties) {
        NameValuePair[] nameValuePair = new NameValuePair[properties.size()];
        int i = 0;
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            nameValuePair[i++] = new NameValuePair(entry.getKey(),
                    entry.getValue());
        }
        return nameValuePair;
    }

}
