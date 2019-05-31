package com.d2c.common.search.base;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.elasticsearch.search.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.facet.FacetRequest;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.util.Assert;

import java.util.List;

import static org.springframework.util.CollectionUtils.isEmpty;

public abstract class BaseSearchTemplate extends ElasticsearchTemplate {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    private Client client;

    public BaseSearchTemplate(Client client) {
        super(client);
        this.client = client;
    }

    private static String[] toArray(List<String> values) {
        String[] valuesAsArray = new String[values.size()];
        return values.toArray(valuesAsArray);
    }

    @Override
    public <T> AggregatedPage<T> queryForPage(SearchQuery query, Class<T> clazz) {
        return queryForPage(query, clazz, getResultsMapper());
    }

    @Override
    public <T> AggregatedPage<T> queryForPage(SearchQuery query, Class<T> clazz, SearchResultMapper mapper) {
        SearchResponse response = doSearch(prepareSearch(query, clazz), query);
        return mapper.mapResults(response, clazz, query.getPageable());
    }

    private SearchResponse doSearch(SearchRequestBuilder searchRequest, SearchQuery searchQuery) {
        if (searchQuery.getFilter() != null) {
            searchRequest.setPostFilter(searchQuery.getFilter());
        }
        if (!isEmpty(searchQuery.getElasticsearchSorts())) {
            for (SortBuilder sort : searchQuery.getElasticsearchSorts()) {
                searchRequest.addSort(sort);
            }
        }
        if (!searchQuery.getScriptFields().isEmpty()) {
            searchRequest.addField("_source");
            for (ScriptField scriptedField : searchQuery.getScriptFields()) {
                searchRequest.addScriptField(scriptedField.fieldName(), scriptedField.script());
            }
        }
        if (searchQuery.getHighlightFields() != null) {
            for (HighlightBuilder.Field highlightField : searchQuery.getHighlightFields()) {
                searchRequest.addHighlightedField(highlightField);
            }
        }
        if (!isEmpty(searchQuery.getIndicesBoost())) {
            for (IndexBoost indexBoost : searchQuery.getIndicesBoost()) {
                searchRequest.addIndexBoost(indexBoost.getIndexName(), indexBoost.getBoost());
            }
        }
        if (!isEmpty(searchQuery.getAggregations())) {
            for (AbstractAggregationBuilder aggregationBuilder : searchQuery.getAggregations()) {
                searchRequest.addAggregation(aggregationBuilder);
            }
        }
        if (!isEmpty(searchQuery.getFacets())) {
            for (FacetRequest aggregatedFacet : searchQuery.getFacets()) {
                searchRequest.addAggregation(aggregatedFacet.getFacet());
            }
        }
        searchRequest.setQuery(searchQuery.getQuery());
        logger.info("ES查询语句:\n" + searchRequest.toString());
        return searchRequest.execute().actionGet();
    }

    private <T> SearchRequestBuilder prepareSearch(Query query, Class<T> clazz) {
        setPersistentEntityIndexAndType(query, clazz);
        return prepareSearch(query);
    }

    private SearchRequestBuilder prepareSearch(Query query) {
        Assert.notNull(query.getIndices(), "No index defined for Query");
        Assert.notNull(query.getTypes(), "No type defined for Query");
        int startRecord = 0;
        SearchRequestBuilder searchRequestBuilder = client.prepareSearch(toArray(query.getIndices()))
                .setSearchType(query.getSearchType()).setTypes(toArray(query.getTypes()));
        if (query.getSourceFilter() != null) {
            SourceFilter sourceFilter = query.getSourceFilter();
            searchRequestBuilder.setFetchSource(sourceFilter.getIncludes(), sourceFilter.getExcludes());
        }
        if (query.getPageable() != null) {
            startRecord = query.getPageable().getPageNumber() * query.getPageable().getPageSize();
            searchRequestBuilder.setSize(query.getPageable().getPageSize());
        }
        searchRequestBuilder.setFrom(startRecord);
        if (!query.getFields().isEmpty()) {
            searchRequestBuilder.addFields(toArray(query.getFields()));
        }
        if (query.getSort() != null) {
            for (Sort.Order order : query.getSort()) {
                searchRequestBuilder.addSort(order.getProperty(),
                        order.getDirection() == Sort.Direction.DESC ? SortOrder.DESC : SortOrder.ASC);
            }
        }
        if (query.getMinScore() > 0) {
            searchRequestBuilder.setMinScore(query.getMinScore());
        }
        return searchRequestBuilder;
    }

    private void setPersistentEntityIndexAndType(Query query, Class<?> clazz) {
        if (query.getIndices().isEmpty()) {
            query.addIndices(retrieveIndexNameFromPersistentEntity(clazz));
        }
        if (query.getTypes().isEmpty()) {
            query.addTypes(retrieveTypeFromPersistentEntity(clazz));
        }
    }

    private String[] retrieveIndexNameFromPersistentEntity(Class<?> clazz) {
        if (clazz != null) {
            return new String[]{getPersistentEntityFor(clazz).getIndexName()};
        }
        return null;
    }

    private String[] retrieveTypeFromPersistentEntity(Class<?> clazz) {
        if (clazz != null) {
            return new String[]{getPersistentEntityFor(clazz).getIndexType()};
        }
        return null;
    }

}
