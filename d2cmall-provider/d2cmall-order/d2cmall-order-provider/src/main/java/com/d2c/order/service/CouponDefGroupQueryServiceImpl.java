package com.d2c.order.service;

import com.d2c.common.api.dto.HelpDTO;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.order.dao.CouponDefGroupMapper;
import com.d2c.order.dao.CouponGroupMapper;
import com.d2c.order.model.CouponDefGroup;
import com.d2c.order.query.CouponDefGroupSearcher;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("couponDefGroupQueryService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class CouponDefGroupQueryServiceImpl extends ListServiceImpl<CouponDefGroup>
        implements CouponDefGroupQueryService {

    @Autowired
    private CouponDefGroupMapper couponDefGroupMapper;
    @Autowired
    private CouponGroupMapper couponGroupMapper;

    @Override
    @Cacheable(value = "coupon_def_group", key = "'coupon_def_group_'+#id", unless = "#result == null")
    public CouponDefGroup findById(Long id) {
        CouponDefGroup group = couponDefGroupMapper.selectByPrimaryKey(id);
        return group;
    }

    @Override
    public CouponDefGroup findByCode(String code) {
        CouponDefGroup group = couponDefGroupMapper.findByCode(code);
        return group;
    }

    @Override
    public CouponDefGroup findByCipher(String password) {
        CouponDefGroup group = couponDefGroupMapper.findByCipher(password);
        return group;
    }

    @Override
    public PageResult<Long> findByUpdateClaimed(Date modifyDate, PageModel page) {
        PageResult<Long> pager = new PageResult<>(page);
        int totalCount = couponDefGroupMapper.countByUpdateClaimed(modifyDate);
        if (totalCount > 0) {
            List<Long> list = couponDefGroupMapper.findByUpdateClaimed(modifyDate, page);
            pager.setTotalCount(totalCount);
            pager.setList(list);
        }
        return pager;
    }

    @Override
    public PageResult<CouponDefGroup> findBySearch(CouponDefGroupSearcher searcher, PageModel page) {
        PageResult<CouponDefGroup> pager = new PageResult<>(page);
        int totalCount = couponDefGroupMapper.countBySearch(searcher);
        if (totalCount > 0) {
            List<CouponDefGroup> list = couponDefGroupMapper.findBySearch(searcher, page);
            pager.setList(list);
        }
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    public int countBySearch(CouponDefGroupSearcher searcher) {
        int totalCount = couponDefGroupMapper.countBySearch(searcher);
        return totalCount;
    }

    @Override
    public List<HelpDTO> findHelpDtosBySearch(CouponDefGroupSearcher searcher, PageModel page) {
        List<HelpDTO> helpDtos = new ArrayList<>();
        int totalCount = couponDefGroupMapper.countBySearch(searcher);
        if (totalCount > 0) {
            List<CouponDefGroup> list = couponDefGroupMapper.findBySearch(searcher, page);
            if (list != null && !list.isEmpty()) {
                for (CouponDefGroup group : list) {
                    HelpDTO dto = new HelpDTO(group);
                    dto.setId(group.getId());
                    dto.setName(group.getName());
                    helpDtos.add(dto);
                }
            }
        }
        return helpDtos;
    }

    @Override
    @CachePut(value = "coupon_def_group", key = "'coupon_def_group_'+#dto.id", unless = "#result == null")
    public CouponDefGroup updateCache(CouponDefGroup dto) {
        return dto;
    }

    @Override
    @CacheEvict(value = "coupon_def_group", key = "'coupon_def_group_'+#id")
    public void clearCache(Long id) {
    }

    /**
     * 是否超过个人限领量
     *
     * @param couponDef
     * @param memberId
     * @return
     */
    @Override
    public boolean getCheckClaimLimit(CouponDefGroup group, Long memberInfoId, String loginCode) {
        boolean result = true;
        int limit = 0;
        if (memberInfoId != null && memberInfoId > 0) {
            limit = couponGroupMapper.countByMemberIdAndGroupId(memberInfoId, group.getId());
        } else if (!StringUtils.isEmpty(loginCode)) {
            limit = couponGroupMapper.countByLoginCodeAndGroupId(loginCode, group.getId());
        }
        if (limit >= group.getClaimLimit()) {
            result = false;
        }
        return result;
    }

}
