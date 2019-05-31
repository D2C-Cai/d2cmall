package com.d2c.behavior.mongo.model.depict;

import com.d2c.behavior.mongo.model.PersonDO;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.common.base.utils.StringUt;
import com.d2c.common.mongodb.model.SparkMongoDO;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 提交画像分析数据
 *
 * @author wull
 */
@Document
public class DepictDataDO extends SparkMongoDO {

    private static final long serialVersionUID = -247153036115072681L;
    /**
     * 用户ID
     */
    private String personId;
    private Long memberId;
    /**
     * 数据类型
     */
    private String dataClz;
    /**
     * 数据明细
     */
    private Object data;

    public DepictDataDO() {
    }

    public DepictDataDO(Object data, PersonDO person) {
        this.data = data;
        this.dataClz = data.getClass().getName();
        if (person != null) {
            this.personId = person.getId();
            this.memberId = person.getMemberInfoId();
        }
        initId();
    }

    private void initId() {
        try {
            String id = BeanUt.getValue(data, "id", String.class);
            if (id != null) {
                this.id = StringUt.join("_", dataClz, personId, id);
            }
        } catch (Exception e) {
        }
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getDataClz() {
        return dataClz;
    }

    public void setDataClz(String dataClz) {
        this.dataClz = dataClz;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
