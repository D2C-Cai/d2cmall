package com.d2c.logger.service;

import com.d2c.common.api.dto.CountDTO;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.logger.dao.MemberSearchInfoMapper;
import com.d2c.logger.model.MemberSearchInfo;
import com.d2c.logger.query.MemberSearchInfoSearcher;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("memberSearchKeyService")
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class MemberSearchKeyServiceImpl extends ListServiceImpl<MemberSearchInfo> implements MemberSearchKeyService {

    @Autowired
    private MemberSearchInfoMapper memberSearchInfoMapper;

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int updateStatistic(String key, Date date, int statistic) {
        return memberSearchInfoMapper.updateStatistic(key, date, statistic);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void remove(String key, Date date, int statistic) {
        memberSearchInfoMapper.remove(key, date, statistic);
    }

    public PageResult<CountDTO<String>> findGroupBySearcher(MemberSearchInfoSearcher searcher, PageModel page) {
        int totalCount = memberSearchInfoMapper.countGroupBySearcher(searcher);
        PageResult<CountDTO<String>> pager = new PageResult<CountDTO<String>>(page);
        List<CountDTO<String>> list = new ArrayList<CountDTO<String>>();
        if (totalCount > 0) {
            list = memberSearchInfoMapper.findGroupBySearcher(searcher, page);
        }
        pager.setTotalCount(totalCount);
        pager.setList(list);
        return pager;
    }

    public PageResult<MemberSearchInfo> findBySearcher(MemberSearchInfoSearcher searcher, PageModel page) {
        int totalCount = memberSearchInfoMapper.countBySearcher(searcher);
        PageResult<MemberSearchInfo> pager = new PageResult<MemberSearchInfo>(page);
        List<MemberSearchInfo> list = new ArrayList<MemberSearchInfo>();
        if (totalCount > 0) {
            list = memberSearchInfoMapper.findBySearcher(searcher, page);
        }
        pager.setTotalCount(totalCount);
        pager.setList(list);
        return pager;
    }

    @Override
    public int countBySearcher(MemberSearchInfoSearcher searcher) {
        return memberSearchInfoMapper.countBySearcher(searcher);
    }

    @Override
    public MemberSearchInfo insert(MemberSearchInfo entity) {
        return this.save(entity);
    }

    @Override
    public MemberSearchInfo findById(Long key) {
        return this.findOneById(key);
    }

    @Override
    public int delete(Long id) {
        return this.deleteById(id);
    }

}
