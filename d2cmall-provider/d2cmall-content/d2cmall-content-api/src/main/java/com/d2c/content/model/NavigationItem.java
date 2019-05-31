package com.d2c.content.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;
import org.springframework.util.StringUtils;

import javax.persistence.Table;

/**
 * 实体类 -导航明细
 */
@Table(name = "v_navigation_item")
public class NavigationItem extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 标题
     */
    @AssertColumn("推荐标题不能为空")
    private String title;
    /**
     * 导航
     */
    @AssertColumn("导航ID不能为空")
    private Long navId;
    /**
     * 绑定url
     */
    private String url;
    /**
     * 排序
     */
    private Integer sequence = 0;
    /**
     * 状态：0停用、1启用、-1删除
     */
    private Integer status = 1;
    /**
     * 样式
     */
    private String cssStyle;
    /**
     * 导航图片
     */
    private String pic;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getNavId() {
        return navId;
    }

    public void setNavId(Long navId) {
        this.navId = navId;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCssStyle() {
        return cssStyle;
    }

    public void setCssStyle(String cssStyle) {
        this.cssStyle = cssStyle;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("id", this.getId());
        obj.put("title", this.getTitle());
        obj.put("url", this.getUrl());
        obj.put("pic", !StringUtils.isEmpty(this.getPic()) ? this.getPic() : "");
        return obj;
    }

}
