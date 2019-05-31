package com.d2c.flame.controller;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.common.core.cache.old.CacheCallback;
import com.d2c.common.core.cache.old.CacheKey;
import com.d2c.common.core.cache.old.CacheTimerHandler;
import com.d2c.content.dto.PageContentDto;
import com.d2c.content.dto.PageDefineDto;
import com.d2c.content.model.Article;
import com.d2c.content.model.FieldContent;
import com.d2c.content.model.PageDefine.MODULE;
import com.d2c.content.model.PageDefine.TERMINAL;
import com.d2c.content.service.ArticleService;
import com.d2c.content.service.FieldContentService;
import com.d2c.content.service.PageContentService;
import com.d2c.content.service.PageDefineService;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.service.MemberInfoService;
import com.d2c.order.service.OrderItemService;
import com.d2c.product.model.Promotion;
import com.d2c.product.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("")
public class PageController extends BaseController {

    @Autowired
    private PageContentService pageContentService;
    @Autowired
    private PageDefineService pageDefineService;
    @Autowired
    private FieldContentService fieldContentService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private PromotionService promotionService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private MemberInfoService memberInfoService;

    /**
     * 首页 (view_module_page表中原本空的字段，重构压缩成json)
     *
     * @param model
     * @return
     */
    @RequestMapping(value = {"/index", "/"}, method = RequestMethod.GET)
    public String index(ModelMap model) {
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        Integer version = 2;
        TERMINAL terminal = TERMINAL.PC;
        if (isNormalDevice()) {
            terminal = TERMINAL.PC;
        } else if (isTabletDevice()) {
            terminal = TERMINAL.PC;
        } else if (isMobileDevice()) {
            terminal = TERMINAL.MOBILE;
            version = 1;
            Article article = articleService.findOneByName("waphome");
            if (article != null) {
                model.put("content", article.getMobileContent());
            }
        }
        PageDefineDto pageDefine = pageDefineService.findPageDefine(MODULE.HOMEPAGE, terminal, version);
        if (pageDefine != null) {
            String key = CacheKey.MODULEHOMEPAGEKEY + "_" + pageDefine.getId();
            PageContentDto homePage = CacheTimerHandler.getAndSetCacheValue(key, 2,
                    new CacheCallback<PageContentDto>() {
                        Long pageDefineId = pageDefine.getId();

                        @Override
                        public PageContentDto doExecute() {
                            return pageContentService.findOneByModule(pageDefineId, 1);
                        }
                    });
            // 新品首发
            PageResult<Promotion> newArrival = promotionService.findByTagId(1L, true, new PageModel());
            // 活动专区
            PageResult<Promotion> promotions = promotionService.findByTagId(2L, true, new PageModel());
            model.put("pageDefine", pageDefine);
            model.put("homePage", homePage);
            model.put("newArrival", newArrival);
            model.put("promotions", promotions);
        }
        return "index";
    }

    /**
     * 明星风范(view_module_page表中原本空的字段，重构压缩成json)
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/star", method = RequestMethod.GET)
    public String star(ModelMap model) {
        int version = 1;
        TERMINAL terminal = TERMINAL.PC;
        if (isNormalDevice()) {
            terminal = TERMINAL.PC;
        } else if (isTabletDevice()) {
            terminal = TERMINAL.PC;
        } else if (isMobileDevice()) {
            terminal = TERMINAL.MOBILE;
        }
        PageDefineDto pageDefine = pageDefineService.findPageDefine(MODULE.STAR, terminal, version);
        if (pageDefine != null) {
            PageContentDto homePage = pageContentService.findOneByModule(pageDefine.getId(), 1);
            // 瀑布流第一页
            PageResult<FieldContent> pager = fieldContentService.findByGroupAndPageId("block10", homePage.getId(),
                    new PageModel(1, 24));
            model.put("homePage", homePage);
            model.put("pageDefine", pageDefine);
            model.put("pager", pager);
            return "cms/star";
        }
        return "redirect:/index";
    }

    /**
     * 瀑布流分页请求
     *
     * @param model
     * @param page
     * @param id
     * @return
     */
    @RequestMapping(value = "/blocks", method = RequestMethod.GET)
    public String loadMoreBlocks(ModelMap model, PageModel page, Long id) {
        PageResult<FieldContent> pager = fieldContentService.findByGroupAndPageId("block10", id, page);
        model.put("pager", pager);
        return "";
    }

    /**
     * 首页预览
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/index/preview/{id}", method = RequestMethod.GET)
    public String previewIndex(ModelMap model, @PathVariable Long id) {
        int version = 2;
        TERMINAL terminal = TERMINAL.PC;
        if (isNormalDevice()) {
            terminal = TERMINAL.PC;
        } else if (isTabletDevice()) {
            terminal = TERMINAL.PC;
        } else if (isMobileDevice()) {
            terminal = TERMINAL.MOBILE;
            version = 1;
        }
        PageDefineDto pageDefine = pageDefineService.findPageDefine(MODULE.HOMEPAGE, terminal, version);
        PageContentDto homePage = pageContentService.findOneById(id);
        // 新品首发
        PageResult<Promotion> newArrival = promotionService.findByTagId(1L, null, new PageModel());
        model.put("homePage", homePage);
        model.put("pageDefine", pageDefine);
        model.put("newArrival", newArrival);
        return "index";
    }

    /**
     * 明星风范预览
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/star/preview/{id}", method = RequestMethod.GET)
    public String previewStar(ModelMap model, @PathVariable Long id) {
        int version = 1;
        TERMINAL terminal = TERMINAL.PC;
        if (isNormalDevice()) {
            terminal = TERMINAL.PC;
        } else if (isTabletDevice()) {
            terminal = TERMINAL.PC;
        } else if (isMobileDevice()) {
            terminal = TERMINAL.MOBILE;
        }
        PageDefineDto pageDefine = pageDefineService.findPageDefine(MODULE.STAR, terminal, version);
        PageContentDto homePage = pageContentService.findOneById(id);
        // 瀑布流第一页
        PageResult<FieldContent> pager = fieldContentService.findByGroupAndPageId("block10", homePage.getId(),
                new PageModel(1, 24));
        model.put("homePage", homePage);
        model.put("pageDefine", pageDefine);
        model.put("pager", pager);
        return "cms/star";
    }

    /**
     * 双11用户数据
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/myinfo1212", method = RequestMethod.GET)
    public String myInfo1111(ModelMap model, Long memberId) {
        MemberInfo memberInfo = null;
        try {
            memberInfo = this.getLoginMemberInfo();
            model.put("memberId", memberId);
            if (memberId == null) {
                memberId = memberInfo.getId();
            }
        } catch (NotLoginException e) {
            model.put("memberId", null);
        }
        MemberInfo thisMemberInfo = memberInfoService.findById(memberId);
        model.put("memberInfo", thisMemberInfo);
        JSONObject obj = orderItemService.findInfoByMemberId(memberId, null, null);
        model.put("info", obj);
        return "";
    }

}
