package com.d2c.common.base.utils.security.core;

import com.d2c.common.base.utils.security.Base64Ut;

import java.io.Serializable;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * RSA 非对称加密密钥持有者
 *
 * @author wull
 */
public class RSAKey implements Serializable {

    private static final long serialVersionUID = 480871388509204322L;
    private PrivateKey privateKey;
    private PublicKey publicKey;

    public RSAKey(KeyPair keyPair) {
        this.publicKey = (RSAPublicKey) keyPair.getPublic();
        this.privateKey = (RSAPrivateKey) keyPair.getPrivate();
    }

    public String getPrivateStr() {
        return Base64Ut.encode(getPrivateByte());
    }

    public String getPublicStr() {
        return Base64Ut.encode(getPublicByte());
    }

    public byte[] getPrivateByte() {
        return privateKey.getEncoded();
    }

    public byte[] getPublicByte() {
        return publicKey.getEncoded();
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

}
