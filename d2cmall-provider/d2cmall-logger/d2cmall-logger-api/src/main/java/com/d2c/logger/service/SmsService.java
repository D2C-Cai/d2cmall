package com.d2c.logger.service;

import com.alibaba.fastjson.JSONObject;

public interface SmsService {

    int sendSMS(String[] mobiles, String smsContent);

    int sendSMS(String mobile, String smsContent);

    int sendGlobalSMS(String[] mobile, String smsContent);

    int sendGlobalSMS(String mobile, String smsContent);

    /**
     * 短信剩余条数
     *
     * @return
     */
    String getBalance();

    /**
     * 发送语音验证码
     *
     * @param mobile 手机号
     * @param code   验证码
     * @param tempId 文本转语音模板id ，在阿里云控制台可见，选填，有默认模板
     * @param params 模板中替换变量的
     * @return
     */
    int doSendVms(String mobile, String code, String tempId, JSONObject params);

}
