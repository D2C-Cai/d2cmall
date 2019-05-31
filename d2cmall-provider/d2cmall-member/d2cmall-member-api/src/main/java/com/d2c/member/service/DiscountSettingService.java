package com.d2c.member.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.dto.DiscountSettingDto;
import com.d2c.member.model.DiscountSetting;
import com.d2c.member.query.DiscountSettingSearcher;

import java.math.BigDecimal;
import java.util.List;

public interface DiscountSettingService {

    /**
     * 根据id查找
     *
     * @param id
     * @return
     */
    DiscountSetting findById(Long id);

    /**
     * 分页查询
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<DiscountSettingDto> findBySearch(DiscountSettingSearcher searcher, PageModel page);

    /**
     * 根据searcher查询
     *
     * @param searcher
     * @return
     */
    int countBySearch(DiscountSettingSearcher searcher);

    /**
     * 根据折扣组id或分销商id查询
     *
     * @param distributorId
     * @param disType
     * @param targetIds
     * @return
     */
    List<DiscountSetting> findDiscountSettings(Long distributorId, String disType, List<Long> targetIds);

    /**
     * 插入分销商折扣设置
     *
     * @param discountSetting 分销商折扣
     * @param username        用户名
     * @return
     */
    DiscountSetting insert(DiscountSetting discountSetting, String username);

    /**
     * 批量插入分销商折扣设置
     *
     * @param discountList 分销商折扣设置列表
     * @param username     用户名
     * @return
     */
    int insertBatch(List<DiscountSetting> discountList, String username);

    /**
     * 根据id更新折扣
     *
     * @param discountSetting
     * @param discount
     * @param username
     * @return
     */
    int updateDiscountById(DiscountSetting discountSetting, BigDecimal discount, String username);

    /**
     * 根据id更新状态
     *
     * @param discountSetting
     * @param status
     * @param username
     * @return
     */
    int updateStatusById(DiscountSetting discountSetting, int status, String username);

    /**
     * 根据groupId和productId查询
     *
     * @param groupId
     * @param productId
     * @return
     */
    DiscountSetting findByGroupIdAndProductId(Long groupId, Long productId);

    /**
     * 根据目标ID更新状态
     *
     * @param discountSetting
     * @param status
     * @param username
     * @return
     */
    int updateStatusByTargetId(DiscountSetting discountSetting, int status, String username);

    /**
     * 更新
     *
     * @param discountSetting
     * @param username
     * @return
     */
    int update(DiscountSetting discountSetting, String username);

    DiscountSetting findByTargetId(String disType, Long targetId, Long groupId, Long distributorId);

}
