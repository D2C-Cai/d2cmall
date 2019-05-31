package com.d2c.report.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.report.model.WalletSummary;
import com.d2c.report.query.WalletSummarySearcher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WalletSummaryMapper extends SuperMapper<WalletSummary> {

    int countBySearcher(@Param("searcher") WalletSummarySearcher searcher);

    List<WalletSummary> findBySearcher(@Param("searcher") WalletSummarySearcher searcher,
                                       @Param("page") PageModel page);

    WalletSummary findLastOne();

}
