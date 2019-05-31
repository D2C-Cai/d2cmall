package com.d2c.order.query;

import com.d2c.common.api.query.model.BaseQuery;
import com.d2c.order.model.CollageOrder.CollageOrderStatus;

import java.util.Date;

public class CollageOrderSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 活动id
     */
    private Long promotionId;
    /**
     * 会员id
     */
    private Long memberId;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 状态
     */
    private Integer[] orderStatus;
    /**
     * 创建时间开始
     */
    private Date beginCreateDate;
    /**
     * 创建时间结束/订单活动最晚结束
     */
    private Date endCreateDate;
    /**
     * 团id
     */
    private Long groupId;
    /**
     * 拼团单编号
     */
    private String sn;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 支付方式
     */
    private Integer paymentType;
    /**
     * 角色
     */
    private Integer type;
    /**
     * 账号
     */
    private String loginCode;
    /**
     * 团编号
     */
    private String groupSn;

    public Long getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Long promotionId) {
        this.promotionId = promotionId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer[] getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer[] orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getBeginCreateDate() {
        return beginCreateDate;
    }

    public void setBeginCreateDate(Date beginCreateDate) {
        this.beginCreateDate = beginCreateDate;
    }

    public Date getEndCreateDate() {
        return endCreateDate;
    }

    public void setEndCreateDate(Date endCreateDate) {
        this.endCreateDate = endCreateDate;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }

    public String getGroupSn() {
        return groupSn;
    }

    public void setGroupSn(String groupSn) {
        this.groupSn = groupSn;
    }

    /**
     * 根据index分析状态
     *
     * @param index
     */
    public void handleIndex(Integer index) {
        if (index != null) {
            switch (index) {
                case -1:
                    // 已关闭
                    this.orderStatus = new Integer[]{CollageOrderStatus.AFTERCLOSE.getCode(),
                            CollageOrderStatus.CLOSE.getCode(), CollageOrderStatus.REFUND.getCode()};
                    break;
                case 1:
                    // 交易中
                    this.orderStatus = new Integer[]{CollageOrderStatus.WAITFORPAY.getCode(),
                            CollageOrderStatus.PROCESS.getCode()};
                    break;
                case 8:
                    // 交易完成
                    this.orderStatus = new Integer[]{CollageOrderStatus.SUCESS.getCode()};
                    break;
            }
        }
    }

}
