package com.d2c.content.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.content.dao.VoteItemMapper;
import com.d2c.content.dto.VoteSelectionDto;
import com.d2c.content.model.VoteItem;
import com.d2c.content.query.VoteItemSearcher;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("voteItemService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class VoteItemServiceImpl extends ListServiceImpl<VoteItem> implements VoteItemService {

    @Autowired
    private VoteItemMapper voteItemMapper;

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public VoteItem insert(VoteItem voteItem) {
        voteItem = this.save(voteItem);
        return voteItem;
    }

    @Override
    public PageResult<VoteSelectionDto> findByDefIdGroupBySelection(VoteItemSearcher searcher, PageModel page) {
        PageResult<VoteSelectionDto> pager = new PageResult<VoteSelectionDto>(page);
        Integer toltalCount = voteItemMapper.countByDefIdGroupBySelection(searcher);
        List<VoteSelectionDto> list = new ArrayList<VoteSelectionDto>();
        if (toltalCount > 0) {
            list = voteItemMapper.findByDefIdGroupBySelection(searcher, page);
        }
        pager.setTotalCount(toltalCount);
        pager.setList(list);
        return pager;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateBySelection(Long selectionId, String defTitle, String selectName, String pic, Double coef) {
        return voteItemMapper.updateByDef(selectionId, defTitle, selectName, pic, coef);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateStatusBySelectionId(Long selectionId, Integer status) {
        return voteItemMapper.updateStatusByDef(selectionId, status);
    }

    @Override
    public List<VoteItem> findByDefIdAndMemberId(Long defId, Long memberId) {
        return voteItemMapper.findByDefIdAndMemberId(defId, memberId);
    }

    @Override
    public int countBySearcher(VoteItemSearcher searcher) {
        return voteItemMapper.countBySearcher(searcher);
    }

}
