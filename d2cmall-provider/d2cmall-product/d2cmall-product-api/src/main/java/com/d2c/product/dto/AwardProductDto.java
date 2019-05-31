package com.d2c.product.dto;

import com.d2c.product.model.AwardProduct;
import com.d2c.product.model.AwardSession;

public class AwardProductDto extends AwardProduct {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private AwardSession awardSession;

    public AwardSession getAwardSession() {
        return awardSession;
    }

    public void setAwardSession(AwardSession awardSession) {
        this.awardSession = awardSession;
    }

}
