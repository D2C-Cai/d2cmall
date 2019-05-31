package com.d2c.member.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.model.PreUserDO;
import com.d2c.common.base.utils.StringUt;

import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

/**
 * 集卡抽奖记录
 *
 * @author Lain
 */
@Table(name = "m_collection_card_record")
public class CollectionCardRecord extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * null抽卡,THREE,SIX,NINE阶段奖励
     */
    private String stage;
    /**
     * 会员ID
     */
    private Long memberId;
    /**
     * 会员名称
     */
    private String memberName;
    /**
     * 奖品名称
     */
    private String awardName;
    /**
     * 关联发奖业务ID
     */
    private Long relationId;
    /**
     * 发奖业务类型
     */
    private String relationType;
    /**
     * 通过谁
     */
    private Long fromId;
    /**
     * 活动ID
     */
    private Long promotionId;

    public CollectionCardRecord() {
    }

    public CollectionCardRecord(CollectionCard collectionCard) {
        this.memberId = collectionCard.getMemberId();
        this.memberName = collectionCard.getNickName();
        this.promotionId = collectionCard.getPromotionId();
        if (collectionCard.getFromId() == null) {
            this.awardName = "翻卡获得" + collectionCard.getPoint() + "积分";
        } else {
            this.awardName = "集卡获得" + collectionCard.getPoint() + "积分";
        }
        this.relationId = collectionCard.getId();
        this.relationType = CardAwardType.CARD.name();
        this.fromId = collectionCard.getFromId();
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getAwardName() {
        return awardName;
    }

    public void setAwardName(String awardName) {
        this.awardName = awardName;
    }

    public Long getRelationId() {
        return relationId;
    }

    public void setRelationId(Long relationId) {
        this.relationId = relationId;
    }

    public String getRelationType() {
        return relationType;
    }

    public void setRelationType(String relationType) {
        this.relationType = relationType;
    }

    public Long getFromId() {
        return fromId;
    }

    public void setFromId(Long fromId) {
        this.fromId = fromId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public Long getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Long promotionId) {
        this.promotionId = promotionId;
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("createDate", this.getCreateDate());
        obj.put("stage", this.getStage());
        obj.put("memberId", this.getMemberId());
        obj.put("memberName", StringUt.hideMobile(this.getMemberName()));
        obj.put("awardName", this.getAwardName());
        return obj;
    }

    public enum StageEnum {
        THREE, SIX, NINE;
        public static Set<String> keys = new HashSet<String>();

        static {
            for (StageEnum stage : StageEnum.values()) {
                keys.add(stage.name());
            }
        }
    }

    public enum CardAwardType {
        CARD
    }

}
