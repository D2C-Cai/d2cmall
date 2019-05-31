// package com.d2c.product.search.service;
//
// import java.util.Date;
// import java.util.List;
//
// import com.d2c.common.api.page.PageResult;
// import com.d2c.common.api.page.Pager;
// import com.d2c.common.search.base.service.ListSearchService;
// import com.d2c.product.query.search.ProductSearchQuery;
// import com.d2c.product.search.model.ProductSearchDO;
//
// public interface ProductSearchService extends
// ListSearchService<ProductSearchDO, Long> {
//
// public void rebuild();
//
// public PageResult<ProductSearchDO> search(ProductSearchQuery query, Pager
// page);
//
// public List<ProductSearchDO> findTopRecom(int limit);
//
// public List<ProductSearchDO> findProductByTopCategory(Long topId, Date
// afterModifyDate, int page, int limit);
//
// public List<ProductSearchDO> findProductTargets(Long topId, int page, int
// limit);
//
// public boolean updateRecom(Long id, Double recomScore, Integer operRecom);
//
// }
