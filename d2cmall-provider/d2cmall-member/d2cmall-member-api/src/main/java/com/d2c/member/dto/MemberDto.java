package com.d2c.member.dto;

import com.alibaba.fastjson.JSONObject;
import com.d2c.member.model.MemberDetail;
import com.d2c.member.model.MemberInfo;
import org.apache.commons.lang.StringUtils;

public class MemberDto extends MemberInfo {

    private static final long serialVersionUID = 1L;
    /**
     * 用户详情
     */
    private MemberDetail memberDetail;
    /**
     * 微信unionId，其他openId
     */
    private String unionId;
    /**
     * 微信openId
     */
    private String openId;
    /**
     * 公众号授权openId
     */
    private String gzOpenId;
    /**
     * 买手服务openId
     */
    private String partnerOpenId;
    /**
     * 用户来源
     */
    private String source;
    /**
     * crm小组名称
     */
    private String crmName;
    /**
     * 设计师名称
     */
    private String designersName;

    public MemberDto() {
    }

    public MemberDto(MemberDetail memberDetail) {
        this.memberDetail = memberDetail;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getGzOpenId() {
        return gzOpenId;
    }

    public void setGzOpenId(String gzOpenId) {
        this.gzOpenId = gzOpenId;
    }

    public String getPartnerOpenId() {
        return partnerOpenId;
    }

    public void setPartnerOpenId(String partnerOpenId) {
        this.partnerOpenId = partnerOpenId;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public MemberDetail getMemberDetail() {
        return memberDetail;
    }

    public void setMemberDetail(MemberDetail memberDetail) {
        this.memberDetail = memberDetail;
    }

    public String getDesignersName() {
        return designersName;
    }

    public void setDesignersName(String designersName) {
        this.designersName = designersName;
    }

    public String getCrmName() {
        return crmName;
    }

    public void setCrmName(String crmName) {
        this.crmName = crmName;
    }

    public boolean isD2c() {
        return StringUtils.isNotBlank(this.getLoginCode());
    }

    @Override
    public String getSource() {
        return source;
    }

    @Override
    public void setSource(String source) {
        this.source = source;
    }

    public JSONObject toJson(String userToken) {
        JSONObject obj = this.toJson();
        if (this.getMemberDetail() != null) {
            obj.putAll(memberDetail.toJson());
        }
        obj.put("source", this.getSource());
        obj.put("openId", this.getOpenId());
        obj.put("unionId", this.getUnionId());
        obj.put("partnerOpenId", this.getPartnerOpenId());
        obj.put("userToken", userToken);
        return obj;
    }

}
