package com.d2c.flame.property;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class ASE {

    private static final Logger logger = LoggerFactory.getLogger(ASE.class);
    private static final String UTF8 = "UTF-8";
    private static final String AES_ALG = "AES";
    private static final String AES_CBC_PCK_ALG = "AES/CBC/PKCS5Padding";
    private static final byte[] AES_IV = initIv(AES_CBC_PCK_ALG);

    /**
     * AES加密
     *
     * @param content
     * @param aesKey
     * @return
     */
    public static String aesEncrypt(String content, String aesKey) {
        return aesEncrypt(content, aesKey, UTF8);
    }

    /**
     * AES解密
     *
     * @param content
     * @param aesKey
     * @return
     */
    public static String aesDecrypt(String content, String aesKey) {
        return aesDecrypt(content, aesKey, UTF8);
    }

    /**
     * AES加密
     *
     * @param content
     * @param aesKey
     * @param charset
     * @return
     */
    public static String aesEncrypt(String content, String aesKey, String charset) {
        try {
            Cipher cipher = Cipher.getInstance(AES_CBC_PCK_ALG);
            IvParameterSpec iv = new IvParameterSpec(AES_IV);
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(Base64.decodeBase64(aesKey.getBytes()), AES_ALG), iv);
            byte[] encryptBytes = cipher.doFinal(content.getBytes(charset));
            return new String(Base64.encodeBase64(encryptBytes));
        } catch (Exception e) {
            logger.error("AES加密失败：Aescontent = " + content + "; charset = " + charset, e);
            return null;
        }
    }

    /**
     * AES解密
     *
     * @param content
     * @param aesKey
     * @param charset
     * @return
     */
    public static String aesDecrypt(String content, String aesKey, String charset) {
        try {
            Cipher cipher = Cipher.getInstance(AES_CBC_PCK_ALG);
            IvParameterSpec iv = new IvParameterSpec(AES_IV);
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(Base64.decodeBase64(aesKey.getBytes()), AES_ALG), iv);
            byte[] cleanBytes = cipher.doFinal(Base64.decodeBase64(content.getBytes()));
            return new String(cleanBytes, charset);
        } catch (Exception e) {
            logger.error("AES解密失败：Aescontent = " + content + "; charset = " + charset, e);
            return null;
        }
    }

    /**
     * 初始向量的方法, 全部为0. 针对AES算法的话,IV值一定是128位的(16字节).
     *
     * @param fullAlg
     * @return
     */
    private static byte[] initIv(String fullAlg) {
        try {
            Cipher cipher = Cipher.getInstance(fullAlg);
            int blockSize = cipher.getBlockSize();
            byte[] iv = new byte[blockSize];
            for (int i = 0; i < blockSize; ++i) {
                iv[i] = 0;
            }
            return iv;
        } catch (Exception e) {
            int blockSize = 16;
            byte[] iv = new byte[blockSize];
            for (int i = 0; i < blockSize; ++i) {
                iv[i] = 0;
            }
            return iv;
        }
    }

}