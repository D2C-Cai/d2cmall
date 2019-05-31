package com.d2c.product.third.productai;

import cn.productai.api.core.DefaultProductAIClient;
import cn.productai.api.core.DefaultProfile;
import cn.productai.api.core.IProfile;
import cn.productai.api.core.IWebClient;
import cn.productai.api.pai.entity.dataset.DataSetSingleAddByImageUrlRequest;
import cn.productai.api.pai.entity.search.ImageSearchByImageUrlRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.product.model.Product;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

/**
 * 以图搜图
 *
 * @author Administrator
 */
@Component
public class ProductAIClinet {

    private static final Log logger = LogFactory.getLog(ProductAIClinet.class);
    private static IWebClient client;
    private static String accessKeyId = "77475ec14d13b7341d2550fd6d57071c";
    private static String secretKey = "c363fdf375ccc61d4d20e25fae89b602";

    static {
        if (client == null) {
            IProfile profile = new DefaultProfile(accessKeyId, secretKey, "1", null);
            client = new DefaultProductAIClient(profile);
        }
    }

    /**
     * 数据集Id
     */
    @Value("${AI_IMAGE_SET_ID}")
    private String imageSetId = "poltc0tr";
    /**
     * 服务id
     */
    @Value("${AI_SERVICE_ID}")
    private String serviceId = "9ublo724";

    private static String sendPOST(String url, TreeMap<String, String> parameterMap) {
        HttpPost httpRequst = new HttpPost(url);
        httpRequst.setHeader("x-ca-version", "1.0");
        httpRequst.setHeader("x-ca-accesskeyid", accessKeyId);
        // 创建请求对象
        List<BasicNameValuePair> params = new ArrayList<>();
        Iterator<Entry<String, String>> it = parameterMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
            params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        // 发送请求
        try {
            httpRequst.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            CloseableHttpClient httpclient = HttpClients.createDefault();
            CloseableHttpResponse httpResponse = httpclient.execute(httpRequst);
            HttpEntity httpEntity = httpResponse.getEntity();
            String result = EntityUtils.toString((org.apache.http.HttpEntity) httpEntity);// 取出应答字符串
            logger.info("[[Thirdpart Order][result] " + result);
            return result;
        } catch (Exception e) {
            logger.error("[[Thirdpart Order][result] " + e);
        }
        return null;
    }

    public static void main(String[] args) {
        String url = "https://api.productai.cn/custom_training/8ge7xo5q";
        TreeMap<String, String> parameterMap = new TreeMap<>();
        parameterMap.put("url", "https://img.d2c.cn/2018/08/28/0339274518eade1d8a34abb5b988e8d2c67112.jpg");
        System.out.println(JSON.parseObject(sendPOST(url, parameterMap)).getJSONArray("results").toJSONString());
    }

    /**
     * 以url搜图
     *
     * @param url
     * @param page
     * @return
     * @throws IOException
     * @throws Exception
     */
    public PageResult<String> searchByUrl(String url, PageModel page) throws IOException, Exception {
        PageResult<String> pager = new PageResult<>(page);
        try {
            ImageSearchByImageUrlRequest request = new ImageSearchByImageUrlRequest(serviceId);
            request.setUrl(url);
            request.setCount(page.getPageSize());
            // 没有返回总共有多少，只能app多请求一次，默认是按相似度排的
            request.getOptions().put("page", page.getStartNumber() + " ");
            String response = client.getResponse(request).getResponseJsonString();
            JSONObject obj = JSONObject.parseObject(response);
            if (obj != null) {
                Set<String> set = new LinkedHashSet<>();
                JSONArray array = (JSONArray) obj.get("results");
                for (int i = 0; i < array.size(); i++) {
                    set.add(array.getJSONObject(i).get("metadata").toString());
                }
                List<String> list = new ArrayList<>();
                list.addAll(set);
                pager.setList(list);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return pager;
    }

    /**
     * 上传商品图片
     *
     * @param product
     * @throws Exception
     */
    public int uploadPic(Product product) throws Exception {
        if (product != null) {
            try {
                ArrayList<String> tagList = new ArrayList<>();
                tagList.add(JSONObject.parseObject(product.getTopCategory()).getString("name"));
                JSONArray categoryArray = JSONArray.parseArray(product.getProductCategory());
                tagList.add(((JSONObject) categoryArray.get(categoryArray.size() - 1)).getString("name"));
                DataSetSingleAddByImageUrlRequest addImageRequest = new DataSetSingleAddByImageUrlRequest(imageSetId,
                        tagList, product.getId().toString());
                for (String url : product.getProductImageList()) {
                    try {
                        addImageRequest.imageUrl = "http://img.d2c.cn/" + url;
                        client.getResponse(addImageRequest);
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return 1;
    }

    /**
     * 图片识别
     *
     * @param pic
     * @return
     */
    public JSONArray imageRecognition(String pic) {
        String url = "https://api.productai.cn/custom_training/8ge7xo5q";
        TreeMap<String, String> parameterMap = new TreeMap<>();
        parameterMap.put("url", pic);
        try {
            JSONArray array = JSON.parseObject(sendPOST(url, parameterMap)).getJSONArray("results");
            return array;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return new JSONArray();
    }

}
