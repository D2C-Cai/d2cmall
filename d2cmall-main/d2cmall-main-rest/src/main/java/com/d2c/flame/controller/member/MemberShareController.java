package com.d2c.flame.controller.member;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.ResponseResult;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.content.model.SubModule;
import com.d2c.content.model.SubModule.ParentEnum;
import com.d2c.content.search.model.SearcherSection;
import com.d2c.content.search.query.SectionSearchBean;
import com.d2c.content.search.service.SectionSearcherService;
import com.d2c.content.service.SubModuleQueryService;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.logger.model.UpyunTask;
import com.d2c.logger.model.UpyunTask.SourceType;
import com.d2c.logger.service.UpyunTaskService;
import com.d2c.member.enums.DeviceTypeEnum;
import com.d2c.member.enums.RestrictTypeEnum;
import com.d2c.member.model.*;
import com.d2c.member.model.MemberShare.ResourceType;
import com.d2c.member.query.MemberShareCommentSearcher;
import com.d2c.member.query.MemberShareSearcher;
import com.d2c.member.search.model.*;
import com.d2c.member.search.query.TopicSearchBean;
import com.d2c.member.search.service.*;
import com.d2c.member.search.support.MemberShareHelp;
import com.d2c.member.service.*;
import com.d2c.order.service.OrderItemService;
import com.d2c.product.model.Brand;
import com.d2c.product.model.ProductShareRelation;
import com.d2c.product.query.ProductSearcher;
import com.d2c.product.search.model.SearcherProduct;
import com.d2c.product.search.query.ProductProSearchQuery;
import com.d2c.product.search.service.ProductSearcherQueryService;
import com.d2c.product.service.BrandService;
import com.d2c.product.service.ProductShareRelationService;
import com.d2c.util.string.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 买家秀
 *
 * @author Lain
 * @version 3.0
 */
@RestController
@RequestMapping(value = "/v3/api/share")
public class MemberShareController extends BaseController {

    @Autowired
    private TopicService topicService;
    @Reference
    private TopicSearcherService topicSearcherService;
    @Autowired
    private MemberShareService memberShareService;
    @Reference
    private MemberShareSearcherService memberShareSearcherService;
    @Reference
    private MemberShareTagSearcherService memberShareTagSearcherService;
    @Autowired
    private MemberShareTagRelationService memberShareTagRelationService;
    @Autowired
    private MemberShareCommentService memberShareCommentService;
    @Autowired
    private ProductShareRelationService productShareRelationService;
    @Autowired
    private MemberLikeService memberLikeService;
    @Reference
    private MemberLikeSearcherService memberLikeSearcherService;
    @Reference
    private MemberFollowSearcherService memberFollowSearcherService;
    @Reference
    private ProductSearcherQueryService productSearcherQueryService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private SubModuleQueryService subModuleQueryService;
    @Reference
    private SectionSearcherService sectionSearcherService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private UpyunTaskService upyunTaskService;
    @Autowired
    private PiliLiveService piliLiveService;

    /**
     * 头部数据
     *
     * @return
     */
    @RequestMapping(value = "/head/info", method = RequestMethod.GET)
    public ResponseResult headInfo() {
        ResponseResult result = new ResponseResult();
        // banner
        String parentModule = ParentEnum.SQUARE.name();
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
        // 话题
        TopicSearchBean searcher = new TopicSearchBean();
        searcher.setStatus(1);
        PageResult<SearcherTopic> pager = topicSearcherService.search(searcher, new PageModel(1, 3));
        JSONArray topicArray = new JSONArray();
        pager.getList().forEach(item -> topicArray.add(item.toJson()));
        result.put("topics", topicArray);
        // 热门
        JSONObject hot = new JSONObject();
        PageModel hotPage = new PageModel();
        hotPage.setPageSize(3);
        for (String type : new String[]{"designer", "member"}) {
            JSONArray hotArray = new JSONArray();
            List<MemberShareHelp> list = memberShareSearcherService.findHotMember(type, hotPage);
            list.forEach(item -> hotArray.add(item.toJson()));
            hot.put("hot" + (type.substring(0, 1).toUpperCase() + type.substring(1, type.length())), hotArray);
        }
        for (String type : new String[]{"video", "pic"}) {
            JSONArray hotArray = new JSONArray();
            PageResult<SearcherMemberShare> sharePager = memberShareSearcherService.findHotShare(type, hotPage);
            List<SearcherMemberShare> list = sharePager.getList();
            list.forEach(item -> hotArray.add(item.toJson()));
            hot.put("hot" + (type.substring(0, 1).toUpperCase() + type.substring(1, type.length())), hotArray);
        }
        JSONArray hotArray = new JSONArray();
        PageResult<PiliLive> livePager = piliLiveService.findHot(hotPage);
        List<PiliLive> list = livePager.getList();
        list.forEach(item -> hotArray.add(item.toJson()));
        hot.put("hotLives", hotArray);
        result.put("hot", hot);
        return result;
    }

