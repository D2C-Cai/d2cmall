package com.d2c.member.service;

import com.d2c.member.model.CommentReply;

import java.util.List;

public interface CommentReplyService {

    /**
     * 查询回复内容
     *
     * @param id：主键ID
     * @return
     */
    CommentReply findById(Long id);

    /**
     * 查询回复
     *
     * @param commentId：回复ID
     * @return
     */
    List<CommentReply> findByCommentId(Long commentId);

    /**
     * 添加回复
     *
     * @param commentReply：回复内容
     * @return
     */
    CommentReply insert(CommentReply commentReply);

    /**
     * 修改回复
     *
     * @param commentReply：回复内容
     * @return
     */
    int update(CommentReply commentReply);

    /**
     * 取消回复
     *
     * @param commentReplyId：回复编号
     * @return
     */
    int doAudit(Long commentReplyId);

    /**
     * 删除回复
     *
     * @param id：主键ID
     * @return
     */
    int deleteById(Long id);

}
