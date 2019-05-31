package com.d2c.content.service;

import com.d2c.common.core.cache.old.CacheKey;
import com.d2c.common.mq.enums.MqEnum;
import com.d2c.content.dao.NavigationItemMapper;
import com.d2c.content.dao.NavigationMapper;
import com.d2c.content.dto.NavigationDto;
import com.d2c.content.model.Navigation;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("navigationService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class NavigationServiceImpl extends ListServiceImpl<Navigation> implements NavigationService {

    @Autowired
    private NavigationMapper navigationMapper;
    @Autowired
    private NavigationItemMapper navigationItemMapper;

    @Override
    public List<NavigationDto> getNavigationTreeList(String version) {
        List<Navigation> navigations = navigationMapper.findAll(version);
        return recursivNavigationTreeList(navigations, null, null);
    }

    private List<NavigationDto> recursivNavigationTreeList(List<Navigation> allNavigationList, Long p,
                                                           List<NavigationDto> temp) {
        if (temp == null) {
            temp = new ArrayList<>();
        }
        for (Navigation node : allNavigationList) {
            if ((p == null && node.getParentId() == null)
                    || (node != null && node.getParentId() != null && node.getParentId().equals(p))) {
                NavigationDto dto = new NavigationDto();
                BeanUtils.copyProperties(node, dto);
                temp.add(dto);
                recursivNavigationTreeList(allNavigationList, node.getId(), temp);
            }
        }
        return temp;
    }

    @Override
    @CacheEvict(value = "navigation", allEntries = true)
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Navigation insert(Navigation navigation) {
        Navigation result = super.save(navigation);
        result = this.updatePath(result);
        return result;
    }

    @Override
    @CacheEvict(value = "navigation", allEntries = true)
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int update(Navigation navigation) {
        int result = this.updateNotNull(navigation);
        this.upateChildPath(navigation);
        return result;
    }

    private Navigation upateChildPath(Navigation navigation) {
        navigation = this.updatePath(navigation);
        List<Navigation> childrens = navigationMapper.findChildrens(navigation.getId());
        for (int i = 0; childrens != null && i < childrens.size(); i++) {
            Navigation child = childrens.get(i);
            upateChildPath(child);
        }
        return navigation;
    }

    private Navigation updatePath(Navigation navigation) {
        Map<String, Object> map = new HashMap<>();
        StringBuilder builder = new StringBuilder();
        builder.insert(0, navigation.getId());
        generatePath(builder, navigation);
        map.put("path", builder.toString());
        map.put("id", navigation.getId());
        navigationMapper.updatePath(map);
        navigation.setPath(builder.toString());
        return navigation;
    }

    private void generatePath(StringBuilder builder, Navigation navigation) {
        Long parentId = navigation.getParentId();
        while (parentId != null && parentId > 0) {
            navigation = this.navigationMapper.selectByPrimaryKey(parentId);
            builder.insert(0, navigation.getId() + Navigation.PATH_SEPARATOR);
            parentId = navigation.getParentId();
        }
    }

    @Override
    public Navigation findById(Long id) {
        return navigationMapper.selectByPrimaryKey(id);
    }

    @Override
    @CacheEvict(value = "navigation", allEntries = true)
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int delete(Long id, Long parentId) {
        return deleteById(id);
    }

    @Override
    public Navigation findNavigationByCode(String code) {
        return navigationMapper.findByCode(code);
    }

    @Override
    @CacheEvict(value = "navigation", allEntries = true)
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateSequence(int sequence, Long id, Long parentId) {
        return navigationMapper.updateSequence(id, sequence);
    }

    @Override
    @CacheEvict(value = "navigation", allEntries = true)
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateSequence(int[] sequence, Long[] id) {
        for (int i = 0; i < sequence.length; i++) {
            navigationMapper.updateSequence(id[i], sequence[i]);
        }
        return 1;
    }

    @Override
    @Cacheable(value = "navigation", key = "'navigation_tree_'+#version", unless = "#result == null")
    public List<NavigationDto> getIndexNavigation(Integer status, String version) {
        List<Navigation> roots = navigationMapper.findRoots(status, version);
        List<NavigationDto> result = new ArrayList<>();
        for (Navigation root : roots) {
            NavigationDto dto = new NavigationDto();
            BeanUtils.copyProperties(root, dto);
            dto.setChildren(this.findByParentId(root.getId(), status, version));
            dto.setNavigationItems(navigationItemMapper.findByNavId(root.getId()));
            result.add(dto);
        }
        return result;
    }

    @Override
    @Cacheable(value = "navigation", key = "'navigation_'+#parentId+'_'+#version", unless = "#result == null")
    public List<NavigationDto> findByParentId(Long parentId, Integer status, String version) {
        if (parentId.equals(0L)) {
            parentId = null;
        }
        List<Navigation> children = navigationMapper.findByParentId(parentId, status, version);
        List<NavigationDto> result = new ArrayList<>();
        for (Navigation child : children) {
            NavigationDto dto = new NavigationDto();
            BeanUtils.copyProperties(child, dto);
            dto.setNavigationItems(navigationItemMapper.findByNavId(child.getId()));
            result.add(dto);
        }
        return result;
    }

    @Override
    @CacheEvict(value = "navigation", allEntries = true)
    public void refreshCache() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("mem_cache", CacheKey.NAVIGATIONKEY);
        MqEnum.FLUSH_CACHE.send(map);
    }

}
