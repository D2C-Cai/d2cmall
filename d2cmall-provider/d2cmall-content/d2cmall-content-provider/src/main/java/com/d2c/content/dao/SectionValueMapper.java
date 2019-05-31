package com.d2c.content.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.content.dto.SectionValueDto;
import com.d2c.content.model.SectionValue;
import com.d2c.content.query.SectionValueSearcher;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SectionValueMapper extends SuperMapper<SectionValue> {

    List<SectionValue> findBySearcher(@Param("searcher") SectionValueSearcher searcher,
                                      @Param("pager") PageModel pager);

    int countBySearcher(@Param("searcher") SectionValueSearcher searcher);

    List<SectionValueDto> findDtoBySearcher(@Param("searcher") SectionValueSearcher searcher,
                                            @Param("pager") PageModel page);

    int deleteBySectionId(Long sectionId);

    int deleteByModuleId(Long moduleId);

    int updateStatus(@Param("id") Long id, @Param("status") Integer status);

    int doRecovery(@Param("id") Long id);

    int countDeletedBySearcher(@Param("searcher") SectionValueSearcher searcher);

    List<SectionValue> findDeletedBySearcher(@Param("searcher") SectionValueSearcher searcher,
                                             @Param("pager") PageModel page);

    int delete(Long id);

}