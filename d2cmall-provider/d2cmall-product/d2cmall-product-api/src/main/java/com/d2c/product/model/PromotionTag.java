package com.d2c.product.model;

import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;
import java.util.Date;

/**
 * 活动标签
 */
@Table(name = "p_promotion_tag")
public class PromotionTag extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 标签名称
     */
    private String name;
    /**
     * 是否是固定标签，固定标签不能删除
     */
    private Integer fixed = 0;
    /**
     * 上架时间（按这个排序）
     */
    private Date upDateTime;
    /**
     * 上下架 1上架 0下架
     */
    private Integer status = 0;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getFixed() {
        return fixed;
    }

    public void setFixed(Integer fixed) {
        this.fixed = fixed;
    }

    public Date getUpDateTime() {
        return upDateTime;
    }

    public void setUpDateTime(Date upDateTime) {
        this.upDateTime = upDateTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
