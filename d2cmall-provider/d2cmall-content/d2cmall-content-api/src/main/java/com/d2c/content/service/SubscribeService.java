package com.d2c.content.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.content.model.Subscribe;
import com.d2c.content.query.SubscribeSearcher;

/**
 * 订阅（subscribe）
 */
public interface SubscribeService {

    /**
     * 根据id获取订阅信息。
     *
     * @param id
     * @return Subscribe
     */
    Subscribe findById(Long id);

    /**
     * 根据SubscribeQuery对象中的过滤条件获取订阅信息， 采用分页方式，封装成PageResult对象返回。
     *
     * @param searcher 过滤器
     * @param page     分页
     * @return PageResult<E>
     */
    PageResult<Subscribe> findBySearcher(SubscribeSearcher searcher, PageModel page);

    /**
     * 根据SubscribeQuery对象中的过滤条件获取订阅总数。
     *
     * @param searcher 过滤器
     * @return int
     */
    int countBySearcher(SubscribeSearcher searcher);

    /**
     * 根据手机号码获取订阅信息
     *
     * @param mobile 手机号码
     * @return Subscribe
     */
    Subscribe findByMobile(String mobile);

    /**
     * 根据Email获取订阅信息
     *
     * @param email 邮件地址
     * @return Subscribe
     */
    Subscribe findByEmail(String email);

    /**
     * 将Subscribe对象插入subscribe表中。
     *
     * @param subscribe
     * @return int
     */
    Subscribe insert(Subscribe subscribe);

    /**
     * 将Subscribe对象更新subscribe表中。
     *
     * @param entity
     * @return
     */
    int update(Subscribe entity);

}
