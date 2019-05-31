package com.d2c.order.third.kaola.reponse;

/**
 * 对应商品仓库库存
 *
 * @author Lain
 */
public class WarehouseStore {

    /**
     * 仓库名称
     */
    private String warehouseName;
    /**
     * 仓库ID
     */
    private Long warehouseId;
    /**
     * 库存数量
     */
    private Integer warehouseStore;

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Integer getWarehouseStore() {
        return warehouseStore;
    }

    public void setWarehouseStore(Integer warehouseStore) {
        this.warehouseStore = warehouseStore;
    }

}
