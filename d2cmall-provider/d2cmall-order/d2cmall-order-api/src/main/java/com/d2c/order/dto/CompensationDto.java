package com.d2c.order.dto;

import com.d2c.order.model.Compensation;
import com.d2c.util.date.DateUtil;

import java.text.ParseException;

public class CompensationDto extends Compensation {

    private static final long serialVersionUID = 1L;
    /**
     * 超时日期
     */
    private Integer expiredDay;

    public Integer getExpiredDay() {
        try {
            return DateUtil.daysBetween(this.getEstimateDate(), this.createDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return expiredDay;
    }

    public void setExpiredDay(Integer expiredDay) {
        this.expiredDay = expiredDay;
    }

    public String getStatusString() {
        if (this.getStatus() == 1) {
            return "已赔偿";
        } else if (this.getStatus() == 0) {
            return "未赔偿";
        } else {
            return "已关闭";
        }
    }

}
