package com.d2c.product.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.model.PreUserDO;
import com.d2c.util.date.DateUtil;
import org.jsoup.helper.StringUtil;

import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 促销活动
 */
@Table(name = "p_promotion")
public class Promotion extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 促销内容
     */
    private String solution;
    /**
     * 促销类型
     */
    private Integer promotionType;
    /**
     * 适用范围 针对单件商品，还是一个订单
     */
    private Integer promotionScope;
    /**
     * 活动名称
     */
    private String name;
    /**
     * 是否启用
     */
    private Boolean enable = false;
    /**
     * 是否显示倒计时
     */
    private Boolean display = false;
    /**
     * 是否已删除
     */
    private Boolean deleted = false;
    /**
     * 全场商品
     */
    private Boolean whole = false;
    /**
     * 开始时间
     */
    private Date startTime;
    /**
     * 结束时间
     */
    private Date endTime;
    /**
     * PC自定义页面
     */
    private String customCode;
    /**
     * 手机自定义页面
     */
    private String mobileCode;
    /**
     * 活动说明
     */
    private String description;
    /**
     * 专场顶通封面(PC)
     */
    private String banner;
    /**
     * 专场封面pc
     */
    private String pcBanner;
    /**
     * 专场封面wap
     */
    private String wapBanner;
    /**
     * 定时 0没有 1定时中
     */
    private Integer timing = 0;
    /**
     * 提前显示价格
     */
    private Integer advance = 0;
    /**
     * 缩略图
     */
    private String smallPic;
    /**
     * 前缀文字
     */
    private String prefix;
    /**
     * 价格背景图片
     */
    private String priceBackPic;
    /**
     * 二维码背景图片
     */
    private String backgroundUrl;
    /**
     * 关联品牌（仅用于品牌页展示该活动）
     */
    private Long brandId;
    /**
     * 品牌
     */
    private String brandPic;

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public Integer getPromotionType() {
        return promotionType;
    }

    public void setPromotionType(Integer promotionType) {
        this.promotionType = promotionType;
    }

    public Integer getPromotionScope() {
        return promotionScope;
    }

    public void setPromotionScope(Integer promotionScope) {
        this.promotionScope = promotionScope;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getTiming() {
        return timing;
    }

    public void setTiming(Integer timing) {
        this.timing = timing;
    }

    public String getCustomCode() {
        return customCode;
    }

    public void setCustomCode(String customCode) {
        this.customCode = customCode;
    }

    public String getMobileCode() {
        return mobileCode;
    }

    public void setMobileCode(String mobileCode) {
        this.mobileCode = mobileCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getDisplay() {
        return display;
    }

    public void setDisplay(Boolean display) {
        this.display = display;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getPcBanner() {
        return pcBanner;
    }

    public void setPcBanner(String pcBanner) {
        this.pcBanner = pcBanner;
    }

    public String getWapBanner() {
        return wapBanner;
    }

    public void setWapBanner(String wapBanner) {
        this.wapBanner = wapBanner;
    }

    public boolean isWhole() {
        return whole;
    }

    public void setWhole(Boolean whole) {
        this.whole = whole;
    }

    public String getSmallPic() {
        return smallPic;
    }

    public void setSmallPic(String smallPic) {
        this.smallPic = smallPic;
    }

    public Integer getAdvance() {
        return advance;
    }

    public void setAdvance(Integer advance) {
        this.advance = advance;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getBrandPic() {
        return brandPic;
    }

    public void setBrandPic(String brandPic) {
        this.brandPic = brandPic;
    }

    public String getPriceBackPic() {
        return priceBackPic;
    }

    public void setPriceBackPic(String priceBackPic) {
        this.priceBackPic = priceBackPic;
    }

    public PromotionScope getEnumPromotionScope() {
        return PromotionScope.getStatus(promotionScope);
    }

    public String getPromotionScopeName() {
        return PromotionScope.getStatus(promotionScope).getName();
    }

    public PromotionType getEnumPromotionType() {
        if (promotionType == null) {
            return null;
        }
        PromotionType type = PromotionType.getStatus(promotionType);
        if (type == null) {
            type = PromotionType.DISCOUNT;
        }
        return type;
    }

    public String getBackgroundUrl() {
        return backgroundUrl;
    }

    public void setBackgroundUrl(String backgroundUrl) {
        this.backgroundUrl = backgroundUrl;
    }

    public boolean isOver() {
        Date date = new Date();
        if (this.enable != null && !enable.booleanValue()) {
            return true;
        }
        if (date.getTime() >= this.getStartTime().getTime() && date.getTime() <= this.getEndTime().getTime()) {
            return false;
        }
        return true;
    }

    public String getPromotionSuloName() {
        try {
            // 一口价没有solution
            if (StringUtil.isBlank(this.solution) && this.promotionType != 4)
                return "";
            switch (this.promotionType) {
                case 0:
                    return this.name;
                case 4:
                    return this.name;
                case 2:
                    String name2 = "";
                    for (String item : solution.split(",")) {
                        name2 += "满" + item.split("-")[0] + "减" + item.split("-")[1] + "，";
                    }
                    return name2.substring(0, name2.length() - 1);
                case 3:
                    return "每满" + solution.split("-")[0] + "减" + solution.split("-")[1];
                case 5:
                    String name5 = "";
                    for (String item : solution.split(",")) {
                        name5 += "满" + item.split("-")[0] + "件打"
                                + new BigDecimal(item.split("-")[1]).multiply(new BigDecimal(10)) + "折，";
                    }
                    return name5.substring(0, name5.length() - 1);
                case 6:
                    String name6 = "";
                    for (String item : solution.split(",")) {
                        name6 += "满" + item.split("-")[0] + "件减" + item.split("-")[1] + "元，";
                    }
                    return name6.substring(0, name6.length() - 1);
                case 7:
                    String name7 = "";
                    for (String item : solution.split(",")) {
                        name7 += item.split("-")[1] + "元任选" + item.split("-")[0] + "件，";
                    }
                    return name7.substring(0, name7.length() - 1);
                default:
                    return "";
            }
        } catch (Exception e) {
            return this.solution;
        }
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("id", this.getId());
        obj.put("type", this.getPromotionType());
        obj.put("promotionType", this.getPromotionType());
        obj.put("promotionTypeName", PromotionType.getStatus(this.getPromotionType()).getName());
        obj.put("promotionSulo", this.getPromotionSuloName());
        obj.put("promotionScope", this.getPromotionScope());
        obj.put("promotionName", this.getName());
        obj.put("promotionUrl", "/promotion/" + this.getId());
        obj.put("startTime", this.getStartTime() == null ? "" : DateUtil.second2str2(this.getStartTime()));
        obj.put("endTime", this.getEndTime() == null ? "" : DateUtil.second2str2(this.getEndTime()));
        obj.put("wapBanner", this.getWapBanner());
        obj.put("advance", (this.getAdvance() == 1 && this.getEnable()) ? 1 : 0);
        obj.put("prefix", this.getPrefix());
        obj.put("solution", this.getSolution());
        obj.put("status", this.getEnable());
        obj.put("backgroundUrl", this.getBackgroundUrl());
        obj.put("brandPic", this.getBrandPic());
        obj.put("priceBackPic", this.getPriceBackPic());
        return obj;
    }

    public JSONObject toSimpleJson() {
        JSONObject obj = new JSONObject();
        Date now = new Date();
        obj.put("id", this.getId());
        obj.put("name", this.getName());
        obj.put("promotionId", this.getId());
        obj.put("promotionType", this.getPromotionType());
        obj.put("promotionTypeName", PromotionType.getStatus(this.getPromotionType()).getName());
        obj.put("promotionSulo", this.getPromotionSuloName());
        obj.put("promotionScope", this.getPromotionScope());
        obj.put("promotionName", this.getName());
        obj.put("promotionUrl", "/promotion/" + this.getId());
        obj.put("remaining", this.getEndTime() == null ? 0 : this.getEndTime().getTime() - now.getTime());
        obj.put("solution", this.getSolution());
        obj.put("backgroundUrl", this.getBackgroundUrl());
        obj.put("brandPic", this.getBrandPic());
        obj.put("priceBackPic", this.getPriceBackPic());
        return obj;
    }

    // 促销类型
    public enum PromotionType {
        DISCOUNT(0, "折扣", 0), REDUCE_PRICE(1, "直减", 0), APRICE(4, "特价", 0),
        X_OFF_Y_STEP(2, "满减", 1), X_OFF_Y_UNLIMITED(3, "满减上不封顶", 1),
        X_OFF_Y_FULLPCS(5, "满件折", 1), X_OFF_Y_FULLSTEP(6, "满件减", 1), X_OFF_Y_PAY(7, "N元任选", 1);
        private static Map<Integer, PromotionType> holder = new HashMap<>();

        static {
            holder.put(0, PromotionType.DISCOUNT);
            holder.put(1, PromotionType.REDUCE_PRICE);
            holder.put(2, PromotionType.X_OFF_Y_STEP);
            holder.put(3, PromotionType.X_OFF_Y_UNLIMITED);
            holder.put(4, PromotionType.APRICE);
            holder.put(5, PromotionType.X_OFF_Y_FULLPCS);
            holder.put(6, PromotionType.X_OFF_Y_FULLSTEP);
            holder.put(7, PromotionType.X_OFF_Y_PAY);
        }

        private int code;
        private String name;
        private int scope;

        PromotionType(int code, String name, int scope) {
            this.name = name;
            this.code = code;
            this.scope = scope;
        }

        public static PromotionType getStatus(int i) {
            return holder.get(i);
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getScope() {
            return scope;
        }

        public void setScope(int scope) {
            this.scope = scope;
        }
    }

    // 促销针对的是单件商品, 还是整个订单, 组合策略（优惠落实到商品）
    public enum PromotionScope {
        GOODS(0), ORDER(1), COMBINATION(2);
        private static Map<Integer, PromotionScope> holder = new HashMap<>();

        static {
            for (PromotionScope scope : values()) {
                holder.put(scope.getCode(), scope);
            }
        }

        private int code;

        PromotionScope(int code) {
            this.code = code;
        }

        public static PromotionScope getStatus(int i) {
            return holder.get(i);
        }

        public int getCode() {
            return code;
        }

        public String getName() {
            switch (this.code) {
                case 0:
                    return "商品优惠";
                case 1:
                    return "订单优惠";
                case 2:
                    return "组合商品";
            }
            return "未知";
        }
    }

}
