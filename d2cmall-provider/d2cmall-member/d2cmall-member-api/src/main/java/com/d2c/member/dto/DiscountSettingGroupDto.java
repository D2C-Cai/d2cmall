package com.d2c.member.dto;

import com.d2c.member.model.DiscountSetting;
import com.d2c.member.model.DiscountSettingGroup;

import java.util.List;

public class DiscountSettingGroupDto extends DiscountSettingGroup {

    private static final long serialVersionUID = 1L;
    /**
     * 折扣
     */
    private List<DiscountSetting> discountSettings;

    public List<DiscountSetting> getDiscountSettings() {
        return discountSettings;
    }

    public void setDiscountSettings(List<DiscountSetting> discountSettings) {
        this.discountSettings = discountSettings;
    }

}
