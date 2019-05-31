package com.d2c.member.model;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;
import com.d2c.common.base.utils.StringUt;
import com.d2c.util.date.DateUtil;
import org.apache.commons.lang.StringUtils;

import javax.persistence.Table;
import java.util.Date;

/**
 * 商品评价
 */
@Table(name = "m_comment")
public class Comment extends PreUserDO {

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
     * 邮件
     */
    private String email;
    /**
     * 源ID
     */
    private Long sourceId;
    /**
     * 商品ID
     */
    @AssertColumn("商品不能为空")
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
     * 删除
     */
    private Integer deleted = 0;
    /**
     * 设计师ID
     */
    private Long designerId;
    /**
     * 设备
     */
    private String device;
    /**
     * app版本
     */
    private String appVersion;
    /**
     * 是否推荐，0否 1是
     */
    private int recomend;
    /**
     * 推荐时间
     */
    private Date recomendDate;
    /**
     * 推荐内容
     */
    private String recomendContent;
    /**
     * 买家秀Id
     */
    private Long shareId;
    /**
     * 视频地址
     */
    private String video;
    /**
     * 评价 -1差评，1好评
     */
    private Integer score = 1;

    public Comment() {
    }

    public String getDisplayName() {
        if (StringUtils.isNotBlank(nickName)) {
            return nickName;
        } else if (StringUtils.isNotBlank(name)) {
            return name;
        } else if (StringUtils.isNotBlank(tel)) {
            return tel;
        } else if (StringUtils.isNotBlank(email)) {
            return email;
        }
        return "";
    }

    public void initComment(MemberInfo memberInfo) {
        this.email = memberInfo.getEmail();
        this.memberId = memberInfo.getId();
        this.name = memberInfo.getLoginCode();
        this.tel = memberInfo.getMobile();
        this.nickName = memberInfo.getDisplayName();
        this.verified = true;
        this.headPic = memberInfo.getHeadPic();
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

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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

    public String getSkuProperty() {
        return skuProperty;
    }

    public void setSkuProperty(String skuProperty) {
        this.skuProperty = skuProperty;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public String[] getPictures() {
        if (this.getPic() != null) {
            return pic.split(",");
        }
        return null;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public Long getDesignerId() {
        return designerId;
    }

    public void setDesignerId(Long designerId) {
        this.designerId = designerId;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public int getRecomend() {
        return recomend;
    }

    public void setRecomend(int recomend) {
        this.recomend = recomend;
    }

    public Date getRecomendDate() {
        return recomendDate;
    }

    public void setRecomendDate(Date recomendDate) {
        this.recomendDate = recomendDate;
    }

    public String getRecomendContent() {
        return recomendContent;
    }

    public void setRecomendContent(String recomendContent) {
        this.recomendContent = recomendContent;
    }

    public Long getShareId() {
        return shareId;
    }

    public void setShareId(Long shareId) {
        this.shareId = shareId;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("id", this.getId());
        obj.put("name", StringUt.hideMobile(this.getName()));
        obj.put("nickname", StringUt.hideMobile(this.getDisplayName()));
        obj.put("headPic", this.getHeadPic() == null ? "" : this.getHeadPic());
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
        obj.put("memberId", this.getMemberId());
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

    /**
     * 评价来源
     */
    public static enum CommentSource {
        DESIGNCOMP, PRODUCTSKU, ORDERITEM, O2OSUBSCRIBE;
    }

}
