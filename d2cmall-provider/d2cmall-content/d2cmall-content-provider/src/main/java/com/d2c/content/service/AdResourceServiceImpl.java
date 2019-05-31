package com.d2c.content.service;

import com.d2c.cache.redis.RedisHandler;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.content.dao.AdResourceMapper;
import com.d2c.content.model.AdResource;
import com.d2c.content.query.AdResourceSearcher;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("adResourceService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class AdResourceServiceImpl extends ListServiceImpl<AdResource> implements AdResourceService {

    @Autowired
    private AdResourceMapper adResourceMapper;
    @Autowired
    private RedisHandler<String, String> redisHandler;

    @Override
    public AdResource findById(Long id) {
        return this.findOneById(id);
    }

    @Override
    public List<AdResource> findByAppChannel(String appChannel) {
        return adResourceMapper.findByAppChannel(appChannel);
    }

    @Override
    @Cacheable(value = "ad_resource", key = "'ad_resource_'+#channel+'_'+#type", unless = "#result == null")
    public AdResource findByAppChannelAndType(String channel, String type) {
        return adResourceMapper.findByAppChannelAndType(channel, type);
    }

    @Override
    public PageResult<AdResource> findBySearcher(AdResourceSearcher searcher, PageModel page) {
        PageResult<AdResource> pager = new PageResult<AdResource>(page);
        Integer totalCount = adResourceMapper.countBySearcher(searcher);
        List<AdResource> list = new ArrayList<AdResource>();
        if (totalCount > 0) {
            list = adResourceMapper.findBySearcher(searcher, page);
        }
        pager.setTotalCount(totalCount);
        pager.setList(list);
        return pager;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public AdResource insert(AdResource adResource) {
        return this.save(adResource);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int update(AdResource adResource) {
        clearCache(this.findById(adResource.getId()));
        if (adResource.getVideo() == null) {
            this.updateFieldById(adResource.getId().intValue(), "video", null);
        }
        return this.updateNotNull(adResource);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateStatus(Long id, Integer status) {
        clearCache(this.findById(id));
        return adResourceMapper.updateStatus(id, status);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int delete(Long id) {
        clearCache(this.findById(id));
        return this.deleteById(id);
    }

    private void clearCache(AdResource adResource) {
        redisHandler.delete("ad_resource_" + adResource.getAppChannel() + '_' + adResource.getType());
    }

    @Override
    public AdResource findByAppChannelAndTypeForBack(String appChannel, String type) {
        return adResourceMapper.findByAppChannelAndTypeForBack(appChannel, type);
    }

}
