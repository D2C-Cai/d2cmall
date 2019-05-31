package com.d2c.common.base.utils.security;

import org.apache.commons.codec.binary.Base64;
import sun.security.ec.ECPrivateKeyImpl;
import sun.security.ec.ECPublicKeyImpl;

import javax.crypto.Cipher;
import javax.crypto.NullCipher;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.*;
import java.util.HashMap;
import java.util.Map;

/**
 * ECC椭圆曲线算法
 *
 * @author wull
 */
@SuppressWarnings("restriction")
public class ECCUt {

    public static final String ALGORITHM = "EC";
    private static final String PUBLIC_KEY = "ECCPublicKey";
    private static final String PRIVATE_KEY = "ECCPrivateKey";

    /**
     * 解密<br>
     * 用私钥解密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decrypt(byte[] data, String key) throws Exception {
        // 对密钥解密
        byte[] keyBytes = decryptBASE64(key);
        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("EC");
        ECPrivateKey priKey = (ECPrivateKey) keyFactory
                .generatePrivate(pkcs8KeySpec);
        ECPrivateKeySpec ecPrivateKeySpec = new ECPrivateKeySpec(priKey.getS(),
                priKey.getParams());
        // 对数据解密
        // TODO Chipher不支持EC算法 未能实现
        Cipher cipher = new NullCipher();
        // Cipher.getInstance(ALGORITHM, keyFactory.getProvider());
        cipher.init(Cipher.DECRYPT_MODE, priKey, ecPrivateKeySpec.getParams());
        return cipher.doFinal(data);
    }

    /**
     * 加密<br>
     * 用公钥加密
     *
     * @param data
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(byte[] data, String privateKey)
            throws Exception {
        // 对公钥解密
        byte[] keyBytes = decryptBASE64(privateKey);
        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("EC");
        ECPublicKey pubKey = (ECPublicKey) keyFactory
                .generatePublic(x509KeySpec);
        ECPublicKeySpec ecPublicKeySpec = new ECPublicKeySpec(pubKey.getW(),
                pubKey.getParams());
        // 对数据加密
        // TODO Chipher不支持EC算法 未能实现
        Cipher cipher = new NullCipher();
        // Cipher.getInstance(ALGORITHM, keyFactory.getProvider());
        cipher.init(Cipher.ENCRYPT_MODE, pubKey, ecPublicKeySpec.getParams());
        return cipher.doFinal(data);
    }

    /**
     * 取得私钥
     *
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static String getPrivateKey(Map<String, Object> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return encryptBASE64(key.getEncoded());
    }

    /**
     * 取得公钥
     *
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static String getPublicKey(Map<String, Object> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return encryptBASE64(key.getEncoded());
    }

    /**
     * 初始化密钥
     *
     * @return
     * @throws Exception
     */
    public static Map<String, Object> initKey() throws Exception {
        BigInteger x1 = new BigInteger(
                "2fe13c0537bbc11acaa07d793de4e6d5e5c94eee8", 16);
        BigInteger x2 = new BigInteger(
                "289070fb05d38ff58321f2e800536d538ccdaa3d9", 16);
        ECPoint g = new ECPoint(x1, x2);
        // the order of generator
        BigInteger n = new BigInteger(
                "5846006549323611672814741753598448348329118574063", 10);
        // the cofactor
        int h = 2;
        int m = 163;
        int[] ks = {7, 6, 3};
        ECFieldF2m ecField = new ECFieldF2m(m, ks);
        // y^2+xy=x^3+x^2+1
        BigInteger a = new BigInteger("1", 2);
        BigInteger b = new BigInteger("1", 2);
        EllipticCurve ellipticCurve = new EllipticCurve(ecField, a, b);
        ECParameterSpec ecParameterSpec = new ECParameterSpec(ellipticCurve, g,
                n, h);
        // 公钥
        ECPublicKey publicKey = new ECPublicKeyImpl(g, ecParameterSpec);
        BigInteger s = new BigInteger(
                "1234006549323611672814741753598448348329118574063", 10);
        // 私钥
        ECPrivateKey privateKey = new ECPrivateKeyImpl(s, ecParameterSpec);
        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }

    public static byte[] decryptBASE64(String data) {
        return Base64.decodeBase64(data);
    }

    public static String encryptBASE64(byte[] data) {
        return new String(Base64.encodeBase64(data));
    }

}
