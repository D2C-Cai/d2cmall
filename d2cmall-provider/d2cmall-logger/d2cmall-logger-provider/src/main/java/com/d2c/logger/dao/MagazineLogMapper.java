package com.d2c.logger.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.logger.model.MagazineLog;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MagazineLogMapper extends SuperMapper<MagazineLog> {

    int doInsert(MagazineLog magazineLog);

    List<MagazineLog> findByMemberId(@Param("memberId") Long memberId, @Param("pager") PageModel page);

    Integer countByMemberId(@Param("memberId") Long memberId);

}
