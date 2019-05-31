package com.d2c.product.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.product.dto.BrandDto;
import com.d2c.product.model.Brand;
import com.d2c.product.query.BrandSearcher;

import java.util.List;
import java.util.Map;

public interface BrandService {

    /**
     * 通过id查询设计师
     *
     * @param brandId
     * @return
     */
    Brand findById(Long brandId);

    /**
     * 通过设计师编号
     *
     * @param designerCode
     * @return
     */
    Brand findByCode(String designerCode);

    /**
     * 通过设计师编号查找
     *
     * @param code
     * @return
     */
    Map<String, Object> findSimpleByCode(String code);

    /**
     * 根据品牌编码查询
     *
     * @param brandCode
     * @return
     */
    Brand findByBrandCode(String brandCode);

    /**
     * 通过区域名查找
     *
     * @param domain
     * @return
     */
    Brand findByDomain(String domain);

    /**
     * 查询设计师数量
     *
     * @param defineId
     * @param page
     * @return
     */
    PageResult<Brand> findByCouponDefId(Long defineId, PageModel page);

    /**
     * 查询设计师标签
     *
     * @param tagId
     * @param page
     * @return
     */
    PageResult<Brand> findByDesignerTagId(Long tagId, PageModel page);

    /**
     * 根据设计师id查询品牌
     *
     * @param id
     * @param status
     * @return
     */
    List<Brand> findByDesignersId(Long id, Integer[] status);

    /**
     * 根据设计师id查询品牌id
     *
     * @param designersId
     * @return
     */
    List<Long> findIdsByDesignersId(Long designersId);

    /**
     * 通过ID查询设计师
     *
     * @param brandId
     * @return
     */
    String findNameByIds(Long[] brandId);

    /**
     * 查询设计师列表
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<BrandDto> findBySearch(BrandSearcher searcher, PageModel page);

    /**
     * 查询设计师数量
     *
     * @param searcher
     * @return
     */
    int countBySearch(BrandSearcher searcher);

    /**
     * 按品牌首字母分类
     *
     * @return
     */
    List<Brand> findByLetters();

    /**
     * 添加关注的设计师
     *
     * @param brand
     * @return
     */
    Brand insert(Brand brand);

    /**
     * 修改关注的设计师
     *
     * @param brand
     * @return
     */
    int update(Brand brand);

    /**
     * 取消关注的设计师
     *
     * @param brandId
     * @param operator
     * @return
     */
    int delete(Long brandId, String operator);

    /**
     * 修改备注
     *
     * @param id
     * @param mark
     * @param marketMan
     * @return
     */
    int updateMark(Long id, int mark, String marketMan);

    /**
     * 修改门店试衣状态
     *
     * @param id
     * @param subscribe
     * @param operator
     * @return
     */
    int updateSubscribe(Long id, int subscribe, String operator);

    /**
     * 是否售后
     *
     * @param id
     * @param status
     * @param operator
     * @return
     */
    int updateAfter(Long id, Integer status, String operator);

    /**
     * 是否货到付款
     *
     * @param id
     * @param status
     * @param operator
     * @return
     */
    int updateCod(Long id, Integer status, String operator);

    /**
     * 是否主推
     *
     * @param id
     * @param recommend
     * @param operator
     * @return
     */
    int updateRecommend(Long id, Integer recommend, String operator);

    /**
     * 更新粉丝数量
     *
     * @param id
     * @param count
     * @return
     */
    int updateFansCount(Long id, Integer count);

    /**
     * 更新标签
     *
     * @param designerId
     * @param ids
     * @return
     */
    int updateTags(Long designerId, String ids);

    /**
     * 更新设计师相关信息
     *
     * @param brandId
     * @param code
     * @param name
     * @return
     */
    int updateDesigners(Long brandId, String code, String name);

    /**
     * 更新设计师视频信息
     *
     * @param id
     * @param video
     * @return
     */
    int updateVideoById(Long id, String video);

    /**
     * 更新品牌形象大图
     *
     * @param id
     * @param bigPic
     * @return
     */
    int updateBigPic(Long id, String bigPic);

    /**
     * 是否设计师直发
     *
     * @param id
     * @param direct
     * @return
     */
    int updateDirect(Long id, Integer direct, String operator);

    /**
     * 更新品牌30天销量
     *
     * @param brandId
     * @param sales
     * @return
     */
    int updateSales(Long brandId, Integer sales);

    /**
     * 查询所有品牌有销量的
     *
     * @return
     */
    List<Long> findAllSales();

    /**
     * 更新品牌的风格和价格
     *
     * @param id
     * @return
     */
    int updateStyleAndPrice(Long brandId, String style, String price);

}
