package com.d2c.product.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.product.dao.ProductOptionMapper;
import com.d2c.product.model.Brand;
import com.d2c.product.model.ProductOption;
import com.d2c.product.model.ProductSkuOption;
import com.d2c.product.query.ProductOptionSearcher;
import com.d2c.product.support.SkuCodeBean;
import com.d2c.util.string.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("productOptionService")
@Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS, readOnly = true)
public class ProductOptionServiceImpl extends ListServiceImpl<ProductOption> implements ProductOptionService {

    @Autowired
    private ProductOptionMapper productOptionMapper;
    @Autowired
    private ProductSkuOptionService productSkuOptionService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private ProductService productService;

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public ProductOption insert(ProductOption productOption, List<ProductSkuOption> skus) {
        this.processPrice(productOption, skus);
        if (1 == productOption.getPop()) {
            productOption.setExternalSn(productOption.getInernalSn());
        }
        Brand brand = brandService.findById(productOption.getDesignerId());
        if (brand != null) {
            productOption.setDesignerCode(brand.getCode());
            productOption.setDesignerName(brand.getName());
        }
        productOption = this.save(productOption);
        // 生成SKU
        for (ProductSkuOption productSku : skus) {
            String sn = productSku.getSn();
            if (StringUtils.isBlank(sn) || StringUtils.isBlank(productSku.getSp1())) {
                throw new BusinessException("SKU数据异常！");
            }
            if (productSku.getOriginalCost().compareTo(productSku.getPrice()) < 0) {
                throw new BusinessException("吊牌价不能小于销售价");
            }
            if (productSku.getPrice().compareTo(productSku.getaPrice()) < 0) {
                throw new BusinessException("销售价不能小于一口价");
            }
            productSku.setProductId(productOption.getId());
            // POP商品不校验款号和条码之间的关系
            if (1 == productOption.getPop()) {
                productSku.setInernalSn(productOption.getInernalSn());
                productSku.setBarCode(productSku.getSn());
            } else {
                SkuCodeBean parser = productService.getSKUCodeParser(productOption.getSp1GroupId(),
                        productOption.getSp2GroupId(), sn);
                if (parser != null) {
                    if (!parser.getProductSn().equals(productOption.getInernalSn())) {
                        throw new BusinessException("条码为：" + sn + "的SKU不属于此商品！");
                    }
                    productSku.setInernalSn(parser.getProductSn());
                    productSku.setBarCode(parser.getBarCode());
                } else {
                    throw new BusinessException("条码为：" + sn + "的SKU解析错误！");
                }
            }
            productSku.setName(productOption.getName());
            productSku.setStatus(1);
            productSku = productSkuOptionService.insert(productSku);
        }
        updateSalesPropertyBySku(skus, productOption.getId());
        return productOption;
    }

    protected void processPrice(ProductOption product, List<ProductSkuOption> skuSet) {
        product.setMinPrice(getMinimumPrice(skuSet));
        product.setMaxPrice(getMaximumPrice(skuSet));
        product.setOriginalPrice(getMaximumOriginalPrice(skuSet));
        product.setaPrice(getMinimumAPrice(skuSet));
        product.setCostPrice(getMaximumCostPrice(skuSet));
    }

