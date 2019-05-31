package com.d2c.order.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.order.dao.PresentOrderMapper;
import com.d2c.order.model.PresentOrder;
import com.d2c.order.query.PresentOrderSearcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service("presentOrderService")
@Transactional(rollbackFor = Exception.class, readOnly = true, propagation = Propagation.SUPPORTS)
public class PresentOrderServiceImpl extends ListServiceImpl<PresentOrder> implements PresentOrderService {

    @Autowired
    private PresentOrderMapper presentOrderMapper;

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public PresentOrder createPresentOrder(PresentOrder presentOrder) {
        presentOrder = this.save(presentOrder);
        return presentOrder;
    }

    public PresentOrder findBySn(String sn) {
        return presentOrderMapper.findBySn(sn);
    }
    // @Override
    // @Transactional(rollbackFor = Exception.class, readOnly = false,
    // propagation = Propagation.REQUIRED)
    // public int doPaySuccess(Long paymentId, String paySn, Integer
    // paymentType, String orderSn, BigDecimal payedAmount) {
    // PresentOrder presentOrder = presentOrderMapper.findBySn(orderSn);
    // if (presentOrder == null) {
    // logger.error("[PresentOrder] 礼物单：" + orderSn + "不存在！");
    // return 0;
    // }
    // if (presentOrder.getOrderStatus().intValue() >
    // PresentOrderStatus.INIT.getCode()) {
    // logger.error("[PresentOrder] 礼物单：" + orderSn + "已经支付成功了！");
    // return 0;
    // }
    // int success = 0;
    // if (presentOrder.getTotalAmount().intValue() == payedAmount.intValue()) {
    // presentOrder.setPayAmount(payedAmount);
    // presentOrder.setPaymentId(paymentId);
    // presentOrder.setPaymentType(paymentType);
    // presentOrder.setPaySn(paySn);
    // // 对设计师进行充值
    // Account account =
    // accountService.findByMemberId(presentOrder.getReceiveMemberInfoId());
    // OrderInfo orderInfo = new OrderInfo(BusinessTypeEnum.PRESENT.name(),
    // PayTypeEnum.GIVE.name());
    // BeanUtils.copyProperties(presentOrder, orderInfo, "memo");
    // orderInfo.setAccountId(account.getId());
    // orderInfo.setMemberId(account.getMemberId());
    // AccountItem item = new AccountItem(orderInfo, new Date());
    // item.setCreator(presentOrder.getLoginCode());
    // success = accountItemService.doCashBack(item);
    // if (success > 0) {
    // presentOrderMapper.updatePaySuccess(presentOrder.getId(), paymentId,
    // paymentType, paySn, payedAmount);
    // }
    // }
    // return success;
    // }

    @Override
    public PageResult<PresentOrder> findBySearcher(PresentOrderSearcher searcher, PageModel page) {
        PageResult<PresentOrder> pager = new PageResult<PresentOrder>(page);
        int totalCount = presentOrderMapper.countBySearcher(searcher);
        if (totalCount > 0) {
            List<PresentOrder> list = presentOrderMapper.findBySearcher(searcher, page);
            pager.setList(list);
        }
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    public int countBySeasrcher(PresentOrderSearcher searcher) {
        return presentOrderMapper.countBySearcher(searcher);
    }

    @Override
    public BigDecimal findVirtualPresentAmount(Long memberId) {
        return presentOrderMapper.findVirtualPresentAmount(memberId);
    }

    @Override
    public BigDecimal findActualPresentAmount(Long memberId) {
        return presentOrderMapper.findActualPresentAmount(memberId);
    }

    @Override
    public BigDecimal findVirtualGiveAmount(Long memberId) {
        return presentOrderMapper.findVirtualGiveAmount(memberId);
    }

    @Override
    public BigDecimal findActualGiveAmount(Long memberId) {
        return presentOrderMapper.findActualGiveAmount(memberId);
    }

}
