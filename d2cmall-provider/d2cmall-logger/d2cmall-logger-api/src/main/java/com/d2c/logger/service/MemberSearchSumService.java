package com.d2c.logger.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.logger.model.MemberSearchSum;
import com.d2c.logger.query.MemberSearchSumSearcher;

/**
 * 会员搜索信息汇总（MemberSearchSum）
 */
public interface MemberSearchSumService {

    /**
     * 根据id更新排序
     *
     * @param id
     * @param sort 排序号
     * @return
     */
    int updateSort(Long id, int sort);

    /**
     * 根据关键字更新搜索次数。
     *
     * @param keyword 关键字
     * @param count   搜索次数
     * @return
     */
    int updateNumberByKeyword(String keyword, int count);

    /**
     * 根据id获取搜索信息。
     *
     * @param id
     * @return MemberSearchSum
     */
    MemberSearchSum findById(Long id);

    /**
     * 根据关键字获取搜索信息。
     *
     * @param keyword
     * @return
     */
    MemberSearchSum findByKey(String keyword);

    /**
     * 根据MemberSearchSumSearcher内的过滤条件，获取相应搜索信息，采用分页，并以列表形式返回。
     *
     * @param searcher 过滤器
     * @param page     分页参数
     * @return
     */
    PageResult<MemberSearchSum> findBySearcher(MemberSearchSumSearcher searcher, PageModel page);

    /**
     * 根据MemberSearchSumSearcher内的过滤条件，获取相应搜索信息总数。
     *
     * @param searcher 过滤器
     * @return
     */
    int countBySearcher(MemberSearchSumSearcher searcher);

    /**
     * 根据id将搜索记录更改为系统创建
     *
     * @param id
     * @return
     */
    int doSystem(Long id);

    /**
     * 保存搜索信息
     *
     * @param entity
     * @return
     */
    MemberSearchSum insert(MemberSearchSum entity);

    /**
     * 更新搜索信息
     *
     * @param newMS
     * @return
     */
    int update(MemberSearchSum newMS);

    /**
     * 根据id删除搜索信息
     *
     * @param id
     * @return
     */
    int delete(Long id);

    /**
     * 保存搜索信息
     *
     * @param entity
     * @return
     */
    MemberSearchSum save(MemberSearchSum entity);

    /**
     * 设置是否为热搜
     *
     * @param status 状态 0否，1是
     * @param id
     * @return
     */
    int updateStatus(Long id, int status);

    /**
     * 根据ID添加次数
     *
     * @param id
     * @return
     */
    int addCountById(Long id);

}
