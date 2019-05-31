package com.d2c.backend.rest.search;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.query.model.BaseQuery;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.common.base.thread.MyExecutors;
import com.d2c.content.dto.ThemeDto;
import com.d2c.content.model.QuizBank;
import com.d2c.content.model.SensitiveWords;
import com.d2c.content.query.QuizBankSearcher;
import com.d2c.content.query.SensitiveWordsSearcher;
import com.d2c.content.query.ThemeSearcher;
import com.d2c.content.search.model.SearcherQuizBank;
import com.d2c.content.search.model.SearcherSensitiveWords;
import com.d2c.content.search.model.SearcherTheme;
import com.d2c.content.search.service.QuizBankSearcherService;
import com.d2c.content.search.service.SensitiveWordsSearcherService;
import com.d2c.content.search.service.ThemeSearcherService;
import com.d2c.content.service.QuizBankService;
import com.d2c.content.service.SensitiveWordsService;
import com.d2c.content.service.ThemeService;
import com.d2c.logger.model.MemberSearchInfo;
import com.d2c.logger.model.MemberSearchSum;
import com.d2c.logger.model.Message;
import com.d2c.logger.query.MemberSearchInfoSearcher;
import com.d2c.logger.query.MemberSearchSumSearcher;
import com.d2c.logger.query.MessageSearcher;
import com.d2c.logger.search.model.SearcherMemberSum;
import com.d2c.logger.search.model.SearcherMessage;
import com.d2c.logger.search.service.MessageSearcherService;
import com.d2c.logger.search.service.SearchKeySearcherService;
import com.d2c.logger.service.MemberSearchKeyService;
import com.d2c.logger.service.MemberSearchSumService;
import com.d2c.logger.service.MessageService;
import com.d2c.member.dto.CommentDto;
import com.d2c.member.dto.ConsultDto;
import com.d2c.member.dto.MemberAttentionDto;
import com.d2c.member.dto.MemberCollectionDto;
import com.d2c.member.model.*;
import com.d2c.member.query.*;
import com.d2c.member.search.model.*;
import com.d2c.member.search.service.*;
import com.d2c.member.service.*;
import com.d2c.product.dto.BargainPromotionDto;
import com.d2c.product.dto.BrandDto;
import com.d2c.product.dto.ProductDto;
import com.d2c.product.dto.SeriesDto;
import com.d2c.product.model.*;
import com.d2c.product.query.*;
import com.d2c.product.search.model.*;
import com.d2c.product.search.service.*;
import com.d2c.product.service.*;
import com.d2c.util.string.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/rest/cms/search")
public class SearchCtrl extends BaseCtrl<BaseQuery> {

    private static Logger logger = LoggerFactory.getLogger(SearchCtrl.class);
    @Autowired
    private MemberSearchSumService memberSearchSumService;
    @Reference
    private SearchKeySearcherService searchKeySearcherService;
    @Reference
    private TopCategorySearcherService topCategorySearcherService;
    @Reference
    private ProductCategorySearcherService productCategorySearcherService;
    @Autowired
    private TopCategoryService topCategoryService;
    @Autowired
    private ProductCategoryService productCategoryService;
    @Autowired
    private ProductSkuService productSkuService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductSummaryService productSummaryService;
    @Reference
    private ProductSearcherService productSearcherService;
    @Autowired
    private ProductModuleSearchService productModuleSearchService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private BrandTagService brandTagService;
    @Reference
    private DesignerSearcherService designerSearcherService;
    @Reference
    private DesignerTagSearcherService designerTagSearcherService;
    @Reference
    private DesignerTagRelationSearcherService designerTagRelationSearcherService;
    @Autowired
    private MemberShareService memberShareService;
    @Autowired
    private MemberShareTagService memberShareTagService;
    @Reference
    private MemberShareSearcherService memberShareSearcherService;
    @Reference
    private MemberShareTagSearcherService memberShareTagSearcherService;
    @Autowired
    private MessageService messageService;
    @Reference
    private MessageSearcherService messageSearcherService;
    @Reference
    private MemberCollectionSearcherService memberCollectionSearcherService;
    @Autowired
    private MemberCollectionService memberCollectionService;
    @Reference
    private MemberAttentionSearcherService memberAttentionSearcherService;
    @Autowired
    private MemberAttentionService memberAttentionService;
    @Reference
    private MemberLikeSearcherService memberLikeSearcherService;
    @Autowired
    private MemberLikeService memberLikeService;
    @Reference
    private SensitiveWordsSearcherService sensitiveWordsSearcherService;
    @Autowired
    private SensitiveWordsService sensitiveWordsService;
    @Reference
    private CommentSearcherService commentSearcherService;
    @Autowired
    private CommentService commentService;
    @Reference
    private CommentReplySearcherService commentReplySearcherService;
    @Reference
    private ConsultSearcherService consultSearcherService;
    @Autowired
    private ConsultService consultService;
    @Reference
    private MemberFollowSearcherService memberFollowSearcherService;
    @Autowired
    private MemberFollowService memberFollowService;
    @Autowired
    private ProductSkuStockService productSkuStockService;
    @Reference
    private SearchInfoSearcherService searchInfoSearcherService;
    @Autowired
    private MemberSearchKeyService memberSearchKeyService;
    @Autowired
    private QuizBankService quizBankService;
    @Reference
    private QuizBankSearcherService quizBankSearcherService;
    @Reference
    private ThemeSearcherService themeSearcherService;
    @Autowired
    private ThemeService themeService;
    @Autowired
    private ProductDetailService productDetailService;
    @Reference
    private TopicSearcherService topicSearcherService;
    @Autowired
    private TopicService topicService;
    @Reference
    private SeriesSearcherService seriesSearcherService;
    @Autowired
    private SeriesService seriesService;
    @Autowired
    private BrandTagRelationService brandTagRelationService;
    @Reference
    private BargainPromotionSearcherService bargainPromotionSearcherService;
    @Autowired
    private BargainPromotionService bargainPromotionService;
    @Autowired
    private ProductShareRelationService productShareRelationService;
    @Reference
    private CollagePromotionSearcherService collagePromotionSearcherService;
    @Autowired
    private CollagePromotionService collagePromotionService;

