package com.d2c.order.query;

import com.d2c.common.api.query.model.BaseQuery;

import java.util.Date;

public class CartItemSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 产品编号
     */
    private String productSn;
    /**
     * 开始时间
     */
    private Date startDate;
    /**
     * 结束时间
     */
    private Date endDate;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 设计师是ID
     */
    private Long designerId;
    /**
     * 设计师名称
     */
    private String designerName;
    /**
     * 登录账号
     */
    private String loginCode;
    /**
     * 用户ID
     */
    private Long buyMemberId;
    /**
     * 设备终端
     */
    private String device;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDesignerName() {
        return designerName;
    }

    public void setDesignerName(String designerName) {
        this.designerName = designerName;
    }

    public String getProductSn() {
        return productSn;
    }

    public void setProductSn(String productSn) {
        this.productSn = productSn;
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

    public String getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }

    public Long getBuyMemberId() {
        return buyMemberId;
    }

    public void setBuyMemberId(Long buyMemberId) {
        this.buyMemberId = buyMemberId;
    }

    public Long getDesignerId() {
        return designerId;
    }

    public void setDesignerId(Long designerId) {
        this.designerId = designerId;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

}
