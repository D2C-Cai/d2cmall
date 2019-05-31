package com.d2c.report.query.base;

import com.d2c.common.api.annotation.search.SearchField;
import com.d2c.common.api.query.enums.OperType;
import com.d2c.common.api.query.model.MongoQuery;

public class BasePartnerQuery extends MongoQuery {

    private static final long serialVersionUID = -5017213275436783402L;
    /**
     * 经销商ID
     */
    @SearchField
    protected Long partnerId;
    /**
     * 父经销商ID
     */
    @SearchField
    protected Long parentId;
    /**
     * 导师ID
     */
    @SearchField
    protected Long masterId;
    /**
     * 会员昵称
     */
    @SearchField(oper = OperType.LIKE)
    protected String name;
    /**
     * 0.AM 1.DM 2.买手
     */
    @SearchField
    protected Integer level;
    /**
     * 状态 0.试用期 1.正常-1.未通过 -9.永久关闭
     */
    @SearchField
    protected Integer status;
    /**
     * 买手顾问ID
     */
    @SearchField
    protected String counselorId;
    /**
     * 买手顾问昵称
     */
    @SearchField(oper = OperType.LIKE)
    protected String counselorName;
    /**
     * 是否通过试用期
     */
    @SearchField(name = "consumeDate", oper = OperType.NOT_NULL)
    protected Integer consume;

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

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getMasterId() {
        return masterId;
    }

    public void setMasterId(Long masterId) {
        this.masterId = masterId;
    }

    public Integer getConsume() {
        return consume;
    }

    public void setConsume(Integer consume) {
        this.consume = consume;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCounselorId() {
        return counselorId;
    }

    public void setCounselorId(String counselorId) {
        this.counselorId = counselorId;
    }

    public String getCounselorName() {
        return counselorName;
    }

    public void setCounselorName(String counselorName) {
        this.counselorName = counselorName;
    }

}
