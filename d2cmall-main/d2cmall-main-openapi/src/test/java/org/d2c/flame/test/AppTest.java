package org.d2c.flame.test;

import com.d2c.common.base.utils.HttpUt;
import com.d2c.common.core.test.BaseTest;
import com.d2c.util.http.HttpUtil;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 测试
 */
public class AppTest extends BaseTest {

    @Test
    public void test() {
        Map<String, Object> params = new HashMap<>();
        params.put("token", "3b446a5b-9607-4e09-8c3d-12047e82764b");
        Long time = new Date().getTime();
        params.put("timestamp", time);
        params.put("nonce", "");
        // String sign =
        // tokenHandler.signToken("3b446a5b-9607-4e09-8c3d-12047e82764b",
        // "Cmj3Gl2u38Uk5T3biE3TfElXrtMuipGNMkV6HOTU5iS_4qqJ74Q2ofSAvsAA", time,
        // null, params);
        // params.put("sign", sign);
        params.put("requisition_id", 230718);
        params.put("delivery_corp", "sf");
        params.put("delivery_sn", "123123");
        params.put("delivery_quantity", 1);
        String result = HttpUtil.sendPostHttps("http://localhost:8060/openapi/requisition/delivery", params, null);
        System.out.println(result);
    }

    @SuppressWarnings("unused")
    private String getUrlParams(Map<String, Object> params) {
        if (params == null || params.isEmpty())
            return "";
        params.remove("token");
        params.remove("timestamp");
        params.remove("nonce");
        params.remove("sign");
        return HttpUt.getUrlParams(params);
    }

}
