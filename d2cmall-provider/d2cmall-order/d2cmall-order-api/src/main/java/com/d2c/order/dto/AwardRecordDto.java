package com.d2c.order.dto;

import com.d2c.order.model.AwardRecord;
import com.d2c.product.model.AwardSession;

public class AwardRecordDto extends AwardRecord {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private AwardSession awardSession;

    public AwardSession getAwardSession() {
        return awardSession;
    }

    public void setAwardSession(AwardSession awardSession) {
        this.awardSession = awardSession;
    }

}
