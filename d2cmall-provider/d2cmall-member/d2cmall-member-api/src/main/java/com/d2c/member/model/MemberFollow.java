package com.d2c.member.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;
import com.d2c.common.base.utils.StringUt;

import javax.persistence.Table;

/**
 * 喜欢的用户
 */
@Table(name = "m_member_follow")
public class MemberFollow extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 会员ID
     */
    @AssertColumn("会员ID不能为空")
    private Long fromId;
    /**
     * 会员昵称
     */
    private String fromNickName;
    /**
     * 会员头像
     */
    private String fromHeadPic;
    /**
     * 关注者ID
     */
    @AssertColumn("关注者ID不能为空")
    private Long toId;
    /**
     * 关注者昵称
     */
    private String toNickName;
    /**
     * 关注者头像
     */
    private String toHeadPic;
    /**
     * 是否好友
     */
    private Integer friends = 0;

    public Long getFromId() {
        return fromId;
    }

    public void setFromId(Long fromId) {
        this.fromId = fromId;
    }

    public String getFromNickName() {
        return fromNickName;
    }

    public void setFromNickName(String fromNickName) {
        this.fromNickName = fromNickName;
    }

    public String getFromHeadPic() {
        return fromHeadPic;
    }

    public void setFromHeadPic(String fromHeadPic) {
        this.fromHeadPic = fromHeadPic;
    }

    public Long getToId() {
        return toId;
    }

    public void setToId(Long toId) {
        this.toId = toId;
    }

    public String getToNickName() {
        return toNickName;
    }

    public void setToNickName(String toNickName) {
        this.toNickName = toNickName;
    }

    public String getToHeadPic() {
        return toHeadPic;
    }

    public void setToHeadPic(String toHeadPic) {
        this.toHeadPic = toHeadPic;
    }

    public Integer getFriends() {
        return friends;
    }

    public void setFriends(Integer friends) {
        this.friends = friends;
    }

    public JSONObject toFromJson() {
        JSONObject obj = new JSONObject();
        obj.put("memberId", this.getFromId());
        obj.put("headPic", this.getFromHeadPic());
        obj.put("nickName", StringUt.hideMobile(this.getFromNickName()));
        obj.put("friends", this.getFriends());
        return obj;
    }

    public JSONObject toToJson() {
        JSONObject obj = new JSONObject();
        obj.put("memberId", this.getToId());
        obj.put("headPic", this.getToHeadPic());
        obj.put("nickName", StringUt.hideMobile(this.getToNickName()));
        obj.put("friends", this.getFriends());
        return obj;
    }

}
