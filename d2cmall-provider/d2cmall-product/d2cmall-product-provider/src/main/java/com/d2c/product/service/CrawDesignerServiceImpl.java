package com.d2c.product.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.product.dao.CrawDesignerMapper;
import com.d2c.product.model.CrawDesigner;
import com.d2c.product.model.CrawUrl;
import com.d2c.product.query.CrawDesignerSearcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("crawDesignerService")
public class CrawDesignerServiceImpl extends ListServiceImpl<CrawDesigner> implements CrawDesignerService {

    @Autowired
    private CrawDesignerMapper crawDesignerMapper;
    @Autowired
    private CrawUrlService crawUrlService;
    @Autowired
    private CrawProductService crawProductService;

    public CrawDesigner insert(CrawDesigner crawDesigner) {
        crawDesigner.setCrawDesignerUrl(crawDesigner.getCrawDesignerUrl().replace("https://", ""));
        crawDesigner.setCrawTimes(0L);
        crawDesigner = this.save(crawDesigner);
        return crawDesigner;
    }

    @Override
    public CrawDesigner findById(Long id) {
        return super.findOneById(id);
    }

    @Override
    public int doCrawByIds(Long[] ids) {
        for (Long designerId : ids) {
            CrawDesigner crawDesigner = this.findById(designerId);
            if (crawDesigner == null || crawDesigner.isCraw()) {
                continue;
            }
            crawDesigner.setCrawDate(new Date());
            crawUrlService.doClearUrlByDesignerId(designerId);
            crawProductService.doClearProductByDesignerId(designerId);
            CrawUrl crawUrl = new CrawUrl();
            crawUrl.setCrawUrl(crawDesigner.getCrawDesignerUrl());
            crawUrl.setCrawPageNo(1);
            crawUrl.setCrawDesignerId(designerId);
            crawUrl.setCrawRooturl(
                    crawDesigner.getCrawDesignerUrl().substring(0, crawDesigner.getCrawDesignerUrl().indexOf("/")));
            crawUrl.setCrawStatus(1);
            crawUrl.setCrawType(1);
            crawUrlService.insert(crawUrl);
            crawDesignerMapper.updateCrawDateById(new Date(), designerId);
        }
        return 1;
    }

    @Override
    public PageResult<CrawDesigner> findBySearch(CrawDesignerSearcher searcher, PageModel page) {
        int totalCount = crawDesignerMapper.countBySearcher(searcher);
        List<CrawDesigner> crawDesigners = new ArrayList<>();
        if (totalCount > 0) {
            crawDesigners = crawDesignerMapper.findBySearcher(searcher, page);
        }
        PageResult<CrawDesigner> pageResult = new PageResult<>();
        pageResult.setTotalCount(totalCount);
        pageResult.setList(crawDesigners);
        return pageResult;
    }

}
