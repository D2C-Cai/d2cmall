package com.d2c.similar.mongo.model;

import com.d2c.common.mongodb.model.SuperMongoDO;
import com.d2c.similar.dto.ProductRecomDTO;
import com.d2c.similar.dto.recom.RecomDimenDTO;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
public class RecomDO extends SuperMongoDO {

    private static final long serialVersionUID = -4903284310787030896L;
    @Id
    private Long productId;
    private Object product;
    private ProductRecomDTO data;
    private List<RecomDimenDTO> dimens = new ArrayList<>();
    @Indexed
    private double score;

    public RecomDO() {
    }

    public RecomDO(Long id, Object product) {
        this.productId = id;
        this.product = product;
        this.data = new ProductRecomDTO(id);
    }

    public void addRecomDimen(RecomDimenDTO dimen) {
        dimens.add(dimen);
    }
    // **************************************

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
