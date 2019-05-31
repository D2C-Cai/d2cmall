package com.d2c.member.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.dto.ConsultDto;
import com.d2c.member.model.Consult;
import com.d2c.member.query.ConsultSearcher;

import java.util.Map;

public interface ConsultService {

    /**
     * 查询咨询
     *
     * @param id：主键ID
     * @return
     */
    Consult findById(Long id);

    /**
     * 查询咨询列表
     *
     * @param searcher：咨询内容
     * @param pager：分页
     * @return
     */
    PageResult<ConsultDto> findBySearcher(ConsultSearcher searcher, PageModel page);

    /**
     * 查询咨询条数总数
     *
     * @param searcher：咨询内容
     * @return
     */
    int countBySearcher(ConsultSearcher searcher);

    /**
     * 查询咨询的数量
     *
     * @param productId
     * @return
     */
    int countByProduct(Long productId, Integer status);

    /**
     * 查询咨询状态数量
     *
     * @return
     */
    Map<String, Object> countGroupByStatus();

    /**
     * 添加咨询
     *
     * @param consult：咨询内容
     * @return
     */
    Consult insert(Consult consult);

    /**
     * 批量置顶或者取消置顶
     *
     * @param ids
     * @param status
     * @return
     */
    int updateRecommand(Long[] ids, Integer top);

    /**
     * 回复咨询内容
     *
     * @param id：主键ID
     * @param reply：回复内容
     * @return
     */
    int doReply(Long[] ids, String reply);

    /**
     * 刷新用户头像
     *
     * @param memberInfoId
     * @param headPic
     */
    void doRefreshHeadPic(Long memberInfoId, String headPic, String nickName);

    /**
     * 删除咨询
     *
     * @param ids主键ID
     * @return
     */
    int deleteByIds(Long[] ids);

    /**
     * 取消删除
     *
     * @param id：主键ID
     * @return
     */
    int cancelDelete(Long[] ids);

}