    /**
     * 强制同步商品库存
     *
     * @return
     * @throws InterruptedException
     */
    @RequestMapping(value = "/product/sync/stock", method = RequestMethod.POST)
    public Response syncProductStore() throws InterruptedException, NotLoginException {
        this.getLoginedAdmin();
        SuccessResponse successResponse = new SuccessResponse();
        Date currentTime = new Date();
        String syncStamp = String.valueOf(currentTime.getTime());
        this.doSyncStock(syncStamp);
        this.updateSearch(syncStamp);
        successResponse.setMsg("强制同步商品库存, 操作成功！");
        return successResponse;
    }

    /**
     * 同步商品库存
     */
    private void doSyncStock(String syncStamp) {
        try {
            /**
             * 同步表中占单库存清零
             */
            productSkuStockService.doCleanOccupy();
            /**
             * 同步SKU的库存
             */
            productSkuService.doSyncSkuStore();
            /**
             * 标记上一步存在变动的商品的时间戳
             */
            productService.updateSyncTimestamp(syncStamp);
            /**
             * 汇总SKU的自营库存
             */
            productSkuService.doSumSkuStore();
            /**
             * 汇总商品的自营库存和POP库存
             */
            productService.doSumProductStore();
        } catch (Exception e) {
            logger.error("doSyncStock error!" + e.getMessage(), e);
        }
    }

    /**
     * 更新存在变动标记的商品索引
     */
    private void updateSearch(String syncStamp) {
        try {
            PageModel page = new PageModel();
            int numberPage = 1;
            page.setP(numberPage);
            page.setPageSize(500);
            PageResult<Product> pager = new PageResult<>();
            do {
                pager = productService.findSyncProductStore(syncStamp, page);
                for (Product product : pager.getList()) {
                    try {
                        int storeUpdate = 0, spotUpdate = 0;
                        if (product.getSyncStore() + product.getPopStore() > 0) {
                            storeUpdate = 1;
                        }
                        if (product.getSyncStore() > 0) {
                            spotUpdate = 1;
                        }
                        productSearcherService.updateStore(product.getId(), storeUpdate, spotUpdate);
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }
                }
                page.setPageNumber(page.getPageNumber() + 1);
            } while (pager.isNext());
        } catch (Exception e) {
            logger.error("updateSearch error!" + e.getMessage(), e);
        }
    }

