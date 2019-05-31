package com.d2c.product.search.model;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.util.Date;

public class SearcherDesigner implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * ID
     */
    private Long id;
    /**
     * 编码
     */
    private String code;
    /**
     * 设计师编码
     */
    private String brandCode;
    /**
     * 品牌名
     */
    private String name;
    /**
     * 品牌介绍
     */
    private String intro;
    /**
     * 设计师ID
     */
    private Long designersId;
    /**
     * 设计师名字
     */
    private String designers;
    /**
     * 地区
     */
    private String country;
    /**
     * 设计风格
     */
    private String style;
    /**
     * 分类
     */
    private String designArea;
    /**
     * 价格段
     */
    private String price;
    /**
     * A-Z
     */
    private String pageGroup;
    /**
     * 运营小组
     */
    private String operation;
    /**
     * 市场定位
     */
    private String market;
    /**
     * 搜索关键字
     */
    private String seo;
    /**
     * 口号
     */
    private String slogan;
    /**
     * 代表作
     */
    private String introPic;
    /**
     * 形象大图
     */
    private String bigPic;
    /**
     * 头像图片
     */
    private String headPic;
    /**
     * 手机端签名图
     */
    private String signPic;
    /**
     * 背景图片
     */
    private String backPic;
    /**
     * 关注数量
     */
    private Integer fans;
    /**
     * 虚拟关注数量
     */
    private Integer vfans;
    /**
     * 展示关注数量
     */
    private Integer tfans;
    /***
     * 是否可以门店试衣：0不可以，1可以
     */
    private Integer subscribe;
    /**
     * 是否推荐
     */
    private Integer recommend;
    /**
     * 是否上架
     */
    private Integer mark;
    /**
     * 标签，多个按","分隔
     */
    private String tags;
    /**
     * 排序日期
     */
    private Date sortDate;
    /**
     * 绑定域名，默认id.d2cmall.com，唯一键
     */
    private String domain;
    /**
     * 视频
     */
    private String video;
    /**
     * 视频图片
     */
    private String videoPic;
    /**
     * 是否是当前用户关注的设计师
     */
    private Integer attentioned = 0;
    /**
     * app作品介紹
     */
    private String appIntro;
    /**
     * 品牌30天销量
     */
    private Integer sales;
    /**
     * 二维码背景图
     */
    private String backgroundUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesigners() {
        return designers;
    }

    public void setDesigners(String designers) {
        this.designers = designers;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getDesignArea() {
        return designArea;
    }

    public void setDesignArea(String designArea) {
        this.designArea = designArea;
    }

    public String getPageGroup() {
        return pageGroup;
    }

    public void setPageGroup(String pageGroup) {
        this.pageGroup = pageGroup;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getSeo() {
        return seo;
    }

    public void setSeo(String seo) {
        this.seo = seo;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String getIntroPic() {
        return introPic;
    }

    public void setIntroPic(String introPic) {
        this.introPic = introPic;
    }

    public String getBigPic() {
        return bigPic;
    }

    public void setBigPic(String bigPic) {
        this.bigPic = bigPic;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public String getSignPic() {
        return signPic;
    }

    public void setSignPic(String signPic) {
        this.signPic = signPic;
    }

    public String getBackPic() {
        return backPic;
    }

    public void setBackPic(String backPic) {
        this.backPic = backPic;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Date getSortDate() {
        return sortDate;
    }

    public void setSortDate(Date sortDate) {
        this.sortDate = sortDate;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Integer getFans() {
        return fans;
    }

    public void setFans(Integer fans) {
        this.fans = fans;
    }

    public Integer getVfans() {
        return vfans;
    }

    public void setVfans(Integer vfans) {
        this.vfans = vfans;
    }

    public Integer getTfans() {
        return tfans;
    }

    public void setTfans(Integer tfans) {
        this.tfans = tfans;
    }

    public Integer getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(Integer subscribe) {
        this.subscribe = subscribe;
    }

    public Integer getRecommend() {
        return recommend;
    }

    public void setRecommend(Integer recommend) {
        this.recommend = recommend;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    public Integer getAttentioned() {
        return attentioned;
    }

    public void setAttentioned(Integer attentioned) {
        this.attentioned = attentioned;
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

    public String getBrandCode() {
        return brandCode;
    }

    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode;
    }

    public String getAppIntro() {
        return appIntro;
    }

    public void setAppIntro(String appIntro) {
        this.appIntro = appIntro;
    }

    public String[] getSignPics() {
        if (this.signPic != null) {
            return this.signPic.split(",");
        }
        return null;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBackgroundUrl() {
        return backgroundUrl;
    }

    public void setBackgroundUrl(String backgroundUrl) {
        this.backgroundUrl = backgroundUrl;
    }

    public Long getDesignersId() {
        return designersId;
    }

    public void setDesignersId(Long designersId) {
        this.designersId = designersId;
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("id", this.getId());
        obj.put("introPic", this.getSignPic() == null ? "" : this.getSignPic());
        obj.put("signPics", this.getSignPics() == null ? "" : this.getSignPics());
        obj.put("headPic", this.getHeadPic() == null ? "" : this.getHeadPic());
        obj.put("brand", this.getDesigners());
        obj.put("name", this.getName());
        obj.put("slogan", this.getSlogan());
        obj.put("intro", this.getIntro() == null ? "" : this.getIntro());
        obj.put("likeCount", this.getTfans());
        obj.put("fans",
                (this.getFans() == null ? 0 : this.getFans()) + (this.getVfans() == null ? 0 : this.getVfans()));
        obj.put("recommend", this.getRecommend());
        obj.put("video", this.getVideo());
        obj.put("videoPic", this.getVideoPic());
        obj.put("attentioned", this.getAttentioned());
        obj.put("appIntro", this.getAppIntro());
        obj.put("backgroundUrl", this.getBackgroundUrl());
        obj.put("designersId", this.getDesignersId());
        return obj;
    }

    // A-Z 显示
    public JSONObject toSimpleJson() {
        JSONObject obj = new JSONObject();
        obj.put("id", this.getId());
        obj.put("name", this.getName() == null ? "" : this.getName());
        obj.put("slogan", this.getSlogan() == null ? "" : this.getSlogan());
        obj.put("recommend", this.getRecommend() == null ? 0 : this.getRecommend());
        obj.put("designer", this.getDesigners() == null ? "" : this.getDesigners());
        obj.put("designersId", this.getDesignersId());
        return obj;
    }

    public JSONObject toRecommendJson() {
        JSONObject obj = new JSONObject();
        obj.put("id", this.getId());
        obj.put("name", this.getName());
        obj.put("headPic", this.getHeadPic());
        obj.put("introPic", this.getSignPics() != null && this.getSignPics().length > 0 ? this.getSignPics()[0] : "");
        obj.put("designersId", this.getDesignersId());
        return obj;
    }

}
