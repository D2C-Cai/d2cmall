package com.d2c.behavior.mongo.enums;

import com.d2c.behavior.mongo.dto.load.TagTypeDTO;
import com.d2c.behavior.mongo.model.TagDO;
import com.d2c.common.base.utils.ConvertUt;
import com.d2c.common.base.utils.ListUt;
import com.d2c.common.core.query.MyCriteria;
import com.d2c.common.core.query.MyCriteriaResolver;
import org.elasticsearch.common.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 事件模式
 * <p> 事件在声明后才会被记录
 *
 * @author wull
 */
public enum TagFromEnum {
    HASH_ADD_TAG("增量离散，不初始化标签，根据数据增加"),
    HASH_TAG("离散数据") {
        public List<TagDO> initTags(TagTypeDTO dto) {
            return ConvertUt.convertList(dto.getTags(), TagDO.class);
        }
    },
    RANGES_TAG("线性范围离散数据") {
        /**
         * "ranges": [1000, 3000, 5000, 10000]
         */
        public List<TagDO> initTags(TagTypeDTO dto) {
            List<TagDO> tags = new ArrayList<>();
            int i = 1;
            Double lv = null;
            for (Double v : dto.getRanges()) {
                TagDO tag = new TagDO(i);
                MyCriteria cri = MyCriteria.build();
                if (lv != null) {
                    cri.gte(lv);
                }
                cri.lt(v);
                tag.setQueryJson(cri.toJson());
                if (lv == null) {
                    tag.setName("lt_" + v.intValue());
                } else {
                    tag.setName(lv.intValue() + "-" + v.intValue());
                }
                tags.add(tag);
                lv = v;
                i++;
            }
            TagDO tag = new TagDO(i, "gt_" + lv.intValue());
            MyCriteria cri = MyCriteria.build().gte(lv);
            tag.setQueryJson(cri.toJson());
            tags.add(tag);
            return tags;
        }

        public TagDO selectTag(List<TagDO> tags, Object value) {
            for (TagDO tag : tags) {
                if (MyCriteriaResolver.check(tag.getJsonBean(), value)) {
                    return tag;
                }
            }
            return null;
        }
    };
    String remark;

    TagFromEnum(String remark) {
        this.remark = remark;
    }

    /**
     * 根据标签配置文件获取标签类型
     */
    public static TagFromEnum getTagFrom(TagTypeDTO dto) {
        if (ListUt.notEmpty(dto.getRanges())) {
            return RANGES_TAG;
        }
        if (ListUt.notEmpty(dto.getTags())) {
            return HASH_TAG;
        }
        return HASH_ADD_TAG;
    }

    public static TagFromEnum getValueOf(String name) {
        try {
            return valueOf(name);
        } catch (Exception e) {
            return HASH_ADD_TAG;
        }
    }

    /**
     * 初始化标签列表
     */
    public TagDO selectTag(List<TagDO> tags, Object value) {
        for (TagDO tag : tags) {
            if (tag.getName().equals(value)) {
                return tag;
            }
        }
        String name = value.toString();
        if (StringUtils.contains(name, ".")) {
            name = name.replaceAll("\\.", "_");
        }
        TagDO tag = new TagDO(name);
        tags.add(tag);
        return tag;
    }

    boolean selectTag(TagDO tag, Object value) {
        return tag.getName().equals(value);
    }

    /**
     * 初始化标签列表
     */
    public List<TagDO> initTags(TagTypeDTO dto) {
        return new ArrayList<>();
    }

    @Override
    public String toString() {
        return remark;
    }
}
