package com.d2c.member.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.dao.MagazineMapper;
import com.d2c.member.model.Magazine;
import com.d2c.member.query.MagazineSearcher;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("magazineService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class MagazineServiceImpl extends ListServiceImpl<Magazine> implements MagazineService {

    @Autowired
    private MagazineMapper magazineMapper;

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Magazine insert(Magazine magazine) {
        return this.save(magazine);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int update(Magazine magazine) {
        return this.updateNotNull(magazine);
    }

    @Override
    public Magazine findById(Long id) {
        return this.findOneById(id);
    }

    @Override
    public PageResult<Magazine> findBySearcher(MagazineSearcher searcher, PageModel page) {
        PageResult<Magazine> pager = new PageResult<>(page);
        int totalCount = magazineMapper.countBySearcher(searcher);
        if (totalCount > 0) {
            List<Magazine> list = magazineMapper.findBySearcher(searcher, page);
            pager.setList(list);
        }
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    public int countBySearcher(MagazineSearcher searcher) {
        return magazineMapper.countBySearcher(searcher);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateStatus(Long id, Integer status, String operator) {
        return magazineMapper.updateStatus(id, status, operator);
    }

}
