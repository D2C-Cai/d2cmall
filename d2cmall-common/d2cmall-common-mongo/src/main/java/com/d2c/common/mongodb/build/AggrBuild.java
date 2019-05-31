package com.d2c.common.mongodb.build;

import com.d2c.common.base.utils.StringUt;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.ArrayList;
import java.util.List;

public class AggrBuild {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private Criteria cri;
    private List<AggregationOperation> list = new ArrayList<>();

    public static AggrBuild build() {
        return new AggrBuild();
    }

    public Criteria and(String key) {
        if (cri == null) {
            cri = Criteria.where(key);
            add(Aggregation.match(cri));
        } else {
            cri = cri.and(key);
        }
        return cri;
    }

    public AggrBuild add(AggregationOperation... aggrs) {
        for (AggregationOperation aggr : aggrs) {
            add(aggr);
        }
        return this;
    }

    public AggrBuild add(AggregationOperation aggr) {
        list.add(aggr);
        return this;
    }

    @Override
    public String toString() {
        return "[" + StringUt.join(toJsons(), ",") + "]";
    }

    public List<String> toJsons() {
        List<String> strs = new ArrayList<>();
        for (AggregationOperation ao : list) {
            strs.add(ao.toDocument(Aggregation.DEFAULT_CONTEXT).toString());
        }
        return strs;
    }

    public List<Document> toBson() {
        List<Document> strs = new ArrayList<>();
        for (AggregationOperation ao : list) {
            strs.add(ao.toDocument(Aggregation.DEFAULT_CONTEXT));
        }
        return strs;
    }

    public Aggregation newAggregation() {
        logger.debug("AggrBuild 查询: " + this.toString());
        return Aggregation.newAggregation(list);
    }

}
