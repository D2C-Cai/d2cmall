package com.d2c.common.mq.listener;

import org.springframework.jms.listener.DefaultMessageListenerContainer;

public class SimpleMqListenerContainer extends DefaultMessageListenerContainer {

    @Override
    public String toString() {
        return "监听控制器:" + this.getDestinationDescription();
    }

}
