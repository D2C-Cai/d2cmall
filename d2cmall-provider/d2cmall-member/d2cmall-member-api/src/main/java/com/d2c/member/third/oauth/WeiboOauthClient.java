package com.d2c.member.third.oauth;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class WeiboOauthClient {

    private static RestTemplate restTemplate = new RestTemplate();
    private String WEIBO_APPKEY;
    private String WEIBO_APPSECRET;
    private String WEIBO_CALLBACK;

    public String getCode() {
        return "redirect:https://api.weibo.com/oauth2/authorize?response_type=code&client_id=" + WEIBO_APPKEY
                + "&redirect_uri=" + WEIBO_CALLBACK;
    }

    public String getCodeUrl(String url) {
        String redirect_uri = "";
        url = url.replaceAll("&", "__");
        redirect_uri = WEIBO_CALLBACK + "?path=" + url;
        return "https://api.weibo.com/oauth2/authorize?response_type=code&client_id=" + WEIBO_APPKEY + "&redirect_uri="
                + redirect_uri;
    }

    public String getAccessToken(String code) {
        String url = "https://api.weibo.com/oauth2/access_token";
        Map<String, String> map = new HashMap<>();
        map.put("client_id", WEIBO_APPKEY);
        map.put("client_secret", WEIBO_APPSECRET);
        map.put("grant_type", "authorization_code");
        map.put("code", code);
        map.put("redirect_uri", WEIBO_CALLBACK);
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<>();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                postParameters.add(entry.getKey(), entry.getValue());
            }
            HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(postParameters, headers);
            String json = restTemplate.postForObject(url, entity, String.class);
            JSONObject jsonObj = JSONObject.parseObject(json);
            return String.valueOf(jsonObj.get("access_token"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getOpenId(String accessToken) {
        String url = "https://api.weibo.com/2/account/get_uid.json?access_token=" + accessToken;
        try {
            String json = restTemplate.getForObject(url, String.class);
            JSONObject jsonObj = JSONObject.parseObject(json);
            return String.valueOf(jsonObj.get("uid"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public JSONObject getUser(String accessToken, String uid) {
        String url = "https://api.weibo.com/2/users/show.json?uid=" + uid + "&access_token=" + accessToken;
        try {
            String json = restTemplate.getForObject(url, String.class);
            JSONObject jsonObj = JSONObject.parseObject(json);
            return jsonObj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getWEIBO_APPKEY() {
        return WEIBO_APPKEY;
    }

    public void setWEIBO_APPKEY(String wEIBO_APPKEY) {
        WEIBO_APPKEY = wEIBO_APPKEY;
    }

    public String getWEIBO_APPSECRET() {
        return WEIBO_APPSECRET;
    }

    public void setWEIBO_APPSECRET(String wEIBO_APPSECRET) {
        WEIBO_APPSECRET = wEIBO_APPSECRET;
    }

    public String getWEIBO_CALLBACK() {
        return WEIBO_CALLBACK;
    }

    public void setWEIBO_CALLBACK(String wEIBO_CALLBACK) {
        WEIBO_CALLBACK = wEIBO_CALLBACK;
    }

}
