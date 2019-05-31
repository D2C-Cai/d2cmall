package com.d2c.common.core.http;

import com.d2c.common.base.exception.HttpException;
import com.d2c.common.base.utils.ConvertUt;
import com.d2c.common.base.utils.HttpUt;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class RestHelper {

    private static RestTemplate restTemplate = new RestTemplate();

    public static <T> T get(String url, Map<String, Object> headers, Class<T> resType, Object... uriVariables) {
        try {
            return restTemplate.exchange(url, HttpMethod.GET, getHttpHeader(headers, null), resType, uriVariables).getBody();
        } catch (Exception e) {
            throw new HttpException("GET消息发送异常...", e);
        }
    }

    public static <T> T get(String url, Map<String, Object> headers, Class<T> resType, Map<String, Object> params) {
        try {
            return restTemplate.exchange(HttpUt.getUrl(url, params), HttpMethod.GET, getHttpHeader(headers, null), resType).getBody();
        } catch (Exception e) {
            throw new HttpException("GET消息发送异常...", e);
        }
    }

    public static <T> T get(String url, Class<T> resType, Object... uriVariables) {
        try {
            return restTemplate.getForObject(url, resType, uriVariables);
        } catch (Exception e) {
            throw new HttpException("GET消息发送异常...", e);
        }
    }

    public static <T> T get(String url, Class<T> resType, Map<String, Object> params) {
        try {
            return restTemplate.getForObject(HttpUt.getUrl(url, params), resType, params);
        } catch (Exception e) {
            throw new HttpException("GET消息发送异常...", e);
        }
    }

    public static <T> T post(String url, Object request, Class<T> resType, Object... uriVariables) {
        try {
            return restTemplate.postForObject(url, request, resType, uriVariables);
        } catch (Exception e) {
            throw new HttpException("GET消息发送异常...", e);
        }
    }

    public static <T> T post(String url, Map<String, Object> headers, Object body, Class<T> resType, Object... uriVariables) {
        try {
            return restTemplate.postForObject(url, getHttpHeader(headers, body), resType, uriVariables);
        } catch (Exception e) {
            throw new HttpException("GET消息发送异常...", e);
        }
    }
    //************************* private ****************************

    /**
     * 获取HttpHeader
     */
    private static <T> HttpEntity<T> getHttpHeader(Map<String, Object> headers, T body) {
        HttpHeaders header = new HttpHeaders();
        if (headers != null && !headers.isEmpty()) {
            headers.forEach((k, v) -> header.add(k, ConvertUt.convertType(v, String.class)));
        }
        return new HttpEntity<>(body, header);
    }

}
