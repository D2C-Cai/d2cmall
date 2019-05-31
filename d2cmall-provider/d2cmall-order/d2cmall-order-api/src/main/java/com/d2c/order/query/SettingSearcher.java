package com.d2c.order.query;

import com.d2c.common.api.query.model.BaseQuery;

public class SettingSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 0 未启用，1启用
     */
    private Integer status;
    /**
     * 参数名称
     */
    private String name;
    /**
     * 参数类型
     */
    private String type;
    /**
     * 参数编号
     */
    private String code;
    /**
     * 会员类型
     */
    private String memberType;
    /**
     * 会员名称
     */
    private String memberName;
    /**
     * 会员ID
     */
    private Long memberId;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMemberType() {
        return memberType;
    }

    public void setMemberType(String memberType) {
        this.memberType = memberType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

}
