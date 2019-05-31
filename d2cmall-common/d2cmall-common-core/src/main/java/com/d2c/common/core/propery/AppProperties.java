package com.d2c.common.core.propery;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppProperties {

    @Value("${app.debug:false}")
    private Boolean debug;
    @Value("${app.sign:true}")
    private Boolean sign;

    public Boolean getDebug() {
        if (debug == null) {
            debug = false;
        }
        return debug;
    }

    public void setDebug(Boolean debug) {
        this.debug = debug;
    }

    public Boolean getSign() {
        return sign;
    }

    public void setSign(Boolean sign) {
        this.sign = sign;
    }

}