    /**
     * 重建商品索引
     *
     * @return
     * @throws InterruptedException
     */
    @RequestMapping(value = "/index/product", method = RequestMethod.POST)
    public Response buildProductSearcherIndex(ProductSearcher searcher) throws InterruptedException, NotLoginException {
        this.getLoginedAdmin();
        // productSearcherService.removeAll();
        PageModel page = new PageModel();
        int numberPage = 1;
        page.setP(numberPage);
        page.setPageSize(500);
        PageResult<ProductDto> pager = new PageResult<>();
        ExecutorService limitThreadPool = MyExecutors.newLimit(20);
        do {
            pager = productService.findBySearch(searcher, page);
            for (Product product : pager.getList()) {
                limitThreadPool.execute(new Runnable() {
                    @Override
                    public void run() {
                        if (product.getMark() == null || product.getMark() < 0) {
                            productSearcherService.remove(product.getId());
                        } else {
                            ProductDetail detail = productDetailService.findByProductId(product.getId());
                            ProductSummary summary = productSummaryService.findByProductId(product.getId());
                            SearcherProduct searcherProduct = productModuleSearchService.getSearchDto(product, summary,
                                    detail);
                            productSearcherService.insert(searcherProduct);
                        }
                    }
                });
            }
            page.setPageNumber(page.getPageNumber() + 1);
        } while (pager.isNext());
        try {
            limitThreadPool.shutdown();
            while (limitThreadPool.isTerminated()) {
                limitThreadPool.awaitTermination(100, TimeUnit.MILLISECONDS);
            }
        } catch (InterruptedException e) {
            logger.error("线程池同步终止异常...", e);
        }
        return new SuccessResponse();
    }

    /**
     * 重建商品分类索引
     *
     * @return
     * @throws InterruptedException
     */
    @RequestMapping(value = "/index/category", method = RequestMethod.POST)
    public Response buildCategorySearcherIndex() throws InterruptedException, NotLoginException {
        this.getLoginedAdmin();
        topCategorySearcherService.removeAll();
        List<TopCategory> topCategories = topCategoryService.findAll(1);
        for (TopCategory top : topCategories) {
            SearcherTopCategory st = new SearcherTopCategory();
            BeanUtils.copyProperties(top, st);
            topCategorySearcherService.insert(st);
        }
        productCategorySearcherService.removeAll();
        List<ProductCategory> productCategories = productCategoryService.findAll(1);
        for (ProductCategory pc : productCategories) {
            SearcherProductCategory spc = new SearcherProductCategory();
            BeanUtils.copyProperties(pc, spc);
            productCategorySearcherService.insert(spc);
        }
        return new SuccessResponse();
    }

    /**
     * 重建设计师索引
     *
     * @return
     * @throws InterruptedException
     */
    @RequestMapping(value = "/index/designer", method = RequestMethod.POST)
    public Response buildDesignerSearcherIndex() throws InterruptedException, NotLoginException {
        this.getLoginedAdmin();
        designerSearcherService.removeAll();
        BrandSearcher dSearcher = new BrandSearcher();
        dSearcher.setMarketable(true);
        int numberPage = 1;
        PageModel page = new PageModel();
        page.setP(numberPage);
        page.setPageSize(200);
        PageResult<BrandDto> designers = new PageResult<>(page);
        do {
            designers = brandService.findBySearch(dSearcher, page);
            for (Brand brand : designers.getList()) {
                if (brand.getMark() == 1) {
                    SearcherDesigner sd = new SearcherDesigner();
                    BeanUtils.copyProperties(brand, sd);
                    Map<String, String> map = seriesService.findStyleAndPriceByBrand(brand.getId());
                    if (map != null) {
                        sd.setPrice(map.get("price").toString());
                    }
                    designerSearcherService.insert(sd);
                }
            }
            numberPage = numberPage + 1;
            page.setPageNumber(numberPage);
            Thread.sleep(1000);
        } while (designers.isNext());
        return new SuccessResponse();
    }

    /**
     * 重建设计师标签
     *
     * @return
     * @throws InterruptedException
     */
    @RequestMapping(value = "/index/designer/tag", method = RequestMethod.POST)
    public Response buildDesignerTSearcherIndex() throws InterruptedException, NotLoginException {
        this.getLoginedAdmin();
        designerTagSearcherService.removeAll();
        PageResult<BrandTag> pager = brandTagService.findBySearch(new BrandTagSearcher(), new PageModel(1, 500));
        for (BrandTag t : pager.getList()) {
            if (t.getStatus() == 1) {
                SearcherDesignerTag sdt = new SearcherDesignerTag();
                BeanUtils.copyProperties(t, sdt);
                designerTagSearcherService.insert(sdt);
            }
        }
        return new SuccessResponse();
    }

