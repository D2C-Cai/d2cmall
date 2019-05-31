package com.d2c.order.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 门店预约明细
 */
@Table(name = "o_store_reserve_item")
public class O2oSubscribeItem extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 商品数量
     */
    protected Integer quantity;
    /**
     * 预约单Id
     */
    @AssertColumn("预约单ID不能为空")
    private Long subscribeId;
    /**
     * 产品Id
     */
    @AssertColumn("商品ID不能为空")
    private Long productId;
    /**
     * 商品货号
     */
    private String productSn;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 商品售价
     */
    private BigDecimal price;
    /**
     * 商品吊牌价
     */
    private BigDecimal originalPrice;
    /**
     * SKU
     */
    @AssertColumn("商品条码ID不能为空")
    private Long productSkuId;
    /**
     * SKU 编号
     */
    private String productSkuSn;
    /**
     * 商品品类
     */
    private String productCategory;
    /**
     * 买家Id
     */
    private Long memberId;
    /**
     * 买家名称
     */
    private String memberName;
    /**
     * 设计师Id
     */
    private Long designerId;
    /**
     * 设计师名称
     */
    private String designerName;
    /**
     * 备注
     */
    private String itemMemo;
    /**
     * 销售属性1
     */
    private String sp1;
    /**
     * 销售属性2
     */
    private String sp2;
    /**
     * 销售属性2
     */
    private String sp3;
    /**
     * 商品图片
     */
    private String productImg;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductSn() {
        return productSn;
    }

    public void setProductSn(String productSn) {
        this.productSn = productSn;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getProductSkuId() {
        return productSkuId;
    }

    public void setProductSkuId(Long productSkuId) {
        this.productSkuId = productSkuId;
    }

    public String getProductSkuSn() {
        return productSkuSn;
    }

    public void setProductSkuSn(String productSkuSn) {
        this.productSkuSn = productSkuSn;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public Long getDesignerId() {
        return designerId;
    }

    public void setDesignerId(Long designerId) {
        this.designerId = designerId;
    }

    public String getDesignerName() {
        return designerName;
    }

    public void setDesignerName(String designerName) {
        this.designerName = designerName;
    }

    public String getItemMemo() {
        return itemMemo;
    }

    public void setItemMemo(String itemMemo) {
        this.itemMemo = itemMemo;
    }

    public Long getSubscribeId() {
        return subscribeId;
    }

    public void setSubscribeId(Long subscribeId) {
        this.subscribeId = subscribeId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
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

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
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

}
