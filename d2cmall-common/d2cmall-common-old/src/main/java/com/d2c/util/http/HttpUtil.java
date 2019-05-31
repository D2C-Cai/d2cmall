package com.d2c.util.http;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.Map.Entry;

public class HttpUtil {

    private static final Log logger = LogFactory.getLog(HttpUtil.class);

    /**
     * HTTPS POST 请求
     *
     * @param url
     * @param xml
     * @return
     */
    public static String sendPostHttps(String url, Map<String, Object> params, Integer timeOut) {
        HttpPost post = null;
        CloseableHttpClient client = null;
        try {
            client = SSLUtil.createSSLClientDefault();
            // client = WebClientDevWrapper.wrapClient(client);
            post = new HttpPost(url);
            if (timeOut != null) {
                RequestConfig defaultRequestConfig = RequestConfig.custom().setSocketTimeout(timeOut)
                        .setConnectTimeout(timeOut).setConnectionRequestTimeout(timeOut)
                        .setStaleConnectionCheckEnabled(true).build();
                post.setConfig(defaultRequestConfig);
            }
            // 设置参数
            List<BasicNameValuePair> list = new ArrayList<>();
            Iterator<Entry<String, Object>> iterator = params.entrySet().iterator();
            while (iterator.hasNext()) {
                Entry<String, Object> elem = iterator.next();
                list.add(new BasicNameValuePair(elem.getKey(), String.valueOf(elem.getValue())));
            }
            if (list.size() > 0) {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, "utf-8");
                post.setEntity(entity);
            }
            HttpResponse response = client.execute(post);
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity resEntity = response.getEntity();
                return EntityUtils.toString(resEntity, "utf-8");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            post.releaseConnection();
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

    /**
     * HTTPS POST XML请求
     *
     * @param url
     * @param xml
     * @return
     */
    public static String sendPostXmlHttps(String url, String xml) {
        HttpPost post = null;
        CloseableHttpClient client = null;
        try {
            client = SSLUtil.createSSLClientDefault();
            // client = WebClientDevWrapper.wrapClient(client);
            post = new HttpPost(url);
            StringEntity myEntity = new StringEntity(xml, "utf-8");
            post.addHeader("Content-Type", "text/xml");
            post.setEntity(myEntity);
            HttpResponse response = client.execute(post);
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity resEntity = response.getEntity();
                return EntityUtils.toString(resEntity, "utf-8");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            post.releaseConnection();
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

    /**
     * 解析出url请求的路径，包括页面
     *
     * @param strURL url地址
     * @return url路径
     */
    public static String urlPage(String strURL) {
        String strPage = null;
        String[] arrSplit = null;
        strURL = strURL.trim().toLowerCase();
        arrSplit = strURL.split("[?]");
        if (strURL.length() > 0) {
            if (arrSplit.length > 1) {
                if (arrSplit[0] != null) {
                    strPage = arrSplit[0];
                }
            }
        }
        return strPage;
    }

    /**
     * 解析出url参数中的键值对 如 "index.jsp?Action=del&id=123"，解析出Action:del,id:123存入map中
     *
     * @param URL url地址
     * @return url请求参数部分
     */
    public static Map<String, String> urlRequest(String URL) {
        Map<String, String> mapRequest = new HashMap<>();
        String[] arrSplit = null;
        String strUrlParam = truncateUrlPage(URL);
        if (strUrlParam == null) {
            return mapRequest;
        }
        // 每个键值为一组
        arrSplit = strUrlParam.split("[&]");
        for (String strSplit : arrSplit) {
            String[] arrSplitEqual = null;
            arrSplitEqual = strSplit.split("[=]");
            // 解析出键值
            if (arrSplitEqual.length > 1) {
                // 正确解析
                mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);
            } else {
                if (arrSplitEqual[0] != "") {
                    // 只有参数没有值，不加入
                    mapRequest.put(arrSplitEqual[0], "");
                }
            }
        }
        return mapRequest;
    }

    /**
     * 去掉url中的路径，留下请求参数部分
     *
     * @param strURL url地址
     * @return url请求参数部分
     */
    private static String truncateUrlPage(String strURL) {
        String strAllParam = null;
        String[] arrSplit = null;
        strURL = strURL.trim().toLowerCase();
        arrSplit = strURL.split("[?]");
        if (strURL.length() > 1) {
            if (arrSplit.length > 1) {
                if (arrSplit[1] != null) {
                    strAllParam = arrSplit[1];
                }
            }
        }
        return strAllParam;
    }

    /**
     * 下载
     *
     * @param url
     * @param dir
     * @param fileName
     * @return
     */
    public static Boolean downloadFromUrl(String fileUrl, String savePath) {
        FileOutputStream fs = null;
        HttpURLConnection httpUrlConnection = null;
        try {
            URL url = new URL(fileUrl);
            httpUrlConnection = (HttpURLConnection) url.openConnection();
            httpUrlConnection.setUseCaches(false);
            httpUrlConnection.setConnectTimeout(30000);
            InputStream inStream = httpUrlConnection.getInputStream();
            File file = new File(savePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            fs = new FileOutputStream(savePath);
            int bytesum = 0;
            int byteread = 0;
            byte[] buffer = new byte[1024];
            while ((byteread = inStream.read(buffer)) != -1) {
                bytesum += byteread;
                fs.write(buffer, 0, byteread);
            }
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            if (fs != null) {
                try {
                    fs.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (httpUrlConnection != null) {
                httpUrlConnection.disconnect();
            }
        }
        return false;
    }

    /**
     * 返回Document 元素
     *
     * @param getUrl  要抓取的url
     * @param headers 请求头
     * @return
     */
    public static Document getDocument(String url, Map<String, String> headers) {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        // HttpClient
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
        HttpGet httpGet = new HttpGet("https://" + url);
        httpGet.addHeader("User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
        if (headers != null) {
            for (String key : headers.keySet()) {
                httpGet.addHeader(key, headers.get(key));
            }
        }
        Document document = null;
        try {
            // 执行get请求
            HttpResponse httpResponse = closeableHttpClient.execute(httpGet);
            // 获取响应消息实体
            HttpEntity entity = httpResponse.getEntity();
            document = Jsoup.parse(EntityUtils.toString(entity));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return document;
    }

    public static JSONObject receivePost(HttpServletRequest request) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        JSONObject jsonObj = JSONObject.parseObject(sb.toString(), Feature.OrderedField);
        return jsonObj;
    }

}
