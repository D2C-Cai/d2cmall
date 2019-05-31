package com.d2c.content.model;

import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;
import java.util.Date;

/**
 * 实体类 -投票定义
 */
@Table(name = "v_vote_def")
public class VoteDef extends PreUserDO {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 标题
     */
    private String title;
    /**
     * 投票说明
     */
    private String description;
    /**
     * 开始时间
     */
    private Date beginDate;
    /**
     * 结束时间
     */
    private Date endDate;
    /**
     * 选择类型单选 多选
     */
    private Integer selectionType;
    /**
     * 图片
     */
    private String pic;
    /**
     * 状态 -1删除 0未开始 1已开始 2已结束
     */
    private Integer status;
    /**
     * 显示标题
     */
    private Integer showTitle;
    /**
     * 显示票数
     */
    private Integer showCount;
    /**
     * 显示排名
     */
    private Integer showRank;
    /**
     * PC版内容
     */
    private String pcContent;
    /**
     * 手机版内容
     */
    private String mobileContent;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getSelectionType() {
        return selectionType;
    }

    public void setSelectionType(Integer selectionType) {
        this.selectionType = selectionType;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getShowTitle() {
        return showTitle;
    }

    public void setShowTitle(Integer showTitle) {
        this.showTitle = showTitle;
    }

    public Integer getShowCount() {
        return showCount;
    }

    public void setShowCount(Integer showCount) {
        this.showCount = showCount;
    }

    public Integer getShowRank() {
        return showRank;
    }

    public void setShowRank(Integer showRank) {
        this.showRank = showRank;
    }

    public String getPcContent() {
        return pcContent;
    }

    public void setPcContent(String pcContent) {
        this.pcContent = pcContent;
    }

    public String getMobileContent() {
        return mobileContent;
    }

    public void setMobileContent(String mobileContent) {
        this.mobileContent = mobileContent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
