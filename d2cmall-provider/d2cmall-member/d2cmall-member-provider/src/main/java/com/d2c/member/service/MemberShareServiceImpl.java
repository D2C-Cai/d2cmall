package com.d2c.member.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.logger.service.MsgUniteService;
import com.d2c.logger.support.MsgBean;
import com.d2c.logger.support.PushBean;
import com.d2c.member.dao.MemberShareMapper;
import com.d2c.member.enums.MemberTaskEnum;
import com.d2c.member.model.MemberShare;
import com.d2c.member.model.Topic;
import com.d2c.member.mongo.services.MemberTaskExecService;
import com.d2c.member.query.MemberShareSearcher;
import com.d2c.member.search.model.SearcherMemberFollow;
import com.d2c.member.search.model.SearcherMemberShare;
import com.d2c.member.search.service.MemberFollowSearcherService;
import com.d2c.member.search.service.MemberShareSearcherService;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service("memberShareService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class MemberShareServiceImpl extends ListServiceImpl<MemberShare> implements MemberShareService {

    @Autowired
    public IntegrationRuleService integrationRuleService;
    @Autowired
    private MemberShareMapper memberShareMapper;
    @Reference
    private MemberShareSearcherService memberShareSearcherService;
    @Reference
    private MemberFollowSearcherService memberFollowSearcherService;
    @Autowired
    private MsgUniteService msgUniteService;
    @Autowired
    private TopicService topicService;
    @Reference
    private MemberTaskExecService memberTaskExecService;

    @Override
    public MemberShare findById(Long id) {
        return this.findOneById(id);
    }

    @Override
    public PageResult<MemberShare> findByTagId(Long tagId, PageModel page) {
        PageResult<MemberShare> pager = new PageResult<>(page);
        int totalCount = memberShareMapper.countByShareTagId(tagId);
        if (totalCount > 0) {
            List<MemberShare> list = memberShareMapper.findByShareTagId(tagId, page);
            pager.setList(list);
            pager.setTotalCount(totalCount);
        }
        return pager;
    }

    @Override
    public PageResult<MemberShare> findMyFollows(Long memberInfoId, Integer status, PageModel page) {
        PageResult<MemberShare> pager = new PageResult<>(page);
        int totalCount = memberShareMapper.countByMyFollows(memberInfoId, status);
        if (totalCount > 0) {
            pager.setList(memberShareMapper.findMyFollows(memberInfoId, status, page));
        }
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    public int findUnSubmitCount() {
        MemberShareSearcher memSearcher = new MemberShareSearcher();
        memSearcher.setStatus(0);
        int memberShareCount = memberShareMapper.countBySearch(memSearcher);
        return memberShareCount;
    }

    @Override
    public PageResult<MemberShare> findBySearch(MemberShareSearcher searcher, PageModel page) {
        PageResult<MemberShare> pager = new PageResult<>(page);
        int totalCount = memberShareMapper.countBySearch(searcher);
        if (totalCount > 0) {
            List<MemberShare> list = memberShareMapper.findBySearch(searcher, page);
            pager.setList(list);
        }
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    public int countByProductId(Long productId, Integer status) {
        return memberShareMapper.countByProductId(productId, status);
    }

    @Override
    public int countByMemberId(Long memberId, Integer status) {
        return memberShareMapper.countByMemberId(memberId, status);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public MemberShare insert(MemberShare memberShare) {
        memberShare = this.save(memberShare);
        SearcherMemberShare sms = new SearcherMemberShare();
        BeanUtils.copyProperties(memberShare, sms);
        memberShareSearcherService.insert(sms);
        if (memberShare.getType() == 1 && memberShare.getStatus() == 1) {
            // 买家秀任务
            memberTaskExecService.taskDone(memberShare.getMemberId(), MemberTaskEnum.SHARE_ADD);
        }
        return memberShare;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int update(MemberShare memberShare) {
        int res = this.updateNotNull(memberShare);
        if (res > 0) {
            SearcherMemberShare sms = new SearcherMemberShare();
            BeanUtils.copyProperties(memberShare, sms);
            sms.setScore(memberShare.getVlikes() + memberShare.getLikes() + memberShare.getComments());
            memberShareSearcherService.update(sms);
        }
        return res;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int updateUrl(Long memberShareId, String url) {
        int res = memberShareMapper.updateUrl(memberShareId, url);
        if (res > 0) {
            SearcherMemberShare sms = new SearcherMemberShare();
            sms.setId(memberShareId);
            sms.setUrl(url);
            memberShareSearcherService.update(sms);
        }
        return res;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int updatePicById(Long id, String pic) {
        int res = memberShareMapper.updatePic(id, pic);
        if (res > 0) {
            SearcherMemberShare sms = new SearcherMemberShare();
            sms.setId(id);
            sms.setPic(pic);
            memberShareSearcherService.update(sms);
        }
        return res;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int updateVideoById(Long id, String video) {
        int success = memberShareMapper.updateVideoById(id, video);
        if (success == 1) {
            SearcherMemberShare memberShare = new SearcherMemberShare();
            memberShare.setId(id);
            memberShare.setVideo(video);
            memberShareSearcherService.update(memberShare);
        }
        return success;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int updatePicTag(Long id, String picTag) {
        int res = memberShareMapper.updatePicTag(id, picTag);
        if (res > 0) {
            SearcherMemberShare sms = new SearcherMemberShare();
            sms.setId(id);
            sms.setPicTag(picTag);
            memberShareSearcherService.update(sms);
        }
        return res;
    }

    @Override
    public int updateLikesCount(Long shareId, int likes) {
        int res = memberShareMapper.updateLikesCount(shareId, likes);
        if (res > 0) {
            MemberShare memberShare = memberShareMapper.selectByPrimaryKey(shareId);
            SearcherMemberShare sms = new SearcherMemberShare();
            sms.setId(memberShare.getId());
            sms.setLikes(memberShare.getLikes());
            sms.setScore(memberShare.getLikes() + memberShare.getVlikes() + memberShare.getComments());
            memberShareSearcherService.update(sms);
        }
        return res;
    }

    @Override
    public int updateCommentsCount(Long shareId, int count) {
        int res = memberShareMapper.updateCommentsCount(shareId, count);
        if (res > 0) {
            MemberShare memberShare = memberShareMapper.selectByPrimaryKey(shareId);
            SearcherMemberShare sms = new SearcherMemberShare();
            sms.setId(memberShare.getId());
            sms.setComments(memberShare.getComments());
            sms.setScore(memberShare.getLikes() + memberShare.getVlikes() + memberShare.getComments());
            memberShareSearcherService.update(sms);
        }
        return res;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int updateBindStatus(Long id, Long productId, Long designerId) {
        int res = memberShareMapper.updateBindStatus(id, productId, designerId);
        if (res > 0) {
            SearcherMemberShare sms = new SearcherMemberShare();
            sms.setId(id);
            sms.setDesignerId(designerId);
            memberShareSearcherService.update(sms);
        }
        return res;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int updateDesignerId(Long id, Long designerId) {
        Integer role = designerId != null && designerId > 0 ? 1 : 0;
        int res = memberShareMapper.updateDesignerId(id, designerId, role);
        if (res > 0) {
            SearcherMemberShare sms = new SearcherMemberShare();
            sms.setId(id);
            sms.setDesignerId(designerId);
            sms.setRole(role);
            memberShareSearcherService.update(sms);
        }
        return res;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int delete(Long id) {
        int res = memberShareMapper.deleteById(id);
        if (res > 0) {
            memberShareSearcherService.remove(id);
        }
        return res;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doWatch(Long shareId) {
        int success = memberShareMapper.doWatch(shareId);
        if (success > 0) {
            memberShareSearcherService.doWatched(shareId);
        }
        return success;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doRefuse(Long shareId, String refuseReason, String lastModifyMan) {
        return memberShareMapper.doRefuse(shareId, refuseReason, lastModifyMan);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doVerify(Long memberShareId, String verifyMan) {
        int res = memberShareMapper.doVerify(memberShareId, verifyMan);
        if (res > 0) {
            SearcherMemberShare sms = new SearcherMemberShare();
            sms.setId(memberShareId);
            sms.setVerifyDate(new Date());
            sms.setStatus(1);
            memberShareSearcherService.update(sms);
            MemberShare memberShare = this.findById(memberShareId);
            if (memberShare != null && memberShare.getType() == 1) {
                // 新增买家秀任务
                memberTaskExecService.taskDone(memberShare.getMemberId(), MemberTaskEnum.SHARE_ADD);
            }
        }
        return res;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doCancelVerify(Long memberShareId, String cancelMan) {
        int res = memberShareMapper.doCancelVerify(memberShareId, cancelMan);
        if (res > 0) {
            SearcherMemberShare sms = new SearcherMemberShare();
            sms.setId(memberShareId);
            sms.setStatus(0);
            memberShareSearcherService.update(sms);
        }
        return res;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doTop(Long id) {
        int res = memberShareMapper.updateTop(id, 1);
        if (res > 0) {
            SearcherMemberShare sms = new SearcherMemberShare();
            sms.setId(id);
            sms.setTop(1);
            memberShareSearcherService.update(sms);
        }
        return res;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doCancelTop(Long id) {
        int res = memberShareMapper.updateTop(id, 0);
        if (res > 0) {
            SearcherMemberShare sms = new SearcherMemberShare();
            sms.setId(id);
            sms.setTop(0);
            memberShareSearcherService.update(sms);
        }
        return res;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doUnBindDesigner(Long id) {
        int res = memberShareMapper.unBindDesigner(id);
        if (res > 0) {
            SearcherMemberShare sms = new SearcherMemberShare();
            sms.setId(id);
            sms.setDesignerId(0L);
            memberShareSearcherService.update(sms);
        }
        return res;
    }

    @Override
    public int doCancelBind(Long id, String type) {
        if (type == null) {
            return -1;
        }
        if (type.equals("designer")) {
            return doUnBindDesigner(id);
        }
        return 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doAccept(Long id, Integer status, String lastModifyMan) {
        return memberShareMapper.doAccept(id, status, lastModifyMan);
    }

    @Override
    public void doSendAcceptMsg(Long memberShareId) {
        MemberShare memberShare = this.findById(memberShareId);
        String subject = "您的体验分享已被我们采纳使用，请联系客服获取相应奖励~";
        String content = subject;
        PushBean pushBean = new PushBean(memberShare.getMemberId(), content, 29);
        pushBean.setAppUrl("/customer/service");
        MsgBean msgBean = new MsgBean(memberShare.getMemberId(), 29, subject, content);
        msgBean.setAppUrl("/customer/service");
        msgUniteService.sendPush(pushBean, msgBean);
    }

    @Override
    public void doSendShareMsg(MemberShare memberShare, String ip) {
        // 消息推送
        PageModel page = new PageModel();
        page.setPageSize(500);
        PageResult<SearcherMemberFollow> pager = memberFollowSearcherService.findByToId(memberShare.getMemberId(), null,
                page);
        String appUrl = "/details/share/" + memberShare.getId();
        JSONObject obj = new JSONObject();
        obj.put("active", "发布了新的买家秀");
        obj.put("nickName", memberShare.getNickname());
        obj.put("headPic", memberShare.getHeadPic());
        obj.put("memberId", memberShare.getMemberId());
        obj.put("sharePic", memberShare.getPicsToArray().size() > 0 ? memberShare.getPicsToArray().getString(0) : "");
        obj.put("shareId", memberShare.getId());
        for (SearcherMemberFollow follow : pager.getList()) {
            if (!memberShare.getMemberId().equals(follow.getFromId())) {
                String subject = "您关注的用户发布了新的买家秀。";
                String content = "会员 " + memberShare.getNickname() + " 发布了新的买家秀。";
                PushBean pushBean = new PushBean(follow.getFromId(), content, 29);
                pushBean.setAppUrl(appUrl);
                MsgBean msgBean = new MsgBean(follow.getFromId(), 43, subject, content);
                msgBean.setAppUrl(appUrl);
                msgBean.setOther(obj.toJSONString());
                msgUniteService.sendPush(pushBean, msgBean);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public void doRefreshHeadPic(Long memberInfoId, String headPic, String nickName) {
        int success = memberShareMapper.doRefreshHeadPic(memberInfoId, headPic, nickName);
        if (success > 0) {
            memberShareSearcherService.doRefreshHeadPic(memberInfoId, headPic, nickName);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doMerge(Long memberSourceId, Long memberTargetId) {
        return memberShareMapper.doMerge(memberSourceId, memberTargetId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doBindTopic(Long id, Long topicId) {
        Topic topic = topicService.findById(topicId);
        int success = memberShareMapper.doBindTopic(id, topicId, topic.getTitle());
        if (success > 0) {
            MemberShare memberShare = this.findById(id);
            SearcherMemberShare searcherMemberShare = new SearcherMemberShare();
            BeanUtils.copyProperties(memberShare, searcherMemberShare);
            memberShareSearcherService.rebuild(searcherMemberShare);
        }
        return success;
    }

    @Override
    public int countByTopic(Long topicId) {
        return memberShareMapper.countByTopic(topicId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int updateRole(Long id, Integer role) {
        int success = memberShareMapper.updateRole(id, role);
        if (success > 0) {
            SearcherMemberShare sms = new SearcherMemberShare();
            sms.setId(id);
            sms.setRole(role);
            memberShareSearcherService.update(sms);
        }
        return success;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int updateRoleByMemberId(Long memberId, Integer role) {
        int success = memberShareMapper.updateRoleByMemberId(memberId, role);
        if (success > 0) {
            memberShareSearcherService.updateRoleByMemberId(memberId, role);
        }
        return success;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doAddDownCount(Long id) {
        int success = memberShareMapper.doAddDownCount(id);
        if (success > 0) {
            MemberShare memberShare = this.findById(id);
            SearcherMemberShare searcherMemberShare = new SearcherMemberShare();
            BeanUtils.copyProperties(memberShare, searcherMemberShare);
            memberShareSearcherService.rebuild(searcherMemberShare);
        }
        return success;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doAddShareCount(Long id) {
        int success = memberShareMapper.doAddShareCount(id);
        if (success > 0) {
            MemberShare memberShare = this.findById(id);
            SearcherMemberShare searcherMemberShare = new SearcherMemberShare();
            BeanUtils.copyProperties(memberShare, searcherMemberShare);
            memberShareSearcherService.rebuild(searcherMemberShare);
        }
        return success;
    }

}
