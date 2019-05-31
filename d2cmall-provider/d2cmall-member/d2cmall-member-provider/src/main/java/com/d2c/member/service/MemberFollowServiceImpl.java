package com.d2c.member.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.logger.service.MsgUniteService;
import com.d2c.logger.support.MsgBean;
import com.d2c.logger.support.PushBean;
import com.d2c.member.dao.MemberFollowMapper;
import com.d2c.member.model.MemberFollow;
import com.d2c.member.search.model.SearcherMemberFollow;
import com.d2c.member.search.service.MemberFollowSearcherService;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("memberFollowService")
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class MemberFollowServiceImpl extends ListServiceImpl<MemberFollow> implements MemberFollowService {

    @Autowired
    private MemberFollowMapper memberFollowMapper;
    @Autowired
    private MsgUniteService msgUniteService;
    @Reference
    private MemberFollowSearcherService memberFollowSearcherService;

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public int insert(MemberFollow memberFollow) {
        if (memberFollow.getToId().equals(memberFollow.getFromId())) {
            throw new BusinessException("不能自己关注自己!");
        }
        // 判断对方是否关注自己
        int n = memberFollowMapper.updateFriends(memberFollow.getToId(), memberFollow.getFromId(), 1);
        if (n <= 0) {
            memberFollow.setFriends(0);
        } else {
            memberFollow.setFriends(1);
            // 对方的设为1
            memberFollowSearcherService.updateFriends(memberFollow.getToId(), memberFollow.getFromId(), 1);
        }
        try {
            memberFollowMapper.updateColumn();
            this.save(memberFollow);
            SearcherMemberFollow searcherMemberFollow = new SearcherMemberFollow();
            BeanUtils.copyProperties(memberFollow, searcherMemberFollow);
            memberFollowSearcherService.insert(searcherMemberFollow);
        } catch (Exception e) {
            throw new BusinessException("您已经关注过了!");
        }
        return memberFollow.getFriends();
    }

    @Override
    public int countByFromId(Long fromId) {
        return memberFollowMapper.countByFromId(fromId);
    }

    @Override
    public PageResult<MemberFollow> findByFromId(Long fromId, PageModel page) {
        PageResult<MemberFollow> pager = new PageResult<>(page);
        int totalCount = memberFollowMapper.countByFromId(fromId);
        if (totalCount > 0) {
            List<MemberFollow> list = memberFollowMapper.findByFromId(fromId, page);
            pager.setTotalCount(totalCount);
            pager.setList(list);
        }
        return pager;
    }

    @Override
    public int countByToId(Long toId, Integer friends) {
        return memberFollowMapper.countByToId(toId, friends);
    }

    @Override
    public PageResult<MemberFollow> findByToId(Long toId, Integer friends, PageModel page) {
        PageResult<MemberFollow> pager = new PageResult<>(page);
        int totalCount = memberFollowMapper.countByToId(toId, friends);
        if (totalCount > 0) {
            List<MemberFollow> list = memberFollowMapper.findByToId(toId, friends, page);
            pager.setTotalCount(totalCount);
            pager.setList(list);
        }
        return pager;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public int delete(Long fromId, Long toId) {
        int success = memberFollowMapper.updateFriends(toId, fromId, 0);
        if (success > 0) {
            memberFollowSearcherService.updateFriends(toId, fromId, 0);
        }
        int deleteSuccess = memberFollowMapper.delete(fromId, toId);
        if (deleteSuccess > 0) {
            memberFollowSearcherService.remove(fromId + "_" + toId);
        }
        return success;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public void doMerge(Long memberSourceId, Long memberTargetId) {
        memberFollowMapper.doMergeFrom(memberSourceId, memberTargetId);
        memberFollowMapper.doMergeTo(memberSourceId, memberTargetId);
    }

    @Override
    public List<Long> findToIdByFromId(Long fromId) {
        return memberFollowMapper.findToIdByFromId(fromId);
    }

    @Override
    public void doSendFollowMsg(Long memberId, MemberFollow memberFollow, String ip) {
        JSONObject obj = new JSONObject();
        obj.put("active", "关注了你");
        obj.put("nickName", memberFollow.getFromNickName());
        obj.put("headPic", memberFollow.getFromHeadPic());
        obj.put("memberId", memberFollow.getFromId());
        String subject = "您获得了新的关注。";
        String content = "会员 " + memberFollow.getFromNickName() + " 关注了您。";
        PushBean pushBean = new PushBean(memberId, content, 42);
        pushBean.setAppUrl("/myInterest/myFans");
        MsgBean msgBean = new MsgBean(memberId, 42, subject, content);
        msgBean.setAppUrl("/myInterest/myFans");
        msgBean.setOther(obj.toJSONString());
        msgUniteService.sendPush(pushBean, msgBean);
    }

    @Override
    public PageResult<MemberFollow> findByPage(PageModel page) {
        PageResult<MemberFollow> pageResult = new PageResult<>();
        int totalCount = memberFollowMapper.countTotal();
        if (totalCount > 0) {
            List<MemberFollow> memberFollows = memberFollowMapper.findByPage(page);
            pageResult.setList(memberFollows);
        }
        pageResult.setTotalCount(totalCount);
        return pageResult;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public void doRefreshHeadPic(Long memberInfoId, String headPic, String nickName) {
        int from_success = memberFollowMapper.doRefreshHeadPic4From(memberInfoId, headPic, nickName);
        int to_success = memberFollowMapper.doRefreshHeadPic4To(memberInfoId, headPic, nickName);
        if (from_success > 0) {
            memberFollowSearcherService.doRefreshHeadPic4From(memberInfoId, headPic, nickName);
        }
        if (to_success > 0) {
            memberFollowSearcherService.doRefreshHeadPic4To(memberInfoId, headPic, nickName);
        }
    }

}
