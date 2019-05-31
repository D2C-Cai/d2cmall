package com.d2c.member.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;

/**
 * 会员等级
 */
@Table(name = "m_member_level")
public class MemberLevel extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 名称
     */
    private String name;
    /**
     * 最低消费金额
     */
    private Integer reach;
    /**
     * 说明
     */
    private String memo;
    /**
     * 级别
     */
    private Integer level = 1;
    /**
     * 满365天后应扣除金额
     */
    private Integer deduction;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getReach() {
        return reach;
    }

    public void setReach(Integer reach) {
        this.reach = reach;
    }

    public Integer getDeduction() {
        return deduction;
    }

    public void setDeduction(Integer deduction) {
        this.deduction = deduction;
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("name", this.getName());
        obj.put("reach", this.getReach());
        obj.put("level", this.getReach());
        return obj;
    }

}
