package com.d2c.member.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.dao.MemberLevelMapper;
import com.d2c.member.model.MemberLevel;
import com.d2c.member.query.MemberLevelSearcher;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("memberLevelService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class MemberLevelServiceImpl extends ListServiceImpl<MemberLevel> implements MemberLevelService {

    @Autowired
    private MemberLevelMapper memberlevelMapper;

    @Override
    @Cacheable(value = "member_level", key = "'member_level_'+#id", unless = "#result == null")
    public MemberLevel findById(Long id) {
        return memberlevelMapper.selectByPrimaryKey(id);
    }

    @Override
    public MemberLevel findByLevel(int level) {
        return memberlevelMapper.findByLevel(level);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public MemberLevel insert(MemberLevel memberLevel) {
        return this.save(memberLevel);
    }

    @Override
    @CacheEvict(value = "member_level", key = "'member_level_'+#memberLevel.id")
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int update(MemberLevel memberLevel) {
        return this.updateNotNull(memberLevel);
    }

    @Override
    public PageResult<MemberLevel> findBySearch(MemberLevelSearcher searcher, PageModel page) {
        PageResult<MemberLevel> pager = new PageResult<>(page);
        int totalCount = memberlevelMapper.countBySearch(searcher);
        if (totalCount > 0) {
            List<MemberLevel> list = memberlevelMapper.findBySearch(searcher, page);
            pager.setList(list);
        }
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    public int countBySearch(MemberLevelSearcher searcher) {
        return memberlevelMapper.countBySearch(searcher);
    }

    @Override
    public MemberLevel findVaildLevel(Integer amount) {
        return memberlevelMapper.findVaildLevel(amount);
    }

}
