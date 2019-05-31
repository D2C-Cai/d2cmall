package com.d2c.content.search.query;

import java.io.Serializable;

public class QuizBankSearchBean implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 类型
     */
    private Integer type;
    /**
     * 状态
     */
    private Integer mark;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

}
