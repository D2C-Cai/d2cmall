package com.d2c.order.model;

import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Column;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 对账单
 */
@Table(name = "o_statement")
public class Statement extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 单据编号
     */
    private String sn;
    /**
     * 对账单的标题
     */
    private String title;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 线上online 线下line
     */
    private String fromType;
    /**
     * 从系统维护里获取 TODO，直接存JSON(开票名称，税号，开户银行，银行账号)
     */
    @Column(insertable = false)
    private String billInfo;
    /**
     * 账单年份
     */
    private int year;
    /**
     * 账单月份
     */
    private int month;
    /**
     * 月份日期(上旬1，中旬2，下旬3，不分0)
     */
    private int periodOfMonth;
    /**
     * 发送账单时间
     */
    private Date sendDate;
    /**
     * 发送账单人
     */
    private String sender;
    /**
     * 设计师ID
     */
    private Long designerId;
    /**
     * 设计师编号
     */
    private String designerCode;
    /**
     * 设计师名称
     */
    private String designerName;
    /**
     * 商品数量
     */
    private Integer quantity;
    /**
     * 吊牌总金额
     */
    private BigDecimal tagAmount;
    /**
     * 总结算金额
     */
    private BigDecimal settleAmount;
    /**
     * 累计付款金额
     */
    private BigDecimal totalPayAmount;
    /**
     * 对账备注
     */
    private String adminMemo;
    /**
     * 设计师备注
     */
    @Column(insertable = false)
    private String designerMemo;
    /**
     * 支付人
     */
    @Column(insertable = false)
    private String payer;
    /**
     * 支付时间
     */
    @Column(insertable = false)
    private Date payDate;
    /**
     * 收款银行
     */
    @Column(insertable = false)
    private String payBank;
    /**
     * 支付流水号
     */
    @Column(insertable = false)
    private String paySn;
    /**
     * 支付截图
     */
    @Column(insertable = false)
    private String payPic;
    /**
     * 支付金额
     */
    @Column(insertable = false)
    private BigDecimal payMoney;
    /**
     * 支付备注
     */
    @Column(insertable = false)
    private String payMemo;
    @Column(insertable = false)
    private String finishDate;
    /**
     * 是否取消申请用款
     */
    @Column(insertable = false)
    private Integer apply = 0;
    /**
     * 赔偿单数
     */
    private Integer compensationCount = 0;
    /**
     * 应赔偿金额
     */
    private BigDecimal compensationAmount = new BigDecimal(0);
    /**
     * 实际赔偿金额
     */
    private BigDecimal actualCompensationAmount = new BigDecimal(0);
    /**
     * 运营小组
     */
    private String operation;

    public String getStatusName() {
        if (this.status == null) {
            return null;
        }
        return StatementStatus.holder.get(status).getName();
    }

    ;

    public String getStatusString() {
        StatementStatus staus = StatementStatus.getStatus(this.getStatus());
        return staus.name();
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getDesignerId() {
        return designerId;
    }

    public void setDesignerId(Long designerId) {
        this.designerId = designerId;
    }

    public String getDesignerName() {
        return designerName;
    }

    public void setDesignerName(String designerName) {
        this.designerName = designerName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getDesignerCode() {
        return designerCode;
    }

    public void setDesignerCode(String designerCode) {
        this.designerCode = designerCode;
    }

    public String getBillInfo() {
        return billInfo;
    }

    public void setBillInfo(String billInfo) {
        this.billInfo = billInfo;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getFromType() {
        return fromType;
    }

    public void setFromType(String fromType) {
        this.fromType = fromType;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getAdminMemo() {
        return adminMemo;
    }

    public void setAdminMemo(String adminMemo) {
        this.adminMemo = adminMemo;
    }

    public String getDesignerMemo() {
        return designerMemo;
    }

    public void setDesignerMemo(String designerMemo) {
        this.designerMemo = designerMemo;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public String getPayBank() {
        return payBank;
    }

    public void setPayBank(String payBank) {
        this.payBank = payBank;
    }

    public String getPaySn() {
        return paySn;
    }

    public void setPaySn(String paySn) {
        this.paySn = paySn;
    }

    public String getPayPic() {
        return payPic;
    }

    public void setPayPic(String payPic) {
        this.payPic = payPic;
    }

    public BigDecimal getTagAmount() {
        return tagAmount;
    }

    public void setTagAmount(BigDecimal tagAmount) {
        this.tagAmount = tagAmount;
    }

    public BigDecimal getSettleAmount() {
        return settleAmount;
    }

    public void setSettleAmount(BigDecimal settleAmount) {
        this.settleAmount = settleAmount;
    }

    public BigDecimal getTotalPayAmount() {
        return totalPayAmount;
    }

    public void setTotalPayAmount(BigDecimal totalPayAmount) {
        this.totalPayAmount = totalPayAmount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(BigDecimal payMoney) {
        this.payMoney = payMoney;
    }

    public String getPayMemo() {
        return payMemo;
    }

    public void setPayMemo(String payMemo) {
        this.payMemo = payMemo;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public Integer getApply() {
        return apply;
    }

    public void setApply(Integer apply) {
        this.apply = apply;
    }

    public Integer getCompensationCount() {
        return compensationCount;
    }

    public void setCompensationCount(Integer compensationCount) {
        this.compensationCount = compensationCount;
    }

    public BigDecimal getCompensationAmount() {
        return compensationAmount;
    }

    public void setCompensationAmount(BigDecimal compensationAmount) {
        this.compensationAmount = compensationAmount;
    }

    public BigDecimal getActualCompensationAmount() {
        return actualCompensationAmount;
    }

    public void setActualCompensationAmount(BigDecimal actualCompensationAmount) {
        this.actualCompensationAmount = actualCompensationAmount;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public int getPeriodOfMonth() {
        return periodOfMonth;
    }

    public void setPeriodOfMonth(int periodOfMonth) {
        this.periodOfMonth = periodOfMonth;
    }

    public String getPeriodOfMonthName() {
        if (periodOfMonth == 1) {
            return "上旬";
        } else if (periodOfMonth == 2) {
            return "中旬";
        } else if (periodOfMonth == 3) {
            return "下旬";
        }
        return "";
    }

    public BigDecimal getNeedPayMoney() {
        return this.getSettleAmount().subtract(this.getActualCompensationAmount());
    }

    public enum StatementStatus {
        INIT(0), WAITSIGN(1), WAITCONFIRM(2), WAITPAY(3), SUCCESS(8);
        private static Map<Integer, StatementStatus> holder = new HashMap<>();

        static {
            for (StatementStatus staus : values()) {
                holder.put(staus.getCode(), staus);
            }
        }

        private int code;

        StatementStatus(int code) {
            this.code = code;
        }

        public static StatementStatus getStatus(int i) {
            return holder.get(i);
        }

        public int getCode() {
            return code;
        }

        public String getName() {
            switch (this.code) {
                case 0:
                    return "待发送";
                case 1:
                    return "待接收";
                case 2:
                    return "待确认";
                case 3:
                    return "待支付";
                case 8:
                    return "已结算";
            }
            return "未知";
        }
    }

    public enum PeriodOfMonthType {
        zero(0), fitst(1), second(2), third(3);
        private int code;

        PeriodOfMonthType(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getTypeName2() {
            if (code == 1) {
                return "上旬";
            } else if (code == 2) {
                return "中旬";
            } else if (code == 3) {
                return "下旬";
            }
            return "";
        }
    }

}
