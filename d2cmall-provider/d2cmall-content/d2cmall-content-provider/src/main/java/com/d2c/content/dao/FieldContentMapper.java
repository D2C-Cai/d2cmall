package com.d2c.content.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.content.model.FieldContent;
import com.d2c.content.query.FieldContentSearcher;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FieldContentMapper extends SuperMapper<FieldContent> {

    List<FieldContent> findByGroupAndPage(@Param("group") String group, @Param("pageid") Long pageid,
                                          @Param("pager") PageModel pager);

    int countByGroupAndPage(@Param("group") String group, @Param("pageid") Long pageid);

    List<FieldContent> findBySearch(@Param("pager") PageModel pager, @Param("searcher") FieldContentSearcher searcher);

    int countBySearch(@Param("searcher") FieldContentSearcher searcher);

}