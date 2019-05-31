package com.d2c.order.service;

import com.codingapi.tx.annotation.TxTransaction;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.order.dao.CouponOrderMapper;
import com.d2c.order.dto.CouponOrderDto;
import com.d2c.order.enums.CouponSourceEnum;
import com.d2c.order.model.CouponOrder;
import com.d2c.order.model.CouponOrder.CouponOrderStatus;
import com.d2c.order.query.CouponOrderSearcher;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service("couponOrderService")
@Transactional(rollbackFor = Exception.class, readOnly = true, propagation = Propagation.SUPPORTS)
public class CouponOrderServiceImpl extends ListServiceImpl<CouponOrder> implements CouponOrderService {

    @Autowired
    private CouponOrderMapper couponOrderMapper;
    @Autowired
    private CouponDefService couponDefService;

    @Override
    public CouponOrder findBySn(String sn) {
        return couponOrderMapper.findBySn(sn);
    }

    @Override
    public CouponOrderDto findByIdAndMemberId(Long id, Long memberId) {
        CouponOrder order = couponOrderMapper.findByIdAndMemberId(id, memberId);
        if (order == null) {
            return null;
        }
        CouponOrderDto dto = new CouponOrderDto();
        BeanUtils.copyProperties(order, dto);
        return dto;
    }

    @Override
    public CouponOrderDto findBySnAndMemberId(String sn, Long memberId) {
        CouponOrder order = couponOrderMapper.findBySnAndMemberId(sn, memberId);
        if (order == null) {
            return null;
        }
        CouponOrderDto dto = new CouponOrderDto();
        BeanUtils.copyProperties(order, dto);
        return dto;
    }

    @Override
    public CouponOrder findByMemberIdAndCouponDefId(Long memberId, Long couponDefId) {
        return couponOrderMapper.findByMemberIdAndCouponDefId(memberId, couponDefId);
    }

    @Override
    public PageResult<CouponOrder> findBySearcher(CouponOrderSearcher searcher, PageModel page) {
        PageResult<CouponOrder> pager = new PageResult<>(page);
        int totalCount = couponOrderMapper.countBySearch(searcher);
        if (totalCount > 0) {
            List<CouponOrder> list = couponOrderMapper.findBySearch(searcher, page);
            pager.setList(list);
        }
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    public int countBySearcher(CouponOrderSearcher searcher) {
        int totalCount = couponOrderMapper.countBySearch(searcher);
        return totalCount;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public CouponOrder insert(CouponOrder couponOrder) {
        return this.save(couponOrder);
    }

    @Override
    @TxTransaction
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doPaySuccess(Long paymentId, String paySn, Integer paymentType, String orderSn, BigDecimal payedAmount) {
        CouponOrder order = couponOrderMapper.findBySn(orderSn);
        if (order == null) {
            logger.error("[CouponOrder] 优惠券单：" + orderSn + "不存在！");
            return 0;
        }
        if (order.getOrderStatus().intValue() > CouponOrderStatus.INIT.getCode()) {
            logger.error("[CouponOrder] 优惠券单：" + orderSn + "已经支付成功了！");
            return 0;
        }
        int success = 0;
        if (order.getTotalAmount().intValue() == payedAmount.intValue()) {
            order.setPayAmount(payedAmount);
            order.setPaymentId(paymentId);
            order.setPaymentType(paymentType);
            order.setPaySn(paySn);
            success = couponOrderMapper.updatePaySuccess(order.getId(), paymentId, paymentType, paySn, payedAmount);
            if (success > 0) {
                couponDefService.doClaimedCoupon4Buy(order.getCouponDefId(), order.getMemberId(), order.getLoginCode(),
                        CouponSourceEnum.PURCHASE.name(), true);
            }
        }
        return success;
    }

}
