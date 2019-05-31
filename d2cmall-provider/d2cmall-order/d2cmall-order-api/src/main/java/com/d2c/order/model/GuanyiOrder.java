package com.d2c.order.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 管易做单记录
 *
 * @author Lain
 */
@Table(name = "o_guanyi_order")
public class GuanyiOrder extends PreUserDO {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 管易单据日期
     */
    private Date deliveryDate;
    /**
     * 单据管易Code
     */
    private String code;
    /**
     * 管易门店code（相当于仓库类型）
     */
    private String shopCode;
    /**
     * 管易门店名称
     */
    private String shopName;
    /**
     * 管易会员code（相当于D2C门店）
     */
    private String vipCode;
    /**
     * 管易会员名
     */
    private String vipName;
    /**
     * 实付金额
     */
    private BigDecimal payMent;
    /**
     * 需要零售单
     */
    private Integer retail = 0;
    /**
     * 需要零售退货单
     */
    private Integer retretail = 0;
    /**
     * 需要采购单
     */
    private Integer pur = 0;
    /**
     * 需要采购退货单
     */
    private Integer retpur = 0;
    /**
     * 需要调拨单
     */
    private Integer transfer = 0;
    /**
     * 需要更新网管物流信息
     */
    private Integer express = 0;
    /**
     * 第一次执行的时候是否成功 <br>
     * 0未处理(失败) 1无需处理的成功单据
     */
    private Integer success = 0;
    /**
     * 平台单号
     */
    private String platformCode;
    /**
     *
     */
    private String expressCode;
    /**
     * 快递公司
     */
    private String expressName;
    /**
     * 快递单号
     */
    private String expressNo;
    /**
     * 收件人
     */
    private String receiverName;
    /** ------------------------推送伯俊异常信息，没有发生异常则没有数据------------------------------- */
    /**
     * 是否发生过异常
     */
    private Integer burgeonFall = 0;
    /**
     * 错误信息
     */
    private String burgeonError;
    /**
     * 是否处理
     */
    private Integer burgeonHandle = 0;
    /**
     * 处理人
     */
    private String burgeonHandleMan;
    /**
     * 处理时间
     */
    private Date burgeonHandleDate;
    /**
     * 处理内容
     */
    private String burgeonHandleContent;
    /** ----------------------------------------物流异常信息-------------------------------------------- */
    /**
     * 是否发生过物流异常
     */
    private Integer expressFall = 0;
    /**
     * 更新物流的异常
     */
    private String expressError;
    /**
     * 是否处理
     */
    private Integer expressHandle = 0;
    /**
     * 物流单据处理人
     */
    private String expressHandleMan;
    /**
     * 处理时间
     */
    private Date expressHandleDate;
    /**
     * 处理内容
     */
    private String expressHandleContent;

    public GuanyiOrder() {
    }

