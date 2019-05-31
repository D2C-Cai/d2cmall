package com.d2c.common.mongodb.base;

import com.d2c.common.api.page.Pager;
import com.d2c.common.api.page.base.PageSort;
import com.d2c.common.core.base.GenericAble;
import com.d2c.common.mongodb.model.SuperMongoDO;
import com.d2c.common.mongodb.page.PageHandler;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import net.sf.json.JSONObject;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.BulkOperations.BulkMode;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public class BaseMongoDao<T> extends GenericAble<T> {

    @Autowired
    protected MongoTemplate mongoTemplate;

    public T insert(T bean) {
        addGmtModified(bean);
        mongoTemplate.insert(bean);
        return bean;
    }

    public Collection<T> insert(Collection<T> list) {
        BulkOperations oper = mongoTemplate.bulkOps(BulkMode.UNORDERED, getTypeClazz());
        for (T bean : list) {
            addGmtModified(bean);
            oper.insert(bean);
        }
        oper.execute();
        return list;
    }

    public T save(T bean) {
        addGmtModified(bean);
        mongoTemplate.save(bean);
        return bean;
    }

    public Collection<T> saveAll(Collection<T> list) {
        for (T bean : list) {
            save(bean);
        }
        return list;
    }

    public T findById(Object id) {
        return mongoTemplate.findById(id, getTypeClazz());
    }

    public T findById(Object id, String collectionName) {
        return mongoTemplate.findById(id, getTypeClazz(), collectionName);
    }

    public List<T> findAll() {
        return mongoTemplate.findAll(getTypeClazz());
    }

    public List<T> findAll(String collectionName) {
        return mongoTemplate.findAll(getTypeClazz(), collectionName);
    }

    public List<T> find(Query query) {
        return mongoTemplate.find(query, getTypeClazz());
    }

    public List<T> find(Criteria cri) {
        return find(new Query(cri));
    }

    public List<T> find(String fieldName, Object fieldValue) {
        return mongoTemplate.find(new Query(Criteria.where(fieldName).is(fieldValue)), getTypeClazz());
    }

    public List<T> findBean(Document bson) {
        return find(new BasicQuery(bson));
    }

    public List<T> findJson(String queryJson) {
        return find(new BasicQuery(queryJson));
    }

    public T findOne(Query query) {
        return mongoTemplate.findOne(query, getTypeClazz());
    }

    public T findOne(String fieldName, Object fieldValue) {
        return mongoTemplate.findOne(new Query(Criteria.where(fieldName).is(fieldValue)), getTypeClazz());
    }

    public List<T> findPage(Query query, Integer page, Integer limit, String orderField, boolean isAsc) {
        return find(PageHandler.init(query, page, limit, orderField, isAsc));
    }

    public List<T> findPage(Query query, Integer page, Integer limit) {
        return find(PageHandler.init(query, page, limit));
    }

    public List<T> findPage(int page, int limit) {
        return find(PageHandler.init(null, page, limit));
    }

    public List<T> findPage(Query query, Pager pager) {
        return find(PageHandler.init(query, pager));
    }

    public List<T> findPage(Query query, Pager pager, String orderField, boolean isAsc) {
        pager.setPageSort(new PageSort(isAsc, orderField));
        return find(PageHandler.init(query, pager));
    }

    public List<T> findLimit(Query query, int limit) {
        return find(PageHandler.init(query, limit));
    }

    public List<T> findPageBean(Document bson, Integer page, Integer limit, String orderField, boolean isAsc) {
        return findPage(new BasicQuery(bson), page, limit, orderField, isAsc);
    }

    public List<T> findPageBean(Document bson, Integer page, Integer limit) {
        return findPage(new BasicQuery(bson), page, limit);
    }

    public List<T> findPageJson(String queryJson, Integer page, Integer limit, String orderField, boolean isAsc) {
        return findPage(new BasicQuery(queryJson), page, limit, orderField, isAsc);
    }

    public List<T> findPageJson(String queryJson, Integer page, Integer limit) {
        return findPage(new BasicQuery(queryJson), page, limit);
    }

    /**
     * 是否已存在
     */
    public boolean exist(Object id) {
        return findById(id) != null;
    }

    public long count(Query query) {
        return mongoTemplate.count(query, getTypeClazz());
    }

    public long countAll() {
        return count(new Query());
    }

    public UpdateResult update(Query query, Update update) {
        if (update == null) {
            return null;
        }
        return mongoTemplate.updateMulti(query, update, getTypeClazz());
    }

    public T updateOne(Query query, Update update) {
        if (update == null) {
            return null;
        }
        return mongoTemplate.findAndModify(query, update, getTypeClazz());
    }

    public long deleteById(Object id) {
        return deleteByQuery(new Query(Criteria.where("id").is(id)));
    }

    public long deleteByQuery(Query query) {
        DeleteResult res = mongoTemplate.remove(query, getTypeClazz());
        return res.getDeletedCount();
    }

    public void cleanAll() {
        mongoTemplate.dropCollection(getTypeClazz());
    }

    public List<JSONObject> aggregate(Aggregation aggregation, Class<?> inputType) {
        return aggregate(aggregation, inputType, JSONObject.class);
    }

    public JSONObject aggregateOne(Aggregation aggregation, Class<?> inputType) {
        return aggregateOne(aggregation, inputType, JSONObject.class);
    }

    public <E> List<E> aggregate(Aggregation aggregation, Class<?> inputType, Class<E> outputType) {
        return mongoTemplate.aggregate(aggregation, inputType, outputType).getMappedResults();
    }

    public <E> E aggregateOne(Aggregation aggregation, Class<?> inputType, Class<E> outputType) {
        List<E> list = aggregate(aggregation, inputType, outputType);
        if (list.isEmpty()) return null;
        return list.get(0);
    }
    //***************** private ******************

    protected Aggregation newAggregation(List<? extends AggregationOperation> operations) {
        return Aggregation.newAggregation(operations);
    }

    protected Aggregation newAggregation(AggregationOperation... operations) {
        return Aggregation.newAggregation(operations);
    }

    protected void addGmtModified(T bean) {
        if (bean instanceof SuperMongoDO) {
            ((SuperMongoDO) bean).setGmtModified(new Date());
        }
    }

}
