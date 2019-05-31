package com.d2c.member.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.logger.service.MsgUniteService;
import com.d2c.logger.support.MsgBean;
import com.d2c.logger.support.PushBean;
import com.d2c.member.dao.MemberShareCommentMapper;
import com.d2c.member.enums.MemberTaskEnum;
import com.d2c.member.model.MemberShare;
import com.d2c.member.model.MemberShareComment;
import com.d2c.member.mongo.services.MemberTaskExecService;
import com.d2c.member.query.MemberShareCommentSearcher;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("memberShareCommentService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class MemberShareCommentServiceImpl extends ListServiceImpl<MemberShareComment>
        implements MemberShareCommentService {

    @Autowired
    private MemberShareCommentMapper memberShareCommentMapper;
    @Autowired
    private MemberShareService memberShareService;
    @Autowired
    private MsgUniteService msgUniteService;
    @Reference
    private MemberTaskExecService memberTaskExecService;

    @Override
    public MemberShareComment findById(Long id) {
        return this.findOneById(id);
    }

    @Override
    public PageResult<MemberShareComment> findBySearcher(PageModel page, MemberShareCommentSearcher searcher) {
        PageResult<MemberShareComment> pager = new PageResult<>(page);
        int totalCount = memberShareCommentMapper.countBySearcher(searcher);
        List<MemberShareComment> list = new ArrayList<>();
        if (totalCount > 0) {
            list = memberShareCommentMapper.findBySearcher(searcher, page);
        }
        pager.setList(list);
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public MemberShareComment insert(MemberShareComment memberShareComment) {
        MemberShareComment prevComment = null;
        if (memberShareComment.getToCommentId() != null && memberShareComment.getToCommentId() > 0) {
            prevComment = this.findById(memberShareComment.getToCommentId());
        }
        if (prevComment != null) {
            memberShareComment.setToMemberId(prevComment.getMemberId());
            memberShareComment.setToNickName(prevComment.getNickName());
            memberShareComment.setToMemberPic(prevComment.getHeadPic());
            memberShareComment.setSourceId(prevComment.getSourceId());
            memberShareComment.setMemberShareUserId(prevComment.getMemberShareUserId());
            memberShareComment.setToCommentId(prevComment.getId());
            memberShareComment.setToCommentContent(prevComment.getContent());
        } else {
            MemberShare share = memberShareService.findById(memberShareComment.getSourceId());
            if (share == null) {
                throw new BusinessException("买家秀不存在！");
            }
            memberShareComment.setToMemberId(share.getMemberId());
            memberShareComment.setToNickName(share.getNickname());
            memberShareComment.setToMemberPic(share.getHeadPic());
            memberShareComment.setMemberShareUserId(share.getMemberId());
            memberShareComment.setSourceId(share.getId());
            memberShareComment.setToCommentId(0L);
        }
        // memberShareCommentMapper.updateColumn();
        int result = memberShareCommentMapper.insert(memberShareComment);
        if (result > 0) {
            result = memberShareService.updateCommentsCount(memberShareComment.getSourceId(), 1);
            // 完成买家秀评论任务
            memberTaskExecService.taskDone(memberShareComment.getMemberId(), MemberTaskEnum.SHARE_COMMENT);
        }
        return memberShareComment;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int updateStatus(int status, Long id, Long shareId) {
        if (shareId == null) {
            MemberShareComment memberShareComment = this.findById(id);
            if (memberShareComment.getVerified().intValue() == status) {
                return 1;
            }
            shareId = memberShareComment.getSourceId();
        }
        int result = memberShareCommentMapper.updateStatus(status, id);
        if (result > 0) {
            int count = status == 1 ? 1 : -1;
            memberShareService.updateCommentsCount(shareId, count);
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int deleteById(Long id) {
        MemberShareComment comment = this.memberShareCommentMapper.selectByPrimaryKey(id);
        int result = memberShareCommentMapper.deleteById(id);
        if (comment != null && result > 0) {
            result = memberShareService.updateCommentsCount(comment.getSourceId(), -1);
        }
        return result;
    }

    @Override
    public void doSendShareCommentMsg(MemberShareComment memberShareComment, String ip) {
        String title = "";
        String msg = "";
        String active = "";
        if (memberShareComment.getToCommentId() != null && memberShareComment.getToCommentId() != 0) {
            title = ("您有新的回复。");
            msg = ("您的买家秀评论获得了新的回复。");
            active = ("回复了你");
        } else {
            title = ("您有新的评论。");
            msg = ("您的买家秀获得了新的评论。");
            active = ("评论了你");
        }
        MemberShare share = memberShareService.findById(memberShareComment.getSourceId());
        JSONObject obj = new JSONObject();
        obj.put("nickName", memberShareComment.getNickName());
        obj.put("headPic", memberShareComment.getHeadPic());
        obj.put("memberId", memberShareComment.getMemberId());
        obj.put("sharePic", share.getPicsToArray().size() > 0 ? share.getPicsToArray().getString(0) : "");
        obj.put("shareId", share.getId());
        obj.put("active", active);
        obj.put("content", memberShareComment.getContent());
        String subject = title;
        String content = msg;
        PushBean pushBean = new PushBean(memberShareComment.getToMemberId(), content, 44);
        pushBean.setAppUrl("/details/share/" + memberShareComment.getSourceId());
        MsgBean msgBean = new MsgBean(memberShareComment.getToMemberId(), 44, subject, content);
        msgBean.setAppUrl("/details/share/" + memberShareComment.getSourceId());
        msgBean.setOther(obj.toJSONString());
        msgUniteService.sendPush(pushBean, msgBean);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public void doRefreshHeadPic(Long memberInfoId, String headPic, String nickName) {
        memberShareCommentMapper.doRefreshHeadPic(memberInfoId, headPic, nickName);
        memberShareCommentMapper.doRefreshHeadPic4To(memberInfoId, headPic, nickName);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int doLike(Long commentId) {
        return memberShareCommentMapper.doLike(commentId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int updateHot(Long id, Integer hot) {
        return memberShareCommentMapper.updateHot(id, hot);
    }

    @Override
    public Integer countBySearcher(MemberShareCommentSearcher searcher) {
        return memberShareCommentMapper.countBySearcher(searcher);
    }

}
