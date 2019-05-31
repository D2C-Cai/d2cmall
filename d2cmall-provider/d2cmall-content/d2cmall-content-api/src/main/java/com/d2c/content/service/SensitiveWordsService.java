package com.d2c.content.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.content.model.SensitiveWords;
import com.d2c.content.query.SensitiveWordsSearcher;

/**
 * 敏感词
 *
 * @author lwz
 */
public interface SensitiveWordsService {

    /**
     * 根据SensitiveWordsSearcher内的过滤条件，获取相应的基础数据， 采用分页方式，以PageResult对象返回
     *
     * @param searcher 过滤器
     * @param page     分页
     * @return
     */
    PageResult<SensitiveWords> findBySearch(SensitiveWordsSearcher searcher, PageModel page);

    /**
     * 插入敏感词
     *
     * @param sensitiveWords
     * @return
     * @throws Exception
     */
    SensitiveWords insert(SensitiveWords sensitiveWords);

    /**
     * 更新敏感词
     *
     * @param sensitiveWords
     * @return
     * @throws Exception
     */
    int update(SensitiveWords sensitiveWords);

    /**
     * 根据id删除敏感词
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
    SensitiveWords findById(Long id);

    /**
     * 更改黑名单状态
     *
     * @param id,status,lastModifyMan
     * @return
     */
    int updateStatus(Long id, Integer status, String lastModifyMan);

    /**
     * 查询是否包含敏感字
     *
     * @param sensitiveWords
     * @return
     */
    boolean findBySensitiveWords(String sensitiveWords);

    /**
     * 根据敏感字查询
     *
     * @param keyword
     * @return
     */
    SensitiveWords findByKeyword(String keyword);

}
