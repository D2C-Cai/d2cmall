package com.d2c.order.third.payment.gongmall.client;

import com.alibaba.fastjson.JSONObject;
import com.d2c.order.third.payment.gongmall.core.GongmallConfig;
import com.d2c.order.third.payment.gongmall.sign.SignHelper;
import com.d2c.util.serial.SerialNumUtil;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

public class GongmallClient {

    private static RestTemplate restTemplate = new RestTemplate();
    private static GongmallClient instance = null;

    public static GongmallClient getInstance() {
        if (instance == null) {
            instance = new GongmallClient();
        }
        return instance;
    }

    /**
     * 修改银行卡号
     *
     * @param config
     * @param name
     * @param mobile
     * @param identity
     * @param oldBankName
     * @param oldBankAccount
     * @param newBankName
     * @param newBankAccount
     * @return
     */
    public JSONObject doBankAccount(GongmallConfig config, String name, String mobile, String identity,
                                    String oldBankName, String oldBankAccount, String newBankName, String newBankAccount) {
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("name", name);
        map.put("mobile", mobile);
        map.put("identity", identity);
        map.put("oldBankName", oldBankName);
        map.put("oldBankAccount", oldBankAccount);
        map.put("newBankName", newBankName);
        map.put("newBankAccount", newBankAccount);
        this.setCommonParam(map, config.getAppkey());
        String sign = SignHelper.getSign(map, config.getAppSecret());
        map.put("sign", sign);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            postParameters.add(entry.getKey(), entry.getValue());
        }
        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(postParameters, headers);
        JSONObject json = restTemplate.postForObject(config.getApiUrl() + "/api/employee/syncBankAccount", entity,
                JSONObject.class);
        return json;
    }

    /**
     * 申请提现
     *
     * @param config
     * @param requestId
     * @param mobile
     * @param name
     * @param amount
     * @param identity
     * @param bankAccount
     * @param dateTime
     * @param remark
     * @return
     */
    public JSONObject doWithdraw(GongmallConfig config, String requestId, String mobile, String name, BigDecimal amount,
                                 String identity, String bankAccount, String dateTime, String remark) {
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("requestId", requestId);
        map.put("mobile", mobile);
        map.put("name", name);
        map.put("amount", String.valueOf(amount));
        map.put("identity", identity);
        map.put("bankAccount", bankAccount);
        map.put("dateTime", dateTime);
        this.setCommonParam(map, config.getAppkey());
        String sign = SignHelper.getSign(map, config.getAppSecret());
        map.put("sign", sign);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            postParameters.add(entry.getKey(), entry.getValue());
        }
        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(postParameters, headers);
        JSONObject json = restTemplate.postForObject(config.getApiUrl() + "/api/withdraw/doWithdraw", entity,
                JSONObject.class);
        return json;
    }

    /**
     * 查询电签结果
     *
     * @param config
     * @param mobile
     * @param name
     * @param identity
     * @return
     */
    public JSONObject getContractResult(GongmallConfig config, String mobile, String name, String identity) {
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("mobile", mobile);
        map.put("name", name);
        map.put("identity", identity);
        this.setCommonParam(map, config.getAppkey());
        String sign = SignHelper.getSign(map, config.getAppSecret());
        map.put("sign", sign);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            postParameters.add(entry.getKey(), entry.getValue());
        }
        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(postParameters, headers);
        JSONObject json = restTemplate.postForObject(config.getApiUrl() + "/api/employee/getContractStatus", entity,
                JSONObject.class);
        return json;
    }

    /**
     * 查询提现结果
     *
     * @param config
     * @param requestId
     * @return
     */
    public JSONObject getWithdrawResult(GongmallConfig config, String requestId) {
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("requestId", requestId);
        this.setCommonParam(map, config.getAppkey());
        String sign = SignHelper.getSign(map, config.getAppSecret());
        map.put("sign", sign);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            postParameters.add(entry.getKey(), entry.getValue());
        }
        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(postParameters, headers);
        JSONObject json = restTemplate.postForObject(config.getApiUrl() + "/api/withdraw/getWithdrawResult", entity,
                JSONObject.class);
        return json;
    }

    /**
     * 查询企业当前余额
     *
     * @param config
     * @return
     */
    public JSONObject getBalance(GongmallConfig config) {
        Map<String, String> map = new LinkedHashMap<String, String>();
        this.setCommonParam(map, config.getAppkey());
        String sign = SignHelper.getSign(map, config.getAppSecret());
        map.put("sign", sign);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            postParameters.add(entry.getKey(), entry.getValue());
        }
        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(postParameters, headers);
        JSONObject json = restTemplate.postForObject(config.getApiUrl() + "/api/company/getBalance", entity,
                JSONObject.class);
        return json;
    }

    private Map<String, String> setCommonParam(Map<String, String> map, String appKey) {
        map.put("appKey", appKey);
        map.put("nonce", SerialNumUtil.getRandomNumber(32));
        map.put("timestamp", String.valueOf(System.currentTimeMillis()));
        return map;
    }

}
