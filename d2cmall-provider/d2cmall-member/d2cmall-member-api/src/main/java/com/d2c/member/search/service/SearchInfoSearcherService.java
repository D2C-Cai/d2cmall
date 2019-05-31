package com.d2c.member.search.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.search.model.SearcherMemberInfo;
import com.d2c.member.search.query.MemberInfoSearchBean;

import java.util.Map;

public interface SearchInfoSearcherService {

    public static final String TYPE_SEACHERINFO = "typeseacherinfo";

    /**
     * 添加关键字搜索数据
     *
     * @param searchSum
     * @return
     */
    int insert(SearcherMemberInfo searchInfo);

    /**
     * 聚合分页查询
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<Map<String, Object>> findGroupBySearcher(MemberInfoSearchBean searcher, PageModel page);

    /**
     * 删除所有
     */
    int removeAll();

}
