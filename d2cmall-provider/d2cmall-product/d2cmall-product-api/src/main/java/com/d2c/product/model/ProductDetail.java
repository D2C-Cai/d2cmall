package com.d2c.product.model;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.annotation.AssertColumn;
import org.springframework.util.StringUtils;

import javax.persistence.Table;
import java.util.Iterator;

/**
 * 商品详情
 */
@Table(name = "p_product_detail")
public class ProductDetail extends RichTextBaseEntity {

    private static final long serialVersionUID = 1L;
    /**
     * 产品Id
     */
    @AssertColumn("商品关联不能为空")
    private Long productId;
    /**
     * 尺码对应表
     */
    private String sizeJson;
    /**
     * 试穿报告
     */
    private String tryOnReportJson;
    /**
     * 参数组ID
     */
    private Long attributeGroupId;
    /**
     * 商品参数
     */
    private String attributes;
    /**
     * 商品介绍
     */
    private String introduction;
    /**
     * boss推荐语
     */
    private String recommendation;
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
     * 简介
     */
    private String summary;

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    public Long getAttributeGroupId() {
        return attributeGroupId;
    }

    public void setAttributeGroupId(Long attributeGroupId) {
        this.attributeGroupId = attributeGroupId;
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

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
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

    /**
     * 尺码对应表
     */
    public JSONObject getSizetable() {
        JSONObject sizetable = new JSONObject();
        if (!StringUtils.isEmpty(getSizeJson())) {
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

    public void setSizetable() {
    }

    public String getAdPic() {
        return adPic;
    }

    public void setAdPic(String adPic) {
        this.adPic = adPic;
    }

    public String[] getProductAdList() {
        if (this.adPic != null) {
            return this.adPic.split(",");
        }
        return null;
    }

    public void setProductAdList() {
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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
        json.put("id", this.getProductId());
        String desc = StringUtils.isEmpty(getMobileDesc()) ? getDescription() : getMobileDesc();
        json.put("desc", desc == null ? "" : desc);
        json.put("summary", this.getSummary() == null ? "" : this.getSummary());
        return json;
    }

    public String getTryOnReportJson() {
        return tryOnReportJson;
    }

    public void setTryOnReportJson(String tryOnReportJson) {
        this.tryOnReportJson = tryOnReportJson;
    }

    /**
     * 试穿报告表
     */
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

    public void setTryOntable() {
    }

}
