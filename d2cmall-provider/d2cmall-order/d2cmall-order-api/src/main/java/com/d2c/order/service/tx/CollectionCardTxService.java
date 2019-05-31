package com.d2c.order.service.tx;

import com.alibaba.fastjson.JSONObject;
import com.d2c.member.model.CollectionCardRecord.StageEnum;
import com.d2c.member.model.MemberInfo;

public interface CollectionCardTxService {

    int doSendStageAward(MemberInfo memberInfo, StageEnum stage, JSONObject award);

}
