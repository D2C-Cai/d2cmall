package com.d2c.member.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.model.PreUserDO;
import com.d2c.member.enums.PointRuleTypeEnum;
import com.d2c.util.string.StringUtil;

import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 会员积分
 */
@Table(name = "m_member_integration")
public class MemberIntegration extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 主账号ID
     */
    private Long memberId;
    /**
     * 会员账号
     */
    private String loginCode;
    /**
     * 活动积分类型
     */
    private String type;
    /**
     * 业务ID
     */
    private Long transactionId;
    /**
     * 提交业务流水号
     */
    private String transactionSn;
    /**
     * 业务发送时间
     */
    private Date transactionTime;
    /**
     * 业务来源信息
     */
    private String transactionInfo;
    /**
     * 1：收入，-1：支出
     */
    private Integer direction = 1;
    /**
     * 积分
     */
    private Integer point = 0;
    /**
     * 支付金额
     */
    private BigDecimal amount = new BigDecimal(0);
    /**
     * 商品ID
     */
    private Long productId;
    /**
     * 商品类型
     */
    private String productType;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 商品数量
     */
    private Integer productQuantity = 1;
    /**
     * 商品图片
     */
    private String pic;
    /**
     * 其他说明
     */
    private String remark;
    /**
     * 状态
     */
    private Integer status;

    public MemberIntegration() {
    }

    public MemberIntegration(MemberInfo memberInfo, PointRuleTypeEnum type, Integer point) {
        this.memberId = memberInfo.getId();
        this.loginCode = memberInfo.getLoginCode();
        this.type = type.name();
        this.direction = type.getDirection();
        this.point = point;
        this.status = StatusEnum.SUCCESS.getCode();
    }

    public MemberIntegration(PointRuleTypeEnum type, Long memberId, String loginCode, Long transactionId,
                             Date transactionTime) {
        this.memberId = memberId;
        this.loginCode = loginCode;
        this.type = type.name();
        this.direction = type.getDirection();
        this.status = StatusEnum.SUCCESS.getCode();
        this.transactionId = transactionId;
        this.transactionTime = transactionTime;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionSn() {
        return transactionSn;
    }

    public void setTransactionSn(String transactionSn) {
        this.transactionSn = transactionSn;
    }

    public Date getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Date transactionTime) {
        this.transactionTime = transactionTime;
    }

    public String getTransactionInfo() {
        return transactionInfo;
    }

    public void setTransactionInfo(String transactionInfo) {
        this.transactionInfo = transactionInfo;
    }

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTypeName() {
        if (StringUtil.isNotBlank(this.type)) {
            return PointRuleTypeEnum.valueOf(this.type).getDisplay();
        }
        return "";
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("productId", this.getProductId());
        obj.put("productType", this.getProductType());
        obj.put("productName", this.getProductName());
        obj.put("productQuantity", this.getProductQuantity());
        obj.put("pic", this.getPic());
        obj.put("point", this.getPoint());
        obj.put("amount", this.getAmount());
        obj.put("direction", this.getDirection());
        obj.put("type", this.getType());
        obj.put("typeName", this.getTypeName());
        obj.put("code", this.getRemark());
        obj.put("remark", this.getRemark());
        obj.put("transactionSn", this.getTransactionSn());
        obj.put("transactionTime", this.getTransactionTime());
        obj.put("transactionInfo", this.getTransactionInfo());
        return obj;
    }

    public enum StatusEnum {
        CLOSE(-1), INIT(0), SUCCESS(8);
        private int code;

        StatusEnum(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }
    }

}
