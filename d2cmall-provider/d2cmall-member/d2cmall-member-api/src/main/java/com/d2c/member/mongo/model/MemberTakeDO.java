package com.d2c.member.mongo.model;

import com.d2c.common.mongodb.model.BaseMongoDO;
import com.d2c.member.model.MemberInfo;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 会员参与记录
 *
 * @author Lain
 */
@Document
public class MemberTakeDO extends BaseMongoDO {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 会员ID
     */
    private Long memberId;
    /**
     * 会员账号
     */
    private String loginCode;
    /**
     * 会员昵称
     */
    private String nickName;
    /**
     * 类型
     */
    private String type;
    /**
     * 标注
     */
    private String mark;
    @Indexed(unique = true)
    private String uniqueMark;

    public MemberTakeDO() {
    }

    public MemberTakeDO(MemberInfo memberInfo, MarkType markType, String mark) {
        this.memberId = memberInfo.getId();
        this.loginCode = memberInfo.getLoginCode();
        this.nickName = memberInfo.getDisplayName();
        this.type = markType.name();
        this.mark = mark;
        this.uniqueMark = markType.name() + "_" + memberInfo.getId() + "_" + (mark == null ? "" : mark);
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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUniqueMark() {
        return uniqueMark;
    }

    public void setUniqueMark(String uniqueMark) {
        this.uniqueMark = uniqueMark;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public enum MarkType {
        AWARD181111
    }

}
