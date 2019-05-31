package com.d2c.member.model;

import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;
import java.util.HashMap;
import java.util.Map;

/**
 * 设计师
 */
@Table(name = "m_designers")
public class Designers extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 编码,唯一
     */
    @AssertColumn("编号不能为空")
    private String code;
    /**
     * 名称
     */
    @AssertColumn("名称不能为空")
    private String name;
    /**
     * D2C账号
     */
    private String loginCode;
    /**
     * 会员ID
     */
    private Long memberId;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Map<String, Object> toJsonMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("designerId", this.getId());
        map.put("designerName", this.getName());
        map.put("designerPic", "");
        map.put("type", 2);
        return map;
    }

}
