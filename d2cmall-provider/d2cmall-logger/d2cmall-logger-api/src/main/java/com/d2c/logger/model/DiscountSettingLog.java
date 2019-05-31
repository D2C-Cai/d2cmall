package com.d2c.logger.model;

import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 折扣组商品日志
 */
@Table(name = "log_discountsetting")
public class DiscountSettingLog extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 折扣ID
     */
    private Long discountSettingId;
    /**
     * 折扣类型
     */
    private String disType;
    /**
     * 分销商ID
     */
    private Long distributorId;
    /**
     * 目标ID
     */
    private Long targetId;
    /**
     * 折扣
     */
    private BigDecimal discount;
    /**
     * 折后产品金额
     */
    private BigDecimal price = new BigDecimal(0);
    /**
     * 0停用，1启用
     */
    private Integer status = 0;
    /**
     * 用户ID
     */
    private Long memberId;

    public DiscountSettingLog() {
    }

    public Long getDiscountSettingId() {
        return discountSettingId;
    }

    public void setDiscountSettingId(Long discountSettingId) {
        this.discountSettingId = discountSettingId;
    }

    public String getDisType() {
        return disType;
    }

    public void setDisType(String disType) {
        this.disType = disType;
    }

    public Long getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(Long distributorId) {
        this.distributorId = distributorId;
    }

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

}
