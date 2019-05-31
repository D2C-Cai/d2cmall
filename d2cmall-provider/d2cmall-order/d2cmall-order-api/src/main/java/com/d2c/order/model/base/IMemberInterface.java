package com.d2c.order.model.base;

import java.io.Serializable;

public interface IMemberInterface extends Serializable {

    Long getMemberId();

    String getLoginCode();

    String getMemberEmail();

    String getMemberMobile();

}
