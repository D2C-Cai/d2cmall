package com.d2c.order.model.base;

import java.io.Serializable;

public interface IAddressInterface extends Serializable {

    String getReciver();

    String getContact();

    String getProvince();

    String getCity();

    String getDistrict();

    String getAddress();

}
