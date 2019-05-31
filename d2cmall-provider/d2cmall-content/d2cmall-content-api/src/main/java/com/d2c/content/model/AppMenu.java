package com.d2c.content.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;

/**
 * 实体类 -app我的菜单
 */
@Table(name = "v_app_menu")
public class AppMenu extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 按钮对应的操作名称
     */
    private String name;
    /**
     * 按钮图标的url
     */
    private String iconUrl;
    /**
     * 按钮跳转的url
     */
    private String url;
    /**
     * 按钮状态 0,关闭 1开启
     */
    private Integer status;
    /**
     * 页面顺序，按从小到大排
     */
    private Integer sequence;
    /**
     * 新增版本号
     */
    private String version = "v3";
    /**
     * 会员类型(type0 全部 | type1~type5 1.普通会员 2.设计师 3.D2C官方 4.经销商 5.D2C达人 |
     * typep1,typep2分销1级和分销2级)
     */
    private String memberType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMemberType() {
        return memberType;
    }

    public void setMemberType(String memberType) {
        this.memberType = memberType;
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("id", this.getId());
        obj.put("name", this.getName());
        obj.put("iconUrl", this.getIconUrl());
        obj.put("url", this.getUrl());
        obj.put("status", this.getStatus());
        obj.put("sequence", this.getSequence());
        obj.put("version", this.getVersion());
        return obj;
    }

}
