package com.d2c.member.search.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.search.model.SearcherConsult;
import com.d2c.member.search.query.ConsultSearchBean;

public interface ConsultSearcherService {

    public static final String TYPE_CONSULT = "typeconsult";

    /**
     * 添加一条咨询回复搜索
     *
     * @param searcherConsult
     * @return
     */
    int insert(SearcherConsult searcherConsult);

    /**
     * 通过id，得到咨询回复搜索的记录
     *
     * @param id
     * @return
     */
    SearcherConsult findById(String id);

    /**
     * 通过商品查询咨询数量
     *
     * @param productId
     * @return
     */
    int countByProductId(Long productId);

    /**
     * 通过搜索条件和分页，得到咨询回复搜索的分页数据
     *
     * @param search
     * @param page
     * @return
     */
    PageResult<SearcherConsult> search(ConsultSearchBean search, PageModel page);

    /**
     * 更新一条咨询回复搜索
     *
     * @param searcherConsult
     * @return
     */
    int update(SearcherConsult searcherConsult);

    /**
     * 通过ids更新状态
     *
     * @param ids
     * @param status
     */
    void updateStatusByIds(Long[] ids, int status);

    /**
     * 重新建立咨询回复搜索记录
     *
     * @param searcherConsult
     * @return
     */
    int rebuild(SearcherConsult searcherConsult);

    /**
     * 刷新用户头像
     *
     * @param memberInfoId
     * @param headPic
     */
    void doRefreshHeadPic(Long memberInfoId, String headPic, String nickName);

    /**
     * 通过id删除咨询回复搜索的记录
     *
     * @param searcherConsultId
     * @return
     */
    int remove(Long searcherConsultId);

    /**
     * 清空所有的咨询回复搜索的数据
     */
    void removeAll();

}
