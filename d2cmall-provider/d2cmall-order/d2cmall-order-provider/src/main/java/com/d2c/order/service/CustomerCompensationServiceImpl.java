package com.d2c.order.service;

import com.codingapi.tx.annotation.TxTransaction;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.logger.model.CompensationLog;
import com.d2c.logger.service.CompensationLogService;
import com.d2c.logger.service.MsgUniteService;
import com.d2c.logger.support.MsgBean;
import com.d2c.logger.support.PushBean;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.service.MemberInfoService;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.order.dao.CustomerCompensationMapper;
import com.d2c.order.model.*;
import com.d2c.order.model.CustomerCompensation.CompensationStatus;
import com.d2c.order.model.CustomerCompensation.CompensationType;
import com.d2c.order.query.CustomerCompensationSearcher;
import com.d2c.util.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service("customerCompensationService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
public class CustomerCompensationServiceImpl extends ListServiceImpl<CustomerCompensation>
        implements CustomerCompensationService {

    @Autowired
    private SettingService settingService;
    @Autowired
    private MsgUniteService msgUniteService;
    @Autowired
    private CustomerCompensationMapper customerCompensationMapper;
    @Autowired
    private CompensationLogService compensationLogService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private MemberInfoService memberInfoService;
    @Autowired
    private RefundService refundService;
    @Autowired
    private CompensationService compensationService;

    @Override
    public CustomerCompensation findById(Long id) {
        return this.findOneById(id);
    }

    @Override
    public PageResult<CustomerCompensation> findBySearcher(CustomerCompensationSearcher searcher, PageModel page) {
        PageResult<CustomerCompensation> pager = new PageResult<>(page);
        int totalCount = customerCompensationMapper.countBySearcher(searcher);
        if (totalCount > 0) {
            List<CustomerCompensation> list = customerCompensationMapper.findBySearcher(searcher, page);
            pager.setList(list);
        }
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    public int countBySearcher(CustomerCompensationSearcher searcher) {
        return customerCompensationMapper.countBySearcher(searcher);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updateCompensationAmount(Long id, BigDecimal compensationAmount, String operator, String remark) {
        int success = 0;
        if (compensationAmount.compareTo(new BigDecimal(0)) < 0) {
            throw new BusinessException("赔偿金额不能为负数");
        }
        CustomerCompensation compensation = this.findOneById(id);
        if (compensation == null) {
            throw new BusinessException("该赔偿单不存在");
        }
        if (!compensation.getStatus().equals(CompensationStatus.WAITFORPAY.getCode())) {
            throw new BusinessException("在非带审核状态下不能修改金额");
        }
        OrderItem orderItem = orderItemService.findById(compensation.getTransactionId());
        if (orderItem != null) {
            if (compensationAmount.compareTo(new BigDecimal(300)) > 0 || compensationAmount
                    .compareTo(new BigDecimal(0.3).multiply(orderItem.getActualDeliveryAmount())) > 0) {
                throw new BusinessException("赔偿金额不能超过300元且不能超过订单明细实付金额的30%");
            }
            success = customerCompensationMapper.updateCompensationAmount(id, compensationAmount);
            if (success > 0) {
                // 订单明细里更新订单赔偿金额
                orderItemService.doCompensation(orderItem.getId(), compensationAmount);
                CompensationLog log = new CompensationLog(null, id,
                        "业务编号：" + orderItem.getOrderSn() + "修改赔偿金额" + compensationAmount + "元,备注：" + remark, operator);
                compensationLogService.insert(log);
            }
        }
        return success;
    }

    @Override
    @TxTransaction
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int doPay(Long id) {
        return customerCompensationMapper.doPay(id);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int doClose(Long id, String operator, String remark) {
        CustomerCompensation compensation = this.findOneById(id);
        if (compensation.getStatus().equals(CompensationStatus.SUCCESS.getCode())
                || compensation.getStatus().equals(CompensationStatus.CLOSE.getCode())) {
            throw new BusinessException("该赔偿单已赔偿或已关闭，关闭不成功");
        }
        int success = customerCompensationMapper.doClose(id, remark);
        if (success > 0) {
            // 订单明细里更新订单赔偿金额
            orderItemService.doCompensation(compensation.getTransactionId(), new BigDecimal(0));
            CompensationLog log = new CompensationLog(null, compensation.getId(), "赔偿单关闭：" + remark, operator);
            compensationLogService.insert(log);
        }
        return success;
    }

    /**
     * 订单发货赔偿
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int doOrderItemCompensation(OrderItem orderItem, String operator, Integer type) {
        int success = 0;
        int send = 0;
        // 查找是否已存在该订单的赔偿单
        CustomerCompensation otherCompensation = customerCompensationMapper.findByTransactionId(orderItem.getId());
        if (otherCompensation == null) {
            Setting setting = settingService.findByCode(Setting.COMPENSATION);
            if (setting != null && setting.getStatus() == 1) {
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    String[] strs = setting.getValue().split(",");
                    // 开始赔偿时间
                    Date beginDate = sf.parse(strs[0]);
                    send = Integer.parseInt(strs[1]);
                    if (orderItem.getCreateDate().before(beginDate)) {
                        return 0;
                    }
                } catch (ParseException e) {
                    logger.error(e.getMessage(), e);
                }
                // 预计发货时间
                Date estimateDate = orderItem.getEstimateDate();
                if (estimateDate != null) {
                    // 操作时间
                    Date deliveryDate = new Date();
                    BigDecimal compensationAmount = new BigDecimal(0);
                    // 没有调拨的算一下赔偿金额
                    if (orderItem.getRequisition() == 0 || orderItem.getRequisition() == -1) {
                        compensationAmount = this.doCalculate(deliveryDate, orderItem);
                    } else {
                        // 调拨的直接找设计师赔偿单
                        Compensation compensation = compensationService.findDesignerByOrderItemId(orderItem.getId());
                        if (compensation != null) {
                            compensationAmount = compensation.getActualAmount();
                        } else if (CompensationType.MANUAL.getCode().equals(type)
                                || CompensationType.OVERAFTER.getCode().equals(type)) {
                            // 调拨单手动生成赔偿单按今日算，而订单的预计发货时间和调拨单的发货时间是一样的，所以取的订单的预计发货时间
                            compensationAmount = this.doCalculate(deliveryDate, orderItem);
                        }
                    }
                    if (compensationAmount.compareTo(new BigDecimal(0)) > 0) {
                        MemberInfo memberInfo = memberInfoService.findById(orderItem.getBuyerMemberId());
                        CustomerCompensation customerCompensation = new CustomerCompensation(orderItem,
                                compensationAmount, type, memberInfo != null ? memberInfo.getLoginCode() : null);
                        customerCompensation = this.save(customerCompensation);
                        if (customerCompensation.getId() != null && customerCompensation.getId() > 0) {
                            success = 1;
                        }
                        // 对赔偿进行记录
                        if (success > 0) {
                            // 订单明细里更新订单赔偿金额
                            orderItemService.doCompensation(orderItem.getId(), compensationAmount);
                            if (send > 0) {
                                StringBuffer buffer = new StringBuffer();
                                buffer.append("尊敬的用户，您的订单").append(orderItem.getOrderSn()).append("中的商品")
                                        .append(orderItem.getProductName())
                                        .append("因未能及时发货，根据D2C客户服务规则，针对该商品发货延迟的赔付款项金额为").append(compensationAmount)
                                        .append("元，赔付款将在该订单完结后3个工作日内汇入您的D2C钱包，感谢您的关注！（D2C全球好设计）");
                                String subject = "发货超时赔偿通知";
                                String content = buffer.toString();
                                PushBean pushBean = new PushBean(orderItem.getBuyerMemberId(), content, 29);
                                pushBean.setAppUrl("/details/order/" + orderItem.getOrderSn());
                                MsgBean msgBean = new MsgBean(orderItem.getBuyerMemberId(), 29, subject, content);
                                msgBean.setAppUrl("/details/order/" + orderItem.getOrderSn());
                                msgBean.setPic(orderItem.getProductImg());
                                msgUniteService.sendPush(pushBean, msgBean);
                            }
                            String info = "";
                            if (orderItem.getRefundId() != null) {
                                Refund refund = refundService.findById(orderItem.getRefundId());
                                info = "订单编号：" + orderItem.getOrderSn() + "退款单编号" + refund == null ? ""
                                        : refund.getRefundSn() + "发货超时赔偿" + customerCompensation.getCompensationAmount()
                                        + "元";
                            } else {
                                info = "业务编号：" + orderItem.getOrderSn() + "发货超时赔偿"
                                        + customerCompensation.getCompensationAmount() + "元";
                            }
                            CompensationLog log = new CompensationLog(null, customerCompensation.getId(), info,
                                    operator);
                            compensationLogService.insert(log);
                        }
                    }
                }
            }
        }
        return success;
    }

    /**
     * 赔偿计算
     *
     * @param operateDate
     * @param orderItem
     * @return
     */
    private BigDecimal doCalculate(Date operateDate, OrderItem orderItem) {
        // 天数差
        int daysBetween = 0;
        // 赔偿金额
        BigDecimal compensation = new BigDecimal(0);
        try {
            daysBetween = DateUtil.daysBetween(orderItem.getEstimateDate(), operateDate);
        } catch (ParseException e) {
            throw new BusinessException("赔偿时间计算异常");
        }
        if (daysBetween >= 1) {
            compensation = orderItem.getActualDeliveryAmount().multiply(new BigDecimal(0.1));
            // 超出两天的部分3%算
            daysBetween = daysBetween - 1;
            if (daysBetween > 0) {
                compensation = compensation
                        .add(orderItem.getActualDeliveryAmount().multiply(new BigDecimal(0.03 * daysBetween)));
            }
            // 赔偿金额=min(30%*实付,300,赔偿金额)
            compensation = (compensation
                    .compareTo(orderItem.getActualDeliveryAmount().multiply(new BigDecimal(0.3))) > 0
                    ? orderItem.getActualDeliveryAmount().multiply(new BigDecimal(0.3)) : compensation);
            compensation = (compensation.compareTo(new BigDecimal(300)) > 0 ? new BigDecimal(300) : compensation)
                    .setScale(2, RoundingMode.HALF_EVEN);
        }
        return compensation;
    }

    @Override
    @TxTransaction
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updateStatusByOrderItem(Long orderItemId) {
        return customerCompensationMapper.updateStatusByOrderItem(orderItemId);
    }

    @Override
    public BigDecimal sumBySearcher(CustomerCompensationSearcher searcher) {
        return customerCompensationMapper.sumBySearcher(searcher);
    }

}
