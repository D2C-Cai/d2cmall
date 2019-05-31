package com.d2c.product.model;

import com.d2c.common.api.model.BaseParentDO;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 爬虫地址
 */
@Table(name = "craw_url")
public class CrawUrl extends BaseParentDO<String> {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator = "JDBC")
    private String crawUrl;
    private Integer crawStatus = 1;
    private Integer crawType = 1;
    private String crawRooturl;
    private Long crawDesignerId;
    private Integer crawPageNo = 1;
    private String crawProductId;

    @Override
    public String getId() {
        return crawUrl;
    }

    @Override
    public void setId(String id) {
        setCrawUrl(id);
    }

    public String getCrawUrl() {
        return crawUrl;
    }

    public void setCrawUrl(String crawUrl) {
        this.crawUrl = crawUrl == null ? null : crawUrl.trim();
    }

    public Integer getCrawStatus() {
        return crawStatus;
    }

    public void setCrawStatus(Integer crawStatus) {
        this.crawStatus = crawStatus;
    }

    public Integer getCrawType() {
        return crawType;
    }

    public void setCrawType(Integer crawType) {
        this.crawType = crawType;
    }

    public String getCrawRooturl() {
        return crawRooturl;
    }

    public void setCrawRooturl(String crawRooturl) {
        this.crawRooturl = crawRooturl == null ? null : crawRooturl.trim();
    }

    public Long getCrawDesignerId() {
        return crawDesignerId;
    }

    public void setCrawDesignerId(Long crawDesignerId) {
        this.crawDesignerId = crawDesignerId;
    }

    public Integer getCrawPageNo() {
        return crawPageNo;
    }

    public void setCrawPageNo(Integer crawPageNo) {
        this.crawPageNo = crawPageNo;
    }

    public String getCrawProductId() {
        return crawProductId;
    }

    public void setCrawProductId(String crawProductId) {
        this.crawProductId = crawProductId;
    }

}