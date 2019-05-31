package com.d2c.member.model;

import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 折扣组商品
 */
@Table(name = "m_discount")
public class DiscountSetting extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 折扣类型
     */
    private String disType;
    /**
     * 分销商
     */
    private Long distributorId;

    ;
    /**
     * 折扣组ID
     */
    private Long groupId;
    /**
     * 目标Id
     */
    private Long targetId;
    /**
     * 折扣
     */
    private BigDecimal discount = new BigDecimal(1);
    /**
     * 0停用，1启用,-1（删除归档）
     */
    private Integer status = 0;

    public String getDiscountTypeName() {
        if (this.getDisType() == null) {
            return "";
        }
        switch (DiscountType.valueOf(this.getDisType()).getCode()) {
            case 1:
                return "设计师";
            case 2:
                return "商品";
            case 8:
                return "全场";
            default:
                return "未知";
        }
    }

    public void setDiscountTypeName() {
    }

    @AssertColumn
    public String validate() {
        if (distributorId != null && groupId != null) {
            return "经销商Id和折扣组ID不能同时为空";
        }
        return null;
    }

    public String getStatusName() {
        switch (status) {
            case -1:
                return "归档";
            case 0:
                return "停用";
            case 1:
                return "启用";
        }
        return "未知";
    }

    public void setStatusName() {
    }

    public BigDecimal getPromotionDiscount() {
        return new BigDecimal(1).subtract(getDiscount());
    }

    public void setPromotionDiscount() {
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
        if (discount == null || discount.compareTo(new BigDecimal(0)) <= 0
                || discount.compareTo(new BigDecimal(1)) > 0) {
            this.discount = new BigDecimal(1);
        } else {
            this.discount = discount;
        }
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public enum DiscountType {
        ALL(8), DESIGNER(1), PRODUCT(2);
        private int code;

        DiscountType(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

}
