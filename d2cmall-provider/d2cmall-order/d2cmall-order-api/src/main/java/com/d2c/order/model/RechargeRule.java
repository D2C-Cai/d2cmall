package com.d2c.order.model;

import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;
import com.d2c.order.handle.XonYProcessPriceBehavior;
import com.d2c.order.handle.XonYStepMethod;
import com.d2c.order.handle.XonYUnlimitedMethod;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Table;
import java.util.Date;

/**
 * 充值规则
 */
@Table(name = "o_recharge_rule")
public class RechargeRule extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 活动类型
     */
    private Integer ruleType = 1;
    /**
     * 促销内容 类似 200+20,400+50,1000+200
     */
    private String solution;
    /**
     * 活动名称
     */
    private String name;
    /**
     * 开始时间
     */
    private Date startTime;
    /**
     * 结束时间
     */
    private Date endTime;
    /**
     * 自定义页面
     */
    private String pcCode;
    /**
     * 手机自定义页面
     */
    private String mobileCode;
    /**
     * 0：下架，1：上架
     */
    private Integer status = 0;
    /**
     * 上架人
     */
    private String publishMan;
    /**
     * 上架时间
     */
    private Date publishDate;
    /**
     * 下架人
     */
    private String cancelMan;
    /**
     * 下架时间
     */
    private Date cancelDate;
    /**
     * 活动说明
     */
    private String description;
    /**
     * 是否定时 1：是，0：否
     */
    private Integer timing = 0;
    /**
     * 仅限在期限内使用 1：是，0：否
     */
    private Integer limited = 0;

    @AssertColumn
    public String validate() {
        if (StringUtils.isEmpty(name)) {
            return "名称不能为空";
        }
        if (this.getStartTime().getTime() >= this.getEndTime().getTime()) {
            return "开始时间小于结束时间，请填写正确时间的关系";
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getPcCode() {
        return pcCode;
    }

    public void setPcCode(String customCode) {
        this.pcCode = customCode;
    }

    public String getMobileCode() {
        return mobileCode;
    }

    public void setMobileCode(String mobileCode) {
        this.mobileCode = mobileCode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getRuleType() {
        return ruleType;
    }

    public void setRuleType(Integer ruleType) {
        this.ruleType = ruleType;
    }

    public String getPublishMan() {
        return publishMan;
    }

    public void setPublishMan(String publishMan) {
        this.publishMan = publishMan;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public String getCancelMan() {
        return cancelMan;
    }

    public void setCancelMan(String cancelMan) {
        this.cancelMan = cancelMan;
    }

    public Date getCancelDate() {
        return cancelDate;
    }

    public void setCancelDate(Date cancelDate) {
        this.cancelDate = cancelDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getTiming() {
        return timing;
    }

    public void setTiming(Integer timing) {
        this.timing = timing;
    }

    public Integer getLimited() {
        return limited;
    }

    public void setLimited(Integer limited) {
        this.limited = limited;
    }

    public boolean isOver() {
        Date date = new Date();
        if (status <= 0) {
            return true;
        }
        if (date.getTime() >= this.getStartTime().getTime() && date.getTime() <= this.getEndTime().getTime()) {
            return false;
        }
        return true;
    }

    public XonYProcessPriceBehavior getProcessMethod() {
        XonYProcessPriceBehavior method = null;
        if (this.getRuleType() != null) {
            switch (this.getRuleType()) {
                case 1:
                    method = new XonYStepMethod();
                    break;
                case 2:
                    method = new XonYUnlimitedMethod();
                    break;
                default:
                    method = null;
            }
        }
        return method;
    }

}
