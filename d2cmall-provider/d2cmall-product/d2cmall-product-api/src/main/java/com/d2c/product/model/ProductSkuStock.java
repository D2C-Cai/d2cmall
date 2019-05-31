package com.d2c.product.model;

import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;

/**
 * 店铺库存
 */
@Table(name = "product_sku_stock")
public class ProductSkuStock extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 条码
     */
    @AssertColumn("商品条码不能为空")
    private String barCode;
    /**
     * 即时库存
     */
    private Integer stock;
    /**
     * 占单库存
     */
    private Integer occupyStock;
    /**
     * 门店编号
     */
    private String storeCode;

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public Integer getOccupyStock() {
        return occupyStock;
    }

    public void setOccupyStock(Integer occupyStock) {
        this.occupyStock = occupyStock;
    }

}
