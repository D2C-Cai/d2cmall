package com.d2c.member.query;

import com.d2c.common.api.query.model.BaseQuery;
import org.apache.commons.lang.StringUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class MemberSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 会员类型，1.普通会员，2.设计师，3.D2C官方，4.分销商
     */
    private Integer type;
    /**
     * 会员ID
     */
    private Long memberInfoId;
    /**
     * D2C账号
     */
    private String[] loginCode;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 真实姓名
     */
    private String name;
    /**
     * 手机号码
     */
    private String mobile;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 登录来源
     */
    private String source;
    /**
     * 登录设备
     */
    private String device;
    /**
     * 注册时间开始
     */
    private Date startDate;
    /**
     * 注册时间结束
     */
    private Date endDate;
    /**
     * 系统创建标识
     */
    private Boolean sysCreate;
    /**
     * 是否购买过
     */
    private Boolean bought;
    /**
     * 会员组ID
     */
    private Long groupId;
    /**
     * 设计师ID
     */
    private Long designerId;
    /**
     * 推荐ID
     */
    private Long recId;
    /**
     * 标签ID
     */
    private Long tagId;
    /**
     * 门店ID
     */
    private Long storeId;
    /**
     * 接待小组ID
     */
    private Long crmGroupId;
    /**
     * 门店会员ID
     */
    private Long storeGroupId;
    /**
     * 父级ID
     */
    private Long parentId;
    /**
     * 游客0 会员1
     */
    private Integer d2c;

    /**
     * 增加或减少endDate的天数
     */
    public void changeEndDate(int day) {
        if (endDate != null) {
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(endDate);
            calendar.add(Calendar.DATE, day);
            this.endDate = calendar.getTime();
        }
    }

    public Integer getD2c() {
        return d2c;
    }

    public void setD2c(Integer d2c) {
        this.d2c = d2c;
    }

    public Boolean getBought() {
        return bought;
    }

    public void setBought(Boolean bought) {
        this.bought = bought;
    }

    public Boolean getSysCreate() {
        return sysCreate;
    }

    public void setSysCreate(Boolean sysCreate) {
        this.sysCreate = sysCreate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Long getMemberInfoId() {
        return memberInfoId;
    }

    public void setMemberInfoId(Long memberInfoId) {
        this.memberInfoId = memberInfoId;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String[] getLoginCode() {
        if (loginCode != null && loginCode.length <= 0) {
            loginCode = null;
        }
        return loginCode;
    }

    public void setLoginCode(String[] loginCode) {
        if (loginCode != null && loginCode.length <= 0) {
            loginCode = null;
        }
        this.loginCode = loginCode;
    }

    public Long getDesignerId() {
        return designerId;
    }

    public void setDesignerId(Long designerId) {
        this.designerId = designerId;
    }

    public Long getRecId() {
        return recId;
    }

    public void setRecId(Long recId) {
        this.recId = recId;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public void setRegistDate(String registDate) {
        if (StringUtils.isBlank(registDate)) {
            return;
        }
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        int backDay = Integer.valueOf(registDate);
        cal.add(Calendar.DAY_OF_YEAR, -backDay);
        this.startDate = cal.getTime();
    }

}
