package com.d2c.member.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.common.mq.enums.MqEnum;
import com.d2c.member.dao.MemberCollectionMapper;
import com.d2c.member.dto.MemberCollectionDto;
import com.d2c.member.enums.MemberTaskEnum;
import com.d2c.member.model.MemberCollection;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.mongo.services.MemberTaskExecService;
import com.d2c.member.query.InterestSearcher;
import com.d2c.member.search.model.SearcherMemberCollection;
import com.d2c.member.search.service.MemberCollectionSearcherService;
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

@Service("memberCollectionService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class MemberCollectionServiceImpl extends ListServiceImpl<MemberCollection> implements MemberCollectionService {

    @Autowired
    private MemberCollectionMapper memberCollectionMapper;
    @Autowired
    private MemberInfoService memberInfoService;
    @Reference
    private MemberCollectionSearcherService memberCollectionSearcherService;
    @Reference
    private MemberTaskExecService memberTaskExecService;

    public PageResult<MemberCollectionDto> findBySearch(@Param("searcher") InterestSearcher searcher,
                                                        @Param("page") PageModel page) {
        PageResult<MemberCollectionDto> pager = new PageResult<MemberCollectionDto>();
        Integer totalCount = memberCollectionMapper.countBySearch(searcher);
        if (totalCount > 0) {
            List<MemberCollection> list = memberCollectionMapper.findBySearch(searcher, page);
            List<MemberCollectionDto> dtos = new ArrayList<MemberCollectionDto>();
            if (list != null && !list.isEmpty()) {
                for (MemberCollection mc : list) {
                    MemberCollectionDto dto = new MemberCollectionDto();
                    BeanUtils.copyProperties(mc, dto);
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
    public MemberCollection insert(MemberCollection memberCollection) {
        memberCollectionMapper.updateColumn();
        try {
            memberCollection = this.save(memberCollection);
        } catch (Exception e) {
            throw new BusinessException("您已经收藏过了！");
        }
        if (memberCollection != null) {
            // 加入索引
            SearcherMemberCollection memberCollectionSearch = new SearcherMemberCollection();
            BeanUtils.copyProperties(memberCollection, memberCollectionSearch);
            memberCollectionSearcherService.insert(memberCollectionSearch);
            this.productSummaryMQ(memberCollection.getProductId(), 1);
            // 收藏商品任务
            memberTaskExecService.taskDone(memberCollection.getMemberId(), MemberTaskEnum.COLLECTION_ADD);
        }
        return memberCollection;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public int delete(Long memberId, Long productId) {
        int result = memberCollectionMapper.delete(memberId, productId);
        if (result > 0) {
            // 删除索引
            memberCollectionSearcherService.remove(memberId, productId);
            this.productSummaryMQ(productId, -1);
        }
        return result;
    }

    private void productSummaryMQ(Long productId, int count) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", productId);
        map.put("count", count);
        map.put("type", "collection");
        MqEnum.PRODUCT_SUMMARY.send(map);
    }

    public int countByMemberId(Long memberId) {
        return memberCollectionMapper.countByMemberId(memberId);
    }

    public PageResult<MemberCollection> findByMemberId(Long memberId, PageModel page) {
        PageResult<MemberCollection> pager = new PageResult<MemberCollection>(page);
        int totalCount = memberCollectionMapper.countByMemberId(memberId);
        if (totalCount > 0) {
            List<MemberCollection> list = memberCollectionMapper.findByMemberId(memberId, page);
            pager.setTotalCount(totalCount);
            pager.setList(list);
        }
        return pager;
    }

    public int countByProductId(Long productId) {
        return memberCollectionMapper.countByProductId(productId);
    }

    public MemberCollection findByMemberAndProductId(Long memberId, Long productId) {
        return memberCollectionMapper.findByMemberAndProductId(memberId, productId);
    }

    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public int doMerge(Long memberSourceId, Long memberTargetId) {
        return memberCollectionMapper.doMerge(memberSourceId, memberTargetId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public void doRefreshHeadPic(Long memberInfoId, String headPic, String nickName) {
        int success = memberCollectionMapper.doRefreshHeadPic(memberInfoId, headPic, nickName);
        if (success > 0) {
            memberCollectionSearcherService.doRefreshHeadPic(memberInfoId, headPic, nickName);
        }
    }

}
