package com.d2c.order.service;

import com.d2c.common.api.dto.HelpDTO;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.order.dao.CouponDefMapper;
import com.d2c.order.model.CouponDef;
import com.d2c.order.model.CouponDef.CouponType;
import com.d2c.order.model.CouponDefGroup;
import com.d2c.order.query.CouponDefSearcher;
import com.d2c.order.query.CouponSearcher;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service(value = "couponDefQueryService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class CouponDefQueryServiceImpl extends ListServiceImpl<CouponDef> implements CouponDefQueryService {

    @Autowired
    private CouponDefMapper couponDefMapper;
    @Autowired
    private CouponDefGroupQueryService couponDefGroupQueryService;
    @Autowired
    private CouponQueryService couponQueryService;

    @Override
    @Cacheable(value = "coupon_def", key = "'coupon_def_'+#id", unless = "#result == null")
    public CouponDef findById(Long id) {
        return couponDefMapper.selectByPrimaryKey(id);
    }

    @Override
    public CouponDef findByCode(String code) {
        return couponDefMapper.findByCode(code);
    }

    @Override
    public CouponDef findByCipher(String cipher) {
        return couponDefMapper.findByCipher(cipher);
    }

    @Override
    public CouponDef findByName(String name) {
        return couponDefMapper.findByName(name);
    }

    @Override
    public List<CouponDef> findByProductId(Long productId) {
        return couponDefMapper.findByProductId(productId);
    }

    @Override
    public CouponDef findByWxCardId(String wxCardId) {
        return couponDefMapper.findByWxCardId(wxCardId);
    }

    @Override
    public PageResult<HelpDTO> findHelpDto(CouponDefSearcher couponDefSearcher, PageModel page) {
        PageResult<HelpDTO> pager = new PageResult<>(page);
        int totalCount = couponDefMapper.countBySearcher(couponDefSearcher);
        if (totalCount > 0) {
            List<CouponDef> couponDefs = couponDefMapper.findBySearcher(page, couponDefSearcher);
            List<HelpDTO> dtos = new ArrayList<>();
            for (CouponDef couponDef : couponDefs) {
                HelpDTO dto = new HelpDTO();
                BeanUtils.copyProperties(couponDef, dto);
                dtos.add(dto);
            }
            pager.setTotalCount(totalCount);
            pager.setList(dtos);
        }
        return pager;
    }

    @Override
    public PageResult<CouponDef> findBySearcher(PageModel page, CouponDefSearcher searcher) {
        if (searcher.getGroupId() != null) {
            CouponDefGroup group = couponDefGroupQueryService.findById(searcher.getGroupId());
            List<Long> list = new ArrayList<>();
            if (group != null) {
                if (group.getFixDefIds() != null && group.getFixDefIds().length() > 0) {
                    String[] fixs = group.getFixDefIds().split(",");
                    for (String fix : fixs) {
                        list.add(Long.parseLong(fix));
                    }
                }
                if (group.getRandomDefIds() != null && group.getRandomDefIds().length() > 0) {
                    String[] rands = group.getRandomDefIds().split(",");
                    for (String rand : rands) {
                        list.add(Long.parseLong(rand));
                    }
                }
                if (list.isEmpty()) {
                    list.add(0L);
                }
            }
            searcher.setCouponDefIds(list.toArray(new Long[0]));
        }
        PageResult<CouponDef> pager = new PageResult<>(page);
        Integer totalCount = couponDefMapper.countBySearcher(searcher);
        List<CouponDef> list = new ArrayList<>();
        if (totalCount > 0) {
            list = couponDefMapper.findBySearcher(page, searcher);
        }
        pager.setList(list);
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    public PageResult<Long> findByUpdateClaimed(Date modifyDate, PageModel page) {
        PageResult<Long> pager = new PageResult<>(page);
        int totalCount = couponDefMapper.countByUpdateClaimed(modifyDate);
        if (totalCount > 0) {
            List<Long> list = couponDefMapper.findByUpdateClaimed(modifyDate, page);
            pager.setTotalCount(totalCount);
            pager.setList(list);
        }
        return pager;
    }

    /**
     * 是否超过总发行量
     *
     * @param couponDef
     * @param memberId
     * @return
     */
    @Override
    public boolean checkQuantity(CouponDef couponDef, int num) {
        boolean result = true;
        CouponSearcher searcher = new CouponSearcher();
        searcher.setDefineId(couponDef.getId());
        int exist = couponQueryService.countBySearcher(searcher);
        int all = couponDef.getQuantity();
        if (all > 0) {
            if (num > all - exist) {
                result = false;
            }
        }
        if (couponDef.getType().equals(CouponType.PASSWORD.toString())) {
            if (all <= 0) {
                result = false;
            }
        }
        return result;
    }

    /**
     * 是否超过个人限领量
     *
     * @param couponDef
     * @param memberId
     * @return
     */
    @Override
    public boolean checkPersonalLimit(Long couponDefId, int claimLimit, Long memberInfoId, String loginCode) {
        boolean result = true;
        CouponSearcher searcher = new CouponSearcher();
        searcher.setDefineId(couponDefId);
        if (memberInfoId != null && memberInfoId > 0) {
            searcher.setMemberId(memberInfoId);
        } else {
            searcher.setLoginCode(loginCode);
        }
        int claimed = couponQueryService.countBySearcher(searcher);
        if (claimed >= claimLimit) {
            result = false;
        }
        return result;
    }

    @Override
    public List<CouponDef> findAvailableCoupons() {
        return couponDefMapper.findAvailableCoupons();
    }

    @Override
    @CacheEvict(value = "coupon_def", key = "'coupon_def_'+#couponDefId")
    public void clearCache(Long couponDefId) {
    }

    @Override
    public List<CouponDef> findAvailableByBrandId(Long brandId, Integer free) {
        return couponDefMapper.findAvailableByBrandId(brandId, free);
    }

}
