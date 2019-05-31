package com.d2c.product.search.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d2c.cache.redis.annotation.CacheMethod;
import com.d2c.common.api.dto.CountDTO;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.page.base.PageOrder;
import com.d2c.common.api.page.base.PageSort;
import com.d2c.common.base.utils.ListUt;
import com.d2c.common.base.utils.StringUt;
import com.d2c.frame.provider.config.ElasticSearchConfig;
import com.d2c.product.model.Product.ProductTradeType;
import com.d2c.product.search.enums.ProductOrderTypeEnum;
import com.d2c.product.search.model.*;
import com.d2c.product.search.query.CategorySearchBean;
import com.d2c.product.search.query.ProductProSearchQuery;
import com.d2c.product.search.support.ProductSearchHelp;
import com.d2c.product.search.support.TopCategorySortHelp;
import com.d2c.util.date.DateUtil;
import com.d2c.util.string.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;
import org.elasticsearch.search.aggregations.metrics.max.MaxBuilder;
import org.elasticsearch.search.aggregations.metrics.tophits.TopHits;
import org.elasticsearch.search.aggregations.metrics.tophits.TopHitsBuilder;
import org.elasticsearch.search.facet.FacetBuilders;
import org.elasticsearch.search.facet.Facets;
import org.elasticsearch.search.facet.terms.TermsFacet;
import org.elasticsearch.search.facet.terms.TermsFacetBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@Service(protocol = "dubbo")
public class ProductSearcherQueryServiceImpl implements ProductSearcherQueryService {

    @Autowired
    private ElasticSearchConfig elasticSearchConfig;
    @Reference
    private ProductCategorySearcherService productCategorySearcherService;
    @Reference
    private TopCategorySearcherService topCategorySearcherService;
    @Reference
    private DesignerSearcherService designerSearcherService;
    @Reference
    private SeriesSearcherService seriesSearcherService;

    private MultiMatchQueryBuilder getProductQueryBuilder(ProductProSearchQuery searcher) {
        MultiMatchQueryBuilder qb = null;
        if (!StringUtils.isEmpty(searcher.getKeyword())) {
            qb = QueryBuilders.multiMatchQuery(searcher.getKeyword(), "name^5", "designer^3", "brand^3", "seo^3",
                    "categoryName^3", "inernalSn^3", "subTitle^1", "externalSn^1", "topCategory^1", "productCategory^1")
                    .analyzer("ik").minimumShouldMatch("1").boost(1);
            if (!ListUt.isEmpty(searcher.getSortFields())) {
                // 非相关性搜索排序，排除相关性较差的数据
                qb.minimumShouldMatch("2");
            }
        }
        return qb;
    }

