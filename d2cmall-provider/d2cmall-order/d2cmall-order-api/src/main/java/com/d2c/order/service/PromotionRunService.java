package com.d2c.order.service;

import com.d2c.order.handle.PromotionCalculateItem;
import com.d2c.order.handle.PromotionCalculateResult;
import com.d2c.order.model.base.IPromotionInterface;

import java.util.List;

public interface PromotionRunService {

    /**
     * 获取整单活动计算后的结果
     *
     * @param distributorId
     * @param orderId
     * @param promotionConditions
     */
    PromotionCalculateResult getPromotionsByOrder(Long distributorId, Long orderId,
                                                  List<IPromotionInterface> promotionConditions);

    /**
     * 获取单条符合条件的所有活动
     *
     * @param distributorId
     * @param promotionCondition
     * @return
     */
    PromotionCalculateItem getPromotionByItem(Long distributorId, IPromotionInterface promotionCondition);

    /**
     * 组合商品计算
     *
     * @param promotionConditions
     * @return
     */
    PromotionCalculateResult getProductCombPromotion(List<IPromotionInterface> promotionConditions);

}
