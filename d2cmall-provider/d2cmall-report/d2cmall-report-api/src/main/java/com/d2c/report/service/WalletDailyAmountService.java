package com.d2c.report.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.report.model.WalletDailyAmount;
import com.d2c.report.query.FinanceDailyAmountSearcher;

public interface WalletDailyAmountService {

    WalletDailyAmount findLastOne();

    WalletDailyAmount insert(WalletDailyAmount walletAmount);

    PageResult<WalletDailyAmount> findWalletBySearcher(FinanceDailyAmountSearcher searcher, PageModel page);

    int countWalletBySearcher(FinanceDailyAmountSearcher searcher);

}
