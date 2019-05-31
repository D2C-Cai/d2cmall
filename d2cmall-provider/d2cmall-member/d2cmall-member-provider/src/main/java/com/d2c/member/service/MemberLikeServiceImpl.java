package com.d2c.member.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.logger.service.MsgUniteService;
import com.d2c.logger.support.MsgBean;
import com.d2c.logger.support.PushBean;
import com.d2c.member.dao.MemberLikeMapper;
import com.d2c.member.enums.MemberTaskEnum;
import com.d2c.member.model.MemberLike;
import com.d2c.member.model.MemberShare;
import com.d2c.member.mongo.services.MemberTaskExecService;
import com.d2c.member.query.MemberLikeSearcher;
import com.d2c.member.search.model.SearcherMemberLike;
import com.d2c.member.search.service.MemberLikeSearcherService;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("memberLikeService")
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class MemberLikeServiceImpl extends ListServiceImpl<MemberLike> implements MemberLikeService {

    @Autowired
    private MemberLikeMapper memberLikeMapper;
    @Autowired
    private MemberShareService memberShareService;
    @Reference
    private MemberLikeSearcherService memberLikeSearcherService;
    @Autowired
    private MsgUniteService msgUniteService;
    @Reference
    private MemberTaskExecService memberTaskExecService;

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public MemberLike insert(MemberLike memberLike) {
        memberLikeMapper.updateColumn();
        try {
            memberLike = this.save(memberLike);
            memberShareService.updateLikesCount(memberLike.getShareId(), 1);
            SearcherMemberLike sm = new SearcherMemberLike();
            BeanUtils.copyProperties(memberLike, sm);
            memberLikeSearcherService.insert(sm);
            // 完成买家秀点赞任务
            memberTaskExecService.taskDone(memberLike.getMemberId(), MemberTaskEnum.SHARE_LIKE);
        } catch (Exception e) {
            throw new BusinessException("您已经喜欢过了！");
        }
        return memberLike;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public int delete(Long memberId, Long shareId) {
        int result = memberLikeMapper.delete(memberId, shareId);
        if (result > 0) {
            result = memberShareService.updateLikesCount(shareId, -1);
            memberLikeSearcherService.remove(memberId, shareId);
        }
        return result;
    }

    public int countByMemberId(Long memberId) {
        return memberLikeMapper.countByMemberId(memberId);
    }

    public PageResult<MemberLike> findByMemberId(Long memberId, PageModel page) {
        PageResult<MemberLike> pager = new PageResult<MemberLike>(page);
        int totalCount = memberLikeMapper.countByMemberId(memberId);
        if (totalCount > 0) {
            List<MemberLike> list = memberLikeMapper.findByMemberId(memberId, page);
            pager.setTotalCount(totalCount);
            pager.setList(list);
        }
        return pager;
    }

    public int countByMeberShareId(Long shareId) {
        return memberLikeMapper.countByMemberShareId(shareId);
    }

    public MemberLike findByMemberAndShareId(Long memberId, Long shareId) {
        return memberLikeMapper.findByMemberAndShareId(memberId, shareId);
    }

    public PageResult<MemberLike> findByShareId(Long shareId, PageModel page) {
        PageResult<MemberLike> pager = new PageResult<MemberLike>(page);
        int totalCount = memberLikeMapper.countByMemberShareId(shareId);
        if (totalCount > 0) {
            List<MemberLike> list = memberLikeMapper.findByMemberShareId(shareId, page);
            pager.setTotalCount(totalCount);
            pager.setList(list);
        }
        return pager;
    }

    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public int doMerge(Long memberSourceId, Long memberTargetId) {
        return memberLikeMapper.doMerge(memberSourceId, memberTargetId);
    }

    @Override
    public PageResult<MemberLike> findBySearch(MemberLikeSearcher searcher, PageModel page) {
        PageResult<MemberLike> pager = new PageResult<MemberLike>(page);
        int totalCount = memberLikeMapper.countBySearcher(searcher);
        List<MemberLike> list = new ArrayList<MemberLike>();
        if (totalCount > 0) {
            list = memberLikeMapper.findBySearcher(searcher, page);
        }
        pager.setList(list);
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    public void doSendLikeMsg(MemberLike memberLike, MemberShare memberShare, String ip) {
        JSONObject obj = new JSONObject();
        obj.put("active", "点赞了你");
        obj.put("nickName", memberLike.getNickName());
        obj.put("headPic", memberLike.getHeadPic());
        obj.put("memberId", memberLike.getMemberId());
        obj.put("sharePic", memberLike.getSharePicFirst());
        obj.put("shareId", memberLike.getShareId());
        String subject = "您获得了新的点赞。";
        String content = "会员 " + memberLike.getNickName() + " 点赞了您的买家秀。";
        PushBean pushBean = new PushBean(memberShare.getMemberId(), content, 41);
        pushBean.setAppUrl("/details/share/" + memberShare.getId());
        MsgBean msgBean = new MsgBean(memberShare.getMemberId(), 41, subject, content);
        msgBean.setAppUrl("/details/share/" + memberShare.getId());
        msgBean.setOther(obj.toJSONString());
        msgUniteService.sendPush(pushBean, msgBean);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public void doRefreshHeadPic(Long memberInfoId, String headPic, String nickName) {
        int success = memberLikeMapper.doRefreshHeadPic(memberInfoId, headPic, nickName);
        if (success > 0) {
            memberLikeSearcherService.doRefreshHeadPic(memberInfoId, headPic, nickName);
        }
    }

}
