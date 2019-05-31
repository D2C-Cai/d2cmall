package com.d2c.behavior.mongo.model;

import com.d2c.behavior.mongo.enums.ThirdSourceType;
import com.d2c.behavior.mongo.model.base.PersonBasic;
import com.d2c.common.mongodb.model.SuperMongoDO;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 用户第三方账户表
 *
 * @author wull
 */
@Document
public class PersonThirdDO extends SuperMongoDO implements PersonBasic {

    private static final long serialVersionUID = -7223788330791673547L;
    /**
     * 唯一ID 微信：unionId 其他：openId
     */
    @Id
    private String unionId;
    /**
     * 用户表关联ID
     */
    @Indexed
    private String personId;
    /**
     * memberId
     */
    @Indexed
    private Long memberId;
    /**
     * openId
     */
    private String openId;
    /**
     * 第三方来源
     *
     * @see ThirdSourceType
     */
    private String source;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 头像
     */
    private String headImg;
    /**
     * 性别
     */
    private String sex;

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

}
