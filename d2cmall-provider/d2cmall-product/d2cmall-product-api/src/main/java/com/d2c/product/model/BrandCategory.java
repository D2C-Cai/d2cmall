package com.d2c.product.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;

/**
 * 品牌分类
 */
@Table(name = "p_brand_category")
public class BrandCategory extends PreUserDO {

    /**
     * 国家
     */
    public static final String COUNTRYTYPE = "country";
    /**
     * 风格
     */
    public static final String STYLETYPE = "style";
    /**
     * 设计领域
     */
    public static final String DESIGNAREATYPE = "designArea";
    /**
     * 市场
     */
    public static final String MARKETTYPE = "market";
    /**
     * key-value基础数据
     */
    public static final String KVData = "KVData";
    /**
     * 邮件
     */
    public static final String EMAIL = "email";
    /**
     * 运营小组
     */
    public static final String OPERATION = "operation";
    /**
     * 价格段
     */
    public static final String PRICE = "price";
    /**
     * 积分商品外部卡的类型
     */
    public static final String POINTPRODUCTSOURCE = "cardSource";
    private static final long serialVersionUID = 1L;
    /**
     * 代码
     */
    private String code;
    /**
     * 名称
     */
    private String name;
    /**
     * 类型
     */
    private String type;
    /**
     * 排序
     */
    private Integer orderList = 0;
    /**
     * 图片
     */
    private String pic;
    /**
     * 图片2
     */
    private String pic2;
    /**
     * 0下架 1上架
     */
    private Integer status = 0;
    /**
     * 其他信息（运营小组-linker对接人,mobile手机,weixin微信）
     */
    private String info;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getOrderList() {
        return orderList;
    }

    public void setOrderList(int orderList) {
        this.orderList = orderList;
    }

    public void setOrderList(Integer orderList) {
        this.orderList = orderList;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getPic2() {
        return pic2;
    }

    public void setPic2(String pic2) {
        this.pic2 = pic2;
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("id", this.getId());
        obj.put("name", this.getName());
        obj.put("sort", this.getOrderList());
        obj.put("code", this.getCode());
        obj.put("pic", this.getPic());
        obj.put("pic2", this.getPic2());
        return obj;
    }

}
