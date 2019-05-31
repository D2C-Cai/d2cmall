package com.d2c.order.service.tx;

import com.d2c.order.model.ShareRedPackets;
import com.d2c.order.model.ShareRedPacketsGroup;
import com.d2c.order.model.ShareRedPacketsPromotion;

public interface ShareRedTxService {

    /**
     * 新增参团明细
     *
     * @param shareRedPackets
     * @return
     */
    ShareRedPackets insertShareRedPackets(ShareRedPackets shareRedPackets);

    /**
     * 新建拼团
     *
     * @param memberId
     * @return
     */
    ShareRedPacketsGroup createGroup(Long memberId, ShareRedPacketsPromotion shareRedPacketsPromotion);

}
