package com.d2c.product.service;

import com.alibaba.fastjson.JSONArray;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.product.third.productai.ProductAIClinet;
import com.d2c.product.third.upyun.UpYunClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("productThirdService")
public class ProductThirdServiceImpl implements ProductThirdService {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ProductAIClinet productAIClinet;

    public PageResult<String> searchByUrl(String url, PageModel page) {
        try {
            return productAIClinet.searchByUrl(url, page);
        } catch (Exception e) {
            logger.error("url搜图失败...", e);
            return null;
        }
    }

    public String[] pullNetworkImage(String[] imageUrls) {
        return UpYunClient.getInstance().pullNetworkImage(imageUrls);
    }

    @Override
    public JSONArray imageRecognition(String url) {
        return productAIClinet.imageRecognition(url);
    }

}
