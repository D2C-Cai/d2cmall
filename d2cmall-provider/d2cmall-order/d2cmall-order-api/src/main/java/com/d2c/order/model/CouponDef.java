package com.d2c.order.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Table;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 优惠券模板
 */
@Table(name = "o_coupon_def")
public class CouponDef extends PreUserDO {

    /**
     * 注册会员送100元优惠券
     */
    public static final Long REGISTERDEFID = 1L;
    private static final long serialVersionUID = 1L;
    /**
     * 名称（唯一键）
     */
    private String name;
    /**
     * 副标题
     */
    private String subTitle;
    /**
     * 图片
     */
    private String pic;
    /**
     * 使用说明
     */
    private String remark;
    /**
     * 类型
     */
    private String type;
    /**
     * 随机码
     */
    private String code;
    /**
     * 暗码
     */
    private String cipher;
    /**
     * 发放状态
     */
    private String managerStatus;
    /**
     * 面额或者折扣额95代表９.５折
     */
    private Integer amount;
    /**
     * 使用该优惠券，订单需要满足的金额
     */
    private Integer needAmount;
    /**
     * 发放数量（<=0不限）
     */
    private Integer quantity = 0;
    /**
     * 已经领取的数量（汇总字段）
     */
    private Integer claimed = 0;
    /**
     * 单人领用限制张数
     */
    private Integer claimLimit = 1;
    /**
     * 1：全场，2：指定设计师，3：指定商品
     */
    private String checkAssociation = Association.ALL.toString();
    /**
     * 领用开始时间
     */
    private Date claimStart;
    /**
     * 领用结束时间
     */
    private Date claimEnd;
    /**
     * 使用开始时间
     */
    private Date enableDate;
    /**
     * 使用结束时间
     */
    private Date expireDate;
    /**
     * 使用有效天数（如果>0 可用时间开始=领用时间，失效时间=领用时间+天数）
     */
    private Integer activeDay;
    /**
     * 使用有效小时（如果>0 可用时间开始=领用时间，失效时间=领用时间+小时数）
     */
    private Integer activeHour;
    /**
     * 跳转链接
     */
    private String redirectUrl;
    /**
     * 是否启用
     */
    private Integer enable = 0;
    /**
     * 关联类型：包含0,不包含1
     */
    private Boolean exclude = false;
    /**
     * 是否随机金额
     */
    private Boolean random = false;
    /**
     * 是否允许转让
     */
    private Boolean transfer = false;
    /**
     * 微信卡包的ID
     */
    private String wxCardId;
    /**
     * PC端自定义代码
     */
    private String pcCode;
    /**
     * WAP端自定义代码
     */
    private String wapCode;
    /**
     * 是否免费
     */
    private Integer free = 1;
    /**
     * 购买金额
     */
    private BigDecimal price;

