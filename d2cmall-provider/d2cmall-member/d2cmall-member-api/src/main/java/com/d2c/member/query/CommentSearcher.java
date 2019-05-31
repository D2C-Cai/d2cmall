package com.d2c.member.query;

import com.d2c.common.api.query.model.BaseQuery;
import com.d2c.common.api.query.model.RoleQuery;

import java.util.Date;
import java.util.List;

public class CommentSearcher extends BaseQuery implements RoleQuery {

    private static final long serialVersionUID = 1L;
    /**
     * ID
     */
    private Long id;
    /**
     * 产品Id
     */
    private Long productId;
    /**
     * SKU Id
     */
    private Long productSkuId;
    /**
     * 会员Id
     */
    private Long memberId;
    /**
     * 会员名称 like
     */
    private String memberName;
    /**
     * 会员昵称 like
     */
    private String nickName;
    /**
     * 标题 like
     */
    private String title;
    /**
     * 内容 like
     */
    private String content;
    /**
     * 源ID
     */
    private Long sourceId;
    /**
     * 来源
     */
    private String source;
    /**
     * 审核
     */
    private Boolean verified;
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
     * 创建时间
     */
    private Date startDate;
    private Date endDate;
    /**
     * 设计师ID
     */
    private Long designerId;
    /**
     * 设计师ID
     */
    private List<Long> designerIds;
    /**
     * 是否有图
     */
    private Integer hasPic;
    /**
     * 设备终端
     */
    private String device;
    /**
     * 商品货号
     */
    private String productSn;
    /**
     * 设计师货号
     */
    private String externalSn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Long getDesignerId() {
        return designerId;
    }

    public void setDesignerId(Long designerId) {
        this.designerId = designerId;
    }

    public List<Long> getDesignerIds() {
        return designerIds;
    }

    public void setDesignerIds(List<Long> designerIds) {
        this.designerIds = designerIds;
    }

    public Integer getHasPic() {
        return hasPic;
    }

    public void setHasPic(Integer hasPic) {
        this.hasPic = hasPic;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    @Override
    public void setStoreId(Long storeId) {
    }

    @Override
    public void setBrandIds(List<Long> brandIds) {
        this.designerIds = brandIds;
    }

    public String getProductSn() {
        return productSn;
    }

    public void setProductSn(String productSn) {
        this.productSn = productSn;
    }

    public String getExternalSn() {
        return externalSn;
    }

    public void setExternalSn(String externalSn) {
        this.externalSn = externalSn;
    }

}
