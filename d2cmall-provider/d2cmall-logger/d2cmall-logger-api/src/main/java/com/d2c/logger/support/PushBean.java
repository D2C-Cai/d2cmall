package com.d2c.logger.support;

import java.io.Serializable;

public class PushBean implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 用户ID
     */
    private Long memberId;
    /**
     * 内容
     */
    private String content;
    /**
     * app唤醒URL
     */
    private String appUrl;
    /**
     * 标题
     */
    private String title;
    /**
     * 内部消息类型
     */
    private Integer inMessageType;
    /**
     * 类标题
     */
    private String subTitle;

    public PushBean() {
    }

    public PushBean(Long memberId, String content, Integer inMessageType) {
        this.memberId = memberId;
        this.content = content;
        this.inMessageType = inMessageType;
        this.subTitle = MsgResource.getTypeName(this.inMessageType);
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getInMessageType() {
        return inMessageType;
    }

    public void setInMessageType(Integer inMessageType) {
        this.inMessageType = inMessageType;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

}
