package com.d2c.flame.controller.callback;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.order.third.kaola.KaolaClient;
import com.d2c.product.model.ProductSku;
import com.d2c.product.service.ProductService;
import com.d2c.product.service.ProductSkuService;
import com.d2c.util.http.HttpUtil;
import com.d2c.util.string.StringUtil;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Controller
@RequestMapping("/kaola/callback")
public class KaolaController extends BaseController {

    public final static DateFormat dayFormat = new SimpleDateFormat("yyyy_MM");
    private static String newline = System.getProperty("line.separator");
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductSkuService productSkuService;

    public static void main(String[] args) {
        Map<String, Object> params = new HashMap<>();
        params.put("info",
                "{&quot;changeTypes&quot;:[9],&quot;channel&quot;:16262,&quot;goodsId&quot;:1630413,&quot;skuId&quot;:&quot;1630413-e196767f8469b2ff5e0744b32fc79c3b&quot;,&quot;updateTime&quot;:1529994691765}");
        String aa = StringEscapeUtils.unescapeHtml((String) params.get("info"));
        System.out.println(aa);
        params.put("info", aa);
        String result = HttpUtil.sendPostHttps("http://localhost:8081/kaola/callback/goods", params, null);
        System.out.println(JSON.parseObject(result).getInteger("recCode"));
    }

    @ResponseBody
    @RequestMapping(value = "/goods", method = RequestMethod.POST)
    public JSONObject goods(String info) {
        toLog(info);
        if (StringUtil.isNotBlank(info)) {
            JSONObject obj = JSON.parseObject(StringEscapeUtils.unescapeHtml(info));
            JSONArray array = obj.getJSONArray("changeTypes");
            // String goodsId = obj.getString("goodsId");
            String skuId = obj.getString("skuId");
            try {
                JSONObject goods = KaolaClient.getInstance().queryGoodsInfoById(skuId, null).getJSONObject("goodsInfo");
                ProductSku productSku = productSkuService.findByBarCode(skuId);
                if (productSku != null && -1 != productSku.getStatus()) {
                    if (array.contains(8)) {
                        productSkuService.updateKaolaProductPopStore(skuId, goods.getInteger("store"),
                                goods.getBigDecimal("marketPrice"));
                    }
                    if (!(array.size() == 1 && array.contains(8))) {
                        productService.updateKaolaProduct(productSku.getProductId(), goods.getBigDecimal("marketPrice"),
                                goods.getInteger("onlineStatus"), goods.getInteger("isFreeTax"),
                                goods.getInteger("isFreeShipping"));
                    }
                }
            } catch (Exception e) {
                logger.info(e.getMessage(), e);
            }
        }
        JSONObject obj = new JSONObject();
        obj.put("recCode", 200);
        obj.put("recMeg", "操作成功");
        return obj;
    }

    private void toLog(final String params) {
        ExecutorService executor = Executors.newFixedThreadPool(1);
        executor.submit(new Runnable() {
            @Override
            public void run() {
                String fileName = "/mnt/kaola/" + "kaola_callback_" + dayFormat.format(new Date()) + ".log";
                File file = new File(fileName);
                FileWriter writer = null;
                try {
                    if (!file.getParentFile().exists()) {
                        file.getParentFile().mkdirs();
                    }
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    writer = new FileWriter(file, true);
                    writer.write(params + newline);
                    writer.flush();
                } catch (IOException e) {
                } finally {
                    if (writer != null) {
                        try {
                            writer.close();
                        } catch (IOException e) {
                        }
                    }
                }
            }
        });
        executor.shutdown();
    }

}
