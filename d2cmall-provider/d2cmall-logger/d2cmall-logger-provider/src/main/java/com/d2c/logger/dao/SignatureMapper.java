package com.d2c.logger.dao;

import com.d2c.logger.model.Signature;
import com.d2c.mybatis.mapper.SuperMapper;

/**
 * 签名信息（微信）
 *
 * @author xh
 */
public interface SignatureMapper extends SuperMapper<Signature> {

    Signature findByAppid(String appid);

}
