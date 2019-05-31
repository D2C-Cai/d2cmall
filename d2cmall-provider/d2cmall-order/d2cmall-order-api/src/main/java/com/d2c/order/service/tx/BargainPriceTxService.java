package com.d2c.order.service.tx;

import com.d2c.member.dto.MemberDto;

public interface BargainPriceTxService {

    /**
     * 领取红包
     *
     * @param memberDto
     * @return
     */
    int doRedPackets(MemberDto memberDto);

}
