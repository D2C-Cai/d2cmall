package com.d2c.similar.dao;

import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.similar.entity.DimenValueDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wull
 */
public interface DimenValueMapper extends SuperMapper<DimenValueDO> {

    public List<Double> getDistByValue(@Param("ruleId") Integer ruleId, @Param("fieldValue") String fieldValue);

}
