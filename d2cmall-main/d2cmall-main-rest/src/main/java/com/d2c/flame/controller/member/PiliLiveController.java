package com.d2c.flame.controller.member;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.ResponseResult;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.content.model.SubModule;
import com.d2c.content.model.SubModule.ParentEnum;
import com.d2c.content.search.model.SearcherSection;
import com.d2c.content.search.query.SectionSearchBean;
import com.d2c.content.search.service.SectionSearcherService;
import com.d2c.content.service.SubModuleQueryService;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.flame.controller.order.PaymentController;
import com.d2c.logger.third.cloud.messages.CmdNtfMessage;
import com.d2c.logger.third.cloud.methods.Message;
import com.d2c.logger.third.cloud.methods.User;
import com.d2c.logger.third.cloud.models.TokenReslut;
import com.d2c.member.model.*;
import com.d2c.member.query.PiliLiveSearcher;
import com.d2c.member.service.*;
import com.d2c.member.third.qiniu.*;
import com.d2c.order.dto.PresentOrderDto;
import com.d2c.order.enums.PaymentTypeEnum;
import com.d2c.order.model.CouponDef;
import com.d2c.order.model.CouponDefGroup;
import com.d2c.order.model.PresentOrder;
import com.d2c.order.service.CouponDefGroupService;
import com.d2c.order.service.CouponDefService;
import com.d2c.order.service.PresentOrderService;
import com.d2c.product.model.Present;
import com.d2c.product.query.PresentSearcher;
import com.d2c.product.query.ProductSearcher;
import com.d2c.product.search.model.SearcherProduct;
import com.d2c.product.search.model.SearcherRecProduct;
import com.d2c.product.search.query.ProductProSearchQuery;
import com.d2c.product.search.service.ProductSearcherQueryService;
import com.d2c.product.search.service.ProductSearcherService;
import com.d2c.product.service.BrandService;
import com.d2c.product.service.PresentService;
import com.d2c.util.string.StringUtil;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 七牛直播
 *
 * @author Lain
 * @version 3.0
 */
@RestController
@RequestMapping(value = "/v3/api/live")
public class PiliLiveController extends BaseController {

    @Autowired
    private SubModuleQueryService subModuleQueryService;
    @Reference
    private SectionSearcherService sectionSearcherService;
    @Reference
    private ProductSearcherService productSearcherService;
    @Reference
    private ProductSearcherQueryService productSearcherQueryService;
    @Autowired
    private PiliLiveService piliLiveService;
    @Autowired
    private LiveRoomService liveRoomService;
    @Autowired
    private DesignersService designersService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private PresentService presentService;
    @Autowired
    private PresentOrderService presentOrderService;
    @Autowired
    private MemberInfoService memberInfoService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private CouponDefService couponDefService;
    @Autowired
    private CouponDefGroupService couponDefGroupService;
    @Autowired
    private PaymentController paymentController;

    /**
     * 直播的banner
     *
     * @return
     */
    @RequestMapping(value = "/banner", method = RequestMethod.GET)
    public ResponseResult banner() {
        ResponseResult result = new ResponseResult();
        String parentModule = ParentEnum.LIVE.name();
        SubModule subModule = null;
        List<SubModule> subModuleList = subModuleQueryService.findByParent(parentModule);
        for (SubModule s : subModuleList) {
            if (s.getStatus() == 1 && s.getIsDefault() == 1) {
                subModule = s;
                break;
            }
        }
        JSONArray array = new JSONArray();
        if (subModule != null) {
            SectionSearchBean searcher = new SectionSearchBean();
            searcher.setModuleId(subModule.getId());
            searcher.setVersion(subModule.getVersion());
            PageResult<SearcherSection> pager = sectionSearcherService.search(searcher, new PageModel());
            pager.getList().forEach(s -> array.add(s.toFixJson()));
        }
        result.put("content", array);
        return result;
    }

