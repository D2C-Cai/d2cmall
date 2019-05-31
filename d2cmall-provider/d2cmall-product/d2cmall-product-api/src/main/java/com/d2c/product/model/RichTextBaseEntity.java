package com.d2c.product.model;

import com.d2c.common.api.model.PreUserDO;

public abstract class RichTextBaseEntity extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * PC端详情
     */
    private String description;
    /**
     * 手机端详情
     */
    private String mobileDesc;

    @Override
    public Long getId() {
        return super.getId();
    }

    @Override
    public void setId(Long id) {
        super.setId(id);
    }

    public String getMobileDesc() {
        if (mobileDesc != null) {
            mobileDesc = mobileDesc.trim();
        }
        return mobileDesc;
    }

    public void setMobileDesc(String mobileDesc) {
        this.mobileDesc = mobileDesc;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
