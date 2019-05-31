package com.d2c.content.search.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.content.search.model.SearcherQuizBank;
import com.d2c.content.search.query.QuizBankSearchBean;

public interface QuizBankSearcherService {

    public static final String TYPE_QUIZ = "typequiz";

    /**
     * 新增
     *
     * @param quizBank
     * @return
     */
    int insert(SearcherQuizBank quizBank);

    /**
     * 分页查找
     *
     * @param searcher
     * @return
     */
    PageResult<SearcherQuizBank> search(QuizBankSearchBean searcher, PageModel page);

    /**
     * 更新
     *
     * @param quizBank
     * @return
     */
    int updateQuizBank(SearcherQuizBank quizBank);

    /**
     * 上下架
     *
     * @param id
     * @param mark
     * @return
     */
    int updateStatus(Long id, int mark);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    int remove(String id);

    /**
     * 删除所有
     */
    int removeAll();

}
