package com.d2c.content.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.content.dto.VoteSelectionDto;
import com.d2c.content.model.VoteItem;
import com.d2c.content.query.VoteItemSearcher;

import java.util.List;

public interface VoteItemService {

    /**
     * 新增投票
     *
     * @param voteItem
     * @return
     */
    VoteItem insert(VoteItem voteItem);

    /**
     * 查询各选项的投票人数
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<VoteSelectionDto> findByDefIdGroupBySelection(VoteItemSearcher searcher, PageModel page);

    /**
     * 通过选项修改投票
     *
     * @param selectionId
     * @param defTitle
     * @param selectName
     * @param pic
     * @param coef
     * @return
     */
    int updateBySelection(Long selectionId, String defTitle, String selectName, String pic, Double coef);

    /**
     * 通过选项状态修改投票状态
     *
     * @param selection
     * @param status
     * @return
     */
    int updateStatusBySelectionId(Long selection, Integer status);

    /**
     * 查询该会员的投票
     *
     * @param defId
     * @param memberId
     * @return
     */
    List<VoteItem> findByDefIdAndMemberId(Long defId, Long memberId);

    /**
     * 查询票数
     *
     * @param searcher
     * @return
     */
    int countBySearcher(VoteItemSearcher searcher);

}
