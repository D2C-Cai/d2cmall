package com.d2c.order.third.kd100.core;

import java.util.HashMap;

public class TaskRequest {

    /**
     * 公司
     */
    private String company;
    /**
     * 单号
     */
    private String number;
    /**
     * 寄件地址
     */
    private String from;
    /**
     * 收件地址
     */
    private String to;
    /**
     * 凭证
     */
    private String key;
    /**
     *
     */
    private String src;
    private HashMap<String, String> parameters = new HashMap<String, String>();

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public HashMap<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(HashMap<String, String> parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return JacksonHelper.toJSON(this);
    }

}
