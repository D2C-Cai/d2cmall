package com.d2c.product.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.product.model.Product;
import com.d2c.product.model.ProductRelation;
import com.d2c.product.model.ProductRelation.RelationType;
import com.d2c.product.query.RelationSearcher;

import java.util.List;

/**
 * 产品相关信息（relation）
 */
public interface ProductRelationService {

    /**
     * 根据关系类型、组对象、产品id更新排序序号
     *
     * @param type      关系类型
     * @param sourceId  组对象
     * @param productId 产品id
     * @param sort      排序序号
     * @return
     */
    int updateSort(RelationType type, Long sourceId, Long productId, Integer sort);

    /**
     * 查询一个关系
     *
     * @param id
     * @return
     */
    ProductRelation findOne(Long id);

    /**
     * 根据参数删除对应关系
     *
     * @param type     关系类型
     * @param rid      关系id
     * @param sourceId 组对象
     * @return
     */
    int deleteOne(String type, Long rid, Long sourceId);

    /**
     * 保存产品关系
     *
     * @param productRelation
     */
    void insert(ProductRelation productRelation);

    /**
     * 保存产品关系
     *
     * @param relations
     * @return
     */
    int insert(List<ProductRelation> relations, Long sourceId);

    /**
     * 更新产品关系
     *
     * @param productRelation
     */
    void update(ProductRelation productRelation);
    // /**
    // * 根据关系类型，关系id删除关联关系
    // *
    // * @param type
    // * @param rid
    // *
    // * @return
    // */
    // int deleteByTypeAndRelationId(String type, Long rid);

    /**
     * 根据关系类型，源id删除关联关系
     *
     * @param type
     * @param sourceId
     * @return
     */
    int deleteByTypeAndSourceId(String type, Long sourceId);

    /**
     * 根据RelationSearcher内的过滤条件，获取产品关联关系 采用分页方式，以PageResult对方返回
     *
     * @param search 过滤器
     * @param page   分页
     * @return
     */
    PageResult<ProductRelation> findRelations(RelationSearcher search, PageModel page);

    /**
     * 获取搭配商品
     *
     * @param sourceId
     * @return
     */
    List<Product> findRelationProducts(Long sourceId, Integer mark);

    /**
     * 删除商品对应的搭配缓存
     *
     * @param sourceId
     */
    void doClearRelationBySourceId(Long sourceId);

    /**
     * 根据relationId查找sourceID
     *
     * @param id
     * @param type
     * @return
     */
    List<Long> findSourceIdsByRelationId(Long id, String type);

    /**
     * 根据sourceId查询relation_id
     *
     * @param sourceId
     * @return
     */
    List<Long> findBySourceId(Long sourceId, String type);

}
