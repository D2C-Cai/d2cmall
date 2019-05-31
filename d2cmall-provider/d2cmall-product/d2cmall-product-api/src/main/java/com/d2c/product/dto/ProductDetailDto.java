package com.d2c.product.dto;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d2c.product.model.ProductDetail;
import org.apache.commons.lang.StringUtils;

import java.util.Iterator;

public class ProductDetailDto extends ProductDetail {

    private static final long serialVersionUID = 1L;
    /**
     * 品类banner
     */
    private String categoryBannerPic;
    /**
     * 品类url
     */
    private String categoryBannerUrl;

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

    /**
     * 尺码对应表
     */
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

    public void setSizetable() {
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

}
