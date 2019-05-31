package com.d2c.content.query;

import com.d2c.common.api.query.model.BaseQuery;

import java.util.Date;

public class FeedBackSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 内容
     */
    private String content;
    /**
     * 开始时间
     */
    private Date startCreateDate;
    /**
     * 结束时间
     */
    private Date endCreateDate;
    /**
     * 账号
     */
    private String loginCode;
    /**
     * 是否已经回复
     */
    private Integer alreadyReply;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 设备终端
     */
    private String device;
    /**
     * 版本号
     */
    private String version;
    /**
     * 是否是咨询
     */
    private boolean isFeedBack = true;
    /**
     * 类型
     */
    private String type;

    public String getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getStartCreateDate() {
        return startCreateDate;
    }

    public void setStartCreateDate(Date startCreateDate) {
        this.startCreateDate = startCreateDate;
    }

    public Date getEndCreateDate() {
        return endCreateDate;
    }

    public void setEndCreateDate(Date endCreateDate) {
        this.endCreateDate = endCreateDate;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getAlreadyReply() {
        return alreadyReply;
    }

    public void setAlreadyReply(Integer alreadyReply) {
        this.alreadyReply = alreadyReply;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public boolean isFeedBack() {
        return isFeedBack;
    }

    public void setFeedBack(boolean isFeedBack) {
        this.isFeedBack = isFeedBack;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
