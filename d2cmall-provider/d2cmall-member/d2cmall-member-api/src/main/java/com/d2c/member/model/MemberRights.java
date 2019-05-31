package com.d2c.member.model;

import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;

/**
 * 会员权益 TODO
 */
@Table(name = "m_member_rights")
public class MemberRights extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 名称
     */
    private String name;
    /**
     * 权益类型 1.线上 2.线下 3.线上线下
     */
    private Integer type;
    /**
     * 说明
     */
    private String desc;
    /**
     * 最低享受级别
     */
    private Integer level;
    /**
     * 系统参数
     */
    private String info;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

}
