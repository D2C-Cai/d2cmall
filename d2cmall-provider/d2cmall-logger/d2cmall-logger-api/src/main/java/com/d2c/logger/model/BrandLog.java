package com.d2c.logger.model;

import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;

/**
 * 品牌日志
 */
@Table(name = "log_brand")
public class BrandLog extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 业务类型
     */
    private String type;
    /**
     * 操作信息
     */
    private String info;
    /**
     * 品牌ID
     */
    private Long designerId;

    public BrandLog() {
    }

    public BrandLog(String creator, String info, DesignerLogType type, Long designerId) {
        this.creator = creator;
        this.type = type.toString();
        this.designerId = designerId;
        this.info = info;
    }

    public String getType() {
        return type;
    }

    ;

    public void setType(String type) {
        this.type = type;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Long getDesignerId() {
        return designerId;
    }

    public void setDesignerId(Long designerId) {
        this.designerId = designerId;
    }

    public enum DesignerLogType {
        Insert, Update, Delete, Series,
        Up, Down, Recommend, Subscribe, Cod, After,
        TagR, PromotionR, CouponR, Direct;

        public String getInfo(DesignerLogType type) {
            switch (type) {
                case Insert:
                    return "新增商品";
                case Update:
                    return "商品编辑";
                case Delete:
                    return "归档";
                case Series:
                    return "设计师系列";
                case Up:
                    return "上架";
                case Down:
                    return "下架 ";
                case Recommend:
                    return "推荐";
                case Subscribe:
                    return "门店试衣";
                case Cod:
                    return "货到付款";
                case After:
                    return "售后";
                case TagR:
                    return "关联标签";
                case PromotionR:
                    return "关联活动";
                case CouponR:
                    return "关联优惠券";
                case Direct:
                    return "设计师直发";
            }
            return "";
        }
    }

}
