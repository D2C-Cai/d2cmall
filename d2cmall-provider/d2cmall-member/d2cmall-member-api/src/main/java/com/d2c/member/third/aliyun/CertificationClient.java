package com.d2c.member.third.aliyun;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * 身份认证
 *
 * @author wwn
 */
public class CertificationClient {

    private static RestTemplate restTemplate = new RestTemplate();
    private static CertificationClient instance = null;
    String appcode = "916d82098bd646e6a147d2627c7f191d";
    String url = "http://1.api.apistore.cn/idcard3";

    public static CertificationClient getInstance() {
        if (instance == null) {
            instance = new CertificationClient();
        }
        return instance;
    }

    public static void main(String[] args) {
        System.out.println(new CertificationClient().doCertificate("谢依秋", "410502198411199003"));
    }

    public String doCertificate(String realName, String cardNo) {
        String requestUrl = url + "?realName=" + realName + "&cardNo=" + cardNo;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "APPCODE " + appcode);
        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(headers);
        JSONObject obj = restTemplate.postForObject(requestUrl, entity, JSONObject.class);
        if (obj != null && obj.get("error_code").equals(0)) {
            return "success";
        }
        return obj == null ? "" : obj.get("reason").toString();
    }

}
