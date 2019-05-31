package com.d2c.product.dto;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d2c.product.model.*;
import com.d2c.product.search.model.SearcherProduct;
import com.d2c.util.string.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.beans.BeanUtils;

import java.util.Iterator;
import java.util.List;

public class ProductDto extends Product {

    private static final long serialVersionUID = 1L;
    /**
     * 商品所有有序SKU
     */
    private List<ProductSkuDto> productSKUSet;
    /**
     * 商品SKU列表
     */
    private List<ProductSku> skuList;
    /**
     * 商品SKU库存列表
     */
    private List<ProductSkuStockSummary> stockList;
    /**
     * 设计师
     */
    private Brand designer;
    /**
     * 系列
     */
    private Series series;
    /**
     * 总销量
     */
    private Integer sales = 0;
    /**
     * 商品查看数
     */
    private Integer views = 0;
    /**
     * 商品加入购物车
     */
    private Integer carts = 0;
    /**
     * 商品加入订单数
     */
    private Integer orders = 0;
    /**
     * 喜欢的数量
     */
    private Integer likes = 0;
    /**
     * 虚拟喜欢的数量
     */
    private Integer vlikes = 0;
    /**
     * 展示喜欢的数量
     */
    private Integer tlikes = 0;
    /**
     * 评论数量
     */
    private Integer comments = 0;
    /**
     * 咨询数量
     */
    private Integer consults = 0;
    /**
     * 尺码对应表
     */
    private String sizeJson;
    /**
     * 试穿报告
     */
    private String tryOnReportJson;
    /**
     * 商品明星图
     */
    private String starPic;
    /**
     * 商品买家秀
     */
    private String showPic;
    /**
     * 商品模特图
     */
    private String modelPic;
    /**
     * 商品静物图
     */
    private String stillPic;
    /**
     * 商品广告图
     */
    private String adPic;
    /**
     * 商品介绍
     */
    private String introduction;
    /**
     * 推荐语
     */
    private String recommendation;
    /**
     * 商品简介
     */
    private String summary;
    /**
     * 参数组ID
     */
    private Long attributeGroupId;
    /**
     * 商品参数
     */
    private String attributes;
    /**
     * 品类图片
     */
    private String categoryBannerPic;
    /**
     * 品类URL
     */
    private String categoryBannerUrl;
    /**
     * 视频地址
     */
    private String videoUrl;
    /**
     * PC端详情
     */
    private String description;
    /**
     * 手机端详情
     */
    private String mobileDesc;
    /**
     * 是否收藏
     */
    private Integer collectioned = 0;
    /**
     * 是否自营
     */
    private Integer pop = 0;
    /**
     * 是否现货
     */
    private Integer spot = 0;

    public ProductDto(Product product, ProductSummary summary) {
        BeanUtils.copyProperties(product, this);
        BeanUtils.copyProperties(summary, this, new String[]{"id"});
    }

    public ProductDto(Product product, ProductDetail productDetail) {
        BeanUtils.copyProperties(product, this);
        BeanUtils.copyProperties(productDetail, this, new String[]{"id", "sizetable"});
    }

    public ProductDto() {
    }

    public List<ProductSkuDto> getProductSKUSet() {
        return productSKUSet;
    }

    public void setProductSKUSet(List<ProductSkuDto> productSKUSet) {
        this.productSKUSet = productSKUSet;
    }

    public List<ProductSku> getSkuList() {
        return skuList;
    }

    public void setSkuList(List<ProductSku> skuList) {
        this.skuList = skuList;
    }

    public List<ProductSkuStockSummary> getStockList() {
        return stockList;
    }

    public void setStockList(List<ProductSkuStockSummary> stockList) {
        this.stockList = stockList;
    }

    public Brand getDesigner() {
        return designer;
    }

    public void setDesigner(Brand designer) {
        this.designer = designer;
    }

    public Series getSeries() {
        return series;
    }

