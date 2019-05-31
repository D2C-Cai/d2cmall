package com.d2c.order.dto;

import com.d2c.order.model.Store;

public class StoreDto extends Store {

    private static final long serialVersionUID = 1L;
    /**
     * 现有库存
     */
    private Integer stock;
    /**
     * 占单库存
     */
    private Integer occupyStock;

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getOccupyStock() {
        return occupyStock;
    }

    public void setOccupyStock(Integer occupyStock) {
        this.occupyStock = occupyStock;
    }

}
