package com.d2c.flame.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.member.dto.CommentDto;
import com.d2c.member.model.CommentReply;
import com.d2c.member.search.model.SearcherComment;
import com.d2c.member.search.model.SearcherCommentReply;
import com.d2c.member.search.query.CommentSearchBean;
import com.d2c.member.search.service.CommentReplySearcherService;
import com.d2c.member.search.service.CommentSearcherService;
import com.d2c.product.dto.ProductCombDto;
import com.d2c.product.service.ProductCombService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping({"/productcomb", "/productComb"})
public class ProductCombController extends BaseController {

    @Autowired
    private ProductCombService productCombService;
    @Reference
    private CommentSearcherService commentSearcherService;
    @Reference
    private CommentReplySearcherService commentReplySearcherService;

    /**
     * 组合商品
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable Long id, ModelMap model) {
        ProductCombDto productComb = productCombService.findDtoById(id);
        if (productComb == null || productComb.getMark() < 0) {
            throw new BusinessException("商品已经下架！");
        }
        model.put("productComb", productComb);
        return "product/productcomb_detail";
    }

    /**
     * 组合商品评论详情
     *
     * @param ids
     * @param model
     * @param page
     * @return
     */
    @RequestMapping(value = "/comment", method = RequestMethod.GET)
    public String comment(long[] ids, ModelMap model, PageModel page) {
        PageResult<CommentDto> pager = getComments(page, ids);
        model.put("pager", pager);
        return "product/comment";
    }

    private PageResult<CommentDto> getComments(PageModel page, long[] productIds) {
        PageResult<SearcherComment> commentsPager = commentSearcherService.findCombComment(productIds, page);
        CommentSearchBean replyBean = new CommentSearchBean();
        replyBean.setCommentIds(new long[commentsPager.getList().size()]);
        for (int i = 0; i < commentsPager.getList().size(); i++) {
            SearcherComment comment = commentsPager.getList().get(i);
            replyBean.getCommentIds()[i] = comment.getId();
        }
        PageResult<SearcherCommentReply> replyPager = commentReplySearcherService.search(replyBean, page);
        HashMap<Long, List<CommentReply>> replyMap = new HashMap<Long, List<CommentReply>>();
        for (int j = 0; j < replyPager.getList().size(); j++) {
            SearcherCommentReply replySearcher = replyPager.getList().get(j);
            CommentReply reply = new CommentReply();
            BeanUtils.copyProperties(replySearcher, reply);
            if (replyMap.get(reply.getCommentId()) == null) {
                replyMap.put(reply.getCommentId(), new ArrayList<CommentReply>());
            }
            replyMap.get(reply.getCommentId()).add(reply);
        }
        PageResult<CommentDto> pager = new PageResult<CommentDto>();
        BeanUtils.copyProperties(commentsPager, pager, "list");
        for (int i = 0; i < commentsPager.getList().size(); i++) {
            SearcherComment comment = commentsPager.getList().get(i);
            CommentDto comDto = new CommentDto();
            BeanUtils.copyProperties(comment, comDto);
            List<CommentReply> commentReplys = replyMap.get(comDto.getId());
            if (commentReplys == null)
                commentReplys = new ArrayList<CommentReply>();
            comDto.setCommentReplys(commentReplys);
            pager.getList().add(comDto);
        }
        return pager;
    }

}
