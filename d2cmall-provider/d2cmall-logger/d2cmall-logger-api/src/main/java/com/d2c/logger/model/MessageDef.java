package com.d2c.logger.model;

import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;
import java.util.Date;

/**
 * 消息模板
 */
@Table(name = "message_def")
public class MessageDef extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 标题
     */
    @AssertColumn("消息标题不能为空")
    private String title;
    /**
     * 消息内容
     */
    private String content;
    /**
     * 分类 1.物流 2.商品 3.订单 4.系统 5.评论 6.其他
     */
    private Integer type = 6;
    /**
     * 过期时间
     */
    private Date expire;
    /**
     * URL
     */
    private String url;
    /**
     * 是否全站
     */
    private Integer global = 0;
    /**
     * 是否定时，0 否，1 是
     */
    private Integer timing = 0;
    /**
     * 推送时间
     */
    private Date sendDate;
    /**
     * 消息状态 -1-已删除,0-未发送，1-已发送
     */
    private Integer status = 0;
    /**
     * 定时-消息延迟时的时间戳
     */
    private Long timestamp;
    /**
     * 消息图片
     */
    private String pic;
    /**
     * 渠道
     */
    private String channel;
    /**
     * 是否跳小程序
     */
    private Integer toMiniProgram;

    public String getTypeName() {
        switch (this.getType()) {
            case 1:
                return "物流通知";
            case 2:
                return "商品提醒";
            case 3:
                return "系统通知";
            case 4:
                return "系统通知";
            case 5:
                return "广场动态";
            case 6:
                return "其他";
            case 7:
                return "广场动态";
            case 8:
                return "广场动态";
            case 9:
                return "广场动态";
            case 10:
                return "系统通知";
            case 11:
                return "物流通知";
            case 12:
                return "物流通知";
            case 13:
                return "系统通知";
            case 14:
                return "系统通知";
            case 21:
                return "系统通知";
            case 22:
                return "系统通知";
            case 23:
                return "系统通知";
            case 24:
                return "系统通知";
            case 25:
                return "系统通知";
            case 26:
                return "系统通知";
            case 27:
                return "系统通知";
            case 29:
                return "系统通知";
            case 31:
                return "我的资产";
            case 32:
                return "我的资产";
            case 33:
                return "我的资产";
            case 34:
                return "我的资产";
            case 41:
                return "广场动态";
            case 42:
                return "广场动态";
            case 43:
                return "广场动态";
            case 44:
                return "广场动态";
            case 45:
                return "广场动态";
            case 51:
                return "商品提醒";
            case 52:
                return "商品提醒";
            case 61:
                return "活动精选";
            case 71:
                return "品牌推荐";
            case 72:
                return "品牌推荐";
            case 81:
                return "买手服务消息";
            case 82:
                return "买手服务消息";
            case 83:
                return "买手服务消息";
            case 84:
                return "买手服务消息";
            case 85:
                return "买手服务消息";
            case 86:
                return "买手服务消息";
            case 89:
                return "买手服务消息";
            default:
                return "其他";
        }
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getExpire() {
        return expire;
    }

    public void setExpire(Date expire) {
        this.expire = expire;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getGlobal() {
        return global;
    }

    public void setGlobal(Integer global) {
        this.global = global;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @AssertColumn
    public String validate() {
        if (this.getTiming() == 1 && new Date().after(this.getSendDate())) {
            return "定时任务所设时间不得小于当前时间";
        }
        return null;
    }

    public Integer getTiming() {
        return timing;
    }

    public void setTiming(Integer timing) {
        this.timing = timing;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public Integer getToMiniProgram() {
        return toMiniProgram;
    }

    public void setToMiniProgram(Integer toMiniProgram) {
        this.toMiniProgram = toMiniProgram;
    }

    public enum MessageChannel {
        ALL, WEIXINTOPARTNER, APP
    }

}