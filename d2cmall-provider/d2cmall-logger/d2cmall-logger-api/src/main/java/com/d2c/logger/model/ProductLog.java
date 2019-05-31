package com.d2c.logger.model;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.model.PreUserDO;
import com.d2c.util.serial.JsonUtil;

import javax.persistence.Table;

/**
 * 商品日志
 */
@Table(name = "log_product")
public class ProductLog extends PreUserDO {

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
     * 商品Id
     */
    private Long productId;
    /**
     * skuId
     */
    private Long productSkuId;

    public ProductLog() {
    }

    public ProductLog(String creator, String info, ProductLogType type, Long productId, Long skuId) {
        this.creator = creator;
        this.type = type.toString();
        this.productId = productId;
        this.info = info;
        this.productSkuId = skuId;
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
        JSONObject jsonObject = JSONObject.parseObject(info);
        String operation = jsonObject.getString("操作");
        String skustr = jsonObject.getString("sku");
        String updateBefore = jsonObject.getString("修改前");
        String updateAfter = jsonObject.getString("修改后");
        String remark = jsonObject.getString("备注");
        String promotionR = jsonObject.getString("活动");
        Product product = (Product) JsonUtil.jsonString2Object(info, Product.class);
        String result = "";
        if (!StringUtils.isEmpty(operation)) {
            result = "操作 :" + operation + "<br>";
        }
        if (!StringUtils.isEmpty(remark)) {
            result = "备注 :" + remark + "<br>";
        }
        if (!StringUtils.isEmpty(promotionR)) {
            result = result + "活动 :" + promotionR + "<br>";
        }
        if (!StringUtils.isEmpty(skustr)) {
            Sku sku = (Sku) JsonUtil.jsonString2Object(skustr, Sku.class);
            result = result + "<br>sku:<br>";
            if (!StringUtils.isEmpty(sku.getDesignerId())) {
                result = result + "designerId:" + sku.getDesignerId() + "<br>";
            }
            if (sku.getImgs() != null && sku.getImgs().length != 0) {
                result = result + "imgs:" + sku.getImgs() + "<br>";
            }
            if (!StringUtils.isEmpty(sku.getDesignerId())) {
                result = result + "img:" + sku.getImg() + "<br>";
            }
            if (sku.getColors() != null && sku.getImgs().length != 0) {
                result = result + "colors:" + sku.getColors() + "<br>";
            }
            if (!StringUtils.isEmpty(sku.getIsCod())) {
                result = result + "isCod:" + sku.getIsCod() + "<br>";
            }
            if (sku.getSizes() != null && sku.getSizes().length != 0) {
                result = result + "sizes:" + sku.getSizes() + "<br>";
            }
            if (!StringUtils.isEmpty(sku.getPrice())) {
                result = result + "price:" + sku.getPrice() + "<br>";
            }
            if (!StringUtils.isEmpty(sku.getMinPrice())) {
                result = result + "minPrice:" + sku.getMinPrice() + "<br>";
            }
            if (!StringUtils.isEmpty(sku.getName())) {
                result = result + "name:" + sku.getName() + "<br>";
            }
            if (!StringUtils.isEmpty(sku.getIsTopical())) {
                result = result + "isTopical:" + sku.getIsTopical() + "<br>";
            }
            if (!StringUtils.isEmpty(sku.getOriginalCost())) {
                result = result + "originalCost:" + sku.getOriginalCost() + "<br>";
            }
            if (!StringUtils.isEmpty(sku.getProductSellType())) {
                result = result + "productSellType:" + sku.getProductSellType() + "<br>";
            }
            if (!StringUtils.isEmpty(sku.getId())) {
                result = result + "id:" + sku.getId() + "<br>";
            }
            if (!StringUtils.isEmpty(sku.getSn())) {
                result = result + "sn:" + sku.getSn() + "<br>";
            }
            if (!StringUtils.isEmpty(sku.getAfter())) {
                result = result + "after:" + sku.getAfter() + "<br>";
            }
            if (!StringUtils.isEmpty(sku.getMark())) {
                result = result + "mark:" + sku.getMark() + "<br>";
            }
        }
        if (!StringUtils.isEmpty(updateBefore)) {
            result = result + "<br>修改前:<br>";
            if (updateAfter.startsWith("{")) {
                product = (Product) JsonUtil.jsonString2Object(updateBefore, Product.class);
                if (!StringUtils.isEmpty(product.getExternalSn())) {
                    result = result + "externalSn: " + product.getExternalSn() + "<br>";
                }
                if (!StringUtils.isEmpty(product.getaPrice())) {
                    result = result + "aPrice: " + product.getaPrice() + "<br>";
                }
                if (!StringUtils.isEmpty(product.getStore())) {
                    result = result + "store: " + product.getStore() + "<br>";
                }
                if (!StringUtils.isEmpty(product.getInernalSn())) {
                    result = result + "inernalSn: " + product.getInernalSn() + "<br>";
                }
                if (!StringUtils.isEmpty(product.getPopStore())) {
                    result = result + "popStore: " + product.getPopStore() + "<br>";
                }
                if (!StringUtils.isEmpty(product.getBarCode())) {
                    result = result + "barCode: " + product.getBarCode() + "<br>";
                }
                if (!StringUtils.isEmpty(product.getEquence())) {
                    result = result + "equence: " + product.getEquence() + "<br>";
                }
                if (!StringUtils.isEmpty(product.getSp1())) {
                    result = result + "sp1: " + product.getSp1() + "<br>";
                }
                if (!StringUtils.isEmpty(product.getSp2())) {
                    result = result + "sp2: " + product.getSp2() + "<br>";
                }
                if (!StringUtils.isEmpty(product.getPrice())) {
                    result = result + "price: " + product.getPrice() + "<br>";
                }
                if (!StringUtils.isEmpty(product.getOriginalCost())) {
                    result = result + "originalCost: " + product.getOriginalCost() + "<br>";
                }
                if (!StringUtils.isEmpty(product.getSn())) {
                    result = result + "sn: " + product.getSn() + "<br>";
                }
                if (!StringUtils.isEmpty(product.getStatus())) {
                    result = result + "status: " + product.getStatus() + "<br>";
                }
            } else {
                result = result + updateBefore + "<br>";
            }
        }
        if (!StringUtils.isEmpty(updateAfter)) {
            result = result + "<br>修改后:<br>";
            if (updateAfter.startsWith("{")) {
                product = (Product) JsonUtil.jsonString2Object(updateAfter, Product.class);
                if (!StringUtils.isEmpty(product.getExternalSn())) {
                    result = result + "externalSn: " + product.getExternalSn() + "<br>";
                }
                if (!StringUtils.isEmpty(product.getaPrice())) {
                    result = result + "aPrice: " + product.getaPrice() + "<br>";
                }
                if (!StringUtils.isEmpty(product.getStore())) {
                    result = result + "store: " + product.getStore() + "<br>";
                }
                if (!StringUtils.isEmpty(product.getInernalSn())) {
                    result = result + "inernalSn: " + product.getInernalSn() + "<br>";
                }
                if (!StringUtils.isEmpty(product.getPopStore())) {
                    result = result + "popStore: " + product.getPopStore() + "<br>";
                }
                if (!StringUtils.isEmpty(product.getBarCode())) {
                    result = result + "barCode: " + product.getBarCode() + "<br>";
                }
                if (!StringUtils.isEmpty(product.getEquence())) {
                    result = result + "equence: " + product.getEquence() + "<br>";
                }
                if (!StringUtils.isEmpty(product.getSp1())) {
                    result = result + "sp1: " + product.getSp1() + "<br>";
                }
                if (!StringUtils.isEmpty(product.getSp2())) {
                    result = result + "sp2: " + product.getSp2() + "<br>";
                }
                if (!StringUtils.isEmpty(product.getPrice())) {
                    result = result + "price: " + product.getPrice() + "<br>";
                }
                if (!StringUtils.isEmpty(product.getOriginalCost())) {
                    result = result + "originalCost: " + product.getOriginalCost() + "<br>";
                }
                if (!StringUtils.isEmpty(product.getSn())) {
                    result = result + "sn: " + product.getSn() + "<br>";
                }
                if (!StringUtils.isEmpty(product.getStatus())) {
                    result = result + "status: " + product.getStatus() + "<br>";
                }
            } else {
                result = result + updateAfter + "<br>";
            }
        }
        this.info = result;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getProductSkuId() {
        return productSkuId;
    }

    public void setProductSkuId(Long productSkuId) {
        this.productSkuId = productSkuId;
    }

    public enum ProductLogType {
        Insert, Update, UpdateSku, Delete, Category, Sort, Remark,
        Up, Down, Topical, Subscribe, Cod, Cart, After, Ad, Search,
        TagR, PromotionR, CouponR, ProductR, COUPON;

        public String getInfo(ProductLogType type) {
            switch (type) {
                case Insert:
                    return "新增商品";
                case Update:
                    return "商品编辑";
                case UpdateSku:
                    return "商品sku编辑";
                case Delete:
                    return "归档";
                case Category:
                    return "品类修改";
                case Sort:
                    return "更新排序";
                case Remark:
                    return "备注";
                case Up:
                    return "上架";
                case Down:
                    return "下架 ";
                case Topical:
                    return "置顶";
                case Subscribe:
                    return "门店试衣";
                case Cod:
                    return "货到付款";
                case Cart:
                    return "购物车";
                case After:
                    return "售后";
                case Ad:
                    return "广告搜索";
                case Search:
                    return "搜索";
                case TagR:
                    return "关联标签";
                case PromotionR:
                    return "关联活动";
                case CouponR:
                    return "关联优惠券";
                case ProductR:
                    return "推荐搭配";
                case COUPON:
                    return "优惠券";
            }
            return "";
        }
    }

}

class Sku {

