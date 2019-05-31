package com.d2c.product.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.codingapi.tx.annotation.TxTransaction;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.logger.model.ProductLog;
import com.d2c.logger.model.ProductLog.ProductLogType;
import com.d2c.logger.model.SkuOperateLog;
import com.d2c.logger.service.ProductLogService;
import com.d2c.logger.service.SkuOperateLogService;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.product.dao.ProductSkuMapper;
import com.d2c.product.dao.SalesPropertyMapper;
import com.d2c.product.dto.ProductSkuDto;
import com.d2c.product.model.ProductSku;
import com.d2c.product.model.ProductSkuStockSummary;
import com.d2c.product.model.SalesProperty.SalesPropertyType;
import com.d2c.product.query.ProductSkuSearcher;
import com.d2c.product.query.ProductSkuStockSearcher;
import com.d2c.product.search.model.SearcherProduct;
import com.d2c.product.search.service.ProductSearcherQueryService;
import com.d2c.product.support.SkuCodeBean;
import com.d2c.product.support.UploadProductBean;
import com.d2c.product.third.kaola.KaolaProduct;
import com.d2c.product.util.ProductCompareUtil;
import com.d2c.product.util.ProductCompareUtil.ProductCompareType;
import com.d2c.util.serial.JsonUtil;
import com.d2c.util.string.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("productSkuService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class ProductSkuServiceImpl extends ListServiceImpl<ProductSku> implements ProductSkuService {

    private final static String PRODUCTSKUKEY = "product_sku_id_";
    @Autowired
    private ProductSkuMapper productSkuMapper;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductModuleSearchService productModuleSearchService;
    @Autowired
    private SkuOperateLogService skuOperateLogService;
    @Autowired
    private ProductSkuStockSummaryService productSkuStockSummaryService;
    @Autowired
    private ProductLogService productLogService;
    @Reference
    private ProductSearcherQueryService productSearcherQueryService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private SalesPropertyMapper salesPropertyMapper;
    @Autowired
    private RedisTemplate<String, Integer> redisTemplate;

    @Override
    public Map<Long, ProductSku> findByIds(Long[] ids) {
        List<ProductSku> list = productSkuMapper.findByIds(ids);
        Map<Long, ProductSku> result = new HashMap<>();
        for (ProductSku p : list) {
            result.put(p.getId(), p);
        }
        return result;
    }

    @Override
    public SkuCodeBean processRegular(Long sp1GroupId, Long sp2GroupId, String code) {
        // 24位，则为用户自定义码
        if (code.length() == 24) {
            code = code.substring(0, 23);
        }
        SkuCodeBean bean = new SkuCodeBean();
        bean.setBarCode(code);
        bean.setProductSn(code.substring(0, 12));
        bean.setDesigner(getDesigner(code.substring(0, 4)));
        bean.setSkuSalesProperties(generateSalesProperty(sp1GroupId, sp2GroupId,
                code.substring(code.length() - 5, code.length() - 2), code.substring(code.length() - 2)));
        return bean;
    }

    @Override
    public SkuCodeBean processthird(Long sp1GroupId, Long sp2GroupId, String code) {
        if (code.length() == 19) {
            code = code.substring(0, 18);
        }
        SkuCodeBean bean = new SkuCodeBean();
        bean.setBarCode(code);
        bean.setProductSn(code.substring(0, 13));
        bean.setDesigner(getDesigner(code.substring(1, 5)));
        bean.setSkuSalesProperties(generateSalesProperty(sp1GroupId, sp2GroupId,
                code.substring(code.length() - 5, code.length() - 2), code.substring(code.length() - 2)));
        return bean;
    }

    @Override
    public SkuCodeBean processStore(Long sp1GroupId, Long sp2GroupId, String code) {
        SkuCodeBean bean = new SkuCodeBean();
        bean.setBarCode(code);
        bean.setProductSn(code.substring(0, 11));
        bean.setDesigner(getDesigner(code.substring(1, 3)));
        bean.setSkuSalesProperties(
                generateSalesProperty(sp1GroupId, sp2GroupId, code.substring(11, 14), code.substring(14, 16)));
        return bean;
    }

    @Override
    public Integer initStore(Long productSkuId, Integer store) {
        logger.info("insert productskuId into redis:" + productSkuId + ", store:" + store);
        try {
            redisTemplate.delete(PRODUCTSKUKEY + productSkuId);
            if (store != null && store > 0) {
                for (Integer i = 1; i <= store; i++) {
                    redisTemplate.opsForList().rightPush(PRODUCTSKUKEY + productSkuId, i);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return store;
    }

    @Override
    public Integer decStore(Long productSkuId) {
        return redisTemplate.opsForList().leftPop(PRODUCTSKUKEY + productSkuId);
    }

    @Override
    public void incStore(Long productSkuId) {
        redisTemplate.opsForList().rightPush(PRODUCTSKUKEY + productSkuId, 1);
    }

    @Override
    public String dump(Long productSkuId) {
        StringBuffer buffer = new StringBuffer();
        String key = PRODUCTSKUKEY + productSkuId;
        BoundListOperations<String, Integer> list = redisTemplate.boundListOps(key);
        Integer value = null;
        while (true) {
            value = list.leftPop();
            if (value == null)
                break;
            buffer.append(value + "\r\n");
        }
        return new String(buffer.toString());
    }

    private Map<String, Object> getDesigner(String code) {
        Map<String, Object> brandList = brandService.findSimpleByCode(code);
        if (brandList == null) {
            return null;
        }
        return brandList;
    }

    private Map<String, Map<String, Object>> generateSalesProperty(Long sp1GroupId, Long sp2GroupId, String code1,
                                                                   String code2) {
        Map<String, Map<String, Object>> map = new HashMap<>();
        // 主规格解析，必然存在
        if (sp1GroupId == null) {
            return null;
        }
        Map<String, Object> color = salesPropertyMapper.findByUnique(sp1GroupId, SalesPropertyType.COLOR, code1);
        if (color == null) {
            return null;
        }
        map.put("color", color);
        // 副规格解析，可能不存在
        if (sp2GroupId == null) {
            return null;
        }
        Map<String, Object> size = salesPropertyMapper.findByUnique(sp2GroupId, SalesPropertyType.SIZE, code2);
        if (size == null) {
            return null;
        }
        map.put("size", size);
        return map;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void saveProductSkuLog(ProductSku oldProductSku, ProductSku newProductSku, String operator) {
        SkuOperateLog skuOperateLog = new SkuOperateLog();
        if (oldProductSku != null) {
            skuOperateLog.setOperateType("update");
            skuOperateLog.setSkuId(oldProductSku.getId());
            skuOperateLog.setSkuSn(oldProductSku.getSn());
            skuOperateLog.setProductSn(oldProductSku.getInernalSn());
            skuOperateLog.setProductId(oldProductSku.getProductId());
            skuOperateLog.setOldContent(JsonUtil.instance().toJSONString(oldProductSku));
            ProductSku temp = new ProductSku();
            BeanUtils.copyProperties(newProductSku, temp);
            temp.setFreezeStore(oldProductSku.getFreezeStore());
            skuOperateLog.setNewContent(JsonUtil.instance().toJSONString(temp));
        } else {
            skuOperateLog.setOperateType("insert");
            skuOperateLog.setSkuId(newProductSku.getId());
            skuOperateLog.setSkuSn(newProductSku.getSn());
            skuOperateLog.setProductSn(newProductSku.getInernalSn());
            skuOperateLog.setProductId(newProductSku.getProductId());
            ProductSku temp = new ProductSku();
            BeanUtils.copyProperties(newProductSku, temp);
            skuOperateLog.setNewContent(JsonUtil.instance().toJSONString(temp));
        }
        skuOperateLog.setCreator(operator);
        skuOperateLog.setLastModifyMan(operator);
        skuOperateLogService.insert(skuOperateLog);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public ProductSku insert(ProductSku sku, int productStatus, String operator) {
        if (StringUtils.isNotBlank(sku.getSp1()) && StringUtils.isNotBlank(sku.getSp2())) {
            this.serializeSalesProperty(sku);
        }
        StringUtil.replaceBlank(sku.getSn());
        StringUtil.replaceBlank(sku.getBarCode());
        sku = this.save(sku);
        // 生成汇总库存
        ProductSkuStockSummary productSkuStockSummary = new ProductSkuStockSummary();
        productSkuStockSummary.setSkuId(sku.getId());
        productSkuStockSummary.setProductId(sku.getProductId());
        productSkuStockSummary.setBarCode(sku.getBarCode());
        productSkuStockSummary.setSp1(sku.getSp1());
        productSkuStockSummary.setSp2(sku.getSp2());
        productSkuStockSummaryService.insert(productSkuStockSummary);
        if (productStatus == 5) {
            // 秒杀商品
            int store = sku.getStore() + sku.getPopStore() - sku.getFreezeStore();
            this.initStore(sku.getId(), store);
        }
        saveProductSkuLog(null, sku, operator);
        // 添加操作日志
        JSONObject info = new JSONObject();
        info.put("操作", "新增sku");
        info.put("sku", sku.logJson());
        productLogService.insert(new ProductLog(operator, info.toJSONString(), ProductLogType.UpdateSku,
                sku.getProductId(), sku.getId()));
        return sku;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int update(ProductSku sku, boolean doPromotion, int productStatus, String operator) {
        if (StringUtils.isNotBlank(sku.getSp1()) && StringUtils.isNotBlank(sku.getSp2())) {
            this.serializeSalesProperty(sku);
        }
        ProductSku oldSku = this.findById(sku.getId());
        if (oldSku.getFreezeStore() > (sku.getStore() + sku.getPopStore())) {
            throw new BusinessException("商品库存修改应大于锁定库存，修改不成功");
        }
        // 锁定库存，不能更新
        int store = sku.getStore() + sku.getPopStore() - sku.getFreezeStore();
        sku.setFreezeStore(null);
        StringUtil.replaceBlank(sku.getSn());
        StringUtil.replaceBlank(sku.getBarCode());
        int success = this.updateNotNull(sku);
        if (oldSku.getFlashPrice() != null && sku.getFlashPrice() == null) {
            this.updateFieldById(sku.getId().intValue(), "flash_price", null);
        }
        if (success > 0) {
            if (!oldSku.getBarCode().equals(sku.getBarCode()) && StringUtil.isNotBlank(sku.getBarCode())) {
                // 去productSkuStockSummary修改
                productSkuStockSummaryService.updateSkuBySkuId(sku.getBarCode(), oldSku.getId(), sku.getSp1(),
                        sku.getSp2());
            }
            if (productStatus == 5) {
                sku = this.findById(sku.getId());
                // 秒杀商品
                this.initStore(sku.getId(), store);
            }
            if (doPromotion) {
                // 再更新索引
                productModuleSearchService.rebuild(sku.getProductId());
            }
            redisTemplate.delete("product_flash_store_" + sku.getProductId());
        }
        saveProductSkuLog(oldSku, sku, operator);
        // 添加操作日志
        JSONObject info = new JSONObject();
        StringBuilder sb = new StringBuilder();
        if (!ProductCompareUtil.compare(oldSku, sku, ProductCompareType.SKU, sb)) {
            info.put("操作", "修改" + sb.toString());
        } else {
            info.put("操作", "修改");
        }
        productLogService.insert(new ProductLog(operator, info.toJSONString(), ProductLogType.UpdateSku,
                oldSku.getProductId(), oldSku.getId()));
        return success;
    }

    private void serializeSalesProperty(ProductSku sku) {
        JSONObject obj1 = JSONObject.parseObject(sku.getSp1());
        JSONObject new1 = new JSONObject();
        new1.put("id", obj1.getLong("id"));
        new1.put("name", obj1.getString("name"));
        new1.put("value", obj1.getString("value"));
        new1.put("code", obj1.getString("code"));
        new1.put("groupId", obj1.getLong("groupId"));
        new1.put("img", obj1.getString("img"));
        JSONObject obj2 = JSONObject.parseObject(sku.getSp2());
        JSONObject new2 = new JSONObject();
        new2.put("id", obj2.getLong("id"));
        new2.put("name", obj2.getString("name"));
        new2.put("value", obj2.getString("value"));
        new2.put("code", obj2.getString("code"));
        new2.put("groupId", obj2.getLong("groupId"));
        new2.put("img", obj2.getString("img"));
        sku.setSp1(new1.toString());
        sku.setSp2(new2.toString());
    }

    @Override
    public ProductSku findById(Long id) {
        return super.findOneById(id);
    }

    @Override
    public ProductSku findByBarCode(String barCode) {
        ProductSku sku = productSkuMapper.findByBarCode(barCode);
        return sku;
    }

    @Override
    public PageResult<ProductSkuDto> findBySearch(ProductSkuSearcher searcher, PageModel page) {
        PageResult<ProductSkuDto> pager = new PageResult<>(page);
        int totalCount = productSkuMapper.countBySearch(searcher);
        if (totalCount > 0) {
            List<ProductSku> list = productSkuMapper.findBySearch(searcher, page);
            List<ProductSkuDto> dtos = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                ProductSkuDto dto = new ProductSkuDto();
                BeanUtils.copyProperties(list.get(i), dto);
                dtos.add(dto);
            }
            pager.setTotalCount(totalCount);
            pager.setList(dtos);
        }
        return pager;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doInitPopStore(Long designerId) {
        return productSkuMapper.initPopStore(designerId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int updatePopStore(UploadProductBean bean, String operator) {
        int result = 0;
        ProductSku sku = productSkuMapper.findByBarCode(bean.getBarCode());
        if (sku != null) {
            result = productSkuMapper.updatePopStore(bean.getPopStore(), bean.getBarCode());
            if (result > 0) {
                productModuleSearchService.updateStore(sku.getProductId());
                JSONObject info = new JSONObject(true);
                info.put("操作", "POP库存修改，sku条码：" + bean.getBarCode() + "修改前POP库存：" + sku.getPopStore() + "修改后POP库存："
                        + bean.getPopStore());
                productLogService.insert(new ProductLog(operator, info.toJSONString(), ProductLogType.UpdateSku,
                        sku.getProductId(), sku.getId()));
            }
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int updateFlashStore(Long productId, Long[] skuIds, Integer[] flashStock, BigDecimal[] collagePrice,
                                BigDecimal[] flashPrice) {
        for (int i = 0; i < skuIds.length; i++) {
            if (collagePrice == null && flashPrice == null) {
                productSkuMapper.updateFlashStore(skuIds[i], flashStock[i], null, null);
            }
            if (collagePrice != null && collagePrice.length > 0) {
                productSkuMapper.updateFlashStore(skuIds[i], flashStock[i], collagePrice[i], null);
            }
            if (flashPrice != null && flashPrice.length > 0) {
                productSkuMapper.updateFlashStore(skuIds[i], flashStock[i], null, flashPrice[i]);
            }
        }
        redisTemplate.delete("product_flash_store_" + productId);
        return 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int doSyncSkuStore() {
        return productSkuMapper.doSyncSkuStore();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int doSumSkuStore() {
        return productSkuMapper.doSumSkuStore();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int doRepairFreezeStock() {
        return productSkuMapper.doRepairFreezeStock();
    }

    @Override
    public List<ProductSku> findByProductId(Long productId) {
        return productSkuMapper.findByProductId(productId);
    }

    @Override
    public int countByProductId(Long id) {
        return productSkuMapper.countByProductId(id);
    }

    @Override
    public List<Map<String, Object>> findStockByStore(String barCode, int primary) {
        return productSkuMapper.findStockByStore(barCode, primary);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int delete(Long skuId, String operator) {
        ProductSku sku = this.productSkuMapper.selectByPrimaryKey(skuId);
        int result = deleteById(skuId);
        if (result > 0) {
            List<ProductSku> skus = this.findByProductId(sku.getProductId());
            productService.updateSalesPropertyBySku(skus, sku.getProductId());
            productModuleSearchService.updateStore(sku.getProductId());
            productSkuStockSummaryService.delete(skuId);
            JSONObject info = new JSONObject();
            info.put("操作", "删除sku：" + sku.getBarCode());
            productLogService.insert(
                    new ProductLog(operator, info.toJSONString(), ProductLogType.UpdateSku, sku.getProductId(), skuId));
        }
        return result;
    }

    @Override
    public Map<String, Object> findBySalesProperties(Long productId, Long colorId, Long sizeId) {
        Map<String, Object> result = new HashMap<>();
        result.put("status", 1);
        List<ProductSku> skuList = this.findByProductId(productId);
        if (skuList == null || skuList.size() <= 0) {
            result.put("msg", "该商品无规格");
            result.put("status", -1);
            return result;
        }
        List<Map<String, Object>> colorList = new ArrayList<>();
        List<Map<String, Object>> sizeList = new ArrayList<>();
        for (ProductSku sku : skuList) {
            // 活动价格
            SearcherProduct searcherProduct = productSearcherQueryService.findById(productId.toString());
            if (searcherProduct != null) {
                sku.setPrice(this.getCurrentPrice(sku, searcherProduct));
            }
            // 颜色命中
            if (colorId != null && sku.getColorId() != 0 && colorId.equals(sku.getColorId())) {
                colorList.add(this.toProbablySku(sku));
                if (sku.getSizeId() == 0) {
                    // 单规格
                    this.toCertainlySku(result, sku);
                } else {
                    // 双规格
                    if (sizeId != null && sizeId.equals(sku.getSizeId())) {
                        this.toCertainlySku(result, sku);
                    }
                }
            }
            // 尺码命中
            if (sizeId != null && sku.getSizeId() != 0 && sizeId.equals(sku.getSizeId())) {
                sizeList.add(this.toProbablySku(sku));
            }
        }
        if (colorId != null) {
            result.put("Color", colorList);
        }
        if (sizeId != null) {
            result.put("Size", sizeList);
        }
        return result;
    }

    private BigDecimal getCurrentPrice(ProductSku sku, SearcherProduct searcherProduct) {
        if (!searcherProduct.isFlashPromotionOver()) {
            return sku.getFlashPrice();
        } else if (!searcherProduct.isPromotionOver()) {
            return searcherProduct.processPromotion(sku.getPrice(), sku.getaPrice());
        }
        return sku.getPrice();
    }

    @JsonIgnore
    private Map<String, Object> toProbablySku(ProductSku sku) {
        Map<String, Object> map = new HashMap<>();
        map.put("skuId", sku.getId());
        map.put("SN", sku.getSn());
        map.put("image", sku.getPic());
        map.put("stock", sku.getStore());
        map.put("Color", sku.getColorValue());
        map.put("Color_id", sku.getColorId());
        map.put("Size", sku.getSizeValue());
        map.put("Size_id", sku.getSizeId());
        // 普通
        map.put("store", sku.getAvailableStore());
        map.put("price", sku.getPrice());
        // 活动
        map.put("flashStore", sku.getAvailableFlashStore());
        map.put("flashPrice", sku.getFlashPrice());
        map.put("collagePrice", sku.getCollagePrice());
        return map;
    }

    @JsonIgnore
    private void toCertainlySku(Map<String, Object> result, ProductSku sku) {
        result.put("id", sku.getId());
        result.put("skuId", sku.getId());
        result.put("SN", sku.getSn());
        result.put("item", sku.getSn());
        result.put("image", sku.getPic());
        result.put("stock", sku.getStore());
        // 普通
        result.put("store", sku.getAvailableStore());
        result.put("price", sku.getPrice());
        // 活动
        result.put("flashStore", sku.getAvailableFlashStore());
        result.put("flashPrice", sku.getFlashPrice());
        result.put("collagePrice", sku.getCollagePrice());
    }

    @Override
    @TxTransaction
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int doFreezeStock(Long productId, Long skuId, int quantity) {
        int result = productSkuMapper.freezeStock(skuId, quantity);
        return result;
    }

    @Override
    @TxTransaction
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int doRevertStock(Long productId, Long skuId, int quantity, int pop) {
        int result = productSkuMapper.revertStock(skuId, quantity, pop);
        if (result > 0 && productId != null) {
            redisTemplate.delete("product_flash_store_" + productId);
        }
        return result;
    }

    @Override
    @TxTransaction
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int doCancelFreezeAndDeduceStock(Long productId, Long skuId, int quantity, int reopen) {
        ProductSku sku = productSkuMapper.selectByPrimaryKey(skuId);
        int pop = (sku.getStore() >= quantity ? 0 : 1);
        int result = productSkuMapper.cancelFreezeAndDeduceStock(skuId, quantity, pop, reopen);
        if (result > 0 && productId != null) {
            // 原先未售罄，当一个SKU售罄时，去更新商品库存
            if (quantity > 0 && (sku.getStore() + sku.getPopStore()) > 0
                    && (sku.getStore() + sku.getPopStore() - quantity) <= 0) {
                productModuleSearchService.updateStore(productId);
            }
        }
        return pop;
    }

    @Override
    @TxTransaction
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int doCancelFreezeAndDeduceFlashStock(Long productId, Long skuId, int quantity, int reopen) {
        ProductSku sku = productSkuMapper.selectByPrimaryKey(skuId);
        int pop = (sku.getStore() >= quantity ? 0 : 1);
        int result = productSkuMapper.cancelFreezeAndDeduceFlashStock(skuId, quantity, pop, reopen);
        if (result > 0 && productId != null) {
            redisTemplate.delete("product_flash_store_" + productId);
        }
        return pop;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int updateStatusByProduct(Long productId, Integer status, String adminName) {
        int result = productSkuMapper.updateStatusByProduct(productId, status, adminName);
        // if (result > 0) {
        // Product product = this.productMapper.selectByPrimaryKey(productId);
        // List<ProductSku> skuList =
        // productSkuMapper.findByProductId(productId);
        // for (ProductSku sku : skuList) {
        // if (product.getStatus() == 5 && status == 1) {
        // // 秒杀商品
        // this.initStore(sku.getId(), store);
        // }
        // }
        // }
        return result;
    }

    @Override
    public List<ProductSku> existSameBarCode(String barCode) {
        return productSkuMapper.findListByBarCode(barCode);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int updateExternalSnBySkuSn(String skuSn, String externalSn) {
        return productSkuMapper.updateExternalSnBySkuSn(skuSn, externalSn);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int updateAPrice(Long skuId, BigDecimal aPrice, String operator) {
        ProductSku oldSku = this.findById(skuId);
        if (oldSku.getOriginalCost().compareTo(aPrice) < 0 || oldSku.getPrice().compareTo(aPrice) < 0) {
            throw new BusinessException("销售价不能小于一口价");
        }
        int success = productSkuMapper.updateAPrice(skuId, aPrice);
        if (success > 0) {
            BigDecimal minAPrice = productSkuMapper.findAPrice(oldSku.getProductId());
            if (minAPrice.compareTo(aPrice) == 0) {
                productService.updateAPrice(oldSku.getProductId(), minAPrice);
            }
            productModuleSearchService.rebuild(oldSku.getProductId());
            // 添加操作日志
            JSONObject info = new JSONObject();
            info.put("操作", "修改一口价");
            info.put("修改前", oldSku.getaPrice());
            info.put("修改后", aPrice);
            productLogService.insert(new ProductLog(operator, info.toJSONString(), ProductLogType.UpdateSku,
                    oldSku.getProductId(), skuId));
        }
        return success;
    }

    @Override
    public PageResult<KaolaProduct> findByProductSource(String source, PageModel page) {
        PageResult<KaolaProduct> pager = new PageResult<>(page);
        Integer totalCount = productSkuMapper.countByProductSource(source);
        List<KaolaProduct> list = new ArrayList<>();
        if (totalCount > 0) {
            list = productSkuMapper.findByProductSource(source, page);
        }
        pager.setList(list);
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    public int countByProductSource(String source) {
        return productSkuMapper.countByProductSource(source);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int updateKaolaProductPopStore(String barCode, Integer popStore, BigDecimal kaolaPrice) {
        Integer result = productSkuMapper.updateKaolaByBarCode(popStore, barCode, kaolaPrice);
        if (result > 0) {
            ProductSku sku = this.findByBarCode(barCode);
            productModuleSearchService.updateStore(sku.getProductId());
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int updateSp1(Long id, String sp1, boolean updateSalesProperty, Long productId) {
        productSkuMapper.updateFieldById(id.intValue(), "sp1", sp1);
        if (updateSalesProperty) {
            List<ProductSku> list = productSkuMapper.findByProductId(productId);
            productService.updateSalesPropertyBySku(list, productId);
        }
        return 1;
    }

    @Override
    public int countKaolaByWaitingForRepair() {
        return productSkuMapper.countKaolaByWaitingForRepair();
    }

    @Override
    public PageResult<ProductSku> findKaolaByWaitingForRepair(PageModel page) {
        PageResult<ProductSku> pager = new PageResult<>(page);
        Integer totalCount = productSkuMapper.countKaolaByWaitingForRepair();
        List<ProductSku> list = new ArrayList<>();
        if (totalCount > 0) {
            list = productSkuMapper.findKaolaByWaitingForRepair(page);
        }
        pager.setList(list);
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    public List<ProductSku> findByBrandIdAndExternalSn(Long brandId, String externalSn) {
        return productSkuMapper.findByBrandIdAndExternalSn(brandId, externalSn);
    }

    @Override
    public PageResult<ProductSkuDto> findByStore(String storeCode, ProductSkuStockSearcher searcher, PageModel page) {
        PageResult<ProductSkuDto> pager = new PageResult<>(page);
        Integer totalCount = productSkuMapper.countByStore(storeCode, searcher);
        List<ProductSkuDto> list = new ArrayList<>();
        if (totalCount > 0) {
            list = productSkuMapper.findByStore(storeCode, searcher, page);
        }
        pager.setList(list);
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    public int countByStore(String storeCode, ProductSkuStockSearcher searcher) {
        return productSkuMapper.countByStore(storeCode, searcher);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int updateCaomeiSkuInfo(Long id, BigDecimal originalCost, BigDecimal price, BigDecimal costPrice,
                                   BigDecimal kaolaPrice, Integer popStore) {
        int success = productSkuMapper.updateCaomeiSkuInfo(id, originalCost, price, costPrice, kaolaPrice, popStore);
        ProductSku productSku = productSkuMapper.selectByPrimaryKey(id);
        if (success > 0) {
            productService.updatePriceBySku(productSku.getProductId());
        }
        // 添加操作日志
        JSONObject info = new JSONObject();
        info.put("操作", "草莓网价格更新");
        info.put("更新后价格", "originalCost:" + originalCost.doubleValue() + ",price:" + price.doubleValue()
                + ",kaolaPrice:" + kaolaPrice.doubleValue() + ",popStore:" + popStore);
        productLogService.insert(new ProductLog("定时器", info.toJSONString(), ProductLogType.UpdateSku,
                productSku.getProductId(), productSku.getId()));
        return success;
    }

    @Override
    @Caching(evict = {@CacheEvict(value = "product", key = "'product_'+#productId"),
            @CacheEvict(value = "product_store", key = "'product_flash_store_'+#productId")})
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int updateFlashPrice(Long productId, Long skuId, BigDecimal flashPrice, Integer flashStore) {
        int success = productSkuMapper.updateFlashPrice(skuId, flashPrice, flashStore);
        if (success > 0) {
            productService.updatePriceBySku(productId);
        }
        return success;
    }

}
