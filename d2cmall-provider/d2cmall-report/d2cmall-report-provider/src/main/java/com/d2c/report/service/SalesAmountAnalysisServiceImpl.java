package com.d2c.report.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.report.dao.SalesAmountAnalysisMapper;
import com.d2c.report.model.SalesAmountAnalysis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(protocol = "dubbo")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class SalesAmountAnalysisServiceImpl extends ListServiceImpl<SalesAmountAnalysis>
        implements SalesAmountAnalysisService {

    @Autowired
    private SalesAmountAnalysisMapper salesAmountAnalysisMapper;

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public SalesAmountAnalysis insert(SalesAmountAnalysis salesAmountAnalysis) {
        return this.save(salesAmountAnalysis);
    }

    @Override
    public SalesAmountAnalysis findByLastOne() {
        return salesAmountAnalysisMapper.findByLastOne();
    }

}
