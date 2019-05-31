package com.d2c.product.dto;

import com.d2c.product.model.Promotion;
import com.d2c.product.model.PromotionTagRelation;

public class PromotionTagRelationDto extends PromotionTagRelation {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Promotion promotion;

    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

}
