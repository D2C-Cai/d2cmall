package com.d2c.member.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.member.model.Topic;
import com.d2c.member.query.TopicSearcher;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TopicMapper extends SuperMapper<Topic> {

    Integer countBySearcher(@Param("searcher") TopicSearcher searcher);

    List<Topic> findBySearcher(@Param("searcher") TopicSearcher searcher, @Param("pager") PageModel page);

    int delete(Long id);

    int updateStatus(@Param("id") Long id, @Param("status") Integer status);

    int updateTop(@Param("id") Long id, @Param("top") Integer top);

    int updateSort(@Param("id") Long id, @Param("sort") Integer sort);

}
