package com.d2c.logger.service;

import com.alibaba.fastjson.JSONObject;
import com.d2c.logger.support.SmsChoice;
import com.d2c.logger.support.SmsChoice.Region;
import com.d2c.logger.third.sms.dahantc.DahanTCSMSClient;
import com.d2c.logger.third.sms.emay.EmayDomesticSMSClient;
import com.d2c.logger.third.sms.emay.EmayGlobalSMSClient;
import com.d2c.logger.third.vms.DyvmsClient;
import org.springframework.stereotype.Service;

/***
 * 短信发送
 */
@Service("smsService")
public class SmsServiceImpl implements SmsService {

    @Override
    public int sendSMS(String[] mobiles, String smsContent) {
        int success = EmayDomesticSMSClient.sendSMS(mobiles, smsContent);
        return success;
    }

    @Override
    public int sendSMS(String mobile, String smsContent) {
        int success = 0;
        try {
            if (SmsChoice.setting.get(Region.Domestic).equalsIgnoreCase("Emay")) {
                success = EmayDomesticSMSClient.sendSMS(mobile, smsContent);
            } else if (SmsChoice.setting.get(Region.Domestic).equalsIgnoreCase("Dahan")) {
                success = DahanTCSMSClient.getInstance().sendSMS(mobile, smsContent, null, null);
            } else {
                success = EmayDomesticSMSClient.sendSMS(mobile, smsContent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    @Override
    public int sendGlobalSMS(String[] mobiles, String smsContent) {
        int success = EmayGlobalSMSClient.sendSMS(mobiles, smsContent);
        return success;
    }

    @Override
    public int sendGlobalSMS(String mobile, String smsContent) {
        int success = EmayGlobalSMSClient.sendSMS(mobile, smsContent);
        return success;
    }

    @Override
    public String getBalance() {
        StringBuilder builder = new StringBuilder();
        builder.append(EmayDomesticSMSClient.getBalance() * 10);
        return builder.toString();
    }

    @Override
    public int doSendVms(String mobile, String code, String tempId, JSONObject params) {
        return DyvmsClient.singleCallByTts(mobile, code, tempId, params);
    }

}
