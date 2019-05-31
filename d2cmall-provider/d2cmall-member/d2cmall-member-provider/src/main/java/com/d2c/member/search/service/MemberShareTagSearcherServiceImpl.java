package com.d2c.member.search.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.frame.provider.config.ElasticSearchConfig;
import com.d2c.member.search.model.SearcherMemberShareTag;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolFilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service(protocol = "dubbo")
public class MemberShareTagSearcherServiceImpl implements MemberShareTagSearcherService {

    private static final Log logger = LogFactory.getLog(MemberShareTagSearcherServiceImpl.class);
    @Autowired
    private ElasticSearchConfig elasticSearchConfig;

    @Override
    public int insert(SearcherMemberShareTag tag) {
        try {
            if (tag == null)
                return 0;
            // 如果是非上架状态不建立索引
            XContentBuilder builder = XContentFactory.jsonBuilder().startObject();
            ElasticSearchConfig.buildContent(builder, tag);
            builder.endObject();
            elasticSearchConfig.getClient()
                    .prepareIndex(ElasticSearchConfig.INDEX_NAME, TYPE_MEMBERSHARE_TAG, tag.getId().toString())
                    .setSource(builder).execute().actionGet();
            logger.info("ID为" + tag.getId() + "的买家秀标签建立索引成功!标签：" + tag.getName());
            return 1;
        } catch (ElasticsearchException e) {
            logger.error(e);
            logger.info("Elasticsearch Exception: " + e.toString());
        } catch (IOException e) {
            logger.error(e);
        }
        return 0;
    }

    @Override
    public SearcherMemberShareTag findById(String id) {
        GetResponse response = elasticSearchConfig.getClient()
                .prepareGet(ElasticSearchConfig.INDEX_NAME, TYPE_MEMBERSHARE_TAG, id).execute().actionGet();
        SearcherMemberShareTag tag = null;
        if (response != null && response.isExists()) {
            Long tagId = Long.valueOf(response.getId());
            tag = new SearcherMemberShareTag();
            Map<String, Object> source = response.getSource();
            if (source != null && !source.isEmpty()) {
                ElasticSearchConfig.transMap2Bean(source, tag);
                tag.setId(tagId);
                return tag;
            }
        }
        return tag;
    }

    @Override
    public SearcherMemberShareTag findByCode(String code) {
        SearchRequestBuilder builder = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setTypes(TYPE_MEMBERSHARE_TAG).setSearchType(SearchType.DEFAULT);
        BoolFilterBuilder boolFilter = FilterBuilders.boolFilter();
        boolFilter.must(FilterBuilders.termFilter("code", code));
        builder.setPostFilter(boolFilter);
        SearchResponse response = builder.execute().actionGet();
        SearcherMemberShareTag tag = new SearcherMemberShareTag();
        for (SearchHit hit : response.getHits()) {
            Long tagId = Long.valueOf(hit.getId());
            Map<String, Object> source = hit.getSource();
            if (!source.isEmpty()) {
                ElasticSearchConfig.transMap2Bean(source, tag);
                tag.setId(tagId);
                break;
            }
        }
        return tag;
    }

    @Override
    public PageResult<SearcherMemberShareTag> search(PageModel page) {
        QueryBuilder qb = null;
        SearchRequestBuilder builder = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setTypes(TYPE_MEMBERSHARE_TAG).setSearchType(SearchType.DEFAULT).setQuery(qb);
        builder.addSort("sort", SortOrder.DESC);
        SearchResponse response = builder.setSearchType(SearchType.DEFAULT)
                .setFrom((page.getPageNumber() - 1) * page.getPageSize()).setSize(page.getPageSize()).setExplain(true)
                .execute().actionGet();
        List<SearcherMemberShareTag> tags = new ArrayList<SearcherMemberShareTag>();
        for (SearchHit hit : response.getHits()) {
            Long tagId = Long.valueOf(hit.getId());
            Map<String, Object> source = hit.getSource();
            if (!source.isEmpty()) {
                SearcherMemberShareTag tag = new SearcherMemberShareTag();
                ElasticSearchConfig.transMap2Bean(source, tag);
                tag.setId(tagId);
                tags.add(tag);
            }
        }
        PageResult<SearcherMemberShareTag> pager = new PageResult<SearcherMemberShareTag>(page);
        pager.setTotalCount(Long.valueOf(response.getHits().getTotalHits()).intValue());
        pager.setList(tags);
        return pager;
    }

    @Override
    public int update(SearcherMemberShareTag tag) {
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder().startObject().field("id", tag.getId());
            if (tag.getName() != null) {
                builder.field("name", tag.getName());
            }
            if (tag.getSort() != null) {
                builder.field("sort", tag.getSort());
            }
            if (tag.getStatus() != null) {
                builder.field("status", tag.getStatus());
            }
            builder.endObject();
            elasticSearchConfig.getClient()
                    .prepareUpdate(ElasticSearchConfig.INDEX_NAME, TYPE_MEMBERSHARE_TAG, tag.getId().toString())
                    .setDoc(builder).execute().actionGet();
            logger.info("ID为" + tag.getId() + "的买家秀标签更新索引成功!标签：" + tag.getName());
            return 1;
        } catch (Exception e) {
            logger.error(e);
        }
        return 0;
    }

    @Override
    public int rebuild(SearcherMemberShareTag tag) {
        remove(tag.getId());
        return insert(tag);
    }

    @Override
    public int remove(Long tagId) {
        String id = tagId.toString();
        elasticSearchConfig.getClient().prepareDelete(ElasticSearchConfig.INDEX_NAME, TYPE_MEMBERSHARE_TAG, id)
                .execute().actionGet();
        logger.info("ID为" + id + "的买家秀标签已删除索引!");
        return 1;
    }

    @Override
    public void removeAll() {
        elasticSearchConfig.getClient().prepareDeleteByQuery(ElasticSearchConfig.INDEX_NAME)
                .setTypes(TYPE_MEMBERSHARE_TAG).setQuery(QueryBuilders.matchAllQuery()).execute().actionGet();
    }

}
