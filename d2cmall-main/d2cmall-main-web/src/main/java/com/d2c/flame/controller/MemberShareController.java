package com.d2c.flame.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.ResponseResult;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.member.model.*;
import com.d2c.member.query.MemberShareCommentSearcher;
import com.d2c.member.query.MemberShareSearcher;
import com.d2c.member.search.model.SearcherMemberFollow;
import com.d2c.member.search.model.SearcherMemberLike;
import com.d2c.member.search.model.SearcherMemberShare;
import com.d2c.member.search.query.MemberShareSearchBean;
import com.d2c.member.search.service.MemberFollowSearcherService;
import com.d2c.member.search.service.MemberLikeSearcherService;
import com.d2c.member.search.service.MemberShareSearcherService;
import com.d2c.member.service.*;
import com.d2c.product.model.ProductShareRelation;
import com.d2c.product.search.model.SearcherProduct;
import com.d2c.product.search.service.ProductSearcherQueryService;
import com.d2c.product.service.ProductShareRelationService;
import org.apache.commons.lang.StringUtils;
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
@RequestMapping(value = {"/membershare", "/details"})
public class MemberShareController extends BaseController {

    @Autowired
    private MemberShareService memberShareService;
    @Autowired
    private MemberShareCommentService memberShareCommentService;
    @Autowired
    private MemberInfoService memberInfoService;
    @Autowired
    private MemberLikeService memberLikeService;
    @Reference
    private MemberFollowSearcherService memberFollowSearcherService;
    @Reference
    private MemberShareSearcherService memberShareSearcherService;
    @Autowired
    private MemberShareTagRelationService memberShareTagRelationService;
    @Reference
    private MemberLikeSearcherService memberLikeSearcherService;
    @Autowired
    private TopicService topicService;
    @Autowired
    private ProductShareRelationService memberShareProductRelationService;
    @Reference
    private ProductSearcherQueryService productSearcherQueryService;

    /**
     * 买家秀详情
     *
     * @param id
     * @param model
     * @param page
     * @return
     */
    @RequestMapping(value = {"/{id}", "/share/{id}"}, method = RequestMethod.GET)
    public String shareDetail(@PathVariable Long id, ModelMap model, PageModel page) {
        ResponseResult result = new ResponseResult();
        model.put("result", result);
        SearcherMemberShare memberShare = memberShareSearcherService.findById(id.toString());
        if (memberShare == null) {
            throw new BusinessException("买家秀不存在或已删除");
        }
        memberShareService.doWatch(id);
        SearcherMemberFollow searcherMemberFollow = null;
        try {
            MemberInfo memberInfo = this.getLoginMemberInfo();
            SearcherMemberLike sm = memberLikeSearcherService.findByMemberAndShareId(memberInfo.getId(),
                    memberShare.getId());
            if (sm != null && sm.getShareId() != null) {
                memberShare.setLiked(1);
            } else {
                memberShare.setLiked(0);
            }
            searcherMemberFollow = memberFollowSearcherService
                    .findById(memberInfo.getId() + "_" + memberShare.getMemberId());
        } catch (NotLoginException e) {
            memberShare.setLiked(0);
        }
        JSONObject obj = memberShare.toJson();
        JSONArray arryc = new JSONArray();
        PageResult<MemberShareComment> commentPager = new PageResult<>(new PageModel(1, 2));
        if (memberShare.getComments() != null && memberShare.getComments() > 0) {
            MemberShareCommentSearcher cs = new MemberShareCommentSearcher();
            cs.setSourceId(memberShare.getId());
            cs.setVerified(1);
            commentPager = memberShareCommentService.findBySearcher(page, cs);
            for (MemberShareComment memberShareComment : commentPager.getList()) {
                JSONObject comment = memberShareComment.toJson();
                arryc.add(comment);
            }
        }
        PageResult<SearcherMemberLike> likePager = memberLikeSearcherService.findByShareId(id, new PageModel(1, 10));
        JSONArray likeArray = new JSONArray();
        likePager.getList().forEach(like -> likeArray.add(like.toJson()));
        obj.put("likes", likeArray);
        obj.put("comments", arryc);
        obj.put("commentCount", commentPager.getTotalCount());
        obj.put("follow", searcherMemberFollow == null ? SearcherMemberFollow.FollowType.UNFOLLOW.getCode()
                : searcherMemberFollow.getFollowType());
        obj.put("productRelations", processProductR(id));
        model.put("memberShare", obj);
        return "society/member_share_detail";
    }

