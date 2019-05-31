package com.d2c.content.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.content.model.AdResource;
import com.d2c.content.query.AdResourceSearcher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdResourceService {

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    AdResource findById(Long id);

    /**
     * 根据appChannel查询
     *
     * @param appChannel
     * @return
     */
    List<AdResource> findByAppChannel(String appChannel);

    /**
     * 根据appChannel和type查询
     *
     * @param appChannel
     * @param type
     * @return
     */
    AdResource findByAppChannelAndType(String appChannel, String type);

    /**
     * 根据searcher查询
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<AdResource> findBySearcher(AdResourceSearcher searcher, PageModel page);

    /**
     * 新增
     *
     * @param adResource
     * @return
     */
    AdResource insert(AdResource adResource);

    /**
     * 更新
     *
     * @param adResource
     * @return
     */
    int update(AdResource adResource);

    /**
     * 更新状态
     *
     * @param id
     * @param status
     * @return
     */
    int updateStatus(Long id, Integer status);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    int delete(Long id);

    /**
     * 后台查询是否存在
     *
     * @param appChannel
     * @param type
     * @return
     */
    AdResource findByAppChannelAndTypeForBack(@Param("appChannel") String appChannel, @Param("type") String type);

}
