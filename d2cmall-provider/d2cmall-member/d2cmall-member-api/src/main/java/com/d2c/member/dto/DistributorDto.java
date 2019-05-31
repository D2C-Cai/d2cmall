package com.d2c.member.dto;

import com.d2c.member.model.DiscountSetting;
import com.d2c.member.model.DiscountSettingGroup;
import com.d2c.member.model.Distributor;
import com.d2c.member.model.MemberInfo;

import java.util.List;

public class DistributorDto extends Distributor {

    private static final long serialVersionUID = 1L;
    /**
     * 会员
     */
    private MemberInfo member;
    /**
     * 折扣
     */
    private List<DiscountSetting> discountSettings;
    /**
     * 折扣组信息
     */
    private DiscountSettingGroup discountSettingGroup;

    public DiscountSettingGroup getDiscountSettingGroup() {
        return discountSettingGroup;
    }

    public void setDiscountSettingGroup(DiscountSettingGroup discountSettingGroup) {
        this.discountSettingGroup = discountSettingGroup;
    }

    public MemberInfo getMember() {
        return member;
    }

    public void setMember(MemberInfo member) {
        this.member = member;
    }

    public List<DiscountSetting> getDiscountSettings() {
        return discountSettings;
    }

    public void setDiscountSettings(List<DiscountSetting> discountSettings) {
        this.discountSettings = discountSettings;
    }

}
