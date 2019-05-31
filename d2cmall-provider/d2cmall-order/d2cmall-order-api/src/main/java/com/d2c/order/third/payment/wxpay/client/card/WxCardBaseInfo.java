package com.d2c.order.third.payment.wxpay.client.card;

import org.json.JSONException;

import java.util.Collection;
import java.util.Date;

public class WxCardBaseInfo {

    public static final String logoUrl = "https://mmbiz.qlogo.cn/mmbiz/Z4hTU9j7HSibXonK4aNVoTYvBlIQLREzxONGqN0ia6DW2aicdXNblKbO0yFzXz7skFpeZicDmrEZYKqxHxB8vW4HQA/0";
    static int CODE_TYPE_TEXT = 0;
    static int CODE_TYPE_BARCODE = 1;
    static int CODE_TYPE_QRCODE = 2;
    org.json.JSONObject m_data;

    public WxCardBaseInfo() throws JSONException {
        m_data = new org.json.JSONObject();
        m_data.put("date_info", new org.json.JSONObject());
        m_data.put("location_id_list", new org.json.JSONArray());
        m_data.put("sku", new org.json.JSONObject());
    }

    public String toJsonString() {
        return m_data.toString();
    }

    public String toString() {
        return toJsonString();
    }

    public String getLogoUrl() {
        return m_data.optString("logo_url");
    }

    public void setLogoUrl(String logoUrl) throws JSONException {
        m_data.put("logo_url", logoUrl);
    }

    public int getCodeType() {
        return m_data.optInt("code_type");
    }

    public void setCodeType(int code) throws JSONException {
        m_data.put("code_type", code);
    }

    public void setBrandName(String name) throws JSONException {
        m_data.put("brand_name", name);
    }

    public String GetBrandName() {
        return m_data.optString("brand_name");
    }

    public String getTitle() {
        return m_data.optString("title");
    }

    public void setTitle(String title) throws JSONException {
        m_data.put("title", title);
    }

    public String getSubTitle() {
        return m_data.optString("sub_title");
    }

    public void setSubTitle(String subTitle) throws JSONException {
        m_data.put("sub_title", subTitle);
    }

    public void setDateInfoTimeRange(Date beginTime, Date endTime)
            throws JSONException {
        setDateInfoTimeRange(beginTime.getTime() / 1000,
                endTime.getTime() / 1000);
    }

    public void setDateInfoTimeRange(long beginTimestamp, long endTimestamp)
            throws JSONException {
        getDateInfo().put("type", 1);
        getDateInfo().put("begin_timestamp", beginTimestamp);
        getDateInfo().put("end_timestamp", endTimestamp);
    }

    public void setDateInfoFixTerm(int fixedTerm) throws JSONException {
        setDateInfoFixTerm(fixedTerm, 0);
    }

    public void setDateInfoFixTerm(int fixedTerm, int fixedBeginTerm)
            throws JSONException // fixedBeginTerm是领取后多少天开始生效
    {
        getDateInfo().put("type", 2);
        getDateInfo().put("fixed_term", fixedTerm);
        getDateInfo().put("fixed_begin_term", fixedBeginTerm);
    }

    public org.json.JSONObject getDateInfo() {
        return m_data.optJSONObject("date_info");
    }

    public String getColor() {
        return m_data.optString("color");
    }

    public void setColor(String color) throws JSONException {
        m_data.put("color", color);
    }

    public String getNotice() {
        return m_data.optString("notice");
    }

    public void setNotice(String notice) throws JSONException {
        m_data.put("notice", notice);
    }

    public String getServicePhone() {
        return m_data.optString("service_phone");
    }

    public void setServicePhone(String phone) throws JSONException {
        m_data.put("service_phone", phone);
    }

    public String getDescription() {
        return m_data.optString("description");
    }

    public void setDescription(String desc) throws JSONException {
        m_data.put("description", desc);
    }

    public void addLocationIdList(int locationId) throws JSONException {
        getLocationIdList().put(locationId);
    }

    public org.json.JSONArray getLocationIdList() throws JSONException {
        return m_data.getJSONArray("location_id_list");
    }

    public void setLocationIdList(Collection<Integer> value)
            throws JSONException {
        org.json.JSONArray array = new org.json.JSONArray();
        for (Integer k : value) {
            array.put(k);
        }
        m_data.put("location_id_list", array);
    }

    public int getUseLimit() {
        return m_data.optInt("useLimit");
    }

    public void setUseLimit(int limit) throws JSONException {
        m_data.put("use_limit", limit);
    }

    public int getGetLimit() {
        return m_data.optInt("get_limit");
    }

    public void setGetLimit(int limit) throws JSONException {
        m_data.put("get_limit", limit);
    }

    public boolean getCanShare() {
        return m_data.optBoolean("can_share");
    }

    public void setCanShare(boolean canShare) throws JSONException {
        m_data.put("can_share", canShare);
    }

    public boolean getCanGiveFriend() {
        return m_data.optBoolean("can_give_friend");
    }

    public void setCanGiveFriend(boolean canGive) throws JSONException {
        m_data.put("can_give_friend", canGive);
    }

    public boolean getUseCustomCode() {
        return m_data.optBoolean("use_custom_code");
    }

    public void setUseCustomCode(boolean isUse) throws JSONException {
        m_data.put("use_custom_code", isUse);
    }

    public boolean getBindOpenid() {
        return m_data.optBoolean("bind_openid");
    }

    public void setBindOpenid(boolean isBind) throws JSONException {
        m_data.put("bind_openid", isBind);
    }

    public int getQuantity() {
        return m_data.optJSONObject("sku").optInt("quantity");
    }

    public void setQuantity(int quantity) throws JSONException {
        m_data.optJSONObject("sku").put("quantity", quantity);
    }

    public String getCustomUrlName() {
        return m_data.optString("custom_url_name");
    }

    public void setCustomUrlName(String customUrlName) throws JSONException {
        m_data.put("custom_url_name", customUrlName);
    }

    public String getCustomUrl() {
        return m_data.optString("custom_url");
    }

    public void setCustomUrl(String customUrl) throws JSONException {
        m_data.put("custom_url", customUrl);
    }

    public String getCustomUrlSubTitle() {
        return m_data.optString("custom_url_sub_title");
    }

    public void setCustomUrlSubTitle(String customUrlSubTitle)
            throws JSONException {
        m_data.put("custom_url_sub_title", customUrlSubTitle);
    }

    public String getPromotionUrlName() {
        return m_data.optString("promotion_url_name");
    }

    public void setPromotionUrlName(String promotionUrlName)
            throws JSONException {
        m_data.put("promotion_url_name", promotionUrlName);
    }

    public String getPromotionUrl() {
        return m_data.optString("promotion_url");
    }

    public void setPromotionUrl(String promotionUrl) throws JSONException {
        m_data.put("promotion_url", promotionUrl);
    }

    public void setPromotionSubTitle(String promotionUrlSubTitle)
            throws JSONException {
        m_data.put("promotion_url_sub_title", promotionUrlSubTitle);
    }

    public String getPromotionUrlSubTitle() {
        return m_data.optString("promotion_url_sub_title");
    }

}
