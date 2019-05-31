package com.d2c.behavior.test;

import com.d2c.behavior.api.mongo.model.TagDO;
import com.d2c.behavior.api.mongo.model.TagTypeDO;
import com.d2c.behavior.api.mongo.model.depict.DepictDO;
import com.d2c.behavior.api.mongo.model.depict.DepictTagDO;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.common.base.utils.JsonUt;
import com.d2c.common.mongodb.utils.BsonUt;
import org.bson.Document;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class BsonTest {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void test() {
        logger.info("开始执行测试...." + BeanUt.isBasic(Document.class));
        TagTypeDO type = new TagTypeDO();
        type.setName("测试类型");
        List<TagDO> tags = new ArrayList<>();
        tags.add(new TagDO(1, "test1"));
        tags.add(new TagDO(2, "test2"));
        type.setTags(tags);
        DepictDO depict = new DepictDO("111", 222L, type);
        logger.info("DepictDO ...." + JsonUt.serialize(depict));
        Document doc = BsonUt.toBson(depict);
        logger.info("Document ...." + JsonUt.serialize(doc));
        DepictDO dto = BsonUt.toBean(doc, DepictDO.class);
        logger.info("BsonUt.toBean ...." + JsonUt.serialize(dto));
        Document doc1 = BsonUt.toBson(doc);
        logger.info("BsonUt.toBson ...." + JsonUt.serialize(doc1));
        DepictTagDO dt1 = dto.getDepictTags().get("test1");
        logger.info("DepictTagDO test1 ...." + JsonUt.serialize(dt1));
    }

}
