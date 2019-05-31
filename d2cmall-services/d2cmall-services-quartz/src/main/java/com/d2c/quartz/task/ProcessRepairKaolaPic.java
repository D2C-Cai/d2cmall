package com.d2c.quartz.task;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.logger.service.UpyunTaskService;
import com.d2c.product.dto.ProductDto;
import com.d2c.product.model.ProductSku;
import com.d2c.product.service.ProductService;
import com.d2c.product.service.ProductSkuService;
import com.d2c.product.service.ProductThirdService;
import com.d2c.quartz.task.base.BaseTask;
import com.d2c.quartz.task.base.EachPage;
import com.d2c.util.string.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 修复考拉商品图片
 *
 * @author Lain
 */
@Component
public class ProcessRepairKaolaPic extends BaseTask {

    @Autowired
    private ProductService productService;
    @Autowired
    private UpyunTaskService upyunTaskService;
    @Autowired
    private ProductSkuService productSkuService;
    @Autowired
    private ProductThirdService productThirdService;

    @Scheduled(fixedDelay = 60 * 1000 * 60)
    public void execute() {
        if (properties.getDebug()) {
            return;
        }
        super.exec();
    }

    @Override
    public void execImpl() {
        this.doRepairKaolaProduct();
        this.doRepaireSku();
    }

    private void doRepairKaolaProduct() {
        try {
            this.processPager(100, new EachPage<ProductDto>() {
                @Override
                public int count() {
                    return productService.countKaolaByWaitingForRepair();
                }

                @Override
                public PageResult<ProductDto> search(PageModel page) {
                    return productService.findKaolaByWaitingForRepair(page);
                }

                @Override
                public boolean each(ProductDto object) {
                    int result = 0;
                    try {
                        String[] pics = object.getIntroPic().split(",");
                        String[] taskIds = productThirdService.pullNetworkImage(pics);
                        try {
                            Thread.sleep(10000);
                        } catch (InterruptedException e) {
                        }
                        List<String> list = upyunTaskService.findPicsByTaskIds(taskIds);
                        if (list != null) {
                            String introPic = StringUtils.join(list.toArray(), ",");
                            object.setIntroPic(introPic);
                            List<ProductSku> productSKUSet = object.getSkuList();
                            for (int i = 0; i < list.size() && i < productSKUSet.size(); i++) {
                                String sp1 = productSKUSet.get(i).getSp1();
                                if (StringUtil.isNotBlank(sp1)) {
                                    JSONObject sp1Json = JSONObject.parseObject(sp1);
                                    sp1Json.put("img", list.get(i));
                                    productSKUSet.get(i).setSp1(sp1Json.toString());
                                }
                            }
                        }
                        result = productService.doRepairKaolaPic(object);
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }
                    return result > 0 ? true : false;
                }
            });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 针对漏网的sp1
     */
    private void doRepaireSku() {
        try {
            this.processPager(100, new EachPage<ProductSku>() {
                @Override
                public int count() {
                    return productSkuService.countKaolaByWaitingForRepair();
                }

                @Override
                public PageResult<ProductSku> search(PageModel page) {
                    return productSkuService.findKaolaByWaitingForRepair(page);
                }

                @Override
                public boolean each(ProductSku object) {
                    int result = 0;
                    try {
                        String[] pics = object.getPic().split(",");
                        String[] taskIds = productThirdService.pullNetworkImage(pics);
                        try {
                            Thread.sleep(10000);
                        } catch (InterruptedException e) {
                        }
                        List<String> list = upyunTaskService.findPicsByTaskIds(taskIds);
                        if (list != null) {
                            JSONObject sp1Json = JSONObject.parseObject(object.getSp1());
                            sp1Json.put("img", list.get(0));
                            result = productSkuService.updateSp1(object.getId(), sp1Json.toJSONString(), true,
                                    object.getProductId());
                        }
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }
                    return result > 0 ? true : false;
                }
            });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

}
