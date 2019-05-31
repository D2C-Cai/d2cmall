package com.d2c.content.query;

import com.d2c.common.api.query.model.BaseQuery;

import java.util.Date;

public class BrandApplySearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 会员ID
     */
    private Long memberId;
    /**
     * 品牌名称
     */
    private String brand;
    /**
     * 设计师名称，多个按，分割
     */
    private String designers;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 创建时间开始
     */
    private Date startCreateDate;
    /**
     * 创建时间结束
     */
    private Date endCreateDate;
    /**
     * 处理时间开始
     */
    private Date startDealDate;
    /**
     * 处理时间结束
     */
    private Date endDealDate;

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDesigners() {
        return designers;
    }

    public void setDesigners(String designers) {
        this.designers = designers;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getStartCreateDate() {
        return startCreateDate;
    }

    public void setStartCreateDate(Date startCreateDate) {
        this.startCreateDate = startCreateDate;
    }

    public Date getEndCreateDate() {
        return endCreateDate;
    }

    public void setEndCreateDate(Date endCreateDate) {
        this.endCreateDate = endCreateDate;
    }

    public Date getStartDealDate() {
        return startDealDate;
    }

    public void setStartDealDate(Date startDealDate) {
        this.startDealDate = startDealDate;
    }

    public Date getEndDealDate() {
        return endDealDate;
    }

    public void setEndDealDate(Date endDealDate) {
        this.endDealDate = endDealDate;
    }

}
