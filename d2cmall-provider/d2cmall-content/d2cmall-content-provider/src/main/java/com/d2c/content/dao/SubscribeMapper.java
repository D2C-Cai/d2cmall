package com.d2c.content.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.content.model.Subscribe;
import com.d2c.content.model.Subscribe.SubType;
import com.d2c.content.query.SubscribeSearcher;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SubscribeMapper extends SuperMapper<Subscribe> {

    List<Subscribe> findByType(@Param("subType") SubType subType);

    int countSubBySearcher(@Param("searcher") SubscribeSearcher searcher);

    List<Subscribe> findSubBySearcher(@Param("searcher") SubscribeSearcher searcher, @Param("pager") PageModel pager);

    Subscribe findBySubscribe(@Param("subscribe") String subscribe);

}