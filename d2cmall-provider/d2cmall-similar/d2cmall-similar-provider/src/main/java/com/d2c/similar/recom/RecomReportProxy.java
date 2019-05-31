package com.d2c.similar.recom;

import com.d2c.common.mongodb.report.ReportProxy;
import com.d2c.similar.mongo.model.RecomReportDO;

public class RecomReportProxy extends ReportProxy<RecomReportDO> {

    public RecomReportProxy() {
        super(new RecomReportDO());
    }

    @Override
    protected String getName() {
        return "商品推荐值";
    }

    @Override
    public int getSaveLimit() {
        return 100;
    }

}
