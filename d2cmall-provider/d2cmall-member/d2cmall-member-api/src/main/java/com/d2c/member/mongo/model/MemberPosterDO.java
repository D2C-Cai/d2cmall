package com.d2c.member.mongo.model;

import com.d2c.common.mongodb.model.BaseMongoDO;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * 我的海报
 */
@Document
public class MemberPosterDO extends BaseMongoDO {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 会员ID和unionID
     */
    @Indexed
    private String memberId;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 商品ID
     */
    private Long productId;
    /**
     * 海报图
     */
    private String pic;
    /**
     * 点赞数量
     */
    private Integer likeCount = 0;
    /**
     * 点踩数量
     */
    private Integer dissCount = 0;
    /**
     * 会员昵称
     */
    private String nickName;
    /**
     * 会员头像
     */
    private String headPic;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public Integer getDissCount() {
        return dissCount;
    }

    public void setDissCount(Integer dissCount) {
        this.dissCount = dissCount;
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

}
