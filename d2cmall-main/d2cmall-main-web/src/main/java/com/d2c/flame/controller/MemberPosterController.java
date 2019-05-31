package com.d2c.flame.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONArray;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.member.dto.MemberDto;
import com.d2c.member.mongo.model.MemberPosterDO;
import com.d2c.member.mongo.model.MemberPosterLikeDO;
import com.d2c.member.mongo.services.CollectCardTaskService;
import com.d2c.member.mongo.services.MemberPosterLikeService;
import com.d2c.member.mongo.services.MemberPosterService;
import com.d2c.product.search.model.SearcherProduct;
import com.d2c.product.search.service.ProductSearcherQueryService;
import com.d2c.product.service.ProductService;
import com.d2c.util.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.*;

@Controller
@RequestMapping("/member/poster")
public class MemberPosterController extends BaseController {

    private final static Date deadline = DateUtil.str2second("2018-11-10 20:00:00");
    @Autowired
    private ProductService productService;
    @Autowired
    private CollectCardTaskService collectCardTaskService;
    @Reference
    private ProductSearcherQueryService productSearcherQueryService;
    @Reference
    private MemberPosterService memberPosterService;
    @Reference
    private MemberPosterLikeService memberPosterLikeService;

    /**
     * 问题列表
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/question", method = RequestMethod.GET)
    public String question(ModelMap model) {
        return "society/poster";
    }

    /**
     * 提交答案
     *
     * @param model
     * @param constellation
     * @param pattern
     * @param interest
     * @param style
     * @param category
     * @return
     */
    @RequestMapping(value = "/select", method = RequestMethod.POST)
    public String select(ModelMap model, Integer constellation, Integer pattern, Integer interest, Integer style,
                         Integer category) {
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        List<Map<String, Object>> choices = productService.findProductBySelect(constellation, pattern, interest, style,
                category);
        List<Map<String, Object>> selects = new ArrayList<>();
        if (choices.size() > 5) {
            Collections.shuffle(choices);
            selects.addAll(choices.subList(0, 5));
        } else {
            selects.addAll(choices);
        }
        result.put("choices", selects);
        if (selects.size() > 0) {
            List<String> productIds = new ArrayList<String>();
            for (Map<String, Object> choice : selects) {
                productIds.add(choice.get("id").toString());
            }
            List<SearcherProduct> products = productSearcherQueryService.findByIds(productIds, 1);
            JSONArray array = new JSONArray();
            products.forEach(item -> array.add(item.toJson()));
            result.put("products", array);
        }
        return "";
    }

    /**
     * 创建海报
     *
     * @param model
     * @param productId
     * @param pic
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(ModelMap model, Long productId, String pic) {
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        MemberDto memberDto = this.getLoginDto();
        if (memberDto.isD2c()) {
            memberDto.setUnionId(memberDto.getLoginCode());
        }
        MemberPosterDO oldPoster = memberPosterService.findByMemberId(memberDto.getUnionId());
        if (oldPoster != null) {
            result.put("posterId", oldPoster.getId());
            result.setStatus(-1);
            result.setMessage("您已经创建过海报啦！");
            return "";
        }
        MemberPosterDO newPoster = new MemberPosterDO();
        newPoster.setMemberId(memberDto.getUnionId());
        newPoster.setProductId(productId);
        newPoster.setPic(pic);
        newPoster.setCreateDate(new Date());
        newPoster.setNickName(memberDto.getNickname());
        newPoster.setHeadPic(memberDto.getHeadPic());
        newPoster = memberPosterService.insert(newPoster);
        result.put("memberPoster", newPoster);
        // 520不要这个
        // if (memberDto.isD2c()) {
        // collectCardTaskService.insert(new
        // CollectCardTaskDO(memberDto.getId(), TaskType.WARDROBE));
        // }
        return "";
    }

    /**
     * 我的海报
     *
     * @param model
     * @param posterId
     * @return
     */
    @RequestMapping(value = "/mine", method = RequestMethod.GET)
    public String mine(ModelMap model, String posterId) {
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        MemberDto memberDto = this.getLoginDto();
        if (memberDto.isD2c()) {
            memberDto.setUnionId(memberDto.getLoginCode());
        }
        MemberPosterDO oldPoster = null;
        if (posterId != null) {
            oldPoster = memberPosterService.findById(posterId);
        } else {
            oldPoster = memberPosterService.findByMemberId(memberDto.getUnionId());
        }
        if (oldPoster == null) {
            throw new BusinessException("该海报不存在！");
        }
        int mine = (oldPoster.getMemberId().equals(memberDto.getUnionId()) ? 1 : 0);
        result.put("memberPoster", oldPoster);
        result.put("mine", mine);
        int ranking = memberPosterService.countRanking(oldPoster.getLikeCount());
        result.put("ranking", ranking + 1);
        return "society/posterdetail";
    }

    /**
     * 点赞和点踩
     *
     * @param model
     * @param posterId
     * @param direct
     * @return
     */
    @RequestMapping(value = "/like", method = RequestMethod.POST)
    public String like(ModelMap model, String posterId, Integer direct) {
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        MemberDto memberDto = this.getLoginDto();
        if (memberDto.isD2c()) {
            memberDto.setUnionId(memberDto.getLoginCode());
        }
        MemberPosterDO oldPoster = memberPosterService.findById(posterId);
        if (oldPoster == null) {
            throw new BusinessException("该海报不存在！");
        }
        Date now = new Date();
        if (now.after(deadline)) {
            throw new BusinessException("该活动已结束！");
        }
        int count = memberPosterLikeService.countByDate(memberDto.getUnionId(), posterId, DateUtil.getStartOfDay(now),
                DateUtil.getEndOfDay(now));
        if (count > 0) {
            throw new BusinessException("您每天只有一次点赞或者点踩机会哦！");
        }
        MemberPosterLikeDO memberPosterLikeDO = new MemberPosterLikeDO();
        memberPosterLikeDO.setCreateDate(now);
        memberPosterLikeDO.setMemberId(memberDto.getUnionId());
        memberPosterLikeDO.setPostId(posterId);
        memberPosterLikeDO.setDirect(direct);
        memberPosterLikeDO.setNickName(memberDto.getNickname());
        memberPosterLikeDO.setHeadPic(memberDto.getHeadPic());
        memberPosterLikeService.insert(memberPosterLikeDO);
        return "";
    }

    /**
     * 海报排行榜
     *
     * @param model
     * @param page
     * @return
     */
    @RequestMapping(value = "/tops", method = RequestMethod.GET)
    public String tops(ModelMap model, PageModel page) {
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        PageResult<MemberPosterDO> pager = memberPosterService.findTops(page);
        result.put("tops", pager);
        return "";
    }

    /**
     * 点赞和点踩列表
     *
     * @param model
     * @param posterId
     * @param page
     * @return
     */
    @RequestMapping(value = "/likes", method = RequestMethod.GET)
    public String likes(ModelMap model, String posterId, PageModel page) {
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        PageResult<MemberPosterLikeDO> pager = memberPosterLikeService.findByPostId(posterId, page);
        result.put("likes", pager);
        return "";
    }

}