    /**
     * 直播列表
     *
     * @param searcher
     * @param page
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseResult list(PiliLiveSearcher searcher, PageModel page) {
        ResponseResult result = new ResponseResult();
        searcher.setMark(1);
        PageResult<PiliLive> pager = piliLiveService.findBySearcher(searcher, page);
        List<PiliLive> lives = pager.getList();
        JSONArray array = new JSONArray();
        for (PiliLive item : lives) {
            if (item.getStatus() == 4) {
                item.setVcount(piliLiveService.getLiveCount(item.getId()));
            }
            array.add(item.toJson());
        }
        result.putPage("lives", pager, array);
        return result;
    }

    /**
     * 直播状态
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseResult detail(@PathVariable Long id) {
        ResponseResult result = new ResponseResult();
        PiliLive piliLive = piliLiveService.findById(id);
        if (piliLive == null) {
            throw new BusinessException("直播不存在！");
        }
        JSONObject json = piliLive.toJson();
        result.put("live", json);
        return result;
    }

    /**
     * 创建直播
     *
     * @param title
     * @param cover
     * @return
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ResponseResult insert(String title, String cover) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        PiliLive old = piliLiveService.findLastOne(memberInfo.getId());
        if (old != null) {
            result.setStatus(-1);
            result.setMsg("您上一次有未完成的直播，是否继续？");
            result.put("old", old.toJson());
            return result;
        }
        if (StringUtil.isBlank(title)) {
            title = "主播很懒，什么都没有留下";
        }
        if (StringUtil.isBlank(cover)) {
            throw new BusinessException("必须上传一个封面！");
        }
        if (memberInfo.getType() != 2 && memberInfo.getType() != 3) {
            throw new BusinessException("您的账号不能创建直播！");
        }
        PiliLive piliLive = new PiliLive(memberInfo);
        // 创建流
        try {
            Client cli = new Client();
            Hub hub = cli.newHub(Config.hubName);
            String streamId = piliLive.getStreamId();
            hub.create(streamId);
            // RTMP推流地址
            piliLive.setPushUrl(cli.RTMPPublishURL(Config.pushUrl, Config.hubName, streamId, 7200));
            // RTMP直播地址
            piliLive.setRtmpUrl(cli.RTMPPlayURL(Config.rtmpUrl, Config.hubName, streamId));
            // HLS直播地址
            piliLive.setHlsUrl(cli.HLSPlayURL(Config.hlsUrl, Config.hubName, streamId));
            // HDL直播地址
            piliLive.setHdlUrl(cli.HDLPlayURL(Config.hdlUrl, Config.hubName, streamId));
            // 截图直播地址
            piliLive.setPicUrl(cli.SnapshotPlayURL(Config.picUrl, Config.hubName, streamId));
        } catch (PiliException e) {
            e.printStackTrace();
            throw new BusinessException("创建直播失败！");
        }
        if (memberInfo.getDesignerId() != null) {
            Designers designers = designersService.findById(memberInfo.getDesignerId());
            if (designers != null) {
                piliLive.setDesignerId(designers.getId());
                piliLive.setDesignerName(designers.getName());
            }
        }
        piliLive.setTitle(title);
        piliLive.setCover(cover);
        piliLive.setStatus(4);
        piliLive = piliLiveService.insert(piliLive);
        result.put("live", piliLive.toJson());
        return result;
    }

    /**
     * 关闭直播
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/close", method = RequestMethod.POST)
    public ResponseResult close(Long id) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        PiliLive piliLive = piliLiveService.findById(id);
        if (piliLive == null) {
            throw new BusinessException("该直播不存在！");
        }
        Stream stream;
        String fname = null;
        try {
            Client cli = new Client();
            Hub hub = cli.newHub(Config.hubName);
            stream = hub.get(piliLive.getStreamId());
            fname = stream.save(0, 0);
            fname = Config.replayUrl + fname;
        } catch (PiliException e) {
            e.printStackTrace();
        } finally {
            int success = piliLiveService.doClose(id, fname);
            if (success > 0) {
                try {
                    // 通知客户端关闭观众的直播
                    CmdNtfMessage message = new CmdNtfMessage(":QL", "OFF");
                    Message.publishChatroom(memberInfo.getId().toString(), new String[]{piliLive.getStreamId()},
                            message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * 游客进入直播间
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/in/{id}", method = RequestMethod.POST)
    public ResponseResult in(@PathVariable Long id) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        PiliLive piliLive = piliLiveService.findById(id);
        if (piliLive == null) {
            throw new BusinessException("该直播不存在！");
        }
        Map<String, Object> realtime = piliLiveService.doIn(id, piliLive.getVrate(), memberInfo.getId(),
                memberInfo.getHeadPic());
        result.put("realtime", realtime);
        return result;
    }

    /**
     * 游客退出直播间
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/out/{id}", method = RequestMethod.POST)
    public ResponseResult out(@PathVariable Long id) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        PiliLive piliLive = piliLiveService.findById(id);
        if (piliLive == null) {
            throw new BusinessException("该直播不存在！");
        }
        Map<String, Object> realtime = piliLiveService.doOut(id, memberInfo.getId(), memberInfo.getHeadPic());
        result.put("realtime", realtime);
        return result;
    }

    /**
     * 游客观看录播
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/watch/{id}", method = RequestMethod.POST)
    public ResponseResult watch(@PathVariable Long id) {
        ResponseResult result = new ResponseResult();
        this.getLoginMemberInfo();
        PiliLive piliLive = piliLiveService.findById(id);
        if (piliLive == null) {
            throw new BusinessException("该直播不存在！");
        }
        piliLiveService.doWatch(id);
        return result;
    }

    /**
     * 删除直播
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public ResponseResult delete(@PathVariable Long id) {
        ResponseResult responseResult = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        piliLiveService.deleteByMemberId(id, memberInfo.getId());
        return responseResult;
    }

    /**
     * 融云的token
     *
     * @return
     */
    @RequestMapping(value = "/token", method = RequestMethod.GET)
    public ResponseResult token() {
        MemberInfo member = this.getLoginMemberInfo();
        ResponseResult result = new ResponseResult();
        try {
            TokenReslut tokenReslut = User.getToken(String.valueOf(member.getId()), member.getDisplayName(),
                    member.getHeadPic());
            result.put("token", tokenReslut.getToken());
        } catch (Exception e) {
            throw new BusinessException("token获取不成功！");
        }
        return result;
    }

