package com.d2c.content.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.content.model.IpBlack;
import com.d2c.content.query.IpBlackSearcher;

/**
 * IP黑名单
 *
 * @author lwz
 */
public interface IpBlackService {

    /**
     * 根据IpBlackSearcher内的过滤条件，获取相应的基础数据， 采用分页方式，以PageResult对象返回
     *
     * @param searcher 过滤器
     * @param page     分页
     * @return
     */
    PageResult<IpBlack> findBySearch(IpBlackSearcher searcher, PageModel page);

    /**
     * 插入IP黑名单
     *
     * @param ipBlack
     * @return
     * @throws Exception
     */
    IpBlack insert(IpBlack ipBlack);

    /**
     * 更新IP黑名单
     *
     * @param ipBlack
     * @return
     * @throws Exception
     */
    int update(IpBlack ipBlack);

    /**
     * 根据id删除用户信息
     *
     * @param id
     * @return
     */
    int deleteById(Long id);

    /**
     * 根据id找到用户信息
     *
     * @param id
     * @return
     */
    IpBlack findById(Long id);

    /**
     * 更改黑名单状态
     *
     * @param id,status,lastModifyMan
     * @return
     */
    int updateStatus(Long id, Integer status, String lastModifyMan);

}
