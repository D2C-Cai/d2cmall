package com.d2c.order.dao;

import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.order.model.Area;

import java.util.List;

/**
 * @author xh
 * @see 地区管理
 */
public interface AreaMapper extends SuperMapper<Area> {

    List<Area> findAreaByName(String name);

    Area findAreaByCode(String code);

}
