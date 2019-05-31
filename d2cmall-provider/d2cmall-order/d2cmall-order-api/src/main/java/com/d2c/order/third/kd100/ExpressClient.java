package com.d2c.order.third.kd100;

import com.d2c.common.base.utils.security.MD5Util;
import com.d2c.common.core.helper.SpringHelper;
import com.d2c.order.property.OrderProperties;
import com.d2c.order.third.kd100.core.JacksonHelper;
import com.d2c.order.third.kd100.core.NoticeResponse;
import com.d2c.order.third.kd100.core.TaskRequest;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

public class ExpressClient {

    private static final Log logger = LogFactory.getLog(ExpressClient.class);
    /**
     * 秘钥
     */
    private static final String apikey = "ujLDjZDG3968";
    /**
     * 快递公司分配的公司编码
     */
    private static final String customer = "F1E3CD4FEF907786BD11643013464B6E";
    private static ExpressClient instance = null;
    private OrderProperties orderProperties;

    private ExpressClient() {
        orderProperties = SpringHelper.getBean(OrderProperties.class);
    }

    public static ExpressClient getInstance() {
        if (instance == null) {
            instance = new ExpressClient();
        }
        return instance;
    }

    /**
     * 查询订单
     *
     * @param com
     * @param num
     * @return
     */
    public static String query(String com, String num) {
        String param = "{\"com\":\"" + com + "\",\"num\":\"" + num + "\"}";
        String sign = MD5Util.encodeMD5Hex(param + apikey + customer);
        HashMap<String, String> p = new HashMap<String, String>();
        p.put("sign", sign);
        p.put("param", param);
        p.put("customer", customer);
        String result = "";
        try {
            result = postData("http://www.kuaidi100.com/poll/query.do", p, "UTF-8");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * 推送数据
     *
     * @param url
     * @param params
     * @param codePage 编码
     * @return
     * @throws Exception
     */
    private synchronized static String postData(String url, Map<String, String> params, String codePage)
            throws Exception {
        final HttpClient httpClient = new HttpClient();
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(25 * 1000);
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(30 * 1000);
        final PostMethod method = new PostMethod(url);
        if (params != null) {
            method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, codePage);
            method.setRequestBody(assembleRequestParams(params));
        }
        String result = "";
        try {
            httpClient.executeMethod(method);
            result = new String(method.getResponseBody(), codePage);
        } catch (final Exception e) {
            throw e;
        } finally {
            method.releaseConnection();
        }
        return result;
    }

    /**
     * 组装http请求参数
     *
     * @param params
     * @param menthod
     * @return
     */
    private synchronized static NameValuePair[] assembleRequestParams(Map<String, String> data) {
        final List<NameValuePair> nameValueList = new ArrayList<NameValuePair>();
        Iterator<Map.Entry<String, String>> it = data.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
            nameValueList.add(new NameValuePair((String) entry.getKey(), (String) entry.getValue()));
        }
        return nameValueList.toArray(new NameValuePair[nameValueList.size()]);
    }

    public static void main(String args[]) {
        try {
            String result = query("yuantong", "807254682471");
            System.out.println(result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 推送订单
     *
     * @param com 快递公司对应编码
     * @param num 订单号
     * @return
     * @throws Exception
     */
    public String pushExpress(String com, String num) throws Exception {
        if (StringUtils.isNotEmpty(num)) {
            TaskRequest req = new TaskRequest();
            req.setCompany(com);
            req.setNumber(num);
            req.getParameters().put("callbackurl", orderProperties.getPcUrl() + "/kuaidi100");
            req.setKey(apikey);
            HashMap<String, String> p = new HashMap<String, String>();
            p.put("schema", "json");
            p.put("param", JacksonHelper.toJSON(req));
            String result = "";
            NoticeResponse resp = new NoticeResponse();
            int count = 0;
            // 由服务器造成的推送不成功就推三次
            if ((resp != null && StringUtils.isNotBlank(resp.getReturnCode()) && resp.getReturnCode().equals("500")
                    && count < 3) || count == 0) {
                result = postData("http://www.kuaidi100.com/poll", p, "UTF-8");
                resp = JacksonHelper.fromJSON(result, NoticeResponse.class);
                count++;
            }
            return resp.getReturnCode();
        } else
            return null;
    }

}
