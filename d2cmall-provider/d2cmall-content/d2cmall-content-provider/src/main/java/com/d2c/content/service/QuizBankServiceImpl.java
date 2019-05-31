package com.d2c.content.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.content.dao.QuizBankMapper;
import com.d2c.content.model.QuizBank;
import com.d2c.content.query.QuizBankSearcher;
import com.d2c.content.search.model.SearcherQuizBank;
import com.d2c.content.search.service.QuizBankSearcherService;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("quizBankService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class QuizBankServiceImpl extends ListServiceImpl<QuizBank> implements QuizBankService {

    @Autowired
    private QuizBankMapper quizBankMapper;
    @Reference
    private QuizBankSearcherService quizBankSearcherService;

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public QuizBank insert(QuizBank quiz) {
        quiz = this.save(quiz);
        if (quiz.getId() != null) {
            SearcherQuizBank searcherQuizBank = new SearcherQuizBank();
            BeanUtils.copyProperties(quiz, searcherQuizBank);
            quizBankSearcherService.insert(searcherQuizBank);
        }
        return quiz;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int update(QuizBank quiz) {
        int result = quizBankMapper.updateQuizBank(quiz);
        if (result > 0) {
            SearcherQuizBank searcherQuizBank = new SearcherQuizBank();
            BeanUtils.copyProperties(quiz, searcherQuizBank);
            quizBankSearcherService.updateQuizBank(searcherQuizBank);
        }
        return result;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int deleteById(Long id) {
        int result = quizBankMapper.deleteById(id);
        if (result > 0) {
            quizBankSearcherService.remove(id.toString());
        }
        return result;
    }

    @Override
    public PageResult<QuizBank> findBySearcher(QuizBankSearcher searcher, PageModel page) {
        PageResult<QuizBank> pager = new PageResult<>(page);
        int totalCount = quizBankMapper.countBySearcher(searcher);
        if (totalCount > 0) {
            List<QuizBank> list = quizBankMapper.findBySearcher(searcher, page);
            pager.setList(list);
        }
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateStatus(Long id, Integer status, String operator) {
        int result = quizBankMapper.updateStatus(id, status, operator);
        if (result > 0) {
            result = quizBankSearcherService.updateStatus(id, status);
        }
        return result;
    }

    @Override
    public int countBySearcher(QuizBankSearcher searcher) {
        return quizBankMapper.countBySearcher(searcher);
    }

}
