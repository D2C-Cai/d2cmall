package com.d2c.member.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.dao.CommentMapper;
import com.d2c.member.dto.CommentDto;
import com.d2c.member.enums.MemberTaskEnum;
import com.d2c.member.enums.PointRuleTypeEnum;
import com.d2c.member.model.Comment;
import com.d2c.member.model.CommentReply;
import com.d2c.member.model.MemberIntegration;
import com.d2c.member.mongo.services.MemberTaskExecService;
import com.d2c.member.query.CommentSearcher;
import com.d2c.member.search.model.SearcherComment;
import com.d2c.member.search.service.CommentSearcherService;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("commentService")
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class CommentServiceImpl extends ListServiceImpl<Comment> implements CommentService {

    @Autowired
    public IntegrationRuleService integrationRuleService;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private CommentReplyService commentReplyService;
    @Reference
    private CommentSearcherService commentSearcherService;
    @Autowired
    private MemberIntegrationService memberIntegrationService;
    @Reference
    private MemberTaskExecService memberTaskExecService;

    @Override
    public Comment findById(Long key) {
        return this.findOneById(key);
    }

    @Override
    public CommentDto findByIdAndMemberId(Long commentId, Long memeberId) {
        Comment comment = commentMapper.findById(commentId, memeberId);
        if (comment == null) {
            throw new RuntimeException("该评论已经不存在了！");
        }
        CommentDto commentDto = new CommentDto();
        BeanUtils.copyProperties(comment, commentDto);
        List<CommentReply> commentReplies = commentReplyService.findByCommentId(commentId);
        commentDto.setCommentReplys(commentReplies);
        return commentDto;
    }

    @Override
    public List<Comment> findTop3ByProductId(Long productId) {
        return commentMapper.findTop3ByProductId(productId);
    }

    @Override
    public PageResult<CommentDto> findBySearcher(CommentSearcher searcher, PageModel page) {
        PageResult<CommentDto> pager = new PageResult<>(page);
        int totalCount = commentMapper.countBySearcher(searcher);
        List<Comment> list = new ArrayList<>();
        List<CommentDto> dtos = new ArrayList<>();
        if (totalCount > 0) {
            list = commentMapper.findBySearcher(searcher, page);
            List<CommentReply> replys = null;
            for (Comment c : list) {
                CommentDto dto = new CommentDto();
                replys = commentReplyService.findByCommentId(c.getId());
                dto.setCommentReplys(replys);
                BeanUtils.copyProperties(c, dto);
                dtos.add(dto);
            }
        }
        pager.setTotalCount(totalCount);
        pager.setList(dtos);
        return pager;
    }

    @Override
    public PageResult<Comment> findSimpleBySearcher(CommentSearcher searcher, PageModel page) {
        PageResult<Comment> pager = new PageResult<>(page);
        int totalCount = commentMapper.countBySearcher(searcher);
        List<Comment> list = new ArrayList<>();
        if (totalCount > 0) {
            list = commentMapper.findBySearcher(searcher, page);
        }
        pager.setTotalCount(totalCount);
        pager.setList(list);
        return pager;
    }

    @Override
    public int countBySearcher(CommentSearcher searcher) {
        return commentMapper.countBySearcher(searcher);
    }

    @Override
    public int countByProduct(Long productId, Boolean verify) {
        return commentMapper.countByProduct(productId, verify);
    }

    @Override
    public Map<String, Object> countGroupByStatus() {
        Map<String, Object> productMap = new HashMap<>();
        List<Map<String, Object>> counts = commentMapper.countGroupByStatus();
        for (Map<String, Object> count : counts) {
            String mark = count.get("verified").toString();
            switch (mark) {
                case "true":
                    productMap.put("verifiedCount", count.get("counts"));
                    break;
                case "false":
                    productMap.put("unVerifiedCount", count.get("counts"));
                    break;
            }
        }
        return productMap;
    }

    @Override
    public List<Map<String, Object>> findCountGroupByScore(String scoreKey, Date startDate, Date endDate) {
        return commentMapper.findCountGroupByScore(scoreKey, startDate, endDate);
    }

    @Override
    public int findUnVerifiedCount() {
        CommentSearcher searcher = new CommentSearcher();
        searcher.setVerified(false);
        return commentMapper.countBySearcher(searcher);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public Comment insert(Comment comment) {
        commentMapper.updateColumn();
        comment = super.save(comment);
        if (comment != null) {
            SearcherComment searcherComment = new SearcherComment();
            BeanUtils.copyProperties(comment, searcherComment);
            commentSearcherService.insert(searcherComment);
            // 评价商品任务
            memberTaskExecService.taskDone(comment.getMemberId(), MemberTaskEnum.PRODUCT_COMMENT);
        }
        return comment;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public Comment insert4Back(Comment comment) {
        commentMapper.updateColumn();
        commentMapper.insert(comment);
        if (comment != null && comment.getId() > 0) {
            SearcherComment searcherComment = new SearcherComment();
            BeanUtils.copyProperties(comment, searcherComment);
            commentSearcherService.insert(searcherComment);
        }
        return comment;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public int update(Comment comment) {
        commentMapper.updateColumn();
        int success = this.updateNotNull(comment);
        if (success > 0) {
            SearcherComment searcherComment = new SearcherComment();
            BeanUtils.copyProperties(comment, searcherComment);
            commentSearcherService.update(searcherComment);
        }
        return success;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public int delete(Long productId, Long commentId) {
        int success = deleteById(commentId);
        if (success > 0) {
            commentSearcherService.remove(commentId);
        }
        return success;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public int updateStatus(Long commentId, Long productId, int status) {
        int result = commentMapper.updateStatus(commentId, status);
        if (result > 0) {
            SearcherComment searcherComment = new SearcherComment();
            searcherComment.setId(commentId);
            searcherComment.setVerified(status == 1 ? true : false);
            commentSearcherService.update(searcherComment);
        }
        return result;
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
    public int updateTop(Long[] ids, Integer top) {
        int success = commentMapper.updateTop(ids, top);
        if (success > 0) {
            for (Long id : ids) {
                SearcherComment searcherComment = new SearcherComment();
                searcherComment.setId(id);
                searcherComment.setRecomend(top);
                commentSearcherService.update(searcherComment);
                if (top == 1) {
                    Comment comment = this.findById(id);
                    MemberIntegration integration = new MemberIntegration(PointRuleTypeEnum.RECOMEND,
                            comment.getMemberId(), comment.getName(), comment.getId(), comment.getCreateDate());
                    memberIntegrationService.addIntegration(integration, PointRuleTypeEnum.RECOMEND, null, null, null);
                }
            }
        }
        return 0;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public int updateShareId(Long id, Long shareId) {
        return commentMapper.updateShareId(id, shareId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public int doMerge(Long memberSourceId, Long memberTargetId) {
        return commentMapper.doMerge(memberSourceId, memberTargetId);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public void doRefreshHeadPic(Long memberInfoId, String headPic, String nickName) {
        int success = commentMapper.doRefreshHeadPic(memberInfoId, headPic, nickName);
        if (success > 0) {
            commentSearcherService.doRefreshHeadPic(memberInfoId, headPic, nickName);
        }
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public int updateVideoById(Long id, String video) {
        return commentMapper.updateVideoById(id, video);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public int updatePic(Long id, String pic) {
        int success = commentMapper.updateFieldById(id.intValue(), "pic", pic);
        if (success > 0) {
            SearcherComment searcherComment = new SearcherComment();
            searcherComment.setId(id);
            searcherComment.setPic(pic);
            commentSearcherService.update(searcherComment);
        }
        return success;
    }

}
