package com.d2c.report.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.report.model.FinanceOnlineDailyAmount;
import com.d2c.report.query.FinanceDailyAmountSearcher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FinanceOnlineDailyAmountMapper extends SuperMapper<FinanceOnlineDailyAmount> {

    int countOnlineBySearcher(@Param("searcher") FinanceDailyAmountSearcher searcher);

    List<FinanceOnlineDailyAmount> findOnlineBySearcher(@Param("searcher") FinanceDailyAmountSearcher searcher,
                                                        @Param("page") PageModel page);

    FinanceOnlineDailyAmount findLastOne();

}
