package com.d2c.product.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.model.PreUserDO;
import com.d2c.util.date.DateUtil;

import javax.persistence.Table;
import java.util.Date;

/**
 * 爬虫品牌
 */
@Table(name = "craw_designer")
public class CrawDesigner extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 抓取设计师的url
     */
    private String crawDesignerUrl;
    /**
     * 抓取设计师名
     */
    private String crawDesignerName;
    /**
     * 抓取次数
     */
    private Long crawTimes;
    /**
     * 抓取时间
     */
    private Date crawDate;
    /**
     * 设计师id
     */
    private Long designerId;

    public String getCrawDesignerUrl() {
        return crawDesignerUrl;
    }

    public void setCrawDesignerUrl(String crawDesignerUrl) {
        this.crawDesignerUrl = crawDesignerUrl == null ? null : crawDesignerUrl.trim();
    }

    public String getCrawDesignerName() {
        return crawDesignerName;
    }

    public void setCrawDesignerName(String crawDesignerName) {
        this.crawDesignerName = crawDesignerName == null ? null : crawDesignerName.trim();
    }

    public Date getCrawDate() {
        return crawDate;
    }

    public void setCrawDate(Date crawDate) {
        this.crawDate = crawDate;
    }

    public Long getDesignerId() {
        return designerId;
    }

    public void setDesignerId(Long designerId) {
        this.designerId = designerId;
    }

    public Long getCrawTimes() {
        return crawTimes;
    }

    public void setCrawTimes(Long crawTimes) {
        this.crawTimes = crawTimes;
    }

    public boolean isCraw() {
        if (crawTimes == 0)
            return false;
        return DateUtil.dateSubtrationToHour(new Date(), crawDate) > 20 ? false : true;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("id", this.getId());
        json.put("crawDesignerUrl", this.getCrawDesignerUrl());
        json.put("crawDesignerName", this.getCrawDesignerName());
        json.put("crawTimes", this.getCrawTimes());
        json.put("isCraw", isCraw());
        return json;
    }

}