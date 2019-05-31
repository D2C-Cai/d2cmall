package com.d2c.content.service;

import com.d2c.content.model.FieldDefine;

import java.util.List;

/**
 * 提供字段定义的相关数据库操作
 */
public interface FieldDefineService {

    /**
     * 根据页面id以及字段code值获取自定义字段信息。
     *
     * @param pageDefId 页面id
     * @param code
     * @return FieldDef
     */
    FieldDefine findOne(Long pageDefId, String code);

    /**
     * 根据view_field_define字段唯一标识id获取自定义字段信息。
     *
     * @param id
     * @return FieldDef
     */
    FieldDefine findOneById(Long id);

    /**
     * 以FieldDef对象作为参数，修改 view_field_define表中自定义字段信息。并修改缓存对应内容
     *
     * @param fieldDef
     * @return int
     */
    int update(FieldDefine fieldDef);

    /**
     * 以FieldDef对象作为参数，将自定义字段信息插入view_field_define表中。 并修改缓存对应内容
     *
     * @param fieldDef
     * @return
     */
    FieldDefine insert(FieldDefine fieldDef);

    /**
     * 根据页面id，从view_field_define表中获取所有该页面的自定义字段，以列表形式返回。
     *
     * @param pageDefId 根据页面id
     * @return List<E>
     */
    List<FieldDefine> findByPageDefId(Long pageDefId);

    /**
     * 根据页面id以及字段定义状态，从view_field_define表中获取所有该页面的自定义字段，以列表形式返回。
     *
     * @param pageDefId 根据页面id
     * @param status    是否可用状态
     * @return List<E>
     */
    List<FieldDefine> findByPageDefId(Long pageDefId, int status);

}
