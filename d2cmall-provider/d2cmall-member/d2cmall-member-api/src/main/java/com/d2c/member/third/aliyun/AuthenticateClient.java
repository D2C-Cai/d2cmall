package com.d2c.member.third.aliyun;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.ZhimaCustomerCertificationInitializeRequest;
import com.alipay.api.response.ZhimaCustomerCertificationInitializeResponse;
import com.d2c.common.base.exception.BusinessException;

public class AuthenticateClient {

    public static AuthenticateClient instance = null;
    String appId = "2016110802634196";
    String url = "https://openapi.alipay.com/gateway.do";
    String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAk9/2/3qMJuUqd68pnNC/sZGKesDZS8SdUvEVOuhXdAv+wJF0KhVzeCog1aCE524Q9uQp9ZupEu5NPAViTaG/IexEb7zHo0iPCvBe0A9hhZBgW7d39qPJrVwYZUxWRGcbRZ5seDZNMtgndhvvcNx0vKaKSANC7lqfjFC51hxt6W+dIGBPITS6iJbmbRaH3akAH/HhL+die1+Acol6DkOgMlQA9zqZoi6eM2cqUrcZG/ScuyIQMk2/RCIXPHbKHfTM3FPk0VDf0oJ2769KyUIB5Z9PeIC2MRy8a9MFOLG6DHGkuCBkAq1Yfc0ajnE9jKE1XJwUTEl5B8PVt6SfndbhZQIDAQAB";
    String privateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDJaDtMH/UDIKeEm98/ud6HwynmQrRBFRDxgek0ojClaJvhxt5bq79uNW70wv2sa7Al4N4N7X2zESW2PYGatgSL9P9CLGF6u32GuTnAZLFeKprk85z86DkfANNSWc4COJ18WUn1a9q7Blf6AveW8OaUvAj6FHBj5S4ckgLtybIw9EXY940ks/VgKq6GZoKPjKRKttEhfyPwFc/XHOho1x5oJqpZXFfkYBiU4Zyh5f0r7IfYcQnJSB+W9r7dqXb+1p5W3rgNYgf2Tdx5x/9/N0JBxAdkAsKGPeKvPi2pQwu6G50ramKV14Fspc+JPMPh33Bw0kMtS/dsXv0P4zvau3h7AgMBAAECggEAR+DwEKnzCiu0PUpXsWmHNpJ7OJcjt3pqMpa3gLZFgDLGNxx/LN4tkop1T9rrJ4bDNbF77iKM0sRS3igzQeCl3MER/C7UlzWkhdlC+Bu3hTDVWNJse4HxKNRllqVANWC5K7C+CV11kXuhGDPI4tCUqg41cfKrbatT+pRNgUmTU46n2HY9bpPUON56vV4JLJHiwn7vzO+CCzlVYh9jsiC/xFW9Mh3sLKLSdOWdC+4ndsuy75Ou+pUkegBQkMRpr4/4HhA9ESWNQ6ja2CV7uEsONUeG3MKJsL3Kr9V+qxkV3pIXdzIXR56SFHm3jNSsHibrXVwtquj9nE8xliIUQQ5B2QKBgQDqvI5lsa0L5cP7tHMDDdn8WUCwnB4pXrfBQvv6wv5TJ1hDKQ3L67fCr88rsXvV1Klu1SeUTV7/+2YxbxX7MHaBzVtrsgNtD54N7q5GFcGuJwZmq8yczQZZVyCh46W02yAI3qihRGS1R4ZOEJdgJqzZkV5ihX1LRKbtJFU3fiRW3wKBgQDbpsfPc3N1e6QpJtAKTvoEENWDIPaOxTYT+DyqbU82ZVurILr3xs07LizPHhvx8HCk5BYFUw+3TBfO+Lpmc721WCgUHZv7Xu5BWUjmaRyLOWEr0gm/i8F5wx1l8D0FWqSx6iqSv7766fGn0VKT77OvBq+He6hbsV6TDWmLAm2d5QKBgQDgBR1c3ilI7nmQcRFGO+TqmP9MzWFXmAaEUXoLQibXkvf/+GErWQVhrslnaJXHZ5JfrNt+BC0C3ZkU2nA+7mxaynJSIGx2m+36W+xEBKV3n9TTDWhTyLMjdiw1c/2D0ZAMBvsmFRCLlPwO6nSdFavmY36RRdZii91G+mK9eAwUPwKBgDPt3DNZciSGVKs999VYEpe/exsDQgdC7KQP/he0vBN6Jw/wLwPBRmgFZKaa0bSDSb5SBjrABmkpXj+aYkcyedlTvDX1QhlNujpM54VBuHPZ3vVDNPbKDbXBwUOGZobLhUBiwinrlzUu9ejc/g7njq2EgR8gxw7Wgit18kvhCRVZAoGAPnjK4XtgPsQVzbjLQ7MJ/qB/f9NyT+cjs2y8x91Vuw38+/tHDRpGXiYkTPwVS5oIOq11qgsu0isswkZE7tw1v1+CW6X4v8pkvXcUO3y2XcnMdPFlWz+WSrey4YVFYQ3hun2k8/NOIVRP8h5kgtMoGMNKBz2EQ91N4YunDAz0lhk=";

    public static AuthenticateClient getInstance() {
        if (instance == null) {
            instance = new AuthenticateClient();
        }
        return instance;
    }

    public static void main(String[] args) throws AlipayApiException {
        AuthenticateClient.getInstance().getBizNo("260104197909275964", "测试");
    }

    public String getBizNo(String certNo, String name) throws AlipayApiException {
        AlipayClient alipayClient = new DefaultAlipayClient(url, appId, privateKey, "json", "utf8", publicKey, "RSA2");
        ZhimaCustomerCertificationInitializeRequest request = new ZhimaCustomerCertificationInitializeRequest();
        JSONObject identityParam = new JSONObject();
        identityParam.put("identity_type", "CERT_INFO");
        identityParam.put("cert_type", "IDENTITY_CARD");
        identityParam.put("cert_name", name);
        identityParam.put("cert_no", certNo);
        JSONObject obj = new JSONObject();
        obj.put("transaction_id", String.valueOf(System.currentTimeMillis()));
        obj.put("product_code", "w1010100000000002978");
        obj.put("biz_code", "FACE_SDK");
        obj.put("identity_param", identityParam.toJSONString());
        request.setBizContent(obj.toJSONString());
        ZhimaCustomerCertificationInitializeResponse response = alipayClient.execute(request);
        if (response.isSuccess()) {
            return response.getBizNo();
        } else {
            throw new BusinessException("获取参数失败");
        }
    }

}
