package com.d2c.similar.service;

import com.d2c.common.api.service.ListService;
import com.d2c.similar.dto.SimilarSchemeDTO;
import com.d2c.similar.entity.SimilarSchemeDO;

import java.util.List;

/**
 * 相似度方案
 *
 * @author wull
 */
public interface SimilarSchemeService extends ListService<SimilarSchemeDO> {

    /**
     * 获取方案内容明细
     */
    public SimilarSchemeDTO getSchemeDetail(Integer schemeId);

    public SimilarSchemeDO findSchemeByCategoryId(Object categoryId);

    public SimilarSchemeDO createScheme(SimilarSchemeDO scheme);

    public int updateSchemeHasExce(Integer id, Boolean hasExce);

    public void clean();

    public List<SimilarSchemeDO> findAll();

}
