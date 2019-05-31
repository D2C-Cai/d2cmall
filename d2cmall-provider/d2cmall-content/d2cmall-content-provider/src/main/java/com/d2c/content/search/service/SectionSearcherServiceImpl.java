package com.d2c.content.search.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.content.search.model.SearcherSection;
import com.d2c.content.search.query.SectionSearchBean;
import com.d2c.frame.provider.config.ElasticSearchConfig;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolFilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service(protocol = "dubbo")
public class SectionSearcherServiceImpl implements SectionSearcherService {

    private static final Log logger = LogFactory.getLog(SectionSearcherServiceImpl.class);
    @Autowired
    private ElasticSearchConfig elasticSearchConfig;

    @Override
    public int insert(SearcherSection searcherSection) {
        IndexRequestBuilder builder = elasticSearchConfig.getClient().prepareIndex(ElasticSearchConfig.INDEX_NAME,
                TYPE_SECTION, searcherSection.getId());
        try {
            XContentBuilder content = XContentFactory.jsonBuilder().startObject();
            ElasticSearchConfig.buildContent(content, searcherSection);
            content.endObject();
            builder.setSource(content);
            builder.execute().actionGet();
            logger.info("ID为" + searcherSection.getId() + "的模块建立索引成功! Name:" + searcherSection.getShortTitle());
            return 1;
        } catch (Exception e) {
            logger.error(e);
        }
        return 0;
    }

    @Override
    public PageResult<SearcherSection> search(SectionSearchBean searcher, PageModel page) {
        SearchRequestBuilder builder = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setTypes(TYPE_SECTION).setSearchType(SearchType.DEFAULT).setTimeout(TimeValue.timeValueSeconds(2));
        this.buildBoolFilterBuilder(builder, searcher);
        builder.addSort("sort", SortOrder.DESC);
        builder.addSort("sectionDefId", SortOrder.DESC);
        builder.addSort("sequence", SortOrder.DESC);
        builder.addSort("createDate", SortOrder.DESC);
        SearchResponse response = builder.setSearchType(SearchType.DEFAULT)
                .setFrom((page.getPageNumber() - 1) * page.getPageSize()).setSize(page.getPageSize()).setExplain(true)
                .execute().actionGet();
        List<SearcherSection> sections = new ArrayList<SearcherSection>();
        for (SearchHit hit : response.getHits()) {
            String id = String.valueOf(hit.getId());
            Map<String, Object> source = hit.getSource();
            if (!source.isEmpty()) {
                SearcherSection section = new SearcherSection();
                ElasticSearchConfig.transMap2Bean(source, section);
                section.setId(id);
                sections.add(section);
            }
        }
        PageResult<SearcherSection> pager = new PageResult<SearcherSection>(page);
        pager.setTotalCount(Long.valueOf(response.getHits().getTotalHits()).intValue());
        pager.setList(sections);
        return pager;
    }

    private void buildBoolFilterBuilder(SearchRequestBuilder builder, SectionSearchBean searcher) {
        BoolFilterBuilder boolFilter = FilterBuilders.boolFilter();
        if (searcher.getModuleId() != null) {
            boolFilter.must(FilterBuilders.termFilter("moduleId", searcher.getModuleId()));
            builder.setPostFilter(boolFilter);
        }
        if (searcher.getSectionDefId() != null) {
            boolFilter.must(FilterBuilders.termFilter("sectionDefId", searcher.getSectionDefId()));
            builder.setPostFilter(boolFilter);
        }
        if (searcher.getVersion() != null) {
            boolFilter.must(FilterBuilders.termFilter("version", searcher.getVersion()));
            builder.setPostFilter(boolFilter);
        }
        if (searcher.getMemberLevel() != null) {
            boolFilter.should(FilterBuilders.termFilter("memberLevel", "n"),
                    FilterBuilders.termFilter("memberLevel", searcher.getMemberLevel()));
        }
        boolFilter.must(FilterBuilders.termFilter("status", 1));
        builder.setPostFilter(boolFilter);
    }

