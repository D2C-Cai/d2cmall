package com.d2c.behavior.mongo.model;

import com.d2c.common.mongodb.model.BaseMongoDO;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * 标签类型定义
 *
 * @author wull
 */
@Document
public class TagTypeDO extends BaseMongoDO {

    private static final long serialVersionUID = 5496583247498404853L;
    private String name;
    /**
     * 输入数据类型
     */
    @Indexed
    private String inputClz;
    /**
     * 输入数据SpEL字段解析表达式
     */
    private String inputSpel;
    /**
     * 标签生成方式
     */
    private String tagFrom;
    private List<TagDO> tags;
    //***************************

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInputClz() {
        return inputClz;
    }

    public void setInputClz(String inputClz) {
        this.inputClz = inputClz;
    }

    public String getInputSpel() {
        return inputSpel;
    }

    public void setInputSpel(String inputSpel) {
        this.inputSpel = inputSpel;
    }

    public String getTagFrom() {
        return tagFrom;
    }

    public void setTagFrom(String tagFrom) {
        this.tagFrom = tagFrom;
    }

    public List<TagDO> getTags() {
        return tags;
    }

    public void setTags(List<TagDO> tags) {
        this.tags = tags;
    }

}
