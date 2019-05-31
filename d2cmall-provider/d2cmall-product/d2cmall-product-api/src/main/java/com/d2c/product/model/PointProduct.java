package com.d2c.product.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 积分商品
 */
@Table(name = "p_point_product")
public class PointProduct extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 名称
     */
    private String name;
    /**
     * 类型
     */
    private String type;
    /**
     * 卡来源
     */
    private Long sourceId;
    /**
     * 积分
     */
    private Integer point;
    /**
     * 金额
     */
    private BigDecimal amount = new BigDecimal(0);
    /**
     * 开始时间
     */
    private Date startDate;
    /**
     * 结束时间
     */
    private Date endDate;
    /**
     * 状态
     */
    private Integer mark = 0;
    /**
     * 账号限领
     */
    private Integer limitCount;
    /**
     * 描述
     */
    private String description;
    /**
     * 排序
     */
    private Integer sort = 0;
    /**
     * 数量
     */
    private Integer count;
    /**
     * 对应优惠券或商品id
     */
    private Long productId;
    /**
     * 列表图
     */
    private String introPic;
    /**
     * 详情图
     */
    private String detailPic;

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    public Integer getLimitCount() {
        return limitCount;
    }

    public void setLimitCount(Integer limitCount) {
        this.limitCount = limitCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getIntroPic() {
        return introPic;
    }

    public void setIntroPic(String introPic) {
        this.introPic = introPic;
    }

    public String getDetailPic() {
        return detailPic;
    }

    public void setDetailPic(String detailPic) {
        this.detailPic = detailPic;
    }

    public boolean isOver() {
        if (this.getMark() == 1 && this.getStartDate().compareTo(new Date()) < 0
                && this.getEndDate().compareTo(new Date()) > 0) {
            return false;
        }
        return true;
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("id", this.getId());
        obj.put("name", this.getName());
        obj.put("type", this.getType());
        obj.put("point", this.getPoint());
        obj.put("count", this.getCount());
        obj.put("amount", this.getAmount());
        obj.put("startDate", this.getStartDate().getTime());
        obj.put("endDate", this.getEndDate().getTime());
        obj.put("description", this.getDescription());
        obj.put("productId", this.getProductId());
        obj.put("introPic", this.getIntroPic());
        obj.put("detailPic", this.getDetailPic());
        return obj;
    }

    public enum PointProductTypeEnum {
        CARD("外部卡"), COUPON("优惠券"), RED("红包");
        String display;

        PointProductTypeEnum(String display) {
            this.display = display;
        }
    }

}
