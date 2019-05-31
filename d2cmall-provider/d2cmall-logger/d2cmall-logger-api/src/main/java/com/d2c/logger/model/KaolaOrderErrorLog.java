package com.d2c.logger.model;

import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;

/**
 * kaola推单失败
 *
 * @author Lain
 */
@Table(name = "log_kaola_order_error")
public class KaolaOrderErrorLog extends PreUserDO {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String identityId;
    private String orderSn;
    private String error;

    public String getIdentityId() {
        return identityId;
    }

    public void setIdentityId(String identityId) {
        this.identityId = identityId;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}
