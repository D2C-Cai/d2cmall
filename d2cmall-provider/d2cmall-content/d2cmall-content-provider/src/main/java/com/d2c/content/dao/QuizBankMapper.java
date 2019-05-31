package com.d2c.content.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.content.model.QuizBank;
import com.d2c.content.query.QuizBankSearcher;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QuizBankMapper extends SuperMapper<QuizBank> {

    int deleteById(@Param("id") Long id);

    int countBySearcher(@Param("searcher") QuizBankSearcher searcher);

    List<QuizBank> findBySearcher(@Param("searcher") QuizBankSearcher searcher, @Param("page") PageModel page);

    int updateStatus(@Param("id") Long id, @Param("mark") Integer mark, @Param("operator") String operator);

    List<QuizBank> findByType(@Param("type") Integer type);

    int updateQuizBank(@Param("quiz") QuizBank quiz);

}