    /**
     * 获取该商品一组SKU中最低价格
     *
     * @param product
     * @param productSKUList
     * @return
     */
    private BigDecimal getMinimumPrice(List<ProductSkuOption> productSKUList) {
        BigDecimal min = null;
        for (ProductSkuOption sku : productSKUList) {
            if (sku.getPrice() == null) {
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
     * 获取该商品一组SKU中最高成本价格
     *
     * @param skuSet
     * @return
     */
    private BigDecimal getMaximumCostPrice(List<ProductSkuOption> productSKUList) {
        BigDecimal max = null;
        for (ProductSkuOption sku : productSKUList) {
            if (sku.getCostPrice() == null) {
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
     * 获取该商品一组SKU中最高价格
     *
     * @param product
     * @param productSKUList
     * @return
     */
    private BigDecimal getMaximumPrice(List<ProductSkuOption> productSKUList) {
        BigDecimal max = null;
        for (ProductSkuOption sku : productSKUList) {
            if (sku.getPrice() == null) {
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
    private BigDecimal getMaximumOriginalPrice(List<ProductSkuOption> productSKUList) {
        BigDecimal max = null;
        for (ProductSkuOption sku : productSKUList) {
            if (sku.getOriginalCost() == null) {
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
     * @param product
     * @param productSKUList
     * @return
     */
    private BigDecimal getMinimumAPrice(List<ProductSkuOption> productSKUList) {
        BigDecimal min = null;
        for (ProductSkuOption sku : productSKUList) {
            if (sku.getaPrice() == null) {
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

    @Override
    public PageResult<ProductOption> findBySearch(ProductOptionSearcher productSearcher, PageModel page) {
        PageResult<ProductOption> pager = new PageResult<ProductOption>(page);
        Integer totalCount = productOptionMapper.countBySearcher(productSearcher);
        List<ProductOption> list = new ArrayList<ProductOption>();
        if (totalCount > 0) {
            list = productOptionMapper.findBySearcher(productSearcher, page);
        }
        pager.setTotalCount(totalCount);
        pager.setList(list);
        return pager;
    }

    @Override
    public int countBySearch(ProductOptionSearcher productSearcher) {
        return productOptionMapper.countBySearcher(productSearcher);
    }

    @Override
    public ProductOption findById(Long id) {
        return productOptionMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int update(ProductOption productOption, List<ProductSkuOption> skus) {
        productOption.setName(productOption.getName() == null ? null
                : productOption.getName().replaceAll("\"", "&quot;").replaceAll("'", "&apos;"));
        // 更新商品
        this.processPrice(productOption, skus);
        if (1 == productOption.getPop()) {
            productOption.setExternalSn(productOption.getInernalSn());
        }
        // 如果设计师不支持售后或货到付款，商品也设置成不支持
        Brand brand = brandService.findById(productOption.getDesignerId());
        if (brand != null) {
            productOption.setDesignerCode(brand.getCode());
            productOption.setDesignerName(brand.getName());
        }
        int success = this.updateNotNull(productOption);
        // 保存SKU
        for (ProductSkuOption productSku : skus) {
            if (productSku.getId() == null || productSku.getId() == 0) {
                // 新增SKU
                String sn = productSku.getSn();
                if (StringUtils.isBlank(sn) || StringUtils.isBlank(productSku.getSp1())) {
                    throw new BusinessException("SKU数据异常！");
                }
                if (productSku.getOriginalCost().compareTo(productSku.getPrice()) < 0) {
                    throw new BusinessException("吊牌价不能小于销售价");
                }
                if (productSku.getPrice().compareTo(productSku.getaPrice()) < 0) {
                    throw new BusinessException("销售价不能小于一口价");
                }
                productSku.setProductId(productOption.getId());
                if (1 == productOption.getPop()) {
                    productSku.setInernalSn(productOption.getInernalSn());
                    productSku.setBarCode(productSku.getSn());
                } else {
                    SkuCodeBean parser = productService.getSKUCodeParser(productOption.getSp1GroupId(),
                            productOption.getSp2GroupId(), sn);
                    if (parser != null) {
                        // POP商品不校验款号和条码之间的关系
                        if (!parser.getProductSn().equals(productOption.getInernalSn())) {
                            throw new BusinessException("条码为：" + sn + "的SKU不属于此商品！");
                        }
                        productSku.setInernalSn(parser.getProductSn());
                        productSku.setBarCode(parser.getBarCode());
                    } else {
                        throw new BusinessException("条码为：" + sn + "的SKU解析错误！");
                    }
                }
                productSku.setName(productOption.getName());
                productSku.setStatus(1);
                productSku = productSkuOptionService.insert(productSku);
            } else {
                // 修改SKU
                String sn = productSku.getSn();
                if (StringUtils.isBlank(sn) || StringUtils.isBlank(productSku.getSp1())) {
                    throw new BusinessException("SKU数据异常！");
                }
                productSku.setExternalSn(productOption.getExternalSn());
                productSku.setProductId(productOption.getId());
                if (1 == productOption.getPop()) {
                    productSku.setInernalSn(productOption.getInernalSn());
                    productSku.setBarCode(productSku.getSn());
                } else {
                    SkuCodeBean parser = productService.getSKUCodeParser(productOption.getSp1GroupId(),
                            productOption.getSp2GroupId(), sn);
                    if (parser != null) {
                        // POP商品不校验款号和条码之间的关系
                        if (!parser.getProductSn().equals(productOption.getInernalSn())) {
                            throw new BusinessException("条码为：" + sn + "的SKU不属于此商品！");
                        }
                        productSku.setInernalSn(parser.getProductSn());
                        productSku.setBarCode(parser.getBarCode());
                    } else {
                        throw new BusinessException("条码为：" + sn + "的SKU解析错误！");
                    }
                }
                productSkuOptionService.update(productSku);
            }
        }
        updateSalesPropertyBySku(skus, productOption.getId());
        return success;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateVideoById(Long productId, String video) {
        return this.updateFieldById(productId.intValue(), "video", video);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doSubmit(Long id) {
        return this.updateFieldById(id.intValue(), "mark", 1);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doRefuse(Long id, String refuseReason) {
        return productOptionMapper.doRefuse(id, refuseReason);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doMarkSuccess(Long id) {
        return this.updateFieldById(id.intValue(), "mark", 8);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int updatePriceBySku(Long productId) {
        List<ProductSkuOption> skuSet = productSkuOptionService.findByProductId(productId);
        ProductOption product = new ProductOption();
        product.setId(productId);
        this.processPrice(product, skuSet);
        int success = productOptionMapper.updatePrice(product);
        return success;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int updateSalesPropertyBySku(List<ProductSkuOption> skuSet, Long productId) {
        Map<Long, JSONObject> sp1Map = new HashMap<Long, JSONObject>();
        Map<Long, JSONObject> sp2Map = new HashMap<Long, JSONObject>();
        for (ProductSkuOption sku : skuSet) {
            if (StringUtil.isNotBlank(sku.getSp1())) {
                JSONObject sp1 = JSONObject.parseObject(sku.getSp1());
                sp1Map.put(sp1.getLong("id"), sp1);
            }
            if (StringUtil.isNotBlank(sku.getSp2())) {
                JSONObject sp2 = JSONObject.parseObject(sku.getSp2());
                sp2Map.put(sp2.getLong("id"), sp2);
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
        int result = productOptionMapper.updateSalesPropertyById(productId, salesProperty.toString());
        return result;
    }

}
