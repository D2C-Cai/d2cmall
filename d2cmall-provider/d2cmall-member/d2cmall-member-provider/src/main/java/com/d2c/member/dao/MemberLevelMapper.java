package com.d2c.member.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.member.model.MemberLevel;
import com.d2c.member.query.MemberLevelSearcher;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MemberLevelMapper extends SuperMapper<MemberLevel> {

    List<MemberLevel> findBySearch(@Param("searcher") MemberLevelSearcher searcher, @Param("page") PageModel page);

    int countBySearch(@Param("searcher") MemberLevelSearcher searcher);

    MemberLevel findByLevel(int level);

    int updateStatus(@Param("id") Long id, @Param("status") Integer status);

    /**
     * 查找达到要求的最高级
     *
     * @param amount
     * @return
     */
    MemberLevel findVaildLevel(@Param("amount") Integer amount);

}
