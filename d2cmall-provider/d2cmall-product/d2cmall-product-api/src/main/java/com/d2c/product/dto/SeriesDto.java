package com.d2c.product.dto;

import com.d2c.product.model.Series;

public class SeriesDto extends Series {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String styleName;
    private String priceName;

    public String getStyleName() {
        return styleName;
    }

    public void setStyleName(String styleName) {
        this.styleName = styleName;
    }

    public String getPriceName() {
        return priceName;
    }

    public void setPriceName(String priceName) {
        this.priceName = priceName;
    }

}
