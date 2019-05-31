package com.d2c.content.search.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.content.search.model.SearcherTheme;
import com.d2c.content.search.query.ThemeSearcherBean;

import java.util.Map;

public interface ThemeSearcherService {

    public static final String TYPE_THEME = "typetheme";

    /**
     * 新增专题
     *
     * @param searcherTheme
     * @return
     */
    int insert(SearcherTheme searcherTheme);

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    SearcherTheme findById(Long id);

    /**
     * 查询
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<SearcherTheme> search(ThemeSearcherBean searcher, PageModel page);

    /**
     * 查询各标签下的数量
     *
     * @return
     */
    Map<Long, Long> countGroupByTag();

    /**
     * 重建专题
     *
     * @param searcherTheme
     * @return
     */
    int rebuild(SearcherTheme searcherTheme);

    /**
     * 删除下架
     *
     * @param id
     * @return
     */
    int remove(Long id);

    /**
     * 清除索引
     */
    void removeAll();

    /**
     * 推荐
     *
     * @param id
     * @param recommend
     * @return
     */
    int updateRecommend(Long id, Integer recommend);

    /**
     * 修改状态
     *
     * @param id
     * @param status
     * @return
     */
    int updateStatus(Long id, Integer status);

    /**
     * 排序
     *
     * @param id
     * @param sort
     * @return
     */
    int updateSort(Long id, Integer sort);

    /**
     * 是否固定
     *
     * @param id
     * @param fix
     * @return
     */
    int updateFixByTagId(Long id, Integer fix);

}
