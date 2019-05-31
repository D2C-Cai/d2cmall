package com.d2c.member.service;

import com.d2c.cache.redis.RedisHandler;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.dao.MemberMapper;
import com.d2c.member.enums.MemberTypeEnum;
import com.d2c.member.model.Member;
import com.d2c.member.query.MemberSearcher;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.util.string.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("memberService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class MemberServiceImpl extends ListServiceImpl<Member> implements MemberService {

    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private RedisHandler<String, Integer> redisHandler;

    @Override
    public Member findById(Long id) {
        return this.findOneById(id);
    }

    @Override
    public Member findByUnionId(String unionId) {
        return memberMapper.findByUnionId(unionId);
    }

    @Override
    public Member findByUnionIdAndSource(String source, String unionId) {
        return memberMapper.findByUnionIdAndSource(source, unionId);
    }

    @Override
    public Member findByLoginCodeAndSource(String source, String loginCode) {
        return memberMapper.findByLoginCodeAndSource(source, loginCode);
    }

    @Override
    public PageResult<Member> findBySearch(MemberSearcher searcher, PageModel page) {
        PageResult<Member> pager = new PageResult<>(page);
        int count = memberMapper.countBySeach(searcher);
        List<Member> list = new ArrayList<>();
        if (count > 0) {
            list = memberMapper.findBySeach(searcher, page);
        }
        pager.setTotalCount(count);
        pager.setList(list);
        return pager;
    }

    @Override
    public int countBySeach(MemberSearcher searcher) {
        return memberMapper.countBySeach(searcher);
    }

    @Override
    public List<Member> findByMemberInfoId(Long memberInfoId) {
        return memberMapper.findByMemberInfoId(memberInfoId);
    }

    @Override
    public Member findPartnerOpenId(Long memberInfoId) {
        return memberMapper.findPartnerOpenId(memberInfoId);
    }

    @Override
    public List<Map<String, Object>> findCountGroupBySource(MemberSearcher searcher) {
        return memberMapper.findCountGroupBySource(searcher);
    }

    @Override
    public Member findWeixinMember(Long memberInfoId) {
        List<Member> list = memberMapper.findByMemberInfoId(memberInfoId);
        for (Member member : list) {
            if (member.getSource().equals(MemberTypeEnum.Weixin.name())
                    || member.getSource().equals(MemberTypeEnum.WeixinGz.name())
                    || member.getSource().equals(MemberTypeEnum.WeixinXcx.name())) {
                if (member.getGzOpenId() != null) {
                    return member;
                }
            }
        }
        return null;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public Member insert(Member member) {
        return this.save(member);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public int updateLogin(String openId, String unionId, String source, String nickname, String headPic, String sex,
                           String token, String gzOpenId, String partnerOpenId) {
        int success = memberMapper.updateLogin(openId, unionId, source, nickname, headPic, sex, token, gzOpenId,
                partnerOpenId);
        if (success > 0) {
            this.refreshLoginMember(unionId);
        }
        return success;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public int updateInfo(String unionId, String nickname, String headPic, String sex) {
        int success = memberMapper.updateInfo(unionId, nickname, headPic, sex);
        if (success > 0) {
            this.refreshLoginMember(unionId);
        }
        return success;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public int updateToken(String unionId, String token) {
        int success = memberMapper.updateToken(unionId, token);
        if (success > 0) {
            this.refreshLoginMember(unionId);
        }
        return success;
    }

    @Override
    @Cacheable(value = "member", key = "'member_'+#token", unless = "#result == null")
    public Member findByToken(String token) {
        return memberMapper.findByToken(token);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public int doBindMemberInfo(String unionId, Long memberInfoId, String loginCode, String nickname, String headPic) {
        int success = memberMapper.doBindMemberInfo(unionId, memberInfoId, loginCode, nickname, headPic);
        if (success > 0) {
            this.refreshLoginMember(unionId);
        }
        return success;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public int doCancelBind(String unionId) {
        int success = memberMapper.doCancelBind(unionId);
        if (success > 0) {
            this.refreshLoginMember(unionId);
        }
        return success;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public int doChangeMobile(Long memberInfoId, String newMobile) {
        return memberMapper.doChangeMobile(memberInfoId, newMobile);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doClean(String loginCode) {
        return memberMapper.doClean(loginCode);
    }

    private void refreshLoginMember(String unionId) {
        String token = memberMapper.findTokenByUnionId(unionId);
        if (StringUtil.isNotBlank(token)) {
            redisHandler.delete("member_" + token);
        }
    }

}
