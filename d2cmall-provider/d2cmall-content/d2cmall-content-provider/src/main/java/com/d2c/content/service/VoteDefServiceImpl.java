package com.d2c.content.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.content.dao.VoteDefMapper;
import com.d2c.content.dto.VoteDefDto;
import com.d2c.content.dto.VoteSelectionDto;
import com.d2c.content.model.VoteDef;
import com.d2c.content.query.VoteDefSearcher;
import com.d2c.content.query.VoteItemSearcher;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("voteDefService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class VoteDefServiceImpl extends ListServiceImpl<VoteDef> implements VoteDefService {

    @Autowired
    private VoteDefMapper voteDefMapper;
    @Autowired
    private VoteItemService voteItemService;
    @Autowired
    private VoteSelectionService voteSelectionService;

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public VoteDef insert(VoteDef voteDef) {
        voteDef = this.save(voteDef);
        return voteDef;
    }

    @Override
    public PageResult<VoteDef> findBySearch(VoteDefSearcher searcher, PageModel page) {
        PageResult<VoteDef> pager = new PageResult<VoteDef>(page);
        Integer toltalCount = voteDefMapper.countBySearcher(searcher);
        List<VoteDef> list = new ArrayList<VoteDef>();
        if (toltalCount > 0) {
            list = voteDefMapper.findBySearcher(searcher, page);
        }
        pager.setTotalCount(toltalCount);
        pager.setList(list);
        return pager;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateStatus(Long id, Integer status) {
        return voteDefMapper.updateStatus(id, status);
    }

    @Override
    public VoteDefDto findDtoById(Long id) {
        VoteDef voteDef = this.findOneById(id);
        if (voteDef == null) {
            return null;
        }
        VoteDefDto dto = new VoteDefDto();
        BeanUtils.copyProperties(voteDef, dto);
        VoteItemSearcher searcher = new VoteItemSearcher();
        searcher.setDefId(id);
        searcher.setSort("vCount");
        PageModel page = new PageModel();
        page.setPageSize(PageModel.MAX_PAGE_SIZE);
        searcher.setSort("create_date");
        PageResult<VoteSelectionDto> voteCounts = voteItemService.findByDefIdGroupBySelection(searcher, page);
        dto.setVoteSelectionList(voteCounts.getList());
        return dto;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int update(VoteDef voteDef) {
        int success = this.updateNotNull(voteDef);
        if (success > 0) {
            voteSelectionService.updateByDefId(voteDef.getId(), voteDef.getTitle());
        }
        return success;
    }

    @Override
    public VoteDef findById(Long id) {
        return this.findOneById(id);
    }

}
