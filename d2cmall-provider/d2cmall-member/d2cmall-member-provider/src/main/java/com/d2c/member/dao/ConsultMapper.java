package com.d2c.member.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.member.model.Consult;
import com.d2c.member.query.ConsultSearcher;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ConsultMapper extends SuperMapper<Consult> {

    List<Consult> findBySearcher(@Param("searcher") ConsultSearcher searcher, @Param("pager") PageModel pager);

    int countBySearcher(@Param("searcher") ConsultSearcher searcher);

    int countByProductId(@Param("productId") Long productId, @Param("status") Integer status);

    List<Map<String, Object>> countGroupByStatus();

    int reply(@Param("id") Long id, @Param("reply") String reply);

    int updateRecomendByRecomendStatus(@Param("ids") Long[] ids, @Param("recomend") Integer recomend);

    int doRefreshHeadPic(@Param("memberInfoId") Long memberInfoId, @Param("headPic") String headPic,
                         @Param("nickName") String nickName);

    int deleteById(@Param("id") Long id);

    int cancelDelete(@Param("ids") Long[] ids);

}
