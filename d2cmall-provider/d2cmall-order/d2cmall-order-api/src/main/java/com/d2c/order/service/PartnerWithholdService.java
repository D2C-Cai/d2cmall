package com.d2c.order.service;

import com.d2c.order.model.PartnerWithhold;

public interface PartnerWithholdService {

    PartnerWithhold insert(PartnerWithhold partnerWithhold);

    PartnerWithhold findBySn(String sn);

}