    /**
     * 重建设计师标签关系
     *
     * @return
     * @throws InterruptedException
     */
    @RequestMapping(value = "/index/designer/tag/relation", method = RequestMethod.POST)
    public Response buildDesignerTRSearcherIndex() throws InterruptedException, NotLoginException {
        this.getLoginedAdmin();
        designerTagRelationSearcherService.removeAll();
        PageResult<BrandTag> pager = brandTagService.findBySearch(new BrandTagSearcher(), new PageModel(1, 500));
        for (BrandTag t : pager.getList()) {
            int numberPage = 1;
            PageModel page = new PageModel();
            page.setP(numberPage);
            page.setPageSize(500);
            PageResult<SearcherDesignerTagRelation> designers = new PageResult<>(page);
            do {
                designers = brandTagRelationService.findByTagId(t.getId(), page);
                for (SearcherDesignerTagRelation designer : designers.getList()) {
                    designerTagRelationSearcherService.insert(designer);
                }
                numberPage = numberPage + 1;
                page.setPageNumber(numberPage);
                Thread.sleep(1000);
            } while (designers.isNext());
        }
        return new SuccessResponse();
    }

    /**
     * 重建买家秀索引
     *
     * @return
     * @throws InterruptedException
     */
    @RequestMapping(value = "/index/membershare", method = RequestMethod.POST)
    public Response buildMemberShareSearcherIndex() throws InterruptedException, NotLoginException {
        this.getLoginedAdmin();
        memberShareSearcherService.removeAll();
        MemberShareSearcher searcher = new MemberShareSearcher();
        int numberPage = 1;
        PageModel page = new PageModel();
        page.setP(numberPage);
        page.setPageSize(500);
        PageResult<MemberShare> memberShares = new PageResult<>(page);
        do {
            memberShares = memberShareService.findBySearch(searcher, page);
            for (MemberShare memberShare : memberShares.getList()) {
                SearcherMemberShare sms = new SearcherMemberShare();
                BeanUtils.copyProperties(memberShare, sms);
                List<Long> productIds = productShareRelationService.findProductIdByShareId(memberShare.getId());
                sms.setProductIds(StringUtil.longListToStr(productIds));
                memberShareSearcherService.insert(sms);
            }
            numberPage = numberPage + 1;
            page.setPageNumber(numberPage);
            Thread.sleep(1000);
        } while (memberShares.isNext());
        return new SuccessResponse();
    }

    /**
     * 重建买家秀标签索引
     *
     * @return
     * @throws InterruptedException
     */
    @RequestMapping(value = "/index/membershare/tag", method = RequestMethod.POST)
    public Response buildMemberShareTSearcherIndex() throws InterruptedException, NotLoginException {
        this.getLoginedAdmin();
        memberShareTagSearcherService.removeAll();
        PageResult<MemberShareTag> pager = memberShareTagService.findBySearch(new MemberShareTagSearcher(),
                new PageModel(1, 500));
        for (MemberShareTag t : pager.getList()) {
            if (t.getStatus() == 1) {
                SearcherMemberShareTag sms = new SearcherMemberShareTag();
                BeanUtils.copyProperties(t, sms);
                memberShareTagSearcherService.insert(sms);
            }
        }
        return new SuccessResponse();
    }

    /**
     * 重建搜索关键字索引
     *
     * @return
     * @throws InterruptedException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @RequestMapping(value = "/index/searcherKey", method = RequestMethod.POST)
    public Response buildSearcherKeySearcherIndex()
            throws InterruptedException, IllegalAccessException, InvocationTargetException, NotLoginException {
        this.getLoginedAdmin();
        MemberSearchSumSearcher mssSearcher = new MemberSearchSumSearcher();
        mssSearcher.setSystem(true);
        PageModel page = new PageModel();
        page.setPageSize(200);
        int numberPage = 1;
        page.setP(numberPage);
        PageResult<MemberSearchSum> mssPager = new PageResult<>(page);
        searchKeySearcherService.removeAll();
        do {
            mssPager = memberSearchSumService.findBySearcher(mssSearcher, page);
            for (MemberSearchSum sum : mssPager.getList()) {
                SearcherMemberSum dto = new SearcherMemberSum();
                BeanUtils.copyProperties(sum, dto);
                searchKeySearcherService.insert(dto, 1);
            }
            numberPage = numberPage + 1;
            page.setPageNumber(numberPage);
            Thread.sleep(1000);
        } while (mssPager.isNext());
        return new SuccessResponse();
    }

    /**
     * 重建消息索引
     *
     * @return
     * @throws InterruptedException
     */
    @RequestMapping(value = "/index/message", method = RequestMethod.POST)
    public Response buildMessageSearcherIndex() throws InterruptedException, NotLoginException {
        this.getLoginedAdmin();
        messageSearcherService.removeAll();
        MessageSearcher searcher = new MessageSearcher();
        int numberPage = 1;
        searcher.setOverTime(false);
        PageResult<Message> pager = null;
        PageModel page = new PageModel(1, 500);
        ExecutorService limitThreadPool = MyExecutors.newLimit(20);
        do {
            pager = messageService.findBySearch(page, searcher);
            for (Message msg : pager.getList()) {
                limitThreadPool.execute(new Runnable() {
                    @Override
                    public void run() {
                        SearcherMessage sms = new SearcherMessage();
                        BeanUtils.copyProperties(msg, sms);
                        messageSearcherService.insert(sms);
                    }
                });
            }
            numberPage = numberPage + 1;
            page.setPageNumber(numberPage);
        } while (pager.isNext());
        try {
            limitThreadPool.shutdown();
            while (limitThreadPool.isTerminated()) {
                limitThreadPool.awaitTermination(100, TimeUnit.MILLISECONDS);
            }
        } catch (InterruptedException e) {
            logger.error("线程池同步终止异常...", e);
        }
        return new SuccessResponse();
    }

