package com.d2c.product.service;

import com.alibaba.fastjson.JSONArray;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;

public interface ProductThirdService {

    PageResult<String> searchByUrl(String url, PageModel page);

    String[] pullNetworkImage(String[] imageUrls);

    JSONArray imageRecognition(String url);

}
