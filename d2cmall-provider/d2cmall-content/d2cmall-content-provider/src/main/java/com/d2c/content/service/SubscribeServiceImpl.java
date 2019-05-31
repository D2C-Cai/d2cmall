package com.d2c.content.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.content.dao.SubscribeMapper;
import com.d2c.content.model.Subscribe;
import com.d2c.content.query.SubscribeSearcher;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("subscribeService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class SubscribeServiceImpl extends ListServiceImpl<Subscribe> implements SubscribeService {

    @Autowired
    private SubscribeMapper subscribeMapper;

    public Subscribe findById(Long id) {
        return this.findOneById(id);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Subscribe insert(Subscribe entity) {
        return this.save(entity);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int update(Subscribe entity) {
        return this.updateNotNull(entity);
    }

    public PageResult<Subscribe> findBySearcher(SubscribeSearcher searcher, PageModel page) {
        PageResult<Subscribe> pager = new PageResult<Subscribe>(page);
        int count = subscribeMapper.countSubBySearcher(searcher);
        if (count > 0) {
            List<Subscribe> list = subscribeMapper.findSubBySearcher(searcher, page);
            pager.setList(list);
            pager.setTotalCount(count);
        }
        return pager;
    }

    @Override
    public int countBySearcher(SubscribeSearcher searcher) {
        return subscribeMapper.countSubBySearcher(searcher);
    }

    @Override
    public Subscribe findByMobile(String mobile) {
        return subscribeMapper.findBySubscribe(mobile);
    }

    @Override
    public Subscribe findByEmail(String email) {
        return subscribeMapper.findBySubscribe(email);
    }

}
