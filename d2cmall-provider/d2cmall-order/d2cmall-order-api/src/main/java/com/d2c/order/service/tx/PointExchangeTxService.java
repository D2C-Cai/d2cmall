package com.d2c.order.service.tx;

import com.alibaba.fastjson.JSONObject;
import com.d2c.member.model.MemberInfo;
import com.d2c.product.model.PointProduct;

import java.util.Map;

public interface PointExchangeTxService {

    /**
     * 积分兑换
     *
     * @param pointProduct
     * @param memberInfo
     * @param quantity
     * @return
     */
    Map<String, JSONObject> doExchange(PointProduct pointProduct, MemberInfo memberInfo, Integer quantity);

}
