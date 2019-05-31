package com.d2c.backend.rest.crm.demo;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ApiOauth {

    public static final String D2C_HTTP = "http://192.168.5.19:8099";// 官网后台域名
    public static final String SECRET_KEY = "6d7da05cc8874640ba6aac060192dee2";// 签名密匙
    private static ApiOauth d2cApiOauth = null;
    private List<Map<String, Object>> params;
    private String apiPath;

    private ApiOauth() {
    }

    public static ApiOauth getInstance() {
        if (d2cApiOauth == null) {
            d2cApiOauth = new ApiOauth();
        }
        return d2cApiOauth;
    }

    public List<Map<String, Object>> getParams(String apiPath) {
        this.apiPath = apiPath;
        params = new ArrayList<Map<String, Object>>();
        return params;
    }

    public JSONObject invoke() throws Exception {
        if (params.size() == 0) {
            return null;
        }
        JSONArray jsonArray = new JSONArray();
        for (Map<String, Object> map : params) {
            jsonArray.put(new JSONObject(map));
        }
        String md5Str = DigestUtils.md5Hex(jsonArray.toString() + SECRET_KEY);
        JSONObject result = new JSONObject();
        result.put("md5Str", md5Str);
        result.put("data", jsonArray);
        CloseableHttpResponse response2 = postJson(D2C_HTTP + apiPath, result.toString());
        JSONObject responseData = null;
        try {
            HttpEntity entity2 = response2.getEntity();
            responseData = getJosn(entity2.getContent());
            EntityUtils.consume(entity2);
        } finally {
            response2.close();
        }
        return responseData;
    }

    public JSONObject getJosn(InputStream inputStream) throws IOException, JSONException {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        if (!StringUtils.hasText(sb)) {
            sb.append("{}");
        }
        return new JSONObject(sb.toString());
    }

    public CloseableHttpResponse postJson(String url, String json) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");
        StringEntity se = new StringEntity(json, "UTF-8");
        se.setContentType("text/json");
        se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        httpPost.setEntity(se);
        return httpclient.execute(httpPost);
    }

}
