package com.d2c.report.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.report.model.WalletDailyAmount;
import com.d2c.report.query.FinanceDailyAmountSearcher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WalletDailyAmountMapper extends SuperMapper<WalletDailyAmount> {

    WalletDailyAmount findLastOne();

    int countBySearcher(@Param("searcher") FinanceDailyAmountSearcher searcher);

    List<WalletDailyAmount> findBySearcher(@Param("searcher") FinanceDailyAmountSearcher searcher,
                                           @Param("page") PageModel page);

}
