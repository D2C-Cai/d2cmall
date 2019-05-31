package com.d2c.product.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.model.PreUserDO;
import org.springframework.util.StringUtils;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.*;

/**
 * 品牌
 */
@Table(name = "p_brand")
public class Brand extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 编码
     */
    private String code;
    /**
     * 品牌编码
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
     * 店铺顶通
     */
    private String designerStore;
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
     * 手机签名图
     */
    private String signPic;
    /**
     * 背景图片
     */
    private String backPic;
    /**
     * 视频地址
     */
    private String video;
    /**
     * 视频图片
     */
    private String videoPic;
    /**
     * 设计师详细介绍
     */
    private String description;
    /**
     * pc自定义首页
     */
    private String moreIntro;
    /**
     * app自定义首页
     */
    private String appIntro;
    /**
     * 二维码背景图
     */
    private String backgroundUrl;
    /**
     * 关注数量
     */
    private Integer fans = 0;
    /**
     * 虚拟关注数量
     */
    private Integer vfans = 0;
    /**
     * 展示关注数量
     */
    @Transient
    private Integer tfans = 0;
    /**
     * 是否可以门店试衣：0不可以，1可以
     */
    private Integer subscribe = 1;
    /**
     * 是否上架：0下架，1上架
     */
    private Integer mark = 1;
    /**
     * 是否支持售后：1支持，0不支持
     */
    private Integer after = 1;
    /**
     * 是否支持货到付款：1支持，0不支持
     */
    private Integer cod = 1;
    /**
     * 是否推荐：0不推荐，1推荐
     */
    private Integer recommend = 0;
    /**
     * 是否设计师直发：0否，1是
     */
    private Integer direct = 0;
    /**
     * 上架时间
     */
    private Date upMarketDate;
    /**
     * 下架时间
     */
    private Date downMarketDate;
    /**
     * 最近一次上架操作人
     */
    private String upMan;
    /**
     * 最近一次下架操作人
     */
    private String downMan;
    /**
     * 标签，多个按","分隔
     */
    private String tags;
    /**
     * 排序日期
     */
    private Date sortDate = new Date();
    /**
     * 绑定域名，默认id.d2cmall.com，唯一键
     */
    private String domain;
    /**
     * 收件人信息
     */
    private String consignee;
    /**
     * 收件人地址
     */
    private String address;
    /**
     * 收件人电话
     */
    private String mobile;
    /**
     * 品牌近30天销量
     */
    private Integer sales = 0;
    /**
     * 优先结算品牌
     */
    private Integer prior;

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

    public String[] getDesginAreas() {
        if (this.getDesignArea() != null) {
            return this.designArea.split(",");
        }
        return null;
    }

    public void setDesginAreas() {
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getIntro() {
        if (intro == null)
            intro = "";
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getFans() {
        return fans;
    }

    public void setFans(Integer fans) {
        this.fans = fans;
    }

    public String getPageGroup() {
        return pageGroup;
    }

    public void setPageGroup(String pageGroup) {
        this.pageGroup = pageGroup;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    public String getDesigners() {
        return designers;
    }

    public void setDesigners(String designers) {
        this.designers = designers;
    }

    public Date getSortDate() {
        return sortDate;
    }

    public void setSortDate(Date sortDate) {
        this.sortDate = sortDate;
    }

    public String getMoreIntro() {
        if (moreIntro == null) {
            moreIntro = "";
        }
        return moreIntro;
    }

    public void setMoreIntro(String moreIntro) {
        this.moreIntro = moreIntro;
    }

    public String getDesignerStore() {
        return designerStore;
    }

    public void setDesignerStore(String designerStore) {
        this.designerStore = designerStore;
    }

    public Integer getVfans() {
        return vfans;
    }

    public void setVfans(Integer vfans) {
        this.vfans = vfans;
    }

    public String getSeo() {
        return seo;
    }

    public void setSeo(String seo) {
        this.seo = seo;
    }

    public Integer getTfans() {
        return this.vfans + this.fans;
    }

    public void setTfans(Integer tfans) {
        this.tfans = tfans;
    }

    public void setTfans() {
    }

    public Integer getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(Integer subscribe) {
        this.subscribe = subscribe;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
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

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Integer getRecommend() {
        return recommend;
    }

    public void setRecommend(Integer recommend) {
        this.recommend = recommend;
    }

    public Integer getAfter() {
        return after;
    }

    public void setAfter(Integer after) {
        this.after = after;
    }

    public Integer getCod() {
        return cod;
    }

    public void setCod(Integer cod) {
        this.cod = cod;
    }

    public Integer getDirect() {
        return direct;
    }

    public void setDirect(Integer direct) {
        this.direct = direct;
    }

    public Date getUpMarketDate() {
        return upMarketDate;
    }

    public void setUpMarketDate(Date upMarketDate) {
        this.upMarketDate = upMarketDate;
    }

    public Date getDownMarketDate() {
        return downMarketDate;
    }

    public void setDownMarketDate(Date downMarketDate) {
        this.downMarketDate = downMarketDate;
    }

    public String getUpMan() {
        return upMan;
    }

    public void setUpMan(String upMan) {
        this.upMan = upMan;
    }

    public String getDownMan() {
        return downMan;
    }

    public void setDownMan(String downMan) {
        this.downMan = downMan;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Long getDesignersId() {
        return designersId;
    }

    public void setDesignersId(Long designersId) {
        this.designersId = designersId;
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

    public String getAppIntro() {
        return appIntro;
    }

    public void setAppIntro(String appIntro) {
        this.appIntro = appIntro;
    }

    public String getBrandCode() {
        return brandCode;
    }

    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public Integer getPrior() {
        return prior;
    }

    public void setPrior(Integer prior) {
        this.prior = prior;
    }

    public List<Map<String, Integer>> getDesignAreaList() {
        if (this.designArea == null || this.designArea.length() == 0) {
            return null;
        }
        String[] str = this.designArea.split(",");
        List<Map<String, Integer>> list = new ArrayList<>();
        for (int i = 0; i < str.length; i++) {
            Map<String, Integer> map = new HashMap<>();
            map.put("id", Integer.parseInt(str[i]));
            list.add(map);
        }
        return list;
    }

    public void setDesignAreaList() {
    }

    public String[] getSignPics() {
        if (this.signPic != null) {
            return this.signPic.split(",");
        }
        return null;
    }

    public void setSignPics() {
    }

    public String getBackgroundUrl() {
        return backgroundUrl;
    }

    public void setBackgroundUrl(String backgroundUrl) {
        this.backgroundUrl = backgroundUrl;
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("id", this.getId());
        obj.put("mark", this.getMark());
        obj.put("introPic", this.getSignPic() == null ? "" : this.getSignPic());
        obj.put("signPics", this.getSignPics() == null ? "" : this.getSignPics());
        obj.put("headPic", this.getHeadPic() == null ? "" : this.getHeadPic());
        obj.put("brand", this.getDesigners() == null ? "" : this.getDesigners());
        obj.put("name", this.getName() == null ? "" : this.getName());
        obj.put("slogan", this.getSlogan() == null ? "" : this.getSlogan());
        obj.put("intro", this.getIntro() == null ? "" : this.getIntro());
        obj.put("likeCount",
                (this.getFans() == null ? 0 : this.getFans()) + (this.getVfans() == null ? 0 : this.getVfans()));
        obj.put("recommend", this.getRecommend() == null ? 0 : this.getRecommend());
        obj.put("isCod", this.getCod());
        obj.put("isAfter", this.getAfter());
        obj.put("isSubscribe", this.getSubscribe());
        obj.put("video", this.getVideo());
        obj.put("videoPic", this.getVideoPic());
        obj.put("appIntro", this.getAppIntro());
        obj.put("hasAppIntro", StringUtils.isEmpty(this.getAppIntro()) ? 0 : 1);
        obj.put("backgroundUrl", this.getBackgroundUrl());
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
        obj.put("headPic", this.getHeadPic() == null ? "" : this.getHeadPic());
        return obj;
    }

}