    /**
     * 处理关联商品
     *
     * @param shareId
     * @return
     */
    private JSONArray processProductR(Long shareId) {
        List<ProductShareRelation> productRs = memberShareProductRelationService.findByShareId(shareId, 9);
        List<String> productIds = new ArrayList<>();
        for (ProductShareRelation productR : productRs) {
            productIds.add(productR.getProductId().toString());
        }
        List<SearcherProduct> productR = productSearcherQueryService.findByIds(productIds, 1);
        JSONArray array = new JSONArray();
        for (SearcherProduct p : productR) {
            array.add(p.toJson());
        }
        return array;
    }

    /**
     * 买家秀列表
     *
     * @param page
     * @param model
     * @param searcher
     * @param template
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(PageModel page, ModelMap model, MemberShareSearcher searcher, String template) {
        searcher.setStatus(1);
        page.setPageSize(100);
        PageResult<MemberShare> pager = memberShareService.findBySearch(searcher, page);
        model.put("pager", pager);
        if (StringUtils.isNotBlank(template)) {
            return "page/" + template;
        } else {
            return "society/member_share_list";
        }
    }

    /**
     * 买家秀评论
     *
     * @param memberShareComment
     * @param model
     * @return
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public String insert(MemberShareComment memberShareComment, ModelMap model) {
        MemberInfo memberInfo = this.getLoginMemberInfo();
        memberShareComment.setMemberId(memberInfo.getId());
        memberShareComment.setHeadPic(memberInfo.getHeadPic());
        memberShareComment.setNickName(memberInfo.getDisplayName());
        this.checkSensitiveWords(memberShareComment.getContent());
        memberShareCommentService.insert(memberShareComment);
        return "";
    }

    /**
     * 我的个人主页
     *
     * @param page
     * @param memberId
     * @param model
     * @return
     */
    @RequestMapping(value = "/mine", method = RequestMethod.GET)
    public String mine(PageModel page, Long memberId, ModelMap model) {
        MemberShareSearcher searcher = new MemberShareSearcher();
        if (memberId == null || memberId <= 0) {
            MemberInfo memberInfo = this.getLoginMemberInfo();
            model.put("member", memberInfo);
            memberId = memberInfo.getId();
            // 我的
        } else {
            // 其他人的
            MemberInfo memberInfo = memberInfoService.findById(memberId);
            model.put("member", memberInfo);
            searcher.setStatus(1);
        }
        int sharesTotalCount = memberShareService.countByMemberId(memberId, searcher.getStatus());
        int fansTotalCount = memberFollowSearcherService.countByToId(memberId, null);
        int followsTotalCount = memberFollowSearcherService.countByFromId(memberId);
        model.put("sharesTotalCount", sharesTotalCount);
        model.put("fansTotalCount", fansTotalCount);
        model.put("followsTotalCount", followsTotalCount);
        searcher.setMemberId(memberId);
        PageResult<SearcherMemberShare> pager = memberShareSearcherService.search(searcher.initSearchQuery(), page);
        model.put("pager", pager);
        return "society/my_share";
    }

