// package com.d2c.product.search.convert;
//
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Autowired;
//
// import com.alibaba.fastjson.JSONArray;
// import com.alibaba.fastjson.JSONObject;
// import com.d2c.common.base.utils.BeanUt;
// import com.d2c.common.search.convert.BaseSearchConvert;
// import com.d2c.product.dao.ProductSummaryMapper;
// import com.d2c.product.model.Brand;
// import com.d2c.product.model.Product;
// import com.d2c.product.model.ProductDetail;
// import com.d2c.product.model.ProductSummary;
// import com.d2c.product.model.Series;
// import com.d2c.product.search.model.ProductSearchDO;
// import com.d2c.product.service.BrandService;
// import com.d2c.product.service.ProductDetailService;
// import com.d2c.product.service.SeriesService;
//
//// @Component
// public class ProductConvert extends BaseSearchConvert<ProductSearchDO,
// Product> {
//
// private final Logger logger = LoggerFactory.getLogger(this.getClass());
//
// @Autowired
// private ProductSummaryMapper productSummaryMapper;
// @Autowired
// private ProductDetailService productDetailService;
//
// @Autowired
// private SeriesService seriesService;
// @Autowired
// private BrandService brandService;
//
// /**
// * 获取索引模型
// */
// @Override
// public ProductSearchDO convertTo(Product product) {
// ProductSearchDO bean = new ProductSearchDO();
//
// // product mapper
// setDetail(product, bean);
// setSummary(product, bean);
//
// // shop provider
// setBrand(product, bean);
// setSeries(product, bean);
//
// BeanUt.copyProperties(bean, product);
//
// // change
// bean.setProductId(product.getId());
// bean.setRecomScore(product.getRecom());
// bean.setStore((product.getSyncStore() + product.getPopStore() > 0) ? 1 : 0);
// bean.setSpot(product.getMark() > 0 ? 1 : 0);
//
// setCategoryName(product, bean);
// return bean;
// }
//
// private void setDetail(Product product, ProductSearchDO bean) {
// ProductDetail detail = productDetailService.findByProductId(product.getId());
// bean.setRecommendation(detail.getRecommendation());
// bean.setIntroduction(detail.getIntroduction());
// }
//
// private void setSummary(Product product, ProductSearchDO bean) {
// ProductSummary summary =
// productSummaryMapper.findByProductId(product.getId());
// if (summary != null) {
// bean.setSales(summary.getSales());
// bean.setComments(summary.getComments());
// bean.setConsults(summary.getConsults());
// bean.setRecentlySales(summary.getRecentlySales());
// }
// }
//
// private void setSeries(Product product, ProductSearchDO bean) {
// Series series = seriesService.findById(product.getSeriesId());
// if (series != null) {
// bean.setSeriesId(product.getSeriesId());
// if (series.getUpDateTime() == null) {
// bean.setSeriesUpDate(product.getUpMarketDate());
// } else {
// bean.setSeriesUpDate(series.getUpDateTime());
// }
// }
// }
//
// private void setBrand(Product product, ProductSearchDO bean) {
// Brand brand = brandService.findById(product.getDesignerId());
// if (brand != null) {
// bean.setBrand(brand.getName());
// bean.setBrandStyle(brand.getStyle());
// bean.setDesigner(brand.getDesigners());
// bean.setDesignerId(product.getDesignerId());
// }
// }
//
// private void setCategoryName(Product product, ProductSearchDO bean) {
// JSONObject category = new JSONObject();
// if (product.getProductCategory() != null) {
// try{
// JSONArray array = JSONArray.parseArray(product.getProductCategory());
// category = (JSONObject) array.get(array.size() - 1);
// }catch(Exception e){
// logger.error("分类productCategory解析json失败..."+ product.getProductCategory(),
// e);
// }
// }
// bean.setCategoryName(category == null ? "" : category.getString("name"));
// }
//
//
// }
