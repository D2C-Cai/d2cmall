package com.d2c.behavior.mongo.model.collabor;

import com.d2c.common.mongodb.model.BaseMongoDO;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 用户商品评分表
 *
 * @author wull
 */
@Document
public class PersonCollaborDO extends BaseMongoDO {

    private static final long serialVersionUID = 1790646873633368072L;
    /**
     * 用户ID
     */
    @Indexed
    private String personId;
    /**
     * D2C会员ID
     */
    @Indexed
    private Long memberId;
    /**
     * 商品ID
     */
    @Indexed
    private Long productId;
    /**
     * 商品评分
     */
    private Double rating = 0.0;

    public Double addRating(Double rating) {
        this.rating += rating;
        return this.rating;
    }
    //******************************

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

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

}
