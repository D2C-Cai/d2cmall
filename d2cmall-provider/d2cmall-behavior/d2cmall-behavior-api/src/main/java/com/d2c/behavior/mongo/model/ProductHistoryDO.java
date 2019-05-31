package com.d2c.behavior.mongo.model;

import com.d2c.common.base.utils.DateUt;
import com.d2c.common.mongodb.model.BaseMongoDO;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * 商品浏览记录
 *
 * @author wull
 */
@Document
public class ProductHistoryDO extends BaseMongoDO {

    private static final long serialVersionUID = 5049158952120571446L;
    /**
     * 用户ID
     */
    @Indexed
    private Long memberId;
    /**
     * 商品ID
     */
    @Indexed
    private Long productId;
    /**
     * 创建时间
     */
    private Date eventTime;
    /**
     * 创建日期
     */
    @Indexed
    private String eventDay;

    public ProductHistoryDO() {
    }

    public ProductHistoryDO(Long memberId, Long productId) {
        this.memberId = memberId;
        this.productId = productId;
        this.eventTime = new Date();
        this.eventDay = DateUt.date2str(eventTime);
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

    public Date getEventTime() {
        return eventTime;
    }

    public void setEventTime(Date eventTime) {
        this.eventTime = eventTime;
    }

    public String getEventDay() {
        return eventDay;
    }

    public void setEventDay(String eventDay) {
        this.eventDay = eventDay;
    }

}
