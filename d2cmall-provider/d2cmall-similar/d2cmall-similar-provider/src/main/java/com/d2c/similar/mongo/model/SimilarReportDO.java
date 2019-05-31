package com.d2c.similar.mongo.model;

import com.d2c.common.mongodb.model.ReportMongoDO;
import com.d2c.similar.entity.SimilarSchemeDO;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @author user
 */
@Document
public class SimilarReportDO extends ReportMongoDO {

    private static final long serialVersionUID = -4903284310787030896L;
    @Indexed
    private Integer schemeId;
    private SimilarType type;
    private SimilarSchemeDO scheme;
    private Integer maxSize;
    private Date lastDate;
    private Date lastRebuildDate;
    private int beanCount = 0;
    private int tgCount = 0;
    @Transient
    private Object beanId;

    public SimilarReportDO(SimilarType type) {
        this.type = type;
    }

    public SimilarReportDO() {
        this(SimilarType.ADD);
    }

    public void addBeanCount() {
        beanCount++;
    }

    @Override
    public void add() {
        beanCount++;
        count = beanCount * tgCount;
    }

    @Override
    public void restart(long count) {
        beanCount = (int) count;
        this.count = beanCount * tgCount;
    }

    @Override
    public String toString() {
        return "第 " + beanCount + " 条 id:" + beanId + " 查询 " + maxSize + " 条, 保存 " + tgCount + " , 共 " + count + "条";
    }

    public Integer getSchemeId() {
        return schemeId;
    }

    public void setSchemeId(Integer schemeId) {
        this.schemeId = schemeId;
    }
    // ***************************************

    public SimilarSchemeDO getScheme() {
        return scheme;
    }

    public void setScheme(SimilarSchemeDO scheme) {
        this.scheme = scheme;
        this.schemeId = scheme.getId();
        this.maxSize = scheme.getMaxSize();
    }

    public Integer getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(Integer maxSize) {
        this.maxSize = maxSize;
    }

    public int getBeanCount() {
        return beanCount;
    }

    public void setBeanCount(int beanCount) {
        this.beanCount = beanCount;
    }

    public int getTgCount() {
        return tgCount;
    }

    public void setTgCount(int tgCount) {
        this.tgCount = tgCount;
    }

    public Object getBeanId() {
        return beanId;
    }

    public void setBeanId(Object beanId) {
        this.beanId = beanId;
    }

    public SimilarType getType() {
        return type;
    }

    public void setType(SimilarType type) {
        this.type = type;
    }

    public Date getLastDate() {
        return lastDate;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }

    public Date getLastRebuildDate() {
        return lastRebuildDate;
    }

    public void setLastRebuildDate(Date lastRebuildDate) {
        this.lastRebuildDate = lastRebuildDate;
    }

    public enum SimilarType {
        ADD, UPDATE;
    }

}
