package com.d2c.content.dto;

import com.d2c.content.model.Article;

public class ArticleDto extends Article {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String brandName;

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

}
