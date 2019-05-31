package com.d2c.order.service;

import com.codingapi.tx.annotation.TxTransaction;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.logger.model.RefundLog;
import com.d2c.logger.model.RefundLog.RefundLogType;
import com.d2c.logger.service.RefundLogService;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.order.dao.RefundMapper;
import com.d2c.order.dto.RefundDto;
import com.d2c.order.model.CustomerCompensation.CompensationType;
import com.d2c.order.model.OrderItem;
import com.d2c.order.model.OrderItem.ItemStatus;
import com.d2c.order.model.Refund;
import com.d2c.order.model.Refund.RefundStatus;
import com.d2c.order.model.Reship;
import com.d2c.order.query.RefundSearcher;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service("refundService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class RefundServiceImpl extends ListServiceImpl<Refund> implements RefundService {

    private static String[] ALL_STATUS = {"applyCount", "waitforpaymentCount"};
    @Autowired
    private RefundMapper refundMapper;
    @Autowired
    private ReshipService reshipService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private RefundLogService refundLogService;
    @Autowired
    private RequisitionItemService requisitionItemService;
    @Autowired
    private CustomerCompensationService customerCompensationService;

    /**
     * 退款日志
     */
    private void insertRefundLog(Refund refund, String operator, String info, RefundLogType type) {
        RefundLog refundLog = new RefundLog(refund.getId(), info, type);
        refundLog.setCreator(operator);
        refundLog.setCreateDate(new Date());
        refundLog.setOrderId(refund.getOrderId());
        refundLog.setOrderItemId(refund.getOrderItemId());
        refundLogService.insert(refundLog);
    }

    @Override
    public Refund findById(Long id) {
        return this.findOneById(id);
    }

    @Override
    public Refund findByIdAndMemberInfoId(Long refundId, Long memberInfoId) {
        Refund refund = refundMapper.findByIdAndMemberInfoId(refundId, memberInfoId);
        return refund;
    }

    @Override
    public Refund findByRefundSn(String refundSn) {
        return refundMapper.findByRefundSn(refundSn);
    }

    @Override
    public PageResult<Refund> findMine(RefundSearcher searcher, PageModel page) {
        PageResult<Refund> pager = new PageResult<>(page);
        int totalCount = refundMapper.countBySearch(searcher);
        if (totalCount > 0) {
            List<Refund> list = new ArrayList<>();
            list = refundMapper.findBySearch(searcher, page);
            pager.setList(list);
        }
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    public PageResult<RefundDto> findBySearch(RefundSearcher searcher, PageModel page) {
        PageResult<RefundDto> pager = new PageResult<>(page);
        int totalCount = refundMapper.countBySearch(searcher);
        List<Refund> list = new ArrayList<>();
        List<RefundDto> dtos = new ArrayList<>();
        if (totalCount > 0) {
            list = refundMapper.findBySearch(searcher, page);
            for (Refund refund : list) {
                RefundDto dto = new RefundDto();
                BeanUtils.copyProperties(refund, dto);
                dtos.add(dto);
            }
        }
        pager.setTotalCount(totalCount);
        pager.setList(dtos);
        return pager;
    }

    @Override
    public int countBySearch(RefundSearcher searcher) {
        return refundMapper.countBySearch(searcher);
    }

    @Override
    public Map<String, Object> countByStatusAndMemberId(Long memberInfoId) {
        List<Map<String, Object>> counts = refundMapper.countByStatusAndMemberId(memberInfoId);
        Map<String, Object> map = new HashMap<>();
        int processCount = 0;
        int totalCount = 0;
        int reshipCount = 0;
        for (Map<String, Object> count : counts) {
            Integer status = Integer.parseInt(count.get("refund_status").toString());
            if (status > 0 && status < 8) {
                processCount = processCount + Integer.parseInt(count.get("counts").toString());
            }
            // 退货的初始数量
            if (status == 0) {
                reshipCount = Integer.parseInt(count.get("counts").toString());
            }
            totalCount = totalCount + Integer.parseInt(count.get("counts").toString());
        }
        map.put("reshipCount", reshipCount);
        map.put("processCount", processCount);
        map.put("totalCount", totalCount);
        return map;
    }

    /**
     * 统计退款数量
     */
    @Override
    public Map<String, Object> countRefundsMaps() {
        Map<String, Object> map = new HashMap<>();
        List<Map<String, Object>> counts = refundMapper.countGroupByStatus();
        for (Map<String, Object> count : counts) {
            String status = count.get("refund_status").toString();
            switch (status) {
                case "1":
                    map.put("applyCount", count.get("counts"));
                    break;
                case "4":
                    map.put("waitforpaymentCount", count.get("counts"));
                    break;
            }
        }
        // 将没有状态的数量，默认设置成0
        for (String status : ALL_STATUS) {
            if (map.get(status) == null) {
                map.put(status, 0);
            }
        }
        return map;
    }

    /**
     * 生成退款单，同一笔明细，不能有两笔及以上退款明细
     *
     * @param refund
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Refund insert(Refund refund) {
        BigDecimal traceAmount = refund.getTradeAmount().add(new BigDecimal(10));
        if (refund.getApplyAmount().intValue() > traceAmount.intValue()) {
            throw new BusinessException("申请金额不能大于交易金额，" + traceAmount);
        }
        OrderItem orderItem = orderItemService.findById(refund.getOrderItemId());
        if (orderItem.getStatus().equals(ItemStatus.SUCCESS.toString())) {
            throw new BusinessException("订单交易已经完成，申请退款不成功！");
        }
        refund = this.save(refund);
        if (refund.getId() > 0) {
            String reasonName = refund.getRefundReasonName();
            if (refund.getProxy() == 1) {
                if (refund.getAllRefund() == -2) {
                    reasonName = "客服代申请退运费：" + reasonName;
                } else if (refund.getAllRefund() == -1) {
                    reasonName = "客服代申请退差价：" + reasonName;
                } else {
                    reasonName = "客服代申请全额退款：" + reasonName;
                }
                // 关闭调拨单
                if (orderItem.getRequisition() > 0) {
                    requisitionItemService.doCloseByOrderItem(orderItem.getId(), refund.getCreator(), reasonName);
                }
            }
            // 数据库行锁订单明细，防止重复申请
            int createSuccess = orderItemService.doCreateRefund(refund.getId(), refund.getOrderItemId());
            if (createSuccess <= 0) {
                orderItemService.updateRefund(refund.getId(), refund.getOrderItemId());
                Refund old = refundMapper.selectByPrimaryKey(orderItem.getRefundId());
                if (old.getRefundStatus() >= 0) {
                    throw new BusinessException("退货或退款单，不能重复申请！");
                }
            }
            // 全款仅退款，待发货状态
            if (refund != null && refund.getExchangeId() == null && refund.getReshipId() == null
                    && refund.getAllRefund() == 1 && orderItem.getEstimateDate() != null
                    && orderItem.getEstimateDate().before(new Date())
                    && orderItem.getStatus().equalsIgnoreCase(ItemStatus.NORMAL.name())) {
                customerCompensationService.doOrderItemCompensation(orderItem, refund.getCreator(),
                        CompensationType.OVERAFTER.getCode());
            }
            this.insertRefundLog(refund, refund.getCreator(), reasonName, RefundLogType.create);
        }
        return refund;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int insertBatch(List<Refund> refunds) {
        for (Refund item : refunds) {
            this.insert(item);
        }
        return 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int updateReshipId(Long refundId, Long reshipId) {
        return refundMapper.updateReshipId(refundId, reshipId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public int updateBackAccount(Long refundId, String backAccountSn, String backAccountName, String creator) {
        if (StringUtils.isEmpty(backAccountSn) || StringUtils.isEmpty(backAccountName)) {
            throw new BusinessException("账户号不能为空！");
        }
        Refund refund = this.findById(refundId);
        if (refund.getRefundStatus() < 4) {
            int success = refundMapper.updateBackAccount(refundId, backAccountSn, backAccountName);
            if (success > 0) {
                String info = "修改退款账户：旧账户号：" + refund.getBackAccountSn() + "，账户名称：" + refund.getBackAccountName()
                        + "，新账户号：" + backAccountSn + "，账户名称：" + backAccountName;
                this.insertRefundLog(refund, creator, info, RefundLogType.modify);
            }
            return success;
        } else {
            throw new BusinessException("退款状态为：" + refund.getRefundStatusName() + "，修改不成功！");
        }
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updateAmount(BigDecimal totalAmount, Long refundId) {
        return refundMapper.updateAmount(totalAmount, refundId);
    }

    @Override
    @TxTransaction
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updateWalletAmount(Long id, BigDecimal cashAmount, BigDecimal giftAmount) {
        return refundMapper.updateWalletAmount(id, cashAmount, giftAmount);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int updateErrorCode(String refundSn, String resultCode) {
        return refundMapper.updateErrorCode(refundSn, resultCode);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doCustomerAgree(Long refundId, BigDecimal totalAmount, String username, String info) {
        Refund refund = this.findById(refundId);
        if (refund.getReshipId() != null) {
            Reship reship = reshipService.findById(refund.getReshipId());
            refund.setCustomerMemo(reship.getCustomerMemo());
        }
        if (refund.getCustomerMemo() != null) {
            info = refund.getCustomerMemo() + "，" + info;
        }
        if (totalAmount == null || totalAmount.intValue() < 0) {
            totalAmount = refund.getTotalAmount();
        }
        if (totalAmount.compareTo(refund.getTradeAmount().add(new BigDecimal(10))) > 0) {
            throw new BusinessException(
                    "退款金额过大，实际交易金额为：" + refund.getTradeAmount().toString() + "审核金额为：" + totalAmount.toString());
        }
        int result = refundMapper.doCustomerAgree(refundId, totalAmount, username, info);
        if (result > 0) {
            // 关闭调拨单
            OrderItem orderItem = orderItemService.findById(refund.getOrderItemId());
            if (orderItem.getRequisition() > 0) {
                requisitionItemService.doCloseByOrderItem(orderItem.getId(), username, "客服同意退款");
            }
            this.insertRefundLog(refund, username, "客服同意，" + info, RefundLogType.modify);
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doCustomerRefuse(Long refundId, String closer, String info) {
        Refund refund = findById(refundId);
        int result = 0;
        if (refund.getReshipId() != null && refund.getRefundStatus() > 0) {
            throw new BusinessException("已经处于退款中，状态为：" + refund.getRefundStatusName() + "，关闭不成功！");
        } else {
            result = refundMapper.doCloseRefund(refundId, closer);
            if (result > 0) {
                this.insertRefundLog(refund, closer, info, RefundLogType.refuse);
                // 订单明细预计完成时间
                OrderItem orderItem = orderItemService.findById(refund.getOrderItemId());
                Date now = new Date();
                if (orderItem.getExpectDate() != null && orderItem.getExpectDate().before(now)) {
                    orderItemService.updateExpectDate(refund.getOrderItemId(), now);
                }
            }
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public int doLock(Long refundId, String lastModifyMan) {
        int success = refundMapper.doLock(refundId, lastModifyMan);
        if (success > 0) {
            Refund refund = this.findById(refundId);
            String info = "客服（" + lastModifyMan + "）锁定，流水号：" + refund.getRefundSn();
            this.insertRefundLog(refund, lastModifyMan, info, RefundLogType.lock);
        }
        return success;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public int doCancelLock(Long refundId, String lastModifyMan) {
        int success = refundMapper.doCancelLock(refundId, lastModifyMan);
        if (success > 0) {
            Refund refund = this.findById(refundId);
            String info = "客服（" + lastModifyMan + "）取消锁定，流水号：" + refund.getRefundSn();
            this.insertRefundLog(refund, lastModifyMan, info, RefundLogType.lock);
        }
        return success;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public int doBack(Long refundId, String lastModifyMan, String info) {
        Refund refund = this.findById(refundId);
        int success = refundMapper.doBack(refundId, lastModifyMan);
        if (success > 0) {
            String reason = "财务退回，流水号：" + refund.getRefundSn() + "，退款单客服重新确认";
            if (StringUtils.isNotBlank(info)) {
                reason = reason + "，原因：" + info;
            }
            this.insertRefundLog(refund, lastModifyMan, reason, RefundLogType.refuse);
        }
        return success;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int doCancel(Long refundId, String modifyMan) {
        String info = "用户取消退款申请";
        int result = 0;
        Refund refund = this.findById(refundId);
        if (refund.getRefundStatus().equals(RefundStatus.APPLY.getCode())
                || refund.getRefundStatus().equals(RefundStatus.CREATE.getCode())) {
            result = refundMapper.doCancel(refundId, modifyMan);
            if (result > 0) {
                this.insertRefundLog(refund, modifyMan, info, RefundLogType.memberClose);
                // 订单明细预计完成时间
                OrderItem orderItem = orderItemService.findById(refund.getOrderItemId());
                Date now = new Date();
                if (orderItem.getExpectDate() != null && orderItem.getExpectDate().before(now)) {
                    orderItemService.updateExpectDate(refund.getOrderItemId(), now);
                }
            } else {
                throw new BusinessException("取消退款不成功，已经退款处理中，请联系客服取消！");
            }
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doCompensation(Long id, BigDecimal compensation) {
        return refundMapper.doCompensation(id, compensation);
    }

    @Override
    @TxTransaction
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doSuccess(Long refundId, Integer backAccountType, Date payDate, BigDecimal payMoney, String paySn,
                         String payer, int allRefund) {
        return refundMapper.doSuccessRefund(refundId, backAccountType, payDate, payMoney, paySn, payer, allRefund);
    }

    @Override
    @TxTransaction
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doThirdSuccess(Long refundId, String paySn, Date notifyTime) {
        return refundMapper.doThirdSuccess(refundId, paySn, notifyTime);
    }

}
