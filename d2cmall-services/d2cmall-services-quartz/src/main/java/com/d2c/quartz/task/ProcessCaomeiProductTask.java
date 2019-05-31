package com.d2c.quartz.task;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.order.third.caomei.CaomeiClient;
import com.d2c.order.third.caomei.CaomeiProduct;
import com.d2c.product.dto.ProductDto;
import com.d2c.product.model.Product.ProductSource;
import com.d2c.product.model.ProductSku;
import com.d2c.product.query.ProductSearcher;
import com.d2c.product.service.ProductService;
import com.d2c.product.service.ProductSkuService;
import com.d2c.quartz.task.base.BaseTask;
import com.d2c.quartz.task.base.EachPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class ProcessCaomeiProductTask extends BaseTask {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductSkuService productSkuService;

    @Scheduled(cron = "0 0 10,18 * * ?")
    public void execute() {
        if (properties.getDebug()) {
            return;
        }
        super.exec();
    }

    @Override
    public void execImpl() {
        processInfo();
    }

    private void processInfo() {
        try {
            this.processPager(10, new EachPage<ProductDto>() {
                @Override
                public int count() {
                    ProductSearcher productSearcher = new ProductSearcher();
                    productSearcher.setSource(ProductSource.CAOMEI.name());
                    return productService.countBySearch(productSearcher);
                }

                @Override
                public PageResult<ProductDto> search(PageModel page) {
                    ProductSearcher productSearcher = new ProductSearcher();
                    productSearcher.setSource(ProductSource.CAOMEI.name());
                    return productService.findBySearch(productSearcher, page);
                }

                @Override
                public boolean each(ProductDto object) {
                    try {
                        List<ProductSku> skus = productSkuService.findByProductId(object.getId());
                        for (ProductSku sku : skus) {
                            CaomeiProduct caomeiProduct = CaomeiClient.getInstance()
                                    .queryProd(sku.getBarCode().split("-")[0]);
                            if (caomeiProduct == null) {
                                continue;
                            }
                            if (caomeiProduct.getSellingPrice() == null
                                    || caomeiProduct.getSellingPrice().compareTo(new BigDecimal(0)) == 0) {// 价格可能会null或0
                                caomeiProduct.setSellingPrice(sku.getPrice());
                            }
                            if (caomeiProduct.getRefPrice() == null
                                    || caomeiProduct.getRefPrice().compareTo(new BigDecimal(0)) == 0) {
                                caomeiProduct.setRefPrice(caomeiProduct.getSellingPrice());
                            }
                            caomeiProduct.setSellingPrice(caomeiProduct.getSellingPrice().multiply(new BigDecimal(1.2))
                                    .setScale(2, BigDecimal.ROUND_HALF_UP));
                            caomeiProduct.setRefPrice(caomeiProduct.getRefPrice().multiply(new BigDecimal(1.2))
                                    .setScale(2, BigDecimal.ROUND_HALF_UP));
                            if (!comparePrice(caomeiProduct, sku)) {
                                productSkuService.updateCaomeiSkuInfo(sku.getId(), caomeiProduct.getRefPrice(),
                                        caomeiProduct.getSellingPrice(), caomeiProduct.getSellingPrice(),
                                        caomeiProduct.getSellingPrice(), caomeiProduct.getInvQty());
                            }
                            // 库存上下架
                            if (caomeiProduct.getInvQty() <= 0 && object.getMark() == 1) {
                                productService.updateMark(object.getId(), 0, "sys定时器");
                            } else if (caomeiProduct.getInvQty() > 0 && object.getMark() == 0) {
                                productService.updateMark(object.getId(), 1, "sys定时器");
                            }
                        }
                    } catch (Exception e) {
                        // 报错，拉不到也当下架
                        productService.updateMark(object.getId(), 0, "sys定时器");
                        logger.error(e.getMessage(), e);
                    }
                    return false;
                }
            });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private boolean comparePrice(CaomeiProduct caomeiProduct, ProductSku sku) {
        if (caomeiProduct.getRefPrice().compareTo(sku.getOriginalCost()) != 0) {
            return false;
        }
        if (caomeiProduct.getSellingPrice().compareTo(sku.getPrice()) != 0) {
            return false;
        }
        if (caomeiProduct.getSellingPrice().compareTo(sku.getKaolaPrice()) != 0) {
            return false;
        }
        if (caomeiProduct.getSellingPrice().compareTo(sku.getCostPrice()) != 0) {
            return false;
        }
        if (caomeiProduct.getInvQty().intValue() != sku.getPopStore().intValue()) {
            return false;
        }
        return true;
    }

}
