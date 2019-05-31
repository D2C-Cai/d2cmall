package com.d2c.logger.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.core.cache.old.CacheKey;
import com.d2c.common.mq.enums.MqEnum;
import com.d2c.logger.dao.MemberSearchSumMapper;
import com.d2c.logger.model.MemberSearchSum;
import com.d2c.logger.query.MemberSearchSumSearcher;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.util.string.PinYinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("memberSearchSumService")
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class MemberSearchSumServiceImpl extends ListServiceImpl<MemberSearchSum> implements MemberSearchSumService {

    @Autowired
    private MemberSearchSumMapper memberSearchSumMapper;

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int updateSort(Long id, int sort) {
        int success = memberSearchSumMapper.updateSort(id, sort);
        if (success > 0) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("mem_cache", CacheKey.SEARCHHOTKEY);
            MqEnum.FLUSH_CACHE.send(map);
        }
        return success;
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int updateNumberByKeyword(String keyword, int count) {
        return memberSearchSumMapper.updateNumberByKeyword(keyword, count);
    }

    public MemberSearchSum findById(Long id) {
        return this.findOneById(id);
    }

    public MemberSearchSum findByKey(String keyword) {
        return memberSearchSumMapper.findByKey(keyword);
    }

    public PageResult<MemberSearchSum> findBySearcher(MemberSearchSumSearcher searcher, PageModel page) {
        int totalCount = memberSearchSumMapper.countBySearcher(searcher);
        List<MemberSearchSum> list = new ArrayList<MemberSearchSum>();
        PageResult<MemberSearchSum> pager = new PageResult<MemberSearchSum>(page);
        if (totalCount > 0) {
            list = memberSearchSumMapper.findBySearcher(searcher, page);
        }
        pager.setTotalCount(totalCount);
        pager.setList(list);
        return pager;
    }

    @Override
    public int countBySearcher(MemberSearchSumSearcher searcher) {
        return memberSearchSumMapper.countBySearcher(searcher);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int doSystem(Long id) {
        MemberSearchSum entity = this.findById(id);
        int result = 0;
        if (entity == null) {
            return result;
        }
        result = memberSearchSumMapper.doSystem(id);
        String keyword = entity.getKeyword();
        if (result > 0 && PinYinUtil.containChinese(keyword)) {
            // entity.setKeyword(PinYinUtil.getFirstSpell(keyword).toLowerCase());
            // entity.setId(null);
            // super.save(entity);
            // entity.setKeyword(PinYinUtil.getFullSpell(keyword).toLowerCase());
            // entity.setId(null);
            // super.save(entity);
        }
        return result;
    }

    @Override
    public MemberSearchSum insert(MemberSearchSum entity) {
        return this.save(entity);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public MemberSearchSum save(MemberSearchSum entity) {
        MemberSearchSum ss = super.save(entity);
        if (ss != null && ss.getId() > 0) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("mem_cache", CacheKey.SEARCHHOTKEY);
            MqEnum.FLUSH_CACHE.send(map);
        }
        return ss;
    }

    @Override
    public int update(MemberSearchSum newMS) {
        newMS.setNumber(null);
        int success = this.updateNotNull(newMS);
        if (success > 0) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("mem_cache", CacheKey.SEARCHHOTKEY);
            MqEnum.FLUSH_CACHE.send(map);
        }
        return 0;
    }

    @Override
    public int delete(Long id) {
        int success = this.deleteById(id);
        if (success > 0) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("mem_cache", CacheKey.SEARCHHOTKEY);
            MqEnum.FLUSH_CACHE.send(map);
        }
        return success;
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int updateStatus(Long id, int status) {
        int success = memberSearchSumMapper.updateStatus(id, status);
        if (success > 0) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("mem_cache", CacheKey.SEARCHHOTKEY);
            MqEnum.FLUSH_CACHE.send(map);
        }
        return success;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int addCountById(Long id) {
        return memberSearchSumMapper.updateCountById(id, 1);
    }

}
