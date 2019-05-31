package com.d2c.order.service;

import com.codingapi.tx.annotation.TxTransaction;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.logger.model.CollageOrderLog;
import com.d2c.logger.model.CollageOrderLog.CollageLogType;
import com.d2c.logger.model.SmsLog.SmsLogType;
import com.d2c.logger.service.CollageOrderLogService;
import com.d2c.logger.service.MsgUniteService;
import com.d2c.logger.support.MsgBean;
import com.d2c.logger.support.SmsBean;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.order.dao.CollageOrderMapper;
import com.d2c.order.dto.CollageOrderDto;
import com.d2c.order.enums.PaymentTypeEnum;
import com.d2c.order.model.CollageGroup;
import com.d2c.order.model.CollageOrder;
import com.d2c.order.model.CollageOrder.CollageOrderStatus;
import com.d2c.order.model.Setting;
import com.d2c.order.query.CollageOrderSearcher;
import com.d2c.product.model.CollagePromotion;
import com.d2c.product.service.CollagePromotionService;
import com.d2c.util.date.DateUtil;
import com.d2c.util.string.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service(value = "collageOrderService")
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class CollageOrderServiceImpl extends ListServiceImpl<CollageOrder> implements CollageOrderService {

    @Autowired
    private CollageOrderMapper collageOrderMapper;
    @Autowired
    private CollageGroupService collageGroupService;
    @Autowired
    private CollageOrderLogService collageOrderLogService;
    @Autowired
    private CollagePromotionService collagePromotionService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private MsgUniteService msgUniteService;
    @Autowired
    private SettingService settingService;
    @Autowired
    private PaymentService paymentService;

    @Override
    public CollageOrder findById(Long id) {
        return this.findOneById(id);
    }

    @Override
    public CollageOrderDto findDtoById(Long id) {
        CollageOrderDto dto = new CollageOrderDto();
        CollageOrder order = this.findOneById(id);
        BeanUtils.copyProperties(order, dto);
        Setting setting = settingService.findByCode(Setting.COLLAGECLOSECODE);
        int secondTime = Integer.parseInt(Setting.defaultValue(setting, 5 * 60).toString());
        dto.setEndTime(DateUtil.add(dto.getCreateDate(), Calendar.SECOND, secondTime));
        return dto;
    }

    @Override
    public CollageOrder findBySn(String sn) {
        return collageOrderMapper.findBySn(sn);
    }

    @Override
    public CollageOrder findOneByGroupId(Long id) {
        return collageOrderMapper.findOneByGroupId(id);
    }

    @Override
    @TxTransaction
    public List<CollageOrder> findByGroupId(Long id) {
        return collageOrderMapper.findByGroupId(id);
    }

    @Override
    public PageResult<CollageOrderDto> findBySearch(CollageOrderSearcher searcher, PageModel page) {
        PageResult<CollageOrderDto> pager = new PageResult<>(page);
        int totalCount = collageOrderMapper.countBySearch(searcher);
        if (totalCount > 0) {
            List<CollageOrder> list = collageOrderMapper.findBySearch(searcher, page);
            List<CollageOrderDto> dtos = new ArrayList<>();
            for (CollageOrder order : list) {
                CollageOrderDto dto = new CollageOrderDto();
                BeanUtils.copyProperties(order, dto);
                CollageGroup group = collageGroupService.findById(order.getGroupId());
                dto.setCollageGroup(group);
                dtos.add(dto);
            }
            pager.setList(dtos);
        }
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    public int countBySearch(CollageOrderSearcher searcher) {
        return collageOrderMapper.countBySearch(searcher);
    }

    @Override
    public PageResult<CollageOrderDto> findMyCollage(PageModel page, CollageOrderSearcher searcher) {
        PageResult<CollageOrderDto> pager = new PageResult<>(page);
        int totalCount = collageOrderMapper.countBySearch(searcher);
        if (totalCount > 0) {
            Setting setting = settingService.findByCode(Setting.COLLAGECLOSECODE);
            int secondTime = Integer.parseInt(Setting.defaultValue(setting, 5 * 60).toString());
            List<CollageOrder> list = collageOrderMapper.findBySearch(searcher, page);
            List<CollageOrderDto> dtos = new ArrayList<>();
            for (CollageOrder order : list) {
                CollageOrderDto dto = new CollageOrderDto();
                BeanUtils.copyProperties(order, dto);
                CollageGroup group = collageGroupService.findById(order.getGroupId());
                dto.setCollageGroup(group);
                dto.setEndTime(DateUtil.add(dto.getCreateDate(), Calendar.SECOND, secondTime));
                dtos.add(dto);
            }
            pager.setList(dtos);
        }
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    public int countExpirePayOrder(Date deadline) {
        CollageOrderSearcher searcher = new CollageOrderSearcher();
        searcher.setStatus(1);
        searcher.setEndCreateDate(deadline);
        return collageOrderMapper.countBySearch(searcher);
    }

    @Override
    public PageResult<CollageOrder> findExpirePayOrder(Date deadline, PageModel page) {
        PageResult<CollageOrder> pager = new PageResult<>(page);
        CollageOrderSearcher searcher = new CollageOrderSearcher();
        searcher.setStatus(1);
        searcher.setEndCreateDate(deadline);
        int totalCount = collageOrderMapper.countBySearch(searcher);
        if (totalCount > 0) {
            List<CollageOrder> list = collageOrderMapper.findBySearch(searcher, page);
            pager.setList(list);
        }
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    @TxTransaction
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public CollageOrder insert(CollageOrder collageOrder) {
        return this.save(collageOrder);
    }

    @Override
    @TxTransaction
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int doPaySuccess(String orderSn, Long paymentId, String paySn, Integer paymentType, BigDecimal payAmount,
                            BigDecimal cashAmount, BigDecimal giftAmount) {
        CollageOrder collageOrder = collageOrderMapper.findBySn(orderSn);
        if (collageOrder == null) {
            logger.error("[CollageOrder] 拼团单：" + orderSn + "不存在！");
            return 0;
        }
        if (collageOrder.getStatus().intValue() > CollageOrderStatus.WAITFORPAY.getCode()) {
            logger.error("[CollageOrder] 拼团单：" + orderSn + "已经支付成功了！");
            return 0;
        }
        if (collageOrder.getStatus().equals(CollageOrderStatus.CLOSE.getCode())) {
            this.doCloseRefund(collageOrder.getId(), paymentId, paySn, paymentType, payAmount);
            return 0;
        }
        int success = 0;
        String logInfo = "";
        if (collageOrder.getPaidAmount().intValue() == payAmount.intValue()) {
            success = collageOrderMapper.doPaySuccess(paymentId, paySn, paymentType, orderSn, payAmount, cashAmount,
                    giftAmount);
            if (success > 0) {
                collageOrder.setPaymentId(paymentId);
                collageOrder.setPaySn(paySn);
                collageOrder.setPaymentType(paymentType);
                collageOrder.setPaymentAmount(payAmount);
                collageOrder.setCashAmount(cashAmount);
                collageOrder.setGiftAmount(giftAmount);
                orderService.doSimulateOrder(collageOrder);
                int result = collageGroupService.updatePayCount(collageOrder.getGroupId(), 1);
                if (result > 0) {
                    CollageGroup group = collageGroupService.findById(collageOrder.getGroupId());
                    if (group != null && group.getCurrentMemberCount().equals(group.getMemberCount())
                            && group.getMemberCount().equals(group.getPayMemberCount())) {
                        // 满团且全支付
                        collageGroupService.doSuccess(group);
                    }
                    logInfo = "支付成功，流水号" + paySn + "，支付金额：" + payAmount + "，支付方式："
                            + PaymentTypeEnum.getByCode(paymentType).getDisplay();
                } else {
                    logInfo = "更新拼团团队支付人数失败！";
                }
            } else {
                logInfo = "更新拼团单状态失败！";
            }
        } else {
            logInfo = "拼团单金额和实付金额不一致！";
        }
        if (StringUtil.isNotBlank(logInfo)) {
            CollageOrderLog log = new CollageOrderLog(collageOrder.getId(), CollageLogType.PAY.name(), logInfo, "sys");
            collageOrderLogService.insert(log);
        }
        return success;
    }

    @Override
    @TxTransaction
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int doClose(Long id, String closeMemo) {
        return collageOrderMapper.doClose(id, closeMemo);
    }

    @Override
    @TxTransaction
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int doRefund(Long id) {
        int success = collageOrderMapper.doRefund(id);
        if (success > 0) {
            CollageOrder order = collageOrderMapper.selectByPrimaryKey(id);
            paymentService.doThirdRefund(id, order.getSn(), order.getPaymentId(), order.getPaySn(),
                    order.getPaymentType(), order.getPaidAmount());
        }
        return success;
    }

    @Override
    @TxTransaction
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int doCloseRefund(Long id, Long paymentId, String paySn, Integer paymentType, BigDecimal payAmount) {
        int success = collageOrderMapper.doCloseRefund(id, paymentId, paySn, paymentType, payAmount);
        if (success > 0) {
            CollageOrderLog log = new CollageOrderLog(id, CollageLogType.WAITFORREFUND.name(), "拼团失败，待退款", "sys");
            collageOrderLogService.insert(log);
            CollageOrder order = this.findById(id);
            CollagePromotion promotion = collagePromotionService.findById(order.getPromotionId());
            String content = "很遗憾，您参与的拼团活动 '" + promotion.getName()
                    + "' 拼团失败了，系统将按照原路尽快给你退款，可到D2C全球好设计小程序或D2C APP中试试其他商品~";
            MsgBean msgBean = new MsgBean(order.getMemberId(), 61, "很遗憾，您参与的拼团活动失败了", content);
            msgBean.setAppUrl("/mycollage/list");
            msgBean.setPic(order.getProductImage());
            SmsBean smsBean = new SmsBean(null, order.getLoginCode(), SmsLogType.REMIND, content);
            msgUniteService.sendMsg(smsBean, null, msgBean, null);
            paymentService.doThirdRefund(id, order.getSn(), paymentId, paySn, paymentType, payAmount);
        }
        return success;
    }

    @Override
    @TxTransaction
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int doSuccess(Long id) {
        int success = collageOrderMapper.doSuccess(id);
        if (success > 0) {
            CollageOrderLog log = new CollageOrderLog(id, CollageLogType.SUCCESS.name(), "拼团成功", "sys");
            collageOrderLogService.insert(log);
        }
        return success;
    }

    @Override
    @TxTransaction
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int doRefundSuccess(Long id, String operator, String refundMemo, Integer refundPaymentType,
                               String refundPaySn) {
        int success = collageOrderMapper.doRefundSuccess(id, operator, refundMemo, refundPaymentType, refundPaySn);
        if (success > 0) {
            CollageOrderLog log = new CollageOrderLog(id, CollageLogType.REFUND.name(),
                    "退款成功，退款流水号：" + refundPaySn + "" + "," + refundMemo, operator);
            collageOrderLogService.insert(log);
        }
        return success;
    }

    @Override
    public CollageOrder findExistProcess(Long promotionId, Long memberId) {
        return collageOrderMapper.findExistProcess(promotionId, memberId);
    }

    @Override
    public CollageOrder findByMemberIdAndGroupId(Long memberId, Long groupId) {
        return collageOrderMapper.findByMemberIdAndGroupId(memberId, groupId);
    }

    @Override
    public CollageOrderDto findBySnAndMemberId(String sn, Long memberId) {
        CollageOrderDto dto = new CollageOrderDto();
        CollageOrder order = collageOrderMapper.findBySnAndMemberId(sn, memberId);
        BeanUtils.copyProperties(order, dto);
        return dto;
    }

    @Override
    public CollageOrderDto findByIdAndMemberId(Long id, Long memberId) {
        CollageOrderDto dto = new CollageOrderDto();
        CollageOrder order = collageOrderMapper.findByIdAndMemberId(id, memberId);
        BeanUtils.copyProperties(order, dto);
        return dto;
    }

    @Override
    public CollageOrder findUnpayByPromotion(Long promotionId, Long memberId) {
        return collageOrderMapper.findUnpayByPromotion(promotionId, memberId);
    }

    @Override
    @TxTransaction
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updateType(Long id, int type) {
        return collageOrderMapper.updateType(id, type);
    }

}
