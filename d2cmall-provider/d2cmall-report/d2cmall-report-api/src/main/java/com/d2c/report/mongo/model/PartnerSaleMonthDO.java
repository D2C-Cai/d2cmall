package com.d2c.report.mongo.model;

import com.d2c.common.base.utils.DateUt;
import com.d2c.member.model.Partner;
import com.d2c.report.mongo.model.base.PartnerSaleTimeDO;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 买手每月销售数据
 *
 * @author wull
 */
@Document
public class PartnerSaleMonthDO extends PartnerSaleTimeDO {

    private static final long serialVersionUID = 8020753465248467989L;
    /**
     * 月报时间 yyyy-mm
     */
    @Indexed
    private String month;

    public PartnerSaleMonthDO() {
    }

    public PartnerSaleMonthDO(Partner partner, String month) {
        super(partner, DateUt.monthStart(month), DateUt.monthEnd(month));
        this.month = month;
        initId();
    }

    public void initId() {
        this.udid = partnerId + "_" + month;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

}
