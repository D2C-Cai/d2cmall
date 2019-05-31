package com.d2c.product.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.product.model.Present;
import com.d2c.product.query.PresentSearcher;

import java.util.List;

public interface PresentService {

    PageResult<Present> findBySearcher(PresentSearcher searcher, PageModel page);

    List<Present> findListBySearcher(PresentSearcher searcher, PageModel page);

    Present findById(Long id);

    int deleteById(Long id, String username);

    int updateStatus(Long id, Integer mark, String username);

    Present insert(Present Present);

    int update(Present Present);

    int updateSort(Long id, Integer sort, String username);

}
