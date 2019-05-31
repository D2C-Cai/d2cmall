package com.d2c.product.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.product.dao.CrawUrlMapper;
import com.d2c.product.model.CrawUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("crawUrlService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class CrawUrlServiceImpl extends ListServiceImpl<CrawUrl> implements CrawUrlService {

    @Autowired
    private CrawUrlMapper crawUrlMapper;

    @Override
    public int countByStatus(int status) {
        return crawUrlMapper.countByStatus(status);
    }

    @Override
    public List<CrawUrl> findListByStatus(int status, PageModel pageModel) {
        return crawUrlMapper.findListByStatus(status, pageModel);
    }

    @Override
    public CrawUrl insert(CrawUrl crawUrl) {
        return this.save(crawUrl);
    }

    @Override
    public int updateStatusByUrl(String crawUrl) {
        return crawUrlMapper.updateStatusByUrl(crawUrl, 0);
    }

    @Override
    public CrawUrl findByRootUrlAndPageNo(String rootUrl, String pageNo) {
        return crawUrlMapper.findByRootUrlAndPageNo(rootUrl, pageNo);
    }

    @Override
    public CrawUrl findById(Long id) {
        return super.findOneById(id);
    }

    @Override
    public CrawUrl findByProductId(String productId) {
        return crawUrlMapper.findByProductId(productId);
    }

    @Override
    public void doClearUrlByDesignerId(Long designerId) {
        crawUrlMapper.deleteByDesignerId(designerId);
    }

}
