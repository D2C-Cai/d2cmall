package com.d2c.member.search.query;

import java.io.Serializable;

public class TopicSearchBean implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 状态
     */
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
