package com.d2c.member.model;

import com.alibaba.fastjson.JSONArray;
import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;

/**
 * 评价回复
 */
@Table(name = "m_comment_reply")
public class CommentReply extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 评论ID
     */
    @AssertColumn("回复对象不能为空")
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
    private Integer verified = 1;
    /**
     * 追评图片
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

    public JSONArray getPicsToArray() {
        JSONArray arry = new JSONArray();
        if (pic != null && pic.length() > 0) {
            for (String item : pic.split(",")) {
                if (item == null) {
                    continue;
                }
                arry.add(item);
            }
        }
        return arry;
    }

    /**
     * CUSTOMER 客户, SYSTEM 客服
     */
    public enum ReplyType {
        CUSTOMER, SYSTEM
    }

}
