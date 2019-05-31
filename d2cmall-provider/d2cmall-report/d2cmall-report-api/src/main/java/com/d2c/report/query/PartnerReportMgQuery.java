package com.d2c.report.query;

import com.d2c.common.api.annotation.search.SearchField;
import com.d2c.common.api.query.enums.OperType;
import com.d2c.common.api.query.enums.ValueType;
import com.d2c.common.api.query.model.MongoQuery;

public class PartnerReportMgQuery extends MongoQuery {

    private static final long serialVersionUID = 2817966647956623836L;
    /**
     * 开始时间
     */
    @SearchField(name = "statDate", oper = OperType.GTE, type = ValueType.VDATE)
    private String dateMin;
    /**
     * 结束时间
     */
    @SearchField(name = "statDate", oper = OperType.LTE, type = ValueType.VDATE)
    private String dateMax;

    public String getDateMin() {
        return dateMin;
    }

    public void setDateMin(String dateMin) {
        this.dateMin = dateMin;
    }

    public String getDateMax() {
        return dateMax;
    }

    public void setDateMax(String dateMax) {
        this.dateMax = dateMax;
    }

}
