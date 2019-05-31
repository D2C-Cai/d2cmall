package com.d2c.order.dto;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.base.utils.StringUt;
import com.d2c.order.model.Address;
import com.d2c.order.model.Area;

public class AddressDto extends Address {

    private static final long serialVersionUID = 1L;
    /**
     * 省
     */
    private Area province;
    /**
     * 市
     */
    private Area city;
    /**
     * 区
     */
    private Area district;
    /**
     * 下单地址来源
     */
    private String origin;

    public Area getProvince() {
        return province;
    }

    public void setProvince(Area province) {
        if (province != null && province.getCode() != null) {
            this.setRegionPrefix(province.getCode() + "");
        }
        this.province = province;
    }

    public Area getCity() {
        return city;
    }

    public void setCity(Area city) {
        if (city != null && city.getCode() != null) {
            this.setRegionMiddle(city.getCode() + "");
        }
        this.city = city;
    }

    public Area getDistrict() {
        return district;
    }

    public void setDistrict(Area district) {
        if (district != null && district.getCode() != null) {
            this.setRegionSuffix(district.getCode() + "");
        }
        this.district = district;
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("id", this.getId());
        obj.put("isdefault", this.getIsdefault());
        obj.put("name", this.getName());
        obj.put("mobile", StringUt.hideMobile(this.getMobile()));
        obj.put("street", this.getStreet());
        obj.put("province", this.getProvince() == null ? "" : this.getProvince().getName());
        obj.put("city", this.getCity() == null ? "" : this.getCity().getName());
        obj.put("district", this.getDistrict() == null ? "" : this.getDistrict().getName());
        obj.put("provinceCode", this.getProvince() == null ? 0 : this.getProvince().getCode());
        obj.put("cityCode", this.getCity() == null ? "" : this.getCity().getCode());
        obj.put("districtCode", this.getDistrict() == null ? 0 : this.getDistrict().getCode());
        obj.put("email", this.getEmail());
        obj.put("weixin", this.getWeixin());
        obj.put("longitude", this.getLongitude() == null ? 0 : this.getLongitude());
        obj.put("latitude", this.getLatitude() == null ? 0 : this.getLatitude());
        return obj;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("收货信息：");
        String reciver = this.getName() == null ? "" : this.getName();
        String contact = this.getMobile() == null ? "" : this.getMobile();
        String address = this.getStreet() == null ? "" : this.getStreet();
        String province = this.getProvince() == null ? "" : this.getProvince().getName();
        String city = this.getCity() == null ? "" : this.getCity().getName();
        String district = this.getDistrict() == null ? "" : this.getDistrict().getName();
        builder.append(province).append(city).append(district).append(address).append("   ").append(reciver)
                .append("   ").append(contact);
        return builder.toString();
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

}
