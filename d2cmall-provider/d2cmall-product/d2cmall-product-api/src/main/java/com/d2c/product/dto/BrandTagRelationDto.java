package com.d2c.product.dto;

import com.d2c.product.model.Brand;
import com.d2c.product.model.BrandTag;
import com.d2c.product.model.BrandTagRelation;

public class BrandTagRelationDto extends BrandTagRelation {

    private static final long serialVersionUID = 1L;
    /**
     * 品牌
     */
    private Brand designer;
    /**
     * 标签
     */
    private BrandTag tag;

    public Brand getDesigner() {
        return designer;
    }

    public void setDesigner(Brand designer) {
        this.designer = designer;
    }

    public BrandTag getTag() {
        return tag;
    }

    public void setTag(BrandTag tag) {
        this.tag = tag;
    }

}
