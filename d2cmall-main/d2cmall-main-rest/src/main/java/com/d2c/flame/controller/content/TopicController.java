package com.d2c.flame.controller.content;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.ResponseResult;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.search.model.SearcherMemberFollow;
import com.d2c.member.search.model.SearcherMemberLike;
import com.d2c.member.search.model.SearcherMemberShare;
import com.d2c.member.search.model.SearcherTopic;
import com.d2c.member.search.query.MemberShareSearchBean;
import com.d2c.member.search.query.TopicSearchBean;
import com.d2c.member.search.service.MemberFollowSearcherService;
import com.d2c.member.search.service.MemberLikeSearcherService;
import com.d2c.member.search.service.MemberShareSearcherService;
import com.d2c.member.search.service.TopicSearcherService;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 话题
 *
 * @author Lain
 * @version 3.0
 */
@RestController
@RequestMapping(value = "/v3/api/topic")
public class TopicController extends BaseController {

    @Reference
    private MemberShareSearcherService memberShareSearcherService;
    @Reference
    private MemberLikeSearcherService memberLikeSearcherService;
    @Reference
    private MemberFollowSearcherService memberFollowSearcherService;
    @Reference
    private TopicSearcherService topicSearcherService;

    /**
     * 话题下的买家秀列表
     *
     * @param id
     * @param page
     * @param orderType
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseResult detail(@PathVariable Long id, PageModel page, String orderType) {
        ResponseResult result = new ResponseResult();
        MemberShareSearchBean search = new MemberShareSearchBean();
        Long memberInfoId = null;
        try {
            MemberInfo memberInfo = this.getLoginMemberInfo();
            memberInfoId = memberInfo.getId();
            search.setMemberId(memberInfoId);
            search.setAllList(true);
        } catch (NotLoginException e) {
            search.setStatus(1);
        }
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
        SearcherTopic topic = topicSearcherService.findById(id);
        JSONObject obj = topic.toJson();
        obj.put("shareCount", pager.getTotalCount());
        result.put("topic", obj);
        result.putPage("memberShares", pager, array);
        return result;
    }

    /**
     * 话题列表
     *
     * @param page
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseResult list(PageModel page) {
        ResponseResult result = new ResponseResult();
        TopicSearchBean searcher = new TopicSearchBean();
        searcher.setStatus(1);
        PageResult<SearcherTopic> pager = topicSearcherService.search(searcher, page);
        MemberShareSearchBean search = new MemberShareSearchBean();
        try {
            MemberInfo memberInfo = this.getLoginMemberInfo();
            search.setMemberId(memberInfo.getId());
            search.setAllList(true);
        } catch (NotLoginException e) {
            search.setStatus(1);
        }
        JSONArray array = new JSONArray();
        for (SearcherTopic topic : pager.getList()) {
            JSONObject obj = topic.toJson();
            search.setTopicId(topic.getId());
            obj.put("shareCount", memberShareSearcherService.count(search));
            array.add(obj);
        }
        result.putPage("topics", pager, array);
        return result;
    }

}
