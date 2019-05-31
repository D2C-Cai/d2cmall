package com.d2c.flame.controller;

import com.d2c.cache.redis.RedisHandler;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.ResponseResult;
import com.d2c.frame.web.control.BaseControl;
import com.d2c.member.model.Member;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.query.MemberSearcher;
import com.d2c.member.service.MemberInfoService;
import com.d2c.member.service.MemberService;
import com.d2c.util.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/report/member")
public class MemberController extends BaseControl {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberInfoService memberInfoService;
    @Autowired
    private RedisHandler<String, Map<String, Object>> redisHandler;

    /**
     * 头部数据
     *
     * @param cookie
     * @return
     */
    @RequestMapping(value = "/head", method = RequestMethod.GET)
    public ResponseResult head() {
        ResponseResult result = new ResponseResult();
        result.setData(redisHandler.opsForValue().get("report_member"));
        return result;
    }

    /**
     * 今日游客
     *
     * @param cookie
     * @param page
     * @return
     */
    @RequestMapping(value = "/mem/list", method = RequestMethod.GET)
    public ResponseResult memList(PageModel page) {
        ResponseResult result = new ResponseResult();
        Date today = new Date();
        Date startDate = DateUtil.getStartOfDay(today);
        Date endDate = DateUtil.getEndOfDay(today);
        MemberSearcher searcher = new MemberSearcher();
        searcher.setStartDate(startDate);
        searcher.setEndDate(endDate);
        PageResult<Member> pager = memberService.findBySearch(searcher, page);
        result.putPage("pager", pager);
        return result;
    }

    /**
     * 今日会员
     *
     * @param cookie
     * @param page
     * @return
     */
    @RequestMapping(value = "/info/list", method = RequestMethod.GET)
    public ResponseResult infoList(PageModel page) {
        ResponseResult result = new ResponseResult();
        Date today = new Date();
        Date startDate = DateUtil.getStartOfDay(today);
        Date endDate = DateUtil.getEndOfDay(today);
        MemberSearcher searcher = new MemberSearcher();
        searcher.setStartDate(startDate);
        searcher.setEndDate(endDate);
        PageResult<MemberInfo> pager = memberInfoService.findBySearch(searcher, page);
        result.putPage("pager", pager);
        return result;
    }

}
