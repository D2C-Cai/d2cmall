package com.d2c.product.model;

import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;

/**
 * 库存盘点
 */
@Table(name = "p_stock_check")
public class StockCheck extends PreUserDO {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 编号
     */
    private String sn;
    /**
     * 门店ID
     */
    private Long storeId;
    /**
     * 门店编号
     */
    private String storeCode;
    /**
     * 门店名称
     */
    private String storeName;
    /**
     * 单据类型 1.全盘单 2.抽盘单
     */
    private Integer type;
    /**
     * 单据状态 0.待提交 1.待处理 8.已处理
     */
    private Integer status = 0;
    /**
     * 备注
     */
    private String memo;
    /**
     * 账面库存
     */
    private Integer bookQuantity;
    /**
     * 实际库存
     */
    private Integer actualQuantity;

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Integer getBookQuantity() {
        return bookQuantity;
    }

    public void setBookQuantity(Integer bookQuantity) {
        this.bookQuantity = bookQuantity;
    }

    public Integer getActualQuantity() {
        return actualQuantity;
    }

    public void setActualQuantity(Integer actualQuantity) {
        this.actualQuantity = actualQuantity;
    }

}
