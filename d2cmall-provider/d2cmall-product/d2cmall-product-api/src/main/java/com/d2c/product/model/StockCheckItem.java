package com.d2c.product.model;

import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;

/**
 * 库存盘点明细
 */
@Table(name = "p_stock_check_item")
public class StockCheckItem extends PreUserDO {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 盘点单ID
     */
    private Long checkId;
    /**
     * 商品条码
     */
    private String barCode;
    /**
     * 账面库存
     */
    private Integer bookQuantity;
    /**
     * 实际库存
     */
    private Integer actualQuantity;
    /**
     * 备注
     */
    private String memo;

    public Long getCheckId() {
        return checkId;
    }

    public void setCheckId(Long checkId) {
        this.checkId = checkId;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
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

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

}
