package com.d2c.order.query;

import com.d2c.common.api.query.model.BaseQuery;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

public class DesignerOrderSumSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 开始时间
     */
    private Date startDate;
    /**
     * 结束时间
     */
    private Date endDate;
    /**
     * 品牌名称
     */
    private String name;
    /**
     * 运营小组
     */
    private String operation;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String[] getNames() {
        if (StringUtils.isNotBlank(this.getName()))
            return this.getName().split(",");
        return null;
    }

}
