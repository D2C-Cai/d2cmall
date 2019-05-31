package com.d2c.content.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.content.model.Section;
import com.d2c.content.query.SectionSearcher;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SectionMapper extends SuperMapper<Section> {

    List<Section> findBySearcher(@Param("searcher") SectionSearcher searcher, @Param("pager") PageModel pager);

    int countBySearcher(@Param("searcher") SectionSearcher searcher);

    int deleteByModuleId(Long moduleId);

}
