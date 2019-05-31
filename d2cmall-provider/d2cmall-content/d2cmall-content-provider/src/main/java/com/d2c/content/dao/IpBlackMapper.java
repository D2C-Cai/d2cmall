package com.d2c.content.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.content.model.IpBlack;
import com.d2c.content.query.IpBlackSearcher;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IpBlackMapper extends SuperMapper<IpBlack> {

    int countBySearcher(@Param("searcher") IpBlackSearcher searcher);

    List<IpBlack> findBysearcher(@Param("searcher") IpBlackSearcher searcher, @Param("pager") PageModel pager);

    int deleteById(@Param("id") Long id);

    int updateStatus(@Param("id") Long id, @Param("status") Integer status,
                     @Param("lastModifyMan") String lastModifyMan);

}
