package com.d2c.order.third.payment.wxpay.sign;

import com.d2c.order.third.payment.wxpay.client.WxPayUtil;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;

public class WeixinSign {

    public static String md5Sign(String content, String key) throws RuntimeException {
        String signStr = "";
        if ("" == key) {
            throw new RuntimeException("财付通签名key不能为空！");
        }
        if ("" == content) {
            throw new RuntimeException("财付通签名内容不能为空");
        }
        signStr = content + "&key=" + key;
        return MD5.md5(signStr).toUpperCase();
    }

    public static Map<String, String> sha1Sign(String ticket, String appId, String url) {
        Map<String, String> ret = new HashMap<String, String>();
        String nonce_str = WxPayUtil.createNoncestr();
        String timestamp = WxPayUtil.createTimestamp();
        String string1;
        String signature = "";
        // 注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + ticket + "&noncestr=" + nonce_str + "&timestamp=" + timestamp + "&url=" + url;
        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ret.put("url", url);
        ret.put("jsapi_ticket", ticket);
        ret.put("nonceStr", nonce_str);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);
        ret.put("appId", appId);
        return ret;
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

}
