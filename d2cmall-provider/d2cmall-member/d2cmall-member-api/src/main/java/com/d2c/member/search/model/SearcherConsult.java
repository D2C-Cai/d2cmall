package com.d2c.member.search.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.dto.PreUserDTO;
import com.d2c.common.base.utils.StringUt;
import com.d2c.util.date.DateUtil;

import java.util.Date;

public class SearcherConsult extends PreUserDTO {

    private static final long serialVersionUID = 1L;
    /**
     * 状态 -1已删除 1未回复 2已回复
     */
    private Integer status;
    /**
     * 咨询内容
     */
    private String question;
    /**
     * 回复
     */
    private String reply;
    /**
     * 回复时间
     */
    private Date replyDate;
    /**
     * 咨询商品
     */
    private Long productId;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 会员ID
     */
    private Long memberId;
    /**
     * 商品图片
     */
    private String productPic;
    /**
     * 会员昵称
     */
    private String nickName;
    /**
     * 会员头像
     */
    private String headPic;
    /**
     * 设备
     */
    private String device;
    /**
     * 是否推荐， 1是 0否
     */
    private Integer recomend;
    /**
     * 推荐日期
     */
    private Date recomendDate;
    /**
     * 推荐内容
     */
    private String recomendContent;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public Date getReplyDate() {
        return replyDate;
    }

    public void setReplyDate(Date replyDate) {
        this.replyDate = replyDate;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getProductPic() {
        return productPic;
    }

    public void setProductPic(String productPic) {
        this.productPic = productPic;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public Integer getRecomend() {
        return recomend;
    }

    public void setRecomend(Integer recomend) {
        this.recomend = recomend;
    }

    public String getRecomendContent() {
        return recomendContent;
    }

    public void setRecomendContent(String recomendContent) {
        this.recomendContent = recomendContent;
    }

    public Date getRecomendDate() {
        return recomendDate;
    }

    public void setRecomendDate(Date recomendDate) {
        this.recomendDate = recomendDate;
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("id", this.getId());
        obj.put("question", this.getQuestion());
        obj.put("reply", this.getReply());
        obj.put("nickName", StringUt.hideMobile(this.getNickName()));
        obj.put("createDate", DateUtil.second2str2(this.getCreateDate()));
        obj.put("replyDate", this.getReplyDate() == null ? "" : DateUtil.second2str2(this.getReplyDate()));
        obj.put("headPic", this.getHeadPic() == null ? "" : this.getHeadPic());
        obj.put("recomend", this.getRecomend());
        return obj;
    }

}
