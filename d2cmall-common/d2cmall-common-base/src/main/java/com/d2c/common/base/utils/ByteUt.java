package com.d2c.common.base.utils;

import com.d2c.common.base.exception.BaseException;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

/**
 * Byte 工具类
 *
 * @author wull
 */
public class ByteUt {

    public final static String UTF8 = "UTF-8";
    public final static String ISO = "ISO-8859-1";

    /**
     * 字符串编码转换
     */
    public static String toStr(String str, String fromCharset, String toCharset) {
        return toStr(toByte(str, fromCharset), toCharset);
    }

    /**
     * 转字符串
     */
    public static String toStr(byte[] bytes) {
        return toStr(bytes, UTF8);
    }

    public static String toStrISO(byte[] bytes) {
        return toStr(bytes, ISO);
    }

    public static String toStr(byte[] bytes, String charsetName) {
        try {
            return new String(bytes, charsetName);
        } catch (Exception e) {
            throw new BaseException(e);
        }
    }

    /**
     * 转byte
     */
    public static byte[] toByte(String str) {
        return toByte(str, UTF8);
    }

    public static byte[] toByteISO(String str) {
        return toByte(str, ISO);
    }

    public static byte[] toByte(String str, String charsetName) {
        try {
            return str == null ? null : str.getBytes(charsetName);
        } catch (Exception e) {
            throw new BaseException(e);
        }
    }
    //************************ HEX *************************

    /**
     * 转 16进制字符串
     */
    public static String toHexStr(String str) {
        return toHexStr(toByte(str));
    }

    public static String toHexStr(byte[] bytes) {
        return Hex.encodeHexString(bytes);
    }

    /**
     * 16进制 HEX bytes
     */
    public static byte[] hexToByte(String str) {
        if (str == null) return null;
        try {
            return Hex.decodeHex(str.toCharArray());
        } catch (DecoderException e) {
            throw new BaseException(e);
        }
    }

}
