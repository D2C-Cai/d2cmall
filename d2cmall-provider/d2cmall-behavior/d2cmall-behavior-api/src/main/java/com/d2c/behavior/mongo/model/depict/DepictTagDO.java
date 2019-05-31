package com.d2c.behavior.mongo.model.depict;

import com.d2c.behavior.mongo.model.TagDO;
import com.d2c.common.base.utils.ConvertUt;
import com.d2c.common.base.utils.MathUt;
import com.d2c.common.base.utils.StringUt;
import com.d2c.common.mongodb.model.BaseMongoDO;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 用户标签概率
 *
 * @author wull
 */
@Document
public class DepictTagDO extends BaseMongoDO {

    private static final long serialVersionUID = 5496583247498404853L;
    @Indexed
    private String depictId;
    @Indexed
    private String tagId;
    private String tagName;
    private Long count = 0L;
    private Double percent = 0.0;
    private Double sumValue = 0.0;

    public DepictTagDO() {
    }

    public DepictTagDO(String depictId, TagDO tag) {
        this.depictId = depictId;
        setTag(tag);
        initId();
    }

    public void initId() {
        this.id = StringUt.join("|", depictId, tagName);
    }

    @Override
    public String toString() {
        return "DepictTag:" + id + ", count:" + count;
    }

    /**
     * 合并
     */
    public DepictTagDO merge(DepictTagDO bean) {
        this.count += bean.count;
        return this;
    }

    public void setTag(TagDO tag) {
        if (tag != null) {
            this.tagId = tag.getId();
            this.tagName = tag.getName();
        }
    }

    /**
     * 增加数据
     */
    public void add(Object value) {
        count++;
    }

    public void addSum(Object value) {
        if (MathUt.isNumber(value)) {
            sumValue += ConvertUt.convertType(value, Double.class);
        }
    }

    /**
     * 计算百分率
     */
    public Double percent(Number all) {
        double cnt = all.doubleValue();
        if (cnt == 0.0) {
            return percent = 0.0;
        }
        return percent = count.doubleValue() / cnt;
    }

    public String getPercentStr() {
        return MathUt.toPercent(percent);
    }
    //****************************************

    public String getDepictId() {
        return depictId;
    }

    public void setDepictId(String depictId) {
        this.depictId = depictId;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Double getPercent() {
        return percent;
    }

    public void setPercent(Double percent) {
        this.percent = percent;
    }

    public Double getSumValue() {
        return sumValue;
    }

    public void setSumValue(Double sumValue) {
        this.sumValue = sumValue;
    }

}
