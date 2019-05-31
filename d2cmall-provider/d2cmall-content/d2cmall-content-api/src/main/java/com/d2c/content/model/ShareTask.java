package com.d2c.content.model;

import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;
import java.util.Date;

/**
 * 实体类 -分享记录
 */
@Table(name = "v_share_task")
public class ShareTask extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 任务定义ID
     */
    @AssertColumn("分享定义不能为空")
    private Long taskDefId;
    /**
     * 编号
     */
    private String sn;
    /**
     * 标题
     */
    private String title;
    /**
     * 会员ID
     */
    @AssertColumn("会员ID不能为空")
    private Long memberId;
    /**
     * 会员名称
     */
    private String memberName;
    /**
     * 每个会员的最多获取积分（小于等于0，不限制）
     */
    private Integer maxPoint = 0;
    /**
     * 点击一次获取积分数（定义）
     */
    private Integer clickPoint = 1;
    /**
     * 授权积分
     */
    private Integer grantPoint = 0;
    /**
     * 赠送积分时间
     */
    private Date sendPointDate;
    /**
     * 0新建，1：积分已赠送
     */
    private Integer status = 0;
    /**
     * URL
     */
    private String url = null;
    /**
     * 累计幸运号码
     */
    private String luckyNum = null;
    /**
     * 父亲ID
     */
    private Long parentTaskId;
    /**
     * 点击开始时间
     */
    private Date clickDate = null;
    /**
     * 点击次数
     */
    private Integer clickNumber = 1;
    /**
     * 分享开始时间
     */
    private Date shareDate = null;
    /**
     * 分享次数
     */
    private Integer shareNumber = 0;

    public ShareTask(ShareTaskDef def) {
        this.url = def.getUrl();
    }

    public ShareTask() {
    }

    public String getStatusName() {
        if (status == null) {
            return "未知";
        }
        switch (this.status) {
            case 0:
                return "积分未赠送";
            case 1:
                return "积分已赠送";
            default:
                return "";
        }
    }

    public Long getTaskDefId() {
        return taskDefId;
    }

    public void setTaskDefId(Long shareTaskDefId) {
        this.taskDefId = shareTaskDefId;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

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
        this.memberName = memberName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getMaxPoint() {
        return maxPoint;
    }

    public void setMaxPoint(Integer maxPoint) {
        this.maxPoint = maxPoint;
    }

    public Integer getClickPoint() {
        return clickPoint;
    }

    public void setClickPoint(Integer clickPoint) {
        this.clickPoint = clickPoint;
    }

    public Integer getGrantPoint() {
        if (this.maxPoint > 0 && this.grantPoint > this.maxPoint) {
            this.grantPoint = this.maxPoint;
        }
        return grantPoint;
    }

    public void setGrantPoint(int grantPoint) {
        this.grantPoint = grantPoint;
    }

    public Date getSendPointDate() {
        return sendPointDate;
    }

    public void setSendPointDate(Date sendPointDate) {
        this.sendPointDate = sendPointDate;
    }

    public String getLuckyNum() {
        return luckyNum;
    }

    public void setLuckyNum(String luckyNum) {
        this.luckyNum = luckyNum;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getParentTaskId() {
        return parentTaskId;
    }

    public void setParentTaskId(Long parentTaskId) {
        this.parentTaskId = parentTaskId;
    }

    public Date getClickDate() {
        return clickDate;
    }

    public void setClickDate(Date clickDate) {
        this.clickDate = clickDate;
    }

    public Integer getClickNumber() {
        return clickNumber;
    }

    public void setClickNumber(Integer clickNumber) {
        this.clickNumber = clickNumber;
    }

    public Date getShareDate() {
        return shareDate;
    }

    public void setShareDate(Date shareDate) {
        this.shareDate = shareDate;
    }

    public Integer getShareNumber() {
        return shareNumber;
    }

    public void setShareNumber(Integer shareNumber) {
        this.shareNumber = shareNumber;
    }

}
