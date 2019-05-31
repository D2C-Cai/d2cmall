package com.d2c.member.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;
import com.d2c.common.base.utils.StringUt;
import com.d2c.util.date.DateUtil;

import javax.persistence.Table;
import java.util.Date;

/**
 * 商品咨询
 */
@Table(name = "m_consult")
public class Consult extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 状态 -1已删除 1未回复 2已回复
     */
    private Integer status = 1;
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
    @AssertColumn("会员ID不能为空")
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
     * app版本
     */
    private String appVersion;
    /**
     * 是否推荐，0否 1是
     */
    private int recomend;
    /**
     * 推荐时间
     */
    private Date recomendDate;
    /**
     * 推荐内容
     */
    private String recomendContent;
    /**
     * D2C货号
     */
    private String inernalSn;
    /**
     * 运营小组
     */
    private String operation;

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

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public int getRecomend() {
        return recomend;
    }

    public void setRecomend(int recomend) {
        this.recomend = recomend;
    }

    public Date getRecomendDate() {
        return recomendDate;
    }

    public void setRecomendDate(Date recomendDate) {
        this.recomendDate = recomendDate;
    }

    public String getRecomendContent() {
        return recomendContent;
    }

    public void setRecomendContent(String recomendContent) {
        this.recomendContent = recomendContent;
    }

    public String getInernalSn() {
        return inernalSn;
    }

    public void setInernalSn(String inernalSn) {
        this.inernalSn = inernalSn;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
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
        obj.put("inernalSn", this.getInernalSn());
        obj.put("productName", this.getProductName());
        obj.put("productPic", this.getProductPic());
        return obj;
    }

}
