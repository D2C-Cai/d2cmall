package com.d2c.content.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.content.dao.VoteSelectionMapper;
import com.d2c.content.model.VoteSelection;
import com.d2c.content.query.VoteSelectionSearcher;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("voteSelectionService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class VoteSelectionServiceImpl extends ListServiceImpl<VoteSelection> implements VoteSelectionService {

    @Autowired
    private VoteSelectionMapper voteSelectionMapper;
    @Autowired
    private VoteItemService voteItemService;

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public VoteSelection insert(VoteSelection voteSelection) {
        return this.save(voteSelection);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int update(VoteSelection voteSelection) {
        int success = voteSelectionMapper.update(voteSelection);
        if (success > 0) {
            voteItemService.updateBySelection(voteSelection.getId(), voteSelection.getDefTitle(),
                    voteSelection.getName(), voteSelection.getPic(), voteSelection.getCoef());
        }
        return success;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int delete(Long id) {
        int success = voteSelectionMapper.delete(id);
        if (success > 0) {
            voteItemService.updateStatusBySelectionId(id, -1);
        }
        return success;
    }

    @Override
    public List<VoteSelection> findByDefId(Long defId) {
        return voteSelectionMapper.findByDefId(defId);
    }

    @Override
    public List<VoteSelection> findByIds(Long[] ids) {
        return voteSelectionMapper.findByIds(ids);
    }

    @Override
    public VoteSelection findById(Long id) {
        return this.findOneById(id);
    }

    @Override
    public PageResult<VoteSelection> findBySearcher(VoteSelectionSearcher searcher, PageModel page) {
        PageResult<VoteSelection> pager = new PageResult<VoteSelection>(page);
        int totalCount = voteSelectionMapper.countBySearcher(searcher);
        List<VoteSelection> list = new ArrayList<VoteSelection>();
        if (totalCount > 0) {
            list = voteSelectionMapper.findBySearcher(searcher, page);
        }
        pager.setList(list);
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int updateByDefId(Long id, String title) {
        return voteSelectionMapper.updateByDefId(id, title);
    }

}
