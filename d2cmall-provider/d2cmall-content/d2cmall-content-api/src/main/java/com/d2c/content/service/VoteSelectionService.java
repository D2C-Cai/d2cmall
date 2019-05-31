package com.d2c.content.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.content.model.VoteSelection;
import com.d2c.content.query.VoteSelectionSearcher;

import java.util.List;

public interface VoteSelectionService {

    /**
     * 新增投票选项
     *
     * @param voteSelection
     * @return
     */
    VoteSelection insert(VoteSelection voteSelection);

    /**
     * 修改投票选项
     *
     * @param voteSelection
     * @return
     */
    int update(VoteSelection voteSelection);

    /**
     * 删除选项
     *
     * @param id
     * @return
     */
    int delete(Long id);

    /**
     * 通过定义ID查询选项
     *
     * @param defId
     * @return
     */
    List<VoteSelection> findByDefId(Long defId);

    /**
     * 通过IDS查询选项
     *
     * @param ids
     * @return
     */
    List<VoteSelection> findByIds(Long[] ids);

    /**
     * 通过ID查询选项
     *
     * @param id
     * @return
     */
    VoteSelection findById(Long id);

    /**
     * 查询列表
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<VoteSelection> findBySearcher(VoteSelectionSearcher searcher, PageModel page);

    /**
     * 修改标题
     *
     * @param id
     * @param title
     * @return
     */
    int updateByDefId(Long id, String title);

}
