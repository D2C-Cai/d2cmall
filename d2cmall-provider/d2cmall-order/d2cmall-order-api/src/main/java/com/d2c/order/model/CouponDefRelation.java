package com.d2c.order.model;

import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;

/**
 * 优惠券使用关系
 */
@Table(name = "o_coupon_def_relation")
public class CouponDefRelation extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 关联类型
     */
    private String type;
    /**
     * 优惠券定义ID
     */
    @AssertColumn("优惠券定义ID不能为空")
    private Long couponDefId;
    /**
     * 对象ID
     */
    @AssertColumn("优惠券关联对象不能为空")
    private Long targetId;

    public Long getCouponDefId() {
        return couponDefId;
    }

    ;

    public void setCouponDefId(Long couponDefId) {
        this.couponDefId = couponDefId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    /**
     * PRODUCT：指定商品，DESIGNER：指定设计师
     */
    public enum CouponRelationType {
        PRODUCT(1), DESIGNER(2);
        protected int code;

        CouponRelationType(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

}