    @AssertColumn
    public String validate() {
        if (StringUtils.isEmpty(this.getName())) {
            return "优惠券名称不能为空";
        }
        if (StringUtils.isEmpty(this.getType())) {
            return "优惠券类型不能为空";
        }
        if (this.getAmount() != null && this.getAmount() < 0) {
            return "优惠券面额不能小于0";
        }
        if (this.getClaimStart() == null) {
            return "领取开始时间不能为空";
        }
        if (this.getClaimEnd() == null) {
            return "领取结束时间不能为空";
        }
        if (this.getEnableDate() == null) {
            return "有效开始时间不能为空";
        }
        if (this.getExpireDate() == null) {
            return "过期时间不能为空";
        }
        if (this.getClaimStart().compareTo(this.getClaimEnd()) > 0) {
            return "领取开始时间，不能大于领取结束时间";
        }
        if ((this.getActiveDay() == null || this.getActiveDay() <= 0)
                && (this.getActiveHour() == null || this.getActiveHour() <= 0)) {
            if (this.getEnableDate().compareTo(this.getExpireDate()) > 0) {
                return "有效时间，不能大于过期时间";
            }
            if (this.getClaimStart().compareTo(this.getEnableDate()) > 0) {
                return "领取开始时间，不能大于有效开始时间";
            }
            if (this.getClaimEnd().compareTo(this.getExpireDate()) > 0) {
                return "领取结束时间，不能大于过期时间";
            }
        }
        return null;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getEnableDate() {
        return enableDate;
    }

    public void setEnableDate(Date enabledate) {
        this.enableDate = enabledate;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expiredate) {
        this.expireDate = expiredate;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getNeedAmount() {
        return needAmount;
    }

    public void setNeedAmount(Integer needAmount) {
        this.needAmount = needAmount;
    }

    public String getManagerStatus() {
        return managerStatus;
    }

    public void setManagerStatus(String managerStatus) {
        this.managerStatus = managerStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getClaimLimit() {
        return claimLimit;
    }

    public void setClaimLimit(Integer claimLimit) {
        this.claimLimit = claimLimit;
    }

    public Integer getActiveDay() {
        return activeDay;
    }

    public void setActiveDay(Integer activeDay) {
        this.activeDay = activeDay;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getCheckAssociation() {
        return checkAssociation;
    }

    public void setCheckAssociation(String checkAssociation) {
        this.checkAssociation = checkAssociation;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getExclude() {
        return exclude;
    }

    public void setExclude(Boolean exclude) {
        this.exclude = exclude;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public Boolean getRandom() {
        if (this.getType() != null && this.getType().equals(CouponType.DISCOUNT.toString())) {
            // 折扣商品，不支持随机
            random = false;
        }
        return random;
    }

    public void setRandom(Boolean random) {
        this.random = random;
    }

    public Integer getActiveHour() {
        return activeHour;
    }

    public void setActiveHour(Integer activeHour) {
        this.activeHour = activeHour;
    }

    public String getCipher() {
        return cipher;
    }

    public void setCipher(String cipher) {
        this.cipher = cipher;
    }

    public Date getClaimStart() {
        return claimStart;
    }

    public void setClaimStart(Date claimStart) {
        this.claimStart = claimStart;
    }

    public Date getClaimEnd() {
        return claimEnd;
    }

    public void setClaimEnd(Date claimEnd) {
        this.claimEnd = claimEnd;
    }

    public String getWxCardId() {
        return wxCardId;
    }

    public void setWxCardId(String wxCardId) {
        this.wxCardId = wxCardId;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public Boolean getTransfer() {
        return transfer;
    }

    public void setTransfer(Boolean transfer) {
        this.transfer = transfer;
    }

    public Integer getClaimed() {
        return claimed;
    }

    public void setClaimed(Integer claimed) {
        this.claimed = claimed;
    }

    public String getPcCode() {
        return pcCode;
    }

    public void setPcCode(String pcCode) {
        this.pcCode = pcCode;
    }

    public String getWapCode() {
        return wapCode;
    }

    public void setWapCode(String wapCode) {
        this.wapCode = wapCode;
    }

    public Integer getFree() {
        return free;
    }

    public void setFree(Integer free) {
        this.free = free;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * 是否超过领取/生成时间
     *
     * @param couponDef
     * @return
     */
    public boolean checkClaimDate() {
        boolean result = true;
        Date now = new Date();
        Date start = this.getClaimStart();
        Date end = this.getClaimEnd();
        if (start == null && end != null) {
            if (now.after(end)) {
                result = false;
            }
        } else if (start != null && end == null) {
            if (now.before(start)) {
                result = false;
            }
        } else if (start != null && end != null) {
            if (now.before(start) || now.after(end)) {
                result = false;
            }
        }
        return result;
    }

    public boolean isOver() {
        if (!(1 == this.getEnable() && this.checkClaimDate())) {
            return true;
        }
        return false;
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("id", this.getId());
        obj.put("type", this.getType());
        obj.put("name", this.getName());
        obj.put("amount", this.getAmount());
        obj.put("needAmount", this.getNeedAmount());
        obj.put("free", this.getFree());
        obj.put("price", this.getPrice());
        obj.put("couponName", this.getName());
        obj.put("activeDay", this.getActiveDay());
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        obj.put("enableDate", this.getEnableDate() == null ? "" : fmt.format(this.getEnableDate()));
        obj.put("expireDate", this.getExpireDate() == null ? "" : fmt.format(this.getExpireDate()));
        obj.put("remark", this.getRemark());
        obj.put("isClaim", this.checkClaimDate());
        return obj;
    }

    /**
     * CASH：抵用券，PASSWORD：密码券，DISCOUNT： 折扣券
     */
    public enum CouponType {
        CASH(0), PASSWORD(1), DISCOUNT(2);
        int code;

        CouponType(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

    /**
     * 注册优惠券默认为已发放状态 ， 密码券为初始状态
     */
    public enum ManagerStatus {
        INIT(0), SENDED(1);
        int code;

        ManagerStatus(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

    /**
     * 1：全场，2：指定品牌，3：指定商品
     */
    public enum Association {
        ALL, DESIGNER, PRODUCT;
    }

}
