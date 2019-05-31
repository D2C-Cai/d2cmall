package com.d2c.similar.service;

import com.d2c.common.api.query.model.MongoQuery;
import com.d2c.similar.dto.RecomDTO;

import java.util.List;
import java.util.Map;

/**
 * 商品推荐表
 *
 * @author wull
 */
public interface RecomService {

    /**
     * 每个品类商品推荐最高的商品
     */
    public Map<Long, Object> findTopOneCategory(Integer limit);

    /**
     * 查询  TOP {limit} 推荐值商品
     */
    public List<RecomDTO> findTopRecom(MongoQuery query, int limit);

    /**
     * 查询商品推荐明细, 如果为空尝试重建
     */
    public RecomDTO findBuildRecomByProductId(Long productId);

    public RecomDTO findRecomByProductId(Long productId);

    /**
     * 根据商品Id，计算商品推荐值并保存
     */
    public RecomDTO buildRecomByProductId(Long productId, Boolean operRecom);

    /**
     * 定时任务: 批量计算商品推荐值
     */
    public void buildAllRecomInJob();

    /**
     * 清除运营操作并重建数据
     */
    public void cleanOperAndRecomAll();

}
