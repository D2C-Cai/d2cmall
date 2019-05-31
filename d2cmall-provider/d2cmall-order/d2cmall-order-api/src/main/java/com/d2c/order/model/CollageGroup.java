package com.d2c.order.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.model.PreUserDO;
import com.d2c.member.model.MemberInfo;
import com.d2c.order.model.CollageOrder.CollageOrderStatus;
import com.d2c.product.model.CollagePromotion;
import com.d2c.util.date.DateUtil;
import com.d2c.util.serial.SerialNumUtil;

import javax.persistence.Table;
import java.util.Date;

/**
 * 拼团的团
 */
@Table(name = "o_collage_group")
public class CollageGroup extends PreUserDO {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 团编号
     */
    private String groupSn;
    /**
     * 发起人id
     */
    private Long initiatorMemberId;
    /**
     * 发起人昵称
     */
    private String initiatorName;
    /**
     * 发起人头像
     */
    private String headPic;
    /**
     * 成团人数
     */
    private Integer memberCount;
    /**
     * 当前人数
     */
    private Integer currentMemberCount = 0;
    /**
     * 当前人数
     */
    private Integer payMemberCount = 0;
    /**
     * 状态
     */
    private Integer status = GroupStatus.PROCESS.getCode();
    /**
     * 活动id
     */
    private Long promotionId;
    /**
     * 商品id
     */
    private Long productId;
    /**
     * 结束时间
     */
    private Date endDate;

    public CollageGroup() {
        this.groupSn = SerialNumUtil.buildCollageGroupSn();
    }

    public CollageGroup(MemberInfo memberInfo, CollagePromotion promotion) {
        this();
        this.initiatorMemberId = memberInfo.getId();
        this.initiatorName = memberInfo.getNickname();
        this.headPic = memberInfo.getHeadPic();
        this.memberCount = promotion.getMemberCount();
        this.promotionId = promotion.getId();
        this.productId = promotion.getProductId();
        // (活动开始后，活动时间不能改，所以取min(活动结束时间，当前时间+成团时间))
        this.endDate = new Date(new Date().getTime() + promotion.getExpireTime() * 60 * 1000)
                .before(promotion.getEndDate()) ? new Date(new Date().getTime() + promotion.getExpireTime() * 60 * 1000)
                : promotion.getEndDate();
    }

    public Long getInitiatorMemberId() {
        return initiatorMemberId;
    }

    public void setInitiatorMemberId(Long initiatorMemberId) {
        this.initiatorMemberId = initiatorMemberId;
    }

    public Integer getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(Integer memberCount) {
        this.memberCount = memberCount;
    }

    public Integer getCurrentMemberCount() {
        return currentMemberCount;
    }

    public void setCurrentMemberCount(Integer currentMemberCount) {
        this.currentMemberCount = currentMemberCount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Long promotionId) {
        this.promotionId = promotionId;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getGroupSn() {
        return groupSn;
    }

    public void setGroupSn(String groupSn) {
        this.groupSn = groupSn;
    }

    public String getInitiatorName() {
        return initiatorName;
    }

    public void setInitiatorName(String initiatorName) {
        this.initiatorName = initiatorName;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getPayMemberCount() {
        return payMemberCount;
    }

    public void setPayMemberCount(Integer payMemberCount) {
        this.payMemberCount = payMemberCount;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("id", this.getId());
        json.put("createDate", DateUtil.second2str2(this.getCreateDate()));
        json.put("status", this.getStatus());
        json.put("statusName", CollageOrderStatus.getStatus(this.getStatus()).getValue());
        json.put("groupSn", this.getGroupSn());
        json.put("initiatorMemberId", this.getInitiatorMemberId());
        json.put("initiatorName", this.getInitiatorName());
        json.put("headPic", this.getHeadPic());
        json.put("memberCount", this.getMemberCount());
        json.put("currentMemberCount", this.getCurrentMemberCount());
        json.put("payMemberCount", this.getPayMemberCount());
        json.put("endDate", this.getEndDate() != null ? this.getEndDate().getTime() : null);
        json.put("promotionId", this.getPromotionId());
        json.put("productId", this.getProductId());
        return json;
    }

    public enum GroupStatus {
        FAIL(-1, "拼团失败"), PROCESS(4, "拼团中"), SUCESS(8, "拼团成功");
        private int code;
        private String value;

        private GroupStatus(int code, String value) {
            this.code = code;
            this.value = value;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

}
