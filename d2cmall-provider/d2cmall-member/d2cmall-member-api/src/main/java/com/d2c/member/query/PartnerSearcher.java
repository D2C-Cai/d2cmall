package com.d2c.member.query;

import com.d2c.common.api.query.model.BaseQuery;

import java.math.BigDecimal;
import java.util.Date;

public class PartnerSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 等级
     */
    private Integer level;
    /**
     * 登录账号
     */
    private String loginCode;
    /**
     * 昵称
     */
    private String name;
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 标签
     */
    private String tags;
    /**
     * 会员ID
     */
    private Long memberId;
    /**
     * 状态 0停用 1启用
     */
    private Integer status;
    /**
     * 是否开单
     */
    private Integer consume;
    /**
     * 父级ID
     */
    private Long parentId;
    /**
     * 导师ID
     */
    private Long masterId;
    /**
     * 分销ID
     */
    private Long partnerId;
    /**
     * 运营顾问ID
     */
    private Long counselorId;
    /**
     * 成交金额
     */
    private BigDecimal totalOrderAmount;
    /**
     * 创建时间开始
     */
    private Date createDateStart;
    /**
     * 创建时间结束
     */
    private Date createDateEnd;
    /**
     * 运营顾问时间开始
     */
    private Date counselorDateStart;
    /**
     * 运营顾问时间结束
     */
    private Date counselorDateEnd;
    /**
     * 门店标识 是=1
     */
    private Integer storeMark;
    /**
     * 最后登录时间结束
     */
    private Date lastLoginDateEnd;

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
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

    public Integer getConsume() {
        return consume;
    }

    public void setConsume(Integer consume) {
        this.consume = consume;
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

    public Long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Long partnerId) {
        this.partnerId = partnerId;
    }

    public Long getCounselorId() {
        return counselorId;
    }

    public void setCounselorId(Long counselorId) {
        this.counselorId = counselorId;
    }

    public BigDecimal getTotalOrderAmount() {
        return totalOrderAmount;
    }

    public void setTotalOrderAmount(BigDecimal totalOrderAmount) {
        this.totalOrderAmount = totalOrderAmount;
    }

    public Date getCreateDateStart() {
        return createDateStart;
    }

    public void setCreateDateStart(Date createDateStart) {
        this.createDateStart = createDateStart;
    }

    public Date getCreateDateEnd() {
        return createDateEnd;
    }

    public void setCreateDateEnd(Date createDateEnd) {
        this.createDateEnd = createDateEnd;
    }

    public Date getCounselorDateStart() {
        return counselorDateStart;
    }

    public void setCounselorDateStart(Date counselorDateStart) {
        this.counselorDateStart = counselorDateStart;
    }

    public Date getCounselorDateEnd() {
        return counselorDateEnd;
    }

    public void setCounselorDateEnd(Date counselorDateEnd) {
        this.counselorDateEnd = counselorDateEnd;
    }

    public Integer getStoreMark() {
        return storeMark;
    }

    public void setStoreMark(Integer storeMark) {
        this.storeMark = storeMark;
    }

    public Date getLastLoginDateEnd() {
        return lastLoginDateEnd;
    }

    public void setLastLoginDateEnd(Date lastLoginDateEnd) {
        this.lastLoginDateEnd = lastLoginDateEnd;
    }

}
