package com.d2c.product.search.model;

import com.alibaba.fastjson.JSONObject;

import java.util.Date;

public class SearcherRecProduct extends SearcherProduct {

    private static final long serialVersionUID = 1L;
    /**
     * 直播ID
     */
    private Long liveId;
    /**
     * 直播时商品编号
     */
    private Integer no;
    /**
     * 是否推荐
     */
    private Integer recommend = 0;
    /**
     * 推荐时间
     */
    private Date recDate;

    public Long getLiveId() {
        return liveId;
    }

    public void setLiveId(Long liveId) {
        this.liveId = liveId;
    }

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }

    public Integer getRecommend() {
        return recommend;
    }

    public void setRecommend(Integer recommend) {
        this.recommend = recommend;
    }

    public Date getRecDate() {
        return recDate;
    }

    public void setRecDate(Date recDate) {
        this.recDate = recDate;
    }

    public JSONObject toJson() {
        JSONObject json = super.toJson();
        json.put("no", this.getNo());
        json.put("isRec", recommend == 1 ? true : false);
        return json;
    }

}
