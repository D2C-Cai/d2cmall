package com.d2c.logger.model;

import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;

/**
 * 草莓做单失败
 *
 * @author Lain
 */
@Table(name = "log_caomei_order_error")
public class CaomeiOrderErrorLog extends PreUserDO {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String orderSn;
    private Long orderItemId;
    private String error;
    private Integer success = 0;

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

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

}
