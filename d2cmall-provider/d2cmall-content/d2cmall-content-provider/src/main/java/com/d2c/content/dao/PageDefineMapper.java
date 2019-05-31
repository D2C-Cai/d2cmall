package com.d2c.content.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.content.model.PageDefine;
import com.d2c.content.model.PageDefine.MODULE;
import com.d2c.content.model.PageDefine.TERMINAL;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PageDefineMapper extends SuperMapper<PageDefine> {

    PageDefine findPageDefine(@Param("module") MODULE module, @Param("terminal") TERMINAL terminal,
                              @Param("version") Integer version);

    List<PageDefine> findBySearch(@Param("searcher") PageDefine searcher, @Param("pager") PageModel pager);

    int countBySearch(@Param("searcher") PageDefine searcher);

    int update(PageDefine define);

}