    /**
     * 重建用户关注的设计师索引
     *
     * @return
     * @throws InterruptedException
     */
    @RequestMapping(value = "/index/memberattention", method = RequestMethod.POST)
    public Response buildMemberAttentionIndex() throws InterruptedException, NotLoginException {
        this.getLoginedAdmin();
        memberAttentionSearcherService.removeAll();
        InterestSearcher searcher = new InterestSearcher();
        int numberPage = 1;
        PageModel page = new PageModel();
        page.setP(numberPage);
        page.setPageSize(500);
        PageResult<MemberAttentionDto> pager = new PageResult<>(page);
        ExecutorService limitThreadPool = MyExecutors.newLimit(20);
        do {
            pager = memberAttentionService.findBySearch(searcher, page);
            for (MemberAttentionDto memberAttentionDto : pager.getList()) {
                limitThreadPool.execute(new Runnable() {
                    @Override
                    public void run() {
                        SearcherMemberAttention searcherMemberAttention = new SearcherMemberAttention();
                        BeanUtils.copyProperties(memberAttentionDto, searcherMemberAttention);
                        memberAttentionSearcherService.insert(searcherMemberAttention);
                    }
                });
            }
            numberPage = numberPage + 1;
            page.setPageNumber(numberPage);
        } while (pager.isNext());
        try {
            limitThreadPool.shutdown();
            while (limitThreadPool.isTerminated()) {
                limitThreadPool.awaitTermination(100, TimeUnit.MILLISECONDS);
            }
        } catch (InterruptedException e) {
            logger.error("线程池同步终止异常...", e);
        }
        return new SuccessResponse();
    }

    /**
     * 重建用户收藏的商品索引
     *
     * @return
     * @throws InterruptedException
     */
    @RequestMapping(value = "/index/membercollection", method = RequestMethod.POST)
    public Response buildMemberCollectionIndex() throws InterruptedException, NotLoginException {
        this.getLoginedAdmin();
        memberCollectionSearcherService.removeAll();
        InterestSearcher searcher = new InterestSearcher();
        int numberPage = 1;
        PageModel page = new PageModel();
        page.setP(numberPage);
        page.setPageSize(500);
        PageResult<MemberCollectionDto> pager = new PageResult<>(page);
        ExecutorService limitThreadPool = MyExecutors.newLimit(20);
        do {
            pager = memberCollectionService.findBySearch(searcher, page);
            for (MemberCollectionDto memberCollectionDto : pager.getList()) {
                limitThreadPool.execute(new Runnable() {
                    @Override
                    public void run() {
                        SearcherMemberCollection memberCollection = new SearcherMemberCollection();
                        BeanUtils.copyProperties(memberCollectionDto, memberCollection);
                        memberCollectionSearcherService.insert(memberCollection);
                    }
                });
            }
            numberPage = numberPage + 1;
            page.setPageNumber(numberPage);
        } while (pager.isNext());
        try {
            limitThreadPool.shutdown();
            while (limitThreadPool.isTerminated()) {
                limitThreadPool.awaitTermination(100, TimeUnit.MILLISECONDS);
            }
        } catch (InterruptedException e) {
            logger.error("线程池同步终止异常...", e);
        }
        return new SuccessResponse();
    }

