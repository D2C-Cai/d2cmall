package com.d2c.member.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.d2c.member.dao.CommentReplyMapper;
import com.d2c.member.model.CommentReply;
import com.d2c.member.search.model.SearcherCommentReply;
import com.d2c.member.search.service.CommentReplySearcherService;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("commentReplyService")
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class CommentReplyServiceImpl extends ListServiceImpl<CommentReply> implements CommentReplyService {

    @Autowired
    private CommentReplyMapper commentReplyMapper;
    @Reference
    private CommentReplySearcherService commentReplySearcherService;

    public CommentReply findById(Long id) {
        return this.findOneById(id);
    }

    @Override
    public List<CommentReply> findByCommentId(Long commentId) {
        List<CommentReply> list = commentReplyMapper.findByCommentId(commentId);
        return list;
    }

    @Override
    public CommentReply insert(CommentReply commentReply) {
        commentReply = this.save(commentReply);
        if (commentReply != null) {
            SearcherCommentReply searcherCommentReply = new SearcherCommentReply();
            BeanUtils.copyProperties(commentReply, searcherCommentReply);
            commentReplySearcherService.insert(searcherCommentReply);
        }
        return commentReply;
    }

    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public int update(CommentReply commentReply) {
        return updateNotNull(commentReply);
    }

    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public int doAudit(Long commentReplyId) {
        return commentReplyMapper.audit(commentReplyId);
    }

    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public int deleteById(Long id) {
        return super.deleteById(id);
    }

}
