package com.d2c.product.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.model.PreUserDO;
import com.d2c.util.date.DateUtil;

import javax.persistence.Table;
import java.util.Date;

/**
 * 限时购场次
 */
@Table(name = "p_flash_promotion")
public class FlashPromotion extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 活动名称
     */
    private String name;
    /**
     * 活动类型
     */
    private String promotionType;
    /**
     * 0表示商品，1表示品牌
     */
    private Integer promotionScope;
    /**
     * 限购数量，不限购就9999
     */
    private Integer limitQuantity = 9999;
    /**
     * 开始时间
     */
    private Date startDate;
    /**
     * 结束时间
     */
    private Date endDate;
    /**
     * 场次
     */
    private String session;
    /**
     * 场次名称
     */
    private String sessionName;
    /**
     * 场次备注
     */
    private String sessionRemark;
    /**
     * 品牌图片
     */
    private String brandPic;
    /**
     * 品牌排序
     */
    private Integer sort = 0;
    /**
     * 状态 0下架，1上架，-1 删除
     */
    private Integer status = 0;
    /**
     * 渠道
     */
    private String channel = channelType.MEMBER.name();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPromotionType() {
        return promotionType;
    }

    public void setPromotionType(String promotionType) {
        this.promotionType = promotionType;
    }

    public Integer getPromotionScope() {
        return promotionScope;
    }

    public void setPromotionScope(Integer promotionScope) {
        this.promotionScope = promotionScope;
    }

    public Integer getLimitQuantity() {
        return limitQuantity;
    }

    public void setLimitQuantity(Integer limitQuantity) {
        this.limitQuantity = limitQuantity;
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

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public String getSessionRemark() {
        return sessionRemark;
    }

    public void setSessionRemark(String sessionRemark) {
        this.sessionRemark = sessionRemark;
    }

    public String getBrandPic() {
        return brandPic;
    }

    public void setBrandPic(String brandPic) {
        this.brandPic = brandPic;
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

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public boolean isOver() {
        Date date = new Date();
        if (this.status != null && status == 0) {
            return true;
        }
        if (date.getTime() >= this.getStartDate().getTime() && date.getTime() <= this.getEndDate().getTime()) {
            return false;
        }
        return true;
    }

    public boolean isEnd() {
        Date date = new Date();
        if (this.status != null && status == 0) {
            return true;
        }
        if (status == 1 && date.before(endDate)) {
            return false;
        }
        return true;
    }

    public String getStatusName() {
        Date now = new Date();
        if (now.after(startDate)) {
            return "已开抢";
        }
        if (now.before(startDate)) {
            return "即将开始";
        }
        return "";
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        Date now = new Date();
        obj.put("id", this.getId());
        obj.put("name", this.getName());
        obj.put("statusName", this.getStatusName());
        obj.put("promotionType", this.getPromotionType());
        obj.put("promotionScope", this.getPromotionScope());
        obj.put("limitQuantity", this.getLimitQuantity());
        obj.put("session", this.getSession());
        obj.put("sessionName", this.getSessionName() == null ? "" : this.getSessionName());
        obj.put("sessionRemark", this.getSessionRemark() == null ? "精选大牌，限时快抢" : this.getSessionRemark());
        obj.put("startDate", this.getStartDate() == null ? "" : DateUtil.second2str2(this.getStartDate()));
        obj.put("endDate", this.getEndDate() == null ? "" : DateUtil.second2str2(this.getEndDate()));
        obj.put("startTimeStamp", this.getStartDate() == null ? 0 : this.getStartDate().getTime());
        obj.put("endTimeStamp", this.getEndDate() == null ? 0 : this.getEndDate().getTime());
        obj.put("flashUrl", "/flashpromotion/product/session");
        obj.put("brandPic", this.getBrandPic() == null ? "" : this.getBrandPic());
        obj.put("remaining", this.getEndDate() == null ? 0 : this.getEndDate().getTime() - now.getTime());
        return obj;
    }

    public enum channelType {
        MEMBER, PARTNER
    }

    public enum promotionTypeEnum {
        FLASH(0, "限时购"), LUXURIES(1, "大牌抢购");
        private String name;
        private int code;

        private promotionTypeEnum(int code, String name) {
            this.name = name;
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }
    }

}
