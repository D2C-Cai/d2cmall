package com.d2c.logger.model;

import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;
import java.util.Date;

/**
 * 弹窗消息
 *
 * @author Lain
 */
@Table(name = "pop_message")
public class PopMessage extends PreUserDO {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 链接
     */
    private String url;
    /**
     * 图片
     */
    private String pic;
    /**
     * 标题
     */
    private String title;
    /**
     * 状态 0未发送，1已发送
     */
    private Integer status = 0;
    /**
     * 发送时间
     */
    private Date sendDate;
    /**
     * 发送人
     */
    private String sendMan;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public String getSendMan() {
        return sendMan;
    }

    public void setSendMan(String sendMan) {
        this.sendMan = sendMan;
    }

}
