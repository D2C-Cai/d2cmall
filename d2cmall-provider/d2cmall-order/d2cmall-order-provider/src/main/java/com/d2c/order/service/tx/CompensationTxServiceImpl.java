package com.d2c.order.service.tx;

import com.alibaba.dubbo.config.annotation.Service;
import com.codingapi.tx.annotation.TxTransaction;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.logger.model.CompensationLog;
import com.d2c.logger.service.CompensationLogService;
import com.d2c.logger.service.MsgUniteService;
import com.d2c.logger.support.MsgBean;
import com.d2c.logger.support.PushBean;
import com.d2c.member.model.Account;
import com.d2c.member.service.AccountService;
import com.d2c.member.support.CompensationInfo;
import com.d2c.order.model.AccountItem;
import com.d2c.order.model.CustomerCompensation;
import com.d2c.order.model.CustomerCompensation.CompensationStatus;
import com.d2c.order.model.OrderItem;
import com.d2c.order.model.OrderItem.ItemStatus;
import com.d2c.order.service.AccountItemService;
import com.d2c.order.service.CustomerCompensationService;
import com.d2c.order.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(protocol = {"dubbo"})
public class CompensationTxServiceImpl implements CompensationTxService {

    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountItemService accountItemService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private MsgUniteService msgUniteService;
    @Autowired
    private CustomerCompensationService customerCompensationService;
    @Autowired
    private CompensationLogService compensationLogService;

    @Override
    @TxTransaction(isStart = true)
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int doCompensationPay(Long id, String operator, String remark) {
        int success = 0;
        CustomerCompensation compensation = customerCompensationService.findById(id);
        if (compensation != null && compensation.getStatus().equals(CompensationStatus.WAITFORPAY.getCode())) {
            OrderItem orderItem = orderItemService.findById(compensation.getTransactionId());
            if (orderItem != null && orderItem.getStatus().equals(ItemStatus.SUCCESS.name())
                    || orderItem.getStatus().equals(ItemStatus.AFTERCLOSE.name())) {
                Account account = accountService.findByMemberId(orderItem.getBuyerMemberId());
                if (account != null) {
                    CompensationInfo compensationInfo = new CompensationInfo();
                    compensationInfo.setAccountId(account.getId());
                    compensationInfo.setMemberId(account.getMemberId());
                    compensationInfo.setBillSourceId(compensation.getId());
                    compensationInfo.setBillSourceSn(compensation.getTransactionSn());
                    compensationInfo.setCompensation(compensation.getCompensationAmount());
                    compensationInfo.setType(0);
                    AccountItem accountItem = new AccountItem(compensationInfo);
                    accountItem = accountItemService.insert(accountItem);
                    success = accountService.doUpdateAmount(account.getId(), accountItem.getFactAmount(),
                            accountItem.getFactGiftAmount(), null, null);
                    if (success > 0) {
                        success = customerCompensationService.doPay(compensation.getId());
                        if (success > 0) {
                            CompensationLog log = new CompensationLog(null, compensation.getId(), "赔偿完成:" + remark,
                                    operator);
                            compensationLogService.insert(log);
                        }
                    } else {
                        throw new BusinessException("赔偿不成功");
                    }
                } else {
                    StringBuffer buffer = new StringBuffer();
                    buffer.append("尊敬的用户，您的订单").append(orderItem.getOrderSn())
                            .append("已处理完结。因处理不及时，根据D2C客户服务规则，针对该订单的赔付款项金额为").append(orderItem.getCompensationAmount())
                            .append("， 由于您的钱包账户还未开通，请及时联系客服，感谢您的关注！（D2C全球好设计）");
                    String subject = "处理超时赔偿到账";
                    String content = buffer.toString();
                    PushBean pushBean = new PushBean(orderItem.getBuyerMemberId(), content, 29);
                    MsgBean msgBean = new MsgBean(orderItem.getBuyerMemberId(), 29, subject, content);
                    msgUniteService.sendPush(pushBean, msgBean);
                }
            }
        }
        return success;
    }

}
