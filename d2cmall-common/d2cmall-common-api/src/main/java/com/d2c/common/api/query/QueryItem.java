package com.d2c.common.api.query;

import com.d2c.common.api.query.enums.OperType;
import com.d2c.common.api.query.enums.ShowType;
import com.d2c.common.api.query.enums.SortType;
import com.d2c.common.api.query.enums.ValueType;

import java.io.Serializable;
import java.util.List;

public class QueryItem implements Serializable {

    private static final long serialVersionUID = 1L;
    private String name;
    private String showName;
    private Object value;
    private List<QueryItem> children;
    private OperType operator = OperType.EQUAL;
    private ShowType showType = ShowType.TEXT;
    private ValueType valueType = ValueType.STRING;
    private SortType sortType = SortType.DESC;
    private List<QueryParam> params;

    public QueryItem() {
    }

    public QueryItem(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public QueryItem(String name, Object value, OperType oper) {
        this.name = name;
        this.value = value;
        this.operator = oper;
    }

    public QueryItem(String name, OperType oper) {
        this.name = name;
        this.operator = oper;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
    // ***********************************************************************

    public OperType getOperator() {
        return operator;
    }

    public void setOperator(OperType operator) {
        this.operator = operator;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ValueType getValueType() {
        return valueType;
    }

    public void setValueType(ValueType valueType) {
        this.valueType = valueType;
    }

    public ShowType getShowType() {
        return showType;
    }

    public void setShowType(ShowType showType) {
        this.showType = showType;
    }

    public SortType getSortType() {
        return sortType;
    }

    public void setSortType(SortType sortType) {
        this.sortType = sortType;
    }

    public List<QueryItem> getChildren() {
        return children;
    }

    public void setChildren(List<QueryItem> children) {
        this.children = children;
    }

    public List<QueryParam> getParams() {
        return params;
    }

    public void setParams(List<QueryParam> params) {
        this.params = params;
    }

    public QueryParam getParam(String key) {
        if (params != null) {
            for (QueryParam param : params) {
                if (param.getName().equals(key)) {
                    return param;
                }
            }
        }
        return null;
    }

}
