package com.d2c.quartz.task;

import com.alibaba.dubbo.config.annotation.Reference;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.model.Account;
import com.d2c.member.query.AccountSearcher;
import com.d2c.member.service.AccountService;
import com.d2c.order.service.tx.AccountTxService;
import com.d2c.quartz.task.base.BaseTask;
import com.d2c.quartz.task.base.EachPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ExpiredAccountTask extends BaseTask {

    @Autowired
    private AccountService accountService;
    @Reference
    private AccountTxService accountTxService;

    @Scheduled(cron = "0 0 2 * * ?")
    public void execute() {
        if (properties.getDebug()) {
            return;
        }
        super.exec();
    }

    @Override
    public void execImpl() {
        this.doCleanGiftAmount();
    }

    private void doCleanGiftAmount() {
        AccountSearcher searcher = new AccountSearcher();
        searcher.setLimitDate(new Date());
        try {
            this.processPager(500, new EachPage<Account>() {
                @Override
                public int count() {
                    return accountService.countBySearch(searcher);
                }

                @Override
                public PageResult<Account> search(PageModel page) {
                    return accountService.findBySearch(searcher, page);
                }

                @Override
                public boolean each(Account object) {
                    int result = accountTxService.doCleanLimit(object);
                    return result > 0 ? true : false;
                }
            });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

}
