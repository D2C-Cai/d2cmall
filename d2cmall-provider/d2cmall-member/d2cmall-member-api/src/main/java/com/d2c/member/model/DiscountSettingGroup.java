package com.d2c.member.model;

import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;
import java.util.Date;

/**
 * 折扣组
 */
@Table(name = "m_discount_group")
public class DiscountSettingGroup extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 名称
     */
    @AssertColumn("折扣组名称不能为空")
    private String name;
    /**
     * 状态，0 未启用,1 启用
     */
    private Integer status = 0;
    /**
     * 折扣组类型
     */
    private String type;
    /**
     * 开始时间 , 要求定时任务
     */
    private Date beginDate;
    /**
     * 结束时间
     */
    private Date endDate;

    public String getTypeName() {
        if (this.type == null) {
            return "";
        }
        switch (GroupType.valueOf(this.type).code) {
            case 1:
                return "经销商";
            case 2:
                return "企业内";
            default:
                return "未知";
        }
    }

    public void setTypeName() {
    }

    public String getName() {
        return name;
    }

    ;

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public enum GroupType {
        Distributor(1), GroupCustom(2);
        private int code;

        GroupType(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

}
