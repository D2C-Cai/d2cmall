package com.d2c.member.dto;

import com.d2c.member.model.LiveRoom;

public class LiveRoomDto extends LiveRoom {

    private static final long serialVersionUID = 1L;
    /**
     * 品牌名称
     */
    private String designerNames;
    /**
     * 优惠券名称
     */
    private String couponName;

    public String getDesignerNames() {
        return designerNames;
    }

    public void setDesignerNames(String designerNames) {
        this.designerNames = designerNames;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

}
