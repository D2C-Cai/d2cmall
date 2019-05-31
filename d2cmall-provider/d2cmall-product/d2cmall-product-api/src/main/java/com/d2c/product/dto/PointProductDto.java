package com.d2c.product.dto;

import com.d2c.product.model.PointProduct;

public class PointProductDto extends PointProduct {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Integer totalUsed;

    public Integer getTotalUsed() {
        return totalUsed;
    }

    public void setTotalUsed(Integer totalUsed) {
        this.totalUsed = totalUsed;
    }

}
