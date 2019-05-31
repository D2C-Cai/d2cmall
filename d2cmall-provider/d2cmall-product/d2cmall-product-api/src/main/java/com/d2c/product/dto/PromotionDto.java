package com.d2c.product.dto;

import com.d2c.product.model.Promotion;

public class PromotionDto extends Promotion {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String brandName;

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

}
