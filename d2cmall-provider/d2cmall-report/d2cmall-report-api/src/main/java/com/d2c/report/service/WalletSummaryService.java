package com.d2c.report.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.report.model.WalletSummary;
import com.d2c.report.query.WalletSummarySearcher;

public interface WalletSummaryService {

    WalletSummary insert(WalletSummary walletSummary);

    PageResult<WalletSummary> findBySearcher(WalletSummarySearcher searcher, PageModel page);

    WalletSummary findLastOne();

    int countBySearcher(WalletSummarySearcher searcher);

}
