package com.d2c.report.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.report.dao.FinanceCodDailyAmountMapper;
import com.d2c.report.model.FinanceCodDailyAmount;
import com.d2c.report.query.FinanceDailyAmountSearcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(protocol = "dubbo")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class FinanceCodDailyAmountServiceImpl extends ListServiceImpl<FinanceCodDailyAmount>
        implements FinanceCodDailyAmountService {

    @Autowired
    private FinanceCodDailyAmountMapper financeCodDailyAmountMapper;

    @Override
    public PageResult<FinanceCodDailyAmount> findCodBySearcher(FinanceDailyAmountSearcher searcher, PageModel page) {
        PageResult<FinanceCodDailyAmount> pager = new PageResult<FinanceCodDailyAmount>();
        int totalCount = financeCodDailyAmountMapper.countCodBySearcher(searcher);
        if (totalCount > 0) {
            List<FinanceCodDailyAmount> list = financeCodDailyAmountMapper.findCodBySearcher(searcher, page);
            pager.setList(list);
        }
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    public FinanceCodDailyAmount findLastCod() {
        return financeCodDailyAmountMapper.findLastCod();
    }

    @Override
    public FinanceCodDailyAmount insert(FinanceCodDailyAmount financeCodDailyAmount) {
        return this.save(financeCodDailyAmount);
    }

    @Override
    public int countCodBySearcher(FinanceDailyAmountSearcher searcher) {
        return financeCodDailyAmountMapper.countCodBySearcher(searcher);
    }

}
