package com.d2c.content.dto;

import com.d2c.content.model.VoteSelection;

public class VoteSelectionDto extends VoteSelection {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Long selectionId;
    /**
     * 票数
     */
    private Integer count;
    /**
     * 票数*系数
     */
    private Integer vCount;
    /**
     * 名次
     */
    private Integer rank;
    /**
     * 选项名称
     */
    private String selectName;

    public Long getSelectionId() {
        return selectionId;
    }

    public void setSelectionId(Long selectionId) {
        this.selectionId = selectionId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getvCount() {
        return vCount;
    }

    public void setvCount(Integer vCount) {
        this.vCount = vCount;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getSelectName() {
        return selectName;
    }

    public void setSelectName(String selectName) {
        this.selectName = selectName;
    }

}
