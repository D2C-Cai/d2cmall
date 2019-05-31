package com.d2c.content.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.content.model.VoteSelection;
import com.d2c.content.query.VoteSelectionSearcher;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VoteSelectionMapper extends SuperMapper<VoteSelection> {

    List<VoteSelection> findByDefId(Long defId);

    int update(VoteSelection voteSelection);

    int delete(Long id);

    List<VoteSelection> findByIds(@Param("ids") Long[] ids);

    List<VoteSelection> findBySearcher(@Param("searcher") VoteSelectionSearcher searcher,
                                       @Param("page") PageModel page);

    int countBySearcher(@Param("searcher") VoteSelectionSearcher searcher);

    int updateByDefId(@Param("defId") Long defId, @Param("title") String title);

}
