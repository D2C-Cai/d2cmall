package com.d2c.logger.model;

import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;

/**
 * APP分享日志
 */
@Table(name = "log_share")
public class ShareLog extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 从哪里分享的
     */
    private String device;
    /**
     * 谁分享的
     */
    private Long memberId;
    /**
     * 分享的链接
     */
    private String url;
    /**
     * 分享(1,分享；-1,点击分享)
     */
    private Integer direction;
    /**
     * 分享渠道
     */
    private String channel;

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

}