    /**
     * 优惠券列表
     *
     * @param liveId
     * @return
     */
    @RequestMapping(value = "/coupon", method = RequestMethod.GET)
    public ResponseResult couponList(Long liveId) {
        ResponseResult result = new ResponseResult();
        PiliLive piliLive = piliLiveService.findById(liveId);
        if (piliLive == null) {
            throw new BusinessException("该直播不存在！");
        }
        LiveRoom liveRoom = liveRoomService.findByMemberId(piliLive.getMemberId());
        if (liveRoom != null && liveRoom.getCouponId() != null) {
            CouponDef couponDef = couponDefService.findById(liveRoom.getCouponId());
            result.put("coupon", couponDef.toJson());
        }
        if (liveRoom != null && liveRoom.getCouponGroupId() != null) {
            CouponDefGroup couponDefGroup = couponDefGroupService.findById(liveRoom.getCouponGroupId());
            result.put("couponGroup", couponDefGroup.toJson());
        }
        return result;
    }

    /**
     * 礼物列表
     *
     * @return
     */
    @RequestMapping(value = "/present/list", method = RequestMethod.GET)
    public ResponseResult presentList() {
        ResponseResult result = new ResponseResult();
        PresentSearcher presentSearcher = new PresentSearcher();
        List<Present> presents = presentService.findListBySearcher(presentSearcher, new PageModel());
        JSONArray array = new JSONArray();
        presents.forEach(item -> array.add(item.toJson()));
        result.put("presents", array);
        return result;
    }

    /**
     * 送出礼物
     *
     * @param liveId
     * @param presentId
     * @param quantity
     * @param toMemberId
     * @param password
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/pay/present", method = RequestMethod.POST)
    public ResponseResult payPresent(Long liveId, Long presentId, Integer quantity, Long toMemberId, String password,
                                     Model model) throws Exception {
        ResponseResult result = new ResponseResult();
        MemberInfo member = this.getLoginMemberInfo();
        Present present = presentService.findById(presentId);
        if (present == null || present.getStatus() != 1) {
            throw new BusinessException("该礼物已下架，可以选择其他礼物哦！");
        }
        if (quantity == null || quantity <= 0) {
            throw new BusinessException("礼物数量异常！");
        }
        PiliLive piliLive = piliLiveService.findById(liveId);
        if (piliLive == null) {
            throw new BusinessException("该直播不存在！");
        }
        MemberInfo toMemberInfo = memberInfoService.findById(toMemberId);
        if (toMemberInfo == null) {
            throw new BusinessException("送礼用户不存在！");
        }
        // 创建礼物订单
        PresentOrder presentOrder = new PresentOrder(member, toMemberInfo);
        presentOrder.setLiveId(liveId);
        presentOrder.setProductId(present.getId());
        presentOrder.setProductName(present.getName());
        presentOrder.setProductPrice(present.getPrice());
        presentOrder.setQuantity(quantity);
        presentOrder.setTotalAmount(present.getPrice().multiply(new BigDecimal(quantity)));
        presentOrder.setRatio(piliLive.getRatio());
        // 目前只做钱包支付
        presentOrder.setPaymentType(PaymentTypeEnum.WALLET.getCode());
        presentOrder = presentOrderService.createPresentOrder(presentOrder);
        if (presentOrder != null && presentOrder.getId() != null) {
            PresentOrderDto dto = new PresentOrderDto();
            BeanUtils.copyProperties(presentOrder, dto);
            paymentController.doPayment(model, dto, PaymentTypeEnum.WALLET.name(), null, password, null, null);
        }
        Account account = accountService.findByMemberId(member.getId());
        result.put("account", account.toJson());
        return result;
    }

    /**
     * 直播推荐商品
     *
     * @param productId
     * @param liveId
     * @param status
     * @return
     */
    @RequestMapping(value = "/do/recommend", method = RequestMethod.POST)
    public ResponseResult recommend(Long productId, Long liveId, Integer status) {
        ResponseResult result = new ResponseResult();
        SearcherProduct product = productSearcherQueryService.findById(String.valueOf(productId));
        if (product == null) {
            throw new BusinessException("推荐商品不成功！");
        }
        SearcherRecProduct searcherProduct = productSearcherQueryService.findRecById(liveId, productId);
        if (1 == status) {
            product.setUpMarketDate(new Date());
            productSearcherService.insertRec(product, liveId, searcherProduct != null ? searcherProduct.getNo() : null);
        }
        productSearcherService.doRecommend(productId, liveId, status);
        return result;
    }

