package com.d2c.order.model;

import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;
import com.d2c.util.string.StringUtil;

import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 门店预约单
 */
@Table(name = "o_store_reserve")
public class O2oSubscribe extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 用户ID
     */
    @AssertColumn("会员ID不能为空")
    private Long memberId;
    /**
     * 预约单号
     */
    private String sn;
    /**
     * 门店ID
     */
    private Long storeId;
    /**
     * 门店名称
     */
    private String storeName;
    /**
     * 门店电话
     */
    private String storeTel;
    /**
     * 门店地址
     */
    private String storeAddress;
    /**
     * 联系方式
     */
    private String contact;
    /**
     * 客户联系电话
     */
    private String tel;
    /**
     * 客户联系Email
     */
    private String email;
    /**
     * 微信号
     */
    private String wechat;
    /**
     * 客户联系人
     */
    private String name;
    /**
     * 性别
     */
    private String sex = "女";
    /**
     * 人数（1,2-3,4人以上）
     */
    private Integer numbers = 1;
    /**
     * 预定抵达日期
     */
    private Date estimateDate;
    /**
     * 预定抵达时间,上午、下午、晚上
     */
    private String estimateTime;
    /**
     * 其它要求
     */
    private String optionRequire;
    /**
     * 预约开始时间
     */
    private Integer startHour;
    private Integer startMinute;
    /**
     * 预约结束时间
     */
    private Integer endHour;
    private Integer endMinute;
    /**
     * 门店服务，客户需求
     */
    private String storeService;
    /**
     * 来店伴侣
     */
    private String companion;
    /**
     * -2客服取消 ，-1用户取消，0用户未提交预约，1已提交待审核，2客服已通知门店，3门店已接收，4门店准备完成，5服务已完成，6客户已评价
     */
    private Integer status;
    /**
     * 客户提交时间
     */
    private Date submitDate;
    /**
     * 客服通知时间
     */
    private Date noticeDate;
    /**
     * 客服通知人
     */
    private String noticeMan;
    /**
     * 门店接收时间
     */
    private Date receiveDate;
    /**
     * 门店接收人
     */
    private String receiveMan;
    /**
     * 门店完成时间
     */
    private Date completeDate;
    /**
     * 客户购买金额
     */
    private BigDecimal payAmount;
    /**
     * 门店完成人
     */
    private String completeMan;
    /**
     * 实际到店人数
     */
    private Integer actualNumbers = 0;
    /**
     * 客户实际购买的零售单(多个用英文逗号隔开)
     */
    private String retailSn;
    /**
     * 线下客户购买状态：-1已取消，0未购买，1已购买
     */
    private Integer payStatus = 0;
    /**
     * 门店结果反馈说明
     */
    private String feedback;
    /**
     * 门店反馈时间
     */
    private Date feedbackDate;
    /**
     * 客服备注
     */
    private String remark;
    /**
     * 客服备注时间
     */
    private Date remarkDate;
    /**
     * 客服回访
     */
    private String visit;
    /**
     * 客服回访时间
     */
    private Date visitDate;
    /**
     * 客服反馈金额
     */
    private BigDecimal cusCost;
    /**
     * 预约取消人
     */
    private String cancelMan;
    /**
     * 取消原因
     */
    private String cancelReason;
    /**
     * 取消时间
     */
    private Date cancelDate;
    /**
     * 评价ID
     */
    private Long commentId;

    public String getStatusName() {
        if (this.getStatus() != null) {
            switch (this.getStatus()) {
                case -2:
                    return "客服取消";
                case -1:
                    return "用户取消";
                case 0:
                    return "用户未提交预约";
                case 1:
                    return "已提交待审核";
                case 2:
                    return "客服已通知门店";
                case 3:
                    return "门店已接收";
                case 4:
                    return "门店准备完成";
                case 5:
                    return "服务已完成";
                case 6:
                    return "客户已评价";
            }
        }
        return "";
    }

    public void setStatusName() {
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

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreTel() {
        return storeTel;
    }

    public void setStoreTel(String storeTel) {
        this.storeTel = storeTel;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getNumbers() {
        return numbers;
    }

    public void setNumbers(Integer numbers) {
        this.numbers = numbers;
    }

    public Date getEstimateDate() {
        return estimateDate;
    }

    public void setEstimateDate(Date estimateDate) {
        this.estimateDate = estimateDate;
    }

    public String getEstimateTime() {
        return estimateTime;
    }

    public void setEstimateTime(String estimateTime) {
        this.estimateTime = estimateTime;
    }

    public String getOptionRequire() {
        return optionRequire;
    }

    public void setOptionRequire(String optionRequire) {
        this.optionRequire = optionRequire;
    }

    public Integer getStartHour() {
        return startHour;
    }

    public void setStartHour(Integer startHour) {
        this.startHour = startHour;
    }

    public Integer getStartMinute() {
        return startMinute;
    }

    public void setStartMinute(Integer startMinute) {
        this.startMinute = startMinute;
    }

    public Integer getEndHour() {
        return endHour;
    }

    public void setEndHour(Integer endHour) {
        this.endHour = endHour;
    }

    public Integer getEndMinute() {
        return endMinute;
    }

    public void setEndMinute(Integer endMinute) {
        this.endMinute = endMinute;
    }

    public String getStoreService() {
        return storeService;
    }

    public void setStoreService(String storeService) {
        this.storeService = storeService;
    }

    public String getCompanion() {
        return companion;
    }

    public void setCompanion(String companion) {
        this.companion = companion;
    }

    /**
     * 拼接来店伴侣
     *
     * @param companions
     */
    public void setCompanion(String[] companions) {
        if (companions != null && companions.length > 0) {
            StringBuilder sb = new StringBuilder();
            for (String str : companions) {
                sb.append(str).append(",");
            }
            sb.deleteCharAt(sb.length() - 1);
            this.companion = sb.toString();
        }
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
    }

    public Date getNoticeDate() {
        return noticeDate;
    }

    public void setNoticeDate(Date noticeDate) {
        this.noticeDate = noticeDate;
    }

    public String getNoticeMan() {
        return noticeMan;
    }

    public void setNoticeMan(String noticeMan) {
        this.noticeMan = noticeMan;
    }

    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    public String getReceiveMan() {
        return receiveMan;
    }

    public void setReceiveMan(String receiveMan) {
        this.receiveMan = receiveMan;
    }

    public Date getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(Date completeDate) {
        this.completeDate = completeDate;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public String getCompleteMan() {
        return completeMan;
    }

    public void setCompleteMan(String completeMan) {
        this.completeMan = completeMan;
    }

    public Integer getActualNumbers() {
        return actualNumbers;
    }

    public void setActualNumbers(Integer actualNumbers) {
        this.actualNumbers = actualNumbers;
    }

    public String getRetailSn() {
        return retailSn;
    }

    public void setRetailSn(String retailSn) {
        this.retailSn = retailSn;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public Date getFeedbackDate() {
        return feedbackDate;
    }

    public void setFeedbackDate(Date feedbackDate) {
        this.feedbackDate = feedbackDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getRemarkDate() {
        return remarkDate;
    }

    public void setRemarkDate(Date remarkDate) {
        this.remarkDate = remarkDate;
    }

    public String getVisit() {
        return visit;
    }

    public void setVisit(String visit) {
        this.visit = visit;
    }

    public Date getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }

    public BigDecimal getCusCost() {
        return cusCost;
    }

    public void setCusCost(BigDecimal cusCost) {
        this.cusCost = cusCost;
    }

    public String getCancelMan() {
        return cancelMan;
    }

    public void setCancelMan(String cancelMan) {
        this.cancelMan = cancelMan;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public Date getCancelDate() {
        return cancelDate;
    }

    public void setCancelDate(Date cancelDate) {
        this.cancelDate = cancelDate;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    /**
     * 拼接客户预约时间
     */
    public String getStartToEndTime() {
        if (StringUtil.hasBlack(new Object[]{getStartHour(), getStartMinute(), getEndHour(), getEndMinute()})) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(getStartHour()).append("点").append(getStartMinute() == 0 ? "00" : getStartMinute()).append("分至")
                .append(getEndHour()).append("点").append(getEndMinute() == 0 ? "00" : getEndMinute()).append("分");
        return sb.toString();
    }

}
