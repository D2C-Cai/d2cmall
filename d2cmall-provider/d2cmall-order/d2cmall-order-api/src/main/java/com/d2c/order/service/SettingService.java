package com.d2c.order.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.order.model.Setting;
import com.d2c.order.query.SettingSearcher;

/**
 * 系统参数设置（sys_setting，唯一键（memberId+code+type+memberType））
 */
public interface SettingService {

    /**
     * 根据id获取参数信息
     *
     * @param id 主键id
     * @return
     */
    Setting findById(Long id);

    /**
     * 根据参数编号获取系统参数设置
     *
     * @param code 参数编号
     * @return
     */
    Setting findByCode(String code);

    /**
     * 根据SettingSearcher内的过滤条件，获取相应的系统参数， 采用分页方式，以PageResult对象返回。
     *
     * @param page     分页
     * @param searcher 过滤器
     * @return
     */
    PageResult<Setting> findBySearch(PageModel page, SettingSearcher searcher);

    /**
     * 保存系统参数
     *
     * @param setting
     * @return
     * @throws Exception
     */
    Setting insert(Setting setting) throws Exception;

    /**
     * 根据id更新值
     *
     * @param value 值
     * @param id    主键id
     * @return
     */
    int updateValueById(String value, Long id, String code);

    /**
     * 根据id更新状态
     *
     * @param id     主键id
     * @param status 状态（ 0 未启用，1启用）
     * @return
     */
    int updateStatus(Long id, int status, String code);

    /**
     * 根据id删除参数
     *
     * @param id 主键id
     * @return
     * @throws Exception
     */
    int delete(Long id, String code) throws Exception;

    /**
     * 更新系统参数
     *
     * @param setting
     * @return
     * @throws Exception
     */
    int update(Setting setting) throws Exception;

}
