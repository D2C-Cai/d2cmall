package com.d2c.member.search.query;

import java.io.Serializable;

public class CommentSearchBean implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 商品ID
     */
    private Long productId;
    /**
     * 用户ID
     */
    private Long memberId;
    /**
     * 审核
     */
    private Boolean verified;
    /**
     * IDs
     */
    private long[] commentIds;
    /**
     * 是否有图
     */
    private Integer hasPic;
    /**
     * 来源
     */
    private String source;
    /**
     * 显示我的所有和其他人审核的
     */
    private Boolean allList = false;

    public Boolean getAllList() {
        return allList;
    }

    public void setAllList(Boolean allList) {
        this.allList = allList;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public long[] getCommentIds() {
        return commentIds;
    }

    public void setCommentIds(long[] commentIds) {
        this.commentIds = commentIds;
    }

    public Integer getHasPic() {
        return hasPic;
    }

    public void setHasPic(Integer hasPic) {
        this.hasPic = hasPic;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

}
