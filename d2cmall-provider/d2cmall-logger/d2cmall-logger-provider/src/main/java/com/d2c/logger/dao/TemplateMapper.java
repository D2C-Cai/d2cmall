package com.d2c.logger.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.logger.model.Template;
import com.d2c.logger.query.TemplateSearcher;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TemplateMapper extends SuperMapper<Template> {

    List<Template> findBySearch(@Param("pager") PageModel pager, @Param("searcher") TemplateSearcher searcher);

    int countBySearch(@Param("searcher") TemplateSearcher searcher);

}