    /**
     * 活跃用户
     *
     * @return
     */
    @RequestMapping(value = "/activemember", method = RequestMethod.GET)
    public ResponseResult active() {
        ResponseResult result = new ResponseResult();
        List<MemberShareHelp> list = memberShareSearcherService.findMemberOrderByCount(null, 50);
        JSONArray array = new JSONArray();
        try {
            MemberInfo memberInfo = this.getLoginMemberInfo();
            String[] memberFollowids = new String[list.size()];
            for (int i = 0; i < memberFollowids.length; i++) {
                memberFollowids[i] = memberInfo.getId() + "_" + list.get(i).getMemberId();
            }
            Map<Long, SearcherMemberFollow> memberFollows = memberFollowSearcherService.findByIds(memberFollowids);
            for (MemberShareHelp talent : list) {
                if (memberFollows.containsKey(talent.getMemberId())) {
                    if (memberFollows.get(talent.getMemberId()).getFollowType() != 0) {
                        continue;
                    }
                }
                array.add(talent.toJson());
                if (array.size() >= 5) {
                    break;
                }
            }
        } catch (NotLoginException e) {
            for (MemberShareHelp talent : list) {
                array.add(talent.toJson());
                if (array.size() >= 5) {
                    break;
                }
            }
        }
        result.put("activeMember", array);
        return result;
    }

    /**
     * 买家秀标签列表
     *
     * @return
     */
    @RequestMapping(value = "/tag/list", method = RequestMethod.GET)
    public ResponseResult tagList() {
        ResponseResult result = new ResponseResult();
        PageModel page = new PageModel();
        PageResult<SearcherMemberShareTag> pager = memberShareTagSearcherService.search(page);
        JSONArray array = new JSONArray();
        pager.getList().forEach(item -> array.add(item.toJson()));
        result.put("tagList", array);
        return result;
    }

