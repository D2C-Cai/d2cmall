package com.d2c.logger.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;
import com.d2c.logger.support.MsgResource;
import com.d2c.util.date.DateUtil;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Table;
import java.util.Date;

/**
 * 站内消息
 */
@Table(name = "message")
public class Message extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 标题
     */
    private String title;
    /**
     * 消息内容
     */
    private String content;
    /**
     * 11.发货提醒 12.到货提醒 13.调拨单提醒 14.对账单提醒 <br>
     * 21.订单未支付 22.退款通知 23.退货通知 24.换货通知 25.购买回复 26.投诉反馈回复 27.门店预约通知 28.代付提醒 29.其他 <br>
     * 31.优惠券到账提醒 32.优惠券过期提醒 33.钱包充值 34.钱包消费 35.红包消息 <br>
     * 41.点赞提醒 42.关注 43.关注用户发布买家秀提醒 44.买家秀评论和回复提醒 45.直播提醒 <br>
     * 51.开抢提醒 52.货到通知 <br>
     * 61.活动精选 <br>
     * 71.设计师上新 72.品牌推荐<br>
     * 81.提现单提醒 82.返利单提醒 83.账户流水 84.访客提醒 85.邀请注册提醒 86.关店提醒 89.其他
     */
    private Integer type = 0;
    /**
     * 0:未读 1：已读
     */
    private Integer status = 0;
    /**
     * 接收者ID
     */
    private Long recId;
    /**
     * 商品 /detail/product/{id} 设计师 /showroom/designer/{id} <br>
     * 买家秀 /detail/share/{id} 直播/zegolive/watch/{id} <br>
     * 我的订单 /detail/order/{orderSn} 我的钱包 /member/account/info <br>
     * 我的优惠券 /coupon/memberCoupon 我的红包 /check/redPacket <br>
     * 我的粉丝 /myInterest/myFans 商品报告 /detail/productreport/{id} <br>
     * 客服/customer/service 反馈投诉 /feedback/{id} <br>
     * 跳到原生模块内容(为了兼容旧版本会跳wap的问题) /promotion/items?tagId=xx&sectionId=xx<br>
     * 我的提现/to/partner/cash 我的团队/to/partner/team 售货明细 /to/partner/sales<br>
     * 账号明细 /to/partner/account/item 买手店铺 /to/partner/store<br>
     * 我的买手 /to/partner/mine 退货单详细/member/reship/{id}<br>
     */
    private String url;
    /**
     * 是否全站
     */
    private Integer global = 0;
    /**
     * 过期时间
     */
    private Date expire;
    /**
     * 消息定义id
     */
    private Long defId;
    /**
     * 消息图片 如果有多张图片，逗号分隔
     */
    private String pic;
    /**
     * 其他信息 <br>
     * 42 nickName,headPic,memberId <br>
     * 41,43 nickName,headPic,memberId,sharePic,shareId <br>
     * 44 nickName,headPic,memberId,sharePic,shareId,active,content <br>
     * 31,32 money
     */
    private String other;

    public String getTypeName() {
        return MsgResource.getTypeName(this.type);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getRecId() {
        return recId;
    }

    public void setRecId(Long recId) {
        this.recId = recId;
    }

    public Integer getGlobal() {
        return global;
    }

    public void setGlobal(Integer global) {
        this.global = global;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getExpire() {
        return expire;
    }

    public void setExpire(Date expire) {
        this.expire = expire;
    }

    public Long getDefId() {
        return defId;
    }

    public void setDefId(Long defId) {
        this.defId = defId;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    private JSONObject getOtherJson() {
        JSONObject obj = null;
        if (this.getOther() != null) {
            obj = JSON.parseObject(this.getOther());
        }
        return obj;
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("id", this.getId());
        obj.put("createDate", DateUtil.second2str2(this.getCreateDate()));
        obj.put("type", this.getType());
        obj.put("typeName", this.getTypeName());
        obj.put("title", this.getTitle());
        obj.put("content", this.getContent());
        obj.put("status", this.getStatus());
        obj.put("recId", this.getRecId());
        if (StringUtils.isNotBlank(this.getUrl())) {
            obj.put("url", this.getUrl());
        } else {
            obj.put("url", "/message/" + this.getId());
        }
        obj.put("global", this.getGlobal() != null ? this.getGlobal() : 0);
        obj.put("pic", this.getPic());
        obj.put("timestamp", this.getCreateDate().getTime());
        obj.put("other", getOtherJson());
        return obj;
    }

    @AssertColumn
    public String validate() {
        if (this.getGlobal() == 1 && (this.getType() < 60 || this.getType() > 79)) {
            return "该类型消息不允许发全站消息";
        }
        return null;
    }

}