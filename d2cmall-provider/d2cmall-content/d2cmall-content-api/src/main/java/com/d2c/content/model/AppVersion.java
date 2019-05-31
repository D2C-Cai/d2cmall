package com.d2c.content.model;

import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;

/**
 * 实体类 -app版本历史
 * <p>
 * 查询当前版本号之后的createDate中的force是否存在强制
 */
@Table(name = "v_app_version")
public class AppVersion extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 版本号
     */
    @AssertColumn("版本号不能为空")
    private String version;
    /**
     * 是否强制：0不强制 1强制
     */
    private Integer upgrade = 0;
    /**
     * 说明
     */
    private String info;
    /**
     * IOS和Android
     */
    @AssertColumn("设备号不能为空")
    private String device;
    /**
     * 类型 pay付费版；free免费版
     */
    private String type;
    /**
     * 跳转url
     */
    private String url;
    /**
     * 安装包大小
     */
    private String size;
    /**
     * 图标路径
     */
    private String iconUrl;
    /**
     * 版本数字化
     */
    private Long digit;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getUpgrade() {
        return upgrade;
    }

    public void setUpgrade(Integer upgrade) {
        this.upgrade = upgrade;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getDigit() {
        return digit;
    }

    public void setDigit(Long digit) {
        this.digit = digit;
    }

    public String getTypeName() {
        return VersionTypeEnum.valueOf(this.type).getDisplay();
    }

    public enum VersionTypeEnum {
        pay("付费版"), free("免费版"), personal("个人版"), boss("商家版");
        private String display;

        VersionTypeEnum(String display) {
            this.display = display;
        }

        public String getDisplay() {
            return display;
        }

        public void setDisplay(String display) {
            this.display = display;
        }
    }

}