    /**
     * 买家秀列表
     *
     * @param page
     * @param searcher
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseResult shareList(PageModel page, MemberShareSearcher searcher) {
        ResponseResult result = new ResponseResult();
        searcher.setStatus(1);
        Long memberInfoId = null;
        try {
            MemberInfo memberInfo = this.getLoginMemberInfo();
            searcher.setMemberId(memberInfo.getId());
            searcher.setAllList(true);
            memberInfoId = memberInfo.getId();
        } catch (Exception e) {
        }
        PageResult<SearcherMemberShare> pager = memberShareSearcherService.search(searcher.initSearchQuery(), page);
        JSONArray array = new JSONArray();
        List<SearcherMemberShare> memberShares = pager.getList();
        if (memberInfoId != null) {
            String[] memberLikeids = new String[memberShares.size()];
            String[] memberFollowids = new String[memberShares.size()];
            for (int i = 0; i < memberShares.size(); i++) {
                memberLikeids[i] = memberInfoId + "_" + memberShares.get(i).getId();
                memberFollowids[i] = memberInfoId + "_" + memberShares.get(i).getMemberId();
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
        } else {
            for (SearcherMemberShare memberShare : pager.getList()) {
                memberShare.setLiked(0);
                JSONObject obj = memberShare.toJson();
                obj.put("follow", SearcherMemberFollow.FollowType.UNFOLLOW.getCode());
                array.add(obj);
            }
        }
        result.putPage("memberShares", pager, array);
        return result;
    }

    /**
     * 点赞买家秀的会员
     *
     * @param shareId
     * @param page
     * @return
     */
    @RequestMapping(value = "/like/list/{shareId}", method = RequestMethod.GET)
    public ResponseResult shareLikeList(@PathVariable Long shareId, PageModel page) {
        ResponseResult result = new ResponseResult();
        PageResult<MemberLike> pager = memberLikeService.findByShareId(shareId, page);
        List<MemberLike> list = pager.getList();
        JSONArray array = new JSONArray();
        try {
            MemberInfo memberInfo = this.getLoginMemberInfo();
            String[] memberFollowids = new String[list.size()];
            for (int i = page.getStartNumber(); i < list.size(); i++) {
                memberFollowids[i] = memberInfo.getId() + "_" + list.get(i).getMemberId();
            }
            Map<Long, SearcherMemberFollow> memberFollows = memberFollowSearcherService.findByIds(memberFollowids);
            for (MemberLike memberLike : list) {
                JSONObject obj = memberLike.toJson();
                this.findLastOne(obj, memberLike.getMemberId());
                if (memberFollows.containsKey(memberLike.getMemberId())) {
                    obj.put("follow", memberFollows.get(memberLike.getMemberId()).getFollowType());
                } else {
                    obj.put("follow", SearcherMemberFollow.FollowType.UNFOLLOW.getCode());
                }
                array.add(obj);
            }
        } catch (NotLoginException e) {
            for (MemberLike memberLike : list) {
                JSONObject obj = memberLike.toJson();
                this.findLastOne(obj, memberLike.getMemberId());
                obj.put("follow", SearcherMemberFollow.FollowType.UNFOLLOW.getCode());
                array.add(obj);
            }
        }
        result.putPage("likes", pager, array);
        return result;
    }

    /**
     * 查询用户最新一条买家秀
     *
     * @param obj
     * @param memberId
     */
    private void findLastOne(JSONObject obj, Long memberId) {
        SearcherMemberShare share = memberShareSearcherService.findLastedByMemberId(memberId);
        if (StringUtil.isNotBlank(share.getDescription())) {
            obj.put("memberShare", share.getDescription());
        } else if (StringUtils.isNotBlank(share.getPic())) {
            obj.put("memberShare", "发布了图片");
        } else if (StringUtil.isNotBlank(share.getVideo())) {
            obj.put("memberShare", "发布了视频");
        } else {
            obj.put("memberShare", "暂无动态");
        }
    }

    /**
     * 点赞买家秀
     *
     * @param shareId
     * @return
     */
    @RequestMapping(value = "/like/insert/{shareId}", method = RequestMethod.POST)
    public ResponseResult likeInsert(@PathVariable Long shareId) {
        ResponseResult result = new ResponseResult();
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
            MemberLike like = memberLikeService.insert(memberLike);
            if (like != null) {
                if (!memberInfo.getId().equals(memberShare.getMemberId())) {
                    memberLikeService.doSendLikeMsg(like, memberShare, getLoginIp());
                }
            }
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        return result;
    }

