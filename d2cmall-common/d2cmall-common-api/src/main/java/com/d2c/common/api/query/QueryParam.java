package com.d2c.common.api.query;

import com.d2c.common.api.annotation.search.SearchParam;

import java.io.Serializable;

public class QueryParam implements Serializable {

    private static final long serialVersionUID = 1L;
    private String name;
    private String value;
    private String[] values;
    public QueryParam(SearchParam p) {
        this.name = p.name();
        this.value = p.value();
        this.values = p.values();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String[] getValues() {
        return values;
    }

    public void setValues(String[] values) {
        this.values = values;
    }

}
