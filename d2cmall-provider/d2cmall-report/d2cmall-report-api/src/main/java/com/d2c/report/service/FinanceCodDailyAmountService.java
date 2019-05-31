package com.d2c.report.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.report.model.FinanceCodDailyAmount;
import com.d2c.report.query.FinanceDailyAmountSearcher;

public interface FinanceCodDailyAmountService {

    PageResult<FinanceCodDailyAmount> findCodBySearcher(FinanceDailyAmountSearcher searcher, PageModel page);

    int countCodBySearcher(FinanceDailyAmountSearcher searcher);

    FinanceCodDailyAmount findLastCod();

    FinanceCodDailyAmount insert(FinanceCodDailyAmount financeCodDailyAmount);

}
