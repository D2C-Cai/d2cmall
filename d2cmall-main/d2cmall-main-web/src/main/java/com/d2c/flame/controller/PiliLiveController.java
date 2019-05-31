package com.d2c.flame.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.logger.third.cloud.messages.CmdNtfMessage;
import com.d2c.logger.third.cloud.methods.Message;
import com.d2c.logger.third.cloud.methods.User;
import com.d2c.logger.third.cloud.models.TokenReslut;
import com.d2c.member.model.Designers;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.model.PiliLive;
import com.d2c.member.query.PiliLiveSearcher;
import com.d2c.member.service.DesignersService;
import com.d2c.member.service.PiliLiveService;
import com.d2c.member.third.qiniu.*;
import com.d2c.product.query.ProductSearcher;
import com.d2c.product.search.model.SearcherProduct;
import com.d2c.product.search.model.SearcherRecProduct;
import com.d2c.product.search.query.ProductProSearchQuery;
import com.d2c.product.search.service.ProductSearcherQueryService;
import com.d2c.product.service.BrandService;
import com.d2c.util.string.StringUtil;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/live")
public class PiliLiveController extends BaseController {

    @Autowired
    private PiliLiveService piliLiveService;
    @Autowired
    private DesignersService designersService;
    @Autowired
    private BrandService brandService;
    @Reference
    private ProductSearcherQueryService productSearcherQueryService;

    /**
     * 直播列表
     *
     * @param searcher
     * @param page
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(ModelMap model, PiliLiveSearcher searcher, PageModel page) {
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        searcher.setMark(1);
        PageResult<PiliLive> pager = piliLiveService.findBySearcher(searcher, page);
        result.put("lives", pager);
        return "";
    }

    /**
     * 直播状态
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String detail(ModelMap model, @PathVariable Long id) {
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        PiliLive piliLive = piliLiveService.findById(id);
        if (piliLive == null) {
            throw new BusinessException("直播不存在！");
        }
        JSONObject json = piliLive.toJson();
        result.put("live", json);
        return "society/pili_live";
    }

    /**
     * 创建直播
     *
     * @param title
     * @param cover
     * @return
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public String insert(ModelMap model, String title, String cover) {
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        MemberInfo memberInfo = this.getLoginMemberInfo();
        PiliLive old = piliLiveService.findLastOne(memberInfo.getId());
        if (old != null) {
            result.setStatus(-1);
            result.setMsg("您上一次有未完成的直播，是否继续？");
            result.put("old", old.toJson());
            return "";
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
            piliLive.setPushUrl(cli.RTMPPublishURL(Config.pushUrl, Config.hubName, streamId, 3600));
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
        return "";
    }

    /**
     * 关闭直播
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/close", method = RequestMethod.POST)
    public String close(ModelMap model, Long id) {
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
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
        return "";
    }

    /**
     * 游客进入直播间
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/in/{id}", method = RequestMethod.POST)
    public String in(ModelMap model, @PathVariable Long id) {
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        MemberInfo memberInfo = this.getLoginMemberInfo();
        PiliLive piliLive = piliLiveService.findById(id);
        if (piliLive == null) {
            throw new BusinessException("该直播不存在！");
        }
        Map<String, Object> realtime = piliLiveService.doIn(id, piliLive.getVrate(), memberInfo.getId(),
                memberInfo.getHeadPic());
        result.put("realtime", realtime);
        return "";
    }

    /**
     * 游客退出直播间
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/out/{id}", method = RequestMethod.POST)
    public String out(ModelMap model, @PathVariable Long id) {
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        MemberInfo memberInfo = this.getLoginMemberInfo();
        PiliLive piliLive = piliLiveService.findById(id);
        if (piliLive == null) {
            throw new BusinessException("该直播不存在！");
        }
        Map<String, Object> realtime = piliLiveService.doOut(id, memberInfo.getId(), memberInfo.getHeadPic());
        result.put("realtime", realtime);
        return "";
    }

    /**
     * 游客观看录播
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/watch/{id}", method = RequestMethod.POST)
    public String watch(ModelMap model, @PathVariable Long id) {
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        this.getLoginMemberInfo();
        PiliLive piliLive = piliLiveService.findById(id);
        if (piliLive == null) {
            throw new BusinessException("该直播不存在！");
        }
        piliLiveService.doWatch(id);
        return "society/live_watch";
    }

    /**
     * 删除直播
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String delete(ModelMap model, @PathVariable Long id) {
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        MemberInfo memberInfo = this.getLoginMemberInfo();
        piliLiveService.deleteByMemberId(id, memberInfo.getId());
        return "";
    }

    /**
     * 融云的token
     *
     * @return
     */
    @RequestMapping(value = "/token", method = RequestMethod.GET)
    public String token(ModelMap model) {
        MemberInfo member = this.getLoginMemberInfo();
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        try {
            TokenReslut tokenReslut = User.getToken(String.valueOf(member.getId()), member.getDisplayName(),
                    member.getHeadPic());
            result.put("token", tokenReslut.getToken());
        } catch (Exception e) {
            throw new BusinessException("token获取不成功！");
        }
        return "";
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
    public String recommendList(ModelMap model, Long liveId, String keyword, PageModel page) {
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
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
                result.put("products", array);
                return "";
            } else {
                // 官方账号
                if (StringUtil.isBlack(keyword)) {
                    PageResult<SearcherRecProduct> recPager = this.getRecProducts(liveId);
                    recPager.getList().forEach(item -> array.add(item.toJson()));
                    result.put("products", array);
                    return "";
                } else {
                    PageResult<SearcherProduct> pager = this.getAllProducts(keyword, page);
                    pager.getList().forEach(item -> array.add(item.toJson()));
                    result.put("products", array);
                    return "";
                }
            }
        } else {
            // 用户端
            PageResult<SearcherRecProduct> recPager = this.getRecProducts(liveId);
            if (recPager.getTotalCount() == 0 && piliLive.getMemberType() == 2) {
                PageResult<SearcherProduct> pager = this.getDesignerProducts(piliLive.getDesignerId(), null, page);
                pager.getList().forEach(item -> array.add(item.toJson()));
                result.put("products", array);
                return "";
            } else {
                recPager.getList().forEach(item -> array.add(item.toJson()));
                result.put("products", array);
                return "";
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
