package com.d2c.content.dto;

import com.d2c.content.model.Section;
import com.d2c.content.model.SectionValue;

import java.util.List;

public class SectionDto extends Section {

    private static final long serialVersionUID = 1L;
    /**
     * 版块内容
     */
    private List<SectionValue> sectionValues;

    public List<SectionValue> getSectionValues() {
        return sectionValues;
    }

    public void setSectionValues(List<SectionValue> sectionValues) {
        this.sectionValues = sectionValues;
    }

}
