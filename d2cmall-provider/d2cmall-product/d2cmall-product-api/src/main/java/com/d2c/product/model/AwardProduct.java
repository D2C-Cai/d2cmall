package com.d2c.product.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;
import java.util.Date;

/**
 * 抽奖商品
 */
@Table(name = "p_product_award")
public class AwardProduct extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 奖品数量
     */
    private Integer quantity;
    /**
     * 奖品权重
     */
    private Integer weight;
    /**
     * 奖品名称
     */
    private String name;
    /**
     * 奖品图片
     */
    private String pic;
    /**
     * 奖品类型 RED, OBJECT, COUPON
     */
    private String type;
    /**
     * 奖品实际对象参数<br>
     * 红包为金额，优惠券为id-productId，实物为空
     */
    private String param;
    /**
     * 中奖级别名称
     */
    private Integer level;
    /**
     * 級別名称
     */
    private String levelName;
    /**
     * 开始时间-奖品实际可抽取开始时间
     */
    private Date startDate;
    /**
     * 结束时间-奖品实际可抽取结束时间
     */
    private Date endDate;
    /**
     * 上下架
     */
    private Integer status;
    /**
     * 场次组ID
     */
    private Long sessionId;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("id", this.getId());
        obj.put("levelName", this.getLevelName());
        obj.put("level", this.getLevel());
        obj.put("name", this.getName());
        obj.put("pic", this.getPic());
        obj.put("type", this.getType());
        obj.put("url", getUrl());
        return obj;
    }

    private String getUrl() {
        switch (this.getType()) {
            case "RED":
                return "/check/redPacket";
            case "OBJECT":
                return "";
            case "COUPON":
                return "/coupon/memberCoupon";
            case "RABATE":
                return "";
        }
        return "";
    }

    public enum AwardType {
        RED("红包"), OBJECT("实物"), COUPON("优惠券"), RABATE("返利");
        private String display;

        AwardType(String display) {
            this.display = display;
        }

        public String getDisplay() {
            return display;
        }

        public void setDisplay(String display) {
            this.display = display;
        }
    }

}
