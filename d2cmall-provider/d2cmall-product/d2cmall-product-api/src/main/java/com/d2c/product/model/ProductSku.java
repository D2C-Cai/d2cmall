package com.d2c.product.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品SKU
 */
@Table(name = "p_product_sku")
public class ProductSku extends PreUserDO {

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
     * 限时购价格
     */
    private BigDecimal flashPrice;
    /**
     * 拼团价格
     */
    private BigDecimal collagePrice;
    /**
     * 考拉价格
     */
    private BigDecimal kaolaPrice;
    /**
     * 1 上架，0 下架，-1 删除
     */
    private Integer status = 1;
    /**
     * 上架时间
     */
    private Date upMarketDate;
    /**
     * 下架时间
     */
    private Date downMarketDate;
    /**
     * 上架人
     */
    private String upMan;
    /**
     * 下架人
     */
    private String downMan;
    /**
     * 顺丰库存
     */
    private Integer sfStock = 0;
    /**
     * 门店库存
     */
    private Integer stStock = 0;
    /**
     * 商品库存数量（每天凌晨更新：实际库存=顺丰库存+门店库存）
     */
    private Integer store = 0;
    /**
     * 第三方商品库存数量
     */
    private Integer popStore = 0;
    /**
     * 冻结库存
     */
    private Integer freezeStore = 0;
    /**
     * 活动设置库存
     */
    private Integer flashStore = 0;
    /**
     * 活动已售库存
     */
    private Integer flashSellStore = 0;
    /**
     * 总销量
     */
    private Integer sale = 0;
    /**
     * 排序
     */
    private Integer sequence = 0;
    /**
     * 安全库存
     */
    private Integer warnStore;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getUpMarketDate() {
        return upMarketDate;
    }

    public void setUpMarketDate(Date upMarketDate) {
        this.upMarketDate = upMarketDate;
    }

    public Date getDownMarketDate() {
        return downMarketDate;
    }

    public void setDownMarketDate(Date downMarketDate) {
        this.downMarketDate = downMarketDate;
    }

    public String getUpMan() {
        return upMan;
    }

    public void setUpMan(String upMan) {
        this.upMan = upMan;
    }

    public String getDownMan() {
        return downMan;
    }

    public void setDownMan(String downMan) {
        this.downMan = downMan;
    }

    public Integer getSfStock() {
        return sfStock;
    }

    public void setSfStock(Integer sfStock) {
        this.sfStock = sfStock;
    }

    public Integer getStStock() {
        return stStock;
    }

    public void setStStock(Integer stStock) {
        this.stStock = stStock;
    }

    public Integer getStore() {
        return store;
    }

    public void setStore(Integer store) {
        this.store = store;
    }

    public Integer getPopStore() {
        return popStore;
    }

    public void setPopStore(Integer popStore) {
        this.popStore = popStore;
    }

    public Integer getFreezeStore() {
        return freezeStore;
    }

    public void setFreezeStore(Integer freezeStore) {
        this.freezeStore = freezeStore;
    }

    public Integer getSale() {
        return sale;
    }

    public void setSale(Integer sale) {
        this.sale = sale;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
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

    public BigDecimal getFlashPrice() {
        return flashPrice;
    }

    public void setFlashPrice(BigDecimal flashPrice) {
        this.flashPrice = flashPrice;
    }

    public Integer getFlashStore() {
        return flashStore;
    }

    public void setFlashStore(Integer flashStore) {
        this.flashStore = flashStore;
    }

    public Integer getFlashSellStore() {
        return flashSellStore;
    }

    public void setFlashSellStore(Integer flashSellStore) {
        this.flashSellStore = flashSellStore;
    }

    public BigDecimal getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    public BigDecimal getKaolaPrice() {
        return kaolaPrice;
    }

    public void setKaolaPrice(BigDecimal kaolaPrice) {
        this.kaolaPrice = kaolaPrice;
    }

    public BigDecimal getCollagePrice() {
        return collagePrice;
    }

    public void setCollagePrice(BigDecimal collagePrice) {
        this.collagePrice = collagePrice;
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

    public int getAllStore() {
        if (status <= 0) {
            return 0;
        }
        return store + popStore;
    }

    public int getAvailableStore() {
        if (status <= 0) {
            return 0;
        }
        int available = store + popStore - freezeStore;
        return available <= 0 ? 0 : available;
    }

    public int getAvailableFlashStore() {
        if (status <= 0) {
            return 0;
        }
        int available = flashStore - flashSellStore - freezeStore;
        if ((store + popStore) <= (flashStore - flashSellStore)) {
            available = this.getAvailableStore();
        }
        return available <= 0 ? 0 : available;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof ProductSku))
            return false;
        ProductSku sku = (ProductSku) obj;
        if (sku.getId() != null && this.getId() != null && sku.getId().equals(this.getId()))
            return true;
        if (sku.getSn() != null && this.getSn() != null && sku.getSn().equals(this.getSn()))
            return true;
        return super.equals(obj);
    }

    public JSONObject logJson() {
        JSONObject json = new JSONObject();
        json.put("inernalSn", this.getInernalSn());
        json.put("externalSn", this.getExternalSn());
        json.put("sn", this.getSn());
        json.put("barCode", this.getBarCode());
        json.put("sp1", this.getSp1());
        json.put("sp2", this.getSp2());
        json.put("sp3", this.getSp3());
        json.put("originalCost", this.getOriginalCost());
        json.put("price", this.getPrice());
        json.put("aPrice", this.getaPrice());
        json.put("status", this.getStatus());
        json.put("store", this.getStore());
        json.put("popStore", this.getPopStore());
        json.put("sequence", this.getSequence());
        return json;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject(true);
        json.put("id", this.getId());
        json.put("size", this.getSizeValue());
        json.put("sizeId", this.getSizeId());
        json.put("color", this.getColorValue());
        json.put("colorId", this.getColorId());
        json.put("sn", this.getSn());
        json.put("store", this.getAvailableStore());
        json.put("flashStore", this.getAvailableFlashStore());
        json.put("freezeStore", this.getFreezeStore());
        return json;
    }

}
