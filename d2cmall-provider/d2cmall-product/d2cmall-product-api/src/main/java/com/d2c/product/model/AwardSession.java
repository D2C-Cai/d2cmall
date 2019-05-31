package com.d2c.product.model;

import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;
import java.util.Date;

/**
 * 抽奖场次
 */
@Table(name = "p_award_session")
public class AwardSession extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 名称
     */
    private String name;
    /**
     * 开始时间
     */
    private Date beginDate;
    /**
     * 结束时间
     */
    private Date endDate;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 描述
     */
    private String description;
    /**
     * 图片
     */
    private String pic;
    /** ============获取抽奖次数的方式================= **/
    /**
     * 分享
     */
    private Integer shareActivity = 0;
    /**
     * 砍价
     */
    private Integer bargin = 0;
    /**
     * 支付
     */
    private Integer payed = 0;
    /**
     * 登录
     */
    private Integer login = 0;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getShareActivity() {
        return shareActivity;
    }

    public void setShareActivity(Integer shareActivity) {
        this.shareActivity = shareActivity;
    }

    public Integer getBargin() {
        return bargin;
    }

    public void setBargin(Integer bargin) {
        this.bargin = bargin;
    }

    public Integer getPayed() {
        return payed;
    }

    public void setPayed(Integer payed) {
        this.payed = payed;
    }

    public Integer getLogin() {
        return login;
    }

    public void setLogin(Integer login) {
        this.login = login;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getStatusName() {
        Date now = new Date();
        if (now.before(this.getBeginDate())) {
            return "未开始";
        }
        if (now.after(this.getBeginDate()) && now.before(this.getEndDate())) {
            return "进行中";
        }
        if (now.after(this.getEndDate())) {
            return "已结束";
        }
        return "";
    }

}
