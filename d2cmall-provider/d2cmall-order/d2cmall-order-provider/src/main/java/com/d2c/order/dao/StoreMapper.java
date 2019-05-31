package com.d2c.order.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.order.model.Store;
import com.d2c.order.query.StoreSearcher;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface StoreMapper extends SuperMapper<Store> {

    Store findByCode(String code);

    List<Store> findBySearch(@Param("searcher") StoreSearcher searcher, @Param("pager") PageModel pager);

    int countBySearch(@Param("searcher") StoreSearcher searcher);

    List<Store> findStoreList();

    int updateSort(@Param("id") Long id, @Param("sort") int sort);

    int updateStatus(@Param("ids") Long[] ids, @Param("status") int status);

    List<String> findProvinceList();

    Long chooseStore(@Param("storeCodeList") List<String> storeCodeList, @Param("x") BigDecimal x,
                     @Param("y") BigDecimal y);

}
