package com.d2c.order.model;

import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;
import com.d2c.util.serial.SerialNumUtil;

import javax.persistence.Table;
import java.util.*;

/**
 * 投诉单
 */
@Table(name = "o_complaint")
public class Complaint extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 投诉单号
     */
    private String cmpSn;
    /**
     * 对应会员Id
     */
    private Long memberId;
    /**
     * 对应会员账号
     */
    private String loginCode;
    /**
     * 投诉人Id
     */
    @AssertColumn("投诉人不能为空")
    private Long complainantId;
    /**
     * 联系人
     */
    private String name;
    /**
     * 联系电话
     */
    private String tel;
    /**
     * QQ
     */
    private String qq;
    /**
     * 微信
     */
    private String wechat;
    /**
     * email
     */
    private String email;
    /**
     * 性别
     */
    private String sex;
    /**
     * 处理状态 -1已关闭， 0 新增， 1 处理中， 2 重新处理， 8 已完结
     */
    private String status = "INIT";
    /**
     * 紧急程度 1->3 高->低
     */
    private Integer level = 3;
    /**
     * 投诉时间
     */
    private Date cmpDate;
    /**
     * 投诉类别
     */
    private String type;
    /**
     * 投诉内容
     */
    private String content;
    /**
     * 业务类型
     */
    private String businessType;
    /**
     * 业务Id TODO 没有数据
     */
    private Long transactionId;
    /**
     * 业务单号
     */
    private String transactionSn;
    /**
     * 业务发送时间 TODO 没有数据
     */
    private Date transactionTime;
    /**
     * 业务信息说明
     */
    private String transactionInfo;
    /**
     * 承诺时间
     */
    private Date promiseDate;
    /**
     * 承诺人
     */
    private String promiseMan;
    /**
     * 承诺内容
     */
    private String promiseContent;
    /**
     * 完结时间
     */
    private Date overDate;
    /**
     * 完结人
     */
    private String overMan;
    /**
     * 完结说明
     */
    private String overContent;
    /**
     * 订单Sn
     */
    private String orderSn;
    /**
     * 特殊说明
     */
    private String memo;
    /**
     * 处理时间
     */
    private Date dealDate;
    /**
     * 处理内容
     */
    private String dealContent;
    /**
     * 回访时间
     */
    private Date callBackDate;
    /**
     * 投诉图片
     */
    private String pic;
    /**
     * 附件
     */
    private String filePath;

    public Complaint() {
        cmpSn = SerialNumUtil.buildComplaintSn();
    }

    public String getCmpSn() {
        return cmpSn;
    }

    ;

    public void setCmpSn(String cmpSn) {
        this.cmpSn = cmpSn;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getComplainantId() {
        return complainantId;
    }

    public void setComplainantId(Long complainantId) {
        this.complainantId = complainantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Date getCmpDate() {
        return cmpDate;
    }

    public void setCmpDate(Date cmpDate) {
        this.cmpDate = cmpDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
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

    public Date getPromiseDate() {
        return promiseDate;
    }

    public void setPromiseDate(Date promiseDate) {
        this.promiseDate = promiseDate;
    }

    public String getPromiseMan() {
        return promiseMan;
    }

    public void setPromiseMan(String promiseMan) {
        this.promiseMan = promiseMan;
    }

    public String getPromiseContent() {
        return promiseContent;
    }

    public void setPromiseContent(String promiseContent) {
        this.promiseContent = promiseContent;
    }

    public Date getOverDate() {
        return overDate;
    }

    public void setOverDate(Date overDate) {
        this.overDate = overDate;
    }

    public String getOverMan() {
        return overMan;
    }

    public void setOverMan(String overMan) {
        this.overMan = overMan;
    }

    public String getOverContent() {
        return overContent;
    }

    public void setOverContent(String overContent) {
        this.overContent = overContent;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public Date getCallBackDate() {
        return callBackDate;
    }

    public void setCallBackDate(Date callBackDate) {
        this.callBackDate = callBackDate;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Date getDealDate() {
        return dealDate;
    }

    public void setDealDate(Date dealDate) {
        this.dealDate = dealDate;
    }

    public String getDealContent() {
        return dealContent;
    }

    public void setDealContent(String dealContent) {
        this.dealContent = dealContent;
    }

    public String getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getBusinessName() {
        if (businessType == null || "".equals(businessType)) {
            return "";
        }
        BusinessType type = BusinessType.getBusinessTypeByName(businessType);
        return type.toZhString();
    }

    public void setBusinessName() {
    }

    public String[] getPictures() {
        if (this.getPic() != null) {
            return pic.split(",");
        }
        return null;
    }

    public void setPictures() {
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String[] getFilePathList() {
        if (this.filePath != null) {
            return this.filePath.split(",");
        }
        return null;
    }

    public void setFilePathList() {
    }

    public List<Map<String, String>> getFileList() {
        if (this.filePath != null) {
            List<Map<String, String>> list = new ArrayList<Map<String, String>>();
            String[] files = this.filePath.split(",");
            for (String file : files) {
                Map<String, String> map = new HashMap<String, String>();
                String[] fileName = file.split("_");
                int size = fileName.length;
                map.put("name", fileName[size - 1]);
                map.put("url", file);
                list.add(map);
            }
            return list;
        }
        return null;
    }

    public void setFileList(List<Map<String, String>> list) {
    }

    /**
     * -1已关闭， 0 保存， 1 处理中，2 重新处理，5处理完成，8 已完结
     */
    public enum ComplaintStatus {
        CLOSE(-1), INIT(0), PROCESS(1), REPROCESS(2), FINISH(5), SUCCESS(8);
        private static Map<Integer, ComplaintStatus> holder = new HashMap<Integer, ComplaintStatus>();

        static {
            for (ComplaintStatus cs : values()) {
                holder.put(cs.getCode(), cs);
            }
        }

        private int code;

        ComplaintStatus(int code) {
            this.code = code;
        }

        public static ComplaintStatus getStatus(int i) {
            return holder.get(i);
        }

        public int getCode() {
            return code;
        }
    }

    public enum BusinessType {
        QUALITY(1), REFUND(2), RESHIP(3), EXCHANGE(4), DELIVERY(5), CHANGEINFO(6);
        private int code;

        BusinessType(int code) {
            this.code = code;
        }

        public static BusinessType getBusinessTypeByName(String busName) {
            if (busName == null || busName.length() == 0) {
                return null;
            }
            for (BusinessType type : BusinessType.values()) {
                if (type.name().equals(busName)) {
                    return type;
                }
            }
            return null;
        }

        public int getCode() {
            return code;
        }

        public String toZhString() {
            switch (this.getCode()) {
                case 1:
                    return "质量问题";
                case 2:
                    return "申请退款";
                case 3:
                    return "申请退货";
                case 4:
                    return "申请换货";
                case 5:
                    return "催促发货";
                case 6:
                    return "修改信息";
            }
            return "未知";
        }
    }

}
