package com.d2c.order.query;

import com.d2c.common.api.query.model.BaseQuery;
import com.d2c.common.api.query.model.RoleQuery;

import java.util.Date;
import java.util.List;

public class StatementSearcher extends BaseQuery implements RoleQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 编号
     */
    private String sn;
    /**
     * 状态
     */
    private Integer[] status;
    /**
     * 品牌名称
     */
    private String desingerName;
    /**
     * 年
     */
    private Integer settleYear;
    /**
     * 月
     */
    private Integer settleMonth;
    /**
     * 付款开始时间
     */
    private Date payStartTime;
    /**
     * 付款结束时间
     */
    private Date payEndTime;
    /**
     * 设计师ID
     */
    private Long designerId;
    /**
     * 设计师IDs
     */
    private List<Long> designerIds;
    /**
     * 是否申请用款
     */
    private Integer apply;
    /**
     * 运营小组
     */
    private String operation;
    /**
     * 业务类型
     */
    private String fromType;
    /**
     * 日期
     */
    private Integer periodOfMonth;

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public Integer[] getStatus() {
        return status;
    }

    public void setStatus(Integer[] status) {
        this.status = status;
    }

    public String getDesingerName() {
        return desingerName;
    }

    public void setDesingerName(String desingerName) {
        this.desingerName = desingerName;
    }

    public Integer getSettleYear() {
        return settleYear;
    }

    public void setSettleYear(Integer settleYear) {
        this.settleYear = settleYear;
    }

    public Integer getSettleMonth() {
        return settleMonth;
    }

    public void setSettleMonth(Integer settleMonth) {
        this.settleMonth = settleMonth;
    }

    public Date getPayStartTime() {
        return payStartTime;
    }

    public void setPayStartTime(Date payStartTime) {
        this.payStartTime = payStartTime;
    }

    public Date getPayEndTime() {
        return payEndTime;
    }

    public void setPayEndTime(Date payEndTime) {
        this.payEndTime = payEndTime;
    }

    public Long getDesignerId() {
        return designerId;
    }

    public void setDesignerId(Long designerId) {
        this.designerId = designerId;
    }

    public List<Long> getDesignerIds() {
        return designerIds;
    }

    public void setDesignerIds(List<Long> designerIds) {
        this.designerIds = designerIds;
    }

    public Integer getApply() {
        return apply;
    }

    public void setApply(Integer apply) {
        this.apply = apply;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    @Override
    public void setStoreId(Long storeId) {
    }

    @Override
    public void setBrandIds(List<Long> brandIds) {
        this.designerIds = brandIds;
    }

    public String getFromType() {
        return fromType;
    }

    public void setFromType(String fromType) {
        this.fromType = fromType;
    }

    public Integer getPeriodOfMonth() {
        return periodOfMonth;
    }

    public void setPeriodOfMonth(Integer periodOfMonth) {
        this.periodOfMonth = periodOfMonth;
    }

}
