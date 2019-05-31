package com.d2c.common.base.utils.security;

import com.d2c.common.base.exception.BaseException;
import com.d2c.common.base.utils.ByteUt;
import com.d2c.common.base.utils.security.core.RSAKey;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.UUID;

/**
 * RSA 非对称加密密钥算法
 */
public class RSAUt {

    /**
     * 非对称加密密钥算法
     */
    public static final String KEY_RSA = "RSA";
    /**
     * RSA密钥长度 默认1024位， 密钥长度必须是64的倍数， 范围在512至65536位之间。
     */
    private static final int KEY_SIZE = 1024;

    static {
        Security.insertProviderAt(new BouncyCastleProvider(), 1);
    }

    /**
     * 私钥解密
     */
    public static String decodePrivate(String key, String data) {
        return ByteUt.toStrISO(decodePrivate(Base64Ut.decodeByte(key), Base64Ut.decodeByte(data)));
    }

    public static byte[] decodePrivate(byte[] key, byte[] data) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_RSA);
            PrivateKey privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(key));
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            int blockSize = cipher.getBlockSize();
            if (blockSize > 0) {
                ByteArrayOutputStream bout = new ByteArrayOutputStream(64);
                int j = 0;
                while (data.length - j * blockSize > 0) {
                    bout.write(cipher.doFinal(data, j * blockSize, blockSize));
                    j++;
                }
                return bout.toByteArray();
            }
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new BaseException("RSA私钥解密失败...", e);
        }
    }

    /**
     * 公钥解密
     */
    public static String decodePublic(String key, String data) {
        return ByteUt.toStrISO(decodePublic(Base64Ut.decodeByte(key), Base64Ut.decodeByte(data)));
    }

    public static byte[] decodePublic(byte[] key, byte[] data) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_RSA);
            PublicKey publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(key));
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new BaseException("RSA公钥解密失败...", e);
        }
    }

    /**
     * 公钥加密
     */
    public static String encodePublic(String key, String data) {
        return Base64Ut.encode(encodePublic(Base64Ut.decodeByte(key), ByteUt.toByteISO(data)));
    }

    public static byte[] encodePublic(byte[] key, byte[] data) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_RSA);
            PublicKey publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(key));
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            int blockSize = cipher.getBlockSize();
            if (blockSize > 0) {
                int outputSize = cipher.getOutputSize(data.length);
                int leavedSize = data.length % blockSize;
                int blocksSize = leavedSize != 0 ? data.length / blockSize + 1 : data.length / blockSize;
                byte[] raw = new byte[outputSize * blocksSize];
                int i = 0, remainSize = 0;
                while ((remainSize = data.length - i * blockSize) > 0) {
                    int inputLen = remainSize > blockSize ? blockSize : remainSize;
                    cipher.doFinal(data, i * blockSize, inputLen, raw, i * outputSize);
                    i++;
                }
                return raw;
            }
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new BaseException("RSA公钥加密失败...", e);
        }
    }

    /**
     * 私钥加密
     */
    public static String encodePrivate(String key, String data) {
        return Base64Ut.encode(encodePrivate(Base64Ut.decodeByte(key), ByteUt.toByteISO(data)));
    }

    public static byte[] encodePrivate(byte[] key, byte[] data) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_RSA);
            PrivateKey privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(key));
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            int blockSize = cipher.getBlockSize();
            if (blockSize > 0) {
                int outputSize = cipher.getOutputSize(data.length);
                int leavedSize = data.length % blockSize;
                int blocksSize = leavedSize != 0 ? data.length / blockSize + 1 : data.length / blockSize;
                byte[] raw = new byte[outputSize * blocksSize];
                int i = 0, remainSize = 0;
                while ((remainSize = data.length - i * blockSize) > 0) {
                    int inputLen = remainSize > blockSize ? blockSize : remainSize;
                    cipher.doFinal(data, i * blockSize, inputLen, raw, i * outputSize);
                    i++;
                }
                return raw;
            }
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new BaseException("RSA私钥加密失败...", e);
        }
    }

    /**
     * 初始化密钥
     */
    public static RSAKey initKey() {
        return initKey(UUID.randomUUID().toString().getBytes());
    }

    public static RSAKey initKey(String seed) {
        return initKey(seed.getBytes());
    }

    public static RSAKey initKey(byte[] seed) {
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_RSA);
            keyPairGen.initialize(KEY_SIZE, new SecureRandom(seed));
            return new RSAKey(keyPairGen.generateKeyPair());
        } catch (Exception e) {
            throw new BaseException("RSA公私钥生成失败...", e);
        }
    }

}
