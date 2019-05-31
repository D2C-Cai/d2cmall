package com.d2c.flame.controller.member;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.ResponseResult;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.model.PiliLive;
import com.d2c.member.search.model.SearcherMemberFollow;
import com.d2c.member.search.model.SearcherMemberLike;
import com.d2c.member.search.model.SearcherMemberShare;
import com.d2c.member.search.service.MemberFollowSearcherService;
import com.d2c.member.search.service.MemberLikeSearcherService;
import com.d2c.member.search.service.MemberShareSearcherService;
import com.d2c.member.search.support.MemberShareHelp;
import com.d2c.member.service.PiliLiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 达人榜单
 *
 * @author Lain
 * @version 3.0
 */
@RestController
@RequestMapping(value = "/v3/api/hot")
public class HotRankController extends BaseController {

    @Reference
    private MemberShareSearcherService memberShareSearcherService;
    @Reference
    private MemberFollowSearcherService memberFollowSearcherService;
    @Reference
    private MemberLikeSearcherService memberLikeSearcherService;
    @Autowired
    private PiliLiveService piliLiveService;

    /**
     * 热门买家秀或直播
     *
     * @param type
     * @param page
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseResult hotList(String type, PageModel page) {
        ResponseResult result = new ResponseResult();
        if (type == null) {
            type = "pic";
        }
        JSONArray array = new JSONArray();
        if ("live".equals(type)) {
            // 直播
            PageResult<PiliLive> pager = piliLiveService.findHot(page);
            List<PiliLive> list = pager.getList();
            try {
                MemberInfo memberInfo = this.getLoginMemberInfo();
                String[] memberFollowids = new String[list.size()];
                for (int i = 0; i < list.size(); i++) {
                    memberFollowids[i] = memberInfo.getId() + "_" + list.get(i).getMemberId();
                }
                Map<Long, SearcherMemberFollow> memberFollows = memberFollowSearcherService.findByIds(memberFollowids);
                for (PiliLive searcherZegoLive : list) {
                    JSONObject obj = searcherZegoLive.toJson();
                    if (memberFollows.containsKey(searcherZegoLive.getMemberId())) {
                        obj.put("follow", memberFollows.get(searcherZegoLive.getMemberId()).getFollowType());
                    } else {
                        obj.put("follow", SearcherMemberFollow.FollowType.UNFOLLOW.getCode());
                    }
                    array.add(obj);
                }
            } catch (NotLoginException e) {
                for (PiliLive live : list) {
                    JSONObject obj = live.toJson();
                    obj.put("follow", SearcherMemberFollow.FollowType.UNFOLLOW.getCode());
                    array.add(obj);
                }
            }
            result.putPage("hotLives", pager, array);
        } else {
            // 买家秀
            PageResult<SearcherMemberShare> pager = memberShareSearcherService.findHotShare(type, page);
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
                for (SearcherMemberShare memberShare : memberShares) {
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
                for (SearcherMemberShare memberShare : memberShares) {
                    memberShare.setLiked(0);
                    JSONObject obj = memberShare.toJson();
                    obj.put("follow", SearcherMemberFollow.FollowType.UNFOLLOW.getCode());
                    array.add(obj);
                }
            }
            result.putPage("hot" + (type.substring(0, 1).toUpperCase() + type.substring(1, type.length())), pager,
                    array);
        }
        return result;
    }

    /**
     * 达人排行榜
     *
     * @param type
     * @param page
     * @return
     */
    @RequestMapping(value = "/member", method = RequestMethod.GET)
    public ResponseResult talentList(String type, PageModel page) {
        if (type == null) {
            type = "member";
        }
        ResponseResult result = new ResponseResult();
        JSONArray array = new JSONArray();
        List<MemberShareHelp> list = memberShareSearcherService.findHotMember(type, page);
        try {
            MemberInfo memberInfo = this.getLoginMemberInfo();
            String[] memberFollowids = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                memberFollowids[i] = memberInfo.getId() + "_" + list.get(i).getMemberId();
            }
            Map<Long, SearcherMemberFollow> memberFollows = memberFollowSearcherService.findByIds(memberFollowids);
            for (MemberShareHelp memberShareHelp : list) {
                JSONObject obj = memberShareHelp.toJson();
                if (memberFollows.containsKey(memberShareHelp.getMemberId())) {
                    obj.put("follow", memberFollows.get(memberShareHelp.getMemberId()).getFollowType());
                } else {
                    obj.put("follow", SearcherMemberFollow.FollowType.UNFOLLOW.getCode());
                }
                array.add(obj);
            }
        } catch (NotLoginException e) {
            for (MemberShareHelp memberShareHelp : list) {
                JSONObject obj = memberShareHelp.toJson();
                obj.put("follow", SearcherMemberFollow.FollowType.UNFOLLOW.getCode());
                array.add(obj);
            }
        }
        result.put("hot" + (type.substring(0, 1).toUpperCase() + type.substring(1, type.length())), array);
        return result;
    }

    /**
     * 活跃用户
     *
     * @param type
     * @param page
     * @return
     */
    @RequestMapping(value = "/active/member", method = RequestMethod.GET)
    public ResponseResult activeMember(String type, PageModel page) {
        ResponseResult result = new ResponseResult();
        PageResult<MemberShareHelp> pager = new PageResult<MemberShareHelp>(page);
        List<MemberShareHelp> list = memberShareSearcherService.findMemberOrderByCount(type, 60);
        pager.setTotalCount(list.size());
        if (page.getStartNumber() > list.size()) {
            return result;
        }
        JSONArray array = new JSONArray();
        try {
            MemberInfo memberInfo = this.getLoginMemberInfo();
            String[] memberFollowids = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                memberFollowids[i] = memberInfo.getId() + "_" + list.get(i).getMemberId();
            }
            Map<Long, SearcherMemberFollow> memberFollows = memberFollowSearcherService.findByIds(memberFollowids);
            for (MemberShareHelp talent : list) {
                JSONObject obj = this.getMemberShare(talent);
                if (memberFollows.containsKey(talent.getMemberId())) {
                    obj.put("follow", memberFollows.get(talent.getMemberId()).getFollowType());
                } else {
                    obj.put("follow", SearcherMemberFollow.FollowType.UNFOLLOW.getCode());
                }
                array.add(obj);
            }
        } catch (NotLoginException e) {
            for (MemberShareHelp talent : list) {
                JSONObject obj = this.getMemberShare(talent);
                obj.put("follow", SearcherMemberFollow.FollowType.UNFOLLOW.getCode());
                array.add(obj);
            }
        }
        result.putPage("activeMember", pager, array);
        return result;
    }

    private JSONObject getMemberShare(MemberShareHelp talent) {
        JSONObject json = talent.toJson();
        PageModel page = new PageModel();
        page.setPageSize(3);
        PageResult<SearcherMemberShare> pager = memberShareSearcherService
                .findByMemberIds(Arrays.asList(talent.getMemberId()), page, 1);
        JSONArray array = new JSONArray();
        pager.getList().forEach(item -> array.add(item.toJson()));
        json.put("shares", array);
        return json;
    }

}
