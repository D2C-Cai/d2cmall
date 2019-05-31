package com.d2c.flame.freemarker;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.core.cache.old.CacheCallback;
import com.d2c.common.core.cache.old.CacheKey;
import com.d2c.common.core.cache.old.CacheTimerHandler;
import com.d2c.logger.model.MemberSearchSum;
import com.d2c.logger.query.MemberSearchSumSearcher;
import com.d2c.logger.service.MemberSearchSumService;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class RenderSearchHot implements TemplateMethodModelEx {

    protected static final Log logger = LogFactory.getLog(RenderSearchHot.class);
    @Autowired
    private MemberSearchSumService memberSearchSumService;

    @Override
    public Object exec(@SuppressWarnings("rawtypes") List arg0) throws TemplateModelException {
        String key = CacheKey.SEARCHHOTKEY;
        List<MemberSearchSum> result = CacheTimerHandler.getAndSetCacheValue(key, 60,
                new CacheCallback<List<MemberSearchSum>>() {
                    @Override
                    public List<MemberSearchSum> doExecute() {
                        MemberSearchSumSearcher searcher = new MemberSearchSumSearcher();
                        List<MemberSearchSum> searchHot = memberSearchSumService
                                .findBySearcher(searcher, new PageModel(1, 11)).getList();
                        return searchHot;
                    }
                });
        return result;
    }

}
