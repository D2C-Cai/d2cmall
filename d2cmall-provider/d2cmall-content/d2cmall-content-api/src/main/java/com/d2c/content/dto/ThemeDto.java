package com.d2c.content.dto;

import com.d2c.content.model.Theme;

public class ThemeDto extends Theme {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String tagName;
    private Integer fix;

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Integer getFix() {
        return fix;
    }

    public void setFix(Integer fix) {
        this.fix = fix;
    }

}
