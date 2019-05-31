package com.d2c.member.search.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.dto.PreUserDTO;
import com.d2c.common.base.utils.StringUt;

public class SearcherMemberFollow extends PreUserDTO {

    private static final long serialVersionUID = 1L;
    /**
     * 会员ID
     */
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

    public int getFollowType() {
        if (friends == 1)
            return FollowType.EACHPOWDER.getCode();
        return FollowType.FOLLOWED.getCode();
    }

    public JSONObject toFromJson() {
        JSONObject obj = new JSONObject();
        obj.put("memberId", this.getFromId());
        obj.put("headPic", this.getFromHeadPic());
        obj.put("nickName", StringUt.hideMobile(this.getFromNickName()));
        obj.put("friends", this.getFriends());
        obj.put("follow", this.getFriends() == 0 ? FollowType.UNFOLLOW.getCode() : FollowType.EACHPOWDER.getCode());
        return obj;
    }

    public JSONObject toToJson() {
        JSONObject obj = new JSONObject();
        obj.put("memberId", this.getToId());
        obj.put("headPic", this.getToHeadPic());
        obj.put("nickName", StringUt.hideMobile(this.getToNickName()));
        obj.put("friends", this.getFriends());
        obj.put("follow", this.getFollowType());
        return obj;
    }

    /**
     * 关注状态 UNFOLLOW 关注, FOLLOWED 被关注, EACHPOWDER 互粉;
     */
    public static enum FollowType {
        UNFOLLOW(0, "未关注"), FOLLOWED(1, "已关注"), EACHPOWDER(2, "互粉");

        /**
         * 枚举码
         */
        private int code;
        /**
         * 枚举描述
         */
        private String desc;
        private FollowType(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

}
