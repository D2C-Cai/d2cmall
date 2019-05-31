package com.d2c.member.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;
import com.d2c.common.base.utils.StringUt;
import com.d2c.common.base.utils.security.Base64Ut;
import com.d2c.util.string.StringUtil;

import javax.persistence.Table;

/**
 * 实名认证信息
 */
@Table(name = "m_member_certification")
public class MemberCertification extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 真实姓名
     */
    @AssertColumn(nullable = false, value = "请输入姓名")
    private String realName;
    /**
     * 身份证号
     */
    @AssertColumn(nullable = false, value = "请输入身份证号")
    private String identityCard;
    /**
     * 身份证正面
     */
    private String frontPic;
    /**
     * 身份证反面
     */
    private String behindPic;
    /**
     * 是否是默认
     */
    private Integer isdefault = 0;
    /**
     * 绑定的会员id
     */
    private Long memberId;
    /**
     * 会员账号
     */
    private String loginCode;
    /**
     * 状态
     */
    private Integer status = 1;
    /**
     * 是否活体认证
     */
    private Integer authenticate = 0;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public String getFrontPic() {
        return frontPic;
    }

    public void setFrontPic(String frontPic) {
        this.frontPic = frontPic;
    }

    public String getBehindPic() {
        return behindPic;
    }

    public void setBehindPic(String behindPic) {
        this.behindPic = behindPic;
    }

    public Integer getIsdefault() {
        return isdefault;
    }

    public void setIsdefault(Integer isdefault) {
        this.isdefault = isdefault;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getAuthenticate() {
        return authenticate;
    }

    public void setAuthenticate(Integer authenticate) {
        this.authenticate = authenticate;
    }

    public JSONObject toJson() {
        // 身份证照片勿传
        JSONObject obj = new JSONObject();
        obj.put("id", this.getId());
        obj.put("realName", this.getRealName());
        obj.put("identityCard", this.getIdentityCard() == null ? "" : StringUt.hideIDCard(this.getIdentityCard()));
        obj.put("isdefault", this.getIsdefault());
        obj.put("memberId", this.getMemberId());
        obj.put("isUpload",
                StringUtil.isNotBlank(this.getFrontPic()) && StringUtil.isNotBlank(this.getBehindPic()) ? 1 : 0);
        obj.put("authenticate", this.getAuthenticate());
        obj.put("cardNo", Base64Ut.encode(this.getIdentityCard()));
        return obj;
    }

}
