package com.d2c.member.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.dao.MagazinePageMapper;
import com.d2c.member.model.MagazinePage;
import com.d2c.member.query.MagazinePageSearcher;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("magazinePageService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class MagazinePageServiceImpl extends ListServiceImpl<MagazinePage> implements MagazinePageService {

    @Autowired
    private MagazinePageMapper magazinePageMapper;

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public MagazinePage insert(MagazinePage magazinePage) {
        return this.save(magazinePage);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int update(MagazinePage magazinePage) {
        return this.updateNotNull(magazinePage);
    }

    @Override
    public MagazinePage findById(Long id) {
        return this.findOneById(id);
    }

    @Override
    public MagazinePage findByCode(String code) {
        return magazinePageMapper.findByCode(code);
    }

    @Override
    public PageResult<MagazinePage> findBySearcher(MagazinePageSearcher searcher, PageModel page) {
        PageResult<MagazinePage> pager = new PageResult<>(page);
        int totalCount = magazinePageMapper.countBySearcher(searcher);
        if (totalCount > 0) {
            List<MagazinePage> list = magazinePageMapper.findBySearcher(searcher, page);
            pager.setList(list);
        }
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    public int countBySearcher(MagazinePageSearcher searcher) {
        return magazinePageMapper.countBySearcher(searcher);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateStatus(Long id, Integer status, String operator) {
        return magazinePageMapper.updateStatus(id, status, operator);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateMagazineId(Long id, Long magazineId) {
        return magazinePageMapper.updateMagazineId(id, magazineId);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateSort(Long id, Integer sort) {
        return magazinePageMapper.updateSort(id, sort);
    }

}
