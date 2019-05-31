package com.d2c.member.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.member.model.Magazine;
import com.d2c.member.query.MagazineSearcher;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MagazineMapper extends SuperMapper<Magazine> {

    List<Magazine> findBySearcher(@Param("searcher") MagazineSearcher searcher, @Param("pager") PageModel pager);

    int countBySearcher(@Param("searcher") MagazineSearcher searcher);

    int updateStatus(@Param("id") Long id, @Param("status") Integer status, @Param("name") String name);

}
