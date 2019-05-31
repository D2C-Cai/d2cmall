// package com.d2c.product.search.dao;
//
// import java.util.Date;
// import java.util.List;
// import java.util.concurrent.ExecutorService;
//
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Autowired;
//
// import com.d2c.common.base.thread.MyExecutors;
// import com.d2c.common.base.utils.DateUt;
// import com.d2c.common.core.base.bucket.PageBucket;
// import com.d2c.common.search.base.ListSearchDao;
// import com.d2c.product.model.Product;
// import com.d2c.product.search.convert.ProductConvert;
// import com.d2c.product.search.model.ProductSearchDO;
// import com.d2c.product.search.repository.ProdoctRepository;
// import com.d2c.product.service.ProductService;
//
//// @Component
// public class ProductSearchDao extends ListSearchDao<ProductSearchDO, Long,
// ProdoctRepository> {
//
// private final static Logger logger =
// LoggerFactory.getLogger(ProductSearchDao.class);
// private final static ExecutorService pools = MyExecutors.newLimit(20);
//
// @Autowired
// private ProductConvert productConvert;
// @Autowired
// private ProductService productService;
//
// /**
// * 重建索引
// */
// public void rebuild(){
// PageBucket<Product> bucket = new PageBucket<Product>() {
// @Override
// public List<Product> nextList(int page, int limit) {
// return productService.findPage(page, limit);
// }
// };
//
// Date start = new Date();
// logger.info("开始商品索引重建..." + bucket.getCount());
// while(bucket.hasNext()){
// Product bean = bucket.next();
// int count = bucket.getCount();
//
// pools.execute(new Runnable() {
// @Override
// public void run() {
// rebuildImpl(bean);
// }
// });
//
// if (count % 100 == 0) {
// logger.info(DateUt.duration(start, new Date()) + " 商品索引重建..." + count );
// }
// }
//
// }
//
// private void rebuildImpl(Product bean) {
// if (bean.getMark() == null || bean.getMark() < 0) {
// this.deleteById(bean.getId());
// }else{
// this.save(bean);
// }
// }
//
// @Override
// @SuppressWarnings("unchecked")
// public ProductConvert getConvert() {
// return productConvert;
// }
//
// }
