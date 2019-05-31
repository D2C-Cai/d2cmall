package com.d2c.content.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;

/**
 * 广告资源位
 *
 * @author Lain
 */
@Table(name = "v_ad_resource")
public class AdResource extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 标题
     */
    private String title;
    /**
     * 广告图片
     */
    private String pic;
    /**
     * 链接
     */
    private String url;
    /**
     * 显示的短标题
     */
    private String shotTitle;
    /**
     * 广告描述
     */
    private String description;
    /**
     * 视频
     */
    private String video;
    /**
     * 视频图片
     */
    private String videoPic;
    /**
     * 状态
     */
    private Integer status;
    /**
     * APP的频道
     */
    private String appChannel = AppChannelEnum.MAIN.name();
    /**
     * 广告位
     */
    private String type;

    public String getAppChannel() {
        return appChannel;
    }

    public void setAppChannel(String appChannel) {
        this.appChannel = appChannel;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
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

    public String getShotTitle() {
        return shotTitle;
    }

    public void setShotTitle(String shotTitle) {
        this.shotTitle = shotTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAppChannelName() {
        return AppChannelEnum.valueOf(this.getAppChannel()).getDisplay();
    }

    public String getTypeName() {
        return TypeEnum.valueOf(this.getType()).getDisplay();
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getVideoPic() {
        return videoPic;
    }

    public void setVideoPic(String videoPic) {
        this.videoPic = videoPic;
    }

    public String[] getPicList() {
        if (this.pic != null) {
            return this.pic.split(",");
        }
        return null;
    }

    public String[] getUrlList() {
        Integer size = getPicList().length;
        if (this.url != null) {
            String[] urls = this.url.split(",");
            if (size != null && size != urls.length) {
                String[] str = new String[size];
                System.arraycopy(urls, 0, str, 0, urls.length);
                processStr(str, urls.length);
                return str;
            }
            return urls;
        }
        return null;
    }

    public String[] getShotTitleList() {
        Integer size = getPicList().length;
        if (this.shotTitle != null) {
            String[] shotTitles = this.shotTitle.split(",");
            if (size != null && size != shotTitles.length) {
                String[] str = new String[size];
                System.arraycopy(shotTitles, 0, str, 0, shotTitles.length);
                processStr(str, shotTitles.length);
                return str;
            }
            return shotTitles;
        }
        return null;
    }

    public String[] getDescriptionList() {
        Integer size = getPicList().length;
        if (this.description != null) {
            String[] descriptions = this.description.split(",");
            if (size != null && size != descriptions.length) {
                String[] str = new String[size];
                System.arraycopy(descriptions, 0, str, 0, descriptions.length);
                processStr(str, descriptions.length);
                return str;
            }
            return descriptions;
        }
        return null;
    }

    private void processStr(String[] str, int startIndex) {
        if (str.length > startIndex) {
            do {
                str[startIndex] = "";
                startIndex++;
            } while (str.length > startIndex);
        }
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("id", this.getId());
        json.put("title", this.getTitle());
        json.put("pic", this.getPic());
        json.put("url", this.getUrl());
        String[] pics = this.getPicList();
        json.put("pics", pics);
        json.put("picsUrl", this.getUrlList());
        json.put("shotTitles", this.getShotTitleList());
        json.put("descriptions", this.getDescriptionList());
        json.put("video", this.getVideo());
        json.put("videoPic", this.getVideoPic());
        return json;
    }

    public enum TypeEnum {
        SHARE_1("晒单有礼"), PRODUCTREPORT_1("商品报告"), BRANDCATEGORY_1("品牌分类"), FEEDBACK("投诉&反馈"),
        BARGAIN("砍价活动"), FLASHPRODUCT("限时购商品"), FLASHBRAND("限时购品牌"), QUESTIONNAIRE("问卷调查"),
        CROSS("跨境电商须知"), NEWUP("商品上新"), POINTPRODUCT("积分商城"), RECPRO("活动推荐"), PAYSUCCESS("支付成功"), TOPBACK("顶部背景");
        private String display;

        TypeEnum(String display) {
            this.display = display;
        }

        public String getDisplay() {
            return display;
        }

        public void setDisplay(String display) {
            this.display = display;
        }
    }

    public enum AppChannelEnum {
        MAIN("首页"), SQUARE("广场"), THEME("专题"), LIVE("直播"),
        BRAND("设计师"), NAV("商品分类"), PRODUCT("商品详细"),
        MY("我的"), FLASHPROMOTION("限时购"), ORDER("订单业务");
        private String display;

        AppChannelEnum(String display) {
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
