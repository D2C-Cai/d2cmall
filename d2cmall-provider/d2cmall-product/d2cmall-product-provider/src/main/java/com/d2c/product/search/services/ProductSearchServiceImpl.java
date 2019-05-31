package com.d2c.product.search.services;
// package com.d2c.product.search.service;
//
// import java.util.Date;
// import java.util.List;
//
// import org.elasticsearch.common.xcontent.XContentBuilder;
// import org.elasticsearch.common.xcontent.XContentFactory;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Autowired;
//
// import com.d2c.common.api.page.PageBean;
// import com.d2c.common.api.page.PageModel;
// import com.d2c.common.api.page.PageResult;
// import com.d2c.common.api.page.Pager;
// import com.d2c.common.api.page.base.PageSort;
// import com.d2c.common.search.base.SearchTemplate;
// import com.d2c.common.search.base.service.ListSearchServiceImpl;
// import com.d2c.product.query.search.ProductSearchQuery;
// import com.d2c.product.search.dao.ProductSearchDao;
// import com.d2c.product.search.enums.ProductOrderEnum;
// import com.d2c.product.search.model.ProductSearchDO;
// import com.d2c.product.search.repository.ProdoctRepository;
//
//// @Service(protocol = "dubbo")
// public class ProductSearchServiceImpl extends
// ListSearchServiceImpl<ProductSearchDO, Long, ProdoctRepository>
// implements ProductSearchService {
//
// protected Logger logger = LoggerFactory.getLogger(this.getClass());
//
// @Autowired
// private ProductSearchDao productSearchDao;
// @Autowired
// protected SearchTemplate searchTemplate;
//
// /**
// * 重建商品索引
// */
// @Override
// public void rebuild() {
// productSearchDao.rebuild();
// }
//
// /**
// * 通用商品搜索
// */
// @Override
// public PageResult<ProductSearchDO> search(ProductSearchQuery query, Pager
// page) {
// //查询可搜索并上架商品
// query.setMark(1);
// query.setSearch(1);
//
// if (page != null) {
// String order = null;
// if (query != null) {
// order = query.getOrder();
// }
// page.setPageSort(new PageSort(ProductOrderEnum.getSort(order)));
// }
// return this.findByPage(query, page);
// }
//
// /**
// * 推荐排序前n条
// */
// @Override
// public List<ProductSearchDO> findTopRecom(int limit) {
// return search(null, new PageBean(limit)).getList();
// }
//
// @Override
// public List<ProductSearchDO> findProductByTopCategory(Long topId, Date
// afterModifyDate, int page, int limit) {
// ProductSearchQuery query = new ProductSearchQuery();
// query.setTopId(topId);
// query.setAfterModifyDate(afterModifyDate);
// PageResult<ProductSearchDO> pager = search(query, new PageModel(page,
// limit));
// return pager.getList();
// }
//
// @Override
// public List<ProductSearchDO> findProductTargets(Long topId, int page, int
// limit) {
// ProductSearchQuery query = new ProductSearchQuery();
// query.setTopId(topId);
// query.setStore(1);
// PageResult<ProductSearchDO> pager = search(query, new PageModel(page,
// limit));
// return pager.getList();
// }
//
// @Override
// public boolean updateRecom(Long id, Double recomScore, Integer operRecom) {
// try {
// XContentBuilder builder =
// XContentFactory.jsonBuilder().startObject().field("id", id);
// if (recomScore != null) {
// builder.field("recomScore", recomScore);
// }
// if (operRecom != null) {
// builder.field("operRecom", operRecom);
// }
// builder.endObject();
// searchTemplate.getClient().prepareUpdate("d2c_index", "product",
// id.toString()).setDoc(builder).execute().actionGet();
// return true;
// } catch (Exception e) {
// logger.error("updateRecom error:", e);
// }
// return false;
//
// // return productSearchDao.update(id, "recomScore", recomScore, "operRecom",
// operRecom);
// }
//
// }