    /**
     * 推荐商品列表
     *
     * @param liveId
     * @param keyword
     * @param page
     * @return
     */
    @RequestMapping(value = "/recommend/list", method = RequestMethod.GET)
    public ResponseResult recommendList(Long liveId, String keyword, PageModel page) {
        ResponseResult result = new ResponseResult();
        PiliLive piliLive = piliLiveService.findById(liveId);
        MemberInfo memberInfo = this.getLoginMemberInfo();
        JSONArray array = new JSONArray();
        if (memberInfo.getId().equals(piliLive.getMemberId())) {
            // 主播端
            if (memberInfo.getType() == 2) {
                // 设计师
                PageResult<SearcherRecProduct> recPager = this.getRecProducts(liveId);
                PageResult<SearcherProduct> pager = this.getDesignerProducts(piliLive.getDesignerId(), keyword, page);
                List<Long> recProductIds = new ArrayList<>();
                recPager.getList().forEach(item -> recProductIds.add(item.getId()));
                for (SearcherProduct product : pager.getList()) {
                    JSONObject jsonObject = product.toJson();
                    jsonObject.put("isRec", recProductIds.contains(product.getId()));
                    array.add(jsonObject);
                }
                result.putPage("products", pager, array);
                return result;
            } else {
                // 官方账号
                if (StringUtil.isBlack(keyword)) {
                    PageResult<SearcherRecProduct> recPager = this.getRecProducts(liveId);
                    recPager.getList().forEach(item -> array.add(item.toJson()));
                    result.putPage("products", recPager, array);
                    return result;
                } else {
                    PageResult<SearcherProduct> pager = this.getAllProducts(keyword, page);
                    pager.getList().forEach(item -> array.add(item.toJson()));
                    result.putPage("products", pager, array);
                    return result;
                }
            }
        } else {
            // 用户端
            PageResult<SearcherRecProduct> recPager = this.getRecProducts(liveId);
            if (recPager.getTotalCount() == 0 && piliLive.getMemberType() == 2) {
                PageResult<SearcherProduct> pager = this.getDesignerProducts(piliLive.getDesignerId(), null, page);
                pager.getList().forEach(item -> array.add(item.toJson()));
                result.putPage("products", pager, array);
                return result;
            } else {
                recPager.getList().forEach(item -> array.add(item.toJson()));
                result.putPage("products", recPager, array);
                return result;
            }
        }
    }

    private PageResult<SearcherRecProduct> getRecProducts(Long liveId) {
        PageModel page = new PageModel(1, 50);
        ProductProSearchQuery searcher = new ProductProSearchQuery();
        searcher.setStatus(1);
        searcher.setStore(1);
        searcher.setSortFields(new String[]{"recommend", "recDate", "upMarketDate"});
        searcher.setOrders(new SortOrder[]{SortOrder.DESC, SortOrder.DESC, SortOrder.DESC});
        searcher.setLiveId(liveId);
        PageResult<SearcherRecProduct> recPager = productSearcherQueryService.searchRec(searcher, page);
        return recPager;
    }

    private PageResult<SearcherProduct> getDesignerProducts(Long designerId, String keyword, PageModel page) {
        PageResult<SearcherProduct> pager = new PageResult<>();
        ProductSearcher searcher = new ProductSearcher();
        searcher.setKeywords(keyword);
        ProductProSearchQuery searcherBean = searcher.convertSearchQuery();
        searcherBean.setStore(1);
        List<Long> designerIds = brandService.findIdsByDesignersId(designerId);
        if (designerIds.size() > 0) {
            searcherBean.setDesignerIds(designerIds);
            pager = productSearcherQueryService.search(searcherBean, page);
            return pager;
        }
        return pager;
    }

    private PageResult<SearcherProduct> getAllProducts(String keyword, PageModel page) {
        PageResult<SearcherProduct> pager = new PageResult<>();
        ProductSearcher searcher = new ProductSearcher();
        searcher.setKeywords(keyword);
        ProductProSearchQuery searcherBean = searcher.convertSearchQuery();
        searcherBean.setStatus(1);
        searcherBean.setStore(1);
        pager = productSearcherQueryService.search(searcherBean, page);
        return pager;
    }

}
