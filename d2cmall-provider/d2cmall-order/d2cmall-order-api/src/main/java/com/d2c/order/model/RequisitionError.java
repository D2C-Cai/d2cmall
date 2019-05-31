package com.d2c.order.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.model.PreUserDO;
import com.d2c.util.string.StringUtil;

import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 调拨采购异常
 *
 * @author Lain
 */
@Table(name = "o_requisition_error")
public class RequisitionError extends PreUserDO {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 类型
     */
    private String type;
    /**
     * 调拨单号
     */
    private String requisitionSn;
    /**
     * 关联单号
     */
    private String relationSn;
    /**
     * sku
     */
    private String barcode;
    /**
     * 数量
     */
    private Integer quantity;
    /**
     * 物流单号
     */
    private String deliverySn;
    /**
     * 发货公司
     */
    private String deliveryCorp;
    /**
     * 备注说明
     */
    private String remark;
    /**
     * 操作记录
     */
    private String log;
    /**
     * 处理人
     */
    private String operator;
    /**
     * 货号
     */
    private String productSn;
    /**
     * 设计师货号
     */
    private String externalSn;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 商品图片
     */
    private String productImg;
    /**
     * 颜色
     */
    private String sp1;
    /**
     * 尺码
     */
    private String sp2;
    /**
     * 顶级分类
     */
    private String topCategory;
    /**
     * 商品分类
     */
    private String productCategory;
    /**
     * 品牌名称
     */
    private String brandName;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRequisitionSn() {
        return requisitionSn;
    }

    public void setRequisitionSn(String requisitionSn) {
        this.requisitionSn = requisitionSn;
    }

    public String getRelationSn() {
        return relationSn;
    }

    public void setRelationSn(String relationSn) {
        this.relationSn = relationSn;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getDeliverySn() {
        return deliverySn;
    }

    public void setDeliverySn(String deliverySn) {
        this.deliverySn = deliverySn;
    }

    public String getDeliveryCorp() {
        return deliveryCorp;
    }

    public void setDeliveryCorp(String deliveryCorp) {
        this.deliveryCorp = deliveryCorp;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
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

    public String getTopCategory() {
        return topCategory;
    }

    public void setTopCategory(String topCategory) {
        this.topCategory = topCategory;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getExternalSn() {
        return externalSn;
    }

    public void setExternalSn(String externalSn) {
        this.externalSn = externalSn;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public List<JSONObject> getLogList() {
        if (StringUtil.isNotBlank(log)) {
            List<JSONObject> list = JSONObject.parseArray(log, JSONObject.class);
            Collections.sort(list, new Comparator<JSONObject>() {
                @Override
                public int compare(JSONObject arg0, JSONObject arg1) {
                    return arg0.getIntValue("sort") > arg1.getIntValue("sort") ? 1 : 0;
                }
            });
            return list;
        }
        return new ArrayList<>();
    }

    public String getSp1Value() {
        if (this.sp1 != null) {
            return JSONObject.parseObject(this.sp1).get("value").toString();
        } else {
            return "";
        }
    }

    public String getSp2Value() {
        if (this.sp2 != null) {
            return JSONObject.parseObject(this.sp2).get("value").toString();
        } else {
            return "";
        }
    }

    public void buildProductByRequisitionItem(RequisitionItem requisitionItem) {
        this.productSn = requisitionItem.getProductSn();
        this.externalSn = requisitionItem.getExternalSn();
        this.productName = requisitionItem.getProductName();
        this.productImg = requisitionItem.getProductImg();
        this.sp1 = requisitionItem.getSp1();
        this.sp2 = requisitionItem.getSp2();
        this.topCategory = requisitionItem.getTopCategory();
        this.productCategory = requisitionItem.getProductCategory();
        this.brandName = requisitionItem.getDesignerName();
    }

}
