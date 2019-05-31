package com.d2c.order.service;

import com.d2c.common.api.dto.HelpDTO;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.order.dao.StoreMapper;
import com.d2c.order.model.Store;
import com.d2c.order.query.StoreSearcher;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service("storeService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class StoreServiceImpl extends ListServiceImpl<Store> implements StoreService {

    @Autowired
    private StoreMapper storeMapper;

    @Override
    public Store findById(Long id) {
        return this.findOneById(id);
    }

    @Override
    public Store findByCode(String code) {
        return storeMapper.findByCode(code);
    }

    @Override
    public PageResult<Store> findBySearch(StoreSearcher searcher, PageModel page) {
        PageResult<Store> pager = new PageResult<>(page);
        int totalCount = storeMapper.countBySearch(searcher);
        List<Store> list = new ArrayList<>();
        if (totalCount > 0) {
            list = storeMapper.findBySearch(searcher, page);
        }
        pager.setTotalCount(totalCount);
        pager.setList(list);
        return pager;
    }

    @Override
    public PageResult<HelpDTO> findBySearchForHelp(StoreSearcher searcher, PageModel page) {
        PageResult<HelpDTO> pager = new PageResult<>(page);
        int totalCount = storeMapper.countBySearch(searcher);
        List<Store> list = new ArrayList<>();
        List<HelpDTO> helpDtos = new ArrayList<>();
        if (totalCount > 0) {
            list = storeMapper.findBySearch(searcher, page);
            for (Store store : list) {
                HelpDTO helpDto = new HelpDTO();
                BeanUtils.copyProperties(store, helpDto);
                helpDtos.add(helpDto);
            }
        }
        pager.setTotalCount(totalCount);
        pager.setList(helpDtos);
        return pager;
    }

    @Override
    @Cacheable(value = "store", key = "'store_list'", unless = "#result == null")
    public List<Store> findStoreList() {
        return storeMapper.findStoreList();
    }

    @Override
    @CacheEvict(value = "store", allEntries = true)
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Store insert(Store store) {
        return this.save(store);
    }

    @Override
    @CacheEvict(value = "store", allEntries = true)
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int update(Store store) {
        return this.updateNotNull(store);
    }

    @Override
    @CacheEvict(value = "store", allEntries = true)
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateStatus(Long[] ids, int status) {
        return storeMapper.updateStatus(ids, status);
    }

    @Override
    @CacheEvict(value = "store", allEntries = true)
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateSort(Long id, Integer sort) {
        return storeMapper.updateSort(id, sort);
    }

    @Override
    @Cacheable(value = "store", key = "'store_province_list'", unless = "#result == null")
    public List<String> findProvinceList() {
        return storeMapper.findProvinceList();
    }

    @Override
    public Long chooseStore(List<String> storeCodeList, BigDecimal x, BigDecimal y) {
        return storeMapper.chooseStore(storeCodeList, x, y);
    }

}
