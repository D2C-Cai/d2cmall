package com.d2c.logger.search.support;

import java.io.Serializable;
import java.util.Date;

public class MessageReadTimeHelp implements Serializable {

    private static final long serialVersionUID = 1L;
    private Date majorTypeTime6;
    private Date majorTypeTime7;

    public MessageReadTimeHelp() {
    }

    public Date getMajorTypeTime6() {
        return majorTypeTime6;
    }

    public void setMajorTypeTime6(Long majorTypeTime6) {
        this.majorTypeTime6 = new Date(majorTypeTime6);
    }

    public Date getMajorTypeTime7() {
        return majorTypeTime7;
    }

    public void setMajorTypeTime7(Long majorTypeTime7) {
        this.majorTypeTime7 = new Date(majorTypeTime7);
    }

}
