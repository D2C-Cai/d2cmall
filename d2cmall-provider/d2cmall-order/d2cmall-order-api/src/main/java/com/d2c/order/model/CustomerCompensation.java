package com.d2c.order.model;

import com.d2c.common.api.model.PreUserDO;
import com.d2c.util.date.DateUtil;

import javax.persistence.Table;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 客户赔偿单
 */
@Table(name = "o_cus_compensation")
public class CustomerCompensation extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 业务id
     */
    private Long transactionId;
    /**
     * 业务编号
     */
    private String transactionSn;
    /**
     * 业务时间
     */
    private Date transactionTime;
    /**
     * 商品sku
     */
    private String productSku;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 交易金额
     */
    private BigDecimal tradeAmount;
    /**
     * 赔偿金额
     */
    private BigDecimal compensationAmount;
    /**
     * 会员id
     */
    private Long memberId;
    /**
     * 会员账号
     */
    private String loginCode;
    /**
     * 类型
     */
    private Integer type;
    /**
     * 备注
     */
    private String remark;
    /**
     * 预计发货时间
     */
    private Date estimateDate;
    public CustomerCompensation() {
    }
    /**
     * 订单生成的赔偿单
     *
     * @param orderItem
     * @param payAmount
     */
    public CustomerCompensation(OrderItem orderItem, BigDecimal payAmount, Integer type, String loginCode) {
        this.transactionId = orderItem.getId();
        this.transactionSn = orderItem.getOrderSn();
        this.type = type;
        this.status = 0;
        this.tradeAmount = orderItem.getActualAmount();
        this.compensationAmount = payAmount;
        this.memberId = orderItem.getBuyerMemberId();
        this.loginCode = loginCode;
        this.productSku = orderItem.getProductSkuSn();
        this.estimateDate = orderItem.getEstimateDate();
        this.transactionTime = orderItem.getPaymentTime();
    }

    public Date getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Date transactionTime) {
        this.transactionTime = transactionTime;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionSn() {
        return transactionSn;
    }

    public void setTransactionSn(String transactionSn) {
        this.transactionSn = transactionSn;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigDecimal getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount(BigDecimal tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    public BigDecimal getCompensationAmount() {
        return compensationAmount;
    }

    public void setCompensationAmount(BigDecimal compensationAmount) {
        this.compensationAmount = compensationAmount;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }

    public String getProductSku() {
        return productSku;
    }

    public void setProductSku(String productSku) {
        this.productSku = productSku;
    }

    public Date getEstimateDate() {
        return estimateDate;
    }

    public void setEstimateDate(Date estimateDate) {
        this.estimateDate = estimateDate;
    }

    public Integer getExpiredDay() {
        try {
            return DateUtil.daysBetween(this.getEstimateDate(), this.createDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 赔偿状态
     * <p>
     * 未赔偿是订单明细还未到完结中；待赔偿是订单明细已完结 ，等待财务审核
     */
    public enum CompensationStatus {
        CLOSE(-1, "关闭"), INIT(0, "未赔偿"), WAITFORPAY(4, "待赔偿"), SUCCESS(8, "已赔偿");
        private static Map<Integer, CompensationStatus> holder = new HashMap<>();

        static {
            for (CompensationStatus status : CompensationStatus.values()) {
                holder.put(status.getCode(), status);
            }
        }

        private Integer code;
        private String name;

        CompensationStatus(Integer code, String name) {
            this.code = code;
            this.name = name;
        }

        public static String getStatusName(Integer code) {
            return holder.get(code).getName();
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    /**
     * 赔偿单生成来源
     */
    public enum CompensationType {
        OVERDELIVERY(0, "超时发货"), OVERAFTER(1, "超时售后"), UNSOLVED(2, "未处理"),
        MANUAL(3, "手工处理"), DESIGNERDELIVERY(4, "设计师超时处理");
        private static Map<Integer, CompensationType> holder = new HashMap<>();

        static {
            for (CompensationType type : CompensationType.values()) {
                holder.put(type.getCode(), type);
            }
        }

        private Integer code;
        private String name;

        private CompensationType(Integer code, String name) {
            this.code = code;
            this.name = name;
        }

        public static String getTypeName(Integer code) {
            return holder.get(code).getName();
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
