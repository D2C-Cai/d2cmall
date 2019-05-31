package com.d2c.content.dto;

import com.d2c.content.model.FieldDefine;
import com.d2c.content.model.PageDefine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageDefineDto extends PageDefine {

    private static final long serialVersionUID = 1L;
    private Map<String, FieldDefine> fieldDefs;
    private List<FieldDefine> fieldList;

    public Map<String, FieldDefine> getFieldDefs() {
        if (fieldDefs == null) {
            fieldDefs = new HashMap<String, FieldDefine>();
        }
        return fieldDefs;
    }

    public void setFieldDefs(Map<String, FieldDefine> fieldDefs) {
        this.fieldDefs = fieldDefs;
    }

    public List<FieldDefine> getFieldList() {
        if (fieldList == null) {
            fieldList = new ArrayList<FieldDefine>();
        }
        return fieldList;
    }

    public void setFieldList(List<FieldDefine> fieldList) {
        this.fieldList = fieldList;
    }

}
