package com.d2c.member.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.dto.DiscountSettingGroupDto;
import com.d2c.member.model.DiscountSettingGroup;
import com.d2c.member.query.DiscountSettingGroupSearcher;

import java.util.Date;

public interface DiscountSettingGroupService {

    /**
     * 根据id查找
     *
     * @param id
     * @return
     */
    DiscountSettingGroup findById(Long id);

    /**
     * 分页查询
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<DiscountSettingGroupDto> findBySearch(DiscountSettingGroupSearcher searcher, PageModel page);

    /**
     * 插入折扣组
     *
     * @param discountSettingGroup
     * @param username
     * @return
     */
    DiscountSettingGroup insert(DiscountSettingGroup discountSettingGroup, String username);

    /**
     * 更新折扣时间
     *
     * @param id
     * @param beginDate
     * @param endDate
     * @return
     */
    int updateDate(Long id, Date beginDate, Date endDate);

    /**
     * 根据id更新状态
     *
     * @param id
     * @param status
     * @return
     */
    int updateStatusById(Long id, int status, String username);

    /**
     * 根据id更新折扣组名称
     *
     * @param id
     * @param name
     * @return
     */
    int updateNameById(Long id, String name);

    /**
     * 完全更新一个折扣设置组
     *
     * @param group
     * @return
     */
    int updateDiscountSettingGroup(DiscountSettingGroup group);

}
