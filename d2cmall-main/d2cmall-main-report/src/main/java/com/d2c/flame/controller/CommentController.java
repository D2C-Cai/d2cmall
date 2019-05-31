package com.d2c.flame.controller;

import com.d2c.cache.redis.RedisHandler;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.ResponseResult;
import com.d2c.frame.web.control.BaseControl;
import com.d2c.member.model.Comment;
import com.d2c.member.query.CommentSearcher;
import com.d2c.member.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/report/comment")
public class CommentController extends BaseControl {

    @Autowired
    private CommentService commentService;
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
        result.setData(redisHandler.get("report_comment"));
        return result;
    }

    /**
     * 今日评价
     *
     * @param cookie
     * @param page
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseResult list(PageModel page) {
        ResponseResult result = new ResponseResult();
        CommentSearcher searcher = new CommentSearcher();
        PageResult<Comment> pager = commentService.findSimpleBySearcher(searcher, page);
        result.putPage("pager", pager);
        return result;
    }

}
