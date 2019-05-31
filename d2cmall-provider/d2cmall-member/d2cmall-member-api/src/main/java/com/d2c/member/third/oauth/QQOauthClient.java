package com.d2c.member.third.oauth;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.client.RestTemplate;

public class QQOauthClient {

    private static RestTemplate restTemplate = new RestTemplate();
    private String QQ_APPKEY;
    private String QQ_APPSECRET;
    private String QQ_CALLBACK;

    public String getCode() {
        return "redirect:https://graph.qq.com/oauth2.0/authorize?response_type=code&client_id=" + QQ_APPKEY
                + "&redirect_uri=" + QQ_CALLBACK;
    }

    public String getAccessToken(String code) {
        String url = "https://graph.qq.com/oauth2.0/token?grant_type=authorization_code&client_id=" + QQ_APPKEY
                + "&client_secret=" + QQ_APPSECRET + "&code=" + code + "&redirect_uri=" + QQ_CALLBACK;
        try {
            String json = restTemplate.getForObject(url, String.class);
            return json.substring(13, 45);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getOpenId(String accessToken) {
        String url = "https://graph.qq.com/oauth2.0/me?access_token=" + accessToken;
        try {
            String json = restTemplate.getForObject(url, String.class);
            JSONObject jsonObject = JSONObject.parseObject(json.substring(9, json.length() - 3));
            return String.valueOf(jsonObject.get("openid"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public JSONObject getUser(String accessToken, String openId) {
        String url = "https://graph.qq.com/user/get_user_info?access_token=" + accessToken + "&oauth_consumer_key="
                + QQ_APPKEY + "&openid=" + openId;
        try {
            String json = restTemplate.getForObject(url, String.class);
            return JSONObject.parseObject(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getQQ_APPKEY() {
        return QQ_APPKEY;
    }

    public void setQQ_APPKEY(String qQ_APPKEY) {
        QQ_APPKEY = qQ_APPKEY;
    }

    public String getQQ_APPSECRET() {
        return QQ_APPSECRET;
    }

    public void setQQ_APPSECRET(String qQ_APPSECRET) {
        QQ_APPSECRET = qQ_APPSECRET;
    }

    public String getQQ_CALLBACK() {
        return QQ_CALLBACK;
    }

    public void setQQ_CALLBACK(String qQ_CALLBACK) {
        QQ_CALLBACK = qQ_CALLBACK;
    }

}
