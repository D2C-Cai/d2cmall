package com.d2c.product.search.view;

import com.d2c.common.api.view.BaseVO;

import java.util.List;

public class CartgoryVO extends BaseVO {

    private static final long serialVersionUID = 3347104012468099610L;
    private String clazz;
    /**
     * 二级分类ID
     */
    private Long categoryId;
    /**
     * 分类名称
     */
    private String categoryName;
    /**
     * 商品对象列表
     */
    private List<ProductVO> products;

    public CartgoryVO() {
        this.clazz = this.getClass().getName();
    }

    public CartgoryVO(List<ProductVO> products) {
        this();
        this.products = products;
        if (products != null && !products.isEmpty()) {
            ProductVO vo = products.get(0);
            this.categoryId = vo.getCategoryId();
            this.categoryName = vo.getCategoryName();
        }
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<ProductVO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductVO> products) {
        this.products = products;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

}
