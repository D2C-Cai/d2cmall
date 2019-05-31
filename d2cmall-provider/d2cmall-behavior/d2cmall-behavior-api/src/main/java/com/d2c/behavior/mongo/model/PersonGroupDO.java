package com.d2c.behavior.mongo.model;

import com.d2c.common.mongodb.model.BaseMongoDO;
import org.bson.Document;
import org.springframework.data.mongodb.core.index.Indexed;

/**
 * 用户分组表
 *
 * @author wull
 */
@org.springframework.data.mongodb.core.mapping.Document
public class PersonGroupDO extends BaseMongoDO {

    private static final long serialVersionUID = 2628742370401855924L;
    /**
     * 用户分组名称
     */
    @Indexed
    private String groupName;
    /**
     * 查询Json条件
     */
    private Document bson;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Document getBson() {
        return bson;
    }

    public void setBson(Document bson) {
        this.bson = bson;
    }

}
