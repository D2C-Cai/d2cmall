package com.d2c.similar.dto;

import com.d2c.common.api.dto.BaseDTO;
import com.d2c.similar.dto.recom.RecomDimenDTO;

import java.util.ArrayList;
import java.util.List;

public class RecomDTO extends BaseDTO {

    private static final long serialVersionUID = -4903284310787030896L;
    private Long productId;
    private Object product;
    private ProductRecomDTO data;
    private List<RecomDimenDTO> dimens = new ArrayList<>();
    private double score;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Object getProduct() {
        return product;
    }

    public void setProduct(Object product) {
        this.product = product;
    }

    public ProductRecomDTO getData() {
        return data;
    }

    public void setData(ProductRecomDTO data) {
        this.data = data;
    }

    public List<RecomDimenDTO> getDimens() {
        return dimens;
    }

    public void setDimens(List<RecomDimenDTO> dimens) {
        this.dimens = dimens;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

}
