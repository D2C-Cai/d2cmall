package com.d2c.order.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Table;

/**
 * 门店
 */
@Table(name = "o_store")
public class Store extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 排序
     */
    private Integer sort = 0;
    /**
     * 营业开始时间
     */
    private Integer startHour;
    private Integer startMinute;
    /**
     * 营业结束时间
     */
    private Integer endHour;
    private Integer endMinute;
    /**
     * 经营方式 (DIRECT:直营，DPLUS:D+店)
     */
    private String busType;
    /**
     * 门店服务
     */
    private String storeService;
    /**
     * 封面图
     */
    private String pic;
    /**
     * 代码
     */
    @AssertColumn("商铺代码不能为空")
    private String code;
    /**
     * 名称
     */
    @AssertColumn("商铺名称不能为空")
    private String name;
    /**
     * 电话
     */
    private String tel;
    /**
     * 省份
     */
    private String province;
    /**
     * 城市
     */
    private String city;
    /**
     * 区县
     */
    private String district;
    /**
     * 地址
     */
    private String address;
    /**
     * 联系人
     */
    private String linker;
    /**
     * 高德地图坐标
     */
    private String xy;
    /**
     * 百度地图坐标
     */
    private String bdxy;
    /**
     * 门店介绍
     */
    private String description;
    /**
     * 状态，0停用、1启用
     */
    private Integer status = 0;
    /**
     * 微信号
     */
    private String weixin;

    /**
     * 拆分服务内容
     */
    public String[] getServiceNames() {
        if (StringUtils.isBlank(this.getStoreService())) {
            return null;
        } else {
            return this.getStoreService().split(",");
        }
    }

    public void setServiceNames() {
    }

    public Integer getStartHour() {
        return startHour;
    }

    public void setStartHour(Integer startHour) {
        this.startHour = startHour;
    }

    public Integer getStartMinute() {
        return startMinute;
    }

    public void setStartMinute(Integer startMinute) {
        this.startMinute = startMinute;
    }

    public Integer getEndHour() {
        return endHour;
    }

    public void setEndHour(Integer endHour) {
        this.endHour = endHour;
    }

    public Integer getEndMinute() {
        return endMinute;
    }

    public void setEndMinute(Integer endMinute) {
        this.endMinute = endMinute;
    }

    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }

    public String getStoreService() {
        return storeService;
    }

    public void setStoreService(String storeService) {
        this.storeService = storeService;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLinker() {
        return linker;
    }

    public void setLinker(String linker) {
        this.linker = linker;
    }

    public String getXy() {
        return xy;
    }

    public void setXy(String xy) {
        this.xy = xy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBdxy() {
        return bdxy;
    }

    public void setBdxy(String bdxy) {
        this.bdxy = bdxy;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("name", this.getName());
        obj.put("tel", this.getTel());
        obj.put("address", this.getAddress());
        obj.put("storeService", this.getStoreService());
        obj.put("linker", this.getLinker());
        obj.put("xy", this.getXy());
        obj.put("bdxy", this.getBdxy());
        obj.put("pic", this.getPic());
        obj.put("weixin", this.getWeixin());
        return obj;
    }

    public enum BusTypeEnum {
        DIRECT, DPLUS
    }

}
