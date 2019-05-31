package com.d2c.logger.third.email;

import com.alibaba.fastjson.JSONObject;
import com.d2c.logger.third.email.common.EmailEntity;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class EmailClient {

    private static final Logger logger = LoggerFactory.getLogger(EmailClient.class);
    private static final String[] API_USER_ARRAY = new String[]{"newslettera", "newsletterb", "newsletterc",
            "newsletterd", "newslettere", "newsletterf"};
    private static final String API_KEY = "N00fXGeMfUK7BOjX";
    private static final String FROM = "app@poster.d2cmall.com";
    private static final String FROMNAME = "D2C-全球好设计";
    private static final String SENDCLODURL = "http://sendcloud.sohu.com/webapi/mail.send.json";

    static {
    }

    private EmailClient() {
    }

    /**
     * 普通发送
     */
    public static boolean send(String to, String subject, String content) {
        return sendOut(to, subject, content);
    }

    public static boolean sendOut(String to, String subject, String content) {
        Random random = new Random();
        int index = random.nextInt(API_USER_ARRAY.length);
        EmailEntity email = new EmailEntity();
        email.setApi_user(API_USER_ARRAY[index]);
        email.setApi_key(API_KEY);
        email.setFrom(FROM);
        email.setFromname(FROMNAME);
        email.setTo(to);
        email.setSubject(subject);
        email.setHtml(content);
        return sendOutByPost(email);
    }

    // 普通Get
    @SuppressWarnings("resource")
    public static boolean sendOutByGet(EmailEntity email) {
        String getURL = SENDCLODURL + "?" + email.getXML();
        HttpClient client = new DefaultHttpClient();
        try {
            HttpGet request = new HttpGet(getURL);
            HttpResponse response = client.execute(request);
            if (response.getStatusLine().getStatusCode() == 200) {
                String result = EntityUtils.toString(response.getEntity());
                JSONObject json = JSONObject.parseObject(result);
                if (json.get("message").toString().equals("success")) {
                    return true;
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            client.getConnectionManager().shutdown();
        }
        return false;
    }

    // 普通Post
    @SuppressWarnings("resource")
    public static boolean sendOutByPost(EmailEntity email) {
        String PostURL = SENDCLODURL;
        String xml = email.getXML();
        HttpClient client = new DefaultHttpClient();
        try {
            HttpPost post = new HttpPost(PostURL);
            StringEntity myEntity = new StringEntity(xml, "utf-8");
            myEntity.setContentType("application/x-www-form-urlencoded");
            post.setEntity(myEntity);
            HttpResponse response = client.execute(post);
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity resEntity = response.getEntity();
                String result = EntityUtils.toString(resEntity, "utf-8");
                JSONObject json = JSONObject.parseObject(result);
                if (json.get("message").toString().equals("success")) {
                    return true;
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            client.getConnectionManager().shutdown();
        }
        return false;
    }
    //
    // // 模板发送,map为发送内容
    // public String sendOutByTemplate(EmailEntity email, Map<String, String>
    // map) {
    // if (email.getUse_maillist() == null ||
    // !email.getUse_maillist().equals("true")) {
    // JSONObject json = new JSONObject();
    // JSONArray to = new JSONArray();
    // to.add(email.getTo());
    // email.setTo(null);
    // json.put("to", to);
    // JSONObject sub = new JSONObject();
    // for (String key : map.keySet()) {
    // JSONArray variable = new JSONArray();
    // variable.add(map.get(key));
    // sub.put(key, variable);
    // }
    // json.put("sub", sub);
    // email.setSubstitution_vars(json.toString());
    // }
    // String PostURL = sendTemplateURL;
    // String xml = email.getXML();
    // try {
    // HttpClient client = new DefaultHttpClient();
    // HttpPost post = new HttpPost(PostURL);
    // StringEntity myEntity = new StringEntity(xml, "utf-8");
    // myEntity.setContentType("application/x-www-form-urlencoded");
    // post.setEntity(myEntity);
    // HttpResponse response = client.execute(post);
    // if (response.getStatusLine().getStatusCode() == 200) {
    // HttpEntity resEntity = response.getEntity();
    // return EntityUtils.toString(resEntity, "utf-8");
    // }
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // return null;
    // }

    public static void main(String[] agrs) {
        System.out.println(EmailClient.send("709931138@qq.com", "这是一封测试邮件", "你好！顾客"));
    }

}
