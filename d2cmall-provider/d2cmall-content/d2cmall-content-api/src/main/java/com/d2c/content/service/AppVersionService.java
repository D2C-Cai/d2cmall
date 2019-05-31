package com.d2c.content.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.content.model.AppVersion;
import com.d2c.content.query.AppVersionSearcher;

import java.util.List;

public interface AppVersionService {

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    AppVersion findById(Long id);

    /**
     * 根据search查询
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<AppVersion> findBySearch(AppVersionSearcher searcher, PageModel page);

    /**
     * 更新强制标识
     *
     * @param id
     * @param force
     * @param lastModifyMan
     * @return
     */
    int updateForce(Long id, int force, String lastModifyMan);

    /**
     * 查询全部
     *
     * @param appTerminal
     * @return
     */
    List<AppVersion> findAllVersion(String appTerminal);

    /**
     * 查找最新版本
     *
     * @param appTerminal
     * @param type
     * @param version
     * @return
     */
    AppVersion findLastVersion(String appTerminal, String type, String version);

    /**
     * 查找相同版本，类型 ，终端的版本(除去已删除的)
     *
     * @param appVersion
     * @return
     */
    AppVersion findSameVersion(AppVersion appVersion);

    /**
     * 新增
     *
     * @param appVersion
     * @return
     */
    AppVersion insert(AppVersion appVersion);

    /**
     * 更新
     *
     * @param appVersion
     * @return
     */
    int update(AppVersion appVersion);

    /**
     * 删除
     *
     * @param id
     * @param lastModifyMan
     * @return
     */
    int deleteById(Long id, String lastModifyMan);

}
