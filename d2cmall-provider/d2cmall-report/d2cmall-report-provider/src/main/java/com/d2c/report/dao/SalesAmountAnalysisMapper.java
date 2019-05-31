package com.d2c.report.dao;

import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.report.model.SalesAmountAnalysis;

public interface SalesAmountAnalysisMapper extends SuperMapper<SalesAmountAnalysis> {

    SalesAmountAnalysis findByLastOne();

}
