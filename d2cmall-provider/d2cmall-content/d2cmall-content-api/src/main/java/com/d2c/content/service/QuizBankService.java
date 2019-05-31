package com.d2c.content.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.content.model.QuizBank;
import com.d2c.content.query.QuizBankSearcher;

public interface QuizBankService {

    /**
     * 插入一条记录
     *
     * @param quiz
     * @return
     */
    QuizBank insert(QuizBank quiz);

    /**
     * 更新一条记录
     *
     * @param quiz
     * @return
     */
    int update(QuizBank quiz);

    /**
     * 物理删除一条记录
     *
     * @param quiz
     * @return
     */
    int deleteById(Long id);

    /**
     * 分页查询
     *
     * @param quiz
     * @return
     */
    PageResult<QuizBank> findBySearcher(QuizBankSearcher searcher, PageModel page);

    /**
     * 上下架
     *
     * @param quiz
     * @return
     */
    int updateStatus(Long id, Integer status, String operator);

    /**
     * 按条件统计总数
     *
     * @param quiz
     * @return
     */
    int countBySearcher(QuizBankSearcher searcher);

}
