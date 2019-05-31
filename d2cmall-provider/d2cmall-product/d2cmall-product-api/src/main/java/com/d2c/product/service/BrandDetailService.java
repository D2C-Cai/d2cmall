package com.d2c.product.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.product.dto.BrandDetailDto;
import com.d2c.product.model.BrandDetail;
import com.d2c.product.query.BrandDetailSearcher;

public interface BrandDetailService {

    /**
     * 通过ID查询
     *
     * @param id
     * @return
     */
    BrandDetailDto findById(Long id);

    /**
     * 通过品牌查询
     *
     * @param id
     * @return
     */
    BrandDetail findByBrandId(Long brandId);

    /**
     * 查询
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<BrandDetailDto> findBySearcher(BrandDetailSearcher searcher, PageModel page);

    /**
     * 查询数量
     *
     * @param searcher
     * @return
     */
    Integer countBySearcher(BrandDetailSearcher searcher);

    /**
     * 新增
     *
     * @param brandDetail
     * @return
     */
    BrandDetail insert(BrandDetail brandDetail);

    /**
     * 修改
     *
     * @param brandDetail
     * @return
     */
    int update(BrandDetailDto brandDetail);

    /**
     * 终止合同
     *
     * @param id
     * @return
     */
    int updateContractStatus(Long id, Integer status);

    /**
     * 更新分销返利
     *
     * @param id
     * @param partnerRatio
     * @return
     */
    int updateRatioById(Long id, String partnerRatio);

}
