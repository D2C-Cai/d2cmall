package com.d2c.content.dao;

import com.d2c.content.model.FieldDefine;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FieldDefineMapper extends SuperMapper<FieldDefine> {

    List<FieldDefine> findByPageDefId(@Param("pageDefId") Long pageDefId);

    List<FieldDefine> findByPageDefIdWithStatus(@Param("pageDefId") Long pageDefId, @Param("status") int status);

    FieldDefine findOne(@Param("pageDefId") Long pageDefId, @Param("code") String code);

}
