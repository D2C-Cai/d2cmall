package com.d2c.order.mongo.model;

import com.d2c.common.mongodb.model.SuperMongoDO;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
public class BargainHelpDO extends SuperMongoDO {

    private static final long serialVersionUID = -6310471283406627019L;
    /**
     * 砍价活动ID
     */
    @Indexed
    private String bargainId;
    /**
     * 微信unionId
     */
    @Indexed
    private String helperUnionId;
    /**
     * 助力会员ID
     */
    @Indexed
    private Long helpMemberId;
    /**
     * 会员名称
     */
    private String userName;
    /**
     * 会员头像
     */
    private String headPic;
    /**
     * 砍价助力价格
     */
    private Double subPrice;
    /**
     * 砍价助力时间
     */
    @Indexed
    private Date helpDate;

    public BargainHelpDO() {
    }

    public BargainHelpDO(String bargainId, Double subPrice, Long memberId, String unionId, String userName,
                         String headPic) {
        this.bargainId = bargainId;
        this.subPrice = subPrice;
        this.helpMemberId = memberId;
        this.helperUnionId = unionId;
        this.userName = userName;
        this.headPic = headPic;
        this.helpDate = new Date();
    }

    public String getBargainId() {
        return bargainId;
    }

    public void setBargainId(String bargainId) {
        this.bargainId = bargainId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public Double getSubPrice() {
        return subPrice;
    }

    public void setSubPrice(Double subPrice) {
        this.subPrice = subPrice;
    }

    public Long getHelpMemberId() {
        return helpMemberId;
    }

    public void setHelpMemberId(Long helpMemberId) {
        this.helpMemberId = helpMemberId;
    }

    public String getHelperUnionId() {
        return helperUnionId;
    }

    public void setHelperUnionId(String helperUnionId) {
        this.helperUnionId = helperUnionId;
    }

    public Date getHelpDate() {
        return helpDate;
    }

    public void setHelpDate(Date helpDate) {
        this.helpDate = helpDate;
    }

}
