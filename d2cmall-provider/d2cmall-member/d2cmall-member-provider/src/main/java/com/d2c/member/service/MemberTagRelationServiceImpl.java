package com.d2c.member.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.dao.MemberTagRelationMapper;
import com.d2c.member.dto.MemberTagRelationDto;
import com.d2c.member.model.MemberTag;
import com.d2c.member.model.MemberTagRelation;
import com.d2c.member.query.MemberTagRelationSearcher;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("memberTagRelationService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class MemberTagRelationServiceImpl extends ListServiceImpl<MemberTagRelation>
        implements MemberTagRelationService {

    @Autowired
    private MemberTagRelationMapper memberTagRelationMapper;
    @Autowired
    private MemberTagService memberTagService;
    @Autowired
    private MemberInfoService memberInfoService;

    @Override
    public MemberTagRelation findById(Long id) {
        return this.findOneById(id);
    }

    @Override
    public List<Long> findByTagId(Long tagId) {
        List<Long> list = memberTagRelationMapper.findByTagId(tagId);
        return list;
    }

    @Override
    public List<MemberTagRelation> findByMemberId(Long memberId) {
        List<MemberTagRelation> list = memberTagRelationMapper.findByMemberId(memberId);
        return list;
    }

    @Override
    public PageResult<MemberTagRelationDto> findBySearch(PageModel page, MemberTagRelationSearcher searcher) {
        PageResult<MemberTagRelationDto> pager = new PageResult<>(page);
        Integer totalCount = memberTagRelationMapper.countBySearch(searcher);
        List<MemberTagRelationDto> dtoList = new ArrayList<>();
        if (totalCount > 0) {
            List<MemberTagRelation> list = memberTagRelationMapper.findBySearch(page, searcher);
            for (MemberTagRelation item : list) {
                MemberTagRelationDto dto = new MemberTagRelationDto();
                BeanUtils.copyProperties(item, dto);
                dto.setMemberInfo(memberInfoService.findById(item.getMemberId()));
                dtoList.add(dto);
            }
            pager.setTotalCount(totalCount);
        }
        pager.setList(dtoList);
        return pager;
    }

    @Override
    public Integer countBySearch(MemberTagRelationSearcher searcher) {
        return memberTagRelationMapper.countBySearch(searcher);
    }

    @Override
    public PageResult<MemberTagRelation> findSimpleBySearch(PageModel page, MemberTagRelationSearcher searcher) {
        PageResult<MemberTagRelation> pager = new PageResult<>(page);
        int totalCount = memberTagRelationMapper.countBySearch(searcher);
        if (totalCount > 0) {
            List<MemberTagRelation> list = memberTagRelationMapper.findBySearch(page, searcher);
            pager.setList(list);
        }
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public MemberTagRelation insert(MemberTagRelation memberTagRelation) {
        return this.save(memberTagRelation);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int batchInsert(Long[] memberIds, Long[] tagIds) {
        for (Long memberId : memberIds) {
            this.deleteByMemberId(memberId);
        }
        if (tagIds != null && tagIds.length > 0) {
            for (Long tagId : tagIds) {
                MemberTag tag = memberTagService.findById(tagId);
                for (Long memberId : memberIds) {
                    MemberTagRelation memberTagRelation = new MemberTagRelation();
                    memberTagRelation.setMemberId(memberId);
                    memberTagRelation.setTagId(tagId);
                    memberTagRelation.setTagName(tag.getName());
                    this.insert(memberTagRelation);
                }
            }
        }
        return 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int deleteByTagId(Long tagId) {
        return memberTagRelationMapper.deleteByTagId(tagId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int deleteByIds(Long[] ids) {
        return memberTagRelationMapper.deleteByIds(ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int deleteByMemberId(Long memberId) {
        return memberTagRelationMapper.deleteByMemberId(memberId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int updateStatusByTagId(Long tagId, Integer status) {
        return memberTagRelationMapper.updateStatusByTagId(tagId, status);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int doReplaceInto(MemberTagRelation memberTagRelation) {
        return memberTagRelationMapper.doReplaceInto(memberTagRelation);
    }

}
