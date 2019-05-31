package com.d2c.member.model;

import com.d2c.common.api.model.PreUserDO;
import com.d2c.util.serial.SerialNumUtil;

import javax.persistence.Table;

/**
 * 集卡明细
 *
 * @author Lain
 */
@Table(name = "m_collection_card")
public class CollectionCard extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 编号
     */
    private String cardSn;
    /**
     * 积分
     */
    private Integer point;
    /**
     * 通过谁获取的
     */
    private String fromName;
    /**
     * 通过谁
     */
    private Long fromId;
    /**
     * 会员ID
     */
    private Long memberId;
    /**
     * 会员账号
     */
    private String loginCode;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 卡片定义ID
     */
    private Long defId;
    /**
     * 卡片名称
     */
    private String defName;
    /**
     * 链接
     */
    private String url;
    /**
     * 图片
     */
    private String pic;
    /**
     * 活动ID
     */
    private Long promotionId;
    /**
     * 活动名称
     */
    private String promotionName;

    public CollectionCard() {
        this.cardSn = SerialNumUtil.buildCollectionCardSn();
    }

    public CollectionCard(CollectionCardDef collectionCardDef) {
        this();
        this.defId = collectionCardDef.getId();
        this.defName = collectionCardDef.getName();
        this.url = collectionCardDef.getUrl();
        this.pic = collectionCardDef.getPic();
        this.promotionId = collectionCardDef.getPromotionId();
        this.promotionName = collectionCardDef.getPromotionName();
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Long getDefId() {
        return defId;
    }

    public void setDefId(Long defId) {
        this.defId = defId;
    }

    public String getDefName() {
        return defName;
    }

    public void setDefName(String defName) {
        this.defName = defName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getCardSn() {
        return cardSn;
    }

    public void setCardSn(String cardSn) {
        this.cardSn = cardSn;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public Long getFromId() {
        return fromId;
    }

    public void setFromId(Long fromId) {
        this.fromId = fromId;
    }

    public Long getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Long promotionId) {
        this.promotionId = promotionId;
    }

    public String getPromotionName() {
        return promotionName;
    }

    public void setPromotionName(String promotionName) {
        this.promotionName = promotionName;
    }

}
