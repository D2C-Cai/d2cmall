package com.d2c.order.third.kaola;

import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class APIUtil {

    private static final char[] bcdLookup = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
            'f'};
    private static Logger logger = Logger.getLogger("APIUtil");

    /**
     * 根据secretkey及参数map生成签名,sign除外
     *
     * @param secretKey
     * @param parameterMap
     * @return
     */
    public static String createSign(String secretKey, TreeMap<String, String> parameterMap) {
        String sign = secretKey;
        Iterator<Entry<String, String>> it = parameterMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
            if ((entry.getKey()).equals("sign"))
                continue;
            sign += entry.getKey() + entry.getValue();
        }
        sign += secretKey;
        try {
            sign = md5Encode2UpperCase(sign);
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
        return sign;
    }

    /**
     * 对字符串进行md5加密并生成32位大写字符串
     *
     * @param sign
     * @return
     * @throws Exception
     */
    private static String md5Encode2UpperCase(String sign) throws Exception {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
            return "";
        }
        byte[] byteArray = sign.getBytes("UTF-8");
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        String rs = hexValue.toString().toUpperCase();
        return rs;
    }

    /**
     * 本方法使用SHA1withRSA签名算法产生签名
     *
     * @param String priKey 签名时使用的私钥(16进制编码)
     * @param String src 签名的原字符串
     * @return String 签名的返回结果(16进制编码)。当产生签名出错的时候，返回null。
     */
    public static String generateSHA1withRSASigature(String priKey, String src) {
        try {
            Signature sigEng = Signature.getInstance("SHA1withRSA");
            byte[] pribyte = hexStrToBytes(priKey.trim());
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pribyte);
            KeyFactory fac = KeyFactory.getInstance("RSA");
            RSAPrivateKey privateKey = (RSAPrivateKey) fac.generatePrivate(keySpec);
            sigEng.initSign(privateKey);
            sigEng.update(src.getBytes());
            byte[] signature = sigEng.sign();
            return bytesToHexStr(signature);
        } catch (Exception e) {
            e.printStackTrace();
            // LogMan.log("[NeteaseSignUtil][generateSHA1withRSASigature]"+e);
            return null;
        }
    }

    /**
     * 将16进制字符串还原为字节数组.
     */
    private static final byte[] hexStrToBytes(String s) {
        byte[] bytes;
        bytes = new byte[s.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(s.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }

    /**
     * 将字节数组转换为16进制字符串的形式.
     */
    private static final String bytesToHexStr(byte[] bcd) {
        StringBuffer s = new StringBuffer(bcd.length * 2);
        for (int i = 0; i < bcd.length; i++) {
            s.append(bcdLookup[(bcd[i] >>> 4) & 0x0f]);
            s.append(bcdLookup[bcd[i] & 0x0f]);
        }
        return s.toString();
    }

}
