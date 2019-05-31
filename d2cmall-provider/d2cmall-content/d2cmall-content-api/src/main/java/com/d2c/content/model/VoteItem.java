package com.d2c.content.model;

import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;

/**
 * 实体类 -投票明细
 */
@Table(name = "v_vote_item")
public class VoteItem extends PreUserDO {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 会员Id
     */
    private Long memberId;
    /**
     * 定义Id
     */
    private Long defId;
    /**
     * 定义标题
     */
    private String defTitle;
    /**
     * 选项
     */
    private Long selectionId;
    /**
     * 选项文字
     */
    private String selectName;
    /**
     * 系数
     */
    private Double coef;
    /**
     * 选项图片
     */
    private String pic;
    /**
     * 选项状态 1整除，-1删除
     */
    private Integer status;

    public VoteItem() {
    }

    public VoteItem(VoteSelection voteSelection) {
        this.defId = voteSelection.getDefId();
        this.defTitle = voteSelection.getDefTitle();
        this.selectionId = voteSelection.getId();
        this.selectName = voteSelection.getName();
        this.coef = voteSelection.getCoef();
        this.pic = voteSelection.getPic();
        this.status = 1;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getDefId() {
        return defId;
    }

    public void setDefId(Long defId) {
        this.defId = defId;
    }

    public String getDefTitle() {
        return defTitle;
    }

    public void setDefTitle(String defTitle) {
        this.defTitle = defTitle;
    }

    public Long getSelectionId() {
        return selectionId;
    }

    public void setSelectionId(Long selectionId) {
        this.selectionId = selectionId;
    }

    public String getSelectName() {
        return selectName;
    }

    public void setSelectName(String selectName) {
        this.selectName = selectName;
    }

    public Double getCoef() {
        return coef;
    }

    public void setCoef(Double coef) {
        this.coef = coef;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
