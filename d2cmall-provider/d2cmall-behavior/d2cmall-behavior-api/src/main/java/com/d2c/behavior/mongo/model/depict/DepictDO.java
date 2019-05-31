package com.d2c.behavior.mongo.model.depict;

import com.d2c.behavior.mongo.enums.TagFromEnum;
import com.d2c.behavior.mongo.model.TagDO;
import com.d2c.behavior.mongo.model.TagTypeDO;
import com.d2c.common.base.utils.StringUt;
import com.d2c.common.mongodb.model.BaseMongoDO;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 用户画像数据
 *
 * @author wull
 */
@Document
public class DepictDO extends BaseMongoDO {

    private static final long serialVersionUID = 5496583247498404853L;
    /**
     * 用户ID
     */
    @Indexed
    private String personId;
    @Indexed
    private Long memberId;
    /**
     * 标签类型ID
     */
    @Indexed
    private String typeId;
    private String typeName;
    private String tagFrom;
    /**
     * 最适合的用户标签概率 ID
     */
    @Indexed
    private String depictTagId;
    private DepictTagDO depictTag;
    @Indexed
    private String tagId;
    private String tagName;
    @Indexed
    private Double tagSum = 0.0;
    /**
     * 收集器共处理的数据数量
     */
    private Long count = 0L;
    /**
     * 画像计算后标签统计数据
     * <p>tagName: DepictTagDO
     */
    private Map<String, DepictTagDO> depictTags;

    public DepictDO() {
        this.depictTags = new LinkedHashMap<>();
        this.gmtModified = new Date();
    }

    public DepictDO(String personId, Long memberId, TagTypeDO tagType) {
        this();
        this.personId = personId;
        this.memberId = memberId;
        if (tagType != null) {
            this.typeId = tagType.getId();
            this.typeName = tagType.getName();
            this.tagFrom = tagType.getTagFrom();
        }
        this.id = StringUt.join("|", personId, typeId);
    }

    /**
     * 合并数据
     */
    public DepictDO merge(DepictDO bean) {
        if (!this.id.equals(bean.getId())) return this;
        bean.getDepictTags().forEach((k, v) -> {
            depictTags.merge(k, v, (otag, ntag) -> {
                return otag.merge(ntag);
            });
        });
        return this;
    }

    /**
     * 更新数据
     */
    public DepictDO update() {
        if (depictTags.isEmpty()) return this;
        count = 0L;
        for (DepictTagDO v : depictTags.values()) {
            count += v.getCount();
        }
        double maxPerc = 0.0;
        DepictTagDO select = null;
        for (DepictTagDO v : depictTags.values()) {
            v.percent(count);
            if (maxPerc <= v.getPercent()) {
                maxPerc = v.getPercent();
                select = v;
            }
        }
        this.setDepictTag(select);
        return this;
    }

    /**
     * 根据标签添加标签达成概率
     */
    public DepictTagDO addTag(TagDO tag, Object value) {
        DepictTagDO dp = findAddDepictTag(tag);
        if (dp != null) {
            dp.add(value);
            if (TagFromEnum.RANGES_TAG.equals(this.tagFrom)) {
                dp.addSum(value);
            }
        }
        return dp;
    }

    /**
     * 查找并新建用户该标签的数据
     */
    public DepictTagDO findAddDepictTag(TagDO tag) {
        if (tag == null) return null;
        DepictTagDO res = depictTags.get(tag.getName());
        if (res == null) {
            res = new DepictTagDO(id, tag);
            depictTags.put(tag.getName(), res);
        }
        return res;
    }

    public String getPersonId() {
        return personId;
    }
    // *********************************

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getDepictTagId() {
        return depictTagId;
    }

    public void setDepictTagId(String depictTagId) {
        this.depictTagId = depictTagId;
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

    public String getTagFrom() {
        return tagFrom;
    }

    public void setTagFrom(String tagFrom) {
        this.tagFrom = tagFrom;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Map<String, DepictTagDO> getDepictTags() {
        return depictTags;
    }

    public void setDepictTags(Map<String, DepictTagDO> depictTags) {
        this.depictTags = depictTags;
    }

    public DepictTagDO getDepictTag() {
        return depictTag;
    }

    /**
     * 设置最高概率标签
     */
    public void setDepictTag(DepictTagDO depictTag) {
        this.depictTag = depictTag;
        if (depictTag != null) {
            this.depictTagId = depictTag.getId();
            this.tagId = depictTag.getTagId();
            this.tagName = depictTag.getTagName();
            this.tagSum = depictTag.getSumValue();
        }
    }

    public Double getTagSum() {
        return tagSum;
    }

    public void setTagSum(Double tagSum) {
        this.tagSum = tagSum;
    }

}
