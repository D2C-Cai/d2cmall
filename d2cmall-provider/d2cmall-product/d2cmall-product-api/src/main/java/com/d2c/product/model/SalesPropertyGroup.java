package com.d2c.product.model;

import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;

/**
 * 销售属性组
 */
@Table(name = "p_product_salesproperty_group")
public class SalesPropertyGroup extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 编号
     */
    @AssertColumn("商品销售属性代码不能为空")
    private String code;
    /**
     * 标题
     */
    @AssertColumn("商品销售属性标题不能为空")
    private String title;
    /**
     * 名称
     */
    @AssertColumn("商品销售属性名称不能为空")
    private String name;
    /**
     * 种类1,2,3 对应商品销售属性1,2,3
     */
    @AssertColumn("商品销售属性类型不能为空")
    private Integer type;
    /**
     * 状态：0停用，1启用
     */
    private Integer status = 1;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
