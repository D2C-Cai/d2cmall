package com.d2c.content.model;

import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;

/**
 * 实体类 -问卷调查
 */
@Table(name = "v_quiz_bank")
public class QuizBank extends PreUserDO {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 题目
     */
    @AssertColumn("题目标题不能为空")
    private String title;
    /**
     * 选项
     */
    @AssertColumn("选项不能为空")
    private String choice;
    /**
     * 答案
     */
    @AssertColumn("正确答案不能为空")
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
    private Integer mark = 1;

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

}
