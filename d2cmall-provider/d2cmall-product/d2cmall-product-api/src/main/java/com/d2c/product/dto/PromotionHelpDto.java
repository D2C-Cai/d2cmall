package com.d2c.product.dto;

import com.d2c.common.api.dto.HelpDTO;

public class PromotionHelpDto extends HelpDTO {

    private static final long serialVersionUID = 1L;
    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

}
