package com.d2c.member.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.member.model.WardrobeCategory;
import com.d2c.member.query.WardrobeCategorySearcher;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WardrobeCategoryMapper extends SuperMapper<WardrobeCategory> {

    Integer countBySearcher(@Param("searcher") WardrobeCategorySearcher query);

    List<WardrobeCategory> findBySearcher(@Param("searcher") WardrobeCategorySearcher query,
                                          @Param("pager") PageModel page);

    List<WardrobeCategory> findByTop(@Param("topName") String topName, @Param("status") Integer status);

    WardrobeCategory findByName(String name);

    int delete(Long id);

}
