package com.d2c.member.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.model.PreUserDO;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Table;
import java.util.Date;

/**
 * 游客
 */
@Table(name = "m_member")
public class Member extends PreUserDO {

    public static final String CASTGC = "CASTGC";
    private static final long serialVersionUID = 1L;
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
     * 微信unionId，其他openId
     */
    private String unionId;
    /**
     * 游客来源
     */
    private String source;
    /**
     * 游客设备
     */
    private String device;
    /**
     * 游客IP
     */
    private String registerIp;
    /**
     * 最后登录时间
     */
    private Date loginDate;
    /**
     * 游客昵称
     */
    private String nickname;
    /**
     * 游客头像
     */
    private String headPic;
    /**
     * 游客性别
     */
    private String sex;
    /**
     * 票据
     */
    private String token;
    /*********************************** 以下是绑定手机后的信息 ***********************************/
    /**
     * 会员账号
     */
    private String loginCode;
    /**
     * 会员ID
     */
    private Long memberInfoId;
    /**
     * 会员日期
     */
    private Date bindDate;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getRegisterIp() {
        return registerIp;
    }

    public void setRegisterIp(String registerIp) {
        this.registerIp = registerIp;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public String getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }

    public Long getMemberInfoId() {
        return memberInfoId;
    }

    public void setMemberInfoId(Long memberInfoId) {
        this.memberInfoId = memberInfoId;
    }

    public Date getBindDate() {
        return bindDate;
    }

    public void setBindDate(Date bindDate) {
        this.bindDate = bindDate;
    }

    public boolean isD2c() {
        if (StringUtils.isEmpty(loginCode)) {
            return false;
        }
        return true;
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("unionId", this.getUnionId());
        obj.put("source", this.getSource());
        return obj;
    }

}
