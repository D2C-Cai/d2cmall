package com.d2c.member.service;

import com.d2c.member.dto.ResourceDto;
import com.d2c.member.model.Resource;
import com.d2c.member.query.ResourceSearcher;

import java.util.List;

/**
 * 资源信息（sys_resource）
 */
public interface ResourceService {

    /**
     * 根据id获取相应的资源信息
     *
     * @param id
     * @return
     */
    Resource findById(Long id);

    /**
     * 根据value值返回相应资源信息，并且其type为url类型。
     *
     * @param url 资源的值
     * @return
     */
    Resource findByValue(String url);

    /**
     * 根据用户id获取该用户具有的资源信息
     *
     * @param roles 角色的值
     * @return
     */
    List<Resource> findByRoles(List<String> roles);

    /**
     * 根据权限id获取该权限下所有的资源id
     *
     * @param roleId 权限id
     * @return
     */
    List<Long> findIdsByRoleId(Long roleId);

    /**
     * 根据ResourceSearcher对象内包含的过滤条件，获取相应的资源信息，以列表形式返回。
     *
     * @param searcher 过滤器
     * @return
     */
    List<ResourceDto> findBySearch(ResourceSearcher searcher);

    /**
     * 插入资源信息
     *
     * @param resource
     * @return
     */
    Resource insert(Resource resource);

    /**
     * 更新资源信息
     *
     * @param oldResource
     * @return
     */
    int update(Resource oldResource);

    /**
     * 根据id删除资源信息
     *
     * @param id
     * @return
     */
    int deleteById(Long id, String value);

    /**
     * 根据权限id解绑资源
     *
     * @param roleId 权限id
     * @return
     */
    int deleteAllRoleByResouceId(Long resourceId);

    /**
     * 排序
     *
     * @param id
     * @param sequence
     * @return
     */
    int updateSequence(Long id, Integer sequence);

}
