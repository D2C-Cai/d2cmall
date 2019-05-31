package com.d2c.member.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.dao.WardrobeMapper;
import com.d2c.member.model.Wardrobe;
import com.d2c.member.query.WardrobeSearcher;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("wardrobeService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class WardrobeServiceImpl extends ListServiceImpl<Wardrobe> implements WardrobeService {

    @Autowired
    private WardrobeMapper wardrobeMapper;

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public Wardrobe insert(Wardrobe wardrobe) {
        return this.save(wardrobe);
    }

    @Override
    public PageResult<Wardrobe> findBySearcher(WardrobeSearcher query, PageModel page) {
        PageResult<Wardrobe> pager = new PageResult<Wardrobe>(page);
        Integer totalCount = wardrobeMapper.countBySearcher(query);
        List<Wardrobe> list = new ArrayList<>();
        if (totalCount > 0) {
            list = wardrobeMapper.findBySearcher(query, page);
        }
        pager.setTotalCount(totalCount);
        pager.setList(list);
        return pager;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int delete(Long id) {
        return wardrobeMapper.delete(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int deleteByMemberId(Long[] ids, Long memberId) {
        return wardrobeMapper.deleteByMemberId(ids, memberId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int update(Wardrobe wardrobe) {
        return this.updateNotNull(wardrobe);
    }

    @Override
    public Integer countBySearcher(WardrobeSearcher query) {
        return wardrobeMapper.countBySearcher(query);
    }

}
