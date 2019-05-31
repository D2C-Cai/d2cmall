package com.d2c.order.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.order.model.Setting;
import com.d2c.order.query.SettingSearcher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SettingMapper extends SuperMapper<Setting> {

    List<Setting> findBySearch(@Param("pager") PageModel pager, @Param("searcher") SettingSearcher searcher);

    int countBySearch(@Param("searcher") SettingSearcher searcher);

    int updateValueById(@Param("value") String value, @Param("id") Long id);

    int updateStatusById(@Param("id") Long id, @Param("status") int status);

    Setting findByCode(@Param("code") String code);

    int deleteByBelongto(Long belongto);

    int updateStatusByBelongto(@Param("belongto") Long belongto, @Param("status") int status);

}
