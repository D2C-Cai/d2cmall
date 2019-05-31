package com.d2c.member.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.dao.WardrobeCollocationMapper;
import com.d2c.member.model.WardrobeCollocation;
import com.d2c.member.query.WardrobeCollocationSearcher;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("wardrobeCollocationService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class WardrobeCollocationServiceImpl extends ListServiceImpl<WardrobeCollocation>
        implements WardrobeCollocationService {

    @Autowired
    private WardrobeCollocationMapper wardrobeCollocationMapper;

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public WardrobeCollocation insert(WardrobeCollocation wardrobeCollocation) {
        return this.save(wardrobeCollocation);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int delete(Long id) {
        return wardrobeCollocationMapper.delete(id);
    }

    @Override
    public PageResult<WardrobeCollocation> findBySearcher(WardrobeCollocationSearcher query, PageModel page) {
        PageResult<WardrobeCollocation> pager = new PageResult<WardrobeCollocation>(page);
        Integer totalCount = wardrobeCollocationMapper.countBySearcher(query);
        List<WardrobeCollocation> list = new ArrayList<>();
        if (totalCount > 0) {
            list = wardrobeCollocationMapper.findBySearcher(query, page);
        }
        pager.setTotalCount(totalCount);
        pager.setList(list);
        return pager;
    }

    @Override
    public WardrobeCollocation findById(Long id) {
        return wardrobeCollocationMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doVerify(Long id, String verifyMan) {
        return wardrobeCollocationMapper.doVerify(id, verifyMan);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doCancelVerify(Long id, String lastModifyMan) {
        return wardrobeCollocationMapper.doCancelVerify(id, lastModifyMan);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doRefuse(Long id, String lastModifyMan) {
        return wardrobeCollocationMapper.doRefuse(id, lastModifyMan);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int update(WardrobeCollocation wardrobeCollocation) {
        return this.updateNotNull(wardrobeCollocation);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int updateVideoById(Long id, String video) {
        return this.updateFieldById(id.intValue(), "video", video);
    }

    @Override
    public PageResult<WardrobeCollocation> findMine(Long memberId, WardrobeCollocationSearcher query, PageModel page) {
        PageResult<WardrobeCollocation> pager = new PageResult<WardrobeCollocation>(page);
        Integer totalCount = wardrobeCollocationMapper.countMine(memberId, query);
        List<WardrobeCollocation> list = new ArrayList<>();
        if (totalCount > 0) {
            list = wardrobeCollocationMapper.findMine(memberId, query, page);
        }
        pager.setTotalCount(totalCount);
        pager.setList(list);
        return pager;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int deleteByMemberId(Long[] ids, Long memberId) {
        return wardrobeCollocationMapper.deleteByMemberId(ids, memberId);
    }

}
