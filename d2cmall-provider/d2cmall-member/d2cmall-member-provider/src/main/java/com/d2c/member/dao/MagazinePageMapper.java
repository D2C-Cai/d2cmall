package com.d2c.member.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.member.model.MagazinePage;
import com.d2c.member.query.MagazinePageSearcher;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MagazinePageMapper extends SuperMapper<MagazinePage> {

    MagazinePage findByCode(@Param("code") String code);

    List<MagazinePage> findBySearcher(@Param("searcher") MagazinePageSearcher searcher,
                                      @Param("pager") PageModel pager);

    int countBySearcher(@Param("searcher") MagazinePageSearcher searcher);

    int updateStatus(@Param("id") Long id, @Param("status") Integer status, @Param("name") String name);

    int updateMagazineId(@Param("id") Long id, @Param("magazineId") Long magazineId);

    int updateSort(@Param("id") Long id, @Param("sort") Integer sort);

}
