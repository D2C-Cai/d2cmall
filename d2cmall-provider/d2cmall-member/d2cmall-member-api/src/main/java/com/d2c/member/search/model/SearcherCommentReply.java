package com.d2c.member.search.model;

import com.d2c.common.api.dto.PreUserDTO;

public class SearcherCommentReply extends PreUserDTO {

    private static final long serialVersionUID = 1L;
    /**
     * 评论ID
     */
    private Long commentId;
    /**
     * 回复内容
     */
    private String content;
    /**
     * 二次回复ID
     */
    private Long replyId;
    /**
     * 回复类型
     */
    private String type;
    /**
     * 是否审核
     */
    private Integer verified;
    /**
     * 图片
     */
    private String pic;

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getReplyId() {
        return replyId;
    }

    public void setReplyId(Long replyId) {
        this.replyId = replyId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getVerified() {
        return verified;
    }

    public void setVerified(Integer verified) {
        this.verified = verified;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

}
