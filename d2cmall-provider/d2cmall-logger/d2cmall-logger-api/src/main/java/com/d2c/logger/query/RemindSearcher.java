package com.d2c.logger.query;

import com.d2c.common.api.query.model.BaseQuery;
import com.d2c.logger.model.Remind;

import java.util.Date;

public class RemindSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 用户ID
     */
    private Long memberId;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 邮箱
     */
    private String mail;
    /**
     * 来源ID
     */
    private Long sourceId;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 邮件发送时间
     */
    private Date sendDate;
    /**
     * 邮件是否已发送
     */
    private Boolean sended;
    /**
     * 内容
     */
    private String content;
    /**
     * 短信是否已发送
     */
    private Boolean smsSend;
    /**
     * 短信发送时间
     */
    private Date smsSendDate;
    /**
     * 邮件是否发送
     */
    private Integer send;
    /**
     * CROWD_BEGIN 预售开始提醒, ARRIVAL 到货提醒, CUSTOM 高定沟通提醒
     */
    private String type;
    /**
     * 开始时间
     */
    private Date beginCreateDate;
    /**
     * 结束时间
     */
    private Date endCreateDate;

    public Boolean getSmsSend() {
        return smsSend;
    }

    public void setSmsSend(Boolean smsSend) {
        this.smsSend = smsSend;
    }

    public Date getSmsSendDate() {
        return smsSendDate;
    }

    public void setSmsSendDate(Date smsSendDate) {
        this.smsSendDate = smsSendDate;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public void remindForSearcher(Remind remind) {
        this.memberId = remind.getMemberId();
        this.mail = remind.getMail();
        this.mobile = remind.getMobile();
        this.setSourceId(remind.getSourceId());
        this.setContent(remind.getContent());
        this.type = remind.getType();
    }

    public Boolean getSended() {
        return sended;
    }

    public void setSended(Boolean sended) {
        this.sended = sended;
    }

    public Integer getSend() {
        return send;
    }

    public void setSend(Integer send) {
        if (send == null) {
            return;
        }
        switch (send) {
            case 0:
                sended = false;
                break;// 邮件未发
            case 1:
                sended = true;
                break;// 邮件已发
            case 2:
                smsSend = false;
                break;// 短信未发
            case 3:
                smsSend = true;
                break;// 短信已发
        }
        this.send = send;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
