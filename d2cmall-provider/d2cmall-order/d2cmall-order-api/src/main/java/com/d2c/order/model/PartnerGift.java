package com.d2c.order.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.model.PreUserDO;
import com.d2c.common.base.utils.StringUt;
import com.d2c.util.date.DateUtil;

import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 分销商礼包记录
 */
@Table(name = "o_partner_gift")
public class PartnerGift extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 订单编号
     */
    private String orderSn;
    /**
     * 商品ID
     */
    private Long productId;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 商品图片
     */
    private String productPic;
    /**
     * 买家账号
     */
    private String loginCode;
    /**
     * 买手ID
     */
    private Long partnerId;
    /**
     * DMID
     */
    private Long parentId;
    /**
     * AMID
     */
    private Long masterId;
    /**
     * 买手返利
     */
    private BigDecimal partnerRebate;
    /**
     * DM返利
     */
    private BigDecimal parentRebate;
    /**
     * AM返利
     */
    private BigDecimal masterRebate;
    /**
     * 奖励状态
     */
    private Integer status = 1;

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPic() {
        return productPic;
    }

    public void setProductPic(String productPic) {
        this.productPic = productPic;
    }

    public String getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }

    public Long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Long partnerId) {
        this.partnerId = partnerId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getMasterId() {
        return masterId;
    }

    public void setMasterId(Long masterId) {
        this.masterId = masterId;
    }

    public BigDecimal getPartnerRebate() {
        return partnerRebate;
    }

    public void setPartnerRebate(BigDecimal partnerRebate) {
        this.partnerRebate = partnerRebate;
    }

    public BigDecimal getParentRebate() {
        return parentRebate;
    }

    public void setParentRebate(BigDecimal parentRebate) {
        this.parentRebate = parentRebate;
    }

    public BigDecimal getMasterRebate() {
        return masterRebate;
    }

    public void setMasterRebate(BigDecimal masterRebate) {
        this.masterRebate = masterRebate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getInviteId() {
        if (this.partnerId != null) {
            return partnerId;
        }
        if (this.parentId != null) {
            return parentId;
        }
        if (this.masterId != null) {
            return masterId;
        }
        return null;
    }

    public BigDecimal getInviteRebate(Long partnerId) {
        if (partnerId == null) {
            return new BigDecimal(0);
        }
        if (this.partnerId != null && this.partnerId.equals(partnerId)) {
            return this.partnerRebate;
        }
        if (this.parentId != null && this.parentId.equals(partnerId)) {
            return this.parentRebate;
        }
        if (this.masterId != null && this.masterId.equals(partnerId)) {
            return this.masterRebate;
        }
        return new BigDecimal(0);
    }

    public JSONObject toJson(Long partnerId) {
        JSONObject obj = new JSONObject();
        obj.put("createDate", DateUtil.second2str2(this.getCreateDate()));
        obj.put("orderSn", this.getOrderSn());
        obj.put("productId", this.getProductId());
        obj.put("productName", this.getProductName());
        obj.put("productPic", this.getProductPic());
        obj.put("loginCode", this.getLoginCode() == null ? "" : StringUt.hideMobile(this.getLoginCode()));
        obj.put("inviteId", this.getInviteId());
        obj.put("inviteRebate", this.getInviteRebate(partnerId));
        return obj;
    }

}
