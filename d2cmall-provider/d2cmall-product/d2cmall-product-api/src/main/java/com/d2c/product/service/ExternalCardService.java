package com.d2c.product.service;

import com.d2c.product.model.ExternalCard;

public interface ExternalCardService {

    /**
     * 兑换卡
     *
     * @param memberId
     * @param sn
     * @param source
     * @return
     */
    int doUse(Long memberId, String sn, Long sourceId, Long productId);

    /**
     * 根据业务流水查询
     *
     * @param memberId
     * @param sn
     * @return
     */
    ExternalCard findBySn(Long memberId, String sn);

    /**
     * 插入
     *
     * @param card
     * @return
     */
    ExternalCard insert(ExternalCard card);

    /**
     * 统计已兑换的
     *
     * @param productId
     * @return
     */
    int countUsed(Long productId);

}
