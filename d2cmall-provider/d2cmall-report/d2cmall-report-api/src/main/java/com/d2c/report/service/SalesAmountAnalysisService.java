package com.d2c.report.service;

import com.d2c.report.model.SalesAmountAnalysis;

public interface SalesAmountAnalysisService {

    SalesAmountAnalysis insert(SalesAmountAnalysis salesAmountAnalysis);

    SalesAmountAnalysis findByLastOne();

}
