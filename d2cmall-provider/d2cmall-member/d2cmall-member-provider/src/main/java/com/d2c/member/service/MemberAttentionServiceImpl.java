package com.d2c.member.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.common.mq.enums.MqEnum;
import com.d2c.member.dao.MemberAttentionMapper;
import com.d2c.member.dto.MemberAttentionDto;
import com.d2c.member.enums.MemberTaskEnum;
import com.d2c.member.model.MemberAttention;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.mongo.services.MemberTaskExecService;
import com.d2c.member.query.InterestSearcher;
import com.d2c.member.search.model.SearcherMemberAttention;
import com.d2c.member.search.service.MemberAttentionSearcherService;
import com.d2c.mybatis.service.ListServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("memberAttentionService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class MemberAttentionServiceImpl extends ListServiceImpl<MemberAttention> implements MemberAttentionService {

    @Autowired
    private MemberAttentionMapper memberAttentionMapper;
    @Autowired
    private MemberInfoService memberInfoService;
    @Reference
    private MemberAttentionSearcherService memberAttentionSearcherService;
    @Reference
    private MemberTaskExecService memberTaskExecService;

    public PageResult<MemberAttentionDto> findBySearch(@Param("searcher") InterestSearcher searcher,
                                                       @Param("page") PageModel page) {
        PageResult<MemberAttentionDto> pager = new PageResult<MemberAttentionDto>();
        Integer totalCount = memberAttentionMapper.countBySearch(searcher);
        if (totalCount > 0) {
            List<MemberAttention> list = memberAttentionMapper.findBySearch(searcher, page);
            List<MemberAttentionDto> dtos = new ArrayList<MemberAttentionDto>();
            if (list != null && !list.isEmpty()) {
                for (MemberAttention ma : list) {
                    MemberAttentionDto dto = new MemberAttentionDto();
                    BeanUtils.copyProperties(ma, dto);
                    MemberInfo memberInfo = memberInfoService.findById(dto.getMemberId());
                    dto.setMember(memberInfo);
                    dtos.add(dto);
                }
            }
            pager.setList(dtos);
            pager.setTotalCount(totalCount);
        }
        return pager;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public MemberAttention insert(MemberAttention memberAttention) {
        memberAttentionMapper.updateColumn();
        try {
            memberAttention = this.save(memberAttention);
        } catch (Exception e) {
            throw new BusinessException("您已经关注过了！");
        }
        if (memberAttention != null) {
            this.brandSummaryMQ(memberAttention.getDesignerId(), 1);
            SearcherMemberAttention sa = new SearcherMemberAttention();
            BeanUtils.copyProperties(memberAttention, sa);
            memberAttentionSearcherService.insert(sa);
            // 关注品牌任务
            memberTaskExecService.taskDone(memberAttention.getMemberId(), MemberTaskEnum.BRAND_ATTENTION);
        }
        return memberAttention;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public int delete(Long memberId, Long designerId) {
        int result = memberAttentionMapper.delete(memberId, designerId);
        if (result == 1) {
            this.brandSummaryMQ(designerId, -1);
            memberAttentionSearcherService.remove(memberId, designerId);
        }
        return result;
    }

    private void brandSummaryMQ(Long designerId, int count) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", designerId);
        map.put("count", count);
        map.put("type", "fans");
        MqEnum.BRAND_SUMMARY.send(map);
    }

    public int countByMemberId(Long memberId) {
        return memberAttentionMapper.countByMemberId(memberId);
    }

    public PageResult<MemberAttention> findByMemberId(Long memberId, PageModel page) {
        PageResult<MemberAttention> pager = new PageResult<MemberAttention>(page);
        int totalCount = memberAttentionMapper.countByMemberId(memberId);
        if (totalCount > 0) {
            List<MemberAttention> list = memberAttentionMapper.findByMemberId(memberId, page);
            pager.setTotalCount(totalCount);
            pager.setList(list);
        }
        return pager;
    }

    public PageResult<MemberAttention> findByDesignerId(Long designerId, PageModel page) {
        PageResult<MemberAttention> pager = new PageResult<MemberAttention>(page);
        int totalCount = memberAttentionMapper.countByDesignerId(designerId);
        if (totalCount > 0) {
            List<MemberAttention> list = memberAttentionMapper.findByDesignerId(designerId, page);
            pager.setTotalCount(totalCount);
            pager.setList(list);
        }
        return pager;
    }

    public MemberAttention findByMemberAndDesignerId(Long memberId, Long designerId) {
        return memberAttentionMapper.findByMemberAndDesignerId(memberId, designerId);
    }

    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public int doMerge(Long memberSourceId, Long memberTargetId) {
        return memberAttentionMapper.doMerge(memberSourceId, memberTargetId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public void doRefreshHeadPic(Long memberInfoId, String headPic, String nickName) {
        int success = memberAttentionMapper.doRefreshHeadPic(memberInfoId, headPic, nickName);
        if (success > 0) {
            memberAttentionSearcherService.doRefreshHeadPic(memberInfoId, headPic, nickName);
        }
    }

}
