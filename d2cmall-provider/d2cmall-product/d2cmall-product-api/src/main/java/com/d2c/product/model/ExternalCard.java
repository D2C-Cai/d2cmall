package com.d2c.product.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;
import java.util.Date;

/**
 * 外部卡券
 */
@Table(name = "p_external_card")
public class ExternalCard extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 兑换码
     */
    private String code;
    /**
     * 类型
     */
    private Long sourceId;
    /**
     * 状态
     */
    private Integer status = 0;
    /**
     * 兑换人ID
     */
    private Long memberId;
    /**
     * 兑换流水号
     */
    private String sn;
    /**
     * 兑换时间
     */
    private Date useDate;
    /**
     * 商品ID
     */
    private Long productId;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public Date getUseDate() {
        return useDate;
    }

    public void setUseDate(Date useDate) {
        this.useDate = useDate;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("code", this.getCode());
        obj.put("sourceId", this.getSourceId());
        obj.put("useDate", this.getUseDate().getTime());
        obj.put("sn", this.getSn());
        return obj;
    }

}