    public String designerId;
    public String[] imgs;
    public String img;
    public Color[] colors;
    public String isCod;
    public String originalPrice;
    public String isCart;
    public String isSubscribe;
    public String price;
    public String minPrice;
    public String name;
    public Size[] sizes;
    public String isTopical;
    public String originalCost;
    public String productSellType;
    public String id;
    public String sn;
    public String after;
    public String isAfter;
    public String mark;

    public String getDesignerId() {
        return designerId;
    }

    public void setDesignerId(String designerId) {
        this.designerId = designerId;
    }

    public String[] getImgs() {
        return imgs;
    }

    public void setImgs(String[] imgs) {
        this.imgs = imgs;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Color[] getColors() {
        return colors;
    }

    public void setColors(Color[] colors) {
        this.colors = colors;
    }

    public String getIsCod() {
        return isCod;
    }

    public void setIsCod(String isCod) {
        this.isCod = isCod;
    }

    public String getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getIsCart() {
        return isCart;
    }

    public void setIsCart(String isCart) {
        this.isCart = isCart;
    }

    public String getIsSubscribe() {
        return isSubscribe;
    }

    public void setIsSubscribe(String isSubscribe) {
        this.isSubscribe = isSubscribe;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Size[] getSizes() {
        return sizes;
    }

    public void setSizes(Size[] sizes) {
        this.sizes = sizes;
    }

    public String getIsTopical() {
        return isTopical;
    }

    public void setIsTopical(String isTopical) {
        this.isTopical = isTopical;
    }

    public String getOriginalCost() {
        return originalCost;
    }

    public void setOriginalCost(String originalCost) {
        this.originalCost = originalCost;
    }

    public String getProductSellType() {
        return productSellType;
    }

    public void setProductSellType(String productSellType) {
        this.productSellType = productSellType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getAfter() {
        return after;
    }

    public void setAfter(String after) {
        this.after = after;
    }

    public String getIsAfter() {
        return isAfter;
    }

    public void setIsAfter(String isAfter) {
        this.isAfter = isAfter;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

}

class Size {

    public String img;
    public String code;
    public String groupId;
    public String name;
    public String id;
    public String value;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}

class Color {

    public String img;
    public String code;
    public String groupId;
    public String name;
    public String id;
    public String value;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}

class Product {

    public String externalSn;
    public String aPrice;
    public String store;
    public String inernalSn;
    public String popStore;
    public String barCode;
    public String equence;
    public String sp1;
    public String sp2;
    public String price;
    public String originalCost;
    public String sn;
    public String status;

    public String getExternalSn() {
        return externalSn;
    }

    public void setExternalSn(String externalSn) {
        this.externalSn = externalSn;
    }

    public String getaPrice() {
        return aPrice;
    }

    public void setaPrice(String aPrice) {
        this.aPrice = aPrice;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getInernalSn() {
        return inernalSn;
    }

    public void setInernalSn(String inernalSn) {
        this.inernalSn = inernalSn;
    }

    public String getPopStore() {
        return popStore;
    }

    public void setPopStore(String popStore) {
        this.popStore = popStore;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getEquence() {
        return equence;
    }

    public void setEquence(String equence) {
        this.equence = equence;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOriginalCost() {
        return originalCost;
    }

    public void setOriginalCost(String originalCost) {
        this.originalCost = originalCost;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