    /**
     * 点赞买家秀
     *
     * @param shareId
     * @param model
     * @return
     */
    @RequestMapping(value = "/like/insert/{shareId}", method = RequestMethod.POST)
    public String likeInsert(@PathVariable Long shareId, ModelMap model) {
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        MemberInfo memberInfo = this.getLoginMemberInfo();
        MemberLike memberLike = new MemberLike();
        memberLike.setMemberId(memberInfo.getId());
        memberLike.setNickName(memberInfo.getDisplayName());
        memberLike.setHeadPic(memberInfo.getHeadPic());
        MemberShare memberShare = memberShareService.findById(shareId);
        memberLike.setShareId(shareId);
        memberLike.setShareName(memberShare.getDescription());
        memberLike.setSharePic(memberShare.getPic());
        try {
            memberLikeService.insert(memberLike);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        return "";
    }

    /**
     * 话题和买家秀列表
     *
     * @param id
     * @param page
     * @param orderType
     * @param model
     * @return
     */
    @RequestMapping(value = "/topic/{id}", method = RequestMethod.GET)
    public String topicDetail(@PathVariable Long id, PageModel page, String orderType, ModelMap model) {
        ResponseResult result = new ResponseResult();
        model.put("result", result);
        MemberShareSearchBean search = new MemberShareSearchBean();
        search.setStatus(1);
        search.setTopicId(id);
        if ("new".equals(orderType)) {
            search.setSortFields(new String[]{"createDate"});
            search.setOrders(new SortOrder[]{SortOrder.DESC});
        } else {
            search.setSortFields(new String[]{"likes", "comments"});
            search.setOrders(new SortOrder[]{SortOrder.DESC, SortOrder.DESC});
        }
        PageResult<SearcherMemberShare> pager = memberShareSearcherService.search(search, page);
        List<SearcherMemberShare> memberShares = pager.getList();
        JSONArray array = new JSONArray();
        try {
            MemberInfo memberInfo = this.getLoginMemberInfo();
            String[] memberLikeids = new String[memberShares.size()];
            String[] memberFollowids = new String[memberShares.size()];
            for (int i = 0; i < memberShares.size(); i++) {
                memberLikeids[i] = memberInfo.getId() + "_" + memberShares.get(i).getId();
                memberFollowids[i] = memberInfo.getId() + "_" + memberShares.get(i).getMemberId();
            }
            Map<Long, SearcherMemberLike> memberLikes = memberLikeSearcherService.findByIds(memberLikeids);
            Map<Long, SearcherMemberFollow> memberFollows = memberFollowSearcherService.findByIds(memberFollowids);
            for (SearcherMemberShare memberShare : pager.getList()) {
                Long shareId = memberShare.getId();
                if (memberLikes.containsKey(shareId)) {
                    memberShare.setLiked(1);
                } else {
                    memberShare.setLiked(0);
                }
                JSONObject obj = memberShare.toJson();
                if (memberFollows.containsKey(memberShare.getMemberId())) {
                    obj.put("follow", memberFollows.get(memberShare.getMemberId()).getFollowType());
                } else {
                    obj.put("follow", SearcherMemberFollow.FollowType.UNFOLLOW.getCode());
                }
                array.add(obj);
            }
        } catch (NotLoginException e) {
            for (SearcherMemberShare memberShare : pager.getList()) {
                memberShare.setLiked(0);
                JSONObject obj = memberShare.toJson();
                obj.put("follow", SearcherMemberFollow.FollowType.UNFOLLOW.getCode());
                array.add(obj);
            }
        }
        Topic topic = topicService.findById(id);
        JSONObject obj = topic.toJson();
        obj.put("shareCount", pager.getTotalCount());
        result.put("topic", obj);
        result.putPage("memberShares", pager, array);
        return "society/member_share_topic";
    }

    /**
     * 运营后台功能 买家秀列表
     *
     * @param page
     * @param model
     * @param searcher
     * @return
     */
    @RequestMapping(value = "/mana/list", method = RequestMethod.GET)
    public String manaList(PageModel page, ModelMap model, MemberShareSearcher searcher) {
        MemberInfo memberInfo = this.getLoginMemberInfo();
        if (memberInfo.getType().intValue() != 3) {
            throw new BusinessException("你没有权限！");
        }
        if (searcher.getStatus() == null) {
            searcher.setStatus(0);
        }
        page.setPageSize(100);
        PageResult<MemberShare> pager = memberShareService.findBySearch(searcher, page);
        model.put("pager", pager);
        return "society/member_share_examine";
    }

    /**
     * 运营后台功能 审核买家秀
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/verify/{id}", method = RequestMethod.POST)
    public String manaVerify(@PathVariable("id") Long id, ModelMap model) {
        MemberInfo memberInfo = this.getLoginMemberInfo();
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        int success = memberShareService.doVerify(id, memberInfo.getLoginCode());
        if (success == 1) {
            MemberShare memberShare = memberShareService.findById(id);
            if (memberShare.getCancelDate() == null) {
                memberShareService.doSendShareMsg(memberShare, null);
            }
        }
        return "";
    }

    /**
     * 运营后台功能 取消审核买家秀
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/cancelVerify/{id}", method = RequestMethod.POST)
    public String manaCancel(@PathVariable("id") Long id, ModelMap model) {
        MemberInfo memberInfo = this.getLoginMemberInfo();
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        memberShareService.doCancelVerify(id, memberInfo.getLoginCode());
        return "";
    }

    /**
     * 运营后台功能 审核买家秀评论
     *
     * @param id
     * @param verified
     * @param model
     * @return
     */
    @RequestMapping(value = "/comment/verify/{id}", method = RequestMethod.POST)
    public String manaVerifyComment(@PathVariable("id") Long id, Integer verified, ModelMap model) {
        this.getLoginMemberInfo();
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        memberShareCommentService.updateStatus(verified, id, null);
        return "";
    }

    /**
     * 运营后台功能 审核买家秀评论
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/comment/list", method = RequestMethod.GET)
    public String manaComment(MemberShareCommentSearcher searcher, PageModel page, ModelMap model) {
        MemberInfo memberInfo = this.getLoginMemberInfo();
        if (memberInfo.getType().intValue() != 3) {
            throw new BusinessException("你没有权限。");
        }
        searcher.setSortCreateDate("DESC");
        PageResult<MemberShareComment> pager = memberShareCommentService.findBySearcher(page, searcher);
        List<JSONObject> list = new ArrayList<JSONObject>();
        for (MemberShareComment shareComment : pager.getList()) {
            JSONObject json = shareComment.toJson();
            MemberInfo member = memberInfoService.findById(shareComment.getMemberId());
            if (member != null) {
                json.put("blockShareComment",
                        member.getShield() != null && member.getShield().equals("[\"SHARECOMMENT\"]") ? 1 : 0);
            }
            list.add(json);
        }
        SuccessResponse result = new SuccessResponse(pager);
        result.put("list", list);
        model.put("result", result);
        return "society/share_comment_list";
    }

    /**
     * 运营后台功能 买家秀绑定标签
     *
     * @param shareId
     * @param tagId
     * @param model
     * @return
     */
    @RequestMapping(value = "/bind/tag", method = RequestMethod.POST)
    public String manaBindTag(Long shareId, Long tagId, ModelMap model) {
        SuccessResponse result = new SuccessResponse();
        this.getLoginMemberInfo();
        model.put("result", result);
        memberShareTagRelationService.insert(shareId, new Long[]{tagId});
        return "";
    }

    /**
     * 运营后台功能 买家秀禁言
     *
     * @param memberInfoId
     * @param status
     * @param model
     * @return
     */
    @RequestMapping(value = "/blockShareComment/{memberInfoId}", method = RequestMethod.POST)
    public String manaRestrict(@PathVariable("memberInfoId") Long memberInfoId, Integer status, ModelMap model) {
        SuccessResponse result = new SuccessResponse();
        this.getLoginMemberInfo();
        try {
            memberInfoService.doRestrictShareComment(memberInfoId, status);
            result.setMessage(status.intValue() == 1 ? "屏蔽成功！" : "取消屏蔽成功！");
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        model.put("result", result);
        return "";
    }

    /**
     * 运营后台功能 下架全部评论
     *
     * @param memberId
     * @param model
     * @return
     */
    @RequestMapping(value = "/comment/downall/{memberId}", method = RequestMethod.POST)
    public String manaDownComment(Long memberId, ModelMap model) {
        SuccessResponse result = new SuccessResponse();
        this.getLoginMemberInfo();
        MemberShareCommentSearcher searcher = new MemberShareCommentSearcher();
        searcher.setMemberId(memberId);
        searcher.setVerified(1);
        PageModel page = new PageModel();
        PageResult<MemberShareComment> pager = null;
        do {
            page.setP(pager == null ? 1 : page.getP() + 1);
            pager = memberShareCommentService.findBySearcher(page, searcher);
            for (MemberShareComment comment : pager.getList()) {
                memberShareCommentService.updateStatus(0, comment.getId(), comment.getSourceId());
            }
        } while (pager.isNext());
        model.put("result", result);
        return "";
    }

}