    /**
     * 重建用户点赞的买家秀索引
     *
     * @return
     * @throws InterruptedException
     */
    @RequestMapping(value = "/index/memberlike", method = RequestMethod.POST)
    public Response buildMemberLikeIndex() throws InterruptedException, NotLoginException {
        this.getLoginedAdmin();
        memberLikeSearcherService.removeAll();
        MemberLikeSearcher searcher = new MemberLikeSearcher();
        int numberPage = 1;
        PageModel page = new PageModel();
        page.setP(numberPage);
        page.setPageSize(500);
        PageResult<MemberLike> pager = new PageResult<>(page);
        ExecutorService limitThreadPool = MyExecutors.newLimit(20);
        do {
            pager = memberLikeService.findBySearch(searcher, page);
            for (MemberLike memberLike : pager.getList()) {
                limitThreadPool.execute(new Runnable() {
                    @Override
                    public void run() {
                        SearcherMemberLike searcherMemberLike = new SearcherMemberLike();
                        BeanUtils.copyProperties(memberLike, searcherMemberLike);
                        memberLikeSearcherService.insert(searcherMemberLike);
                    }
                });
            }
            numberPage = numberPage + 1;
            page.setPageNumber(numberPage);
        } while (pager.isNext());
        try {
            limitThreadPool.shutdown();
            while (limitThreadPool.isTerminated()) {
                limitThreadPool.awaitTermination(100, TimeUnit.MILLISECONDS);
            }
        } catch (InterruptedException e) {
            logger.error("线程池同步终止异常...", e);
        }
        return new SuccessResponse();
    }

    /**
     * 重建用户关注的人索引
     *
     * @return
     * @throws InterruptedException
     */
    @RequestMapping(value = "/index/follow", method = RequestMethod.POST)
    public Response buildMemberFollowIndex() throws InterruptedException, NotLoginException {
        this.getLoginedAdmin();
        memberFollowSearcherService.removeAll();
        int numberPage = 1;
        PageModel page = new PageModel();
        page.setP(numberPage);
        page.setPageSize(500);
        PageResult<MemberFollow> pager = new PageResult<>(page);
        ExecutorService limitThreadPool = MyExecutors.newLimit(20);
        do {
            pager = memberFollowService.findByPage(page);
            for (MemberFollow memberFollow : pager.getList()) {
                limitThreadPool.execute(new Runnable() {
                    @Override
                    public void run() {
                        SearcherMemberFollow searcherMemberFollow = new SearcherMemberFollow();
                        BeanUtils.copyProperties(memberFollow, searcherMemberFollow);
                        memberFollowSearcherService.rebuild(searcherMemberFollow);
                    }
                });
            }
            numberPage = numberPage + 1;
            page.setPageNumber(numberPage);
        } while (pager.isNext());
        try {
            limitThreadPool.shutdown();
            while (limitThreadPool.isTerminated()) {
                limitThreadPool.awaitTermination(100, TimeUnit.MILLISECONDS);
            }
        } catch (InterruptedException e) {
            logger.error("线程池同步终止异常...", e);
        }
        return new SuccessResponse();
    }

    /**
     * 重建敏感字索引
     */
    @RequestMapping(value = "/index/sensitivewords", method = RequestMethod.POST)
    public Response buildSensitiveWordsIndex() throws InterruptedException, NotLoginException {
        this.getLoginedAdmin();
        sensitiveWordsSearcherService.removeAll();
        SensitiveWordsSearcher searcher = new SensitiveWordsSearcher();
        int numberPage = 1;
        PageModel page = new PageModel();
        page.setP(numberPage);
        page.setPageSize(500);
        PageResult<SensitiveWords> pager = new PageResult<>(page);
        do {
            pager = sensitiveWordsService.findBySearch(searcher, page);
            for (SensitiveWords sensitiveWords : pager.getList()) {
                SearcherSensitiveWords searcherSensitiveWords = new SearcherSensitiveWords();
                BeanUtils.copyProperties(sensitiveWords, searcherSensitiveWords);
                sensitiveWordsSearcherService.insert(searcherSensitiveWords);
            }
            numberPage = numberPage + 1;
            page.setPageNumber(numberPage);
            Thread.sleep(1000);
        } while (pager.isNext());
        return new SuccessResponse();
    }

    /**
     * 重建评论索引
     */
    @RequestMapping(value = "/index/comments", method = RequestMethod.POST)
    public Response buildCommentsIndex() throws InterruptedException, NotLoginException {
        this.getLoginedAdmin();
        commentSearcherService.removeAll();
        commentReplySearcherService.removeAll();
        CommentSearcher searcher = new CommentSearcher();
        int numberPage = 1;
        PageModel page = new PageModel();
        page.setP(numberPage);
        page.setPageSize(500);
        PageResult<CommentDto> pager = new PageResult<>(page);
        do {
            pager = commentService.findBySearcher(searcher, page);
            for (CommentDto commentDto : pager.getList()) {
                SearcherComment searcherComment = new SearcherComment();
                BeanUtils.copyProperties(commentDto, searcherComment);
                commentSearcherService.rebuild(searcherComment);
                for (CommentReply commentReply : commentDto.getCommentReplys()) {
                    SearcherCommentReply searcherCommentReply = new SearcherCommentReply();
                    BeanUtils.copyProperties(commentReply, searcherCommentReply);
                    commentReplySearcherService.rebuild(searcherCommentReply);
                }
            }
            numberPage = numberPage + 1;
            page.setPageNumber(numberPage);
            Thread.sleep(1000);
        } while (pager.isNext());
        return new SuccessResponse();
    }

