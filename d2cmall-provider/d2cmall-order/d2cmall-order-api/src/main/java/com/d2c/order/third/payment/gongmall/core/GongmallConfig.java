package com.d2c.order.third.payment.gongmall.core;

public class GongmallConfig {

    private String contractUrl = "https://contract-qa.gongmall.com/url_contract.html?companyId=1z94bM";
    private String apiUrl = "https://openapi-qa.gongmall.com";
    // 测试环境
    private String appkey = "f8047f4c11f840ccaf80e6e69dce17bd";
    private String appSecret = "726e4a1789035b41427871021350cb46";

    public String getContractUrl() {
        return contractUrl;
    }

    public void setContractUrl(String contractUrl) {
        this.contractUrl = contractUrl;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

}
