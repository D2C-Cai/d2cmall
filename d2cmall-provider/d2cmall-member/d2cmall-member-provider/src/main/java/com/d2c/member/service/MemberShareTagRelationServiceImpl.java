package com.d2c.member.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.d2c.member.dao.MemberShareMapper;
import com.d2c.member.dao.MemberShareTagRelationMapper;
import com.d2c.member.model.MemberShare;
import com.d2c.member.model.MemberShareTagRelation;
import com.d2c.member.search.model.SearcherMemberShare;
import com.d2c.member.search.service.MemberShareSearcherService;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.util.string.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("memberShareTagRelationService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class MemberShareTagRelationServiceImpl extends ListServiceImpl<MemberShareTagRelation>
        implements MemberShareTagRelationService {

    @Autowired
    private MemberShareTagRelationMapper memberShareTagRelationMapper;
    @Autowired
    private MemberShareMapper memberShareMapper;
    @Reference
    private MemberShareSearcherService memberShareSearcherService;

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int insert(Long shareId, Long[] tagIds) {
        memberShareTagRelationMapper.deleteByMemberShareId(shareId);
        if (tagIds != null) {
            for (Long tagId : tagIds) {
                MemberShareTagRelation memberShareTagRelation = new MemberShareTagRelation();
                memberShareTagRelation.setShareId(shareId);
                memberShareTagRelation.setTagId(tagId);
                memberShareTagRelationMapper.insertNotNull(memberShareTagRelation);
            }
            String tags = StringUtil.longArrayToStr(tagIds);
            memberShareMapper.updateTags(shareId, tags);
            SearcherMemberShare sms = new SearcherMemberShare();
            sms.setId(shareId);
            sms.setTags(tags);
            memberShareSearcherService.update(sms);
        }
        return 1;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int deleteByTagIdAndMemberShareId(Long tagId, Long shareId) {
        memberShareTagRelationMapper.deleteByTagIdAndMemberShareId(tagId, shareId);
        MemberShare memberShare = memberShareMapper.selectByPrimaryKey(shareId);
        List<Long> list = StringUtil.strToLongList(memberShare.getTags());
        list.remove(tagId);
        SearcherMemberShare sms = new SearcherMemberShare();
        sms.setId(shareId);
        sms.setTags(StringUtil.longListToStr(list));
        memberShareSearcherService.update(sms);
        return 1;
    }

    @Override
    public MemberShareTagRelation findById(Long relationId) {
        return memberShareTagRelationMapper.selectByPrimaryKey(relationId);
    }

    @Override
    public int updateSort(Long shareId, Long tagId, Integer sort) {
        memberShareTagRelationMapper.updateSort(shareId, tagId, sort);
        return 1;
    }

}
