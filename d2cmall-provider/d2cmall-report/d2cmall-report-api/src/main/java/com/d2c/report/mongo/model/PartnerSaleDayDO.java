package com.d2c.report.mongo.model;

import com.d2c.common.base.utils.DateUt;
import com.d2c.member.model.Partner;
import com.d2c.report.mongo.model.base.PartnerSaleTimeDO;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 买手每日销售数据
 *
 * @author wull
 */
@Document
public class PartnerSaleDayDO extends PartnerSaleTimeDO {

    private static final long serialVersionUID = -7755422262982295261L;
    /**
     * 日报时间 yyyy-mm-dd
     */
    @Indexed
    private String day;

    public PartnerSaleDayDO() {
    }

    public PartnerSaleDayDO(Partner partner, String day) {
        super(partner, DateUt.dayStart(day), DateUt.dayEnd(day));
        this.day = day;
        initId();
    }

    public void initId() {
        this.udid = partnerId + "_" + day;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

}
