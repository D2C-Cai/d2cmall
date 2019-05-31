package com.d2c.order.mongo.model;

import com.d2c.common.mongodb.model.BaseMongoDO;
import com.d2c.member.model.MemberInfo;
import com.d2c.product.model.BargainPromotion;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document
public class BargainPriceDO extends BaseMongoDO {

    private static final long serialVersionUID = -4903284310787030896L;
    /**
     * 砍价活动ID
     */
    @Indexed
    private Long bargainId;
    /**
     * 会员ID
     */
    @Indexed
    private Long memberId;
    /**
     * 微信unionId
     */
    @Indexed
    private String unionId;
    /**
     * 创建时间
     */
    @Indexed
    private Date createDate;
    /**
     * 砍价状态 BargainStatus(只分是否支付成功)
     */
    @Indexed
    private String status;
    /**
     * 实时价格
     */
    private Double price;
    /**
     * 初始价格
     */
    private Double originalPrice;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 会员账号
     */
    private String loginCode;
    /**
     * 会员头像
     */
    private String pic;
    /**
     * 会员昵称
     */
    private String nickname;
    /**
     * 砍价活动
     */
    private BargainPromotion bargain;
    /**
     * 砍价助力列表
     */
    @Transient
    private List<BargainHelpDO> helpers = new ArrayList<>();

    public BargainPriceDO() {
        this.createDate = new Date();
        this.status = BargainStatus.BEGIN.name();
    }

    public BargainPriceDO(MemberInfo memberInfo, BargainPromotion bargain, BigDecimal originalPrice,
                          String productName) {
        this();
        this.bargainId = bargain.getId();
        this.memberId = memberInfo.getId();
        this.loginCode = memberInfo.getLoginCode();
        this.pic = memberInfo.getHeadPic();
        this.nickname = memberInfo.getDisplayName();
        this.productName = productName;
        this.bargain = bargain;
        this.price = originalPrice.doubleValue();
        this.originalPrice = originalPrice.doubleValue();
    }

    public Long getBargainId() {
        return bargainId;
    }

    public void setBargainId(Long bargainId) {
        this.bargainId = bargainId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public BargainPromotion getBargain() {
        return bargain;
    }

    public void setBargain(BargainPromotion bargain) {
        this.bargain = bargain;
    }

    public List<BargainHelpDO> getHelpers() {
        return helpers;
    }

    public void setHelpers(List<BargainHelpDO> helpers) {
        this.helpers = helpers;
    }

    public String getStatusName() {
        switch (BargainStatus.valueOf(this.getStatus())) {
            case BEGIN:
                if (bargain.isOver()) {
                    return "砍价结束";
                }
                if (bargain.getMinPrice().doubleValue() == this.getPrice()) {
                    return "砍价成功";
                }
                return BargainStatus.valueOf(this.getStatus()).getRemark();
            case ORDERED:
                if (bargain.getEndDate().getTime() <= new Date().getTime() - 24 * 3600 * 1000) {
                    return "商品过期";
                }
                return BargainStatus.valueOf(this.getStatus()).getRemark();
            case BUYED:
                return BargainStatus.valueOf(this.getStatus()).getRemark();
            default:
                return BargainStatus.valueOf(this.getStatus()).getRemark();
        }
    }

    public enum BargainStatus {
        BEGIN("砍价中"), ORDERED("等待支付"), BUYED("购买成功");
        String remark;

        BargainStatus(String remark) {
            this.remark = remark;
        }

        public String getRemark() {
            return remark;
        }
    }

}
