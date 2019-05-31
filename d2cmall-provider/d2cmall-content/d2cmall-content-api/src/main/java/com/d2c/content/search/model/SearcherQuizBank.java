package com.d2c.content.search.model;

import java.io.Serializable;
import java.util.Date;

public class SearcherQuizBank implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 题目
     */
    private String title;
    /**
     * 选项
     */
    private String choice;
    /**
     * 答案
     */
    private String answer;
    /**
     * 类型
     */
    private Integer type;
    /**
     * 题目图片
     */
    private String pic;
    /**
     * 0，下架;1,上架
     */
    private Integer mark = 0;
    /**
     * 修改日期
     */
    private Date modifyDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

}
