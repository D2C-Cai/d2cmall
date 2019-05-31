package com.d2c.order.model;

import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;
import com.d2c.util.serial.SerialNumUtil;

import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 充值卡模板
 */
@Table(name = "o_cashcard_def")
public class CashCardDef extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 编号 唯一键
     */
    @AssertColumn("会员卡编码不能为空")
    private String code;
    /**
     * 优惠券名称
     */
    @AssertColumn("会员卡编码名称不能为空")
    private String name;
    /**
     * 使用开始时间
     */
    private Date enableDate;
    /**
     * 使用结束时间
     */
    private Date expireDate;
    /**
     * 面额
     */
    @AssertColumn(value = "会员卡编码不能为空, 金额应该大于0", min = 0)
    private BigDecimal amount;
    /**
     * 发放数量最大9999张
     */
    @AssertColumn(value = "会员卡发行数量要大于0", min = 0, maxeq = 9999)
    private Integer quantity = 0;
    /**
     * 优惠券使用说明
     */
    private String remark;
    /**
     * 是否启用(0:定义，-1删除 ，1：已审核，8：已生成)
     */
    private Integer status = 0;

    public CashCard createCashCard(int number) {
        CashCard card = new CashCard();
        card.setAmount(this.getAmount());
        String code = SerialNumUtil.buildCashCardSn(this.getCode(), card.getAmount());
        String str = String.format("%04d", number);
        card.setCode(code + str);
        card.setDefineId(this.getId());
        card.setEnableDate(this.getEnableDate());
        card.setExpireDate(this.getExpireDate());
        card.setName(this.getName());
        card.setRemark(this.getRemark());
        card.setPassword(SerialNumUtil.getRandomNumber(8));
        return card;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getEnableDate() {
        return enableDate;
    }

    public void setEnableDate(Date enableDate) {
        this.enableDate = enableDate;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public String getStatusName() {
        String statusName = "未知";
        if (this.getStatus() == 0) {
            statusName = "未审核";
        } else if (this.getStatus() == 1) {
            statusName = "已审核";
        } else if (this.getStatus() == 8) {
            statusName = "已生成";
        } else if (this.getStatus() == -1) {
            statusName = "删除";
        }
        return statusName;
    }

    public void setStatusName() {
    }

}