    public GuanyiOrder(JSONObject obj) {
        this.deliveryDate = obj.getJSONObject("delivery_statusInfo").getDate("delivery_date");
        this.expressCode = obj.getString("express_code");
        this.expressNo = obj.getString("express_no");
        this.expressName = obj.getString("express_name");
        this.code = obj.getString("code");
        this.shopCode = obj.getString("shop_code");
        this.shopName = obj.getString("shop_name");
        this.vipCode = obj.getString("vip_code");
        this.vipName = obj.getString("vip_name");
        this.payMent = obj.getBigDecimal("payment");
        this.success = 0;
        this.platformCode = obj.getString("platform_code");
        this.receiverName = obj.getString("receiver_name");
        if ("D2C001".equals(shopCode)) {
            this.transfer = 1;
            this.retail = 1;
            this.express = 1;
        } else if ("D2C004".equals(shopCode)) {
            this.retpur = 1;
        } else if ("D2C022".equals(shopCode)) {
            this.transfer = 1;
            this.express = 1;
        } else if ("D2C024".equals(shopCode)) {
            this.transfer = 1;
            this.retail = 1;
            this.express = 1;
        } else {
            this.transfer = 1;
        }
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getVipCode() {
        return vipCode;
    }

    public void setVipCode(String vipCode) {
        this.vipCode = vipCode;
    }

    public String getVipName() {
        return vipName;
    }

    public void setVipName(String vipName) {
        this.vipName = vipName;
    }

    public BigDecimal getPayMent() {
        return payMent;
    }

    public void setPayMent(BigDecimal payMent) {
        this.payMent = payMent;
    }

    public Integer getRetail() {
        return retail;
    }

    public void setRetail(Integer retail) {
        this.retail = retail;
    }

    public Integer getRetretail() {
        return retretail;
    }

    public void setRetretail(Integer retretail) {
        this.retretail = retretail;
    }

    public Integer getPur() {
        return pur;
    }

    public void setPur(Integer pur) {
        this.pur = pur;
    }

    public Integer getRetpur() {
        return retpur;
    }

    public void setRetpur(Integer retpur) {
        this.retpur = retpur;
    }

    public Integer getTransfer() {
        return transfer;
    }

    public void setTransfer(Integer transfer) {
        this.transfer = transfer;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public String getPlatformCode() {
        return platformCode;
    }

    public void setPlatformCode(String platformCode) {
        this.platformCode = platformCode;
    }

    public Integer getBurgeonFall() {
        return burgeonFall;
    }

    public void setBurgeonFall(Integer burgeonFall) {
        this.burgeonFall = burgeonFall;
    }

    public String getBurgeonError() {
        return burgeonError;
    }

    public void setBurgeonError(String burgeonError) {
        this.burgeonError = burgeonError;
    }

    public Integer getBurgeonHandle() {
        return burgeonHandle;
    }

    public void setBurgeonHandle(Integer burgeonHandle) {
        this.burgeonHandle = burgeonHandle;
    }

    public String getBurgeonHandleMan() {
        return burgeonHandleMan;
    }

    public void setBurgeonHandleMan(String burgeonHandleMan) {
        this.burgeonHandleMan = burgeonHandleMan;
    }

    public Date getBurgeonHandleDate() {
        return burgeonHandleDate;
    }

    public void setBurgeonHandleDate(Date burgeonHandleDate) {
        this.burgeonHandleDate = burgeonHandleDate;
    }

    public String getBurgeonHandleContent() {
        return burgeonHandleContent;
    }

    public void setBurgeonHandleContent(String burgeonHandleContent) {
        this.burgeonHandleContent = burgeonHandleContent;
    }

    public String getExpressHandleMan() {
        return expressHandleMan;
    }

    public void setExpressHandleMan(String expressHandleMan) {
        this.expressHandleMan = expressHandleMan;
    }

    public String getExpressCode() {
        return expressCode;
    }

    public void setExpressCode(String expressCode) {
        this.expressCode = expressCode;
    }

    public String getExpressName() {
        return expressName;
    }

    public void setExpressName(String expressName) {
        this.expressName = expressName;
    }

    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }

    public Integer getExpressFall() {
        return expressFall;
    }

    public void setExpressFall(Integer expressFall) {
        this.expressFall = expressFall;
    }

    public String getExpressError() {
        return expressError;
    }

    public void setExpressError(String expressError) {
        this.expressError = expressError;
    }

    public Integer getExpressHandle() {
        return expressHandle;
    }

    public void setExpressHandle(Integer expressHandle) {
        this.expressHandle = expressHandle;
    }

    public Date getExpressHandleDate() {
        return expressHandleDate;
    }

    public void setExpressHandleDate(Date expressHandleDate) {
        this.expressHandleDate = expressHandleDate;
    }

    public String getExpressHandleContent() {
        return expressHandleContent;
    }

    public void setExpressHandleContent(String expressHandleContent) {
        this.expressHandleContent = expressHandleContent;
    }

    public Integer getExpress() {
        return express;
    }

    public void setExpress(Integer express) {
        this.express = express;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

}
