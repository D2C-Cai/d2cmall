package com.d2c.behavior.mongo.dto.load;

import com.d2c.common.api.dto.BaseDTO;

import java.util.List;

/**
 * 标签类型导入
 *
 * @author wull
 */
public class TagTypeDTO extends BaseDTO {

    private static final long serialVersionUID = 6312458042499876803L;
    /**
     * 标签ID
     */
    private String id;
    /**
     * 标签类型名称
     */
    private String name;
    /**
     * 是否覆盖
     */
    private Boolean override = false;
    /**
     * 输入数据类型
     */
    private String inputClz;
    /**
     * 输入数据SpEL字段解析表达式
     */
    private String inputSpel;
    /**
     * 标签生成方式
     */
    private String tagFrom;
    /**
     * 离散数据范围
     */
    private List<Double> ranges;
    /**
     * 标签列表
     */
    private List<TagDTO> tags;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getOverride() {
        return override;
    }

    public void setOverride(Boolean override) {
        this.override = override;
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

    public List<Double> getRanges() {
        return ranges;
    }

    public void setRanges(List<Double> ranges) {
        this.ranges = ranges;
    }

    public List<TagDTO> getTags() {
        return tags;
    }

    public void setTags(List<TagDTO> tags) {
        this.tags = tags;
    }

}