    /**
     * 取消点赞买家秀
     *
     * @param shareId
     * @return
     */
    @RequestMapping(value = "/like/delete/{shareId}", method = RequestMethod.POST)
    public ResponseResult likeDelete(@PathVariable Long shareId) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        memberLikeService.delete(memberInfo.getId(), shareId);
        return result;
    }

    /**
     * 买家秀详情
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseResult shareDetail(@PathVariable Long id) {
        ResponseResult result = new ResponseResult();
        SearcherMemberShare memberShare = memberShareSearcherService.findById(id.toString());
        if (memberShare == null) {
            result.setStatus(-2);
            result.setMsg("买家秀不存在或已删除！");
            return result;
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
            cs.setHot(1);
            commentPager = memberShareCommentService.findBySearcher(new PageModel(1, 2), cs);
            commentPager.getList().forEach(item -> arryc.add(item.toJson()));
        }
        PageResult<SearcherMemberLike> likePager = memberLikeSearcherService.findByShareId(id, new PageModel(1, 10));
        JSONArray likeArray = new JSONArray();
        likePager.getList().forEach(like -> likeArray.add(like.toJson()));
        obj.put("likes", likeArray);
        obj.put("comments", arryc);
        obj.put("commentCount", memberShare.getComments());
        obj.put("productRelations", this.processProductR(id));
        obj.put("follow", searcherMemberFollow == null ? SearcherMemberFollow.FollowType.UNFOLLOW.getCode()
                : searcherMemberFollow.getFollowType());
        result.put("memberShare", obj);
        return result;
    }

    /**
     * 处理关联商品
     *
     * @param shareId
     * @return
     */
    private JSONArray processProductR(Long shareId) {
        List<ProductShareRelation> productRs = productShareRelationService.findByShareId(shareId, 9);
        List<String> productIds = new ArrayList<>();
        productRs.forEach(item -> productIds.add(item.getProductId().toString()));
        List<SearcherProduct> productR = productSearcherQueryService.findByIds(productIds, null);
        JSONArray array = new JSONArray();
        productR.forEach(item -> array.add(item.toJson()));
        return array;
    }

    /**
     * 新增买家秀
     *
     * @param memberShare
     * @param taskIds
     * @param productIds
     * @param commentId
     * @return
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ResponseResult shareInsert(MemberShare memberShare, String taskIds, String productIds, Long commentId) {
        ResponseResult result = new ResponseResult();
        MemberInfo member = this.getLoginMemberInfo();
        if (member != null && member.getDesignerId() != null && member.getDesignerId() > 0) {
            List<Brand> brands = brandService.findByDesignersId(member.getDesignerId(), new Integer[]{1});
            memberShare.setStatus(1);
            memberShare.setVerifyDate(new Date());
            memberShare.setRole(1);
            if (brands.size() > 0) {
                memberShare.setDesignerId(brands.get(0).getId());
            }
        } else if (member != null && member.getType().intValue() == 3) {
            memberShare.setRole(2);
            memberShare.setStatus(1);
            memberShare.setVerifyDate(new Date());
        } else if (member != null && member.getType().intValue() == 5) {
            memberShare.setRole(5);
            memberShare.setStatus(1);
            memberShare.setVerifyDate(new Date());
        }
        memberShare.setMemberId(member.getId());
        memberShare.setHeadPic(member.getHeadPic());
        memberShare.setNickname(member.getDisplayName());
        memberShare.setName(member.getLoginCode());
        memberShare.setMobile(member.getMobile());
        memberShare.setEmail(member.getEmail());
        memberShare.setWatched(0L);
        if (StringUtils.isNotBlank(memberShare.getVideo())) {
            memberShare.setResourceType(ResourceType.video.toString());
        } else {
            memberShare.setResourceType(ResourceType.pic.toString());
        }
        if (StringUtils.isNotBlank(memberShare.getDescription())) {
            this.checkSensitiveWords(memberShare.getDescription());
        }
        if (memberShare.getTopicId() != null) {
            Topic topic = topicService.findById(memberShare.getTopicId());
            memberShare.setTopicName(topic != null ? topic.getTitle() : null);
        }
        if (memberShare.getDesignerId() == null && StringUtils.isNotBlank(productIds)) {
            String[] productStr = productIds.split(",");
            SearcherProduct product = productSearcherQueryService.findById(productStr[0]);
            if (product != null) {
                memberShare.setDesignerId(product.getDesignerId());
            }
        }
        memberShare = memberShareService.insert(memberShare);
        if (StringUtils.isNotBlank(taskIds) && commentId != null) {
            // 评论发布的买家秀
            commentService.findById(commentId);
            this.processTaskIds(taskIds, memberShare.getId(), commentId);
        } else if (StringUtils.isNotBlank(taskIds) && memberShare.getId() != null) {
            // 短视频转码任务
            this.processTaskIds(taskIds, memberShare.getId(), null);
        }
        result.put("shareId", memberShare.getId());
        result.put("topName", memberShare.getTopicName());
        List<Long> tagIds = new ArrayList<>();
        if (memberShare.getRole() == 1 && memberShare.getStatus() == 1) {
            SearcherMemberShareTag tag = memberShareTagSearcherService.findByCode("bigtalk");
            if (tag != null) {
                tagIds.add(tag.getId());
            }
        }
        if (memberShare.getRole() == 5 && memberShare.getStatus() == 1) {
            SearcherMemberShareTag tag = memberShareTagSearcherService.findByCode("expert");
            if (tag != null) {
                tagIds.add(tag.getId());
            }
        }
        if (ResourceType.video.toString().equals(memberShare.getResourceType())) {
            SearcherMemberShareTag tag = memberShareTagSearcherService.findByCode("smallvideo");
            if (tag != null) {
                tagIds.add(tag.getId());
            }
        }
        memberShareTagRelationService.insert(memberShare.getId(), tagIds.toArray(new Long[tagIds.size()]));
        if (StringUtils.isNoneBlank(productIds)) {
            this.processProductRelation(productIds, memberShare.getId());
        }
        // 发送推送消息
        if (memberShare.getStatus().intValue() == 1 && StringUtils.isNoneBlank(memberShare.getPic())) {
            memberShareService.doSendShareMsg(memberShare, getLoginIp());
        }
        return result;
    }

    /**
     * 处理短视频任务
     *
     * @param taskIds
     * @param shareId
     * @param commentId
     */
    private void processTaskIds(String taskIds, Long shareId, Long commentId) {
        String[] taskIdss = taskIds.split(",");
        for (String taskId : taskIdss) {
            UpyunTask upyunTask = new UpyunTask();
            upyunTask.setTaskIds(taskId);
            upyunTask.setStatus(0);
            if (commentId != null) {
                upyunTask.setSourceType(SourceType.COMMENT.toString());
                upyunTask.setSourceId(commentId);
                commentService.updateShareId(commentId, shareId);
            } else {
                upyunTask.setSourceType(SourceType.SHARE.toString());
                upyunTask.setSourceId(shareId);
            }
            upyunTask = upyunTaskService.insert(upyunTask);
            if (upyunTask.getStatus() == 1 && upyunTask.getVideo() != null) {
                memberShareService.updateVideoById(shareId, upyunTask.getVideo());
            }
            if (upyunTask.getStatus() == 1 && upyunTask.getPic() != null) {
                memberShareService.updatePicById(shareId, upyunTask.getPic());
            }
        }
    }

    /**
     * 处理买家秀和商品关联
     *
     * @param productIdStr
     * @param shareId
     */
    private void processProductRelation(String productIdStr, Long shareId) {
        String[] productIds = productIdStr.split(",");
        for (String productId : productIds) {
            if (StringUtils.isNoneBlank(productId)) {
                productShareRelationService.doReplace(new ProductShareRelation(Long.parseLong(productId), shareId));
            }
        }
        if (StringUtils.isNotBlank(productIdStr)) {
            SearcherMemberShare sms = new SearcherMemberShare();
            sms.setId(shareId);
            sms.setProductIds(productIdStr);
            memberShareSearcherService.update(sms);
        }
    }

    /**
     * 更新买家秀图片
     *
     * @param pic
     * @param id
     * @return
     */
    @RequestMapping(value = "/update/pic", method = RequestMethod.POST)
    public ResponseResult updatePic(String pic, Long id) {
        this.getLoginMemberInfo();
        ResponseResult result = new ResponseResult();
        if (StringUtils.isBlank(pic)) {
            throw new BusinessException("买家秀图片不能为空！");
        }
        int success = memberShareService.updatePicById(id, pic);
        // 发送推送消息
        if (success > 0) {
            MemberShare memberShare = memberShareService.findById(id);
            if (memberShare.getStatus().intValue() == 1) {
                memberShareService.doSendShareMsg(memberShare, getLoginIp());
            }
        }
        return result;
    }

    /**
     * 买家秀删除
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ResponseResult shareDelete(@PathVariable Long id) {
        this.getLoginMemberInfo();
        ResponseResult result = new ResponseResult();
        int success = memberShareService.delete(id);
        if (success <= 0) {
            throw new BusinessException("删除不成功！");
        }
        return result;
    }

    /**
     * 买家秀点赞的头像
     *
     * @param shareId
     * @return
     */
    @RequestMapping(value = "/like/{shareId}", method = RequestMethod.GET)
    public ResponseResult shareLikePic(@PathVariable Long shareId) {
        ResponseResult result = new ResponseResult();
        PageResult<MemberLike> pager = memberLikeService.findByShareId(shareId, new PageModel(1, 10));
        if (pager.getList() == null || pager.getList().size() == 0) {
            result.put("headPics", new JSONArray());
            return result;
        }
        JSONArray array = new JSONArray();
        pager.getList().forEach(item -> array.add(item.getHeadPic() == null ? "" : item.getHeadPic()));
        result.put("headPics", array);
        return result;
    }

    /**
     * 买家秀评论列表
     *
     * @param page
     * @param shareId
     * @return
     */
    @RequestMapping(value = "/comment/list/{shareId}", method = RequestMethod.GET)
    public ResponseResult commentList(PageModel page, @PathVariable Long shareId) {
        ResponseResult result = new ResponseResult();
        PageResult<MemberShareComment> pager = new PageResult<>();
        SearcherMemberShare memberShare = memberShareSearcherService.findById(shareId.toString());
        if (memberShare != null && memberShare.getComments() != null && memberShare.getComments() > 0) {
            MemberShareCommentSearcher cs = new MemberShareCommentSearcher();
            cs.setSourceId(shareId);
            cs.setVerified(1);
            cs.setSortCreateDate("DESC");
            try {
                cs.setMemberId(this.getLoginMemberInfo().getId());
                cs.setAllList(true);
            } catch (NotLoginException e) {
            }
            pager = memberShareCommentService.findBySearcher(page, cs);
        }
        JSONArray array = new JSONArray();
        pager.getList().forEach(item -> array.add(item.toJson()));
        result.putPage("comments", pager, array);
        return result;
    }

    /**
     * 新增买家秀评论
     *
     * @param memberShareComment
     * @param appTerminal
     * @param appVersion
     * @return
     */
    @RequestMapping(value = "/comment/insert", method = RequestMethod.POST)
    public ResponseResult commentInsert(MemberShareComment memberShareComment, String appTerminal, String appVersion) {
        ResponseResult result = new ResponseResult();
        MemberInfo member = this.getLoginMemberInfo();
        // 限制用户评论
        if (StringUtils.isNotBlank(member.getShield())) {
            JSONArray array = JSONArray.parseArray(member.getShield());
            if (array.contains(RestrictTypeEnum.SHARECOMMENT.toString())) {
                throw new BusinessException("你已被限制评论！");
            }
        }
        // 敏感字过滤
        memberShareComment.setMemberId(member.getId());
        memberShareComment.setHeadPic(member.getHeadPic());
        memberShareComment.setNickName(member.getDisplayName());
        memberShareComment.setVerified(1);
        memberShareComment.setAppVersion(appVersion);
        memberShareComment.setDevice(DeviceTypeEnum.divisionDevice(appTerminal));
        this.checkSensitiveWords(memberShareComment.getContent());
        memberShareComment = memberShareCommentService.insert(memberShareComment);
        if (memberShareComment == null) {
            throw new BusinessException("评论不成功！");
        } else {
            if (member.getId().intValue() != memberShareComment.getToMemberId().intValue()) {
                memberShareCommentService.doSendShareCommentMsg(memberShareComment, getLoginIp());
            }
            result.put("comment", memberShareComment.toJson());
            return result;
        }
    }

    /**
     * 删除买家秀评论
     *
     * @param commentId
     * @return
     */
    @RequestMapping(value = "/comment/delete/{commentId}", method = RequestMethod.GET)
    public ResponseResult commentDelete(@PathVariable Long commentId) {
        ResponseResult result = new ResponseResult();
        this.getLoginMemberInfo();
        int success = memberShareCommentService.deleteById(commentId);
        if (success <= 0) {
            throw new BusinessException("删除不成功！");
        }
        return result;
    }

    /**
     * 买家秀评论点赞
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/comment/like/{id}", method = RequestMethod.POST)
    public ResponseResult likeComment(Long id) {
        ResponseResult result = new ResponseResult();
        memberShareCommentService.doLike(id);
        return result;
    }

    /**
     * 买家秀选择商品
     *
     * @param searcher
     * @param keyword
     * @param page
     * @return
     */
    @RequestMapping(value = "/select/product", method = RequestMethod.GET)
    public ResponseResult selectProduct(ProductSearcher searcher, String keyword, PageModel page) {
        ResponseResult result = new ResponseResult();
        Long memberId = null;
        try {
            memberId = this.getLoginMemberInfo().getId();
        } catch (NotLoginException e) {
        }
        PageResult<SearcherProduct> pager = new PageResult<>();
        searcher.setKeywords(keyword);
        ProductProSearchQuery searcherBean = searcher.convertSearchQuery();
        if (memberId != null && StringUtils.isBlank(keyword) && searcher.getSn() == null) {
            List<Long> productIds = orderItemService.findProductIdByBuyer(memberId);
            if (productIds.size() > 0) {
                List<String> productIdsStr = new ArrayList<>();
                productIds.forEach(productId -> productIdsStr.add(productId.toString()));
                searcherBean.setProductIds(productIdsStr);
                pager = productSearcherQueryService.search(searcherBean, page);
            }
        }
        if (pager.getList() == null || pager.getList().size() == 0) {
            pager = productSearcherQueryService.search(searcherBean, page);
        }
        JSONArray array = new JSONArray();
        pager.getList().forEach(item -> array.add(item.toJson()));
        result.putPage("products", pager, array);
        return result;
    }

    /**
     * 我关注的买家秀列表
     *
     * @param page
     * @param searcher
     * @return
     */
    @RequestMapping(value = "/myfollow/list", method = RequestMethod.GET)
    public ResponseResult myFollows(PageModel page, MemberShareSearcher searcher) {
        ResponseResult result = new ResponseResult();
        if (searcher.getMemberId() == null || searcher.getMemberId() <= 0) {
            try {
                searcher.setMemberId(this.getLoginMemberInfo().getId());
            } catch (NotLoginException e) {
                result.putPage("memberShares", new PageResult<SearcherMemberShare>(), new JSONArray());
                return result;
            }
        }
        List<Long> memberIds = memberFollowSearcherService.findToIdByFromId(searcher.getMemberId());
        if (memberIds == null || memberIds.size() == 0) {
            result.putPage("memberShares", new PageResult<SearcherMemberShare>(), new JSONArray());
            return result;
        }
        PageResult<SearcherMemberShare> pager = memberShareSearcherService.findByMemberIds(memberIds, page, 1);
        JSONArray array = new JSONArray();
        List<SearcherMemberShare> memberShares = pager.getList();
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
        result.putPage("memberShares", pager, array);
        return result;
    }

    /**
     * 更新下载数
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/add/down/{id}", method = RequestMethod.POST)
    public ResponseResult addDown(@PathVariable Long id) {
        ResponseResult result = new ResponseResult();
        memberShareService.doAddDownCount(id);
        return result;
    }

    /**
     * 更新分享数
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/add/share/{id}", method = RequestMethod.POST)
    public ResponseResult addShare(@PathVariable Long id) {
        ResponseResult result = new ResponseResult();
        memberShareService.doAddShareCount(id);
        return result;
    }

}
