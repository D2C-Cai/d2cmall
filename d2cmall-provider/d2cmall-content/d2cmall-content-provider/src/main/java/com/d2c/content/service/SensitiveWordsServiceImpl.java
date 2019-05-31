package com.d2c.content.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.content.dao.SensitiveWordsMapper;
import com.d2c.content.model.SensitiveWords;
import com.d2c.content.query.SensitiveWordsSearcher;
import com.d2c.content.search.service.SensitiveWordsSearcherService;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("sensitiveWordsService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class SensitiveWordsServiceImpl extends ListServiceImpl<SensitiveWords> implements SensitiveWordsService {

    @Autowired
    private SensitiveWordsMapper sensitiveWordsMapper;
    @Reference
    private SensitiveWordsSearcherService sensitiveWordsSearcherService;

    @Override
    public PageResult<SensitiveWords> findBySearch(SensitiveWordsSearcher searcher, PageModel page) {
        PageResult<SensitiveWords> pager = new PageResult<SensitiveWords>(page);
        int count = sensitiveWordsMapper.countBySearcher(searcher);
        if (count > 0) {
            pager.setTotalCount(count);
            pager.setList(sensitiveWordsMapper.findBysearcher(searcher, page));
        }
        return pager;
    }

    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public SensitiveWords insert(SensitiveWords sensitiveWords) {
        sensitiveWords = this.save(sensitiveWords);
        /*
         * if (sensitiveWords != null) { SearcherSensitiveWords
         * searcherSensitiveWords = new SearcherSensitiveWords();
         * searcherSensitiveWords.setId(sensitiveWords.getId());
         * searcherSensitiveWords.setKeyword(sensitiveWords.getKeyword());
         * sensitiveWordsSearcherService.insert(searcherSensitiveWords); }
         */
        return sensitiveWords;
    }

    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int update(SensitiveWords sensitiveWords) {
        int success = this.updateNotNull(sensitiveWords);
        /*
         * if (success > 0) { SearcherSensitiveWords searcherSensitiveWords =
         * new SearcherSensitiveWords();
         * searcherSensitiveWords.setId(sensitiveWords.getId());
         * searcherSensitiveWords.setKeyword(sensitiveWords.getKeyword());
         * sensitiveWordsSearcherService.update(searcherSensitiveWords); }
         */
        return success;
    }

    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int deleteById(Long id) {
        int success = sensitiveWordsMapper.deleteById(id);
        if (success > 0) {
            sensitiveWordsSearcherService.remove(id);
        }
        return success;
    }

    @Override
    public SensitiveWords findById(Long id) {
        return this.findOneById(id);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateStatus(Long id, Integer status, String lastModifyMan) {
        int success = sensitiveWordsMapper.updateStatus(id, status, lastModifyMan);
        // if (success > 0) {
        // if (status == -1 || status == 0) {
        // sensitiveWordsSearcherService.remove(id);
        // } else {
        // SensitiveWords sensitiveWords = this.findById(id);
        // SearcherSensitiveWords searcherSensitiveWords = new
        // SearcherSensitiveWords();
        // searcherSensitiveWords.setId(sensitiveWords.getId());
        // searcherSensitiveWords.setKeyword(sensitiveWords.getKeyword());
        // sensitiveWordsSearcherService.insert(searcherSensitiveWords);
        // }
        // }
        return success;
    }

    @Override
    public boolean findBySensitiveWords(String sensitiveWords) {
        int success = sensitiveWordsMapper.findBySensitiveWords(sensitiveWords);
        return success > 0 ? true : false;
    }

    @Override
    public SensitiveWords findByKeyword(String keyword) {
        return sensitiveWordsMapper.findByKeyword(keyword);
    }

}
