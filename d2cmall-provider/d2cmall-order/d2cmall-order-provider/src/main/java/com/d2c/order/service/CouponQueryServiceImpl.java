package com.d2c.order.service;

import com.d2c.common.api.dto.HelpDTO;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.order.dao.CouponMapper;
import com.d2c.order.dto.CouponDto;
import com.d2c.order.model.Coupon;
import com.d2c.order.model.Coupon.CouponStatus;
import com.d2c.order.query.CouponSearcher;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service("couponQueryService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class CouponQueryServiceImpl extends ListServiceImpl<Coupon> implements CouponQueryService {

    @Autowired
    private CouponMapper couponMapper;

    @Override
    public Coupon findById(Long id) {
        return this.couponMapper.selectByPrimaryKey(id);
    }

    @Override
    public Coupon findByCode(String code, Long memberId) {
        return couponMapper.findCouponByCode(code, memberId);
    }

    @Override
    public Coupon findByOrderId(Long orderId, CouponStatus status) {
        return couponMapper.findByOrderIdAndStatus(orderId, status);
    }

    @Override
    public PageResult<Coupon> findByMemberId(Long memberId, String[] status, PageModel page) {
        PageResult<Coupon> pager = new PageResult<>(page);
        Date expireDateStart = null;
        Date expireDateEnd = null;
        for (int i = 0; status != null && i < status.length; i++) {
            if (status[i].equals(Coupon.CouponStatus.CLAIMED.toString())) {
                // 可使用
                Date currentDate = new Date();
                expireDateStart = currentDate;
            } else if (status[i].equals(Coupon.CouponStatus.INVALID.toString())) {
                // 已过期
                Date currentDate = new Date();
                expireDateEnd = currentDate;
            }
        }
        int totalCount = couponMapper.countByMemberId(memberId, status, expireDateStart, expireDateEnd);
        List<Coupon> list = new ArrayList<>();
        if (totalCount > 0) {
            list = couponMapper.findByMemberId(memberId, status, expireDateStart, expireDateEnd, page);
        }
        pager.setList(list);
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    public int countByMemberId(Long memberId, String[] status) {
        Date expireDateStart = null;
        Date expireDateEnd = null;
        for (int i = 0; status != null && i < status.length; i++) {
            if (status[i].equals(Coupon.CouponStatus.CLAIMED.toString())) {
                // 可使用
                Date currentDate = new Date();
                expireDateStart = currentDate;
            } else if (status[i].equals(Coupon.CouponStatus.INVALID.toString())) {
                // 已过期
                Date currentDate = new Date();
                expireDateEnd = currentDate;
            }
        }
        int totalCount = couponMapper.countByMemberId(memberId, status, expireDateStart, expireDateEnd);
        return totalCount;
    }

    @Override
    public PageResult<Coupon> findMyUnusedCoupons(Long memberInfoId, BigDecimal totalAmount) {
        PageResult<Coupon> pager = this.findByMemberId(memberInfoId, new String[]{CouponStatus.CLAIMED.name()},
                new PageModel(1, 100));
        return pager;
    }

    @Override
    public List<Map<String, Object>> findExpireCoupon(int days) {
        return couponMapper.findExpireCoupon(days);
    }

    @Override
    public PageResult<CouponDto> findBySearcher(CouponSearcher searcher, PageModel page) {
        PageResult<CouponDto> pager = new PageResult<>(page);
        Integer totalCount = couponMapper.countBySearcher(searcher);
        List<Coupon> list = new ArrayList<>();
        List<CouponDto> dtos = new ArrayList<>();
        if (totalCount > 0) {
            list = couponMapper.findBySearcher(searcher, page);
            for (Coupon coupon : list) {
                CouponDto dto = new CouponDto();
                BeanUtils.copyProperties(coupon, dto);
                dtos.add(dto);
            }
        }
        pager.setList(dtos);
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    public int countBySearcher(CouponSearcher searcher) {
        return couponMapper.countBySearcher(searcher);
    }

    @Override
    public List<HelpDTO> findHelpDto(CouponSearcher searcher, PageModel page) {
        List<HelpDTO> dtos = new ArrayList<>();
        Integer totalCount = couponMapper.countBySearcher(searcher);
        List<Coupon> list = null;
        if (totalCount > 0) {
            list = couponMapper.findBySearcher(searcher, page);
            for (Coupon coupon : list) {
                HelpDTO dto = new HelpDTO();
                dto.setId(coupon.getId());
                dto.setName(coupon.getCode());
                dtos.add(dto);
            }
        }
        return dtos;
    }

    @Override
    public int countByWxCodeAndDefine(String wxCode, Long defineId) {
        return couponMapper.countByWxCodeAndDefine(wxCode, defineId);
    }

    @Override
    public Map<String, Object> countGroupByStatus(Long memberInfoId) {
        Map<String, Object> map = new HashMap<>();
        List<Map<String, Object>> counts = couponMapper.countGropByStatus(memberInfoId);
        for (Map<String, Object> count : counts) {
            String status = count.get("status").toString();
            switch (status) {
                case "USED":
                    map.put("USED", count.get("counts"));
                    break;
                case "LOCKED":
                    map.put("LOCKED", count.get("counts"));
                    break;
                case "CLAIMED":
                    map.put("CLAIMED", count.get("counts"));
                    break;
                case "INVALID":
                    map.put("INVALID", count.get("counts"));
                    break;
                case "UNCLAIMED":
                    map.put("UNCLAIMED", count.get("counts"));
                    break;
            }
        }
        return map;
    }

}
