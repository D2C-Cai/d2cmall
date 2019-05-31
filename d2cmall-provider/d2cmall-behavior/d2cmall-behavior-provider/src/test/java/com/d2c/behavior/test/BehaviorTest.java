package com.d2c.behavior.test;

import com.d2c.behavior.mongo.dto.EventDTO;
import com.d2c.common.api.json.JsonBean;
import com.d2c.common.core.query.MyCriteria;
import com.d2c.common.core.query.MyCriteriaResolver;
import com.d2c.common.core.test.SimpleTest;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

public class BehaviorTest extends SimpleTest {

    @Test
    public void test() throws IOException {
        logger.info("开始执行测试....");
        MyCriteria cri = MyCriteria.where("data").all("wull", "aaa");
        JsonBean bean = cri.getJsonBean();
        String json = cri.getJsonBean().toJson();
        logger.info("json...." + json);
        EventDTO obj = new EventDTO();
        obj.setEvent("wull");
        obj.setData(Arrays.asList("wull", "aaa", "bbb"));
        boolean check = MyCriteriaResolver.check(bean, obj);
        logger.info("check...." + check);
//		JsonList list = new JsonList();
//		list.add(cri.getJsonBean());
//		
//		JsonBean jsonBean = JsonUt.deserialize(json, JsonBeanImpl.class);
//		logger.info("jsonBean ..." + jsonBean);
//		
//		JsonBean jsonBean1 = JsonBean.build(list.toJson());
//		logger.info("JsonList ..." + jsonBean1);
    }

}
