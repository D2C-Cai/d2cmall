package com.d2c.order.query;

import com.d2c.common.api.query.model.BaseQuery;
import com.d2c.order.model.CouponDef.CouponType;

import java.util.Date;

public class CouponDefSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * ID
     */
    private Long id;
    /**
     * IDS
     */
    private String ids;
    /**
     * 名称
     */
    private String name;
    /**
     * 创建时间开始
     */
    private Date beginCreateDate;
    /**
     * 创建时间结束
     */
    private Date endCreateDate;
    /**
     * 类型
     */
    private CouponType type;
    /**
     * 是否启用
     */
    private Boolean enable;
    /**
     * 优惠券组的ID
     */
    private Long groupId;
    /**
     * 暗号
     */
    private String cipher;
    /**
     * 是否免费
     */
    private Integer free;
    /**
     * 优惠券定义Id的集合
     */
    private Long[] couponDefIds;
    /**
     * 1：全场，2：指定设计师，3：指定商品
     */
    private String checkAssociation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CouponType getType() {
        return type;
    }

    public void setType(CouponType type) {
        this.type = type;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getCipher() {
        return cipher;
    }

    public void setCipher(String cipher) {
        this.cipher = cipher;
    }

    public Integer getFree() {
        return free;
    }

    public void setFree(Integer free) {
        this.free = free;
    }

    public Date getBeginCreateDate() {
        return beginCreateDate;
    }

    public void setBeginCreateDate(Date beginCreateDate) {
        this.beginCreateDate = beginCreateDate;
    }

    public Date getEndCreateDate() {
        return endCreateDate;
    }

    public void setEndCreateDate(Date endCreateDate) {
        this.endCreateDate = endCreateDate;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public Long[] getCouponDefIds() {
        return couponDefIds;
    }

    public void setCouponDefIds(Long[] couponDefIds) {
        this.couponDefIds = couponDefIds;
    }

    public String getCheckAssociation() {
        return checkAssociation;
    }

    public void setCheckAssociation(String checkAssociation) {
        this.checkAssociation = checkAssociation;
    }

}
