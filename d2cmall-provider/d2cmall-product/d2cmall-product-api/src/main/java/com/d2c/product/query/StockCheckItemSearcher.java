package com.d2c.product.query;

import com.d2c.common.api.query.model.BaseQuery;

public class StockCheckItemSearcher extends BaseQuery {

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
     * 只看未填
     */
    private Integer unfilled;
    /**
     * 只看差异
     */
    private Integer difference;

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

    public Integer getUnfilled() {
        return unfilled;
    }

    public void setUnfilled(Integer unfilled) {
        this.unfilled = unfilled;
    }

    public Integer getDifference() {
        return difference;
    }

    public void setDifference(Integer difference) {
        this.difference = difference;
    }

}
