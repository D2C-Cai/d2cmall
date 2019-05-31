package com.d2c.logger.service;

import com.d2c.logger.model.Signature;

/**
 * 签名（signature）
 */
public interface SignatureService {

    /**
     * 根据appid获取签名
     *
     * @param appId 用户唯一凭证,主键
     * @return
     */
    Signature findByAppid(String appId);

    /**
     * 更新签名
     *
     * @param signature
     * @return
     */
    int update(Signature signature);

}
