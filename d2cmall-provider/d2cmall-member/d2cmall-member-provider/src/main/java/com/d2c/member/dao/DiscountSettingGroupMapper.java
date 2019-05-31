package com.d2c.member.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.member.model.DiscountSettingGroup;
import com.d2c.member.query.DiscountSettingGroupSearcher;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface DiscountSettingGroupMapper extends SuperMapper<DiscountSettingGroup> {

    List<DiscountSettingGroup> findBySearch(@Param("searcher") DiscountSettingGroupSearcher searcher,
                                            @Param("pager") PageModel pager);

    int countBySearch(@Param("searcher") DiscountSettingGroupSearcher searcher);

    int updateStatusById(@Param("id") Long id, @Param("status") int status);

    int updateNameById(@Param("id") Long id, @Param("name") String name);

    int updateDate(@Param("id") Long id, @Param("beginDate") Date beginDate, @Param("endDate") Date endDate);

}
