package com.d2c.product.dto;

import com.d2c.product.model.ProductCategory;
import com.d2c.product.model.TopCategory;

import java.util.ArrayList;
import java.util.List;

public class ProductCategoryDto extends ProductCategory {

    private static final long serialVersionUID = 1L;
    /**
     * 子集
     */
    private List<ProductCategoryDto> subsets;
    /**
     * 父级
     */
    private ProductCategory parent;
    /**
     * 顶级
     */
    private TopCategory topCategory;

    public List<ProductCategoryDto> getSubsets() {
        if (this.subsets == null)
            subsets = new ArrayList<ProductCategoryDto>();
        return subsets;
    }

    public void setSubsets(List<ProductCategoryDto> subsets) {
        this.subsets = subsets;
    }

    public ProductCategory getParent() {
        return parent;
    }

    public void setParent(ProductCategory productCategory) {
        this.parent = productCategory;
    }

    public TopCategory getTopCategory() {
        return topCategory;
    }

    public void setTopCategory(TopCategory topCategory) {
        this.topCategory = topCategory;
    }

}
