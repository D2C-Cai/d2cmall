package com.d2c.content.query;

import com.d2c.common.api.query.model.BaseQuery;

public class QuizBankSearcher extends BaseQuery {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 题目
     */
    private String title;
    /**
     * 类型
     */
    private Integer type;
    /**
     * 状态
     */
    private Integer mark;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

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
