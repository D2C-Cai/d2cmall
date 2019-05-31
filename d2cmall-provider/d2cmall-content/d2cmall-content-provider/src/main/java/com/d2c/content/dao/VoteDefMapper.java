package com.d2c.content.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.content.model.VoteDef;
import com.d2c.content.query.VoteDefSearcher;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VoteDefMapper extends SuperMapper<VoteDef> {

    List<VoteDef> findBySearcher(@Param("searcher") VoteDefSearcher searcher, @Param("page") PageModel page);

    Integer countBySearcher(@Param("searcher") VoteDefSearcher searcher);

    int updateStatus(@Param("id") Long id, @Param("status") Integer status);

}
