package com.d2c.logger.model;

import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.annotation.HideColumn;
import com.d2c.common.api.annotation.HideEnum;
import com.d2c.common.api.model.PreUserDO;
import com.d2c.util.string.LoginUtil;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

/**
 * 会员提醒
 */
@Table(name = "member_remind")
public class Remind extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 用户ID
     */
    @AssertColumn("会员ID不能为空")
    private Long memberId;
    /**
     * 手机号
     */
    @HideColumn(type = HideEnum.MOBILE)
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
     * 提醒内容
     */
    private String content;
    /**
     * 邮件是否已发送
     */
    @Column(name = "is_send")
    private boolean sended = false;
    /**
     * 邮件发送时间
     */
    private Date sendDate;
    /**
     * 短信是否已发送
     */
    private Boolean smsSend = false;
    /**
     * 短信发送时间
     */
    private Date smsSendDate;
    /**
     * 类型
     */
    private String type;

    public String getRemindTypeName() {
        if (type == null) {
            return "";
        }
        if (type.equals(RemindType.CROWD_BEGIN.name())) {
            return "预售开始提醒";
        } else if (type.equals(RemindType.ARRIVAL.name())) {
            return "到货提醒";
        } else if (type.equals(RemindType.CUSTOM.name())) {
            return "高定沟通提醒";
        } else if (type.equals(RemindType.FLASHPROMOTION.name())) {
            return "限时购提醒";
        }
        return "";
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

    public boolean isSended() {
        return sended;
    }

    public void setSended(boolean sended) {
        this.sended = sended;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long productId) {
        this.sourceId = productId;
    }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean check() {
        return sourceId != null && type != null && LoginUtil.checkMobile(mobile);
    }

    /**
     * CROWD_BEGIN 预售开始提醒，ARRIVAL 到货提醒，CUSTOM 高定沟通提醒 ，FLASHPROMOTION 限时购提醒
     */
    public enum RemindType {
        CROWD_BEGIN, ARRIVAL, CUSTOM, FLASHPROMOTION;
    }

}