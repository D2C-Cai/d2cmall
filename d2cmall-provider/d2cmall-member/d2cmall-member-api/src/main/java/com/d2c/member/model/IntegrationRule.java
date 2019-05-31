package com.d2c.member.model;

import com.d2c.common.api.model.PreUserDO;
import com.d2c.member.enums.PointRuleTypeEnum;
import com.d2c.util.string.StringUtil;

import javax.persistence.Table;

/**
 * 会员积分规则
 */
@Table(name = "m_member_integration_rule")
public class IntegrationRule extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 名称
     */
    private String name;
    /**
     * 固定（1：固定，0：比例）
     */
    private Integer fixed = 1;
    /**
     * 状态（1：启用，0：未启用，-1：删除）
     */
    private Integer status = 0;
    /**
     * 比率 （100:1）
     */
    private String ratio;
    /**
     * 类型,用来匹配积分变化渠道
     */
    private String type;
    /**
     * 增加或减少
     */
    private Integer direction;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getFixed() {
        return fixed;
    }

    public void setFixed(Integer fixed) {
        this.fixed = fixed;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRatio() {
        return ratio;
    }

    public void setRatio(String ratio) {
        this.ratio = ratio;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }

    public String getTypeName() {
        if (StringUtil.isNotBlank(this.getType())) {
            return PointRuleTypeEnum.valueOf(this.getType()).getDisplay();
        }
        return "";
    }

}
