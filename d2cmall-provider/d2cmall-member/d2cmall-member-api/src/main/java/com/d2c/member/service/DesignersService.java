package com.d2c.member.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.model.Designers;
import com.d2c.member.query.DesignersSearcher;

public interface DesignersService {

    /**
     * 根据id查询设计师
     *
     * @param id
     * @return
     */
    Designers findById(Long id);

    /**
     * 分页查找
     *
     * @param designers
     * @param page
     * @return
     */
    PageResult<Designers> findBySearcher(DesignersSearcher searcher, PageModel page);

    /**
     * 插入设计师
     *
     * @param designers
     * @return
     */
    Designers insert(Designers designers, String operator);

    /**
     * 编辑设计师
     *
     * @param designers
     * @return
     */
    int update(Designers designers, String operator);

    /**
     * 绑定会员
     *
     * @param id
     * @param memberId
     * @param loginCode
     * @return
     */
    int bindMemberInfo(Long id, Long memberId, String loginCode);

    /**
     * 解绑会员
     *
     * @param id
     * @return
     */
    int cancelMemberInfo(Long id);

}
