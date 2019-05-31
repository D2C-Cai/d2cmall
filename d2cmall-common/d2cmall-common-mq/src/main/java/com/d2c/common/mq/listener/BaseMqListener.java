package com.d2c.common.mq.listener;

import com.d2c.common.mq.enums.MqEnum;

import javax.jms.MessageListener;

public interface BaseMqListener extends MessageListener {

    public MqEnum getMqEnum();

}
