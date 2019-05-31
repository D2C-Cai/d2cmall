package com.d2c.content.query;

import com.d2c.common.api.query.model.BaseQuery;

public class NavigationItemSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 标题
     */
    private String title;
    /**
     * 绑定url
     */
    private String url;
    /**
     * 导航
     */
    private Long navId;
    /**
     * 状态：0停用,1启用,-1删除
     */
    private Integer gtStatus = 0;

    public Long getNavId() {
        return navId;
    }

    public void setNavId(Long navId) {
        this.navId = navId;
    }

    public Integer getGtStatus() {
        return gtStatus;
    }

    public void setGtStatus(Integer gtStatus) {
        this.gtStatus = gtStatus;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
