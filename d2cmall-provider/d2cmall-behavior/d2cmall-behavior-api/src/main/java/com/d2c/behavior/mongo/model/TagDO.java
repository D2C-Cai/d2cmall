package com.d2c.behavior.mongo.model;

import com.d2c.common.api.json.JsonBean;
import com.d2c.common.base.utils.StringUt;
import com.d2c.common.core.query.MyCriteria;
import com.d2c.common.mongodb.model.BaseMongoDO;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 用户标签
 * <p>
 * 默认标签进行
 *
 * @author wull
 */
@Document
public class TagDO extends BaseMongoDO {

    private static final long serialVersionUID = 6239772410037801425L;
    /**
     * 标签类型ID （必填）
     *
     * @see TagTypeDO
     */
    @Indexed
    private String typeId;
    /**
     * 名称（必填）
     */
    private String name;
    /**
     * 排序Index
     */
    private Integer order;
    /**
     * 查询条件JSON
     *
     * @see MyCriteria
     * @see JsonBean
     */
    private String queryJson;
    @Transient
    private JsonBean jsonBean;

    public TagDO() {
    }

    public TagDO(Integer order) {
        this.order = order;
    }

    public TagDO(Integer order, String name) {
        this.order = order;
        this.name = name;
    }

    public TagDO(String name) {
        this.name = name;
    }

    public void initId() {
        this.id = StringUt.join("|", typeId, name);
    }

    public JsonBean getJsonBean() {
        if (jsonBean == null && queryJson != null) {
            jsonBean = JsonBean.build(queryJson);
        }
        return jsonBean;
    }
    //***********************************

    public void setJsonBean(JsonBean jsonBean) {
        this.jsonBean = jsonBean;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getQueryJson() {
        return queryJson;
    }

    public void setQueryJson(String queryJson) {
        this.queryJson = queryJson;
    }

}
