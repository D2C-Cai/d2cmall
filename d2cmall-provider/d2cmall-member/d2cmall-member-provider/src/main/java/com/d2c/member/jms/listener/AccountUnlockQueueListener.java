package com.d2c.member.jms.listener;

import com.d2c.common.mq.enums.MqEnum;
import com.d2c.common.mq.listener.AbsMqListener;
import com.d2c.member.model.Account;
import com.d2c.member.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.MapMessage;
import javax.jms.Message;
import java.util.Date;

@Component
public class AccountUnlockQueueListener extends AbsMqListener {

    @Autowired
    private AccountService accountService;

    @Override
    public void onMessage(Message message) {
        MapMessage mapMsg = (MapMessage) message;
        try {
            Long accountId = mapMsg.getLong("accountId");
            Account account = accountService.findById(accountId);
            if (account != null && new Date().getTime() - account.getLastPayDate().getTime() >= 2 * 60 * 60) {
                accountService.doLockAccount(accountId, false);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public MqEnum getMqEnum() {
        return MqEnum.UNLOCK_ACCOUNT;
    }

}