    /**
     * 重建咨询索引
     */
    @RequestMapping(value = "/index/consult", method = RequestMethod.POST)
    public Response buildConsultIndex() throws InterruptedException, NotLoginException {
        this.getLoginedAdmin();
        consultSearcherService.removeAll();
        ConsultSearcher searcher = new ConsultSearcher();
        int numberPage = 1;
        PageModel page = new PageModel();
        page.setP(numberPage);
        page.setPageSize(500);
        PageResult<ConsultDto> pager = new PageResult<>(page);
        do {
            pager = consultService.findBySearcher(searcher, page);
            for (ConsultDto consultDto : pager.getList()) {
                SearcherConsult searcherConsult = new SearcherConsult();
                BeanUtils.copyProperties(consultDto, searcherConsult);
                consultSearcherService.rebuild(searcherConsult);
            }
            numberPage = numberPage + 1;
            page.setPageNumber(numberPage);
            Thread.sleep(1000);
        } while (pager.isNext());
        return new SuccessResponse();
    }

    /**
     * 重建关键字索引
     */
    @RequestMapping(value = "/index/searchInfo", method = RequestMethod.POST)
    public Response buildSearchInfoIndex() throws InterruptedException, NotLoginException {
        this.getLoginedAdmin();
        searchInfoSearcherService.removeAll();
        int numberPage = 1;
        PageModel page = new PageModel();
        page.setP(numberPage);
        page.setPageSize(500);
        MemberSearchInfoSearcher searcher = new MemberSearchInfoSearcher();
        PageResult<MemberSearchInfo> pager = new PageResult<>(page);
        do {
            pager = memberSearchKeyService.findBySearcher(searcher, page);
            for (MemberSearchInfo searchInfo : pager.getList()) {
                SearcherMemberInfo searcherSearchInfo = new SearcherMemberInfo();
                BeanUtils.copyProperties(searchInfo, searcherSearchInfo);
                searchInfoSearcherService.insert(searcherSearchInfo);
            }
            numberPage = numberPage + 1;
            page.setPageNumber(numberPage);
            Thread.sleep(1000);
        } while (pager.isNext());
        return new SuccessResponse();
    }

    /**
     * 重建题库索引
     */
    @RequestMapping(value = "/index/quizbank", method = RequestMethod.POST)
    public Response buildQuizBank() throws InterruptedException, NotLoginException {
        this.getLoginedAdmin();
        quizBankSearcherService.removeAll();
        int numberPage = 1;
        PageModel page = new PageModel();
        page.setP(numberPage);
        page.setPageSize(500);
        QuizBankSearcher searcher = new QuizBankSearcher();
        PageResult<QuizBank> pager = new PageResult<>(page);
        do {
            pager = quizBankService.findBySearcher(searcher, page);
            for (QuizBank quizBank : pager.getList()) {
                SearcherQuizBank searcherQuizBank = new SearcherQuizBank();
                BeanUtils.copyProperties(quizBank, searcherQuizBank);
                quizBankSearcherService.insert(searcherQuizBank);
            }
            numberPage = numberPage + 1;
            page.setPageNumber(numberPage);
            Thread.sleep(1000);
        } while (pager.isNext());
        return new SuccessResponse();
    }

    /**
     * 重建专题索引
     */
    @RequestMapping(value = "/index/theme", method = RequestMethod.POST)
    public Response buildTheme() throws InterruptedException, NotLoginException {
        this.getLoginedAdmin();
        themeSearcherService.removeAll();
        int numberPage = 1;
        PageModel page = new PageModel();
        page.setP(numberPage);
        page.setPageSize(500);
        ThemeSearcher searcher = new ThemeSearcher();
        PageResult<ThemeDto> pager = new PageResult<>(page);
        do {
            pager = themeService.findDtoBySearcher(searcher, page);
            for (ThemeDto theme : pager.getList()) {
                SearcherTheme searcherTheme = new SearcherTheme();
                BeanUtils.copyProperties(theme, searcherTheme);
                themeSearcherService.insert(searcherTheme);
            }
            numberPage = numberPage + 1;
            page.setPageNumber(numberPage);
            Thread.sleep(1000);
        } while (pager.isNext());
        return new SuccessResponse();
    }

