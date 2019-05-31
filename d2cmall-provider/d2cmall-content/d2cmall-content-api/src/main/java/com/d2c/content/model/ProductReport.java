package com.d2c.content.model;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Table(name = "v_product_report")
public class ProductReport extends PreUserDO {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 描述
     */
    private String content;
    /**
     * 图片
     */
    private String pic;
    /**
     * 状态
     */
    private Integer status = 0;
    /**
     * 商品id
     */
    private Long productId;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 商品图片
     */
    private String productImg;
    /**
     * 会员id
     */
    private Long memberId;
    /**
     * 手机号
     */
    private String loginCode;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 提交时间
     */
    private Date submitDate;
    /**
     * 审核时间
     */
    private Date verifyDate;
    /**
     * 审核说明
     */
    private String verifyReason;
    /**
     * 审核人
     */
    private String verifyMan;
    /**
     * 是否删除
     */
    private Integer deleted = 0;

    public String getStatusName() {
        return ReportStatusEnum.getByCode(this.status).name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Date getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
    }

    public Date getVerifyDate() {
        return verifyDate;
    }

    public void setVerifyDate(Date verifyDate) {
        this.verifyDate = verifyDate;
    }

    public String getVerifyReason() {
        return verifyReason;
    }

    public void setVerifyReason(String verifyReason) {
        this.verifyReason = verifyReason;
    }

    public String getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }

    public String getVerifyMan() {
        return verifyMan;
    }

    public void setVerifyMan(String verifyMan) {
        this.verifyMan = verifyMan;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public JSONArray getPicsToArray() {
        JSONArray arry = new JSONArray();
        if (pic != null && pic.length() > 0) {
            for (String item : pic.split(",")) {
                if (item == null) {
                    continue;
                }
                arry.add(item);
            }
        }
        return arry;
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("id", this.getId());
        obj.put("content", this.getContent());
        obj.put("pic", this.getPicsToArray());
        obj.put("status", this.getStatus());
        obj.put("statusName", this.getStatusName());
        obj.put("productId", this.getProductId());
        obj.put("productName", this.getProductName());
        obj.put("memberId", this.getMemberId());
        obj.put("submitDate", this.getSubmitDate());
        obj.put("verifyReason", this.getVerifyReason());
        obj.put("productImg", this.getProductImg());
        return obj;
    }

    public enum ReportStatusEnum {
        init(0, "待提交"), submit(1, "待审核"), refuse(-1, "审核不通过"), verified(2, "审核通过");
        private static Map<Integer, ReportStatusEnum> holder = new HashMap<>();

        static {
            for (ReportStatusEnum reportStatusEnum : values()) {
                holder.put(reportStatusEnum.getCode(), reportStatusEnum);
            }
        }

        private Integer code;
        private String name;

        ReportStatusEnum(int code, String name) {
            this.code = code;
            this.name = name;
        }

        public static ReportStatusEnum getByCode(Integer i) {
            return holder.get(i);
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
