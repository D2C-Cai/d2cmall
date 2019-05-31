package com.d2c.member.dao;

import com.d2c.member.model.Resource;
import com.d2c.member.query.ResourceSearcher;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ResourceMapper extends SuperMapper<Resource> {

    Resource findByValue(String value);

    List<Long> findIdsByRoleId(Long id);

    List<Resource> findByRoles(@Param("roles") List<String> roles);

    List<Resource> findBySearch(@Param("searcher") ResourceSearcher searcher);

    int deleteById(Long id);

    int deleteRoleResouceByResourceId(@Param("resourceId") Long resourceId);

    int updateSequence(@Param("id") Long id, @Param("sequence") Integer sequence);

}
