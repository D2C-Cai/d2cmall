package com.d2c.member.model;

import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;

/**
 * 集卡定义
 *
 * @author Lain
 */
@Table(name = "m_collection_card_def")
public class CollectionCardDef extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 卡片名字
     */
    private String name;
    /**
     * 图片
     */
    private String pic;
    /**
     * 链接
     */
    private String url;
    /**
     * 对应该活动ID
     */
    private Long promotionId;
    /**
     * 对应该活动名称
     */
    private String promotionName;
    /**
     * 总数量
     */
    private Integer quantity;
    /**
     * 权重
     */
    private Integer weight;
    /**
     * 状态
     */
    private Integer status = 0;
    /**
     * 前端排序
     */
    private Integer sort = 0;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Long promotionId) {
        this.promotionId = promotionId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getPromotionName() {
        return promotionName;
    }

    public void setPromotionName(String promotionName) {
        this.promotionName = promotionName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

}
