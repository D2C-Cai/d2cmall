package com.d2c.report.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.report.model.FinanceCodDailyAmount;
import com.d2c.report.query.FinanceDailyAmountSearcher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FinanceCodDailyAmountMapper extends SuperMapper<FinanceCodDailyAmount> {

    int countCodBySearcher(@Param("searcher") FinanceDailyAmountSearcher searcher);

    List<FinanceCodDailyAmount> findCodBySearcher(@Param("searcher") FinanceDailyAmountSearcher searcher,
                                                  @Param("page") PageModel page);

    FinanceCodDailyAmount findLastCod();

}
