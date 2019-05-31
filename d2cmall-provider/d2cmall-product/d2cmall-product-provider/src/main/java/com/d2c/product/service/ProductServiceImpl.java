package com.d2c.product.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.common.mq.enums.MqEnum;
import com.d2c.logger.model.ProductLog;
import com.d2c.logger.model.ProductLog.ProductLogType;
import com.d2c.logger.service.ProductLogService;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.product.dao.ProductMapper;
import com.d2c.product.dto.ProductDto;
import com.d2c.product.dto.ProductSkuDto;
import com.d2c.product.model.*;
import com.d2c.product.model.Product.SaleCategory;
import com.d2c.product.model.ProductRelation.RelationType;
import com.d2c.product.query.ProductSearcher;
import com.d2c.product.query.ProductSkuStockSearcher;
import com.d2c.product.query.RelationSearcher;
import com.d2c.product.search.model.SearcherProduct;
import com.d2c.product.search.service.ProductSearcherService;
import com.d2c.product.support.SkuCodeBean;
import com.d2c.product.third.productai.ProductAIClinet;
import com.d2c.product.util.ProductCompareUtil;
import com.d2c.product.util.ProductCompareUtil.ProductCompareType;
import com.d2c.util.date.DateUtil;
import com.d2c.util.string.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("productService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class ProductServiceImpl extends ListServiceImpl<Product> implements ProductService {

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductSummaryService productSummaryService;
    @Autowired
    private ProductDetailService productDetailService;
    @Autowired
    private ProductSkuService productSkuService;
    @Autowired
    private ProductSkuStockSummaryService ProductSkuStockSummaryService;
    @Autowired
    private ProductModuleSearchService productModuleSearchService;
    @Reference
    private ProductSearcherService productSearcherService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private ProductRelationService productRelationService;
    @Autowired
    private ProductLogService productLogService;
    @Autowired
    private ProductCombService productCombService;
    @Autowired
    private PromotionService promotionService;
    @Autowired
    private FlashPromotionService flashPromotionService;
    @Autowired
    private CollagePromotionService collagePromotionService;
    @Autowired
    private ProductAIClinet productAIClinet;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Map<Long, Product> findByIds(Long[] ids) {
        List<Product> list = productMapper.findByIds(ids);
        Map<Long, Product> result = new HashMap<>();
        for (Product p : list) {
            result.put(p.getId(), p);
        }
        return result;
    }

    @Override
    public PageResult<ProductDto> findBySearch(ProductSearcher productSearcher, PageModel page) {
        PageResult<ProductDto> pager = new PageResult<>(page);
        int totalCount = productMapper.countBySearch(productSearcher);
        if (totalCount > 0) {
            List<Product> productList = productMapper.findBySearch(productSearcher, page);
            List<ProductDto> dtos = new ArrayList<>();
            for (int i = 0; i < productList.size(); i++) {
                ProductDto dto = new ProductDto();
                BeanUtils.copyProperties(productList.get(i), dto);
                dtos.add(dto);
            }
            pager.setTotalCount(totalCount);
            pager.setList(dtos);
        }
        return pager;
    }

    @Override
    @Cacheable(value = "product", key = "'product_'+#id", unless = "#result == null")
    public Product findById(Long id) {
        return super.findOneById(id);
    }

    @Override
    public ProductDto findDetailById(Long id) {
        Product product = this.findById(id);
        if (product == null) {
            return null;
        }
        ProductDetail productDetail = productDetailService.findByProductId(id);
        if (productDetail == null)
            return null;
        ProductDto dto = new ProductDto(product, productDetail);
        return dto;
    }

    @Override
    @CacheEvict(value = "product", key = "'product_'+#id")
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int updateIndex(Long id, Integer status, String operator) {
        int result = productMapper.updateIndex(id, status);
        if (result > 0) {
            if (status == 1) {
                productModuleSearchService.rebuild(id);
            } else {
                productSearcherService.updateIndex(id, 0);
            }
            // 添加操作日志
            JSONObject info = new JSONObject();
            info.put("操作", "商品" + (status.intValue() == 1 ? "设置允许搜索" : "取消允许搜索"));
            productLogService.insert(new ProductLog(operator, info.toJSONString(),
                    status.intValue() == 1 ? ProductLogType.Up : ProductLogType.Down, id, null));
        }
        return result;
    }

    @Override
    @CacheEvict(value = "product", key = "'product_'+#id")
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int updateMark(Long id, Integer status, String adminName) {
        int skuCount = this.productSkuService.countByProductId(id);
        if (skuCount <= 0) {
            throw new BusinessException("sku数量为空，上架不成功");
        }
        Date markDate = new Date();
        int result = productMapper.updateMark(id, status, markDate, adminName);
        if (result > 0) {
            productSkuService.updateStatusByProduct(id, status, adminName);
            productModuleSearchService.updateMark(id, status, markDate);
            RelationSearcher searcher = new RelationSearcher();
            searcher.setRelationId(id);
            PageModel page = new PageModel(1, 500);
            PageResult<ProductRelation> pager = new PageResult<>(page);
            do {
                pager = productRelationService.findRelations(searcher, page);
                for (ProductRelation relation : pager.getList()) {
                    productRelationService.doClearRelationBySourceId(relation.getSourceId());
                    // 清组合商品
                    if ("PRODUCTCOMB".equals(relation.getType())) {
                        productCombService.doClearBySourceId(relation.getSourceId());
                    }
                }
                pager.setPageNumber(pager.getPageNumber() + 1);
                page.setP(page.getP() + 1);
            } while (pager.isNext());
            // 添加操作日志
            JSONObject info = new JSONObject();
            info.put("操作", "商品" + (status.intValue() == 1 ? "上架" : "下架"));
            productLogService.insert(new ProductLog(adminName, info.toJSONString(),
                    status.intValue() == 1 ? ProductLogType.Up : ProductLogType.Down, id, null));
            // 上传图片到productAI
            if (status > 0) {
                this.uploadPic(id);
            }
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int uploadPic(Long id) {
        Product product = this.findById(id);
        try {
            return productAIClinet.uploadPic(product);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return 0;
    }

    @Override
    @CacheEvict(value = "product", key = "'product_'+#id")
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int updateSort(Long id, Integer sort, String operator) {
        int result = productMapper.updateSort(id, sort);
        if (result > 0) {
            SearcherProduct product = new SearcherProduct();
            product.setId(id);
            product.setSort(sort);
            productModuleSearchService.update(product);
            JSONObject info = new JSONObject();
            info.put("操作", "更新排序，优先级：" + sort);
            productLogService.insert(new ProductLog(operator, info.toJSONString(), ProductLogType.Sort, id, null));
        }
        return result;
    }

    @Override
    @CacheEvict(value = "product", key = "'product_'+#id")
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int updatePromotionSort(Long id, Integer gpSort, Integer opSort, Integer fpSort) {
        int result = productMapper.updatePromotionSort(id, gpSort, opSort, fpSort);
        if (result > 0) {
            SearcherProduct product = new SearcherProduct();
            product.setId(id);
            product.setGpSort(gpSort);
            product.setOpSort(opSort);
            product.setFpSort(fpSort);
            productModuleSearchService.update(product);
        }
        return result;
    }

    @Override
    @CacheEvict(value = "product", key = "'product_'+#id")
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int updateTopical(Long id, int top, String operator) {
        int result = productMapper.updateTopical(id, top);
        if (result > 0) {
            SearcherProduct product = new SearcherProduct();
            product.setId(id);
            product.setTopical(top);
            productModuleSearchService.update(product);
            // 添加操作日志
            JSONObject info = new JSONObject();
            info.put("操作", top == 1 ? "商品置顶" : "取消商品置顶");
            productLogService.insert(new ProductLog(operator, info.toJSONString(), ProductLogType.Topical, id, null));
        }
        return result;
    }

    @Override
    @CacheEvict(value = "product", key = "'product_'+#id")
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int updateAd(Long id, int ad, String operator) {
        int result = productMapper.updateAd(id, ad);
        if (result > 0) {
            JSONObject info = new JSONObject();
            info.put("操作", ad == 1 ? "设置广告商品" : "取消广告商品");
            productLogService.insert(new ProductLog(operator, info.toJSONString(), ProductLogType.Ad, id, null));
        }
        return result;
    }

    @Override
    @CacheEvict(value = "product", key = "'product_'+#id")
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int updateTags(Long id, String tags) {
        int result = productMapper.updateTags(id, tags);
        if (result > 0) {
            productModuleSearchService.updateTags(id, tags);
        }
        return result;
    }

    @Override
    @CacheEvict(value = "product", key = "'product_'+#id")
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int updateCod(Long id, int cod, String operator) {
        Product product = this.findById(id);
        if (product.getSaleCategory().equals(SaleCategory.POPPRODUCT)) {
            throw new BusinessException("第三方商品，不支持货到付款的修改");
        }
        int result = productMapper.updateCod(id, cod);
        if (result > 0) {
            JSONObject info = new JSONObject();
            info.put("操作", cod == 1 ? "设置允许货到付款" : "取消允许货到付款");
            productLogService.insert(new ProductLog(operator, info.toJSONString(), ProductLogType.Cod, id, null));
        }
        return result;
    }

    @Override
    @CacheEvict(value = "product", key = "'product_'+#id")
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int updateCart(Long id, int cart, String operator) {
        int result = productMapper.updateCart(id, cart);
        if (result > 0) {
            JSONObject info = new JSONObject();
            info.put("操作", cart == 1 ? "设置允许加入购物车" : "取消允许加入购物车");
            productLogService.insert(new ProductLog(operator, info.toJSONString(), ProductLogType.Cart, id, null));
        }
        return result;
    }

    @Override
    @CacheEvict(value = "product", key = "'product_'+#id")
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int updateSubscribe(Long id, int subscribe, String operator) {
        Product product = this.findById(id);
        if (product.getSaleCategory().equals(SaleCategory.POPPRODUCT) && subscribe == 1) {
            throw new BusinessException("第三方商品，不支持门店试衣");
        }
        Brand brand = this.brandService.findById(product.getDesignerId());
        if (brand != null && brand.getSubscribe() != null && brand.getSubscribe().intValue() == 0) {
            throw new BusinessException(brand.getName() + "不支持门店试衣");
        }
        int result = productMapper.updateSubscribe(id, subscribe);
        if (result > 0) {
            productModuleSearchService.updateSubscribe(id, subscribe);
            JSONObject info = new JSONObject();
            info.put("操作", subscribe == 1 ? "允许门店预约" : "取消允许门店预约");
            productLogService.insert(new ProductLog(operator, info.toJSONString(), ProductLogType.Subscribe, id, null));
        }
        return result;
    }

    @Override
    @CacheEvict(value = "product", key = "'product_'+#id")
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int updateAfter(Long id, int after, String operator) {
        int result = productMapper.updateAfter(id, after);
        if (result > 0) {
            productModuleSearchService.updateAfter(id, after);
            JSONObject info = new JSONObject();
            info.put("操作", after == 1 ? "允许售后" : "取消允许售后");
            productLogService.insert(new ProductLog(operator, info.toJSONString(), ProductLogType.After, id, null));
        }
        return result;
    }

    @Override
    @CacheEvict(value = "product", key = "'product_'+#id")
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int updateCoupon(Long id, Integer coupon, String operator) {
        Integer success = productMapper.updateCoupon(id, coupon);
        if (success > 0) {
            JSONObject info = new JSONObject();
            info.put("操作", coupon == 1 ? "允许使用优惠券" : "不允许使用优惠券");
            productLogService.insert(new ProductLog(operator, info.toJSONString(), ProductLogType.COUPON, id, null));
        }
        return success;
    }

    @Override
    @CacheEvict(value = "product", key = "'product_'+#id")
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int updateRemark(Long id, String remark, String operator) {
        int result = productMapper.updateRemark(id, remark);
        if (result > 0) {
            JSONObject info = new JSONObject();
            info.put("备注", remark);
            productLogService.insert(new ProductLog(operator, info.toJSONString(), ProductLogType.Remark, id, null));
        }
        return result;
    }

    @Override
    @CacheEvict(value = "product", key = "'product_'+#id")
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int updateMarketDate(Long id, int mark, Date marketDate, String operator) {
        int result = 0;
        Product oldProduct = this.findById(id);
        if (mark == 0) {
            result = productMapper.updateDownMarketDate(id, marketDate);
        } else if (mark == 1) {
            result = productMapper.updateUpMarketDate(id, marketDate);
        }
        if (result > 0 && mark == 1) {
            SearcherProduct product = new SearcherProduct();
            product.setId(id);
            product.setUpMarketDate(marketDate);
            productModuleSearchService.update(product);
            JSONObject info = new JSONObject();
            info.put("操作", "修改上架时间");
            info.put("修改前", new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(oldProduct.getUpMarketDate()));
            info.put("修改后", new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(marketDate));
            productLogService.insert(new ProductLog(operator, info.toJSONString(), ProductLogType.Update, id, null));
        }
        return result;
    }

    @Override
    @CacheEvict(value = "product", key = "'product_'+#id")
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int delete(Long id, int mark, String operator) {
        int result = productMapper.delete(id, mark);
        if (result > 0) {
            List<ProductSku> skus = productSkuService.findByProductId(id);
            if (skus != null) {
                for (int i = 0; i < skus.size(); i++) {
                    productSkuService.delete(skus.get(i).getId(), operator);
                }
            }
            this.updateSalesPropertyBySku(skus, id);
            productModuleSearchService.remove(id);
            // 添加日志
            JSONObject info = new JSONObject();
            info.put("操作", "商品归档");
            productLogService.insert(new ProductLog(operator, info.toJSONString(), ProductLogType.Delete, id, null));
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int updateSyncTimestamp(String syncStamp) {
        int result = productMapper.updateSyncTimestamp(syncStamp);
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doSumProductStore() {
        int result = productMapper.doSumProductStore();
        return result;
    }

    @Override
    public PageResult<Product> findSyncProductStore(String syncStamp, PageModel page) {
        PageResult<Product> pager = new PageResult<>(page);
        ProductSearcher productSearcher = new ProductSearcher();
        productSearcher.setMark(1);
        productSearcher.setSyncStamp(syncStamp);
        int totalCount = productMapper.countBySearch(productSearcher);
        if (totalCount > 0) {
            List<Product> list = productMapper.findSyncProductStore(syncStamp, page);
            pager.setTotalCount(totalCount);
            pager.setList(list);
        }
        return pager;
    }

    @Override
    public Map<String, Object> findProductStoreBySku(Long productId) {
        return productMapper.findProductStoreBySku(productId);
    }

    @Override
    @Cacheable(value = "product_store", key = "'product_flash_store_'+#productId", unless = "#result == null")
    public Map<String, Integer> findFlashStore(Long productId) {
        List<ProductSku> skuList = productSkuService.findByProductId(productId);
        int flashStore = 0;
        int flashSellStore = 0;
        for (ProductSku sku : skuList) {
            flashStore += (sku.getAvailableFlashStore() + sku.getFlashSellStore());
            flashSellStore += sku.getFlashSellStore();
        }
        Map<String, Integer> result = new HashMap<>();
        result.put("flashStock", flashStore);
        result.put("flashSellStock", flashSellStore);
        return result;
    }

    @Override
    @CacheEvict(value = "product", key = "'product_'+#productId")
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int updateSalesPropertyBySku(List<ProductSku> skuSet, Long productId) {
        Map<String, JSONObject> sp1Map = new LinkedHashMap<String, JSONObject>();
        Map<String, JSONObject> sp2Map = new LinkedHashMap<String, JSONObject>();
        for (ProductSku sku : skuSet) {
            if (StringUtil.isNotBlank(sku.getSp1())) {
                JSONObject sp1 = JSONObject.parseObject(sku.getSp1());
                if (sp1.getString("value") == null) {
                    throw new BusinessException("规格的值不能为空！");
                }
                sp1Map.put(sp1.getString("value"), sp1);
            }
            if (StringUtil.isNotBlank(sku.getSp2())) {
                JSONObject sp2 = JSONObject.parseObject(sku.getSp2());
                if (sp2.getString("value") == null) {
                    throw new BusinessException("规格的值不能为空！");
                }
                sp2Map.put(sp2.getString("value"), sp2);
            }
        }
        JSONArray sp1Array = new JSONArray();
        JSONArray sp2Array = new JSONArray();
        if (!sp1Map.isEmpty()) {
            for (JSONObject item : sp1Map.values()) {
                sp1Array.add(item);
            }
        }
        if (!sp2Map.isEmpty()) {
            for (JSONObject item : sp2Map.values()) {
                sp2Array.add(item);
            }
        }
        JSONObject salesProperty = new JSONObject();
        salesProperty.put("sp1", sp1Array);
        salesProperty.put("sp2", sp2Array);
        int result = productMapper.updateSalesPropertyBySku(productId, salesProperty.toString());
        return result;
    }

    @Override
    @CacheEvict(value = "product", key = "'product_'+#productId")
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int updatePriceBySku(Long productId) {
        List<ProductSku> skuSet = this.productSkuService.findByProductId(productId);
        Product product = new Product();
        product.setId(productId);
        this.processPrice(product, skuSet);
        int success = productMapper.updatePrice(product);
        if (success > 0) {
            productModuleSearchService.rebuild(productId);
        }
        return success;
    }

    @Override
    public SkuCodeBean getSKUCodeParser(Long sp1GroupId, Long sp2GroupId, String code) {
        if (code == null || StringUtils.isEmpty(code))
            return null;
        SkuCodeBean scBean = null;
        if (code.length() == 23 || code.length() == 17 || code.length() == 24) {
            scBean = productSkuService.processRegular(sp1GroupId, sp2GroupId, code);
        } else if (code.length() == 16) {
            scBean = productSkuService.processStore(sp1GroupId, sp2GroupId, code);
        } else if (code.length() == 18 || code.length() == 19) {
            scBean = productSkuService.processthird(sp1GroupId, sp2GroupId, code);
        }
        if (scBean == null || scBean.getSkuSalesProperties() == null) {
            return null;
        } else {
            return scBean;
        }
    }

    private List<ProductSku> copy2ProductSkuList(List<ProductSkuDto> skuSet) {
        List<ProductSku> skuList = new ArrayList<>();
        for (int i = 0; i < skuSet.size(); i++) {
            ProductSkuDto dto = skuSet.get(i);
            ProductSku sku = new ProductSku();
            BeanUtils.copyProperties(dto, sku);
            skuList.add(sku);
        }
        return skuList;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public Product insert(ProductDto productDto, String operator) {
        // 获取数据
        productDto.setName(productDto.getName() == null ? null
                : productDto.getName().replaceAll("\"", "&quot;").replaceAll("'", "&apos;"));
        Product product = new Product();
        BeanUtils.copyProperties(productDto, product);
        ProductDetail productDetail = new ProductDetail();
        BeanUtils.copyProperties(productDto, productDetail);
        ProductSummary productSummary = new ProductSummary();
        BeanUtils.copyProperties(productDto, productSummary);
        List<ProductSkuDto> skuSet = productDto.getProductSKUSet();
        List<ProductSku> skuList = copy2ProductSkuList(skuSet);
        // 生成商品
        this.processPrice(product, skuList);
        if (1 == productDto.getPop()) {
            product.setExternalSn(product.getInernalSn());
        }
        if (product.getStatus() == 5) {
            // 秒杀，不允许加入购物车
            product.setCart(0);
            product.setCod(0);
        }
        if (product.getSaleCategory().equals(SaleCategory.POPPRODUCT)) {
            // 第三方商品，不支持货到付款，不支持门店试衣
            product.setCod(0);
            product.setSubscribe(0);
        }
        Brand brand = brandService.findById(product.getDesignerId());
        // 如果设计师不支持售后或货到付款，商品也设置成不支持
        if (product.getAfter() == 1 || product.getCod() == 1) {
            if (brand != null) {
                if (brand.getCod() == 0) {
                    product.setCod(0);
                }
                if (brand.getAfter() == 0) {
                    product.setAfter(0);
                }
            }
        }
        if (brand != null) {
            if (brand.getSubscribe() != null && brand.getSubscribe().intValue() == 0) {
                product.setSubscribe(0);
            }
            product.setDesignerCode(brand.getCode());
            product.setDesignerName(brand.getName());
        }
        product = this.save(product);
        // 添加操作日志
        JSONObject info = new JSONObject();
        info.put("操作", "商品新增");
        productLogService
                .insert(new ProductLog(operator, info.toJSONString(), ProductLogType.Insert, product.getId(), null));
        // 生成商品详情
        productDetail.setProductId(product.getId());
        productDetailService.insert(productDetail);
        // 生成商品统计
        productSummary.setProductId(product.getId());
        productSummaryService.insert(productSummary);
        // 生成SKU
        for (ProductSku productSku : skuSet) {
            String sn = productSku.getSn();
            if (StringUtils.isBlank(sn) || StringUtils.isBlank(productSku.getSp1())) {
                throw new BusinessException("SKU数据异常！");
            }
            productSku.setProductId(product.getId());
            productSku.setName(product.getName());
            // POP商品不校验款号和条码之间的关系
            if (1 == productDto.getPop()) {
                productSku.setInernalSn(product.getInernalSn());
                productSku.setBarCode(productSku.getSn());
            } else {
                SkuCodeBean parser = this.getSKUCodeParser(product.getSp1GroupId(), product.getSp2GroupId(), sn);
                if (parser != null) {
                    if (!parser.getProductSn().equals(product.getInernalSn())) {
                        throw new BusinessException("条码为：" + sn + "的SKU不属于此商品！");
                    }
                    productSku.setInernalSn(parser.getProductSn());
                    productSku.setBarCode(parser.getBarCode());
                } else {
                    throw new BusinessException("条码为：" + sn + "的SKU解析错误！");
                }
            }
            productSku.setCreator(product.getCreator());
            productSku = productSkuService.insert(productSku, product.getStatus(), operator);
        }
        this.insertAggregation(skuList, product.getId());
        this.timingProductMQ(product);
        return product;
    }

    private void timingProductMQ(Product product) {
        if (product.getTiming() != null && product.getTiming() == 1) {
            Date now = new Date();
            if (product.getUpMarketDate().after(now)) {
                long interval = (product.getUpMarketDate().getTime() - now.getTime()) / 1000 + 1;
                Map<String, Object> map = new HashMap<>();
                map.put("id", product.getId());
                map.put("mark", 1);
                map.put("date", product.getUpMarketDate().getTime());
                MqEnum.TIMING_PRODUCT.send(map, interval);
            }
        }
    }

    /**
     * 插入商品后续操作
     *
     * @param product
     */
    protected void insertAggregation(List<ProductSku> skuSet, Long productId) {
        this.updateSalesPropertyBySku(skuSet, productId);
        productModuleSearchService.rebuild(productId);
    }

    @Override
    @Caching(evict = {@CacheEvict(value = "product", key = "'product_'+#productDto.id"),
            @CacheEvict(value = "product_store", key = "'product_flash_store_'+#productDto.id")})
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int update(ProductDto productDto, String operator) {
        productDto.setName(productDto.getName() == null ? null
                : productDto.getName().replaceAll("\"", "&quot;").replaceAll("'", "&apos;"));
        // 获取数据
        Product product = new Product();
        BeanUtils.copyProperties(productDto, product);
        ProductDetail productDetail = new ProductDetail();
        BeanUtils.copyProperties(productDto, productDetail);
        ProductSummary productSummary = new ProductSummary();
        BeanUtils.copyProperties(productDto, productSummary);
        List<ProductSkuDto> skuSet = productDto.getProductSKUSet();
        List<ProductSku> skuList = copy2ProductSkuList(skuSet);
        // 更新商品
        this.processPrice(product, skuList);
        if (1 == productDto.getPop()) {
            product.setExternalSn(product.getInernalSn());
        }
        if (product.getStatus() == 5) {
            // 秒杀，不允许加入购物车
            product.setCart(0);
            product.setCod(0);
        }
        if (product.getSaleCategory().equals(SaleCategory.POPPRODUCT)) {
            // 第三方商品，不支持货到付款，不支持门店试衣
            product.setCod(0);
            product.setSubscribe(0);
        }
        // 如果设计师不支持售后或货到付款，商品也设置成不支持
        Brand brand = brandService.findById(product.getDesignerId());
        if (product.getAfter() == 1 || product.getCod() == 1) {
            if (brand != null) {
                if (brand.getCod() == 0) {
                    product.setCod(0);
                }
                if (brand.getAfter() == 0) {
                    product.setAfter(0);
                }
            }
        }
        if (brand != null) {
            if (brand.getSubscribe() != null && brand.getSubscribe().intValue() == 0) {
                product.setSubscribe(0);
            }
            product.setDesignerCode(brand.getCode());
            product.setDesignerName(brand.getName());
        }
        Product old = this.findById(product.getId());
        this.updateNotNull(product);
        // TODO 临时操作
        productMapper.upEstimate(product.getId(), product.getEstimateDate(), product.getEstimateDay());
        // 商品详情的更新
        ProductDetail dbProductDetail = productDetailService.findByProductId(product.getId());
        productDetail.setProductId(product.getId());
        productDetail.setId(dbProductDetail.getId());
        productDetailService.update(productDetail);
        // 添加操作日志
        JSONObject info = new JSONObject();
        StringBuilder sb = new StringBuilder();
        if (!ProductCompareUtil.compare(old, product, ProductCompareType.PRODUCT, sb)) {
            info.put("操作", "修改商品" + sb.toString());
        } else {
            info.put("操作", "修改商品");
        }
        productLogService
                .insert(new ProductLog(operator, info.toJSONString(), ProductLogType.Update, productDto.getId(), null));
        // 保存SKU
        for (ProductSku productSku : skuSet) {
            if (productSku.getId() == null || productSku.getId() == 0) {
                // 新增SKU
                String sn = productSku.getSn();
                if (StringUtils.isBlank(sn) || StringUtils.isBlank(productSku.getSp1())) {
                    throw new BusinessException("SKU数据异常！");
                }
                productSku.setProductId(product.getId());
                if (1 == productDto.getPop()) {
                    productSku.setInernalSn(product.getInernalSn());
                    productSku.setBarCode(productSku.getSn());
                } else {
                    SkuCodeBean parser = this.getSKUCodeParser(product.getSp1GroupId(), product.getSp2GroupId(), sn);
                    if (parser != null) {
                        // POP商品不校验款号和条码之间的关系
                        if (!parser.getProductSn().equals(product.getInernalSn())) {
                            throw new BusinessException("条码为：" + sn + "的SKU不属于此商品！");
                        }
                        productSku.setInernalSn(parser.getProductSn());
                        productSku.setBarCode(parser.getBarCode());
                    } else {
                        throw new BusinessException("条码为：" + sn + "的SKU解析错误！");
                    }
                }
                productSku = productSkuService.insert(productSku, product.getStatus(), operator);
            } else {
                // 修改SKU
                String sn = productSku.getSn();
                if (StringUtils.isBlank(sn) || StringUtils.isBlank(productSku.getSp1())) {
                    throw new BusinessException("SKU数据异常！");
                }
                productSku.setExternalSn(product.getExternalSn());
                productSku.setProductId(product.getId());
                if (1 == productDto.getPop()) {
                    productSku.setInernalSn(product.getInernalSn());
                    productSku.setBarCode(productSku.getSn());
                } else {
                    SkuCodeBean parser = this.getSKUCodeParser(product.getSp1GroupId(), product.getSp2GroupId(), sn);
                    if (parser != null) {
                        // POP商品不校验款号和条码之间的关系
                        if (!parser.getProductSn().equals(product.getInernalSn())) {
                            throw new BusinessException("条码为：" + sn + "的SKU不属于此商品！");
                        }
                        productSku.setInernalSn(parser.getProductSn());
                        productSku.setBarCode(parser.getBarCode());
                    } else {
                        throw new BusinessException("条码为：" + sn + "的SKU解析错误！");
                    }
                }
                productSkuService.update(productSku, false, product.getStatus(), operator);
            }
        }
        timingProductMQ(product);
        return this.updateAggregation(skuList, product.getId());
    }

    /**
     * 更新商品后续操作
     *
     * @param productId
     */
    protected int updateAggregation(List<ProductSku> skuSet, Long productId) {
        this.updateSalesPropertyBySku(skuSet, productId);
        int result = productModuleSearchService.rebuild(productId);
        return result;
    }

    /**
     * 计算商品价格
     *
     * @param product
     * @param skuSet
     */
    protected void processPrice(Product product, List<ProductSku> skuSet) {
        product.setMinPrice(getMinimumPrice(skuSet));
        product.setMaxPrice(getMaximumPrice(skuSet));
        product.setOriginalPrice(getMaximumOriginalPrice(skuSet));
        product.setaPrice(getMinimumAPrice(skuSet));
        product.setFlashPrice(getMinimumFlashPrice(skuSet));
        product.setCostPrice(getMaximumCostPrice(skuSet));
        product.setKaolaPrice(getMinimumKaolaPrice(skuSet));
        product.setCollagePrice(getMinimumCollagePrice(skuSet));
    }

    /**
     * 获取该商品一组SKU中最高成本价格
     *
     * @param skuSet
     * @return
     */
    private BigDecimal getMaximumCostPrice(List<ProductSku> productSKUList) {
        BigDecimal max = null;
        for (ProductSku sku : productSKUList) {
            if (sku.getCostPrice() == null || (sku.getStatus() != null && sku.getStatus() < 0)) {
                continue;
            }
            if (max == null)
                max = sku.getCostPrice();
            else if (max.compareTo(sku.getCostPrice()) < 0)
                max = sku.getCostPrice();
        }
        return max;
    }

    /**
     * 获取该商品一组SKU中最低价格
     *
     * @param product
     * @param productSKUList
     * @return
     */
    private BigDecimal getMinimumPrice(List<ProductSku> productSKUList) {
        BigDecimal min = null;
        for (ProductSku sku : productSKUList) {
            if (sku.getPrice() == null || (sku.getStatus() != null && sku.getStatus() < 0)) {
                continue;
            }
            if (min == null) {
                min = sku.getPrice();
            } else if (min.compareTo(sku.getPrice()) > 0) {
                min = sku.getPrice();
            }
        }
        return min;
    }

    /**
     * 获取该商品一组SKU中最高价格
     *
     * @param product
     * @param productSKUList
     * @return
     */
    private BigDecimal getMaximumPrice(List<ProductSku> productSKUList) {
        BigDecimal max = null;
        for (ProductSku sku : productSKUList) {
            if (sku.getPrice() == null || (sku.getStatus() != null && sku.getStatus() < 0)) {
                continue;
            }
            if (max == null)
                max = sku.getPrice();
            else if (max.compareTo(sku.getPrice()) < 0)
                max = sku.getPrice();
        }
        return max;
    }

    /**
     * 获取该商品一组SKU中最高吊牌价
     *
     * @param productSKUList
     * @return
     */
    private BigDecimal getMaximumOriginalPrice(List<ProductSku> productSKUList) {
        BigDecimal max = null;
        for (ProductSku sku : productSKUList) {
            if (sku.getOriginalCost() == null || (sku.getStatus() != null && sku.getStatus() < 0)) {
                continue;
            }
            if (max == null)
                max = sku.getOriginalCost();
            else if (max.compareTo(sku.getOriginalCost()) < 0)
                max = sku.getOriginalCost();
        }
        return max;
    }

    /**
     * 获取该商品一组SKU中最低一口价
     *
     * @param productSKUList
     * @return
     */
    private BigDecimal getMinimumAPrice(List<ProductSku> productSKUList) {
        BigDecimal min = null;
        for (ProductSku sku : productSKUList) {
            if (sku.getaPrice() == null || (sku.getStatus() != null && sku.getStatus() < 0)) {
                continue;
            }
            if (min == null) {
                min = sku.getaPrice();
            } else if (min.compareTo(sku.getaPrice()) > 0) {
                min = sku.getaPrice();
            }
        }
        return min;
    }

    /**
     * 获取该商品一组SKU中最低限时购价格
     *
     * @param productSKUList
     * @return
     */
    private BigDecimal getMinimumFlashPrice(List<ProductSku> productSKUList) {
        BigDecimal min = null;
        for (ProductSku sku : productSKUList) {
            if (sku.getFlashPrice() == null || (sku.getStatus() != null && sku.getStatus() < 0)) {
                continue;
            }
            if (min == null) {
                min = sku.getFlashPrice();
            } else if (min.compareTo(sku.getFlashPrice()) > 0) {
                min = sku.getFlashPrice();
            }
        }
        return min;
    }

    /**
     * 获取该商品一组SKU中最低考拉价格
     *
     * @param productSKUList
     * @return
     */
    private BigDecimal getMinimumKaolaPrice(List<ProductSku> productSKUList) {
        BigDecimal min = null;
        for (ProductSku sku : productSKUList) {
            if (sku.getKaolaPrice() == null || (sku.getStatus() != null && sku.getStatus() < 0)) {
                continue;
            }
            if (min == null) {
                min = sku.getKaolaPrice();
            } else if (min.compareTo(sku.getKaolaPrice()) > 0) {
                min = sku.getKaolaPrice();
            }
        }
        return min;
    }

    /**
     * 获取该商品一组SKU中最低拼团价格
     *
     * @param productSKUList
     * @return
     */
    private BigDecimal getMinimumCollagePrice(List<ProductSku> productSKUList) {
        BigDecimal min = null;
        for (ProductSku sku : productSKUList) {
            if (sku.getCollagePrice() == null || (sku.getStatus() != null && sku.getStatus() < 0)) {
                continue;
            }
            if (min == null) {
                min = sku.getCollagePrice();
            } else if (min.compareTo(sku.getCollagePrice()) > 0) {
                min = sku.getCollagePrice();
            }
        }
        return min;
    }

    @Override
    public int getMaxSort(Long id) {
        return productMapper.getMaxSort(id);
    }

    @Override
    public Map<String, Object> countGroupByMark() {
        Map<String, Object> productMap = new HashMap<>();
        List<Map<String, Object>> counts = productMapper.countGroupByMark();
        for (Map<String, Object> count : counts) {
            String mark = count.get("mark").toString();
            switch (mark) {
                case "-1":
                    productMap.put("deleteCount", count.get("counts"));// 删除
                    break;
                case "0":
                    productMap.put("downCount", count.get("counts"));// 下架
                    break;
                case "1":
                    productMap.put("upCount", count.get("counts"));// 上架
            }
        }
        productMap.put("NoStore", productMapper.countNoStore());// 缺货
        return productMap;
    }

    @Override
    public Integer countBySearch(ProductSearcher productSearcher) {
        return productMapper.countBySearch(productSearcher);
    }

    @Override
    public List<Product> findProductBySn(String sn) {
        return productMapper.findProductBySn(sn);
    }

    @Override
    public Product findOneBySn(String sn) {
        return productMapper.findOneBySn(sn);
    }

    @Override
    @CacheEvict(value = "product", key = "'product_'+#productId")
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int updateCategory(Long productId, ProductCategory productCategory, String pcJson, String tcJson,
                              String operator) {
        int result = productMapper.updateCategory(productId, productCategory.getId(), productCategory.getTopId(),
                pcJson, tcJson);
        if (result > 0) {
            result = productDetailService.updateAttribute(productId, productCategory.getAttributeGroupId());
            if (result > 0) {
                SearcherProduct product = new SearcherProduct();
                product.setId(productId);
                product.setTopCategory(tcJson);
                product.setTopCategoryId(productCategory.getTopId());
                product.setProductCategory(pcJson);
                product.setProductCategoryId(productCategory.getId());
                productModuleSearchService.update(product);
                JSONObject info = new JSONObject();
                info.put("操作", "更新商品分类");
                productLogService.insert(
                        new ProductLog(operator, info.toJSONString(), ProductLogType.Category, productId, null));
            }
        }
        return result;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int doTiming(Long id, int timing) {
        return productMapper.doTiming(id, timing);
    }

    @Override
    public int countStockBySearch(ProductSkuStockSearcher searcher) {
        return productMapper.countStockBySearch(searcher);
    }

    @Override
    public PageResult<ProductDto> findStockBySearch(PageModel page, ProductSkuStockSearcher searcher) {
        PageResult<ProductDto> pager = new PageResult<>(page);
        int totalCount = productMapper.countStockBySearch(searcher);
        if (totalCount > 0) {
            List<Product> list = productMapper.findStockBySearch(searcher, page);
            List<ProductDto> dtos = new ArrayList<>();
            for (Product product : list) {
                ProductDto dto = new ProductDto();
                BeanUtils.copyProperties(product, dto);
                List<ProductSkuStockSummary> stockList = ProductSkuStockSummaryService.findByProductId(product.getId());
                dto.setStockList(stockList);
                dtos.add(dto);
            }
            pager.setList(dtos);
        }
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    public PageResult<Product> findByCouponDefId(Long defineId, PageModel page) {
        PageResult<Product> pager = new PageResult<>(page);
        int totalCount = productMapper.countByCouponDefId(defineId);
        if (totalCount > 0) {
            List<Product> list = productMapper.findByCouponDefId(page, defineId);
            pager.setTotalCount(totalCount);
            pager.setList(list);
        }
        return pager;
    }

    @Override
    public PageResult<Product> findByProductTagId(Long tagId, PageModel page) {
        PageResult<Product> pager = new PageResult<>(page);
        int totalCount = productMapper.countByProductTagId(tagId);
        if (totalCount > 0) {
            List<Product> list = productMapper.findByProductTagId(tagId, page);
            pager.setTotalCount(totalCount);
            pager.setList(list);
        }
        return pager;
    }

    @Override
    public PageResult<Product> findByPromotionId(Long promotionId, PageModel page) {
        PageResult<Product> pager = new PageResult<>(page);
        int totalCount = productMapper.countByPromotionId(promotionId);
        if (totalCount > 0) {
            List<Product> list = productMapper.findByPromotionId(promotionId, page);
            pager.setTotalCount(totalCount);
            pager.setList(list);
        }
        return pager;
    }

    @Override
    public PageResult<Product> findByDesignerId(Long designerId, PageModel page) {
        PageResult<Product> pager = new PageResult<>(page);
        int totalCount = productMapper.countByDesignerId(designerId);
        if (totalCount > 0) {
            List<Product> list = productMapper.findByDesignerId(designerId, page);
            pager.setTotalCount(totalCount);
            pager.setList(list);
        }
        return pager;
    }

    @Override
    public PageResult<Product> findBySourceId(Long sourceId, RelationType type, PageModel page, Integer mark) {
        PageResult<Product> pager = new PageResult<>(page);
        int count = productMapper.countBySourceId(sourceId, type.toString(), mark);
        if (count > 0) {
            List<Product> list = productMapper.findBySourceId(sourceId, type.toString(), page, mark);
            pager.setTotalCount(count);
            pager.setList(list);
        }
        return pager;
    }

    @Override
    public Product findByMemberShareId(Long shareId) {
        return productMapper.findByMemberShareId(shareId);
    }

    @Override
    public ProductDto findDtoById(Long id) {
        ProductDto dto = new ProductDto();
        Product product = this.findById(id);
        BeanUtils.copyProperties(product, dto);
        Brand brand = brandService.findById(product.getDesignerId());
        dto.setDesigner(brand);
        List<ProductSku> list = productSkuService.findByProductId(id);
        dto.setSkuList(list);
        return dto;
    }

    @Override
    public PageResult<ProductDto> findAdBySearch(ProductSearcher productSearcher, PageModel page) {
        PageResult<ProductDto> pager = new PageResult<>(page);
        int totalCount = productMapper.countBySearch(productSearcher);
        if (totalCount > 0) {
            List<Product> productList = productMapper.findBySearch(productSearcher, page);
            List<ProductDto> dtos = new ArrayList<>();
            for (int i = 0; i < productList.size(); i++) {
                ProductDto dto = new ProductDto();
                BeanUtils.copyProperties(productList.get(i), dto);
                ProductDetail productDetail = productDetailService.findByProductId(dto.getId());
                if (productDetail != null) {
                    dto.setAdPic(productDetail.getAdPic());
                }
                dtos.add(dto);
            }
            pager.setTotalCount(totalCount);
            pager.setList(dtos);
        }
        return pager;
    }

    @Override
    public List<Long> findByDesignerIdAndBarCode(Long designerId, String barCode, List<Long> designerIds) {
        return productMapper.findByDesignerIdAndBarCode(designerId, barCode, designerIds);
    }

    @Override
    public PageResult<ProductDto> findNoStore(PageModel page) {
        PageResult<ProductDto> pager = new PageResult<>(page);
        int totalCount = productMapper.countNoStore();
        if (totalCount > 0) {
            List<Product> productList = productMapper.findNoStore(page);
            List<ProductDto> dtos = new ArrayList<>();
            for (int i = 0; i < productList.size(); i++) {
                ProductDto dto = new ProductDto();
                BeanUtils.copyProperties(productList.get(i), dto);
                dtos.add(dto);
            }
            pager.setTotalCount(totalCount);
            pager.setList(dtos);
        }
        return pager;
    }

    @Override
    @CacheEvict(value = "product", key = "'product_'+#productId")
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int updateAfterByDesigner(Long designerId, Integer after, Long productId) {
        return productMapper.updateAfterByDesigner(designerId, after);
    }

    @Override
    @CacheEvict(value = "product", key = "'product_'+#productId")
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int updateCodByDesigner(Long designerId, Integer cod, Long productId) {
        return productMapper.updateCodByDesigner(designerId, cod);
    }

    @Override
    public List<Long> findProductId(Long designerId) {
        return productMapper.findProductId(designerId);
    }

    @Override
    public Product findByProductSn(String productSn) {
        return productMapper.findByProductSn(productSn);
    }

    /**
     * 获取该种类下的所有商品，按推荐值倒序
     */
    @Override
    public List<Product> findDeepByCategoryId(Long productCategoryId, Date lastDate, int page, int limit) {
        return productMapper.findDeepByCategoryId(productCategoryId, lastDate, new PageModel(page, limit));
    }

    @Override
    public PageResult<ProductDto> findExport(ProductSearcher productSearcher, PageModel page) {
        PageResult<ProductDto> pager = new PageResult<>(page);
        int totalCount = productMapper.countBySearch(productSearcher);
        if (totalCount > 0) {
            List<Product> productList = productMapper.findBySearch(productSearcher, page);
            List<ProductDto> dtos = new ArrayList<>();
            for (int i = 0; i < productList.size(); i++) {
                ProductDto dto = new ProductDto();
                BeanUtils.copyProperties(productList.get(i), dto);
                dto.setSkuList(productSkuService.findByProductId(dto.getId()));
                dtos.add(dto);
            }
            pager.setTotalCount(totalCount);
            pager.setList(dtos);
        }
        return pager;
    }

    @Override
    @CacheEvict(value = "product", key = "'product_'+#productId")
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int updateEstimateDate(Long id, String estimateDate, String operator) {
        int result = 0;
        if (StringUtils.isNotBlank(estimateDate)) {
            result = productMapper.updateEstimateDate(id, DateUtil.str2second(estimateDate), operator);
            if (result > 0) {
                JSONObject info = new JSONObject();
                info.put("操作", "修改预计发货时间");
                info.put("预计发货时间", estimateDate);
                productLogService
                        .insert(new ProductLog(operator, info.toJSONString(), ProductLogType.Update, id, null));
            }
        }
        return result;
    }

    @Override
    @CacheEvict(value = "product", key = "'product_'+#id")
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int updateVideoById(Long id, String video) {
        return productMapper.updateVideoById(id, video);
    }

    @Override
    @CacheEvict(value = "product", key = "'product_'+#id")
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int updateRecomById(Long id, Double recom, Boolean operRecom) {
        Integer oper = null;
        if (operRecom != null) {
            oper = operRecom ? 1 : 0;
        }
        return productMapper.updateRecomById(id, recom, oper);
    }

    @Override
    @CacheEvict(value = "product", key = "'product_'+#id")
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int updateRebate(Long id, BigDecimal firstRatio, BigDecimal secondRatio, BigDecimal grossRatio,
                            String operator) {
        Product product = this.findById(id);
        int success = productMapper.updateRebate(id, firstRatio, secondRatio, grossRatio);
        if (success > 0) {
            productModuleSearchService.rebuild(id);
            JSONObject info = new JSONObject();
            info.put("操作", "设置返利");
            info.put("修改前", "一级返利：" + product.getFirstRatio() + "；二级返利：" + product.getSecondRatio());
            info.put("修改后", "一级返利：" + firstRatio + "；二级返利：" + secondRatio);
            productLogService.insert(new ProductLog(operator, info.toJSONString(), ProductLogType.Update, id, null));
        }
        return success;
    }

    @Override
    @CacheEvict(value = "product", key = "'product_'+#productId")
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int updateGoodPromotion(Long productId, Long goodPromotionId, String operator) {
        int success = productMapper.updateGoodPromotion(productId, goodPromotionId);
        if (success > 0) {
            Promotion productPromotion = promotionService.findSimpleById(goodPromotionId);
            productModuleSearchService.updatePromotion(productId, productPromotion);
            JSONObject info = new JSONObject();
            info.put("操作", "绑定商品活动");
            info.put("活动", "活动ID：" + goodPromotionId + "活动名称：" + productPromotion.getName());
            productLogService
                    .insert(new ProductLog(operator, info.toJSONString(), ProductLogType.PromotionR, productId, null));
        }
        return success;
    }

    @Override
    @CacheEvict(value = "product", key = "'product_'+#productId")
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int updateOrderPromotion(Long productId, Long orderPromotionId, String operator) {
        int success = productMapper.updateOrderPromotion(productId, orderPromotionId);
        if (success > 0) {
            Promotion orderPromotion = promotionService.findSimpleById(orderPromotionId);
            productModuleSearchService.updatePromotion(productId, orderPromotion);
            JSONObject info = new JSONObject();
            info.put("操作", "绑定订单活动");
            info.put("活动", "活动ID：" + orderPromotionId + "活动名称：" + orderPromotion.getName());
            productLogService
                    .insert(new ProductLog(operator, info.toJSONString(), ProductLogType.PromotionR, productId, null));
        }
        return success;
    }

    @Override
    @CacheEvict(value = "product", key = "'product_'+#productId")
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int deleteGoodPromotion(Long productId, Long goodPromotionId, String operator) {
        int success = productMapper.updateGoodPromotion(productId, null);
        if (success > 0) {
            productSearcherService.removeGoodPromotion(productId);
            JSONObject info = new JSONObject();
            info.put("操作", "删除商品活动");
            info.put("活动", "活动ID：" + goodPromotionId);
            productLogService
                    .insert(new ProductLog(operator, info.toJSONString(), ProductLogType.PromotionR, productId, null));
        }
        return success;
    }

    @Override
    @CacheEvict(value = "product", key = "'product_'+#productId")
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int deleteOrderPromotion(Long productId, Long orderPromotionId, String operator) {
        int success = productMapper.updateOrderPromotion(productId, null);
        if (success > 0) {
            productSearcherService.removeOrderPromotion(productId);
            JSONObject info = new JSONObject();
            info.put("操作", "删除订单活动");
            info.put("活动", "活动ID：" + orderPromotionId);
            productLogService
                    .insert(new ProductLog(operator, info.toJSONString(), ProductLogType.PromotionR, productId, null));
        }
        return success;
    }

    @Override
    @CacheEvict(value = "product", key = "'product_'+#id")
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int updateAPrice(Long id, BigDecimal aPrice) {
        return productMapper.updateAPrice(id, aPrice);
    }

    @Override
    @CacheEvict(value = "product", key = "'product_'+#id")
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int updateFlashPromotion(Long id, Long flashPromotionId, String username) {
        int success = productMapper.updateFlashPromotionId(id, flashPromotionId);
        if (success > 0) {
            FlashPromotion flashPromotion = flashPromotionService.findById(flashPromotionId);
            productModuleSearchService.updateFlashPromotion(id, flashPromotion);
        }
        return success;
    }

    @Override
    @CacheEvict(value = "product", key = "'product_'+#id")
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int deleteFlashPromotion(Long id) {
        int success = productMapper.updateFlashPromotionId(id, null);
        if (success > 0) {
            productSearcherService.removeFlashPromotion(id);
        }
        return success;
    }

    @Override
    @CacheEvict(value = "product", key = "'product_'+#id")
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int updateCollagePromotion(Long id, Long collagePromotionId, String username) {
        int success = productMapper.updateCollagePromotionId(id, collagePromotionId);
        if (success > 0) {
            CollagePromotion collagePromotion = collagePromotionService.findById(collagePromotionId);
            productModuleSearchService.updateCollagePromotion(id, collagePromotion);
        }
        return success;
    }

    @Override
    @CacheEvict(value = "product", key = "'product_'+#id")
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int deleteCollagePromotion(Long id) {
        int success = productMapper.updateCollagePromotionId(id, null);
        if (success > 0) {
            productSearcherService.removeCollagePromotion(id);
        }
        return success;
    }

    @Override
    @CacheEvict(value = "product", key = "'product_'+#id")
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int updateAfterMemo(Long id, String afterMemo, String operator) {
        int success = productMapper.updateAfterMemo(id, afterMemo);
        if (success > 0) {
            JSONObject info = new JSONObject();
            info.put("备注", afterMemo);
            productLogService.insert(new ProductLog(operator, info.toJSONString(), ProductLogType.Remark, id, null));
        }
        return success;
    }

    @Override
    @CacheEvict(value = "product", key = "'product_'+#id")
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int updateSellType(Long id, String sellType, Date estimateDate, Integer estimateDay, String remark) {
        int success = productMapper.updateSellType(id, sellType, estimateDate, estimateDay, remark);
        if (success > 0) {
            SearcherProduct product = new SearcherProduct();
            product.setId(id);
            product.setProductSellType(sellType);
            productSearcherService.update(product);
        }
        return success;
    }

    @Override
    public PageResult<Long> findByBrandAndSeries(Long brandId, Long seriesId, PageModel pageModel) {
        PageResult<Long> pager = new PageResult<>(pageModel);
        int totalCount = productMapper.countByBrandAndSeries(brandId, seriesId);
        if (totalCount > 0) {
            List<Long> productIds = productMapper.findByBrandAndSeries(brandId, seriesId, pageModel);
            pager.setList(productIds);
        }
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    @CacheEvict(value = "product", key = "'product_'+#id")
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int updateKaolaProduct(Long id, BigDecimal kaolaPrice, Integer mark, Integer taxation, Integer shipping) {
        productMapper.updateKaolaProduct(id, kaolaPrice, taxation, shipping);
        if (mark != null) {
            updateMark(id, mark, "sys");
        }
        return 1;
    }

    @Override
    @Caching(evict = {@CacheEvict(value = "product", key = "'product_'+#dto.id"),
            @CacheEvict(value = "product_store", key = "'product_flash_store_'+#dto.id")})
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int doRepairKaolaPic(ProductDto dto) {
        this.updateFieldById(dto.getId().intValue(), "intro_pic", dto.getIntroPic());
        List<ProductSku> skuList = dto.getSkuList();
        for (ProductSku sku : skuList) {
            productSkuService.updateSp1(sku.getId(), sku.getSp1(), false, null);
        }
        this.insertAggregation(skuList, dto.getId());
        return 1;
    }

    @Override
    public PageResult<ProductDto> findKaolaByWaitingForRepair(PageModel page) {
        PageResult<ProductDto> pager = new PageResult<ProductDto>(page);
        List<ProductDto> dtos = new ArrayList<>();
        Integer totalCount = productMapper.countKaolaByWaitingForRepair();
        if (totalCount > 0) {
            List<Product> list = productMapper.findKaolaByWaitingForRepair(page);
            for (Product product : list) {
                ProductDto dto = new ProductDto();
                BeanUtils.copyProperties(product, dto);
                dto.setSkuList(productSkuService.findByProductId(product.getId()));
                dtos.add(dto);
            }
        }
        pager.setTotalCount(totalCount);
        pager.setList(dtos);
        return pager;
    }

    @Override
    public int countKaolaByWaitingForRepair() {
        return productMapper.countKaolaByWaitingForRepair();
    }

    @Override
    public List<Map<String, Object>> findProductBySelect(Integer constellation, Integer pattern, Integer interest,
                                                         Integer style, Integer category) {
        String sql = "SELECT id, pic, back FROM p_product_select WHERE FIND_IN_SET(?,constellation) AND FIND_IN_SET(?,pattern) AND"
                + " FIND_IN_SET(?,interest) AND FIND_IN_SET(?,style) AND FIND_IN_SET(?,category);";
        return jdbcTemplate.queryForList(sql, constellation, pattern, interest, style, category);
    }

}
