package com.d2c.content.query;

import com.d2c.common.api.query.model.BaseQuery;

public class FieldContentSearcher extends BaseQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 页面定义ID
     */
    private Long modulePageId;
    /**
     * 组名
     */
    private String groupName;
    /**
     * 标题
     */
    private String title;
    /**
     * 绑定地址
     */
    private String bindUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBindUrl() {
        return bindUrl;
    }

    public void setBindUrl(String bindUrl) {
        this.bindUrl = bindUrl;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Long getModulePageId() {
        return modulePageId;
    }

    public void setModulePageId(Long modulePageId) {
        this.modulePageId = modulePageId;
    }

}
