package com.d2c.member.mongo.model;

import com.d2c.common.mongodb.model.BaseMongoDO;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * 海报的点赞和点踩
 */
@Document
public class MemberPosterLikeDO extends BaseMongoDO {

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
     * 海报ID
     */
    @Indexed
    private String postId;
    /**
     * 创建时间
     */
    @Indexed
    private Date createDate;
    /**
     * 1点赞 -1点踩
     */
    private Integer direct = 1;
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

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getDirect() {
        return direct;
    }

    public void setDirect(Integer direct) {
        this.direct = direct;
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
