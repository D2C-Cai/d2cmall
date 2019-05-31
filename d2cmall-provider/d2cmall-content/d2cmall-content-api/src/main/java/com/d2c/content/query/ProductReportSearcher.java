package com.d2c.content.query;

import com.d2c.common.api.query.model.BaseQuery;

import java.util.Date;

/**
 * 商品报告查询
 *
 * @author wwn
 */
public class ProductReportSearcher extends BaseQuery {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 会员id
     */
    private Long memberId;
    /**
     * 账号
     */
    private String loginCode;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 开始提交时间
     */
    private Date submitStartDate;
    /**
     * 提交结束时间
     */
    private Date submitEndDate;
    /**
     * 验证开始时间
     */
    private Date verifyStartDate;
    /**
     * 验证结束时间
     */
    private Date verifyEndDate;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 状态
     */
    private Integer status;

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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Date getSubmitStartDate() {
        return submitStartDate;
    }

    public void setSubmitStartDate(Date submitStartDate) {
        this.submitStartDate = submitStartDate;
    }

    public Date getSubmitEndDate() {
        return submitEndDate;
    }

    public void setSubmitEndDate(Date submitEndDate) {
        this.submitEndDate = submitEndDate;
    }

    public Date getVerifyStartDate() {
        return verifyStartDate;
    }

    public void setVerifyStartDate(Date verifyStartDate) {
        this.verifyStartDate = verifyStartDate;
    }

    public Date getVerifyEndDate() {
        return verifyEndDate;
    }

    public void setVerifyEndDate(Date verifyEndDate) {
        this.verifyEndDate = verifyEndDate;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
