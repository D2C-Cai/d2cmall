package com.d2c.member.mongo.model;

import com.d2c.common.mongodb.model.BaseMongoDO;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
public class CollectCardTaskDO extends BaseMongoDO {

    /**
     *
     */
    private static final long serialVersionUID = -2734564893235507171L;
    /**
     * 会员ID
     */
    @Indexed
    private Long memberId;
    /**
     * 类型
     */
    @Indexed
    private String type;
    /**
     * 创建时间
     */
    @Indexed
    private Date createDate;
    /**
     * 1可用，0不可用
     */
    @Indexed
    private Integer status = 1;

    public CollectCardTaskDO() {
        this.createDate = new Date();
    }

    public CollectCardTaskDO(Long memberId, TaskType type) {
        this();
        this.memberId = memberId;
        this.type = type.name();
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public enum TaskType {
        SHAREACTIVITY, SHARE520
    }

}
