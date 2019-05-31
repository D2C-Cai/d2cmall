package com.d2c.main.openapi.interceptor;

import com.d2c.common.api.dto.BaseDTO;
import com.d2c.common.base.utils.ConvertUt;

/**
 * Token用户验证
 *
 * @author wull
 */
public class TokenDTO extends BaseDTO {

    private static final long serialVersionUID = -2407942537511131669L;
    /**
     * token令牌
     */
    private String token;
    /**
     * timestamp 时间校验, 进行超时校验 一般 60s
     */
    private Long timestamp;
    /**
     * 唯一的请求ID (可选)
     * <p> 防止 nonce过多而设计
     */
    private String nonce;
    /**
     * 非对称加密公钥(RSA)
     * <p>根据令牌从数据库中获取, 用于解锁数字签名
     */
    private String publicKey;
    /**
     * sign数字签名数据
     *
     * <p>需签名字符串拼接signString格式: ${token}_${timestamp}_${nonce}_${params}
     * <br>${params}: 对参数按key进行字典升序排列，排除token， timestamp， nonce， sign
     * <br>使用MD5对 signString需签名数据加密，然后用RSA私钥签名
     * <br>${nonce} 可为空，为空则 ${token}_${timestamp}_${params}
     */
    private String sign;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        if (timestamp == null) {
            this.timestamp = null;
        } else {
            this.timestamp = ConvertUt.convertType(timestamp, Long.class);
        }
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

}
