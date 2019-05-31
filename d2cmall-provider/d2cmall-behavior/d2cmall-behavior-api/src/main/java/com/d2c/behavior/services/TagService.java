package com.d2c.behavior.services;

import com.d2c.behavior.mongo.model.TagTypeDO;

import java.util.List;
import java.util.Map;

public interface TagService {

    /**
     * 初始化用户标签定义
     */
    List<TagTypeDO> initTag();

    TagTypeDO save(TagTypeDO type);

    Iterable<TagTypeDO> saveAll(Iterable<TagTypeDO> types);

    void cleanAll();

    Map<String, List<TagTypeDO>> findTagTypeMap();

    /**
     * 获取需要处理的用户标签
     */
    List<TagTypeDO> findTagType(Class<?> inputClz);

    List<TagTypeDO> findTagType(String inputClz);

}