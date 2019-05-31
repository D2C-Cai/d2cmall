package com.d2c.behavior.mongo.model.collabor;

import com.d2c.common.mongodb.model.SuperMongoDO;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * 用户行为表
 *
 * @author wull
 */
@Document
public class PersonActionDO extends SuperMongoDO {

    private static final long serialVersionUID = 1790646873633368072L;
    /**
     * 用户ID
     */
    @Id
    private String personId;
    /**
     * D2C会员ID
     */
    @Indexed
    private Long memberId;
    /**
     * 订单商品ID列表
     */
    private List<Long> orderProductIds;
    /**
     * 购物车商品ID列表
     */
    private List<Long> carProductIds;
    /**
     * 收藏商品ID列表
     */
    private List<Long> collectionProductIds;

    @Override
    public String toString() {
        return memberId + "<" + orderProductIds.size() + ", " + carProductIds.size() + ", " + collectionProductIds.size() + ">";
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public List<Long> getOrderProductIds() {
        return orderProductIds;
    }

    public void setOrderProductIds(List<Long> orderProductIds) {
        this.orderProductIds = orderProductIds;
    }

    public List<Long> getCarProductIds() {
        return carProductIds;
    }

    public void setCarProductIds(List<Long> carProductIds) {
        this.carProductIds = carProductIds;
    }

    public List<Long> getCollectionProductIds() {
        return collectionProductIds;
    }

    public void setCollectionProductIds(List<Long> collectionProductIds) {
        this.collectionProductIds = collectionProductIds;
    }

}
