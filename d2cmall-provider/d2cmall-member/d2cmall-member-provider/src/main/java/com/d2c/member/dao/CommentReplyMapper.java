package com.d2c.member.dao;

import com.d2c.member.model.CommentReply;
import com.d2c.mybatis.mapper.SuperMapper;

import java.util.List;

public interface CommentReplyMapper extends SuperMapper<CommentReply> {

    List<CommentReply> findByCommentId(Long commentId);

    void updateColumn();

    int audit(Long id);

}