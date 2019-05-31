package com.d2c.content.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.content.dto.VoteSelectionDto;
import com.d2c.content.model.VoteItem;
import com.d2c.content.query.VoteItemSearcher;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VoteItemMapper extends SuperMapper<VoteItem> {

    List<VoteSelectionDto> findByDefIdGroupBySelection(@Param("searcher") VoteItemSearcher searcher,
                                                       @Param("page") PageModel page);

    Integer countByDefIdGroupBySelection(@Param("searcher") VoteItemSearcher searcher);

    int updateByDef(@Param("selectionId") Long selectionId, @Param("defTitle") String defTitle,
                    @Param("selectName") String selectName, @Param("pic") String pic, @Param("coef") Double coef);

    int updateStatusByDef(@Param("selectionId") Long selectionId, @Param("status") Integer status);

    List<VoteItem> findByDefIdAndMemberId(@Param("defId") Long defId, @Param("memberId") Long memberId);

    int countBySearcher(@Param("searcher") VoteItemSearcher searcher);

}