    public void setSeries(Series series) {
        this.series = series;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Integer getCarts() {
        return carts;
    }

    public void setCarts(Integer carts) {
        this.carts = carts;
    }

    public Integer getOrders() {
        return orders;
    }

    public void setOrders(Integer orders) {
        this.orders = orders;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getVlikes() {
        return vlikes;
    }

    public void setVlikes(Integer vlikes) {
        this.vlikes = vlikes;
    }

    public Integer getTlikes() {
        return tlikes;
    }

    public void setTlikes(Integer tlikes) {
        this.tlikes = tlikes;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public Integer getConsults() {
        return consults;
    }

    public void setConsults(Integer consults) {
        this.consults = consults;
    }

    public String getSizeJson() {
        return sizeJson;
    }

    public void setSizeJson(String sizeJson) {
        this.sizeJson = sizeJson;
    }

    public String getStarPic() {
        return starPic;
    }

    public void setStarPic(String starPic) {
        this.starPic = starPic;
    }

    public String getShowPic() {
        return showPic;
    }

    public void setShowPic(String showPic) {
        this.showPic = showPic;
    }

    public String getModelPic() {
        return modelPic;
    }

    public void setModelPic(String modelPic) {
        this.modelPic = modelPic;
    }

    public String getStillPic() {
        return stillPic;
    }

    public void setStillPic(String stillPic) {
        this.stillPic = stillPic;
    }

    public String getAdPic() {
        return adPic;
    }

    public void setAdPic(String adPic) {
        this.adPic = adPic;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Long getAttributeGroupId() {
        return attributeGroupId;
    }

    public void setAttributeGroupId(Long attributeGroupId) {
        this.attributeGroupId = attributeGroupId;
    }

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    public String getCategoryBannerPic() {
        return categoryBannerPic;
    }

    public void setCategoryBannerPic(String categoryBannerPic) {
        this.categoryBannerPic = categoryBannerPic;
    }

    public String getCategoryBannerUrl() {
        return categoryBannerUrl;
    }

    public void setCategoryBannerUrl(String categoryBannerUrl) {
        this.categoryBannerUrl = categoryBannerUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public Integer getCollectioned() {
        return collectioned;
    }

    public void setCollectioned(Integer collectioned) {
        this.collectioned = collectioned;
    }

    public Integer getSpot() {
        return spot;
    }

    public void setSpot(Integer spot) {
        this.spot = spot;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

    public String getMobileDesc() {
        if (mobileDesc != null) {
            mobileDesc = mobileDesc.trim();
        }
        return mobileDesc;
    }

    public void setMobileDesc(String mobileDesc) {
        this.mobileDesc = mobileDesc;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTryOnReportJson() {
        return tryOnReportJson;
    }

    public void setTryOnReportJson(String tryOnReportJson) {
        this.tryOnReportJson = tryOnReportJson;
    }

    public Integer getPop() {
        if (SaleCategory.NORMALPRODUCT.name().equals(this.getSaleCategory())) {
            pop = 0;
        } else {
            pop = 1;
        }
        return pop;
    }

    public void setPop(Integer pop) {
        this.pop = pop;
    }

    /**
     * 尺码对应表
     */
    @JsonIgnore
    public JSONObject getSizetable() {
        JSONObject sizetable = new JSONObject();
        if (StringUtils.isNotBlank(getSizeJson())) {
            JSONArray heads = new JSONArray();
            JSONArray bodys = new JSONArray();
            JSONArray body = null;
            JSONObject sizeJson = JSONObject.parseObject(getSizeJson());
            heads = sizeJson.getJSONArray("header");
            heads.add(0, "本款尺码");
            JSONObject data = sizeJson.getJSONObject("data");
            Iterator<?> it = data.keySet().iterator();
            Iterator<?> subIt = null;
            String dataKey = null;
            String subDataKey = null;
            JSONObject subData = null;
            while (it.hasNext()) {
                body = new JSONArray();
                dataKey = String.valueOf(it.next());
                body.add(dataKey);
                subData = data.getJSONObject(dataKey);
                subIt = subData.keySet().iterator();
                while (subIt.hasNext()) {
                    subDataKey = String.valueOf(subIt.next());
                    body.add(subData.getString(subDataKey));
                }
                bodys.add(body);
            }
            sizetable.put("heads", heads);
            sizetable.put("bodys", bodys);
        }
        return sizetable;
    }

    public void setSizetable(JSONObject obj) {
    }

    /**
     * 试穿报告表
     */
    @JsonIgnore
    public JSONObject getTryOntable() {
        JSONObject tryOntable = new JSONObject();
        if (!StringUtils.isEmpty(getTryOnReportJson())) {
            JSONArray heads = new JSONArray();
            JSONArray bodys = new JSONArray();
            JSONArray body = null;
            JSONObject tryOnJson = JSONObject.parseObject(getTryOnReportJson());
            heads = tryOnJson.getJSONArray("header");
            heads.add(0, "本款尺码");
            JSONObject data = tryOnJson.getJSONObject("data");
            Iterator<?> it = data.keySet().iterator();
            Iterator<?> subIt = null;
            String dataKey = null;
            String subDataKey = null;
            JSONObject subData = null;
            while (it.hasNext()) {
                body = new JSONArray();
                dataKey = String.valueOf(it.next());
                body.add(dataKey);
                subData = data.getJSONObject(dataKey);
                subIt = subData.keySet().iterator();
                while (subIt.hasNext()) {
                    subDataKey = String.valueOf(subIt.next());
                    body.add(subData.getString(subDataKey));
                }
                bodys.add(body);
            }
            tryOntable.put("heads", heads);
            tryOntable.put("bodys", bodys);
        }
        return tryOntable;
    }

    public void setTryOntable(JSONObject obj) {
    }

    public JSONObject toProductDataJson() {
        JSONObject resultJson = new JSONObject();
        if (this.getStarPic() != null) {
            JSONArray starPics = new JSONArray();
            for (String item : getStarPic().split(",")) {
                JSONObject json = new JSONObject();
                json.put("path", item);
                starPics.add(json);
            }
            resultJson.put("starPics", starPics);
        }
        if (this.getShowPic() != null) {
            JSONArray showPics = new JSONArray();
            for (String item : getShowPic().split(",")) {
                JSONObject json = new JSONObject();
                json.put("path", item);
                showPics.add(json);
            }
            resultJson.put("showPics", showPics);
        }
        if (this.getModelPic() != null) {
            JSONArray modelPics = new JSONArray();
            for (String item : getModelPic().split(",")) {
                JSONObject json = new JSONObject();
                json.put("path", item);
                modelPics.add(json);
            }
            resultJson.put("modelPics", modelPics);
        }
        if (this.getStillPic() != null) {
            JSONArray stillPics = new JSONArray();
            for (String item : getStillPic().split(",")) {
                JSONObject json = new JSONObject();
                json.put("path", item);
                stillPics.add(json);
            }
            resultJson.put("stillPics", stillPics);
        }
        return resultJson;
    }

    public JSONObject toJsonDetail() {
        JSONObject json = new JSONObject();
        json.put("id", this.getId());
        json.put("sizetable", this.getSizetable());
        String desc = StringUtils.isBlank(getMobileDesc()) ? getDescription() : getMobileDesc();
        json.put("desc", desc);
        return json;
    }

    public JSONObject toJson(SearcherProduct searcherProduct) {
        JSONObject json = new JSONObject();
        if (searcherProduct == null) {
            searcherProduct = new SearcherProduct();
            searcherProduct.setMinPrice(this.getMinPrice());
            searcherProduct.setStore(0);
            searcherProduct.setSpot(0);
            searcherProduct.setStatus(0);
            searcherProduct.setMark(0);
        }
        json.put("id", this.getId());
        json.put("sn", this.getInernalSn());
        json.put("name", this.getName());
        json.put("subTitle", this.getSubTitle());
        json.put("img", this.getProductImageCover());
        json.put("imgs", this.getProductImageList());
        json.put("picStyle", this.getPicStyle());
        json.put("price", searcherProduct.getCurrentPrice());
        json.put("minPrice", searcherProduct.getCurrentPrice());
        json.put("maxPrice", this.getMaxPrice());
        json.put("salePrice", this.getMinPrice());
        json.put("sellPrice", this.getMinPrice());
        json.put("originalPrice", this.getOriginalPrice());
        json.put("flashPrice", this.getFlashPrice() == null ? 0 : this.getFlashPrice());
        json.put("collagePrice", this.getCollagePrice() == null ? 0 : this.getCollagePrice());
        json.put("soonPrice", searcherProduct.getSoonPrice());
        json.put("taxPrice", this.getTaxPrice());
        json.put("store", searcherProduct.getStore());
        json.put("mark", this.getMark());
        json.put("after", this.getAfter());
        json.put("isSpot", searcherProduct.getSpot() == 1);
        json.put("isTopical", this.getTopical() == 1);
        json.put("isCod", this.getCod());
        json.put("isCart", this.getCart());
        json.put("isAfter", this.getAfter());
        json.put("isSubscribe", this.getSubscribe());
        json.put("isTaxation", this.getTaxation());
        json.put("isShipping", this.getShipping());
        json.put("firstRatio", this.getFirstRatio());
        json.put("secondRatio", this.getSecondRatio());
        json.put("grossRatio", this.getGrossRatio());
        json.put("collectioned", this.getCollectioned());
        json.put("colors", JSONObject.parseObject(this.getSalesproperty()).getJSONArray("sp1"));
        json.put("sizes", JSONObject.parseObject(this.getSalesproperty()).getJSONArray("sp2"));
        json.put("productSaleType", this.getProductSaleType());
        json.put("productSellType", this.getProductSellType());
        json.put("productTradeType", this.getProductTradeType());
        json.put("productSource", this.getSource());
        json.put("importType", this.getImportType());
        json.put("brand", this.getDesignerName());
        json.put("designerId", this.getDesignerId());
        json.put("designerPic", this.getDesigner() == null ? "" : this.getDesigner().getHeadPic());
        json.put("productStatus", this.getStatus());
        json.put("estimateDate", this.getEstimateDate());
        json.put("estimateDay", this.getEstimateDay());
        json.put("remark", this.getRemark());
        json.put("video", this.getVideo());
        json.put("introduction", searcherProduct.getIntroduction());
        json.put("recommendation", searcherProduct.getRecommendation());
        json.put("afterMemo", this.getAfterMemos());
        json.put("isInstallment", 0);
        json.put("status", searcherProduct.analyseAppStatus());
        json.put("soonPromotion", searcherProduct.getSoonPromotion());
        return json;
    }

    /**
     * 颜色列表
     */
    public String getColors() {
        String colors = new String();
        String sp = this.getSalesproperty();
        if (sp != null) {
            JSONObject spJson = JSONObject.parseObject(sp);
            colors = spJson.getJSONArray("sp1").toJSONString();
        }
        return colors;
    }

    public void setColors(String colors) {
    }

    /**
     * 尺码列表
     */
    public String getSizes() {
        String sizes = new String();
        String sp = this.getSalesproperty();
        if (sp != null) {
            JSONObject spJson = JSONObject.parseObject(sp);
            sizes = spJson.getJSONArray("sp2").toJSONString();
        }
        return sizes;
    }

    public void setSizes(String colors) {
    }

    /**
     * 售后备注
     *
     * @return
     */
    public String getAfterMemos() {
        if (!StringUtil.isBlack(this.getAfterMemo())) {
            return this.getAfterMemo();
        }
        if (this.getAfter() == 1) {
            return "自您签收商品之日起7天内，D2C为您提供退货服务";
        } else {
            return "定制商品或其它特殊商品非货品质量原因不支持7天无理由退换货";
        }
    }

    public void setAfterMemos() {
    }

}
