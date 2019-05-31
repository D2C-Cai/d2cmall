package com.d2c.logger.third.sms.dahantc;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.base.utils.security.MD5Ut;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;

public class DahanTCSMSClient {

    static String url = "http://www.dh3t.com/json/sms/Submit";
    static String account = "dh4610";
    static String password = "d7J50N43";
    private static DahanTCSMSClient clinet = null;
    private static RestTemplate restTemplate = new RestTemplate();

    private DahanTCSMSClient() {
    }

    public synchronized static DahanTCSMSClient getInstance() {
        if (clinet == null) {
            clinet = new DahanTCSMSClient();
        }
        return clinet;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        DahanTCSMSClient.getInstance().sendSMS("18969553171", "您好！您的验证码为4125，验证码20分钟内有效。如果出现异常请联系客服。D2C提示您，注意保密验证码",
                null, null);
    }

    public int sendSMS(String phones, String content, String subcode, String sendtime)
            throws UnsupportedEncodingException {
        JSONObject postParameters = new JSONObject();
        postParameters.put("account", account);
        postParameters.put("password", MD5Ut.md5(password));
        postParameters.put("msgid", "");
        postParameters.put("phones", phones);
        postParameters.put("content", content);
        postParameters.put("sign", null);
        postParameters.put("subcode", subcode);
        postParameters.put("sendtime", sendtime);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json;charset=UTF-8");
        HttpEntity<byte[]> entity = new HttpEntity<>(postParameters.toJSONString().getBytes("utf-8"), headers);
        byte[] bytes = restTemplate.postForObject(url, entity, byte[].class);
        JSONObject json = JSONObject.parseObject(new String(bytes));
        if (json != null && "0".equals(json.get("result"))) {
            return 1;
        }
        return 0;
    }

}
