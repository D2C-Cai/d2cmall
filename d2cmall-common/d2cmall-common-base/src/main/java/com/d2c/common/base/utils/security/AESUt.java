package com.d2c.common.base.utils.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * 对称加密算法
 *
 * @author wull
 */
public class AESUt {

    private static final Logger logger = LoggerFactory.getLogger(AESUt.class);
    private static final String KEY = "AES";
    private static final String DEFAULT_CHARSET = "UTF-8";

    /**
     * 生成秘钥
     */
    public static String generaterKey() throws NoSuchAlgorithmException {
        KeyGenerator keygen = KeyGenerator.getInstance(KEY);
        keygen.init(128, new SecureRandom());
        SecretKey secretKey = keygen.generateKey();
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    /**
     * 加密
     */
    public static String encrypt(String content, String sessionKey) throws Exception {
        Key key = getSessionKey(sessionKey);
        Cipher cipher = Cipher.getInstance(KEY);// 创建密码器
        cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
        byte[] result = cipher.doFinal(content.getBytes(DEFAULT_CHARSET));
        return Base64.getEncoder().encodeToString(result);
    }

    /**
     * 解密
     */
    public static String decrypt(String content, String sessionKey) throws Exception {
        Key key = getSessionKey(sessionKey);
        Cipher cipher = Cipher.getInstance(KEY);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] result = cipher.doFinal(Base64.getDecoder().decode(content));
        return new String(result);
    }

    private static SecretKeySpec getSessionKey(String sessionKey) {
        byte[] secretKey = Base64.getDecoder().decode(sessionKey);
        logger.info("sessionKey:" + sessionKey + " length:" + secretKey.length);
        return new SecretKeySpec(secretKey, KEY);
    }

}
