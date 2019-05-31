package com.d2c.order.service;

import com.d2c.common.api.service.ListService;
import com.d2c.order.model.Area;

import java.util.List;

/**
 * 地区（area）
 */
public interface AreaService extends ListService<Area> {

    /**
     * @param name 名称
     * @return
     */
    List<Area> findAreaByName(String name);

    /**
     * 通过编码得到区域
     *
     * @param code
     * @return
     */
    Area findAreaByCode(Integer code);

    List<Area> findAll();

}
