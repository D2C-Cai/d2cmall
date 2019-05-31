package com.d2c.logger.support;

import java.io.Serializable;

public class MsgBean implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 用户ID
     */
    private Long memberId;
    /**
     * 标题
     */
    private String subject;
    /**
     * 内容
     */
    private String content;
    /**
     * 消息类型
     */
    private Integer inMsgType;
    /**
     * app唤醒URL
     */
    private String appUrl;
    /**
     * 图片
     */
    private String pic;
    /**
     * 附加信息
     */
    private String other;

    public MsgBean() {
    }

    public MsgBean(Long memberId, Integer inMsgType, String subject, String content) {
        this.memberId = memberId;
        this.inMsgType = inMsgType;
        this.subject = subject;
        this.content = content;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getInMsgType() {
        return inMsgType;
    }

    public void setInMsgType(Integer inMsgType) {
        this.inMsgType = inMsgType;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

}
