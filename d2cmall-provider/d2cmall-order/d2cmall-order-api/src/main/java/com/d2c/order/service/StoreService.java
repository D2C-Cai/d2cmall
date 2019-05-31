package com.d2c.order.service;

import com.d2c.common.api.dto.HelpDTO;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.order.model.Store;
import com.d2c.order.query.StoreSearcher;

import java.math.BigDecimal;
import java.util.List;

public interface StoreService {

    /**
     * 通过门店id，查找门店实体数据
     *
     * @param storeId
     * @return
     */
    Store findById(Long storeId);

    /**
     * 通过门店编码，得到门店的实体数据
     *
     * @param storeCode
     * @return
     */
    Store findByCode(String storeCode);

    /**
     * 分页查询门店的实体数据
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<Store> findBySearch(StoreSearcher searcher, PageModel page);

    /**
     * 分页查询门店的实体数据,以helpDto实体返回，
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<HelpDTO> findBySearchForHelp(StoreSearcher searcher, PageModel page);

    /**
     * 门店列表的展示
     *
     * @return
     */
    List<Store> findStoreList();

    /**
     * 添加一条门店数据
     *
     * @param store
     * @return
     */
    Store insert(Store store);

    /**
     * 更新门店数据
     *
     * @param store
     * @return
     */
    int update(Store store);

    /**
     * 批量更新门店的状态
     *
     * @param ids
     * @param status
     * @return
     */
    int updateStatus(Long[] ids, int status);

    /**
     * 更新门店的排序权重
     *
     * @param id
     * @param sort
     * @return
     */
    int updateSort(Long id, Integer sort);

    /**
     * 查询所有的城市
     *
     * @return
     */
    List<String> findProvinceList();

    /**
     * 在店铺列表中选最近的
     *
     * @param storeCodeList
     * @param x
     * @param y
     * @return
     */
    Long chooseStore(List<String> storeCodeList, BigDecimal x, BigDecimal y);

}
