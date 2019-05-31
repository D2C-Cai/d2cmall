package com.d2c.order.service;

import com.codingapi.tx.annotation.TxTransaction;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.logger.service.MsgUniteService;
import com.d2c.logger.service.WeixinPushService;
import com.d2c.logger.support.MsgBean;
import com.d2c.logger.support.PushBean;
import com.d2c.logger.third.wechat.WeixinPushEntity;
import com.d2c.member.model.Partner;
import com.d2c.member.service.PartnerService;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.order.dao.PartnerBillMapper;
import com.d2c.order.model.PartnerBill;
import com.d2c.order.query.PartnerBillSearcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("partnerBillService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class PartnerBillServiceImpl extends ListServiceImpl<PartnerBill> implements PartnerBillService {

    @Autowired
    private PartnerBillMapper partnerBillMapper;
    @Autowired
    private PartnerService partnerService;
    @Autowired
    private WeixinPushService weixinPushService;
    @Autowired
    private MsgUniteService msgUniteService;

    @Override
    public PartnerBill findById(Long id) {
        return this.findOneById(id);
    }

    @Override
    @TxTransaction
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public PartnerBill insert(PartnerBill partnerBill, Partner partner) {
        partnerBill.setPartnerCode(partner.getLoginCode());
        partnerBill.setPartnerLevel(partner.getLevel());
        partnerBill.setExcep(partner.getStatus() == 1 ? 0 : 1);
        partnerBill = this.save(partnerBill);
        if (partnerBill.getId() > 0 && !partnerBill.getBuyerCode().equals(partnerBill.getPartnerCode())) {
            WeixinPushEntity msgObj = new WeixinPushEntity(partner.getOpenId(), "您有一条新的返利订单",
                    partnerBill.getOrderSn(), new Date(), "返利金额：" + partnerBill.getActualAmount()
                    .multiply(partnerBill.getPartnerRatio()).setScale(2, BigDecimal.ROUND_HALF_UP) + "元~",
                    "/pages/member/bill/list");
            weixinPushService.send(msgObj);
            PushBean pushBean = new PushBean(partner.getMemberId(), "您有一条新的返利订单生成，预计收益" + partnerBill.getActualAmount()
                    .multiply(partnerBill.getPartnerRatio()).setScale(2, BigDecimal.ROUND_HALF_UP) + "元~", 82);
            pushBean.setAppUrl("/to/partner/sales");
            MsgBean msgBean = new MsgBean(partner.getMemberId(), 82, "返利单",
                    "您有一条新的返利订单生成，预计收益" + partnerBill.getActualAmount().multiply(partnerBill.getPartnerRatio())
                            .setScale(2, BigDecimal.ROUND_HALF_UP) + "元~");
            msgBean.setAppUrl("/to/partner/sales");
            msgUniteService.sendPush(pushBean, msgBean);
        }
        return partnerBill;
    }

    @Override
    public PageResult<PartnerBill> findBySearcher(PartnerBillSearcher searcher, PageModel page) {
        PageResult<PartnerBill> pager = new PageResult<>(page);
        Integer toltalCount = partnerBillMapper.countBySearcher(searcher);
        List<PartnerBill> list = new ArrayList<>();
        if (toltalCount > 0) {
            list = partnerBillMapper.findBySearcher(searcher, page);
        }
        pager.setTotalCount(toltalCount);
        pager.setList(list);
        return pager;
    }

    @Override
    public Integer countBySearcher(PartnerBillSearcher searcher) {
        return partnerBillMapper.countBySearcher(searcher);
    }

    @Override
    public int updateStatus(Long id, Integer status) {
        return partnerBillMapper.updateStatus(id, status);
    }

    @Override
    public PartnerBill findByOrderItemId(Long orderItemId) {
        return partnerBillMapper.findByOrderItemId(orderItemId);
    }

    @Override
    @TxTransaction
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doClose(Long orderItemId) {
        PartnerBill partnerBill = partnerBillMapper.findByOrderItemId(orderItemId);
        int success = partnerBillMapper.doClose(orderItemId);
        if (success > 0) {
            Partner partner = partnerService.findById(partnerBill.getPartnerId());
            WeixinPushEntity msgObj = new WeixinPushEntity(partner.getOpenId(), "您有一条返利订单已关闭",
                    partnerBill.getOrderSn(), new Date(), "返利金额：" + partnerBill.getActualAmount()
                    .multiply(partnerBill.getPartnerRatio()).setScale(2, BigDecimal.ROUND_HALF_UP) + "元",
                    "/pages/member/bill/list");
            weixinPushService.send(msgObj);
            PushBean pushBean = new PushBean(partner.getMemberId(), "您有一条返利订单已被取消，将损失" + partnerBill.getActualAmount()
                    .multiply(partnerBill.getPartnerRatio()).setScale(2, BigDecimal.ROUND_HALF_UP) + "元", 82);
            pushBean.setAppUrl("/to/partner/sales");
            MsgBean msgBean = new MsgBean(partner.getMemberId(), 82, "返利单",
                    "您有一条返利订单已被取消，将损失" + partnerBill.getActualAmount().multiply(partnerBill.getPartnerRatio())
                            .setScale(2, BigDecimal.ROUND_HALF_UP) + "元");
            msgBean.setAppUrl("/to/partner/sales");
            msgUniteService.sendPush(pushBean, msgBean);
        }
        return success;
    }

    @Override
    public Map<String, Object> findPartnerSummaryToday(Long partnerId, Date beginDate, Date endDate, Integer[] status) {
        return partnerBillMapper.findPartnerSummaryToday(partnerId, beginDate, endDate, status);
    }

    @Override
    public Map<String, Object> findChildrenSummaryToday(Long partnerId, Date beginDate, Date endDate,
                                                        Integer[] status) {
        return partnerBillMapper.findChildrenSummaryToday(partnerId, beginDate, endDate, status);
    }

    @Override
    public List<Map<String, Object>> findBillSummary(Long id, String rid, String ratio) {
        return partnerBillMapper.findBillSummary(id, rid, ratio);
    }

    @Override
    public List<Map<String, Object>> findCountGroupByStatus(Date beginDate, Date endDate) {
        return partnerBillMapper.findCountGroupByStatus(beginDate, endDate);
    }

    @Override
    public List<Map<String, Object>> findCountGroupByLevel(Date beginDate, Date endDate) {
        return partnerBillMapper.findCountGroupByLevel(beginDate, endDate);
    }

}
