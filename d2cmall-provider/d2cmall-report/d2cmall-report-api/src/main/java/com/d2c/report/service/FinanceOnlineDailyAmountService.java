package com.d2c.report.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.report.model.FinanceOnlineDailyAmount;
import com.d2c.report.query.FinanceDailyAmountSearcher;

public interface FinanceOnlineDailyAmountService {

    FinanceOnlineDailyAmount insert(FinanceOnlineDailyAmount financeDailyAmount);

    int countBySearcher(FinanceDailyAmountSearcher searcher);

    FinanceOnlineDailyAmount findLastOne();

    PageResult<FinanceOnlineDailyAmount> findOnlineBySearcher(FinanceDailyAmountSearcher searcher, PageModel page);

}
