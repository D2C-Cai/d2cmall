package com.d2c.order.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.annotation.HideColumn;
import com.d2c.common.api.annotation.HideEnum;
import com.d2c.common.api.model.PreUserDO;
import com.d2c.member.model.MemberInfo;
import com.d2c.product.model.Product;
import com.d2c.product.model.ProductSku;

import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 购物车明细
 */
@Table(name = "o_cart_item")
public class CartItem extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 产品Id
     */
    @AssertColumn("商品不能为空")
    private Long productId;
    /**
     * 产品编号
     */
    private String productSn;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 产品图片
     */
    private String productImg;
    /**
     * SKU Id
     */
    @AssertColumn("商品条码Id不能为空")
    private Long productSkuId;
    /**
     * SKU 名称
     */
    private String productSkuName;
    /**
     * SKU 编号
     */
    @AssertColumn("商品条码不能为空")
    private String productSkuSn;
    /**
     * 贸易类型
     */
    private String productTradeType;
    /**
     * 运营商品说明
     */
    private String productRemark;
    /**
     * 购买数量
     */
    private int quantity;
    /**
     * 销售价格
     */
    private BigDecimal price;
    /**
     * 加入购物车时的价格
     */
    private BigDecimal oldPrice;
    /**
     * 吊牌价格
     */
    private BigDecimal originalPrice;
    /**
     * 买家ID
     */
    @AssertColumn("会员ID不能为空")
    private Long buyMemberId;
    /**
     * 买家账号
     */
    @HideColumn(type = HideEnum.MOBILE)
    private String buyMemberLoginCode;
    /**
     * 买家手机号
     */
    @HideColumn(type = HideEnum.MOBILE)
    private String buyMemberMobile;
    /**
     * 设计师ID
     */
    private Long designerId;
    /**
     * 设计师编码
     */
    private String designerCode;
    /**
     * 设计师名称
     */
    private String designerName;
    /**
     * 分销商ID
     */
    private Long partnerId;
    /**
     * 分享的形式
     */
    private String partnerStyle;
    /**
     * 销售属性1
     */
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
     * 是否售后
     */
    private int after = 1;
    /**
     * 是否包税
     */
    private int taxation = 1;
    /**
     * 税费金额，多个,号隔开
     */
    private String taxPrice;
    /**
     * 设备
     */
    private String device;
    /**
     * app版本
     */
    private String appVersion;

    public CartItem() {
    }

    public CartItem(Product product, ProductSku productSku, MemberInfo memberInfo) {
        this.setProductSkuId(productSku.getId());
        this.setProductSkuSn(productSku.getSn());
        this.setProductSkuName(productSku.getSn());
        this.setSp1(productSku.getSp1());
        this.setSp2(productSku.getSp2());
        this.setSp3(productSku.getSp3());
        this.setProductId(product.getId());
        this.setProductName(product.getName());
        this.setProductSn(product.getInernalSn());
        this.setProductTradeType(product.getProductTradeType());
        this.setPrice(productSku.getPrice());
        this.setOldPrice(productSku.getPrice());
        this.setOriginalPrice(product.getOriginalPrice());
        this.setProductImg(product.getProductImageCover());
        this.setAfter(product.getAfter());
        this.setTaxation(product.getTaxation());
        this.setTaxPrice(product.getTaxPrice());
        this.setDesignerId(product.getDesignerId());
        this.setDesignerCode(product.getDesignerCode());
        this.setDesignerName(product.getDesignerName());
        this.setBuyMemberId(memberInfo.getId());
        this.setBuyMemberLoginCode(memberInfo.getLoginCode());
        this.setBuyMemberMobile(memberInfo.getMobile());
        this.setProductRemark(product.getRemark());
    }

    // 数量加1
    public void incrementQuantity() {
        quantity++;
    }

    // 数量减1
    public void decreaseQuantity() {
        if (quantity > 1) {
            quantity--;
        } else
            quantity = 0;
    }

    /**
     * 现在库存数是否满足购物车的数量
     */
    public Boolean isMeetInventory(int inventoryQuantity) {
        if (this.quantity <= inventoryQuantity) {
            return true;
        }
        return false;
    }

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

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public Long getProductSkuId() {
        return productSkuId;
    }

    public void setProductSkuId(Long productSkuId) {
        this.productSkuId = productSkuId;
    }

    public String getProductSkuName() {
        return productSkuName;
    }

    public void setProductSkuName(String productSkuName) {
        this.productSkuName = productSkuName;
    }

    public String getProductSkuSn() {
        return productSkuSn;
    }

    public void setProductSkuSn(String productSkuSn) {
        this.productSkuSn = productSkuSn;
    }

    public String getProductTradeType() {
        return productTradeType;
    }

    public void setProductTradeType(String productTradeType) {
        this.productTradeType = productTradeType;
    }

    public String getProductRemark() {
        return productRemark;
    }

    public void setProductRemark(String productRemark) {
        this.productRemark = productRemark;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(BigDecimal oldPrice) {
        this.oldPrice = oldPrice;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Long getBuyMemberId() {
        return buyMemberId;
    }

    public void setBuyMemberId(Long buyMemberId) {
        this.buyMemberId = buyMemberId;
    }

    public String getBuyMemberLoginCode() {
        return buyMemberLoginCode;
    }

    public void setBuyMemberLoginCode(String buyMemberLoginCode) {
        this.buyMemberLoginCode = buyMemberLoginCode;
    }

    public String getBuyMemberMobile() {
        return buyMemberMobile;
    }

    public void setBuyMemberMobile(String buyMemberMobile) {
        this.buyMemberMobile = buyMemberMobile;
    }

    public Long getDesignerId() {
        return designerId;
    }

    public void setDesignerId(Long designerId) {
        this.designerId = designerId;
    }

    public String getDesignerCode() {
        return designerCode;
    }

    public void setDesignerCode(String designerCode) {
        this.designerCode = designerCode;
    }

    public String getDesignerName() {
        return designerName;
    }

    public void setDesignerName(String designerName) {
        this.designerName = designerName;
    }

    public Long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Long partnerId) {
        this.partnerId = partnerId;
    }

    public String getPartnerStyle() {
        return partnerStyle;
    }

    public void setPartnerStyle(String partnerStyle) {
        this.partnerStyle = partnerStyle;
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

    public int getAfter() {
        return after;
    }

    public void setAfter(int after) {
        this.after = after;
    }

    public int getTaxation() {
        return taxation;
    }

    public void setTaxation(int taxation) {
        this.taxation = taxation;
    }

    public String getTaxPrice() {
        return taxPrice;
    }

    public void setTaxPrice(String taxPrice) {
        this.taxPrice = taxPrice;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getSp1Img() {
        if (this.sp1 != null) {
            return JSONObject.parseObject(sp1).get("img").toString();
        } else {
            return "";
        }
    }

}
