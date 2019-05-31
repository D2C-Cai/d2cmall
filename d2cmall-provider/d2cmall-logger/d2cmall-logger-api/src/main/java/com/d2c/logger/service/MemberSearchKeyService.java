package com.d2c.logger.service;

import com.d2c.common.api.dto.CountDTO;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.logger.model.MemberSearchInfo;
import com.d2c.logger.query.MemberSearchInfoSearcher;

import java.util.Date;

/**
 * 会员搜索信（MemberSearchInfo）
 */
public interface MemberSearchKeyService {

    /**
     * 根据id获取相应信息
     *
     * @param id
     * @return
     */
    MemberSearchInfo findById(Long id);

    /**
     * 根据MemberSearchInfoSearcher中的过滤条件获取相应的搜索信息，按照关键字分组，
     * 采用分页方式，封装成PageResult对象返回。
     *
     * @param searcher 过滤器
     * @param pager    分页参数
     * @return
     */
    PageResult<CountDTO<String>> findGroupBySearcher(MemberSearchInfoSearcher searcher, PageModel pager);

    /**
     * 根据MemberSearchInfoSearcher中的过滤条件获取相应的搜索信息， 采用分页方式，封装成PageResult对象返回。
     *
     * @param searcher 过滤器
     * @param pager    分页参数
     * @return
     */
    PageResult<MemberSearchInfo> findBySearcher(MemberSearchInfoSearcher searcher, PageModel pager);

    /**
     * 根据MemberSearchInfoSearcher中的过滤条件获取相应的搜索信息总数。
     *
     * @param searcher 过滤器
     * @return
     */
    int countBySearcher(MemberSearchInfoSearcher searcher);

    /**
     * 将MemberSearchInfo对象插入数据库
     *
     * @param entity
     * @return
     */
    MemberSearchInfo insert(MemberSearchInfo entity);

    /**
     * 将关键字为key值，并且创建时间大于date的关键字设为已汇总
     *
     * @param key       会员搜索关键字
     * @param date      日期
     * @param statistic 是否汇总
     * @return
     */
    int updateStatistic(String key, Date date, int statistic);

    /**
     * 根据参数删除对应搜索信息
     *
     * @param key       会员搜索关键字
     * @param date      日期
     * @param statistic 是否汇总
     * @return
     */
    void remove(String key, Date date, int statistic);

    /**
     * 根据id删除对应信息
     *
     * @param id
     * @return
     */
    int delete(Long id);

}
