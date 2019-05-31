package com.d2c.order.third.payment.alipay.sgin;import com.d2c.order.third.payment.alipay.core.pcwap.AlipayConfig;import java.io.UnsupportedEncodingException;import java.security.MessageDigest;import java.security.NoSuchAlgorithmException;/** * 功能：支付宝MD5加密处理核心文件，不需要修改 版本：3.0 修改日期：2010-06-18 说明： * 以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。 * 该代码仅供学习和研究支付宝接口使用，只是提供一个 */public class MD5 {    /**     * Used building output as Hex     */    private static final char[] DIGITS = {'0', '1', '2', '3', '4', '5', '6',            '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};    /**     * 对字符串进行MD5加密     *     * @param text 明文     * @return 密文     */    public static String md5(String text) {        MessageDigest msgDigest = null;        try {            msgDigest = MessageDigest.getInstance("MD5");        } catch (NoSuchAlgorithmException e) {            throw new IllegalStateException(                    "System doesn't support MD5 algorithm.");        }        try {            msgDigest.update(text.getBytes(AlipayConfig.input_charset)); // 注意改接口是按照指定编码形式加密        } catch (UnsupportedEncodingException e) {            throw new IllegalStateException(                    "System doesn't support your  EncodingException.");        }        byte[] bytes = msgDigest.digest();        String md5Str = new String(encodeHex(bytes));        return md5Str;    }    /**     * 对字符串进行MD5加密     *     * @param text 明文     * @return 密文     */    public static String md5(String text, String charset) {        MessageDigest msgDigest = null;        try {            msgDigest = MessageDigest.getInstance("MD5");        } catch (NoSuchAlgorithmException e) {            throw new IllegalStateException(                    "System doesn't support MD5 algorithm.");        }        try {            msgDigest.update(text.getBytes(charset)); // 注意改接口是按照指定编码形式加密        } catch (UnsupportedEncodingException e) {            throw new IllegalStateException(                    "System doesn't support your  EncodingException.");        }        byte[] bytes = msgDigest.digest();        String md5Str = new String(encodeHex(bytes));        return md5Str;    }    /**     * 签名字符串     *     * @param text          需要签名的字符串     * @param key           密钥     * @param input_charset 编码格式     * @return 签名结果     */    public static String sign(String text, String key) {        text = text + key;        return md5(text);    }    /**     * 签名字符串     *     * @param text          需要签名的字符串     * @param sign          签名结果     * @param key           密钥     * @param input_charset 编码格式     * @return 签名结果     */    public static boolean verify(String text, String sign, String key) {        text = text + key;        String mysign = md5(text);        if (mysign.equals(sign)) {            return true;        } else {            return false;        }    }    public static char[] encodeHex(byte[] data) {        int l = data.length;        char[] out = new char[l << 1];        // two characters form the hex value.        for (int i = 0, j = 0; i < l; i++) {            out[j++] = DIGITS[(0xF0 & data[i]) >>> 4];            out[j++] = DIGITS[0x0F & data[i]];        }        return out;    }}