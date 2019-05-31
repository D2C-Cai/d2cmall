package com.d2c.order.query;

import com.d2c.common.api.query.model.BaseQuery;

import java.util.Date;
import java.util.List;

public class OrderSaleSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 开始时间
     */
    private Date startDate;
    /**
     * 结束时间
     */
    private Date endDate;
    /**
     * 商品id
     */
    private List<Long> productId;
    /**
     * 是否需要图片
     */
    private Boolean hasPic = false;
    /**
     * 按商品id（0）,还是款号（1），还是条码查询（2）,
     */
    private Integer type = 0;
    /**
     * 商品款号
     */
    private List<String> productSn;
    /**
     * 商品条码
     */
    private List<String> barCode;

    public List<Long> getProductId() {
        return productId;
    }

    public void setProductId(List<Long> productId) {
        this.productId = productId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Boolean getHasPic() {
        return hasPic;
    }

    public void setHasPic(Boolean hasPic) {
        this.hasPic = hasPic;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public List<String> getProductSn() {
        return productSn;
    }

    public void setProductSn(List<String> productSn) {
        this.productSn = productSn;
    }

    public List<String> getBarCode() {
        return barCode;
    }

    public void setBarCode(List<String> barCode) {
        this.barCode = barCode;
    }

}
