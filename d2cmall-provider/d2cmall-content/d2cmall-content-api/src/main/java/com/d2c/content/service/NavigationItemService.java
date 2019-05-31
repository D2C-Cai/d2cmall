package com.d2c.content.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.content.dto.NavigationItemDto;
import com.d2c.content.model.NavigationItem;
import com.d2c.content.query.NavigationItemSearcher;

/**
 * 提供导航组的相关数据库操作
 */
public interface NavigationItemService {

    /**
     * 以NavigationItemSearcher对象中包含的过滤条件从navigation_item表中获取所有符合条件的导航组信息，
     * 采用分页形式，以PageResult对象返回。
     *
     * @param searcher 过滤器
     * @param page     分页
     * @return PageResult<E>
     */
    PageResult<NavigationItemDto> findBySearch(NavigationItemSearcher searcher, PageModel page);

    /**
     * 以NavigationItem对象作为参数，将导航组信息插入navigation_item表中。
     *
     * @param item
     * @return NavigationItem
     */
    NavigationItem insert(NavigationItem item);

    /**
     * 以NavigationItem对象作为参数，修改相应导航组信息。
     *
     * @param item
     * @return int
     */
    int update(NavigationItem item);

    /**
     * 根据id删除导航组信息。
     *
     * @param id
     * @return int
     */
    int delete(Long id);

    NavigationItem findById(Long id);

    int doUp(Long id);

    int doDown(Long id);

}
