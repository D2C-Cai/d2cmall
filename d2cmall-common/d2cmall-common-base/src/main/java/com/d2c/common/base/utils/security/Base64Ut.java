package com.d2c.common.base.utils.security;

import com.d2c.common.base.exception.BaseException;
import com.d2c.common.base.utils.ByteUt;
import org.apache.commons.codec.binary.Base64;

/**
 * Base64编码
 */
public class Base64Ut {
    //****************** UTF-8 ***********************

    /**
     * Base64编码
     */
    public static String encodeUTF(String data) {
        return encodeUTF(ByteUt.toByte(data));
    }

    public static String encodeUTF(byte[] data) {
        return ByteUt.toStr(encodeByte(data));
    }

    /**
     * Base64解码
     */
    public static String decodeUTF(byte[] data) {
        return ByteUt.toStr(decodeByte(data));
    }

    public static String decodeUTF(String data) {
        return decodeUTF(ByteUt.toByte(data));
    }

    public static byte[] decodeByteUTF(String data) {
        return decodeByte(ByteUt.toByte(data));
    }
    //****************** ISO ***********************

    public static String encode(String data) {
        return encode(ByteUt.toByteISO(data));
    }

    public static String encode(byte[] data) {
        return ByteUt.toStrISO(encodeByte(data));
    }

    public static String decode(String data) {
        return decode(ByteUt.toByteISO(data));
    }

    public static String decode(byte[] data) {
        return ByteUt.toStrISO(decodeByte(data));
    }

    public static byte[] decodeByte(String data) {
        return decodeByte(ByteUt.toByteISO(data));
    }
    //**************** BASE ***********************

    public static byte[] encodeByte(byte[] data) {
        try {
            return Base64.encodeBase64URLSafe(data);
        } catch (Exception e) {
            throw new BaseException(e);
        }
    }

    public static byte[] decodeByte(byte[] data) {
        try {
            return Base64.decodeBase64(data);
        } catch (Exception e) {
            throw new BaseException(e);
        }
    }

}
