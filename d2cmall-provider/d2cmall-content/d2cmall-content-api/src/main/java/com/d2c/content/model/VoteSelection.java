package com.d2c.content.model;

import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;

/**
 * 实体类 -投票选项
 */
@Table(name = "v_vote_selection")
public class VoteSelection extends PreUserDO {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 定义ID
     */
    private Long defId;
    /**
     * 定义标题
     */
    private String defTitle;
    /**
     * 选项文字
     */
    private String name;
    /**
     * 图片
     */
    private String pic;
    /**
     * 系数
     */
    private Double coef;
    /**
     * 链接
     */
    private String url;
    /**
     * 状态1 正常-1删除
     */
    private Integer status;

    public Long getDefId() {
        return defId;
    }

    public void setDefId(Long defId) {
        this.defId = defId;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Double getCoef() {
        return coef;
    }

    public void setCoef(Double coef) {
        this.coef = coef;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDefTitle() {
        return defTitle;
    }

    public void setDefTitle(String defTitle) {
        this.defTitle = defTitle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
