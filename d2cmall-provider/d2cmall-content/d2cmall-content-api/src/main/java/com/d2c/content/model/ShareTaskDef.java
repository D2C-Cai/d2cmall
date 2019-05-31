package com.d2c.content.model;

import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;
import java.util.Date;

/**
 * 实体类 -分享任务
 */
@Table(name = "v_share_task_def")
public class ShareTaskDef extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 规则
     */
    private String rule;
    /**
     * 大图，封面图
     */
    private String bigPic;
    /**
     * 小图，缩略图
     */
    private String smallPic;
    /**
     * 开始时间
     */
    private Date startTime;
    /**
     * 结束时间
     */
    private Date endTime;
    /**
     * 编号，唯一键
     */
    private String sn;
    /**
     * 标题
     */
    @AssertColumn("标题不能为空")
    private String title;
    /**
     * 分享页面地址
     */
    private String url = "";
    /**
     * 位数
     */
    private Integer luckNumDigit = 3;
    /**
     * 总积分（大于0）
     */
    private Integer totalPoint = 0;
    /**
     * 已发放
     */
    private Integer grantPoint = 0;
    /**
     * 每个会员的最多获取积分（小于等于0，不限制）
     */
    private Integer memberPoint = 0;
    /**
     * 点击积分
     */
    private Integer clickPoint = 1;
    /**
     * 分享的内容
     */
    private String shareContent;
    /**
     * 分享的内容（手机）
     */
    private String mobileContent;
    /**
     * 状态：-1 归档，0 停用，1启用，2完成，8已核算
     */
    private Integer status = 0;

    public String getStatusName() {
        switch (status) {
            case -1:
                return "归档";
            case 0:
                return "停用";
            case 1:
                return "启用";
            case 2:
                return "已完成";
            case 8:
                return "已核算";
            default:
                return "未知";
        }
    }

    /**
     * 判断任务是否开始
     */
    public boolean isStart() {
        if (new Date().getTime() >= startTime.getTime()) {
            return true;
        }
        return false;
    }

    /**
     * 判断任务是否完成
     */
    public boolean isOver() {
        if (grantPoint > totalPoint) {
            return true;
        }
        if (new Date().getTime() > endTime.getTime()) {
            return true;
        }
        return false;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getBigPic() {
        return bigPic;
    }

    public void setBigPic(String bigPic) {
        this.bigPic = bigPic;
    }

    public String getSmallPic() {
        return smallPic;
    }

    public void setSmallPic(String smallPic) {
        this.smallPic = smallPic;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getLuckNumDigit() {
        return luckNumDigit;
    }

    public void setLuckNumDigit(Integer luckNumDigit) {
        this.luckNumDigit = luckNumDigit;
    }

    public Integer getTotalPoint() {
        return totalPoint;
    }

    public void setTotalPoint(Integer totalPoint) {
        this.totalPoint = totalPoint;
    }

    public Integer getGrantPoint() {
        return grantPoint;
    }

    public void setGrantPoint(Integer grantPoint) {
        this.grantPoint = grantPoint;
    }

    public Integer getMemberPoint() {
        return memberPoint;
    }

    public void setMemberPoint(Integer memberPoint) {
        this.memberPoint = memberPoint;
    }

    public Integer getClickPoint() {
        return clickPoint;
    }

    public void setClickPoint(Integer clickPoint) {
        this.clickPoint = clickPoint;
    }

    public String getShareContent() {
        return shareContent;
    }

    public void setShareContent(String shareContent) {
        this.shareContent = shareContent;
    }

    public String getMobileContent() {
        return mobileContent;
    }

    public void setMobileContent(String mobileContent) {
        this.mobileContent = mobileContent;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
