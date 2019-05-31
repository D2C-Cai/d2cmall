package com.d2c.member.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.annotation.HideColumn;
import com.d2c.common.api.annotation.HideEnum;
import com.d2c.common.api.model.PreUserDO;
import com.d2c.common.base.utils.StringUt;
import com.d2c.util.date.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

/**
 * 会员
 */
@Table(name = "m_member_info")
public class MemberInfo extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 1.普通会员 2.设计师 3.D2C官方 5.D2C达人
     */
    private Integer type = 1;
    /**
     * 地区编号
     */
    private String nationCode = "86";
    /**
     * 会员账号
     */
    @HideColumn(type = HideEnum.MOBILE)
    private String loginCode;
    /**
     * 会员密码
     */
    @JsonIgnore
    private String password;
    /**
     * 会员来源
     */
    private String source;
    /**
     * 会员设备
     */
    private String device;
    /**
     * 注册IP
     */
    private String registerIp;
    /**
     * 最后登录时间
     */
    private Date loginDate;
    /**
     * 最后登录设备
     */
    private String loginDevice;
    /**
     * 会员昵称
     */
    private String nickname;
    /**
     * 会员头像
     */
    private String headPic;
    /**
     * 会员性别
     */
    private String sex;
    /**
     * 会员背景
     */
    private String background;
    /**
     * 会员手机
     */
    @HideColumn(type = HideEnum.MOBILE)
    private String mobile;
    /**
     * 会员邮箱
     */
    private String email;
    /**
     * 会员生日
     */
    private Date birthday;
    /**
     * 推荐人ID
     */
    private Long recId;
    /**
     * 推荐码
     */
    private String recCode;
    /**
     * 推荐时间
     */
    private Date recDate;
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 身份证号
     */
    private String identityCard;
    /**
     * 同意协议时间
     */
    @Column(insertable = false)
    private Date agreeDate;
    /**
     * 屏蔽权限
     */
    @Column(insertable = false)
    private String shield;
    /**
     * 分销商ID
     */
    private Long partnerId;
    /**
     * 上级分销ID
     */
    private Long parentId;
    /**
     * 设计师ID
     */
    @Column(insertable = false)
    private Long designerId;
    /**
     * 门店ID
     */
    @Column(insertable = false)
    private Long storeId;
    /**
     * 经销商ID
     */
    @Column(insertable = false)
    private Long distributorId;
    /**
     * 折扣组ID
     */
    @Column(insertable = false)
    private Long groupId = null;
    /**
     * CRM小组ID
     */
    @Column(insertable = false)
    private Long crmGroupId;
    /**
     * 门店会员ID
     */
    @Column(insertable = false)
    private Long storeGroupId;
    /**
     * 是否实名
     */
    @Column(insertable = false)
    private boolean certification = false;

    public MemberInfo() {
    }

    public MemberInfo(Long id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getLoginDevice() {
        return loginDevice;
    }

    public void setLoginDevice(String loginDevice) {
        this.loginDevice = loginDevice;
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

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Long getRecId() {
        return recId;
    }

    public void setRecId(Long recId) {
        this.recId = recId;
    }

    public String getRecCode() {
        return recCode;
    }

    public void setRecCode(String recCode) {
        this.recCode = recCode;
    }

    public Date getRecDate() {
        return recDate;
    }

    public void setRecDate(Date recDate) {
        this.recDate = recDate;
    }

    public Date getAgreeDate() {
        return agreeDate;
    }

    public void setAgreeDate(Date agreeDate) {
        this.agreeDate = agreeDate;
    }

    public String getShield() {
        return shield;
    }

    public void setShield(String shield) {
        this.shield = shield;
    }

    public Long getDesignerId() {
        return designerId;
    }

    public void setDesignerId(Long designerId) {
        this.designerId = designerId;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Long getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(Long distributorId) {
        this.distributorId = distributorId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getCrmGroupId() {
        return crmGroupId;
    }

    public void setCrmGroupId(Long crmGroupId) {
        this.crmGroupId = crmGroupId;
    }

    public Long getStoreGroupId() {
        return storeGroupId;
    }

    public void setStoreGroupId(Long storeGroupId) {
        this.storeGroupId = storeGroupId;
    }

    public Long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Long partnerId) {
        this.partnerId = partnerId;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public boolean isCertification() {
        return certification;
    }

    public void setCertification(boolean certification) {
        this.certification = certification;
    }

    public String getNationCode() {
        if (!StringUtils.isNotBlank(nationCode)) {
            nationCode = "86";
        }
        return nationCode;
    }

    public void setNationCode(String nationCode) {
        if (!StringUtils.isNotBlank(nationCode)) {
            nationCode = "86";
        }
        this.nationCode = nationCode;
    }

    @Override
    public boolean equals(Object member) {
        MemberInfo thisMember;
        if (!(member instanceof MemberInfo))
            return false;
        thisMember = (MemberInfo) member;
        return this.getId().equals(thisMember.getId());
    }
    // @Override
    // public void validate() {
    // Map<String, String> result = new HashMap<>();
    // if (this.getEmail() != null) {
    // // 验证邮箱格式
    // Pattern p =
    // Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
    // Matcher m = p.matcher(this.getEmail());
    //
    // if (!m.find()) {
    // result.put("email_error", "请输入正确的邮箱。");
    // }
    // }
    // // if (ArrayUtils.contains(field, "nickname")) {
    // // if (this.getNickname() == null || this.getNickname().length() < 1
    // // || this.getNickname().length() < 2) {
    // // // 请输入昵称
    // // result.put("nickname_error", "请输入昵称，2-20个字符。");
    // // }
    // // }
    // }

    public String getDisplayName() {
        if (StringUtils.isNotBlank(this.getNickname())) {
            return this.getNickname();
        } else {
            return "匿名_" + this.getId();
        }
    }

    public void setDisplayName() {
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("id", this.getId());
        obj.put("d2c", !StringUtils.isEmpty(this.getLoginCode()));
        obj.put("type", this.getType());
        obj.put("memberId", this.getId());
        obj.put("nationCode", this.getNationCode());
        obj.put("loginCode", this.getLoginCode() == null ? "" : this.getLoginCode());
        obj.put("name", this.getLoginCode() == null ? "" : StringUt.hideMobile(this.getLoginCode()));
        obj.put("nickname", this.getNickname() == null ? "" : this.getNickname());
        obj.put("sex", this.getSex());
        obj.put("head", this.getHeadPic());
        obj.put("thirdPic", this.getHeadPic());
        obj.put("backgroud", this.getBackground() == null ? "" : this.getBackground());
        obj.put("mobile", this.getMobile() == null ? "" : StringUt.hideMobile(this.getMobile()));
        obj.put("email", this.getEmail());
        obj.put("birthDay", DateUtil.second2str2(this.getBirthday()));
        obj.put("recId", this.getRecId() == null ? 0L : this.getRecId());
        obj.put("recCode", this.getRecCode());
        obj.put("designerId", this.getDesignerId() == null ? 0L : this.getDesignerId());
        obj.put("distributorId", this.getDistributorId() == null ? 0L : this.getDistributorId());
        obj.put("partnerId", this.getPartnerId() == null ? 0L : this.getPartnerId());
        obj.put("parentId", this.getParentId() == null ? 0L : this.getParentId());
        obj.put("isCertification", this.isCertification());
        return obj;
    }

}