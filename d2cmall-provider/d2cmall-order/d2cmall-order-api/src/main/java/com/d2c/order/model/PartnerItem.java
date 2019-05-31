package com.d2c.order.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 分销日志
 */
@Table(name = "o_partner_item")
public class PartnerItem extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 分销商ID
     */
    private Long partnerId;
    /**
     * 业务类型
     */
    private String sourceType = PartnerLogType.BILL.toString();
    /**
     * 业务ID
     */
    private Long sourceId;
    /**
     * 业务编号
     */
    private String sourceSn;
    /**
     * 1：收入，-1：支出
     */
    private Integer direction = 1;
    /**
     * 变动金额
     */
    private BigDecimal amount;
    /**
     * 业务状态
     */
    private Integer sourceStatus;

    public PartnerItem() {
    }

    public PartnerItem(PartnerLogType type) {
        this.sourceStatus = 8;
        this.sourceType = type.name();
        this.direction = type.getDirect();
    }

    public Long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Long partnerId) {
        this.partnerId = partnerId;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public String getSourceSn() {
        return sourceSn;
    }

    public void setSourceSn(String sourceSn) {
        this.sourceSn = sourceSn;
    }

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getSourceStatus() {
        return sourceStatus;
    }

    public void setSourceStatus(Integer sourceStatus) {
        this.sourceStatus = sourceStatus;
    }

    public String getStatusName() {
        return "已完成";
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("id", this.getId());
        obj.put("createDate", this.getCreateDate());
        obj.put("amount", this.getAmount());
        obj.put("direction", this.getDirection());
        obj.put("sourceType", this.getSourceType());
        obj.put("typeName", PartnerLogType.valueOf(this.getSourceType()).getDisplay());
        obj.put("sourceSn", this.getSourceSn());
        obj.put("sourceStatus", this.getSourceStatus());
        obj.put("statusName", this.getStatusName());
        return obj;
    }

    public enum PartnerLogType {
        BILL("个人返利", 1, "返利流水："), TEAM("团队返利", 1, "返利流水："), OTHER("其他奖励", 1, "奖励账号："),
        GIFT("礼包奖励", 1, "订单编号："), INVITE("邀请奖励", 1, "受邀账号："), MASTER("导师奖励", 1, "导师账号："),
        DIFF("差价扣减", -1, "返利流水："), WITHHOLD("门店扣减", -1, "扣减单号："), CASH("提现成功", -1, "提现单号：");
        private String display;
        private Integer direct;
        private String desc;

        PartnerLogType(String display, Integer direct, String desc) {
            this.display = display;
            this.direct = direct;
            this.desc = desc;
        }

        public String getDisplay() {
            return display;
        }

        public void setDisplay(String display) {
            this.display = display;
        }

        public Integer getDirect() {
            return direct;
        }

        public void setDirect(Integer direct) {
            this.direct = direct;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

}
