package com.d2c.order.third.kaola;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d2c.common.core.helper.SpringHelper;
import com.d2c.order.property.OrderProperties;
import com.d2c.order.third.kaola.enums.CloseReason;
import com.d2c.order.third.kaola.model.OrderItem;
import com.d2c.order.third.kaola.model.UserInfo;
import com.d2c.order.third.kaola.reponse.KaolaOrderItemStatus;
import com.d2c.order.third.kaola.reponse.KaolaOrderStatus;
import com.d2c.order.third.kaola.reponse.TrackLogistics;
import com.d2c.util.date.DateUtil;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.*;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KaolaClient {

    private static OrderProperties orderProperties = new OrderProperties();
    private static KaolaClient instance = null;
    private static Logger logger = Logger.getLogger("KaolaClient");

    private KaolaClient() {
        orderProperties = SpringHelper.getBean(OrderProperties.class);
    }

    public static KaolaClient getInstance() {
        if (instance == null) {
            instance = new KaolaClient();
        }
        return instance;
    }

    /**
     * @param parameterMap 请求参数
     * @param method       请求方法
     * @return
     * @throws Exception
     */
    private static String send(TreeMap<String, String> parameterMap, String method) throws Exception {
        // 请求参数
        String url = orderProperties.getKaolaApiUrl() + method;
        String source = orderProperties.getKaolaChannelId();
        String sign_method = "md5";
        String appKey = orderProperties.getKaolaAppKey();
        String appSecret = orderProperties.getKaolaSecretKey();
        String version = "1.0";
        Date nowDate = new Date();
        parameterMap.put("timestamp", DateUtil.second2str(nowDate));
        parameterMap.put("v", version);
        parameterMap.put("sign_method", sign_method);
        parameterMap.put("app_key", appKey);
        parameterMap.put("channelId", source);
        parameterMap.put("source", source);
        // 获取签名
        String sign = APIUtil.createSign(appSecret, parameterMap);
        // 创建请求对象
        List<BasicNameValuePair> params = new ArrayList<>();
        Iterator<Entry<String, String>> it = parameterMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
            params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        params.add(new BasicNameValuePair("sign", sign));
        HttpPost httpRequst = new HttpPost(url);
        // 发送请求
        try {
            httpRequst.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            CloseableHttpClient httpclient = HttpClients.createDefault();
            CloseableHttpResponse httpResponse = httpclient.execute(httpRequst);
            HttpEntity httpEntity = httpResponse.getEntity();
            String result = EntityUtils.toString((org.apache.http.HttpEntity) httpEntity);// 取出应答字符串
            logger.log(Level.INFO, "[Thirdpart CallAPIDemo][result] " + result);
            return result;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "[[Thirdpart Order][result] " + e);
            throw new Exception(e.getMessage());
        }
    }

    public static void main(String[] args) throws Exception {
        KaolaClient client = KaolaClient.getInstance();
        // client.queryAllGoodsId();
        // closeOrder("111", CloseReason.BestPlan, "111");
        // System.out.println(client.queryGoodsInfoById("58735638-b3496e25c625803b858635a0a354b71f",
        // null));
        // client.querySkuIdsByGoodsIds(Arrays.asList(new String[] {
        // "10204244-40ff49789ce88f957fcc41b900807275", "" }));
        // client.bookorder("A1234", userInfo, orderItemList);
        // client.payOrder("A123");
        // client.closeOrder("A1234", CloseReason.BestPlan,
        // "201806051638GORDER85538081");
        // client.cancelOrder("A1234", CloseReason.BestPlan,
        // "201806051638GORDER85538081", "");
        client.queryGoodsInfoByIds(Arrays.asList(
                new String[]{"58735564-a1e1cb77107513f4ede2eca6a0721cd6", "10204244-40ff49789ce88f957fcc41b900807275",
                        "10220018-40ff49789ce88f957fcc41b900807275", "1226800-9f09660c69ecafe91e2d3a395db2968e",
                        "1226800-e975675bba97f5dfaff78ce18a22464f", "13285677-ecc4090b639c47f89b453980923afb8e",
                        "15928264-88971fe79fba958fc151a190dca3544b", "171563-e94a8ae8eac15e81c7286f10453bbb4f",
                        "1720994-0d67186392123e7a0e2dc1806a87960e", "1720994-2276d9c199147d3f35de06c82769cb55",
                        "1720994-c5ca4fc077a065f880e0570dd4461a0e", "1720994-d2b959467464fec365fdd1361ee88d30",
                        "22286339-ecc4090b639c47f89b453980923afb8e", "2308470-7fd974ff55d3326a4bd8e83e1de77116",
                        "2308470-bc14ad1a6e79c137f2373d8f00ffd744", "258809-10053b3032cd03140dae78b155cba464",
                        "260107-2afb4f4d30b85b7266474331c92b188a", "26147305-ecc4090b639c47f89b453980923afb8e",
                        "26765638-4ec8f5bd59928cbb85efb15c1ca8b184", "26765638-6605a66e15648a893913be172d2fec55",
                        "26765638-86614c59eb2d26502e5219d91e1184a5", "26765638-8f00d6340b92ff29fce656d7a689d48a",
                        "26765638-a1e1cb77107513f4ede2eca6a0721cd6", "26765638-b3496e25c625803b858635a0a354b71f",
                        "26812253-ecc4090b639c47f89b453980923afb8e", "26827147-4ec8f5bd59928cbb85efb15c1ca8b184",
                        "26827147-6605a66e15648a893913be172d2fec55", "26827147-86614c59eb2d26502e5219d91e1184a5",
                        "26827147-8f00d6340b92ff29fce656d7a689d48a", "26827147-a1e1cb77107513f4ede2eca6a0721cd6",
                        "26827147-b3496e25c625803b858635a0a354b71f", "30162245-ecc4090b639c47f89b453980923afb8e",
                        "37555388-ecc4090b639c47f89b453980923afb8e", "38293969-ecc4090b639c47f89b453980923afb8e",
                        "38312619-ecc4090b639c47f89b453980923afb8e", "38706767-ecc4090b639c47f89b453980923afb8e",
                        "3872829-ecc4090b639c47f89b453980923afb8e", "39086049-ecc4090b639c47f89b453980923afb8e",
                        "4179394-5925f2fa39cc18da9aa4d2ad5ee4dd5b", "44697558-09a7d97ee9f97f73dde58c571a1862fe",
                        "44697558-2308c416091d831c0f3be7b9cd82af20", "44700857-09a7d97ee9f97f73dde58c571a1862fe",
                        "44700857-2308c416091d831c0f3be7b9cd82af20", "44708794-09a7d97ee9f97f73dde58c571a1862fe",
                        "44708794-2308c416091d831c0f3be7b9cd82af20", "45290665-4ec8f5bd59928cbb85efb15c1ca8b184",
                        "45290665-6605a66e15648a893913be172d2fec55", "45290665-86614c59eb2d26502e5219d91e1184a5",
                        "45290665-8f00d6340b92ff29fce656d7a689d48a", "45290665-a1e1cb77107513f4ede2eca6a0721cd6",
                        "45290665-b3496e25c625803b858635a0a354b71f", "45290668-4ec8f5bd59928cbb85efb15c1ca8b184",
                        "45290668-6605a66e15648a893913be172d2fec55", "45290668-86614c59eb2d26502e5219d91e1184a5",
                        "45290668-8f00d6340b92ff29fce656d7a689d48a", "45290668-b3496e25c625803b858635a0a354b71f",
                        "45416473-09a7d97ee9f97f73dde58c571a1862fe", "45416473-2308c416091d831c0f3be7b9cd82af20",
                        "45445312-ecc4090b639c47f89b453980923afb8e", "45447913-ecc4090b639c47f89b453980923afb8e",
                        "45449048-ecc4090b639c47f89b453980923afb8e", "45454897-ecc4090b639c47f89b453980923afb8e",
                        "45454903-ecc4090b639c47f89b453980923afb8e", "45470818-ecc4090b639c47f89b453980923afb8e",
                        "45611667-09a7d97ee9f97f73dde58c571a1862fe", "45611667-2308c416091d831c0f3be7b9cd82af20",
                        "46380391-ecc4090b639c47f89b453980923afb8e", "46408414-ecc4090b639c47f89b453980923afb8e",
                        "46408415-ecc4090b639c47f89b453980923afb8e", "46408416-ecc4090b639c47f89b453980923afb8e",
                        "46408418-ecc4090b639c47f89b453980923afb8e", "46408420-ecc4090b639c47f89b453980923afb8e",
                        "46408516-ecc4090b639c47f89b453980923afb8e", "46408519-ecc4090b639c47f89b453980923afb8e",
                        "46408553-ecc4090b639c47f89b453980923afb8e", "46408554-ecc4090b639c47f89b453980923afb8e",
                        "46408557-ecc4090b639c47f89b453980923afb8e", "46408597-ecc4090b639c47f89b453980923afb8e",
                        "46408598-ecc4090b639c47f89b453980923afb8e", "46408630-ecc4090b639c47f89b453980923afb8e",
                        "46408633-ecc4090b639c47f89b453980923afb8e", "46408634-ecc4090b639c47f89b453980923afb8e",
                        "46408635-ecc4090b639c47f89b453980923afb8e", "46408667-ecc4090b639c47f89b453980923afb8e",
                        "46408669-ecc4090b639c47f89b453980923afb8e", "46408670-ecc4090b639c47f89b453980923afb8e",
                        "46408671-ecc4090b639c47f89b453980923afb8e", "46408727-ecc4090b639c47f89b453980923afb8e",
                        "46412555-ecc4090b639c47f89b453980923afb8e", "46577032-ecc4090b639c47f89b453980923afb8e",
                        "46658931-ecc4090b639c47f89b453980923afb8e", "46995418-ecc4090b639c47f89b453980923afb8e",
                        "47004025-ecc4090b639c47f89b453980923afb8e", "47336800-ecc4090b639c47f89b453980923afb8e",
                        "47336801-ecc4090b639c47f89b453980923afb8e", "47336802-ecc4090b639c47f89b453980923afb8e",
                        "47336803-09a7d97ee9f97f73dde58c571a1862fe", "47336803-2308c416091d831c0f3be7b9cd82af20",
                        "47336803-c098bae5a4e7dd31fca6c999eb921810", "47336804-09a7d97ee9f97f73dde58c571a1862fe",
                        "47336804-2308c416091d831c0f3be7b9cd82af20", "47336804-c098bae5a4e7dd31fca6c999eb921810",
                        "47336805-09a7d97ee9f97f73dde58c571a1862fe", "47336805-2308c416091d831c0f3be7b9cd82af20",
                        "47336805-c098bae5a4e7dd31fca6c999eb921810", "47336806-09a7d97ee9f97f73dde58c571a1862fe",
                        "47336806-2308c416091d831c0f3be7b9cd82af20", "47336806-c098bae5a4e7dd31fca6c999eb921810",
                        "47336820-ecc4090b639c47f89b453980923afb8e", "47336821-ecc4090b639c47f89b453980923afb8e",
                        "47336822-09a7d97ee9f97f73dde58c571a1862fe", "47336822-2308c416091d831c0f3be7b9cd82af20",
                        "47336823-09a7d97ee9f97f73dde58c571a1862fe", "47336823-2308c416091d831c0f3be7b9cd82af20",
                        "47336824-09a7d97ee9f97f73dde58c571a1862fe", "47336824-2308c416091d831c0f3be7b9cd82af20",
                        "47337204-4ec8f5bd59928cbb85efb15c1ca8b184", "47337204-6605a66e15648a893913be172d2fec55",
                        "47337204-86614c59eb2d26502e5219d91e1184a5", "47337204-8f00d6340b92ff29fce656d7a689d48a",
                        "47337204-a1e1cb77107513f4ede2eca6a0721cd6", "47337204-b3496e25c625803b858635a0a354b71f",
                        "47337435-09a7d97ee9f97f73dde58c571a1862fe", "47337435-2308c416091d831c0f3be7b9cd82af20",
                        "47353513-2308c416091d831c0f3be7b9cd82af20", "47416385-ecc4090b639c47f89b453980923afb8e",
                        "47427583-ecc4090b639c47f89b453980923afb8e", "47427647-ecc4090b639c47f89b453980923afb8e",
                        "47427648-ecc4090b639c47f89b453980923afb8e", "47427649-ecc4090b639c47f89b453980923afb8e",
                        "47427787-ecc4090b639c47f89b453980923afb8e", "47428117-ecc4090b639c47f89b453980923afb8e",
                        "47494160-ecc4090b639c47f89b453980923afb8e", "47495223-09a7d97ee9f97f73dde58c571a1862fe",
                        "47495223-2308c416091d831c0f3be7b9cd82af20", "47495246-ecc4090b639c47f89b453980923afb8e",
                        "47495248-ecc4090b639c47f89b453980923afb8e", "47533411-2308c416091d831c0f3be7b9cd82af20",
                        "47534478-ecc4090b639c47f89b453980923afb8e", "47535444-ecc4090b639c47f89b453980923afb8e",
                        "47558323-ecc4090b639c47f89b453980923afb8e", "47747866-ecc4090b639c47f89b453980923afb8e",
                        "48146924-09a7d97ee9f97f73dde58c571a1862fe", "48146924-2308c416091d831c0f3be7b9cd82af20",
                        "48221681-ecc4090b639c47f89b453980923afb8e", "48239592-ecc4090b639c47f89b453980923afb8e",
                        "48251136-ecc4090b639c47f89b453980923afb8e", "48723297-ecc4090b639c47f89b453980923afb8e",
                        "48816291-ecc4090b639c47f89b453980923afb8e", "48816992-4ec8f5bd59928cbb85efb15c1ca8b184",
                        "48816992-6605a66e15648a893913be172d2fec55", "48816992-86614c59eb2d26502e5219d91e1184a5",
                        "48816992-8f00d6340b92ff29fce656d7a689d48a", "48816992-a1e1cb77107513f4ede2eca6a0721cd6",
                        "48816992-b3496e25c625803b858635a0a354b71f", "48822054-ecc4090b639c47f89b453980923afb8e",
                        "48889796-ecc4090b639c47f89b453980923afb8e", "48889797-ecc4090b639c47f89b453980923afb8e",
                        "48889798-ecc4090b639c47f89b453980923afb8e", "48905979-ecc4090b639c47f89b453980923afb8e",
                        "48905980-ecc4090b639c47f89b453980923afb8e", "49003244-ecc4090b639c47f89b453980923afb8e",
                        "49216422-ecc4090b639c47f89b453980923afb8e", "49218730-ecc4090b639c47f89b453980923afb8e",
                        "49218731-ecc4090b639c47f89b453980923afb8e", "49218732-ecc4090b639c47f89b453980923afb8e",
                        "49219006-09a7d97ee9f97f73dde58c571a1862fe", "49219006-2308c416091d831c0f3be7b9cd82af20",
                        "49220293-ecc4090b639c47f89b453980923afb8e", "49244406-ecc4090b639c47f89b453980923afb8e",
                        "49244887-ecc4090b639c47f89b453980923afb8e", "49249470-ecc4090b639c47f89b453980923afb8e",
                        "49253386-ecc4090b639c47f89b453980923afb8e", "49254408-ecc4090b639c47f89b453980923afb8e",
                        "49255695-ecc4090b639c47f89b453980923afb8e", "49276467-ecc4090b639c47f89b453980923afb8e",
                        "49276778-ecc4090b639c47f89b453980923afb8e", "49395660-ecc4090b639c47f89b453980923afb8e",
                        "49395663-ecc4090b639c47f89b453980923afb8e", "49395665-ecc4090b639c47f89b453980923afb8e",
                        "49548960-ecc4090b639c47f89b453980923afb8e", "49549280-4ec8f5bd59928cbb85efb15c1ca8b184",
                        "49549280-6605a66e15648a893913be172d2fec55", "49549280-86614c59eb2d26502e5219d91e1184a5",
                        "49549280-8f00d6340b92ff29fce656d7a689d48a", "49549280-a1e1cb77107513f4ede2eca6a0721cd6",
                        "49549280-b3496e25c625803b858635a0a354b71f", "49549282-4ec8f5bd59928cbb85efb15c1ca8b184",
                        "49549282-6605a66e15648a893913be172d2fec55", "49549282-86614c59eb2d26502e5219d91e1184a5",
                        "49549282-8f00d6340b92ff29fce656d7a689d48a", "49549282-a1e1cb77107513f4ede2eca6a0721cd6",
                        "49549282-b3496e25c625803b858635a0a354b71f", "49549284-09eb271f56c864492e0917a3115ff153",
                        "49549284-355d44186c3ed2f0b87b491fbea83a67", "49549284-42d52b693302bcfe28e04c2de8dba13f",
                        "49549284-4ec8f5bd59928cbb85efb15c1ca8b184", "49549284-590a4d2c0d0ebd9978d4d075fe8aff26",
                        "49549284-6605a66e15648a893913be172d2fec55", "49549284-6ffe5b6b9e3d25f6a0f508e04bf3c015",
                        "49549284-75fdf3ae98944dce60d1997f1ca78bd1", "49549284-77ce0873f5f41663c824cc7a7c760813",
                        "49549284-7cd78a23bf1d725973358bb6f41e55ba", "49549284-86614c59eb2d26502e5219d91e1184a5",
                        "49549284-8f00d6340b92ff29fce656d7a689d48a", "49549284-a0ebe4538b0303f1f626d253b3ee0d01",
                        "49549284-a1e1cb77107513f4ede2eca6a0721cd6", "49549284-b3496e25c625803b858635a0a354b71f",
                        "49549284-de2ce1ccfd728f77b3657343acd27c4d", "49549284-e79becfef9f478e613ea32facdfa7d50",
                        "49549284-fccc129d1fdae6c6bd82ee84f2242288", "49549340-ecc4090b639c47f89b453980923afb8e",
                        "49549341-ecc4090b639c47f89b453980923afb8e", "49549342-ecc4090b639c47f89b453980923afb8e",
                        "49549343-ecc4090b639c47f89b453980923afb8e", "49549344-ecc4090b639c47f89b453980923afb8e",
                        "49549345-ecc4090b639c47f89b453980923afb8e", "49549346-ecc4090b639c47f89b453980923afb8e",
                        "49549347-ecc4090b639c47f89b453980923afb8e", "49549348-ecc4090b639c47f89b453980923afb8e",
                        "49549383-ecc4090b639c47f89b453980923afb8e", "49549384-ecc4090b639c47f89b453980923afb8e",
                        "49549385-ecc4090b639c47f89b453980923afb8e", "49549386-ecc4090b639c47f89b453980923afb8e",
                        "49549387-ecc4090b639c47f89b453980923afb8e", "49549388-ecc4090b639c47f89b453980923afb8e",
                        "49549389-ecc4090b639c47f89b453980923afb8e", "49549393-ecc4090b639c47f89b453980923afb8e",
                        "49549394-ecc4090b639c47f89b453980923afb8e", "49549395-ecc4090b639c47f89b453980923afb8e",
                        "49549396-ecc4090b639c47f89b453980923afb8e", "49549707-ecc4090b639c47f89b453980923afb8e",
                        "49549723-ecc4090b639c47f89b453980923afb8e", "49549749-09a7d97ee9f97f73dde58c571a1862fe",
                        "49549749-2308c416091d831c0f3be7b9cd82af20", "49549758-4ec8f5bd59928cbb85efb15c1ca8b184",
                        "49549758-6605a66e15648a893913be172d2fec55", "49549758-86614c59eb2d26502e5219d91e1184a5",
                        "49549758-8f00d6340b92ff29fce656d7a689d48a", "49549758-a1e1cb77107513f4ede2eca6a0721cd6",
                        "49549758-b3496e25c625803b858635a0a354b71f", "49549820-ecc4090b639c47f89b453980923afb8e",
                        "49549821-ecc4090b639c47f89b453980923afb8e", "49549822-ecc4090b639c47f89b453980923afb8e",
                        "49549827-ecc4090b639c47f89b453980923afb8e", "49549831-ecc4090b639c47f89b453980923afb8e",
                        "49549832-ecc4090b639c47f89b453980923afb8e", "49549834-ecc4090b639c47f89b453980923afb8e",
                        "49549837-ecc4090b639c47f89b453980923afb8e", "49549838-ecc4090b639c47f89b453980923afb8e",
                        "49549839-ecc4090b639c47f89b453980923afb8e", "49549840-ecc4090b639c47f89b453980923afb8e",
                        "49549841-ecc4090b639c47f89b453980923afb8e", "49549842-ecc4090b639c47f89b453980923afb8e",
                        "49549851-ecc4090b639c47f89b453980923afb8e", "49549852-ecc4090b639c47f89b453980923afb8e",
                        "49549887-ecc4090b639c47f89b453980923afb8e", "49549890-ecc4090b639c47f89b453980923afb8e",
                        "49549891-ecc4090b639c47f89b453980923afb8e", "49549892-ecc4090b639c47f89b453980923afb8e",
                        "49549893-ecc4090b639c47f89b453980923afb8e", "49549897-ecc4090b639c47f89b453980923afb8e",
                        "49556629-ecc4090b639c47f89b453980923afb8e", "49556648-ecc4090b639c47f89b453980923afb8e",
                        "49556781-ecc4090b639c47f89b453980923afb8e", "49559093-ecc4090b639c47f89b453980923afb8e",
                        "49559199-ecc4090b639c47f89b453980923afb8e", "49559303-ecc4090b639c47f89b453980923afb8e",
                        "49559535-ecc4090b639c47f89b453980923afb8e", "49560480-ecc4090b639c47f89b453980923afb8e",
                        "49561001-ecc4090b639c47f89b453980923afb8e", "49561121-ecc4090b639c47f89b453980923afb8e",
                        "49561321-ecc4090b639c47f89b453980923afb8e", "49568321-ecc4090b639c47f89b453980923afb8e",
                        "49570278-ecc4090b639c47f89b453980923afb8e", "49614836-077c197d3e595ce8a763df6876ca965e",
                        "49614836-09eb271f56c864492e0917a3115ff153", "49614836-355d44186c3ed2f0b87b491fbea83a67",
                        "49614836-37db835a8015ff9c5970e565090c598f", "49614836-3a4dee5a48e73b7af6dda08d0f0445b7",
                        "49614836-42d52b693302bcfe28e04c2de8dba13f", "49614836-49a6f3f6ef19cbf3991feae58845971b",
                        "49614836-4ec8f5bd59928cbb85efb15c1ca8b184", "49614836-590a4d2c0d0ebd9978d4d075fe8aff26",
                        "49614836-6605a66e15648a893913be172d2fec55", "49614836-6ffe5b6b9e3d25f6a0f508e04bf3c015",
                        "49614836-75fdf3ae98944dce60d1997f1ca78bd1", "49614836-77ce0873f5f41663c824cc7a7c760813",
                        "49614836-7cd78a23bf1d725973358bb6f41e55ba", "49614836-86614c59eb2d26502e5219d91e1184a5",
                        "49614836-881dd31804afbba1eff178065c56798e", "49614836-8f00d6340b92ff29fce656d7a689d48a",
                        "49614836-a0ebe4538b0303f1f626d253b3ee0d01", "49614836-a1e1cb77107513f4ede2eca6a0721cd6",
                        "49614836-a5dda73904d35b5f4b4c4c555ea428cc", "49614836-b3496e25c625803b858635a0a354b71f",
                        "49614836-c3de7e8d703381240bf26be146dbe71e", "49614836-ca4b936a974a0ca03201f4bcc3394609",
                        "49614836-de2ce1ccfd728f77b3657343acd27c4d", "49614836-e79becfef9f478e613ea32facdfa7d50",
                        "49614836-fbb4fbd358fc6ad034b17ee57affd37c", "49614836-fccc129d1fdae6c6bd82ee84f2242288",
                        "49614838-ecc4090b639c47f89b453980923afb8e", "49614839-ecc4090b639c47f89b453980923afb8e",
                        "49614841-ecc4090b639c47f89b453980923afb8e", "49614842-ecc4090b639c47f89b453980923afb8e",
                        "49614843-ecc4090b639c47f89b453980923afb8e", "49614844-ecc4090b639c47f89b453980923afb8e",
                        "49614845-ecc4090b639c47f89b453980923afb8e", "49614846-ecc4090b639c47f89b453980923afb8e",
                        "49614847-ecc4090b639c47f89b453980923afb8e", "49614848-ecc4090b639c47f89b453980923afb8e",
                        "49614849-ecc4090b639c47f89b453980923afb8e", "49614851-ecc4090b639c47f89b453980923afb8e",
                        "49614852-ecc4090b639c47f89b453980923afb8e", "49614853-ecc4090b639c47f89b453980923afb8e",
                        "49614854-ecc4090b639c47f89b453980923afb8e", "49614855-ecc4090b639c47f89b453980923afb8e",
                        "49614856-ecc4090b639c47f89b453980923afb8e", "49614857-ecc4090b639c47f89b453980923afb8e",
                        "49614858-ecc4090b639c47f89b453980923afb8e", "49614859-ecc4090b639c47f89b453980923afb8e",
                        "49614860-ecc4090b639c47f89b453980923afb8e", "49614861-ecc4090b639c47f89b453980923afb8e",
                        "49614862-ecc4090b639c47f89b453980923afb8e", "49614863-ecc4090b639c47f89b453980923afb8e",
                        "49614864-ecc4090b639c47f89b453980923afb8e", "49614865-ecc4090b639c47f89b453980923afb8e",
                        "49614866-ecc4090b639c47f89b453980923afb8e", "49614867-ecc4090b639c47f89b453980923afb8e",
                        "49614868-ecc4090b639c47f89b453980923afb8e", "49614869-ecc4090b639c47f89b453980923afb8e",
                        "49614870-ecc4090b639c47f89b453980923afb8e", "49614871-ecc4090b639c47f89b453980923afb8e",
                        "49614872-ecc4090b639c47f89b453980923afb8e", "49614873-ecc4090b639c47f89b453980923afb8e",
                        "49614874-ecc4090b639c47f89b453980923afb8e", "49614875-ecc4090b639c47f89b453980923afb8e",
                        "49614876-ecc4090b639c47f89b453980923afb8e", "49614877-ecc4090b639c47f89b453980923afb8e",
                        "49614878-ecc4090b639c47f89b453980923afb8e", "49614879-ecc4090b639c47f89b453980923afb8e",
                        "49614880-ecc4090b639c47f89b453980923afb8e", "49614881-ecc4090b639c47f89b453980923afb8e",
                        "49614882-ecc4090b639c47f89b453980923afb8e", "49614883-ecc4090b639c47f89b453980923afb8e",
                        "49614884-ecc4090b639c47f89b453980923afb8e", "49614885-ecc4090b639c47f89b453980923afb8e",
                        "49614886-ecc4090b639c47f89b453980923afb8e", "49614887-ecc4090b639c47f89b453980923afb8e",
                        "49614888-ecc4090b639c47f89b453980923afb8e", "49614889-ecc4090b639c47f89b453980923afb8e",
                        "49614890-ecc4090b639c47f89b453980923afb8e", "49614891-ecc4090b639c47f89b453980923afb8e",
                        "49614892-ecc4090b639c47f89b453980923afb8e", "49614893-ecc4090b639c47f89b453980923afb8e",
                        "49614894-ecc4090b639c47f89b453980923afb8e", "49614895-ecc4090b639c47f89b453980923afb8e",
                        "49614896-ecc4090b639c47f89b453980923afb8e", "49614897-ecc4090b639c47f89b453980923afb8e",
                        "49614898-ecc4090b639c47f89b453980923afb8e", "49614902-ecc4090b639c47f89b453980923afb8e",
                        "49614903-ecc4090b639c47f89b453980923afb8e", "49615364-ecc4090b639c47f89b453980923afb8e",
                        "49615366-ecc4090b639c47f89b453980923afb8e", "49615368-ecc4090b639c47f89b453980923afb8e",
                        "49615380-ecc4090b639c47f89b453980923afb8e", "49615402-ecc4090b639c47f89b453980923afb8e",
                        "49615474-09a7d97ee9f97f73dde58c571a1862fe", "49615474-2308c416091d831c0f3be7b9cd82af20",
                        "49615474-c098bae5a4e7dd31fca6c999eb921810", "49615474-d0d4e28b9ab37e048f225ba969f09dbf",
                        "49615474-e3f0e140df5068317d103edeb04fc668", "49615475-ecc4090b639c47f89b453980923afb8e",
                        "49615500-ecc4090b639c47f89b453980923afb8e", "49615501-ecc4090b639c47f89b453980923afb8e",
                        "49615502-ecc4090b639c47f89b453980923afb8e", "49615503-ecc4090b639c47f89b453980923afb8e",
                        "49615504-ecc4090b639c47f89b453980923afb8e", "49615505-ecc4090b639c47f89b453980923afb8e",
                        "49615508-ecc4090b639c47f89b453980923afb8e", "49615509-ecc4090b639c47f89b453980923afb8e",
                        "49615528-ecc4090b639c47f89b453980923afb8e", "49615529-ecc4090b639c47f89b453980923afb8e",
                        "49615530-ecc4090b639c47f89b453980923afb8e", "49615531-ecc4090b639c47f89b453980923afb8e",
                        "49615532-ecc4090b639c47f89b453980923afb8e", "49615533-ecc4090b639c47f89b453980923afb8e",
                        "49615538-ecc4090b639c47f89b453980923afb8e", "49615539-ecc4090b639c47f89b453980923afb8e",
                        "49615585-ecc4090b639c47f89b453980923afb8e", "49615665-ecc4090b639c47f89b453980923afb8e",
                        "49615917-ecc4090b639c47f89b453980923afb8e", "49615918-ecc4090b639c47f89b453980923afb8e",
                        "49616100-ecc4090b639c47f89b453980923afb8e", "49616191-ecc4090b639c47f89b453980923afb8e",
                        "49616192-ecc4090b639c47f89b453980923afb8e", "49616193-ecc4090b639c47f89b453980923afb8e",
                        "49616241-ecc4090b639c47f89b453980923afb8e", "49616297-ecc4090b639c47f89b453980923afb8e",
                        "49616343-ecc4090b639c47f89b453980923afb8e", "49616344-ecc4090b639c47f89b453980923afb8e",
                        "49616345-ecc4090b639c47f89b453980923afb8e", "49616346-ecc4090b639c47f89b453980923afb8e",
                        "49616347-ecc4090b639c47f89b453980923afb8e", "50334077-ecc4090b639c47f89b453980923afb8e",
                        "51793000-09a7d97ee9f97f73dde58c571a1862fe", "51793000-2308c416091d831c0f3be7b9cd82af20",
                        "51823263-ecc4090b639c47f89b453980923afb8e", "51826199-ecc4090b639c47f89b453980923afb8e",
                        "51828420-ecc4090b639c47f89b453980923afb8e", "51828446-ecc4090b639c47f89b453980923afb8e",
                        "51828547-ecc4090b639c47f89b453980923afb8e", "51828549-ecc4090b639c47f89b453980923afb8e",
                        "51828648-ecc4090b639c47f89b453980923afb8e", "51828946-ecc4090b639c47f89b453980923afb8e",
                        "51828947-ecc4090b639c47f89b453980923afb8e", "51828948-ecc4090b639c47f89b453980923afb8e",
                        "51828949-ecc4090b639c47f89b453980923afb8e", "51828950-ecc4090b639c47f89b453980923afb8e",
                        "51828951-ecc4090b639c47f89b453980923afb8e", "51828952-ecc4090b639c47f89b453980923afb8e",
                        "51828953-ecc4090b639c47f89b453980923afb8e", "51828954-ecc4090b639c47f89b453980923afb8e",
                        "51828955-ecc4090b639c47f89b453980923afb8e", "51828956-ecc4090b639c47f89b453980923afb8e",
                        "51828957-ecc4090b639c47f89b453980923afb8e", "51828958-ecc4090b639c47f89b453980923afb8e",
                        "51828959-ecc4090b639c47f89b453980923afb8e", "51828960-ecc4090b639c47f89b453980923afb8e",
                        "51828961-ecc4090b639c47f89b453980923afb8e", "51828963-ecc4090b639c47f89b453980923afb8e",
                        "51828965-ecc4090b639c47f89b453980923afb8e", "51828966-ecc4090b639c47f89b453980923afb8e",
                        "51828969-ecc4090b639c47f89b453980923afb8e", "51828971-ecc4090b639c47f89b453980923afb8e",
                        "51828973-ecc4090b639c47f89b453980923afb8e", "51828976-ecc4090b639c47f89b453980923afb8e",
                        "51828977-ecc4090b639c47f89b453980923afb8e", "51828978-ecc4090b639c47f89b453980923afb8e",
                        "51828979-ecc4090b639c47f89b453980923afb8e", "51828980-ecc4090b639c47f89b453980923afb8e",
                        "51828981-ecc4090b639c47f89b453980923afb8e", "51828982-ecc4090b639c47f89b453980923afb8e",
                        "51828983-ecc4090b639c47f89b453980923afb8e", "51828984-ecc4090b639c47f89b453980923afb8e",
                        "51828986-ecc4090b639c47f89b453980923afb8e", "51828987-ecc4090b639c47f89b453980923afb8e",
                        "51828990-ecc4090b639c47f89b453980923afb8e", "51828992-ecc4090b639c47f89b453980923afb8e",
                        "51828995-ecc4090b639c47f89b453980923afb8e", "51828996-ecc4090b639c47f89b453980923afb8e",
                        "51828998-ecc4090b639c47f89b453980923afb8e", "51829000-ecc4090b639c47f89b453980923afb8e",
                        "51829002-ecc4090b639c47f89b453980923afb8e", "51829003-ecc4090b639c47f89b453980923afb8e",
                        "51829004-ecc4090b639c47f89b453980923afb8e", "51829005-ecc4090b639c47f89b453980923afb8e",
                        "51829006-ecc4090b639c47f89b453980923afb8e", "51829007-ecc4090b639c47f89b453980923afb8e",
                        "51829008-ecc4090b639c47f89b453980923afb8e", "51829009-ecc4090b639c47f89b453980923afb8e",
                        "51829010-ecc4090b639c47f89b453980923afb8e", "51829011-ecc4090b639c47f89b453980923afb8e",
                        "51829012-ecc4090b639c47f89b453980923afb8e", "51829013-ecc4090b639c47f89b453980923afb8e",
                        "51829014-ecc4090b639c47f89b453980923afb8e", "51829015-ecc4090b639c47f89b453980923afb8e",
                        "51829016-ecc4090b639c47f89b453980923afb8e", "51829017-ecc4090b639c47f89b453980923afb8e",
                        "51829018-ecc4090b639c47f89b453980923afb8e", "51829019-ecc4090b639c47f89b453980923afb8e",
                        "51829020-ecc4090b639c47f89b453980923afb8e", "51829021-ecc4090b639c47f89b453980923afb8e",
                        "51829022-ecc4090b639c47f89b453980923afb8e", "51829023-ecc4090b639c47f89b453980923afb8e",
                        "51829025-ecc4090b639c47f89b453980923afb8e", "51829026-ecc4090b639c47f89b453980923afb8e",
                        "51829027-ecc4090b639c47f89b453980923afb8e", "51829028-ecc4090b639c47f89b453980923afb8e",
                        "51829029-ecc4090b639c47f89b453980923afb8e", "51829030-ecc4090b639c47f89b453980923afb8e",
                        "51829031-ecc4090b639c47f89b453980923afb8e", "51829032-ecc4090b639c47f89b453980923afb8e",
                        "51829033-ecc4090b639c47f89b453980923afb8e", "51829034-ecc4090b639c47f89b453980923afb8e",
                        "51829035-ecc4090b639c47f89b453980923afb8e", "51829036-ecc4090b639c47f89b453980923afb8e",
                        "51829037-ecc4090b639c47f89b453980923afb8e", "51829038-ecc4090b639c47f89b453980923afb8e",
                        "51829039-ecc4090b639c47f89b453980923afb8e", "51829040-ecc4090b639c47f89b453980923afb8e",
                        "51829041-ecc4090b639c47f89b453980923afb8e", "51829042-ecc4090b639c47f89b453980923afb8e",
                        "51829043-ecc4090b639c47f89b453980923afb8e", "51829044-ecc4090b639c47f89b453980923afb8e",
                        "51829045-ecc4090b639c47f89b453980923afb8e", "51840741-ecc4090b639c47f89b453980923afb8e",
                        "51840742-09a7d97ee9f97f73dde58c571a1862fe", "51840742-2308c416091d831c0f3be7b9cd82af20",
                        "51840743-09a7d97ee9f97f73dde58c571a1862fe", "51840743-2308c416091d831c0f3be7b9cd82af20",
                        "51840744-09a7d97ee9f97f73dde58c571a1862fe", "51840744-2308c416091d831c0f3be7b9cd82af20",
                        "51840745-09a7d97ee9f97f73dde58c571a1862fe", "51840745-2308c416091d831c0f3be7b9cd82af20",
                        "51840746-09a7d97ee9f97f73dde58c571a1862fe", "51840746-2308c416091d831c0f3be7b9cd82af20",
                        "51840747-09a7d97ee9f97f73dde58c571a1862fe", "51840747-2308c416091d831c0f3be7b9cd82af20",
                        "51840748-09a7d97ee9f97f73dde58c571a1862fe", "51840748-2308c416091d831c0f3be7b9cd82af20",
                        "51840749-09a7d97ee9f97f73dde58c571a1862fe", "51840749-2308c416091d831c0f3be7b9cd82af20",
                        "51840750-09a7d97ee9f97f73dde58c571a1862fe", "51840750-2308c416091d831c0f3be7b9cd82af20",
                        "51840751-09a7d97ee9f97f73dde58c571a1862fe", "51840751-2308c416091d831c0f3be7b9cd82af20",
                        "51840752-09a7d97ee9f97f73dde58c571a1862fe", "51840752-2308c416091d831c0f3be7b9cd82af20",
                        "51840753-09a7d97ee9f97f73dde58c571a1862fe", "51840753-2308c416091d831c0f3be7b9cd82af20",
                        "51840754-09a7d97ee9f97f73dde58c571a1862fe", "51840754-2308c416091d831c0f3be7b9cd82af20",
                        "51840755-09a7d97ee9f97f73dde58c571a1862fe", "51840755-2308c416091d831c0f3be7b9cd82af20",
                        "51840756-09a7d97ee9f97f73dde58c571a1862fe", "51840756-2308c416091d831c0f3be7b9cd82af20",
                        "51840757-09a7d97ee9f97f73dde58c571a1862fe", "51840757-2308c416091d831c0f3be7b9cd82af20",
                        "51840758-09a7d97ee9f97f73dde58c571a1862fe", "51840758-2308c416091d831c0f3be7b9cd82af20",
                        "51840759-09a7d97ee9f97f73dde58c571a1862fe", "51840759-2308c416091d831c0f3be7b9cd82af20",
                        "51840760-4ec8f5bd59928cbb85efb15c1ca8b184", "51840760-6605a66e15648a893913be172d2fec55",
                        "51840760-86614c59eb2d26502e5219d91e1184a5", "51840760-8f00d6340b92ff29fce656d7a689d48a",
                        "51840760-a1e1cb77107513f4ede2eca6a0721cd6", "51840760-b3496e25c625803b858635a0a354b71f",
                        "51840761-4ec8f5bd59928cbb85efb15c1ca8b184", "51840761-6605a66e15648a893913be172d2fec55",
                        "51840761-86614c59eb2d26502e5219d91e1184a5", "51840761-8f00d6340b92ff29fce656d7a689d48a",
                        "51840761-a1e1cb77107513f4ede2eca6a0721cd6", "51840761-b3496e25c625803b858635a0a354b71f",
                        "51840762-4ec8f5bd59928cbb85efb15c1ca8b184", "51840762-6605a66e15648a893913be172d2fec55",
                        "51840762-86614c59eb2d26502e5219d91e1184a5", "51840762-8f00d6340b92ff29fce656d7a689d48a",
                        "51840762-a1e1cb77107513f4ede2eca6a0721cd6", "51840762-b3496e25c625803b858635a0a354b71f",
                        "51840763-4ec8f5bd59928cbb85efb15c1ca8b184", "51840763-6605a66e15648a893913be172d2fec55",
                        "51840763-86614c59eb2d26502e5219d91e1184a5", "51840763-8f00d6340b92ff29fce656d7a689d48a",
                        "51840763-a1e1cb77107513f4ede2eca6a0721cd6", "51840763-b3496e25c625803b858635a0a354b71f",
                        "51840764-4ec8f5bd59928cbb85efb15c1ca8b184", "51840764-6605a66e15648a893913be172d2fec55",
                        "51840764-86614c59eb2d26502e5219d91e1184a5", "51840764-8f00d6340b92ff29fce656d7a689d48a",
                        "51840764-a1e1cb77107513f4ede2eca6a0721cd6", "51840764-b3496e25c625803b858635a0a354b71f",
                        "51840765-4ec8f5bd59928cbb85efb15c1ca8b184", "51840765-6605a66e15648a893913be172d2fec55",
                        "51840765-86614c59eb2d26502e5219d91e1184a5", "51840765-8f00d6340b92ff29fce656d7a689d48a",
                        "51840765-a1e1cb77107513f4ede2eca6a0721cd6", "51840765-b3496e25c625803b858635a0a354b71f",
                        "51840766-4ec8f5bd59928cbb85efb15c1ca8b184", "51840767-4ec8f5bd59928cbb85efb15c1ca8b184",
                        "51840767-6605a66e15648a893913be172d2fec55", "51840767-86614c59eb2d26502e5219d91e1184a5",
                        "51840767-8f00d6340b92ff29fce656d7a689d48a", "51840767-a1e1cb77107513f4ede2eca6a0721cd6",
                        "51840767-b3496e25c625803b858635a0a354b71f", "51840768-4ec8f5bd59928cbb85efb15c1ca8b184",
                        "51840768-6605a66e15648a893913be172d2fec55", "51840768-86614c59eb2d26502e5219d91e1184a5",
                        "51840768-8f00d6340b92ff29fce656d7a689d48a", "51840768-a1e1cb77107513f4ede2eca6a0721cd6",
                        "51840768-b3496e25c625803b858635a0a354b71f", "51840769-4ec8f5bd59928cbb85efb15c1ca8b184",
                        "51840769-6605a66e15648a893913be172d2fec55", "51840769-86614c59eb2d26502e5219d91e1184a5",
                        "51840769-8f00d6340b92ff29fce656d7a689d48a", "51840769-a1e1cb77107513f4ede2eca6a0721cd6",
                        "51840769-b3496e25c625803b858635a0a354b71f", "51840770-4ec8f5bd59928cbb85efb15c1ca8b184",
                        "51840770-6605a66e15648a893913be172d2fec55", "51840770-86614c59eb2d26502e5219d91e1184a5",
                        "51840770-8f00d6340b92ff29fce656d7a689d48a", "51840770-a1e1cb77107513f4ede2eca6a0721cd6",
                        "51840770-b3496e25c625803b858635a0a354b71f", "51840771-4ec8f5bd59928cbb85efb15c1ca8b184",
                        "51840771-6605a66e15648a893913be172d2fec55", "51840771-86614c59eb2d26502e5219d91e1184a5",
                        "51840771-8f00d6340b92ff29fce656d7a689d48a", "51840771-a1e1cb77107513f4ede2eca6a0721cd6",
                        "51840771-b3496e25c625803b858635a0a354b71f", "51840772-4ec8f5bd59928cbb85efb15c1ca8b184",
                        "51840772-6605a66e15648a893913be172d2fec55", "51840772-86614c59eb2d26502e5219d91e1184a5",
                        "51840772-8f00d6340b92ff29fce656d7a689d48a", "51840772-a1e1cb77107513f4ede2eca6a0721cd6",
                        "51840772-b3496e25c625803b858635a0a354b71f", "51840773-4ec8f5bd59928cbb85efb15c1ca8b184",
                        "51840773-6605a66e15648a893913be172d2fec55", "51840773-86614c59eb2d26502e5219d91e1184a5",
                        "51840773-8f00d6340b92ff29fce656d7a689d48a", "51840773-a1e1cb77107513f4ede2eca6a0721cd6",
                        "51840773-b3496e25c625803b858635a0a354b71f", "51840774-4ec8f5bd59928cbb85efb15c1ca8b184",
                        "51840774-6605a66e15648a893913be172d2fec55", "51840774-86614c59eb2d26502e5219d91e1184a5",
                        "51840774-8f00d6340b92ff29fce656d7a689d48a", "51840774-a1e1cb77107513f4ede2eca6a0721cd6",
                        "51840774-b3496e25c625803b858635a0a354b71f", "51840775-4ec8f5bd59928cbb85efb15c1ca8b184",
                        "51840775-6605a66e15648a893913be172d2fec55", "51840775-86614c59eb2d26502e5219d91e1184a5",
                        "51840775-8f00d6340b92ff29fce656d7a689d48a", "51840775-a1e1cb77107513f4ede2eca6a0721cd6",
                        "51840775-b3496e25c625803b858635a0a354b71f", "51841182-09a7d97ee9f97f73dde58c571a1862fe",
                        "51841182-2308c416091d831c0f3be7b9cd82af20", "51841182-460c992d969c85a2f00e5de2cb43cf9c",
                        "51841182-468ee5c006308ffa018d9a5aeede86ea", "51841182-6f04aaa51ddfbe2aad2e5d139528bc95",
                        "51841182-7f0c7a58d36e3f72e80ab29b630bfb03", "51841182-c098bae5a4e7dd31fca6c999eb921810",
                        "51841182-d0d4e28b9ab37e048f225ba969f09dbf", "51841182-e3f0e140df5068317d103edeb04fc668",
                        "51841182-f4191faeede738b930443b59cb653f4d", "51841183-09a7d97ee9f97f73dde58c571a1862fe",
                        "51841183-2308c416091d831c0f3be7b9cd82af20", "51841183-c098bae5a4e7dd31fca6c999eb921810",
                        "51841184-077c197d3e595ce8a763df6876ca965e", "51841184-09eb271f56c864492e0917a3115ff153",
                        "51841184-355d44186c3ed2f0b87b491fbea83a67", "51841184-37db835a8015ff9c5970e565090c598f",
                        "51841184-3a4dee5a48e73b7af6dda08d0f0445b7", "51841184-42d52b693302bcfe28e04c2de8dba13f",
                        "51841184-49a6f3f6ef19cbf3991feae58845971b", "51841184-4ec8f5bd59928cbb85efb15c1ca8b184",
                        "51841184-590a4d2c0d0ebd9978d4d075fe8aff26", "51841184-6605a66e15648a893913be172d2fec55",
                        "51841184-6ffe5b6b9e3d25f6a0f508e04bf3c015", "51841184-75fdf3ae98944dce60d1997f1ca78bd1",
                        "51841184-77ce0873f5f41663c824cc7a7c760813", "51841184-7cd78a23bf1d725973358bb6f41e55ba",
                        "51841184-86614c59eb2d26502e5219d91e1184a5", "51841184-881dd31804afbba1eff178065c56798e",
                        "51841184-8f00d6340b92ff29fce656d7a689d48a", "51841184-a0ebe4538b0303f1f626d253b3ee0d01",
                        "51841184-a1e1cb77107513f4ede2eca6a0721cd6", "51841184-a5dda73904d35b5f4b4c4c555ea428cc",
                        "51841184-b3496e25c625803b858635a0a354b71f", "51841184-c3de7e8d703381240bf26be146dbe71e",
                        "51841184-ca4b936a974a0ca03201f4bcc3394609", "51841184-de2ce1ccfd728f77b3657343acd27c4d",
                        "51841184-de58146a851480d3e2e2c3a45e13fac0", "51841184-e26574164a1389eebe8a95d9d0ad9aed",
                        "51841184-e79becfef9f478e613ea32facdfa7d50", "51841184-f6dac1b79179b4a5b7e0c3c7d033484d",
                        "51841184-fbb4fbd358fc6ad034b17ee57affd37c", "51841184-fccc129d1fdae6c6bd82ee84f2242288",
                        "51841188-ecc4090b639c47f89b453980923afb8e", "51841189-ecc4090b639c47f89b453980923afb8e",
                        "51841190-09a7d97ee9f97f73dde58c571a1862fe", "51841190-2308c416091d831c0f3be7b9cd82af20",
                        "51841190-460c992d969c85a2f00e5de2cb43cf9c", "51841191-8f00d6340b92ff29fce656d7a689d48a",
                        "51841192-4ec8f5bd59928cbb85efb15c1ca8b184", "51841192-6605a66e15648a893913be172d2fec55",
                        "51841192-86614c59eb2d26502e5219d91e1184a5", "51841192-8f00d6340b92ff29fce656d7a689d48a",
                        "51841192-a1e1cb77107513f4ede2eca6a0721cd6", "51841192-b3496e25c625803b858635a0a354b71f",
                        "51841193-09a7d97ee9f97f73dde58c571a1862fe", "51841193-2308c416091d831c0f3be7b9cd82af20",
                        "51841200-4ec8f5bd59928cbb85efb15c1ca8b184", "51841200-6605a66e15648a893913be172d2fec55",
                        "51841200-86614c59eb2d26502e5219d91e1184a5", "51841200-8f00d6340b92ff29fce656d7a689d48a",
                        "51841200-a1e1cb77107513f4ede2eca6a0721cd6", "51841200-b3496e25c625803b858635a0a354b71f",
                        "51841201-09a7d97ee9f97f73dde58c571a1862fe", "51841201-2308c416091d831c0f3be7b9cd82af20",
                        "51841202-4ec8f5bd59928cbb85efb15c1ca8b184", "51841202-6605a66e15648a893913be172d2fec55",
                        "51841202-86614c59eb2d26502e5219d91e1184a5", "51841202-8f00d6340b92ff29fce656d7a689d48a",
                        "51841202-a1e1cb77107513f4ede2eca6a0721cd6", "51841202-b3496e25c625803b858635a0a354b71f",
                        "51841203-09a7d97ee9f97f73dde58c571a1862fe", "51841203-2308c416091d831c0f3be7b9cd82af20",
                        "51841203-6f04aaa51ddfbe2aad2e5d139528bc95", "51841203-c098bae5a4e7dd31fca6c999eb921810",
                        "51841203-d0d4e28b9ab37e048f225ba969f09dbf", "51841203-e3f0e140df5068317d103edeb04fc668",
                        "51841205-8f00d6340b92ff29fce656d7a689d48a", "51841206-2308c416091d831c0f3be7b9cd82af20",
                        "51841207-ecc4090b639c47f89b453980923afb8e", "51841209-ecc4090b639c47f89b453980923afb8e",
                        "51841211-4ec8f5bd59928cbb85efb15c1ca8b184", "51841211-6605a66e15648a893913be172d2fec55",
                        "51841211-86614c59eb2d26502e5219d91e1184a5", "51841211-8f00d6340b92ff29fce656d7a689d48a",
                        "51841211-a1e1cb77107513f4ede2eca6a0721cd6", "51841211-b3496e25c625803b858635a0a354b71f",
                        "51841212-4ec8f5bd59928cbb85efb15c1ca8b184", "51841212-6605a66e15648a893913be172d2fec55",
                        "51841212-86614c59eb2d26502e5219d91e1184a5", "51841212-8f00d6340b92ff29fce656d7a689d48a",
                        "51841212-a1e1cb77107513f4ede2eca6a0721cd6", "51841212-b3496e25c625803b858635a0a354b71f",
                        "51841214-4ec8f5bd59928cbb85efb15c1ca8b184", "51841214-6605a66e15648a893913be172d2fec55",
                        "51841214-86614c59eb2d26502e5219d91e1184a5", "51841214-8f00d6340b92ff29fce656d7a689d48a",
                        "51841214-a1e1cb77107513f4ede2eca6a0721cd6", "51841214-b3496e25c625803b858635a0a354b71f",
                        "51841215-ecc4090b639c47f89b453980923afb8e", "51841217-ecc4090b639c47f89b453980923afb8e",
                        "51841219-4ec8f5bd59928cbb85efb15c1ca8b184", "51841219-6605a66e15648a893913be172d2fec55",
                        "51841219-86614c59eb2d26502e5219d91e1184a5", "51841219-8f00d6340b92ff29fce656d7a689d48a",
                        "51841219-a1e1cb77107513f4ede2eca6a0721cd6", "51841219-b3496e25c625803b858635a0a354b71f",
                        "51841221-09a7d97ee9f97f73dde58c571a1862fe", "51841221-2308c416091d831c0f3be7b9cd82af20",
                        "51841222-4ec8f5bd59928cbb85efb15c1ca8b184", "51841222-6605a66e15648a893913be172d2fec55",
                        "51841222-86614c59eb2d26502e5219d91e1184a5", "51841222-8f00d6340b92ff29fce656d7a689d48a",
                        "51841222-a1e1cb77107513f4ede2eca6a0721cd6", "51841222-b3496e25c625803b858635a0a354b71f",
                        "51841223-09a7d97ee9f97f73dde58c571a1862fe", "51841223-2308c416091d831c0f3be7b9cd82af20",
                        "51841227-ecc4090b639c47f89b453980923afb8e", "51842137-ecc4090b639c47f89b453980923afb8e",
                        "51842141-09a7d97ee9f97f73dde58c571a1862fe", "51842141-2308c416091d831c0f3be7b9cd82af20",
                        "51842142-09a7d97ee9f97f73dde58c571a1862fe", "51842142-2308c416091d831c0f3be7b9cd82af20",
                        "51842190-ecc4090b639c47f89b453980923afb8e", "51842269-ecc4090b639c47f89b453980923afb8e",
                        "51842306-ecc4090b639c47f89b453980923afb8e", "51842307-ecc4090b639c47f89b453980923afb8e",
                        "51842308-ecc4090b639c47f89b453980923afb8e", "51842309-ecc4090b639c47f89b453980923afb8e",
                        "51842354-ecc4090b639c47f89b453980923afb8e", "51842387-ecc4090b639c47f89b453980923afb8e",
                        "51842388-ecc4090b639c47f89b453980923afb8e", "51842434-ecc4090b639c47f89b453980923afb8e",
                        "51842525-ecc4090b639c47f89b453980923afb8e", "51842526-ecc4090b639c47f89b453980923afb8e",
                        "51842527-ecc4090b639c47f89b453980923afb8e", "51842528-ecc4090b639c47f89b453980923afb8e",
                        "51842529-ecc4090b639c47f89b453980923afb8e", "51842530-ecc4090b639c47f89b453980923afb8e",
                        "51842531-ecc4090b639c47f89b453980923afb8e", "51842532-ecc4090b639c47f89b453980923afb8e",
                        "51842533-ecc4090b639c47f89b453980923afb8e", "51842534-ecc4090b639c47f89b453980923afb8e",
                        "51842535-ecc4090b639c47f89b453980923afb8e", "51842536-ecc4090b639c47f89b453980923afb8e",
                        "51842537-ecc4090b639c47f89b453980923afb8e", "51842538-ecc4090b639c47f89b453980923afb8e",
                        "51842539-ecc4090b639c47f89b453980923afb8e", "51842540-ecc4090b639c47f89b453980923afb8e",
                        "51842541-ecc4090b639c47f89b453980923afb8e", "51842542-ecc4090b639c47f89b453980923afb8e",
                        "51842543-ecc4090b639c47f89b453980923afb8e", "51842544-ecc4090b639c47f89b453980923afb8e",
                        "51842545-ecc4090b639c47f89b453980923afb8e", "51842546-ecc4090b639c47f89b453980923afb8e",
                        "51842547-ecc4090b639c47f89b453980923afb8e", "51842548-ecc4090b639c47f89b453980923afb8e",
                        "51842549-ecc4090b639c47f89b453980923afb8e", "51842550-ecc4090b639c47f89b453980923afb8e",
                        "51842551-ecc4090b639c47f89b453980923afb8e", "51842552-ecc4090b639c47f89b453980923afb8e",
                        "51842553-ecc4090b639c47f89b453980923afb8e", "51842554-ecc4090b639c47f89b453980923afb8e",
                        "51842555-ecc4090b639c47f89b453980923afb8e", "51842556-ecc4090b639c47f89b453980923afb8e",
                        "51842557-ecc4090b639c47f89b453980923afb8e", "51842558-ecc4090b639c47f89b453980923afb8e",
                        "51842559-ecc4090b639c47f89b453980923afb8e", "51842560-ecc4090b639c47f89b453980923afb8e",
                        "51842561-ecc4090b639c47f89b453980923afb8e", "51842562-ecc4090b639c47f89b453980923afb8e",
                        "51842563-ecc4090b639c47f89b453980923afb8e", "51842564-ecc4090b639c47f89b453980923afb8e",
                        "51842565-ecc4090b639c47f89b453980923afb8e", "51842566-ecc4090b639c47f89b453980923afb8e",
                        "51842567-ecc4090b639c47f89b453980923afb8e", "51842568-ecc4090b639c47f89b453980923afb8e",
                        "51842569-ecc4090b639c47f89b453980923afb8e", "51842570-ecc4090b639c47f89b453980923afb8e",
                        "51842571-ecc4090b639c47f89b453980923afb8e", "51842572-ecc4090b639c47f89b453980923afb8e",
                        "51842573-ecc4090b639c47f89b453980923afb8e", "51842574-ecc4090b639c47f89b453980923afb8e",
                        "51842575-ecc4090b639c47f89b453980923afb8e", "51842576-ecc4090b639c47f89b453980923afb8e",
                        "51842577-ecc4090b639c47f89b453980923afb8e", "51842578-ecc4090b639c47f89b453980923afb8e",
                        "51842579-ecc4090b639c47f89b453980923afb8e", "51842580-ecc4090b639c47f89b453980923afb8e",
                        "51842581-ecc4090b639c47f89b453980923afb8e", "51842582-ecc4090b639c47f89b453980923afb8e",
                        "51842583-ecc4090b639c47f89b453980923afb8e", "51842584-ecc4090b639c47f89b453980923afb8e",
                        "51842585-ecc4090b639c47f89b453980923afb8e", "51842586-ecc4090b639c47f89b453980923afb8e",
                        "51842587-ecc4090b639c47f89b453980923afb8e", "51842588-ecc4090b639c47f89b453980923afb8e",
                        "51842589-ecc4090b639c47f89b453980923afb8e", "51842590-ecc4090b639c47f89b453980923afb8e",
                        "51842591-ecc4090b639c47f89b453980923afb8e", "51842592-ecc4090b639c47f89b453980923afb8e",
                        "51842593-ecc4090b639c47f89b453980923afb8e", "51842594-ecc4090b639c47f89b453980923afb8e",
                        "51842595-ecc4090b639c47f89b453980923afb8e", "51842596-ecc4090b639c47f89b453980923afb8e",
                        "51842597-ecc4090b639c47f89b453980923afb8e", "51842598-ecc4090b639c47f89b453980923afb8e",
                        "51842599-ecc4090b639c47f89b453980923afb8e", "51842600-ecc4090b639c47f89b453980923afb8e",
                        "51842601-ecc4090b639c47f89b453980923afb8e", "51842602-ecc4090b639c47f89b453980923afb8e",
                        "51842603-ecc4090b639c47f89b453980923afb8e", "51842604-ecc4090b639c47f89b453980923afb8e",
                        "51842605-ecc4090b639c47f89b453980923afb8e", "51842606-ecc4090b639c47f89b453980923afb8e",
                        "51842607-ecc4090b639c47f89b453980923afb8e", "51842608-ecc4090b639c47f89b453980923afb8e",
                        "51842609-ecc4090b639c47f89b453980923afb8e", "51842610-ecc4090b639c47f89b453980923afb8e",
                        "51842611-ecc4090b639c47f89b453980923afb8e", "51842612-ecc4090b639c47f89b453980923afb8e",
                        "51842613-ecc4090b639c47f89b453980923afb8e", "51842614-ecc4090b639c47f89b453980923afb8e",
                        "51842615-ecc4090b639c47f89b453980923afb8e", "51842616-ecc4090b639c47f89b453980923afb8e",
                        "51842617-ecc4090b639c47f89b453980923afb8e", "51842618-ecc4090b639c47f89b453980923afb8e",
                        "51842619-ecc4090b639c47f89b453980923afb8e", "51842620-ecc4090b639c47f89b453980923afb8e",
                        "51842621-ecc4090b639c47f89b453980923afb8e", "51842622-ecc4090b639c47f89b453980923afb8e",
                        "51842623-ecc4090b639c47f89b453980923afb8e", "51842624-ecc4090b639c47f89b453980923afb8e",
                        "51842625-ecc4090b639c47f89b453980923afb8e", "51842626-ecc4090b639c47f89b453980923afb8e",
                        "51842627-ecc4090b639c47f89b453980923afb8e", "51842628-ecc4090b639c47f89b453980923afb8e",
                        "51842629-ecc4090b639c47f89b453980923afb8e", "51842630-ecc4090b639c47f89b453980923afb8e",
                        "51842631-ecc4090b639c47f89b453980923afb8e", "51842632-ecc4090b639c47f89b453980923afb8e",
                        "51842633-ecc4090b639c47f89b453980923afb8e", "51842634-ecc4090b639c47f89b453980923afb8e",
                        "51842635-ecc4090b639c47f89b453980923afb8e", "51842636-ecc4090b639c47f89b453980923afb8e",
                        "51842637-ecc4090b639c47f89b453980923afb8e", "51842638-ecc4090b639c47f89b453980923afb8e",
                        "51842639-ecc4090b639c47f89b453980923afb8e", "51842640-ecc4090b639c47f89b453980923afb8e",
                        "51842641-ecc4090b639c47f89b453980923afb8e", "51842642-ecc4090b639c47f89b453980923afb8e",
                        "51842643-ecc4090b639c47f89b453980923afb8e", "51842644-ecc4090b639c47f89b453980923afb8e",
                        "51842645-ecc4090b639c47f89b453980923afb8e", "51842646-ecc4090b639c47f89b453980923afb8e",
                        "51842647-ecc4090b639c47f89b453980923afb8e", "51842648-ecc4090b639c47f89b453980923afb8e",
                        "51842649-ecc4090b639c47f89b453980923afb8e", "51842650-ecc4090b639c47f89b453980923afb8e",
                        "51842651-ecc4090b639c47f89b453980923afb8e", "51842652-ecc4090b639c47f89b453980923afb8e",
                        "51842653-ecc4090b639c47f89b453980923afb8e", "51842654-ecc4090b639c47f89b453980923afb8e",
                        "51842655-ecc4090b639c47f89b453980923afb8e", "51842656-ecc4090b639c47f89b453980923afb8e",
                        "51842657-ecc4090b639c47f89b453980923afb8e", "51842658-ecc4090b639c47f89b453980923afb8e",
                        "51842659-ecc4090b639c47f89b453980923afb8e", "51842660-ecc4090b639c47f89b453980923afb8e",
                        "51842661-ecc4090b639c47f89b453980923afb8e", "51842662-ecc4090b639c47f89b453980923afb8e",
                        "51842663-ecc4090b639c47f89b453980923afb8e", "51842664-ecc4090b639c47f89b453980923afb8e",
                        "51842665-ecc4090b639c47f89b453980923afb8e", "51842666-ecc4090b639c47f89b453980923afb8e",
                        "51842667-ecc4090b639c47f89b453980923afb8e", "51842668-ecc4090b639c47f89b453980923afb8e",
                        "51842669-ecc4090b639c47f89b453980923afb8e", "51842670-ecc4090b639c47f89b453980923afb8e",
                        "51842671-ecc4090b639c47f89b453980923afb8e", "51842672-ecc4090b639c47f89b453980923afb8e",
                        "51842673-ecc4090b639c47f89b453980923afb8e", "51842674-ecc4090b639c47f89b453980923afb8e",
                        "51842675-ecc4090b639c47f89b453980923afb8e", "51842676-ecc4090b639c47f89b453980923afb8e",
                        "51842677-ecc4090b639c47f89b453980923afb8e", "51842678-ecc4090b639c47f89b453980923afb8e",
                        "51842679-ecc4090b639c47f89b453980923afb8e", "51842680-ecc4090b639c47f89b453980923afb8e",
                        "51842681-ecc4090b639c47f89b453980923afb8e", "51842682-ecc4090b639c47f89b453980923afb8e",
                        "51842683-ecc4090b639c47f89b453980923afb8e", "51842684-ecc4090b639c47f89b453980923afb8e",
                        "51842685-ecc4090b639c47f89b453980923afb8e", "51842686-ecc4090b639c47f89b453980923afb8e",
                        "51842687-ecc4090b639c47f89b453980923afb8e", "51842688-ecc4090b639c47f89b453980923afb8e",
                        "51842689-ecc4090b639c47f89b453980923afb8e", "51842690-ecc4090b639c47f89b453980923afb8e",
                        "51842691-ecc4090b639c47f89b453980923afb8e", "51842692-ecc4090b639c47f89b453980923afb8e",
                        "51842693-ecc4090b639c47f89b453980923afb8e", "51842694-ecc4090b639c47f89b453980923afb8e",
                        "51842695-ecc4090b639c47f89b453980923afb8e", "51842696-ecc4090b639c47f89b453980923afb8e",
                        "51842697-ecc4090b639c47f89b453980923afb8e", "51842698-ecc4090b639c47f89b453980923afb8e",
                        "51842699-ecc4090b639c47f89b453980923afb8e", "51842700-ecc4090b639c47f89b453980923afb8e",
                        "51842701-ecc4090b639c47f89b453980923afb8e", "51842702-ecc4090b639c47f89b453980923afb8e",
                        "51842703-ecc4090b639c47f89b453980923afb8e", "51842704-ecc4090b639c47f89b453980923afb8e",
                        "51842705-ecc4090b639c47f89b453980923afb8e", "51842706-ecc4090b639c47f89b453980923afb8e",
                        "51842707-ecc4090b639c47f89b453980923afb8e", "51842708-ecc4090b639c47f89b453980923afb8e",
                        "51842709-ecc4090b639c47f89b453980923afb8e", "51842710-ecc4090b639c47f89b453980923afb8e",
                        "51842711-ecc4090b639c47f89b453980923afb8e", "51842712-ecc4090b639c47f89b453980923afb8e",
                        "51842713-ecc4090b639c47f89b453980923afb8e", "51842714-ecc4090b639c47f89b453980923afb8e",
                        "51842715-ecc4090b639c47f89b453980923afb8e", "51842716-ecc4090b639c47f89b453980923afb8e",
                        "51842717-ecc4090b639c47f89b453980923afb8e", "51842718-ecc4090b639c47f89b453980923afb8e",
                        "51842719-ecc4090b639c47f89b453980923afb8e", "51842720-ecc4090b639c47f89b453980923afb8e",
                        "51842721-ecc4090b639c47f89b453980923afb8e", "51842722-ecc4090b639c47f89b453980923afb8e",
                        "51842723-ecc4090b639c47f89b453980923afb8e", "51842724-ecc4090b639c47f89b453980923afb8e",
                        "51842725-ecc4090b639c47f89b453980923afb8e", "51842726-ecc4090b639c47f89b453980923afb8e",
                        "51842727-ecc4090b639c47f89b453980923afb8e", "51842728-ecc4090b639c47f89b453980923afb8e",
                        "51842729-ecc4090b639c47f89b453980923afb8e", "51842730-ecc4090b639c47f89b453980923afb8e",
                        "51842731-ecc4090b639c47f89b453980923afb8e", "51842732-ecc4090b639c47f89b453980923afb8e",
                        "5738-a77d476837ebf2aaaef97267c5e66f66", "58707342-26e9f74ffea0454719685009efff00fd",
                        "58707342-5466168d37fb3774b822d0c63600db37", "58707342-7a716ed239aaec4223946e2357332b66",
                        "58708752-ef67d515554890c2bd5e22550dd43d30", "58708764-ef67d515554890c2bd5e22550dd43d30",
                        "58708764-ef67d515554890c2bd5e22550dd43d30", "58708764-ef67d515554890c2bd5e22550dd43d30",
                        "58708796-e095e851eff64b13c2fa30f18df3b355", "58708796-e095e851eff64b13c2fa30f18df3b355",
                        "58708796-e095e851eff64b13c2fa30f18df3b355", "58708796-e095e851eff64b13c2fa30f18df3b355",
                        "58708835-e095e851eff64b13c2fa30f18df3b355", "58708835-e095e851eff64b13c2fa30f18df3b355",
                        "58708835-e095e851eff64b13c2fa30f18df3b355", "58708835-e095e851eff64b13c2fa30f18df3b355",
                        "58708865-0a1e6672e8515b5a2b30f43ad1cf237f", "58708865-0a1e6672e8515b5a2b30f43ad1cf237f",
                        "58708865-0a1e6672e8515b5a2b30f43ad1cf237f", "58708871-71ba0dd0043597644fd4d6dba5d4525a",
                        "58708871-71ba0dd0043597644fd4d6dba5d4525a", "58708871-71ba0dd0043597644fd4d6dba5d4525a",
                        "58709533-42d52b693302bcfe28e04c2de8dba13f", "58709964-88971fe79fba958fc151a190dca3544b",
                        "58709964-f7d074d381dafc8964c407f16a4c7a11", "58709971-27c6a456353f988b546dffcefdb2712e",
                        "58709971-5e9f7264e8a0b99654029f3a912d3c09", "58718369-ecc4090b639c47f89b453980923afb8e",
                        "58718448-ecc4090b639c47f89b453980923afb8e", "58718463-ecc4090b639c47f89b453980923afb8e",
                        "58719693-5819068a45f28e23db96630ba902dd2f", "58722930-ecc4090b639c47f89b453980923afb8e",
                        "58722933-ecc4090b639c47f89b453980923afb8e", "58723163-ecc4090b639c47f89b453980923afb8e",
                        "58723497-ecc4090b639c47f89b453980923afb8e", "58723499-ecc4090b639c47f89b453980923afb8e",
                        "58723501-ecc4090b639c47f89b453980923afb8e", "58723503-ecc4090b639c47f89b453980923afb8e",
                        "58724170-ecc4090b639c47f89b453980923afb8e", "58724172-ecc4090b639c47f89b453980923afb8e",
                        "58724634-ecc4090b639c47f89b453980923afb8e", "58724636-ecc4090b639c47f89b453980923afb8e",
                        "58724658-ecc4090b639c47f89b453980923afb8e", "58724673-4ec8f5bd59928cbb85efb15c1ca8b184",
                        "58724673-6605a66e15648a893913be172d2fec55", "58724673-86614c59eb2d26502e5219d91e1184a5",
                        "58724673-8f00d6340b92ff29fce656d7a689d48a", "58724673-a1e1cb77107513f4ede2eca6a0721cd6",
                        "58724673-b3496e25c625803b858635a0a354b71f", "58724680-ecc4090b639c47f89b453980923afb8e",
                        "58724682-ecc4090b639c47f89b453980923afb8e", "58724684-ecc4090b639c47f89b453980923afb8e",
                        "58724686-ecc4090b639c47f89b453980923afb8e", "58724690-ecc4090b639c47f89b453980923afb8e",
                        "58724692-ecc4090b639c47f89b453980923afb8e", "58724694-ecc4090b639c47f89b453980923afb8e",
                        "58724696-ecc4090b639c47f89b453980923afb8e", "58724784-ecc4090b639c47f89b453980923afb8e",
                        "58724797-09a7d97ee9f97f73dde58c571a1862fe", "58724797-2308c416091d831c0f3be7b9cd82af20",
                        "58724799-09a7d97ee9f97f73dde58c571a1862fe", "58724799-2308c416091d831c0f3be7b9cd82af20",
                        "58724801-ecc4090b639c47f89b453980923afb8e", "58724803-ecc4090b639c47f89b453980923afb8e",
                        "58724808-ecc4090b639c47f89b453980923afb8e", "58724810-ecc4090b639c47f89b453980923afb8e",
                        "58724818-ecc4090b639c47f89b453980923afb8e", "58724822-ecc4090b639c47f89b453980923afb8e",
                        "58724981-ecc4090b639c47f89b453980923afb8e", "58724989-ecc4090b639c47f89b453980923afb8e",
                        "58725001-ecc4090b639c47f89b453980923afb8e", "58725026-09a7d97ee9f97f73dde58c571a1862fe",
                        "58725026-2308c416091d831c0f3be7b9cd82af20", "58725043-ecc4090b639c47f89b453980923afb8e",
                        "58725049-ecc4090b639c47f89b453980923afb8e", "58725054-09a7d97ee9f97f73dde58c571a1862fe",
                        "58725054-2308c416091d831c0f3be7b9cd82af20", "58725056-4ec8f5bd59928cbb85efb15c1ca8b184",
                        "58725056-6605a66e15648a893913be172d2fec55", "58725056-86614c59eb2d26502e5219d91e1184a5",
                        "58725056-8f00d6340b92ff29fce656d7a689d48a", "58725056-a1e1cb77107513f4ede2eca6a0721cd6",
                        "58725056-b3496e25c625803b858635a0a354b71f", "58725058-ecc4090b639c47f89b453980923afb8e",
                        "58725060-ecc4090b639c47f89b453980923afb8e", "58725062-ecc4090b639c47f89b453980923afb8e",
                        "58725105-ecc4090b639c47f89b453980923afb8e", "58725109-ecc4090b639c47f89b453980923afb8e",
                        "58725331-ecc4090b639c47f89b453980923afb8e", "58725478-ecc4090b639c47f89b453980923afb8e",
                        "58726192-ecc4090b639c47f89b453980923afb8e", "58726194-ecc4090b639c47f89b453980923afb8e",
                        "58726196-ecc4090b639c47f89b453980923afb8e", "58726200-ecc4090b639c47f89b453980923afb8e",
                        "58726202-ecc4090b639c47f89b453980923afb8e", "58726236-ecc4090b639c47f89b453980923afb8e",
                        "58726271-ecc4090b639c47f89b453980923afb8e", "58726275-ecc4090b639c47f89b453980923afb8e",
                        "58726306-ecc4090b639c47f89b453980923afb8e", "58726339-ecc4090b639c47f89b453980923afb8e",
                        "58726341-ecc4090b639c47f89b453980923afb8e", "58726343-ecc4090b639c47f89b453980923afb8e",
                        "58726345-ecc4090b639c47f89b453980923afb8e", "58726347-ecc4090b639c47f89b453980923afb8e",
                        "58726352-ecc4090b639c47f89b453980923afb8e", "58726355-ecc4090b639c47f89b453980923afb8e",
                        "58726357-4ec8f5bd59928cbb85efb15c1ca8b184", "58726357-6605a66e15648a893913be172d2fec55",
                        "58726357-86614c59eb2d26502e5219d91e1184a5", "58726357-8f00d6340b92ff29fce656d7a689d48a",
                        "58726357-a1e1cb77107513f4ede2eca6a0721cd6", "58726357-b3496e25c625803b858635a0a354b71f",
                        "58726359-ecc4090b639c47f89b453980923afb8e", "58726361-ecc4090b639c47f89b453980923afb8e",
                        "58726363-ecc4090b639c47f89b453980923afb8e", "58726365-ecc4090b639c47f89b453980923afb8e",
                        "58726367-ecc4090b639c47f89b453980923afb8e", "58726369-ecc4090b639c47f89b453980923afb8e",
                        "58726371-ecc4090b639c47f89b453980923afb8e", "58726408-ecc4090b639c47f89b453980923afb8e",
                        "58726504-ecc4090b639c47f89b453980923afb8e", "58726511-ecc4090b639c47f89b453980923afb8e",
                        "58726513-ecc4090b639c47f89b453980923afb8e", "58726515-ecc4090b639c47f89b453980923afb8e",
                        "58726518-ecc4090b639c47f89b453980923afb8e", "58726520-ecc4090b639c47f89b453980923afb8e",
                        "58726522-ecc4090b639c47f89b453980923afb8e", "58726524-ecc4090b639c47f89b453980923afb8e",
                        "58726526-ecc4090b639c47f89b453980923afb8e", "58726528-ecc4090b639c47f89b453980923afb8e",
                        "58726530-ecc4090b639c47f89b453980923afb8e", "58726532-ecc4090b639c47f89b453980923afb8e",
                        "58726534-ecc4090b639c47f89b453980923afb8e", "58726538-ecc4090b639c47f89b453980923afb8e",
                        "58726573-ecc4090b639c47f89b453980923afb8e", "58726585-ecc4090b639c47f89b453980923afb8e",
                        "58726621-ecc4090b639c47f89b453980923afb8e", "58726623-ecc4090b639c47f89b453980923afb8e",
                        "58726625-ecc4090b639c47f89b453980923afb8e", "58726627-ecc4090b639c47f89b453980923afb8e",
                        "58726630-ecc4090b639c47f89b453980923afb8e", "58726714-ecc4090b639c47f89b453980923afb8e",
                        "58726788-ecc4090b639c47f89b453980923afb8e", "58726790-ecc4090b639c47f89b453980923afb8e",
                        "58726792-ecc4090b639c47f89b453980923afb8e", "58726828-ecc4090b639c47f89b453980923afb8e",
                        "58735263-ecc4090b639c47f89b453980923afb8e", "58735267-ecc4090b639c47f89b453980923afb8e",
                        "58735269-ecc4090b639c47f89b453980923afb8e", "58735272-ecc4090b639c47f89b453980923afb8e",
                        "58735274-ecc4090b639c47f89b453980923afb8e", "58735388-ecc4090b639c47f89b453980923afb8e",
                        "58735474-ecc4090b639c47f89b453980923afb8e", "58735488-ecc4090b639c47f89b453980923afb8e",
                        "58735495-ecc4090b639c47f89b453980923afb8e", "58735517-4ec8f5bd59928cbb85efb15c1ca8b184",
                        "58735517-6605a66e15648a893913be172d2fec55", "58735517-86614c59eb2d26502e5219d91e1184a5",
                        "58735517-8f00d6340b92ff29fce656d7a689d48a", "58735517-a1e1cb77107513f4ede2eca6a0721cd6",
                        "58735517-b3496e25c625803b858635a0a354b71f", "58735519-ecc4090b639c47f89b453980923afb8e",
                        "58735524-ecc4090b639c47f89b453980923afb8e", "58735562-4ec8f5bd59928cbb85efb15c1ca8b184",
                        "58735562-6605a66e15648a893913be172d2fec55", "58735562-86614c59eb2d26502e5219d91e1184a5",
                        "58735562-8f00d6340b92ff29fce656d7a689d48a", "58735562-a1e1cb77107513f4ede2eca6a0721cd6",
                        "58735562-b3496e25c625803b858635a0a354b71f", "58735564-4ec8f5bd59928cbb85efb15c1ca8b184",
                        "58735564-6605a66e15648a893913be172d2fec55", "58735564-86614c59eb2d26502e5219d91e1184a5",
                        "58735564-8f00d6340b92ff29fce656d7a689d48a", "58735564-a1e1cb77107513f4ede2eca6a0721cd6",
                        "58735564-b3496e25c625803b858635a0a354b71f", "58735570-09eb271f56c864492e0917a3115ff153",
                        "58735570-355d44186c3ed2f0b87b491fbea83a67", "58735570-4ec8f5bd59928cbb85efb15c1ca8b184",
                        "58735570-6605a66e15648a893913be172d2fec55", "58735570-6ffe5b6b9e3d25f6a0f508e04bf3c015",
                        "58735570-75fdf3ae98944dce60d1997f1ca78bd1", "58735570-77ce0873f5f41663c824cc7a7c760813",
                        "58735570-7cd78a23bf1d725973358bb6f41e55ba", "58735570-86614c59eb2d26502e5219d91e1184a5",
                        "58735570-8f00d6340b92ff29fce656d7a689d48a", "58735570-a0ebe4538b0303f1f626d253b3ee0d01",
                        "58735570-a1e1cb77107513f4ede2eca6a0721cd6", "58735570-b3496e25c625803b858635a0a354b71f",
                        "58735570-de2ce1ccfd728f77b3657343acd27c4d", "58735570-fccc129d1fdae6c6bd82ee84f2242288",
                        "58735572-ecc4090b639c47f89b453980923afb8e", "58735574-4ec8f5bd59928cbb85efb15c1ca8b184",
                        "58735574-6605a66e15648a893913be172d2fec55", "58735574-86614c59eb2d26502e5219d91e1184a5",
                        "58735574-8f00d6340b92ff29fce656d7a689d48a", "58735574-a1e1cb77107513f4ede2eca6a0721cd6",
                        "58735574-b3496e25c625803b858635a0a354b71f", "58735576-4ec8f5bd59928cbb85efb15c1ca8b184",
                        "58735576-6605a66e15648a893913be172d2fec55", "58735576-86614c59eb2d26502e5219d91e1184a5",
                        "58735576-8f00d6340b92ff29fce656d7a689d48a", "58735576-a1e1cb77107513f4ede2eca6a0721cd6",
                        "58735576-b3496e25c625803b858635a0a354b71f", "58735578-4ec8f5bd59928cbb85efb15c1ca8b184",
                        "58735578-6605a66e15648a893913be172d2fec55", "58735578-75fdf3ae98944dce60d1997f1ca78bd1",
                        "58735578-7cd78a23bf1d725973358bb6f41e55ba", "58735578-86614c59eb2d26502e5219d91e1184a5",
                        "58735578-8f00d6340b92ff29fce656d7a689d48a", "58735578-a1e1cb77107513f4ede2eca6a0721cd6",
                        "58735578-b3496e25c625803b858635a0a354b71f", "58735578-de2ce1ccfd728f77b3657343acd27c4d",
                        "58735580-4ec8f5bd59928cbb85efb15c1ca8b184", "58735580-6605a66e15648a893913be172d2fec55",
                        "58735580-86614c59eb2d26502e5219d91e1184a5", "58735580-8f00d6340b92ff29fce656d7a689d48a",
                        "58735580-a1e1cb77107513f4ede2eca6a0721cd6", "58735580-b3496e25c625803b858635a0a354b71f",
                        "58735582-355d44186c3ed2f0b87b491fbea83a67", "58735582-4ec8f5bd59928cbb85efb15c1ca8b184",
                        "58735582-6605a66e15648a893913be172d2fec55", "58735582-6ffe5b6b9e3d25f6a0f508e04bf3c015",
                        "58735582-75fdf3ae98944dce60d1997f1ca78bd1", "58735582-7cd78a23bf1d725973358bb6f41e55ba",
                        "58735582-86614c59eb2d26502e5219d91e1184a5", "58735582-8f00d6340b92ff29fce656d7a689d48a",
                        "58735582-a1e1cb77107513f4ede2eca6a0721cd6", "58735582-b3496e25c625803b858635a0a354b71f",
                        "58735582-de2ce1ccfd728f77b3657343acd27c4d", "58735582-fccc129d1fdae6c6bd82ee84f2242288",
                        "58735584-355d44186c3ed2f0b87b491fbea83a67", "58735584-4ec8f5bd59928cbb85efb15c1ca8b184",
                        "58735584-6605a66e15648a893913be172d2fec55", "58735584-6ffe5b6b9e3d25f6a0f508e04bf3c015",
                        "58735584-75fdf3ae98944dce60d1997f1ca78bd1", "58735584-7cd78a23bf1d725973358bb6f41e55ba",
                        "58735584-86614c59eb2d26502e5219d91e1184a5", "58735584-8f00d6340b92ff29fce656d7a689d48a",
                        "58735584-a1e1cb77107513f4ede2eca6a0721cd6", "58735584-b3496e25c625803b858635a0a354b71f",
                        "58735584-de2ce1ccfd728f77b3657343acd27c4d", "58735584-fccc129d1fdae6c6bd82ee84f2242288",
                        "58735586-09eb271f56c864492e0917a3115ff153", "58735586-355d44186c3ed2f0b87b491fbea83a67",
                        "58735586-4ec8f5bd59928cbb85efb15c1ca8b184", "58735586-6605a66e15648a893913be172d2fec55",
                        "58735586-6ffe5b6b9e3d25f6a0f508e04bf3c015", "58735586-75fdf3ae98944dce60d1997f1ca78bd1",
                        "58735586-77ce0873f5f41663c824cc7a7c760813", "58735586-7cd78a23bf1d725973358bb6f41e55ba",
                        "58735586-86614c59eb2d26502e5219d91e1184a5", "58735586-8f00d6340b92ff29fce656d7a689d48a",
                        "58735586-a0ebe4538b0303f1f626d253b3ee0d01", "58735586-a1e1cb77107513f4ede2eca6a0721cd6",
                        "58735586-b3496e25c625803b858635a0a354b71f", "58735586-de2ce1ccfd728f77b3657343acd27c4d",
                        "58735586-fccc129d1fdae6c6bd82ee84f2242288", "58735596-09eb271f56c864492e0917a3115ff153",
                        "58735596-355d44186c3ed2f0b87b491fbea83a67", "58735596-4ec8f5bd59928cbb85efb15c1ca8b184",
                        "58735596-6605a66e15648a893913be172d2fec55", "58735596-6ffe5b6b9e3d25f6a0f508e04bf3c015",
                        "58735596-75fdf3ae98944dce60d1997f1ca78bd1", "58735596-77ce0873f5f41663c824cc7a7c760813",
                        "58735596-7cd78a23bf1d725973358bb6f41e55ba", "58735596-86614c59eb2d26502e5219d91e1184a5",
                        "58735596-8f00d6340b92ff29fce656d7a689d48a", "58735596-a0ebe4538b0303f1f626d253b3ee0d01",
                        "58735596-a1e1cb77107513f4ede2eca6a0721cd6", "58735596-b3496e25c625803b858635a0a354b71f",
                        "58735596-de2ce1ccfd728f77b3657343acd27c4d", "58735596-fccc129d1fdae6c6bd82ee84f2242288",
                        "58735628-4ec8f5bd59928cbb85efb15c1ca8b184", "58735628-6605a66e15648a893913be172d2fec55",
                        "58735628-86614c59eb2d26502e5219d91e1184a5", "58735628-8f00d6340b92ff29fce656d7a689d48a",
                        "58735628-a1e1cb77107513f4ede2eca6a0721cd6", "58735628-b3496e25c625803b858635a0a354b71f",
                        "58735632-09eb271f56c864492e0917a3115ff153", "58735632-355d44186c3ed2f0b87b491fbea83a67",
                        "58735632-4ec8f5bd59928cbb85efb15c1ca8b184", "58735632-6605a66e15648a893913be172d2fec55",
                        "58735632-6ffe5b6b9e3d25f6a0f508e04bf3c015", "58735632-75fdf3ae98944dce60d1997f1ca78bd1",
                        "58735632-77ce0873f5f41663c824cc7a7c760813", "58735632-7cd78a23bf1d725973358bb6f41e55ba",
                        "58735632-86614c59eb2d26502e5219d91e1184a5", "58735632-8f00d6340b92ff29fce656d7a689d48a",
                        "58735632-a0ebe4538b0303f1f626d253b3ee0d01", "58735632-a1e1cb77107513f4ede2eca6a0721cd6",
                        "58735632-b3496e25c625803b858635a0a354b71f", "58735632-de2ce1ccfd728f77b3657343acd27c4d",
                        "58735632-fccc129d1fdae6c6bd82ee84f2242288", "58735634-09eb271f56c864492e0917a3115ff153",
                        "58735634-355d44186c3ed2f0b87b491fbea83a67", "58735634-4ec8f5bd59928cbb85efb15c1ca8b184",
                        "58735634-6605a66e15648a893913be172d2fec55", "58735634-6ffe5b6b9e3d25f6a0f508e04bf3c015",
                        "58735634-75fdf3ae98944dce60d1997f1ca78bd1", "58735634-77ce0873f5f41663c824cc7a7c760813",
                        "58735634-7cd78a23bf1d725973358bb6f41e55ba", "58735634-86614c59eb2d26502e5219d91e1184a5",
                        "58735634-8f00d6340b92ff29fce656d7a689d48a", "58735634-a0ebe4538b0303f1f626d253b3ee0d01",
                        "58735634-a1e1cb77107513f4ede2eca6a0721cd6", "58735634-b3496e25c625803b858635a0a354b71f",
                        "58735634-de2ce1ccfd728f77b3657343acd27c4d", "58735634-fccc129d1fdae6c6bd82ee84f2242288",
                        "58735636-09eb271f56c864492e0917a3115ff153", "58735636-355d44186c3ed2f0b87b491fbea83a67",
                        "58735636-4ec8f5bd59928cbb85efb15c1ca8b184", "58735636-6605a66e15648a893913be172d2fec55",
                        "58735636-6ffe5b6b9e3d25f6a0f508e04bf3c015", "58735636-75fdf3ae98944dce60d1997f1ca78bd1",
                        "58735636-77ce0873f5f41663c824cc7a7c760813", "58735636-7cd78a23bf1d725973358bb6f41e55ba",
                        "58735636-86614c59eb2d26502e5219d91e1184a5", "58735636-8f00d6340b92ff29fce656d7a689d48a",
                        "58735636-a0ebe4538b0303f1f626d253b3ee0d01", "58735636-a1e1cb77107513f4ede2eca6a0721cd6",
                        "58735636-b3496e25c625803b858635a0a354b71f", "58735636-de2ce1ccfd728f77b3657343acd27c4d",
                        "58735636-fccc129d1fdae6c6bd82ee84f2242288", "58735638-09eb271f56c864492e0917a3115ff153",
                        "58735638-355d44186c3ed2f0b87b491fbea83a67", "58735638-4ec8f5bd59928cbb85efb15c1ca8b184",
                        "58735638-6605a66e15648a893913be172d2fec55", "58735638-6ffe5b6b9e3d25f6a0f508e04bf3c015",
                        "58735638-75fdf3ae98944dce60d1997f1ca78bd1", "58735638-77ce0873f5f41663c824cc7a7c760813",
                        "58735638-7cd78a23bf1d725973358bb6f41e55ba", "58735638-86614c59eb2d26502e5219d91e1184a5",
                        "58735638-8f00d6340b92ff29fce656d7a689d48a", "58735638-a0ebe4538b0303f1f626d253b3ee0d01",
                        "58735638-a1e1cb77107513f4ede2eca6a0721cd6", "58735638-b3496e25c625803b858635a0a354b71f",
                        "58735638-de2ce1ccfd728f77b3657343acd27c4d", "58735638-fccc129d1fdae6c6bd82ee84f2242288",
                        "58735642-09eb271f56c864492e0917a3115ff153", "58735642-355d44186c3ed2f0b87b491fbea83a67",
                        "58735642-4ec8f5bd59928cbb85efb15c1ca8b184", "58735642-6605a66e15648a893913be172d2fec55",
                        "58735642-6ffe5b6b9e3d25f6a0f508e04bf3c015", "58735642-75fdf3ae98944dce60d1997f1ca78bd1",
                        "58735642-77ce0873f5f41663c824cc7a7c760813", "58735642-7cd78a23bf1d725973358bb6f41e55ba",
                        "58735642-86614c59eb2d26502e5219d91e1184a5", "58735642-8f00d6340b92ff29fce656d7a689d48a",
                        "58735642-a0ebe4538b0303f1f626d253b3ee0d01", "58735642-a1e1cb77107513f4ede2eca6a0721cd6",
                        "58735642-b3496e25c625803b858635a0a354b71f", "58735642-de2ce1ccfd728f77b3657343acd27c4d",
                        "58735642-fccc129d1fdae6c6bd82ee84f2242288", "58735654-09eb271f56c864492e0917a3115ff153",
                        "58735654-355d44186c3ed2f0b87b491fbea83a67", "58735654-4ec8f5bd59928cbb85efb15c1ca8b184",
                        "58735654-6605a66e15648a893913be172d2fec55", "58735654-6ffe5b6b9e3d25f6a0f508e04bf3c015",
                        "58735654-75fdf3ae98944dce60d1997f1ca78bd1", "58735654-77ce0873f5f41663c824cc7a7c760813",
                        "58735654-7cd78a23bf1d725973358bb6f41e55ba", "58735654-86614c59eb2d26502e5219d91e1184a5",
                        "58735654-8f00d6340b92ff29fce656d7a689d48a", "58735654-a0ebe4538b0303f1f626d253b3ee0d01",
                        "58735654-a1e1cb77107513f4ede2eca6a0721cd6", "58735654-b3496e25c625803b858635a0a354b71f",
                        "58735654-de2ce1ccfd728f77b3657343acd27c4d", "58735654-fccc129d1fdae6c6bd82ee84f2242288",
                        "58735668-ecc4090b639c47f89b453980923afb8e", "58735670-4ec8f5bd59928cbb85efb15c1ca8b184",
                        "58735670-6605a66e15648a893913be172d2fec55", "58735670-86614c59eb2d26502e5219d91e1184a5",
                        "58735670-8f00d6340b92ff29fce656d7a689d48a", "58735670-a1e1cb77107513f4ede2eca6a0721cd6",
                        "58735670-b3496e25c625803b858635a0a354b71f", "58735672-09eb271f56c864492e0917a3115ff153",
                        "58735672-355d44186c3ed2f0b87b491fbea83a67", "58735672-4ec8f5bd59928cbb85efb15c1ca8b184",
                        "58735672-6605a66e15648a893913be172d2fec55", "58735672-6ffe5b6b9e3d25f6a0f508e04bf3c015",
                        "58735672-75fdf3ae98944dce60d1997f1ca78bd1", "58735672-77ce0873f5f41663c824cc7a7c760813",
                        "58735672-7cd78a23bf1d725973358bb6f41e55ba", "58735672-86614c59eb2d26502e5219d91e1184a5",
                        "58735672-8f00d6340b92ff29fce656d7a689d48a", "58735672-a0ebe4538b0303f1f626d253b3ee0d01",
                        "58735672-a1e1cb77107513f4ede2eca6a0721cd6", "58735672-b3496e25c625803b858635a0a354b71f",
                        "58735672-de2ce1ccfd728f77b3657343acd27c4d", "58735672-fccc129d1fdae6c6bd82ee84f2242288",
                        "58735678-09eb271f56c864492e0917a3115ff153", "58735678-355d44186c3ed2f0b87b491fbea83a67",
                        "58735678-4ec8f5bd59928cbb85efb15c1ca8b184", "58735678-6605a66e15648a893913be172d2fec55",
                        "58735678-6ffe5b6b9e3d25f6a0f508e04bf3c015", "58735678-75fdf3ae98944dce60d1997f1ca78bd1",
                        "58735678-77ce0873f5f41663c824cc7a7c760813", "58735678-7cd78a23bf1d725973358bb6f41e55ba",
                        "58735678-86614c59eb2d26502e5219d91e1184a5", "58735678-8f00d6340b92ff29fce656d7a689d48a",
                        "58735678-a0ebe4538b0303f1f626d253b3ee0d01", "58735678-a1e1cb77107513f4ede2eca6a0721cd6",
                        "58735678-b3496e25c625803b858635a0a354b71f", "58735678-de2ce1ccfd728f77b3657343acd27c4d",
                        "58735678-fccc129d1fdae6c6bd82ee84f2242288", "58735680-09eb271f56c864492e0917a3115ff153",
                        "58735680-355d44186c3ed2f0b87b491fbea83a67", "58735680-4ec8f5bd59928cbb85efb15c1ca8b184",
                        "58735680-6605a66e15648a893913be172d2fec55", "58735680-6ffe5b6b9e3d25f6a0f508e04bf3c015",
                        "58735680-75fdf3ae98944dce60d1997f1ca78bd1", "58735680-77ce0873f5f41663c824cc7a7c760813",
                        "58735680-7cd78a23bf1d725973358bb6f41e55ba", "58735680-86614c59eb2d26502e5219d91e1184a5",
                        "58735680-8f00d6340b92ff29fce656d7a689d48a", "58735680-a0ebe4538b0303f1f626d253b3ee0d01",
                        "58735680-a1e1cb77107513f4ede2eca6a0721cd6", "58735680-b3496e25c625803b858635a0a354b71f",
                        "58735680-de2ce1ccfd728f77b3657343acd27c4d", "58735680-fccc129d1fdae6c6bd82ee84f2242288",
                        "58735682-09eb271f56c864492e0917a3115ff153", "58735682-4ec8f5bd59928cbb85efb15c1ca8b184",
                        "58735682-6605a66e15648a893913be172d2fec55", "58735682-6ffe5b6b9e3d25f6a0f508e04bf3c015",
                        "58735682-75fdf3ae98944dce60d1997f1ca78bd1", "58735682-77ce0873f5f41663c824cc7a7c760813",
                        "58735682-7cd78a23bf1d725973358bb6f41e55ba", "58735682-86614c59eb2d26502e5219d91e1184a5",
                        "58735682-8f00d6340b92ff29fce656d7a689d48a", "58735682-a0ebe4538b0303f1f626d253b3ee0d01",
                        "58735682-a1e1cb77107513f4ede2eca6a0721cd6", "58735682-b3496e25c625803b858635a0a354b71f",
                        "58735682-de2ce1ccfd728f77b3657343acd27c4d", "58735682-fccc129d1fdae6c6bd82ee84f2242288",
                        "58735684-09eb271f56c864492e0917a3115ff153", "58735684-355d44186c3ed2f0b87b491fbea83a67",
                        "58735684-4ec8f5bd59928cbb85efb15c1ca8b184", "58735684-6605a66e15648a893913be172d2fec55",
                        "58735684-6ffe5b6b9e3d25f6a0f508e04bf3c015", "58735684-75fdf3ae98944dce60d1997f1ca78bd1",
                        "58735684-77ce0873f5f41663c824cc7a7c760813", "58735684-7cd78a23bf1d725973358bb6f41e55ba",
                        "58735684-86614c59eb2d26502e5219d91e1184a5", "58735684-8f00d6340b92ff29fce656d7a689d48a",
                        "58735684-a0ebe4538b0303f1f626d253b3ee0d01", "58735684-a1e1cb77107513f4ede2eca6a0721cd6",
                        "58735684-b3496e25c625803b858635a0a354b71f", "58735684-de2ce1ccfd728f77b3657343acd27c4d",
                        "58735684-fccc129d1fdae6c6bd82ee84f2242288", "58735686-ecc4090b639c47f89b453980923afb8e",
                        "58735688-ecc4090b639c47f89b453980923afb8e", "58735690-ecc4090b639c47f89b453980923afb8e",
                        "58736044-ecc4090b639c47f89b453980923afb8e", "58736107-ecc4090b639c47f89b453980923afb8e",
                        "58736109-ecc4090b639c47f89b453980923afb8e", "58736111-ecc4090b639c47f89b453980923afb8e",
                        "58736113-ecc4090b639c47f89b453980923afb8e", "58736225-ecc4090b639c47f89b453980923afb8e",
                        "58736278-ecc4090b639c47f89b453980923afb8e", "58736280-ecc4090b639c47f89b453980923afb8e",
                        "58736288-ecc4090b639c47f89b453980923afb8e", "58736294-ecc4090b639c47f89b453980923afb8e",
                        "58736298-09a7d97ee9f97f73dde58c571a1862fe", "58736298-2308c416091d831c0f3be7b9cd82af20",
                        "58736384-09a7d97ee9f97f73dde58c571a1862fe", "58736384-2308c416091d831c0f3be7b9cd82af20",
                        "58736438-09a7d97ee9f97f73dde58c571a1862fe", "58736438-2308c416091d831c0f3be7b9cd82af20",
                        "58736440-4ec8f5bd59928cbb85efb15c1ca8b184", "58736440-6605a66e15648a893913be172d2fec55",
                        "58736440-86614c59eb2d26502e5219d91e1184a5", "58736440-8f00d6340b92ff29fce656d7a689d48a",
                        "58736440-a1e1cb77107513f4ede2eca6a0721cd6", "58736440-b3496e25c625803b858635a0a354b71f",
                        "58736488-09a7d97ee9f97f73dde58c571a1862fe", "58736488-2308c416091d831c0f3be7b9cd82af20",
                        "58736537-09a7d97ee9f97f73dde58c571a1862fe", "58736537-2308c416091d831c0f3be7b9cd82af20",
                        "58736539-ecc4090b639c47f89b453980923afb8e", "58736542-770147e4585a65bbcec7ed6492e8bf95",
                        "58736546-ecc4090b639c47f89b453980923afb8e", "58736548-ecc4090b639c47f89b453980923afb8e",
                        "58736552-ecc4090b639c47f89b453980923afb8e", "58736693-ecc4090b639c47f89b453980923afb8e",
                        "58736707-ecc4090b639c47f89b453980923afb8e", "58736711-ecc4090b639c47f89b453980923afb8e",
                        "58736713-ecc4090b639c47f89b453980923afb8e", "58736715-ecc4090b639c47f89b453980923afb8e",
                        "58736717-ecc4090b639c47f89b453980923afb8e", "58736720-ecc4090b639c47f89b453980923afb8e",
                        "58736787-ecc4090b639c47f89b453980923afb8e", "58736790-ecc4090b639c47f89b453980923afb8e",
                        "58736938-ecc4090b639c47f89b453980923afb8e", "58737018-ecc4090b639c47f89b453980923afb8e",
                        "58737022-ecc4090b639c47f89b453980923afb8e", "58737024-ecc4090b639c47f89b453980923afb8e",
                        "58737026-ecc4090b639c47f89b453980923afb8e", "58737029-ecc4090b639c47f89b453980923afb8e",
                        "58737031-ecc4090b639c47f89b453980923afb8e", "58737033-ecc4090b639c47f89b453980923afb8e",
                        "58737035-ecc4090b639c47f89b453980923afb8e", "58737051-ecc4090b639c47f89b453980923afb8e",
                        "58737094-ecc4090b639c47f89b453980923afb8e", "58737096-ecc4090b639c47f89b453980923afb8e",
                        "58737098-ecc4090b639c47f89b453980923afb8e", "58737122-09a7d97ee9f97f73dde58c571a1862fe",
                        "58737122-2308c416091d831c0f3be7b9cd82af20", "58737128-2308c416091d831c0f3be7b9cd82af20",
                        "58737130-2308c416091d831c0f3be7b9cd82af20", "58737132-ecc4090b639c47f89b453980923afb8e",
                        "58737136-ecc4090b639c47f89b453980923afb8e", "58737138-ecc4090b639c47f89b453980923afb8e",
                        "58741751-ecc4090b639c47f89b453980923afb8e", "58867598-0e2cecd2ca2b544ed975af9fd5a484d9",
                        "58867598-859638235fa9d4859ecc65b646a10305", "58867598-a71bc550f6e21382cbc6874edd35f51b",
                        "58867598-bd48daefe7af82726f3922d875380d8b", "58867598-c755d1e7f3c2cd40d1fc1ad504e17281",
                        "58867598-f98a6a42d119992e9e8c79bfecaa0808", "58895010-ecc4090b639c47f89b453980923afb8e",
                        "58895232-ecc4090b639c47f89b453980923afb8e", "58941077-16553c87dfc1d8880c3cdd398fb68ce5",
                        "58941077-967238de9e74011a10ada1b58a112501", "58941797-c1f24cc96f82d596c544e052a8c96757",
                        "58941797-c4081a143fb8d22e65c662d7e5485d97", "58942407-ecc4090b639c47f89b453980923afb8e",
                        "58942428-ecc4090b639c47f89b453980923afb8e", "58944224-ecc4090b639c47f89b453980923afb8e",
                        "58944225-ecc4090b639c47f89b453980923afb8e", "58944468-ecc4090b639c47f89b453980923afb8e",
                        "58949541-ecc4090b639c47f89b453980923afb8e", "58950081-b49635317e4a12d19f83241b657d1858",
                        "59070208-6940c9e9bd90a0d8ddccef178992a46d", "59070208-7a6e3aaea8965c11fbb407ae58b39ad6",
                        "59070208-83ace525eeff5530d8986a206dc555bf", "59070208-96359ea7676df2eea9ce9acf7c538005",
                        "59076218-6f04aaa51ddfbe2aad2e5d139528bc95", "59077671-cdfe59eb911f5b727240c310df9fad57",
                        "59083052-6f04aaa51ddfbe2aad2e5d139528bc95", "59086857-6f04aaa51ddfbe2aad2e5d139528bc95",
                        "59087414-88361eccfd32c1e72deaddf0c03c5491", "6014-e3d4f8c9df8d3ddf344d1d4ea1603b4a",
                        "6024-d46389875f39726d27af6c3e81fdcf77", "6024-e3d4f8c9df8d3ddf344d1d4ea1603b4a",
                        "6064-128010f98e698bb5365cc41a972e4b9f", "6626-bba69a3033db10d7908615a3dedeb6c5",
                        "6720750-ecc4090b639c47f89b453980923afb8e", "6886-ecc4090b639c47f89b453980923afb8e",
                        "7030302-ecc4090b639c47f89b453980923afb8e", "8979-3845321bfcf28ddc40b4cca6613834a3",
                        "9017-fd619f0359020094b63a73656691297d", "9100-ecc4090b639c47f89b453980923afb8e"
                }), null, 0);
    }

    /**
     * 所有商品skuId查询
     *
     * @throws Exception
     */
    public void queryAllGoodsId() throws Exception {
        String obj = send(new TreeMap<String, String>(), "queryAllGoodsId");
        System.out.println(JSON.parseObject(obj).getJSONArray("goodsInfo"));
    }

    /**
     * 查询渠道下所有的goodsId和对应的skuId
     *
     * @throws Exception
     */
    public void queryAllGoodsIdAndSkuId() throws Exception {
        TreeMap<String, String> parameterMap = new TreeMap<>();
        send(parameterMap, "queryAllGoodsIdAndSkuId");
    }

    /**
     * 单个商品详情查询
     *
     * @param skuId            必传
     * @param channelSalePrice 渠道销售价，返回税后价
     * @throws Exception
     */
    public JSONObject queryGoodsInfoById(String skuId, String channelSalePrice) throws Exception {
        TreeMap<String, String> parameterMap = new TreeMap<>();
        parameterMap.put("skuId", skuId);
        if (channelSalePrice != null) {
            parameterMap.put("channelSalePrice", channelSalePrice);
        }
        parameterMap.put("queryType", "0");
        JSONObject response = JSON.parseObject(send(parameterMap, "queryGoodsInfoById"));
        if (200 != response.getInteger("recCode")) {
            throw new Exception(response.getString("recMeg"));
        }
        return response;
    }

    /**
     * 商品详情批量查询
     *
     * @param skuIds            商品skuId
     * @param channelSalePrices 渠道税前价
     * @param queryType         0表示返回全部信息、1表示只返回关键信息如：商品id、价格和库存
     * @throws Exception
     */
    public JSONArray queryGoodsInfoByIds(List<String> skuIds, List<String> channelSalePrices, int queryType)
            throws Exception {
        TreeMap<String, String> parameterMap = new TreeMap<>();
        List<String> skuList = new ArrayList<>();
        skuIds.forEach(skuId -> skuList.add("\"" + skuId + "\""));
        parameterMap.put("skuIds", skuList.toString());
        if (channelSalePrices != null && channelSalePrices.size() == skuIds.size()) {
            parameterMap.put("channelSalePrices", channelSalePrices.toString());
        }
        parameterMap.put("queryType", String.valueOf(queryType));
        String response = send(parameterMap, "queryGoodsInfoByIds");
        JSONArray goodsInfoArray = JSONArray.parseArray(response);
        return goodsInfoArray;
    }

    /**
     * 根据goodsId(前台)查询出下面所有的skuId
     *
     * @param goodsIds 商品id
     * @throws Exception
     */
    public JSONObject querySkuIdsByGoodsIds(List<String> goodsIds) throws Exception {
        TreeMap<String, String> parameterMap = new TreeMap<>();
        parameterMap.put("goodsIds", goodsIds.toString());
        JSONObject response = JSON.parseObject(send(parameterMap, "querySkuIdsByGoodsIds"));
        if (200 != response.getInteger("recCode")) {
            throw new Exception(response.getString("recMeg"));
        }
        return response;
    }

    /**
     * 下单接口(分销2.0)
     *
     * @param thirdPartOrderId 第三方订单ID（长度最大32）
     * @param userInfo         用户信息，包括地址、实名等
     * @param orderItemLists   订单项
     * @throws Exception
     */
    public JSONObject bookOrder(String thirdPartOrderId, UserInfo userInfo, List<OrderItem> orderItemLists)
            throws Exception {
        TreeMap<String, String> parameterMap = new TreeMap<>();
        parameterMap.put("thirdPartOrderId", thirdPartOrderId);
        parameterMap.put("userInfo", ParameUtil.userInfo(userInfo));
        parameterMap.put("orderItemList", ParameUtil.orderItemLists(orderItemLists));
        JSONObject response = JSON.parseObject(send(parameterMap, "bookorder"));
        if (200 != response.getInteger("recCode")) {
            throw new Exception(response.getString("recMeg"));
        }
        return response;
    }

    /**
     * 代下单代支付(分销2.0)
     *
     * @param thirdPartOrderId 第三方订单ID（长度最大32）
     * @param userInfo         用户信息，包括地址、实名等
     * @param orderItemLists   订单项
     * @throws Exception
     */
    public void bookPayOrder(String thirdPartOrderId, UserInfo userInfo, List<OrderItem> orderItemLists)
            throws Exception {
        TreeMap<String, String> parameterMap = new TreeMap<>();
        parameterMap.put("thirdPartOrderId", thirdPartOrderId);
        parameterMap.put("userInfo", ParameUtil.userInfo(userInfo));
        parameterMap.put("orderItemList", ParameUtil.orderItemLists(orderItemLists));
        String result = send(parameterMap, "bookpayorder");
        JSONObject response = JSON.parseObject(result);
        if (200 != response.getInteger("recCode")) {
            throw new Exception(response.getString("recMeg"));
        }
    }

    /**
     * 订单确认(分销2.0)
     *
     * @throws Exception
     */
    public JSONObject orderConfirm(UserInfo userInfo, List<OrderItem> orderItemList) throws Exception {
        TreeMap<String, String> parameterMap = new TreeMap<>();
        parameterMap.put("userInfo", ParameUtil.userInfo(userInfo));
        parameterMap.put("orderItemList", ParameUtil.orderItemLists(orderItemList));
        JSONObject response = JSON.parseObject(send(parameterMap, "orderConfirm"));
        // if (200 != response.getInteger("recCode")) {
        // throw new Exception(response.getString("recMeg"));
        // }
        return response;
    }

    /**
     * 支付接口
     *
     * @param thirdPartOrderId 分销商订单ID
     * @throws Exception
     */
    public void payOrder(String thirdPartOrderId) throws Exception {
        TreeMap<String, String> parameterMap = new TreeMap<>();
        parameterMap.put("thirdPartOrderId", thirdPartOrderId);
        String result = send(parameterMap, "payOrder");
        JSONObject response = JSON.parseObject(result);
        if (200 != response.getInteger("recCode")) {
            throw new Exception(response.getString("recMeg"));
        }
    }

    /**
     * 渠道订订单取消接口
     *
     * @param thirdPartOrderId 第三方订单Id
     * @param closeReason      关闭订单原因（枚举值请按照下面请求示例中给出的枚举传入）
     * @param channelId        渠道Id
     * @param remark           备注
     * @throws Exception
     */
    public JSONObject cancelOrder(String thirdPartOrderId, CloseReason closeReason, String remark) throws Exception {
        TreeMap<String, String> parameterMap = new TreeMap<>();
        parameterMap.put("thirdpartOrderId", thirdPartOrderId);
        parameterMap.put("reasonId", String.valueOf(closeReason.getId()));
        parameterMap.put("channelId", orderProperties.getKaolaChannelId());
        if (remark != null) {
            parameterMap.put("remark", remark);
        }
        JSONObject response = JSON.parseObject(send(parameterMap, "cancelOrder"));
        // if (200 != response.getInteger("recCode")) {
        // throw new Exception(response.getString("recMeg"));
        // }
        return response;
    }

    /**
     * 渠道订单关闭接口
     *
     * @param thirdPartOrderId 第三方订单Id
     * @param closeReason      关闭订单原因（枚举值请按照下面请求示例中给出的枚举传入）
     * @param channelId        渠道Id
     * @throws Exception
     */
    public void closeOrder(String thirdPartOrderId, CloseReason closeReason) throws Exception {
        TreeMap<String, String> parameterMap = new TreeMap<>();
        parameterMap.put("thirdpartOrderId", thirdPartOrderId);
        parameterMap.put("reasonId", String.valueOf(closeReason.getId()));
        parameterMap.put("channelId", orderProperties.getKaolaChannelId());
        JSONObject response = JSON.parseObject(send(parameterMap, "closeOrder"));
        if (200 != response.getInteger("closeCode")) {
            throw new Exception(response.getString("closeCodeDesc"));
        }
    }
    /******************************************************************************************************************************************/

    /**
     * 渠道根据前台goodsId拉取评论数据
     *
     * @param goodsId   商品Id
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @throws Exception
     */
    public void queryCommentInfo(String goodsId, Date startTime, Date endTime) throws Exception {
        TreeMap<String, String> parameterMap = new TreeMap<>();
        parameterMap.put("goodsId", goodsId);
        parameterMap.put("startTime", String.valueOf(startTime.getTime()));
        parameterMap.put("endTime", String.valueOf(endTime.getTime()));
        send(parameterMap, "queryCommentInfo");
    }

    /**
     * 订单状态查询
     *
     * @param thirdPartOrderId 分销商订单ID
     * @throws Exception
     */
    public KaolaOrderStatus queryOrderStatus(String thirdPartOrderId) throws Exception {
        TreeMap<String, String> parameterMap = new TreeMap<>();
        parameterMap.put("thirdPartOrderId", thirdPartOrderId);
        String response = send(parameterMap, "queryOrderStatus");
        JSONObject obj = JSON.parseObject(response);
        JSONArray results = obj.getJSONArray("result");
        obj.remove("result");
        List<KaolaOrderItemStatus> result = new ArrayList<>();
        for (int i = 0; i < results.size(); i++) {
            KaolaOrderItemStatus kaolaOrderItemStatus = JSON.toJavaObject(results.getJSONObject(i),
                    KaolaOrderItemStatus.class);
            result.add(kaolaOrderItemStatus);
        }
        JSONObject trackLogisticss = obj.getJSONObject("trackLogistics");
        obj.remove("trackLogistics");
        Map<String, List<TrackLogistics>> map = new HashMap<>();
        for (String key : trackLogisticss.keySet()) {
            JSONArray trackLogisticsArray = trackLogisticss.getJSONArray(key);
            List<TrackLogistics> list = new ArrayList<>();
            for (int i = 0; i < trackLogisticsArray.size(); i++) {
                TrackLogistics trackLogistics = JSON.toJavaObject(trackLogisticsArray.getJSONObject(i),
                        TrackLogistics.class);
                list.add(trackLogistics);
            }
            map.put(key, list);
        }
        KaolaOrderStatus orderStatus = JSON.toJavaObject(obj, KaolaOrderStatus.class);
        orderStatus.setTrackLogisticss(map);
        orderStatus.setResult(result);
        return orderStatus;
    }

}
