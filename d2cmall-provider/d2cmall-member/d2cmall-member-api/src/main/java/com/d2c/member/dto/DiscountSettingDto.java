package com.d2c.member.dto;

import com.d2c.member.model.DiscountSetting;
import com.d2c.member.model.DiscountSettingGroup;

public class DiscountSettingDto extends DiscountSetting {

    private static final long serialVersionUID = 1L;
    /**
     * 折扣组
     */
    private DiscountSettingGroup discountSettingGroup;
    /**
     * 商品
     */
    private Object product;

    public DiscountSettingGroup getDiscountSettingGroup() {
        return discountSettingGroup;
    }

    public void setDiscountSettingGroup(DiscountSettingGroup discountSettingGroup) {
        this.discountSettingGroup = discountSettingGroup;
    }

    public Object getProduct() {
        return product;
    }

    public void setProduct(Object product) {
        this.product = product;
    }

}
