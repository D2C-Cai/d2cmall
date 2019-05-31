package com.d2c.report.mongo.model.base;

import com.d2c.member.model.Partner;
import org.springframework.data.annotation.Id;

import java.util.Date;

/**
 * 买手时间段内销售数据统计
 *
 * @author wull
 */
public class PartnerSaleTimeDO extends PartnerSaleBaseDO {

    private static final long serialVersionUID = -1469309914326167374L;
    /**
     * ID 主键
     * <p> {partnerId}_{day}
     */
    @Id
    protected String udid;
    /**
     * 统计开始时间
     */
    protected Date startTime;
    /**
     * 统计结束时间
     */
    protected Date endTime;

    public PartnerSaleTimeDO() {
    }

    public PartnerSaleTimeDO(Partner partner, Date start, Date end) {
        super(partner);
        setStartTime(start);
        setEndTime(end);
    }

    public String getUdid() {
        return udid;
    }

    public void setUdid(String udid) {
        this.udid = udid;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

}
