package com.d2c.content.service;

import com.d2c.content.dto.NavigationDto;
import com.d2c.content.model.Navigation;

import java.util.List;

/**
 * 提供导航条的相关数据库操作
 */
public interface NavigationService {

    /**
     * 获取所有正在使用的导航，并组织成树形结构。
     *
     * @return List<E>
     */
    List<NavigationDto> getNavigationTreeList(String version);

    /**
     * 根据code获取对应的导航信息。
     *
     * @param code
     * @return Navigation
     */
    Navigation findNavigationByCode(String code);

    /**
     * 以Navigation对象作为参数，将导航信息插入Navigation表中。
     *
     * @param navigation
     * @return Navigation
     */
    Navigation insert(Navigation navigation);

    /**
     * 以Navigation对象作为参数，将导航信息更新至Navigation表中。
     *
     * @param navigation
     * @return int
     */
    int update(Navigation navigation);

    /**
     * 根据id删除对应导航信息。
     *
     * @param id
     * @return int
     */
    int delete(Long id, Long parentId);

    /**
     * 根据id及对应的序号，更新排序号
     *
     * @param sequence 排序号
     * @param id
     * @return int
     */
    int updateSequence(int sequence, Long id, Long parentId);

    /**
     * 根据id数组及对应的序号数组，进行批量更新排序。
     *
     * @param sequenceArray 排序数组
     * @param idArray       id数组
     * @return int
     */
    int updateSequence(int[] sequenceArray, Long[] idArray);

    /**
     * 根据id获取对应的导航信息。
     *
     * @param navId
     * @return Navigation
     */
    Navigation findById(Long navId);

    /**
     * 根据状态获取所有顶层导航。优先从Cache中获取数据。
     *
     * @param status
     * @return List<E>
     */
    List<NavigationDto> getIndexNavigation(Integer status, String version);

    /**
     * 根据父id以及状态获取下级导航。优先从Cache中获取数据。
     *
     * @param parentId 父节点id
     * @param status   状态
     * @return List<E>
     */
    List<NavigationDto> findByParentId(Long parentId, Integer status, String version);

    void refreshCache();

}
