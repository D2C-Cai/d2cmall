package com.d2c.content.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.content.model.AdResource;
import com.d2c.content.query.AdResourceSearcher;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdResourceMapper extends SuperMapper<AdResource> {

    List<AdResource> findBySearcher(@Param("searcher") AdResourceSearcher searcher, @Param("pager") PageModel page);

    Integer countBySearcher(@Param("searcher") AdResourceSearcher searcher);

    List<AdResource> findByAppChannel(@Param("appChannel") String appChannel);

    AdResource findByAppChannelAndType(@Param("appChannel") String appChannel, @Param("type") String type);

    int updateStatus(@Param("id") Long id, @Param("status") Integer status);

    int delete(Long id);

    AdResource findByAppChannelAndTypeForBack(@Param("appChannel") String appChannel, @Param("type") String type);

}
