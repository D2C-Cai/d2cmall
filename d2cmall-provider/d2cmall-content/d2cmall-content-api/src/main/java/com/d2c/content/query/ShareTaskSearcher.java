package com.d2c.content.query;

import com.d2c.common.api.query.model.BaseQuery;
import com.d2c.util.string.LoginUtil;

import java.util.Date;

public class ShareTaskSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 会员id
     */
    private Long memberId;
    /**
     * 会员名称
     */
    private String memberName;
    /**
     * 会员邮箱
     */
    private String memberEmail;
    /**
     * 会员手机
     */
    private String memberMobile;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 分享任务编号
     */
    private String sn;
    /**
     * 分享任务标题
     */
    private String title;
    /**
     * 任务定义id
     */
    private Long taskDefId;
    /**
     * 开始时间
     */
    private Date beginCreateDate;
    /**
     * 结束时间
     */
    private Date endCreateDate;

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        if (LoginUtil.checkMobile(memberName)) {
            this.setMemberMobile(memberName);
            this.setMemberName(null);
        } else {
            this.memberName = memberName;
        }
    }

    public String getMemberEmail() {
        return memberEmail;
    }

    public void setMemberEmail(String memberEmail) {
        this.memberEmail = memberEmail;
    }

    public String getMemberMobile() {
        return memberMobile;
    }

    public void setMemberMobile(String memberMobile) {
        this.memberMobile = memberMobile;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getTaskDefId() {
        return taskDefId;
    }

    public void setTaskDefId(Long taskDefId) {
        this.taskDefId = taskDefId;
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

}
