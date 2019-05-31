package com.d2c.behavior.test;

import com.d2c.common.mongodb.build.AggrBuild;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;

public class AggrTest {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void test() {
        logger.info("开始执行测试....");
        AggrBuild ab = AggrBuild.build();
        ab.and("name").is("wull");
        logger.info("AggrBuild.build()...." + ab.toBson());
        logger.info("Aggregation match...." + Aggregation.match(Criteria.where("name").is("wull")).toDocument(Aggregation.DEFAULT_CONTEXT));
        logger.info("AggrBuild newAggregation...." + ab.newAggregation());
        logger.info("Aggregation...." + Aggregation.newAggregation(Aggregation.match(Criteria.where("name").is("wull"))).toString());
    }

}
