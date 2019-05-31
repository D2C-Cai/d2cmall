package com.d2c.behavior.mongo.model;

import com.d2c.behavior.mongo.model.base.PersonBasic;
import com.d2c.common.mongodb.model.BaseMongoDO;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * 用户表
 *
 * @author wull
 */
@Document
public class PersonDO extends BaseMongoDO implements PersonBasic {

    private static final long serialVersionUID = -7223788330791673547L;
    /**
     * 电话号码
     */
    @Indexed
    protected String phone;
    /**
     * D2C会员ID
     */
    @Indexed
    protected Long memberInfoId;
    /**
     * 会员头像
     */
    protected String headImg;
    /**
     * 会员昵称
     */
    protected String nickname;
    /**
     * 真实姓名
     */
    protected String realName;
    /**
     * 性别
     */
    protected String sex;
    /**
     * 邮箱
     */
    protected String email;
    /**
     * 生日
     */
    protected Date birthday;
    /**
     * 身份证
     */
    protected String identityCard;
    /**
     * 国际电话区号
     */
    protected String nationCode;
    /**
     * 属性对象
     */
    protected Object props;

    public PersonDO() {
    }

    public PersonDO(String phone, Object props) {
        this.phone = phone;
        this.props = props;
    }

    @Override
    public String toString() {
        return "phone:" + phone + " nickname:" + nickname + " realName:" + realName;
    }

    public Long getMemberInfoId() {
        return memberInfoId;
    }

    public void setMemberInfoId(Long memberInfoId) {
        this.memberInfoId = memberInfoId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
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

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public String getNationCode() {
        return nationCode;
    }

    public void setNationCode(String nationCode) {
        this.nationCode = nationCode;
    }

    public Object getProps() {
        return props;
    }

    public void setProps(Object props) {
        this.props = props;
    }

}
