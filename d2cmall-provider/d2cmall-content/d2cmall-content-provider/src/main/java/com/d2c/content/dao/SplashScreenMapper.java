package com.d2c.content.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.content.model.SplashScreen;
import com.d2c.content.query.SplashScreenSearcher;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SplashScreenMapper extends SuperMapper<SplashScreen> {

    Integer countBySearcher(@Param("searcher") SplashScreenSearcher searcher);

    List<SplashScreen> findBySearcher(@Param("pager") PageModel page, @Param("searcher") SplashScreenSearcher searcher);

    SplashScreen findCurrentVersion();

    int updateStatus(@Param("id") Long id, @Param("status") Integer status,
                     @Param("lastModifyMan") String lastModifyMan);

    int doTiming(@Param("id") Long id, @Param("timing") Integer timing);

    int doDownAll();

}
