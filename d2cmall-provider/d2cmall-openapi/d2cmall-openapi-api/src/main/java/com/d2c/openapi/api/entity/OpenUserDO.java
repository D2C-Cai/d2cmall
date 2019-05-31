package com.d2c.openapi.api.entity;

import com.d2c.common.api.model.BaseUserDO;
import com.d2c.openapi.api.constant.Constant;

import javax.persistence.Table;

/**
 * OpenApi 商家用户表
 *
 * @author wull
 */
@Table(name = Constant.PLAT_PREFIX + "user")
public class OpenUserDO extends BaseUserDO {

    private static final long serialVersionUID = 8892073391665511551L;
    /**
     * token 票据
     */
    private String token;
    /**
     * RSA 加密公钥
     */
    private String publicKey;
    /**
     * RSA 加密私钥
     */
    private String privateKey;
    /**
     * 品牌ID
     */
    private Long brandId;
    /**
     * 品牌CODE
     */
    private String brandCode;
    /**
     * 品牌名称
     */
    private String brandName;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getBrandCode() {
        return brandCode;
    }

    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

}
