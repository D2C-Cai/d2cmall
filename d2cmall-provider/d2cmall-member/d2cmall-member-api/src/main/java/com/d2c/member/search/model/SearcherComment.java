package com.d2c.member.search.model;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.dto.PreUserDTO;
import com.d2c.common.base.utils.StringUt;
import com.d2c.util.date.DateUtil;
import org.apache.commons.lang.StringUtils;

import java.util.Date;

public class SearcherComment extends PreUserDTO {

    private static final long serialVersionUID = 1L;
    /**
     * 会员ID
     */
    private Long memberId;
    /**
     * 会员名称
     */
    private String name;
    /**
     * 会员头像
     */
    private String headPic;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 电话
     */
    private String tel;
    /**
     * 源ID
     */
    private Long sourceId;
    /**
     * 商品ID
     */
    private Long productId;
    /**
     * SKU ID
     */
    private Long productSkuId;
    /**
     * 图片
     */
    private String productImg;
    /**
     * 颜色尺码
     */
    private String skuProperty;
    /**
     * 评价来源业务类型
     */
    private String source;
    /**
     * 评价对象（默认商品名称或作品名称）
     */
    private String title;
    /**
     * 评论内容
     */
    private String content;
    /**
     * 是否通过审核，默认为否
     */
    private Boolean verified;
    /**
     * 评论图片
     */
    private String pic;
    /**
     * 商品质量评分
     */
    private Integer productScore;
    /**
     * 商品包装评分
     */
    private Integer packageScore;
    /**
     * 配送速度评分
     */
    private Integer deliverySpeedScore;
    /**
     * 物流服务评分
     */
    private Integer deliveryServiceScore;
    /**
     * 设备
     */
    private String device;
    /**
     * 是否推荐，0否 1是
     */
    private Integer recomend;
    /**
     * 推荐时间
     */
    private Date recomendDate;
    /**
     * 视频
     */
    private String video;

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getProductSkuId() {
        return productSkuId;
    }

    public void setProductSkuId(Long productSkuId) {
        this.productSkuId = productSkuId;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public String getSkuProperty() {
        return skuProperty;
    }

    public void setSkuProperty(String skuProperty) {
        this.skuProperty = skuProperty;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Integer getProductScore() {
        return productScore;
    }

    public void setProductScore(Integer productScore) {
        this.productScore = productScore;
    }

    public Integer getPackageScore() {
        return packageScore;
    }

    public void setPackageScore(Integer packageScore) {
        this.packageScore = packageScore;
    }

    public Integer getDeliverySpeedScore() {
        return deliverySpeedScore;
    }

    public void setDeliverySpeedScore(Integer deliverySpeedScore) {
        this.deliverySpeedScore = deliverySpeedScore;
    }

    public Integer getDeliveryServiceScore() {
        return deliveryServiceScore;
    }

    public void setDeliveryServiceScore(Integer deliveryServiceScore) {
        this.deliveryServiceScore = deliveryServiceScore;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public Integer getRecomend() {
        return recomend;
    }

    public void setRecomend(Integer recomend) {
        this.recomend = recomend;
    }

    public Date getRecomendDate() {
        return recomendDate;
    }

    public void setRecomendDate(Date recomendDate) {
        this.recomendDate = recomendDate;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getDisplayName() {
        if (StringUtils.isNotBlank(nickName)) {
            return nickName;
        } else if (StringUtils.isNotBlank(name)) {
            return name;
        } else if (StringUtils.isNotBlank(tel)) {
            return tel;
        }
        return "";
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("id", this.getId());
        obj.put("name", this.getName());
        obj.put("nickname", StringUt.hideMobile(this.getDisplayName()));
        obj.put("headPic", this.getHeadPic());
        obj.put("createDate", DateUtil.second2str2(this.getCreateDate()));
        obj.put("title", this.getTitle());
        obj.put("content", this.getContent());
        obj.put("productImg", this.getProductImg());
        obj.put("skuProperty", this.getSkuProperty() == null ? "" : this.getSkuProperty());
        obj.put("productScore", this.getProductScore());
        obj.put("packageScore", this.getPackageScore());
        obj.put("deliverySpeedScore", this.getDeliverySpeedScore());
        obj.put("deliveryServiceScore", this.getDeliveryServiceScore());
        obj.put("recomend", this.getRecomend());
        if (this.getPic() != null) {
            JSONArray pics = new JSONArray();
            String[] items = this.getPic().split(",");
            for (String item : items) {
                pics.add(item);
            }
            obj.put("pics", pics);
        }
        obj.put("video", this.getVideo());
        return obj;
    }

}
