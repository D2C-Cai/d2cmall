package com.d2c.common.core.excel;

import java.io.Serializable;

public class ExcelColumn implements Serializable {

    private static final long serialVersionUID = 1L;
    private String name;
    private String title;

    public ExcelColumn(String name, String title) {
        this.name = name;
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
