package com.d2c.logger.third.sms.emay;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.base.utils.security.MD5Util;
import com.d2c.util.date.DateUtil;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

/**
 * 语音验证码
 *
 * @author wwn
 */
public class EmayDomesticAudioClient {

    /**
     * appId
     */
    public static String appId = "6SDK-EMY-6688-JCZRL";
    /**
     * 密钥
     */
    public static String secretKey = "B95C0CC76DAAA6A3";
    /**
     * url http://bjmtn.b2m.cn/ ; http://shmtn.b2m.cn/ (一个北京地址，一个上海地址，任选一)
     */
    public static String url = "http://shmtn.b2m.cn/voice/sendSMS";
    private static RestTemplate restTemplate = new RestTemplate();

    public static int sendAudioCode(String mobile, String code) {
        String timestamp = DateUtil.convertDate2Str(new Date(), "yyyyMMddHHmmss");
        String sign = MD5Util.encodeMD5Hex(appId + secretKey + timestamp);
        String result = restTemplate.getForObject(url + "?appId=" + appId + "&timestamp=" + timestamp + "&sign=" + sign
                + "&mobile=" + mobile + "&content=" + code, String.class);
        JSONObject obj = JSONObject.parseObject(result);
        if (obj.get("code").toString().equalsIgnoreCase("SUCCESS")) {
            return 1;
        }
        return 0;
    }

    public static void main(String[] args) {
        EmayDomesticAudioClient.sendAudioCode("15757129970", "1234");
    }

}
