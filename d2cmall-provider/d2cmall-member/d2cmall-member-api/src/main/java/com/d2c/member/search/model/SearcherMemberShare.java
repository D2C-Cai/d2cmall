package com.d2c.member.search.model;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d2c.common.base.utils.StringUt;
import com.d2c.util.date.DateUtil;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.Date;

public class SearcherMemberShare implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 买家秀ID
     */
    private Long id;
    /**
     * 日期
     */
    private Date createDate;
    /**
     * 会员ID
     */
    private Long memberId;
    /**
     * 会员昵称
     */
    private String nickname;
    /**
     * 会员发布时头像
     */
    private String headPic;
    /**
     * X坐标
     */
    private Double x;
    /**
     * Y坐标
     */
    private Double y;
    /**
     * 城市
     */
    private String city;
    /**
     * 街道
     */
    private String street;
    /**
     * 买家秀图片
     */
    private String pic;
    /**
     * 描述
     */
    private String description;
    /**
     * URL
     */
    private String url;
    /**
     * 状态，0未审核，1已审核
     */
    private Integer status;
    /**
     * 点赞次数
     */
    private Integer likes;
    /**
     * 评论次数
     */
    private Integer comments;
    /**
     * 虚拟点赞次数
     */
    private Integer vlikes;
    /**
     * 是否置顶 TOP
     */
    private Integer top;
    /**
     * 会员名
     */
    private String name;
    /**
     * 会员手机号
     */
    private String mobile;
    /**
     * 会员邮箱
     */
    private String email;
    /**
     * 设计师ID
     */
    private Long designerId;
    /**
     * 商品ID
     */
    private Long productId;
    /**
     * 标签,逗号分隔
     */
    private String tags;
    /**
     * 图片标签
     */
    private String picTag;
    /**
     * 当前用户是否喜欢该买家秀
     */
    private Integer liked;
    /**
     * 会员角色 0普通会员，1设计师
     */
    private Integer role;
    /**
     * 短视频地址
     */
    private String video;
    /**
     * 资源类型
     */
    private String resourceType;
    /**
     * 视频长度
     */
    private Long timeLength;
    /**
     * 买家秀类型 1.买家秀 2.评论生成 3.体验分享
     */
    private Integer type;
    /**
     * 话题ID
     */
    private Long topicId;
    /**
     * 话题名称
     */
    private String topicName;
    /**
     * 评论+点赞数
     */
    private Integer score;
    /**
     * 审核时间
     */
    private Date verifyDate;
    /**
     * 关联商品
     */
    private String productIds;
    /**
     * 查看数
     */
    private Long watched;
    /**
     * 下载数
     */
    private Long downCount;
    /**
     * 分享数
     */
    private Long shareCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
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

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public Integer getVlikes() {
        return vlikes;
    }

    public void setVlikes(Integer vlikes) {
        this.vlikes = vlikes;
    }

    public Integer getTop() {
        return top;
    }

    public void setTop(Integer top) {
        this.top = top;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getDesignerId() {
        return designerId;
    }

    public void setDesignerId(Long designerId) {
        this.designerId = designerId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getPicTag() {
        return picTag;
    }

    public void setPicTag(String picTag) {
        this.picTag = picTag;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Integer getLiked() {
        return liked;
    }

    public void setLiked(Integer liked) {
        this.liked = liked;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public Long getTimeLength() {
        return timeLength;
    }

    public void setTimeLength(Long timeLength) {
        this.timeLength = timeLength;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public Long getWatched() {
        return watched;
    }

    public void setWatched(Long watched) {
        this.watched = watched;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Date getVerifyDate() {
        return verifyDate;
    }

    public void setVerifyDate(Date verifyDate) {
        this.verifyDate = verifyDate;
    }

    public String getProductIds() {
        return productIds;
    }

    public void setProductIds(String productIds) {
        this.productIds = productIds;
    }

    public Long getDownCount() {
        return downCount;
    }

    public void setDownCount(Long downCount) {
        this.downCount = downCount;
    }

    public Long getShareCount() {
        return shareCount;
    }

    public void setShareCount(Long shareCount) {
        this.shareCount = shareCount;
    }

    public JSONArray picsToArray() {
        JSONArray arry = new JSONArray();
        if (pic != null && pic.length() > 0) {
            for (String item : pic.split(",")) {
                if (StringUtils.isBlank(item)) {
                    continue;
                }
                arry.add(item);
            }
        }
        return arry;
    }

    public JSONArray getPics() {
        return this.picsToArray();
    }

    public JSONObject toSimpleJson() {
        JSONObject obj = new JSONObject();
        obj.put("id", this.getId());
        obj.put("likeMeCount", this.getLikes() + this.getVlikes());
        obj.put("url", this.getUrl());
        if (pic != null && pic.length() > 0) {
            obj.put("pic", pic.split(",")[0]);
        }
        return obj;
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("id", this.getId());
        obj.put("memberId", this.getMemberId());
        obj.put("memberName", this.getNickname() == null ? "匿名用户" : StringUt.hideMobile(this.getNickname()));
        obj.put("memberHead", this.getHeadPic() == null ? "" : this.getHeadPic());
        obj.put("createDate", DateUtil.second2str2(this.getCreateDate()));
        obj.put("x", this.getX());
        obj.put("y", this.getY());
        obj.put("city", this.getCity());
        obj.put("street", this.getStreet());
        obj.put("pics", this.picsToArray());
        obj.put("description", this.getDescription());
        obj.put("url", this.getUrl());
        obj.put("productId", this.getProductId());
        obj.put("designerId", this.getDesignerId() != null ? this.getDesignerId() : 0);
        obj.put("likeMeCount", this.getLikes());
        obj.put("commentCount", this.getComments());
        obj.put("watchCount", this.getWatched());
        obj.put("shareCount", this.getShareCount());
        obj.put("downCount", this.getDownCount());
        obj.put("top", this.getTop());
        obj.put("liked", this.getLiked());
        obj.put("role", this.getRole());
        obj.put("video", this.getVideo());
        obj.put("resourceType", this.getResourceType());
        obj.put("timeLength", this.getTimeLength());
        obj.put("topicId", this.getTopicId());
        obj.put("topicName", this.getTopicName());
        obj.put("timestamp", this.getCreateDate().getTime());
        obj.put("verifyDate", this.getVerifyDate());
        return obj;
    }

}
