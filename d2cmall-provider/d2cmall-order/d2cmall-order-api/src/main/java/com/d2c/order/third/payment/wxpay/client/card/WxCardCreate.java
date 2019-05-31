package com.d2c.order.third.payment.wxpay.client.card;

import com.d2c.util.http.SSLUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class WxCardCreate {

    protected static final Log logger = LogFactory.getLog(WxCardCreate.class);

    public static String doPostJson(String url, String body) {
        // 创建连接
        CloseableHttpClient client = null;
        HttpPost post = null;
        try {
            client = SSLUtil.createSSLClientDefault();
            post = new HttpPost(url);
            StringEntity myEntity = new StringEntity(body, "utf-8");
            post.addHeader("Content-Type", "text/json;utf-8");
            post.setEntity(myEntity);
            HttpResponse response = client.execute(post);
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity resEntity = response.getEntity();
                return EntityUtils.toString(resEntity, "utf-8");
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (post != null) {
                post.releaseConnection();
            }
            if (client != null) {
                try {
                    client.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

}
