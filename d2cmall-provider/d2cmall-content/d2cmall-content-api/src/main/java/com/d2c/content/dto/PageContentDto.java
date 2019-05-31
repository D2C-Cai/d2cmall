package com.d2c.content.dto;

import com.d2c.content.model.FieldContent;
import com.d2c.content.model.PageContent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageContentDto extends PageContent {

    private static final long serialVersionUID = 1L;
    private Map<String, List<FieldContent>> blocks;

    public Map<String, List<FieldContent>> getBlocks() {
        if (blocks == null) {
            blocks = new HashMap<String, List<FieldContent>>();
        }
        return blocks;
    }

    public void setBlocks(Map<String, List<FieldContent>> blocks) {
        this.blocks = blocks;
    }

}