    private void buildBoolFilterBuilder(SearchRequestBuilder builder, ProductProSearchQuery searcher) {
        BoolFilterBuilder boolFilter = FilterBuilders.boolFilter();
        if (searcher.getTopId() != null) {
            boolFilter.must(FilterBuilders.termFilter("topCategory", searcher.getTopId()));
        }
        if (searcher.getCategoryId() != null) {
            boolFilter.must(FilterBuilders.termFilter("productCategory", searcher.getCategoryId()));
        }
        if (searcher.getDesignerId() != null) {
            boolFilter.must(FilterBuilders.termFilter("designerId", searcher.getDesignerId()));
        }
        if (searcher.getSeriesId() != null) {
            boolFilter.must(FilterBuilders.termFilter("seriesId", searcher.getSeriesId()));
        }
        if (searcher.getPromotionId() != null) {
            boolFilter.must(FilterBuilders.termFilter("promotionId", searcher.getPromotionId()));
        }
        if (searcher.getOrderPromotionId() != null) {
            boolFilter.must(FilterBuilders.termFilter("orderPromotionId", searcher.getOrderPromotionId()));
        }
        if (searcher.getFlashPromotionId() != null) {
            boolFilter.must(FilterBuilders.termFilter("flashPromotionId", searcher.getFlashPromotionId()));
        }
        if (searcher.getLiveId() != null) {
            boolFilter.must(FilterBuilders.termFilter("liveId", searcher.getLiveId()));
        }
        if (searcher.getTagId() != null) {
            boolFilter.must(FilterBuilders.termFilter("tags", searcher.getTagId()));
        }
        if (searcher.getProductIds() != null && searcher.getProductIds().size() > 0) {
            boolFilter.must(FilterBuilders.termsFilter("productId", searcher.getProductIds()));
        }
        if (searcher.getDesignerIds() != null && searcher.getDesignerIds().size() > 0) {
            boolFilter.must(FilterBuilders.termsFilter("designerId", searcher.getDesignerIds()));
        }
        if (searcher.getSeriesIds() != null && searcher.getSeriesIds().size() > 0) {
            boolFilter.must(FilterBuilders.termFilter("seriesId", searcher.getSeriesIds()));
        }
        if (searcher.getCategoryIds() != null && searcher.getCategoryIds().size() > 0) {
            boolFilter.must(FilterBuilders.termsFilter("productCategoryId", searcher.getCategoryIds()));
        }
        if (searcher.getStatus() != null) {
            boolFilter.must(FilterBuilders.termFilter("status", searcher.getStatus()));
        }
        if (searcher.getMinPrice() != null) {
            boolFilter.must(FilterBuilders.rangeFilter("minPrice").gte(searcher.getMinPrice()));
        }
        if (searcher.getMaxPrice() != null) {
            boolFilter.must(FilterBuilders.rangeFilter("minPrice").lte(searcher.getMaxPrice()));
        }
        if (searcher.getHasPromotion() != null) {
            boolFilter.must(FilterBuilders.rangeFilter("promotionId").gt(0));
        }
        if (searcher.getMonth() != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.MONTH, searcher.getMonth() * (-1));
            boolFilter.must(FilterBuilders.rangeFilter("upMarketDate").from(calendar.getTime()).to(new Date()));
        }
        if (searcher.getBeginUpDate() != null) {
            boolFilter.must(FilterBuilders.rangeFilter("upMarketDate").gte(searcher.getBeginUpDate().getTime()));
        }
        if (searcher.getEndUpDate() != null) {
            boolFilter.must(FilterBuilders.rangeFilter("upMarketDate").lte(searcher.getEndUpDate().getTime()));
        }
        if (searcher.getAfterModifyDate() != null) {
            boolFilter.must(FilterBuilders.rangeFilter("modifyDate").from(searcher.getAfterModifyDate().getTime()));
        }
        if (searcher.getBeginCreateDate() != null) {
            boolFilter.must(FilterBuilders.rangeFilter("createDate").from(searcher.getBeginCreateDate().getTime()));
        }
        if (searcher.getRecommend() != null) {
            boolFilter.must(FilterBuilders.termFilter("recommend", searcher.getRecommend()));
        }
        if (searcher.getStore() != null) {
            boolFilter.must(FilterBuilders.termFilter("store", searcher.getStore()));
        }
        if (searcher.getMinRecom() != null) {
            boolFilter.must(FilterBuilders.rangeFilter("recom").gte(searcher.getMinRecom()));
        }
        if (searcher.getMaxRecom() != null) {
            boolFilter.must(FilterBuilders.rangeFilter("recom").lte(searcher.getMaxRecom()));
        }
        if (searcher.getOperRecom() != null) {
            boolFilter.must(FilterBuilders.termFilter("operRecom", searcher.getOperRecom()));
        }
        if (searcher.getTopical() != null) {
            boolFilter.must(FilterBuilders.termFilter("topical", searcher.getTopical()));
        }
        if (searcher.getSubscribe() != null && searcher.getSubscribe() == 1) {
            boolFilter.must(FilterBuilders.termFilter("subscribe", searcher.getSubscribe()));
        }
        if (searcher.getAfter() != null && searcher.getAfter() == true) {
            boolFilter.must(FilterBuilders.termFilter("after", 1));
        }
        if (searcher.getProductSellType() != null && searcher.getProductSellType().size() > 0) {
            boolFilter.must(FilterBuilders.termsFilter("productSellType", searcher.getProductSellType()));
        }
        if (searcher.getInernalSn() != null) {
            boolFilter.must(FilterBuilders.termsFilter("inernalSn", searcher.getInernalSn()));
        }
        if (searcher.getPromotionDate() != null) {
            boolFilter.must(FilterBuilders.rangeFilter("startDate").lte(searcher.getPromotionDate().getTime()));
            boolFilter.must(FilterBuilders.rangeFilter("endDate").gte(searcher.getPromotionDate().getTime()));
            boolFilter.must(FilterBuilders.termFilter("promotionMark", 1));
        }
        if (searcher.getTopIds() != null) {
            boolFilter.must(FilterBuilders.termsFilter("topCategory", searcher.getTopIds()));
        }
        if (searcher.getWorldTrade() != null) {
            if (searcher.getWorldTrade()) {
                boolFilter.must(FilterBuilders.termsFilter("productTradeType",
                        StringUt.toLowerCase(ProductTradeType.CROSS.name())));
            }
        }
        boolFilter.must(FilterBuilders.termFilter("search", searcher.getSearchInt()));
        boolFilter.must(FilterBuilders.termFilter("mark", searcher.getMarkInt()));
        /*
         * if (searcher.getIgnoreIds() != null) { for (Long id :
         * searcher.getIgnoreIds()) {
         * boolFilter.mustNot(FilterBuilders.termFilter("id", id));
         * builder.setPostFilter(boolFilter); } }
         */
        builder.setPostFilter(boolFilter);
    }

    @Override
    public PageResult<SearcherProduct> search(ProductProSearchQuery searcher, PageModel page) {
        if (searcher == null)
            searcher = new ProductProSearchQuery();
        if (page == null)
            page = new PageModel();
        if (searcher.getSortFields() == null) {
            makeSearchSort(searcher);
        }
        SearchRequestBuilder builder = makeSearchBuilder();
        MultiMatchQueryBuilder qb = this.getProductQueryBuilder(searcher);
        if (qb != null) {
            builder.setQuery(qb);
        } else {
            builder.setQuery(QueryBuilders.matchAllQuery());
        }
        buildBoolFilterBuilder(builder, searcher);
        for (int i = 0; searcher.getSortFields() != null && i < searcher.getSortFields().length; i++) {
            builder.addSort(searcher.getSortFields()[i], searcher.getOrders()[i]);
        }
        SearchType searchType = SearchType.DEFAULT;
        if (StringUtil.isNotBlank(searcher.getKeyword())) {
            searchType = SearchType.DFS_QUERY_THEN_FETCH;
        }
        SearchResponse response = builder.setSearchType(searchType)
                .setFrom((page.getPageNumber() - 1) * page.getPageSize()).setSize(page.getPageSize()).setExplain(true)
                .execute().actionGet();
        List<SearcherProduct> products = buildResList(response);
        PageResult<SearcherProduct> pager = new PageResult<>(page);
        pager.setTotalCount(Long.valueOf(response.getHits().getTotalHits()).intValue());
        pager.setList(products);
        return pager;
    }

    @Override
    public int count(ProductProSearchQuery searcher) {
        if (searcher == null)
            searcher = new ProductProSearchQuery();
        SearchRequestBuilder builder = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setTypes(ProductSearcherService.TYPE_PRODUCT).setSearchType(SearchType.COUNT);
        QueryBuilder qb = this.getProductQueryBuilder(searcher);
        if (qb != null) {
            builder.setQuery(qb);
        } else {
            builder.setQuery(QueryBuilders.matchAllQuery());
        }
        buildBoolFilterBuilder(builder, searcher);
        SearchResponse response = builder.execute().actionGet();
        return (int) response.getHits().getTotalHits();
    }

    @Override
    public PageResult<SearcherRecProduct> searchRec(ProductProSearchQuery searcher, PageModel page) {
        SearchRequestBuilder builder = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setTypes(ProductSearcherService.TYPE_REC_PRODUCT).setSearchType(SearchType.DEFAULT)
                .setTimeout(TimeValue.timeValueSeconds(2));
        QueryBuilder qb = this.getProductQueryBuilder(searcher);
        if (qb != null) {
            builder.setQuery(qb);
        } else {
            builder.setQuery(QueryBuilders.matchAllQuery());
        }
        buildBoolFilterBuilder(builder, searcher);
        for (int i = 0; searcher.getSortFields() != null && i < searcher.getSortFields().length; i++) {
            builder.addSort(searcher.getSortFields()[i], searcher.getOrders()[i]);
        }
        SearchResponse response = builder.setSearchType(SearchType.DEFAULT)
                .setFrom((page.getPageNumber() - 1) * page.getPageSize()).setSize(page.getPageSize()).setExplain(true)
                .execute().actionGet();
        List<SearcherRecProduct> products = new ArrayList<>();
        for (SearchHit hit : response.getHits()) {
            // Long productId = Long.valueOf(hit.getId());
            Map<String, Object> source = hit.getSource();
            if (!source.isEmpty()) {
                SearcherRecProduct product = new SearcherRecProduct();
                ElasticSearchConfig.transMap2Bean(source, product);
                product.setId(Long.parseLong(source.get("productId").toString()));
                products.add(product);
            }
        }
        PageResult<SearcherRecProduct> pager = new PageResult<>(page);
        pager.setTotalCount(Long.valueOf(response.getHits().getTotalHits()).intValue());
        pager.setList(products);
        return pager;
    }

    @Override
    public SearcherProduct findById(String id) {
        GetResponse response = elasticSearchConfig.getClient()
                .prepareGet(ElasticSearchConfig.INDEX_NAME, ProductSearcherService.TYPE_PRODUCT, id).execute()
                .actionGet();
        SearcherProduct product = null;
        if (response != null && response.isExists()) {
            Long productId = Long.valueOf(response.getId());
            Map<String, Object> source = response.getSource();
            if (source != null && !source.isEmpty()) {
                product = new SearcherProduct();
                try {
                    ElasticSearchConfig.transMap2Bean(source, product);
                    product.setId(productId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return product;
    }

    @Override
    public List<SearcherProduct> findByIds(List<String> productIds, Integer mark) {
        List<SearcherProduct> products = new ArrayList<>();
        SearchRequestBuilder builder = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setTypes(ProductSearcherService.TYPE_PRODUCT).setSearchType(SearchType.DEFAULT);
        BoolFilterBuilder boolFilter = FilterBuilders.boolFilter();
        boolFilter.must(FilterBuilders.termsFilter("productId", productIds));
        if (mark != null) {
            boolFilter.must(FilterBuilders.termFilter("mark", mark));
        }
        builder.setPostFilter(boolFilter);
        SearchResponse response = builder.setFrom(0).setSize(productIds.size()).setExplain(true).execute().actionGet();
        for (SearchHit hit : response.getHits()) {
            Long productId = Long.valueOf(hit.getId());
            Map<String, Object> source = hit.getSource();
            if (!source.isEmpty()) {
                SearcherProduct product = new SearcherProduct();
                ElasticSearchConfig.transMap2Bean(source, product);
                product.setId(productId);
                products.add(product);
            }
        }
        return products;
    }

    @Override
    public Map<Long, SearcherProduct> findMapByIds(String[] ids) {
        SearchResponse response = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setQuery(QueryBuilders.idsQuery(ProductSearcherService.TYPE_PRODUCT).addIds(ids)).setSize(ids.length)
                .execute().actionGet();
        Map<Long, SearcherProduct> products = new HashMap<>();
        for (SearchHit hit : response.getHits()) {
            Long productId = Long.valueOf(hit.getId());
            Map<String, Object> source = hit.getSource();
            if (!source.isEmpty()) {
                SearcherProduct product = new SearcherProduct();
                ElasticSearchConfig.transMap2Bean(source, product);
                product.setId(productId);
                products.put(product.getProductId(), product);
            }
        }
        return products;
    }

    @Override
    public SearcherRecProduct findRecById(Long liveId, Long productId) {
        GetResponse response = elasticSearchConfig.getClient().prepareGet(ElasticSearchConfig.INDEX_NAME,
                ProductSearcherService.TYPE_REC_PRODUCT, productId + "_" + liveId).execute().actionGet();
        SearcherRecProduct product = null;
        if (response != null && response.isExists()) {
            Map<String, Object> source = response.getSource();
            if (source != null && !source.isEmpty()) {
                product = new SearcherRecProduct();
                try {
                    ElasticSearchConfig.transMap2Bean(source, product);
                    product.setId(productId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return product;
    }

    @Override
    public List<SearcherProduct> findByPage(int page, int limit) {
        SearchRequestBuilder builder = makeSearchBuilder();
        BoolFilterBuilder boolFilter = FilterBuilders.boolFilter();
        boolFilter.must(FilterBuilders.termFilter("search", 1));
        boolFilter.must(FilterBuilders.termFilter("mark", 1));
        builder.setPostFilter(boolFilter);
        builder.addSort("productId", SortOrder.ASC);
        SearchResponse response = searchPage(page, limit, builder);
        return buildResList(response);
    }

    @CacheMethod
    @Override
    public List<SearcherProduct> findTopRecom(int limit) {
        ProductProSearchQuery query = new ProductProSearchQuery();
        query.setStore(1);
        return search(null, new PageModel(1, limit)).getList();
    }

    @CacheMethod
    @Override
    public List<SearcherProduct> findSimilarTargets(Long topId, int page, int limit) {
        ProductProSearchQuery searcherBean = new ProductProSearchQuery();
        searcherBean.setTopId(topId);
        searcherBean.setStore(1);
        PageResult<SearcherProduct> pager = search(searcherBean, new PageModel(page, limit));
        return pager.getList();
    }

    @Override
    public List<Long> findFactSeries(Long designerId) {
        TermsFacetBuilder termBuilder = FacetBuilders.termsFacet("seriesId").field("seriesId")
                .facetFilter(FilterBuilders.matchAllFilter()).size(Integer.MAX_VALUE);
        BoolFilterBuilder bfBuilder = FilterBuilders.boolFilter();
        bfBuilder.must(FilterBuilders.termFilter("designerId", designerId));
        termBuilder.facetFilter(bfBuilder);
        bfBuilder.must(FilterBuilders.termFilter("mark", 1));
        termBuilder.facetFilter(bfBuilder);
        SearchRequestBuilder builder = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setTypes(ProductSearcherService.TYPE_PRODUCT).setSearchType(SearchType.COUNT);
        builder.addFacet(termBuilder);
        SearchResponse response = builder.execute().actionGet();
        Facets f = response.getFacets();
        TermsFacet facet = (TermsFacet) f.getFacets().get("seriesId");
        List<Long> seriesIds = new ArrayList<>();
        for (TermsFacet.Entry tf : facet.getEntries()) {
            Long id = Long.parseLong(tf.getTerm().toString());
            seriesIds.add(id);
        }
        return seriesIds;
    }

    @Override
    public Map<String, List<CountDTO<?>>> findFactDesignerAndCategory(ProductProSearchQuery searcher, Long parentId,
                                                                      Long topCategoryId) {
        TermsFacetBuilder designerTermBuilder = FacetBuilders.termsFacet("designerId").field("designerId")
                .size(Integer.MAX_VALUE);
        BoolFilterBuilder designerBq = FilterBuilders.boolFilter();
        BoolFilterBuilder categoryBq = FilterBuilders.boolFilter();
        if (searcher.getDesignerId() != null && searcher.getDesignerId() > 0) {
            designerBq.must(FilterBuilders.termFilter("designerId", searcher.getDesignerId()));
            designerTermBuilder.facetFilter(designerBq);
        }
        if (searcher.getSeriesId() != null && searcher.getSeriesId() > 0) {
            designerBq.must(FilterBuilders.termFilter("seriesId", searcher.getSeriesId()));
            designerTermBuilder.facetFilter(designerBq);
        }
        if (searcher.getCategoryId() != null && searcher.getCategoryId() > 0) {
            categoryBq.must(FilterBuilders.termFilter("productCategory", searcher.getCategoryId()));
            designerTermBuilder.facetFilter(categoryBq);
        }
        if (searcher.getTopId() != null && searcher.getTopId() > 0) {
            categoryBq.must(FilterBuilders.termFilter("topCategory", searcher.getTopId()));
            designerTermBuilder.facetFilter(categoryBq);
        }
        if (!StringUtils.isEmpty(searcher.getKeyword())) {
            categoryBq.must(FilterBuilders.queryFilter(getProductQueryBuilder(searcher)));
            designerTermBuilder.facetFilter(categoryBq);
        }
        SearchRequestBuilder builder = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setTypes(ProductSearcherService.TYPE_PRODUCT).setSearchType(SearchType.COUNT);
        builder.setQuery(QueryBuilders.matchAllQuery());
        builder.addFacet(designerTermBuilder);
        SearchResponse response = builder.execute().actionGet();
        Facets f = response.getFacets();
        Map<String, List<CountDTO<?>>> map = new HashMap<>();
        // 通过过滤出的设计师
        TermsFacet facet = (TermsFacet) f.getFacets().get("designerId");
        List<CountDTO<?>> des = new ArrayList<>();
        map.put("designer", des);
        String[] designerIds = new String[facet.getEntries().size()];
        for (int i = 0; i < facet.getEntries().size(); i++) {
            designerIds[i] = facet.getEntries().get(i).getTerm().toString();
        }
        Map<Long, SearcherDesigner> designers = designerSearcherService.findMapByIds(designerIds, null);
        for (TermsFacet.Entry tf : facet.getEntries()) {
            Long designerId = Long.valueOf(tf.getTerm().toString());
            SearcherDesigner designer = designers.get(designerId);
            if (designer != null) {
                des.add(new CountDTO<>(designer, tf.getCount()));
            }
        }
        PageModel page = new PageModel(1, 500);
        // 一级分类
        CategorySearchBean tpSearcher = new CategorySearchBean();
        tpSearcher.setParentId(parentId);
        List<SearcherTopCategory> tops = topCategorySearcherService.search(tpSearcher, page).getList();
        List<CountDTO<?>> tpcs = getTopCategorys(tops);
        map.put("topCategory", tpcs);
        // 二级分类
        CategorySearchBean pcSearcher = new CategorySearchBean();
        pcSearcher.setTopId(topCategoryId);
        List<SearcherProductCategory> cates = productCategorySearcherService.search(pcSearcher, page).getList();
        List<SearcherProductCategory> newList = processSequence(cates, null, null);
        List<CountDTO<?>> pcs = getProductCategorys(newList);
        map.put("category", pcs);
        return map;
    }

    private List<SearcherProductCategory> processSequence(List<SearcherProductCategory> all, Long p,
                                                          List<SearcherProductCategory> temp) {
        if (temp == null) {
            temp = new ArrayList<>();
        }
        for (SearcherProductCategory pc : all) {
            if (((p == null && pc.getParentId() == null))
                    || (pc != null && pc.getParentId() != null && pc.getParentId().equals(p))) {
                temp.add(pc);
                processSequence(all, pc.getId(), temp);
            }
        }
        return temp;
    }

    private List<CountDTO<?>> getTopCategorys(List<SearcherTopCategory> tops) {
        List<CountDTO<?>> pcs = new ArrayList<>();
        for (int i = 0; i < tops.size(); i++) {
            SearcherTopCategory top = tops.get(i);
            CountDTO<?> vc = new CountDTO<>(top, 0);
            pcs.add(vc);
        }
        return pcs;
    }

    private List<CountDTO<?>> getProductCategorys(List<SearcherProductCategory> cates) {
        List<CountDTO<?>> pcs = new ArrayList<>();
        for (int i = 0; i < cates.size(); i++) {
            SearcherProductCategory cate = cates.get(i);
            CountDTO<?> vc = new CountDTO<>(cate, 0);
            pcs.add(vc);
        }
        return pcs;
    }

    @Override
    public PageResult<SearcherProduct> findSaleProductByDesigner(Long designerId, PageModel page,
                                                                 ProductProSearchQuery searcherBean) {
        searcherBean.setDesignerId(designerId);
        if (StringUtil.isBlank(searcherBean.getOrderByStr()) || searcherBean.getOrderByStr().equalsIgnoreCase("bd")) {
            searcherBean.setOrderByStr(null);
            searcherBean.setSortFields(new String[]{"store", "topical", "seriesUpDate", "recomScore"});
            searcherBean.setOrders(new SortOrder[]{SortOrder.DESC, SortOrder.DESC, SortOrder.DESC, SortOrder.DESC});
        }
        PageResult<SearcherProduct> pager = search(searcherBean, page);
        return pager;
    }

    @Override
    public int countSaleProductByDesigner(Long designerId) {
        ProductProSearchQuery searcherBean = new ProductProSearchQuery();
        searcherBean.setDesignerId(designerId);
        int totalCount = count(searcherBean);
        return totalCount;
    }

    @Override
    public List<SearcherProduct> findProductByTopCategory(Long topId, Date afterModifyDate, int page, int limit) {
        SearchRequestBuilder builder = makeSearchBuilder();
        BoolFilterBuilder boolFilter = FilterBuilders.boolFilter();
        if (topId != null) {
            boolFilter.must(FilterBuilders.termFilter("topCategory", topId));
        }
        if (afterModifyDate != null) {
            boolFilter.must(FilterBuilders.rangeFilter("modifyDate").from(afterModifyDate.getTime()));
        }
        builder.setPostFilter(boolFilter);
        builder.addSort("productId", SortOrder.ASC);
        return buildResList(searchPage(page, limit, builder));
    }

    @Override
    public Map<String, List<ProductSearchHelp>> findProductSearchHelp(ProductProSearchQuery searcher, String[] params) {
        Map<String, List<ProductSearchHelp>> map = new HashMap<>();
        List<String> topCategoryIds = new ArrayList<>();
        List<String> productCategoryIds = new ArrayList<>();
        List<String> designerIds = new ArrayList<>();
        for (String param : params) {
            TermsFacetBuilder termBuilder = FacetBuilders.termsFacet(param).field(param)
                    .facetFilter(FilterBuilders.matchAllFilter()).size(Integer.MAX_VALUE);
            BoolFilterBuilder bfBuilder = FilterBuilders.boolFilter();
            bfBuilder.must(FilterBuilders.termFilter("mark", 1));
            bfBuilder.must(FilterBuilders.termFilter("search", 1));
            termBuilder.facetFilter(bfBuilder);
            if (!StringUtils.isEmpty(searcher.getKeyword())) {
                bfBuilder.must(FilterBuilders.queryFilter(getProductQueryBuilder(searcher)));
                termBuilder.facetFilter(bfBuilder);
            }
            // 聚合出想要的结果
            SearchRequestBuilder builder = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                    .setTypes(ProductSearcherService.TYPE_PRODUCT).setSearchType(SearchType.COUNT);
            builder.addFacet(termBuilder);
            SearchResponse response = builder.execute().actionGet();
            Facets f = response.getFacets();
            TermsFacet facet = (TermsFacet) f.getFacets().get(param);
            for (TermsFacet.Entry tf : facet.getEntries()) {
                String id = tf.getTerm().toString();
                // 处理顶级分类
                if (param.equals("topCategoryId")) {
                    topCategoryIds.add(id);
                }
                // 处理二级分类
                if (param.equals("productCategoryId")) {
                    productCategoryIds.add(id);
                }
                // 处理设计师
                if (param.equals("designerId")) {
                    designerIds.add(id);
                }
            }
        }
        List<ProductSearchHelp> designerList = designerSearcherService
                .findHelpByIds(designerIds.toArray(new String[designerIds.size()]));
        map.put(DesignerSearcherService.TYPE_DESIGNER, designerList);
        List<ProductSearchHelp> topCategoryList = topCategorySearcherService
                .findHelpByIds(topCategoryIds.toArray(new String[topCategoryIds.size()]));
        map.put(TopCategorySearcherService.TYPE_TOPCATEGORY, topCategoryList);
        List<ProductSearchHelp> productCategoryList = productCategorySearcherService
                .findHelpByIds(productCategoryIds.toArray(new String[productCategoryIds.size()]));
        map.put(ProductCategorySearcherService.TYPE_PRODUCTCATEGORY, productCategoryList);
        return map;
    }

    @Override
    public JSONObject filterService(ProductProSearchQuery searcher) {
        JSONObject obj = new JSONObject();
        SearchRequestBuilder builder = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setTypes(ProductSearcherService.TYPE_PRODUCT);
        QueryBuilder qb = this.getProductQueryBuilder(searcher);
        if (qb != null) {
            builder.setQuery(qb);
        } else {
            builder.setQuery(QueryBuilders.matchAllQuery());
        }
        buildBoolFilterBuilder(builder, searcher);
        List<String> productType = this.getProductType(searcher, builder);
        String[] types = new String[]{"spot", "presell", "custom"};
        for (String type : types) {
            if (productType.contains(type)) {
                obj.put(type, true);
            } else {
                obj.put(type, false);
            }
        }
        // 是否有售后
        obj.put("isAfter", hasAfter(searcher, builder));
        obj.put("isSubscribe", hasSubscribe(searcher, builder));
        obj.put("isPromotion", hasPromotion(searcher, builder));
        return obj;
    }

    @Override
    public JSONObject filterProperties(ProductProSearchQuery searcher) {
        JSONObject obj = new JSONObject();
        JSONObject service = this.filterService(searcher);
        obj.put("service", service);
        SearchRequestBuilder builder = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setTypes(ProductSearcherService.TYPE_PRODUCT);
        QueryBuilder qb = this.getProductQueryBuilder(searcher);
        if (qb != null) {
            builder.setQuery(qb);
        } else {
            builder.setQuery(QueryBuilders.matchAllQuery());
        }
        buildQueryBuilder(builder, searcher);
        List<String> topCategoryIds = new ArrayList<>();
        List<String> productCategoryIds = new ArrayList<>();
        List<String> designerIds = new ArrayList<>();
        String[] params = new String[]{"topCategoryId", "productCategoryId", "designerId"};
        for (String param : params) {
            TermsBuilder teamAgg = AggregationBuilders.terms(param).field(param)
                    .size(param.equals("productCategoryId") ? 100 : 10);
            SearchResponse response = builder.addAggregation(teamAgg).execute().actionGet();
            Map<String, Aggregation> asMap = response.getAggregations().getAsMap();
            LongTerms tersAgg = (LongTerms) asMap.get(param);
            Iterator<Bucket> teamBucketIt = tersAgg.getBuckets().iterator();
            while (teamBucketIt.hasNext()) {
                Bucket bucket = teamBucketIt.next();
                if (param.equals("topCategoryId")) {
                    topCategoryIds.add(bucket.getKey());
                }
                // 处理二级分类
                if (param.equals("productCategoryId")) {
                    productCategoryIds.add(bucket.getKey());
                }
                // 处理二级分类
                if (param.equals("designerId")) {
                    designerIds.add(bucket.getKey());
                }
            }
        }
        List<ProductSearchHelp> tops = topCategorySearcherService
                .findHelpByIds(topCategoryIds.toArray(new String[topCategoryIds.size()]));
        Collections.sort(tops, new Comparator<ProductSearchHelp>() {
            @Override
            public int compare(ProductSearchHelp arg0, ProductSearchHelp arg1) {
                return TopCategorySortHelp.compare(arg0.getSourceId(), arg1.getSourceId());
            }
        });
        List<ProductSearchHelp> categorys = productCategorySearcherService
                .findHelpByIds(productCategoryIds.toArray(new String[productCategoryIds.size()]));
        List<ProductSearchHelp> designers = designerSearcherService
                .findHelpByIds(designerIds.toArray(new String[designerIds.size()]));
        obj.put("tops", tops);
        obj.put("categorys", categorys);
        obj.put("designers", designers);
        return obj;
    }

    private void buildQueryBuilder(SearchRequestBuilder builder, ProductProSearchQuery searcher) {
        BoolQueryBuilder qb = QueryBuilders.boolQuery();
        if (searcher.getKeyword() != null && !StringUtils.isEmpty(searcher.getKeyword())) {
            qb.must(this.getProductQueryBuilder(searcher));
        }
        if (searcher.getTopId() != null) {
            qb.must(QueryBuilders.termQuery("topCategory", searcher.getTopId()));
        }
        if (searcher.getCategoryId() != null) {
            qb.must(QueryBuilders.termQuery("productCategory", searcher.getCategoryId()));
        }
        if (searcher.getDesignerId() != null) {
            qb.must(QueryBuilders.termQuery("designerId", searcher.getDesignerId()));
        }
        if (searcher.getSeriesId() != null) {
            qb.must(QueryBuilders.termQuery("seriesId", searcher.getSeriesId()));
        }
        if (searcher.getPromotionId() != null) {
            qb.must(QueryBuilders.termQuery("promotionId", searcher.getPromotionId()));
        }
        if (searcher.getOrderPromotionId() != null) {
            qb.must(QueryBuilders.termQuery("orderPromotionId", searcher.getOrderPromotionId()));
        }
        if (searcher.getFlashPromotionId() != null) {
            qb.must(QueryBuilders.termQuery("flashPromotionId", searcher.getFlashPromotionId()));
        }
        if (searcher.getLiveId() != null) {
            qb.must(QueryBuilders.termQuery("liveId", searcher.getLiveId()));
        }
        if (searcher.getTagId() != null) {
            qb.must(QueryBuilders.termQuery("tags", searcher.getTagId()));
        }
        if (searcher.getProductIds() != null && searcher.getProductIds().size() > 0) {
            qb.must(QueryBuilders.termQuery("productId", searcher.getProductIds()));
        }
        if (searcher.getDesignerIds() != null && searcher.getDesignerIds().size() > 0) {
            qb.must(QueryBuilders.termQuery("designerId", searcher.getDesignerIds()));
        }
        if (searcher.getSeriesIds() != null && searcher.getSeriesIds().size() > 0) {
            qb.must(QueryBuilders.termQuery("seriesId", searcher.getSeriesIds()));
        }
        if (searcher.getCategoryIds() != null && searcher.getCategoryIds().size() > 0) {
            qb.must(QueryBuilders.termQuery("productCategoryId", searcher.getCategoryIds()));
        }
        if (searcher.getStatus() != null) {
            qb.must(QueryBuilders.termQuery("status", searcher.getStatus()));
        }
        if (searcher.getMinPrice() != null) {
            qb.must(QueryBuilders.rangeQuery("minPrice").gte(searcher.getMinPrice()));
        }
        if (searcher.getMaxPrice() != null) {
            qb.must(QueryBuilders.rangeQuery("minPrice").lte(searcher.getMaxPrice()));
        }
        if (searcher.getHasPromotion() != null) {
            qb.must(QueryBuilders.rangeQuery("promotionId").gt(0));
        }
        if (searcher.getMonth() != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.MONTH, searcher.getMonth() * (-1));
            qb.must(QueryBuilders.rangeQuery("upMarketDate").from(calendar.getTime()).to(new Date()));
        }
        if (searcher.getBeginUpDate() != null) {
            qb.must(QueryBuilders.rangeQuery("upMarketDate").gte(searcher.getBeginUpDate().getTime()));
        }
        if (searcher.getEndUpDate() != null) {
            qb.must(QueryBuilders.rangeQuery("upMarketDate").lte(searcher.getEndUpDate().getTime()));
        }
        if (searcher.getAfterModifyDate() != null) {
            qb.must(QueryBuilders.rangeQuery("modifyDate").from(searcher.getAfterModifyDate().getTime()));
        }
        if (searcher.getBeginCreateDate() != null) {
            qb.must(QueryBuilders.rangeQuery("createDate").from(searcher.getBeginCreateDate().getTime()));
        }
        if (searcher.getRecommend() != null) {
            qb.must(QueryBuilders.termQuery("recommend", searcher.getRecommend()));
        }
        if (searcher.getStore() != null) {
            qb.must(QueryBuilders.termQuery("store", searcher.getStore()));
        }
        if (searcher.getMinRecom() != null) {
            qb.must(QueryBuilders.rangeQuery("recom").gte(searcher.getMinRecom()));
        }
        if (searcher.getMaxRecom() != null) {
            qb.must(QueryBuilders.rangeQuery("recom").lte(searcher.getMaxRecom()));
        }
        if (searcher.getOperRecom() != null) {
            qb.must(QueryBuilders.termQuery("operRecom", searcher.getOperRecom()));
        }
        if (searcher.getTopical() != null) {
            qb.must(QueryBuilders.termQuery("topical", searcher.getTopical()));
        }
        if (searcher.getSubscribe() != null && searcher.getSubscribe() == 1) {
            qb.must(QueryBuilders.termQuery("subscribe", searcher.getSubscribe()));
        }
        if (searcher.getAfter() != null && searcher.getAfter() == true) {
            qb.must(QueryBuilders.termQuery("after", 1));
        }
        if (searcher.getProductSellType() != null && searcher.getProductSellType().size() > 0) {
            qb.must(QueryBuilders.termQuery("productSellType", searcher.getProductSellType()));
        }
        if (searcher.getInernalSn() != null) {
            qb.must(QueryBuilders.termQuery("inernalSn", searcher.getInernalSn()));
        }
        if (searcher.getPromotionDate() != null) {
            qb.must(QueryBuilders.rangeQuery("startDate").lte(searcher.getPromotionDate().getTime()));
            qb.must(QueryBuilders.rangeQuery("endDate").gte(searcher.getPromotionDate().getTime()));
            qb.must(QueryBuilders.termQuery("promotionMark", 1));
        }
        if (searcher.getTopIds() != null) {
            qb.must(QueryBuilders.termQuery("topCategory", searcher.getTopIds()));
        }
        qb.must(QueryBuilders.termQuery("search", 1));
        qb.must(QueryBuilders.termQuery("mark", 1));
        builder.setQuery(qb);
    }

    /**
     * 商品类型
     *
     * @param searcher
     * @param builder
     * @return
     */
    private List<String> getProductType(ProductProSearchQuery searcher, SearchRequestBuilder builder) {
        TermsBuilder teamAgg = AggregationBuilders.terms("productSellType").field("productSellType").size(10);
        SearchResponse response = builder.addAggregation(teamAgg).execute().actionGet();
        Map<String, Aggregation> asMap = response.getAggregations().getAsMap();
        StringTerms tersAgg = (StringTerms) asMap.get("productSellType");
        Iterator<Bucket> teamBucketIt = tersAgg.getBuckets().iterator();
        List<String> list = new ArrayList<>();
        while (teamBucketIt.hasNext()) {
            Bucket bucket = teamBucketIt.next();
            list.add(bucket.getKey());
        }
        return list;
    }

    /**
     * 是否售后
     *
     * @param searcher
     * @param builder
     * @return
     */
    private Boolean hasAfter(ProductProSearchQuery searcher, SearchRequestBuilder builder) {
        BoolFilterBuilder boolFilter = FilterBuilders.boolFilter();
        boolFilter.must(FilterBuilders.termFilter("after", 1));
        builder.setPostFilter(boolFilter);
        builder.setSearchType(SearchType.COUNT);
        SearchResponse response = builder.execute().actionGet();
        int count = (int) response.getHits().getTotalHits();
        return count > 0 ? true : false;
    }

    /**
     * 是否有门店
     *
     * @param searcher
     * @param builder
     * @return
     */
    private Boolean hasSubscribe(ProductProSearchQuery searcher, SearchRequestBuilder builder) {
        BoolFilterBuilder boolFilter = FilterBuilders.boolFilter();
        boolFilter.must(FilterBuilders.termFilter("subscribe", 1));
        builder.setPostFilter(boolFilter);
        builder.setSearchType(SearchType.COUNT);
        SearchResponse response = builder.execute().actionGet();
        int count = (int) response.getHits().getTotalHits();
        return count > 0 ? true : false;
    }

    /**
     * 是否促销
     *
     * @param searcher
     * @param builder
     * @return
     */
    private Boolean hasPromotion(ProductProSearchQuery searcher, SearchRequestBuilder builder) {
        BoolFilterBuilder boolFilter = FilterBuilders.boolFilter();
        boolFilter.must(FilterBuilders.termFilter("promotionMark", 1));
        boolFilter.must(FilterBuilders.existsFilter("promotionId"));
        boolFilter.must(FilterBuilders.rangeFilter("startDate").lt(ElasticSearchConfig.transDate2Long(new Date())));
        boolFilter.must(FilterBuilders.rangeFilter("endDate").gt(ElasticSearchConfig.transDate2Long(new Date())));
        builder.setPostFilter(boolFilter);
        builder.setSearchType(SearchType.COUNT);
        SearchResponse response = builder.execute().actionGet();
        int count = (int) response.getHits().getTotalHits();
        return count > 0 ? true : false;
    }

    @Override
    public PageResult<JSONObject> brandList(ProductProSearchQuery searcher, PageModel page) {
        PageResult<JSONObject> pager = new PageResult<>(page);
        SearchRequestBuilder builder = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setTypes(ProductSearcherService.TYPE_PRODUCT).setSearchType(SearchType.COUNT);
        buildBoolFilterBuilder(builder, searcher);
        TermsBuilder teamAgg = AggregationBuilders.terms("designerId").field("designerId");
        builder.addAggregation(teamAgg);
        SearchResponse response = builder.setFrom(page.getStartNumber()).setSize(page.getPageSize()).execute()
                .actionGet();
        Map<String, Aggregation> asMap = response.getAggregations().getAsMap();
        LongTerms tersAgg = (LongTerms) asMap.get("designerId");
        Iterator<Bucket> teamBucketIt = tersAgg.getBuckets().iterator();
        Bucket buck = null;
        List<JSONObject> list = new ArrayList<>();
        while (teamBucketIt.hasNext()) {
            buck = teamBucketIt.next();
            SearcherDesigner designer = designerSearcherService.findById(buck.getKey());
            if (designer != null) {
                if (designer.getId() == null) {
                    continue;
                }
                JSONObject obj = new JSONObject();
                obj.put("id", designer.getId());
                obj.put("name", designer.getName());
                list.add(obj);
            }
        }
        int totalCount = tersAgg.getBuckets().size();
        pager.setList(list);
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    public PageResult<JSONObject> seriesList(ProductProSearchQuery searcher, PageModel page) {
        PageResult<JSONObject> pager = new PageResult<>(page);
        SearchRequestBuilder builder = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setTypes(ProductSearcherService.TYPE_PRODUCT);
        QueryBuilder qb = QueryBuilders.boolQuery().must(QueryBuilders.matchPhraseQuery("mark", 1))
                .must(QueryBuilders.termQuery("designerId", searcher.getDesignerId()));
        TermsBuilder teamAgg = AggregationBuilders.terms("seriesId").field("seriesId").size(page.getPageSize());
        builder.addAggregation(teamAgg);
        SearchResponse response = builder.setQuery(qb).execute().actionGet();
        Map<String, Aggregation> asMap = response.getAggregations().getAsMap();
        LongTerms tersAgg = (LongTerms) asMap.get("seriesId");
        Iterator<Bucket> teamBucketIt = tersAgg.getBuckets().iterator();
        Bucket buck = null;
        List<JSONObject> list = new ArrayList<>();
        while (teamBucketIt.hasNext()) {
            buck = teamBucketIt.next();
            SearcherSeries series = seriesSearcherService.findById(buck.getKey());
            if (series != null) {
                if (series.getId() == null) {
                    continue;
                }
                JSONObject obj = new JSONObject();
                obj.put("id", series.getId());
                obj.put("name", series.getName());
                list.add(obj);
            }
        }
        int totalCount = tersAgg.getBuckets().size();
        pager.setList(list);
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    public Map<Long, List<SearcherProduct>> findNewUpProductByBrand(Integer pageSize, Integer productSize,
                                                                    List<Long> designerIds) {
        SearchRequestBuilder responsebuilder = elasticSearchConfig.getClient()
                .prepareSearch(ElasticSearchConfig.INDEX_NAME).setTypes(ProductSearcherService.TYPE_PRODUCT);
        AggregationBuilder<?> aggregation = AggregationBuilders.terms("agg").field("designerId")
                .order(Terms.Order.aggregation("maxUpMarket", false)).size(pageSize);
        MaxBuilder maxAgg = AggregationBuilders.max("maxUpMarket").field("upMarketDate");
        TopHitsBuilder topAgg = AggregationBuilders.topHits("top").addSort("upMarketDate", SortOrder.DESC)
                .setSize(productSize);
        aggregation.subAggregation(maxAgg);
        aggregation.subAggregation(topAgg);
        QueryBuilder qb = null;
        if (designerIds != null && designerIds.size() > 0) {
            qb = QueryBuilders.boolQuery().must(QueryBuilders.matchPhraseQuery("mark", 1))
                    .must(QueryBuilders.termsQuery("designerId", designerIds));
        } else {
            qb = QueryBuilders.boolQuery().must(QueryBuilders.matchPhraseQuery("mark", 1));
        }
        SearchResponse response = responsebuilder.setQuery(qb).addAggregation(aggregation).setExplain(true).execute()
                .actionGet();
        Map<String, Aggregation> aggMap = response.getAggregations().asMap();
        Terms gradeTerms = (Terms) aggMap.get("agg");
        Iterator<Bucket> typeBucketIt = gradeTerms.getBuckets().iterator();
        Map<Long, List<SearcherProduct>> map = new HashMap<>();
        while (typeBucketIt.hasNext()) {
            List<SearcherProduct> products = new ArrayList<>();
            Bucket buck = typeBucketIt.next();
            TopHits topHits = buck.getAggregations().get("top");
            for (SearchHit hit : topHits.getHits().getHits()) {
                Long productId = Long.valueOf(hit.getId());
                Map<String, Object> source = hit.getSource();
                if (!source.isEmpty()) {
                    SearcherProduct product = new SearcherProduct();
                    ElasticSearchConfig.transMap2Bean(source, product);
                    product.setId(productId);
                    products.add(product);
                }
            }
            map.put(products.get(0).getDesignerId(), products);
        }
        return map;
    }

    @Override
    public List<Long> findNewUpBrandByTopCate(Integer day, Integer size, Long[] topIds) {
        SearchRequestBuilder responsebuilder = elasticSearchConfig.getClient()
                .prepareSearch(ElasticSearchConfig.INDEX_NAME).setTypes(ProductSearcherService.TYPE_PRODUCT);
        AggregationBuilder<?> aggregation = AggregationBuilders.terms("agg").field("designerId").size(size);
        BoolQueryBuilder qb = QueryBuilders.boolQuery().must(QueryBuilders.matchPhraseQuery("mark", 1))
                .must(QueryBuilders.rangeQuery("upMarketDate").gt(DateUtil.getIntervalDay(new Date(), -day).getTime()));
        if (topIds != null) {
            qb.must(QueryBuilders.termsQuery("topCategoryId", topIds));
        }
        SearchResponse response = responsebuilder.setQuery(qb).addAggregation(aggregation).setExplain(true).execute()
                .actionGet();
        Map<String, Aggregation> aggMap = response.getAggregations().asMap();
        Terms gradeTerms = (Terms) aggMap.get("agg");
        Iterator<Bucket> typeBucketIt = gradeTerms.getBuckets().iterator();
        List<Long> designerIds = new ArrayList<>();
        while (typeBucketIt.hasNext()) {
            Bucket buck = typeBucketIt.next();
            designerIds.add(Long.parseLong(buck.getKey()));
        }
        return designerIds;
    }

    @Override
    public List<Long> findNewCreateBrandByTopCate(Integer day, Integer size, Long[] topIds) {
        SearchRequestBuilder responsebuilder = elasticSearchConfig.getClient()
                .prepareSearch(ElasticSearchConfig.INDEX_NAME).setTypes(ProductSearcherService.TYPE_PRODUCT);
        AggregationBuilder<?> aggregation = AggregationBuilders.terms("agg").field("designerId")
                .order(Terms.Order.aggregation("maxCreateDate", false))
                .subAggregation(AggregationBuilders.max("maxCreateDate").field("createDate")).size(size);
        BoolQueryBuilder qb = QueryBuilders.boolQuery().must(QueryBuilders.matchPhraseQuery("mark", 1))
                .must(QueryBuilders.rangeQuery("createDate").gt(DateUtil.getIntervalDay(new Date(), -day).getTime()));
        if (topIds != null && topIds.length > 0) {
            qb.must(QueryBuilders.termsQuery("topCategoryId", topIds));
        }
        SearchResponse response = responsebuilder.setQuery(qb).addAggregation(aggregation).setExplain(true).execute()
                .actionGet();
        Map<String, Aggregation> aggMap = response.getAggregations().asMap();
        Terms gradeTerms = (Terms) aggMap.get("agg");
        Iterator<Bucket> typeBucketIt = gradeTerms.getBuckets().iterator();
        List<Long> designerIds = new ArrayList<>();
        while (typeBucketIt.hasNext()) {
            Bucket buck = typeBucketIt.next();
            designerIds.add(Long.parseLong(buck.getKey()));
        }
        return designerIds;
    }

    @Override
    public JSONArray findNewUpGoodsCountGroupBrand(Integer day, Integer size) {
        SearchRequestBuilder responsebuilder = elasticSearchConfig.getClient()
                .prepareSearch(ElasticSearchConfig.INDEX_NAME).setTypes(ProductSearcherService.TYPE_PRODUCT);
        AggregationBuilder<?> aggregation = AggregationBuilders.terms("agg").field("designerId")
                .order(Terms.Order.aggregation("maxCreateDate", false))
                .subAggregation(AggregationBuilders.max("maxCreateDate").field("createDate")).size(size);
        BoolQueryBuilder qb = QueryBuilders.boolQuery().must(QueryBuilders.matchPhraseQuery("mark", 1))
                .must(QueryBuilders.rangeQuery("createDate").gt(DateUtil.getIntervalDay(new Date(), -day).getTime()));
        SearchResponse response = responsebuilder.setQuery(qb).addAggregation(aggregation).setExplain(true).execute()
                .actionGet();
        Map<String, Aggregation> aggMap = response.getAggregations().asMap();
        Terms gradeTerms = (Terms) aggMap.get("agg");
        Iterator<Bucket> typeBucketIt = gradeTerms.getBuckets().iterator();
        JSONArray map = new JSONArray();
        while (typeBucketIt.hasNext()) {
            JSONObject brandHelp = new JSONObject();
            Bucket buck = typeBucketIt.next();
            brandHelp.put("count", buck.getDocCount());
            SearcherDesigner brand = designerSearcherService.findById(buck.getKey());
            if (brand == null) {
                continue;
            }
            brandHelp.put("id", brand.getId());
            brandHelp.put("name", brand.getName());
            brandHelp.put("headPic", brand.getHeadPic());
            map.add(brandHelp);
        }
        return map;
    }

    @Override
    public Map<Long, Long> findNewUpGoodsCountGroupTopCate(Integer day, Integer size) {
        SearchRequestBuilder responsebuilder = elasticSearchConfig.getClient()
                .prepareSearch(ElasticSearchConfig.INDEX_NAME).setTypes(ProductSearcherService.TYPE_PRODUCT);
        AggregationBuilder<?> aggregation = AggregationBuilders.terms("agg").field("topCategoryId").size(size);
        BoolQueryBuilder qb = QueryBuilders.boolQuery().must(QueryBuilders.matchPhraseQuery("mark", 1))
                .must(QueryBuilders.matchPhraseQuery("search", 1))
                .must(QueryBuilders.rangeQuery("createDate").gt(DateUtil.getIntervalDay(new Date(), -day).getTime()));
        SearchResponse response = responsebuilder.setQuery(qb).addAggregation(aggregation).setExplain(true).execute()
                .actionGet();
        Map<String, Aggregation> aggMap = response.getAggregations().asMap();
        Terms gradeTerms = (Terms) aggMap.get("agg");
        Iterator<Bucket> typeBucketIt = gradeTerms.getBuckets().iterator();
        Map<Long, Long> map = new HashMap<>();
        while (typeBucketIt.hasNext()) {
            JSONObject topCategoryHelp = new JSONObject();
            Bucket buck = typeBucketIt.next();
            topCategoryHelp.put("count", buck.getDocCount());
            map.put(Long.parseLong(buck.getKey()), buck.getDocCount());
        }
        return map;
    }
    // ****************************************************************

    private SearchResponse searchPage(int page, int limit, SearchRequestBuilder builder) {
        return builder.setSearchType(SearchType.DEFAULT).setFrom((page - 1) * limit).setSize(limit).setExplain(true)
                .execute().actionGet();
    }

    private SearchRequestBuilder makeSearchBuilder() {
        return elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setTypes(ProductSearcherService.TYPE_PRODUCT).setSearchType(SearchType.DEFAULT)
                .setTimeout(TimeValue.timeValueSeconds(2));
    }

    private void makeSearchSort(ProductProSearchQuery searcher) {
        // 1.最高级别库存排序，没库存默认沉底
        PageSort sort = PageSort.build(false, "store");
        // 2.用户选择排序，最新热销价格↑↓
        String orderType = searcher.getOrderByStr();
        if (StringUtils.isNotBlank(orderType)) {
            sort.add(ProductOrderTypeEnum.getPageSort(orderType));
        }
        // 3.如果存在关键字，匹配度优先排序
        if (StringUtils.isNotBlank(searcher.getKeyword())) {
            sort.addDesc("_score");
        }
        // 4.商品订单活动列表，运营设置排序
        if (searcher.getPromotionId() != null) {
            sort.addDesc("gpSort");
        } else if (searcher.getOrderPromotionId() != null) {
            sort.addDesc("opSort");
        } else if (searcher.getFlashPromotionId() != null) {
            sort.addDesc("fpSort");
        }
        // 5.商品推荐值排序
        sort.addDesc("recomScore");
        // 6.商品上架时间排序
        sort.addDesc("upMarketDate");
        List<PageOrder> ol = sort.getOrders();
        String[] sortFields = new String[ol.size()];
        SortOrder[] sortOrders = new SortOrder[ol.size()];
        int i = 0;
        for (PageOrder o : ol) {
            sortFields[i] = o.getProperty();
            sortOrders[i] = o.isAsc() ? SortOrder.ASC : SortOrder.DESC;
            i++;
        }
        searcher.setOrders(sortOrders);
        searcher.setSortFields(sortFields);
    }

    private List<SearcherProduct> buildResList(SearchResponse response) {
        List<SearcherProduct> list = new ArrayList<>();
        for (SearchHit hit : response.getHits()) {
            Long id = Long.valueOf(hit.getId());
            Map<String, Object> source = hit.getSource();
            if (!source.isEmpty()) {
                SearcherProduct bean = new SearcherProduct();
                ElasticSearchConfig.transMap2Bean(source, bean);
                bean.setId(id);
                list.add(bean);
            }
        }
        return list;
    }

}
