package com.d2c.order.service.tx;

import com.alibaba.dubbo.config.annotation.Service;
import com.codingapi.tx.annotation.TxTransaction;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.logger.service.MsgUniteService;
import com.d2c.logger.support.MsgBean;
import com.d2c.logger.support.PushBean;
import com.d2c.member.enums.BusinessTypeEnum;
import com.d2c.member.enums.PayTypeEnum;
import com.d2c.member.service.AccountService;
import com.d2c.member.support.CashCardInfo;
import com.d2c.order.model.AccountItem;
import com.d2c.order.model.CashCard;
import com.d2c.order.service.AccountItemService;
import com.d2c.order.service.CashCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(protocol = {"dubbo"})
public class CashCardTxServiceImpl implements CashCardTxService {

    @Autowired
    private CashCardService cashCardService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountItemService accountItemService;
    @Autowired
    private MsgUniteService msgUniteService;

    @Override
    @TxTransaction(isStart = true)
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public CashCard doConvertCashCard(String sn, String password, Long memberInfoId, String loginCode, Long accountId) {
        CashCard card = cashCardService.findBySnAndPassword(sn, password);
        if (card == null) {
            throw new BusinessException("D2C码不正确！");
        }
        if (card.getMemberId() != null) {
            throw new BusinessException("D2C码已经领取！");
        }
        int success = cashCardService.doConvert(card.getId(), memberInfoId, loginCode, accountId);
        if (success > 0) {
            int result = cashCardService.doOver(card.getId());
            if (result > 0) {
                card = cashCardService.findById(card.getId());
                CashCardInfo info = new CashCardInfo(BusinessTypeEnum.CASHCARD.name(), PayTypeEnum.RECHARGE.name());
                info.setAccountId(accountId);
                info.setMemberId(memberInfoId);
                info.setBillSourceId(card.getId());
                info.setBillSourceSn(card.getCode());
                info.setCardAmount(card.getAmount());
                AccountItem accountItem = new AccountItem(info);
                accountItem = accountItemService.insert(accountItem);
                accountService.doUpdateAmount(accountId, accountItem.getFactAmount(), accountItem.getFactGiftAmount(),
                        null, null);
                accountItemService.updateTransactionInfo(accountItem.getId(), card.getId(), "充值信息：" + card.getCode());
                String subject = "钱包充值到账通知";
                String content = "亲爱的用户，您已成功充值" + card.getAmount() + "元，查看钱包余额，请到我的钱包中查看";
                PushBean pushBean = new PushBean(memberInfoId, content, 33);
                pushBean.setAppUrl("/member/account/info");
                MsgBean msgBean = new MsgBean(memberInfoId, 33, subject, content);
                msgBean.setAppUrl("/member/account/info");
                msgUniteService.sendPush(pushBean, msgBean);
                return card;
            }
        }
        return null;
    }

}
