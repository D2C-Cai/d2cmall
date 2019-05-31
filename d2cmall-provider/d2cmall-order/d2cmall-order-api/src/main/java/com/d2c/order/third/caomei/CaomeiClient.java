package com.d2c.order.third.caomei;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.d2c.order.model.Order;
import com.d2c.order.model.OrderItem;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CaomeiClient {

    private static final String siteID = "D2C";
    private static final String LangID = "80";// 简体中文
    private static final String Currency = "CNY";// 货币，RMB
    private static Logger logger = Logger.getLogger("StrawberryClient");
    private static CaomeiClient instance = null;
    private static SerializeConfig mapping;

    static {
        mapping = new SerializeConfig();
        mapping.put(Date.class, new SimpleDateFormatSerializer("MM/dd/yyyy"));
    }

    private CaomeiClient() {
    }

    public static CaomeiClient getInstance() {
        if (instance == null) {
            instance = new CaomeiClient();
        }
        return instance;
    }

    public static void main(String[] args) {
        try {
            // System.out.println(getInstance().queryExpress("Q15399430435934110"));
            getInstance().queryExpress("Q15514519905107084");
            // getInstance().createOrder(null, null, null);
            // send("http://affiliate.strawberrynet.com/affiliate/cgi/statusResponse.aspx?siteID=D2C&refNo=Q123456");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 获取商品信息
     *
     * @param prodId
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public CaomeiProduct queryProd(String prodId) throws Exception {
        if (prodId == null) {
            throw new Exception("草莓网商品ID不能为空");
        }
        String url = "http://affiliate.strawberrynet.com/affiliate/cgi/directListXML.aspx?" + "siteID=" + siteID
                + "&langID=" + LangID + "&currency=" + Currency + "&prodID=" + prodId;
        String response = send(url);
        Document doc = DocumentHelper.parseText(response);
        Element rootElt = doc.getRootElement(); // 获取根节点
        Element Item = rootElt.element("Item"); // 我们通过prodId查询只会返回一个对象
        if (Item == null) {
            return null;
        }
        Iterator<Element> iters = Item.elementIterator();// 获取该对象的键值对集合
        JSONObject obj = new JSONObject();
        while (iters.hasNext()) {// 遍历对象的键值
            Element itemEle = (Element) iters.next();
            String name = itemEle.getName();
            String value = itemEle.getStringValue();
            if ("null".equals(value.trim()) && ("SellingPrice".equals(name) || "RefPrice".equals(name))) {
                value = "0";
            }
            if ("SellingPrice".equals(name) || "RefPrice".equals(name)) {
                value = value.replace(",", "");
            }
            obj.put(name, value);
        }
        String jsonStr = JSON.toJSONString(obj, mapping);
        CaomeiProduct prod = JSON.parseObject(jsonStr, CaomeiProduct.class);
        return prod;
    }

    /**
     * 库存查询
     *
     * @param prodId
     * @return
     * @throws Exception
     */
    public Integer queryStock(String prodId) throws Exception {
        if (prodId == null) {
            throw new Exception("草莓网商品ID不能为空");
        }
        String url = "http://affiliate.strawberrynet.com/affiliate/cgi/QTYResponse.asp?ProdId=" + prodId;
        String response = send(url);
        Document doc = DocumentHelper.parseText(response);
        Element rootElt = doc.getRootElement(); // 获取根节点
        return Integer.parseInt(rootElt.getStringValue());
    }

    /**
     * 下单
     *
     * @param order
     * @param list
     * @param idno
     * @throws Exception
     */
    public void createOrder(Order order, List<OrderItem> list, String idno) throws Exception {
        String url = "http://affiliate.strawberrynet.com/affiliate/CGI/receiveResponse.aspx?"
                + toParam(order, list, idno);
        String response = send(url);
        Document doc = DocumentHelper.parseText(response);
        Element rootElt = doc.getRootElement(); // 获取根节点
        Element element = rootElt.element("status");
        if (element != null && "Success".equals(element.getStringValue())) {
            // OK
        } else {
            throw new Exception(response);
        }
    }

    /**
     * 查询物流
     *
     * @param orderSn
     * @return
     * @throws Exception
     */
    public Map<String, String> queryExpress(String orderSn) throws Exception {
        String url = "http://affiliate.strawberrynet.com/affiliate/cgi/statusResponse.aspx?siteID=D2C&refNo=" + orderSn
                + "&remark=1&product=1";
        String response = send(url);
        System.out.println(response);
        Document doc = DocumentHelper.parseText(response);
        Element rootElt = doc.getRootElement(); // 获取根节点
        if (StringUtils.isBlank(doc.getStringValue())) {
            return null;
        }
        String expressNo = null;
        String expressCom = null;
        Map<String, String> map = null;
        for (int i = 1; i < 4; i++) {
            Element expressNoEle = rootElt.element("Order").element("ShipmentRefNo" + i);
            Element expressComEle = rootElt.element("Order").element("Courier" + i);
            if (expressNoEle != null && expressComEle != null) {
                expressNo = expressNoEle.getStringValue();
                expressCom = expressComEle.getStringValue();
                map = new HashMap<>();
                map.put("expressCom", expressCom);
                map.put("expressNo", expressNo);
                return map;
            }
        }
        return null;
    }

    private String toParam(Order order, List<OrderItem> list, String idno) throws UnsupportedEncodingException {
        StringBuilder productsSB = new StringBuilder();
        list.forEach(oi -> {
            Integer quantity = oi.getProductQuantity();
            do {
                productsSB.append(oi.getProductSkuSn().split("-")[0]).append(",");
            } while ((--quantity) > 0);
        });
        String products = productsSB.substring(0, productsSB.length() - 1).toString();
        String addr1 = order.getAddress();
        String addr2 = "";
        String addr3 = "";
        if (order.getAddress().length() > 15) {
            addr1 = order.getAddress().substring(0, 15);
            addr2 = order.getAddress().substring(15);
            if (order.getAddress().length() > 30) {
                addr2 = order.getAddress().substring(15, 30);
                addr3 = order.getAddress().substring(30);
            }
        }
        return "siteID=" + siteID + "&firstname=" + order.getReciver() + "&addr1=" + URLEncoder.encode(addr1, "UTF-8")
                + "&addr2=" + URLEncoder.encode(addr2, "UTF-8") + "&addr3=" + URLEncoder.encode(addr3, "UTF-8")
                + "&city=" + order.getCity() + "&state=" + order.getProvince() + "&country=" + "China" + "&mob="
                + order.getMemberMobile() + "&tel=" + order.getMemberMobile() + "&products=" + products + "&idno="
                + idno + "&dp=1&affiliateref=" + order.getOrderSn() + "&postalcode=" + 330000 + "&langId=190";
    }

    /**
     * =======================================================================================
     */
    private String send(String url) throws Exception {
        HttpGet httpRequst = new HttpGet(url);
        // 发送请求
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            CloseableHttpResponse httpResponse = httpclient.execute(httpRequst);
            HttpEntity httpEntity = httpResponse.getEntity();
            String result = EntityUtils.toString((org.apache.http.HttpEntity) httpEntity);// 取出应答字符串
            logger.log(Level.INFO, "[Thirdpart CallAPIDemo][result] " + result);
            return result;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "[[Thirdpart Order][result] " + e);
            throw new Exception(e.getMessage());
        }
    }

}
