package com.d2c.order.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;
import org.apache.commons.lang.StringUtils;

import javax.persistence.Table;
import java.util.Date;

/**
 * 优惠券包模板
 */
@Table(name = "o_coupon_def_group")
public class CouponDefGroup extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 名称
     */
    @AssertColumn("优惠券名称不能为空")
    private String name;
    /**
     * 编码
     */
    @AssertColumn("优惠券代码不能为空")
    private String code;
    /**
     * 暗码
     */
    private String cipher;
    /**
     * 是否启用：0未启用，1启用，-1删除
     */
    private Integer status = 0;
    /**
     * 随机数量
     */
    private Integer randomNum = 1;
    /**
     * 概率
     */
    private String rates = "1:1:1";
    /**
     * 固定优惠券定义ID，多个按","分割
     */
    private String fixDefIds;
    /**
     * 随机的优惠券定义ID，多个按","分割 下个版本修改[{id:11,quantity:1},{id:11,quantity:1},]
     */
    private String randomDefIds;
    /**
     * 发放数量（<=0不限）
     */
    private int quantity;
    /**
     * 已经领取的数量
     */
    private Integer claimed = 0;
    /**
     * 会员领用张数限制
     */
    private int claimLimit = 1;
    /**
     * 可以领用开始时间
     */
    private Date claimStart;
    /**
     * 可以领用结束时间
     */
    private Date claimEnd;
    /**
     * PC端自定义代码
     */
    private String pcCode;
    /**
     * WAP端自定义代码
     */
    private String wapCode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFixDefIds() {
        return fixDefIds;
    }

    public void setFixDefIds(String fixDefIds) {
        this.fixDefIds = fixDefIds;
    }

    public String getRandomDefIds() {
        return randomDefIds;
    }

    public void setRandomDefIds(String randomDefIds) {
        this.randomDefIds = randomDefIds;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getRandomNum() {
        return randomNum;
    }

    public void setRandomNum(Integer randomNum) {
        this.randomNum = randomNum;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCipher() {
        return cipher;
    }

    public void setCipher(String cipher) {
        this.cipher = cipher;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getClaimLimit() {
        return claimLimit;
    }

    public void setClaimLimit(int claimLimit) {
        this.claimLimit = claimLimit;
    }

    public Integer getClaimed() {
        return claimed;
    }

    public void setClaimed(Integer claimed) {
        this.claimed = claimed;
    }

    public String getRates() {
        return rates;
    }

    public void setRates(String rates) {
        this.rates = rates;
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

    /**
     * 是否超过领取/生成时间
     */
    public boolean checkClaimDate() {
        if (this.getStatus() <= 0) {
            return false;
        }
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

    public int getTotalNum() {
        int totalNum = this.getRandomNum() == null ? 0 : getRandomNum();
        String[] fixIds = null;
        if (!StringUtils.isEmpty(this.getFixDefIds())) {
            fixIds = this.getFixDefIds().split(",");
        }
        if (fixIds != null) {
            totalNum = totalNum + fixIds.length;
        }
        return totalNum;
    }

    public boolean isOver() {
        if (!(1 == this.getStatus() && this.checkClaimDate())) {
            return true;
        }
        return false;
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("id", this.getId());
        obj.put("name", this.getName());
        obj.put("couponName", this.getName());
        obj.put("isClaim", this.checkClaimDate());
        return obj;
    }

}