    /**
     * 重建话题索引
     */
    @RequestMapping(value = "/index/topic", method = RequestMethod.POST)
    public Response buildTopic() throws InterruptedException, NotLoginException {
        this.getLoginedAdmin();
        topicSearcherService.removeAll();
        int numberPage = 1;
        PageModel page = new PageModel();
        page.setP(numberPage);
        page.setPageSize(500);
        TopicSearcher searcher = new TopicSearcher();
        PageResult<Topic> pager = new PageResult<>(page);
        do {
            pager = topicService.findBySearcher(searcher, page);
            for (Topic topic : pager.getList()) {
                SearcherTopic searcherTopic = new SearcherTopic();
                BeanUtils.copyProperties(topic, searcherTopic);
                topicSearcherService.insert(searcherTopic);
            }
            numberPage = numberPage + 1;
            page.setPageNumber(numberPage);
            Thread.sleep(1000);
        } while (pager.isNext());
        return new SuccessResponse();
    }

    /**
     * 重建系列索引
     */
    @RequestMapping(value = "/index/series", method = RequestMethod.POST)
    public Response buildSeries() throws InterruptedException, NotLoginException {
        seriesSearcherService.removeAll();
        int numberPage = 1;
        PageModel page = new PageModel();
        page.setP(numberPage);
        page.setPageSize(500);
        SeriesSearcher searcher = new SeriesSearcher();
        PageResult<SeriesDto> pager = new PageResult<>(page);
        do {
            pager = seriesService.findBySearch(searcher, page);
            for (Series series : pager.getList()) {
                SearcherSeries searcherSerice = new SearcherSeries();
                BeanUtils.copyProperties(series, searcherSerice);
                seriesSearcherService.insert(searcherSerice);
            }
            numberPage = numberPage + 1;
            page.setPageNumber(numberPage);
            Thread.sleep(1000);
        } while (pager.isNext());
        return new SuccessResponse();
    }

    /**
     * 重建砍价活动
     *
     * @return
     * @throws InterruptedException
     */
    @RequestMapping(value = "/index/bargain", method = RequestMethod.POST)
    public Response buildBargain() throws InterruptedException {
        bargainPromotionSearcherService.removeAll();
        int numberPage = 1;
        PageModel page = new PageModel();
        page.setP(numberPage);
        page.setPageSize(500);
        BargainPromotionSearcher searcher = new BargainPromotionSearcher();
        PageResult<BargainPromotionDto> pager = new PageResult<>(page);
        do {
            pager = bargainPromotionService.findBySearcher(searcher, page);
            for (BargainPromotionDto bargain : pager.getList()) {
                SearcherBargainPromotion searcherBargain = new SearcherBargainPromotion();
                BeanUtils.copyProperties(bargain, searcherBargain);
                bargainPromotionSearcherService.insert(searcherBargain);
            }
            numberPage = numberPage + 1;
            page.setPageNumber(numberPage);
            Thread.sleep(1000);
        } while (pager.isNext());
        return new SuccessResponse();
    }

    /**
     * 重建拼团活动
     *
     * @return
     * @throws InterruptedException
     */
    @RequestMapping(value = "/index/collage", method = RequestMethod.POST)
    public Response buildCollage() throws InterruptedException {
        collagePromotionSearcherService.removeAll();
        int numberPage = 1;
        PageModel page = new PageModel();
        page.setP(numberPage);
        page.setPageSize(500);
        CollagePromotionSearcher searcher = new CollagePromotionSearcher();
        PageResult<CollagePromotion> pager = new PageResult<>(page);
        do {
            pager = collagePromotionService.findBySearch(searcher, page);
            for (CollagePromotion collagePromotion : pager.getList()) {
                SearcherCollagePromotion searcherCollage = new SearcherCollagePromotion();
                BeanUtils.copyProperties(collagePromotion, searcherCollage);
                collagePromotionSearcherService.insert(searcherCollage);
            }
            numberPage = numberPage + 1;
            page.setPageNumber(numberPage);
            Thread.sleep(1000);
        } while (pager.isNext());
        return new SuccessResponse();
    }

    @Override
    protected Response doList(BaseQuery searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected int count(BaseQuery searcher) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    protected String getExportFileType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected List<Map<String, Object>> getRow(BaseQuery searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected String getFileName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected String[] getExportTitles() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doHelp(BaseQuery searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response findById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doInsert(JSONObject data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doDelete(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doBatchDelete(Long[] ids) {
        // TODO Auto-generated method stub
        return null;
    }

}