    @Override
    public int deleteBySectionDefId(String sectionDefId) {
        if (sectionDefId == null)
            return 0;
        elasticSearchConfig.getClient().prepareDeleteByQuery(ElasticSearchConfig.INDEX_NAME).setTypes(TYPE_SECTION)
                .setQuery(QueryBuilders.boolQuery().must(QueryBuilders.termQuery("sectionDefId", sectionDefId)))
                .execute().actionGet();
        return 1;
    }

    @Override
    public int deleteVersion(String moduleId, Integer version) {
        if (version == null)
            return 0;
        elasticSearchConfig.getClient().prepareDeleteByQuery(ElasticSearchConfig.INDEX_NAME).setTypes(TYPE_SECTION)
                .setQuery(QueryBuilders.boolQuery().must(QueryBuilders.termQuery("moduleId", moduleId))
                        .must(QueryBuilders.termQuery("version", version)))
                .execute().actionGet();
        return 1;
    }

    @Override
    public int rebuild(SearcherSection searcherSection) {
        this.remove(searcherSection.getId());
        IndexRequestBuilder builder = elasticSearchConfig.getClient().prepareIndex(ElasticSearchConfig.INDEX_NAME,
                TYPE_SECTION, searcherSection.getId());
        try {
            XContentBuilder content = XContentFactory.jsonBuilder().startObject()
                    .field("moduleId", searcherSection.getModuleId())
                    .field("sectionDefId", searcherSection.getSectionDefId())
                    .field("createDate", ElasticSearchConfig.transDate2Long(searcherSection.getCreateDate()))
                    .field("shortTitle", searcherSection.getShortTitle())
                    .field("longTitle", searcherSection.getLongTitle()).field("price", searcherSection.getPrice())
                    .field("originalPrice", searcherSection.getOriginalPrice())
                    .field("brand", searcherSection.getBrand()).field("url", searcherSection.getUrl())
                    .field("frontPic", searcherSection.getFrontPic()).field("endPic", searcherSection.getEndPic())
                    .field("videoPic", searcherSection.getVideoPic()).field("videoPath", searcherSection.getVideoPath())
                    .field("sequence", searcherSection.getSequence()).field("type", searcherSection.getType())
                    .field("properties", searcherSection.getProperties()).field("title", searcherSection.getTitle())
                    .field("visible", searcherSection.getVisible()).field("types", searcherSection.getTypes())
                    .field("sort", searcherSection.getSort()).field("prop", searcherSection.getProp())
                    .field("sectionValues", searcherSection.getSectionValues())
                    .field("fixed", searcherSection.getFixed()).field("status", searcherSection.getStatus())
                    .field("version", searcherSection.getVersion())
                    .field("watched", searcherSection.getWatched() != null ? searcherSection.getWatched() : 0)
                    .field("more", searcherSection.getMore() != null ? searcherSection.getMore() : 0)
                    .field("moreUrl", searcherSection.getMoreUrl())
                    .field("relationType", searcherSection.getRelationType())
                    .field("relationId", searcherSection.getRelationId())
                    .field("memberLevel", searcherSection.getMemberLevel()).endObject();
            builder.setSource(content);
            builder.execute().actionGet();
            logger.info("ID为" + searcherSection.getId() + "的模块建立索引成功! Name:" + searcherSection.getShortTitle());
            return 1;
        } catch (Exception e) {
            logger.error(e);
        }
        return 0;
    }

    @Override
    public int remove(String id) {
        elasticSearchConfig.getClient().prepareDelete(ElasticSearchConfig.INDEX_NAME, TYPE_SECTION, id.toString())
                .execute().actionGet();
        logger.info("ID为" + id + "的模块已删除索引!");
        return 1;
    }

    @Override
    public void removeAll() {
        elasticSearchConfig.getClient().prepareDeleteByQuery(ElasticSearchConfig.INDEX_NAME).setTypes(TYPE_SECTION)
                .setQuery(QueryBuilders.matchAllQuery()).execute().actionGet();
    }

}
