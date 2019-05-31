package com.d2c.member.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;
import com.d2c.common.base.utils.StringUt;
import com.d2c.member.enums.DeviceTypeEnum;
import com.d2c.util.date.DateUtil;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Table;

/**
 * 买家秀评价
 */
@Table(name = "m_share_comment")
public class MemberShareComment extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 会员
     */
    @AssertColumn("会员ID不能为空")
    private Long memberId;
    /**
     * 会员头像
     */
    private String headPic;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 源ID
     */
    private Long sourceId;
    /**
     * 评价对象（默认商品名称或作品名称）
     */
    private String title;
    /**
     * 评论内容
     */
    private String content;
    /**
     * 是否通过审核，默认为否
     */
    private Integer verified = 1;
    /**
     * 买家秀会员ID
     */
    private Long memberShareUserId;
    /**
     * 评分 1-5分
     */
    private Integer score;
    /**
     * 回复谁的ID
     */
    private Long toMemberId;
    /**
     * 回复谁的头像
     */
    private String toMemberPic;
    /**
     * 回复谁的昵称
     */
    private String toNickName;
    /**
     * 被追加评论ID
     */
    private Long toCommentId;
    /**
     * 设备
     */
    private String device;
    /**
     * app版本号
     */
    private String appVersion;
    /**
     * 引用的内容
     */
    private String toCommentContent;
    /**
     * 评论点赞数
     */
    private Long likes;
    /**
     * 热门评论
     */
    private Integer hot;

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getMemberShareUserId() {
        return memberShareUserId;
    }

    public void setMemberShareUserId(Long memberShareUserId) {
        this.memberShareUserId = memberShareUserId;
    }

    public Long getToMemberId() {
        return toMemberId;
    }

    public void setToMemberId(Long toMemberId) {
        this.toMemberId = toMemberId;
    }

    public String getToMemberPic() {
        return toMemberPic;
    }

    public void setToMemberPic(String toMemberPic) {
        this.toMemberPic = toMemberPic;
    }

    public String getToNickName() {
        return toNickName;
    }

    public void setToNickName(String toNickName) {
        this.toNickName = toNickName;
    }

    public Long getToCommentId() {
        return toCommentId;
    }

    public void setToCommentId(Long toCommentId) {
        this.toCommentId = toCommentId;
    }

    public Integer getVerified() {
        return verified;
    }

    public void setVerified(Integer verified) {
        this.verified = verified;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getToCommentContent() {
        return toCommentContent;
    }

    public void setToCommentContent(String toCommentContent) {
        this.toCommentContent = toCommentContent;
    }

    public Long getLikes() {
        return likes;
    }

    public void setLikes(Long likes) {
        this.likes = likes;
    }

    public Integer getHot() {
        return hot;
    }

    public void setHot(Integer hot) {
        this.hot = hot;
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("id", this.getId());
        obj.put("memberId", this.getMemberId());
        obj.put("headPic", this.getHeadPic());
        obj.put("nickName", StringUt.hideMobile(this.getNickName()));
        obj.put("sourceId", this.getSourceId());
        obj.put("title", this.getTitle());
        obj.put("content", this.getContent());
        obj.put("verified", this.getVerified() == 1 ? true : false);
        obj.put("memberShareUserId", this.getMemberShareUserId());
        obj.put("score", this.getScore());
        obj.put("createDate", DateUtil.second2str2(this.getCreateDate()));
        obj.put("toMemberId", this.getToMemberId());
        obj.put("toMemberPic", this.getToMemberPic());
        obj.put("toNickName", this.getToNickName());
        obj.put("toCommentId", this.getToCommentId());
        obj.put("toCommentContent", this.getToCommentContent());
        obj.put("likes", this.getLikes());
        obj.put("hot", this.getHot());
        return obj;
    }

    public String getDeviceName() {
        if (StringUtils.isNotBlank(this.getDevice())) {
            return DeviceTypeEnum.valueOf(this.getDevice()).getDisplay();
        }
        return "电脑PC";
    }

}
