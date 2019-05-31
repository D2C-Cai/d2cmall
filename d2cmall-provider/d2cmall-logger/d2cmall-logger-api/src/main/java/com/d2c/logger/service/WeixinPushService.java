package com.d2c.logger.service;

import com.d2c.logger.third.wechat.WeixinPushEntity;

public interface WeixinPushService {

    void send(WeixinPushEntity msgObj);

}
