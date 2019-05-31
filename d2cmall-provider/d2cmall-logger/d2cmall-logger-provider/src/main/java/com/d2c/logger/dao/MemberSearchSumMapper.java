package com.d2c.logger.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.logger.model.MemberSearchSum;
import com.d2c.logger.query.MemberSearchSumSearcher;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MemberSearchSumMapper extends SuperMapper<MemberSearchSum> {

    MemberSearchSum findByKey(@Param("keyword") String keyword);

    List<MemberSearchSum> findBySearcher(@Param("searcher") MemberSearchSumSearcher searcher,
                                         @Param("pager") PageModel pager);

    int countBySearcher(@Param("searcher") MemberSearchSumSearcher searcher);

    /**
     * 更新排序
     */
    int updateSort(@Param("id") Long id, @Param("sort") int sort);

    /**
     * 更新搜索次数
     */
    int updateNumberByKeyword(@Param("keyword") String keyword, @Param("count") int count);

    int doSystem(Long id);

    int updateStatus(@Param("id") Long id, @Param("status") int status);

    /**
     * 根据Id去增加一次搜索次数
     *
     * @param id
     * @return
     */
    int updateCountById(@Param("id") Long id, @Param("count") int count);

}
