package com.d2c.order.service;

import com.d2c.order.model.Complainant;

public interface ComplainantService {

    /**
     * 以memberId（会员id）作为参数，从crm_complainant表中获取投诉人信息。
     *
     * @param memberId
     * @return Complainant
     */
    Complainant findByMemberId(Long memberId);

    /**
     * 以Complainant对象作为参数，将投诉人信息插入crm_complainant表中。
     *
     * @param complainant
     * @return Complainant
     */
    Complainant insert(Complainant complainant);

    /**
     * 以Complainant对象作为参数，修改crm_complainant表中的投诉人相关信息。
     *
     * @param complainant
     * @return int
     */
    int update(Complainant complainant);

}
