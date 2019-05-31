package com.d2c.product.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 备选商品SKU
 */
@Table(name = "p_product_sku_option")
public class ProductSkuOption extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 销售价格
     */
    @AssertColumn(value = "商品价格不能为空，必须大于0", min = 0)
    protected BigDecimal price = new BigDecimal(0);
    /**
     * 商品ID
     */
    @AssertColumn("商品没有指定")
    private Long productId;
    /**
     * 款号
     */
    private String inernalSn;
    /**
     * 设计师sku
     */
    private String externalSn;
    /**
     * 官网条码，唯一键
     */
    @AssertColumn("商品条码未指定")
    private String sn;
    /**
     * 实际条码
     */
    private String barCode;
    /**
     * SKU 名称
     */
    private String name;
    /**
     * 销售属性1
     */
    @AssertColumn("商品未设置主规格")
    private String sp1;
    /**
     * 销售属性2
     */
    private String sp2;
    /**
     * 销售属性3
     */
    private String sp3;
    /**
     * 吊牌价格
     */
    @AssertColumn(value = "吊牌价格不能为空，必须大于0", min = 0)
    private BigDecimal originalCost;
    /**
     * 一口价价格
     */
    @AssertColumn(value = "一口价不能为空，必须大于0", min = 0)
    private BigDecimal aPrice;
    /**
     * 成本价
     */
    private BigDecimal costPrice;
    /**
     * 安全库存
     */
    private Integer warnStore;
    /**
     * 已成功通过审核
     */
    private Integer mark;
    /**
     * 状态
     */
    private Integer status = 0;
    /**
     * pop库存
     */
    private Integer popStore = 0;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getInernalSn() {
        return inernalSn;
    }

    public void setInernalSn(String inernalSn) {
        this.inernalSn = inernalSn;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSp1() {
        return sp1;
    }

    public void setSp1(String sp1) {
        this.sp1 = sp1;
    }

    public String getSp2() {
        return sp2;
    }

    public void setSp2(String sp2) {
        this.sp2 = sp2;
    }

    public String getSp3() {
        return sp3;
    }

    public void setSp3(String sp3) {
        this.sp3 = sp3;
    }

    public BigDecimal getOriginalCost() {
        return originalCost;
    }

    public void setOriginalCost(BigDecimal originalCost) {
        this.originalCost = originalCost;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getaPrice() {
        if (aPrice == null) {
            aPrice = this.price;
        }
        return aPrice;
    }

    public void setaPrice(BigDecimal aPrice) {
        this.aPrice = aPrice;
    }

    public String getExternalSn() {
        return externalSn;
    }

    public void setExternalSn(String externalSn) {
        this.externalSn = externalSn;
    }

    public Integer getWarnStore() {
        return warnStore;
    }

    public void setWarnStore(Integer warnStore) {
        this.warnStore = warnStore;
    }

    public BigDecimal getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPopStore() {
        return popStore;
    }

    public void setPopStore(Integer popStore) {
        this.popStore = popStore;
    }

    public String getPic() {
        if (this.sp1 != null) {
            return JSONObject.parseObject(sp1).get("img").toString();
        } else {
            return "";
        }
    }

    public void setPic() {
    }

    public String getColorValue() {
        if (this.sp1 != null) {
            return JSONObject.parseObject(sp1).get("value").toString();
        } else {
            return "";
        }
    }

    public void setColorValue() {
    }

    public String getSizeValue() {
        if (this.sp2 != null) {
            return JSONObject.parseObject(sp2).get("value").toString();
        } else {
            return "";
        }
    }

    public void setSizeValue() {
    }

    public Long getColorId() {
        if (this.sp1 != null) {
            return JSONObject.parseObject(sp1).getLong("id");
        } else {
            return 0L;
        }
    }

    public void setColorId() {
    }

    public Long getSizeId() {
        if (this.sp2 != null) {
            return JSONObject.parseObject(sp2).getLong("id");
        } else {
            return 0L;
        }
    }

    public void setSizeId() {
    }

    public void setAvailableStore() {
    }

}
